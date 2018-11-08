package site.amcu.amcuweb.validate;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Description:    验证码对象
 *                  衍生：图形验证码、短信验证码和邮箱验证码
 * @Author: Ben-Zheng
 * @Date: 2018/10/27 20:34
 */
public class ValidateCode implements Serializable {
    /** 序列化uid */
    private static final long serialVersionUID = -7569955916789025927L;

    /** 验证码 */
    private String code;

    /** 验证码的有效时间，需要传入一个未来的时间 */
    private LocalDateTime expireTime;

    /**
     * @param code
     * @param expireIn  验证码的持续时间
     */
    public ValidateCode(String code, int expireIn) {
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
    }

    /** 该构造器较为少用，需要传入一个未来的时间 */
    public ValidateCode(String code, LocalDateTime expireTime) {
        this.code = code;
        this.expireTime = expireTime;
    }

    /***************** setter & getter 是为了自动注入 *********************/

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }

    /**
     * 判断当前时间是否已经超过了验证的有效时间
     * @return
     */
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expireTime);
    }
}
