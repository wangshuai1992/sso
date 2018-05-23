package com.wangshuai.sso.common;

/**
 * 单点登录权限返回码
 * @author wangshuai
 * @version V1.0
 * @date 2017-11-20 18:47
 */
public class SsoResultCode {
    // SSO 用户授权出错
    public final static int SSO_TOKEN_ERROR = 1001; // TOKEN未授权或已过期
    public final static int SSO_PERMISSION_ERROR = 1002; // 没有访问权限
}
