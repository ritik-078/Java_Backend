package com.learnspring.spring.annotation_based_config;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AnnoAppConfig.class);

        Bike bike = ac.getBean(Bike.class);
        bike.start();

        Car car = ac.getBean(Car.class);
        car.start();



    }
}
