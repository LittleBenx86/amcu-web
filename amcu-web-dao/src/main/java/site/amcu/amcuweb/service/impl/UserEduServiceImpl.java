package site.amcu.amcuweb.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import site.amcu.amcuweb.service.UserEduService;

/**
 * @Description:    用户教育背景数据服务层接口实现
 * @Author: Ben-Zheng
 * @Date: 2018/11/13 9:57
 */
@Service
@Transactional(propagation = Propagation.NESTED, isolation = Isolation.DEFAULT, readOnly = false)
public class UserEduServiceImpl implements UserEduService {

}
