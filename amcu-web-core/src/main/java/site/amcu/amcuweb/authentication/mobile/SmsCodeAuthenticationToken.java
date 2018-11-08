package site.amcu.amcuweb.authentication.mobile;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

import java.util.Collection;

/**
 * @Description:    短信验证码登录的token实现
 * @Author: Ben-Zheng
 * @Date: 2018/11/02 19:43
 */
public class SmsCodeAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    /** 放置短信登陆的用户信息 */
    private final Object principal;

    public SmsCodeAuthenticationToken(String mobile) {
        super(null);
        /** 没有登录成功时,先存入登录的手机号 */
        this.principal = mobile;
        this.setAuthenticated(false);
    }

    public SmsCodeAuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        /** 登录成功后,存入的是用户的信息 */
        this.principal = principal;
        /** 覆盖时,必须使用父类的授权 */
        super.setAuthenticated(true);
    }

    public Object getPrincipal() {
        return this.principal;
    }

    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException(
                    "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }
        super.setAuthenticated(false);
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
    }

    @Override
    public Object getCredentials() {
        return null;
    }

}
