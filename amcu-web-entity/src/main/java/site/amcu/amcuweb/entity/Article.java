package site.amcu.amcuweb.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description:    用户/管理员发布的文章实体类
 * @Author: Ben-Zheng
 * @Date: 2018/11/13 14:15
 */
@TableName(value = "tbl_name")
public class Article implements Serializable {

    private static final long serialVersionUID = 2268480572307053318L;

    @ApiModelProperty(value = "文章的id")
    @TableId(value = "article_id")
    private Integer id;

    @ApiModelProperty(value = "文章关联的用户id(外键)")
    private Integer userId;

    @ApiModelProperty(value = "文章关联的文章分类id(外键)")
    @TableField(value = "article_category_id")
    private Integer categoryId;

    @ApiModelProperty(value = "文章的标题")
    @TableField(value = "article_title")
    private String title;

    @ApiModelProperty(value = "文章的文本内容")
    @TableField(value = "article_text")
    private String text;

    @ApiModelProperty(value = "用户对文章自定义的标签")
    private String diyCategory;

    /** 文章的审核状态: 2:草稿 1:审核中 3:通过审核 0:审核失败 */
    @ApiModelProperty(value = "文章的审核状态")
    private Integer status;

    @ApiModelProperty(value = "文章的创建时间")
    private Date createTime;

    @ApiModelProperty(value = "文章的修改时间")
    private Date updateTime;

    /***************** setter & getter 是为了自动注入 *********************/

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDiyCategory() {
        return diyCategory;
    }

    public void setDiyCategory(String diyCategory) {
        this.diyCategory = diyCategory;
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", userId=" + userId +
                ", categoryId=" + categoryId +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", diyCategory='" + diyCategory + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
