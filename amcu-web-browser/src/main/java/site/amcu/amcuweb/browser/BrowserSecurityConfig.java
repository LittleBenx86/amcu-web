package site.amcu.amcuweb.browser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.social.security.SpringSocialConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import site.amcu.amcuweb.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import site.amcu.amcuweb.browser.authentication.AbstractChannelSecurityConfig;
import site.amcu.amcuweb.properties.SecurityConstants;
import site.amcu.amcuweb.properties.SecurityProperties;
import site.amcu.amcuweb.validate.ValidateCodeSecurityConfig;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @Description:    浏览器端的配置类
 * @Author: Ben-Zheng
 * @Date: 2018/11/04 13:30
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true, securedEnabled = true)
public class BrowserSecurityConfig extends AbstractChannelSecurityConfig {

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Autowired
    private SpringSocialConfigurer generalSocialSecurityConfig;

    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;

    @Resource
    private SessionInformationExpiredStrategy sessionInformationExpiredStrategy;

    @Resource
    private InvalidSessionStrategy invalidSessionStrategy;

    /**
     * 开启注解权限访问,需要配置该Bean
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    /**
     * 注入数据源,产生使用token实现免密码登录的token机制(表)
     * @return
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        /** 启动的时候自动创建表,下次启动需要改为false（使用配置文件配置） */
        tokenRepository.setCreateTableOnStartup(false);
        return tokenRepository;
    }

    /**
     * 静态资源js css html的权限开放
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/static/**", "/js/**", "/css/**", "/fonts/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        this.applyPasswordAuthenticationConfig(http);
        http
                .apply(validateCodeSecurityConfig)
                    .and()
                .apply(smsCodeAuthenticationSecurityConfig)
                    .and()
                .apply(generalSocialSecurityConfig)
                    .and()
                .rememberMe()
                    .tokenRepository(persistentTokenRepository())
                    .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
                    .userDetailsService(userDetailsService)
                    .and()
                .sessionManagement()
                    .invalidSessionStrategy(this.invalidSessionStrategy)
                    .maximumSessions(securityProperties.getBrowser().getSession().getMaxNumInSession())
                    .maxSessionsPreventsLogin(securityProperties.getBrowser().getSession().isMaxSessionsPreventsSignIn())
                    .expiredSessionStrategy(this.sessionInformationExpiredStrategy)
                        .and()
                    .and()
                .authorizeRequests()
                    .antMatchers(
                            SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
                            SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE,
                            securityProperties.getBrowser().getLoginPage(),
                            securityProperties.getBrowser().getSignUpPage(),
                            SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/*",
                            SecurityConstants.DEFAULT_DRUID_URL_PREFIX + "/*",
                            SecurityConstants.DEFAULT_SIGNUP_PROCESSING_URL_FORM,
                            securityProperties.getBrowser().getSignUpUrl(),
                            securityProperties.getBrowser().getSession().getSessionInvalidUrl()
                    ).permitAll()
                    .and()
                .csrf().disable();
    }

}
