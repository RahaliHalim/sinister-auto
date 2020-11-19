package com.gaconnecte.auxilium.service.impl;

import com.gaconnecte.auxilium.service.TarifLineService;
import com.gaconnecte.auxilium.domain.TarifLine;
import com.gaconnecte.auxilium.repository.TarifLineRepository;
import com.gaconnecte.auxilium.service.dto.TarifLineDTO;
import com.gaconnecte.auxilium.service.mapper.TarifLineMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing TarifLine.
 */
@Service
@Transactional
public class TarifLineServiceImpl implements TarifLineService{

    private final Logger log = LoggerFactory.getLogger(TarifLineServiceImpl.class);

    private final TarifLineRepository tarifLineRepository;

    private final TarifLineMapper tarifLineMapper;

    public TarifLineServiceImpl(TarifLineRepository tarifLineRepository, TarifLineMapper tarifLineMapper) {
        this.tarifLineRepository = tarifLineRepository;
        this.tarifLineMapper = tarifLineMapper;
    }

    /**
     * Save a tarif.
     *
     * @param tarifDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TarifLineDTO save(TarifLineDTO tarifLineDTO) {
        log.debug("Request to save TarifLine : {}", tarifLineDTO);
        TarifLine tarifLine = tarifLineMapper.toEntity(tarifLineDTO);
        tarifLine = tarifLineRepository.save(tarifLine);
        return tarifLineMapper.toDto(tarifLine);
    }

    /**
     *  Get all the tarifLines.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TarifLineDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TarifLines");
        return tarifLineRepository.findAll(pageable)
            .map(tarifLineMapper::toDto);
    }

    /**
     *  Get one tarif by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public TarifLineDTO findOne(Long id) {
        log.debug("Request to get TarifLine : {}", id);
        TarifLine tarifLine = tarifLineRepository.findOne(id);
        return tarifLineMapper.toDto(tarifLine);
    }

    /**
     *  Delete the  tarifLine by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TarifLine : {}", id);
        tarifLineRepository.delete(id);
    }
}
