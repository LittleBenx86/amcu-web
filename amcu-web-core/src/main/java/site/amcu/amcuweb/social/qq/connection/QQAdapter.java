package site.amcu.amcuweb.social.qq.connection;

import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;
import site.amcu.amcuweb.social.qq.api.QQ;
import site.amcu.amcuweb.social.qq.api.QQUserInfo;

/**
 * @Description:    qq第三方调用的api适配器实现类,装配Connection需要的参数
 * @Author: Ben-Zheng
 * @Date: 2018/11/06 8:11
 */
public class QQAdapter implements ApiAdapter<QQ> {

    /**
     * 测试当前的api是否可用
     * true:可用
     * false:发送请求检测判断
     * @param api
     * @return
     */
    @Override
    public boolean test(QQ api) {
        return true;
    }

    /**
     * 设置连接的值
     * @param api
     * @param values    包含创建Connection所需要的所有数据项(数据来源于QQ)
     */
    @Override
    public void setConnectionValues(QQ api, ConnectionValues values) {
        QQUserInfo userInfo = api.getUserInfo();
        /** 设置显示的昵称 */
        values.setDisplayName(userInfo.getNickname());
        /** 设置头像:qq的40 *40头像一定会有 */
        values.setImageUrl(userInfo.getFigureurl_qq_1());
        /** 设置个人主页:qq无个人主页 */
        values.setProfileUrl(null);
        /** 设置服务商提供的openId */
        values.setProviderUserId(userInfo.getOpenId());
    }

    /**
     * 绑定个人主页
     * @param api
     * @return
     */
    @Override
    public UserProfile fetchUserProfile(QQ api) {
        return null;
    }

    /**
     * 主页信息更新
     * @param api
     * @param message
     */
    @Override
    public void updateStatus(QQ api, String message) {
        /** qq do nothing */
    }
}
