package site.amcu.amcuweb.browser.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import site.amcu.amcuweb.browser.security.BrowserSocialUserBuildType;
import site.amcu.amcuweb.entity.User;
import site.amcu.amcuweb.entity.UserEdu;
import site.amcu.amcuweb.entity.UserIntegrationLog;
import site.amcu.amcuweb.entity.UserPersonalInfo;
import site.amcu.amcuweb.service.UserEduService;
import site.amcu.amcuweb.service.UserIntegrationLogService;
import site.amcu.amcuweb.service.UserPersonalInfoService;
import site.amcu.amcuweb.service.UserService;
import site.amcu.amcuweb.support.RoleSupport;
import site.amcu.amcuweb.support.UserAccountStatusSupport;
import site.amcu.amcuweb.support.UserIntegrationTypeSupport;
import site.amcu.amcuweb.support.UserIntegrationTypeValueSupport;
import site.amcu.amcuweb.utils.SignInRegxUtils;
import site.amcu.amcuweb.vo.Response;
import site.amcu.amcuweb.vo.SocialUserInfoVO;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author: Ben-Zheng
 * @Date: 2018/11/05 17:19
 */
@RestController
public class UserBasicController {

    @Autowired
    private ProviderSignInUtils providerSignInUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Resource
    private UserService userService;

    @Autowired
    private UserPersonalInfoService personalInfoService;

    @Autowired
    private UserEduService userEduService;

    @Autowired
    private UserIntegrationLogService integrationLogService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     *  获取已登录用户的信息
     * @param userId
     * @param request
     * @return
     * @throws Exception
     */
    @RolesAllowed({"USER"})
    @GetMapping("/usr/signin-usr")
    public ResponseEntity<Response> getCurrentUser(@RequestParam(value = "userId") String userId, HttpServletRequest request) throws Exception {

        SocialUserDetails principal;
        ResponseEntity<Response> resp = null;

        if(SecurityContextHolder.getContext().getAuthentication() != null &&
                SecurityContextHolder.getContext().getAuthentication().isAuthenticated() &&
                !SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().equalsIgnoreCase("anonymousUser")) {
            principal = (SocialUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if(null != principal && userId.equals(principal.getUserId())){
                User usr = userService.findBySocialUserId(Integer.parseInt(userId));
                if(usr != null)
                    resp = ResponseEntity.ok().body(new Response(true, "", HttpStatus.OK.value(), usr));
            }
        }
        return resp == null ?
                ResponseEntity.ok().body(new Response(false, "登录异常,信息获取失败!", HttpStatus.INTERNAL_SERVER_ERROR.value())) :
                resp;
    }

    /**
     *  获取其他用户的信息
     * @param userId
     * @return
     */
    @PermitAll
    @GetMapping("/usr/other-usr")
    public ResponseEntity<Response> getTarUser(@RequestParam(value = "tuid") String userId) {
        ResponseEntity<Response> resp = null;
        if(userId != null && !userId.equalsIgnoreCase("") && StringUtils.isNumeric(userId)) {
            User usr = userService.findBySocialUserId(Integer.parseInt(userId));
            if(null != usr)
                resp = ResponseEntity.ok().body(new Response(true, "", HttpStatus.OK.value(), usr));
        }
        return resp == null ?
                ResponseEntity.ok().body(new Response(false, "该用户信息获取失败!", HttpStatus.INTERNAL_SERVER_ERROR.value())) :
                resp;
    }

    /**
     *  github,Coding等第三方用户的绑定/所有第三方首次登陆时的账号注册or绑定
     * @param type
     * @param usr
     * @param request
     */
    @PermitAll
    @PostMapping("/usr/social-signup/{type}")
    public void socialUserRegistOrBinding(@PathVariable(name = "type") Integer type,
                                          User usr, HttpServletRequest request) {
        if(BrowserSocialUserBuildType.CREATE_NEW_USER_BY_SOCIAL == type) {
            usr.setSignupDate(new Date());
            usr.setRole(RoleSupport.DEFAULT_COMMON_USER_ROLE);
            usr.setStatus(UserAccountStatusSupport.ACCOUNT_ACTIVE);
            Integer id = userService.socialUserRegist(usr);

            UserPersonalInfo info = new UserPersonalInfo();
            info.setArticles(0);
            info.setFollowers(0);
            info.setFollowings(0);
            info.setIntegration(UserIntegrationTypeValueSupport.FIRST_SIGNUP_WEB.getIntegrationsValue());
            info.setRank(0);
            info.setWilling(0);
            info.setUserId(id);

            UserEdu ue = new UserEdu();
            ue.setUserId(id);

            UserIntegrationLog log = new UserIntegrationLog();
            log.setUserId(id);
            log.setType(UserIntegrationTypeSupport.FIRST_SIGNUP_WEB);
            log.setIntegration(UserIntegrationTypeValueSupport.FIRST_SIGNUP_WEB.getIntegrationsValue());
            log.setObtainTime(new Date());

            personalInfoService.createUserPersonalInfo(info);
            userEduService.createUserEdu(ue);
            integrationLogService.createAnIntegrationLog(log);
            providerSignInUtils.doPostSignUp(id.toString(), new ServletWebRequest(request));
        } else if(BrowserSocialUserBuildType.BINDING_USER_ON_EXISTS == type) {
            int t = SignInRegxUtils.getSignInTypeByRegx(usr.getUsername());
            User tarUser = userService.findByLoginCondition(usr.getUsername(), t);
            providerSignInUtils.doPostSignUp(tarUser.getId().toString(), new ServletWebRequest(request));
        }
    }

    /**
     *  获取第三方账户的信息
     * @param authentication
     * @param request
     * @return
     */
    @PermitAll
    @GetMapping("/usr/social-info")
    public SocialUserInfoVO getSocialUserInfo(Authentication authentication, HttpServletRequest request) {
        SocialUserInfoVO info = new SocialUserInfoVO();

        Connection<?> connection = providerSignInUtils != null ? providerSignInUtils.getConnectionFromSession(new ServletWebRequest(request)) : null;
        if(connection != null) {
            info.setProviderId(connection.getKey().getProviderId());
            info.setSocialNickname(connection.getDisplayName());
            info.setSocialAvatar(connection.getImageUrl());
        }

        /** 判断是否有用户已经登录 */
        if(authentication != null && authentication.isAuthenticated()
                && !authentication.getPrincipal().toString().equalsIgnoreCase("anonymousUser")) {
            SocialUserDetails socialUserDetails = (SocialUserDetails) authentication.getPrincipal();
            User usr = userService.findBySocialUserId(Integer.parseInt(socialUserDetails.getUserId()));
            info.setHasSignIn(true);
            info.setCurUserId(usr.getId().toString());
            info.setCurUsername(usr.getUsername());
            info.setCurUserAvatar(usr.getAvatar());
        } else {
            info.setHasSignIn(false);
        }

        return info;
    }

    /**
     *  用户名/邮箱地址查重
     * @param username
     * @param email
     * @return
     */
    @PermitAll
    @PostMapping("/usr/check")
    public Object validateUsernameOrEmailExist(@RequestParam(value = "username", required = false) String username,
                                               @RequestParam(value = "email", required = false) String email) {
        boolean flag = false;
        String result = null;
        Map<String, Boolean> map = new HashMap<>();

        if(StringUtils.isNotBlank(username)) {
           flag = userService.findByUsername(username);
        } else if(StringUtils.isNotBlank(email)) {
            flag = userService.findByEmail(email);
        }

        map.put("valid", !flag);

        try {
            result = objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
        }

        return result;
    }


    @RolesAllowed({"USER"})
    @PostMapping("/usr/old-email-modify-valid")
    public ResponseEntity<Response> oldEmailModifyValid() {
        return ResponseEntity.ok().body(new Response(true, "验证成功", HttpStatus.OK.value()));
    }

    @RolesAllowed({"USER"})
    @PostMapping("/usr/new-email-confirm-valid")
    public ResponseEntity<Response> newEmailValid(@RequestParam(value="newEmail") String email) {
        logger.info("更换的邮箱地址为:" + email);
        return ResponseEntity.ok().body(new Response(true, "验证成功", HttpStatus.OK.value()));
    }

    @RolesAllowed({"USER"})
    @PostMapping("/usr/name-modify")
    public ResponseEntity<Response> usernameModify(@RequestParam(value = "username") String username,
                                                   Authentication authentication) {
        User usr = new User();
        if(authentication != null && authentication.isAuthenticated()
                && !authentication.getPrincipal().toString().equalsIgnoreCase("anonymousUser")) {
            SocialUserDetails socialUserDetails = (SocialUserDetails) authentication.getPrincipal();
            usr.setId(Integer.parseInt(socialUserDetails.getUserId()));
        }
        usr.setUsername(username);
        userService.updateUserInfoById(usr);
        return ResponseEntity.ok().body(new Response(true, "验证成功", HttpStatus.OK.value()));
    }

    @RolesAllowed({"USER"})
    @PostMapping("/usr/pwd-modify/{type}")
    public ResponseEntity<Response> passwordModifyWithType(@PathVariable(value = "type") String type,
                                                           @RequestParam(value = "oldPassword", required = false) String oPwd,
                                                           @RequestParam(value = "newPassword") String nPwd,
                                                           @RequestParam(value = "confirmPassword") String cPwd,
                                                           Authentication authentication) {
        Response resp = null;
        User usr = null;
        boolean isMatches = false;
        if(authentication != null && authentication.isAuthenticated()
                && !authentication.getPrincipal().toString().equalsIgnoreCase("anonymousUser")) {
            SocialUserDetails socialUserDetails = (SocialUserDetails) authentication.getPrincipal();
            usr = userService.findBySocialUserId(Integer.parseInt(socialUserDetails.getUserId()));
        }

        if(StringUtils.isNumeric(type)) {
            if (StringUtils.equals("1", type)){

                if(null != usr) {
                    isMatches = passwordEncoder.matches(oPwd, usr.getPassword());
                    if(!isMatches){
                        return ResponseEntity.ok().body(new Response(false, "原密码错误!", HttpStatus.INTERNAL_SERVER_ERROR.value()));
                    }

                    isMatches = StringUtils.equalsIgnoreCase(nPwd, cPwd);
                    if(!isMatches) {
                        return ResponseEntity.ok().body(new Response(false, "两次新密码输入不一致!", HttpStatus.INTERNAL_SERVER_ERROR.value()));
                    }
                }

            } else if(StringUtils.equals("2", type)) {
                isMatches = StringUtils.equalsIgnoreCase(nPwd, cPwd);
                if(!isMatches) {
                    return ResponseEntity.ok().body(new Response(false, "两次新密码输入不一致!", HttpStatus.INTERNAL_SERVER_ERROR.value()));
                }
            } else {
                return ResponseEntity.ok().body(new Response(false, "请求链接出错!", HttpStatus.INTERNAL_SERVER_ERROR.value()));
            }
        } else {
            return ResponseEntity.ok().body(new Response(false, "请求链接出错!", HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }

        User u = new User();
        u.setId(usr.getId());
        u.setPassword(this.passwordEncoder.encode(nPwd));
        int rows = userService.updateUserInfoById(u);
        if(rows == 1)
            resp = new Response(true, "密码已更新!\n需要重新登录!", HttpStatus.OK.value());
        else
            resp = new Response(false, "密码更新失败!", HttpStatus.INTERNAL_SERVER_ERROR.value());

        return ResponseEntity.ok().body(resp);
    }

    @RolesAllowed({"USER"})
    @PostMapping("/usr/avatar-modify")
    public void userAvatarModify(@RequestParam(value = "avatar") String avatar, Authentication authentication) {
        User usr = new User();
        if(authentication != null && authentication.isAuthenticated()
                && !authentication.getPrincipal().toString().equalsIgnoreCase("anonymousUser")) {
            SocialUserDetails socialUserDetails = (SocialUserDetails) authentication.getPrincipal();
            usr.setId(Integer.parseInt(socialUserDetails.getUserId()));
        }
        usr.setAvatar(avatar);
        userService.updateUserInfoById(usr);
    }

}
