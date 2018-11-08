package site.amcu.amcuweb.social.linkedin.connection;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import site.amcu.amcuweb.social.linkedin.api.LinkedInResponseVO;

import java.nio.charset.Charset;

/**
 * @Description:    linkedin符合oauth2协议的连接模版
 * @Author: Ben-Zheng
 * @Date: 2018/11/07 16:00
 */
public class LinkedInOAuth2Template extends OAuth2Template {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /** 需要在linkedin developers上开放访问范围(scope optional默认就是r_basicprofile) */
    public static final String LINKEDIN_SCOPE = "r_basicprofile";

    public LinkedInOAuth2Template(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl) {
        super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
        /** 默认是false,只有当true的时候才会带上client_id和client_secret */
        this.setUseParametersForClientAuthentication(true);
    }

    @Override
    protected AccessGrant postForAccessGrant(String accessTokenUrl, MultiValueMap<String, String> parameters) {
        LinkedInResponseVO responseStr = this.getRestTemplate().postForObject(accessTokenUrl, parameters, LinkedInResponseVO.class);
        logger.info("authorize result(access_token):" + responseStr.toString());
        /** linkedin获取access_token & expires_in */
        return new AccessGrant(responseStr.getAccess_token(), LINKEDIN_SCOPE, null, responseStr.getExpires_in());
    }

    @Override
    protected RestTemplate createRestTemplate() {
        RestTemplate restTemplate = super.createRestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        return restTemplate;
    }

}
