package site.amcu.amcuweb.validate;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * @Description:    验证码一些列处理过程的接口规范
 *                      创建验证码
 *                      检验验证码
 * @Author: Ben-Zheng
 * @Date: 2018/10/27 23:43
 */
public interface ValidateCodeProcessor {

    /**
     * 创建验证码(生成 & 保存)
     * @param request   ServletWebRequest为spring自带的工具类,封装了request & response
     */
    void create(ServletWebRequest request) throws Exception ;

    /**
     * 校验: 比较请求传回来的验证码和在session中的验证码
     * @param request   ServletWebRequest为spring自带的工具类,封装了request & response
     */
    void validate(ServletWebRequest request);

}
