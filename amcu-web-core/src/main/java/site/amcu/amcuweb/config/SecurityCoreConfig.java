package site.amcu.amcuweb.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import site.amcu.amcuweb.properties.SecurityProperties;

/**
 * @Description:    使配置类SecurityProperties生效的配置
 * @Author: Ben-Zheng
 * @Date: 2018/10/27 22:19
 */
@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityCoreConfig {

    /**
     * 通用密码加密配置,解决browser和app模块不兼容的问题
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
