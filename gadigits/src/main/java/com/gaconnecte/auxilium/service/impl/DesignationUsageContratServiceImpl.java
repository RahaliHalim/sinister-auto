package com.gaconnecte.auxilium.service.impl;

import com.gaconnecte.auxilium.service.DesignationUsageContratService;
import com.gaconnecte.auxilium.domain.DesignationUsageContrat;
import com.gaconnecte.auxilium.repository.DesignationUsageContratRepository;
import com.gaconnecte.auxilium.repository.search.DesignationUsageContratSearchRepository;
import com.gaconnecte.auxilium.service.dto.DesignationUsageContratDTO;
import com.gaconnecte.auxilium.service.mapper.DesignationUsageContratMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing DesignationUsageContrat.
 */
@Service
@Transactional
public class DesignationUsageContratServiceImpl implements DesignationUsageContratService{

    private final Logger log = LoggerFactory.getLogger(DesignationUsageContratServiceImpl.class);

    private final DesignationUsageContratRepository designationUsageContratRepository;

    private final DesignationUsageContratMapper designationUsageContratMapper;

    private final DesignationUsageContratSearchRepository designationUsageContratSearchRepository;

    public DesignationUsageContratServiceImpl(DesignationUsageContratRepository designationUsageContratRepository, DesignationUsageContratMapper designationUsageContratMapper, DesignationUsageContratSearchRepository designationUsageContratSearchRepository) {
        this.designationUsageContratRepository = designationUsageContratRepository;
        this.designationUsageContratMapper = designationUsageContratMapper;
        this.designationUsageContratSearchRepository = designationUsageContratSearchRepository;
    }

    /**
     * Save a designationUsageContrat.
     *
     * @param designationUsageContratDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public DesignationUsageContratDTO save(DesignationUsageContratDTO designationUsageContratDTO) {
        log.debug("Request to save DesignationUsageContrat : {}", designationUsageContratDTO);
        DesignationUsageContrat designationUsageContrat = designationUsageContratMapper.toEntity(designationUsageContratDTO);
        designationUsageContrat = designationUsageContratRepository.save(designationUsageContrat);
        DesignationUsageContratDTO result = designationUsageContratMapper.toDto(designationUsageContrat);
        designationUsageContratSearchRepository.save(designationUsageContrat);
        return result;
    }

    /**
     *  Get all the designationUsageContrats.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DesignationUsageContratDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DesignationUsageContrats");
        return designationUsageContratRepository.findAll(pageable)
            .map(designationUsageContratMapper::toDto);
    }

    /**
     *  Get one designationUsageContrat by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public DesignationUsageContratDTO findOne(Long id) {
        log.debug("Request to get DesignationUsageContrat : {}", id);
        DesignationUsageContrat designationUsageContrat = designationUsageContratRepository.findOne(id);
        return designationUsageContratMapper.toDto(designationUsageContrat);
    }

    /**
     *  Delete the  designationUsageContrat by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DesignationUsageContrat : {}", id);
        designationUsageContratRepository.delete(id);
        designationUsageContratSearchRepository.delete(id);
    }

    /**
     * Search for the designationUsageContrat corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DesignationUsageContratDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of DesignationUsageContrats for query {}", query);
        Page<DesignationUsageContrat> result = designationUsageContratSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(designationUsageContratMapper::toDto);
    }

	@Override
	public DesignationUsageContratDTO findOneByCompagnieIdAndUsageContratiD(Long compagnieId, Long refUsageContratId) {
		log.debug("Request to get DesignationUsageContrat : {}", compagnieId);
		log.debug("Request to get DesignationUsageContrat : {}", refUsageContratId);
        DesignationUsageContrat designationUsageContrat = designationUsageContratRepository.findOneByCompagnieIdAndUsageContratiD(compagnieId,refUsageContratId);
        return designationUsageContratMapper.toDto(designationUsageContrat);
	}
}
