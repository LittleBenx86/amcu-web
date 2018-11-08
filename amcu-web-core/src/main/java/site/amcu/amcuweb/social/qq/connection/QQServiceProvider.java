package site.amcu.amcuweb.social.qq.connection;


import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import site.amcu.amcuweb.social.qq.api.QQ;
import site.amcu.amcuweb.social.qq.api.QQImpl;

/**
 * @Description:    qq第三方服务提供
 * @Author: Ben-Zheng
 * @Date: 2018/11/06 8:38
 */
public class QQServiceProvider extends AbstractOAuth2ServiceProvider<QQ> {

    /** qq互联开发者申请:唯一 */
    private String appId;
    /** 用户在该地址点击确认qq授权 */
    private static final String URL_AUTHORIZE = "https://graph.qq.com/oauth2.0/authorize";
    /** 系统获取qq access_token时访问的地址 */
    private static final String URL_ACCESS_TOKEN = "https://graph.qq.com/oauth2.0/token";

    /**
     * 调用OAuth2模版发送请求
     * @param appId
     * @param appSecret     qq自动分配获得
     */
    public QQServiceProvider(String appId, String appSecret) {
        super(new QQOAuth2Template(appId, appSecret, URL_AUTHORIZE, URL_ACCESS_TOKEN));
        /** 确保appId始终一致 */
        this.appId = appId;
    }

    @Override
    public QQ getApi(String accessToken) {
        return new QQImpl(accessToken, this.appId);
    }
}
