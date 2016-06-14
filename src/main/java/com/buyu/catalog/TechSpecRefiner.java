package com.buyu.catalog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

@Component
public class TechSpecRefiner {

	@Autowired
	private CatalogBuilder catalogBuilder;
	
	public void validateAllTechSpecs(){
		List<Map<String, Object>> specs = catalogBuilder.getAllTechSpecifications();
		List<String> keyList = new ArrayList<String>();
		for(Map<String, Object> spec:specs){
			
			String specJson = spec.get("spec").toString();
		//	System.out.println(specJson);
			Gson gson = new Gson();
			Map<String, String> mp = gson.fromJson(specJson, Map.class);
			Set<String> keys = mp.keySet();
			String value = mp.get("Type");
			if(value!=null){
			 System.out.println(value);
			}
			for(String key: keys){
				if(!keyList.contains(key)){
					keyList.add(key);
				}
			}
			
			
		}
		for(String k:keyList){
			//System.out.println(k);
		}
	}
	
}
