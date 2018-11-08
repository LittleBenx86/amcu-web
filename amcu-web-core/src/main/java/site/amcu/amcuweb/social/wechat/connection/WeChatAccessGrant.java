package site.amcu.amcuweb.social.wechat.connection;

import org.springframework.social.oauth2.AccessGrant;

/**
 * @Description:    微信授权实现
 *                  由于微信在获取access_token时会同时返回openId,并没有单独的通过accessToke换取openId的服务
 *                  这是与OAuth2.0实现不同的,故需要重新实现
 * @Author: Ben-Zheng
 * @Date: 2018/11/06 20:18
 */
public class WeChatAccessGrant extends AccessGrant {

    private static final long serialVersionUID = -8358917480450688178L;

    private String openId;

    public WeChatAccessGrant() {
        super("");
    }

    public WeChatAccessGrant(String accessToken, String scope, String refreshToken, Long expiresIn) {
        super(accessToken, scope, refreshToken, expiresIn);
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
