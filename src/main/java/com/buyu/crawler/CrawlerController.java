package com.buyu.crawler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
@Component
public class CrawlerController {
	private static final Logger logger = LoggerFactory.getLogger(CrawlerController.class);
	 
    @Autowired
	private CrawlerDAO crawlerDAO;
	public void startCrawling(String configId) throws Exception {
		CrawlerConfig crawlerConfig = new CrawlerConfig(configId);
		CustomData customData = new CustomData();
		customData.setCrawlerConfig(crawlerConfig);
		customData.setCrawlerDAO(crawlerDAO);
		logger.debug("start crawling"+crawlerConfig.getValue("numberOfCrawlers"));
		
		int numberOfCrawlers = Integer.parseInt(crawlerConfig.getValue("numberOfCrawlers"));

		CrawlConfig config = crawlerConfig.getCrawlerConfig();

		/*
		 * Instantiate the controller for this crawl.
		 */
		PageFetcher pageFetcher = new PageFetcher(config);
		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
		CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

		/*
		 * For each crawl, you need to add some seed urls. These are the first
		 * URLs that are fetched and then the crawler starts following links
		 * which are found in these pages
		 */
		controller.setCustomData(customData);
	    
		for (String seed : crawlerConfig.getValue("seedURLs").split(",")) {
			controller.addSeed(seed);
		}

		/*
		 * Start the crawl. This is a blocking operation, meaning that your code
		 * will reach the line after this only when crawling is finished.
		 */
		controller.start(EcommerceCrawler.class, numberOfCrawlers);
		 
	}
	
	public void startCrawling(CrawlerConfig crawlerConfig) throws Exception {
		
		CustomData customData = new CustomData();
		customData.setCrawlerConfig(crawlerConfig);
		customData.setCrawlerDAO(crawlerDAO);
		logger.debug("start crawling"+crawlerConfig.getValue("numberOfCrawlers"));
		
		int numberOfCrawlers = Integer.parseInt(crawlerConfig.getValue("numberOfCrawlers"));

		CrawlConfig config = crawlerConfig.getCrawlerConfig();

		/*
		 * Instantiate the controller for this crawl.
		 */
		PageFetcher pageFetcher = new PageFetcher(config);
		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
		CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

		/*
		 * For each crawl, you need to add some seed urls. These are the first
		 * URLs that are fetched and then the crawler starts following links
		 * which are found in these pages
		 */
		controller.setCustomData(customData);
	    
		for (String seed : crawlerConfig.getValue("seedURLs").split(",")) {
			controller.addSeed(seed);
		}

		/*
		 * Start the crawl. This is a blocking operation, meaning that your code
		 * will reach the line after this only when crawling is finished.
		 */
		controller.start(EcommerceCrawler.class, numberOfCrawlers);
		 
	}
	
	
	public static void main(String... args){
		
		CrawlerConfig config = new CrawlerConfig("FLIPKART_CONF");
		config.setValue("seedURLs","http://www.flipkart.com/apple-iphone-6-plus/p/itme7zgfevzthsdh?pid=MOBEYHZ2VXZM8HZD&&colorSelected=true&otracker=pp_mobile_color");
		config.setValue("ContainsValue", "MOBEFJG74WYMR4ZJ, MOBEFJG7Q6AAUVGM, MOBEFJG7RZNK3YNV, MOBEYHZ2RVZHCMQK,MOBEYHZ2VXZM8HZD,MOBEYHZ2RVZHCMQK,MOBEYHZ2VXZM8HZD");
		config.setValue("maxDepth", "1");
		CrawlerController controller = new CrawlerController();
		try {
			controller.startCrawling(config);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	 
}