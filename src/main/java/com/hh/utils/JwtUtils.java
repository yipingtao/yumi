package com.hh.utils;

import com.hh.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.joda.time.DateTime;

import java.security.PrivateKey;
import java.security.PublicKey;

public class JwtUtils {

    /**
     * 私钥加密token
     *
     * @param userInfo      载荷中的数据
     * @param privateKey    私钥
     * @param expireMinutes 过期时间，单位秒
     * @return
     * @throws Exception
     */

    private String privateKeys = "hwj";

    public static String generateToken(User userInfo, PrivateKey privateKey, int expireMinutes) throws Exception {
        return Jwts.builder()
                .claim("id", userInfo.getId())
                .claim("name", userInfo.getName())
                .setExpiration(DateTime.now().plusMinutes(expireMinutes).toDate())
                .signWith(SignatureAlgorithm.RS256, privateKey)
                .compact();
    }

    /**
     * 私钥加密token
     *
     * @param userInfo      载荷中的数据
     * @param privateKey    私钥字节数组
     * @param expireMinutes 过期时间，单位秒
     * @return
     * @throws Exception
     */
    public static String generateToken(User userInfo, byte[] privateKey, int expireMinutes) throws Exception {
        return Jwts.builder()
                .claim("id", userInfo.getId())
                .claim("name", userInfo.getName())
                .setExpiration(DateTime.now().plusMinutes(expireMinutes).toDate())
                .signWith(SignatureAlgorithm.RS256, RsaUtils.getPrivateKey(privateKey))
                .compact();
    }

    /**
     * 公钥解析token
     *
     * @param token     用户请求中的token
     * @param publicKey 公钥
     * @return
     * @throws Exception
     */
    private static Jws<Claims> parserToken(String token, PublicKey publicKey) {
        return Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token);
    }

    /**
     * 公钥解析token
     *
     * @param token     用户请求中的token
     * @param publicKey 公钥字节数组
     * @return
     * @throws Exception
     */
    private static Jws<Claims> parserToken(String token, byte[] publicKey) throws Exception {
        return Jwts.parser().setSigningKey(RsaUtils.getPublicKey(publicKey))
                .parseClaimsJws(token);
    }

    private static Jws<Claims> parserToken(String token, String publicKey) throws Exception {
        return Jwts.parser().setSigningKey(publicKey)
                .parseClaimsJws(token);
    }

    /**
     * 获取token中的用户信息
     *
     * @param token     用户请求中的令牌
     * @param publicKey 公钥
     * @return 用户信息
     * @throws Exception
     */
    public static User getInfoFromToken(String token, PublicKey publicKey) throws Exception {
        Jws<Claims> claimsJws = parserToken(token, publicKey);
        Claims body = claimsJws.getBody();
        User user = new User();
        user.setId(Integer.parseInt(body.get("id").toString()));
        user.setName(body.get("name").toString());
        return user;
    }

    /**
     * 获取token中的用户信息
     *
     * @param token     用户请求中的令牌
     * @param publicKey 公钥
     * @return 用户信息
     * @throws Exception
     */
    public static User getInfoFromToken(String token, byte[] publicKey) throws Exception {
        Jws<Claims> claimsJws = parserToken(token, publicKey);
        Claims body = claimsJws.getBody();
        User user = new User();
        user.setId(Integer.parseInt(body.get("id").toString()));
        user.setName(body.get("name").toString());
        return user;
    }


    /**
     * HS256  加密token
     *
     * @param userInfo      载荷中的数据
     * @param privateKey    私钥
     * @param expireMinutes 过期时间，单位秒
     * @return
     * @throws Exception
     */
    public static String generateToken(User userInfo, String privateKey, int expireMinutes) throws Exception {
        return Jwts.builder()
                .claim("id", userInfo.getId())
                .claim("name", userInfo.getName())
                .setExpiration(DateTime.now().plusMinutes(expireMinutes).toDate())
//                .signWith(SignatureAlgorithm.HS256, privateKey)
                .signWith(SignatureAlgorithm.HS256, "hwj2018")
                .compact();
    }

    /**
     * 获取token中的用户信息  HS256  加密token
     *
     * @param token      用户请求中的令牌
     * @param privateKey 私钥
     * @return 用户信息
     * @throws Exception
     */
    public static User getInfoFromToken(String token, String privateKey) throws Exception {
//        Jws<Claims> claimsJws = parserToken(token, privateKey);
        Jws<Claims> claimsJws = parserToken(token, "hwj2018");
        Claims body = claimsJws.getBody();
        User user = new User();
        user.setId(Integer.parseInt(body.get("id").toString()));
        user.setName(body.get("name").toString());
        return user;
    }
//    public static void main(String[] args) throws Exception {
//        String privateKey = "HUUUJ";
//        User user = new User();
//        user.setId(111);
//        user.setName("hwj");
//        String JwtString =  JwtUtils.generateToken(user,privateKey,10);
//        System.out.println("token="+JwtString);
//        User user1 = JwtUtils.getInfoFromToken("eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MTExLCJuYW1lIjoiaHdqIiwiZXhwIjoxNTQ4MTQxMjg4fQ.2_4nmEYjYWBZrrfKhcqlnDdDDt-1q6btSqE0Z2O-Qhg",privateKey);
//        System.out.println(user1);
//    }


}
