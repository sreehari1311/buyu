package com.buyu.crawler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.buyu.utils.common.BuyuConfigurator;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
 

public class CrawlerConfig {

	private String path;
	private Map<String, String> configMap;
	private CrawlConfig crawlConfig;
	private Map<String, Boolean> skipMap;
	private Map<String,Boolean> crawledUrls;
	private String siteId;
	public CrawlerConfig(String configId){
		this.path = BuyuConfigurator.getInstance().getValue(configId);
		this.siteId = configId;
		configMap = new HashMap<String,String>();
		skipMap = new HashMap<String,Boolean>();
		crawlConfig = new CrawlConfig();
		crawledUrls = new HashMap<String,Boolean>();
		initConfig();
		initCrawlerConfig();
	}
	public String getSiteId(){
		return this.siteId;
	}
	public String getValue(String key){
		return configMap.get(key);
	}
	public void setValue(String key,String value){
		configMap.put(key, value);
	}
	public CrawlConfig getCrawlerConfig(){
		return crawlConfig;
	}
	public void addUrl(String url){
		crawledUrls.put(url, true);
	}
	public boolean isSkip(String url){
		 
		if(configMap.containsKey("NotContainsValue")){
			String contains[] = configMap.get("NotContainsValue").toLowerCase().split(",");
			for(String contain:contains){
			 
			if(url.toLowerCase().contains(contain.toLowerCase().trim())){
				return false;
			}
			
			} 
		}
		if(configMap.containsKey("ContainsValue")){
			String contains[] = configMap.get("ContainsValue").toLowerCase().split(",");
			for(String contain:contains){
			 
			if(url.toLowerCase().contains(contain.toLowerCase().trim())){
				return true;
			}
			
			}return false;
		}
		for(String skipUrl:skipMap.keySet()){
		 
			if(url.toLowerCase().startsWith(skipUrl.toLowerCase())){
				
				return true;
			}
		}
		return false;
	}
	private void initCrawlerConfig(){
		System.out.println("Init config");
		crawlConfig.setCrawlStorageFolder(configMap.get("destination"));
		crawlConfig.setUserAgentString(configMap.get("user-agent"));
		crawlConfig.setPolitenessDelay(Integer.parseInt(configMap.get("delay")));
		crawlConfig.setMaxDepthOfCrawling(Integer.parseInt(configMap.get("maxDepth")));
		crawlConfig.setIncludeBinaryContentInCrawling(Boolean.valueOf(configMap.get("includeBinaryContent")));
		crawlConfig.setMaxPagesToFetch(Integer.parseInt(configMap.get("maxPages")));
		crawlConfig.setResumableCrawling(Boolean.valueOf(configMap.get("resumable")));
		String skipUrls = configMap.get("skipUrls");
		for(String skipUrl:skipUrls.split(",")){
			skipMap.put(skipUrl, true);
		}
	}
	
	 private void initConfig(){
			FileReader fr;
			try {
				fr = new FileReader(new File(path));
				BufferedReader buffer = new BufferedReader(fr);
				String line = null;
				while((line =buffer.readLine())!=null){
					String key = (line.split("="))[0];
					String value = line.substring(line.indexOf("=")+1,line.length());
					configMap.put(key.trim(), value.trim()); 
					System.out.println(key+" = "+value);
					
				}
				buffer.close();
				fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
		}
	 
}
