package site.amcu.amcuweb.social.qq.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.social.connect.ConnectionFactory;
import site.amcu.amcuweb.properties.QQProperties;
import site.amcu.amcuweb.properties.SecurityProperties;
import site.amcu.amcuweb.social.qq.connection.QQConnectionFactory;

/**
 * @Description:    qq第三方服务配置类
 *                  该配置类不生效,就无法使用该第三方登录服务
 * @Author: Ben-Zheng
 * @Date: 2018/11/06 8:53
 */
@Configuration
@ConditionalOnProperty(prefix = "sys.properties.social.qq", name = "app-id")
@Order(2)
public class QQAutoConfig extends SocialAutoConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    protected ConnectionFactory<?> createConnectionFactory() {
        QQProperties qqConfig = securityProperties.getSocial().getQq();
        return new QQConnectionFactory(qqConfig.getProviderId(), qqConfig.getAppId(), qqConfig.getAppSecret());
    }
}
