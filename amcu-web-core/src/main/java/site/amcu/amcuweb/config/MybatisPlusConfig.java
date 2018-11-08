package site.amcu.amcuweb.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description:    mybatis-plus的配置类
 * @Author: Ben-Zheng
 * @Date: 2018/11/05 9:11
 */
@Configuration
@MapperScan("site.amcu.amcuweb.dao")
public class MybatisPlusConfig {

    /**
     * mybatis plus sql执行效率插件(只在开发时使用,生产环境下应该关闭)
     * @return
     */
    @Bean
    public PerformanceInterceptor performanceInterceptor() {
        PerformanceInterceptor interceptor = new PerformanceInterceptor();
        /** 格式化输出 */
        interceptor.setFormat(true);
        return interceptor;
    }

    /**
     * mybatis plus分页插件
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor page = new PaginationInterceptor();
        /** mysql方言 */
        page.setDialectType("mysql");
        return page;
    }

}
