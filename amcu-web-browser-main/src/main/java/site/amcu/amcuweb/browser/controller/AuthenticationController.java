package site.amcu.amcuweb.browser.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.web.bind.annotation.*;
import site.amcu.amcuweb.entity.User;
import site.amcu.amcuweb.properties.SecurityConstants;
import site.amcu.amcuweb.service.UserService;
import site.amcu.amcuweb.support.RoleSupport;
import site.amcu.amcuweb.vo.CommonResponse;
import site.amcu.amcuweb.vo.Response;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author: Ben-Zheng
 * @Date: 2018/11/04 16:12
 */
@RestController
public class AuthenticationController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private ObjectMapper om = new ObjectMapper();

    @Autowired
    private UserService userService;

    @PostMapping(SecurityConstants.DEFAULT_SIGNUP_PROCESSING_URL_FORM)
    public void authRegist(User user) {
        user.setRole(RoleSupport.DEFAULT_COMMON_USER_ROLE);
        user.setSignupDate(new Date());
        logger.info(user.toString());
        int num = userService.addNewUser(user);
        logger.info("增加注册的用户数量:" + num);
    }

    @PermitAll
    @GetMapping("/auth/usr-enduring")
    public ResponseEntity<Response> getPersistentUserFromSession(Authentication auth, HttpServletRequest request) {
        SocialUserDetails principal;
        ResponseEntity<Response> resp = null;
        Map<String, String> res = new HashMap<>();
        if(auth != null && auth.isAuthenticated() && !auth.getPrincipal().toString().equalsIgnoreCase("anonymousUser")) {
            principal = (SocialUserDetails) auth.getPrincipal();
            if(null != principal ){
                res.put("userId", principal.getUserId());
                try {
                    resp = ResponseEntity.ok().body(new Response(true, "", res));
                } catch (Exception e) {
                    resp = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body(new Response(false, "当前用户信息数据格式化异常!"));
                }
            }
        }
        return resp == null ?
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new Response(false, "当前用户信息获取失败!")) :
                resp;
    }

}
