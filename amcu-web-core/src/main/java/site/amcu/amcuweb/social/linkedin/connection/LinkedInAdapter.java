package site.amcu.amcuweb.social.linkedin.connection;

import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;
import site.amcu.amcuweb.social.linkedin.api.LinkedIn;
import site.amcu.amcuweb.social.linkedin.api.LinkedInUserInfo;

/**
 * @Description:    linkedin第三方调用的api适配器实现类,装配Connection需要的参数
 * @Author: Ben-Zheng
 * @Date: 2018/11/07 16:08
 */
public class LinkedInAdapter implements ApiAdapter<LinkedIn> {


    @Override
    public boolean test(LinkedIn api) {
        return true;
    }

    @Override
    public void setConnectionValues(LinkedIn api, ConnectionValues values) {
        LinkedInUserInfo userInfo = api.getUserInfo();
        values.setDisplayName(userInfo.getFormattedName());
        values.setImageUrl(userInfo.getPictureUrl());
        /** linkedin用户拥有个人主页 */
        values.setProfileUrl(userInfo.getPublicProfileUrl());
        /** github没有openid,使用github的id(唯一标识)替代 */
        values.setProviderUserId(userInfo.getId());
    }

    @Override
    public UserProfile fetchUserProfile(LinkedIn api) {
        return null;
    }

    @Override
    public void updateStatus(LinkedIn api, String message) {
        /** linkedin do nothing */
    }
}
