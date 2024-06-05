package com.bluesky.ndls.web.security;

import com.bluesky.ndls.web.service.CommonUserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @ClassName UserDetailsServiceImpl
 * @Description TO DO
 * @Author lenovo
 * @Date 2024/5/19
 * @Version 1.0
 **/
@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    @Resource
    CommonUserService commonUserService;

    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        return commonUserService.findOneByUserName(userName);
    }
}
