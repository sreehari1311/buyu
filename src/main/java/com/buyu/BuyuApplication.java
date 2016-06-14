package com.buyu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

 
import com.buyu.elasticsearch.IndexData;
import com.buyu.products.Processor;
import com.buyu.products.amazon.AmazonProductsProcessor;
 
@SpringBootApplication
public class BuyuApplication {
	
    public static void main(String[] args) {
    	ConfigurableApplicationContext context =SpringApplication.run(BuyuApplication.class, args);
    	BuyuCatalogBuilder bCat = context.getBean(BuyuCatalogBuilder.class);
    	bCat.buildCatalog();
    	Processor amazonProduct =context.getBean(AmazonProductsProcessor.class);
		amazonProduct.processAllProducts(true);
    	IndexData indexData = context.getBean(IndexData.class);
    	indexData.indexAllProduct();
    }
}
