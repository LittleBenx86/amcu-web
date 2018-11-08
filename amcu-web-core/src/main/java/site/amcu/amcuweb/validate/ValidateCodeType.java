package site.amcu.amcuweb.validate;

import site.amcu.amcuweb.properties.SecurityConstants;

/**
 * @Description:    验证码的类型:
 *                      图片验证码
 *                      手机验证码
 *                      邮箱验证码
 * @Author: Ben-Zheng
 * @Date: 2018/10/27 23:03
 */
public enum ValidateCodeType {

    /** 短信验证码枚举 */
    SMS {
        @Override
        public String getParamNameOnValidate() {
            return SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_SMS;
        }
    },
    /** 图形验证码枚举 */
    IMAGE {
        @Override
        public String getParamNameOnValidate() {
            return SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_IMAGE;
        }
    },
    /** 邮箱验证码枚举 */
    EMAIL {
        @Override
        public String getParamNameOnValidate() {
            return SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_EMAIL;
        }
    };

    /**
     * 校验时从请求中获取参数的名称
     * @return
     */
    public abstract String getParamNameOnValidate();
}
