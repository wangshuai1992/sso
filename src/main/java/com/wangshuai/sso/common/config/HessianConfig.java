package com.wangshuai.sso.common.config;

import com.wangshuai.sso.share.service.AuthenticationRpcService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.caucho.HessianServiceExporter;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping;

import javax.annotation.Resource;

/**
 * TODO
 *
 * @author wangshuai
 * @version V1.0
 * @date 2018-01-07 21:47
 */
@Configuration
public class HessianConfig {

    @Resource
    private AuthenticationRpcService authenticationRpcService;

    @Bean
    public HandlerMapping getBeanNameUrlHandlerMapping() {
        return new BeanNameUrlHandlerMapping();
    }

    @Bean(name = "/authenticationRpcService")
    public HessianServiceExporter getHessianExporter() {
        HessianServiceExporter hessianServiceExporter = new HessianServiceExporter();
        hessianServiceExporter.setService(authenticationRpcService);
        hessianServiceExporter.setServiceInterface(AuthenticationRpcService.class);
        return hessianServiceExporter;
    }
}
