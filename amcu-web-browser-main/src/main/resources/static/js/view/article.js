/**
 * author: Ben Zheng
 * date: 2018-11-23
 */

/******** vue数据控制 ********/

var articleVM = new Vue({
    el : '#user-nav-wrapper',
    data: {
        isLogin : false,
        curUsrAvatar : "img/avatar/5_1.png",
        curUsername : "测试人员",
        curUserEmail : "123456@163.com",
        curUserCenterUrl : "javascript:void(0)"
    },
    methods : {
        getLoginUserInfo : function(userId) {
            this.$http.get('/usr/signin-usr?userId=' + userId).then(function(result){
                if(200 === result.status){
                    if(200 === result.statusCode) {
                        this.isLogin = true;
                        this.curUsrAvatar = result.body.respBody.avatar == null ? this.curUsrAvatar : result.body.respBody.avatar;
                        this.curUsername = result.body.respBody.username;
                        this.curUserEmail = result.body.respBody.email;
                        this.curIntegrations = 0;
                    } else if(500 === result.body.statusCode) {
                        toastr.error(result.body.msg + "\n请重新登录!", "提示");
                        this.isLogin = false;
                    }
                }
            }, function(xhr){
                toastr.error("用户信息获取异常,请重新登录", "警告");
                this.isLogin = false;
            });
        }
    },
    created : function () {
        this.$http.get('/auth/usr-enduring').then(function(result){
            if(200 === result.status){
                if(200 === result.body.statusCode) {
                    this.getLoginUserInfo(result.body.respBody.userId);
                } else if(500 === result.body.statusCode) {
                    toastr.error(result.body.msg + "\n请重新登录!", "提示");
                    this.isLogin = false;
                } else if(404 === result.body.statusCode) {
                    toastr.info(result.body.msg);
                    this.isLogin = false;
                }
            }
        }, function(xhr) {
            toastr.info("未登录!");
            this.isLogin = false;
        });
    }
});

$(function(){

    stickySidebar(".left-sidebar", 10);

});