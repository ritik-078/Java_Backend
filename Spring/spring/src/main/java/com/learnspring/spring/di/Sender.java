package com.learnspring.spring.di;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class Sender {
    private Service service;

    //In case of multiple constructors @Autowired is compulsory to avoid ambiguity to
    // call constructor
    @Autowired
    public Sender(@Qualifier("emailService") Service service) {
        // Constructor based DI
        this.service = service;
    }

    public Sender(SMSService smsService){
        this.service = smsService;
    }

    public void sendMessage(String message){
        this.service.sendMessage(message);
    }
}
