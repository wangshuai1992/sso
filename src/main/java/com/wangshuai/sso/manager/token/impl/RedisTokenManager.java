package com.wangshuai.sso.manager.token.impl;


import com.wangshuai.sso.common.LoginUser;
import com.wangshuai.sso.common.cache.RedisCache;
import com.wangshuai.sso.manager.token.TokenManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 分布式环境令牌管理(使用redis)
 *
 * @author wangshuai
 * @version V1.0
 * @date 2017-11-21 10:06
 */
@Service
public class RedisTokenManager extends TokenManager {

    /**
     * 是否需要扩展token过期时间
     */
    private Set<String> tokenSet = new CopyOnWriteArraySet<String>();

    @Resource
    private RedisCache<LoginUser> redisCache;

    @Override
    public void addToken(String token, LoginUser loginUser) {
        redisCache.set(token, loginUser, tokenTimeout * 1000);
    }

    @Override
    public LoginUser validate(String token) {
        LoginUser loginUser = redisCache.get(token);
        if (loginUser != null && !tokenSet.contains(token)) {
            tokenSet.add(token);
            addToken(token, loginUser);
        }
        return loginUser;
    }

    @Override
    public void remove(String token) {
        redisCache.delete(token);
    }

    @Override
    public void verifyExpired() {
        tokenSet.clear();
    }
}
