package com.gaconnecte.auxilium.service.impl;

import com.gaconnecte.auxilium.domain.Partner;
import com.gaconnecte.auxilium.service.RefModeGestionService;
import com.gaconnecte.auxilium.domain.RefModeGestion;
import com.gaconnecte.auxilium.repository.PartnerRepository;
import com.gaconnecte.auxilium.repository.RefModeGestionRepository;
import com.gaconnecte.auxilium.repository.search.RefModeGestionSearchRepository;
import com.gaconnecte.auxilium.service.dto.RefModeGestionDTO;
import com.gaconnecte.auxilium.service.mapper.RefModeGestionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.HashSet;
import java.util.Set;

import static org.elasticsearch.index.query.QueryBuilders.*;


@Service
@Transactional
public class RefModeGestionServiceImpl implements RefModeGestionService{

    private final Logger log = LoggerFactory.getLogger(RefModeGestionServiceImpl.class);

    private final RefModeGestionRepository refModeGestionRepository;

    private final RefModeGestionMapper refModeGestionMapper;

    private final RefModeGestionSearchRepository refModeGestionSearchRepository;

    private final PartnerRepository partnerRepository;

    public RefModeGestionServiceImpl(RefModeGestionRepository refModeGestionRepository, RefModeGestionMapper refModeGestionMapper, 
            RefModeGestionSearchRepository refModeGestionSearchRepository, PartnerRepository partnerRepository) {
        this.refModeGestionRepository = refModeGestionRepository;
        this.refModeGestionMapper = refModeGestionMapper;
        this.refModeGestionSearchRepository = refModeGestionSearchRepository;
        this.partnerRepository = partnerRepository;
    }

    /**
     * Save a refModeGestion.
     *
     * @param refModeGestionDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RefModeGestionDTO save(RefModeGestionDTO refModeGestionDTO) {
        log.debug("Request to save RefModeGestion : {}", refModeGestionDTO);
        RefModeGestion refModeGestion = refModeGestionMapper.toEntity(refModeGestionDTO);
        refModeGestion = refModeGestionRepository.save(refModeGestion);
        RefModeGestionDTO result = refModeGestionMapper.toDto(refModeGestion);
        refModeGestionSearchRepository.save(refModeGestion);
        return result;
    }

    /**
     *  Get all the refModeGestions.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RefModeGestionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RefModeGestions");
        return refModeGestionRepository.findAll(pageable)
            .map(refModeGestionMapper::toDto);
    }

    /**
     *  Get one refModeGestion by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public RefModeGestionDTO findOne(Long id) {
        log.debug("Request to get RefModeGestion : {}", id);
        RefModeGestion refModeGestion = refModeGestionRepository.findOne(id);
        return refModeGestionMapper.toDto(refModeGestion);
    }

    /**
     *  Delete the  refModeGestion by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RefModeGestion : {}", id);
        refModeGestionRepository.delete(id);
        refModeGestionSearchRepository.delete(id);
    }

    /**
     * Search for the refModeGestion corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RefModeGestionDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of RefModeGestions for query {}", query);
        Page<RefModeGestion> result = refModeGestionSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(refModeGestionMapper::toDto);
    }

    /**
     * Search for the mode gestion list corresponding to the query.
     *
     *  @param clientId the client id
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Set<RefModeGestionDTO> findModeGestionListByClient(Long clientId) {
        log.debug("Request to search for a set of RefModeGestion for client ");
        Set<RefModeGestionDTO> dtos = new HashSet<>();
        Partner client = partnerRepository.findOne(clientId);
        if(client != null){
            // TODO: correct this
        }
        return dtos;
    }

}
