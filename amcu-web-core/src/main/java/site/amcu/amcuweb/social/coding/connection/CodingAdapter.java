package site.amcu.amcuweb.social.coding.connection;

import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;
import site.amcu.amcuweb.social.coding.api.Coding;
import site.amcu.amcuweb.social.coding.api.CodingUserInfo;

/**
 * @Description:    coding第三方调用的api适配器实现类,装配Connection需要的参数
 * @Author: Ben-Zheng
 * @Date: 2018/11/11 18:21
 */
public class CodingAdapter implements ApiAdapter<Coding> {

    @Override
    public boolean test(Coding api) {
        return true;
    }

    @Override
    public void setConnectionValues(Coding api, ConnectionValues values) {
        CodingUserInfo userInfo = api.getUserInfo();
        values.setDisplayName(userInfo.getName());
        values.setImageUrl(userInfo.getAvatar());
        /** coding用户拥有个人主页 */
        values.setProfileUrl(userInfo.getPath());
        /** coding没有openid,使用coding的id(唯一标识)替代 */
        values.setProviderUserId(userInfo.getId().toString());
    }

    @Override
    public UserProfile fetchUserProfile(Coding api) {
        return null;
    }

    @Override
    public void updateStatus(Coding api, String message) {
        /** coding do nothing */
    }
}
