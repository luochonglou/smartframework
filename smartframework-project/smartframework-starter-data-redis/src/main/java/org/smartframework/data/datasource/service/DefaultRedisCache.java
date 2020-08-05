/**
 * Created by Tiro on 2020/07/30.
 */
package org.smartframework.data.datasource.service;

import lombok.extern.slf4j.Slf4j;
import org.smartframework.data.datasource.api.RedisCache;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 类描述：缓存服务 <br>
 *
 * @Description: 缓存服务
 * @Author: Tiro
 * @Date: 2020/7/30 20:03
 */
@Slf4j
public class DefaultRedisCache implements RedisCache {

    /**
     * redis template
     */
    private RedisTemplate redisTemplate;


    private RedisScript<Long> setNxTimeoutScript;
    private RedisScript<Object> getAndSetTimeoutScript;
    private RedisScript<Long> incTimeoutScript;


    /**
     * constructor
     *
     * @param redisTemplate
     */
    public DefaultRedisCache(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.setNxTimeoutScript = DefaultRedisScriptUtil.createRedisScript("lua/setnx_timeout.lua", Long.class);
        this.getAndSetTimeoutScript = DefaultRedisScriptUtil.createRedisScript("lua/getandset_timeout.lua", Object.class);
        this.incTimeoutScript = DefaultRedisScriptUtil.createRedisScript("lua/inc_timeout.lua", Long.class);
    }


    /**
     * 设置过期
     *
     * @param key
     * @param timeout
     * @param unit
     * @return
     */
    @Override
    public boolean expire(Object key, final long timeout, final TimeUnit unit) {
        return redisTemplate.expire(key, timeout, unit);
    }

    /**
     * 判断key存在
     *
     * @param key
     * @return
     */
    @Override
    public boolean hasKey(Object key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 获取Hash值
     *
     * @param key
     * @param hashKey
     * @return
     */
    @Override
    public Object hget(String key, String hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }

    /**
     * 设置Hash值
     *
     * @param key
     * @param hashKey
     * @param value
     */
    @Override
    public void hset(String key, String hashKey, Object value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    /**
     * 删除Hash值
     *
     * @param key
     * @param hashKey
     */
    @Override
    public void hdel(String key, String... hashKey) {
        redisTemplate.opsForHash().delete(key, hashKey);
    }


    /**
     * 获取值
     */
    @Override
    public Object get(Object key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 获得指定类型的值
     *
     * @param
     * @return
     */
    @Override
    public <T> T get(Object key, Class<T> type) {
        return (T) redisTemplate.opsForValue().get(key);
    }

    /**
     * 设置值
     */
    @Override
    public void set(Object key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 设置值，并设置过期时间
     */
    @Override
    public void set(Object key, Object value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }


    /**
     * 在左边put所有数据
     *
     * @param key
     * @param values
     * @return
     */
    @Override
    public Long leftPushAll(Object key, Object... values) {
        return redisTemplate.opsForList().leftPushAll(key, values);
    }

    /**
     * 得到一个list类型的值
     *
     * @return
     */
    @Override
    public List<Object> getList(Object key) {
        return redisTemplate.opsForList().range(key, 0, -1);
    }


    /**
     * 得到一个list类型的值，list里面的元素类型为指定类型
     *
     * @param type
     * @return
     */
    @Override
    public <T> List<T> getList(Object key, Class<T> type) {
        return redisTemplate.opsForList().range(key, 0, -1);
    }


    /**
     * 设置值，如果当前值为空的话
     */
    @Override
    public Boolean setIfAbsent(Object key, Object value) {
        return redisTemplate.opsForValue().setIfAbsent(key, value);
    }

    /**
     * 设置值，如果当前值为空的话,并设置过期时间
     */
    @Override
    public Boolean setIfAbsent(Object key, Object value, long timeout, TimeUnit unit) {
        timeout = unit.toSeconds(timeout);
        Long result = (Long) redisTemplate.execute(setNxTimeoutScript, Collections.singletonList(key), value, timeout);
        return result != null && result == 1;
    }

    /**
     * 对多个key设置值
     */
    @Override
    public void multiSet(Map<? extends Object, ? extends Object> map) {
        redisTemplate.opsForValue().multiSet(map);
    }

    /**
     * 对多个key设置值，如果对应的key没有值的话
     */
    @Override
    public Boolean multiSetIfAbsent(Map<? extends Object, ? extends Object> map) {
        return redisTemplate.opsForValue().multiSetIfAbsent(map);
    }


    /**
     * 获取值,同时设置一个新值
     */
    @Override
    public Object getAndSet(Object key, Object value) {
        return redisTemplate.opsForValue().getAndSet(key, value);
    }

    /**
     * 获取值,同时设置一个新值,并设置过期时间
     */
    @Override
    public Object getAndSet(Object key, Object value, long timeout, TimeUnit unit) {
        timeout = unit.toSeconds(timeout);
        return redisTemplate.execute(getAndSetTimeoutScript, Collections.singletonList(key), value, timeout);
    }

    /**
     * 获得多个key的值
     */
    @Override
    public List<Object> multiGet(Collection<Object> keys) {
        return redisTemplate.opsForValue().multiGet(keys);
    }

    /**
     * 自增
     */
    @Override
    public Long increment(Object key, long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 自增
     */
    @Override
    public Double increment(Object key, double delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 自增并带有时间
     */
    @Override
    public Long increment(Object key, long delta, long timeout, TimeUnit unit) {
        timeout = unit.toSeconds(timeout);
        Long result = (Long) redisTemplate.execute(incTimeoutScript, Collections.singletonList(key), delta, timeout);
        return result;
    }


    /**
     * 追加字符串
     */
    @Override
    public Integer append(Object key, String value) {
        return redisTemplate.opsForValue().append(key, value);
    }

    /**
     * 获得字符串中的一部分
     */
    @Override
    public String get(Object key, long start, long end) {
        return redisTemplate.opsForValue().get(key, start, end);
    }

    /**
     * 指定的字符串覆盖给定 key 所储存的字符串值，覆盖的位置从偏移量 offset 开始。
     */
    @Override
    public void set(Object key, Object value, long offset) {
        redisTemplate.opsForValue().set(key, value, offset);
    }

    /**
     * 获得字符串长度
     */
    @Override
    public Long size(Object key) {
        return redisTemplate.opsForValue().size(key);
    }

    /**
     * 对 key 所储存的字符串值，设置或清除指定偏移量上的位(bit)。
     */
    @Override
    public Boolean setBit(Object key, long offset, boolean value) {
        return redisTemplate.opsForValue().setBit(key, offset, value);
    }

    /**
     * 对 key 所储存的字符串值，获得指定偏移量上的位(bit)。
     */
    @Override
    public Boolean getBit(Object key, long offset) {
        return redisTemplate.opsForValue().getBit(key, offset);
    }
}
