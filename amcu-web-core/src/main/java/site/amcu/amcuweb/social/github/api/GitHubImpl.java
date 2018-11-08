package site.amcu.amcuweb.social.github.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

/**
 * @Description:    最终获取github的用户信息
 *                  github用户没有openid的获取机制
 * @Author: Ben-Zheng
 * @Date: 2018/11/07 8:06
 */
public class GitHubImpl extends AbstractOAuth2ApiBinding implements GitHub {

    /** 获取用户的信息(通过access_token & openid获取,access_token会由spring social自动发送) */
    private static final String URL_GET_USERINFO = "https://api.github.com/user";

    /** 即client_id */
    private String appId;

    private ObjectMapper objectMapper = new ObjectMapper();

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public GitHubImpl(String accessToken, String appId) {
        /** 调用该父类方法存入accessToken */
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
        this.appId = appId;
    }

    @Override
    public GitHubUserInfo getUserInfo() {
        String result = this.getRestTemplate().getForObject(URL_GET_USERINFO, String.class);
        logger.info("userinfo result:" + result);

        GitHubUserInfo userInfo = null;
        try {
            userInfo = objectMapper.readValue(result, GitHubUserInfo.class);
            return userInfo;
        } catch (Exception e) {
            throw new RuntimeException("获取用户信息失败", e);
        }

    }

}
