package site.amcu.amcuweb.properties;

import org.springframework.boot.autoconfigure.social.SocialProperties;

/**
 * @Description:    github登录所需相关属性配置
 * @Author: Ben-Zheng
 * @Date: 2018/11/07 7:42
 */
public class GitHubProperties extends SocialProperties {

    /** 服务提供方的标识 */
    private String providerId = "github";

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }
}
