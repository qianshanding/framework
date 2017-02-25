package com.qianshanding.framework.test.kafka9;

/**
 * @Author : jianghengwei
 * @Date :Created by 2017/2/6 上午10:41.
 * @Description : KafaConsumer 基础类
 */
public interface KafkaClientConsumer {

	String getBrokerAddress();

	String getTopic();

	String getGroup();

	void setKafkaCallback(ConsumerCallback callback);

	void start();
}
