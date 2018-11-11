package site.amcu.amcuweb.browser.session;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;
import site.amcu.amcuweb.vo.SimpleResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description:    抽象的session管理策略
 *                  需要继承实现session过期处理策略
 *                  和session失效处理策略
 * @Author: Ben-Zheng
 * @Date: 2018/11/09 19:02
 */
public class AbstractSessionStrategy {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /** session过期/失效跳转的url */
    private String destinationUrl;

    /** 重定向策略 */
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    /** url跳转前是否创建新的session(开启) */
    private Boolean createNewSession = true;

    private ObjectMapper objectMapper = new ObjectMapper();

    public AbstractSessionStrategy(String invalidSessionUrl) {
        Assert.isTrue(UrlUtils.isValidRedirectUrl(invalidSessionUrl), "url must start with '/' or with 'http(s)'");
        this.destinationUrl = invalidSessionUrl;
    }

    /**
     * 在session失效的时候触发
     * @param request
     * @param response
     * @throws IOException
     */
    protected void onSessionInvalid(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if(createNewSession) {
            request.getSession();
        }

        String sourceUrl = request.getRequestURI();
        String targetUrl;

        if(StringUtils.endsWithIgnoreCase(sourceUrl, ".html")) {
            targetUrl = destinationUrl + ".html";
            logger.warn("session失效，跳转到" + targetUrl);
            redirectStrategy.sendRedirect(request, response, targetUrl);
        } else {
            String message = "session已经失效";
            if(this.isConcurrency()) {
                message = message + "，有可能是并发登录（同一用户多处登陆）导致的！";
            }
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(new SimpleResponse(message)));
        }
    }

    /**
     * 当前session失效是否是由并发登录造成的
     * @return
     */
    protected boolean isConcurrency() {
        return false;
    }

    public void setCreateNewSession(Boolean creaetNewSession) {
        this.createNewSession = creaetNewSession;
    }

}
