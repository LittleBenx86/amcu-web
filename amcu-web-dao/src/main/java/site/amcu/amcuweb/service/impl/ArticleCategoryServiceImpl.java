package site.amcu.amcuweb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import site.amcu.amcuweb.dao.ArticleCategoryMapper;
import site.amcu.amcuweb.entity.ArticleCategory;
import site.amcu.amcuweb.service.ArticleCategoryService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description:
 * @Author: Ben-Zheng
 * @Date: 2018/11/13 12:30
 */
@Service
@Transactional(propagation = Propagation.NESTED, isolation = Isolation.DEFAULT, readOnly = false)
public class ArticleCategoryServiceImpl implements ArticleCategoryService {

    @Resource
    private ArticleCategoryMapper categoryMapper;

    @Override
    public List<ArticleCategory> findAllArticleCategories() {
        List<ArticleCategory> clist = categoryMapper.selectList(new QueryWrapper<ArticleCategory>());
        return clist;
    }

    @Override
    public ArticleCategory findArticleCategoryById(Integer categoryId) {
        ArticleCategory ac = categoryMapper.selectOne(new QueryWrapper<ArticleCategory>().lambda().eq(ArticleCategory::getId, categoryId));
        return ac;
    }
}
