package site.amcu.amcuweb.validate;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * @Description:    验证码的保存,获取和移除规范接口
 *                  实现类在各自的模块中实现
 *                  浏览器:    amcu-web-browser    --->    SessionValidateCodeRepository
 *                  移动端:    amcu-web-app
 * @Author: Ben-Zheng
 * @Date: 2018/10/27 22:56
 */
public interface ValidateCodeRepository {

    /**
     * 保存验证码到session/redis
     * @param request
     * @param code
     * @param type
     */
    void save(ServletWebRequest request, ValidateCode code, ValidateCodeType type);

    /**
     * 从session/redis中获取验证码
     * @param request
     * @param type
     * @return
     */
    ValidateCode get(ServletWebRequest request, ValidateCodeType type);

    /**
     * 校验完(校验正确)验证码后,从session/redis移除验证码
     * @param request
     * @param type
     */
    void remove(ServletWebRequest request, ValidateCodeType type);

}
