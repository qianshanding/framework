package com.qianshanding.framework.test.kafka9;

import com.qianshanding.framework.test.kafka9.impl.KafkaClientProducerImpl;

/**
 * @Author : jianghengwei
 * @Date :Created by 2017/2/6 下午2:02.
 * @Description :
 */
public class KafkaClientProducerTest {


	public static void main(String[] args) {

		String brokerAddress = "10.9.46.127:9092,10.9.47.232:9092";
		KafkaClientProducer producer = new KafkaClientProducerImpl(brokerAddress);


		String topic = "test-data-001";
		Thread task = new Thread(new Runnable() {
			@Override
			public void run() {
				 long count = 0;

				while (true) {
					producer.sendMessages(topic, "hello,122333,flg=111--> " + count++);

					try {
						Thread.sleep(5L);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		String topic2 = "test-data-002";
		Thread task2 = new Thread(new Runnable() {
			@Override
			public void run() {
				long count = 0;

				while (true) {
					producer.sendMessages(topic2, "topic is 2222,122333,flg=111--> " + count++);
					try {
						Thread.sleep(5L);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});

		task.start();
		task2.start();

	}
}
