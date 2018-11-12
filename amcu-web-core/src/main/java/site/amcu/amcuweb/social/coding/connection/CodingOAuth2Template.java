package site.amcu.amcuweb.social.coding.connection;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jndi.toolkit.url.Uri;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.StringUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.xml.transform.Source;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @Description:    coding符合oauth2协议的连接模版
 * @Author: Ben-Zheng
 * @Date: 2018/11/11 18:18
 */
public class CodingOAuth2Template extends OAuth2Template {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public final static String CODING_SCOPE = "user";

    private String authorizeUrl;

    private ObjectMapper objectMapper = new ObjectMapper();

    public CodingOAuth2Template(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl) {
        super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
        /** 默认是false,只有当true的时候才会带上client_id和client_secret */
        this.setUseParametersForClientAuthentication(true);
        this.authorizeUrl = authorizeUrl + "?client_id=" + this.formEncode(clientId) + "&scope=" + CODING_SCOPE;
    }

    @Override
    public String buildAuthorizeUrl(GrantType grantType, OAuth2Parameters parameters) {
        return this.buildAuthUrl(this.authorizeUrl, grantType, parameters);
    }

    @Override
    public String buildAuthenticateUrl(OAuth2Parameters parameters) {
        return this.buildAuthorizeUrl(GrantType.AUTHORIZATION_CODE, parameters);
    }

    private String buildAuthUrl(String baseAuthUrl, GrantType grantType, OAuth2Parameters parameters) {
        StringBuilder authUrl = new StringBuilder(baseAuthUrl);
        if (grantType == GrantType.AUTHORIZATION_CODE) {
            authUrl.append('&').append("response_type").append('=').append("code");
        } else if (grantType == GrantType.IMPLICIT_GRANT) {
            authUrl.append('&').append("response_type").append('=').append("token");
        }
        for (Iterator<Map.Entry<String, List<String>>> additionalParams = parameters.entrySet().iterator(); additionalParams.hasNext();) {
            Map.Entry<String, List<String>> param = additionalParams.next();
            String name = formEncode(param.getKey());
            for (Iterator<String> values = param.getValue().iterator(); values.hasNext();) {
                authUrl.append('&').append(name);
                String value = values.next();
                if (StringUtils.hasLength(value)) {
                    authUrl.append('=').append(formEncode(value));
                }
            }
        }
        return authUrl.toString();
    }

    @Override
    protected AccessGrant postForAccessGrant(String accessTokenUrl, MultiValueMap<String, String> parameters) {

        String response = this.getAccessToken(accessTokenUrl,
                parameters.get("client_id").toString().replace("[", "").replace("]", ""),
                parameters.get("client_secret").toString().replace("[", "").replace("]", ""),
                parameters.get("code").toString().replace("[", "").replace("]", ""),
                parameters.get("redirect_uri").toString().replace("[", "").replace("]", ""),
                parameters.get("grant_type").toString().replace("[", "").replace("]", ""));
        CodingAccessTokenResponseVO responseVO = null;
        try {
            responseVO = objectMapper.readValue(response, CodingAccessTokenResponseVO.class);
        } catch (IOException e) {
            logger.error("读取response数据失败:" + e);
        }
        /** coding获取access_token的格式 */
        return new AccessGrant(responseVO.getAccess_token(), CODING_SCOPE, responseVO.getRefresh_token(), responseVO.getExpires_in());
    }

    private String formEncode(String data) {
        try {
            return URLEncoder.encode(data, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            throw new IllegalStateException(ex);
        }
    }

    public String getAccessToken(String accessTokenUrl, String clientId, String clientSecret, String code, String redirectUrl, String grantType) {
        StringBuilder accessTokenRequestUrl = new StringBuilder(accessTokenUrl);

        /** 省略redirect_url,其实可以不用发送 */
        accessTokenRequestUrl.append("?client_id=" + clientId);
        accessTokenRequestUrl.append("&client_secret=" + clientSecret);
        accessTokenRequestUrl.append("&grant_type=" + grantType);
        accessTokenRequestUrl.append("&code=" + code);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(accessTokenRequestUrl.toString());
        URI uri = builder.build().encode().toUri();
        String response = this.createRestTemplate().getForObject(uri, String.class);
        logger.info("getAccessToken Response:" + response);
        return response;
    }

    @Override
    protected RestTemplate createRestTemplate() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setReadTimeout(120000);
        List<HttpMessageConverter<?>> messageConverters = new LinkedList<>();
        messageConverters.add(new ByteArrayHttpMessageConverter());
        messageConverters.add(new StringHttpMessageConverter(StandardCharsets.UTF_8));
        messageConverters.add(new ResourceHttpMessageConverter());
        messageConverters.add(new SourceHttpMessageConverter<Source>());
        messageConverters.add(new AllEncompassingFormHttpMessageConverter());
        messageConverters.add(new MappingJackson2HttpMessageConverter());
        RestTemplate restTemplate = new RestTemplate(messageConverters);
        restTemplate.setRequestFactory(requestFactory);
        return restTemplate;
    }


}
