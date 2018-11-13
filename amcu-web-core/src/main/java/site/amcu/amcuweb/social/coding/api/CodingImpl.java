package site.amcu.amcuweb.social.coding.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

import java.util.Map;

/**
 * @Description:    最终获取coding的用户信息
 *                  coding用户没有openid的获取机制
 * @Author: Ben-Zheng
 * @Date: 2018/11/11 18:28
 */
public class CodingImpl extends AbstractOAuth2ApiBinding implements Coding {

    /** 获取用户的信息(通过access_token & openid获取,access_token会由spring social自动发送) */
    private static final String URL_GET_USERINFO = "https://coding.net/api/account/current_user";

    /** 即client_id */
    private String appId;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public CodingImpl(String accessToken, String appId) {
        /** 调用该父类方法存入accessToken */
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
        this.appId = appId;
    }

    @Override
    public CodingUserInfo getUserInfo() {
        String result = this.getRestTemplate().getForObject(URL_GET_USERINFO, String.class);
        result = StringUtils.substringBetween(result, "\"data\":", "}}") + "}";
        logger.info("userinfo result:" + result);

        CodingUserInfo userInfo = new CodingUserInfo();
        try {
            Map<String, Object> vars = JSON.parseObject(result);
            userInfo.setAvatar(vars.get("avatar").toString());
            userInfo.setGravatar(vars.get("gravatar").toString());
            userInfo.setLavatar(vars.get("lavatar").toString());
            userInfo.setName(vars.get("name").toString());
            userInfo.setCompany(vars.get("company").toString());
            userInfo.setPath("https://coding.net" + vars.get("path").toString());
            userInfo.setSlogan(vars.get("slogan").toString());
            userInfo.setId(Integer.parseInt(vars.get("id").toString()));
            userInfo.setSchool(vars.get("school").toString());
            userInfo.setLocation(((JSONObject) vars).getString("location"));
            return userInfo;
        } catch (Exception e) {
            throw new RuntimeException("获取用户信息失败", e);
        }

    }

}
