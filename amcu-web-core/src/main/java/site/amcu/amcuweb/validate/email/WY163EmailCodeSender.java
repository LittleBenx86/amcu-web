package site.amcu.amcuweb.validate.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

/**
 * @Description:    网易163邮箱验证码发送器
 * @Author: Ben-Zheng
 * @Date: 2018/11/22 7:36
 */
public class WY163EmailCodeSender implements EmailCodeSender {

    @Value("${spring.mail.sys-config.host-addr}")
    private String from;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    JavaMailSender sender;

    @Override
    public void send(String toEmail, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(this.from);
        message.setTo(toEmail);
        /** 邮件标题 */
        message.setSubject("AMCU网站邮箱验证码");
        /** 邮件内容 */
        message.setText("AMCU提醒您:请保管好以下验证,不要泄露给他人!\n您的验证码为:" + code);
        /** 发送邮件 */
        try {
            this.sender.send(message);
        } catch (MailException e) {
            logger.error("邮件发送失败:" + e);
        }
        logger.info("邮件已经发送成功");
    }
}
