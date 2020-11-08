package com.cn.hydra.aspectdemo.token.jwt;

import io.jsonwebtoken.*;

import java.util.Date;
import java.util.Map;

/**
 * @program: aspectdemo
 * @author: Hydra
 * @create: 2020-11-02 14:19
 **/
public class TokenUtils {

    private static final String secret="hydra";

    private static int expireTime=1;

    protected static final long MILLIS_SECOND = 1000;

    protected static final long MILLIS_MINUTE = 60 * MILLIS_SECOND;

    private static final String ISSURE="hydra";

    public static String createTokenForWechat(Map<String, Object> claims) {
        String token = Jwts.builder()
                .setIssuer(ISSURE)
                .setIssuedAt(new Date()) // 签发时间
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() +expireTime*MILLIS_MINUTE))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
        return token;
    }

    public static String getTokenField(String token,String field) {
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
        return (String)claims.get(field);
    }

    public static boolean verifyTokenExpire(String token){
        try{
            Claims claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();

            System.out.println((claims.getExpiration()));
            System.out.println(getTokenField(token, "name"));
            return new Date().before(claims.getExpiration());
        }catch (ExpiredJwtException e){
            e.printStackTrace();
            System.out.println("token 过期");
        }catch (SignatureException e){
            e.printStackTrace();
            System.out.println("token 错误");
        }
        return false;
    }
}
