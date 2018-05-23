package com.wangshuai.sso.common.exception;


import com.wangshuai.sso.common.ResultCode;

/**
 * 业务逻辑异常
 *
 * @author wangshuai
 * @version V1.0
 * @date 2017-11-20 18:28
 */
public class ServiceException extends ApplicationException {
    private static final long serialVersionUID = -2678203134198782909L;

    public static final String MESSAGE = "业务逻辑异常";

    public ServiceException() {
        super(MESSAGE);
    }

    public ServiceException(String message) {
        super(message);
        this.code = ResultCode.SERVICE_ERROR;
    }

    public ServiceException(int code, String message) {
        super(message);
        this.code = code;
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
        this.code = ResultCode.SERVICE_ERROR;
    }

    public ServiceException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public ServiceException(Throwable cause) {
        super(cause);
        this.code = ResultCode.SERVICE_ERROR;
    }
}
