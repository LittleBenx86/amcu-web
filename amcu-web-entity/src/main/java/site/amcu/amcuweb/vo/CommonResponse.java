package site.amcu.amcuweb.vo;

import java.io.Serializable;

/**
 * @Description:    通用信息返回
 * @Author: Ben-Zheng
 * @Date: 2018/11/23 9:38
 */
public class CommonResponse implements Serializable {

    private static final long serialVersionUID = -6867175010273549460L;

    /** 响应处理标志 */
    private Integer respCode;

    /** 处理信息,成功/失败信息 */
    private String msg;

    /** 返回数据的主体 */
    private Object respBody;

    public CommonResponse() {

    }

    public CommonResponse(Integer respCode, String msg) {
        this.respCode = respCode;
        this.msg = msg;
    }

    public CommonResponse(Integer respCode, String msg, Object respBody) {
        this(respCode, msg);
        this.respBody = respBody;
    }

    public Integer getRespCode() {
        return respCode;
    }

    public void setRespCode(Integer respCode) {
        this.respCode = respCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getRespBody() {
        return respBody;
    }

    public void setRespBody(Object respBody) {
        this.respBody = respBody;
    }
}
