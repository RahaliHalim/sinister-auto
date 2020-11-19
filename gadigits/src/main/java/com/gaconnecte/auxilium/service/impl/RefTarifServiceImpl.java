package com.gaconnecte.auxilium.service.impl;

import com.gaconnecte.auxilium.service.RefTarifService;
import com.gaconnecte.auxilium.domain.RefTugTruck;
import com.gaconnecte.auxilium.domain.RefTarif;
import com.gaconnecte.auxilium.repository.RefTarifRepository;
import com.gaconnecte.auxilium.service.dto.RefTugTruckDTO;
import com.gaconnecte.auxilium.service.dto.RefTarifDTO;
import com.gaconnecte.auxilium.service.mapper.RefTarifMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing RefTarif.
 */
@Service
@Transactional
public class RefTarifServiceImpl implements RefTarifService{

    private final Logger log = LoggerFactory.getLogger(RefTarifServiceImpl.class);

    private final RefTarifRepository refTarifRepository;

    private final RefTarifMapper refTarifMapper;

    public RefTarifServiceImpl(RefTarifRepository refTarifRepository, RefTarifMapper refTarifMapper) {
        this.refTarifRepository = refTarifRepository;
        this.refTarifMapper = refTarifMapper;
    }

    /**
     * Save a refTarif.
     *
     * @param refTarifDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RefTarifDTO save(RefTarifDTO refTarifDTO) {
        log.debug("Request to save RefTarif : {}", refTarifDTO);
        RefTarif refTarif = refTarifMapper.toEntity(refTarifDTO);
        refTarif = refTarifRepository.save(refTarif);
        return refTarifMapper.toDto(refTarif);
    }

    /**
     *  Get all the refTarifs.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RefTarifDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RefTarifs");
        return refTarifRepository.findAll(pageable)
            .map(refTarifMapper::toDto);
    }

    /**
     *  Get one refTarif by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public RefTarifDTO findOne(Long id) {
        log.debug("Request to get RefTarif : {}", id);
        RefTarif refTarif = refTarifRepository.findOne(id);
        return refTarifMapper.toDto(refTarif);
    }
    /**
     *  Get one TarifLine by lineId.
     *
     *  @param lineId the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RefTarifDTO> findByTarifLine(Pageable pageable, Long lineId) {
        log.debug("Request to get TarifLine : {}", lineId);
        Page<RefTarif> refTarifs = refTarifRepository.findRefTarifsByTarifLine(pageable, lineId);
        return refTarifs.map(refTarifMapper::toDto);
         
    }
    /**
     *  Delete the  refTarif by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RefTarif : {}", id);
        refTarifRepository.delete(id);
    }
}
