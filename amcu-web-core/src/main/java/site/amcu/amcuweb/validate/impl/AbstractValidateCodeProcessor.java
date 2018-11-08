package site.amcu.amcuweb.validate.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import site.amcu.amcuweb.validate.*;

import java.util.Map;

/**
 * @Description:    基础实现ValidateCodeProcessor(行为有重复,需要做这层的封装),具体的实现还是需要交由子类实现
 * @Author: Ben-Zheng
 * @Date: 2018/10/27 23:54
 */
public abstract class AbstractValidateCodeProcessor<C extends ValidateCode> implements ValidateCodeProcessor {

    /** 此处调用不同模块对验证码的存放机制 */
    @Autowired
    private ValidateCodeRepository validateCodeRepository;

    /** 在该系统启动时,自动收集所有的ValidateCodeGenerator实现(配置为spring的Component Bean);如果收集失败,会导致系统无法正常启动;注意相应的注解的配置 */
    @Autowired
    private Map<String, ValidateCodeGenerator> validateCodeGenerators;

    /**
     * 调用验证码的生成方法,子类继承该类后,就会调用相应的验证码生成方法
     * @param request
     * @return
     */
    private C generate(ServletWebRequest request) {
        String type = this.getValidateCodeType(request).toString().toLowerCase();
        String generatorName = type + ValidateCodeGenerator.class.getSimpleName();
        ValidateCodeGenerator generator = validateCodeGenerators.get(generatorName);
        if(null == generator) {
            throw new ValidateCodeException("验证码生成器:" + generatorName + " 不存在!");
        }
        return (C) generator.generate(request);

    }

    /**
     * 从请求url中获取校验码的类型
     *      图片校验码--imageCode
     *      短信校验码--smsCode
     *      邮箱校验码--emailCode
     * @param request
     * @return
     */
    private ValidateCodeType getValidateCodeType(ServletWebRequest request) {
        String type = StringUtils.substringBefore(this.getClass().getSimpleName(), "CodeProcessor");
        return ValidateCodeType.valueOf(type.toUpperCase());
    }

    /**
     * 验证码的发送规范,由子类进行实现
     *      邮箱验证码的发送
     *      手机验证码的发送
     * @param request
     * @param validateCode
     * @throws Exception
     */
    protected abstract void send(ServletWebRequest request, C validateCode) throws Exception;

    /**
     * 验证码的保存
     * 由于同一个服务环境下验证码的保存方式是一致的,此处只需要做一次封装即可
     * @param request
     * @param validateCode
     */
    private void save(ServletWebRequest request, C validateCode) {
        ValidateCode code = new ValidateCode(validateCode.getCode(), validateCode.getExpireTime());
        validateCodeRepository.save(request, code, this.getValidateCodeType(request));
    }

    /**
     * 规范验证码的创建过程(生成/保存/发送)
     * @param request   ServletWebRequest为spring自带的工具类,封装了request & response
     * @throws Exception
     */
    @Override
    public void create(ServletWebRequest request) throws Exception {
        C validateCode = this.generate(request);
        this.save(request, validateCode);
        this.send(request, validateCode);
    }

    /**
     * 规范验证码的校验过程
     * @param request   ServletWebRequest为spring自带的工具类,封装了request & response
     */
    @Override
    public void validate(ServletWebRequest request) {
        ValidateCodeType type = this.getValidateCodeType(request);
        C codeInSession = (C) validateCodeRepository.get(request, type);
        String codeInRequest = null;

        try {
            codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(), type.getParamNameOnValidate());
        } catch (ServletRequestBindingException e) {
           throw new ValidateCodeException("获取验证码失败");
        }

        if(StringUtils.isBlank(codeInRequest)) {
            throw new ValidateCodeException(type + "验证码的值不能为空");
        }

        if(null == codeInSession) {
            throw new ValidateCodeException(type + "验证码不存在");
        }

        if(codeInSession.isExpired()) {
            validateCodeRepository.remove(request, type);
            throw new ValidateCodeException(type + "验证码已过期");
        }

        if(!StringUtils.equalsIgnoreCase(codeInSession.getCode(), codeInRequest)) {
            throw new ValidateCodeException(type + "验证码不匹配");
        }

        /** 校验通过 */
        validateCodeRepository.remove(request, type);
    }
}
