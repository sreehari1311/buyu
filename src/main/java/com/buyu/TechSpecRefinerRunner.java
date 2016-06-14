package com.buyu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.buyu.catalog.TechSpecRefiner;

@SpringBootApplication
public class TechSpecRefinerRunner {

	public static void main(String... args){
		ConfigurableApplicationContext context =SpringApplication.run(AmazonProductRunner.class, args);
		TechSpecRefiner refiner = context.getBean(TechSpecRefiner.class);
		refiner.validateAllTechSpecs();
	}
}
