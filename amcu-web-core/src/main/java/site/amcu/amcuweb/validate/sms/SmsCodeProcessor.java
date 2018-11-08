package site.amcu.amcuweb.validate.sms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import site.amcu.amcuweb.properties.SecurityConstants;
import site.amcu.amcuweb.validate.ValidateCode;
import site.amcu.amcuweb.validate.impl.AbstractValidateCodeProcessor;

/**
 * @Description:    短信验证码的处理机制实现
 *                  只需要实现发送机制即可(多态的),其余行为都是重复的
 * @Author: Ben-Zheng
 * @Date: 2018/10/28 9:38
 */
@Component("smsValidateCodeProcessor")
public class SmsCodeProcessor extends AbstractValidateCodeProcessor<ValidateCode> {

    @Autowired
    private SmsCodeSender smsCodeSender;

    @Override
    protected void send(ServletWebRequest request, ValidateCode validateCode) throws Exception {
        String paramName = SecurityConstants.DEFAULT_PARAMETER_NAME_MOBILE;
        String mobile = ServletRequestUtils.getRequiredStringParameter(request.getRequest(), paramName);
        smsCodeSender.send(mobile, validateCode.getCode());
    }

}
