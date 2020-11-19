package com.gaconnecte.auxilium.service.impl;

import com.gaconnecte.auxilium.domain.Cellule;
import com.gaconnecte.auxilium.repository.CelluleRepository;
import com.gaconnecte.auxilium.service.CelluleService;
import com.gaconnecte.auxilium.service.dto.CelluleDTO;
import com.gaconnecte.auxilium.service.mapper.CelluleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Cellule.
 */
@Service
@Transactional
public class CelluleServiceImpl implements CelluleService{

    private final Logger log = LoggerFactory.getLogger(CelluleServiceImpl.class);

    private final CelluleRepository celluleRepository;

    private final CelluleMapper celluleMapper;

    public CelluleServiceImpl(CelluleRepository celluleRepository, CelluleMapper celluleMapper) {
        this.celluleRepository = celluleRepository;
        this.celluleMapper = celluleMapper;
    }

    /**
     * Save a cellule.
     *
     * @param celluleDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CelluleDTO save(CelluleDTO celluleDTO) {
        log.debug("Request to save Cellule : {}", celluleDTO);
        Cellule cellule = celluleMapper.toEntity(celluleDTO);
        cellule = celluleRepository.save(cellule);
        return celluleMapper.toDto(cellule);
    }

    /**
     *  Get all the cellules.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CelluleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Cellules");
        return celluleRepository.findAll(pageable)
            .map(celluleMapper::toDto);
    }

    /**
     *  Get one cellule by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CelluleDTO findOne(Long id) {
        log.debug("Request to get Cellule : {}", id);
        Cellule cellule = celluleRepository.findOne(id);
        return celluleMapper.toDto(cellule);
    }

    /**
     *  Delete the  cellule by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Cellule : {}", id);
        celluleRepository.delete(id);
    }

     /**
     *  Get one cellule by name.
     *
     *  @param name the name of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CelluleDTO findByName(String nom) {
        log.debug("Request to get cellule : {}", nom);
        Cellule cellule = celluleRepository.findByName(nom);
        return celluleMapper.toDto(cellule);
    }
}
