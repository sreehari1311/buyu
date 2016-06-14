package com.buyu.products;
import java.io.File;
import com.buyu.utils.common.BuyuConfigurator;
import com.buyu.utils.file.FileUtil;
import com.buyu.utils.http.Request;

public abstract class ProductsProcessor implements Processor{
	private Request httpRequest = new Request(); 
	public String fetchData(String url,boolean isLocal,String localStoreId,String productId){
		String filePath = BuyuConfigurator.getInstance().getValue(localStoreId)+"/"+productId+".xml";
		File f = new File(filePath);
		String response = null;
		if(isLocal&&f.exists()){
			response = FileUtil.getFileData(filePath);
		}else{
			try {
				// TODO configure sleep time
				System.out.println("SLeep....");
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			response = httpRequest.getRequest(url);
			FileUtil.writeData(filePath, response);
		}
		return response;
	}
	
	
}
