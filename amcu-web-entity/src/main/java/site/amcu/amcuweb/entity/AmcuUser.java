package site.amcu.amcuweb.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @Description:    用户AMCU成员(认证)的补充的信息实体类
 * @Author: Ben-Zheng
 * @Date: 2018/11/13 10:25
 */
@TableName(value = "tbl_amcu_user")
public class AmcuUser implements Serializable {

    private static final long serialVersionUID = -3857745974700164623L;

    @ApiModelProperty(value = "主键id")
    @TableId(value = "amcu_user_id")
    private Integer id;

    @ApiModelProperty(value = "关联用户的主键(外键)")
    private Integer userId;

    @ApiModelProperty(value = "AMCU部门编号")
    private Integer departNo;

    @ApiModelProperty(value = "AMCU任职分类编号")
    private Integer jobNo;

    /** AMCU的第几届成员 */
    @ApiModelProperty(value = "AMCU的第几届成员")
    private Integer sessionNo;

    @ApiModelProperty(value = "AMCU成员的姓")
    private String lastname;

    @ApiModelProperty(value = "AMCU成员的名")
    private String firstname;

    /** 用户注册后自行填写单片机协会成员的资料,人工审核填写的资料(0:审核不通过, 1:审核通过, 2:审核中) */
    @ApiModelProperty(value = "AMCU成员申请的审核状态")
    private Integer status;

    /** 提供审核的图片证明材料(url) */
    @ApiModelProperty(value = "AMCU成员申请证明图片材料url")
    private String authImg;

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

    public Integer getDepartNo() {
        return departNo;
    }

    public void setDepartNo(Integer departNo) {
        this.departNo = departNo;
    }

    public Integer getJobNo() {
        return jobNo;
    }

    public void setJobNo(Integer jobNo) {
        this.jobNo = jobNo;
    }

    public Integer getSessionNo() {
        return sessionNo;
    }

    public void setSessionNo(Integer sessionNo) {
        this.sessionNo = sessionNo;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAuthImg() {
        return authImg;
    }

    public void setAuthImg(String authImg) {
        this.authImg = authImg;
    }

    @Override
    public String toString() {
        return "AmcuUser{" +
                "id=" + id +
                ", userId=" + userId +
                ", departNo=" + departNo +
                ", jobNo=" + jobNo +
                ", sessionNo=" + sessionNo +
                ", lastname='" + lastname + '\'' +
                ", firstname='" + firstname + '\'' +
                ", status=" + status +
                ", authImg='" + authImg + '\'' +
                '}';
    }
}
