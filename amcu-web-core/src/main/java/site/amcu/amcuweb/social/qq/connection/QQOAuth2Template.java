package site.amcu.amcuweb.social.qq.connection;

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
 * @Description:    qq符合oauth2协议的连接模版
 * @Author: Ben-Zheng
 * @Date: 2018/11/06 8:25
 */
public class QQOAuth2Template extends OAuth2Template {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public QQOAuth2Template(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl) {
        super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
        /** 默认是false,只有当true的时候才会带上client_id和client_secret */
        this.setUseParametersForClientAuthentication(true);
    }

    /**
     * 使用该模版发送请求获取access_token的授权
     * @param accessTokenUrl
     * @param parameters
     * @return
     */
    @Override
    protected AccessGrant postForAccessGrant(String accessTokenUrl, MultiValueMap<String, String> parameters) {
        /** 获取access_token的响应 */
        String responseStr = this.getRestTemplate().postForObject(accessTokenUrl, parameters, String.class);
        logger.info("access_token的响应:" + responseStr);
        /** QQ互联返回参数的格式解析 */
        String[] items = StringUtils.split(responseStr, "&");
        String accessToken = StringUtils.substringAfterLast(items[0], "=");
        Long expiresIn = new Long(StringUtils.substringAfterLast(items[1], "="));
        String refreshToken = StringUtils.substringAfterLast(items[2], "=");
        return new AccessGrant(accessToken, null, refreshToken, expiresIn);
    }

    /**
     * 创建OAuth2的模版
     * @return
     */
    @Override
    protected RestTemplate createRestTemplate() {
        RestTemplate restTemplate = super.createRestTemplate();
        /** http消息的编码格式设置 */
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        return restTemplate;
    }
}
