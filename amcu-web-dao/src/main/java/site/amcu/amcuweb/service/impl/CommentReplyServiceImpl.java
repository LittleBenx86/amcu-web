package site.amcu.amcuweb.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import site.amcu.amcuweb.service.CommentReplyService;

/**
 * @Description:    对评论(回复)的回复数据服务层接口实现
 * @Author: Ben-Zheng
 * @Date: 2018/11/13 15:59
 */
@Service
@Transactional(propagation = Propagation.NESTED, isolation = Isolation.DEFAULT, readOnly = false)
public class CommentReplyServiceImpl implements CommentReplyService {
    
}
