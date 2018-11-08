package site.amcu.amcuweb.validate.email;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;
import site.amcu.amcuweb.properties.SecurityProperties;
import site.amcu.amcuweb.validate.ValidateCode;
import site.amcu.amcuweb.validate.ValidateCodeGenerator;

/**
 * @Description:    邮箱验证码生成器实现
 *                  该业务较为固定,可直接配置为@Component
 * @Author: Ben-Zheng
 * @Date: 2018/10/27 22:23
 */
@Component("emailValidateCodeGenerator")
public class EmailCodeGenerator implements ValidateCodeGenerator {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public ValidateCode generate(ServletWebRequest request) {
        String code = RandomStringUtils.randomNumeric(securityProperties.getCode().getEmail().getLength());
        return new ValidateCode(code, securityProperties.getCode().getEmail().getExpireIn());
    }
}
