/**
 * Created by Tiro on 2020/08/05.
 */
package org.smartframework.data.mybatis;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.smartframework.data.exception.MybatisConfigureException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.util.Objects;

/**
 * 类描述：Mybatis自动配置 <br>
 *
 * @Description: Mybatis自动配置
 * @Author: Tiro
 * @Date: 2020/8/5 16:06
 */
@Configuration
@ConditionalOnClass({SqlSessionFactory.class, SqlSessionFactoryBean.class})
@ConditionalOnSingleCandidate(DataSource.class)
public class MybatisAutoConfiguration implements EnvironmentAware {

    @Bean
    public MapperScannerConfigurer scannerConfigurer() {
        MapperScannerConfigurer scannerConfigurer = new MapperScannerConfigurer();
        scannerConfigurer.setBasePackage(basePackages);
        return scannerConfigurer;
    }

    /**
     * basePackage
     */
    private String basePackages;

    @Override
    public void setEnvironment(Environment environment) {
        this.basePackages = environment.getProperty("mybatis-plus.base-packages");
        if (Objects.isNull(basePackages)) {
            throw new MybatisConfigureException("mybatis-plus.base-packages must configure.");
        }
    }
}
