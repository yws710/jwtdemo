package com.yws.jwtdemo.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * 创建 JWTToken替换Shiro原生token<br>
 * Shiro原生的token中存在用户名、密码，以及其他信息（验证码、记住我）<br>
 * @author yaoweisong
 *
 */
public class JWTToken implements AuthenticationToken {
	private static final long serialVersionUID = -5260185714618381770L;
	// 密钥
    private String token;

    public JWTToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}