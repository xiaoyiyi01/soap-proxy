package com.bluesky.soap.provider.services.ws.entity;

/**
 * @ClassName User
 * @Description TO DO
 * @Author lenovo
 * @Date 2024/5/5
 * @Version 1.0
 **/
public class User {
    private String userName;

    private int age;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
