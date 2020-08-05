/**
 * Created by Tiro on 2020/08/04.
 */
package org.smartframework.data.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * 类描述：德鲁伊数据源自动配置 <br>
 *
 * @Description: 德鲁伊数据源自动配置
 * @Author: Tiro
 * @Date: 2020/8/4 17:45
 */
@Slf4j
@Configuration
@ConditionalOnClass({DruidDataSource.class})
@AutoConfigureBefore({DataSourceAutoConfiguration.class})
@EnableConfigurationProperties({DruidDataSourceProperties.class, DataSourceProperties.class})
public class DruidDataSourceAutoConfiguration {

    @Bean(
            initMethod = "init"
    )
    @ConditionalOnMissingBean
    public DataSource dataSource(DruidDataSourceProperties properties) {
        log.info("Init DruidDataSource");
        try {
            DataSourceBuilder builder = DataSourceBuilder.create()
                    .driverClassName(properties.getDriverClassName())
                    .url(properties.getUrl())
                    .username(properties.getUsername())
                    .password(properties.getPassword())
                    .type(DruidDataSource.class);

            DruidDataSource dataSource = (DruidDataSource) builder.build();
            //设置最大活跃
            dataSource.setMaxActive(properties.getMaxActive());
            if (properties.getDriverClassName().indexOf("jtds") != -1 || properties.getDriverClassName().indexOf("sqlserver") != -1) {
                dataSource.setValidationQuery("select 1");
            } else if (properties.getDriverClassName().indexOf("oracle") != -1) {
                dataSource.setValidationQuery("SELECT 'x' FROM dual");
            }
            return dataSource;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
