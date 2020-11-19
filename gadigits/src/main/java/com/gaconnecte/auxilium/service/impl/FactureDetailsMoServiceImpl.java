package com.gaconnecte.auxilium.service.impl;

import com.gaconnecte.auxilium.service.FactureDetailsMoService;
import com.gaconnecte.auxilium.domain.FactureDetailsMo;
import com.gaconnecte.auxilium.repository.FactureDetailsMoRepository;
import com.gaconnecte.auxilium.repository.search.FactureDetailsMoSearchRepository;
import com.gaconnecte.auxilium.service.dto.FactureDetailsMoDTO;
import com.gaconnecte.auxilium.service.mapper.FactureDetailsMoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing FactureDetailsMo.
 */
@Service
@Transactional
public class FactureDetailsMoServiceImpl implements FactureDetailsMoService{

    private final Logger log = LoggerFactory.getLogger(FactureDetailsMoServiceImpl.class);

    private final FactureDetailsMoRepository factureDetailsMoRepository;

    private final FactureDetailsMoMapper factureDetailsMoMapper;

    private final FactureDetailsMoSearchRepository factureDetailsMoSearchRepository;

    public FactureDetailsMoServiceImpl(FactureDetailsMoRepository factureDetailsMoRepository, FactureDetailsMoMapper factureDetailsMoMapper, FactureDetailsMoSearchRepository factureDetailsMoSearchRepository) {
        this.factureDetailsMoRepository = factureDetailsMoRepository;
        this.factureDetailsMoMapper = factureDetailsMoMapper;
        this.factureDetailsMoSearchRepository = factureDetailsMoSearchRepository;
    }

    /**
     * Save a factureDetailsMo.
     *
     * @param factureDetailsMoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public FactureDetailsMoDTO save(FactureDetailsMoDTO factureDetailsMoDTO) {
        log.debug("Request to save FactureDetailsMo : {}", factureDetailsMoDTO);
        FactureDetailsMo factureDetailsMo = factureDetailsMoMapper.toEntity(factureDetailsMoDTO);
        factureDetailsMo = factureDetailsMoRepository.save(factureDetailsMo);
        FactureDetailsMoDTO result = factureDetailsMoMapper.toDto(factureDetailsMo);
        factureDetailsMoSearchRepository.save(factureDetailsMo);
        return result;
    }

    /**
     *  Get all the factureDetailsMos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FactureDetailsMoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FactureDetailsMos");
        return factureDetailsMoRepository.findAll(pageable)
            .map(factureDetailsMoMapper::toDto);
    }

    /**
     *  Get one factureDetailsMo by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public FactureDetailsMoDTO findOne(Long id) {
        log.debug("Request to get FactureDetailsMo : {}", id);
        FactureDetailsMo factureDetailsMo = factureDetailsMoRepository.findOne(id);
        return factureDetailsMoMapper.toDto(factureDetailsMo);
    }

    /**
     *  Delete the  factureDetailsMo by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete FactureDetailsMo : {}", id);
        factureDetailsMoRepository.delete(id);
        factureDetailsMoSearchRepository.delete(id);
    }

    /**
     * Search for the factureDetailsMo corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FactureDetailsMoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of FactureDetailsMos for query {}", query);
        Page<FactureDetailsMo> result = factureDetailsMoSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(factureDetailsMoMapper::toDto);
    }
}
