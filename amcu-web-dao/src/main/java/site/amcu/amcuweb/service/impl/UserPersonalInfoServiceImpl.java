package site.amcu.amcuweb.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import site.amcu.amcuweb.service.UserPersonalInfoService;

/**
 * @Description:    用户个人信息数据服务层接口实现
 * @Author: Ben-Zheng
 * @Date: 2018/11/13 9:37
 */
@Service
@Transactional(propagation = Propagation.NESTED, isolation = Isolation.DEFAULT, readOnly = false)
public class UserPersonalInfoServiceImpl implements UserPersonalInfoService {



}
