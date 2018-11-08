package site.amcu.amcuweb.social;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.SpringSocialConfigurer;
import site.amcu.amcuweb.properties.SecurityProperties;

import javax.sql.DataSource;

/**
 * @Description:    第三方登录(注册)的用户信息入库配置
 * @Author: Ben-Zheng
 * @Date: 2018/11/06 9:09
 */
@Configuration
@EnableSocial
@Order(1)
public class SocialConfig extends SocialConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private SecurityProperties securityProperties;

    /** 使用第三方登录就相当于注册 */
    @Autowired(required = false)
    private ConnectionSignUp connectionSignUp;

    /** app端需要实现 */
    @Autowired(required = false)
    private SocialAuthenticationFilterPostProcessor socialAuthenticationFilterPostProcessor;

    /**
     * 覆盖父类的InMemoryUsersConnectionRepository实现,改为从数据库读取
     * @param connectionFactoryLocator
     * @return
     */
    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository(dataSource,
                connectionFactoryLocator, Encryptors.noOpText());
        repository.setTablePrefix("tbl_");
        if(connectionSignUp != null) {
            repository.setConnectionSignUp(connectionSignUp);
        }
        return repository;
    }

    @Bean
    public SpringSocialConfigurer generalSocialSecurityConfig() {
        String filterProcessesUrl = securityProperties.getSocial().getFilterProcessesUrl();
        GeneralSpringSocialConfigurer configurer = new GeneralSpringSocialConfigurer(filterProcessesUrl);
        /** 用户使用第三方登录时，如果系统查询不到该登陆的用户，说明该用户需要注册 */
        configurer.signupUrl(securityProperties.getBrowser().getSignUpUrl());
        if(null != socialAuthenticationFilterPostProcessor)
            configurer.setSocialAuthenticationFilterPostProcessor(socialAuthenticationFilterPostProcessor);
        return configurer;
    }

    @Bean
    public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator connectionFactoryLocator) {
        /** 拿到spring social的相关信息 */
        return new ProviderSignInUtils(connectionFactoryLocator,
                this.getUsersConnectionRepository(connectionFactoryLocator)) {

        };
    }

}
