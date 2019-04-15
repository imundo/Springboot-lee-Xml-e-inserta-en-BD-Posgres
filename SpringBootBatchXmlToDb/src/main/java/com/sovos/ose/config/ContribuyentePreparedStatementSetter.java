package com.sovos.ose.config;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.batch.item.database.ItemPreparedStatementSetter;

import com.sovos.ose.model.Contribuyente;

public class ContribuyentePreparedStatementSetter implements ItemPreparedStatementSetter<Contribuyente> {

	@Override
	public void setValues(Contribuyente contribuyente, PreparedStatement ps) throws SQLException {
		ps.setString(1, contribuyente.getTaxpayerid());
		ps.setInt(2, contribuyente.getStatus());
		ps.setInt(3, contribuyente.getCondition());
	}
	
}
