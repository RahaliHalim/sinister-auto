package com.gaconnecte.auxilium.service;

import java.util.List;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gaconnecte.auxilium.service.dto.DossiersDTO;
import com.gaconnecte.auxilium.service.dto.RechercheDTO;
import com.gaconnecte.auxilium.service.dto.SearchDTO;

public interface DossiersService {
	
	 /**
     *  Get all the dossiers.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
	List<DossiersDTO> findAll();
	 List<DossiersDTO> findAll(Long idUser);

    /** public 
     *  Get the "id" dossier.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    DossiersDTO findOne(Long id);
    
	 List<DossiersDTO> Search(SearchDTO searchDTO);
	 List<DossiersDTO> findAll(SearchDTO searchDTO,Long idUser);
}
