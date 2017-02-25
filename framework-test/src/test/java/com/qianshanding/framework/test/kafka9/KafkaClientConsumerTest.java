package com.qianshanding.framework.test.kafka9;

import com.qianshanding.framework.test.kafka9.impl.KafkaClientConsumerImpl;

import java.util.List;

/**
 * @Author : jianghengwei
 * @Date :Created by 2017/2/6 下午2:06.
 * @Description :
 */
public class KafkaClientConsumerTest {

	public static void main(String[] args) {

		String topic = "test-data-001, test-data-002";
		String brokerAddress = "10.9.46.127:9092,10.9.47.232:9092";

		String group = "default-a";
		KafkaClientConsumer clientConsumer = new KafkaClientConsumerImpl(topic, brokerAddress, group, new ConsumerCallback() {
			@Override
			public void callback(String topic, final List<String> dataList) {

				System.out.println("topic is " +  topic + ",   " + dataList);
			}
		});



		clientConsumer.start();
	}
}
