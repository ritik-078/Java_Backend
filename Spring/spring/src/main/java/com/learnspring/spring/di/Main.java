package com.learnspring.spring.di;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        // Using interface to avoid tight coupling between sender and type of service used
        String message = "Ritik Bhateley";

//        SMSService smsService = new SMSService();
//        EmailService emailService = new EmailService();
//        Sender sender = new Sender(smsService);
//        sender.sendMessage(message);


        // Using DI, we don't have to initialize service objects to send message
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext((AppConfig.class));

        Sender sender = ac.getBean(Sender.class);
        sender.sendMessage(message);
    }
}
