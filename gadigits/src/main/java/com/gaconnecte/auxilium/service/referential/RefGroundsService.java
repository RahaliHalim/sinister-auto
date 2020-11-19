package com.gaconnecte.auxilium.service.referential;

import com.gaconnecte.auxilium.service.referential.dto.RefGroundsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import java.util.Set;

/**
 * Service Interface for managing RefGrounds.
 */
public interface RefGroundsService {

    /**
     * Save a refGrounds.
     *
     * @param refGroundsDTO the entity to save
     * @return the persisted entity
     */
    RefGroundsDTO save(RefGroundsDTO refGroundsDTO);

    /**
     *  Get all the refGrounds.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<RefGroundsDTO> findAll(Pageable pageable);

    /**
     * Get all the refGrounds by id status.
     *
     *  @param id 
     *  @return the list of entities
     */
    Set<RefGroundsDTO> findByStatus(Long id);

    /**
     *  Get the "id" refGrounds.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    RefGroundsDTO findOne(Long id);

    /**
     *  Delete the "id" refGrounds.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

}
