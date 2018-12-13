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
        /** Editor */
        isCommentEnable : true,
        editor : null,
        title : "",
    },
    methods : {
        saveAsScratchEvent() {
            if(this.editor !== null) {

            } else {
                toastr.error("获取编辑器文本异常!", "Sorry!");
            }
        },
        releaseArticleEvent() {
            if(this.editor !== null) {

            } else {
                toastr.error("获取编辑器文本异常!", "Sorry!");
            }
        },
    },
    created : () => {

    },
});

/******** dom操作 ********/

$(function() {

    stickySidebar(".right-sidedar", 10);

    /******** switch ********/
    $("input#commentSwitch").bootstrapSwitch({
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

    /******** diy tags ********/

    $('#diy-tags').tagsinput({
        maxTags : 6,
        maxChars : 16,
        trimValue : true
    });

    /******** 函数定义 ********/
    function validateCommontStatus() {
        return $('#comment-switch')[0].checked;
    }

});
