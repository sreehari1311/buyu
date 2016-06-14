package com.buyu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.buyu.utils.common.PinCodeImporter;

@SpringBootApplication
public class PinCodeRunner {

	public static void main(String...args){
		ConfigurableApplicationContext context =SpringApplication.run(PinCodeRunner.class, args);
		PinCodeImporter pinImporter = context.getBean(PinCodeImporter.class);
		pinImporter.importAllFromFile();
	}
}
