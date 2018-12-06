/**
 * 用户个人中心
 * author: Ben Zheng
 * date: 2018-11-28
 */

/******** vue数据控制 ********/

var usrCenterVM = new Vue({
    el : "#vUsrCenter",
    data : {
        isCurUser: false,
        isLogin : false,
        isAMCUer : false,
        isADMIN : false,
        isQQBinded : false,
        isWxBinded : false,
        isLkBinded : false,
        isGithubBinded : false,
        isCodingBinded : false,
        hasPriorityView : false,
        hasFollowing : false,
        curUserId : null,
        tarUserId : null,
        curUserAvatar : "img/avatar/5_1.png",
        tarUserAvatar : "img/avatar/5_1.png",
        curUsername : "测试人员",
        tarUsername : "测试作者",

    },
    methods : {
        getCurLoginUserInfo : function(userId) {
           axios.get('/usr/signin-usr?userId=' + userId).then(function(result){
                if(200 === result.status){
                    if(200 === result.data.statusCode) {
                        usrCenterVM.isLogin = true;
                        usrCenterVM.isCurUser = true;
                        usrCenterVM.curUserId = userId;
                        usrCenterVM.curUserAvatar = (result.data.respBody.avatar == null ?
                            usrCenterVM.curUserAvatar :
                            result.data.respBody.avatar);
                        usrCenterVM.curUsername = result.data.respBody.username;
                        usrCenterVM.curIntegrations = 0;
                        usrCenterVM.hasPriorityView = true;

                        usrCenterVM.$options.methods.getOAuthBindingInfo();

                    } else if(500 === result.data.statusCode) {
                        toastr.error(result.data.msg + "\n请重新登录!", "提示");
                        this.isLogin = false;
                    }
                }
            }, function(xhr){
                toastr.error("用户信息获取异常,请重新登录");
                timeoutRelocateToIndex(1500);
            });
        },
        getOAuthBindingInfo : function() {
            axios.get("/connect").then(function(result){
                var data = result.data;
                usrCenterVM.isQQBinded = data["callback.do"];
                usrCenterVM.isWxBinded = data["weixin"];
                usrCenterVM.isLkBinded = data["linkedin"];
                usrCenterVM.isGithubBinded = data["github"];
                usrCenterVM.isCodingBinded = data["coding"];

            }, function(xhr){

            });
        },
        getTarUserInfo : function(tuid) {
            var _this = this;
            axios.get('/usr/other-usr?tuid=' + tuid).then(function(result){
                if(200 == result.status){
                    if(200 == result.data.statusCode){
                        _this.isLogin = false;
                        _this.isCurUser = false;
                        _this.tarUserAvatar = (result.data.respBody.avatar == null ?
                            _this.tarUserAvatar :
                            result.data.respBody.avatar);
                        _this.tarUsername = result.data.respBody.username;
                        _this.hasPriorityView = false;
                    } else if(500 == result.data.statusCode) {
                        toastr.error(result.data.msg + "\n请检查请求链接!", "提示");
                        timeoutRelocateToIndex(1500);
                    }
                }
            }, function(xhr){
                relocateToIndexImmediately();
            });
        },

    },
    created : function() {
       var tuid = getUrlParam("tuid");
       if(null == tuid || undefined === tuid || isNaN(tuid)) {
           axios.get('/auth/usr-enduring').then(function(result){
               if(200 === result.status){
                   if(200 === result.data.statusCode){
                       usrCenterVM.$options.methods.getCurLoginUserInfo(result.data.respBody.userId);
                   } else if(500 === result.data.statusCode) {
                       usrCenterVM.isLogin = false;
                       toastr.error(result.data.msg + "\n请重新登录!");
                   } else if(404 === result.data.statusCode) {
                       usrCenterVM.isLogin = false;
                       toastr.error(result.data.msg + "\n请重新登录!", "提示");
                       timeoutRelocateToIndex(1500);
                   }
               }
           }, function(xhr) {
               toastr.error("用户信息获取异常,请重新登录");
               timeoutRelocateToIndex(1500);
           });
       } else {
           this.tarUserId = tuid;
           this.getTarUserInfo(tuid);
       }
    }
});

/******** dom初始化 ********/

$(function(){
    $("#info-tabs a").click(function (e) {
        e.preventDefault();
        $(this).tab('show');
        if(this.attributes[0].value === '#usr-info') {
            $("a#cur-type").text("个人信息");
        } else if(this.attributes[0].value === '#account-info') {
            $("a#cur-type").text("账号信息");
        } else if(this.attributes[0].value === '#oauth2-info') {
            $("a#cur-type").text("第三方账号绑定");
        } else if(this.attributes[0].value === '#auth-info') {
            $("a#cur-type").text("单片机协会认证信息");
        } else if(this.attributes[0].value === '#following-info') {
            $("a#cur-type").text("关注作者");
        } else if(this.attributes[0].value === '#follower-info') {
            $("a#cur-type").text("粉丝信息");
        }
    });

    showTarTabView(getUrlParam('type'));

    stickySidebar(".left-sidebar", 10);

    /******** 头像剪裁保存 ********/

    $("input#avatarInput.avatar-input").on('change', function (e) {
        /** 2M */
        var fileMaxSizes = 1024 * 2;
        var target = $(e.target);
        var size = target[0].files[0].size / 1024;
        if(size > fileMaxSizes) {
            toastr.error("图片过大,请重新选择图片!");
            return false;
        }
        if(!this.files[0].type.match(/image.*/)) {
            toastr.error('请选择正确的图片!');
        } else {
            var filename = document.querySelector("#avatar-name");
            var texts = document.querySelector("#avatarInput").value;
            var testend = texts.match(/[^\\]+\.[^\(]+/i);
            filename.innerHTML = testend;
        }

    });

    $(".avatar-save").on("click", function() {

        var $image = $('img.cropper-hidden');
        var data = {option:{}};
        data.option.width = 184;
        data.option.height = 184;
        data.option.fillColor = '#fff';
        var result = $image.cropper('getCroppedCanvas', data.option, undefined);
        var dataUrl = result.toDataURL("image/png");
        var formData = new FormData();
        var params = {"mediaType" : 3};
        if(dataUrl) {
            var blobBin = dataURLtoBlob(dataUrl);
            var fileType = blobBin.type.split("/")[1];
            params.fileType = fileType;
            formData.append("file", blobBin);
        }
        var dataWithType = new Blob([JSON.stringify(params)], {
            type : "application/json"
        });
        formData.append("data", dataWithType);
        avatarUploadAjax(formData);
    });

    /******** 函数定义 ********/
    function showTarTabView(type) {
        $("#info-tabs a[href='#" + type + "']").tab('show');
    }

    function avatarUploadAjax(src) {
        $.ajax({
            url: "http://127.0.0.1:8976/upload",
            data: src,
            type: "POST",
            cache: false,
            dataType: 'json',
            processData: false,
            contentType: false,
            success: function(result) {
                console.info(result.success + ":" + result.respBody)
                if(result.success){
                    $("img.usr-avatar").src = result.respBody;
                }

            }
        });
    }

    function dataURLtoBlob(dataurl) {
        var arr = dataurl.split(',');
        var mime = arr[0].match(/:(.*?);/)[1];
        var bstr = atob(arr[1].replace(/\s/g, ''));
        var n = bstr.length;
        var u8arr = new Uint8Array(n);
        while (n--) {
            u8arr[n] = bstr.charCodeAt(n);
        }
        return new Blob([u8arr], {type: mime});
    }

});