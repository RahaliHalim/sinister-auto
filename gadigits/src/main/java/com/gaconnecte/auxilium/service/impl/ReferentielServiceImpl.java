package com.gaconnecte.auxilium.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gaconnecte.auxilium.domain.Referentiel;
import com.gaconnecte.auxilium.repository.ReferentielRepository;
import com.gaconnecte.auxilium.service.ReferentielService;
import com.gaconnecte.auxilium.service.dto.ReferentielDTO;
import com.gaconnecte.auxilium.service.mapper.ReferentielMapper;


@Service
@Transactional
public class ReferentielServiceImpl implements ReferentielService{

    private final Logger log = LoggerFactory.getLogger(ReferentielServiceImpl.class);

    private final ReferentielRepository referentielRepository;

    private final ReferentielMapper referentielMapper;

    public ReferentielServiceImpl(ReferentielRepository referentielRepository, ReferentielMapper referentielMapper) {
        this.referentielRepository = referentielRepository;
        this.referentielMapper = referentielMapper;
    }

    /**
     * Save a cellule.
     *
     * @param celluleDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ReferentielDTO save(ReferentielDTO referentielDTO) {
        log.debug("Request to save Cellule : {}", referentielDTO);
        Referentiel referentiel = referentielMapper.toEntity(referentielDTO);
        referentiel = referentielRepository.save(referentiel);
        return referentielMapper.toDto(referentiel);
    }

    /**
     *  Get all the cellules.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ReferentielDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Cellules");
        List<Referentiel> ref = referentielRepository.findAll();
        for(int i=0;i<ref.size();i++)
        {log.debug("le ref est "+ref.get(i).getLibelle());}
        return referentielRepository.findAll(pageable)
            .map(referentielMapper::toDto);
    }

    /**
     *  Get one cellule by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ReferentielDTO findOne(Long id) {
        log.debug("Request to get Cellule : {}", id);
        Referentiel referentiel = referentielRepository.findOne(id);
        return referentielMapper.toDto(referentiel);
    }

    /**
     *  Delete the  cellule by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Cellule : {}", id);
        referentielRepository.delete(id);
    }

     /**
     *  Get one cellule by name.
     *
     *  @param name the name of the entity
     *  @return the entity
     */
  
}
