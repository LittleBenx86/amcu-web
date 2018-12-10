package site.amcu.amcuweb.support;

/**
 * @Description:    积分类型对应的值
 * @Author: Ben-Zheng
 * @Date: 2018/12/09 21:05
 */
public enum UserIntegrationTypeValueSupport {

    /** 首次注册成为网站成员 : 积分 +10 */
    FIRST_SIGNUP_WEB {
        @Override
        public int getIntegrationsValue() {
            return 10;
        }
    },

    /** 发表一篇文章 : 积分 +6 */
    WRITE_AN_ARTICLE {
        @Override
        public int getIntegrationsValue() {
            return 6;
        }
    },

    /** 发表一条评论 : 积分 +2 */
    WRITE_A_COMMENT {
        @Override
        public int getIntegrationsValue() {
            return 2;
        }
    },

    /** 对评论进行回复 : 积分 +1 */
    WRITE_A_REPLY_TO_COMMENT_OR_REPLY {
        @Override
        public int getIntegrationsValue() {
            return 1;
        }
    },

    /** 文章得到一条评论 : 积分 +1 */
    ARTICLE_OBTAIN_A_COMMENT {
        @Override
        public int getIntegrationsValue() {
            return 1;
        }
    },

    /** 文章获得一次点赞 : 积分 +1 */
   ARTICLE_OBTAIN_A_VOTE {
        @Override
        public int getIntegrationsValue() {
            return 1;
        }
    },

    /** 文章获得一次收藏  : 积分 +1 */
   ARTICLE_OBTAIN_A_COLLECT {
        @Override
        public int getIntegrationsValue() {
            return 1;
        }
    },

    /** 文章每获得100浏览量  : 积分 +2 */
    ARTICLE_PER_OBTAIN_100_VIEWS {
        @Override
        public int getIntegrationsValue() {
            return 2;
        }
    },

    /** 文章删除 : 积分 -6 还要减去点赞/收藏/浏览量获得的积分*/
    ARTICLE_DELETE {
        @Override
        public int getIntegrationsValue() {
            return -6;
        }
    },

    /** 文章点赞删除  : 积分 -1 */
    ARTICLE_VOTE_DEL {
        @Override
        public int getIntegrationsValue() {
            return -1;
        }
    },

    /** 文章收藏删除  : 积分 -1 */
    ARTICLE_COLLECT_DEL {
        @Override
        public int getIntegrationsValue() {
            return -1;
        }
    };

    /** 获取类型对应的值 */
    public abstract int getIntegrationsValue();

}
