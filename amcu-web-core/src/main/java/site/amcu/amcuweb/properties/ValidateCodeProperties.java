package site.amcu.amcuweb.properties;

/**
 * @Description:    用于统一管理所有的验证码配置类
 * @Author: Ben-Zheng
 * @Date: 2018/10/27 21:34
 */
public class ValidateCodeProperties {

    /** 手机验证码配置 */
    private SmsCodeProperties sms = new SmsCodeProperties();

    /** 邮箱验证码配置 */
    private EmailCodeProperties email = new EmailCodeProperties();

    /** 图片验证码配置 */
    private ImageCodeProperties image = new ImageCodeProperties();

    /***************** setter & getter 是为了自动注入 *********************/
    public SmsCodeProperties getSms() {
        return sms;
    }

    public void setSms(SmsCodeProperties sms) {
        this.sms = sms;
    }

    public EmailCodeProperties getEmail() {
        return email;
    }

    public void setEmail(EmailCodeProperties email) {
        this.email = email;
    }

    public ImageCodeProperties getImage() {
        return image;
    }

    public void setImage(ImageCodeProperties image) {
        this.image = image;
    }
}
