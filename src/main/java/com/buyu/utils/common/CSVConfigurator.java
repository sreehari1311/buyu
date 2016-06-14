package com.buyu.utils.common;

import java.io.BufferedReader;
import java.io.File;

import java.io.FileReader;
import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

public class CSVConfigurator {

	private Map<String, Map<String,Integer>> configMap = new HashMap<String,Map<String,Integer>>();
	private CSVConfigurator(){
		initConfig();
	}
	
	public static CSVConfigurator getInstance(){
		return CSVConfigLoader.INSTANCE;
	}
	private static class CSVConfigLoader{
		private static final CSVConfigurator INSTANCE = new CSVConfigurator();
	}
	public Map<String, Integer> getHeaders(String key){
		return configMap.get(key);
	}
	public void initConfig(){
		FileReader fr;
		try {
			fr = new FileReader(new File(BuyuConfigurator.getInstance().getValue("CSV_CONFIG")));
			BufferedReader buffer = new BufferedReader(fr);
			String line = null;
			while((line =buffer.readLine())!=null){
				String key = (line.split("="))[0];
				String value = (line.split("="))[1];
				Map<String, Integer> map = new HashMap<String,Integer>();
				int i = 0;
				for(String k:value.split(",")){
					map.put(k, i++);
				}
				configMap.put(key, map); 
				
			}
			buffer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	
}
