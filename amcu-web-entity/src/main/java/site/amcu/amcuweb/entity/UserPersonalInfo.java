package site.amcu.amcuweb.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @Description:    用户个人信息实体类
 * @Author: Ben-Zheng
 * @Date: 2018/11/13 8:33
 */
@TableName(value = "tbl_user_personal_info")
public class UserPersonalInfo implements Serializable {

    private static final long serialVersionUID = -6112312745747909588L;

    /** 主键 */
    @ApiModelProperty(value = "用户信息主键")
    @TableId(value = "info_id")
    private Integer id;

    /** user的关联主键(外键) */
    @ApiModelProperty(value = "关联用户的id(外键)")
    private Integer userId;

    /** 姓(兼容英文名) */
    @ApiModelProperty(value = "姓")
    private String lastname;

    /** 名(兼容英文名) */
    @ApiModelProperty(value = "名")
    private String firstname;

    /** 当前职业 */
    @ApiModelProperty(value = "职业")
    private String curCareer;

    /** 个性签名 */
    @ApiModelProperty(value = "个性签名")
    private String diySignature;

    /** 该用户被关注的数量 */
    @ApiModelProperty(value = "被关注数量")
    private Integer followers;

    /** 该用户的文章数量 */
    @ApiModelProperty(value = "文章数量")
    private Integer articles;

    /** 该用户关注的其他用户的数量 */
    @ApiModelProperty(value = "正在关注数量")
    @TableField(value = "follow_others")
    private Integer followings;

    /** 是否愿意公开个人的信息(姓名,职业) */
    @ApiModelProperty(value = "是否公开个人信息")
    @TableField(value = "is_willing")
    private Integer willing;

    /** 用户的积分 */
    @ApiModelProperty(value = "积分")
    private Integer integration;

    /** 用户的排名 */
    @ApiModelProperty(value = "排名")
    private  Integer rank;

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

    public String getCurCareer() {
        return curCareer;
    }

    public void setCurCareer(String curCareer) {
        this.curCareer = curCareer;
    }

    public String getDiySignature() {
        return diySignature;
    }

    public void setDiySignature(String diySignature) {
        this.diySignature = diySignature;
    }

    public Integer getFollowers() {
        return followers;
    }

    public void setFollowers(Integer followers) {
        this.followers = followers;
    }

    public Integer getArticles() {
        return articles;
    }

    public void setArticles(Integer articles) {
        this.articles = articles;
    }

    public Integer getFollowings() {
        return followings;
    }

    public void setFollowings(Integer followings) {
        this.followings = followings;
    }

    public Integer getWilling() {
        return willing;
    }

    public void setWilling(Integer willing) {
        this.willing = willing;
    }

    public Integer getIntegration() {
        return integration;
    }

    public void setIntegration(Integer integration) {
        this.integration = integration;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "UserPersonalInfo{" +
                "id=" + id +
                ", userId=" + userId +
                ", lastname='" + lastname + '\'' +
                ", firstname='" + firstname + '\'' +
                ", curCareer='" + curCareer + '\'' +
                ", diySignature='" + diySignature + '\'' +
                ", followers=" + followers +
                ", articles=" + articles +
                ", followings=" + followings +
                ", willing=" + willing +
                ", integration=" + integration +
                ", rank=" + rank +
                '}';
    }
}
