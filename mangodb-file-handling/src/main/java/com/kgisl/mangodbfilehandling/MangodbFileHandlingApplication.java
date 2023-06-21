package com.kgisl.mangodbfilehandling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.kgisl.mangodbfilehandling"})
public class MangodbFileHandlingApplication {

	public static void main(String[] args) {
		SpringApplication.run(MangodbFileHandlingApplication.class, args);
	}

}
