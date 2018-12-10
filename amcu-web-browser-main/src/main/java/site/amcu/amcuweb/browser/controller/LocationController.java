package site.amcu.amcuweb.browser.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import site.amcu.amcuweb.service.AreaService;
import site.amcu.amcuweb.vo.AreaVO;
import site.amcu.amcuweb.vo.Response;

import javax.annotation.security.RolesAllowed;
import java.util.List;

/**
 * @Description:    获取省市区地址的控制器
 * @Author: Ben-Zheng
 * @Date: 2018/12/10 15:45
 */
@RestController
@RequestMapping("/location")
public class LocationController {

    @Autowired
    private AreaService areaService;

    @RolesAllowed({"USER", "ADMIN"})
    @GetMapping("/provinces")
    public ResponseEntity<Response> getAllProvinces() {
        List<AreaVO> provinces = areaService.findAllProvince();
        return null == provinces ?
                ResponseEntity.ok().body(new Response(false, "获取省份数据错误", HttpStatus.INTERNAL_SERVER_ERROR.value())) :
                ResponseEntity.ok().body(new Response(true, "", HttpStatus.OK.value(), provinces));
    }

    @RolesAllowed({"USER", "ADMIN"})
    @GetMapping("/cities")
    public ResponseEntity<Response> getAllCities(@RequestParam(value = "code") String code) {
        List<AreaVO> cities = areaService.findAllCitiesByProvincesCode(code);
        return 0 == cities.size() ?
                ResponseEntity.ok().body(new Response(false, "获取城市数据错误", HttpStatus.INTERNAL_SERVER_ERROR.value())) :
                ResponseEntity.ok().body(new Response(true, "", HttpStatus.OK.value(), cities));
    }

    @RolesAllowed({"USER", "ADMIN"})
    @GetMapping("/areas")
    public ResponseEntity<Response> getAllAreas(@RequestParam(value = "code") String code) {
        List<AreaVO> areas = areaService.findAllAreasByCityCode(code);
        return 0 == areas.size() ?
                ResponseEntity.ok().body(new Response(false, "获取市区数据错误", HttpStatus.INTERNAL_SERVER_ERROR.value())) :
                ResponseEntity.ok().body(new Response(true, "", HttpStatus.OK.value(), areas));
    }

}
