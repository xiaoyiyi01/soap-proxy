package com.bluesky.web.service.impl;

import com.bluesky.web.service.UserInfoService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.ws.client.core.WebServiceTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName UserInfoServiceImpl
 * @Description TO DO
 * @Author lenovo
 * @Date 2024/5/5
 * @Version 1.0
 **/
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Resource
    RestTemplate restTemplate;

    @Resource
    WebServiceTemplate webServiceTemplate;

    public String getUserAddress(String soapRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_XML);

        // 创建HttpEntity，包装请求头和请求体
        HttpEntity<String> entity = new HttpEntity(soapRequest, headers);

        ResponseEntity<String> respEntity = restTemplate.postForEntity("http://localhost:9998/services/ws/userinfo?wsdl", entity, String.class);
        return respEntity.getBody();
    }

  /*  public String getUserAddress(String soapRequest) {
        webServiceTemplate.setDefaultUri("http://localhost:9998/services/ws/userinfo?wsdl");
        Object result = webServiceTemplate.marshalSendAndReceive(soapRequest);
        return result.toString();
    }*/
}
