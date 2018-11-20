/**
 * 文章 & 资讯 & 用户搜索的详细页面
 * author: Ben Zheng
 */

$(function() {

    bindNProgress();

    goTop();

    $("#search-tabs a").click(function (e) {
        e.preventDefault();
        $(this).tab('show');
        if(this.attributes[0].value == '#search-article') {
            $("a#cur-type").text("文章");
        } else if(this.attributes[0].value == '#search-notice') {
            $("a#cur-type").text("资讯");
        } else if(this.attributes[0].value == '#search-usr') {
            $("a#cur-type").text("用户");
        }
    });

    showTarTabView(getUrlParam('type'));

    stickySidebar(".left-sidebar", 10);

    /******** 函数定义 ********/
    function showTarTabView(type) {
        $("#search-tabs a[href='#" + type + "']").tab('show');
    }

});