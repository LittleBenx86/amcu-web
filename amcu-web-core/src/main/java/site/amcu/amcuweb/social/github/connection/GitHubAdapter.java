package site.amcu.amcuweb.social.github.connection;

import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;
import site.amcu.amcuweb.social.github.api.GitHub;
import site.amcu.amcuweb.social.github.api.GitHubUserInfo;

/**
 * @Description:    github第三方调用的api适配器实现类,装配Connection需要的参数
 * @Author: Ben-Zheng
 * @Date: 2018/11/07 8:38
 */
public class GitHubAdapter implements ApiAdapter<GitHub> {


    @Override
    public boolean test(GitHub api) {
        return true;
    }

    @Override
    public void setConnectionValues(GitHub api, ConnectionValues values) {
        GitHubUserInfo userInfo = api.getUserInfo();
        values.setDisplayName(userInfo.getLogin());
        values.setImageUrl(userInfo.getAvatar_url());
        /** github用户拥有个人主页 */
        values.setProfileUrl(userInfo.getHtml_url());
        /** github没有openid,使用github的id(唯一标识)替代 */
        values.setProviderUserId(userInfo.getId());
    }

    @Override
    public UserProfile fetchUserProfile(GitHub api) {
        return null;
    }

    @Override
    public void updateStatus(GitHub api, String message) {
        /** github do nothing */
    }
}
