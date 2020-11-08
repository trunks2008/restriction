package com.cn.hydra.aspectdemo.token.service;

import com.cn.hydra.aspectdemo.token.annotation.NeedToken;
import com.cn.hydra.aspectdemo.token.test.BaseService2;
import org.springframework.stereotype.Service;

/**
 * @program: aspectdemo
 * @author: Hydra
 * @create: 2020-10-30 14:29
 **/
@Service
public class MyService2 extends BaseService2 {

    @NeedToken
    public boolean testToken(String name) {
        String token=TOKEN.get();
        boolean check = name.equals(token);
        System.out.println(name+"  "+token +"  "+check);
        return  check;
    }
}
