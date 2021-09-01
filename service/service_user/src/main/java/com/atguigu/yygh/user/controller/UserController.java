package com.atguigu.yygh.user.controller;


import com.atguigu.yygh.common.result.Result;
import com.atguigu.yygh.model.user.UserInfo;
import com.atguigu.yygh.user.service.UserInfoService;
import com.atguigu.yygh.vo.user.UserInfoQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Api("后台用户管理接口")
@RestController
@RequestMapping("/admin/user")
public class UserController {

    @Autowired
    private UserInfoService userInfoService;


    /**
     * 用户列表（条件加分页）
     * @param page
     * @param limit
     * @param userInfoQueryVo
     * @return
     */
    @ApiOperation("条件查询用户列表并分页接口")
    @GetMapping("{page}/{limit}")
    public Result list(@PathVariable Long page, @PathVariable Long limit, UserInfoQueryVo userInfoQueryVo){
        Page<UserInfo> pageParm = new Page<>(page, limit);
        IPage<UserInfo> pageModel =  userInfoService.selectPage(pageParm, userInfoQueryVo);
        return Result.ok(pageModel);
    }

    /**
     * 更改用户锁定状态接口
     * @param userId
     * @param status
     * @return
     */
    @ApiOperation("更改用户锁定状态接口")
    @GetMapping("lock/{userId}/{status}")
    public Result lock(@PathVariable Long userId, @PathVariable Integer status){
        userInfoService.lock(userId, status);
        return Result.ok();
    }

    /**
     * 展示某用户详情接口
     * @param userId
     * @return
     */
    @ApiOperation("展示某用户详情接口")
    @GetMapping("show/{userId}")
    public Result show(@PathVariable Long userId){
        Map<String,Object> map = userInfoService.show(userId);
        return Result.ok(map);
    }

    /**
     * 用户认证审批接口
     * @param userId
     * @param authStatus
     * @return
     */
    @ApiOperation("用户认证审批接口")
    @GetMapping("approve/{userId}/{authStatus}")
    public Result approve(@PathVariable Long userId, @PathVariable Integer authStatus){
        userInfoService.approve(userId,authStatus);
        return Result.ok();
    }

    /**
     * 根据用户认证的姓名，模糊查询叫该用户的所用用户列表，用作远程调用
     * @param username
     * @return
     */
    @ApiOperation("根据认证用户姓名模糊查询用户列表")
    @GetMapping("findUserListByUserName/{username}")
    public List<UserInfo> findUserListByUserName(@PathVariable String username){
        List<UserInfo> userInfoList =  userInfoService.findUserListByUserName(username);
        return userInfoList;
    }

    /**
     * 根据用户id返回用户信息，用作远程调用接口
     * @param userId
     * @return
     */
    @ApiOperation("根据用户id查询用户认证姓名")
    @GetMapping("findUserById/{userId}")
    public UserInfo findUserById(@PathVariable Long userId){
        UserInfo userInfo = userInfoService.getById(userId);
        return userInfo;
    }

}
