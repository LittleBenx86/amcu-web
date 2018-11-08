package site.amcu.amcuweb.social.linkedin.connection;

import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import site.amcu.amcuweb.social.linkedin.api.LinkedIn;

/**
 * @Description:    linkedin第三方登录连接工厂,创建Connection
 * @Author: Ben-Zheng
 * @Date: 2018/11/07 16:16
 */
public class LinkedInConnectionFactory extends OAuth2ConnectionFactory<LinkedIn> {

    public LinkedInConnectionFactory(String providerId, String appId, String appSecret) {
        super(providerId, new LinkedInServiceProvider(appId, appSecret), new LinkedInAdapter());
    }

}
