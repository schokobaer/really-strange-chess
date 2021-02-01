package com.example.its.cockpit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ItsCockpitApplication {

	public static void main(String[] args) {
		SpringApplication.run(ItsCockpitApplication.class, args);
	}

}
