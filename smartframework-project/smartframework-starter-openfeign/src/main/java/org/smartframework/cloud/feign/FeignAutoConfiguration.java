/**
 * Created by Tiro on 2020/07/23.
 */
package org.smartframework.cloud.feign;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import feign.Logger;
import feign.RequestInterceptor;
import feign.Util;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.smartframework.cloud.feign.exception.FeignBusinessException;
import org.smartframework.common.FeignFlag;
import org.smartframework.common.exception.SystemException;
import org.smartframework.common.exception.basic.DefaultExceptionCode;
import org.smartframework.common.result.ExceptionResponse;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Objects;
import java.util.Optional;

/**
 * 类描述：feign 自动配置 <br>
 *
 * @Description: feign 自动配置
 * @Author: Tiro
 * @Date: 2020/7/23 20:28
 */
@Slf4j
@Configuration
@EnableCircuitBreaker
@EnableFeignClients(basePackages = {"**.microservices"})
public class FeignAutoConfiguration {


    /**
     * feign日志级别
     *
     * @return
     */
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }


    /**
     * feign请求拦截器
     *
     * @return
     */
    @Bean
    RequestInterceptor headerRequestInterceptor() {
        return (template) -> template.header(FeignFlag._HEADER, FeignFlag._HEADER);
    }


    /**
     * decoder
     *
     * @return
     */
    @Bean
    public Decoder decoder() {
        HttpMessageConverter jacksonConverter = new MappingJackson2HttpMessageConverter(customObjectMapper());
        ObjectFactory<HttpMessageConverters> objectFactory = () -> new HttpMessageConverters(jacksonConverter);
        return new ResponseEntityDecoder(new SpringDecoder(objectFactory));
    }

    /**
     * encoder
     *
     * @return
     */
    @Bean
    public Encoder encoder() {
        HttpMessageConverter jacksonConverter = new MappingJackson2HttpMessageConverter(customObjectMapper());
        ObjectFactory<HttpMessageConverters> objectFactory = () -> new HttpMessageConverters(jacksonConverter);
        return new SpringEncoder(objectFactory);
    }

    /**
     * 自定义ObjectMapper
     *
     * @return
     */
    private ObjectMapper customObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        //序列化的时候，不输出null值
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        return objectMapper;
    }


    /**
     * 异常解码器
     *
     * @return
     */
    @Bean
    public ErrorDecoder errorDecoder() {
        return (methodKey, response) -> {
            ExceptionResponse result = Optional.ofNullable(response.body())
                    .map(body -> {
                        try {
                            return Util.toString(response.body().asReader(Charset.forName("UTF-8")));
                        } catch (IOException e) {
                            log.error(e.getMessage(), e);
                            return null;
                        }
                    })
                    .map(s -> {
                        try {
                            log.debug("[FeignAutoConfiguration.errorDecoder] result={}", s);
                            return JsonMapper.builder().build().readValue(s, ExceptionResponse.class);
                        } catch (JsonProcessingException e) {
                            log.error(e.getMessage(), e);
                            return null;
                        }
                    })
                    .orElse(null);


            if (Objects.isNull(result)) {
                return new SystemException("服务调用失败");
            }

            //已知业务异常不参与熔断策略
            if (Objects.nonNull(result.getCode()) && result.getCode() > DefaultExceptionCode.SYSTEM.getCode()) {
                return new FeignBusinessException(result.getCode(), result.getMsg());
            }

            return new SystemException(result.getMsg());
        };
    }
}