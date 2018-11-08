package site.amcu.amcuweb.browser.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import site.amcu.amcuweb.vo.SimpleResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description:    浏览器web端用户身份认证失败处理器(认证失败只能返回json数据;需要加入到过滤器链配置中覆盖原本的处理器)
 * @Author: Ben-Zheng
 * @Date: 2018/11/02 16:11
 */
@Component("browserAuthenticationFailureHandler")
public class BrowserAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    /** 开发阶段:日志记录 */
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /** 对象快速json格式化工具 */
    @Autowired
    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 覆盖登录失败处理的方法
     * @param request
     * @param response
     * @param exception
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        logger.info("开发阶段测试:登录失败");

        /** 返回服务器内部错误状态码 */
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(new SimpleResponse(exception.getMessage())));

    }
}
