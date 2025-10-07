package com.learnspringboot.spring_boot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

//    @RestController == @Controller + @ResponseBody
@RestController
public class HelloWorldController {
    //HTTP Get Request
    @GetMapping("/hello-world")
    public String HelloWorld(){
        return "Hello World!!";
    }
}
