package com.atguigu.yygh.msm.controller;


import com.atguigu.yygh.common.result.Result;
import com.atguigu.yygh.msm.service.MsmService;
import com.atguigu.yygh.msm.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/msm")
public class MsmApiController {
    @Autowired
    private MsmService msmService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    //发送手机验证码
    @GetMapping("send/{phone}")
    public Result sendCode(@PathVariable String phone){
        //从redis里面获取
        //key 手机号 value 验证码
        String code = redisTemplate.opsForValue().get(phone);
        if(!StringUtils.isEmpty(code)){
            //redis有，直接返回
            return Result.ok();
        }

        //redis获取不到，生成验证码，容联云
        String sixBitRandom = RandomUtil.getSixBitRandom();
        //调用service方法，整合短信服务进行发送
        boolean isSend = msmService.send(phone, sixBitRandom);
        //生成验证码放到redis中，并设置有效时间
        if(isSend){
            redisTemplate.opsForValue().set(phone,sixBitRandom,2, TimeUnit.MINUTES);
            return Result.ok();
        }else{
            return Result.fail().message("发送短信失败");
        }

    }
}
