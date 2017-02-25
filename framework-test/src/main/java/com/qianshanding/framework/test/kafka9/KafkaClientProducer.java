package com.qianshanding.framework.test.kafka9;

/**
 * @Author : jianghengwei
 * @Date :Created by 2017/2/6 下午12:25.
 * @Description :生产者发送消息
 */
public interface KafkaClientProducer {

	void sendMessages(String topic, String value);

	String getBrokerAddress();

}
