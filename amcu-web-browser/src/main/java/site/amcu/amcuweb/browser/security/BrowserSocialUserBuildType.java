package site.amcu.amcuweb.browser.security;

/**
 * @Description:    用于区分username/userId的创建用户的方式以及账号绑定或者创建(对单回调地址的应用而言)
 *                  form:username
 *                  social:userId
 * @Author: Ben-Zheng
 * @Date: 2018/11/09 23:49
 */
public interface BrowserSocialUserBuildType {

    /** form表单登录的用户 */
    public static final int BUIL_USER_TYPE_FORM     = 1;

    /** 通过oauth授权的第三方登录(默认是这个,为了绑定账户所必需的) */
    public static final int BUIL_USER_TYPE_SOCIAL   = 2;

    /** (github单回调地址oauth应用)首次登录创建新用户 */
    public static final int CREATE_NEW_USER_BY_SOCIAL = 1;

    /** (github单回调地址oauth应用)首次登录绑定已存在的账户 */
    public static final int BINDING_USER_ON_EXISTS    = 2;

}
