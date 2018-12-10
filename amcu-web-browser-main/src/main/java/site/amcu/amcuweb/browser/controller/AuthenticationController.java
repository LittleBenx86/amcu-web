package site.amcu.amcuweb.browser.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.web.bind.annotation.*;
import site.amcu.amcuweb.entity.User;
import site.amcu.amcuweb.entity.UserEdu;
import site.amcu.amcuweb.entity.UserIntegrationLog;
import site.amcu.amcuweb.entity.UserPersonalInfo;
import site.amcu.amcuweb.properties.SecurityConstants;
import site.amcu.amcuweb.service.UserEduService;
import site.amcu.amcuweb.service.UserIntegrationLogService;
import site.amcu.amcuweb.service.UserPersonalInfoService;
import site.amcu.amcuweb.service.UserService;
import site.amcu.amcuweb.support.RoleSupport;
import site.amcu.amcuweb.support.UserAccountStatusSupport;
import site.amcu.amcuweb.support.UserIntegrationTypeSupport;
import site.amcu.amcuweb.support.UserIntegrationTypeValueSupport;
import site.amcu.amcuweb.vo.Response;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
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

    @Autowired
    private UserService userService;

    @Autowired
    private UserPersonalInfoService personalInfoService;

    @Autowired
    private UserEduService userEduService;

    @Autowired
    private UserIntegrationLogService integrationLogService;

    /**
     * 用户注册
     * @param user
     */
    @PermitAll
    @PostMapping(SecurityConstants.DEFAULT_SIGNUP_PROCESSING_URL_FORM)
    public void authRegist(User user) {
        user.setRole(RoleSupport.DEFAULT_COMMON_USER_ROLE);
        user.setSignupDate(new Date());
        user.setStatus(UserAccountStatusSupport.ACCOUNT_ACTIVE);
        int num = userService.addNewUser(user);
        if(1 == num) {
            UserPersonalInfo info = new UserPersonalInfo();
            info.setArticles(0);
            info.setFollowers(0);
            info.setFollowings(0);
            info.setIntegration(UserIntegrationTypeValueSupport.FIRST_SIGNUP_WEB.getIntegrationsValue());
            info.setRank(0);
            info.setWilling(0);
            info.setUserId(user.getId());

            UserEdu ue = new UserEdu();
            ue.setUserId(user.getId());

            UserIntegrationLog log = new UserIntegrationLog();
            log.setUserId(user.getId());
            log.setType(UserIntegrationTypeSupport.FIRST_SIGNUP_WEB);
            log.setIntegration(UserIntegrationTypeValueSupport.FIRST_SIGNUP_WEB.getIntegrationsValue());
            log.setObtainTime(new Date());

            personalInfoService.createUserPersonalInfo(info);
            userEduService.createUserEdu(ue);
            integrationLogService.createAnIntegrationLog(log);
        }
    }

    /**
     * 获取在session中的用户信息
     * @param auth
     * @param request
     * @return
     */
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
                    resp = ResponseEntity.ok().body(new Response(true, "", HttpStatus.OK.value(), res));
                } catch (Exception e) {
                    resp = ResponseEntity.ok()
                            .body(new Response(false, "当前用户信息数据格式化异常!", HttpStatus.INTERNAL_SERVER_ERROR.value()));
                }
            }
        }
        return resp == null ?
                ResponseEntity.ok()
                        .body(new Response(false, "未登录", HttpStatus.valueOf(404).value())) :
                resp;
    }

}
