package com.wangshuai.sso.client.filter;

import com.wangshuai.sso.common.utils.SessionUtils;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 单点退出Filter
 *
 * @author wangshuai
 * @version V1.0
 * @date 2017-11-21 18:00
 */
public class LogoutFilter extends ClientFilter {
    /**
     * 单点退出成功后跳转页(配置当前应用上下文相对路径，不设置或为空表示项目根目录)
     */
    protected String ssoBackUrl;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        super.init(filterConfig);
        ssoBackUrl = filterConfig.getInitParameter("ssoBackUrl");
        if (ssoBackUrl == null)
            ssoBackUrl = "";
    }

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        SessionUtils.invalidate(request);
        String ssoLogoutUrl = ssoServerUrl + "/logout?backUrl=" +
                getLocalUrl(request) + ssoBackUrl;
        response.sendRedirect(ssoLogoutUrl);
    }

    /**
     * 获取当前上下文路径
     *
     * @param request
     * @return
     */
    private String getLocalUrl(HttpServletRequest request) {
        return request.getScheme() + "://" + request.getServerName() +
                ":" + (request.getServerPort() == 80 ? "" : request.getServerPort()) +
                request.getContextPath();
    }
}
