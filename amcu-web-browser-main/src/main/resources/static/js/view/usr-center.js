/**
 * author: Ben Zheng
 * date: 2018-11-28
 */

$(function(){
    $("#info-tabs a").click(function (e) {
        e.preventDefault();
        $(this).tab('show');
        if(this.attributes[0].value == '#usr-info') {
            $("a#cur-type").text("个人信息");
        } else if(this.attributes[0].value == '#account-info') {
            $("a#cur-type").text("账号信息");
        } else if(this.attributes[0].value == '#oauth2-info') {
            $("a#cur-type").text("第三方账号绑定");
        } else if(this.attributes[0].value == '#auth-info') {
            $("a#cur-type").text("单片机协会认证信息");
        } else if(this.attributes[0].value == '#following-info') {
            $("a#cur-type").text("关注作者");
        } else if(this.attributes[0].value == '#follower-info') {
            $("a#cur-type").text("粉丝信息");
        }
    });

    showTarTabView(getUrlParam('type'));

    stickySidebar(".left-sidebar", 10);

    $("input#avatarInput.avatar-input").on('change', function (e) {
        /** 2M */
        var fileMaxSizes = 1024 * 2;
        var target = $(e.target);
        console.info(target);
        var size = target[0].files[0].size / 1024;
        console.info(size);
        if(size > fileMaxSizes) {
            toastr.error("图片过大,请重新选择图片!");
            $(".avatar-wrapper").childre().remove;
            return false;
        }
        if(!this.files[0].type.match(/image.*/)) {
            toastr.error('请选择正确的图片!');
        } else {
            var filename = document.querySelector("#avatar-name");
            var texts = document.querySelector("#avatarInput").value;
            var testend = texts.match(/[^\\]+\.[^\(]+/i);
            filename.innerHTML = testend;
        }

    });

    $(".avatar-save").on("click", function() {
        var img_lg = document.getElementById('imageHead');
        /** 截图小的显示框内的内容 */
        html2canvas(img_lg, {
            allowTaint: true,
            taintTest: false,
            onrendered: function(canvas) {
                canvas.id = "tarCanvas";
                /** 生成base64图片数据 */
                var dataUrl = canvas.toDataURL("image/png");
                console.info("dataUrl:" + dataUrl);
                var newImg = document.createElement("img");
                newImg.src = dataUrl;
                imagesAjax(dataUrl);
            }
        });
    });

    /******** 函数定义 ********/
    function showTarTabView(type) {
        $("#info-tabs a[href='#" + type + "']").tab('show');
    }

    function imagesAjax(src) {
        var data = {};
        data.img = src;
        data.jid = $('#jid').val();
        $.ajax({
            url: "upload-logo.php",
            data: data,
            type: "POST",
            dataType: 'json',
            success: function(re) {
                if(re.status == '1') {
                    $('.user_pic img').attr('src',src );
                }
            }
        });
    }

});