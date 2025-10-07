package com.learnspring.spring.csr;

import com.learnspring.spring.csr.controller.DemoController;
import com.learnspring.spring.csr.repository.DemoRepository;
import com.learnspring.spring.csr.service.DemoService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(CSRAppConfig.class);

        DemoController demoController = applicationContext.getBean(DemoController.class);
        System.out.println(demoController.hello());

        DemoService demoService = applicationContext.getBean(DemoService.class);
        System.out.println(demoService.hello());

        DemoRepository demoRepository = applicationContext.getBean(DemoRepository.class);
        System.out.println(demoRepository.hello());
    }
}
