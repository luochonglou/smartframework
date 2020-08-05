/**
 * Created by Tiro on 2020/07/30.
 */
package org.smartframework.data.datasource.service;

import lombok.extern.slf4j.Slf4j;
import org.smartframework.data.datasource.api.RedisLock;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;

import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 类描述：redis锁服务 <br>
 *
 * @Description: redis锁服务
 * @Author: Tiro
 * @Date: 2020/7/30 20:03
 */
@Slf4j
public class DefaultRedisLock implements RedisLock {

    /**
     * Redis分布式锁前缀
     */
    private static final String LOCK_PREFIX = "DistributedLock:";


    /**
     * redis template
     */
    private RedisTemplate redisTemplate;
    /**
     * 加锁lua对象
     */
    private RedisScript<Long> tryLockScript;
    /**
     * 释放lua对象
     */
    private RedisScript<Long> unLockScript;


    /**
     * constructor
     *
     * @param redisTemplate
     */
    public DefaultRedisLock(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.tryLockScript = DefaultRedisScriptUtil.createRedisScript("lua/trylock.lua", Long.class);
        this.unLockScript = DefaultRedisScriptUtil.createRedisScript("lua/unlock.lua", Long.class);
    }

    /**
     * 获取锁，判断返回值为空白获取失败，获取成功后默认6秒自动解锁
     *
     * @param lockKey 锁定键
     * @return
     */
    @Override
    public String tryLock(String lockKey) {
        return tryLock(lockKey, 6);
    }

    /**
     * 获取锁，判断返回值为空白获取失败
     *
     * @param lockKey       锁定键
     * @param unlockTimeout 过期释放时间(秒)
     * @return
     */
    @Override
    public String tryLock(String lockKey, long unlockTimeout) {
        return tryLock(lockKey, unlockTimeout, TimeUnit.SECONDS);
    }

    /**
     * 获取锁，判断返回值为空白获取失败
     *
     * @param lockKey        锁定键
     * @param unlockTimeout  过期释放时间
     * @param unlockTimeUnit 过期释放时间单位
     * @return
     */
    @Override
    public String tryLock(String lockKey, long unlockTimeout, TimeUnit unlockTimeUnit) {
        String lockValue = UUID.randomUUID().toString();
        if (tryLock(lockKey, lockValue, unlockTimeout, unlockTimeUnit)) {
            return lockValue;
        }
        return "";
    }


    /**
     * 获取锁，默认每2秒重试，重试3次，获取成功后默认6秒自动解锁，判断返回值为空白获取失败
     *
     * @param lockKey 锁定键
     * @return
     */
    @Override
    public String tryLockOrWait(String lockKey) {
        return tryLockOrWait(lockKey, 6);
    }


    /**
     * 获取锁，默认每2秒重试，重试3次，判断返回值为空白获取失败
     *
     * @param lockKey       锁定键
     * @param unlockTimeout 过期释放时间(秒)
     * @return
     */
    @Override
    public String tryLockOrWait(String lockKey, long unlockTimeout) {
        return tryLockOrWait(lockKey, unlockTimeout, TimeUnit.SECONDS);
    }

    /**
     * 获取锁，默认每2秒重试，重试3次，判断返回值为空白获取失败
     *
     * @param lockKey        锁定键
     * @param unlockTimeout  过期释放时间
     * @param unlockTimeUnit 过期释放时间单位
     * @return
     */
    @Override
    public String tryLockOrWait(String lockKey, long unlockTimeout, TimeUnit unlockTimeUnit) {
        return tryLockOrWait(lockKey, unlockTimeout, unlockTimeUnit, 2000);
    }

    /**
     * 获取锁，默认重试3次，判断返回值为空白获取失败
     *
     * @param lockKey        锁定键
     * @param unlockTimeout  过期释放时间
     * @param unlockTimeUnit 过期释放时间单位
     * @param retryTime      重试等待时间(毫秒)
     * @return
     */
    @Override
    public String tryLockOrWait(String lockKey, long unlockTimeout, TimeUnit unlockTimeUnit,
                                long retryTime) {
        return tryLockOrWait(lockKey, unlockTimeout, unlockTimeUnit, retryTime, 3);
    }

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
    @Override
    public String tryLockOrWait(String lockKey, long unlockTimeout, TimeUnit unlockTimeUnit,
                                long retryTime, int maxRetry) {
        return tryLockOrWait(lockKey, UUID.randomUUID().toString(), unlockTimeout, unlockTimeUnit, retryTime, maxRetry, 0);
    }

    /**
     * 释放锁
     *
     * @param lockKey
     */
    @Override
    public boolean unLock(String lockKey, String lockVal) {
        Long releaseResult = (Long) redisTemplate.execute(unLockScript, Collections.singletonList(LOCK_PREFIX + lockKey), lockVal);
        if (releaseResult == 1L) {
            return true;
        }
        return false;
    }


    /**
     * 加锁
     *
     * @param lockKey        锁定键
     * @param lockVal        锁定值
     * @param unlockTimeout  过期释放时间
     * @param unlockTimeUnit 过期释放时间单位
     * @return
     */
    private boolean tryLock(String lockKey, String lockVal, long unlockTimeout, TimeUnit unlockTimeUnit) {
        unlockTimeout = unlockTimeUnit.toSeconds(unlockTimeout);
        Long lockResult = (Long) redisTemplate.execute(tryLockScript,
                Collections.singletonList(LOCK_PREFIX + lockKey), lockVal, unlockTimeout);
        return lockResult != null && lockResult == 1L;
    }


    /**
     * 获取锁，可重试，判断返回值为空白获取失败
     *
     * @param lockKey        锁定键
     * @param lockVal        锁定值
     * @param unlockTimeout  过期释放时间
     * @param unlockTimeUnit 过期释放时间单位
     * @param retryTime      重试等待时间(毫秒)
     * @param maxRetry       重试次数
     * @param retry          当前次数
     * @return
     */
    private String tryLockOrWait(String lockKey, String lockVal, long unlockTimeout, TimeUnit unlockTimeUnit, long retryTime,
                                 int maxRetry, int retry) {
        if (retry >= maxRetry) {
            return "";
        }
        if (retryTime * maxRetry > unlockTimeUnit.toMillis(unlockTimeout)) {
            // 自动解锁的时间最大只能重试最大时间
            unlockTimeout = retryTime * maxRetry;
            unlockTimeUnit = TimeUnit.MILLISECONDS;
        }
        if (tryLock(lockKey, lockVal, unlockTimeout, unlockTimeUnit)) {
            return lockVal;
        } else {
            try {
                Thread.sleep(retryTime);
            } catch (InterruptedException e) {
                log.error("获取锁线程等待异常,最大重试次数={}，当前重试次数={}", maxRetry, retry);
            }
        }
        return tryLockOrWait(lockKey, lockVal, unlockTimeout, unlockTimeUnit, retryTime, maxRetry, retry + 1);
    }
}
