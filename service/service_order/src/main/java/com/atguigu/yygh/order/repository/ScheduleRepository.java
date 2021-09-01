package com.atguigu.yygh.order.repository;

import com.atguigu.yygh.model.hosp.Schedule;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface ScheduleRepository extends MongoRepository<Schedule,String> {
    Schedule findScheduleById(String scheduleId);
}