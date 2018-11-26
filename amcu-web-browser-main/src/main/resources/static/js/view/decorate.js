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
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
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

$(function(){

    bindNProgress();

    goTop();

});