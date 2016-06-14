package com.buyu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.buyu.products.Processor;
import com.buyu.products.amazon.AmazonProductsProcessor;
 
@SpringBootApplication
public class AmazonProductRunner {

	public static void main(String... args){
		ConfigurableApplicationContext context =SpringApplication.run(AmazonProductRunner.class, args);
		Processor amazonProduct =context.getBean(AmazonProductsProcessor.class);
		amazonProduct.processAllProducts(true);
		
	}
}
