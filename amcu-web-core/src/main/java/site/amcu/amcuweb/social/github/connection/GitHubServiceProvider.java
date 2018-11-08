package site.amcu.amcuweb.social.github.connection;

import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import site.amcu.amcuweb.social.github.api.GitHub;
import site.amcu.amcuweb.social.github.api.GitHubImpl;
import site.amcu.amcuweb.social.qq.connection.QQOAuth2Template;

/**
 * @Description:     github第三方服务提供
 * @Author: Ben-Zheng
 * @Date: 2018/11/07 8:48
 */
public class GitHubServiceProvider extends AbstractOAuth2ServiceProvider<GitHub> {

    /** clientid */
    private String appId;
    /** 用户在该地址点击确认github授权 */
    private static final String URL_AUTHORIZE = "https://github.com/login/oauth/authorize";
    /** 系统获取github access_token时访问的地址 */
    private static final String URL_ACCESS_TOKEN = "https://github.com/login/oauth/access_token";

    public GitHubServiceProvider(String appId, String appSecret) {
        super(new GitHubOAuth2Template(appId, appSecret, URL_AUTHORIZE, URL_ACCESS_TOKEN));
        this.appId = appId;
    }

    @Override
    public GitHub getApi(String accessToken) {
        return new GitHubImpl(accessToken, this.appId);
    }
}
