package com.qianshanding.framework.redis;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.exceptions.JedisException;
import redis.clients.util.Pool;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Administrator Exp
 * @version V1.0
 * @FileName AbstractRedistClient.java
 * @Description:
 * @Date 2016年3月7日 下午12:56:07
 */
public abstract class AbstractRedistClient<T extends Pool<Jedis>, R extends Jedis> implements RedisClient {
    private static final Log logger = LogFactory.getLog(AbstractRedistClient.class);

    ThreadLocal<T> redisPool = new ThreadLocal<T>();

    abstract public void initPool();

    public T getRedisPool() {
        return redisPool.get();
    }

    public void setRedisPool(T pool) {
        redisPool.set(pool);
    }

    @Override
    public Jedis getJedis() {
        Jedis jedis = null;
        try {
            jedis = getRedisPool().getResource();
        } catch (JedisConnectionException e) {
            logger.error("redis get connection exception", e);
            throw e;
        }
        return jedis;
    }

    @Override
    public void returnResource(Jedis jedis) {
        try {
            if (jedis != null) {
                jedis.close();
            }
        } catch (JedisException e) {
            logger.error("redis close connection exception", e);
        }
    }

    @Override
    public String ping() {
        Jedis jedis = this.getJedis();
        String result = jedis.ping();
        returnResource(jedis);
        return result;
    }

    @Override
    public long dbSize() {
        Jedis jedis = this.getJedis();
        long result = jedis.dbSize();
        returnResource(jedis);
        return result;
    }

    @Override
    public String flushDB() {
        Jedis jedis = this.getJedis();
        String result = jedis.flushDB();
        returnResource(jedis);
        return result;
    }

    @Override
    public String info() {
        Jedis jedis = this.getJedis();
        String result = jedis.info();
        returnResource(jedis);
        return result;
    }

    @Override
    public Set<String> keys(String pattern) {
        Jedis jedis = this.getJedis();
        Set<String> result = jedis.keys(pattern);
        returnResource(jedis);
        return result;
    }

    @Override
    public String get(String key) {
        Jedis jedis = this.getJedis();
        String result = jedis.get(key);
        returnResource(jedis);
        return result;
    }

    @Override
    public Boolean exists(String key) {
        Jedis jedis = this.getJedis();
        Boolean result = jedis.exists(key);
        returnResource(jedis);
        return result;
    }

    @Override
    public String set(byte[] key, byte[] value, int liveTime) {
        return set(String.valueOf(key), String.valueOf(value), liveTime);
    }

    @Override
    public String set(String key, String value, int liveTime) {
        Jedis jedis = this.getJedis();
        try {
            String result = jedis.set(key, value);
            jedis.expire(key, liveTime);
            return result;
        } finally {
            returnResource(jedis);
        }
    }

    @Override
    public Long del(String key) {
        Jedis jedis = this.getJedis();
        try {
            return jedis.del(key);
        } finally {
            returnResource(jedis);
        }
    }

    @Override
    public Long del(byte[] key) {
        return del(String.valueOf(key));
    }

    @Override
    public String set(String key, String value) {
        Jedis jedis = this.getJedis();
        try {
            return jedis.set(key, value);
        } finally {
            returnResource(jedis);
        }
    }

    @Override
    public String set(byte[] key, byte[] value) {
        return set(String.valueOf(key), String.valueOf(value));
    }

    @Override
    public byte[] get(byte[] key) {
        Jedis jedis = this.getJedis();
        byte[] result = jedis.get(key);
        returnResource(jedis);
        return result;
    }

    @Override
    public String rename(String oldKey, String newKey) {
        Jedis jedis = this.getJedis();
        try {
            return jedis.rename(oldKey, newKey);
        } finally {
            returnResource(jedis);
        }
    }

    @Override
    public Long expire(String key, int seconds) {
        Jedis jedis = this.getJedis();
        Long result = jedis.expire(key, seconds);
        returnResource(jedis);
        return result;
    }

    @Override
    public String getSet(String key, String value) {
        Jedis jedis = this.getJedis();
        String result = jedis.getSet(key, value);
        returnResource(jedis);
        return result;
    }

    @Override
    public List<String> mget(String... keys) {
        Jedis jedis = this.getJedis();
        List<String> result = jedis.mget(keys);
        returnResource(jedis);
        return result;
    }

    @Override
    public Long setnx(String key, String value) {
        Jedis jedis = this.getJedis();
        Long result = jedis.setnx(key, value);
        returnResource(jedis);
        return result;
    }

    @Override
    public String setex(String key, int seconds, String value) {
        Jedis jedis = this.getJedis();
        String result = jedis.setex(key, seconds, value);
        returnResource(jedis);
        return result;
    }

    @Override
    public Long append(String key, String value) {
        Jedis jedis = this.getJedis();
        Long result = jedis.append(key, value);
        returnResource(jedis);
        return result;
    }

    @Override
    public Long rpush(String key, String... strings) {
        Jedis jedis = this.getJedis();
        Long result = jedis.rpush(key, strings);
        returnResource(jedis);
        return result;
    }

    @Override
    public Long lpush(String key, String... strings) {
        Jedis jedis = this.getJedis();
        Long result = jedis.lpush(key, strings);
        returnResource(jedis);
        return result;
    }

    @Override
    public Long llen(String key) {
        Jedis jedis = this.getJedis();
        Long result = jedis.llen(key);
        returnResource(jedis);
        return result;
    }

    @Override
    public String lindex(String key, long index) {
        Jedis jedis = this.getJedis();
        String result = jedis.lindex(key, index);
        returnResource(jedis);
        return result;
    }

    @Override
    public Long lrem(String key, long count, String value) {
        Jedis jedis = this.getJedis();
        Long result = jedis.lrem(key, count, value);
        returnResource(jedis);
        return result;
    }

    @Override
    public String lset(String key, long index, String value) {
        Jedis jedis = this.getJedis();
        try {
            return jedis.lset(key, index, value);
        } finally {
            returnResource(jedis);
        }
    }

    @Override
    public String lpop(String key) {
        Jedis jedis = this.getJedis();
        String result = jedis.lpop(key);
        returnResource(jedis);
        return result;
    }

    @Override
    public String rpop(String key) {
        Jedis jedis = this.getJedis();
        String result = jedis.rpop(key);
        returnResource(jedis);
        return result;
    }

    @Override
    public Double hincrByFloat(String key, String field, Double value) {
        Jedis jedis = this.getJedis();
        Double result = jedis.hincrByFloat(key, field, value);
        returnResource(jedis);
        return result;
    }

    @Override
    public Long hincrBy(String key, String field, Long value) {
        Jedis jedis = this.getJedis();
        Long result = jedis.hincrBy(key, field, value);
        returnResource(jedis);
        return result;
    }

    @Override
    public Long hset(String key, String field, String value) {
        Jedis jedis = this.getJedis();
        Long result = jedis.hset(key, field, value);
        returnResource(jedis);
        return result;
    }

    @Override
    public String hget(String key, String field) {
        Jedis jedis = this.getJedis();
        String result = jedis.hget(key, field);
        returnResource(jedis);
        return result;
    }

    @Override
    public Long hsetnx(String key, String field, String value) {
        Jedis jedis = this.getJedis();
        try {
            return jedis.hsetnx(key, field, value);
        } finally {
            returnResource(jedis);
        }
    }

    @Override
    public String hmset(String key, Map<String, String> hash) {
        Jedis jedis = this.getJedis();
        try {
            return jedis.hmset(key, hash);
        } finally {
            returnResource(jedis);
        }
    }

    @Override
    public List<String> hmget(String key, String... fields) {
        Jedis jedis = this.getJedis();
        List<String> result = jedis.hmget(key, fields);
        returnResource(jedis);
        return result;
    }

    @Override
    public Boolean hexists(String key, String field) {
        Jedis jedis = this.getJedis();
        Boolean result = jedis.hexists(key, field);
        returnResource(jedis);
        return result;
    }

    @Override
    public Long hdel(String key, String... fields) {
        Jedis jedis = this.getJedis();
        Long result = jedis.hdel(key, fields);
        returnResource(jedis);
        return result;
    }

    @Override
    public Long hlen(String key) {
        Jedis jedis = this.getJedis();
        Long result = jedis.hlen(key);
        returnResource(jedis);
        return result;
    }

    @Override
    public Set<String> hkeys(String key) {
        Jedis jedis = this.getJedis();
        Set<String> result = jedis.hkeys(key);
        returnResource(jedis);
        return result;
    }

    @Override
    public List<String> hvals(String key) {
        Jedis jedis = this.getJedis();
        List<String> result = jedis.hvals(key);
        returnResource(jedis);
        return result;
    }

    @Override
    public Map<String, String> hgetAll(String key) {
        Jedis jedis = this.getJedis();
        Map<String, String> result = jedis.hgetAll(key);
        returnResource(jedis);
        return result;
    }

    @Override
    public Long sadd(String key, String... members) {
        Jedis jedis = this.getJedis();
        Long result = jedis.sadd(key, members);
        returnResource(jedis);
        return result;
    }

    @Override
    public Set<String> smembers(String key) {
        Jedis jedis = this.getJedis();
        Set<String> result = jedis.smembers(key);
        returnResource(jedis);
        return result;
    }

    @Override
    public Long srem(String key, String... members) {
        Jedis jedis = this.getJedis();
        Long result = jedis.srem(key, members);
        returnResource(jedis);
        return result;
    }

    @Override
    public String spop(String key) {
        Jedis jedis = this.getJedis();
        String result = jedis.spop(key);
        returnResource(jedis);
        return result;
    }

    @Override
    public Long smove(String srckey, String dstkey, String member) {
        Jedis jedis = this.getJedis();
        Long result = jedis.smove(srckey, dstkey, member);
        returnResource(jedis);
        return result;
    }

    @Override
    public Long scard(String key) {
        Jedis jedis = this.getJedis();
        Long result = jedis.scard(key);
        returnResource(jedis);
        return result;
    }

    @Override
    public Boolean sismember(String key, String member) {
        Jedis jedis = this.getJedis();
        Boolean result = jedis.sismember(key, member);
        returnResource(jedis);
        return result;
    }

    @Override
    public Set<String> sinter(String... keys) {
        Jedis jedis = this.getJedis();
        Set<String> result = jedis.sinter(keys);
        returnResource(jedis);
        return result;
    }

    @Override
    public void sinterstore(String dstkey, String... keys) {
        Jedis jedis = this.getJedis();
        jedis.sinterstore(dstkey, keys);
        returnResource(jedis);
    }

    @Override
    public Set<String> sunion(String... keys) {
        Jedis jedis = this.getJedis();
        Set<String> result = jedis.sunion(keys);
        returnResource(jedis);
        return result;
    }

    @Override
    public Long sunionstore(String dstkey, String... keys) {
        Jedis jedis = this.getJedis();
        Long result = jedis.sunionstore(dstkey, keys);
        returnResource(jedis);
        return result;
    }

    @Override
    public Set<String> sdiff(String... keys) {
        Jedis jedis = this.getJedis();
        Set<String> result = jedis.sdiff(keys);
        returnResource(jedis);
        return result;
    }

    @Override
    public Long sdiffstore(String dstkey, String... keys) {
        Jedis jedis = this.getJedis();
        Long result = jedis.sdiffstore(dstkey, keys);
        returnResource(jedis);
        return result;
    }

    @Override
    public String srandmember(String key) {
        Jedis jedis = this.getJedis();
        String result = jedis.srandmember(key);
        returnResource(jedis);
        return result;
    }

    @Override
    public Long zadd(String key, double score, String member) {
        Jedis jedis = this.getJedis();
        Long result = jedis.zadd(key, score, member);
        returnResource(jedis);
        return result;
    }

    @Override
    public Set<String> zrange(String key, long start, long end) {
        Jedis jedis = this.getJedis();
        Set<String> result = jedis.zrange(key, start, end);
        returnResource(jedis);
        return result;
    }

    @Override
    public Long zrem(String key, String... members) {
        Jedis jedis = this.getJedis();
        Long result = jedis.zrem(key, members);
        returnResource(jedis);
        return result;
    }

    @Override
    public Double zincrby(String key, double score, String member) {
        Jedis jedis = this.getJedis();
        Double result = jedis.zincrby(key, score, member);
        returnResource(jedis);
        return result;
    }

    @Override
    public Long zrank(String key, String member) {
        Jedis jedis = this.getJedis();
        Long result = jedis.zrank(key, member);
        returnResource(jedis);
        return result;
    }

}
