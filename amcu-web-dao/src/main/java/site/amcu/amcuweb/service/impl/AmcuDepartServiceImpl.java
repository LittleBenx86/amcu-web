package site.amcu.amcuweb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import site.amcu.amcuweb.dao.AmcuDepartMapper;
import site.amcu.amcuweb.entity.AmcuDepart;
import site.amcu.amcuweb.service.AmcuDepartService;
import site.amcu.amcuweb.vo.DepartVO;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:    AMCU协会部门/任职分类数据服务层接口实现
 * @Author: Ben-Zheng
 * @Date: 2018/11/13 10:21
 */
@Service
@Transactional(propagation = Propagation.NESTED, isolation = Isolation.DEFAULT, readOnly = false)
public class AmcuDepartServiceImpl implements AmcuDepartService {

    @Resource
    private AmcuDepartMapper departMapper;

    @Override
    public List<DepartVO> findAllDepartment() {
        List<AmcuDepart> res = departMapper.selectList(new QueryWrapper<AmcuDepart>()
                .lambda().between(AmcuDepart::getId, 1, 7));
        List<DepartVO> departVOS = new ArrayList<>();
        res.stream().forEach(d -> departVOS.add(new DepartVO(d.getId(), d.getTitle())));
        return departVOS;
    }

    @Override
    public List<DepartVO> findAllJobByDepartmentNo(Integer departNo) {
        List<AmcuDepart> res = departMapper.selectList(new QueryWrapper<AmcuDepart>()
                .lambda().eq(AmcuDepart::getDepartId, departNo));
        List<DepartVO> departVOS = new ArrayList<>();
        res.stream().forEach(d -> departVOS.add(new DepartVO(d.getId(), d.getTitle())));
        return departVOS;
    }
}
