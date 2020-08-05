package org.smartframework.data.datasource; /**
 * Created by Tiro on 2020/07/30.
 */

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.smartframework.data.datasource.api.RedisCache;
import org.smartframework.data.datasource.api.RedisLock;
import org.smartframework.data.datasource.service.DefaultRedisCache;
import org.smartframework.data.datasource.service.DefaultRedisLock;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

/**
 * 类描述：redis缓存自动配置 <br>
 *
 * @Description: redis缓存自动配置
 * @Author: Tiro
 * @Date: 2020/7/30 19:41
 */
@EnableCaching
@Configuration
public class RedisAutoConfiguration extends CachingConfigurerSupport {

    /**
     * 自定义缓存key的生成策略
     *
     * @return
     */
    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> {
            StringBuilder key = new StringBuilder();
            key.append(target.getClass().getName());
            key.append(method.getName());
            for (Object obj : params) {
                key.append(obj.toString());
            }
            return key.toString();
        };
    }

    /**
     * 缓存配置管理器
     */
    @Bean
    public CacheManager cacheManager(LettuceConnectionFactory factory) {
        // 以锁写入的方式创建RedisCacheWriter对象
        return new RedisCacheManager(RedisCacheWriter.lockingRedisCacheWriter(factory),
                RedisCacheConfiguration.defaultCacheConfig());
    }

    /**
     * 获取缓存操作助手对象
     *
     * @return
     */
    @Bean
    public RedisTemplate<String, String> redisTemplate(LettuceConnectionFactory factory) {
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(
                Object.class);
        jackson2JsonRedisSerializer.setObjectMapper(new ObjectMapper()
                .setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY)
                .enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL)
        );
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(factory);
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }


    /**
     * @param redisTemplate
     * @return
     */
    @Bean
    public RedisCache redisCache(RedisTemplate redisTemplate) {
        return new DefaultRedisCache(redisTemplate);
    }

    /**
     * @param redisTemplate
     * @return
     */
    @Bean
    public RedisLock redisLock(RedisTemplate redisTemplate) {
        return new DefaultRedisLock(redisTemplate);
    }
}
