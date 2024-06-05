package com.bluesky.ndls.web.service;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * @ClassName CommonUserService
 * @Description TO DO
 * @Author lenovo
 * @Date 2024/5/19
 * @Version 1.0
 **/
public interface CommonUserService {
    UserDetails findOneByUserName(String userName);
}
