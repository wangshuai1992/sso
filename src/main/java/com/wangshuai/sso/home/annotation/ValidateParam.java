package com.wangshuai.sso.home.annotation;


import com.wangshuai.sso.common.utils.Validator;

import java.lang.annotation.*;

/**
 * 自定义请求参数注解
 *
 * @author wangshuai
 * @version V1.0
 * @date 2017-11-20 18:14
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidateParam {
    /**
     * 验证器
     *
     * @return
     */
    Validator[] value() default {};

    /**
     * 参数的描述名称
     *
     * @return
     */
    String name() default "";
}
