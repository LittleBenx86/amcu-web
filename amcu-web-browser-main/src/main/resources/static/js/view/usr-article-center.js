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
                if(200 == result.status){
                    this.isLogin = true;
                    console.info(result.body.respBody);
                    this.curUsrAvatar = result.body.respBody.avatar == null ? this.curUsrAvatar : result.body.respBody.avatar;
                    this.curUsername = result.body.respBody.username;
                    this.curUserEmail = result.body.respBody.email;
                    this.curIntegrations = 0;
                } else if(500 == result.status) {
                    toastr.error(result.body.msg + "\n请重新登录!");
                    this.isLogin = false;
                }
            }, function(xhr){
                toastr.error("用户信息获取异常,请重新登录");
                this.isLogin = false;
            });
        }
    },
    created : function () {
        this.$http.get('/auth/usr-enduring').then(function(result){
            if(200 == result.status){
                console.info(result.body.respBody)
                this.getLoginUserInfo(result.body.respBody.userId);
            } else if(500 == result.status) {
                toastr.error(result.body.msg + "\n请重新登录!");
                this.isLogin = false;
            }
        }, function(xhr) {
            toastr.info("未登录!");
            this.isLogin = false;
        });
    }
});

$(function () {

    stickySidebar(".left-sidebar", 10);

    $("#search-tabs a").click(function (e) {
        e.preventDefault();
        $(this).tab('show');
        if(this.attributes[0].value == '#search-per-article') {
            $("a#cur-type").text("个人文章");
        } else if(this.attributes[0].value == '#search-collect-article') {
            $("a#cur-type").text("收藏文章");
        }
    });

    showTarTabView(getUrlParam('type'));

    /******** 函数定义 ********/
    function showTarTabView(type) {
        $("#search-tabs a[href='#" + type + "']").tab('show');
    }

});