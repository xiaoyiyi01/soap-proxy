package com.bluesky.ndls.web;

import lombok.Data;

/**
 * @ClassName RespMessage
 * @Description TO DO
 * @Author lenovo
 * @Date 2024/5/19
 * @Version 1.0
 **/
@Data
public class RespMessage<T> {
    String code;
    String desc;
    T data;
}
