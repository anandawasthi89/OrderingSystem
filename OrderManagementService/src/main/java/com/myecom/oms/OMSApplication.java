package com.myecom.oms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class OMSApplication {

	public static void main(String[] args) {
		SpringApplication.run(OMSApplication.class, args);
	}

}
