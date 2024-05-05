package com.bluesky.web.controller;

import com.bluesky.web.service.UserInfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @ClassName UserInfoController
 * @Description TO DO
 * @Author lenovo
 * @Date 2024/5/5
 * @Version 1.0
 **/
@RestController
@RequestMapping("/user")
public class UserInfoController {

    @Resource
    UserInfoService userInfoService;

    @PostMapping(value = "addr")
    public String getUserAddress(@RequestBody String soapRequest){
        System.out.println(soapRequest);
        return userInfoService.getUserAddress(soapRequest);
    }
}
