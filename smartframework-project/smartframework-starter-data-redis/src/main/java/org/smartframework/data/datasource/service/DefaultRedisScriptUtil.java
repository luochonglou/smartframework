/**
 * Created by Tiro on 2020/07/30.
 */
package org.smartframework.data.datasource.service;

import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.lang.Nullable;

/**
 * 类描述： redis lua 脚本工具类<br>
 *
 * @Description: redis lua 脚本工具类
 * @Author: Tiro
 * @Date: 2020/7/30 20:06
 */
public class DefaultRedisScriptUtil {

    /**
     * 创建RedisScript
     *
     * @param path
     * @param resultType
     * @param <T>
     * @return
     */
    public static <T> RedisScript<T> createRedisScript(String path, @Nullable Class<T> resultType) {
        org.springframework.data.redis.core.script.DefaultRedisScript redisScript = new org.springframework.data.redis.core.script.DefaultRedisScript();
        redisScript.setLocation(new ClassPathResource(path));
        redisScript.setResultType(resultType);
        return redisScript;
    }
}
