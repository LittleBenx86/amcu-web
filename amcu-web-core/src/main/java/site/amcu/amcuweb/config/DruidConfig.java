package site.amcu.amcuweb.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;


/**
 * 阿里巴巴Druid数据库连接池配置类
 *      druid并不是在springboot中直接支持的，需要进行配置信息的定制
 * @author Ben-Zheng
 * @Date 2018-10-27
 */
@Configuration
@ServletComponentScan/** 用于扫描所有的Servlet、Filter、Listener */
public class DruidConfig {

    /** slf4j日志 */
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.druid")
    public DataSource druidDataSource(){
       return new DruidDataSource();
    }

    /**
     * 配置事务管理器
     * @return
     */
    @Bean(name = "transactionManager")
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(this.druidDataSource());
    }

}
