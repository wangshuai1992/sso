package com.wangshuai.sso.share.service;


import com.wangshuai.sso.share.dto.RpcUser;

/**
 * 身份认证授权服务接口
 *
 * @author wangshuai
 * @version V1.0
 * @date 2017-11-20 18:30
 */
public interface AuthenticationRpcService {
    /**
     * 验证是否已经登录
     *
     * @param token 授权码
     * @return
     */
    boolean validate(String token);

    /**
     * 根据登录的Token和应用编码获取授权用户信息
     *
     * @param token 授权码
     * @return
     */
    RpcUser findAuthInfo(String token);

}
