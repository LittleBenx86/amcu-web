package site.amcu.amcuweb.properties;

import org.springframework.boot.autoconfigure.social.SocialProperties;

/**
 * @Description:    第三方微信登录属性配置(微信登录流程非严格的OAuth2协议的实现)
 *                  providerId
 *                  appId appSecret
 * @Author: Ben-Zheng
 * @Date: 2018/11/06 19:50
 */
public class WeChatProperties extends SocialProperties {

    /** 服务商标识 */
    private String providerId = "weixin";

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }
}
