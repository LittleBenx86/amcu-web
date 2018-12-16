package site.amcu.amcuweb.support;

import java.io.Serializable;

/**
 * @Description:    文章发表状态类型
 * @Author: Ben-Zheng
 * @Date: 2018/12/14 15:39
 */
public final class ArticleStatusSupport {

    /** 草稿状态 */
    public final static int ARTICLE_AS_SCRATCH = 1;

    public final static String ARTICLE_AS_SCRATCH_CONTENT = "草稿";

    /** 正在发布状态,待审核 */
    public final static int ARTICLE_RELEASE_AND_AUDIT = 2;

    public final static String ARTICLE_RELEASE_AND_AUDIT_CONTENT = "待审核";

    /** 审核通过,处于正常状态 */
    public final static int ARTICLE_AUDIT_SUCCESS_AND_IS_ACTIVE = 3;

    public final static String ARTICLE_AUDIT_SUCCESS_AND_IS_ACTIVE_CONTENT = "正常/审核通过";

    /** 审核失败 */
    public final static int ARTICLE_AUDIT_FAILED = 4;

    public final static String ARTICLE_AUDIT_FAILED_CONTENT = "审核失败";

    /** 冻结不可见,文章封禁 */
    public final static int ARTICLE_FREEZE = 5;

    public final static String ARTICLE_FREEZE_CONTENT = "违规禁止";

    /** 管理员(系统)文章 */
    public final static int ARTICLE_FOR_ADMIN = 6;

    public final static String ARTICLE_FOR_ADMIN_CONTENT = "系统文章";

    public static String getArticleTypeContent(int status) {
        String content = "";
        switch(status) {
            case ARTICLE_AS_SCRATCH :
                content = ARTICLE_AS_SCRATCH_CONTENT;
                break;
            case ARTICLE_RELEASE_AND_AUDIT:
                content = ARTICLE_RELEASE_AND_AUDIT_CONTENT;
                break;
            case ARTICLE_AUDIT_SUCCESS_AND_IS_ACTIVE :
                content = ARTICLE_AUDIT_SUCCESS_AND_IS_ACTIVE_CONTENT;
                break;
            case ARTICLE_AUDIT_FAILED:
                content = ARTICLE_AUDIT_FAILED_CONTENT;
                break;
            case ARTICLE_FREEZE :
                content = ARTICLE_FREEZE_CONTENT;
                break;
            case ARTICLE_FOR_ADMIN:
                content = ARTICLE_FOR_ADMIN_CONTENT;
                break;
        }
        return content;
    }

}
