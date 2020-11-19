package com.gaconnecte.auxilium.service;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.gaconnecte.auxilium.service.dto.PrestationPECDTO;
import com.gaconnecte.auxilium.service.dto.SinisterPecDTO;

import net.sf.jasperreports.engine.JasperPrint;

public interface LettreOuvertureService {
	
	
    Map<String, Object> infosToSendLetterOuvertureReport(SinisterPecDTO sinisterPecDTO);
	    
    public void generateLetterOuverture(HttpServletResponse response, SinisterPecDTO sinisterPecDTO);
    
    JasperPrint multipageLinking(JasperPrint page1, JasperPrint page2);

}
