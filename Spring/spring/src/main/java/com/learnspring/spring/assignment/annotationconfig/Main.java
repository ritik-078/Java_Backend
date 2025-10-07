package com.learnspring.spring.assignment.annotationconfig;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);

        EmailService emailService = applicationContext.getBean(EmailService.class);

        emailService.sendEmail();
    }
}
