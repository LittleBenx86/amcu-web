package site.amcu.amcuweb.validate;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;
import site.amcu.amcuweb.properties.SecurityConstants;
import site.amcu.amcuweb.properties.SecurityProperties;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Description:    验证码校验过滤器
 * @Author: Ben-Zheng
 * @Date: 2018/11/02 16:29
 */
@Component("validateCodeFilter")
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {

    /** 验证码校验失败过滤器 */
    @Autowired
    private AuthenticationFailureHandler failureHandler;

    /** 获取配置 */
    @Autowired
    private SecurityProperties securityProperties;

    /** 注入校验码处理器 */
    @Autowired
    private ValidateCodeProcessorHolder validateCodeProcessorHolder;

    /** 存放所有需要校验验证码的url请求,用于匹配拦截 */
    private Map<String, ValidateCodeType> urlMap = new HashMap<>();

    /** url匹配的工具类 */
    private AntPathMatcher pathMatcher = new AntPathMatcher();

    /** 将系统中配置的需要校验的验证码的url根据校验类型放入map */
    private void addUrlToMap(String url, ValidateCodeType type) {
        if(StringUtils.isNotBlank(url)) {
            String[] urls = StringUtils.splitByWholeSeparatorPreserveAllTokens(url, ",");
            for (String u : urls) {
                urlMap.put(u, type);
            }
        }
    }

    /** 在该配置类加载完成后触发该设置 */
    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
        /** 图片验证码匹配 */
        /** 1.表单登陆需要图片验证码 */
        urlMap.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM, ValidateCodeType.IMAGE);
        /** 2.其他需要图片验证码的请求 */
        addUrlToMap(securityProperties.getCode().getImage().getUrl(), ValidateCodeType.IMAGE);

        /** 短信验证码匹配 */
        /** 1.手机短信验证码登陆 */
        urlMap.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE, ValidateCodeType.SMS);
        /** 2.其他需要短信验证码的请求 */
        addUrlToMap(securityProperties.getCode().getSms().getUrl(), ValidateCodeType.SMS);

        /** 邮箱验证码匹配 */
        /** 1.其他需要邮箱验证码的请求 */
        addUrlToMap(securityProperties.getCode().getEmail().getUrl(), ValidateCodeType.EMAIL);
    }

    /**
     * 从请求中获取校验码的类型
     * 只能从非get方法中获取
     * @param request
     * @return
     */
    private ValidateCodeType getValidateCodeType(HttpServletRequest request) {
        ValidateCodeType type = null;
        if(!StringUtils.equalsIgnoreCase(request.getMethod(), "get")) {
            Set<String> urls = urlMap.keySet();
            for(String url : urls) {
                if(pathMatcher.match(url, request.getRequestURI())) {
                    type = urlMap.get(url);
                }
            }
        }
        return type;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ValidateCodeType type = this.getValidateCodeType(request);
        if(type != null) {
            logger.info("开发阶段:校验码请求(" + request.getRequestURI() + ")中的验证码,验证码类型:" + type);
            try {
                validateCodeProcessorHolder.findValidateCodeProcessor(type).validate(new ServletWebRequest(request, response));
            } catch (ValidateCodeException e) {
                failureHandler.onAuthenticationFailure(request, response, e);
                return;
            }
            logger.info("开发阶段:校验码验证通过");
        }
        /** 交给过滤器链的下一个过滤器 */
        filterChain.doFilter(request, response);
    }
}
