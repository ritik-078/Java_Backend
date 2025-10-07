package com.learnspring.spring.java_based_config;

public class Car implements Vehicle{
    @Override
    public void start(){
        System.out.println("Car Running");
    }
}
