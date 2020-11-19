package com.gaconnecte.auxilium.service;

import java.time.LocalDate;
import java.time.Month;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;

import com.gaconnecte.auxilium.service.dto.PriseEnChargeDTO;
import com.gaconnecte.auxilium.service.dto.RechercheDTO;
import com.gaconnecte.auxilium.service.dto.SearchDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

public interface PriseEnChargeService {

    /**
     * Get all the PriseEnCharge.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    List<PriseEnChargeDTO> findAll();

    /**
     * Get all the PriseEnCharge.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    List<PriseEnChargeDTO> findAll(Long idUser);

    public Page<PriseEnChargeDTO> findAll(Long idUser,String filter, Pageable pageable);

    public Long getCountPecWithFiltter(Long idUser,String filter);

    public Long getCountPec();

    /**
     * Get the "id" PriseEnCharge.
     *
     * @param id the id of the entity
     * @return the entity
     */
    PriseEnChargeDTO findOne(Long id);

   // Set<PriseEnChargeDTO> findPriseEnCharge(RechercheDTO rechercheDTO);

   
    List<PriseEnChargeDTO> Search(SearchDTO searchDTO);
      
    List<PriseEnChargeDTO> Search(SearchDTO searchDTO,Long idUser);
}
