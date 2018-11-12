package site.amcu.amcuweb.social.coding.connection;

import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import site.amcu.amcuweb.social.coding.api.Coding;

/**
 * @Description:    coding第三方登录连接工厂,创建Connection
 * @Author: Ben-Zheng
 * @Date: 2018/11/11 18:37
 */
public class CodingConnectionFactory extends OAuth2ConnectionFactory<Coding> {

    public CodingConnectionFactory(String providerId, String appId, String appSecret) {
        super(providerId, new CodingServiceProvider(appId, appSecret), new CodingAdapter());
    }

}
