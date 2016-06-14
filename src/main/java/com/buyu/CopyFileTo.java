package com.buyu;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

 
import com.buyu.catalog.CatalogBuilder;
import com.buyu.matcher.MatcherUtil;
import com.buyu.products.amazon.ItemAttribute;
import com.buyu.utils.common.BuyuConfigurator;
@SpringBootApplication
public class CopyFileTo {

	public static boolean parseXML(String xmlContent,String title) throws Exception {
	    //Get the DOM Builder Factory
	    ItemAttribute itemAttribute = null;

	    DocumentBuilderFactory factory = 
	        DocumentBuilderFactory.newInstance();

	    //Get the DOM Builder
	    DocumentBuilder builder = factory.newDocumentBuilder();

	    //Load and Parse the XML document
	    //document contains the complete XML as a Tree.
	    InputSource in = new InputSource(new StringReader(xmlContent));
	    Document document =  builder.parse(in);

	    
	    //Iterating through the nodes and extracting the data.
	    NodeList nodeList = document.getDocumentElement().getChildNodes();

	    for (int i = 0; i < nodeList.getLength(); i++) {

	      //We have encountered an <employee> tag.
	      
	      Node node = nodeList.item(i);
	      if(node.getNodeName().equals("OperationRequest")){
	    	 // System.out.println(node.getNodeName());
	    	  NodeList ndList = node.getChildNodes();
	    	  for(int f = 0;f<ndList.getLength();f++){
	    		  Node n = ndList.item(f);
	    		 
	    		  if(n.getNodeName().equals("Arguments") && n.getChildNodes().getLength() >0){
	    			  for(int l = 0;l< n.getChildNodes().getLength();l++){
	    				  if(n.getChildNodes().item(l).getNodeName().equals("Argument")){
	    					   
	    					  NamedNodeMap attributes = n.getChildNodes().item(l).getAttributes();

	    					  for (int a = 0; a < attributes.getLength(); a++) 
	    					  {
	    					          Node theAttribute = attributes.item(a);
	    					          //System.out.println(theAttribute.getNodeName());
	    					          if(theAttribute.getNodeName().equals("Name") && theAttribute.getNodeValue().equals("Keywords")){
	    					        	   //theAttribute = attributes.item(1);
	    					        	   //System.out.println( theAttribute.getNodeValue());
	    					        	   theAttribute = attributes.item(a+1);
	    					        	   if(title.equals(theAttribute.getNodeValue())){
	    					        		   return true;
	    					        	   }
	    					        	  // System.out.println( );
	    					        	   
	    					          }
	    					          //System.out.println(theAttribute.getNodeName() + "=" + theAttribute.getNodeValue());
	    					  }


	    					   
	    				  }
	    				   
	    			 
	    			  }
	    			   
	    			 
	    			  
	    		  }
	    	  }
	      
	      }
	    }
	    
	return false;
	
	}
	public static String getFileName(String keyWord){
		File fl = new File(BuyuConfigurator.getInstance().getValue("AMAZON_XML_STORE"));
		String flNames[] = fl.list();
		for(String file:flNames){
			if(file.endsWith(".xml")){
				StringBuffer amazonResponse = new StringBuffer();
				 
				java.io.FileReader fwr;
				try {
					fwr = new java.io.FileReader(new File(BuyuConfigurator.getInstance().getValue("AMAZON_XML_STORE")+"/"+file));
					int ch =0;
		        	while((ch=fwr.read())!=-1){
		        		amazonResponse.append((char)ch);
		        	}
		        	fwr.close();
		        	try {
						if(parseXML(amazonResponse.toString(),keyWord)){
							return file;
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch ( IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	
			}
		}
		return null;
	}
	public static void main(String... args){
		
		ConfigurableApplicationContext context =SpringApplication.run(AmazonProductRunner.class, args);
		CatalogBuilder catalogBuilder =context.getBean(CatalogBuilder.class);
		List<Map<String, Object>> dataList = catalogBuilder.getAllProductsDetailedList();
		int fileCount = 1;
		for (Map<String, Object> data : dataList) {
			String title =  MatcherUtil.getMobileTitle(data);
			if(title!=null){
				String fileName = getFileName(title);
				if(fileName != null){
					System.out.println(title+":"+fileName);
					File source = new File(BuyuConfigurator.getInstance().getValue("AMAZON_XML_STORE")+"/"+fileName);
					File dest = new File(BuyuConfigurator.getInstance().getValue("AMAZON_XML_STORE")+"/New/"+data.get("subProductId")+".xml");
					try {
						Files.copy(source.toPath(), dest.toPath());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					fileCount++;
				}
				//System.out.println(fileName);
				 
			}
		}
		System.out.println("Total Files: "+fileCount);
	}
}
