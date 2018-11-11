package site.amcu.amcuweb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import site.amcu.amcuweb.entity.User;

/**
 * @Description:    用户数据服务层接口
 * @Author: Ben-Zheng
 * @Date: 2018/11/05 10:46
 */
public interface UserService {

    /**
     * 用户注册添加一个新用户
     * @param user
     * @return
     */
    int addNewUser(User user);

    /**
     * 使用第三方登录(首次)的用户添加为新用户
     * @param user
     * @return  返回主键
     */
    int socialUserRegist(User user);

    /**
     * 用户登录:用户名/手机号/邮箱等条件
     * @param condition 用户名/手机号/邮箱
     * @param type      区分用户名/手机号/邮箱的标记
     * @return
     */
    User findByLoginCondition(String condition, int type);

    /**
     * 获取第三方登录用户与当前系统中关联用户的信息
     * @param userId
     * @return
     */
    User findBySocialUserId(Integer userId);

    /**
     * 对密码进行加密
     * @param password
     */
    String encodePassword(String password);

}
