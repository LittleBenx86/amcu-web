package site.amcu.amcuweb.validate;

import org.springframework.security.core.AuthenticationException;

/**
 * @Description:    验证码验证异常
 * @Author: Ben-Zheng
 * @Date: 2018/10/28 0:11
 */
public class ValidateCodeException extends AuthenticationException {

    private static final long serialVersionUID = 4869069691478890034L;

    public ValidateCodeException(String msg) {
        super(msg);
    }
}
