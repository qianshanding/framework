package com.qsd.framework.test.webmagic;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.io.*;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zhengyu on 2017/7/13.
 */
public class JubiPageProcessor implements PageProcessor {
    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);

    private static Spider spider;

    private static int count = 0;

    private static Map<String, BinDetail> nameMap = new ConcurrentHashMap<>();

    private static Map<String, Double> highValueMap = new ConcurrentHashMap<>();
    private static Map<String, Double> lowValueMap = new ConcurrentHashMap<>();


    private static List<String> dataList = new ArrayList<>();

    public static void main(String[] args) {
        spider = Spider.create(new JubiPageProcessor());
        spider.addUrl("https://www.jubi.com").thread(5).run();

        System.out.println("name size = " + nameMap.size());

        System.out.println("highValueMap size = " + highValueMap.size());
        System.out.println("lowValueMap size = " + lowValueMap.size());
        System.out.println("count = " + count);

        for (String name : highValueMap.keySet()) {
            double rate = highValueMap.get(name) / lowValueMap.get(name);
//            if (rate > 20) {
//                System.out.println(name + " : 最高价->\t\t" + highValueMap.get(name));
//                System.out.println(name + " : 最低价->\t\t" + lowValueMap.get(name));
//                System.out.println(name + " : 增幅比->\t\t" + rate + " 倍");
//                System.out.println("---------------------" + name + "-------------------------");
//            }
            System.out.println(name + " : 最高价->\t\t" + highValueMap.get(name));
            System.out.println(name + " : 最低价->\t\t" + lowValueMap.get(name));
            System.out.println(name + " : 增幅比->\t\t" + rate + " 倍");
            System.out.println("---------------------" + name + "-------------------------");
        }

        double minPrice = Double.MAX_VALUE;
        String name = "";
        for (String key : nameMap.keySet()) {
            Double nowPrice = nameMap.get(key).getNowPrice();
            if (nowPrice < minPrice) {
                minPrice = nowPrice;
                name = nameMap.get(key).getChinaName();
            }
        }
        System.out.println(name + "--->" + minPrice);

        writeFile(dataList, "/Users/zhengyu/Documents/业务文档/bi/data-" + new SimpleDateFormat(("yyyy-MM-dd")).format(new Date()));
        List<String> nameList = new ArrayList<>();
        nameList.add(JSON.toJSONString(nameMap));
        writeFile(nameList, "/Users/zhengyu/Documents/业务文档/bi/name-" + new SimpleDateFormat(("yyyy-MM-dd")).format(new Date()));
    }

    @Override
    public void process(Page page) {
        try {
            count++;

            if (page.getUrl().get().equals("https://www.jubi.com")) {
                List<String> chinaNameList = page.getJson().regex("<p>(\\S*)&nbsp;").all();
                List<String> urlSuffixList = page.getJson().regex("<dt><i></i><a href=\"(\\S*)\"><").all();
                List<String> enNameList = page.getJson().regex("&nbsp;<b>(\\S*)</b>").all();

                Set<String> dataUrls = new HashSet<>();
                Set<String> datailUrls = new HashSet<>();
                int i = 0;
                for (String chinaName : chinaNameList) {
                    nameMap.put(enNameList.get(i) + "/CNY", new BinDetail(null, null, null, chinaName, enNameList.get(i)));
                    String dataUrl = "https://www.jubi.com" + urlSuffixList.get(i).replace("peb.html", "") + "k.js";
                    dataUrls.add(dataUrl);

                    String detailUrl = "https://www.jubi.com" + urlSuffixList.get(i);
                    datailUrls.add(detailUrl);
                    i++;
                }
                page.addTargetRequests(new ArrayList<>(dataUrls));
                page.addTargetRequest("https://www.jubi.com/coin/allcoin");
            } else if (page.getUrl().get().equals("https://www.jubi.com/coin/allcoin")) {
                nameMap = new HashMap<>();
                Map<String, List<Object>> data = JSON.parseObject(page.getJson().get(), new TypeReference<Map<String, List<Object>>>() {
                });

                for (String enName : data.keySet()) {
                    String chinaName = URLDecoder.decode(String.valueOf(data.get(enName).get(0)), "UTF-8");
                    Double nowPrice = Double.valueOf(String.valueOf(data.get(enName).get(1)));

                    nameMap.put(enName.toUpperCase() + "/CNY", new BinDetail(nowPrice, null, null, chinaName, enName));

                }
            } else if (page.getUrl().get().endsWith("k.js")) {

                String json = page.getJson().get().replace("chart=", "").trim();

                dataList.add(json);

                ChartData data = JSON.parseObject(json.substring(0, json.length() - 1), ChartData.class);

                List<List<Object>> allList = data.getTime_line().get("1d");
                for (List<Object> values : allList) {
                    //时间
                    long time = Long.valueOf(String.valueOf(values.get(0)));
                    //成交量
                    double payNum = Double.parseDouble(String.valueOf(values.get(1)));
                    //开盘价
                    double startPrice = Double.parseDouble(String.valueOf(values.get(2)));
                    //最高价
                    double highPrice = Double.parseDouble(String.valueOf(values.get(3)));
                    //最低价
                    double lowPrice = Double.parseDouble(String.valueOf(values.get(4)));
                    //收盘价
                    double endPrice = Double.parseDouble(String.valueOf(values.get(5)));

                    BinDetail binDetail = nameMap.get(data.getSymbol_view());

                    String chinaName = binDetail.getChinaName();

                    if (highValueMap.get(chinaName) == null) {
                        highValueMap.put(chinaName, highPrice);
                    } else {
                        highValueMap.put(chinaName, highValueMap.get(chinaName) > highPrice == true ? highValueMap.get(chinaName) : highPrice);
                    }

                    if (lowValueMap.get(chinaName) == null) {
                        lowValueMap.put(chinaName, lowPrice);
                    } else {
                        lowValueMap.put(chinaName, lowValueMap.get(chinaName) < lowPrice == true ? lowValueMap.get(chinaName) : lowPrice);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    private static void writeFile(List<String> dataList, String fileName) {
        try {
            FileWriter writer = new FileWriter(fileName);
            BufferedWriter bw = new BufferedWriter(writer);
            for (String data : dataList) {
                bw.write(data + "\n");
            }
            bw.close();
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
    }


    private static List<String> readFile(String fileName) {
        List<String> dataList = new ArrayList<>();
        try {
            FileReader reader = new FileReader(fileName);
            BufferedReader br = new BufferedReader(reader);

            String str = null;

            while ((str = br.readLine()) != null) {
                dataList.add(str);
            }
            br.close();
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataList;
    }
}
