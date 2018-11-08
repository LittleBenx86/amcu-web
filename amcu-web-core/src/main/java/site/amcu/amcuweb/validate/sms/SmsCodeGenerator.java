package site.amcu.amcuweb.validate.sms;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;
import site.amcu.amcuweb.properties.SecurityProperties;
import site.amcu.amcuweb.validate.ValidateCode;
import site.amcu.amcuweb.validate.ValidateCodeGenerator;

import java.util.Random;

/**
 * @Description:    手机验证码生成器实现
 *                  该业务较为固定,可直接配置为@Component
 * @Author: Ben-Zheng
 * @Date: 2018/10/27 22:08
 */
@Component("smsValidateCodeGenerator")
public class SmsCodeGenerator implements ValidateCodeGenerator {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public ValidateCode generate(ServletWebRequest request) {
        String code = RandomStringUtils.randomNumeric(securityProperties.getCode().getSms().getLength());
        return new ValidateCode(code, securityProperties.getCode().getSms().getExpireIn());
    }

    public SecurityProperties getSecurityProperties() {
        return securityProperties;
    }

    public void setSecurityProperties(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }
}
