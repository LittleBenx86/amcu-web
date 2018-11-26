package site.amcu.amcuweb.browser.security;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
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

    /**
     * 使用SocialUserDetails是为了绑定第三方账号
     * @param user
     * @return
     */
    private SocialUserDetails buildUser(User user, int type) {

        String name = type == BrowserSocialUserBuildType.BUIL_USER_TYPE_FORM ? user.getUsername() : user.getId().toString();
        SocialUserDetails usr = new SocialUser(name,
                user.getPassword() == null ? RandomStringUtils.randomAscii(8) : user.getPassword(),
                user.isEnabled(),
                user.isAccountNonExpired(),
                user.isCredentialsNonExpired(),
                user.isAccountNonLocked(),
                user.getAuthorities());
        return usr;
}

    /**
     * 表单登录使用的获取用户信息的方法
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User usr = null;
        if(StringUtils.isNumeric(username)) {
            logger.info("开发阶段:正在通过remember-me免密登录的用户id--" + username);
            usr = userService.findBySocialUserId(Integer.parseInt(username));
        } else {
            logger.info("开发阶段:正在登录的用户--" + username);
            int type = SignInRegxUtils.getSignInTypeByRegx(username);
            logger.info("开发阶段:username类型:" + type);
            usr = userService.findByLoginCondition(username, type);
        }

        if(null == usr) {
            return null;
        }

        return this.buildUser(usr, BrowserSocialUserBuildType.BUIL_USER_TYPE_SOCIAL);
    }

    /**
     * 第三方登录所使用的获取用户信息的方法
     * @param userId
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {

        logger.info("开发阶段:正在通过第三方登录的用户的id--" + userId);
        User usr = userService.findBySocialUserId(Integer.parseInt(userId));
        return this.buildUser(usr, BrowserSocialUserBuildType.BUIL_USER_TYPE_SOCIAL);
    }

}
