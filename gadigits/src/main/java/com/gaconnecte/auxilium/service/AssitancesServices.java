package com.gaconnecte.auxilium.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gaconnecte.auxilium.service.dto.AssitancesDTO;
import com.gaconnecte.auxilium.service.dto.RechercheDTO;
import com.gaconnecte.auxilium.service.dto.SearchDTO;

public interface AssitancesServices {
	/**
     *  Get all the Assitances.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
	List<AssitancesDTO> findAll(Long idUser);
	Page<AssitancesDTO> findAll(String filter, LocalDate startDate, LocalDate endDate, Pageable pageable,String immatriculation, String reference, Long statusId, Long partnerId, Long agencyId, boolean vr);

    /**
     *  Get the "id" Assitances.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
	AssitancesDTO findOne(Long id);
	
	 List<AssitancesDTO> Search(SearchDTO searchDTO);
	 List<AssitancesDTO> Search(SearchDTO searchDTO,Long idUser);
     //Page<AssitancesDTO> SearchView(SearchDTO searchDTO, Long idUser, Pageable pageable) ;

	 Long getCountAssitancesWithFiltter(String filter, LocalDate startDate, LocalDate endDate, String immatriculation, String reference, Long partnerId, Long statutId, Long agencyId, boolean vr);

	 Long getCountAssitances(LocalDate startDate, LocalDate endDate, String immatriculation, String reference, Long partnerId, Long statutId, Long agencyId, boolean vr);
}
