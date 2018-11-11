package site.amcu.amcuweb.social.linkedin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.web.servlet.View;
import site.amcu.amcuweb.properties.LinkedInProperties;
import site.amcu.amcuweb.properties.SecurityProperties;
import site.amcu.amcuweb.social.SocialBindingConnectedView;
import site.amcu.amcuweb.social.linkedin.connection.LinkedInConnectionFactory;

/**
 * @Description:    linkedin第三方服务配置类
 *                  该配置类不生效,就无法使用该第三方登录服务
 * @Author: Ben-Zheng
 * @Date: 2018/11/07 16:19
 */
@Configuration
@ConditionalOnProperty(prefix = "sys.properties.social.linkedin", name = "app-id")
@Order(2)
public class LinkedInAutoConfig extends SocialAutoConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    protected ConnectionFactory<?> createConnectionFactory() {
        LinkedInProperties linkedConfig = securityProperties.getSocial().getLinkedin();
        return new LinkedInConnectionFactory(linkedConfig.getProviderId(), linkedConfig.getAppId(), linkedConfig.getAppSecret());
    }

    /**
     * linkedin绑定和解绑操作
     * 固定写法:providerId + Connect/Connected
     * 绑定使用post,解绑使用delete
     * @return
     */
    @Bean({"connect/linkedinConnect", "connect/linkedinConnected"})
    @ConditionalOnMissingBean(name = "linkedinConnectedView")
    public View linkedinConnectedView() {
        return new SocialBindingConnectedView();
    }

}
