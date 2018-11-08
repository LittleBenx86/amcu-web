package site.amcu.amcuweb.validate.sms;

import org.springframework.stereotype.Component;

import java.sql.SQLOutput;

/**
 * @Description:    开发阶段的短信验证码的发送器
 *                  尚未集成第三方短信服务商
 *                  不需要在此固定配置为@Component
 * @Author: Ben-Zheng
 * @Date: 2018/10/28 9:34
 */
public class DefaultSmsCodeSender implements SmsCodeSender {

    @Override
    public void send(String mobile, String code) {
        System.out.println("向手机:" + mobile + "发送验证码:" + code);
    }
}
