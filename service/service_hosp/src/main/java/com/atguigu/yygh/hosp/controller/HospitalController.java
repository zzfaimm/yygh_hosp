package com.atguigu.yygh.hosp.controller;


import com.atguigu.yygh.common.result.Result;
import com.atguigu.yygh.hosp.service.HospitalService;
import com.atguigu.yygh.model.hosp.Hospital;
import com.atguigu.yygh.vo.hosp.HospitalQueryVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/admin/hosp/hospital")
//@CrossOrigin
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;

    /**
     * 医院列表，条件查询带分页
     * @param page 当前页
     * @param limit 每页记录数
     * @param hospitalQueryVo
     * @return
     */
    @ApiOperation("医院列表分页")
    @GetMapping("list/{page}/{limit}")
    public Result listHospital(@PathVariable Integer page,
                               @PathVariable Integer limit,
                               HospitalQueryVo hospitalQueryVo){
        Page<Hospital> pageModel =  hospitalService.selectHospPage(page,limit,hospitalQueryVo);
        return Result.ok(pageModel);
    }

    /**
     * 更新医院上线状态
     * @param id
     * @param status
     * @return
     */
    @ApiOperation("更新医院上线状态接口")
    @GetMapping("updateHospStatus/{id}/{status}")
    public Result updateHospStatus(@PathVariable String id, @PathVariable Integer status){
        hospitalService.updateStatus(id,status);
        return Result.ok();
    }

    /**
     * 医院详情信息接口
     * @param id
     * @return
     */
    @ApiOperation("医院详情信息接口")
    @GetMapping("showHospDetail/{id}")
    public Result showHospDetail(@PathVariable String id){
        Map<String,Object> map = hospitalService.getHospById(id);
        return Result.ok(map);
    }
}
