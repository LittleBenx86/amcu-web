package site.amcu.amcuweb.support;

/**
 * @Description:    用户角色常量配置类型
 * @Author: Ben-Zheng
 * @Date: 2018/11/05 16:31
 */
public interface RoleSupport {

    /** 网站注册用户的默认权限 */
    public final static String DEFAULT_COMMON_USER_ROLE = "ROLE_USER";

    /** 网站管理员默认普通权限 */
    public final static String DEFAULT_ADMIN_ROLE       = "ROLE_ADMIN";

    /** 网站管理员超级权限 */
    public final static String SUPER_AMDIN_ROLE         = "ROLE_SUPER_ADMIN";

}
