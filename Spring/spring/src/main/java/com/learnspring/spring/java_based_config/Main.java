package com.learnspring.spring.java_based_config;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(JavaAppConfig.class);

        Car car = ac.getBean(Car.class);
        car.start();

        Bike bike = ac.getBean(Bike.class);
        bike.start();

    }
}
