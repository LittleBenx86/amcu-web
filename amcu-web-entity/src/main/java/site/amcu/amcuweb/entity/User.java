package site.amcu.amcuweb.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @Description:    单片机协会网站的用户实体对象
 * @Author: Ben-Zheng
 * @Date: 2018/11/01 20:13
 */
@TableName(value = "tbl_user")
public class User implements UserDetails, Serializable {
    /** 序列化uid */
    private static final long serialVersionUID = -1532523603034496835L;

    /** 用户的主键标识(唯一标识) */
    @ApiModelProperty(value = "用户的编号(主键)")
    @TableId(value = "user_id")
    private Integer id;

    /** 用户的昵称 */
    @ApiModelProperty(value = "用户的昵称")
    private String username;

    /** 用户的登录密码 */
    @ApiModelProperty(value = "用户的密码")
    private String password;

    /** 用户登录的手机号(唯一) */
    @ApiModelProperty(value = "用户的手机号")
    private String mobile;

    /** 用户登录的邮箱号(唯一) */
    @ApiModelProperty(value = "用户的邮箱")
    private String email;

    /** 用户的性别 */
    @ApiModelProperty(value = "用户的性别")
    private Integer gender;

    /** 用户的头像 */
    @ApiModelProperty(value = "用户的头像(url)")
    private String avatar;

    /** 用户的权限角色 */
    @ApiModelProperty(value = "用户的角色(权限)")
    private String role;

    /** 账户的状态 */
    @ApiModelProperty(value = "用户账户的使用状态")
    private Integer status;

    /** 注册日期 */
    @ApiModelProperty(value = "用户的注册日期")
    private Date signupDate;

    /***************** setter & getter 是为了自动注入 *********************/

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getSignupDate() {
        return signupDate;
    }

    public void setSignupDate(Date signupDate) {
        this.signupDate = signupDate;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.commaSeparatedStringToAuthorityList(this.getRole());
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", gender=" + gender +
                ", avatar='" + avatar + '\'' +
                ", role='" + role + '\'' +
                ", status=" + status +
                ", signupDate=" + signupDate +
                '}';
    }
}
