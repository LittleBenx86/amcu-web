package site.amcu.amcuweb.support;

/**
 * @Description:    用户(表单)登录的类型
 * @Author: Ben-Zheng
 * @Date: 2018/11/05 16:54
 */
public interface SignInTypeSupport {

    /** 用户名登录类型 */
    public static final int FORM_USERNAME_SIGNIN_TYPE    = 1;

    /** 用户手机号登录类型 */
    public static final int FORM_USER_MOBILE_SIGNIN_TYPE = 2;

    /** 用户邮箱登录类型 */
    public static final int FORM_USER_EMAIL_SIGNIN_TYPE  = 3;
}
