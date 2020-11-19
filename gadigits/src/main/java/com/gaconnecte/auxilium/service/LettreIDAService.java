package com.gaconnecte.auxilium.service;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.gaconnecte.auxilium.domain.SinisterPec;
import com.gaconnecte.auxilium.service.dto.PrestationPECDTO;
import com.gaconnecte.auxilium.service.dto.QuotationDTO;
import com.gaconnecte.auxilium.service.dto.SinisterPecDTO;

import net.sf.jasperreports.engine.JasperPrint;

public interface LettreIDAService {

	Map<String, Object> infosToSendLetterIDAReport(SinisterPecDTO sinisterPecDTO);

	void generateQuotationPdf(HttpServletResponse response, QuotationDTO quotationPdf, Long pecId);
	
	public void generateLetterIDA(HttpServletResponse response, SinisterPecDTO sinisterPecDTO);
	
	JasperPrint multipageLinking(JasperPrint page1, JasperPrint page2);
	
	
	
}
