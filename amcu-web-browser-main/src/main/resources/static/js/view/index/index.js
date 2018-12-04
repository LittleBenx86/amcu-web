/**
 * author: Ben Zheng
 * date: 2018-11-15
 */

/******** vue数据控制 ********/

var indexVM = new Vue({
    el : '#v-index',
    data : {
        isLogin : false,
        avatar : "img/avatar/5_1.png",
        curUsername : '测试人员',
        curIntegrations : 0,
        isAMCUer: false,
        isADMIN: false,
        writeArticle : 'javascript:void(0)',

    },
    methods : {
        getLoginUserInfo : function(userId) {
            this.$http.get('/usr/signin-usr?userId=' + userId).then(function(result){
                if(200 == result.status){
                    this.isLogin = true;
                    console.info(result.body.respBody);
                    this.avatar = result.body.respBody.avatar == null ? this.avatar : result.body.respBody.avatar;
                    this.curUsername =result.body.respBody.username;
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

/******** dom初始化 ********/

$(function () {

    $('.slider-content .carousel-inner .item a img').jqthumb({
        classname : 'carousel-img',
        width : '80%',
        height : '300px',
        position : {
            x : '50%',
            y : '50%'
        },
        zoom : '1',
        method : 'auto'
    });

    stickySidebar(".right-sidebar", 0);

    /******** 输入校验事件 ********/

    function formValidate() {
        $('#index-signin-form').formValidation({
            framework: 'bootstrap',
            icon : {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields : {
                username : {
                    validators : {
                        notEmpty: {
                            message : "请填写用户名/邮箱"
                        },
                        stringLength : {
                            min : 6,
                            max : 100,
                            message : "用户名至少6位"
                        },
                        regexp : {
                            regexp: /^(^(?!_)(?!.*?_$)[a-zA-Z0-9_\u4e00-\u9fa5\uac00-\ud7ff\u0800-\u4e00]{6,16}$)|(^\w[-\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\.)+[A-Za-z]{2,14}$)$/,
                            message : "请输入正确的用户名(最长36位,特殊符号只能是_且不能在开头或结尾使用)/邮箱地址"
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
                            message : "密码至少6位"
                        }
                    }
                },
                imageCode : {
                    validators : {
                        notEmpty: {
                            message : "请填写验证码"
                        },
                        regexp : {
                            regexp : /^[\da-zA-Z]{4}$/,
                            message : "图形验证码为4位的数字或字母"
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
                        if(200 == result.respCode) {
                            $("#signin-modal").modal("hide");
                            indexVM.getLoginUserInfo(result.respBody.userId);
                        }
                    },
                    error : function(xhr, status, error) {
                        if(500 == xhr.status){
                            if('坏的凭证' == xhr.responseJSON.content) {
                                toastr.error("密码错误!请校验密码!", xhr.responseJSON.content);
                                $("input#e-pw").val("");
                            } else {
                                toastr.error(xhr.responseJSON.content);
                            }
                            $("img#code").attr("src",'/code/image?width=120&height=36&ver=' + new Date());
                            $("input#e-code").val("");
                        }
                    }
                });
            }
        );
    }

    /******** modal事件 ********/

    $("img#code").click(function(){
        this.src = '/code/image?width=120&height=36&ver=' + new Date();
    });

    $("#signin-modal").on("show.bs.modal", function() {
        $("img#code").attr("src",'/code/image?width=120&height=36&ver=' + new Date());
    });

    $('#signin-modal').on('shown.bs.modal', function() {
        formValidate();
    });

    $("#signin-modal").on("hidden.bs.modal", function() {
        $('#index-signin-form').formValidation('resetForm', true);
        $('#index-signin-form').formValidation('destroy');
    });

    /******** 函数定义 ********/




});



