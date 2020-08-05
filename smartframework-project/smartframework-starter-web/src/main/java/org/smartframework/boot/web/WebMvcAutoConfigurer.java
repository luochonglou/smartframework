/**
 * Created by Tiro on 2020/07/27.
 */
package org.smartframework.boot.web;

import lombok.extern.slf4j.Slf4j;
import org.smartframework.boot.web.converter.StringToBasicEnumConverterFactory;
import org.smartframework.boot.web.resolver.HeaderParamsResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * 类描述：WebMvc自动配置 <br>
 *
 * @Description: WebMvc自动配置
 * @Author: Tiro
 * @Date: 2020/7/27 15:04
 */
@Slf4j
@Configuration
@ComponentScan("org.smartframework.boot.web.advice")
public class WebMvcAutoConfigurer implements WebMvcConfigurer {


    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        //添加请求头参数解析器
        argumentResolvers.add(headerArgumentResolver());
    }


    @Override
    public void addFormatters(FormatterRegistry registry) {
        //字符串转枚举
        registry.addConverterFactory(stringToBasicEnumConverterFactory());
    }


    /**
     * 请求头参数解析器
     *
     * @return
     */
    @Bean
    public HeaderParamsResolver headerArgumentResolver() {
        return new HeaderParamsResolver();
    }


    /**
     * 字符串转换枚举
     *
     * @return
     * @see org.smartframework.boot.web.converter.BasicEnum
     */
    @Bean
    public StringToBasicEnumConverterFactory stringToBasicEnumConverterFactory() {
        return new StringToBasicEnumConverterFactory();
    }


}
