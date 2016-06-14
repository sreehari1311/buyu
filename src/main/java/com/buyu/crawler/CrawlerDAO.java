package com.buyu.crawler;

 

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
 
import com.buyu.utils.db.DBConnector;

@Component
public class CrawlerDAO {
	@Autowired
	private DBConnector dbConnector;
	public void insertCrawlData(Crawler crawler){
		  	dbConnector.getJDBCTemplate().update("insert into Crawler(site_id ,title,url,parent_url,crawl_date,productId) values(?,?,?,?,CURDATE(),?)",new Object[]{crawler.getSiteId(),
		  			crawler.getTitle(),crawler.getUrl(),crawler.getParentUrl(),crawler.getProductId()});
		 
	}
	
	public List<Crawler> allCrawlData(String configId){
		List<Map<String,Object>> dataList = dbConnector.getJDBCTemplate().queryForList("select * from Crawler where site_id=?",new Object[]{configId} );
		List<Crawler> crawlerList = new ArrayList<Crawler>();
		for(Map<String, Object> data:dataList){
			Crawler crawler = new Crawler();
			if(data.get("parent_url")!=null){
				crawler.setParentUrl(data.get("parent_url").toString());
			}
			if(data.get("url")!=null){
			crawler.setUrl(data.get("url").toString());
			}
			crawler.setId(data.get("categoryId").toString());
			if(data.get("title")!=null){
			crawler.setTitle(data.get("title").toString());
			}
			crawlerList.add(crawler);
			 
		}
		return crawlerList;
	 
}
}
