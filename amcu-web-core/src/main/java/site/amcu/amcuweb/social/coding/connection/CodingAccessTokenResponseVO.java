package site.amcu.amcuweb.social.coding.connection;

/**
 * @Description:    coding获取access_token相关的参数
 * @Author: Ben-Zheng
 * @Date: 2018/11/11 21:51
 */
public class CodingAccessTokenResponseVO {

    private Long expires_in;

    private String refresh_token;

    private String access_token;

    public Long getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(Long expires_in) {
        this.expires_in = expires_in;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    @Override
    public String toString() {
        return "CodingAccessTokenResponseVO{" +
                "expires_in=" + expires_in +
                ", refresh_token='" + refresh_token + '\'' +
                ", access_token='" + access_token + '\'' +
                '}';
    }
}
