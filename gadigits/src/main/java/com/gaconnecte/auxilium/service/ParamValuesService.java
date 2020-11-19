package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.service.dto.ParamsValueDroolsDTO;

public interface ParamValuesService {

	
	ParamsValueDroolsDTO findByCompagnieIdParamIdGarantieId(Long compagnieId, Long paramId, Long garantieId);
}
