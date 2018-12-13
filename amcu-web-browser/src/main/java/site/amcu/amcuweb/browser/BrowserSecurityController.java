package site.amcu.amcuweb.browser;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import site.amcu.amcuweb.properties.SecurityConstants;
import site.amcu.amcuweb.properties.SecurityProperties;
import site.amcu.amcuweb.vo.SimpleResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description:
 * @Author: Ben-Zheng
 * @Date: 2018/11/06 18:54
 */
@RestController
public class BrowserSecurityController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /** 从请求的缓存中获取请求的信息 */
    private RequestCache requestCache = new HttpSessionRequestCache();

    /** 重定向工具 */
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private ProviderSignInUtils providerSignInUtils;

    @RequestMapping(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public void requiresAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        /** 之前缓存的请求 */
        SavedRequest savedRequest = requestCache.getRequest(request, response);

        if(null != savedRequest) {
            String tarUrl = savedRequest.getRedirectUrl();
            logger.info("引发跳转的请求是：" + tarUrl);
            redirectStrategy.sendRedirect(request, response, securityProperties.getBrowser().getLoginPage());
        }
    }

}
