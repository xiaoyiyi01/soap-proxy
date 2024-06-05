package com.bluesky.ndls.web.service.impl;

import com.bluesky.ndls.web.service.CommonUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * @ClassName CommonUserServiceImpl
 * @Description TO DO
 * @Author lenovo
 * @Date 2024/5/19
 * @Version 1.0
 **/
@Slf4j
@Service
public class CommonUserServiceImpl implements CommonUserService {
    @Resource
    PasswordEncoder passwordEncoder;

    public UserDetails findOneByUserName(String userName) {
        return new User("admin", passwordEncoder.encode("123456"),
                Arrays.asList(new SimpleGrantedAuthority("ROLE_ACTIVITI_USER"),
                        new SimpleGrantedAuthority("GROUP_activitiTeam")));
    }
}
