package com.qianshanding.framework.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @FileName RedisOperate.java
 * @Description:
 * @Date 2016年3月7日 下午12:46:24
 * @author Administrator Exp
 * @version V1.0
 * 
 */
public interface RedisOperate {
	public Set<String> keys(String pattern);

	public String get(String key);

	public Boolean exists(String key);

	public String set(byte[] key, byte[] value, int liveTime);

	public String set(String key, String value, int liveTime);

	public Long del(String key);

	public Long del(byte[] key);

	public String set(String key, String value);

	public String set(byte[] key, byte[] value);

	public byte[] get(byte[] key);

	public String rename(String oldKey, String newKey);

	public Long expire(String key, int seconds);

	public String getSet(final String key, final String value);

	public List<String> mget(final String... keys);

	public Long setnx(final String key, final String value);

	public String setex(final String key, final int seconds, final String value);

	public Long append(final String key, final String value);

	public Long rpush(final String key, final String... strings);

	public Long lpush(final String key, final String... strings);

	public Long llen(final String key);

	public String lindex(final String key, final long index);

	public Long lrem(final String key, long count, final String value);

	public String lset(final String key, final long index, final String value);

	public String lpop(final String key);

	public String rpop(final String key);

	public Long hset(final String key, final String field, final String value);

	public String hget(final String key, final String field);

	public Long hsetnx(final String key, final String field, final String value);

	public String hmset(final String key, final Map<String, String> hash);

	public Double hincrByFloat(String key, String field, Double value);
	
	public Long hincrBy(String key, String field, Long value);
	
	public Map<String, String> hgetAll(String key);

	public List<String> hmget(final String key, final String... fields);

	public Boolean hexists(final String key, final String field);

	public Long hdel(final String key, final String... fields);

	public Long hlen(final String key);

	public Set<String> hkeys(final String key);

	public List<String> hvals(final String key);

	public Long sadd(final String key, final String... members);

	public Set<String> smembers(final String key);

	public Long srem(final String key, final String... members);

	public String spop(final String key);

	public Long smove(final String srckey, final String dstkey, final String member);

	public Long scard(final String key);

	public Boolean sismember(final String key, final String member);

	public Set<String> sinter(final String... keys);

	public void sinterstore(final String dstkey, final String... keys);

	public Set<String> sunion(final String... keys);

	public Long sunionstore(final String dstkey, final String... keys);

	public Set<String> sdiff(final String... keys);

	public Long sdiffstore(final String dstkey, final String... keys);

	public String srandmember(final String key);

	public Long zadd(final String key, final double score, final String member);

	public Set<String> zrange(final String key, final long start, final long end);

	public Long zrem(final String key, final String... members);

	public Double zincrby(final String key, final double score, final String member);

	public Long zrank(final String key, final String member);

	// Other
}
