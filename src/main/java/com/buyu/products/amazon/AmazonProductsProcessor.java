package com.buyu.products.amazon;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.buyu.catalog.CatalogBuilder;
import com.buyu.catalog.Seller;
import com.buyu.matcher.MatcherUtil;
import com.buyu.products.ProductsProcessor;
import com.buyu.utils.common.BuyuConfigurator;
import com.buyu.utils.file.FileUtil;

@Component
public class AmazonProductsProcessor extends ProductsProcessor {
	@Autowired
	private CatalogBuilder catalogBuilder;
	
	@Override
	public void processAllProducts(boolean isLocal) {
	 	List<Map<String, Object>> dataList = catalogBuilder.getAllProductsDetailedList();
	  
	 	int matchCount = 1;
		for (Map<String, Object> data : dataList) {
			
			String matcherFromString = MatcherUtil.getMobileMatcher(data);
			String amazonSearchQuery = MatcherUtil.getMobileTitle(data);
			String sProductId = data.get("sPid").toString();
	 		if (matcherFromString == null || amazonSearchQuery == null) {
			 continue;
			}
	 		String fileContent = fetchData(AmazonUtil.getUrl(amazonSearchQuery),isLocal, "AMAZON_XML_STORE", sProductId);
	 	 	ItemAttribute attribute;
			try {
				attribute = AmazonXMLParser.parseXML(fileContent);
				if(attribute == null){
					continue;
				}
				System.out.println(fileContent);
			} catch (Exception e) {
				e.printStackTrace();
				continue;
		 	}
			String amazonOneData = "ID: 'AMZ" + data.get("subProductId").toString() + "' ,ProductName: '"
					+ attribute.getTitle().replace(",", " ") + "' ,Color: '" + attribute.getColor() + "' ,Internal: '"
					+ attribute.getInternal() + "' ,Brand: '" + attribute.getBrand() + "'";
			 
			if(matchAndMerge(matcherFromString,amazonOneData)){
			 
		    		Seller seller = new Seller();
					seller.setSellerId("AMZ");
					seller.setSubProductId(data.get("subProductId").toString());
					seller.setSellerProductId(attribute.getAmazonId());
					seller.setSellerProductURL(attribute.getProductURL());
					seller.setSellingPrice(attribute.getPrice());
					seller.setMrp(0);
					catalogBuilder.insertSellerData(seller);
					matchCount++;

				} else {
					System.out.println("Doesn't Match");
				}
			
			
		}
		System.out.println("Total Matched: "+matchCount);
	}
 
	@Override
	public boolean matchAndMerge(String sourceData,String destinationData) {
 		FileUtil.writeData(BuyuConfigurator.getInstance().getValue("FLIPKART_MATCHER_FILE"), sourceData);
		FileUtil.writeData(BuyuConfigurator.getInstance().getValue("AMAZON_MATCHER_FILE"), destinationData);
	    if(MatcherUtil.isMobileProductMatch()){
	    	return true;
	    }else{
	    	return false;
	    }
		
	}

	 
 
}
