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
        var size = target[0].files[0].size / 1024;
        if(size > fileMaxSizes) {
            toastr.error("图片过大,请重新选择图片!");
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
        var img_lg = $('#imageHead');
        var width = img_lg.offsetWidth;
        var height = img_lg.offsetHeight;
        var scale = 2;
        var canvas = document.createElement("canvas");
        canvas.width = width * scale;
        canvas.height = height * scale;
        var content = canvas.getContext('2d');
        content.scale(scale, scale);
        var rect = img_lg.get(0).getBoundingClientRect();
        content.translate(-rect.left, -rect.top);
        var opts = {
            dpi : window.devicePixelRatio * 2,
            scale : scale,
            logging : false,         /** html2canvas日志 */
            useCORS : true,          /** 跨域配置 */
            width : width,
            height : height,
            canvas : canvas,
        }

        /** 截图小的显示框内的内容 */
        html2canvas(img_lg[0], opts).then(function(canvas) {
            var dataUrl = canvas.toDataURL("image/png");
            var formData = new FormData();
            var params = {"mediaType" : 3};
            if(dataUrl) {
                var blobBin = dataURLtoBlob(dataUrl);
                var fileType = blobBin.type.split("/")[1];
                params.fileType = fileType;
                formData.append("file", blobBin);
            }
            var dataWithType = new Blob([JSON.stringify(params)], {
                type : "application/json"
            });
            formData.append("data", dataWithType);
            avatarUploadAjax(formData);
        });
    });

    /******** 函数定义 ********/
    function showTarTabView(type) {
        $("#info-tabs a[href='#" + type + "']").tab('show');
    }

    function avatarUploadAjax(src) {
        $.ajax({
            url: "http://127.0.0.1:8976/upload",
            data: src,
            type: "POST",
            cache: false,
            dataType: 'json',
            processData: false,
            contentType: false,
            success: function(res) {
                console.info(res);
            }
        });
    }

    function dataURLtoBlob(dataurl) {
        var arr = dataurl.split(',');
        var mime = arr[0].match(/:(.*?);/)[1];
        var bstr = atob(arr[1].replace(/\s/g, ''));
        var n = bstr.length;
        var u8arr = new Uint8Array(n);
        while (n--) {
            u8arr[n] = bstr.charCodeAt(n);
        }
        return new Blob([u8arr], {type: mime});
    }

});