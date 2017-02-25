package com.qianshanding.framework.test.kafka9;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.utils.Utils;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author : jianghengwei
 * @Date :Created by 2017/2/6 上午11:33.
 * @Description : 消息分区处理
 */
public class RobinPartitioner implements Partitioner {

	private final AtomicInteger counter = new AtomicInteger(new Random().nextInt());

	private static int toPositive(int number) {
		return number & 0x7fffffff;
	}

	@Override
	public int partition(String topic, Object k, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
		List<PartitionInfo> partitions = cluster.partitionsForTopic(topic);
		int numPartitions = partitions.size();
		if (keyBytes == null) {
			int nextValue = counter.getAndIncrement();
			List<PartitionInfo> availablePartitions = cluster.availablePartitionsForTopic(topic);
			if (availablePartitions.size() > 0) {
				int part = toPositive(nextValue) % availablePartitions.size();
				return availablePartitions.get(part).partition();
			} else {
				// no partitions are available, give a non-available partition
				return toPositive(nextValue) % numPartitions;
			}
		} else {
			// hash the keyBytes to choose a partition
			return toPositive(Utils.murmur2(keyBytes)) % numPartitions;
		}
	}

	@Override
	public void close() {}

	@Override
	public void configure(final Map<String, ?> map) {}

}
