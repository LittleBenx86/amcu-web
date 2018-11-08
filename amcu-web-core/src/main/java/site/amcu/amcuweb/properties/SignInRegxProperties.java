package site.amcu.amcuweb.properties;

/**
 * @Description:    登录(username)正则表达式配置
 * @Author: Ben-Zheng
 * @Date: 2018/11/05 19:34
 */
public class SignInRegxProperties {

    /** 要求username不能部分匹配该正则表达式 */
    private String usernameRegx = "(^[A-Za-z0-9\\u4e00-\\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$)|(^\\d{11}$)";

    private String mobileRegx = "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|166|198|199|(147))\\d{8}$";

    private String emailRegx = "^[A-Za-z0-9\\u4e00-\\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";

    public String getUsernameRegx() {
        return usernameRegx;
    }

    public void setUsernameRegx(String usernameRegx) {
        this.usernameRegx = usernameRegx;
    }

    public String getMobileRegx() {
        return mobileRegx;
    }

    public void setMobileRegx(String mobileRegx) {
        this.mobileRegx = mobileRegx;
    }

    public String getEmailRegx() {
        return emailRegx;
    }

    public void setEmailRegx(String emailRegx) {
        this.emailRegx = emailRegx;
    }
}
