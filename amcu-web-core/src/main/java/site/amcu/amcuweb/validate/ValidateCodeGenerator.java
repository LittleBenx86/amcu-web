package site.amcu.amcuweb.validate;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * @Description:    验证码生成器(接口规范)
 *                  主要是为了生成的逻辑可以配置(由子类进行实现)
 * @Author: Ben-Zheng
 * @Date: 2018/10/27 21:10
 */
public interface ValidateCodeGenerator {

    /**
     * 验证码生成接口
     * @param request
     * @return
     */
    ValidateCode generate(ServletWebRequest request);

}
