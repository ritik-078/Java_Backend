package com.learnspring.spring.java_based_config;

public class Traveller {
    private Vehicle vehicle;

    public Traveller(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public void startJourney(){
        this.vehicle.start();
    }
}
