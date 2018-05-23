package com.wangshuai.sso.manager.user.impl;

import com.wangshuai.sso.common.LoginUser;
import com.wangshuai.sso.common.Result;
import com.wangshuai.sso.manager.user.UserService;
import org.springframework.stereotype.Service;

/**
 * TODO
 *
 * @author wangshuai
 * @version V1.0
 * @date 2018-01-06 23:14
 */
@Service
public class UserServiceImpl implements UserService {

    @Override
    public Result<LoginUser> login(String userName, String pwd) {
        Result<LoginUser> result = new Result<>();
        result.setData(new LoginUser(1L, "wangshuai"));
        return result;
    }
}
