 package com.buyu.utils.common;

import java.io.BufferedReader;
import java.io.File;

import java.io.FileReader;
import java.io.IOException;
 
import java.util.HashMap;
import java.util.Map;

public class BuyuConfigurator {

	private Map<String, String> configMap = new HashMap<String,String>();
	private BuyuConfigurator(){
		initConfig();
	}
	
	public static BuyuConfigurator getInstance(){
		return BuyuConfiguratorLoader.INSTANCE;
	}
	private static class BuyuConfiguratorLoader{
		private static final BuyuConfigurator INSTANCE = new BuyuConfigurator();
	}
	public String getValue(String key){
		return configMap.get(key);
	}
	public void initConfig(){
		FileReader fr;
		try {
			fr = new FileReader(new File("/Users/sreehari/Dream/WorkSpace/buyu/src/main/resources/buyu.config"));
			BufferedReader buffer = new BufferedReader(fr);
			String line = null;
			while((line =buffer.readLine())!=null){
				String key = (line.split("="))[0];
				String value = (line.split("="))[1];
				configMap.put(key, value); 
				
			}
			buffer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	
}
