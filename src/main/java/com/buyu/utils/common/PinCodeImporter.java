package com.buyu.utils.common;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.buyu.utils.db.DBConnector;
import com.buyu.utils.file.CSVReader;
import com.buyu.utils.file.Token;

@Component
public class PinCodeImporter {

 
	private CSVReader csvReader;
	@Autowired
	private DBConnector dbConnector;
	
	public void importAllFromFile(){
	 	List<Pin> pins = new ArrayList<Pin>();
	 	try {
	 		csvReader = new CSVReader(BuyuConfigurator.getInstance().getValue("PIN_CONFIG"),"PIN_CODES");
			Token token = null;
		    csvReader.skip();
		    while((token = csvReader.getToken())!=null){
		    	Pin pin = new Pin();
		    	pin.setPostOffice(token.getString("Post Office Name"));
		    	pin.setPin(token.getLong("Pincode"));
		    	pin.setDistrict(token.getString("Districts Name"));
		    	pin.setCity(token.getString("City Name"));
		    	pin.setState(token.getString("State"));
		    	pins.add(pin);
		    }
		} catch (FileNotFoundException e) {
					e.printStackTrace();
		}
	 	insertPinData(pins); 
	}
	
	public void insertPinData(List<Pin> pinList){
		
		for(Pin pin: pinList){   
			dbConnector.getJDBCTemplate().update("insert into PinCodes(postOffice,pin,city,district,state) values(?,?,?,?,?)",
					new Object[]{pin.getPostOffice(),pin.getPin() ,pin.getCity(),pin.getDistrict(),pin.getState()});
		}
	}
}
