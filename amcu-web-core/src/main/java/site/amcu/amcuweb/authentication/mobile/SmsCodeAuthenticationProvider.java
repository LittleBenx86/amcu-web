package site.amcu.amcuweb.authentication.mobile;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @Description:    短信验证码登录的权限提供实现
 * @Author: Ben-Zheng
 * @Date: 2018/11/02 19:55
 */
public class SmsCodeAuthenticationProvider implements AuthenticationProvider {

    /** 用于获取用户的信息 */
    private UserDetailsService userDetailsService;

    /**
     * 短信验证码的身份验证逻辑
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        /** 当前没有进行验证的用户信息 */
        SmsCodeAuthenticationToken authenticationToken = (SmsCodeAuthenticationToken) authentication;

        /** 需要实现从数据库查询手机号对应的用户信息 */
        UserDetails user = userDetailsService.loadUserByUsername((String) authenticationToken.getPrincipal());

        if(null == user) {
            throw new InternalAuthenticationServiceException("无法正确获取用户的信息");
        }

        /** 已认证的用户信息 */
        SmsCodeAuthenticationToken authenticationResult = new SmsCodeAuthenticationToken(user, user.getAuthorities());

        /** 存入已认证的用户信息细节 */
        authenticationResult.setDetails(authenticationToken.getDetails());

        return authenticationResult;
    }

    /**
     * 用于判断传入的类型是否属于SmsCodeAuthentication这种类型的
     * 拓展AuthenticationManager的验证范围
     * @param authentication
     * @return
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return SmsCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
}
