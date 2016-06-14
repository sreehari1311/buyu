package com.buyu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.buyu.elasticsearch.IndexData;
 
@SpringBootApplication
public class IndexRunner {

	public static void main(String... args){
		ConfigurableApplicationContext context =SpringApplication.run(AmazonProductRunner.class, args);
		IndexData indexData = context.getBean(IndexData.class);
		//indexData.indexAllProduct();
		indexData.indexAllSellers();
	}
}
