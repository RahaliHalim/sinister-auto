package com.gaconnecte.auxilium.service;
import com.gaconnecte.auxilium.service.dto.RefTugTruckDTO;
import com.gaconnecte.auxilium.service.dto.RefTarifDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing RefTarif.
 */
public interface RefTarifService {

    /**
     * Save a refTarif.
     *
     * @param refTarifDTO the entity to save
     * @return the persisted entity
     */
    RefTarifDTO save(RefTarifDTO tarifDTO);

    /**
     *  Get all the refTarifs.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<RefTarifDTO> findAll(Pageable pageable);
    Page<RefTarifDTO> findByTarifLine(Pageable pageable, Long id);
    /**
     *  Get the "id" refTarif.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    RefTarifDTO findOne(Long id);

    /**
     *  Delete the "id" refTarif.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

}
