/**
 * Created by Tiro on 2020/08/14.
 */
package org.smartframework.cloud.feign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Properties;

/**
 * 类描述：eureka 环境配置 <br>
 *
 * @Description: eureka 环境配置
 * @Author: Tiro
 * @Date: 2020/8/14 15:48
 */
public class EurekaEnvironmentPostProcessor implements EnvironmentPostProcessor {
    private final Properties properties = new Properties();

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        //自定义配置文件
        String[] profiles = {
                "endpoints-default.properties",
                "eureka-default.properties",
                "hystrix-default.properties",
                "ribbon-default.properties",
        };

        //循环添加
        for (String profile : profiles) {
            //从classpath路径下面查找文件
            Resource resource = new ClassPathResource(profile);
            //加载成PropertySource对象，并添加到Environment环境中
            environment.getPropertySources().addLast(loadProfiles(resource));
        }
    }

    /**
     * 加载单个配置文件
     *
     * @param resource
     * @return
     */
    private PropertySource<?> loadProfiles(Resource resource) {
        if (!resource.exists()) {
            throw new IllegalArgumentException("资源" + resource + "不存在");
        }
        try {
            //从输入流中加载一个Properties对象
            properties.load(resource.getInputStream());
            return new PropertiesPropertySource(resource.getFilename(), properties);
        } catch (IOException ex) {
            throw new IllegalStateException("加载配置文件失败" + resource, ex);
        }
    }
}