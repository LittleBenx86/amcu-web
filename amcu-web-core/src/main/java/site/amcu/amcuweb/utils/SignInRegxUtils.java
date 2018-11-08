package site.amcu.amcuweb.utils;

import org.springframework.beans.factory.annotation.Autowired;
import site.amcu.amcuweb.properties.SecurityProperties;
import site.amcu.amcuweb.properties.SignInRegxProperties;
import site.amcu.amcuweb.support.SignInTypeSupport;

import java.util.regex.Pattern;

/**
 * @Description:    登录用户名正则匹配工具
 * @Author: Ben-Zheng
 * @Date: 2018/11/05 20:07
 */
public final class SignInRegxUtils {

    public static int getSignInTypeByRegx(String condtion) {
        SignInRegxProperties regxs = new SignInRegxProperties();

        if(Pattern.compile(regxs.getMobileRegx()).matcher(condtion).matches()) {
            return SignInTypeSupport.FORM_USER_MOBILE_SIGNIN_TYPE;
        } else if(Pattern.compile(regxs.getEmailRegx()).matcher(condtion).matches()) {
            return SignInTypeSupport.FORM_USER_EMAIL_SIGNIN_TYPE;
        } else if(!Pattern.compile(regxs.getUsernameRegx()).matcher(condtion).find()) {
            return SignInTypeSupport.FORM_USERNAME_SIGNIN_TYPE;
        }
        return -1;
    }

}
