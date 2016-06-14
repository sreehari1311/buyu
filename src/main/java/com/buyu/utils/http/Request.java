package com.buyu.utils.http;

import java.io.IOException;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import com.buyu.utils.common.BuyuConfigurator;

//TODO Remove elastic search Server and make it generic
@Component
public class Request {
	private static final String ELASTIC_SERVER= BuyuConfigurator.getInstance().getValue("ELASTIC_SERVER");
	//TODO change function name - elastic request call
	public String postRequest(String postContent,String path){
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
         
            HttpPost httppost = new HttpPost(ELASTIC_SERVER+path);
            HttpEntity entity;
            String result = null;
			try {
				entity = new ByteArrayEntity(postContent.getBytes("UTF-8"));
				httppost.setEntity(entity);
	            HttpResponse response = httpclient.execute(httppost);
	            result = EntityUtils.toString(response.getEntity());
			} catch (IOException e) {
				e.printStackTrace();
			}finally {
	            try {
					httpclient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
	        }
            
          return result;       
	}
	
	


public String postRequest( String path,Map<String, String> headers){
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
         
            HttpPost httppost = new HttpPost(path);
            HttpEntity entity;
            String result = null;
			try {
				 
				 for(String key:headers.keySet()){
					 
					 httppost.addHeader(key, headers.get(key));
				 }
	            HttpResponse response = httpclient.execute(httppost);
	            result = EntityUtils.toString(response.getEntity());
			} catch (IOException e) {
				e.printStackTrace();
			}finally {
	            try {
					httpclient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
	        }
            
          return result;       
	}	

public String getRequest( String path){
	//System.out.println("PATH:::::"+path);
	CloseableHttpClient httpclient = HttpClients.createDefault();
     
        HttpGet httpGet = new HttpGet(path);
        String result = null;
		try {
			
			//System.out.print("Response fired");
            HttpResponse response = httpclient.execute(httpGet);
            //System.out.println("Respons is "+response);
            result = EntityUtils.toString(response.getEntity());
            //System.out.println("Result is ::::"+result);
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
            try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        
      return result;       
}


public String getRequest( String path,Map<String, String> headers){
	//System.out.println("PATH:::::"+path);
	CloseableHttpClient httpclient = HttpClients.createDefault();
     
        HttpGet httpGet = new HttpGet(path);
        String result = null;
		try {
			
			//System.out.print("Response fired");
			for(String key:headers.keySet()){
				 
				httpGet.addHeader(key, headers.get(key));
			 }
            HttpResponse response = httpclient.execute(httpGet);
            //System.out.println("Respons is "+response);
            result = EntityUtils.toString(response.getEntity());
            //System.out.println("Result is ::::"+result);
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
            try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        
      return result;       
}
}
