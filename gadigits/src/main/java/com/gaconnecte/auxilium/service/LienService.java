package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.service.dto.LienDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Lien.
 */
public interface LienService {

    /**
     * Save a lien.
     *
     * @param lienDTO the entity to save
     * @return the persisted entity
     */
    LienDTO save(LienDTO lienDTO);

    /**
     *  Get all the liens.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<LienDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" lien.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    LienDTO findOne(Long id);

    /**
     *  Delete the "id" lien.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    List<LienDTO> findLienByUser(Long parentId);

    List<LienDTO> findLienWithoutParent();
}
