package site.amcu.amcuweb.social.coding.api;

import java.util.Date;

/**
 * @Description:     coding第三方登录获取的用户信息实体类
 * @Author: Ben-Zheng
 * @Date: 2018/11/11 18:03
 */
public class CodingUserInfo {

    private String birthday;

    private String location;

    private String company;

    private String slogan;

    private String website;

    /** coding用户的头像 */
    private String avatar;

    /** coding用户的大头像 */
    private String gravatar;

    /** coding用户的小头像 */
    private String lavatar;

    private Date created_at;

    private String name;

    private String name_pinyin;

    private String email;

    /** coding用户的唯一标识(替代openid) */
    private Integer id;

    /** coding用户的个人主页 */
    private String path;

    private String school;

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getGravatar() {
        return gravatar;
    }

    public void setGravatar(String gravatar) {
        this.gravatar = gravatar;
    }

    public String getLavatar() {
        return lavatar;
    }

    public void setLavatar(String lavatar) {
        this.lavatar = lavatar;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName_pinyin() {
        return name_pinyin;
    }

    public void setName_pinyin(String name_pinyin) {
        this.name_pinyin = name_pinyin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    @Override
    public String toString() {
        return "CodingUserInfo{" +
                "birthday='" + birthday + '\'' +
                ", location='" + location + '\'' +
                ", company='" + company + '\'' +
                ", slogan='" + slogan + '\'' +
                ", website='" + website + '\'' +
                ", avatar='" + avatar + '\'' +
                ", gravatar='" + gravatar + '\'' +
                ", lavatar='" + lavatar + '\'' +
                ", created_at=" + created_at +
                ", name='" + name + '\'' +
                ", name_pinyin='" + name_pinyin + '\'' +
                ", email='" + email + '\'' +
                ", id=" + id +
                ", path='" + path + '\'' +
                ", school='" + school + '\'' +
                '}';
    }
}
