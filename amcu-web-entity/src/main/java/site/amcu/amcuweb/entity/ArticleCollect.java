package site.amcu.amcuweb.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @Description:    文章收藏(文章和收藏者)关联实体类
 * @Author: Ben-Zheng
 * @Date: 2018/11/13 14:39
 */
@TableName(value = "tbl_artcle_collect")
public class ArticleCollect implements Serializable {

    private static final long serialVersionUID = -418457098011250427L;

    @ApiModelProperty(value = "主键id")
    @TableId(value = "id")
    private Integer id;

    @ApiModelProperty(value = "被收藏的文章的id")
    private Integer articleId;

    @ApiModelProperty(value = "收藏者的用户id")
    private Integer userId;

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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "ArticleCollect{" +
                "id=" + id +
                ", articleId=" + articleId +
                ", userId=" + userId +
                '}';
    }
}
