package site.amcu.amcuweb.social.github.connection;

import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import site.amcu.amcuweb.social.github.api.GitHub;

/**
 * @Description:    github第三方登录连接工厂,创建Connection
 * @Author: Ben-Zheng
 * @Date: 2018/11/07 8:52
 */
public class GitHubConnectionFactory extends OAuth2ConnectionFactory<GitHub> {

    public GitHubConnectionFactory(String providerId, String appId, String appSecret) {
        super(providerId, new GitHubServiceProvider(appId, appSecret), new GitHubAdapter());
    }

}
