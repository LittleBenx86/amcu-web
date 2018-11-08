package site.amcu.amcuweb.social.qq.connection;

import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import site.amcu.amcuweb.social.qq.api.QQ;

/**
 * @Description:    qq连接(信息)创建工厂
 * @Author: Ben-Zheng
 * @Date: 2018/11/06 8:21
 */
public class QQConnectionFactory extends OAuth2ConnectionFactory<QQ> {

    public QQConnectionFactory(String providerId, String appId, String appSecret) {
        super(providerId, new QQServiceProvider(appId, appSecret), new QQAdapter());
    }

}
