package site.amcu.amcuweb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import site.amcu.amcuweb.dao.UserPersonalInfoMapper;
import site.amcu.amcuweb.entity.UserPersonalInfo;
import site.amcu.amcuweb.service.UserPersonalInfoService;

import javax.annotation.Resource;

/**
 * @Description:    用户个人信息数据服务层接口实现
 * @Author: Ben-Zheng
 * @Date: 2018/11/13 9:37
 */
@Service
@Transactional(propagation = Propagation.NESTED, isolation = Isolation.DEFAULT, readOnly = false)
public class UserPersonalInfoServiceImpl implements UserPersonalInfoService {

    @Resource
    private UserPersonalInfoMapper upiMapper;

    @Override
    public int createUserPersonalInfo(UserPersonalInfo upi) {
        int num = upiMapper.insert(upi);
        return num;
    }

    @Override
    public int updateUserPersonalInfo(UserPersonalInfo upi) {
        int num = upiMapper.updateById(upi);
        return num;
    }

    @Transactional(readOnly = true)
    @Override
    public UserPersonalInfo findUserPersonalInfoByUserId(Integer userId) {
        UserPersonalInfo upi = upiMapper.selectOne(new QueryWrapper<UserPersonalInfo>().lambda().eq(UserPersonalInfo::getUserId, userId));
        return upi;
    }
}
