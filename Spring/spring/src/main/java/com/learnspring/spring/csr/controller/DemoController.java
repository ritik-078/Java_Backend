package com.learnspring.spring.csr.controller;

import org.springframework.stereotype.Controller;

@Controller
public class DemoController {

    //private DemoService demoService;

    public String hello(){
        return "hello controller";
    }
}
