package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.service.dto.ObservationApecDTO;

public interface ObservationApecService {
	
	ObservationApecDTO save(ObservationApecDTO observationApecDTO);
	
	ObservationApecDTO findOne(Long id);
	
	ObservationApecDTO findOneByApecId(Long id);
	
	void delete(Long id);

}
