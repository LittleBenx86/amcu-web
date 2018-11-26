/**
 * 热门文章 & 最新文章的详细页面
 * author: Ben Zheng
 */

$(function() {

    $("#article-tabs a").click(function (e) {
        e.preventDefault();
        $(this).tab('show');
        if(this.attributes[0].value == '#hot-article') {
            $("a#cur-type").text("热门文章");
        } else if(this.attributes[0].value == '#new-article') {
            $("a#cur-type").text("最新文章");
        }
    });

    showTarTabView(getUrlParam('type'));

    stickySidebar(".left-sidebar", 10);

    /******** 函数定义 ********/
    function showTarTabView(type) {
        $("#article-tabs a[href='#" + type + "']").tab('show');
    }

});