package com.learnspring.spring.annotation_based_config;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary // Default bean in case of multiple beans of same type
public class Car implements Vehicle {
    @Override
    public void start(){
        System.out.println("Car Running");
    }
}
