package site.amcu.amcuweb.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import site.amcu.amcuweb.service.AreaService;

/**
 * @Description:    省/市/区数据服务层接口实现
 * @Author: Ben-Zheng
 * @Date: 2018/11/12 16:25
 */
@Service
@Transactional(propagation = Propagation.NESTED, isolation = Isolation.DEFAULT, readOnly = false)
public class AreaServiceImpl implements AreaService {

}
