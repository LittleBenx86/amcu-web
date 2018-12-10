package site.amcu.amcuweb.service;

import site.amcu.amcuweb.entity.UserEdu;

/**
 * @Description:    用户教育背景数据服务层接口
 * @Author: Ben-Zheng
 * @Date: 2018/11/13 9:56
 */
public interface UserEduService {

    /**
     * 创建用户教育背景记录(注册成功时创建)
     * @param ue
     * @return
     */
    int createUserEdu(UserEdu ue);

    /**
     * 更新用户教育背景(根据主键更新)
     * @param ue
     * @return
     */
    int updateUserEdu(UserEdu ue);

    /**
     * 根据用户id查找用户教育背景记录
     * @param userId
     * @return
     */
    UserEdu findUserEduByUserId(Integer userId);

}
