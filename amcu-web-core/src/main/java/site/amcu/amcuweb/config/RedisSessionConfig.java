package site.amcu.amcuweb.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @Description:    启用redis实现session的共享
 * @Author: Ben-Zheng
 * @Date: 2018/11/10 17:47
 */
@Configuration
@EnableRedisHttpSession
public class RedisSessionConfig {
}
