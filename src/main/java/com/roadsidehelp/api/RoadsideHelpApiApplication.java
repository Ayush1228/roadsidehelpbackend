package com.roadsidehelp.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RoadsideHelpApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(RoadsideHelpApiApplication.class, args);
	}

}
