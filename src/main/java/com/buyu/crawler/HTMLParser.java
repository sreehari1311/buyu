package com.buyu.crawler;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.buyu.CrawlerRunner;
import com.buyu.utils.db.DBConnector;
import com.buyu.utils.file.FileWriter;
 
@Component
public class HTMLParser {
	private static final Logger logger = Logger.getLogger(HTMLParser.class);

	private CrawlerConfig crawlerConfig;
	@Autowired
	private CrawlerDAO crawlerDAO;
	
	public Map<String,String> getParsedContent(CrawlerConfig crawlerConfig,String productId){
		
		this.crawlerConfig = crawlerConfig;
		Map<String,String> parsedConfig = new HashMap<String, String>();
			File file = new File(crawlerConfig.getValue("destination")+"/"+productId+".html");
	 
			 logger.debug(crawlerConfig.getValue("destination")+"/"+productId+".html");
			try {
				Document doc = Jsoup.parse(file, "UTF-8");
				  
				Elements select =doc.select(crawlerConfig.getValue("titleParser"));
				if(select == null || select.size()<=0){
					logger.debug("Not a product detail page");
					return parsedConfig;
				}else if(select.get(0) == null){
					if(doc.select(crawlerConfig.getValue("priceParser")).get(0) == null){
						logger.debug( "This is not a price detail page");
						return parsedConfig;
					}
				}
				String title = select.get(0).text();
				//TODO refactor this functionality
				//ID: 'MOBD53DJFMXM5YF7', Title: 'Samsung Galaxy J1'
				
				String product ="ID: '"+productId+"' ,Title: '"+title+"'";
			 	System.out.println(product);
				select = doc.select(crawlerConfig.getValue("priceParser"));
				String price ="";
				if(select!=null && select.size()>0){
					price = select.get(0).attr("content");
					price = price.replaceAll(",","");
					System.out.println("PRICEEEEEEEEEEEEEEEEE:::::"+select.get(0).attr("content"));
				}
				//TODO handle 100.50 type of value
				price = price.replaceAll("[^0-9]","");
				System.out.println(title+" : "+price);
				parsedConfig.put("title", title);
				parsedConfig.put("price", price);
				
				select = doc.select(crawlerConfig.getValue("specParser"));
			    String key = null;
			    String val = null;
				for(Element el:select){
					if(el.attr("class").equals("specsKey")){
						key = el.text();
					}else if(el.attr("class").equals("specsValue")){
						val = el.text();
						parsedConfig.put(key, val);
					}
					
				}
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
		return parsedConfig;
		//fw.close();
	}
	public void parseContent(String configId){
		CrawlerConfig crawlerConfig = new CrawlerConfig(configId);
		this.crawlerConfig = crawlerConfig;
		FileWriter fw = new FileWriter("/Users/sreehari/Dream/WorkSpace/buyu/src/main/resources/r.csv");
		List<Crawler> crawledPageList = crawlerDAO.allCrawlData(configId);
		for(Crawler crawler:crawledPageList){
			File file = new File(crawlerConfig.getValue("destination")+"/"+crawler.getTitle()+".html");
			 logger.debug(crawler.getTitle());
			 logger.debug(crawlerConfig.getValue("destination")+"/"+crawler.getTitle()+".html");
			try {
				Document doc = Jsoup.parse(file, "UTF-8");
				  
				Elements select =doc.select(crawlerConfig.getValue("titleParser"));
				if(select == null || select.size()<=0){
					logger.debug("Not a product detail page");
					continue;
				}else if(select.get(0) == null){
					if(doc.select(crawlerConfig.getValue("priceParser")).get(0) == null){
						logger.debug( "This is not a price detail page");
						continue;
					}
				}
				String title = select.get(0).text();
				//TODO refactor this functionality
				//ID: 'MOBD53DJFMXM5YF7', Title: 'Samsung Galaxy J1'
				String product ="ID: '"+crawler.getId()+"' ,Title: '"+title+"'";
				fw.writeLine(product);
				select = doc.select(crawlerConfig.getValue("priceParser"));
				String price =select.get(0).text();
				//TODO handle 100.50 type of value
				price = price.replaceAll("[^0-9]","");
				System.out.println(title+" : "+price);
				String url = crawler.getUrl();
				String pidStart = crawlerConfig.getValue("pIdStart");
				String pidEnd = crawlerConfig.getValue("pIdEnd");
				
				String pId = "NO";
				if(url.indexOf(pidStart)>0){
					url.substring(url.indexOf(pidStart), url.length());
				}
				pId = pId.replace(pidStart,"").replace(pidEnd,"");
				select = doc.select(crawlerConfig.getValue("specParser"));
				System.out.println(pId);
				for(Element el:select){
					//System.out.println("KEY::::::::"+el.text());
				}
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		fw.close();
	}
	
	
	public static void main(String...args){
		CrawlerConfig config = new CrawlerConfig("FLIPKART_CONF");
		HTMLParser parser = new HTMLParser();
		System.out.println(parser.getParsedContent(config, "MOBD9Q6NWHUDFCYM"));
	}
}
