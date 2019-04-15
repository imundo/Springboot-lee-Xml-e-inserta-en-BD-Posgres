package com.sovos.ose.processor;

import org.springframework.batch.item.ItemProcessor;

import com.sovos.ose.model.Contribuyente;

public class ContribuyenteItenProcessor implements ItemProcessor<Contribuyente, Contribuyente>{

	@Override
	public Contribuyente process(Contribuyente contribuyente) throws Exception {
		return contribuyente;
	}
}
