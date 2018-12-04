$(function () {

    var sentCodeBtn = $("#sent-email-code");
    var seconds = 60;
    sentCodeBtn.click(function() {
        sendEmailCodeFrom163($("input#n-mail").val());
    });

    $("#auth-signup-form").formValidation({
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
            email : {
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
            password : {
                validators : {
                    notEmpty : {
                        message : "请填写密码!"
                    },
                    stringLength : {
                        min : 6,
                        max : 16,
                        message : "密码至少6位,最长16位"
                    },
                    different : {
                        field : "username",
                        message : "密码和用户名不能相同!"
                    }
                }
            },
            confirmPassword : {
                validators : {
                    notEmpty : {
                        message : "请再次填写密码!"
                    },
                    stringLength : {
                        min : 6,
                        max : 16,
                        message : "密码至少6位,最长16位"
                    },
                    identical : {
                        field : "password",
                        message : "两次密码输入不一致!"
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
            agree : {
                validators : {
                    notEmpty : {
                        message : "请同意注册守则"
                    }
                }
            }
        }
    }).on('success.form.fv', function(e) {
        e.preventDefault();
        var $form = $(e.target);

        $.ajax({
            url : $form.attr('action'),
            data : $form.serialize(),
            type : "POST",
            dataType : 'JSON',
            success : function(result, status, xhr) {
                console.info(result);
            },
            error : function(xhr, status, error) {
                if(500 == xhr.status){
                    toastr.error(xhr.responseJSON.content);
                }
            }
        });
    });

    $('#auth-signup-form').find('[name="password"]').on('keyup', function() {
        var isEmpty = $(this).val() == '';
        $('#auth-signup-form').formValidation('enableFieldValidators', 'password', !isEmpty)
            .formValidation('enableFieldValidators', 'confirmPassword', !isEmpty);
        if ($(this).val().length >= 6) {
            $('#auth-signup-form').formValidation('validateField', 'password')
                .formValidation('validateField', 'confirmPassword');
        }
    });

    /******** 函数定义 ********/

    function sentCodeCountdown() {
        if(0 == seconds) {
            sentCodeBtn.removeClass("disabled");
            sentCodeBtn.removeClass("btn-default");
            sentCodeBtn.addClass("btn-warning");
            sentCodeBtn.html("获取邮箱验证码");
            seconds = 60;
            return;
        } else {
            sentCodeBtn.addClass("disabled");
            sentCodeBtn.html("重新发送(" + --seconds + "s)");
        }
        setTimeout(sentCodeCountdown, 1000);
    }

    function sendEmailCodeFrom163(emailAddr) {

        if(null == emailAddr || "" == emailAddr || undefined == emailAddr) {
            toastr.warning("请先填写邮箱地址");
            return;
        }

        sentCodeBtn.removeClass("btn-warning");
        sentCodeBtn.addClass("btn-default");
        sentCodeCountdown(seconds);

        toastr.success("验证码正在发送!", "请注意查收!");
        $.ajax({
            url : "/code/email",
            data : {
                'toEmail' : emailAddr,
                'ver' : new Date()
            },
            type : "GET",
            dataType : 'JSON',
            timeout: 60000,
            success : function(result, status, xhr) {

            },
            error : function(xhr, status, error) {
                if(status != "timeout" && status != "parsererror")
                    toastr.error("验证码发送失败!", xhr);
            }
        });
    }

});
