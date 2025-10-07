package com.learnspring.spring.annotation_based_config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class Traveller {
    private Vehicle vehicle;

    @Autowired
//    public Traveller(@Qualifier("car") Vehicle vehicle) {
//        this.vehicle = vehicle;
//    }
    public Traveller(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public void startJourney(){
        this.vehicle.start();
    }
}
