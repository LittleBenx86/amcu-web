package site.amcu.amcuweb.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @Description:    文章的额外信息实体类
 *                  浏览数量
 *                  喜欢数量
 *                  收藏数量
 * @Author: Ben-Zheng
 * @Date: 2018/11/13 15:12
 */
@TableName(value = "tbl_article_more")
public class ArticleMore implements Serializable {

    private static final long serialVersionUID = -6338261850439972426L;

    @ApiModelProperty(value = "主键id")
    @TableId(value = "id")
    private Integer id;

    @ApiModelProperty(value = "关联文章的id(外键)")
    private Integer articleId;

    @ApiModelProperty(value = "文章的浏览数量")
    private Integer views;

    @ApiModelProperty(value = "文章的喜欢数量")
    private Integer likes;

    @ApiModelProperty(value = "文章被收藏的数量")
    private Integer collects;

    /***************** setter & getter 是为了自动注入 *********************/

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Integer getCollects() {
        return collects;
    }

    public void setCollects(Integer collects) {
        this.collects = collects;
    }

    @Override
    public String toString() {
        return "ArticleMore{" +
                "id=" + id +
                ", articleId=" + articleId +
                ", views=" + views +
                ", likes=" + likes +
                ", collects=" + collects +
                '}';
    }
}
