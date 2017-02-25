package com.qianshanding.framework.test.kafka9.impl;

import com.qianshanding.framework.test.kafka9.KafkaClientProducer;
import com.qianshanding.framework.test.kafka9.KafkaConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 * @Author : jianghengwei
 * @Date :Created by 2017/2/6 上午11:18.
 * @Description :消息发送实现类
 */
public class KafkaClientProducerImpl implements KafkaClientProducer {

	private String brokerAddress;

	private Producer kafkaProducer;

	public KafkaClientProducerImpl(String brokerAddress) {

		this.brokerAddress = brokerAddress;
		this.kafkaProducer = createProducer(brokerAddress);
	}

	private static Producer<String, String> createProducer(String brokerAddress) {
		Properties props = new Properties();
		props.put("bootstrap.servers", brokerAddress);
		props.put("acks", "all");
		props.put("retries", 0);
		props.put("batch.size", 10);
		props.put("linger.ms", 1);
		props.put("key.serializer", KafkaConfig.KEY_SERIALIZER);
		props.put("value.serializer", KafkaConfig.VALUE_SERIALIZER);
		props.put("partitioner.class", "RobinPartitioner");
		return new KafkaProducer(props);
	}

	public void sendMessages(String topic, String value) {
		this.kafkaProducer.send(new ProducerRecord<String, String>(topic, value));
	}

	@Override
	public String getBrokerAddress() {
		return brokerAddress;
	}

}
