/**
 * 用户个人中心
 * author: Ben Zheng
 * date: 2018-11-28
 */

/******** vue数据控制 ********/

Vue.directive('dom', {
    bind: (el, binding) => {
        let obj = binding.value;
        if (obj != null) {
            let key = Object.keys(binding.modifiers)[0] || "el";
            Vue.set(obj, key, el);
        }
    }
});

let usrCenterVM = new Vue({
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
        isValidOldEmail : true,
        isAccountModify : false,
        hasPriorityView : false,
        hasFollowing : false,
        startPasswordModifyByOldPassword : false,
        startPasswordModifyByEmail : false,
        curUserId : null,
        tarUserId : null,
        curUserAvatar : "img/avatar/5_1.png",
        tarUserAvatar : "img/avatar/5_1.png",
        curUsername : "测试人员",
        tarUsername : "测试作者",
        curUserEmail : null,
        curUserNewEmail : null,
        curUserSignupDate : null,
        curUserMobile : '尚未绑定手机号',
        oldBtnContent : "获取邮箱验证码",
        seconds1 : 60,
        canSentCodeOldBtnClick : true,
        newBtnContent : "获取邮箱验证码",
        seconds2 : 60,
        canSentCodeNewBtnClick : true,
        newEmailAddr : {val : ''},
        pwdEmailBtnContent : "获取邮箱验证码",
        seconds3 : 60,
        canPwdEmailBtnClick : true,
        /** user profile */
        realname : "尚未填写",
        career : "尚未填写",
        simpleProfile : "尚未填写",
        startProfileModify : false,
        /** education */
        universityAddress : "尚未填写",
        university : "尚未填写",
        collage : "尚未填写",
        major : "尚未填写",
        grade : "尚未填写",
        startEduModify : false,
        /** amcuer validate */
        authRealname : "尚未认证",
        authSession : "尚未认证",
        authDeprt : "尚未认证",
        authJob : "尚未认证",
        sessions : 10,
    },
    methods : {
        getCurLoginUserInfo(userId) {
           axios.get('/usr/signin-usr?userId=' + userId).then((result) => {
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
                        usrCenterVM.curUserEmail = result.data.respBody.email;
                        usrCenterVM.curUserSignupDate = dateFormate('yyyy-MM-dd HH:mm:ss', new Date(result.data.respBody.signupDate));
                        usrCenterVM.$options.methods.getOAuthBindingInfo();

                    } else if(500 === result.data.statusCode) {
                        toastr.error(result.data.msg + "\n请重新登录!", "提示");
                        this.isLogin = false;
                    }
                }
            }).catch((xhr) => {
               toastr.error("用户信息获取异常,请重新登录");
               timeoutRelocateToIndex(1500);
           });
        },
        getOAuthBindingInfo() {
            axios.get("/connect").then((result) => {
                let data = result.data;
                usrCenterVM.isQQBinded = data["callback.do"];
                usrCenterVM.isWxBinded = data["weixin"];
                usrCenterVM.isLkBinded = data["linkedin"];
                usrCenterVM.isGithubBinded = data["github"];
                usrCenterVM.isCodingBinded = data["coding"];
            }).catch( (xhr) => {

            });
        },
        getTarUserInfo(tuid) {
            let _this = this;
            axios.get('/usr/other-usr?tuid=' + tuid).then((result) => {
                if(200 === result.status){
                    if(200 === result.data.statusCode){
                        _this.isLogin = false;
                        _this.isCurUser = false;
                        _this.tarUserAvatar = (result.data.respBody.avatar == null ?
                            _this.tarUserAvatar :
                            result.data.respBody.avatar);
                        _this.tarUsername = result.data.respBody.username;
                        _this.hasPriorityView = false;
                    } else if(500 === result.data.statusCode) {
                        toastr.error(result.data.msg + "\n请检查请求链接!", "提示");
                        timeoutRelocateToIndex(1500);
                    }
                }
            }).catch((xhr) => {
                relocateToIndexImmediately();
            });
        },
        modifyAccountEvent() {
            this.isAccountModify = !this.isAccountModify;
        },
        modifyPasswordByOldPasswordEvent() {
            this.startPasswordModifyByOldPassword = !this.startPasswordModifyByOldPassword;
            if(this.startPasswordModifyByEmail)
                this.startPasswordModifyByEmail = !this.startPasswordModifyByEmail;
        },
        modifyPasswordByEmailEvent() {
            this.startPasswordModifyByEmail = !this.startPasswordModifyByEmail;
            if(this.startPasswordModifyByOldPassword)
                this.startPasswordModifyByOldPassword = !this.startPasswordModifyByOldPassword;
        },
        sendEmailCodeBy163(mailAddr) {
            if(null == mailAddr || "" === mailAddr || undefined === mailAddr) {
                toastr.warning("请先填写邮箱地址", "提示");
                return;
            }

            axios.get("/code/email", {
                params: {
                    'toEmail' : mailAddr,
                    'ver' : new Date()
                },
                timeout: 30000,
            }).then((result, status, xhr) => {

            }).catch((xhr, status, error) => {
                if(status !== "timeout" && status !== "parsererror")
                    toastr.error("验证码发送失败!", xhr);
            });

        },
        countDown4OldSentEmail() {
            if (!this.canSentCodeOldBtnClick) return;
            this.sendEmailCodeBy163(this.curUserEmail);
            this.canSentCodeOldBtnClick = false;
            this.oldBtnContent = this.seconds1 + 's后重新发送';
            let clock = window.setInterval(() => {
                this.seconds1--;
                this.oldBtnContent = this.seconds1 + 's后重新发送';
                if (this.seconds1 <= 0) {
                    window.clearInterval(clock);
                    this.oldBtnContent = '获取邮箱验证码';
                    this.seconds1 = 60;
                    this.canSentCodeOldBtnClick = true;
                }
            },1000);
        },
        countDown4NewSentEmail() {
            if (!this.canSentCodeNewBtnClick) return;
            this.sendEmailCodeBy163(this.newEmailAddr.val);
            this.canSentCodeNewBtnClick = false;
            this.newBtnContent = this.seconds2 + 's后重新发送';
            let clock = window.setInterval(() => {
                this.seconds2--;
                this.newBtnContent = this.seconds2 + 's后重新发送';
                if (this.seconds2 <= 0) {
                    window.clearInterval(clock);
                    this.newBtnContent = '获取邮箱验证码';
                    this.seconds2 = 60;
                    this.canSentCodeNewBtnClick = true;
                }
            },1000);
        },
        countDown4PwdSentEmail() {
            if (!this.canPwdEmailBtnClick) return;
            this.sendEmailCodeBy163(this.curUserEmail);
            this.canPwdEmailBtnClick = false;
            this.pwdEmailBtnContent = this.seconds3 + 's后重新发送';
            let clock = window.setInterval(() => {
                this.seconds3--;
                this.pwdEmailBtnContent = this.seconds3 + 's后重新发送';
                if (this.seconds3 <= 0) {
                    window.clearInterval(clock);
                    this.pwdEmailBtnContent = '获取邮箱验证码';
                    this.seconds3 = 60;
                    this.canPwdEmailBtnClick = true;
                }
            },1000);
        },
        mobileFunctionInDeveloping() {
            toastr.info("手机(短信)业务功能尚不可用!", "提示");
        },
        modifyUserAvatarEvent(avatar) {
            let data = Qs.stringify({'avatar':avatar})
            axios.post("/usr/avatar-modify", data, {
                headers:{
                    'Content-Type':'application/x-www-form-urlencoded',
                }
            }).then((result, status, xhr) => {
                toastr.success("头像修改成功", "Congratulation");
            }).catch((xhr, status, error) => {
                toastr.error("头像修改失败", "提示");
            });
        },
        modifyEduEvent() {
            this.startEduModify = !this.startEduModify;
        },
        modifyProfileEvent() {
            this.startProfileModify = !this.startProfileModify;
        }
    },
    created : () => {
       let tuid = getUrlParam("tuid");
       if(null == tuid || undefined === tuid || isNaN(tuid)) {
           axios.get('/auth/usr-enduring').then(function(result){
               if(200 === result.status){
                   if(200 === result.data.statusCode){
                       usrCenterVM.$options.methods.getCurLoginUserInfo(result.data.respBody.userId);
                   } else if(500 === result.data.statusCode) {
                       usrCenterVM.isLogin = false;
                       toastr.error(result.data.msg + "\n请重新登录!", "提示");
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
        let fileMaxSizes = 1024 * 2;
        let target = $(e.target);
        let size = target[0].files[0].size / 1024;
        if(size > fileMaxSizes) {
            toastr.error("图片过大,请重新选择图片!");
            return false;
        }
        if(!this.files[0].type.match(/image.*/)) {
            toastr.error('请选择正确的图片!');
        } else {
            let filename = document.querySelector("#avatar-name");
            let texts = document.querySelector("#avatarInput").value;
            let testend = texts.match(/[^\\]+\.[^\(]+/i);
            filename.innerHTML = testend;
        }

    });

    $(".avatar-save").on("click", function() {

        let $image = $('img.cropper-hidden');
        let data = {option:{}};
        data.option.width = 184;
        data.option.height = 184;
        data.option.fillColor = '#fff';
        let result = $image.cropper('getCroppedCanvas', data.option, undefined);
        let dataUrl = result.toDataURL("image/png");
        let formData = new FormData();
        let params = {"mediaType" : 3};
        if(dataUrl) {
            let blobBin = dataURLtoBlob(dataUrl);
            params.fileType = blobBin.type.split("/")[1];
            formData.append("file", blobBin);
        }
        let dataWithType = new Blob([JSON.stringify(params)], {
            type : "application/json"
        });
        formData.append("data", dataWithType);
        avatarUploadAjax(formData);
    });

    /******** 邮箱更改校验相关 ********/

    $("#oldEmailValidForm").formValidation({
        framework: 'bootstrap',
        icon : {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields : {
            emailCode : {
                validators : {
                    notEmpty: {
                        message : "请填写邮箱验证码"
                    },
                    regexp : {
                        regexp : /^\d{6}$/,
                        message : "邮箱验证码为6位的数字"
                    }
                }
            },
        }
    }).on('success.form.fv', function(e) {
        e.preventDefault();
        let $form = $(e.target);

        $.ajax({
            url : $form.attr('action'),
            data : $form.serialize(),
            type : "POST",
            dataType : 'JSON',
            success : function(result, status, xhr) {
                console.info(result);
                if(200 === result.statusCode){
                    usrCenterVM.isValidOldEmail = false;
                }
            },
            error : function(xhr, status, error) {
                if(500 === xhr.status){
                    toastr.error(xhr.responseJSON.content);
                }
                usrCenterVM.isValidOldEmail = true;
            }
        });
    });

    $("#newEmailValidForm").formValidation({
        framework: 'bootstrap',
        icon : {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields : {
            newEmail : {
                verbose : false,
                validators : {
                    notEmpty : {
                        message : "请填写邮箱地址!"
                    },
                    emailAddress : {
                        message : "请检查邮箱格式!"
                    },
                    remote : {
                        type : "POST",
                        url : "/usr/check",
                        message : "该邮箱已经被注册!请更换一个!",
                        delay : 2000
                    }
                }
            },
            emailCode : {
                validators : {
                    notEmpty: {
                        message : "请填写邮箱验证码"
                    },
                    regexp : {
                        regexp : /^\d{6}$/,
                        message : "邮箱验证码为6位的数字"
                    }
                }
            },
        }
    }).on('success.form.fv', function(e) {
        e.preventDefault();
        let $form = $(e.target);

        $.ajax({
            url : $form.attr('action'),
            data : $form.serialize(),
            type : "POST",
            dataType : 'JSON',
            success : function(result, status, xhr) {
                if(200 === result.statusCode) {
                    $("#editEmailModal").modal("hide");
                    usrCenterVM.isValidOldEmail = true;
                }
            },
            error : function(xhr, status, error) {
                if(500 === xhr.status){
                    toastr.error(xhr.responseJSON.content);
                }
                usrCenterVM.isValidOldEmail = false;
            }
        });
    });

    $("#editEmailModal").on("hidden.bs.modal",function() {
        usrCenterVM.isValidOldEmail = true;
    });

    /******** 用户更改密码校验相关 ********/

    $('#modifyPwdByOldPwdForm').formValidation({
            framework: 'bootstrap',
            icon : {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields : {
                oldPassword : {
                    validators : {
                        notEmpty : {
                            message : "请填写原密码!"
                        },
                        stringLength : {
                            min : 6,
                            max : 16,
                            message : "密码至少6位,最长16位"
                        },
                    }
                },
                newPassword : {
                    validators : {
                        notEmpty : {
                            message : "请填写新密码!"
                        },
                        stringLength : {
                            min : 6,
                            max : 16,
                            message : "密码至少6位,最长16位"
                        },
                    }
                },
                confirmPassword : {
                    validators : {
                        notEmpty : {
                            message : "请再次填写新密码!"
                        },
                        stringLength : {
                            min : 6,
                            max : 16,
                            message : "密码至少6位,最长16位"
                        },
                        identical : {
                            field : "newPassword",
                            message : "两次密码输入不一致!"
                        }
                    }
                },
            }
        }).on('success.form.fv', function(e) {
        e.preventDefault();
        let $form = $(e.target);
        $.ajax({
            url : $form.attr('action'),
            data : $form.serialize(),
            type : "POST",
            dataType : 'JSON',
            success : function(result, status, xhr) {
                if(200 === result.statusCode) {
                    toastr.success(result.msg, "提示");
                    timeoutRelocateToSignout(1500);
                } else
                    toastr.error(result.msg, "提示");
            },
            error : function(xhr, status, error) {
                if(500 === xhr.status) {
                    toastr.error(xhr.responseJSON.content);
                }
            }
        });
    });

    $("#modifyPwdByEmailForm").formValidation({
        framework: 'bootstrap',
        icon : {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields : {
            emailCode : {
                validators : {
                    notEmpty: {
                        message : "请填写邮箱验证码"
                    },
                    regexp : {
                        regexp : /^\d{6}$/,
                        message : "邮箱验证码为6位的数字"
                    }
                }
            },
            newPassword : {
                validators : {
                    notEmpty : {
                        message : "请填写新密码!"
                    },
                    stringLength : {
                        min : 6,
                        max : 16,
                        message : "密码至少6位,最长16位"
                    },
                }
            },
            confirmPassword : {
                validators : {
                    notEmpty : {
                        message : "请再次填写新密码!"
                    },
                    stringLength : {
                        min : 6,
                        max : 16,
                        message : "密码至少6位,最长16位"
                    },
                    identical : {
                        field : "newPassword",
                        message : "两次密码输入不一致!"
                    }
                }
            },
        }
    }).on('success.form.fv', function(e) {
        e.preventDefault();
        let $form = $(e.target);

        $.ajax({
            url : $form.attr('action'),
            data : $form.serialize(),
            type : "POST",
            dataType : 'JSON',
            success : function(result, status, xhr) {
                if(200 === result.statusCode) {
                    toastr.success(result.msg, "提示");
                    timeoutRelocateToSignout(1500);
                } else
                    toastr.error(result.msg, "提示");
            },
            error : function(xhr, status, error) {
                if(500 === xhr.status){
                    toastr.error(xhr.responseJSON.content);
                }
            }
        });

    });

    /******** 用户昵称修改相关 ********/

    $("#modifyUsernameForm").formValidation({
        framework: 'bootstrap',
        icon : {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields : {
            username : {
                verbose : false,
                validators : {
                    notEmpty: {
                        message : "请填写用户名"
                    },
                    stringLength : {
                        min : 6,
                        max : 36,
                        message : "用户名至少6位,最多36位"
                    },
                    regexp : {
                        regexp: /^(^(?!_)(?!\d)(?!.*?_$)[a-zA-Z0-9_\u4e00-\u9fa5\uac00-\ud7ff\u0800-\u4e00]{6,16}$)|(^\w[-\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\.)+[A-Za-z]{2,14}$)$/,
                        message : "请输入正确的用户名(最长36位,不能以数字开头,特殊符号只能是_且不能在开头或结尾使用)"
                    },
                    remote : {
                        type : "POST",
                        url : "/usr/check",
                        message : "该用户名已经被注册!请更换一个!",
                        delay : 2000
                    }
                }
            },
        }
    }).on('success.form.fv', function(e) {
        e.preventDefault();
        let $form = $(e.target);

        $.ajax({
            url : $form.attr('action'),
            data : $form.serialize(),
            type : "POST",
            dataType : 'JSON',
            success : function(result, status, xhr) {
                toastr.success("可以使用新昵称呐!", "Congratulation");
            },
            error : function(xhr, status, error) {
                if(500 === xhr.status){
                    toastr.error(xhr.responseJSON.content);
                }

            }
        });

    });

    /******** user profile ********/

    $("#userProfileNameForm").formValidation({
        framework: 'bootstrap',
        icon : {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields : {
            firstname : {
                validators : {
                    notEmpty: {
                        message : "名不能为空"
                    },
                    regexp : {
                        regexp : /^(^[\u4E00-\u9FFF]{1,20}$)|(^[A-Za-z]{1,20}$)$/,
                        message : "名最多只能输入20个字符",
                    },
                    stringLength : {
                        max : 20,
                        message : "最长20个字符"
                    }
                }
            },
            lastname : {
                validators : {
                    notEmpty: {
                        message : "姓不能为空"
                    },
                    regexp : {
                        regexp : /^(^[\u4E00-\u9FFF]{1,17}$)|(^[A-Za-z]{1,20}$)$/,
                        message : "姓最多只能输入英文20个字符/中文17个字符",
                    },
                    stringLength : {
                        max : 20,
                        message : "最长20个字符"
                    }
                }
            },
        }
    }).on('success.form.fv', function(e) {
        e.preventDefault();
        let $form = $(e.target);

        /*
        $.ajax({
            url : $form.attr('action'),
            data : $form.serialize(),
            type : "POST",
            dataType : 'JSON',
            success : function(result, status, xhr) {
                if(200 === result.statusCode) {
                    $("#editEmailModal").modal("hide");
                    usrCenterVM.isValidOldEmail = true;
                }
            },
            error : function(xhr, status, error) {
                if(500 === xhr.status){
                    toastr.error(xhr.responseJSON.content);
                }
                usrCenterVM.isValidOldEmail = false;
            }
        });
        */
    });

    $("#userProfileCareerForm").formValidation({
        framework: 'bootstrap',
        icon : {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields : {
            curCareer : {
                validators : {
                    notEmpty: {
                        message : "当前职业不能为空"
                    },
                    stringLength : {
                        max : 36,
                        message : "职业名称最多只能输入36个字符"
                    }
                }
            },
        }
    }).on('success.form.fv', function(e) {
        e.preventDefault();
        let $form = $(e.target);

        /*
        $.ajax({
            url : $form.attr('action'),
            data : $form.serialize(),
            type : "POST",
            dataType : 'JSON',
            success : function(result, status, xhr) {
                if(200 === result.statusCode) {
                    $("#editEmailModal").modal("hide");
                    usrCenterVM.isValidOldEmail = true;
                }
            },
            error : function(xhr, status, error) {
                if(500 === xhr.status){
                    toastr.error(xhr.responseJSON.content);
                }
                usrCenterVM.isValidOldEmail = false;
            }
        });
        */
    });

    $("#userProfileSignatureForm").formValidation({
        framework: 'bootstrap',
        icon : {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields : {
            diySignature : {
                validators : {
                    notEmpty: {
                        message : "个人简介不能为空(～￣(OO)￣)ブ"
                    },
                    stringLength : {
                        max : 160,
                        message : "最多只能输入160个字符"
                    }
                }
            },
        }
    }).on('success.form.fv', function(e) {
        e.preventDefault();
        let $form = $(e.target);

        /*
        $.ajax({
            url : $form.attr('action'),
            data : $form.serialize(),
            type : "POST",
            dataType : 'JSON',
            success : function(result, status, xhr) {
                if(200 === result.statusCode) {
                    $("#editEmailModal").modal("hide");
                    usrCenterVM.isValidOldEmail = true;
                }
            },
            error : function(xhr, status, error) {
                if(500 === xhr.status){
                    toastr.error(xhr.responseJSON.content);
                }
                usrCenterVM.isValidOldEmail = false;
            }
        });
        */
    });

    /******** 省/市/区select ********/

    $('#pidSelect').on('shown.bs.select', function () {
        getProvincesDataEvent().then((provinces) => {
            $('#pidSelect').html('');
            $('#cidSelect').html('');
            $('#cidSelect').selectpicker('refresh');
            $('#aidSelect').html('');
            $('#aidSelect').selectpicker('refresh');
            $('#pidSelect').append('<option value="" disabled selected>请选择</option>');
            $.each(provinces, (i) => {
                $('#pidSelect').append('<option value='+ provinces[i].code +'>' + provinces[i].text + '</option>');
            });
            $('#pidSelect').selectpicker('refresh');
        });
    });

    $('#pidSelect').on('change', function () {
        getCitiesDataEvent(this.options[this.selectedIndex].value).then((cities) => {
            $('#cidSelect').html('');
            $('#aidSelect').html('');
            $('#cidSelect').selectpicker('refresh');
            $('#cidSelect').append('<option value="" disabled selected>请选择</option>');
            $.each(cities, (i) => {
                $('#cidSelect').append('<option value='+ cities[i].code +'>' + cities[i].text + '</option>');
            });
            $('#cidSelect').selectpicker('refresh');
        });
    });

    $('#cidSelect').on('change', function () {
        getAreasDataEvent(this.options[this.selectedIndex].value).then((areas) => {
            $('#aidSelect').html('');
            $('#cidSelect').selectpicker('refresh');
            $('#aidSelect').append('<option value="" disabled selected>请选择</option>');
            $.each(areas, (i) => {
                $('#aidSelect').append('<option value='+ areas[i].code +'>' + areas[i].text + '</option>');
            });
            $('#aidSelect').selectpicker('refresh');
        });
    });

    $("#userEduPCAForm").formValidation({
        framework: 'bootstrap',
        icon : {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields : {
            provinceCode : {
                validators : {
                    notEmpty: {
                        message : "请选择省份"
                    },
                }
            },
            cityCode : {
                validators : {
                    notEmpty: {
                        message : "请选择城市"
                    },
                }
            },
            areaCode : {
                validators : {
                    notEmpty: {
                        message : "请选择市区"
                    },
                }
            },
        }
    }).on('success.form.fv', function(e) {
        e.preventDefault();
        let $form = $(e.target);

        /*
        $.ajax({
            url : $form.attr('action'),
            data : $form.serialize(),
            type : "POST",
            dataType : 'JSON',
            success : function(result, status, xhr) {
                if(200 === result.statusCode) {
                    $("#editEmailModal").modal("hide");
                    usrCenterVM.isValidOldEmail = true;
                }
            },
            error : function(xhr, status, error) {
                if(500 === xhr.status){
                    toastr.error(xhr.responseJSON.content);
                }
                usrCenterVM.isValidOldEmail = false;
            }
        });
        */
    });

    $("#userEduUniversityForm").formValidation({
        framework: 'bootstrap',
        icon : {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields : {
            university : {
                validators : {
                    notEmpty: {
                        message : "请填写在读/毕业学校"
                    },
                }
            },
        }
    }).on('success.form.fv', function(e) {
        e.preventDefault();
        let $form = $(e.target);

        /*
        $.ajax({
            url : $form.attr('action'),
            data : $form.serialize(),
            type : "POST",
            dataType : 'JSON',
            success : function(result, status, xhr) {
                if(200 === result.statusCode) {
                    $("#editEmailModal").modal("hide");
                    usrCenterVM.isValidOldEmail = true;
                }
            },
            error : function(xhr, status, error) {
                if(500 === xhr.status){
                    toastr.error(xhr.responseJSON.content);
                }
                usrCenterVM.isValidOldEmail = false;
            }
        });
        */
    });

    $("#userEduCollageForm").formValidation({
        framework: 'bootstrap',
        icon : {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields : {
            collage : {
                validators : {
                    notEmpty: {
                        message : "请填写学院"
                    },
                }
            },
        }
    }).on('success.form.fv', function(e) {
        e.preventDefault();
        let $form = $(e.target);

        /*
        $.ajax({
            url : $form.attr('action'),
            data : $form.serialize(),
            type : "POST",
            dataType : 'JSON',
            success : function(result, status, xhr) {
                if(200 === result.statusCode) {
                    $("#editEmailModal").modal("hide");
                    usrCenterVM.isValidOldEmail = true;
                }
            },
            error : function(xhr, status, error) {
                if(500 === xhr.status){
                    toastr.error(xhr.responseJSON.content);
                }
                usrCenterVM.isValidOldEmail = false;
            }
        });
        */
    });

    $("#userEduMajorForm").formValidation({
        framework: 'bootstrap',
        icon : {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields : {
            major : {
                validators : {
                    notEmpty: {
                        message : "请填写所修专业"
                    },
                }
            },
        }
    }).on('success.form.fv', function(e) {
        e.preventDefault();
        let $form = $(e.target);

        /*
        $.ajax({
            url : $form.attr('action'),
            data : $form.serialize(),
            type : "POST",
            dataType : 'JSON',
            success : function(result, status, xhr) {
                if(200 === result.statusCode) {
                    $("#editEmailModal").modal("hide");
                    usrCenterVM.isValidOldEmail = true;
                }
            },
            error : function(xhr, status, error) {
                if(500 === xhr.status){
                    toastr.error(xhr.responseJSON.content);
                }
                usrCenterVM.isValidOldEmail = false;
            }
        });
        */
    });

    $("#userEduGradeForm").formValidation({
        framework: 'bootstrap',
        icon : {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields : {
            grade : {
                validators : {
                    notEmpty: {
                        message : "请选择年级"
                    },
                }
            },
        }
    }).on('success.form.fv', function(e) {
        e.preventDefault();
        let $form = $(e.target);

        /*
        $.ajax({
            url : $form.attr('action'),
            data : $form.serialize(),
            type : "POST",
            dataType : 'JSON',
            success : function(result, status, xhr) {
                if(200 === result.statusCode) {
                    $("#editEmailModal").modal("hide");
                    usrCenterVM.isValidOldEmail = true;
                }
            },
            error : function(xhr, status, error) {
                if(500 === xhr.status){
                    toastr.error(xhr.responseJSON.content);
                }
                usrCenterVM.isValidOldEmail = false;
            }
        });
        */
    });

    /******** amcu auth validate ********/

    $('#authImgUpload').ssi_uploader({
        url:'http://127.0.0.1:8976/upload',
        local : 'en',
        maxFileSize : 2, //Mb
        allowed:['jpg','png', 'jpeg'],
        maxNumberOfFiles : 1,
        responseValidation : {
            validationKey : {
                success: 'success',
                error: 'error'
            },
            resultKey: 'validationKey'
        },
        ajaxOptions : {
            success : (result) => {
                console.info(result.respBody);
                $('#authImgInput').val(result.respBody);
            },
            error : (xhr) => {
                toastr.error("图片上传异常", "Exception");
            },
        },
        errorHandler : (msg, type) => {
            console.info(msg);
            console.info(type);
        },
        beforeEachUpload:function(fileInfo, xhr){
            if(fileInfo.size > 2048) {
                xhr.abort();
            }
            return '文件尺寸大于2M！';
        },
        onEachUpload: (fileInfo) => {
            if(fileInfo.uploadStatus === "success") {
                toastr.success("认证图片上传成功", "Congratulation");
            }
        },
    });

    $('#sessionSelect').on('shown.bs.select', function () {
        $('#sessionSelect').html('');
        $('#sessionSelect').append('<option value="" disabled selected>请选择</option>');
        for (let i = 0; i < usrCenterVM.sessions; i++) {
            $('#sessionSelect').append('<option value=' + (i+1) + '>第' + (i+1) + '届</option>');
        }
        $('#sessionSelect').selectpicker('refresh');
    });

    $('#departSelect').on('shown.bs.select', function () {
        getDepartsDataEvent().then((ds) => {
            $('#departSelect').html('');
            $('#jobSelect').html('');
            $('#jobSelect').selectpicker('refresh');
            $('#departSelect').append('<option value="" disabled selected>请选择</option>');
            $.each(ds, (i) => {
                $('#departSelect').append('<option value='+ ds[i].id +'>' + ds[i].title + '</option>');
            });
            $('#departSelect').selectpicker('refresh');
        });
    });

    $('#departSelect').on('change', function () {
        getJobsDataEvent(this.options[this.selectedIndex].value).then((js) => {
            $('#jobSelect').html('');
            $('#jobSelect').append('<option value="" disabled selected>请选择</option>');
            $.each(js, (i) => {
                $('#jobSelect').append('<option value='+ js[i].id +'>' + js[i].title + '</option>');
            });
            $('#jobSelect').selectpicker('refresh');
        });
    });

    $("#amcuerAuthValidateForm").formValidation({
        framework: 'bootstrap',
        icon : {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields : {
            authImg : {
                validators : {
                    notEmpty: {
                        message : "请上传认证图片"
                    },
                }
            },
            firstname : {
                validators : {
                    notEmpty: {
                        message : "名不能为空"
                    },
                    regexp : {
                        regexp : /^(^[\u4E00-\u9FFF]{1,20}$)|(^[A-Za-z]{1,20}$)$/,
                        message : "名最多只能输入20个字符",
                    },
                    stringLength : {
                        max : 20,
                        message : "最长20个字符"
                    }
                }
            },
            lastname : {
                validators : {
                    notEmpty: {
                        message : "姓不能为空"
                    },
                    regexp : {
                        regexp : /^(^[\u4E00-\u9FFF]{1,17}$)|(^[A-Za-z]{1,20}$)$/,
                        message : "姓最多只能输入英文20个字符/中文17个字符",
                    },
                    stringLength : {
                        max : 20,
                        message : "最长20个字符"
                    }
                }
            },
            sessionNo : {
                validators : {
                    notEmpty: {
                        message : "请选定认证的届数"
                    },
                }
            },
            departNo : {
                validators : {
                    notEmpty: {
                        message : "请选定认证的部门"
                    },
                }
            },
            jobNo : {
                validators : {
                    notEmpty: {
                        message : "请选定认证的部门工作"
                    },
                }
            },
        }
    }).on('success.form.fv', function(e) {
        e.preventDefault();
        let $form = $(e.target);

        $.ajax({
            url : $form.attr('action'),
            data : $form.serialize(),
            type : "POST",
            dataType : 'JSON',
            success : function(result, status, xhr) {
                if(200 === result.statusCode) {
                    $("#editEmailModal").modal("hide");
                    usrCenterVM.isValidOldEmail = true;
                }
            },
            error : function(xhr, status, error) {
                if(500 === xhr.status){
                    toastr.error(xhr.responseJSON.content);
                }
                usrCenterVM.isValidOldEmail = false;
            }
        });
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
                if(result.success){
                    usrCenterVM.curUserAvatar = result.respBody;
                    usrCenterVM.$options.methods.modifyUserAvatarEvent(result.respBody);
                }
            }
        });
    }

    function dataURLtoBlob(dataurl) {
        let arr = dataurl.split(',');
        let mime = arr[0].match(/:(.*?);/)[1];
        let bstr = atob(arr[1].replace(/\s/g, ''));
        let n = bstr.length;
        let u8arr = new Uint8Array(n);
        while (n--) {
            u8arr[n] = bstr.charCodeAt(n);
        }
        return new Blob([u8arr], {type: mime});
    }

    function getProvincesDataEvent() {
        return $.ajax({
            url : "/location/provinces",
            type : 'GET',
            dataType : 'JSON',
        }).then((result) => {
            if(200 === result.statusCode) {
                return result.respBody;
            } else if(500 === result.statusCode) {
                toastr.error(result.msg, "Sorry!");
            }
        }).catch((xhr) => {
            toastr.error("获取省份数据出错", "Sorry!");
        });
    }

    function getCitiesDataEvent(pid) {
        return $.ajax({
            url : "/location/cities",
            type : 'GET',
            dataType : 'JSON',
            data : { code : pid },
        }).then((result) => {
            if(200 === result.statusCode) {
                return result.respBody;
            } else if(500 === result.statusCode) {
                toastr.error(result.msg, "Sorry!");
            }
        }).catch((xhr) => {
            toastr.error("获取城市数据出错", "Sorry!");
        });
    }

    function getAreasDataEvent(cid) {
        return $.ajax({
            url : "/location/areas",
            type : 'GET',
            dataType : 'JSON',
            data : { code : cid },
        }).then((result) => {
            if(200 === result.statusCode) {
                return result.respBody;
            } else if(500 === result.statusCode) {
                toastr.error(result.msg, "Sorry!");
            }
        }).catch((xhr) => {
            toastr.error("获取市区数据出错", "Sorry!");
        });
    }

    function getDepartsDataEvent() {
        return $.ajax({
            url : "/depart",
            type : 'GET',
            dataType : 'JSON',
        }).then((result) => {
            if(200 === result.statusCode) {
                return result.respBody;
            } else if(500 === result.statusCode) {
                toastr.error(result.msg, "Sorry!");
            }
        }).catch((xhr) => {
            toastr.error("获取单片机协会部门数据出错", "Sorry!");
        });
    }

    function getJobsDataEvent(departNo) {
        return $.ajax({
            url : "/depart/jobs",
            type : 'GET',
            dataType : 'JSON',
            data : { departNo : departNo },
        }).then((result) => {
            if(200 === result.statusCode) {
                return result.respBody;
            } else if(500 === result.statusCode) {
                toastr.error(result.msg, "Sorry!");
            }
        }).catch((xhr) => {
            toastr.error("获取单片机协会部门工作数据出错", "Sorry!");
        });
    }

});