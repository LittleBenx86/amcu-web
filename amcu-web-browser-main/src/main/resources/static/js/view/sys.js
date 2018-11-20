if (!/Android|webOS|iPhone|iPod|BlackBerry/i.test(navigator.userAgent)) {
    $("#main-search-input").focus(function () {
        $("#main-search-input").animate({width: "350px"}, "fast", "swing");
        $(this).parents("form").next("ul").hide();
    });
    $("#main-search-input").blur(function () {
        $("#main-search-input").animate({width: "250px"}, "fast", function () {
            $(this).parents("form").next("ul").show();
        });
    });
}