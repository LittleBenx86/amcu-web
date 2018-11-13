package site.amcu.amcuweb.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description:    回复评论实体类
 *                  对评论的回复
 *                  对评论回复的回复
 * @Author: Ben-Zheng
 * @Date: 2018/11/13 15:44
 */
@TableName(value = "tbl_comment_reply")
public class CommentReply implements Serializable {

    private static final long serialVersionUID = 7563725381650752615L;

    @ApiModelProperty(value = "回复评论的id")
    @TableId(value = "reply_id")
    private Integer id;

    @ApiModelProperty(value = "关联文章的id(外键)")
    private Integer toArticleId;

    @ApiModelProperty(value = "关联文章下的评论的id(外键)")
    private Integer toCommentId;

    @ApiModelProperty(value = "关联发起回复的用户的id(外键)")
    private Integer replyUserId;

    @ApiModelProperty(value = "关联回复的id")
    private Integer toReplyId;

    @ApiModelProperty(value = "回复的正文")
    private String replyText;

    @ApiModelProperty(value = "回复的创建时间")
    private Date createTime;

    @ApiModelProperty(value = "回复的审核状态")
    private Integer status;

    /***************** setter & getter 是为了自动注入 *********************/

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getToArticleId() {
        return toArticleId;
    }

    public void setToArticleId(Integer toArticleId) {
        this.toArticleId = toArticleId;
    }

    public Integer getToCommentId() {
        return toCommentId;
    }

    public void setToCommentId(Integer toCommentId) {
        this.toCommentId = toCommentId;
    }

    public Integer getReplyUserId() {
        return replyUserId;
    }

    public void setReplyUserId(Integer replyUserId) {
        this.replyUserId = replyUserId;
    }

    public Integer getToReplyId() {
        return toReplyId;
    }

    public void setToReplyId(Integer toReplyId) {
        this.toReplyId = toReplyId;
    }

    public String getReplyText() {
        return replyText;
    }

    public void setReplyText(String replyText) {
        this.replyText = replyText;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "CommentReply{" +
                "id=" + id +
                ", toArticleId=" + toArticleId +
                ", toCommentId=" + toCommentId +
                ", replyUserId=" + replyUserId +
                ", toReplyId=" + toReplyId +
                ", replyText='" + replyText + '\'' +
                ", createTime=" + createTime +
                ", status=" + status +
                '}';
    }
}
