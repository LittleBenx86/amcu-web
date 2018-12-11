package site.amcu.amcuweb.browser.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import site.amcu.amcuweb.service.AmcuDepartService;
import site.amcu.amcuweb.vo.DepartVO;
import site.amcu.amcuweb.vo.Response;

import javax.annotation.security.RolesAllowed;
import java.util.List;

/**
 * @Description:    单片机协会部门信息控制器
 * @Author: Ben-Zheng
 * @Date: 2018/12/11 19:15
 */
@RestController
@RequestMapping("/depart")
public class DepartmentController {

    @Autowired
    private AmcuDepartService departService;

    @RolesAllowed({"USER", "ADMIN"})
    @GetMapping
    public ResponseEntity<Response> getAllDepartments() {
        List<DepartVO> departs = departService.findAllDepartment();
        return 0 == departs.size() ?
                ResponseEntity.ok().body(new Response(false, "获取单片机协会部门数据错误", HttpStatus.INTERNAL_SERVER_ERROR.value())) :
                ResponseEntity.ok().body(new Response(true, "", HttpStatus.OK.value(), departs));
    }

    @RolesAllowed({"USER", "ADMIN"})
    @GetMapping("/jobs")
    public ResponseEntity<Response> getAllDepartmentJobs(@RequestParam(value = "departNo") String no) {
        List<DepartVO> jobs = departService.findAllJobByDepartmentNo(Integer.parseInt(no));
        return 0 == jobs.size() ?
                ResponseEntity.ok().body(new Response(false, "获取单片机协会部门工作数据错误", HttpStatus.INTERNAL_SERVER_ERROR.value())) :
                ResponseEntity.ok().body(new Response(true, "", HttpStatus.OK.value(), jobs));
    }

}
