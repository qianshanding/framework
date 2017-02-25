package com.qianshanding.framework.redis;

import redis.clients.jedis.Jedis;

/**
 * @FileName RedisManage.java
 * @Description:
 * @Date 2016年3月7日 下午12:50:22
 * @author Administrator Exp
 * @version V1.0
 * 
 */
public interface RedisManage {

	/**
	 * @Title: getJedis
	 * @Description:获取jedis
	 * @return
	 * @author Administrator
	 * @date 2016年3月7日
	 */
	public abstract Jedis getJedis();

	/**
	 * @Title: returnResource
	 * @Description:获取Resource
	 * @param jedis
	 * @author Administrator
	 * @date 2016年3月7日
	 */
	public void returnResource(Jedis jedis);

	/**
	 * @Title: ping
	 * @Description:
	 * @return
	 * @author Administrator
	 * @date 2016年3月7日
	 */
	public abstract String ping();

	/**
	 * @Title: dbSize
	 * @Description:DB size
	 * @return
	 * @author Administrator
	 * @date 2016年3月7日
	 */
	public abstract long dbSize();

	/**
	 * @Title: flushDB
	 * @Description:Flush db.
	 * @return
	 * @author Administrator
	 * @date 2016年3月7日
	 */
	public abstract String flushDB();

	/**
	 * @Title: info
	 * @Description:
	 * @return
	 * @author Administrator
	 * @date 2016年3月7日
	 */
	public abstract String info();

}
