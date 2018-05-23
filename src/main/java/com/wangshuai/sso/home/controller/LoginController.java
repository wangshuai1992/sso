package com.wangshuai.sso.home.controller;

import com.wangshuai.sso.common.LoginUser;
import com.wangshuai.sso.common.Result;
import com.wangshuai.sso.common.ResultCode;
import com.wangshuai.sso.common.utils.CaptchaHelper;
import com.wangshuai.sso.common.utils.CookieUtils;
import com.wangshuai.sso.common.utils.IdProvider;
import com.wangshuai.sso.common.utils.Validator;
import com.wangshuai.sso.home.annotation.ValidateParam;
import com.wangshuai.sso.home.constants.SsoConstants;
import com.wangshuai.sso.manager.token.TokenManager;
import com.wangshuai.sso.manager.user.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

/**
 * 单点登录管理
 *
 * @author wangshuai
 * @version V1.0
 * @date 2017-11-20 17:57
 */
@Controller
public class LoginController {
    /**
     * 登录页
     */
    private static final String LOGIN_PATH = "/login";

    @Resource
    private TokenManager tokenManager;

    @Resource
    private UserService userService;

    /**
     * 登录页
     *
     * @param backUrl
     * @param appCode
     * @param request
     * @return
     */
    @RequestMapping(value = "/login")
    public String login(@ValidateParam({Validator.NOT_BLANK}) String backUrl,
                        @ValidateParam({Validator.NOT_BLANK}) String appCode,
                        HttpServletRequest request) {
        String token = CookieUtils.getCookie(request, "token");
        if (token == null) {
            return goLoginPath(backUrl, appCode, request);
        } else {
            LoginUser loginUser = tokenManager.validate(token);
            if (loginUser != null) {
                return "redirect:" + authBackUrl(backUrl, token);
            } else {
                return goLoginPath(backUrl, appCode, request);
            }
        }
    }

    /**
     * 登录提交请求
     *
     * @param backUrl
     * @param appCode
     * @param account
     * @param password
     * @param captcha
     * @param request
     * @param response
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value = "/login.json")
    public String login(@ValidateParam({Validator.NOT_BLANK}) String backUrl,
                        @ValidateParam({Validator.NOT_BLANK}) String appCode,
                        @ValidateParam({Validator.NOT_BLANK}) String account,
                        @ValidateParam({Validator.NOT_BLANK}) String password,
                        @ValidateParam({Validator.NOT_BLANK}) String captcha,
                        HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        if (!CaptchaHelper.validate(request, captcha)) {
            request.setAttribute("errorMessage", "验证码不正确");
            return goLoginPath(backUrl, appCode, request);
        }
        Result<LoginUser> result = userService.login(account, password);
        if (!result.getCode().equals(ResultCode.SUCCESS)) {
            request.setAttribute("errorMessage", result.getMessage());
            return goLoginPath(backUrl, appCode, request);
        } else {
            LoginUser loginUser = result.getData();
            String token = CookieUtils.getCookie(request, "token");
            if (StringUtils.isBlank(token) || tokenManager.validate(token) == null) {// 没有登录的情况
                token = createToken(loginUser);
                addTokenInCookie(token, request, response);
            }

            // 跳转到原请求
            backUrl = URLDecoder.decode(backUrl, StandardCharsets.UTF_8.name());
            return "redirect:" + authBackUrl(backUrl, token);
        }
    }

    private String goLoginPath(String backUrl, String appCode, HttpServletRequest request) {
        request.setAttribute("backUrl", backUrl);
        request.setAttribute("appCode", appCode);

        ServletContext servletContext = request.getServletContext();
        servletContext.setAttribute("_path", servletContext.getContextPath());
        return LOGIN_PATH;
    }

    private String authBackUrl(String backUrl, String token) {
        StringBuilder sbf = new StringBuilder(backUrl);
        if (backUrl.indexOf('?') > 0) {
            sbf.append('&');
        } else {
            sbf.append('?');
        }
        sbf.append(SsoConstants.SSO_TOKEN_NAME).append('=').append(token);
        return sbf.toString();
    }

    private String createToken(LoginUser loginUser) {
        // 生成token
        String token = IdProvider.createUUIDId();

        // 缓存中添加token对应User
        tokenManager.addToken(token, loginUser);
        return token;
    }

    private void addTokenInCookie(String token, HttpServletRequest request, HttpServletResponse response) {
        // Cookie添加token
        Cookie cookie = new Cookie("token", token);
        cookie.setPath("/");
        if ("https".equals(request.getScheme())) {
            cookie.setSecure(true);
        }
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }
}
