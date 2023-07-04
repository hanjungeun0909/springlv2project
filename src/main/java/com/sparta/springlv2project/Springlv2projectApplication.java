package com.sparta.springlv2project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Springlv2projectApplication {

	public static void main(String[] args) {
		SpringApplication.run(Springlv2projectApplication.class, args);
	}

}
