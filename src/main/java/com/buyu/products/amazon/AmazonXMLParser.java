package com.buyu.products.amazon;

import java.io.StringReader; 

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document; 
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.buyu.matcher.MatcherUtil;

 
public class AmazonXMLParser {


	public static ItemAttribute parseXML(String xmlContent) throws Exception {
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
	      if(node.getNodeName().equals("Items")){
	    	 // System.out.println(node.getNodeName());
	    	  NodeList ndList = node.getChildNodes();
	    	  for(int f = 0;f<ndList.getLength();f++){
	    		  Node n = ndList.item(f);
	    		  String brand = "";
	    		  String color = "";
	    		  String manufacturer = "";
	    		  String model = "";
	    		  String mpn = "";
	    		  String os = "";
	    		  String productTypeName = "";
	    		  String title = "";
	    		  String amount = "";
	    		  String pageURL = "";
	    		  String amazonId = "";
	    		  String size = "";
	    		  if(n.getNodeName().equals("Item") && n.getChildNodes().getLength() >0){
	    			  for(int l = 0;l< n.getChildNodes().getLength();l++){
	    				  if(n.getChildNodes().item(l).getNodeName().equals("ASIN")){
	    					  amazonId = n.getChildNodes().item(l).getTextContent();
	    				  }
	    				  if(n.getChildNodes().item(l).getNodeName().equals("ItemAttributes")){
	    					  Node attrib = n.getChildNodes().item(l);
	    					  NodeList attribChilds = attrib.getChildNodes();
	    					  for(int s = 0;s<attribChilds.getLength();s++){
	    						  String nodeName = attribChilds.item(s).getNodeName();
	    						  String content =  attribChilds.item(s).getTextContent();
	    						  if(nodeName.equals("Brand")){
	    							  brand = content;
	    						  }else if(nodeName.equals("Color")){
	    							  color = content;
	    						  }else if(nodeName.equals("Manufacturer")){
	    							  manufacturer = content;
	    						  }else if(nodeName.equals("Model")){
	    							  model = content;
	    						  }else if(nodeName.equals("MPN")){
	    							  mpn = content;
	    						  }else if(nodeName.equals("OperatingSystem")){
	    							  os = content;
	    						  }else if(nodeName.equals("ProductTypeName")){
	    							  productTypeName = content;
	    						  }else if(nodeName.equals("Title")){
	    							  title = content;
	    						  }else if(nodeName.equals("Size")){
	    							  size = content;
	    						  }
	    					  }
	    				  } if(n.getChildNodes().item(l).getNodeName().equals("OfferSummary")){
	    					  
	    					  amount = n.getChildNodes().item(l).getChildNodes().item(0).getChildNodes().item(2).getTextContent();//.getChildNodes().item(2).getTextContent();
	    					  amount = amount.replace("INR","");
	    				  } if(n.getChildNodes().item(l).getNodeName().equals("DetailPageURL")){
	    					  pageURL = n.getChildNodes().item(l).getTextContent();
	    				  }
	    			 
	    			  }
	    			   
	    			  if(productTypeName.equals("PHONE"))
	    			  {
	    				  itemAttribute = new ItemAttribute();
	    				  itemAttribute.setBrand(brand);
	    				  itemAttribute.setTitle(title);
	    				  itemAttribute.setManufacturer(manufacturer);
	    				  itemAttribute.setModel(model);
	    				  itemAttribute.setColor(color);
	    				  itemAttribute.setManufacturer(manufacturer);
	    				  itemAttribute.setProductTypeName(productTypeName);;
	    				  itemAttribute.setAmazonId(amazonId);
	    				  itemAttribute.setProductURL(pageURL);
	    				  itemAttribute.setPrice(Float.parseFloat(amount.replace(",", "")));
	    				  if(size == null || size.trim().equals("")){
	    					  itemAttribute.setInternal(MatcherUtil.extractSizeFromTitle(title));
	    				  }else{
	    					  itemAttribute.setInternal(size);
	    				  }
	    				  
		    			  System.out.println("\""+brand+"\",\""+title+"\",\""+manufacturer+"\",\""+model+"\",\""+color+"\",\""+mpn+"\",\""+os+"\",\""+amount+"\",\""+pageURL+"\"\n");
		    			return itemAttribute;  
	    			  }
	    			  
	    		  }
	    	  }
	      
	      }
	    }
	    
	return itemAttribute;
	
	}}

	    //Printing the Employee list populated.
	   
		 
 
