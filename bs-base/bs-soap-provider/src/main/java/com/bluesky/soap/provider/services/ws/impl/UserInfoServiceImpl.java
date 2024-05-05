package com.bluesky.soap.provider.services.ws.impl;

import com.bluesky.soap.provider.services.ws.UserInfoService;
import org.springframework.stereotype.Service;

import javax.jws.WebService;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;

/**
 * @ClassName UserInfoServiceImpl
 * @Description TO DO
 * @Author lenovo
 * @Date 2024/5/3
 * @Version 1.0
 **/
@WebService(serviceName = "UserInfoService",
        targetNamespace = "http://ws.services.provider.soap.bluesky.com",
        endpointInterface = "com.bluesky.soap.provider.services.ws.UserInfoService"
)
@BindingType(value= SOAPBinding.SOAP12HTTP_BINDING)
@Service
public class UserInfoServiceImpl implements UserInfoService {

    public String getUserAddress(String userName) {
        return userName + "在白云区人和镇方石村";
    }
}
