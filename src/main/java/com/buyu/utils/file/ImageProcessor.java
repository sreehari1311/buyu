package com.buyu.utils.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import java.util.Random;

public class ImageProcessor {

	public static void saveImage(String productName,String images[]){
		try{
		File f = new File("/Users/sreehari/Dream/WorkSpace/buyu/src/test/resources/productImages/"+productName);
		f.mkdir();
		Random rn = new Random();
		int range = 12000 - 2000 ;
		for(int i=0;i<images.length;i++){
			File fl = new File("/Users/sreehari/Dream/WorkSpace/buyu/src/test/resources/productImages/"+productName+"/"+i+".jpeg");
			URL url = new URL(images[i]);
			InputStream is = url.openStream();
			OutputStream os = new FileOutputStream(fl);

			byte[] b = new byte[2048];
			int length;

			while ((length = is.read(b)) != -1) {
				os.write(b, 0, length);
			}

			is.close();
			os.close();
			int randomNum =  rn.nextInt(range) + 2000;
			Thread.sleep(randomNum);
		}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public static void main(String...strings){
		
		 
		
		 
	}
}
