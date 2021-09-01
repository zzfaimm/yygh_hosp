package com.atguigu.yygh.order.controller.api;

import com.atguigu.yygh.common.result.Result;
import com.atguigu.yygh.common.utils.AuthContextHolder;
import com.atguigu.yygh.enums.OrderStatusEnum;
import com.atguigu.yygh.model.order.OrderInfo;
import com.atguigu.yygh.model.user.UserInfo;
import com.atguigu.yygh.order.service.OrderService;
import com.atguigu.yygh.vo.order.OrderCountQueryVo;
import com.atguigu.yygh.vo.order.OrderQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Api(tags = "订单接口")
@RestController
@RequestMapping("/api/order/orderInfo")
public class OrderApiController {

    @Autowired
    private OrderService orderService;

    /**
     * 根据scheduleId patientId生成挂号订单接口
     * @param scheduleId
     * @param patientId
     * @return
     */
    @ApiOperation("生成挂号订单接口")
    @PostMapping("auth/submitOrder/{scheduleId}/{patientId}")
    public Result saveOrder(@PathVariable String scheduleId,
                            @PathVariable Long patientId){
        Long orderId = orderService.saveOrder(scheduleId,patientId);
        return Result.ok(orderId);
    }

    /**
     * 根据订单id查询订单详情
     * @param orderId
     * @return
     */
    @ApiOperation("根据订单id查询订单详情")
    @GetMapping("auth/getOrder/{orderId}")
    public Result getOrder(@PathVariable Long orderId){
        OrderInfo orderInfo = orderService.getOrder(orderId);
        return Result.ok(orderInfo);
    }

    /**
     * 查询订单列表并进行分页
     * @param page
     * @param limit
     * @param orderQueryVo
     * @param request
     * @return
     */
    @ApiOperation("查询订单列表并分页")
    @GetMapping("auth/{page}/{limit}")
    public Result list(@PathVariable Long page, @PathVariable Long limit,
                       OrderQueryVo orderQueryVo, HttpServletRequest request){
        //设置当前用户id
        orderQueryVo.setUserId(AuthContextHolder.getUserId(request));
        Page<OrderInfo> pageParam = new Page<OrderInfo>(page, limit);
        IPage<OrderInfo> pageModel =  orderService.selectPage(pageParam, orderQueryVo);
        return Result.ok(pageModel);
    }

    /**
     * 返回订单状态
     * @return
     */
    @ApiOperation("获取订单状态")
    @GetMapping("auth/getStatusList")
    public Result getStatusList(){
        return Result.ok(OrderStatusEnum.getStatusList());
    }

    /**
     * 根据orderId 取消预约接口
     * @param orderId
     * @return
     */
    @ApiOperation("取消预约接口")
    @GetMapping("auth/cancelOrder/{orderId}")
    public Result cancelOrder(@PathVariable Long orderId){
        Boolean isOrder = orderService.cancelOrder(orderId);
        return Result.ok(isOrder);
    }

    /**
     * 获取订单统计数据接口
     * @param orderCountQueryVo
     * @return
     */
    @ApiOperation("获取订单统计数据")
    @PostMapping("inner/getCountMap")
    public Map<String,Object> getCountMap(@RequestBody OrderCountQueryVo orderCountQueryVo){
        return orderService.getCountMap(orderCountQueryVo);
    }


}
