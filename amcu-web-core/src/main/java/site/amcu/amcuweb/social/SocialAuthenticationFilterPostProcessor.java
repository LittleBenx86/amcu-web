package site.amcu.amcuweb.social;

import org.springframework.social.security.SocialAuthenticationFilter;

/**
 * @Description:    filterProcessesUrl回调地址处理接口规范
 * @Author: Ben-Zheng
 * @Date: 2018/11/06 9:36
 */
public interface SocialAuthenticationFilterPostProcessor {

    /**
     * fiterProcessesUrl处理接口(主要是app的)
     * @param socialAuthenticationFilter
     */
    void process(SocialAuthenticationFilter socialAuthenticationFilter);

}
