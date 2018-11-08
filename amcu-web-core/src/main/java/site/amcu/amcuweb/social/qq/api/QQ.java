package site.amcu.amcuweb.social.qq.api;

/**
 * @Description:    获取qq第三方的信息
 *                  qq第三方是按照OAuth2协议实现的
 * @Author: Ben-Zheng
 * @Date: 2018/11/05 22:21
 */
public interface QQ {

    /**
     * 获取qq第三方用户的信息
     * @return
     */
    QQUserInfo getUserInfo();

}
