package com.qsd.framework.test.webmagic;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhengyu on 2017/7/15.
 */
@Data
public class ChartData {
    private String symbol;
    private String symbol_view;
    private Float ask;
    private Map<String, List<List<Object>>> time_line;
    public static void main(String[] args) {
        ChartData chartData = new ChartData();
        chartData.setSymbol("LSK_CNY");
        chartData.setSymbol_view("LSK/CNY");
        chartData.setAsk(1.2f);

        Map<String, List<List<Object>>> time_line = new HashMap<>();
        List<Object> values = new ArrayList<>();
        values.add(1500063300000l);
        values.add(2.994);
        values.add("68.0000000000");
        values.add(68);
        values.add(68);
        values.add(68.0000000000);

        List<List<Object>> list = new ArrayList<>();
        list.add(values);
        list.add(new ArrayList<>(values));
        time_line.put("5m", list);

        List<Object> values1 = new ArrayList<>();
        values1.add(1500063300000l);
        values1.add(2.994);
        values1.add("68.0000000000");
        values1.add(68);
        values1.add(68);
        values1.add(68.0000000000);

        List<List<Object>> list1 = new ArrayList<>();
        list1.add(values1);
        list1.add(new ArrayList<>(values1));
        time_line.put("1d", new ArrayList<>(list1));

        chartData.setTime_line(time_line);

        System.out.println(JSON.toJSONString(chartData));
        System.out.println(JSON.parseObject(JSON.toJSONString(chartData)));
    }
}
