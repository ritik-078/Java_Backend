package com.learnspring.spring.java_based_config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


// to create a config class
@Configuration
public class JavaAppConfig {

    // to create beans so that spring handles the objects
    @Bean
    public Vehicle car(){
        return new Car();
    }

    @Bean
    public Vehicle bike(){
        return new Bike();
    }

    @Bean
    public Traveller traveller(){
        return new Traveller(car());
    }
}
