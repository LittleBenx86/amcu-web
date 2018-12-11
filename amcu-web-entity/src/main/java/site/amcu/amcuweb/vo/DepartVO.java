package site.amcu.amcuweb.vo;

import java.io.Serializable;

/**
 * @Description:    单片机协会部门信息
 * @Author: Ben-Zheng
 * @Date: 2018/12/11 18:57
 */
public class DepartVO implements Serializable {

    private Integer id;

    private String title;

    public DepartVO(Integer id, String title) {
        this.id = id;
        this.title = title;
    }

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

    @Override
    public String toString() {
        return "DepartVO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
