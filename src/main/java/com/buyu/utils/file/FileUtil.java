package com.buyu.utils.file;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

 

public class FileUtil {

	public static String getFileData(String path){
		StringBuffer  amazonResponse = new StringBuffer();
    	java.io.FileReader fwr = null;
		try {
			fwr = new java.io.FileReader(new File(path));
			int ch =0;
	    	while((ch=fwr.read())!=-1){
	    		amazonResponse.append((char)ch);
	    	}
	    	
		} catch ( IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				if(fwr!=null){
					fwr.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    	
    	return amazonResponse.toString();
	}
	public static void writeData(String path,String content){
		FileWriter fwr;
		try {
			fwr = new FileWriter(new File(path));
			fwr.write(content);
			fwr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
