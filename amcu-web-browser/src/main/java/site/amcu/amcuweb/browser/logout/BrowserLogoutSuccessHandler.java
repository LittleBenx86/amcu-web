package site.amcu.amcuweb.browser.logout;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import site.amcu.amcuweb.vo.SimpleResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description:    浏览器用户退出处理器
 * @Author: Ben-Zheng
 * @Date: 2018/11/12 9:44
 */
public class BrowserLogoutSuccessHandler implements LogoutSuccessHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private ObjectMapper objectMapper = new ObjectMapper();

    private String signOutUrl;

    public BrowserLogoutSuccessHandler(String signOutUrl) {
        this.signOutUrl = signOutUrl;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request,
                                HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if(StringUtils.isBlank(this.signOutUrl)) {
            /** 没有配置链接，返回json数据 */
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(new SimpleResponse("退出成功")));
        } else {
            /** 配置了链接就重定向 */
            response.sendRedirect(this.signOutUrl);
        }
    }
}
