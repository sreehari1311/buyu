package com.buyu.flipkart;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.buyu.catalog.CatalogBuilder;
import com.buyu.catalog.Company;
import com.buyu.catalog.Product;
import com.buyu.catalog.ProductSpec;
import com.buyu.catalog.Seller;
import com.buyu.crawler.CrawlerConfig;
import com.buyu.crawler.CrawlerController;
import com.buyu.crawler.HTMLParser;
import com.buyu.utils.common.BuyuConfigurator;
import com.buyu.utils.file.CSVReader;
import com.buyu.utils.file.ImageProcessor;
import com.buyu.utils.file.Token;
import com.buyu.utils.http.Request;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
@Component
public class FlipKartCSVProcessor {

	
	private static final Logger logger = LogManager.getLogger(FlipKartCSVProcessor.class); 
	private CSVReader csvReader;
	private CrawlerConfig config = new CrawlerConfig("FLIPKART_CONF");
	private CrawlerConfig customConfig = new CrawlerConfig("FLIPKART_CONF");
	private Set<String> productIdset = new HashSet<String>();
	@Autowired
	private CatalogBuilder catalogBuilder;
	
	@Autowired
    private CrawlerController crawlerController;
	
	public void processCSVFile(String filePath,String configName){
		try {
			csvReader = new CSVReader(filePath,configName);
			logger.debug("Processing Company Cataglog data");
			 makeCompanyCatalog();
			logger.debug("Processing Product Cataglog data");
			makeProductCatalog();
			logger.debug("Processing Product Specification data");
			//makeProductSpec();
			logger.debug("Processing FlipKart Data Specification data");
			//saveToFlipKart();
		} catch (FileNotFoundException e) {
	 		e.printStackTrace();
		}
	}
	private void saveImages(){
		 
	 	    Token token = null;
		    csvReader.skip();
		    File pt = new File(BuyuConfigurator.getInstance().getValue("FLIPKART_IMG_DEST"));
			List<String> prods = Arrays.asList(pt.list());
		    while((token = csvReader.getToken())!=null){
		    	
		    	String imageURL = token.getString(3);
		    	Map<String, Boolean> productMap = new HashMap<String,Boolean>();
		    	String productName = token.getString(1);
		    	 
		    	if(!productMap.containsKey(productName) && imageURL!=null && !prods.contains(productName)){
		    		ImageProcessor.saveImage(productName, imageURL.split(";"));
		    		productMap.put(productName, new Boolean(true));
		     	}
		    } 
	}
	
	private void makeSubProduct(long pId,String productId,String productURL,String colorVariants,String sizeVariants,String mrp,String imageURL){
		Request request = new Request();
		Map<String,String> headers = new HashMap<String,String>();
	 	
	 	if(productURL != null){
			productURL = productURL.replace("http://dl.flipkart.com/dl/", "http://www.flipkart.com/");
			System.out.println(productURL);
			
			 
			//
			
			 headers = new HashMap<String,String>();
			headers.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.10; rv:41.0) Gecko/20100101 Firefox/41.0");
			try {
				boolean isLocal = true;
				//TODO Disabled Crawling
				/* 
			 */
				File f = new File(config.getValue("destination")+"/"+productId+".html");
					if(!f.exists()){
					String content = request.getRequest(productURL,headers);
					FileWriter fwr = new FileWriter(f);
					fwr.write(content);
					fwr.close();
					Thread.sleep(1500);
					}
				 
				HTMLParser parser = new HTMLParser();
				Map<String,String> parsedMap = parser.getParsedContent(config, productId);
				Gson gson = new Gson();
				System.out.println(gson.toJson(parsedMap));
				ProductSpec spec = new ProductSpec();
				spec.setSpec(gson.toJson(parsedMap));
				//TODO make key value as internal
				if(parsedMap.get("Handset Color")!=null){
					spec.setColor(parsedMap.get("Handset Color"));
				}
				if(parsedMap.get("Internal")!=null){
					if(parsedMap.get("Internal").split(" ").length>1){
						System.out.println("INTERNAL MEMORY::::::::::::::::::"+parsedMap.get("Internal"));
						String internal[] =  parsedMap.get("Internal").split(" ");
						spec.setInternal(Integer.parseInt(internal[0]));
						spec.setInternalUnit((internal[1]));
					}
				}
				
				long specId = catalogBuilder.insertProductSpecData(spec);
				long subProductId =catalogBuilder.insertSubProductData(pId,specId,imageURL,productId);
				Seller seller = new Seller();
				seller.setSellerId("FLIPKART");
				//TODO change to numbers
				seller.setSubProductId(""+subProductId);
				seller.setMrp(Float.parseFloat(mrp));
				seller.setSellerProductURL(productURL);
				seller.setSellerProductId(productId);
				//TODO use Google Gua for null handling
				if(parsedMap.get("price")!=null && !parsedMap.get("price").trim().equals("")){
					seller.setSellingPrice(Float.parseFloat(parsedMap.get("price")));
				}
				catalogBuilder.insertSellerData(seller);
				 
				//return;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(colorVariants!= null ){
			colorVariants =colorVariants.replace("[", "").replace("]","");
			
		}
		if(sizeVariants!=null){
			sizeVariants =sizeVariants.replace("[", "").replace("]","");
		}
		  
		if((sizeVariants!=null && !sizeVariants.trim().equals(""))||(colorVariants!=null && !colorVariants.trim().equals(""))){
			/*String purl = productURL.replace("http://www.flipkart.com/","");
			purl = purl.substring(0,purl.indexOf("/p/"));
			purl = purl+"/p/";
			System.out.println("PURLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL"+purl);
			
			config.setValue("seedURLs",productURL);
			config.setValue("ContainsValue", purl);
			config.setValue("maxDepth", "2");
			try {
				crawlerController.startCrawling(config);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
    	} 
			 
		 
	}
	 
	private void makeProductSpec(){
		List<ProductSpec> productSpecList = new ArrayList<ProductSpec>();
		Map<String, Boolean> productSpecMap = new HashMap<String, Boolean>();
		Map<String,Long> productMap =  catalogBuilder.getProductIdList();
		     Token token = null;
		    csvReader.skip();
		    while((token = csvReader.getToken())!=null){
		    	Long size = new Long("0");
		    	if(token.getString(16) != null){
		    		size = new Long(token.getString(16));
		    	}
		    	String color = token.getString(17);
		    	String sizeUnit = token.getString(18);
		    	String sizeVarient = token.getString(19);
		    	String colorVarient = token.getString(20);
			 	String productName = token.getString(1);
				if(!productSpecMap.containsKey(productName)){
					ProductSpec productSpec = new ProductSpec();
					productSpec.setColor(color);
					productSpec.setProductId(productMap.get(productName));
					productSpec.setSize(size);
					productSpec.setSizeUnit(sizeUnit);
					 
					productSpecList.add(productSpec);
					productSpecMap.put(productName, new Boolean(true));
				  
				}
				
				
			}
		   // catalogBuilder.insertProductSpecData(productSpecList);;
			 
		 
	}
	private void makeProductCatalog(){
		List<Product> productList = new ArrayList<Product>();
		Map<String, Product> productMap = new HashMap<String,Product>();
		Map<String, Long> companyMap = catalogBuilder.getCompanyIdList();
		
		Token token = null;
		    csvReader.skip();
		    int p =0;
		    while((token = csvReader.getToken())!=null){
				String companyName = token.getString("productBrand").trim();
				p++;
				/*if(p<=7552){
					continue;
				}*/
				//TODO only handling  Samsung and Apple
				System.out.println(companyName +(companyName.equals("Samsung")));
				/*if(!companyName.equals("Micromax")){
					continue;
				}*/
				 
				String productName = token.getString("title");
				String productDesc = token.getString("description");
				String imageURL = token.getString("imageUrlStr");
				String productId = token.getString("productId");
				String sizeVariants = token.getString("sizeVariants").replace("[", "").replace("]","");
				String colorVariants = token.getString("colorVariants");
				String productURL = token.getString("productUrl");
				String mrp = token.getString("mrp");
				String imgUrl = null;
				System.out.println("PROODUCT:::::::::::::::::::"+p+")"+productName);
				if(imageURL !=null && !imageURL.trim().equals("")){
					String iu[] = imageURL.split(";");
					if(iu.length>0){
						imgUrl = iu[0];
					}
				}
				if(!productMap.containsKey(productName)){
					Product product = new Product();
					product.setProductName(productName);
					product.setProductDescription(productDesc);
					product.setImageURL(imageURL);
				  	product.setCompanyId(companyMap.get(companyName));
					productMap.put(productName, product);
					productList.add(product);
					long pId = catalogBuilder.insertProductData(product);
					product.setProductId(pId);
					
					makeSubProduct(pId,productId,productURL,colorVariants,sizeVariants,mrp,imgUrl);
					 
		 		}else{
		 			makeSubProduct(productMap.get(productName).getProductId(),productId,productURL,colorVariants,sizeVariants,mrp,imgUrl);
		 		}
		 		
			}
		    //catalogBuilder.insertProductData(productList);;
			 
		 
	}
	private  void saveToFlipKart(){
		List<FlipKart> productList = new ArrayList<FlipKart>();
		Map<String, Boolean> flipKartMap = new HashMap<String,Boolean>();
		Map<String,Long> productMap =  catalogBuilder.getProductIdList();
		     Token token = null;
		     csvReader.skip();
		    while((token = csvReader.getToken())!=null){
				String flipKartId = token.getString(0);
				String productName = token.getString(1);
				long productId = productMap.get(productName);
				String price = token.getString(5);
				String prodURL = token.getString(6);
				float p = 0;
				if(price!=null && !price.trim().equals("")){
					p = Float.valueOf(price);
				}
			 
				if(!flipKartMap.containsKey(productName)){
					FlipKart product = new FlipKart();
					product.setProductId(productId);
					product.setFlipKartId(flipKartId);
					product.setPrice(p);
					product.setProductURL(prodURL);
					flipKartMap.put(productName, new Boolean(true));
					productList.add(product);
			 
				}
				
				
			}
		    catalogBuilder.insertFlipKart(productList);;
	 	 
	}
	private void makeCompanyCatalog(){
		List<Company> companyList = new ArrayList<Company>();
		Map<String, Boolean> companyMap = new HashMap<String,Boolean>();
		    Token token = null;
		    csvReader.skip();
		    while((token = csvReader.getToken())!=null){
				String companyName = token.getString("productBrand");
				if(!companyMap.containsKey(companyName)){
					Company company = new Company();
					company.setName(companyName);
					companyMap.put(companyName, new Boolean(true));
					companyList.add(company);
				}
				
				
			}
		    catalogBuilder.insertCompanyData(companyList); 	 
	}
	 
}
