package com.buyu.utils.file;

import java.util.HashMap;
import java.util.Map;

public final class Token {

	private String [] tokens;
	private Map<String, Integer> indexMap = new HashMap<String, Integer>();
	private int length;
	public Token(String...tokens){
		this.tokens = tokens;
		this.length = tokens.length;
	}
	public Token(Map<String, Integer> indexMap,String...tokens){
		this.tokens = tokens;
		this.length = tokens.length;
		this.indexMap = indexMap;
	}
	public String getString(String key){
		//System.out.println(indexMap);
		 return getString(indexMap.get(key));
	}
	
	public long getLong(String key){
		 return getLong(indexMap.get(key));
	}
	
	public String getString(int index){
		if(index >= length || tokens[index].trim().equals("")){
			return null;
		}
		return tokens[index];
	}
	
	public long getLong(int index){
		if(index >= length || tokens[index].trim().equals("")){
			return 0;
		}
		 
		return Long.valueOf(tokens[index].trim());
	}
}
