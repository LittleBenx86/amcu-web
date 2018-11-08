package site.amcu.amcuweb.site.amcu.amcuweb.browser.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import site.amcu.amcuweb.entity.User;
import site.amcu.amcuweb.properties.SecurityConstants;
import site.amcu.amcuweb.service.UserService;
import site.amcu.amcuweb.support.RoleSupport;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description:
 * @Author: Ben-Zheng
 * @Date: 2018/11/04 16:12
 */
@RestController
public class AuthenticationController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    @PostMapping(SecurityConstants.DEFAULT_SIGNUP_PROCESSING_URL_FORM)
    public void authRegist(User user) {
        logger.info(user.toString());
        user.setRole(RoleSupport.DEFAULT_COMMON_USER_ROLE);
        int num = userService.addNewUser(user);
        logger.info("增加注册的用户数量:" + num);
    }

}
