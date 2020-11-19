package com.gaconnecte.auxilium.service.impl;

import com.gaconnecte.auxilium.service.AvisExpertMoService;
import com.gaconnecte.auxilium.domain.AvisExpertMo;
import com.gaconnecte.auxilium.repository.AvisExpertMoRepository;
import com.gaconnecte.auxilium.repository.search.AvisExpertMoSearchRepository;
import com.gaconnecte.auxilium.service.dto.AvisExpertMoDTO;
import com.gaconnecte.auxilium.service.mapper.AvisExpertMoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing AvisExpertMo.
 */
@Service
@Transactional
public class AvisExpertMoServiceImpl implements AvisExpertMoService{

    private final Logger log = LoggerFactory.getLogger(AvisExpertMoServiceImpl.class);

    private final AvisExpertMoRepository avisExpertMoRepository;

    private final AvisExpertMoMapper avisExpertMoMapper;

    private final AvisExpertMoSearchRepository avisExpertMoSearchRepository;

    public AvisExpertMoServiceImpl(AvisExpertMoRepository avisExpertMoRepository, AvisExpertMoMapper avisExpertMoMapper, AvisExpertMoSearchRepository avisExpertMoSearchRepository) {
        this.avisExpertMoRepository = avisExpertMoRepository;
        this.avisExpertMoMapper = avisExpertMoMapper;
        this.avisExpertMoSearchRepository = avisExpertMoSearchRepository;
    }

    /**
     * Save a avisExpertMo.
     *
     * @param avisExpertMoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AvisExpertMoDTO save(AvisExpertMoDTO avisExpertMoDTO) {
        log.debug("Request to save AvisExpertMo : {}", avisExpertMoDTO);
        AvisExpertMo avisExpertMo = avisExpertMoMapper.toEntity(avisExpertMoDTO);
        avisExpertMo = avisExpertMoRepository.save(avisExpertMo);
        AvisExpertMoDTO result = avisExpertMoMapper.toDto(avisExpertMo);
        avisExpertMoSearchRepository.save(avisExpertMo);
        return result;
    }

    /**
     *  Get all the avisExpertMos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AvisExpertMoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AvisExpertMos");
        return avisExpertMoRepository.findAll(pageable)
            .map(avisExpertMoMapper::toDto);
    }

    /**
     *  Get one avisExpertMo by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public AvisExpertMoDTO findOne(Long id) {
        log.debug("Request to get AvisExpertMo : {}", id);
        AvisExpertMo avisExpertMo = avisExpertMoRepository.findOne(id);
        return avisExpertMoMapper.toDto(avisExpertMo);
    }

    /**
     *  Delete the  avisExpertMo by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AvisExpertMo : {}", id);
        avisExpertMoRepository.delete(id);
        avisExpertMoSearchRepository.delete(id);
    }

    /**
     * Search for the avisExpertMo corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AvisExpertMoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of AvisExpertMos for query {}", query);
        Page<AvisExpertMo> result = avisExpertMoSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(avisExpertMoMapper::toDto);
    }
}
