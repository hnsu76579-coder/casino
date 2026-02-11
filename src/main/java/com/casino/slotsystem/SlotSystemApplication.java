package com.casino.slotsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import java.util.TimeZone;
import jakarta.annotation.PostConstruct;
@EnableCaching
@SpringBootApplication
public class SlotSystemApplication {
	   @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Kolkata"));
    }

	  public static void main(String[] args) {
        SpringApplication.run(SlotSystemApplication.class, args);
    }

}
