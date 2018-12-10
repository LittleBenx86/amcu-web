package site.amcu.amcuweb.vo;

import java.io.Serializable;

/**
 * @Description:    省市区查询结果封装
 * @Author: Ben-Zheng
 * @Date: 2018/12/10 16:00
 */
public class AreaVO implements Serializable {

    /** 地点代码 */
    private String code;

    /** 地点名 */
    private String text;

    public AreaVO(String code, String text) {
        this.code = code;
        this.text = text;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "AreaVO{" +
                "code='" + code + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
