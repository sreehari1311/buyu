package com.buyu;

 

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
 

import com.buyu.crawler.Crawler;
import com.buyu.crawler.CrawlerConfig;
import com.buyu.crawler.CrawlerController;
import com.buyu.crawler.CrawlerDAO;
import com.buyu.crawler.HTMLParser;
import com.buyu.utils.db.DBConnector;
 
@SpringBootApplication
public class CrawlerRunner {
	 
	private static final Logger logger = Logger.getLogger(CrawlerRunner.class);
	//private ConfigurableApplicationContext context = SpringApplication.run(CrawlerRunner.class);
	public  void storeCrawlerInfo(Crawler crawler) {
		
		//HTMLParser dao = context.getBean(HTMLParser.class);
		//dao.i
		//dao.parseContent("INFIBEAM_CONF");
		 
		/*CrawlerDAO dao = context.getBean(CrawlerDAO.class);
		dao.insertCrawlData(crawler);
		DBConnector con =  context.getBean(DBConnector.class);
		try {
			con.getJDBCTemplate().getDataSource().getConnection().close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
	}
	 
	public static void main(String... args){
		PropertyConfigurator.configure("/Users/sreehari/Dream/WorkSpace/buyu/src/main/resources/log4j.properties");

		CrawlerConfig config = new CrawlerConfig("INFIBEAM_CONF");
		String url = "http://forex-warez.no-ip.biz/Trading%20Books/";
		//String purl = url.replace("http://www.flipkart.com/","");
		//purl = purl.substring(0,purl.indexOf("/p/"));
		String purl = "infibeam.com/Mobiles";
		System.out.println("PURLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL"+purl);
		
		config.setValue("seedURLs",url);
		config.setValue("ContainsValue", purl);
		config.setValue("NotContainsValue", "infibeam.com/Mobiles/search?");
		config.setValue("maxDepth", "2");
		ConfigurableApplicationContext context = SpringApplication.run(CrawlerRunner.class);
	   
		CrawlerController controller = context.getBean(CrawlerController.class);
		try {
			controller.startCrawling(config);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main1(String... args){
		 
		PropertyConfigurator.configure("/Users/sreehari/Dream/WorkSpace/buyu/src/main/resources/log4j.properties");
	 	logger.debug("start....");
	 	logger.error("Log error");
	 	String configId = "AMAZON_CONF";
	  	 ConfigurableApplicationContext context = SpringApplication.run(CrawlerRunner.class);
	  	 CrawlerController cRunner = context.getBean(CrawlerController.class);
		try {
			cRunner.startCrawling(configId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		HTMLParser parser =  context.getBean(HTMLParser.class);
		logger.error("Got Beans.....................");
		 //parser.parseContent(configId); 
 	
	}
	
	
}
