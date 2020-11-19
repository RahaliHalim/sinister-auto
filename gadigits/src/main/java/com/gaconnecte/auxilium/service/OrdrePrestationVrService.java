package com.gaconnecte.auxilium.service;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.gaconnecte.auxilium.service.dto.SinisterPrestationDTO;


public interface OrdrePrestationVrService {

	Map<String, Object> infosToSendOrdrePrestationVr(SinisterPrestationDTO sinisterPrestationDTO);
	
	void generateOrdrePrestationVr(HttpServletResponse response, Long sinisterPrestationId);
	
}
