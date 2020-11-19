package com.gaconnecte.auxilium.service.impl;

import com.gaconnecte.auxilium.service.PersonnePhysiqueService;
import com.gaconnecte.auxilium.domain.PersonnePhysique;
import com.gaconnecte.auxilium.repository.PersonnePhysiqueRepository;
import com.gaconnecte.auxilium.repository.search.PersonnePhysiqueSearchRepository;
import com.gaconnecte.auxilium.service.dto.PersonnePhysiqueDTO;
import com.gaconnecte.auxilium.service.mapper.PersonnePhysiqueMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing PersonnePhysique.
 */
@Service
@Transactional
public class PersonnePhysiqueServiceImpl implements PersonnePhysiqueService{

    private final Logger log = LoggerFactory.getLogger(PersonnePhysiqueServiceImpl.class);

    private final PersonnePhysiqueRepository personnePhysiqueRepository;

    private final PersonnePhysiqueMapper personnePhysiqueMapper;

    private final PersonnePhysiqueSearchRepository personnePhysiqueSearchRepository;

    public PersonnePhysiqueServiceImpl(PersonnePhysiqueRepository personnePhysiqueRepository, PersonnePhysiqueMapper personnePhysiqueMapper, PersonnePhysiqueSearchRepository personnePhysiqueSearchRepository) {
        this.personnePhysiqueRepository = personnePhysiqueRepository;
        this.personnePhysiqueMapper = personnePhysiqueMapper;
        this.personnePhysiqueSearchRepository = personnePhysiqueSearchRepository;
    }

    /**
     * Save a personnePhysique.
     *
     * @param personnePhysiqueDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PersonnePhysiqueDTO save(PersonnePhysiqueDTO personnePhysiqueDTO) {
        log.debug("Request to save PersonnePhysique : {}", personnePhysiqueDTO);
        PersonnePhysique personnePhysique = personnePhysiqueMapper.toEntity(personnePhysiqueDTO);
        personnePhysique = personnePhysiqueRepository.save(personnePhysique);
        PersonnePhysiqueDTO result = personnePhysiqueMapper.toDto(personnePhysique);
        personnePhysiqueSearchRepository.save(personnePhysique);
        return result;
    }

    /**
     *  Get all the personnePhysiques.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PersonnePhysiqueDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PersonnePhysiques");
        return personnePhysiqueRepository.findAll(pageable)
            .map(personnePhysiqueMapper::toDto);
    }

    /**
     *  Get one personnePhysique by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public PersonnePhysiqueDTO findOne(Long id) {
        log.debug("Request to get PersonnePhysique : {}", id);
        PersonnePhysique personnePhysique = personnePhysiqueRepository.findOne(id);
        return personnePhysiqueMapper.toDto(personnePhysique);
    }

    /**
     *  Delete the  personnePhysique by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PersonnePhysique : {}", id);
        personnePhysiqueRepository.delete(id);
        personnePhysiqueSearchRepository.delete(id);
    }

    /**
     * Search for the personnePhysique corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PersonnePhysiqueDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of PersonnePhysiques for query {}", query);
        Page<PersonnePhysique> result = personnePhysiqueSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(personnePhysiqueMapper::toDto);
    }
}
