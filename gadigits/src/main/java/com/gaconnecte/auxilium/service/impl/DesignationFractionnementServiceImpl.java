package com.gaconnecte.auxilium.service.impl;

import com.gaconnecte.auxilium.service.DesignationFractionnementService;
import com.gaconnecte.auxilium.domain.DesignationFractionnement;
import com.gaconnecte.auxilium.repository.DesignationFractionnementRepository;
import com.gaconnecte.auxilium.repository.search.DesignationFractionnementSearchRepository;
import com.gaconnecte.auxilium.service.dto.DesignationFractionnementDTO;
import com.gaconnecte.auxilium.service.mapper.DesignationFractionnementMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing DesignationFractionnement.
 */
@Service
@Transactional
public class DesignationFractionnementServiceImpl implements DesignationFractionnementService{

    private final Logger log = LoggerFactory.getLogger(DesignationFractionnementServiceImpl.class);

    private final DesignationFractionnementRepository designationFractionnementRepository;

    private final DesignationFractionnementMapper designationFractionnementMapper;

    private final DesignationFractionnementSearchRepository designationFractionnementSearchRepository;

    public DesignationFractionnementServiceImpl(DesignationFractionnementRepository designationFractionnementRepository, DesignationFractionnementMapper designationFractionnementMapper, DesignationFractionnementSearchRepository designationFractionnementSearchRepository) {
        this.designationFractionnementRepository = designationFractionnementRepository;
        this.designationFractionnementMapper = designationFractionnementMapper;
        this.designationFractionnementSearchRepository = designationFractionnementSearchRepository;
    }

    /**
     * Save a designationFractionnement.
     *
     * @param designationFractionnementDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public DesignationFractionnementDTO save(DesignationFractionnementDTO designationFractionnementDTO) {
        log.debug("Request to save DesignationFractionnement : {}", designationFractionnementDTO);
        DesignationFractionnement designationFractionnement = designationFractionnementMapper.toEntity(designationFractionnementDTO);
        designationFractionnement = designationFractionnementRepository.save(designationFractionnement);
        DesignationFractionnementDTO result = designationFractionnementMapper.toDto(designationFractionnement);
        designationFractionnementSearchRepository.save(designationFractionnement);
        return result;
    }

    /**
     *  Get all the designationFractionnements.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DesignationFractionnementDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DesignationFractionnements");
        return designationFractionnementRepository.findAll(pageable)
            .map(designationFractionnementMapper::toDto);
    }

    /**
     *  Get one designationFractionnement by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public DesignationFractionnementDTO findOne(Long id) {
        log.debug("Request to get DesignationFractionnement : {}", id);
        DesignationFractionnement designationFractionnement = designationFractionnementRepository.findOne(id);
        return designationFractionnementMapper.toDto(designationFractionnement);
    }

    /**
     *  Delete the  designationFractionnement by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DesignationFractionnement : {}", id);
        designationFractionnementRepository.delete(id);
        designationFractionnementSearchRepository.delete(id);
    }

    /**
     * Search for the designationFractionnement corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DesignationFractionnementDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of DesignationFractionnements for query {}", query);
        Page<DesignationFractionnement> result = designationFractionnementSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(designationFractionnementMapper::toDto);
    }
}
