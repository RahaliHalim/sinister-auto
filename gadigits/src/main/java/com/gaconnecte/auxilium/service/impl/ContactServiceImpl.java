package com.gaconnecte.auxilium.service.impl;

import com.gaconnecte.auxilium.service.ContactService;
import com.gaconnecte.auxilium.domain.Contact;
import com.gaconnecte.auxilium.repository.ContactRepository;
import com.gaconnecte.auxilium.repository.search.ContactSearchRepository;
import com.gaconnecte.auxilium.service.dto.ContactDTO;
import com.gaconnecte.auxilium.service.mapper.ContactMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Contact.
 */
@Service
@Transactional
public class ContactServiceImpl implements ContactService{

    private final Logger log = LoggerFactory.getLogger(ContactServiceImpl.class);

    private final ContactRepository contactRepository;

    private final ContactMapper contactMapper;

    private final ContactSearchRepository contactSearchRepository;

    public ContactServiceImpl(ContactRepository contactRepository, ContactMapper contactMapper, ContactSearchRepository contactSearchRepository) {
        this.contactRepository = contactRepository;
        this.contactMapper = contactMapper;
        this.contactSearchRepository = contactSearchRepository;
    }

    /**
     * Save a contact.
     *
     * @param contactDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ContactDTO save(ContactDTO contactDTO) {
        log.debug("Request to save Contact : {}", contactDTO);
        Contact contact = contactMapper.toEntity(contactDTO);
        contact = contactRepository.save(contact);
        ContactDTO result = contactMapper.toDto(contact);
        contactSearchRepository.save(contact);
        return result;
    }

    /**
     *  Get all the contacts.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ContactDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Contacts");
        return contactRepository.findAll(pageable)
            .map(contactMapper::toDto);
    }

    /**
     *  Get one contact by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ContactDTO findOne(Long id) {
        log.debug("Request to get Contact : {}", id);
        Contact contact = contactRepository.findOne(id);
        return contactMapper.toDto(contact);
    }

    /**
     *  Get one contact by personneMoraleId.
     *
     *  @param personneMoraleId the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ContactDTO> findByPersonneMorale(Pageable pageable, Long personneMoraleId) {
        log.debug("Request to get Contact : {}", personneMoraleId);
        Page<Contact> contacts = contactRepository.findContactsByPersonneMorale(pageable, personneMoraleId);
        return contacts.map(contactMapper::toDto);
         
    }
    /**
     *  Get the main  contact by personneMoraleId.
     *
     *  @param personneMoraleId the id of the entity
     *  @return the entity
     */
     @Override
     @Transactional(readOnly = true)
     public ContactDTO findMainContact(Long personneMoraleId) {
         log.debug("Request to get the main Contact : {}", personneMoraleId);
         Contact contact = contactRepository.findMainContactGerantByPersonneMoraleId(personneMoraleId);
         log.debug("abd : {}", contact);
         if (contact == null) {
        	 contact = contactRepository.findMainContactNonGerantByPersonneMoraleId(personneMoraleId);
         }
         return contactMapper.toDto(contact);
     }
         

    /**
     *  Delete the  contact by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Contact : {}", id);
        contactRepository.delete(id);
        contactSearchRepository.delete(id);
    }

    /**
     * Search for the contact corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ContactDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Contacts for query {}", query);
        Page<Contact> result = contactSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(contactMapper::toDto);
    }

    
}
