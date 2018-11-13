package site.amcu.amcuweb.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @Description:    用户教育背景信息实体类
 * @Author: Ben-Zheng
 * @Date: 2018/11/13 9:45
 */
@TableName(value = "tbl_user_edu")
public class UserEdu implements Serializable {

    private static final long serialVersionUID = -7999904461022056393L;

    @ApiModelProperty(value = "用户教育背景信息记录主键")
    @TableId(value = "user_edu_id")
    private Integer id;

    @ApiModelProperty(value = "关联省主键编号(外键)")
    private String provinceCode;

    @ApiModelProperty(value = "关联市主键编号(外键)")
    private String cityCode;

    @ApiModelProperty(value = "关联区主键编号(外键)")
    private String areaCode;

    @ApiModelProperty(value = "关联用户主键(外键)")
    private Integer userId;

    @ApiModelProperty(value = "本科在读/毕业院校")
    private String university;

    @ApiModelProperty(value = "主修专业")
    private String major;

    /** 大一~大四,研究生属于本科已毕业 */
    @ApiModelProperty(value = "年级/已毕业")
    private Integer grade;

    @ApiModelProperty(value = "学院")
    private String college;

    /***************** setter & getter 是为了自动注入 *********************/

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    @Override
    public String toString() {
        return "UserEdu{" +
                "id=" + id +
                ", provinceCode='" + provinceCode + '\'' +
                ", cityCode='" + cityCode + '\'' +
                ", areaCode='" + areaCode + '\'' +
                ", userId=" + userId +
                ", university='" + university + '\'' +
                ", major='" + major + '\'' +
                ", grade=" + grade +
                ", college='" + college + '\'' +
                '}';
    }
}
