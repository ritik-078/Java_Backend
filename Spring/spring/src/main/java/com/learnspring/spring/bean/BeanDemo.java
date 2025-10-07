package com.learnspring.spring.bean;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

class Student{
    private Address address;

    public Student(Address address){
        this.address = address;
    }

    public void print(){
        System.out.println("Student class method called ...");
        address.print();
    }

    public void init(){

        System.out.println("Intialization logic");
    }

    public void destroy(){

        System.out.println("Destruction logic");
    }
}

class Address{

    public void print(){

        System.out.println("Address class method called ...");
    }
}

@Configuration
class AppConfig{

    @Bean(name = "addressBean")
    public Address address(){

        return new Address();
    }

    // initMethod is used to initialize bean (executed at creation of bean)
    // destroyMethod is used to destroy the bean (executed at deletion of bean)
    @Bean(name = "studentBean", initMethod = "init", destroyMethod = "destroy")
    public Student student(){

        return new Student(address());
    }
}

public class BeanDemo {

    public static void main(String[] args) {

        // By using try, application context is automatically destroyed when try block finishes
        try(var applicationContext
                    = new AnnotationConfigApplicationContext(AppConfig.class) ){

            //Student student = applicationContext.getBean(Student.class);
            Student student = (Student) applicationContext.getBean("studentBean");
            String[] beanNames = applicationContext.getBeanDefinitionNames();
            for (String bean: beanNames){
                System.out.println(bean);
            }
            student.print();
        }
    }
}
