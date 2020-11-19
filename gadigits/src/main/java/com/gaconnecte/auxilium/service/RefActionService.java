package com.gaconnecte.auxilium.service;


import com.gaconnecte.auxilium.service.dto.RefActionDTO;
import java.util.List;

public interface RefActionService {
	
	RefActionDTO save(RefActionDTO refActionDTO);
	
	List<RefActionDTO> findAll();
	
	RefActionDTO findOne(Long id);
	
	void delete(Long id);

}
