package com.example.springbootquartz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author liyuzhe
 * @Date 2021-10-26 17:31:00
 */

@Controller
@RequestMapping("/view")
public class WebController {
    @GetMapping("")
    public String index() throws Exception {
        return "index.html";
    }

    @GetMapping("{param1:^(?!static).*?$}/**")
    public String redirect() throws Exception {
        return "index.html";
    }
}
