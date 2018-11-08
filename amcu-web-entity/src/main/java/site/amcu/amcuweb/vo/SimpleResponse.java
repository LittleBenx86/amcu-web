package site.amcu.amcuweb.vo;

/**
 * @Description:    开发阶段简易的对象返回vo
 * @Author: Ben-Zheng
 * @Date: 2018/11/02 16:21
 */
public class SimpleResponse {

    /** 需要格式化的内容 */
    private Object content;

    public SimpleResponse(Object content) {
        this.content = content;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }
}
