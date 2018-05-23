package com.wangshuai.sso.manager.user;


import com.wangshuai.sso.common.LoginUser;
import com.wangshuai.sso.common.Result;

/**
 * 接入的用户服务
 *
 * @author wangshuai
 * @version V1.0
 * @date 2017-11-21 16:37
 */
public interface UserService {

    Result<LoginUser> login(String userName, String pwd);

}
