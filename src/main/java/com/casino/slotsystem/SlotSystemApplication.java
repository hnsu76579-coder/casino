package com.casino.slotsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class SlotSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(SlotSystemApplication.class, args);
	}

}
