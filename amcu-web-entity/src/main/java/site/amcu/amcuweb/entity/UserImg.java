package site.amcu.amcuweb.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @Description:    用户图片展示信息实体类
 * @Author: Ben-Zheng
 * @Date: 2018/11/13 10:59
 */
@TableName(value = "tbl_user_img")
public class UserImg implements Serializable {

    private static final long serialVersionUID = -6873516295956354975L;

    @ApiModelProperty(value = "用户图片编号(主键)")
    @TableId(value = "img_id")
    private Integer id;

    @ApiModelProperty(value = "关联用户的id(外键)")
    private Integer userId;

    @ApiModelProperty(value = "用户图片的展示编号顺序")
    private Integer imgOrder;

    @ApiModelProperty(value = "图片url")
    private String imgUrl;

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

    public Integer getImgOrder() {
        return imgOrder;
    }

    public void setImgOrder(Integer imgOrder) {
        this.imgOrder = imgOrder;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Override
    public String toString() {
        return "UserImg{" +
                "id=" + id +
                ", userId=" + userId +
                ", imgOrder=" + imgOrder +
                ", imgUrl='" + imgUrl + '\'' +
                '}';
    }
}
