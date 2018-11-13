package site.amcu.amcuweb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import site.amcu.amcuweb.dao.UserMapper;
import site.amcu.amcuweb.entity.User;
import site.amcu.amcuweb.service.UserService;
import site.amcu.amcuweb.support.SignInTypeSupport;

import javax.annotation.Resource;

/**
 * @Description:    用户数据服务层接口实现
 * @Author: Ben-Zheng
 * @Date: 2018/11/05 10:48
 */
@Service
@Transactional(propagation = Propagation.NESTED, isolation = Isolation.DEFAULT, readOnly = false)
public class UserServiceImpl implements UserService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public int addNewUser(User user) {
        user.setPassword(this.encodePassword(user.getPassword()));
        int num = userMapper.insert(user);
        return num;
    }

    @Override
    public int socialUserRegist(User user) {
        userMapper.insert(user);
        return user.getId();
    }

    @Transactional(readOnly = true)
    @Override
    public User findByLoginCondition(String condition, int type) {

        User usr = new User();

        switch (type) {
            case SignInTypeSupport.FORM_USERNAME_SIGNIN_TYPE :
                usr.setUsername(condition);
                break;
            case SignInTypeSupport.FORM_USER_MOBILE_SIGNIN_TYPE :
                usr.setMobile(condition);
                break;
            case SignInTypeSupport.FORM_USER_EMAIL_SIGNIN_TYPE :
                usr.setEmail(condition);
                break;
            default:
                return null;
        }

        usr = userMapper.selectOne(new QueryWrapper<User>(usr));

        logger.info("开发阶段:通过登录信息查找用户:" + usr.toString());

        return usr;
    }

    @Transactional(readOnly = true)
    @Override
    public User findBySocialUserId(Integer userId) {
        User usr = userMapper.selectById(userId);
        logger.info("开发阶段:通过第三方登录用户的信息:" + usr.toString());
        return usr;
    }

    @Override
    public String encodePassword(String password) {
        String encodePassword = passwordEncoder.encode(password);
        return encodePassword;
    }


}
