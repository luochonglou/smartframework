/**
 * Created by Tiro on 2020/08/04.
 */
package org.smartframework.data.mybatis.plus.generator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import lombok.AllArgsConstructor;
import lombok.Setter;

import java.util.*;

/**
 * 类描述：自动生成 <br>
 * <p>
 * 使用模板生成需要添加plus-generator  和 模板引擎，例如：
 * <dependency>
 * <groupId>com.baomidou</groupId>
 * <artifactId>mybatis-plus-generator</artifactId>
 * <version>3.3.0</version>
 * <scope>test</scope>
 * </dependency>
 * <dependency>
 * <groupId>org.apache.velocity</groupId>
 * <artifactId>velocity-engine-core</artifactId>
 * <version>2.1</version>
 * <scope>test</scope>
 * </dependency>
 *
 * @Description: 自动生成
 * @Author: Tiro
 * @Date: 2020/8/4 20:02
 */
@Setter
@AllArgsConstructor
public class CodeGenerator {

    /**
     * 全局配置
     */
    private GlobalConfig globalConfig;
    /**
     * 数据源配置
     */
    private DataSourceConfig dataSourceConfig;
    /**
     * 策略配置
     */
    private StrategyConfig strategyConfig;
    /**
     * 打包配置
     */
    private PackageConfig packageConfig;
    /**
     * 自定义配置
     */
    private InjectionConfig injectionConfig;
    /**
     * 模板配置
     */
    private TemplateConfig templateConfig;


    /**
     * @return
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder
     */
    public static class Builder {
        /**
         * 数据库地址
         */
        private String url;

        /**
         * 用户名
         */
        private String userName;

        /**
         * 密码
         */
        private String passWord;

        /**
         * 驱动
         */
        private String driver;

        /**
         * 自定义需要填充的字段
         */
        private List<TableFill> tableFillList;

        /**
         * 需要生成的表
         */
        private String[] tableNames;

        /**
         * 表前缀
         */
        private String[] tablePrefix;

        /**
         * 包名
         */
        private String packageName;

        /**
         * 模块名
         */
        private String moduleName;

        /**
         * 作者
         */
        private String author;

        private Builder() {

        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder userName(String userName) {
            this.userName = userName;
            return this;
        }

        public Builder passWord(String passWord) {
            this.passWord = passWord;
            return this;
        }

        public Builder driver(String driver) {
            this.driver = driver;
            return this;
        }

        public Builder tableFillList(List<TableFill> tableFillList) {
            this.tableFillList = tableFillList;
            return this;
        }

        public Builder tableNames(String[] tableNames) {
            this.tableNames = tableNames;
            return this;
        }

        public Builder tablePrefix(String[] tablePrefix) {
            this.tablePrefix = tablePrefix;
            return this;
        }

        public Builder packageName(String packageName) {
            this.packageName = packageName;
            return this;
        }

        public Builder moduleName(String moduleName) {
            this.moduleName = moduleName;
            return this;
        }

        public Builder author(String author) {
            this.author = author;
            return this;
        }


        /**
         * @return
         */
        public CodeGenerator build() {
            if (Objects.isNull(url)) {
                throw new NullPointerException("url must configure.");
            }
            if (Objects.isNull(userName)) {
                throw new NullPointerException("userName must configure.");
            }
            if (Objects.isNull(passWord)) {
                throw new NullPointerException("passWord must configure.");
            }
            if (Objects.isNull(driver)) {
                throw new NullPointerException("driver must configure.");
            }

            return new CodeGenerator(new GlobalConfig()
                    // 输出目录
                    .setOutputDir("/CodeGenerator")
                    // 是否覆盖文件
                    .setFileOverride(false)
                    // 开启 activeRecord 模式
                    .setActiveRecord(true)
                    // XML 二级缓存
                    .setEnableCache(false)
                    // XML ResultMap
                    .setBaseResultMap(false)
                    // XML columnList
                    .setBaseColumnList(true)
                    // 自定义xml文件命名，%s 会自动填充表实体属性！
                    .setXmlName("%sMapper")
                    //.setServiceName("MP%sService")
                    //.setServiceImplName("%sServiceDiy")
                    //.setControllerName("%sAction")
                    // 自定义mapper文件命名
                    .setMapperName("%sDao")
                    .setServiceName("%sService")
                    .setAuthor(this.author),

                    new DataSourceConfig()
                            // 数据库类型
                            .setDbType(DbType.MYSQL)
                            //驱动程序
                            .setDriverName(this.driver)
                            //数据库地址
                            .setUrl(this.url)
                            //用户名
                            .setUsername(this.userName)
                            //密码
                            .setPassword(this.passWord)
                            //类型转换，可以重写MySqlTypeConvert.processTypeConvert方法实现自定义
                            .setTypeConvert(new MySqlTypeConvert()),

                    new StrategyConfig()
                            // 全局大写命名
                            // .setCapitalMode(true)
                            // 表名生成策略
                            .setNaming(NamingStrategy.underline_to_camel)
                            // 需要生成的表
                            .setInclude(this.tableNames)
                            // 排除生成的表
                            // .setExclude(new String[]{"test"})
                            // 自定义实体，公共字段
                            // .setSuperEntityColumns(new String[]{"test_id"})
                            .setTableFillList(this.tableFillList)
                            // 自定义实体父类
                            // .setSuperEntityClass("com.baomidou.demo.baseserve.BsBaseEntity")
                            // // 自定义 dao 父类
                            // .setSuperMapperClass("com.baomidou.demo.baseserve.BsBaseMapper")
                            // // 自定义 service 父类
                            // .setSuperServiceClass("com.baomidou.demo.baseserve.BsBaseService")
                            // // 自定义 service 实现类父类
                            // .setSuperServiceImplClass("com.baomidou.demo.baseserve.BsBaseServiceImpl")
                            // 自定义 controller 父类
                            // .setSuperControllerClass("com.baomidou.demo.TestController")
                            // 【实体】是否生成字段常量（默认 false）
                            // public static final String ID = "test_id";
                            .setEntityColumnConstant(true)
                            // 【实体】是否为构建者模型（默认 false）
                            // public User setName(String name) {this.name = name; return this;}
                            .setEntityBuilderModel(true)
                            // Boolean类型字段是否移除is前缀处理
                            // .setEntityBooleanColumnRemoveIsPrefix(true)
                            // .setRestControllerStyle(true)
                            // .setControllerMappingHyphenStyle(true)
                            // 【实体】是否为lombok模型（默认 false）<a href="https://projectlombok.org/">document</a>
                            .setEntityLombokModel(true)
                            .setTablePrefix(tablePrefix),


                    new PackageConfig()
                            .setModuleName(this.moduleName)
                            .setEntity("domain")
                            // 包路径
                            .setParent(this.packageName)
                            // controller包名，默认 lang
                            .setController("controller")
                            //xml包名，默认mapper.xml
                            .setXml("dao")
                            //mapper报名，默认mapper
                            .setMapper("dao"),


                    new InjectionConfig() {
                        // 注入自定义配置，可以在 VM 中使用 cfg.abc 设置的值
                        @Override
                        public void initMap() {
                            Map<String, Object> map = new HashMap<>(1);
                            map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-mp");
                            this.setMap(map);
                        }
                    }.setFileOutConfigList(
                            Collections.singletonList(new FileOutConfig("/templates/mapper.xml.vm") {
                                // 自定义输出文件目录
                                @Override
                                public String outputFile(TableInfo tableInfo) {
                                    return "/CodeGenerator/xml/" + tableInfo.getEntityName() + "Mapper.xml";
                                }
                            })
                    ),

                    new TemplateConfig().setXml(null)
                    // 自定义模板配置，模板可以参考源码 /baseserve-plus/src/main/resources/template 使用 copy
                    // 至您项目 src/main/resources/template 目录下，模板名称也可自定义如下配置：
                    // .setController("...");
                    // .setEntity("...");
                    // .setMapper("...");
                    // .setXml("...");
                    // .setService("...");
                    // .setServiceImpl("...");
            );

        }

    }

    public CodeGenerator globalConfig(GlobalConfig globalConfig) {
        this.globalConfig = globalConfig;
        return this;
    }

    public CodeGenerator dataSourceConfig(DataSourceConfig dataSourceConfig) {
        this.dataSourceConfig = dataSourceConfig;
        return this;
    }

    public CodeGenerator strategyConfig(StrategyConfig strategyConfig) {
        this.strategyConfig = strategyConfig;
        return this;
    }

    public CodeGenerator packageConfig(PackageConfig packageConfig) {
        this.packageConfig = packageConfig;
        return this;
    }

    public CodeGenerator injectionConfig(InjectionConfig injectionConfig) {
        this.injectionConfig = injectionConfig;
        return this;
    }

    public CodeGenerator templateConfig(TemplateConfig templateConfig) {
        this.templateConfig = templateConfig;
        return this;
    }


    /**
     * 初始化
     */

    /**
     * 自动构建
     */
    public synchronized void authGenerator() {

        // 代码生成器
        AutoGenerator mpg = new AutoGenerator()
                // 全局配置
                .setGlobalConfig(this.globalConfig)
                // 数据源配置
                .setDataSource(this.dataSourceConfig)
                // 策略配置
                .setStrategy(this.strategyConfig)
                // 打包配置
                .setPackageInfo(this.packageConfig)
                // 自定义配置
                .setCfg(this.injectionConfig)
                // 自定义模板配置
                .setTemplate(this.templateConfig);
        // 执行生成
        mpg.execute();
    }
}
