package site.amcu.amcuweb.browser.controller;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.web.bind.annotation.*;
import site.amcu.amcuweb.entity.Article;
import site.amcu.amcuweb.entity.ArticleCategory;
import site.amcu.amcuweb.service.ArticleCategoryService;
import site.amcu.amcuweb.service.ArticleService;
import site.amcu.amcuweb.support.ArticleStatusSupport;
import site.amcu.amcuweb.vo.ArticleVO;
import site.amcu.amcuweb.vo.Response;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:    文章操作相关控制器
 * @Author: Ben-Zheng
 * @Date: 2018/12/12 15:38
 */
@RestController
@RequestMapping("/article")
public class ArticlesController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ArticleCategoryService categoryService;

    @Autowired
    private ArticleService articleService;

    @RolesAllowed({"USER", "ADMIN"})
    @GetMapping("/categories")
    public ResponseEntity<Response> getCategories() {
        List<ArticleCategory> list = categoryService.findAllArticleCategories();
        return (0 != list.size()) ?
            ResponseEntity.ok().body(new Response(true, "", HttpStatus.OK.value(), list)) :
                ResponseEntity.ok().body(new Response(false, "获取文章分类信息异常!", HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }

    @RolesAllowed({"USER", "ADMIN"})
    @PostMapping("/save-scratch")
    public ResponseEntity<Response> saveOrUpdateArticleAsScratch(Article article, Authentication auth) {
        int scratchId = 0;
        Map<String, Object> res = new HashMap<>();
        article.setUserId(Integer.parseInt(((SocialUserDetails) auth.getPrincipal()).getUserId()));
        if(article.getId() == null) {
            scratchId = articleService.saveArticleAsScratch(article);
        } else {
            scratchId = articleService.updateScratch(article);
        }

        if(-1 == scratchId) {
            return ResponseEntity.ok().body(new Response(false, "草稿保存失败,请稍后再试!", HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }

        res.put("id", scratchId);
        res.put("status", ArticleStatusSupport.ARTICLE_AS_SCRATCH);
        res.put("content", ArticleStatusSupport.ARTICLE_AS_SCRATCH_CONTENT);

        return ResponseEntity.ok().body(new Response(true, "", HttpStatus.OK.value(), res));
    }

    @RolesAllowed({"USER", "ADMIN"})
    @PostMapping("/release-audit")
    public ResponseEntity<Response> articleRelease(Article article, Authentication auth) {
        int releaseId = 0;
        Map<String, Object> res = new HashMap<>();
        article.setUserId(Integer.parseInt(((SocialUserDetails) auth.getPrincipal()).getUserId()));
        if(article.getId() == null) {
            releaseId = articleService.saveArticleAndRelease(article);
        } else {
            releaseId = articleService.updateArticleToRelease(article);
        }

        if(-1 == releaseId) {
            return ResponseEntity.ok().body(new Response(false, "文章发表失败,请稍后再试!", HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }

        res.put("aid", releaseId);
        res.put("uid", article.getUserId());

        return ResponseEntity.ok().body(new Response(true, "", HttpStatus.OK.value(), res));
    }



    @RolesAllowed({"USER", "ADMIN"})
    @GetMapping("/modify")
    public ResponseEntity<Response> reloadArticle2Modify(@RequestParam(value = "type") String etype, @RequestParam(value = "aid") String aid) {
        ArticleVO vo = null;
        if(StringUtils.equalsIgnoreCase(etype, "am") &&
                StringUtils.isNumeric(aid)) {
            vo = articleService.findUserArticle2ModifyById(Integer.parseInt(aid));
            if(vo.getArticle().getCategoryId() != null) {
                ArticleCategory ac = categoryService.findArticleCategoryById(vo.getArticle().getCategoryId());
                Map<String, Object> exts = new HashMap<>();
                exts.put("acvalue", ac.getName());
                vo.setExts(exts);
            }
        }
        return null == vo ?
                ResponseEntity.ok().body(new Response(false, "该文章查找失败!无法修改!", HttpStatus.INTERNAL_SERVER_ERROR.value())) :
                ResponseEntity.ok().body(new Response(true, "", HttpStatus.OK.value(), vo));
    }

    @PermitAll
    @GetMapping
    public ResponseEntity<Response> loadArticleContent(@RequestParam(value = "aid") String aid,
                                                       @RequestParam(value = "auid") String auid,
                                                       @RequestParam(value = "view") String view,
                                                       Authentication auth) {
        ArticleVO vo = null;
        if(StringUtils.isNumeric(aid) && StringUtils.isNumeric(auid) && StringUtils.isNotEmpty(view)) {

            int authorId = articleService.findAuthorIdById(Integer.parseInt(aid));

            if(!StringUtils.equals(authorId + "", auid)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response(false, "错误请求"));
            }

            if(auth != null && auth.isAuthenticated() &&
                    !auth.getPrincipal().toString().equalsIgnoreCase("anonymousUser")) {
                if(!StringUtils.equals(authorId + "", ((SocialUserDetails)auth.getPrincipal()).getUserId())) {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Response(false, "未经授权的访问!"));
                }

                if(StringUtils.equalsIgnoreCase(view, "release-audit")) {
                    vo = articleService.findUserAuditArticleById(Integer.parseInt(aid));
                } else if(StringUtils.equalsIgnoreCase(view, "scratch")) {
                    vo = articleService.findUserScratchArticleById(Integer.parseInt(aid));
                }
            }

            if(null == vo && StringUtils.equalsIgnoreCase(view, "release")) {
                vo = articleService.findUserReleaseArticleById(Integer.parseInt(aid));
            }

            if(vo != null && vo.getArticle().getCategoryId() != null) {
                ArticleCategory ac = categoryService.findArticleCategoryById(vo.getArticle().getCategoryId());
                Map<String, Object> exts = new HashMap<>();
                exts.put("category", ac.getName());
                vo.setExts(exts);
            }
        }

        return null == vo ?
                ResponseEntity.ok().body(new Response(false, "该文章查找失败!无法查看!", HttpStatus.INTERNAL_SERVER_ERROR.value())) :
                ResponseEntity.ok().body(new Response(true, "", HttpStatus.OK.value(), vo));
    }

}
