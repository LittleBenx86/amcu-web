package site.amcu.amcuweb.support;

/**
 * @Description:    用户积分(声望)获取类型
 * @Author: Ben-Zheng
 * @Date: 2018/12/09 18:34
 */
public interface UserIntegrationTypeSupport {

    /** 首次注册成为网站成员 */
    public final static int FIRST_SIGNUP_WEB = 1;

    /** 发表一篇文章 : 积分 +6 */
    public final static int WRITE_AN_ARTICLE = 2;

    /** 发表一条评论 : 积分 +2 */
    public final static int WRITE_A_COMMENT = 3;

    /** 对评论进行回复 : 积分 +1 */
    public final static int WRITE_A_REPLY_TO_COMMENT_OR_REPLY = 4;

    /** 文章得到一条评论 : 积分 +1 */
    public final static int ARTICLE_OBTAIN_A_COMMENT = 5;

    /** 文章获得一次点赞 : 积分 +1 */
    public final static int ARTICLE_OBTAIN_A_VOTE = 6;

    /** 文章获得一次收藏  : 积分 +1 */
    public final static int ARTICLE_OBTAIN_A_COLLECT = 7;

    /** 文章每获得100浏览量  : 积分 +2 */
    public final static int ARTICLE_PER_OBTAIN_100_VIEWS = 8;

    /** 文章删除 : 积分 -6 还要减去点赞/收藏/浏览量获得的积分*/
    public final static int ARTICLE_DELETE = 9;

    /** 文章点赞删除  : 积分 -1 */
    public final static int ARTICLE_VOTE_DEL = 10;

    /** 文章收藏删除  : 积分 -1 */
    public final static int ARTICLE_COLLECT_DEL = 11;

}
