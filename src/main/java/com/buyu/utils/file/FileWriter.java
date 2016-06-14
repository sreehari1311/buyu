package com.buyu.utils.file;

import java.io.IOException;

public class FileWriter {
	
	private java.io.FileWriter fileWriter;
	public FileWriter(String path){
		try {
			fileWriter = new java.io.FileWriter(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void writeContent(String content){
		 	 
			write(content);
			close();
	 
	}
	public void write(String content){
		try {
			fileWriter.write(content);
			 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void writeLine(String content){
		try {
			fileWriter.write(content);
			fileWriter.write("\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void close(){
		try {
			fileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	}
	
	
}
