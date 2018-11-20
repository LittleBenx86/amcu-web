/**
 * author: Ben Zheng
 * date: 2018-11-15
 */

$(function () {

    bindNProgress();

    $('.slider-content .carousel-inner .item a img').jqthumb({
        classname : 'carousel-img',
        width : '80%',
        height : '300px',
        position : {
            x : '50%',
            y : '50%'
        },
        zoom : '1',
        method : 'auto'
    });

    goTop();

    stickySidebar(".right-sidebar", 0);



});

