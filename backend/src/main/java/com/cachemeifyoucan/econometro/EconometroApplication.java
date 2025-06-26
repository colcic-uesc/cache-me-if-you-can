package com.cachemeifyoucan.econometro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EconometroApplication {

	public static void main(String[] args) {
		SpringApplication.run(EconometroApplication.class, args);
	}

}
