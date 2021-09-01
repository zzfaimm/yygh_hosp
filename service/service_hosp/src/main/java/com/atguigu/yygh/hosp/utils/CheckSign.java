package com.atguigu.yygh.hosp.utils;


import com.atguigu.yygh.common.utils.MD5;
import com.atguigu.yygh.hosp.service.HospitalSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import javax.annotation.PostConstruct;
import java.util.Map;

@Component
public class CheckSign {
    @Autowired
    private HospitalSetService hospitalSetService;

    public static CheckSign checkSign;

    /**
     * @PostConstruct 注解的作用是在加载类的构造函数之后执行，也就是在加载了构造函数之后，执行init方法；
     * (@PreDestroy 注解定义容器销毁之前的所做的操作)
     */
    @PostConstruct
    public void init(){
        checkSign = this;
    }


    public static boolean checkSignEquals(Map<String, Object> paramMap){
        //获取医院编号
        String hoscode = (String) paramMap.get("hoscode");
        //1.获取医院系统传递过来的前面sign ,已经进行了MD5加密
        String hospSign = (String) paramMap.get("sign");

        //2.根据传递过来医院编号，查询签名
        String signKey = checkSign.hospitalSetService.getSignKey(hoscode);

        //3.把数据库查询签名进行MD5加密
        String signKeyMd5 = MD5.encrypt(signKey);

        return hospSign.equals(signKeyMd5);
    }
}
