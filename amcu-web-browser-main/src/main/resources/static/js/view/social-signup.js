$(function () {

    bindNProgress();

    goTop();

    //getSocialInfo();

    $("img#code").click(function(){
        this.src = '/code/image?width=120&height=36&' + new Date();
    });

    /******** 函数定义 ********/
    function getSocialInfo() {
        $.ajax({
            url : '/usr/social-info',
            type : 'GET',
            success : function (result, status) {
                $("img#avatar.social-usr").attr("src", result.socialAvatar);
                $("p#nickname.social-usr").text(result.socialNickname);
                $("p#provider-id.social-usr").text(result.providerId);
            },
            error : function (xhr, e1, e2) {
                console.error("出错");
            }
        });
    }

});