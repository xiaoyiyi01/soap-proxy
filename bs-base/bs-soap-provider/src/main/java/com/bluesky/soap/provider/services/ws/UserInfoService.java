package com.bluesky.soap.provider.services.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;

/**
 * @ClassName UserInfoService
 * @Description TO DO
 * @Author lenovo
 * @Date 2024/5/3
 * @Version 1.0
 **/
@WebService(name = "UserInfoService",
        targetNamespace = "http://ws.services.provider.soap.bluesky.com"
)
@BindingType(value= SOAPBinding.SOAP12HTTP_BINDING)
public interface UserInfoService {

    @WebMethod
    String getUserAddress(@WebParam(name = "userName", targetNamespace = "http://ws.services.provider.soap.bluesky.com") String userName);

}
