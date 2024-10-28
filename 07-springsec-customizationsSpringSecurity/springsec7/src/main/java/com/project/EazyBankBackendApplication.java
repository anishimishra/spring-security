package com.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// @EnableWebSecurity - optional
//@ComponentScan("com.project.springsec1.controller")
//@EnableJpaRepositories("com.project.repository")
//@EntityScan("com.project.model")
public class EazyBankBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(EazyBankBackendApplication.class, args);
	}

}
