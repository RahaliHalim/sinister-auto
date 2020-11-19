package com.gaconnecte.auxilium.service.impl;

import com.gaconnecte.auxilium.service.DetailsMoService;
import com.gaconnecte.auxilium.domain.DetailsMo;
import com.gaconnecte.auxilium.repository.DetailsMoRepository;
import com.gaconnecte.auxilium.repository.search.DetailsMoSearchRepository;
import com.gaconnecte.auxilium.service.dto.DetailsMoDTO;
import com.gaconnecte.auxilium.service.mapper.DetailsMoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing DetailsMo.
 */
@Service
@Transactional
public class DetailsMoServiceImpl implements DetailsMoService{

    private final Logger log = LoggerFactory.getLogger(DetailsMoServiceImpl.class);

    private final DetailsMoRepository detailsMoRepository;

    private final DetailsMoMapper detailsMoMapper;

    private final DetailsMoSearchRepository detailsMoSearchRepository;

    public DetailsMoServiceImpl(DetailsMoRepository detailsMoRepository, DetailsMoMapper detailsMoMapper, DetailsMoSearchRepository detailsMoSearchRepository) {
        this.detailsMoRepository = detailsMoRepository;
        this.detailsMoMapper = detailsMoMapper;
        this.detailsMoSearchRepository = detailsMoSearchRepository;
    }

    /**
     * Save a detailsMo.
     *
     * @param detailsMoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public DetailsMoDTO save(DetailsMoDTO detailsMoDTO) {
        log.debug("Request to save DetailsMo : {}", detailsMoDTO);
        DetailsMo detailsMo = detailsMoMapper.toEntity(detailsMoDTO);
        detailsMo = detailsMoRepository.save(detailsMo);
        DetailsMoDTO result = detailsMoMapper.toDto(detailsMo);
        detailsMoSearchRepository.save(detailsMo);
        return result;
    }

    /**
     *  Get all the detailsMos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DetailsMoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DetailsMos");
        return detailsMoRepository.findAll(pageable)
            .map(detailsMoMapper::toDto);
    }

    /**
     *  Get one detailsMo by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public DetailsMoDTO findOne(Long id) {
        log.debug("Request to get DetailsMo : {}", id);
        DetailsMo detailsMo = detailsMoRepository.findOne(id);
        return detailsMoMapper.toDto(detailsMo);
    }

    /**
     *  Delete the  detailsMo by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DetailsMo : {}", id);
        detailsMoRepository.delete(id);
        detailsMoSearchRepository.delete(id);
    }

    /**
     * Search for the detailsMo corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DetailsMoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of DetailsMos for query {}", query);
        Page<DetailsMo> result = detailsMoSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(detailsMoMapper::toDto);
    }

    
     /**
     *  Get detailsMo by devisId.
     *
     *  @param devisId the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DetailsMoDTO> findByDevis(Pageable pageable, Long devisId) {
        log.debug("Request to get PieceJointes : {}", devisId);
        Page<DetailsMo> detailsMo = detailsMoRepository.findDetailsMoByDevis(pageable, devisId);
        return detailsMo.map(detailsMoMapper::toDto);
         
    }
}
