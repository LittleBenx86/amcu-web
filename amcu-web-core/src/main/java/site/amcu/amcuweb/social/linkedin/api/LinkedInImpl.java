package site.amcu.amcuweb.social.linkedin.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @Description:    最终获取linkedin的用户信息
 * @Author: Ben-Zheng
 * @Date: 2018/11/07 15:54
 */
public class LinkedInImpl extends AbstractOAuth2ApiBinding implements LinkedIn {

    /** 获取用户的信息(通过access_token & openid获取,access_token会由spring social自动发送),linkedin获取的数据可指定 */
    private static final String URL_GET_USERINFO = "https://api.linkedin.com/v1/people/~:(id,formatted-name,picture-url,public-profile-url)?format=json";

    /** 即client_id */
    private String appId;

    /** 获取linkedin返回accessToken */
    private String accessToken;

    private ObjectMapper objectMapper = new ObjectMapper();

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public LinkedInImpl(String accessToken, String appId) {
        /** 调用该父类方法存入accessToken */
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
        this.appId = appId;
        this.accessToken = accessToken;
    }

    @Override
    public LinkedInUserInfo getUserInfo() {

        String result = this.doGet(URL_GET_USERINFO, "Bearer " + this.accessToken);
        logger.info("userinfo result:" + result);

        LinkedInUserInfo userInfo = null;
        try {
            userInfo = objectMapper.readValue(result, LinkedInUserInfo.class);
            return userInfo;
        } catch (Exception e) {
            throw new RuntimeException("获取用户信息失败", e);
        }

    }

    /**
     * linkedin获取用户数据的请求类似于app的发送的get请求,是需要Bearer access_token的机制的
     * 不能使用RestTemplate().postForObject()的方式发送(除非覆盖重新实现)
     * @param userInfoUrl
     * @param authorization
     * @return
     */
    public String doGet(String userInfoUrl, String authorization) {
        String result = "";
        try {
            URL url = new URL(userInfoUrl);
            /** 处理请求头 */
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", authorization);
            connection.connect();

            /** 处理响应数据 */
            try (
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            ) {
                String line;

                while ((line = br.readLine()) != null) {
                    result += line;
                }

            } catch (IOException e) {
                throw new RuntimeException("读取LinkedIn用户数据失败", e);
            }

        } catch (Exception e) {
            logger.error("linkedin请求:" + userInfoUrl);
            logger.error("获取到的用户数据:" + result);
            throw new RuntimeException("读取LinkedIn用户数据失败", e);
        }
        return result;
    }

}
