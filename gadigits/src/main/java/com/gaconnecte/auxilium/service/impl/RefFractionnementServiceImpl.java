package com.gaconnecte.auxilium.service.impl;

import com.gaconnecte.auxilium.service.RefFractionnementService;
import com.gaconnecte.auxilium.domain.RefFractionnement;
import com.gaconnecte.auxilium.repository.RefFractionnementRepository;
import com.gaconnecte.auxilium.repository.search.RefFractionnementSearchRepository;
import com.gaconnecte.auxilium.service.dto.RefFractionnementDTO;
import com.gaconnecte.auxilium.service.mapper.RefFractionnementMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing RefFractionnement.
 */
@Service
@Transactional
public class RefFractionnementServiceImpl implements RefFractionnementService{

    private final Logger log = LoggerFactory.getLogger(RefFractionnementServiceImpl.class);

    private final RefFractionnementRepository refFractionnementRepository;

    private final RefFractionnementMapper refFractionnementMapper;

    private final RefFractionnementSearchRepository refFractionnementSearchRepository;

    public RefFractionnementServiceImpl(RefFractionnementRepository refFractionnementRepository, RefFractionnementMapper refFractionnementMapper, RefFractionnementSearchRepository refFractionnementSearchRepository) {
        this.refFractionnementRepository = refFractionnementRepository;
        this.refFractionnementMapper = refFractionnementMapper;
        this.refFractionnementSearchRepository = refFractionnementSearchRepository;
    }

    /**
     * Save a refFractionnement.
     *
     * @param refFractionnementDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RefFractionnementDTO save(RefFractionnementDTO refFractionnementDTO) {
        log.debug("Request to save RefFractionnement : {}", refFractionnementDTO);
        RefFractionnement refFractionnement = refFractionnementMapper.toEntity(refFractionnementDTO);
        refFractionnement = refFractionnementRepository.save(refFractionnement);
        RefFractionnementDTO result = refFractionnementMapper.toDto(refFractionnement);
        refFractionnementSearchRepository.save(refFractionnement);
        return result;
    }

    /**
     *  Get all the refFractionnements.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RefFractionnementDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RefFractionnements");
        return refFractionnementRepository.findAll(pageable)
            .map(refFractionnementMapper::toDto);
    }

    /**
     *  Get one refFractionnement by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public RefFractionnementDTO findOne(Long id) {
        log.debug("Request to get RefFractionnement : {}", id);
        RefFractionnement refFractionnement = refFractionnementRepository.findOne(id);
        return refFractionnementMapper.toDto(refFractionnement);
    }

    /**
     *  Delete the  refFractionnement by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RefFractionnement : {}", id);
        refFractionnementRepository.delete(id);
        refFractionnementSearchRepository.delete(id);
    }

    /**
     * Search for the refFractionnement corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RefFractionnementDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of RefFractionnements for query {}", query);
        Page<RefFractionnement> result = refFractionnementSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(refFractionnementMapper::toDto);
    }
}
