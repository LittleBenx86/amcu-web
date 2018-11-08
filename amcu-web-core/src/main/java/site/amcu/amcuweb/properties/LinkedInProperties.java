package site.amcu.amcuweb.properties;

import org.springframework.boot.autoconfigure.social.SocialProperties;

/**
 * @Description:    LinkedIn登录所需相关属性配置
 * @Author: Ben-Zheng
 * @Date: 2018/11/07 15:27
 */
public class LinkedInProperties extends SocialProperties {

    /** 服务提供方的标识 */
    private String providerId = "linkedin";

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

}
