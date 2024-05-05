package com.bluesky.soap.provider.config;

import com.bluesky.soap.provider.services.ws.UserInfoService;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.xml.ws.Endpoint;

/**
 * @ClassName ServicePublisher
 * @Description TO DO
 * @Author lenovo
 * @Date 2024/5/3
 * @Version 1.0
 **/
@Configuration
public class ServicePublisher {
    @Resource
    UserInfoService userInfoService;

    @Resource
    SpringBus springBus;

    @Bean
    public Endpoint userServicePublishEndpoint() {
        EndpointImpl endpoint = new EndpointImpl(springBus, userInfoService);
        endpoint.publish("/ws/userinfo");
        return endpoint;
    }
}
