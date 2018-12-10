package site.amcu.amcuweb.service;

import site.amcu.amcuweb.vo.AreaVO;

import java.util.List;

/**
 * @Description:    省/市/区数据服务层接口
 * @Author: Ben-Zheng
 * @Date: 2018/11/12 16:24
 */
public interface AreaService {

    /**
     * 获取所有省份的数据
     * @return
     */
    List<AreaVO> findAllProvince();

    /**
     * 根据省份的代码(id)获取所有相关的城市数据
     * @param pid
     * @return
     */
    List<AreaVO> findAllCitiesByProvincesCode(String pid);

    /**
     * 根据城市的代码(id)获取所有相关的市区数据
     * @param cid
     * @return
     */
    List<AreaVO> findAllAreasByCityCode(String cid);

}
