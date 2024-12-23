package com.gratitude.gratitude_photodiary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})

public class GratitudePhotodiaryApplication {

	public static void main(String[] args) {
		SpringApplication.run(GratitudePhotodiaryApplication.class, args);
	}

}
