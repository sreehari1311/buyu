package com.buyu.crawler;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.http.Header;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.buyu.CrawlerRunner;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

public class EcommerceCrawler extends WebCrawler {
	private static final Logger logger = LoggerFactory.getLogger(EcommerceCrawler.class);
	//TODO move this to crawler config
	private final  Pattern FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpg"
            + "|png|mp3|mp3|zip|gz))$");
public static int i=0;
private CrawlerConfig crawlerConfig;
private CrawlerDAO crawlerDao;
private static int bookNumber = 1;
@Override
public void onStart() {
	CustomData customData = (CustomData) myController.getCustomData();
	crawlerConfig =  customData.getCrawlerConfig();
	crawlerDao = customData.getCrawlerDAO();
	 
  }


/**
 * You should implement this function to specify whether the given url
 * should be crawled or not (based on your crawling logic).
 */
@Override
public boolean shouldVisit(Page referringPage, WebURL url) {
	
  String href = url.getURL().toLowerCase();
  if(href.endsWith(".pdf")){
	  System.out.println("PdFFFFFFFFFFFFFFFFFFFFFPdFFFFFFFFFFFFFFFFFFFFFPdFFFFFFFFFFFFFFFFFFFFFPdFFFFFFFFFFFFFFFFFFFFFPdFFFFFFFFFFFFFFFFFFFFFPdFFFFFFFFFFFFFFFFFFFFF:::"+href);
	  String fileName;
	try {
		fileName = java.net.URLDecoder.decode(href.substring(href.lastIndexOf("/")+1),"UTF-8");
		 System.out.println("FileName:::::"+fileName);
		 URL oracle = new URL(href);
	        URLConnection yc = oracle.openConnection();
	       //InputStreamReader in =  yc.getInputStream().;
	        int n =0;
	        FileOutputStream fos = new FileOutputStream(new File(crawlerConfig.getValue("destination")+"/"+fileName));
	        while((n=yc.getInputStream().read())!=-1){
	        	fos.write(n);
	        }
	        fos.close();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return false;
	} 
	 
	  
	  return false;
  }
   // Ignore the url if it has an extension that matches our defined set of image extensions.
 // System.out.println(href+"::::::::"+"http://forex-warez.no-ip.biz/Trading%20Books/".toLowerCase()+":::"+href.startsWith("http://forex-warez.no-ip.biz/Trading%20Books/".toLowerCase())); 
  return (!FILTERS.matcher(href).matches()
          )&& href.startsWith("http://forex-warez.no-ip.biz/Trading%20Books/".toLowerCase());
  
  // Only accept the url if it is in the "www.ics.uci.edu" domain and protocol is "http".
   
}

/**
 * This function is called when a page is fetched and ready to be processed
 * by your program.
 */
@Override
public void visit(Page page) {
	
  int docid = page.getWebURL().getDocid();
  String url = page.getWebURL().getURL();
  String domain = page.getWebURL().getDomain();
  String path = page.getWebURL().getPath();
  String subDomain = page.getWebURL().getSubDomain();
  String parentUrl = page.getWebURL().getParentUrl();
  String anchor = page.getWebURL().getAnchor();
  Crawler crawler = new Crawler();
  
  logger.debug("Docid: {}", docid);
  logger.info("URL: {}", url);
  logger.info("query:"+page.getWebURL().toString());
  logger.debug("Domain: '{}'", domain);
  logger.debug("Sub-domain: '{}'", subDomain);
  logger.debug("Path: '{}'", path);
  logger.debug("Parent page: {}", parentUrl);
  logger.debug("Anchor text: {}", anchor);
  logger.debug("Title: ",crawlerConfig.getValue("title"));
  crawler.setUrl(url);
  crawler.setParentUrl(parentUrl);
  crawler.setSiteId(crawlerConfig.getSiteId());
  if(url.endsWith(".pdf")){
		System.out.println("Book Found@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+url);

		FileOutputStream fos;
		try {
			fos = new FileOutputStream(new File(crawlerConfig.getValue("destination")+"/"+bookNumber+".pdf"));
			fos.write(page.getContentData());
			fos.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
  if (page.getParseData() instanceof HtmlParseData) {
    HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
    String text = htmlParseData.getText();
    String html = htmlParseData.getHtml();
    for(String key:htmlParseData.getMetaTags().keySet()){
    	System.out.println(key+" : "+htmlParseData.getMetaTags().get(key));
    	
    }
    String title = htmlParseData.getMetaTags().get(crawlerConfig.getValue("title"));
    if(title == null){
    //	Elements select =doc.select(crawlerConfig.getValue("titleParser"));
    	Document doc = Jsoup.parse(html, "UTF-8");
    	Elements select =doc.getElementsByTag("title");
    	title = select.get(0).text();
    }
    String flName = null;
    if(crawlerConfig.getValue("pIdStart")!=null){
    	if(url.indexOf(crawlerConfig.getValue("pIdStart"))>0){
    		String ul = url.substring(url.indexOf(crawlerConfig.getValue("pIdStart")));
    		if(ul.indexOf(crawlerConfig.getValue("pIdEnd"))>0){
    			flName = ul.substring(0, ul.indexOf(crawlerConfig.getValue("pIdEnd")));
    			System.out.println("PRODUCT ID:::::::::::::::::::::::::::::::::::::::::::::::"+flName);
    			flName = flName.replace("pid=", "");
    			crawler.setProductId(flName);
    		}else{
    			flName = ul.substring(0, ul.length());
    			System.out.println("PRODUCT ID LAST:::::::::::::::::::::::::::::::::::::::::::::::"+flName);
    			flName = flName.replace("pid=", "");
    			crawler.setProductId(flName);
    		}
    	}
    	 
    }
    String fileName = title+".html";
    if(flName !=null){
    	fileName = flName+".html";
    }else if(crawlerConfig.getValue("FileName")!=null){
    	 fileName = crawlerConfig.getValue("FileName")+".html";
    }
    fileName +=i+"-"+fileName;
    crawler.setTitle(title); 
    i++;
    try {
    	    //	System.out.println(html);
    	bookNumber++;
    	if(url.endsWith(".pdf")){
    		System.out.println("Book Found@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+url);

    		FileWriter fr = new FileWriter(new File(crawlerConfig.getValue("destination")+"/"+bookNumber+".pdf"));
		fr.write(html);
		fr.close();
    	}
		
		//crawlerDao.insertCrawlData(crawler);
		//crawlerConfig.addUrl(url);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    System.out.println("Title:::::::"+htmlParseData.getMetaTags().get(crawlerConfig.getValue("title")));
    Set<WebURL> links = htmlParseData.getOutgoingUrls();

    logger.debug("Text length: {}", text.length());
    logger.debug("Html length: {}", html.length());
    logger.debug("Number of outgoing links: {}", links.size());
    for(WebURL link:links){
    	if(link.getURL().startsWith("http://www.infibeam.com/Mobiles/apple")){
    	
    	  logger.debug("LINK: "+link.getURL());
    	}
    }
  }

  Header[] responseHeaders = page.getFetchResponseHeaders();
  if (responseHeaders != null) {
    logger.debug("Response headers:");
    for (Header header : responseHeaders) {
      logger.debug("\t{}: {}", header.getName(), header.getValue());
    }
  }

  logger.debug("=============");
}}