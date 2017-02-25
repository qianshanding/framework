package com.qianshanding.framework.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @FileName SpringRedisClientImpl.java
 * @Description:
 * @Date 2016年3月7日 下午2:07:30
 * @author Administrator Exp
 * @version V1.0
 * 
 */
public class SimpleRedisClient extends AbstractRedistClient<JedisPool, Jedis> {

	JedisPool jedisPool;

	public SimpleRedisClient() {

	}

	public void setJedisPool(JedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}

	@Override
	public void initPool() {
		this.setRedisPool(jedisPool);
	}

	@Override
	public Jedis getJedis() {
		if (getRedisPool() == null) {
			initPool();
		}
		return getRedisPool().getResource();
	}
}