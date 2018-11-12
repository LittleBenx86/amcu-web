package site.amcu.amcuweb.social.github.connection;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;

import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;


/**
 * @Description:    github符合oauth2协议的连接模版
 * @Author: Ben-Zheng
 * @Date: 2018/11/07 8:40
 */
public class GitHubOAuth2Template extends OAuth2Template {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    public GitHubOAuth2Template(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl) {
        super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
        /** 默认是false,只有当true的时候才会带上client_id和client_secret */
        this.setUseParametersForClientAuthentication(true);
    }

    @Override
    protected AccessGrant postForAccessGrant(String accessTokenUrl, MultiValueMap<String, String> parameters) {
        String responseStr = this.getRestTemplate().postForObject(accessTokenUrl, parameters, String.class);
        logger.info("authorize result(access_token):" + responseStr);
        /** github获取access_token的格式 */
        String[] items = StringUtils.split(responseStr, "&");
        String accessToken = StringUtils.substringAfterLast(items[0], "=");
        String tokenType = StringUtils.substringAfterLast(items[1], "=");
        return new AccessGrant(accessToken);
    }

    @Override
    protected RestTemplate createRestTemplate() {
        RestTemplate restTemplate = super.createRestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        return restTemplate;
    }

}
