package site.amcu.amcuweb.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Description:    管理整个Security安全框架的所有配置(可在配置文件application.yml中赋值管理)
 * @Author: Ben-Zheng
 * @Date: 2018/10/27 21:41
 */
@ConfigurationProperties(prefix = "sys.properties")
public class SecurityProperties {

    /** 验证码的核心配置 */
    private ValidateCodeProperties code = new ValidateCodeProperties();

    /** 浏览器的核心配置 */
    private BrowserProperties browser = new BrowserProperties();

    /** 登录校验正则表达式核心配置 */
    private SignInRegxProperties signInRegx = new SignInRegxProperties();

    /** 第三方登录的核心配置 */
    private SocialsProperties social = new SocialsProperties();

    /***************** setter & getter 是为了自动注入 *********************/

    public ValidateCodeProperties getCode() {
        return code;
    }

    public void setCode(ValidateCodeProperties code) {
        this.code = code;
    }

    public BrowserProperties getBrowser() {
        return browser;
    }

    public void setBrowser(BrowserProperties browser) {
        this.browser = browser;
    }

    public SignInRegxProperties getSignInRegx() {
        return signInRegx;
    }

    public void setSignInRegx(SignInRegxProperties signInRegx) {
        this.signInRegx = signInRegx;
    }

    public SocialsProperties getSocial() {
        return social;
    }

    public void setSocial(SocialsProperties social) {
        this.social = social;
    }
}
