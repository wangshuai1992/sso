package com.wangshuai.sso.common.utils;

import com.wangshuai.sso.common.SessionUser;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 当前已登录用户Session
 *
 * @author wangshuai
 * @version V1.0
 * @date 2017-11-20 18:41
 */
public class SessionUtils {

    /**
     * 用户信息
     */
    public static final String SESSION_USER = "_sessionUser";

    public static SessionUser getSessionUser(HttpServletRequest request) {
        return (SessionUser) WebUtils.getSessionAttribute(request, SESSION_USER);
    }

    public static void setSessionUser(HttpServletRequest request, SessionUser sessionUser) {
        WebUtils.setSessionAttribute(request, SESSION_USER, sessionUser);
    }


    public static void invalidate(HttpServletRequest request) {
        setSessionUser(request, null);
    }
}
