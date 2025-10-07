package com.springboot.user_management;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "User Management Spring Boot REST API Documentation",
				description = "User Management Spring Boot REST API Documentation",
				version = "v1.0",
				contact = @Contact(
						name = "Ritik Bhateley",
						email = "ritikbhateley@gmail.com",
						url = "https://ritik-078.github.io/Personal-Portfolio/"
				),
				license = @License(
						name = "Apache 2.0",
						url = "https://ritik-078.github.io/Personal-Portfolio/"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "Spring Boot User Management Documentation",
				url = "https://ritik-078.github.io/Personal-Portfolio/"
		)
)

public class SpringBootUserManagementApplication {

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}
	public static void main(String[] args) {
		SpringApplication.run(SpringBootUserManagementApplication.class, args);
	}

}
