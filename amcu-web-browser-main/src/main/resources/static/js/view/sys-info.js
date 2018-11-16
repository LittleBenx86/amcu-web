/**
 * 资讯 & 网站公告详细页面
 * author: Ben Zheng
 */

$(function() {

    bindNProgress();

    goTop();

    $("#info-tabs a").click(function (e) {
        e.preventDefault();
        $(this).tab('show');
    });
    
    showTarTabView(getUrlParam('type'));

    stickySidebar(".left-sidebar", 10);

    /******** 函数定义 ********/
    function showTarTabView(type) {
        $("#info-tabs a[href='#" + type + "']").tab('show');
    }

});

