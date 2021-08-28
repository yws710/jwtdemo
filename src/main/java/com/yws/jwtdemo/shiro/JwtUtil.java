package com.yws.jwtdemo.shiro;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

/**
 * 简单的JWT加密、校验工具<br>
 * 保证token即使被他人截获也不能破解。<br>
 * 在token中附带username，设置过期时间。
 * @author yaoweisong
 *
 */
public class JwtUtil {

	private static final long EXPIRE_TIME = 5*60*1000;
	
	/**
	 * 校验token是否正确
	 * @param token
	 * @param username
	 * @param secret
	 * @return
	 */
	public static boolean verify(String token, String username, String secret) {
		try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("username", username)
                    .build();
            verifier.verify(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
	}
	
	/**
	 * 获取token中的用户名<br>
	 * @param token
	 * @return
	 */
	public static String getUsername(String token) {
		try {
            DecodedJWT jwt = JWT.decode(token);
            Date expiresAt = jwt.getExpiresAt();
            System.out.println("expiresAt=" + expiresAt);
            return jwt.getClaim("username").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
	}
	
	/**
	 * 生成签名，设置过期时间
	 * @param username 用户名
	 * @param secret 用户的密码
	 * @return
	 */
	public static String sign(String username,String secret) {
		Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			return JWT.create()
					.withClaim("username", username)
					.withExpiresAt(date)
					.sign(algorithm);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return null;
	}
}
