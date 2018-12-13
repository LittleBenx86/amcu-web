/**
 * author: Ben Zheng
 * date: 2018-11-23
 */

/******** vue数据控制 ********/

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
                        console.info(result);
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

$(function(){

    stickySidebar(".left-sidebar", 10);

});