package site.amcu.amcuweb.service;

import site.amcu.amcuweb.entity.ArticleCategory;

import java.util.List;

/**
 * @Description:    文章(主)分类数据服务层接口
 * @Author: Ben-Zheng
 * @Date: 2018/11/13 12:29
 */
public interface ArticleCategoryService {

    /**
     * 获取所有的文章分类
     * @return
     */
    List<ArticleCategory> findAllArticleCategories();

    /**
     * 根据id获取文章分类名称
     * @param categoryId
     * @return
     */
    ArticleCategory findArticleCategoryById(Integer categoryId);



}
