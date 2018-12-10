package site.amcu.amcuweb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import site.amcu.amcuweb.dao.UserEduMapper;
import site.amcu.amcuweb.entity.UserEdu;
import site.amcu.amcuweb.service.UserEduService;

import javax.annotation.Resource;

/**
 * @Description:    用户教育背景数据服务层接口实现
 * @Author: Ben-Zheng
 * @Date: 2018/11/13 9:57
 */
@Service
@Transactional(propagation = Propagation.NESTED, isolation = Isolation.DEFAULT, readOnly = false)
public class UserEduServiceImpl implements UserEduService {

    @Resource
    private UserEduMapper ueMapper;

    @Override
    public int createUserEdu(UserEdu ue) {
        int num = ueMapper.insert(ue);
        return num;
    }

    @Override
    public int updateUserEdu(UserEdu ue) {
        int num = ueMapper.updateById(ue);
        return num;
    }

    @Transactional(readOnly = true)
    @Override
    public UserEdu findUserEduByUserId(Integer userId) {
        UserEdu ue = ueMapper.selectOne(new QueryWrapper<UserEdu>().lambda().eq(UserEdu::getUserId, userId));
        return ue;
    }
}
