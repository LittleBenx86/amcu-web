package site.amcu.amcuweb.browser.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import site.amcu.amcuweb.properties.ResponseType;
import site.amcu.amcuweb.properties.SecurityProperties;
import site.amcu.amcuweb.vo.CommonResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description:    浏览器web端用户登录身份验证成功处理器(需要加入到过滤器链配置中覆盖原本的处理器)
 * @Author: Ben-Zheng
 * @Date: 2018/11/02 15:59
 */
@Component("browserAuthenticationSuccessHandler")
public class BrowserAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    /** 开发阶段的日志配置 */
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /** 对象快速json格式化工具 */
    @Autowired
    private ObjectMapper objectMapper;

    /** 获取配置 */
    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 重新覆盖登录成功的处理方法
     * @param request
     * @param response
     * @param authentication
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {

        logger.info("开发阶段测试:登陆成功");

        if(ResponseType.JSON.equals(securityProperties.getBrowser().getResponseType())) {
            Map<String, Object> map = new HashMap<>();
            map.put("userId", authentication.getName());
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(new CommonResponse(200, "", map)));
        } else {
            /** 父类的方法:进行跳转 */
            super.onAuthenticationSuccess(request, response, authentication);
        }

    }
}
