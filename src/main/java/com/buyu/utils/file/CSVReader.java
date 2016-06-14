package com.buyu.utils.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;



import com.buyu.utils.common.CSVConfigurator;

public class CSVReader {

	private FileReader fileReader;
	private BufferedReader bufferedReader;
	private Map<String, Integer> indexMap;
	private String fileName;
	public CSVReader(String fileName,String config) throws FileNotFoundException{
		this.fileName =fileName;
		fileReader = new FileReader(new File(fileName));
		bufferedReader = new BufferedReader(fileReader);
		indexMap = CSVConfigurator.getInstance().getHeaders(config);
 	}
	
	private String readLine() throws IOException{
		String line = null;
		line = bufferedReader.readLine();
		 if(line == null){
		 		fileReader.close();
				bufferedReader.close();
		}
		return line;
	}
	public void skip() {
		try {
			fileReader = new FileReader(new File(fileName));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		bufferedReader = new BufferedReader(fileReader);
		String line = null;
		try {
			line = bufferedReader.readLine();
			 if(line == null){
			 		fileReader.close();
					bufferedReader.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public Token getToken(){
		String line = null;
		try {
			line = readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(line == null){
			return null;
		}
		String tokens[] = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
		Token token = new Token(indexMap,tokens);
		return token;
	}
	
}
