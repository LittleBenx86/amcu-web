package site.amcu.amcuweb.support;

/**
 * @Description:    用户账户使用状态
 * @Author: Ben-Zheng
 * @Date: 2018/11/12 16:30
 */
public interface UserAccountStatusSupport {

    /** 账号冻结 */
    public final static int ACCOUNT_FREEAED             = 0;

    /** 账号正常使用 */
    public final static int ACCOUNT_ACTIVE              = 1;

    /** 账号的所有者为SCAU的学生 */
    public final static int ACCOUNT_USER_IS_SCAUER      = 2;

    /** 账号的所有者经过了AMCU的实名认证 */
    public final static int ACCOUNT_USER_IS_AMCUER      = 4;

}
