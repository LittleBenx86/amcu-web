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
}

/*************** gotop *******************/

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