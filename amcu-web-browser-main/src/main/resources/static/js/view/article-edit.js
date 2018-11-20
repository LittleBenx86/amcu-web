/**
 * 文章编辑
 * author: Ben Zheng
 */

$(function() {

    bindNProgress();

    goTop();

    /******** simditor编辑器 ********/
    var  toolbars = ['title', 'bold', 'mark', 'italic', 'underline', 'fontScale',
                    'color', 'ol', 'ul', 'blockquote', 'code', 'table',
                    'link', 'image', 'hr', 'indent', 'outdent', 'alignment', 'html'];

    var editor = new Simditor({
        textarea : $("#s-editor"),
        pasteImage : true,
        defaultImage : "",
        toolbarFloat : true,
        toolbar : toolbars,
        upload : {
            url : "",
            fileKey : "upload_file",
            connectionCount : 3,
            leaveConfirm : "正在上传文件，如果离开上传会自动取消"
        }
    });

    /******** switch ********/
    $('#comment-switch').ios6switch({
        size: 20,
        switchoffText: '关闭',
        switchonText: '开启',
        animateSpeed: 250
    });

    $('#comment-switch')[0].onchange = function() {
        console.info(validateCommontStatus());
    };

    /******** diy tags ********/

    $('#diy-tags').tagsinput({
        maxTags : 6,
        maxChars : 16,
        trimValue : true
    });

    /******** 函数定义 ********/
    function validateCommontStatus() {
        return $('#comment-switch')[0].checked;
    }

});