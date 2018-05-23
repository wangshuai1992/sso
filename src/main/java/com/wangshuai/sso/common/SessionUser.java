package com.wangshuai.sso.common;

/**
 * 已登录用户信息
 * @author wangshuai
 * @version V1.0
 * @date 2017-11-20 18:45
 */
public class SessionUser {
    private static final long serialVersionUID = 1764365572138947234L;

    /**
     * 登录用户访问Token
     */
    private String token;

    /**
     * 登录名
     */
    private String loginId;

    public SessionUser() {
        super();
    }

    public SessionUser(String token, String loginId) {
        super();
        this.token = token;
        this.loginId = loginId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }
}
