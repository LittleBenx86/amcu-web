package site.amcu.amcuweb.social.linkedin.api;

/**
 * @Description:    linkedin第三方登录获取的用户信息实体类
 *                  linkedin请求的用户数据可以自行定制
 * @Author: Ben-Zheng
 * @Date: 2018/11/07 15:41
 */
public class LinkedInUserInfo {

    /** linkedin用户的唯一标识(替代openid) */
    private String id;

    /** linked用户用户名(按照对应的语言进行格式化后的) */
    private String formattedName;

    /** linkedin用户的头像url */
    private String pictureUrl;

    /** linkedin用户公开的个人主页url */
    private String publicProfileUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getPublicProfileUrl() {
        return publicProfileUrl;
    }

    public void setPublicProfileUrl(String publicProfileUrl) {
        this.publicProfileUrl = publicProfileUrl;
    }

    public String getFormattedName() {
        return formattedName;
    }

    public void setFormattedName(String formattedName) {
        this.formattedName = formattedName;
    }
}
