/**
 * Created by Tiro on 2020/08/05.
 */
package org.smartframework.data.mybatis;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 类描述：Mybatis自动配置 <br>
 *
 * @Description: Mybatis自动配置
 * @Author: Tiro
 * @Date: 2020/8/5 16:06
 */
@Configuration
@AutoConfigureBefore(MybatisPlusAutoConfiguration.class)
public class MybatisAutoConfiguration implements BeanFactoryAware {

    private BeanFactory beanFactory;

    @Bean
    @ConditionalOnMissingBean(MapperScannerConfigurer.class)
    public MapperScannerConfigurer scannerConfigurer() {
        List<String> packages = AutoConfigurationPackages.get(this.beanFactory);
        String basePackages = StringUtils.collectionToCommaDelimitedString(packages);
        MapperScannerConfigurer scannerConfigurer = new MapperScannerConfigurer();
        scannerConfigurer.setBasePackage(basePackages);
        return scannerConfigurer;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
