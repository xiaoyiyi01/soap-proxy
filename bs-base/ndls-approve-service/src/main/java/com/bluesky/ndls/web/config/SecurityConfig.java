package com.bluesky.ndls.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;

/**
 * @ClassName SecurityConfig
 * @Description TO DO
 * @Author lenovo
 * @Date 2024/5/19
 * @Version 1.0
 **/
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // 关闭csrf防护
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()

                //定制url访问权限
                .authorizeRequests()
                //无需登录即可访问
                .antMatchers("/**").permitAll()
                //需要特定权限
//                .antMatchers("/sysUser/**","/sysAuthority/**").hasAnyAuthority("ROLE_ADMIN","ROLE_SA")
                //其他接口登录才能访问
//                .anyRequest().authenticated()
                .and();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
