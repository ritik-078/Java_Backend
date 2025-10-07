package com.learnspring.spring.di;

import org.springframework.stereotype.Component;

@Component
public class SMSService implements Service {
    @Override
    public void sendMessage(String message){
        System.out.println("Sending SMS: " + message);
    }
}
