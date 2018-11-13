package site.amcu.amcuweb.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description:    用户获取积分记录实体类
 * @Author: Ben-Zheng
 * @Date: 2018/11/13 12:07
 */
@TableName(value = "tbl_user_integration_log")
public class UserIntegrationLog implements Serializable {

    private static final long serialVersionUID = -6475827018511987556L;

    @ApiModelProperty(value = "用户获取积分记录主键")
    @TableId(value = "id")
    private Integer id;

    @ApiModelProperty(value = "关联用户的主键id(外键)")
    private Integer userId;

    /** 积分来源类型 */
    @ApiModelProperty(value = "积分的类型")
    private Integer type;

    /** 本次获取积分的值 */
    @ApiModelProperty(value = "积分的值")
    private Integer integration;

    /** 积分获取的时间 */
    @ApiModelProperty(value = "获取时间")
    private Date obtainTime;

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getIntegration() {
        return integration;
    }

    public void setIntegration(Integer integration) {
        this.integration = integration;
    }

    public Date getObtainTime() {
        return obtainTime;
    }

    public void setObtainTime(Date obtainTime) {
        this.obtainTime = obtainTime;
    }

    @Override
    public String toString() {
        return "UserIntegrationLog{" +
                "id=" + id +
                ", userId=" + userId +
                ", type=" + type +
                ", integration=" + integration +
                ", obtainTime=" + obtainTime +
                '}';
    }
}
