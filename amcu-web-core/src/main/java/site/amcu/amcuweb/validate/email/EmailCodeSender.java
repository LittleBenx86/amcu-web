package site.amcu.amcuweb.validate.email;

/**
 * @Description:    邮箱验证码的发送器接口规范
 * @Author: Ben-Zheng
 * @Date: 2018/10/28 9:55
 */
public interface EmailCodeSender {

    /**
     * 需要集成邮箱服务商的邮件发送
     * @param emailAddress  邮箱的地址
     * @param code
     */
    void send(String emailAddress, String code);
}

