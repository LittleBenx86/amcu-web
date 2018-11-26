$(function () {

    var t = 5;

    /******** 函数定义 ********/
    var refer = function() {
        if (t == 0){
            location.replace("/index.html");
        }
        document.getElementById('show').innerHTML = "" + t + "秒后跳转转到AMCU网站首页";
        t--;
        return refer;
    }

    setInterval(refer(), 1000);

});