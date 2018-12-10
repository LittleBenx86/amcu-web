package site.amcu.amcuweb.service;

import site.amcu.amcuweb.entity.UserIntegrationLog;

/**
 * @Description:    用户积分获取数据服务层接口
 * @Author: Ben-Zheng
 * @Date: 2018/11/13 12:17
 */
public interface UserIntegrationLogService {

    /**
     * 创建积分(声望)获取日志记录
     * @param log
     * @return
     */
    int createAnIntegrationLog(UserIntegrationLog log);



}
