/**
 * Created by Tiro on 2020/08/04.
 */
package org.smartframework.data.datasource;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 类描述：德鲁伊数据源配置<br>
 *
 * @Description: 德鲁伊数据源配置
 * @Author: Tiro
 * @Date: 2020/8/4 17:18
 */
@Getter
@Setter
@NoArgsConstructor
@ConfigurationProperties("spring.datasource.druid")
class DruidDataSourceProperties implements InitializingBean {

    @Autowired
    private DataSourceProperties basicProperties;

    /**
     * 数据源地址
     */
    private String url;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 驱动名
     */
    private String driverClassName;


    /**
     * 最大数据库连接数，默认：8
     */
    private int maxActive = 8;

    /**
     * @throws Exception
     */
    public void afterPropertiesSet() throws Exception {
        if (this.getUsername() == null) {
            this.setUsername(this.basicProperties.determineUsername());
        }

        if (this.getPassword() == null) {
            this.setPassword(this.basicProperties.determinePassword());
        }

        if (this.getUrl() == null) {
            this.setUrl(this.basicProperties.determineUrl());
        }

        if (this.getDriverClassName() == null) {
            this.setDriverClassName(this.basicProperties.getDriverClassName());
        }

    }
}
