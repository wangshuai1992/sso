package com.wangshuai.sso.common;


/**
 * 返回结果
 *
 * @author wangshuai
 * @version V1.0
 * @date 2017-11-20 18:28
 */
public class Result<T> {
    /**
     * 结果体
     */
    protected T data;

    /**
     * 状态码
     */
    protected Integer code;

    /**
     * 信息
     */
    protected String message;

    public Result() {
        this.code = ResultCode.SUCCESS;
    }

    private Result(Integer code) {
        this.code = code;
    }

    public static Result create(Integer code) {
        return new Result(code);
    }

    public static Result createSuccessResult() {
        return create(ResultCode.SUCCESS);
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public Result setCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Result setMessage(String message) {
        this.message = message;
        return this;
    }
}
