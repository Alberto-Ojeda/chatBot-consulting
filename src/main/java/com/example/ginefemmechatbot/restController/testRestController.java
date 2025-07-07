package com.example.ginefemmechatbot.restController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class testRestController {


    @GetMapping("/test")
    public String test(){
        return "test";
    }
}
