package com.learnspring.spring.bean.scope;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

	public static void main(String[] args) {

		var context = SpringApplication.run(Main.class, args);

		//Singleton bean return the same bean every time having same hash code
		SingletonBean s1 = context.getBean(SingletonBean.class);
		System.out.println(s1.hashCode());
		SingletonBean s2 = context.getBean(SingletonBean.class);
		System.out.println(s2.hashCode());
		SingletonBean s3 = context.getBean(SingletonBean.class);
		System.out.println(s3.hashCode());

		//Prototype bean return different bean every time having different hash code
		PrototypeBean p1 = context.getBean(PrototypeBean.class);
		System.out.println(p1.hashCode());
		PrototypeBean p2 = context.getBean(PrototypeBean.class);
		System.out.println(p2.hashCode());
		PrototypeBean p3 = context.getBean(PrototypeBean.class);
		System.out.println(p3.hashCode());
	}

}
