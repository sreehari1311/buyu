package com.buyu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.buyu.flipkart.FlipKartCSVProcessor;
import com.buyu.utils.common.BuyuConfigurator;

@Repository
@Component("buyuCatalogBuilder")
public class BuyuCatalogBuilder {

 	@Autowired
	private FlipKartCSVProcessor flipKartCSVProcessor;
 
 	public void buildCatalog(){
		flipKartCSVProcessor.processCSVFile(BuyuConfigurator.getInstance().getValue("FLIPKART_CSV"), "FLIPKART_PRODUCT");		 
	}
}
