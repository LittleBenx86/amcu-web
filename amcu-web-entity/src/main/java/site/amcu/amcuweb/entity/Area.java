package site.amcu.amcuweb.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @Description:    省市区实体对象
 * @Author: Ben-Zheng
 * @Date: 2018/11/12 16:13
 */
@TableName(value = "tbl_area")
public class Area implements Serializable  {

    private static final long serialVersionUID = 3047463309985326231L;

    /** CHN:省/市/区三者的主键id标识 */
    @ApiModelProperty(value = "省/市/区的主键id标识")
    @TableId(value = "area_code")
    private String areaCode;

    /** CHN:省/市/区的名称 */
    @ApiModelProperty(value = "省/市/区的名称")
    private String areaName;

    /** CHN:省没有上级编码(为null);市的上级编码的省的编码;区的上级编码是市的编码(外键:关联当前的类主键) */
    @ApiModelProperty(value = "上级地区的编码:自关联外键")
    private String upAreaCode;

    /***************** setter & getter 是为了自动注入 *********************/

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getUpAreaCode() {
        return upAreaCode;
    }

    public void setUpAreaCode(String upAreaCode) {
        this.upAreaCode = upAreaCode;
    }

    @Override
    public String toString() {
        return "Area{" +
                "areaCode='" + areaCode + '\'' +
                ", areaName='" + areaName + '\'' +
                ", upAreaCode='" + upAreaCode + '\'' +
                '}';
    }
}
