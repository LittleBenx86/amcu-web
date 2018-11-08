package site.amcu.amcuweb.properties;

import org.springframework.boot.autoconfigure.social.SocialProperties;

/**
 * @Description:
 * @Author: Ben-Zheng
 * @Date: 2018/11/06 8:58
 */
public class QQProperties extends SocialProperties {

    /** 服务商的标识 */
    private String providerId = "qq";

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }
}
