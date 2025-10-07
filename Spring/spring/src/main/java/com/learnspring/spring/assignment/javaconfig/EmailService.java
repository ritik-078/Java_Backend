package com.learnspring.spring.assignment.javaconfig;

public class EmailService {

    private DataSource dataSource;

    public EmailService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    void sendEmail(){
        String[] emails = dataSource.getEmails();

        for(String email: emails){
            System.out.println(email);
        }
    }
}
