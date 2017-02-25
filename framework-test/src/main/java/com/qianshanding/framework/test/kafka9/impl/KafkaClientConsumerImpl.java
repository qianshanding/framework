package com.qianshanding.framework.test.kafka9.impl;

import com.qianshanding.framework.test.kafka9.NamedThreadFactory;
import com.google.common.base.Preconditions;
import com.qianshanding.framework.test.kafka9.ConsumerCallback;
import com.qianshanding.framework.test.kafka9.KafkaClientConsumer;
import com.qianshanding.framework.test.kafka9.KafkaConfig;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author : jianghengwei
 * @Date :Created by 2017/2/6 上午10:23.
 * @Description :kafka9 consumer 采用默认自动提交的方式
 */
public class KafkaClientConsumerImpl implements KafkaClientConsumer {

    private static ExecutorService consumerExecutors = Executors.newCachedThreadPool(new NamedThreadFactory("kafka9-consumer"));

    private List<String> topicList = null;

    private String brokerAddress;

    private String topic;

    private String group;

    private KafkaConsumer<String, String> consumer;

    private List<ConsumerCallback> callbackList = new ArrayList<>();

    public KafkaClientConsumerImpl(String topic, String brokerAddress, String group) {

        Preconditions.checkArgument(StringUtils.isNotBlank(topic), "topic is null.");
        Preconditions.checkArgument(StringUtils.isNotBlank(brokerAddress), "broker address is null.");
        Preconditions.checkArgument(StringUtils.isNotBlank(group), "group name is null.");

        this.brokerAddress = brokerAddress;
        this.topic = topic;
        this.group = group;
        this.topicList = convertTopic(topic);
        this.consumer = createConsumer(brokerAddress, group);
    }

    public void start() {

        this.consumer.subscribe(this.topicList);
        consumerExecutors.execute(new Runnable() {
            @Override
            public void run() {
                process();
            }
        });

    }

    public KafkaClientConsumerImpl(String topic, String brokerAddress, String group, ConsumerCallback kafkaCallback) {

        Preconditions.checkArgument(StringUtils.isNotBlank(topic), "topic is null.");
        Preconditions.checkArgument(StringUtils.isNotBlank(brokerAddress), "broker address is null.");
        Preconditions.checkArgument(StringUtils.isNotBlank(group), "group name is null.");

        this.brokerAddress = brokerAddress;
        this.topic = topic;
        this.group = group;
        callbackList.add(kafkaCallback);
        this.topicList = convertTopic(topic);

        this.consumer = createConsumer(brokerAddress, group);
        this.setKafkaCallback(kafkaCallback);
    }

    public void setKafkaCallback(ConsumerCallback callback) {
        callbackList.add(callback);
    }

    protected void process() {

        while (true) {

            ConsumerRecords<String, String> records = this.consumer.poll(200);

            Map<String, List<String>> topicMap = new ConcurrentHashMap<>();

            for (ConsumerRecord<String, String> record : records) {
                String topic = record.topic();
                topicMap.putIfAbsent(topic, new ArrayList());
                List<String> dataList = topicMap.get(topic);
                dataList.add(record.value());
            }

            //按topic 回调
            for (ConsumerCallback consumerCallback : callbackList) {
                for (Map.Entry<String, List<String>> entry : topicMap.entrySet()) {
                    consumerCallback.callback(entry.getKey(), entry.getValue());
                }
            }
            topicMap.clear();
        }
    }

    @Override
    public String getBrokerAddress() {
        return this.brokerAddress;
    }

    @Override
    public String getTopic() {
        return this.topic;
    }

    @Override
    public String getGroup() {
        return this.group;
    }


    public static KafkaConsumer<String, String> createConsumer(String brokerAddress, String group) {

        Preconditions.checkArgument(StringUtils.isEmpty(brokerAddress), "broker address is null.");
        Preconditions.checkArgument(StringUtils.isNotBlank(group), "group name is null.");

        Properties props = new Properties();
        props.put("bootstrap.servers", brokerAddress);
        props.put("group.id", group);

        // Set this property, if auto commit should happen.
        props.put("enable.auto.commit", "true");

        // Auto commit interval is an important property, kafka9 would commit offset at this interval.
        props.put("auto.commit.interval.ms", "101");

        // Max bytes (10K)
        props.put("max.partition.fetch.bytes", "10240");

        // Set this if you want to always read from beginning.
        props.put("auto.offset.reset", "earliest");
        props.put("heartbeat.interval.ms", "3000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", KafkaConfig.KEY_DESERIALIZER);
        props.put("value.deserializer", KafkaConfig.VALUE_DESERIALIZER);
        return new KafkaConsumer<String, String>(props);
    }

    public static List<String> convertTopic(String topics) {

        List<String> topicList = new ArrayList<>();
        if (StringUtils.isBlank(topics)) return new ArrayList<>();

        String[] datas = topics.split(",");
        for (String topic : datas) {
            if (StringUtils.isBlank(topic)) continue;
            topicList.add(topic.trim());
        }
        return topicList;

    }
}
