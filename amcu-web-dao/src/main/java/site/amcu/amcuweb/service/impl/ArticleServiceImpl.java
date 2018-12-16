package site.amcu.amcuweb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import site.amcu.amcuweb.dao.ArticleMapper;
import site.amcu.amcuweb.entity.Article;
import site.amcu.amcuweb.service.ArticleService;
import site.amcu.amcuweb.support.ArticleStatusSupport;
import site.amcu.amcuweb.vo.ArticleVO;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Description:    文章(主)分类数据服务层接口实现
 * @Author: Ben-Zheng
 * @Date: 2018/11/13 14:33
 */
@Service
@Transactional(propagation = Propagation.NESTED, isolation = Isolation.DEFAULT, readOnly = false)
public class ArticleServiceImpl implements ArticleService {

    @Resource
    private ArticleMapper articleMapper;

    @Override
    public int saveArticleAsScratch(Article article) {
        article.setCreateTime(new Date());
        article.setUpdateTime(article.getCreateTime());
        article.setStatus(ArticleStatusSupport.ARTICLE_AS_SCRATCH);
        int i = articleMapper.insert(article);
        return i == 1 ? article.getId() : -1;
    }

    @Override
    public int updateScratch(Article article) {
        article.setUpdateTime(new Date());
        article.setStatus(ArticleStatusSupport.ARTICLE_AS_SCRATCH);
        int i = articleMapper.updateById(article);
        return i == 1 ? article.getId() : -1;
    }

    @Override
    public int saveArticleAndRelease(Article article) {
        article.setCreateTime(new Date());
        article.setUpdateTime(article.getCreateTime());
        article.setStatus(ArticleStatusSupport.ARTICLE_RELEASE_AND_AUDIT);
        int i = articleMapper.insert(article);
        return i == 1 ? article.getId() : -1;
    }

    @Override
    public int updateArticleToRelease(Article article) {
        article.setUpdateTime(new Date());
        article.setStatus(ArticleStatusSupport.ARTICLE_RELEASE_AND_AUDIT);
        int i = articleMapper.updateById(article);
        return i == 1 ? article.getId() : -1;
    }

    @Override
    public Article findArticleByIdAndStatus(Integer articleId, Integer articleStatus) {
        Article a = articleMapper.selectOne(new LambdaQueryWrapper<Article>()
                .eq(Article::getId, articleId)
                .eq(Article::getStatus, articleStatus));
        return a;
    }

    @Override
    public ArticleVO findUserScratchArticleById(Integer articleId) {
        Article a = this.findArticleByIdAndStatus(articleId, ArticleStatusSupport.ARTICLE_AS_SCRATCH);
        ArticleVO vo = new ArticleVO(a, ArticleStatusSupport.ARTICLE_AS_SCRATCH_CONTENT);
        return vo;
    }

    @Override
    public ArticleVO findUserAuditArticleById(Integer articleId) {
        Article a = this.findArticleByIdAndStatus(articleId, ArticleStatusSupport.ARTICLE_RELEASE_AND_AUDIT);
        ArticleVO vo = new ArticleVO(a, ArticleStatusSupport.ARTICLE_RELEASE_AND_AUDIT_CONTENT);
        return vo;
    }

    @Override
    public ArticleVO findUserReleaseArticleById(Integer articleId) {
        Article a = this.findArticleByIdAndStatus(articleId, ArticleStatusSupport.ARTICLE_AUDIT_SUCCESS_AND_IS_ACTIVE);
        ArticleVO vo = new ArticleVO(a, ArticleStatusSupport.ARTICLE_AUDIT_SUCCESS_AND_IS_ACTIVE_CONTENT);
        return vo;
    }

    @Override
    public ArticleVO findUserArticle2ModifyById(Integer articleId) {
        Article a = articleMapper.selectOne(new QueryWrapper<Article>().lambda()
                .eq(Article::getId, articleId)
                .notIn(Article::getStatus, ArticleStatusSupport.ARTICLE_FREEZE, ArticleStatusSupport.ARTICLE_FOR_ADMIN));
        ArticleVO vo = new ArticleVO(a, ArticleStatusSupport.getArticleTypeContent(a.getStatus()));
        return vo;
    }

    @Override
    public Integer findAuthorIdById(Integer articleId) {
        Article a = articleMapper.selectOne(new LambdaQueryWrapper<Article>().eq(Article::getId, articleId));
        return a.getUserId();
    }

}
