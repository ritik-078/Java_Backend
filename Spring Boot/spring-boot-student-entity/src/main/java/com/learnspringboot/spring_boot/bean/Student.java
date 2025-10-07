package com.learnspringboot.spring_boot.bean;

public class Student {
    private int id;
    private  String firstName;
    private  String lastName;

    public Student(String firstName, int id, String lastName) {
        this.firstName = firstName;
        this.id = id;
        this.lastName = lastName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
