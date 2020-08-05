/**
 * Created by Tiro on 2020/07/30.
 */
package org.smartframework.data.datasource.api;

import java.util.concurrent.TimeUnit;

/**
 * 接口描述：redis锁 <br>
 *
 * @Description: redis锁
 * @Author: Tiro
 * @Date: 2020/7/30 20:00
 */
public interface RedisLock {
    /**
     * 获取锁，判断返回值为空白获取失败，获取成功后默认6秒自动解锁
     *
     * @param lockKey 锁定键
     * @return
     */
    String tryLock(String lockKey);

    /**
     * 获取锁，判断返回值为空白获取失败
     *
     * @param lockKey       锁定键
     * @param unlockTimeout 过期释放时间(秒)
     * @return
     */
    String tryLock(String lockKey, long unlockTimeout);

    /**
     * 获取锁，判断返回值为空白获取失败
     *
     * @param lockKey        锁定键
     * @param unlockTimeout  过期释放时间
     * @param unlockTimeUnit 过期释放时间单位
     * @return
     */
    String tryLock(String lockKey, long unlockTimeout, TimeUnit unlockTimeUnit);

    /**
     * 获取锁，默认每2秒重试，重试3次，获取成功后默认6秒自动解锁，判断返回值为空白获取失败
     *
     * @param lockKey 锁定键
     * @return
     */
    String tryLockOrWait(String lockKey);

    /**
     * 获取锁，默认每2秒重试，重试3次，判断返回值为空白获取失败
     *
     * @param lockKey       锁定键
     * @param unlockTimeout 过期释放时间(秒)
     * @return
     */
    String tryLockOrWait(String lockKey, long unlockTimeout);

    /**
     * 获取锁，默认每2秒重试，重试3次，判断返回值为空白获取失败
     *
     * @param lockKey        锁定键
     * @param unlockTimeout  过期释放时间
     * @param unlockTimeUnit 过期释放时间单位
     * @return
     */
    String tryLockOrWait(String lockKey, long unlockTimeout, TimeUnit unlockTimeUnit);

    /**
     * 获取锁，默认重试3次，判断返回值为空白获取失败
     *
     * @param lockKey        锁定键
     * @param unlockTimeout  过期释放时间
     * @param unlockTimeUnit 过期释放时间单位
     * @param retryTime      重试等待时间(毫秒)
     * @return
     */
    String tryLockOrWait(String lockKey, long unlockTimeout, TimeUnit unlockTimeUnit,
                         long retryTime);

    /**
     * 获取锁，可重试，判断返回值为空白获取失败
     *
     * @param lockKey        锁定键
     * @param unlockTimeout  过期释放时间
     * @param unlockTimeUnit 过期释放时间单位
     * @param retryTime      重试等待时间(毫秒)
     * @param maxRetry       重试次数
     * @return
     */
    String tryLockOrWait(String lockKey, long unlockTimeout, TimeUnit unlockTimeUnit,
                         long retryTime, int maxRetry);

    /**
     * 释放锁
     *
     * @param lockKey
     */
    boolean unLock(String lockKey, String lockVal);
}
