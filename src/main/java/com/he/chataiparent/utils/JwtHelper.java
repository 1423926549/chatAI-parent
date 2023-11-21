package com.he.chataiparent.utils;

import com.he.chataiparent.common.exception.ChatException;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JwtHelper {

    // 过期时间
    private static final long shortTokenExpiration = 60 * 60 * 1000L;
    private static final long longTokenExpiration = 24 * 60 * 60 * 1000L;
    // 密钥
    private static final String tokenSignKey = "chat";

    /**
     * 生成token
     * @param userId 用户id
     * @param userName 用户名字
     * @return token
     */
    public static String createShortToken(Long userId, String userName) {
        String token = Jwts.builder()
                .setSubject("chat-USER")  // 设置分组

                .setExpiration(new Date(System.currentTimeMillis() + shortTokenExpiration))  // 设置过期时间

                // 设置私有部分（自己需要设置的东西）
                .claim("userId", userId)
                .claim("userName", userName)

                .signWith(SignatureAlgorithm.HS512, tokenSignKey)  // 编码方式及密钥

                .compressWith(CompressionCodecs.GZIP)  // 压缩
                .compact();
        return token;
    }

    public static String createLongToken(Long userId, String userName) {
        String token = Jwts.builder()
                .setSubject("chat-USER")  // 设置分组

                .setExpiration(new Date(System.currentTimeMillis() + longTokenExpiration))  // 设置过期时间

                // 设置私有部分（自己需要设置的东西）
                .claim("userId", userId)
                .claim("userName", userName)

                .signWith(SignatureAlgorithm.HS512, tokenSignKey)  // 编码方式及密钥

                .compressWith(CompressionCodecs.GZIP)  // 压缩
                .compact();
        return token;
    }

    public static Map<String, String> refresh(String token) {
        Map<String, String> map = new HashMap<>();
        try {
            Long userId = getUserId(token);
            String userName = getUserName(token);
            String shortToken = JwtHelper.createShortToken(userId, userName);
            String longToken = JwtHelper.createLongToken(userId, userName);
            map.put("shortToken", shortToken);
            map.put("longToken", longToken);
        } catch (Exception e) {
            log.info("token已失效，请重新登录");
            throw new ChatException(401, "token已失效，请重新登录");
        }
        return map;
    }

    public static Long getUserId(String token) {
        if(token.isBlank()) return null;

        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        return (Long) claims.get("userId");
        // return 1L;
    }

    public static String getUserName(String token) {
        if(token.isBlank()) return "";

        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        return (String)claims.get("userName");
    }

    public static void removeToken(String token) {
        //jwttoken无需删除，客户端扔掉即可。
    }

    public static void main(String[] args) throws InterruptedException {
        String token = JwtHelper.createShortToken(1726247319990394882L, "user0001");
        System.out.println(token);
        System.out.println(JwtHelper.getUserId(token));
        System.out.println(JwtHelper.getUserName(token));
    }
}