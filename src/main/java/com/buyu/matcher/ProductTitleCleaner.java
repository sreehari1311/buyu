package com.buyu.matcher;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.ws.soap.MTOM;

import no.priv.garshol.duke.Cleaner;

public class ProductTitleCleaner implements Cleaner {

	 
	private static List<String> sizeList;
	public ProductTitleCleaner(){
	 	sizeList = new ArrayList<String>();
	 	sizeList.add("16GB");
		sizeList.add("32GB");
		sizeList.add("64GB");
		sizeList.add("8GB");
		sizeList.add("4GB");
	}
	@Override
	public String clean(String input) {
		// TODO Auto-generated method stub
		Pattern p = Pattern.compile("\\((.*?)\\)");
		Matcher m = p.matcher(input);
		List<String> mList = new ArrayList<String>();
		while(m.find())
		{
		 	mList.add("("+m.group(1)+")");
	 	}
		for(String mtchs:mList){
			input = input.replace(mtchs, "");
		}
	  	String colorFound = MatcherConfig.getInstance().containsColor(input);
		if(colorFound != null){
			input = input.replace(colorFound, " ");
			input = input.replace("(", "").replace(")","");
		}
		
		for(String space:sizeList){
			if(input.contains(space)){
				input = input.replace(space, " ");
				input = input.replace("(", "").replace(")","");
			}
		} 
		System.out.println("CLEANED: "+input);
		return input;
	}

}
