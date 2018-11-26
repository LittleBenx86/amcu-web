package site.amcu.amcuweb.validate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import site.amcu.amcuweb.properties.SecurityProperties;
import site.amcu.amcuweb.validate.email.DefaultEmailCodeSender;
import site.amcu.amcuweb.validate.email.EmailCodeSender;
import site.amcu.amcuweb.validate.email.LocalEmailCodeSender;
import site.amcu.amcuweb.validate.email.WY163EmailCodeSender;
import site.amcu.amcuweb.validate.image.ImageCodeGenerator;
import site.amcu.amcuweb.validate.sms.DefaultSmsCodeSender;
import site.amcu.amcuweb.validate.sms.SmsCodeSender;

/**
 * @Description:    用于管理验证码的生成器和发送器的Bean建立(加入spring管理,用于自动注入)
 * @Author: Ben-Zheng
 * @Date: 2018/10/28 10:44
 */
@Configuration
public class ValidateCodeBeanConfig {

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 由于图片验证码的产生方式是多态的,不好直接配置@Component
     * 如果有一个新的图片验证码类实现了ValidateCodeGenerator并@Component注解为imageValidateCodeGenerator
     * 下面该Bean类不会起作用
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(name = "imageValidateCodeGenerator")
    public ValidateCodeGenerator imageValidateCodeGenerator() {
        ImageCodeGenerator generator = new ImageCodeGenerator();
        generator.setSecurityProperties(securityProperties);
        return generator;
    }

    /** 配置短信验证码发送器的Bean,比@Component的组件配置灵活 */
    @Bean
    @ConditionalOnMissingBean(name = "smsCodeSender")
    public SmsCodeSender smsCodeSender() {
        /** 可替换不同的发送器实现,以维护或替换 */
        return new DefaultSmsCodeSender();
    }

    /** 配置邮箱验证码发送器的Bean,比@Component的组件配置灵活 */
    @Bean
    @ConditionalOnMissingBean(name = "emailCodeSender")
    public EmailCodeSender emailCodeSender() {
        /** 可替换不同的发送器实现,以维护或替换 */
        return new WY163EmailCodeSender();
    }

}
