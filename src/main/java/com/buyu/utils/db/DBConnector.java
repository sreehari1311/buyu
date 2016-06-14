package com.buyu.utils.db;

import javax.sql.DataSource;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

@Component
public class DBConnector {

	 private JdbcTemplate jdbcTemplate;

	 @Autowired
	 public void setDataSource(DataSource dataSource) {
		 this.jdbcTemplate = new JdbcTemplate(dataSource);
	 }

	 public void insertData(String query,Object inputs[]){
		 jdbcTemplate.update(query,inputs);
	 }
	 
	 public void createTable(String query){
		  
		 jdbcTemplate.execute(query);
	 }
	 
	 public JdbcTemplate getJDBCTemplate(){
		 return this.jdbcTemplate;
	 }
}
