package site.amcu.amcuweb.service;

import site.amcu.amcuweb.vo.DepartVO;

import java.util.List;

/**
 * @Description:    AMCU协会部门/任职分类数据服务层接口
 * @Author: Ben-Zheng
 * @Date: 2018/11/13 10:20
 */
public interface AmcuDepartService {

    /**
     * 查找所有部门
     * @return
     */
    List<DepartVO> findAllDepartment();

    /**
     * 查找部门下的所有的职位分配
     * @param departNo
     * @return
     */
    List<DepartVO> findAllJobByDepartmentNo(Integer departNo);

}
