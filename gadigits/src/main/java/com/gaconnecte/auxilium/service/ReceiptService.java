package com.gaconnecte.auxilium.service;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.gaconnecte.auxilium.service.dto.SinisterPecDTO;

import net.sf.jasperreports.engine.JasperPrint;

public interface ReceiptService {
	
	Map<String, Object> infosToSendBonSortieReport(SinisterPecDTO sinisterPecDTO);
	
	Map<String, Object> infosToOrdreMissionExpertReport(SinisterPecDTO sinisterPecDTO);
	
	public void generateBonSortie(HttpServletResponse response, SinisterPecDTO sinisterPecDTO);
	
	public void generateOrdreMissionExpert(HttpServletResponse response, SinisterPecDTO sinisterPecDTO);
	public JasperPrint multipageLinking(JasperPrint page1, JasperPrint page2);

}
