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

/******** dom操作 ********/

$(function() {

    stickySidebar(".right-sidedar", 10);

    CurrentLocalStorage.Cache.remove("/articles/temporary/autosave");

    /******** simditor编辑器 ********/
    let  toolbars = ['title', 'bold', 'mark', 'italic', 'underline', 'fontScale', 'color', '|',
                        'blockquote','indent', 'outdent', 'alignment', 'ol', 'ul', 'hr', '|',
                        'checklist', 'code', 'table', 'link', 'image', '|',
                        'markdown', 'html', 'fullscreen'];

    let editor = new Simditor({
        textarea : $("#s-editor"),
        pasteImage : true,
        defaultImage : "",
        toolbarFloat : true,
        toolbar : toolbars,
        markdown: false,
        upload : {
            url : "http://127.0.0.1:8976/upload",
            fileKey : "file",
            connectionCount : 3,
            leaveConfirm : "正在上传文件，如果离开上传会自动取消"
        },
    });

    /******** switch ********/
    $('#comment-switch').ios6switch({
        size: 20,
        switchoffText: '关闭',
        switchonText: '开启',
        animateSpeed: 250
    });

    $('#comment-switch')[0].onchange = function() {
        console.info(validateCommontStatus());
    };

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
