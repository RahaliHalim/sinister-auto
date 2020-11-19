package com.gaconnecte.auxilium.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.gaconnecte.auxilium.service.dto.RefNaturePanneDTO;

public interface RefNaturePanneService {
	
	List<RefNaturePanneDTO> findAll();
	
	RefNaturePanneDTO findOne(Long id);
	
	RefNaturePanneDTO save(RefNaturePanneDTO refNaturePanneDTO);
	
	void delete(Long id);
	
	RefNaturePanneDTO findByLabel(String label);
	List<RefNaturePanneDTO> findByActive();

}
