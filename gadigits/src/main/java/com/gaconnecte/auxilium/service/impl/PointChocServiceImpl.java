package com.gaconnecte.auxilium.service.impl;

import com.gaconnecte.auxilium.service.PointChocService;
import com.gaconnecte.auxilium.domain.PointChoc;
import com.gaconnecte.auxilium.repository.PointChocRepository;
import com.gaconnecte.auxilium.repository.search.PointChocSearchRepository;
import com.gaconnecte.auxilium.service.dto.PointChocDTO;
import com.gaconnecte.auxilium.service.mapper.PointChocMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing PointChoc.
 */
@Service
@Transactional
public class PointChocServiceImpl implements PointChocService{

    private final Logger log = LoggerFactory.getLogger(PointChocServiceImpl.class);

    private final PointChocRepository pointChocRepository;

    private final PointChocMapper pointChocMapper;

    private final PointChocSearchRepository pointChocSearchRepository;

    public PointChocServiceImpl(PointChocRepository pointChocRepository, PointChocMapper pointChocMapper, PointChocSearchRepository pointChocSearchRepository) {
        this.pointChocRepository = pointChocRepository;
        this.pointChocMapper = pointChocMapper;
        this.pointChocSearchRepository = pointChocSearchRepository;
    }

    /**
     * Save a pointChoc.
     *
     * @param pointChocDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PointChocDTO save(PointChocDTO pointChocDTO) {
        log.debug("Request to save PointChoc : {}", pointChocDTO);
        PointChoc pointChoc = pointChocMapper.toEntity(pointChocDTO);
        pointChoc = pointChocRepository.save(pointChoc);
        PointChocDTO result = pointChocMapper.toDto(pointChoc);
        pointChocSearchRepository.save(pointChoc);
        return result;
    }

    /**
     *  Get all the pointChocs.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PointChocDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PointChocs");
        return pointChocRepository.findAll(pageable)
            .map(pointChocMapper::toDto);
    }

    /**
     *  Get one pointChoc by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public PointChocDTO findOne(Long id) {
        log.debug("Request to get PointChoc : {}", id);
        PointChoc pointChoc = pointChocRepository.findOne(id);
        return pointChocMapper.toDto(pointChoc);
    }

    /**
     *  Delete the  pointChoc by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PointChoc : {}", id);
        pointChocRepository.delete(id);
        pointChocSearchRepository.delete(id);
    }

    /**
     * Search for the pointChoc corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PointChocDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of PointChocs for query {}", query);
        Page<PointChoc> result = pointChocSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(pointChocMapper::toDto);
    }
}
