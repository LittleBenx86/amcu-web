package site.amcu.amcuweb.social.linkedin.connection;

import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import site.amcu.amcuweb.social.linkedin.api.LinkedIn;
import site.amcu.amcuweb.social.linkedin.api.LinkedInImpl;

/**
 * @Description:    linkedin第三方服务提供
 * @Author: Ben-Zheng
 * @Date: 2018/11/07 15:59
 */
public class LinkedInServiceProvider extends AbstractOAuth2ServiceProvider<LinkedIn> {

    /** clientid */
    private String appId;
    /** 用户在该地址点击确认github授权 */
    private static final String URL_AUTHORIZE = "https://www.linkedin.com/oauth/v2/authorization";
    /** 系统获取linkedin access_token时访问的地址 */
    private static final String URL_ACCESS_TOKEN = "https://www.linkedin.com/oauth/v2/accessToken";

    public LinkedInServiceProvider(String appId, String appSecret) {
        super(new LinkedInOAuth2Template(appId, appSecret, URL_AUTHORIZE, URL_ACCESS_TOKEN));
        this.appId = appId;
    }

    @Override
    public LinkedIn getApi(String accessToken) {
        return new LinkedInImpl(accessToken, this.appId);
    }

}
