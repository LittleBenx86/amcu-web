package site.amcu.amcuweb.properties;

import org.springframework.boot.autoconfigure.social.SocialProperties;

/**
 * @Description:    coding登录所需相关属性配置
 * @Author: Ben-Zheng
 * @Date: 2018/11/11 18:00
 */
public class CodingProperties extends SocialProperties {

    /** 服务提供方的标识 */
    private String providerId = "coding";

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

}
