package site.amcu.amcuweb.validate.email;

import org.springframework.stereotype.Component;

/**
 * @Description:    邮箱验证码发送器实现
 *                  开发初期:没有集成邮箱服务商的邮件发送,做测试用
 *                  不需要在此固定配置为@Component
 * @Author: Ben-Zheng
 * @Date: 2018/10/28 9:57
 */
public class DefaultEmailCodeSender implements EmailCodeSender {

    @Override
    public void send(String emailAddress, String code) {
        System.out.println("向邮箱:" + emailAddress + "发送验证码:" + code);
    }
}
