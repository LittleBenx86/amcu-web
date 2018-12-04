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

        var $image = $('img.cropper-hidden');
        var data = {option:{}};
        data.option.width = 184;
        data.option.height = 184;
        data.option.fillColor = '#fff';
        var result = $image.cropper('getCroppedCanvas', data.option, undefined);
        var dataUrl = result.toDataURL("image/png");
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
            success: function(result) {
                console.info(result.success + ":" + result.respBody)
                if(result.success){
                    $("img.usr-avatar").src = result.respBody;
                }

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