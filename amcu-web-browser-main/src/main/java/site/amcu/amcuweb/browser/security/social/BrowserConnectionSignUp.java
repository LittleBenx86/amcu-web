package site.amcu.amcuweb.browser.security.social;

import org.apache.commons.lang.StringUtils;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Component;
import site.amcu.amcuweb.entity.User;
import site.amcu.amcuweb.service.UserService;
import site.amcu.amcuweb.support.RoleSupport;
import site.amcu.amcuweb.utils.RandomUsernameGeneratorUtils;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Description:    第一次第三方登录默认注册
 * @Author: Ben-Zheng
 * @Date: 2018/11/06 10:41
 */
@Component
public class BrowserConnectionSignUp implements ConnectionSignUp {

    @Resource
    private UserService userService;

    /**
     * 可以根据社交用户信息默认创建用户(可能需要加锁)
     * @param connection
     * @return
     */
    @Override
    public String execute(Connection<?> connection) {
        User usr = new User();
        String prefix = StringUtils.substringBefore(connection.getKey().toString(), ":");
        if(prefix.equalsIgnoreCase("callback.do")) {
            prefix = "qq";
        }
        prefix += "_";

        usr.setUsername(prefix + RandomUsernameGeneratorUtils.generatorUsername(6));
        usr.setRole(RoleSupport.DEFAULT_COMMON_USER_ROLE);
        usr.setAvatar(connection.getImageUrl());
        usr.setSignupDate(new Date());
        int id = userService.socialUserRegist(usr);

        return "" + id;
    }
}
