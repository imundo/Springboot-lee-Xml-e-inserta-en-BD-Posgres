package com.sovos.ose.processor;

import org.springframework.batch.item.ItemProcessor;

import com.sovos.ose.model.Padrones;;

public class PadronesItenProcessor implements ItemProcessor<Padrones, Padrones>{

	@Override
	public Padrones process(Padrones padrones) throws Exception {
		return padrones;
	}

}
