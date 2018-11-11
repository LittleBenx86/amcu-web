package site.amcu.amcuweb.properties;

/**
 * @Description:    session管理的配置类
 * @Author: Ben-Zheng
 * @Date: 2018/11/09 16:56
 */
public class SessionProperties {

    /** 同一个用户在系统中最多可以使用session的数量,默认是1(同一用户只能同一时间只能存在一次有效登录) */
    private int maxNumInSession = 1;

    /** 达到最大session时是否阻止新的登陆请求，默认为false：不阻止，此时新的登陆会把旧的登陆失效掉（强制登陆--掉线） */
    private boolean maxSessionsPreventsSignIn = false;

    /** session失效后的跳转地址,需要重新登录 */
    private String sessionInvalidUrl = SecurityConstants.DEFAULT_SESSION_INVALID_URL;

    public int getMaxNumInSession() {
        return maxNumInSession;
    }

    public void setMaxNumInSession(int maxNumInSession) {
        this.maxNumInSession = maxNumInSession;
    }

    public boolean isMaxSessionsPreventsSignIn() {
        return maxSessionsPreventsSignIn;
    }

    public void setMaxSessionsPreventsSignIn(boolean maxSessionsPreventsSignIn) {
        this.maxSessionsPreventsSignIn = maxSessionsPreventsSignIn;
    }

    public String getSessionInvalidUrl() {
        return sessionInvalidUrl;
    }

    public void setSessionInvalidUrl(String sessionInvalidUrl) {
        this.sessionInvalidUrl = sessionInvalidUrl;
    }
}
