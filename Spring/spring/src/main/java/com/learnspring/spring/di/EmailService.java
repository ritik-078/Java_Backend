package com.learnspring.spring.di;

import org.springframework.stereotype.Component;

@Component
public class EmailService implements Service {
    @Override
    public void sendMessage(String message){
        System.out.println("Sending Email: " + message);
    }
}
