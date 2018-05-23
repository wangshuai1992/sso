package com.wangshuai.sso.common;

import java.io.Serializable;

/**
 * 登录成功用户对象
 *
 * @author wangshuai
 * @version V1.0
 * @date 2017-11-20 18:00
 */
public class LoginUser implements Serializable {
    private static final long serialVersionUID = 4507869346123296527L;

    /**
     * 登录成功ID
     */
    private Long userId;

    /**
     * 登录成功用户名
     */
    private String loginId;

    public LoginUser(Long userId, String loginId) {
        super();
        this.userId = userId;
        this.loginId = loginId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        LoginUser other = (LoginUser) obj;
        if (userId == null) {
            if (other.userId != null)
                return false;
        } else if (!userId.equals(other.userId))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = userId.hashCode();
        return 31 * result + loginId.hashCode();
    }
}
