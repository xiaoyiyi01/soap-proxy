package com.bluesky.ndls.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName ActivitiController
 * @Description TO DO
 * @Author lenovo
 * @Date 2024/5/25
 * @Version 1.0
 **/
@RestController
public class ActivitiController {
    @GetMapping("/modeler")
    public void modeler(HttpServletResponse response) throws IOException {
        response.sendRedirect("/modeler.html");
    }
}
