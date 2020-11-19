package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.service.dto.DetailsMoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing DetailsMo.
 */
public interface DetailsMoService {

    /**
     * Save a detailsMo.
     *
     * @param detailsMoDTO the entity to save
     * @return the persisted entity
     */
    DetailsMoDTO save(DetailsMoDTO detailsMoDTO);

    /**
     *  Get all the detailsMos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<DetailsMoDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" detailsMo.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    DetailsMoDTO findOne(Long id);

    /**
     *  Delete the "id" detailsMo.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the detailsMo corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<DetailsMoDTO> search(String query, Pageable pageable);

    Page<DetailsMoDTO> findByDevis(Pageable pageable, Long devisId);
}
