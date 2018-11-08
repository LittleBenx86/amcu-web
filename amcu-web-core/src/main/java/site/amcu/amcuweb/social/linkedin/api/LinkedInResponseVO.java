package site.amcu.amcuweb.social.linkedin.api;

/**
 * @Description:    用于接收linkedin返回的access_token和expires_in参数
 * @Author: Ben-Zheng
 * @Date: 2018/11/07 18:11
 */
public class LinkedInResponseVO {

    private String access_token;

    private Long expires_in;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public Long getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(Long expires_in) {
        this.expires_in = expires_in;
    }

    @Override
    public String toString() {
        return "access_token=" + this.access_token + "&expires_in=" + this.expires_in;
    }
}
