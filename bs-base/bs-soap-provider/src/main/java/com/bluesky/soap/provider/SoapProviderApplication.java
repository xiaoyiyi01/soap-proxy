package com.bluesky.soap.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @ClassName SoapProviderApplication
 * @Description TO DO
 * @Author lenovo
 * @Date 2024/5/2
 * @Version 1.0
 **/
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class SoapProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(SoapProviderApplication.class, args);
    }
}
