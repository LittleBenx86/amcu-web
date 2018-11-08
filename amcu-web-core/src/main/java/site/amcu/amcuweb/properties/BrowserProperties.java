package site.amcu.amcuweb.properties;

/**
 * @Description:    浏览器相关属性的配置
 *                  登录页
 *                  数据返回类型
 *                  token的记录时间(用于自动登陆)
 *                  session管理
 *                  注册页面管理
 * @Author: Ben-Zheng
 * @Date: 2018/11/02 15:50
 */
public class BrowserProperties {

    /** 配置数据的返回类型(默认返回json) */
    private ResponseType responseType = ResponseType.JSON;

    /** 默认的登录页面 */
    private String loginPage = SecurityConstants.DEFAULT_LOGIN_PAGE_URL;

    /** 默认的表单注册页面 */
    private String signUpPage = SecurityConstants.DEFAULT_AUTH_SIGNUP_PAGE_URL;

    /** 网站登录的默认记住登录的时间长度:6h */
    private int rememberMeSeconds = 6 * 60 * 60;

    /** 用户第三方登录(未注册需要前往的注册页面[optional]) */
    private String signUpUrl = SecurityConstants.DEFAULT_SOCIAL_SIGNUP_URL;

    public ResponseType getResponseType() {
        return responseType;
    }

    public void setResponseType(ResponseType responseType) {
        this.responseType = responseType;
    }

    public String getLoginPage() {
        return loginPage;
    }

    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }

    public int getRememberMeSeconds() {
        return rememberMeSeconds;
    }

    public void setRememberMeSeconds(int rememberMeSeconds) {
        this.rememberMeSeconds = rememberMeSeconds;
    }

    public String getSignUpPage() {
        return signUpPage;
    }

    public void setSignUpPage(String signUpPage) {
        this.signUpPage = signUpPage;
    }

    public String getSignUpUrl() {
        return signUpUrl;
    }

    public void setSignUpUrl(String signUpUrl) {
        this.signUpUrl = signUpUrl;
    }
}
