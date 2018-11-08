package site.amcu.amcuweb.validate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Description:    用于管理(调用)不同的验证码处理器
 * @Author: Ben-Zheng
 * @Date: 2018/10/28 10:18
 */
@Component
public class ValidateCodeProcessorHolder {

    /** 在系统启动的时候收集所有的验证码处理器,根据不同的验证码请求对应获取并调用 */
    @Autowired
    private Map<String, ValidateCodeProcessor> validateCodeProcessors;

    /**
     * 通过验证码的类型从容器中获取对应的验证码处理器
     * @param type
     * @return
     */
    public ValidateCodeProcessor findValidateCodeProcessor(String type) {
        /** 验证码处理器的组件(Bean)名称定义规范,如:imageValidateCodeProcessor,smsValidateCodeProcessor,emailValidateCodeProcessor--type + ValidateCodeProcessor */
        String name = type.toLowerCase() + ValidateCodeProcessor.class.getSimpleName();

        ValidateCodeProcessor processor = validateCodeProcessors.get(name);

        if(null == processor) {
            throw new ValidateCodeException("验证码处理器:" + name + "不存在");
        }

        return processor;
    }

    /**
     * 通过验证码的类型从容器中获取对应的验证码处理器
     * @param type
     * @return
     */
    public ValidateCodeProcessor findValidateCodeProcessor(ValidateCodeType type) {
        String codeType = type.toString().toLowerCase();
        return this.findValidateCodeProcessor(codeType);
    }

}
