package site.amcu.amcuweb.social.wechat.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

import java.nio.charset.Charset;
import java.util.List;

/**
 * @Description:
 * @Author: Ben-Zheng
 * @Date: 2018/11/06 20:09
 */
public class WeChatImpl extends AbstractOAuth2ApiBinding implements WeChat {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private ObjectMapper objectMapper = new ObjectMapper();

    /** 通过openid请求微信用户信息 */
    private static final String URL_GET_USER_INFO = "https://api.weixin.qq.com/sns/userinfo?openid=";

    public WeChatImpl(String accessToken) {
        /** 令牌携带参数 */
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
    }

    @Override
    public WeChatUserInfo getUserInfo(String openId) {
        String url = URL_GET_USER_INFO + openId;
        String response = this.getRestTemplate().getForObject(url, String.class);
        if(StringUtils.contains(response, "errcode")) {
            return null;
        }

        WeChatUserInfo profile = null;

        try {
            profile = objectMapper.readValue(response, WeChatUserInfo.class);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return profile;
    }

    /**
     * 默认注册的StringHttpMessageConverter字符集为ISO-8859-1，而微信返回的是UTF-8的，所以覆盖原来的方法
     * @return
     */
    protected List<HttpMessageConverter<?>> getMessageConverters(){
        List<HttpMessageConverter<?>> messageConverters = super.getMessageConverters();
        /** 去除父类默认的第一个HttpMessageConverter<?>的编码格式ISO-8859-1 */
        messageConverters.remove(0);
        messageConverters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        return messageConverters;
    }
}
