package com.wangshuai.sso.home.controller;

import com.wangshuai.sso.common.utils.CookieUtils;
import com.wangshuai.sso.common.utils.SessionUtils;
import com.wangshuai.sso.manager.token.TokenManager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 单点登出
 *
 * @author wangshuai
 * @version V1.0
 * @date 2017-11-21 16:09
 */
@Controller
public class LogoutController {

    @Resource
    private TokenManager tokenManager;

    @RequestMapping("/logout")
    public String logout(String backUrl, HttpServletRequest request) {
        String token = CookieUtils.getCookie(request, "token");
        if (StringUtils.isNotBlank(token)) {
            tokenManager.remove(token);
        }
        SessionUtils.invalidate(request);
        return "redirect:" + (StringUtils.isBlank(backUrl) ? "/admin/admin" : backUrl);
    }
}
