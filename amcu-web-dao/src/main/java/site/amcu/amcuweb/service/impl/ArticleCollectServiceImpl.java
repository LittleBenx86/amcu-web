package site.amcu.amcuweb.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import site.amcu.amcuweb.service.ArticleCollectService;

/**
 * @Description:    文章收藏(文章和收藏者)关联数据服务层接口实现
 * @Author: Ben-Zheng
 * @Date: 2018/11/13 14:48
 */
@Service
@Transactional(propagation = Propagation.NESTED, isolation = Isolation.DEFAULT, readOnly = false)
public class ArticleCollectServiceImpl implements ArticleCollectService {

}
