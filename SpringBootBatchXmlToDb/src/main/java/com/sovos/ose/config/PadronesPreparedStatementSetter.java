package com.sovos.ose.config;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.sovos.ose.model.Padrones;


public class PadronesPreparedStatementSetter implements ItemPreparedStatementSetter<Padrones> {
//	@Autowired
//	public DataSource dataSource;
	
	@Override
	public void setValues(Padrones padrones, PreparedStatement ps) throws SQLException {
		
//		  org.springframework.jdbc.core.JdbcTemplate jdbcTemplateObject = new JdbcTemplate(dataSource);
//		  Long seq; 
//		  String sql = "SELECTSELECT nextval('hibernate_sequence')";
//		  seq = jdbcTemplateObject.queryForObject(sql, new Object[] {}, Long.class);
//		ps.setFloat(1, seq);  
		ps.setString(1, padrones.getTaxpayerid());
		ps.setInt(2, padrones.getType());
	}


}
