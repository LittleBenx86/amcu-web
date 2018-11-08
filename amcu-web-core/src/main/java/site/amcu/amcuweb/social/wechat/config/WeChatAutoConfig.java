package site.amcu.amcuweb.social.wechat.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.social.connect.ConnectionFactory;
import site.amcu.amcuweb.properties.SecurityProperties;
import site.amcu.amcuweb.properties.WeChatProperties;
import site.amcu.amcuweb.social.wechat.connection.WeChatConnectionFactory;

/**
 * @Description:    微信登录的配置类
 *                  配置加入到spring security的过滤器链才生效
 *                  该配置类不生效,就无法使用该第三方登录服务
 * @Author: Ben-Zheng
 * @Date: 2018/11/06 20:56
 */
@Configuration
@ConditionalOnProperty(prefix = "sys.properties.social.wechat", name = "app-id")
@Order(2)
public class WeChatAutoConfig extends SocialAutoConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    protected ConnectionFactory<?> createConnectionFactory() {
        WeChatProperties wechatProperties = securityProperties.getSocial().getWechat();
        return new WeChatConnectionFactory(wechatProperties.getProviderId(), wechatProperties.getAppId(),
                wechatProperties.getAppSecret());
    }

}
