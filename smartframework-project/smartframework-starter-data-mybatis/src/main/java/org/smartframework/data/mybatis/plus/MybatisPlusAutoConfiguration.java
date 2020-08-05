/**
 * Created by Tiro on 2020/08/04.
 */
package org.smartframework.data.mybatis.plus;

import com.baomidou.mybatisplus.core.incrementer.IKeyGenerator;
import com.baomidou.mybatisplus.extension.incrementer.H2KeyGenerator;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 类描述：mybatis-plus 自动配置 <br>
 *
 * @Description: mybatis-plus 自动配置
 * @Author: Tiro
 * @Date: 2020/8/4 18:05
 */
@Slf4j
@Configuration
public class MybatisPlusAutoConfiguration {
    /**
     * @description mybatis-plus分页插件
     */
    @Bean
    public PaginationInterceptor masterPaginationInterceptor() {
        return new PaginationInterceptor();
    }

    /**
     * 注入主键生成器
     */
    @Bean
    public IKeyGenerator keyGenerator() {
        return new H2KeyGenerator();
    }
}
