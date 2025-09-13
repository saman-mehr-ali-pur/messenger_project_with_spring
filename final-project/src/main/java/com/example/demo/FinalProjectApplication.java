package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.example.demo.repo"})
@Slf4j
public class        FinalProjectApplication {

	private static Logger logger =  LoggerFactory.getLogger(FinalProjectApplication.class);
	public static void main(String[] args) {
		logger.info("application start ...");
		SpringApplication.run(FinalProjectApplication.class, args);
	}

}
