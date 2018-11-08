package site.amcu.amcuweb.properties;

/**
 * @Description:    管理第三方登录的Properties
 * @Author: Ben-Zheng
 * @Date: 2018/11/06 9:04
 */
public class SocialsProperties {

    private QQProperties qq = new QQProperties();

    private WeChatProperties wechat = new WeChatProperties();

    private GitHubProperties github = new GitHubProperties();

    private LinkedInProperties linkedin = new LinkedInProperties();

    /** 本系统使用第三方登录共用的回调地址 */
    private String filterProcessesUrl = "/auth";

    public QQProperties getQq() {
        return qq;
    }

    public void setQq(QQProperties qq) {
        this.qq = qq;
    }

    public String getFilterProcessesUrl() {
        return filterProcessesUrl;
    }

    public void setFilterProcessesUrl(String filterProcessesUrl) {
        this.filterProcessesUrl = filterProcessesUrl;
    }

    public WeChatProperties getWechat() {
        return wechat;
    }

    public void setWechat(WeChatProperties wechat) {
        this.wechat = wechat;
    }

    public GitHubProperties getGithub() {
        return github;
    }

    public void setGithub(GitHubProperties github) {
        this.github = github;
    }

    public LinkedInProperties getLinkedin() {
        return linkedin;
    }

    public void setLinkedin(LinkedInProperties linkedin) {
        this.linkedin = linkedin;
    }
}
