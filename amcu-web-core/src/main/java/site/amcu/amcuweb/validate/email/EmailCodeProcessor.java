package site.amcu.amcuweb.validate.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import site.amcu.amcuweb.properties.SecurityConstants;
import site.amcu.amcuweb.validate.ValidateCode;
import site.amcu.amcuweb.validate.impl.AbstractValidateCodeProcessor;

/**
 * @Description:    邮箱验证码规范处理实现
 * @Author: Ben-Zheng
 * @Date: 2018/10/28 10:00
 */
@Component("emailValidateCodeProcessor")
public class EmailCodeProcessor extends AbstractValidateCodeProcessor<ValidateCode> {

    @Autowired
    private EmailCodeSender emailCodeSender;

    @Override
    protected void send(ServletWebRequest request, ValidateCode validateCode) throws Exception {
        String paramName = SecurityConstants.DEFAULT_PARAMETER_NAME_EMAIL;
        String tarEmail = ServletRequestUtils.getStringParameter(request.getRequest(), paramName);
        emailCodeSender.send(tarEmail, validateCode.getCode());
    }
}
