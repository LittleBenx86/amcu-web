
$(function () {

    bindNProgress();

    goTop();

    stickySidebar(".left-sidebar", 10);

    $("#search-tabs a").click(function (e) {
        e.preventDefault();
        $(this).tab('show');
        if(this.attributes[0].value == '#search-per-article') {
            $("a#cur-type").text("个人文章");
        } else if(this.attributes[0].value == '#search-collect-article') {
            $("a#cur-type").text("收藏文章");
        }
    });

    showTarTabView(getUrlParam('type'));

    /******** 函数定义 ********/
    function showTarTabView(type) {
        $("#search-tabs a[href='#" + type + "']").tab('show');
    }

});