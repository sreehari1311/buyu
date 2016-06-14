package com.buyu.elasticsearch;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.buyu.catalog.CatalogBuilder;
import com.buyu.utils.common.BuyuConfigurator;
import com.buyu.utils.http.Request;
import com.google.gson.Gson;
@Component
public class IndexData {

	@Autowired
	private CatalogBuilder catalogBuilder;
	@Autowired Request request;
	private Gson gson = new Gson();
	
	
	 
	//"select p.productName,ps.color,ps.internal,ps.internalUnit,s.productId,s.subProductId,"
    //+"s.specId,c.companyName 
	public String indexAllProduct(){
		List<Map<String, Object>> dataList = catalogBuilder.getAllProductsDetailedList();
		for (Map<String, Object> data : dataList) {
			ProductIndex product = new ProductIndex();
			product.setBrand(data.get("companyName").toString());
			if(data.get("color")!=null){
				product.setColor(data.get("color").toString());
			}
			if(data.get("internal")!=null){
			product.setInternalMemory(data.get("internal").toString());
			}
			if(data.get("internalUnit")!=null){
			product.setInternalMemoryUnit(data.get("internalUnit").toString());
			}
			if(data.get("productId")!=null){
			product.setProductId(data.get("productId").toString());
			}
			if(data.get("productName")!=null){
			product.setProductName(data.get("productName").toString());
			}
			if(data.get("subProductId")!=null){
			product.setSubProductId(data.get("subProductId").toString());
			}
			if(data.get("imageURL")!=null){
				product.setImageURL(data.get("imageURL").toString());
				}
			indexOneProduct(product);
		}
		/*for(Product product:allProds){
			request.postRequest(gson.toJson(product),BuyuConfigurator.getInstance().getValue("PRODUCT_INDEX")+"/"+product.getProductId());
		}*/
	 	return "success";
	}
	
	public String indexOneProduct(ProductIndex product){
		
	 	request.postRequest(gson.toJson(product),BuyuConfigurator.getInstance().getValue("PRODUCT_INDEX")+"/"+product.getSubProductId());
		return "success";
	}
	public void indexAllSellers(){
		List<Map<String, Object>> dataList = catalogBuilder.getAllSellers();
		int id =1000;
		for (Map<String, Object> data : dataList) {
			id++;
			
			//String json =gson.toJson(data);
			request.postRequest(gson.toJson(data),BuyuConfigurator.getInstance().getValue("SELLER_INDEX")+"/"+id);
			 
		}
	}
	public void indexSubProducts(){
		
	}
}
