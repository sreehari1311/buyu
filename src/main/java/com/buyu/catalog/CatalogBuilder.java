package com.buyu.catalog;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import com.buyu.flipkart.FlipKart;
import com.buyu.utils.db.DBConnector;

@Component
public class CatalogBuilder {

	@Autowired
	private DBConnector dbConnector;
	public void insertCompanyData(List<Company> companyList){
		for(Company company: companyList){
			dbConnector.getJDBCTemplate().update("insert into Company(companyName) values(?)",new Object[]{company.getName()});
			
		}
	}
	
	 
   public void insertFlipKart(List<FlipKart> productList){
		
		for(FlipKart product: productList){
			 	
			dbConnector.getJDBCTemplate().update("insert into Flipkart(productId,flipkartId,itemPrice,productURL) values(?,?,?,?)",
					new Object[]{product.getProductId(),product.getFlipKartId() ,product.getPrice(),product.getProductURL()});
		}
	}
	
    
	public void insertProductData(List<Product> productList){
		
		for(Product product: productList){
			if(product.getProductDescription()!=null && product.getProductDescription().length()>500){
				product.setProductDescription(product.getProductDescription().substring(0, 499));
			}
			
			dbConnector.getJDBCTemplate().update("insert into Product(productName,productDesc,companyId) values(?,?,?)",
					new Object[]{product.getProductName(),product.getProductDescription() ,product.getCompanyId()});
		}
	}
	
public long insertProductData(final Product product){
		
		KeyHolder holder = new GeneratedKeyHolder();
		dbConnector.getJDBCTemplate().update(new PreparedStatementCreator() {           

            @Override
            public PreparedStatement createPreparedStatement(Connection connection)
                    throws SQLException {
                PreparedStatement ps = connection.prepareStatement("insert into Product(productName,productDesc,companyId) values(?,?,?)",
                		Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, product.getProductName());
                if(product.getProductDescription()!=null && product.getProductDescription().length()>500){
                ps.setString(2, product.getProductDescription().substring(0,498));
                }else{
                	ps.setString(2, product.getProductDescription());
                }
               
                ps.setLong(3, product.getCompanyId());
                return ps;
            }
        }, holder);	
			 
		Long newProductId = holder.getKey().longValue();
		return newProductId;
	}
	
public long insertProductSpecData(final ProductSpec productSpec){
	
	KeyHolder holder = new GeneratedKeyHolder();
	dbConnector.getJDBCTemplate().update(new PreparedStatementCreator() {           

        @Override
        public PreparedStatement createPreparedStatement(Connection connection)
                throws SQLException {
            PreparedStatement ps = connection.prepareStatement("insert into ProductSpec(color,internal,internalUnit,spec) values(?,?,?,?)",
            		Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, productSpec.getColor());
            ps.setInt(2, productSpec.getInternal());
            ps.setString(3, productSpec.getInternalUnit());
            ps.setString(4, productSpec.getSpec());
            
            return ps;
        }
    }, holder);	
		 
	Long newSpecId = holder.getKey().longValue();
	return newSpecId;
}

public long insertSubProductData(final long productId,final long specId,final String imageURL,final String sPid){
	
	KeyHolder holder = new GeneratedKeyHolder();
	dbConnector.getJDBCTemplate().update(new PreparedStatementCreator() {           

        @Override
        public PreparedStatement createPreparedStatement(Connection connection)
                throws SQLException {
            PreparedStatement ps = connection.prepareStatement("insert into SubProduct(productId,specId,imageURL,sPid) values(?,?,?,?)",
            		Statement.RETURN_GENERATED_KEYS);
           ps.setLong(1, productId);
           ps.setLong(2, specId);
           ps.setString(3, imageURL);
           ps.setString(4, sPid);
           return ps;
        }
    }, holder);	
		 
	Long newSpecId = holder.getKey().longValue();
	return newSpecId;
}

public void insertSellerData(Seller seller){
	
	dbConnector.getJDBCTemplate().update("insert into Sellers(sellerId,subProductId,sellerPID,productURL,mrp,sellingPrice) values(?,?,?,?,?,?)",
			new Object[]{seller.getSellerId(),seller.getSubProductId(),seller.getSellerProductId(),seller.getSellerProductURL(),seller.getMrp(),seller.getSellingPrice()});
 
}
public void insertProductSpecData(long productId,long specId){
	
		dbConnector.getJDBCTemplate().update("insert into SubProduct(productId,specId) values(?,?)",
				new Object[]{productId,specId});
	 
}



	 
	
	public Map<String, Long> getCompanyIdList(){
		List<Map<String,Object>> dataList = dbConnector.getJDBCTemplate().queryForList("select companyName,companyId from Company");
		Map<String, Long> companyMap = new HashMap<String,Long>();
		for(Map<String, Object> data:dataList){
			Long companyId = Long.valueOf((data.get("companyId")).toString());
			companyMap.put(data.get("companyName").toString(), companyId);
 		}
		return companyMap;
	}
	public List<Map<String,Object>> getAllSellers(){
	 	List<Map<String,Object>> dataList = dbConnector.getJDBCTemplate().queryForList(
					"select * from sellers");
	 	
		return dataList;
}
	public List<Map<String,Object>> getAllProductsDetailedList(){
	 	List<Map<String,Object>> dataList = dbConnector.getJDBCTemplate().queryForList(
					"select p.productName,ps.color,ps.internal,ps.internalUnit,s.productId,s.subProductId,s.sPid,s.imageURL,"
				    +"s.specId,c.companyName from subProduct s,Product p,ProductSpec ps,Company c where "
				    +"s.productId = p.productId and ps.specId = s.specId and p.companyId = c.companyId");
	 	
		return dataList;
}
	
	public List<Map<String,Object>> getAllTechSpecifications(){
		return dbConnector.getJDBCTemplate().queryForList("select specId,spec from ProductSpec");
	}
	
	public Map<String, Long> getProductIdList(){
		List<Map<String,Object>> dataList = dbConnector.getJDBCTemplate().queryForList("select productName,productId from Product");
		Map<String, Long> productMap = new HashMap<String,Long>();
		for(Map<String, Object> data:dataList){
			Long companyId = Long.valueOf((data.get("productId")).toString());
			productMap.put(data.get("productName").toString(), companyId);
			 
		}
		return productMap;
	}
}
