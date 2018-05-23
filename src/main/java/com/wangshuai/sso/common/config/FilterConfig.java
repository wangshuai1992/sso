package com.wangshuai.sso.common.config;

import com.wangshuai.sso.home.filter.CaptchaFilter;
import com.wangshuai.sso.home.filter.CrossOriginFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * TODO
 *
 * @author wangshuai
 * @version V1.0
 * @date 2018-01-07 20:18
 */
@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean captchaFilterRegistration() {

        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new CaptchaFilter());
        registration.addUrlPatterns("/captcha");
//        registration.addInitParameter("paramName", "paramValue");
        registration.setName("captchaFilter");
        registration.setOrder(2);
        return registration;
    }

    @Bean
    public FilterRegistrationBean crossOriginFilterRegistration() {

        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new CrossOriginFilter());
        registration.addUrlPatterns("/*");
        registration.setName("crossOriginFilter");
        registration.setOrder(1);
        return registration;
    }

}
