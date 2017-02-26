package com.blogadmin.common.utils;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.Protocol;

import com.blogadmin.common.SystemConfig;

public class RestRedisApi {
    private static JedisPool    jedisPool = null;
    static {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig
                .setMaxIdle(Integer.parseInt(String.valueOf(SystemConfig.INSTANCE.getValue("redis.maxIdle", "1500"))));
        poolConfig
                .setMaxTotal(Integer.parseInt(String.valueOf(SystemConfig.INSTANCE.getValue("redis.maxTotal", "5000"))));
        poolConfig.setMaxWaitMillis(Integer.parseInt(String.valueOf(SystemConfig.INSTANCE.getValue("redis.maxWait",
                "3000"))));
        poolConfig.setTestOnBorrow(Boolean.parseBoolean(String.valueOf(SystemConfig.INSTANCE.getValue(
                "rest.cache.testOnBorrow", "true"))));
        jedisPool = new JedisPool(poolConfig,
                String.valueOf(SystemConfig.INSTANCE.getValue("redis.host", "localhost")), Protocol.DEFAULT_PORT,
                Protocol.DEFAULT_TIMEOUT, StringUtils.isEmpty(String.valueOf(SystemConfig.INSTANCE.getValue(
                        "redis.port", ""))) ? null : String.valueOf(SystemConfig.INSTANCE.getValue("redis.pass", "")));
    }

    private static final Logger logger    = Logger.getLogger(RestRedisApi.class);

    /**
     * 消息发布
     * 
     * @param channel
     * @param message
     */
    public static void publish(String channel, String message) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.publish(channel, message);
        } catch (Exception e) {
            logger.error("redis publish fail channel:" + channel);
        } finally {
            jedisPool.returnResourceObject(jedis);
        }
    }

    /**
     * 获取订单编号、动态编号
     * 
     * @param key key的编码方式为 方法名:slot[slot取值0~9] 例： GuidTo16String:0
     */
    public static String get(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.get(key);
        } catch (Exception e) {
            logger.error("redis get set fail :" + key);
        } finally {
            jedisPool.returnResourceObject(jedis);
        }
        return null;
    }

    /**
     * 获取订单编号、动态编号
     * 
     * @param key key的编码方式为 方法名:slot[slot取值0~9] 例： GuidTo16String:0
     * @param value
     */
    public static void set(String key, String value, int... times) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.set(key, value);
            if (times != null && times.length > 0) {
                jedis.expire(key, times[0]);
            }
        } catch (Exception e) {
            logger.error("redis set fail :" + key);
        } finally {
            jedisPool.returnResourceObject(jedis);
        }
    }

    /**
     * 消息订阅
     * 
     * @param jedisPubSub
     * @param channels
     */
    public static void subScrible(JedisPubSub jedisPubSub, String channels) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.subscribe(jedisPubSub, channels);
        } catch (Exception e) {
            logger.error("redis subScrible fail channel:" + channels);
        } finally {
            jedisPool.returnResourceObject(jedis);
        }
    }

    /**
     * 出对列
     * 
     * @param key
     * @return
     */
    public static String pop(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.rpop(key);
        } catch (Exception e) {
            logger.error("redis pop fail :" + key + "  " + e.getMessage());
        } finally {
            jedisPool.returnResourceObject(jedis);
        }
        return null;
    }

    /**
     * 入队列
     * 
     * @param key
     * @param value
     */
    public static void push(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.lpush(key, value);
        } catch (Exception e) {
            logger.error("redis push fail :" + key);
        } finally {
            jedisPool.returnResourceObject(jedis);
        }
    }

    /**
     * 删除数据
     * 
     * @param key
     * @param value
     */
    public static void del(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.del(key);
        } catch (Exception e) {
            logger.error("redis del fail :" + key);
        } finally {
            jedisPool.returnResourceObject(jedis);
        }
    }

    /**
     * 设定数据
     * 
     * @param key
     * @param value
     * @param sessionId
     */
    public static void hset(String key, String value, String sessionId, int... times) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.hset(key, sessionId, value);

            if (times != null && times.length > 0) {
                jedis.expire(key, times[0]);
            } else {
                // 默认设定20分钟让set数据失效
                jedis.expire(key, 20 * 60);
            }

        } catch (Exception e) {
            logger.error("redis hset fail :" + key + " field : " + sessionId);
        } finally {
            jedisPool.returnResourceObject(jedis);
        }
    }

    /**
     * 获取set结构中的数据
     * 
     * @param key
     * @param sessionId field
     */
    public static String hget(String key, String sessionId) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.hget(key, sessionId);
        } catch (Exception e) {
            logger.error("redis hget fail :" + key);
        } finally {
            jedisPool.returnResourceObject(jedis);
        }
        return null;
    }

}
