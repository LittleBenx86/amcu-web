package site.amcu.amcuweb.social.wechat.connection;

import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import site.amcu.amcuweb.social.wechat.api.WeChat;
import site.amcu.amcuweb.social.wechat.api.WeChatImpl;

/**
 * @Description:    微信登录的服务提供
 * @Author: Ben-Zheng
 * @Date: 2018/11/06 20:47
 */
public class WeChatServiceProvider extends AbstractOAuth2ServiceProvider<WeChat> {

    /** 获取微信授权码的url */
    private static final String URL_AUTHORIZE = "https://open.weixin.qq.com/connect/qrconnect";

    /** 获取微信accessToken的url */
    private static final String URL_ACCESS_TOKEN = "https://api.weixin.qq.com/sns/oauth2/access_token";

    public WeChatServiceProvider(String appId, String appSecret) {
        super(new WeChatOAuth2Template(appId, appSecret, URL_AUTHORIZE, URL_ACCESS_TOKEN));
    }

    @Override
    public WeChat getApi(String accessToken) {
        return new WeChatImpl(accessToken);
    }

}
