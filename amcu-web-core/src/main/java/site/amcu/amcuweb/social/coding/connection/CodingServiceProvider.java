package site.amcu.amcuweb.social.coding.connection;

import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import site.amcu.amcuweb.social.coding.api.Coding;
import site.amcu.amcuweb.social.coding.api.CodingImpl;

/**
 * @Description:    coding第三方服务提供
 * @Author: Ben-Zheng
 * @Date: 2018/11/11 18:26
 */
public class CodingServiceProvider extends AbstractOAuth2ServiceProvider<Coding> {

    /** clientid */
    private String appId;
    /** 用户在该地址点击确认coding授权 */
    private static final String URL_AUTHORIZE = "https://coding.net/oauth_authorize.html";
    /** 系统获取coding access_token时访问的地址 */
    private static final String URL_ACCESS_TOKEN = "https://coding.net/api/oauth/access_token";

    public CodingServiceProvider(String appId, String appSecret) {
        super(new CodingOAuth2Template(appId, appSecret, URL_AUTHORIZE, URL_ACCESS_TOKEN));
        this.appId = appId;
    }

    @Override
    public Coding getApi(String accessToken) {
        return new CodingImpl(accessToken, this.appId);
    }
}
