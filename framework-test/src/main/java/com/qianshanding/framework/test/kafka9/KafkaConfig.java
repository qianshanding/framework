package com.qianshanding.framework.test.kafka9;

/**
 * @Author : jianghengwei
 * @Date :Created by 2017/2/6 下午2:20.
 * @Description : 静态公共配置类
 */
public interface KafkaConfig {

	String KEY_SERIALIZER = "org.apache.kafka9.commons.serialization.StringSerializer";
	String VALUE_SERIALIZER = "org.apache.kafka9.commons.serialization.StringSerializer";

	String KEY_DESERIALIZER = "org.apache.kafka9.commons.serialization.StringDeserializer";
	String VALUE_DESERIALIZER = "org.apache.kafka9.commons.serialization.StringDeserializer";

}
