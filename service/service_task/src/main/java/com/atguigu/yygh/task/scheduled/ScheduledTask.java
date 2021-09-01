package com.atguigu.yygh.task.scheduled;


import com.atguigu.common.rabbit.constant.MqConst;
import com.atguigu.common.rabbit.service.RabbitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling   //开启定时任务
public class ScheduledTask {

    @Autowired
    private RabbitService rabbitService;

    /**
     * 每天8点 提醒就诊
     */
    @Scheduled(cron = "0/20 * * * * ?") //每20秒发一次，用于测试
    public void taskPatient(){
        rabbitService.sendMessage(MqConst.EXCHANGE_DIRECT_TASK, MqConst.ROUTING_TASK_8, "");
    }
}
