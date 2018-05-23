package com.wangshuai.sso.client.filter;


import com.wangshuai.sso.common.SessionUser;
import com.wangshuai.sso.common.SsoResultCode;
import com.wangshuai.sso.common.exception.ServiceException;
import com.wangshuai.sso.common.utils.SessionUtils;
import com.wangshuai.sso.home.constants.SsoConstants;
import com.wangshuai.sso.share.dto.RpcUser;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 单点登录及Token验证Filter
 * @author wangshuai
 * @version V1.0
 * @date 2017-11-20 18:26
 */
public class SsoFilter extends ClientFilter  {

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String token = getLocalToken(request);
        if (token == null) {
            if (getParameterToken(request) != null) {
                // 再跳转一次当前URL，以便去掉URL中token参数
                response.sendRedirect(request.getRequestURL().toString());
            }
            else
                redirectLogin(request, response);
        }
        else if (isLogined(token))
            chain.doFilter(request, response);
        else
            redirectLogin(request, response);
    }

    /**
     * 获取Session中token
     *
     * @param request
     * @return
     */
    private String getLocalToken(HttpServletRequest request) {
        SessionUser sessionUser = SessionUtils.getSessionUser(request);
        return sessionUser == null ? null : sessionUser.getToken();
    }

    /**
     * 获取服务端回传token参数且验证
     *
     * @param request
     * @return
     * @throws IOException
     */
    private String getParameterToken(HttpServletRequest request) throws IOException {
        String token = request.getParameter(SsoConstants.SSO_TOKEN_NAME);
        if (token != null) {
            RpcUser rpcUser = authenticationRpcService.findAuthInfo(token);
            if (rpcUser != null) {
                invokeAuthenticationInfoInSession(request, token, rpcUser.getLoginId());
                return token;
            }
        }
        return null;
    }

    /**
     * 跳转登录
     *
     * @param request
     * @param response
     * @throws IOException
     */
    private void redirectLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (isAjaxRequest(request)) {
            throw new ServiceException(SsoResultCode.SSO_TOKEN_ERROR, "未登录或已超时");
        }
        else {
            SessionUtils.invalidate(request);
            String ssoLoginUrl = ssoServerUrl + "/login?backUrl=" +
                    request.getRequestURL() + "&appCode=" + ssoAppCode;

            response.sendRedirect(ssoLoginUrl);
        }
    }

    /**
     * 保存认证信息到Session
     *
     * @param request
     * @param token
     * @param account
     */
    private void invokeAuthenticationInfoInSession(HttpServletRequest request, String token, String account) {
        SessionUtils.setSessionUser(request, new SessionUser(token, account));
    }

    /**
     * 是否已登录
     *
     * @param token
     * @return
     */
    private boolean isLogined(String token) {
        return authenticationRpcService.validate(token);
    }

    /**
     * 是否Ajax请求
     *
     * @param request
     * @return
     */
    private boolean isAjaxRequest(HttpServletRequest request) {
        String requestedWith = request.getHeader("X-Requested-With");
        return requestedWith != null && "XMLHttpRequest".equals(requestedWith);
    }
}
