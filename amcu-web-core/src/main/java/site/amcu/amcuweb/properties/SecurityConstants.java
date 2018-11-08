package site.amcu.amcuweb.properties;

/**
 * @Description:    核心安全模块的常量定义接口
 *                  常量包括:
 *                      请求参数名称
 *                      请求url
 * @Author: Ben-Zheng
 * @Date: 2018/10/27 23:05
 */
public interface SecurityConstants {

    /** 图片验证码的http请求携带的参数名称常量 */
    public static final String DEFAULT_PARAMETER_NAME_CODE_IMAGE = "imageCode";

    /** 短信验证码的http请求携带的参数名称常量 */
    public static final String DEFAULT_PARAMETER_NAME_CODE_SMS   = "smsCode";

    /** 邮箱验证码的http请求携带的参数名称常量 */
    public static final String DEFAULT_PARAMETER_NAME_CODE_EMAIL = "emailCode";

    /** 短信验证码的接收手机的http请求携带的参数名称常量 */
    public static final String DEFAULT_PARAMETER_NAME_MOBILE     = "mobile";

    /** 邮箱验证码的接收手机的http请求携带的参数名称常量 */
    public static final String DEFAULT_PARAMETER_NAME_EMAIL      = "email";

    /** 验证码存入session时的前缀 */
    public static final String SESSION_KEY_PREFIX                = "SESSION_KEY_FOR_CODE_";

    /** 验证码请求的前缀 */
    public static final String DEFAULT_VALIDATE_CODE_URL_PREFIX  = "/code";

    /** 阿里巴巴druid后台数据库性能监控管理请求前缀 */
    public static final String DEFAULT_DRUID_URL_PREFIX          = "/druid";

    /** 默认的用户名和密码表单登录 */
    public static final String DEFAULT_LOGIN_PROCESSING_URL_FORM = "/auth/form";

    /** 默认的表单注册post请求url */
    public static final String DEFAULT_SIGNUP_PROCESSING_URL_FORM  = "/auth/signup";

    /** 手机验证码登录请求url */
    public static final String DEFAULT_LOGIN_PROCESSING_URL_MOBILE = "/auth/mobile";

    /** 当该请求需要进行身份验证时跳转的url */
    public static final String DEFAULT_UNAUTHENTICATION_URL       = "/auth/require";

    /** web默认的登陆页面 */
    public static final String DEFAULT_LOGIN_PAGE_URL             = "/login.html";

    /** web默认的注册页面 */
    public static final String DEFAULT_AUTH_SIGNUP_PAGE_URL       = "/auth-signup.html";

    /** 用户第三方登录(未注册需要前往的注册页面[optional]) */
    public static final String DEFAULT_SOCIAL_SIGNUP_URL          = "/social-signup.html";
}
