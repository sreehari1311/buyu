package com.buyu.vendor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.buyu.utils.db.DBConnector;
@Component
public class VendorBuilder {

	@Autowired
	private DBConnector dbConnector;
	
	public void addVendor(Vendor vendor){
		dbConnector.getJDBCTemplate().update("insert into Vendor(name,address,city,state,pin,location) values(?,?,?,?,?,?)",
				new Object[]{vendor.getName(),vendor.getAddress(),vendor.getCity(),vendor.getState(),vendor.getPin(),vendor.getLocation()});

	}
}
