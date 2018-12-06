/**
 * session失效
 * author: Ben Zheng
 * date: 2018-12-05
 */

var sessionVM = new Vue({
    el : "#vSession",
    data : {
        sessionMsg : "session失效",
        jumpLink : "/",
        jumpMsg : "点击此处回到首页",
        isShow : false,
        showMsg : "测试",
    },
    methods : {

    },
    created : function () {
        var smsg = getUrlParam("smsg");
        if(smsg.trim() === 'invalid' || smsg == null || smsg === undefined) {
            this.sessionMsg = "session过期失效";
            this.jumpLink = "/";
            this.jumpMsg = "点击此处回到首页";
        } else if(smsg.trim() === 'mutli-invalid') {
            this.sessionMsg = "session失效,有可能是并发登录（同一用户多处登陆）导致的！";
            this.jumpLink = "/signout";
            this.jumpMsg = "点击此处安全退出";
            this.isShow = true;
            var s = 3;
            /******** 函数定义 ********/
            var refer = function() {
                if (s === 0){
                    location.href = "/signout";
                }
                this.showMsg = (s + "秒后自动安全退出");
                s--;
                return refer;
            }
            setInterval(refer(), 1000);
        }
    },
});
