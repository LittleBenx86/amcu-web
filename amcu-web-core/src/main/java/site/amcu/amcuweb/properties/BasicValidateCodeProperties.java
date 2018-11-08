package site.amcu.amcuweb.properties;

/**
 * @Description:    验证码基础配置类,用于继承实现
 *                  主要是为了验证码的参数可以在配置文件中灵活配置
 * @Author: Ben-Zheng
 * @Date: 2018/10/27 21:21
 */
public class BasicValidateCodeProperties {
    /** 验证码的长度 */
    private int length = 6;

    /** 验证码的有效时间(过期时间),单位s */
    private int expireIn = 180;

    /** 验证码url,可被Filter拦截(用于配置验证码的拦截器),可以配置多个url(逗号隔开) */
    private String url;

    public int getLength() {
        return length;
    }

    /***************** setter & getter 是为了自动注入 *********************/

    public void setLength(int length) {
        this.length = length;
    }

    public int getExpireIn() {
        return expireIn;
    }

    public void setExpireIn(int expireIn) {
        this.expireIn = expireIn;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
