package com.learnspring.spring.annotation_based_config;

import org.springframework.stereotype.Component;

@Component
public class Bike implements Vehicle {
    @Override
    public void start(){
        System.out.println("Riding Bike");
    }
}
