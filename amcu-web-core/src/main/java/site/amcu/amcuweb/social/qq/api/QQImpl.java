package site.amcu.amcuweb.social.qq.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

/**
 * @Description:    qq接口的实现类
 * @Author: Ben-Zheng
 * @Date: 2018/11/05 22:31
 */
public class QQImpl extends AbstractOAuth2ApiBinding implements QQ {

    /** 获取openId的请求 */
    private static final String URL_GET_OPENID = "https://graph.qq.com/oauth2.0/me?access_token=%s";

    /** 获取用户信息的请求 */
    private static final String URL_GET_USERINFO = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";

    /** 系统的配置信息 */
    private String appId;

    /** 通过access_token获取的重要凭证 */
    private String openId;

    private ObjectMapper objectMapper = new ObjectMapper();

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public QQImpl(String accessToken, String appId) {
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
        this.appId = appId;

        String url = String.format(URL_GET_OPENID, accessToken);
        String result = this.getRestTemplate().getForObject(url, String.class);

        logger.info(result);

        this.openId = StringUtils.substringBetween(result, "\"openid\":\"", "\"}");
    }

    @Override
    public QQUserInfo getUserInfo() {
        /** 会自动带上access_token参数 */
        String url = String.format(URL_GET_USERINFO, this.appId, this.openId);
        String result = this.getRestTemplate().getForObject(url, String.class);

        logger.info(result);

        QQUserInfo userInfo = null;
        try {
            userInfo = objectMapper.readValue(result, QQUserInfo.class);
            userInfo.setOpenId(this.openId);
            return userInfo;
        } catch (Exception e) {
            throw new RuntimeException("获取用户信息失败", e);
        }
    }
}
