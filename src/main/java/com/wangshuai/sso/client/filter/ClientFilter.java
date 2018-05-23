package com.wangshuai.sso.client.filter;

import com.alibaba.fastjson.JSON;
import com.wangshuai.sso.common.Result;
import com.wangshuai.sso.common.exception.ServiceException;
import com.wangshuai.sso.share.service.AuthenticationRpcService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

/**
 * 单点登录权限系统Filter基类
 *
 * @author wangshuai
 * @version V1.0
 * @date 2017-11-20 18:27
 */
public abstract class ClientFilter implements Filter {

    protected Environment environment;

    /**
     * 单点登录服务端URL
     */
    protected String ssoServerUrl;

    /**
     * 当前应用关联权限系统的应用编码
     */
    protected String ssoAppCode;

    /**
     * 单点登录服务端提供的RPC服务，由Spring容器注入
     */
    protected AuthenticationRpcService authenticationRpcService;

    /**
     * 排除拦截
     */
    protected List<String> excludeList = null;

    private PathMatcher pathMatcher = null;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        if (StringUtils.isBlank(ssoServerUrl = environment.getProperty("sso.server"))) {
            throw new IllegalArgumentException("ssoServerUrl不能为空");
        }
        if (StringUtils.isBlank(ssoAppCode = environment.getProperty("sso.app.code"))) {
            throw new IllegalArgumentException("ssoAppCode不能为空");
        }
        if (authenticationRpcService == null) {
            throw new IllegalArgumentException("authenticationRpcService注入失败");
        }

        String excludes = filterConfig.getInitParameter("excludes");
        if (StringUtils.isNotBlank(excludes)) {
            excludeList = Arrays.asList(excludes.split(","));
            pathMatcher = new AntPathMatcher();
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        if (matchExcludePath(httpRequest.getServletPath()))
            chain.doFilter(request, response);
        else {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            try {
                doFilter(httpRequest, httpResponse, chain);
            } catch (ServiceException e) {
                httpResponse.setContentType("application/json;charset=UTF-8");
                httpResponse.setStatus(HttpStatus.OK.value());
                PrintWriter writer = httpResponse.getWriter();
                writer.write(JSON.toJSONString(Result.create(e.getCode()).setMessage(e.getMessage())));
                writer.flush();
                writer.close();
            }
        }
    }

    private boolean matchExcludePath(String path) {
        if (excludeList != null) {
            for (String ignore : excludeList) {
                if (pathMatcher.match(ignore, path)) {
                    return true;
                }
            }
        }
        return false;
    }

    public abstract void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException, ServiceException;

    @Override
    public void destroy() {
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public void setAuthenticationRpcService(AuthenticationRpcService authenticationRpcService) {
        this.authenticationRpcService = authenticationRpcService;
    }
}
