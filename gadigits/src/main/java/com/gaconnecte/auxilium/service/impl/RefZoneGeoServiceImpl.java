package com.gaconnecte.auxilium.service.impl;

import com.gaconnecte.auxilium.service.RefZoneGeoService;
import com.gaconnecte.auxilium.domain.RefZoneGeo;
import com.gaconnecte.auxilium.repository.RefZoneGeoRepository;
import com.gaconnecte.auxilium.repository.search.RefZoneGeoSearchRepository;
import com.gaconnecte.auxilium.service.dto.RefZoneGeoDTO;
import com.gaconnecte.auxilium.service.mapper.RefZoneGeoMapper;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



/**
 * Service Implementation for managing RefZoneGeo.
 */
@Service
@Transactional
public class RefZoneGeoServiceImpl implements RefZoneGeoService{

    private final Logger log = LoggerFactory.getLogger(RefZoneGeoServiceImpl.class);

    private final RefZoneGeoRepository refZoneGeoRepository;

    private final RefZoneGeoMapper refZoneGeoMapper;

    private final RefZoneGeoSearchRepository refZoneGeoSearchRepository;

    public RefZoneGeoServiceImpl(RefZoneGeoRepository refZoneGeoRepository, RefZoneGeoMapper refZoneGeoMapper, RefZoneGeoSearchRepository refZoneGeoSearchRepository) {
        this.refZoneGeoRepository = refZoneGeoRepository;
        this.refZoneGeoMapper = refZoneGeoMapper;
        this.refZoneGeoSearchRepository = refZoneGeoSearchRepository;
    }

    /**
     * Save a refZoneGeo.
     *
     * @param refZoneGeoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RefZoneGeoDTO save(RefZoneGeoDTO refZoneGeoDTO) {
        log.debug("Request to save RefZoneGeo : {}", refZoneGeoDTO);
        RefZoneGeo refZoneGeo = refZoneGeoMapper.toEntity(refZoneGeoDTO);
        refZoneGeo = refZoneGeoRepository.save(refZoneGeo);
        RefZoneGeoDTO result = refZoneGeoMapper.toDto(refZoneGeo);
        refZoneGeoSearchRepository.save(refZoneGeo);
        return result;
    }

    /**
     *  Get all the refZoneGeos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RefZoneGeoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RefZoneGeos");
        return refZoneGeoRepository.findAll(pageable)
            .map(refZoneGeoMapper::toDto);
    }

    /**
     *  Get one refZoneGeo by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public RefZoneGeoDTO findOne(Long id) {
        log.debug("Request to get RefZoneGeo : {}", id);
        RefZoneGeo refZoneGeo = refZoneGeoRepository.findOneWithEagerRelationships(id);
        return refZoneGeoMapper.toDto(refZoneGeo);
    }

    /**
     *  Delete the  refZoneGeo by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RefZoneGeo : {}", id);
        refZoneGeoRepository.delete(id);
        refZoneGeoSearchRepository.delete(id);
    }

    /**
     * Search for the refZoneGeo corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RefZoneGeoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of RefZoneGeos for query {}", query);
        Page<RefZoneGeo> result = refZoneGeoSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(refZoneGeoMapper::toDto);
    }
}
