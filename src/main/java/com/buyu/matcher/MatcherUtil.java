package com.buyu.matcher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.xml.sax.SAXException;

import com.buyu.utils.common.BuyuConfigurator;

import no.priv.garshol.duke.ConfigLoader;
import no.priv.garshol.duke.Configuration;
import no.priv.garshol.duke.Processor;

public class MatcherUtil {

	public static String getMobileMatcher(Map<String, Object> data){
		String color = null;
		String internal = null;
		String internalUnit = null;
		String productName = null;
		if (data.get("color") != null) {
			color = data.get("color").toString();
		}
		if (data.get("internal") != null) {
			internal = data.get("internal").toString();
		}

		if (data.get("internalUnit") != null) {
			internalUnit = data.get("internalUnit").toString();
		}
		if (data.get("productName") != null) {
			productName = data.get("productName").toString();
		}
		if (color != null && internal != null && internalUnit != null) {
			//TODO Brand is hard coded
			return "ID: '" + data.get("subProductId").toString() + "' ,ProductName: '" + productName
					+ "' ,Color: '" + color + "' ,Internal: '" + internal.trim() + internalUnit.trim()
					+ "' ,Brand: '"+data.get("companyName").toString()+"'";
		}else{
			
			return null;
		}
	}
	public static String getMobileTitle(Map<String, Object> data){
		String color = null;
		String internal = null;
		String internalUnit = null;
		String productName = null;
		if (data.get("color") != null) {
			color = data.get("color").toString();
		}
		if (data.get("internal") != null) {
			internal = data.get("internal").toString();
		}

		if (data.get("internalUnit") != null) {
			internalUnit = data.get("internalUnit").toString();
		}
		if (data.get("productName") != null) {
			productName = data.get("productName").toString();
		}
		 if (color != null && internal != null && internalUnit != null) {
			return   productName + " (" + color + " , " + internal + " " + internalUnit + ")";
		}else{
			//TODO Brand is hard coded
			return null;
		}
	}
	
	public static boolean isMobileProductMatch(){
		Configuration config = null;
		try {
			config = ConfigLoader.load(BuyuConfigurator.getInstance().getValue("DUKE_TITLE_CONFIG"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 

		Processor proc = new Processor(config);
		PrintMatchListener matchListener = new PrintMatchListener(true, true, true, false,
				config.getProperties(), true);
		proc.addMatchListener(matchListener);
		proc.setPerformanceProfiling(true);

		proc.link();

		proc.close();
		System.out.println("CONFIDENT::::"+matchListener.getConfident());
		//TODO Matching logic hard coded here..
		if (matchListener.getMatches() >= 1 && matchListener.getConfident() > .98) {
			return true;
		}else{
			return false;
		}
	}
	
	public static String extractSizeFromTitle(String title){
		Pattern p = Pattern.compile("\\((.*?)\\)");
		Matcher m = p.matcher(title);
		String size = "";
		List<String> mList = new ArrayList<String>();
		while(m.find())
		{
		 	mList.add("("+m.group(1)+")");
	 	}
		for(String mtchs:mList){
			String colorFound = MatcherConfig.getInstance().containsColor(title);
			if(colorFound != null){
				mtchs = mtchs.replace(colorFound, " ");
				mtchs = mtchs.replace("(", " ").replace(")","").replace(",", " ");
			    mtchs = mtchs.trim();
			
			}
			//TODO handle Case sensitive
			if(mtchs.contains("MB") || mtchs.contains("GB")){
				size = mtchs.replace(" ","");
			}
			System.out.println(mtchs);
		}
		return size;
	}
	//TODO same implementation for Title Cleaner matcher
	public static void main(String...args){
		System.out.println(extractSizeFromTitle("Samsung Galaxy E7 (Black , 16 GB)"));
	}
}
