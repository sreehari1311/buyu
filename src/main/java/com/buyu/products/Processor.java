package com.buyu.products;

public interface Processor {

	public void processAllProducts(boolean isLocal);
	public String fetchData(String url,boolean isLocal,String localStoreId,String productId);
	public boolean matchAndMerge(String source,String destination);
	//public void indexData();
}
