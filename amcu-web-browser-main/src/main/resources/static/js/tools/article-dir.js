/**
 * @Description:    目录生成JS工具
 *                     原作者:孤傲苍狼
 * @Author: Ben-Zheng
 * @Date: 2018/12/15 23:15
 */

let ArticleDirectory = {
    /*
        获取元素位置，距浏览器左边界的距离（left）和距浏览器上边界的距离（top）
    */
    getElementPosition : (ele) => {
        let topPosition = 0;
        let leftPosition = 0;
        while (ele){
            topPosition += ele.offsetTop;
            leftPosition += ele.offsetLeft;
            ele = ele.offsetParent;
        }
        return {top:topPosition, left:leftPosition};
    },

    /*
    获取滚动条当前位置
    */
    getScrollBarPosition : () => {
        let scrollBarPosition = document.body.scrollTop || document.documentElement.scrollTop;
        return  scrollBarPosition;
    },

    /*
    移动滚动条，finalPos 为目的位置，internal 为移动速度
    */
    moveScrollBar : (finalpos, interval) => {

        //若不支持此方法，则退出
        if(!window.scrollTo) {
            return false;
        }

        //窗体滚动时，禁用鼠标滚轮
        window.onmousewheel = function(){
            return false;
        };

        //清除计时
        if (document.body.movement) {
            clearTimeout(document.body.movement);
        }

        let currentpos = ArticleDirectory.getScrollBarPosition();//获取滚动条当前位置

        let dist = 0;
        if (currentpos === finalpos) {//到达预定位置，则解禁鼠标滚轮，并退出
            window.onmousewheel = () => {
                return true;
            }
            return true;
        }
        if (currentpos < finalpos) {//未到达，则计算下一步所要移动的距离
            dist = Math.ceil((finalpos - currentpos) / 10);
            currentpos += dist;
        }
        if (currentpos > finalpos) {
            dist = Math.ceil((currentpos - finalpos) / 10);
            currentpos -= dist;
        }

        let scrTop = ArticleDirectory.getScrollBarPosition();//获取滚动条当前位置
        window.scrollTo(0, currentpos);//移动窗口
        if(ArticleDirectory.getScrollBarPosition() === scrTop) {//若已到底部，则解禁鼠标滚轮，并退出
            window.onmousewheel = () => {
                return true;
            }
            return true;
        }

        //进行下一步移动
        let repeat = "ArticleDirectory.moveScrollBar(" + finalpos + "," + interval + ")";
        document.body.movement = setTimeout(repeat, interval);
    },

    htmlDecode : (text) =>{
        let temp = document.createElement("div");
        temp.innerHTML = text;
        let output = temp.innerText || temp.textContent;
        temp = null;
        return output;
    },

    /**
     * 创建目录
     * @param id    表示包含博文正文的 div 容器的 id，
     * @param mt    主标题的标签名称    （如 H2、H3，大写或小写都可以！）
     * @param st    次级标题的标签名称   （如 H2、H3，大写或小写都可以！）
     * @param interval  表示移动的速度
     * @returns {boolean}
     */
    createArticleDirectory : (id, mt, st, interval) => {
        //获取正文div容器
        let elem = document.getElementById(id);
        if(!elem)
            return false;
        //获取div中所有元素结点
        let nodes = elem.getElementsByTagName("*");

        //查找目录的div容器
        let catalogue = document.getElementById("catalogue");

        let innerTabContents = document.createElement("DIV");
        innerTabContents.setAttribute("id", "innerContents");
        catalogue.appendChild(innerTabContents);

        //创建自定义列表
        let dlist = document.createElement("dl");
        innerTabContents.appendChild(dlist);
        let num = 0;//统计找到的mt和st
        mt = mt.toUpperCase();//转化成大写
        st = st.toUpperCase();//转化成大写
        //遍历所有元素结点
        for(let i=0; i<nodes.length; i++) {
            if(nodes[i].nodeName === mt|| nodes[i].nodeName === st) {
                //获取标题文本
                let nodetext = nodes[i].innerHTML.replace(/<\/?[^>]+>/g,"");//innerHTML里面的内容可能有HTML标签，所以用正则表达式去除HTML的标签
                nodetext = nodetext.replace(/ /ig, "");//替换掉所有的 
                nodetext = ArticleDirectory.htmlDecode(nodetext);
                //插入锚        
                nodes[i].setAttribute("id", "articleTitle" + num);
                let item;
                switch(nodes[i].nodeName) {
                    case mt:    //若为主标题 
                        item = document.createElement("dt");
                        break;
                    case st:    //若为子标题
                        item = document.createElement("dd");
                        break;
                }

                //创建锚链接
                let itemtext = document.createTextNode(nodetext);
                item.appendChild(itemtext);
                item.setAttribute("name", num);
                item.onclick = function() {        //添加鼠标点击触发函数
                    let pos = ArticleDirectory.getElementPosition(document.getElementById("articleTitle" + this.getAttribute("name")));
                    if(!ArticleDirectory.moveScrollBar(pos.top, interval)) return false;
                };

                //将自定义表项加入自定义列表中
                dlist.appendChild(item);
                num++;
            }
        }

        if(num === 0) return false;

    }

};
