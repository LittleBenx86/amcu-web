package site.amcu.amcuweb.browser.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;
import site.amcu.amcuweb.browser.security.BrowserSocialUserBuildType;
import site.amcu.amcuweb.entity.User;
import site.amcu.amcuweb.service.UserService;
import site.amcu.amcuweb.support.RoleSupport;
import site.amcu.amcuweb.utils.SignInRegxUtils;
import site.amcu.amcuweb.vo.CommonResponse;
import site.amcu.amcuweb.vo.Response;
import site.amcu.amcuweb.vo.SocialUserInfoVO;

import javax.annotation.Resource;
import javax.annotation.security.DenyAll;
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

    @Resource
    private UserService userService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private ObjectMapper objectMapper = new ObjectMapper();

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
                 resp = ResponseEntity.ok().body(new Response(true, "", usr));
            }
        }
        return resp == null ?
                ResponseEntity.status(HttpStatus.valueOf(500)).body(new Response(false, "登录异常,信息获取失败!")) :
                resp;
    }

    @PermitAll
    @PostMapping("/usr/social-signup/{type}")
    public void socialUserRegistOrBinding(@PathVariable(name = "type") Integer type,
                                          User usr, HttpServletRequest request) {
        if(BrowserSocialUserBuildType.CREATE_NEW_USER_BY_SOCIAL == type) {
            usr.setSignupDate(new Date());
            usr.setRole(RoleSupport.DEFAULT_COMMON_USER_ROLE);
            Integer id = userService.socialUserRegist(usr);
            providerSignInUtils.doPostSignUp(id.toString(), new ServletWebRequest(request));
        } else if(BrowserSocialUserBuildType.BINDING_USER_ON_EXISTS == type) {
            int t = SignInRegxUtils.getSignInTypeByRegx(usr.getUsername());
            User tarUser = userService.findByLoginCondition(usr.getUsername(), t);
            providerSignInUtils.doPostSignUp(tarUser.getId().toString(), new ServletWebRequest(request));
        }
    }

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

}
