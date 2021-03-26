package com.xantrix.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
 * to Run the Spring application without compiled that is without create .jar file
 * we can use Maven from PowerShell ( Command Line )
 * execute : mvn spring-boot:run
 */

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
