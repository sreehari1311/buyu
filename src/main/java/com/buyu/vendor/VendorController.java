package com.buyu.vendor;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
/*import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
*/
import com.buyu.vendor.Vendor;
import com.buyu.vendor.VendorBuilder;

//@RestController
public class VendorController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @Autowired
    VendorBuilder vendorBuilder;
    
    
  /*  @RequestMapping(value="/createVendor",method = RequestMethod.POST)
    public String createVendor(@RequestBody  Vendor vendor) {
    	vendorBuilder.addVendor(vendor);
    	return "Success";
    }*/ 
    
}