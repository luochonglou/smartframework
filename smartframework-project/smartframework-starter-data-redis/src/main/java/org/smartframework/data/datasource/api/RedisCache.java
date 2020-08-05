/**
 * Created by Tiro on 2020/07/30.
 */
package org.smartframework.data.datasource.api;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 接口描述：缓存API <br>
 *
 * @Description: 缓存API
 * @Author: Tiro
 * @Date: 2020/7/30 19:59
 */
public interface RedisCache {

    /**
     * 设置过期
     *
     * @param key
     * @param timeout
     * @param unit
     * @return
     */
    boolean expire(Object key, final long timeout, final TimeUnit unit);

    /**
     * 判断key存在
     *
     * @param key
     * @return
     */
    boolean hasKey(Object key);

    /**
     * 获取Hash值
     *
     * @param key
     * @param hashKey
     * @return
     */
    Object hget(String key, String hashKey);

    /**
     * 设置Hash值
     *
     * @param key
     * @param hashKey
     * @param value
     */
    void hset(String key, String hashKey, Object value);

    /**
     * 删除Hash值
     *
     * @param key
     * @param hashKey
     */
    void hdel(String key, String... hashKey);

    /**
     * 获取值
     */
    Object get(Object key);

    /**
     * 获得指定类型的值
     *
     * @param
     * @return
     */
    <T> T get(Object key, Class<T> type);

    /**
     * 设置值
     */
    void set(Object key, Object value);

    /**
     * 设置值，并设置过期时间
     */
    void set(Object key, Object value, long timeout, TimeUnit unit);

    /**
     * 在左边put所有数据
     *
     * @param key
     * @param values
     * @return
     */
    Long leftPushAll(Object key, Object... values);

    /**
     * 得到一个list类型的值
     *
     * @return
     */
    List<Object> getList(Object key);

    /**
     * 得到一个list类型的值，list里面的元素类型为指定类型
     *
     * @param type
     * @return
     */
    <T> List<T> getList(Object key, Class<T> type);


    /**
     * 设置值，如果当前值为空的话
     */
    Boolean setIfAbsent(Object key, Object value);

    /**
     * 设置值，如果当前值为空的话,并设置过期时间
     */
    Boolean setIfAbsent(Object key, Object value, long timeout, TimeUnit unit);

    /**
     * 对多个key设置值
     */
    void multiSet(Map<? extends Object, ? extends Object> map);

    /**
     * 对多个key设置值，如果对应的key没有值的话
     */
    Boolean multiSetIfAbsent(Map<? extends Object, ? extends Object> map);

    /**
     * 获取值,同时设置一个新值
     */
    Object getAndSet(Object key, Object value);

    /**
     * 获取值,同时设置一个新值,并设置过期时间
     */
    Object getAndSet(Object key, Object value, long timeout, TimeUnit unit);

    /**
     * 获得多个key的值
     */
    List<Object> multiGet(Collection<Object> keys);

    /**
     * 自增
     */
    Long increment(Object key, long delta);

    /**
     * 自增
     */
    Double increment(Object key, double delta);

    /**
     * 自增并带有时间
     */
    Long increment(Object key, long delta, long timeout, TimeUnit unit);

    /**
     * 追加字符串
     */
    Integer append(Object key, String value);

    /**
     * 获得字符串中的一部分
     */
    String get(Object key, long start, long end);

    /**
     * 指定的字符串覆盖给定 key 所储存的字符串值，覆盖的位置从偏移量 offset 开始。
     */
    void set(Object key, Object value, long offset);

    /**
     * 获得字符串长度
     */
    Long size(Object key);

    /**
     * 对 key 所储存的字符串值，设置或清除指定偏移量上的位(bit)。
     */
    Boolean setBit(Object key, long offset, boolean value);

    /**
     * 对 key 所储存的字符串值，获得指定偏移量上的位(bit)。
     */
    Boolean getBit(Object key, long offset);
}
