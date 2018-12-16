/**
 * @Description:    网上资源修改后的js桌面宠物
 * @Author: Ben-Zheng
 * @Date: 2018/12/14 10:57
 */

$(document).ready(function ($) {

    //拖动
    let _move = false;
    let ismove = false; //移动标记
    let _x, _y; //鼠标离控件左上角的相对位置
    let isindex=false;
    let title="给博客添加一个浮动小人";
    let visitor="游客";
    let spig_top = 50;
    let stat_click = 0;
    let msgs = null;
    let i = 0;
    let $girl = $(".mumu"), $msg = $("#message"), $petDiv = $("#spig.spig");

    /** 开始界面 */

    if (isindex) { //如果是主页
        let now = (new Date()).getHours();
        if (now > 0 && now <= 6) {
            showMessage(visitor + ' 你是夜猫子呀？还不睡觉，明天起的来么你？', 6000);
        } else if (now > 6 && now <= 11) {
            showMessage(visitor + ' 早上好，早起的鸟儿有虫吃噢！早起的虫儿被鸟吃，你是鸟儿还是虫儿？嘻嘻！', 6000);
        } else if (now > 11 && now <= 14) {
            showMessage(visitor + ' 中午了，吃饭了么？不要饿着了，饿死了谁来挺我呀！', 6000);
        } else if (now > 14 && now <= 18) {
            showMessage(visitor + ' 中午的时光真难熬！还好有你在！', 6000);
        } else {
            showMessage(visitor + ' 快来逗我玩吧！', 6000);
        }
    } else {
        showMessage('欢迎' + visitor + '来写博客 ' + title + ' ', 6000);
    }

    $petDiv.animate({
        top: $petDiv.offset().top + 300,
        left: document.body.offsetWidth - 160
    }, {
        queue: false,
        duration: 1000
    });

    /** 鼠标点击显示秘密通道 */

    $petDiv.on("mousedown", function (e) {
        if(e.which === 3){
            showMessage("秘密通道:<br />返回<a href=\"/\" title=\"首页\">AMCU首页</a>",10000);
        }
    });

    $petDiv.bind("contextmenu", function(e) {
        return false;
    });

    /** 鼠标在上方时点击事件 */

    $girl.on("mouseover", () => {
        $girl.fadeTo("300", 0.3);
        msgs = ["我隐身了，你看不到我．<{=．．．．（嘎~嘎~嘎~）", "你看我会隐身哦！嘿嘿！╰(￣▽￣)╭", "别动手动脚的，把手拿开！", "把手拿开我才出来！"];
        i = Math.floor(Math.random() * msgs.length);
        showMessage(msgs[i]);
    });

    $girl.on('mouseout', () => {
        $girl.fadeTo("300", 1)
    });

    /** 鼠标点击消息框事件 */

    $msg.on("hover", () => {
        $msg.fadeTo("300", 1);
    });

    /** 鼠标在某些元素上方时 */

    $('h2 a').on("click", () => {//标题被点击时
        showMessage('正在用吃奶的劲加载《<span style="color:#0099cc;">' + $(this).text() + '</span>》请稍候');
    });

    $('h2 a').on("mouseover", () => {
        showMessage('要看看《<span style="color:#0099cc;">' + $(this).text() + '</span>》这篇文章么？');
    });

    $('#prev-page').on("mouseover", () => {
        showMessage('要翻到上一页吗?');
    });

    $('#next-page').on("mouseover", () => {
        showMessage('要翻到下一页吗?');
    });

    $('#index-links li a').on("mouseover", () => {
        showMessage('去 <span style="color:#0099cc;">' + $(this).text() + '</span> 逛逛');
    });

    $('.comments').on("mouseover", () => {
        showMessage('<span style="color:#0099cc;">' + visitor + '</span> 向评论栏出发吧！');
    });

    $('#submit').on("mouseover", () => {
        showMessage('确认提交了么？');
    });

    $('#s').on("focus", () => {
        showMessage('输入你想搜索的关键词再按Enter(回车)键就可以搜索啦!');
    });

    $('#go-prev').on("mouseover", () => {
        showMessage('点它可以后退哦！');
    });

    $('#go-next').on("mouseover", () => {
        showMessage('点它可以前进哦！');
    });

    $('#refresh').on("mouseover", () => {
        showMessage('点它可以重新载入此页哦！');
    });

    $('#go-home').on("mouseover", () => {
        showMessage('点它就可以回到首页啦！');
    });

    $('#addfav').on("mouseover", () => {
        showMessage('点它可以把此页加入书签哦！');
    });

    $('#nav-two a').on("mouseover", () => {
        showMessage('嘘，从这里可以进入控制面板的哦！');
    });

    $('.post-category a').on("mouseover", () => {
        showMessage('点击查看此分类下得所有文章');
    });

    $('.post-heat a').on("mouseover", () => {
        showMessage('点它可以直接跳到评论列表处.');
    });

    $('#tho-shareto span a').on("mouseover", () => {
        showMessage('你知道吗?点它可以分享本文到'+$(this).attr('title'));
    });

    $('#switch-to-wap').on("mouseover", () => {
        showMessage('点击可以切换到手机版博客版面');
    });

    /** 无聊播报 */

    window.setInterval( () => {
        //<iframe name="xidie" src="http://t.xidie.com/skin/2010-0601.html"frameborder=“0” scrolling="no" height="15px"  width="130px" allowtransparency="true" ></iframe>
        msgs = ["播报明日天气(小哥哥正在开发中)", "陪我聊天吧！", "好无聊哦，你都不陪我玩！", "…@……!………", "^%#&*!@*(&#)(!)(", "我可爱吧！嘻嘻!~^_^!~~","努力干活!天天向上!<(ˉ^ˉ)>","从前有座山，山上有座庙，庙里有个老和尚给小和尚讲故事，讲：“从前有座……”"];
        i = Math.floor(Math.random() * msgs.length);
        showMessage(msgs[i], 10000);
    }, 35000);

    /** 无聊动动 */

    window.setInterval(() => {
        //<iframe name="xidie" src="http://t.xidie.com/skin/2010-0601.html"frameborder=“0” scrolling="no" height="15px"  width="130px" allowtransparency="true" ></iframe>
        msgs = ["播报明日天气(小哥哥正在开发中)", "乾坤大挪移！", "我飘过来了！~", "我飘过去了", "我得意地飘！~飘！~"];
        i = Math.floor(Math.random() * msgs.length);
        let s = [0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.75, 0.9, -0.1, -0.2, -0.3, -0.4, -0.5, -0.6, -0.7, -0.75, -0.9];
        let i1 = Math.floor(Math.random() * s.length);
        let i2 = Math.floor(Math.random() * s.length);
        $petDiv.animate({
                left: document.body.offsetWidth/ 2* (1+s[i1]),
                top:  document.body.offsetHeight/ 2* (1+s[i2])
            }, {
                duration: 2000,
                complete: showMessage(msgs[i])
            });
    }, 45000);

    /** 评论资料 */

    $("#author").on("click", () => {
        showMessage("留下你的尊姓大名！");
        $petDiv.animate({
            top: $("#author").offset().top - 70,
            left: $("#author").offset().left - 170
        }, {
            queue: false,
            duration: 1000
        });
    });

    $("#email").on("click", () => {
        showMessage("留下你的邮箱，不然就是无头像人士了！");
        $petDiv.animate({
            top: $("#email").offset().top - 70,
            left: $("#email").offset().left - 170
        }, {
            queue: false,
            duration: 1000
        });
    });

    $("#url").on("click", () => {
        showMessage("快快告诉我你的家在哪里，好让我去参观参观！");
        $petDiv.animate({
            top: $("#url").offset().top - 70,
            left: $("#url").offset().left - 170
        }, {
            queue: false,
            duration: 1000
        });
    });

    $("#comment").on("click", () => {
        showMessage("认真填写哦！不然会被认作垃圾评论的！我的乖乖~");
        $petDiv.animate({
            top: $("#comment").offset().top - 70,
            left: $("#comment").offset().left - 170
        }, {
            queue: false,
            duration: 1000
        });
    });

    /** 滚动条移动 */
    let f = $petDiv.offset().top;
    $(window).scroll(() => {
        $petDiv.animate({
            top: $(window).scrollTop() + f + 300
        }, {
            queue: false,
            duration: 1000
        });
    });

    /** 鼠标点击事件2 */

    $girl.on("click", () => {
        if (!ismove) {
            stat_click++;
            if (stat_click > 4) {
                msgs = ["你有完没完呀(*￣︿￣)？", "你已经摸我" + stat_click + "次了o(≧口≦)o", "非礼呀！救命！Oh，My ladygaga!ε(┬┬﹏┬┬)3"];
                i = Math.floor(Math.random() * msgs.length);
                showMessage(msgs[i]);
            } else {
                msgs = ["筋斗云！~我飞！～(￣▽￣～)(～￣▽￣)～", "我跑呀跑呀跑！~~<(￣ˇ￣)/", "别摸我，人家会害羞的！(✿◡‿◡)", "哼!o(≧口≦)o!还不赶紧写博客!", "莫挨老子！讨厌!o(￣ヘ￣o＃)", "我是可爱的初音小姐姐！٩(๑òωó๑)۶"];
                i = Math.floor(Math.random() * msgs.length);
                showMessage(msgs[i]);
            }
            let s = [0.1, 0.2, 0.3, 0.4, 0.5, 0.6,0.7,0.75, 0.9, -0.1, -0.2, -0.3, -0.4, -0.5, -0.6,-0.7,-0.75, -0.9];
            let i1 = Math.floor(Math.random() * s.length);
            let i2 = Math.floor(Math.random() * s.length);
            $petDiv.animate({
                left: document.body.offsetWidth / 2 * (1 + s[i1]),
                top:  document.body.offsetHeight / 2 * (1 + s[i2])
            }, {
                duration: 500,
                complete: showMessage(msgs[i])
            });
        } else {
            ismove = false;
        }
    });

    /** 人物移动 */

    $petDiv.on("mousedown", (e) => {
        _move = true;
        _x = e.pageX - parseInt($petDiv.css("left"));
        _y = e.pageY - parseInt($petDiv.css("top"));
    });

    $(document).on("mousemove", (e) => {
        if (_move) {
            let x = e.pageX - _x;
            let y = e.pageY - _y;
            let wx = $(window).width() - $petDiv.width();
            let dy = $(document).height() - $petDiv.height();
            if(x >= 0 && x <= wx && y > 0 && y <= dy) {
                $petDiv.css({
                    top: y,
                    left: x
                }); //控件新位置
                ismove = true;
            }
        }
    }).on("mouseup", () => {
        _move = false;
    });

    /** 消息显示函数 */
    function showMessage(a, b) {
        if (b == null)
            b = 10000;
        $msg.hide().stop();
        $msg.html(a);
        $msg.fadeIn();
        $msg.fadeTo("1", 1);
        $msg.fadeOut(b);
    };

});


