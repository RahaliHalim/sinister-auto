package com.gaconnecte.auxilium.web.rest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gaconnecte.auxilium.drools.FactDevis;
import com.gaconnecte.auxilium.drools.Product;
import com.gaconnecte.auxilium.service.DroolsService;

@RestController
public class DroolsController {

	private final DroolsService droolsService;

	@Autowired
	public DroolsController(DroolsService droolsService) {
		this.droolsService = droolsService;
	}

	@RequestMapping(value = "/insured-participation", method = RequestMethod.GET, produces = "application/json")
	public FactDevis getInsuredParticipation() {

		Product product = new Product();
		product.setType("gold");
		FactDevis devis = new FactDevis();
		//devis.setModeId(1);
		devis.setDroitTimbre(25.253F);
		droolsService.getInsuredParticipation(devis);
		return devis;
	}
}