package site.amcu.amcuweb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import site.amcu.amcuweb.dao.AreaMapper;
import site.amcu.amcuweb.entity.Area;
import site.amcu.amcuweb.service.AreaService;
import site.amcu.amcuweb.vo.AreaVO;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:    省/市/区数据服务层接口实现
 * @Author: Ben-Zheng
 * @Date: 2018/11/12 16:25
 */
@Service
@Transactional(propagation = Propagation.NESTED, isolation = Isolation.DEFAULT, readOnly = false)
public class AreaServiceImpl implements AreaService {

    @Resource
    private AreaMapper areaMapper;

    @Transactional(readOnly = true)
    @Override
    public List<AreaVO> findAllProvince() {
        List<Area> provinces = areaMapper.selectList(new QueryWrapper<Area>().lambda().like(Area::getAreaCode, "__0000"));
        List<AreaVO> res = new ArrayList<>();
        provinces.stream().forEach( p -> res.add(new AreaVO(p.getAreaCode(), p.getAreaName())) );
        return res;
    }

    @Transactional(readOnly = true)
    @Override
    public List<AreaVO> findAllCitiesByProvincesCode(String pid) {
        List<Area> provinces = areaMapper.selectList(new QueryWrapper<Area>().lambda().eq(Area::getUpAreaCode, pid));
        List<AreaVO> res = new ArrayList<>();
        provinces.stream().forEach( p -> res.add(new AreaVO(p.getAreaCode(), p.getAreaName())) );
        return res;
    }

    @Transactional(readOnly = true)
    @Override
    public List<AreaVO> findAllAreasByCityCode(String cid) {
        List<Area> provinces = areaMapper.selectList(new QueryWrapper<Area>().lambda().eq(Area::getUpAreaCode, cid));
        List<AreaVO> res = new ArrayList<>();
        provinces.stream().forEach( p -> res.add(new AreaVO(p.getAreaCode(), p.getAreaName())) );
        return res;
    }
}
