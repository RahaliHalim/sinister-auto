package com.gaconnecte.auxilium.service.impl;

import com.gaconnecte.auxilium.service.TarifService;
import com.gaconnecte.auxilium.domain.Tarif;
import com.gaconnecte.auxilium.repository.TarifRepository;
import com.gaconnecte.auxilium.service.dto.TarifDTO;
import com.gaconnecte.auxilium.service.mapper.TarifMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Tarif.
 */
@Service
@Transactional
public class TarifServiceImpl implements TarifService{

    private final Logger log = LoggerFactory.getLogger(TarifServiceImpl.class);

    private final TarifRepository tarifRepository;

    private final TarifMapper tarifMapper;

    public TarifServiceImpl(TarifRepository tarifRepository, TarifMapper tarifMapper) {
        this.tarifRepository = tarifRepository;
        this.tarifMapper = tarifMapper;
    }

    /**
     * Save a tarif.
     *
     * @param tarifDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TarifDTO save(TarifDTO tarifDTO) {
        log.debug("Request to save Tarif : {}", tarifDTO);
        Tarif tarif = tarifMapper.toEntity(tarifDTO);
        tarif = tarifRepository.save(tarif);
        return tarifMapper.toDto(tarif);
    }

    /**
     *  Get all the tarifs.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TarifDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Tarifs");
        return tarifRepository.findAll(pageable)
            .map(tarifMapper::toDto);
    }

    /**
     *  Get one tarif by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public TarifDTO findOne(Long id) {
        log.debug("Request to get Tarif : {}", id);
        Tarif tarif = tarifRepository.findOne(id);
        return tarifMapper.toDto(tarif);
    }

    /**
     *  Delete the  tarif by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Tarif : {}", id);
        tarifRepository.delete(id);
    }
}
