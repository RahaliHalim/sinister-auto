package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.service.dto.ContactDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Contact.
 */
public interface ContactService {

    /**
     * Save a contact.
     *
     * @param contactDTO the entity to save
     * @return the persisted entity
     */
    ContactDTO save(ContactDTO contactDTO);

    /**
     *  Get all the contacts.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ContactDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" contact.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ContactDTO findOne(Long id);

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
    Page<ContactDTO> search(String query, Pageable pageable);
    Page<ContactDTO> findByPersonneMorale(Pageable pageable, Long id);
    
    /**
     *  Get the "id" of a Personne Morale and return the main Contact.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ContactDTO findMainContact(Long personneMoraleId);
}
