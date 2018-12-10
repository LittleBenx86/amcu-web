package site.amcu.amcuweb.service;

import site.amcu.amcuweb.entity.UserPersonalInfo;

/**
 * @Description:    用户个人信息数据服务层接口
 * @Author: Ben-Zheng
 * @Date: 2018/11/13 9:35
 */
public interface UserPersonalInfoService {

    /**
     * 创建用户的个人信息记录(注册时创建)
     * @param upi
     * @return
     */
    int createUserPersonalInfo(UserPersonalInfo upi);

    /**
     * 更新用户个人信息(根据主键更新)
     * @param upi
     * @return
     */
    int updateUserPersonalInfo(UserPersonalInfo upi);

    /**
     * 根据userId获取用户个人信息
     * @param userId
     * @return
     */
    UserPersonalInfo findUserPersonalInfoByUserId(Integer userId);

}
