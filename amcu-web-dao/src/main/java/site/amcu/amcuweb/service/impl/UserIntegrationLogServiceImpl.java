package site.amcu.amcuweb.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import site.amcu.amcuweb.dao.UserIntegrationLogMapper;
import site.amcu.amcuweb.entity.UserIntegrationLog;
import site.amcu.amcuweb.service.UserIntegrationLogService;

import javax.annotation.Resource;

/**
 * @Description:    用户积分获取数据服务层接口实现
 * @Author: Ben-Zheng
 * @Date: 2018/11/13 12:17
 */
@Service
@Transactional(propagation = Propagation.NESTED, isolation = Isolation.DEFAULT, readOnly = false)
public class UserIntegrationLogServiceImpl implements UserIntegrationLogService {

    @Resource
    private UserIntegrationLogMapper uilMapper;

    @Override
    public int createAnIntegrationLog(UserIntegrationLog log) {
        int num = uilMapper.insert(log);
        return num;
    }

}
