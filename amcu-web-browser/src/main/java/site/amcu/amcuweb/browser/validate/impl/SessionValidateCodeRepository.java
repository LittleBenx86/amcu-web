package site.amcu.amcuweb.browser.validate.impl;

import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;
import site.amcu.amcuweb.properties.SecurityConstants;
import site.amcu.amcuweb.validate.ValidateCode;
import site.amcu.amcuweb.validate.ValidateCodeRepository;
import site.amcu.amcuweb.validate.ValidateCodeType;

/**
 * @Description:    浏览器(Browser)验证码存储的实现类
 * @Author: Ben-Zheng
 * @Date: 2018/10/27 23:27
 */
@Component
public class SessionValidateCodeRepository implements ValidateCodeRepository {

    /** 操作session的工具类 */
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    /**
     * 构建存入session的key的名称
     * @param request
     * @param type
     * @return
     */
    private String getSessionKey(ServletWebRequest request, ValidateCodeType type) {
        return SecurityConstants.SESSION_KEY_PREFIX + type.toString().toUpperCase();
    }

    @Override
    public void save(ServletWebRequest request, ValidateCode code, ValidateCodeType type) {
        sessionStrategy.setAttribute(request, this.getSessionKey(request, type), code);
    }

    @Override
    public ValidateCode get(ServletWebRequest request, ValidateCodeType type) {
        return (ValidateCode) sessionStrategy.getAttribute(request, this.getSessionKey(request, type));
    }

    @Override
    public void remove(ServletWebRequest request, ValidateCodeType type) {
        sessionStrategy.removeAttribute(request, this.getSessionKey(request, type));
    }
}
