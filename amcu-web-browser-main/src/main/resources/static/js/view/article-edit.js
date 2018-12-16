/**
 * 文章编辑
 * author: Ben Zheng
 */

/******** vue数据控制 ********/

let usrNavWrapperVM = new Vue({
    el : '#userNavWrapper',
    data: {
        isLogin : false,
        curUsrAvatar : "img/avatar/5_1.png",
        curUsername : "",
        curUserEmail : "",
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
                        toastr.error(result.data.msg + "\n请重新登录!", "Sorry!");
                        _this.isLogin = false;
                    }
                }
            }).catch((xhr) => {
                toastr.error("用户信息获取异常,请重新登录", "Sorry!");
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
            toastr.info("登录异常!");
            _this.isLogin = false;
        });
    }
});

let articleEditVM = new Vue({
    el : "#articleEdit",
    data : {
        articleId : null,
        isCommentEnable : true,
        editor : null,
        title : null,
        category : null,
        diyCategory : null,
        articleStatusContent : "还未开始写作",
        articleStatusColor : "label-warning",
        eType : null,
    },
    methods : {
        querySysCategoriesEvent() {
            return axios.get("/article/categories").then((result) => {
                if(200 === result.data.statusCode) {
                    return result.data.respBody;
                } else if(500 === result.data.statusCode) {
                    toastr.error(result.data.msg, "Sorry!");
                }
            }).catch((xhr) => {
                toastr.error("获取文章主分类出错", "Sorry!");
            });
        },
        saveAsScratchEvent() {
            if(this.isEditorNormal() &&
                this.validateTitleIsCorrect()) {
                let _this = this;
                let data = Qs.stringify({
                    'id' : _this.articleId,
                    'title': _this.title,
                    'text' : _this.editor.getValue(),
                    'categoryId' : _this.category,
                    'diyCategory' : _this.diyCategory.val(),
                    'commentOpen' : _this.isCommentEnable === true ? 1 : 0,
                });
                axios.post("/article/save-scratch", data, {
                    headers:{
                        'Content-Type':'application/x-www-form-urlencoded;charset=utf-8',
                    },
                }).then((result) => {
                    if(result.data.statusCode === 200) {
                        _this.articleId = result.data.respBody.id;
                        _this.setArticleStatusColorByStatus(result.data.respBody.status);
                        _this.articleStatusContent = result.data.respBody.content;
                        toastr.success("草稿保存成功", "Congratulate");
                    } else if(result.data.statusCode === 500) {
                        toastr.error(result.data.msg, "Sorry");
                    }
                }).catch((xhr) => {
                    toastr.error("草稿保存失败", "Request Error");
                });
            }
        },
        releaseArticleEvent() {
            if(this.isEditorNormal() &&
                this.validateTitleIsCorrect() &&
                this.validateCategoryIsCorrect() &&
                this.validateDiyCategoryIsCorrect()) {
                let _this = this;
                let data = Qs.stringify({
                    'id' : _this.articleId,
                    'title': _this.title,
                    'text' : _this.editor.getValue(),
                    'categoryId' : _this.category,
                    'diyCategory' : _this.diyCategory.val(),
                    'commentOpen' : _this.isCommentEnable === true ? 1 : 0,
                });
                axios.post("/article/release-audit", data, {
                    headers:{
                        'Content-Type':'application/x-www-form-urlencoded;charset=utf-8',
                    },
                }).then((result) => {
                    if(result.data.statusCode === 200) {
                        toastr.success("文章发表成功!\n等待审核中,可前往预览", "Congratulate");
                        timeoutRelocateToTarAddr("/content/article.html?aid=" + result.data.respBody.aid + "&auid="
                            + result.data.respBody.uid + "&view=release-audit", 1500);
                    } else if(result.data.statusCode === 500) {
                        toastr.error(result.data.msg, "Sorry");
                    }
                }).catch((xhr) => {
                    toastr.error("请求错误!文章发表失败!", "Request Error");
                });
            }
        },
        articleModifyReloadDomEvent(e, i, t, slc, tags, s) {
            let _this = articleEditVM;
            axios.get("/article/modify", {
                params : {
                    "aid" : i,
                    "type" : t,
                }
            }).then((result) => {
                if(200 === result.data.statusCode) {
                    let article = result.data.respBody.article;
                    let exts = result.data.respBody.exts;
                    _this.articleId = article.id;
                    _this.setArticleStatusColorByStatus(article.status);
                    _this.articleStatusContent = result.data.respBody.articleStatusContent;
                    _this.title = article.title;
                    _this.isCommentEnable = (article.commentOpen === 1);
                    s.bootstrapSwitch('state', _this.isCommentEnable);
                    if(article.categoryId != null) {
                        slc.on('loaded.bs.select', function () {
                            slc.html('');
                            slc.append('<option value='+ article.categoryId +'>' + exts.acvalue + '</option>');
                            slc.selectpicker('refresh');
                        });
                    }
                    tags.tagsinput('add', article.diyCategory);
                    e.setValue(article.text);
                } else {
                    toastr.error(result.data.msg, "Sorry");
                    timeoutRelocateToIndex(1200);
                }
            }).catch((xhr) => {
                toastr.error("请求错误/Internal Server Error!无法修改!", "Sorry");
                timeoutRelocateToIndex(1200);
            });
        },
        validateCategoryIsCorrect() {
            if(this.category === null || isNaN(this.category) || this.category === "") {
                toastr.error("文章主分类没有选择!", "Error");
                return false;
            }
            return true;
        },
        isEditorNormal() {
            if(this.editor === null) {
                toastr.error("获取编辑器文本异常!", "Sorry");
                return false;
            }
            return true;
        },
        validateDiyCategoryIsCorrect() {
            if(this.diyCategory === null || this.diyCategory.length === 0) {
                toastr.error("请制定该文章的标签!", "Sorry");
                return false;
            }
            return true;
        },
        validateTitleIsCorrect() {
            if(this.title === null || this.title.length === 0) {
                toastr.error("请编写文章标题!", "Sorry");
                return false;
            }
            return true;
        },
        setArticleStatusColorByStatus(articleStatus) {
            if(articleStatus === 1 || articleStatus === 2) {
                this.articleStatusColor = "label-warning";
            } else if(articleStatus === 3 || articleStatus === 6) {
                this.articleStatusColor = "label-success";
            } else {
                this.articleStatusColor = "label-danger";
            }
        },
    },
    created() {
        let editType = getUrlParam("type"), aid = getUrlParam("aid");
        if(editType === "am" && !isNaN(aid) && aid !== null) {
            this.eType = "am";
            this.id = aid;
        } else if(editType === null && aid === null) {

        } else {
            relocateTo400Immediately();
        }
    },
});

/******** dom操作 ********/

$(function() {

    let $sysCategorySelect = $('#sysCategorySelect'), $diyTags = $('#diy-tags'), $switch = $("input#commentSwitch");

    stickySidebar(".right-sidedar", 10);

    /******** switch ********/
    $switch.bootstrapSwitch({
        onText:"开启",
        offText: "关闭",
        onColor: "success",
        offColor: "warning",
        size : "mini",
        state : articleEditVM.isCommentEnable,
        onSwitchChange : function(event, state){
            articleEditVM.isCommentEnable = state;
        }
    });

    /******** simditor ********/

    articleEditVM.editor = new Simditor({
        textarea : $("#editor"),
        placeholder : '请开始你的创作',
        pasteImage : true,
        defaultImage : "",
        toolbarFloat : true,
        toolbar : ['title', 'bold', 'mark', 'italic', 'underline', 'fontScale', 'color', '|',
            'blockquote','indent', 'outdent', 'alignment', 'ol', 'ul', 'hr', '|',
            'checklist', 'code', 'table', 'link', 'image', '|',
            'markdown', 'html', 'fullscreen'],
        cleanPaste : false,
        markdown: false,
        upload : {
            url : "http://127.0.0.1:8976/upload",
            fileKey : "file",
            connectionCount : 3,
            leaveConfirm : "正在上传文件，如果离开上传会自动取消"
        },
    });

    if(articleEditVM.editor !== null &&
        articleEditVM.eType === "am") {
        articleEditVM.$options.methods.articleModifyReloadDomEvent(articleEditVM.editor, articleEditVM.id,
            articleEditVM.eType, $sysCategorySelect, $diyTags, $switch);
    }

    /******** diy tags ********/

    $diyTags.tagsinput({
        maxTags : 6,
        maxChars : 10,
        trimValue : true,
    });
    articleEditVM.diyCategory = $diyTags;

    $sysCategorySelect.on('shown.bs.select', function () {
        articleEditVM.querySysCategoriesEvent().then((cs) => {
            $sysCategorySelect.html('');
            $sysCategorySelect.append('<option value="" disabled selected>请选择主分类</option>');
            $.each(cs, (i) => {
                $sysCategorySelect.append('<option value='+ cs[i].id +'>' + cs[i].name + '</option>');
            });
            $sysCategorySelect.selectpicker('refresh');
        });
    });

    /******** 函数定义 ********/

});
