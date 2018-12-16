/**
 * author: Ben Zheng
 * date: 2018-11-23
 */

/******** vue数据控制 ********/

Vue.directive('highlight', function (el) {
    let blocks = el.querySelectorAll('pre code');
    blocks.forEach((block) => {
        hljs.lineNumbersBlock(block);
        hljs.highlightBlock(block);
    });
});

let usrNavWrapperVM = new Vue({
    el : '#userNavWrapper',
    data: {
        isLogin : false,
        curUsrAvatar : "img/avatar/5_1.png",
        curUsername : "测试人员",
        curUserEmail : "123456@163.com",
        curUserCenterUrl : "javascript:void(0)",
    },
    methods : {
        getLoginUserInfo : function(userId) {
            let _this = this;
            axios.get('/usr/signin-usr?userId=' + userId).then(function(result){
                if(200 === result.status){
                    if(200 === result.data.statusCode) {
                        _this.isLogin = true;
                        _this.curUsrAvatar = result.data.respBody.avatar == null ?
                            _this.curUsrAvatar :
                            result.data.respBody.avatar;
                        _this.curUsername = result.data.respBody.username;
                        _this.curUserEmail = result.data.respBody.email;
                        _this.curIntegrations = 0;
                    } else if(500 === result.data.statusCode) {
                        toastr.error(result.data.msg + "\n请重新登录!", "提示");
                        _this.isLogin = false;
                    }
                }
            }).catch((xhr) => {
                toastr.error("用户信息获取异常,请重新登录", "警告");
                _this.isLogin = false;
            });
        }
    },
    created : function () {
        let _this = this;
        axios.get('/auth/usr-enduring').then((result) => {
            if(200 === result.status){
                if(200 === result.data.statusCode) {
                    _this.getLoginUserInfo(result.data.respBody.userId);
                } else if(500 === result.data.statusCode) {
                    toastr.error(result.data.msg + "\n请重新登录!", "提示");
                    _this.isLogin = false;
                } else if(404 === result.data.statusCode) {
                    toastr.warn(result.data.msg, "Sorry!");
                    _this.isLogin = false;
                }
            }
        }).catch((xhr) => {
            toastr.info("未登录!");
            _this.isLogin = false;
        });
    }
});

let articleMainVM = new Vue({
    el : "#articleMain",
    data : {
        /** author info */
        author : "加载中...",
        integrations : 100,
        /** article content */
        articleTitle : "加载中...",
        articleCategory : "加载中...",
        articleTags : null,
        articlePassAuditTime : "加载中...",
        articleContent : "<p>加载中...</p>",
        articleAllowComment : true,
        /** article exts */
        articleVotes : "100",
        articleCollects : "100",
        articleComments : "100",
        articleViews : "100",
    },
    created() {
        let aid = getUrlParam("aid"), auid = getUrlParam("auid"), view = getUrlParam("view");
        if(aid !== null && !isNaN(aid) &&
            auid !== null && !isNaN(auid) &&
            view !== null) {
            let _this = this;
            _this.loadArticleContent(aid, auid, view).then((data) => {
                if(data !== null && data !== undefined) {
                    _this.articleTitle = data.article.title;
                    _this.articleCategory = data.exts.category;
                    _this.articlePassAuditTime = dateFormate("yyyy-MM-dd HH:mm:ss", new Date(data.article.updateTime));
                    _this.articleContent = data.article.text;
                    _this.articleAllowComment = (data.article.commentOpen === 1);
                    _this.articleTags = data.article.diyCategory.split(",");
                }
            });
        } else {
            relocateTo400Immediately();
        }
    },
    methods : {
        loadArticleContent(aid, uid, v) {
            return axios.get("/article", {
                params : {
                    "aid" : aid,
                    "auid" : uid,
                    "view" : v,
                },
            }).then((result) => {
                if(200 === result.data.statusCode){
                    return result.data.respBody;
                } else if(500 === result.data.statusCode) {
                    relocateTo400Immediately();
                }
            }).catch((xhr) => {
                relocateTo400Immediately();
            });
        },
    },
});

$(function(){

    stickySidebar(".left-sidebar", 10);

    ArticleDirectory.createArticleDirectory("singleContent","h2","h3",46);

    $("[data-toggle='tooltip']").tooltip();

});