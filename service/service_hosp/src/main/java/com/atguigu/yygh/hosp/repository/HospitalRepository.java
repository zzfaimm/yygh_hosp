package com.atguigu.yygh.hosp.repository;

import com.atguigu.yygh.model.hosp.Hospital;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HospitalRepository extends MongoRepository<Hospital, String> {

    //根据Hoscode查询hospitial
    Hospital getHospitalByHoscode(String hoscode);
    //根据医院名称查询医院信息
    List<Hospital> findHospitalByHosnameLike(String hosname);
}
