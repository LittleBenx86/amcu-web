package site.amcu.amcuweb.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @Description:    AMCU协会部门实体类
 * @Author: Ben-Zheng
 * @Date: 2018/11/13 10:09
 */
@TableName(value = "tbl_amcu_depart")
public class AmcuDepart implements Serializable {

    private static final long serialVersionUID = 3041968502560908004L;

    @ApiModelProperty(value = "AMCU协会部门/任职分类主键")
    @TableId(value = "id")
    private Integer id;

    @ApiModelProperty(value = "AMCU协会部门/任职分类名称")
    private String title;

    @ApiModelProperty(value = "上级部门编号:(外键:自关联主键)")
    private Integer departId;

    /***************** setter & getter 是为了自动注入 *********************/

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getDepartId() {
        return departId;
    }

    public void setDepartId(Integer departId) {
        this.departId = departId;
    }

    @Override
    public String toString() {
        return "AmcuDepart{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", departId=" + departId +
                '}';
    }
}
