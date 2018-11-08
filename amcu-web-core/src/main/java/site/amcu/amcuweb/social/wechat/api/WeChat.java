package site.amcu.amcuweb.social.wechat.api;

/**
 * @Description:    获取微信用户信息的接口规范
 *                  wechat的openId是直接同时和accessToken携带传输过来的；属于多用户多对象的，每个对象都是不一致的
 *                  wechat并不是标准的oauth2.0协议实现
 * @Author: Ben-Zheng
 * @Date: 2018/11/06 20:07
 */
public interface WeChat {

    /**
     * 获取微信用户信息的接口
     * @param openId
     * @return
     */
    WeChatUserInfo getUserInfo(String openId);

}
