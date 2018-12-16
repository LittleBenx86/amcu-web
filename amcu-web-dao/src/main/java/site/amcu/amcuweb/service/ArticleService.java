package site.amcu.amcuweb.service;

import site.amcu.amcuweb.entity.Article;
import site.amcu.amcuweb.vo.ArticleVO;

/**
 * @Description:    文章数据服务层接口
 * @Author: Ben-Zheng
 * @Date: 2018/11/13 14:32
 */
public interface ArticleService {

    /**
     * 文章存为草稿
     * @param article
     * @return
     */
    int saveArticleAsScratch(Article article);

    /**
     * 文章存为草稿后继续更新草稿
     * @param article
     * @return
     */
    int updateScratch(Article article);

    /**
     * 文章(未保存成为过草稿)的首次发布(进行审核)
     * @param article
     * @return
     */
    int saveArticleAndRelease(Article article);

    /**
     * 文章(保存成为过草稿)的首次发布(进行审核)
     * @param article
     * @return
     */
    int updateArticleToRelease(Article article);

    /**
     * 根据文章编号和文章状态查找文章
      * @param articleId
     * @param articleStatus
     * @return
     */
    Article findArticleByIdAndStatus(Integer articleId, Integer articleStatus);

    /**
     * 根据文章编号,查找用户的草稿文章(单篇)
     * @param articleId
     * @return
     */
    ArticleVO findUserScratchArticleById(Integer articleId);

    /**
     * 根据文章编号,查找用户的待审核文章(单篇)
     * @param articleId
     * @return
     */
    ArticleVO findUserAuditArticleById(Integer articleId);

    /**
     * 根据文章编号,查找用户的发布文章(单篇)
     * @param articleId
     * @return
     */
    ArticleVO findUserReleaseArticleById(Integer articleId);

    /**
     * 根据文章编号,查找用户的文章进行修改
     * 被冻结的文章不可修改
     * @param articleId
     * @return
     */
    ArticleVO findUserArticle2ModifyById(Integer articleId);

    /**
     * 查找文章作者的id
     * @param articleId
     * @return
     */
    Integer findAuthorIdById(Integer articleId);

}
