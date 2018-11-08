package site.amcu.amcuweb.validate.sms;

/**
 * @Description:    短信验证码的发送器规范
 * @Author: Ben-Zheng
 * @Date: 2018/10/28 9:32
 */
public interface SmsCodeSender {

    /**
     * 短信验证码的发送规范
     * 主要是集成第三方的短信服务商的短信发送服务
     * @param mobile
     * @param code
     */
    void send(String mobile, String code);
}
