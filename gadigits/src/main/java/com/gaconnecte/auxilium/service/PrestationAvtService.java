package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.service.dto.PrestationAvtDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import com.gaconnecte.auxilium.domain.enumeration.TypePrestation;


/**
 * Service Interface for managing PrestationAvt.
 */
public interface PrestationAvtService {

    /**
     * Save a prestationAvt.
     *
     * @param prestationAvtDTO the entity to save
     * @return the persisted entity
     */
    PrestationAvtDTO save(PrestationAvtDTO prestationAvtDTO);

    /**
     *  Get all the prestationAvts.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PrestationAvtDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" prestationAvt.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PrestationAvtDTO findOne(Long id);

    /**
     *  Delete the "id" prestationAvt.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);


 /**
     *  Get the  prestationAvt.
     *
     *  @param typePrestation the type of the entity
     *  @return the entity
     */
    List<PrestationAvtDTO> findAllPrestationByType(TypePrestation typePrestation);

    PrestationAvtDTO findByPrestation( Long prestationId);
    
    

    /**
     * Get count of avt prestation
     * @return the number of avt prestation
     */
    Long getCountPrestationAvt(String debut, String fin);

    PrestationAvtDTO annulerPrestation(Long id);
}
