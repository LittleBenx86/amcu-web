/**
 * 页面装饰特效
 * author: Ben Zheng
 */

/*************** 页面加载进度条 *******************/

function bindNProgress() {
    $(document).ready(function(){
        NProgress.start();
    });

    window.onload = function(){
        NProgress.done();
    }

    setTimeout(function() {
        NProgress.done();
    }, 1000);
}

$(window).ajaxStart(function () {
    NProgress.start();
});

$(window).ajaxStop(function () {
    NProgress.done();
});

/*************** gotop *******************/

function goTop() {
    $("#gotop").hide();

    $(window).scroll(function() {
        if ($(window).scrollTop() > 100) {
            $("#gotop").fadeIn()
        } else {
            $("#gotop").fadeOut()
        }
    });

    $("#gotop").click(function() {
        $('html,body').animate({
            'scrollTop': 0
        }, 500)
    });
}

/**
 * 获取window.locate定点跳转链接url中的参数function
 * @param name	url中key
 * @returns
 */
function getUrlParam(name) {
    let reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
    let r = window.location.search.substr(1).match(reg);
    if(r!=null)
        return  unescape(r[2]);
    return null;
}

/*************** 侧边栏浮动 *******************/

function stickySidebar(tarSelector, mtop) {
    $(tarSelector).theiaStickySidebar({
        additionalMarginTop: mtop
    });
}

/*************** 侧边栏浮动 *******************/

function relocateToIndexImmediately() {
    window.location.replace("/");
}

function timeoutRelocateToIndex(timeout) {
    let regx = /^[0-9]+$/;
    if(regx.test(timeout))
        setTimeout(function(){ relocateToIndexImmediately(); }, timeout);
    else
        relocateToIndexImmediately();
}

function relocateToSignoutImmediately() {
    window.location.replace("/signout");
}

function timeoutRelocateToSignout(timeout) {
    let regx = /^[0-9]+$/;
    if(regx.test(timeout)) {
        setTimeout(function() { relocateToSignoutImmediately(); }, timeout);
    } else {
        relocateToSignoutImmediately();
    }
}

function dateFormate(fmt, date) {
    let o = {
        "M+" : date.getMonth() + 1,         //月份
        "d+" : date.getDate(),              //日
        "H+" : date.getHours(),             //小时
        "m+" : date.getMinutes(),           //分
        "s+" : date.getSeconds(),           //秒
        "q+" : Math.floor((date.getMonth() + 3) / 3), //季度
        "S"  : date.getMilliseconds(),      //毫秒
    };
    if(/(y+)/.test(fmt))
        fmt=fmt.replace(RegExp.$1, (date.getFullYear()+"").substr(4 - RegExp.$1.length));
    for(let k in o)
        if(new RegExp("("+ k +")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length === 1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
    return fmt;
}

$(function(){

    toastr.options = {
        closeButton: true,
        debug: false,
        progressBar: true,
        positionClass: "toast-top-center",
        onclick: null,
        showDuration: "300",
        hideDuration: "1000",
        timeOut: "1000",
        extendedTimeOut: "1000",
        showEasing: "swing",
        hideEasing: "linear",
        showMethod: "fadeIn",
        hideMethod: "fadeOut"
    };

    bindNProgress();

    goTop();

});