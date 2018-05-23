package com.wangshuai.sso.share.service.impl;

import com.wangshuai.sso.common.LoginUser;
import com.wangshuai.sso.manager.token.TokenManager;
import com.wangshuai.sso.share.dto.RpcUser;
import com.wangshuai.sso.share.service.AuthenticationRpcService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author wangshuai
 * @version V1.0
 * @date 2017-11-20 18:33
 */
@Service
public class AuthenticationRpcServiceImpl implements AuthenticationRpcService {

    @Resource
    private TokenManager tokenManager;

    @Override
    public boolean validate(String token) {
        return tokenManager.validate(token) != null;
    }

    @Override
    public RpcUser findAuthInfo(String token) {
        LoginUser user = tokenManager.validate(token);
        if (user != null) {
            return new RpcUser(user.getLoginId());
        }
        return null;
    }

}
