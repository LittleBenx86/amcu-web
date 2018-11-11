package site.amcu.amcuweb.browser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import site.amcu.amcuweb.browser.session.BrowserExpiredSessionStrategy;
import site.amcu.amcuweb.browser.session.BrowserInvalidSessionStrategy;
import site.amcu.amcuweb.properties.SecurityProperties;

/**
 * @Description:    浏览器端的Bean配置类
 * @Author: Ben-Zheng
 * @Date: 2018/11/09 19:22
 */
@Configuration
public class BrowserSecurityBeanConfig {

    /** 配置session的策略Bean */

    @Autowired
    private SecurityProperties securityProperties;

    @Bean
    @ConditionalOnMissingBean(InvalidSessionStrategy.class)
    public InvalidSessionStrategy invalidSessionStrategy() {
        return new BrowserInvalidSessionStrategy(securityProperties.getBrowser().getSession().getSessionInvalidUrl());
    }

    @Bean
    @ConditionalOnMissingBean(SessionInformationExpiredStrategy.class)
    public SessionInformationExpiredStrategy sessionInformationExpiredStrategy() {
        return new BrowserExpiredSessionStrategy(securityProperties.getBrowser().getSession().getSessionInvalidUrl());
    }


}
