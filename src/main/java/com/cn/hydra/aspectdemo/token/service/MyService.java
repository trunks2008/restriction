package com.cn.hydra.aspectdemo.token.service;

import com.cn.hydra.aspectdemo.token.annotation.NeedToken;
import com.cn.hydra.aspectdemo.token.test.BaseService;
import org.springframework.stereotype.Service;

/**
 * @program: aspectdemo
 * @author: Hydra
 * @create: 2020-10-30 14:29
 **/
//@Service
public class MyService extends BaseService {

    @NeedToken
    public String test() {
        System.out.println(TOKEN);
        //验证token，处理业务逻辑
        return  TOKEN;
    }

    @NeedToken
    public boolean checkToken(String name) {
        System.out.println(name+"  "+TOKEN  +" "+ name.equals(TOKEN));
        return  name.equals(TOKEN);
    }

    @NeedToken
    public String test(String token){
        System.out.println(token);
        //验证token，处理业务逻辑
        return "success";
    }

}
