package com.wangshuai.sso.share.dto;

import java.io.Serializable;

/**
 * RPC回传用户对象
 *
 * @author wangshuai
 * @version V1.0
 * @date 2017-11-20 18:34
 */
public class RpcUser implements Serializable {
    private static final long serialVersionUID = 4507869346123296527L;

    /**
     * 登录名
     */
    private String loginId;

    public RpcUser(String loginId) {
        super();
        this.loginId = loginId;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }
}
