package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.service.dto.VisAVisDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Contact.
 */
public interface VisAVisService {

    /**
     * Save a contact.
     *
     * @param contactDTO the entity to save
     * @return the persisted entity
     */
    VisAVisDTO save(VisAVisDTO visAVisDTO);

    /**
     *  Get all the contacts.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<VisAVisDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" contact.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    VisAVisDTO findOne(Long id);

    /**
     *  Delete the "id" contact.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the contact corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<VisAVisDTO> search(String query, Pageable pageable);
   
    
  

}
