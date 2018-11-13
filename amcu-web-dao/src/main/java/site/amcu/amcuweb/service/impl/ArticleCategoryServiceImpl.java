package site.amcu.amcuweb.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import site.amcu.amcuweb.service.ArticleCategoryService;

/**
 * @Description:
 * @Author: Ben-Zheng
 * @Date: 2018/11/13 12:30
 */
@Service
@Transactional(propagation = Propagation.NESTED, isolation = Isolation.DEFAULT, readOnly = false)
public class ArticleCategoryServiceImpl implements ArticleCategoryService {



}
