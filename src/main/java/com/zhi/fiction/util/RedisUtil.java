package com.zhi.fiction.util;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtil {
    private static JedisPool jedisPool = null;
    // Redis服务器IP
    private static String    ADDR      = "127.0.0.1";
    // Redis的端口号
    private static int       PORT      = 6379;
    // 访问密码
    private static String    AUTH      = "123456";

    /**
     * 初始化Redis连接池
     */
    static {
        try {
            JedisPoolConfig config = new JedisPoolConfig();
            // 连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true
            config.setBlockWhenExhausted(true);
            // 设置的逐出策略类名, 默认DefaultEvictionPolicy(当连接超过最大空闲时间,或连接数超过最大空闲连接数)
            config.setEvictionPolicyClassName("org.apache.commons.pool2.impl.DefaultEvictionPolicy");
            // 是否启用pool的jmx管理功能, 默认true
            config.setJmxEnabled(true);
            // 最大空闲连接数, 默认8个 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
            config.setMaxIdle(8);
            // 最大连接数, 默认8个
            config.setMaxTotal(200);
            // 表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
            config.setMaxWaitMillis(1000 * 100);
            // 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
            config.setTestOnBorrow(true);
            jedisPool = new JedisPool(config, ADDR, PORT, 3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取Jedis实例
     * 
     * @return
     */
    public synchronized static Jedis getJedis() {
        try {
            if (jedisPool != null) {
                Jedis resource = jedisPool.getResource();
                return resource;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 释放jedis资源
     * 
     * @param jedis
     */
    public static void close(final Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }

    /**存储Object对象
     * @param key
     * @param obj
     */
    public static void setObjectValue(String key, Object obj) {
        Jedis jedis = getJedis();
        jedis.set(key.getBytes(), SerializeUtil.serialize(obj));
        close(jedis);
    }

    /**获取Object对象
     * @param key
     * @return
     */
    public static Object getObjectValue(String key) {
        Jedis jedis = getJedis();
        Object obj = SerializeUtil.unserialize(jedis.get(key.getBytes()));
        close(jedis);
        return obj;
    }

    /**存储List
     * @param key
     * @param obj
     */
    public static void setListValue(String key, List<?> list) {
        Jedis jedis = getJedis();
        for (Object li : list) {
            jedis.lpush(key.getBytes(), SerializeUtil.serialize(li));
        }
        close(jedis);
    }

    /**获取List
     * @param key
     * @return
     */
    public static List<?> getListValue(String key) {
        Jedis jedis = RedisUtil.getJedis();
        //0代表第一个元素 1代表第二个元素 ；-1代表倒数第一个元素，-2代表倒数第二个元素。以此类推
        List<byte[]> list = jedis.lrange(key.getBytes(), 0, -1);
        List<Object> realList = Lists.newArrayList();
        for (byte[] li : list) {
            realList.add(SerializeUtil.unserialize(li));
        }
        close(jedis);
        return realList;
    }

    /**存储Object对象
     * @param key
     * @param obj
     */
    public static void setObjectValueByJson(String key, Object obj) {
        Jedis jedis = RedisUtil.getJedis();
        jedis.set(key, JSON.toJSONString(obj));
        close(jedis);
    }

    /**获取Object对象
     * @param key
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static Object getObjectValueByJson(String key, Class clazz) {
        Jedis jedis = RedisUtil.getJedis();
        Object obj = JSON.parseObject(jedis.get(key), clazz);
        close(jedis);
        return obj;
    }
    
    /**存储List-单个
     * @param key
     * @param obj
     */
    public static void lpush(String key, Object obj) {
        Jedis jedis = RedisUtil.getJedis();
        jedis.lpush(key, JSON.toJSONString(obj));
        close(jedis);
    }
    
    

    /**存储List
     * @param key
     * @param obj
     */
    public static void setListValueByJson(String key, List<?> list) {
        Jedis jedis = RedisUtil.getJedis();
        for (Object li : list) {
            jedis.lpush(key, JSON.toJSONString(li));
        }
        close(jedis);
    }

    /**获取List
     * @param key
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static List<?> getListValueByJson(String key, Class clazz) {
        Jedis jedis = RedisUtil.getJedis();
        //0代表第一个元素 1代表第二个元素 ；-1代表倒数第一个元素，-2代表倒数第二个元素。以此类推
        List<String> list = jedis.lrange(key, 0, -1);
        List<Object> realList = Lists.newArrayList();
        for (String li : list) {
            realList.add(JSON.parseObject(li, clazz));
        }
        close(jedis);
        return realList;
    }
}