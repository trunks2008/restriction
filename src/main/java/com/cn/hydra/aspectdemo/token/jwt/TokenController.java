package com.cn.hydra.aspectdemo.token.jwt;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: aspectdemo
 * @author: Hydra
 * @create: 2020-11-02 14:22
 **/
@RestController
@RequestMapping("token")
public class TokenController {

    @RequestMapping("sign")
    public String sign(){
        Map<String, Object> claims =new HashMap<>();
        claims.put("name","aaa");
        String token = TokenUtils.createTokenForWechat(claims);
        return  token;
    }

    @RequestMapping("getTokenField")
    public String getTokenField(String token){
        String name = TokenUtils.getTokenField(token, "name");
        return  name;
    }

    @RequestMapping("verifyTokenExpire")
    public boolean verifyTokenExpire(String token){
        boolean expire = TokenUtils.verifyTokenExpire(token);
        return  expire;
    }
}
