package site.amcu.amcuweb.vo;

/**
 * @Description:    用于包装经过oauth2.0认证的第三方用户的信息和当前登录的用户信息
 * @Author: Ben-Zheng
 * @Date: 2018/11/11 9:51
 */
public class SocialUserInfoVO {

    /** 第三方用户的信息 */

    private String providerId;

    private String socialNickname;

    private String socialAvatar;

    /** 当前登录的用户信息(绑定操作) */

    private String curUserId;

    private String curUsername;

    private String curUserAvatar;

    private Boolean hasSignIn;

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getSocialNickname() {
        return socialNickname;
    }

    public void setSocialNickname(String socialNickname) {
        this.socialNickname = socialNickname;
    }

    public String getSocialAvatar() {
        return socialAvatar;
    }

    public void setSocialAvatar(String socialAvatar) {
        this.socialAvatar = socialAvatar;
    }

    public String getCurUserId() {
        return curUserId;
    }

    public void setCurUserId(String curUserId) {
        this.curUserId = curUserId;
    }

    public String getCurUsername() {
        return curUsername;
    }

    public void setCurUsername(String curUsername) {
        this.curUsername = curUsername;
    }

    public String getCurUserAvatar() {
        return curUserAvatar;
    }

    public void setCurUserAvatar(String curUserAvatar) {
        this.curUserAvatar = curUserAvatar;
    }

    public Boolean getHasSignIn() {
        return hasSignIn;
    }

    public void setHasSignIn(Boolean hasSignIn) {
        this.hasSignIn = hasSignIn;
    }

    @Override
    public String toString() {
        return "SocialUserInfoVO{" +
                "providerId='" + providerId + '\'' +
                ", socialNickname='" + socialNickname + '\'' +
                ", socialAvatar='" + socialAvatar + '\'' +
                ", curUserId='" + curUserId + '\'' +
                ", curUsername='" + curUsername + '\'' +
                ", curUserAvatar='" + curUserAvatar + '\'' +
                ", hasSignIn=" + hasSignIn +
                '}';
    }
}
