package site.amcu.amcuweb.vo;

import site.amcu.amcuweb.entity.Article;

import java.io.Serializable;
import java.util.Map;

/**
 * @Description:    文章对象返回的包装类
 * @Author: Ben-Zheng
 * @Date: 2018/12/14 16:38
 */
public class ArticleVO implements Serializable {

    private Article article;

    /** 文章的状态名称 */
    private String articleStatusContent;

    /** 文章的额外参数 */
    private Map<String, Object> exts;

    public ArticleVO(Article article, String articleStatusContent) {
        this.article = article;
        this.articleStatusContent = articleStatusContent;
    }

    public ArticleVO(Article article, String articleStatusContent, Map<String, Object> exts) {
        this(article, articleStatusContent);
        this.exts = exts;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public String getArticleStatusContent() {
        return articleStatusContent;
    }

    public void setArticleStatusContent(String articleStatusContent) {
        this.articleStatusContent = articleStatusContent;
    }

    public Map<String, Object> getExts() {
        return exts;
    }

    public void setExts(Map<String, Object> exts) {
        this.exts = exts;
    }

    @Override
    public String toString() {
        return "ArticleVO{" +
                "article=" + article +
                ", articleStatusContent='" + articleStatusContent + '\'' +
                ", exts=" + exts +
                '}';
    }
}
