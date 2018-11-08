package site.amcu.amcuweb.social.github.api;

import java.util.Date;

/**
 * @Description:    github第三方登录获取的用户信息实体类
 * @Author: Ben-Zheng
 * @Date: 2018/11/07 7:48
 */
public class GitHubUserInfo {

    /** github登录的用户名(相当于个性主页) */
    private String login;

    /** github用户的唯一标识 */
    private String id;

    /** github用户的节点id */
    private String node_id;

    /** github用户的头像url */
    private String avatar_url;

    /** 作用不明确 */
    private String gravatar_id;

    /** github用户个人api主页 */
    private String url;

    /** github个人主页 */
    private String html_url;

    /** github个人主页--粉丝 */
    private String followers_url;

    /** github个人主页--正在关注 */
    private String following_url;

    /** 不明确作用 */
    private String gists_url;

    /** 不明确作用 */
    private String starred_url;

    /** 不明确作用 */
    private String subscriptions_url;

    /** 不明确作用 */
    private String organizations_url;

    /** github用户的个人仓库 */
    private String repos_url;

    /** 不明确作用 */
    private String events_url;

    /** 不明确作用 */
    private String received_events_url;

    /** github用户的类型 */
    private String type;

    /** 是否为github的网站管理员 */
    private Boolean site_admin;

    /** github用户的昵称(不同于登录的用户名) */
    private String name;

    /** 所在公司 */
    private String company;

    /** 用户在github上搭建博客的url */
    private String blog;

    /** github用户所在的国家/地区 */
    private String location;

    /** github用户备注的email */
    private String email;

    /** 不明确作用 */
    private String hireable;

    /** github用户的个性签名 */
    private String bio;

    /** github用户的公共仓库的数量 */
    private Integer public_repos;

    /** 不明确作用 */
    private Integer public_gists;

    /** github用户的粉丝数量 */
    private Integer followers;

    /** github用户正在关注其他用户的数量 */
    private Integer following;

    /** github用户的注册日期 */
    private Date created_at;

    /** github用户上次更新信息的时间? */
    private Date updated_at;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNode_id() {
        return node_id;
    }

    public void setNode_id(String node_id) {
        this.node_id = node_id;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getGravatar_id() {
        return gravatar_id;
    }

    public void setGravatar_id(String gravatar_id) {
        this.gravatar_id = gravatar_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    public String getFollowers_url() {
        return followers_url;
    }

    public void setFollowers_url(String followers_url) {
        this.followers_url = followers_url;
    }

    public String getFollowing_url() {
        return following_url;
    }

    public void setFollowing_url(String following_url) {
        this.following_url = following_url;
    }

    public String getGists_url() {
        return gists_url;
    }

    public void setGists_url(String gists_url) {
        this.gists_url = gists_url;
    }

    public String getStarred_url() {
        return starred_url;
    }

    public void setStarred_url(String starred_url) {
        this.starred_url = starred_url;
    }

    public String getSubscriptions_url() {
        return subscriptions_url;
    }

    public void setSubscriptions_url(String subscriptions_url) {
        this.subscriptions_url = subscriptions_url;
    }

    public String getOrganizations_url() {
        return organizations_url;
    }

    public void setOrganizations_url(String organizations_url) {
        this.organizations_url = organizations_url;
    }

    public String getRepos_url() {
        return repos_url;
    }

    public void setRepos_url(String repos_url) {
        this.repos_url = repos_url;
    }

    public String getEvents_url() {
        return events_url;
    }

    public void setEvents_url(String events_url) {
        this.events_url = events_url;
    }

    public String getReceived_events_url() {
        return received_events_url;
    }

    public void setReceived_events_url(String received_events_url) {
        this.received_events_url = received_events_url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getSite_admin() {
        return site_admin;
    }

    public void setSite_admin(Boolean site_admin) {
        this.site_admin = site_admin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getBlog() {
        return blog;
    }

    public void setBlog(String blog) {
        this.blog = blog;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHireable() {
        return hireable;
    }

    public void setHireable(String hireable) {
        this.hireable = hireable;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Integer getPublic_repos() {
        return public_repos;
    }

    public void setPublic_repos(Integer public_repos) {
        this.public_repos = public_repos;
    }

    public Integer getPublic_gists() {
        return public_gists;
    }

    public void setPublic_gists(Integer public_gists) {
        this.public_gists = public_gists;
    }

    public Integer getFollowers() {
        return followers;
    }

    public void setFollowers(Integer followers) {
        this.followers = followers;
    }

    public Integer getFollowing() {
        return following;
    }

    public void setFollowing(Integer following) {
        this.following = following;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }
}
