package site.amcu.amcuweb.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @Description:    网站文章分类实体类
 * @Author: Ben-Zheng
 * @Date: 2018/11/13 12:22
 */
@TableName(value = "tbl_article_category")
public class ArticleCategory implements Serializable {

    private static final long serialVersionUID = -2069726837374383341L;

    @ApiModelProperty(value = "文章分类主键id")
    @TableId(value = "article_category_id")
    private Integer id;

    @ApiModelProperty(value = "分类名称")
    @TableField(value = "article_name")
    private String name;

    /***************** setter & getter 是为了自动注入 *********************/

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ArticleCategory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
