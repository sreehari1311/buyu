package com.buyu.matcher;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


import com.buyu.utils.common.BuyuConfigurator;

public class MatcherConfig {

	private Map<String,Boolean> configMap = new HashMap<String,Boolean>();
	private MatcherConfig(){
		init();
	}
	public void init(){
		FileReader fr;
		try {
			fr = new FileReader(new File(BuyuConfigurator.getInstance().getValue("COLOR_CONFIG")));
			BufferedReader buffer = new BufferedReader(fr);
			String line = null;
			while((line =buffer.readLine())!=null){
				 
				configMap.put(line.trim(), true); 
				
			}
			buffer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public boolean isColor(String color){
		if(configMap.get(color)!=null){
			return configMap.get(color);
		}else{
			return false;
		}
	}
	public String containsColor(String input){
		
		for(String color: configMap.keySet()){
			if(input.contains(color)){
				return color;
			}
		}
		return null;
	}
	public static MatcherConfig getInstance(){
		return MatcherConfigLoader.INSTANCE;
	}
	private static class MatcherConfigLoader{
		private static final MatcherConfig INSTANCE = new MatcherConfig();
	}
}
