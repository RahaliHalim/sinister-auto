package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.drools.FactDevis;
import com.gaconnecte.auxilium.service.dto.AccordPriseChargeDTO;
import com.gaconnecte.auxilium.service.dto.SinisterPecDTO;

public interface DroolsService {

	
	public FactDevis getDataFromDevis(SinisterPecDTO accordPriseChargeDTO, Long id);
	public FactDevis getInsuredParticipation(FactDevis devis);
}