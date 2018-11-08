package site.amcu.amcuweb.social.wechat.connection;

import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;
import site.amcu.amcuweb.social.wechat.api.WeChat;
import site.amcu.amcuweb.social.wechat.api.WeChatUserInfo;

/**
 * @Description:    微信登录请求参数装配
 * @Author: Ben-Zheng
 * @Date: 2018/11/06 20:22
 */
public class WeChatAdapter implements ApiAdapter<WeChat> {

    /** 与qq实现最大的不同(qq的openid在QQImpl) */
    private String openId;

    public WeChatAdapter() {

    }

    public WeChatAdapter(String openId) {
        super();
        this.openId = openId;
    }

    @Override
    public boolean test(WeChat api) {
        return true;
    }

    @Override
    public void setConnectionValues(WeChat api, ConnectionValues values) {
        WeChatUserInfo profile = api.getUserInfo(this.openId);
        values.setProviderUserId(profile.getOpenid());
        values.setDisplayName(profile.getNickname());
        values.setImageUrl(profile.getHeadimgurl());
    }

    /**
     * 微信用户也没有个人主页
     * @param api
     * @return
     */
    @Override
    public UserProfile fetchUserProfile(WeChat api) {
        return null;
    }

    @Override
    public void updateStatus(WeChat api, String message) {
        /** wechat do nothing */
    }

}
