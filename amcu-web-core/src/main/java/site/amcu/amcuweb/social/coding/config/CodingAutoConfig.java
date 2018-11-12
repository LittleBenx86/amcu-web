package site.amcu.amcuweb.social.coding.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.web.servlet.View;
import site.amcu.amcuweb.properties.CodingProperties;
import site.amcu.amcuweb.properties.GitHubProperties;
import site.amcu.amcuweb.properties.SecurityProperties;
import site.amcu.amcuweb.social.SocialBindingConnectedView;
import site.amcu.amcuweb.social.coding.connection.CodingConnectionFactory;

/**
 * @Description:
 * @Author: Ben-Zheng
 * @Date: 2018/11/11 19:10
 */
@Configuration
@ConditionalOnProperty(prefix = "sys.properties.social.coding", name = "app-id")
@Order(2)
public class CodingAutoConfig extends SocialAutoConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    protected ConnectionFactory<?> createConnectionFactory() {
        CodingProperties codingConfig = securityProperties.getSocial().getCoding();
        return new CodingConnectionFactory(codingConfig.getProviderId(), codingConfig.getAppId(), codingConfig.getAppSecret());
    }

    /**
     * github绑定和解绑操作
     * 固定写法:providerId + Connect/Connected
     * 绑定使用post,解绑使用delete
     * @return
     */
    @Bean({"connect/codingConnected"})
    @ConditionalOnMissingBean(name = "codingConnectedView")
    public View codingConnectedView() {
        return new SocialBindingConnectedView();
    }

}
