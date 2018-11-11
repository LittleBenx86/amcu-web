package site.amcu.amcuweb.browser.security.social;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import site.amcu.amcuweb.entity.User;
import site.amcu.amcuweb.properties.SecurityProperties;
import site.amcu.amcuweb.service.UserService;
import site.amcu.amcuweb.support.RoleSupport;
import site.amcu.amcuweb.utils.RandomUsernameGeneratorUtils;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Description:    首次第三方登录默认注册(为了适应oauth2协议中只能设置单个回调地址的第三方)
 * @Author: Ben-Zheng
 * @Date: 2018/11/06 10:41
 */
//@Component
public class BrowserConnectionSignUp implements ConnectionSignUp {

    @Resource
    private UserService userService;

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 可以根据社交用户信息默认创建用户(可能需要加锁)
     * @param connection
     * @return
     */
    @Override
    public String execute(Connection<?> connection) {
        Integer id;
        User usr = new User();
        String prefix = StringUtils.substringBefore(connection.getKey().toString(), ":");

        /** 由于github的回调地址只有一个,绑定处理只好通过登录实现(非首次登录,即注册不会进入到该方法中) */

        if(securityProperties.getSocial().getQq().getProviderId().equalsIgnoreCase(prefix)) {
            prefix = "qq";
        }
        prefix += "_";

        usr.setUsername(prefix + RandomUsernameGeneratorUtils.generatorUsername(6));
        usr.setRole(RoleSupport.DEFAULT_COMMON_USER_ROLE);
        usr.setAvatar(connection.getImageUrl());
        usr.setSignupDate(new Date());
        id = userService.socialUserRegist(usr);

        return id.toString();
    }
}
