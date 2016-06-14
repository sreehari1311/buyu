package com.buyu.crawler;

public class CustomData {

	private CrawlerDAO crawlerDAO;
	public CrawlerDAO getCrawlerDAO() {
		return crawlerDAO;
	}
	public void setCrawlerDAO(CrawlerDAO crawlerDAO) {
		this.crawlerDAO = crawlerDAO;
	}
	public CrawlerConfig getCrawlerConfig() {
		return crawlerConfig;
	}
	public void setCrawlerConfig(CrawlerConfig crawlerConfig) {
		this.crawlerConfig = crawlerConfig;
	}
	private CrawlerConfig crawlerConfig;
	
}
