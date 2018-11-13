package site.amcu.amcuweb.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description:    文章评论实体类
 * @Author: Ben-Zheng
 * @Date: 2018/11/13 15:26
 */
@TableName(value = "tbl_article_comment")
public class ArticleComment implements Serializable {

    private static final long serialVersionUID = 6700779819326329619L;

    @ApiModelProperty(value = "评论的主键id")
    @TableId(value = "comment_id")
    private Integer id;

    @ApiModelProperty(value = "关联文章的id(外键)")
    private Integer articleId;

    @ApiModelProperty(value = "关联用户的id(外键)")
    private Integer userId;

    @ApiModelProperty(value = "文章评论的正文")
    private String commentText;

    /** 文章评论的审核状态 */
    @ApiModelProperty(value = "文章评论的审核状态")
    private Integer status;

    /** 文章评论的创建时间 */
    @ApiModelProperty(value = "文章评论的创建时间")
    private Date createTime;

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

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "ArticleComment{" +
                "id=" + id +
                ", articleId=" + articleId +
                ", userId=" + userId +
                ", commentText='" + commentText + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                '}';
    }
}
