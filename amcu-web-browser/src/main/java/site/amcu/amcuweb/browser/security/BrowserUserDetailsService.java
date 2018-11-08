package site.amcu.amcuweb.browser.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;
import site.amcu.amcuweb.entity.User;
import site.amcu.amcuweb.service.UserService;
import site.amcu.amcuweb.utils.SignInRegxUtils;

/**
 * @Description:    从数据库获取用户的信息进行封装
 * @Author: Ben-Zheng
 * @Date: 2018/11/04 10:19
 */
@Component
public class BrowserUserDetailsService implements UserDetailsService, SocialUserDetailsService {


    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    private SocialUserDetails buildUser(User user) {
        return new SocialUser(user.getUsername(),
                user.getPassword(),
                user.isEnabled(),
                user.isAccountNonExpired(),
                user.isCredentialsNonExpired(),
                user.isAccountNonLocked(),
                user.getAuthorities());
    }

    /**
     * 表单登录使用的获取用户信息的方法
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        logger.info("开发阶段:正在登录的用户--" + username);
        int type = SignInRegxUtils.getSignInTypeByRegx(username);
        logger.info("开发阶段:username类型:" + type);
        User usr = userService.findByLoginCondition(username, type);

        if(null == usr) {
            return null;
        }

        return this.buildUser(usr);
    }

    /**
     * 第三方登录所使用的获取用户信息的方法
     * @param userId
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        return null;
    }
}
