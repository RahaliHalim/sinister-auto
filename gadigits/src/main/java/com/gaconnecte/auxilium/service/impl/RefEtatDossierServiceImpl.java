package com.gaconnecte.auxilium.service.impl;

import com.gaconnecte.auxilium.service.RefEtatDossierService;
import com.gaconnecte.auxilium.domain.RefEtatDossier;
import com.gaconnecte.auxilium.repository.RefEtatDossierRepository;
import com.gaconnecte.auxilium.repository.search.RefEtatDossierSearchRepository;
import com.gaconnecte.auxilium.service.dto.RefEtatDossierDTO;
import com.gaconnecte.auxilium.service.mapper.RefEtatDossierMapper;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



/**
 * Service Implementation for managing RefEtatDossier.
 */
@Service
@Transactional
public class RefEtatDossierServiceImpl implements RefEtatDossierService{

    private final Logger log = LoggerFactory.getLogger(RefEtatDossierServiceImpl.class);

    private final RefEtatDossierRepository refEtatDossierRepository;

    private final RefEtatDossierMapper refEtatDossierMapper;

    private final RefEtatDossierSearchRepository refEtatDossierSearchRepository;

    public RefEtatDossierServiceImpl(RefEtatDossierRepository refEtatDossierRepository, RefEtatDossierMapper refEtatDossierMapper, RefEtatDossierSearchRepository refEtatDossierSearchRepository) {
        this.refEtatDossierRepository = refEtatDossierRepository;
        this.refEtatDossierMapper = refEtatDossierMapper;
        this.refEtatDossierSearchRepository = refEtatDossierSearchRepository;
    }

    /**
     * Save a refEtatDossier.
     *
     * @param refEtatDossierDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RefEtatDossierDTO save(RefEtatDossierDTO refEtatDossierDTO) {
        log.debug("Request to save RefEtatDossier : {}", refEtatDossierDTO);
        RefEtatDossier refEtatDossier = refEtatDossierMapper.toEntity(refEtatDossierDTO);
        refEtatDossier = refEtatDossierRepository.save(refEtatDossier);
        RefEtatDossierDTO result = refEtatDossierMapper.toDto(refEtatDossier);
        refEtatDossierSearchRepository.save(refEtatDossier);
        return result;
    }

    /**
     *  Get all the refEtatDossiers.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RefEtatDossierDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RefEtatDossiers");
        return refEtatDossierRepository.findAll(pageable)
            .map(refEtatDossierMapper::toDto);
    }

    /**
     *  Get one refEtatDossier by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public RefEtatDossierDTO findOne(Long id) {
        log.debug("Request to get RefEtatDossier : {}", id);
        RefEtatDossier refEtatDossier = refEtatDossierRepository.findOne(id);
        return refEtatDossierMapper.toDto(refEtatDossier);
    }

    /**
     *  Delete the  refEtatDossier by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RefEtatDossier : {}", id);
        refEtatDossierRepository.delete(id);
        refEtatDossierSearchRepository.delete(id);
    }

    /**
     * Search for the refEtatDossier corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RefEtatDossierDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of RefEtatDossiers for query {}", query);
        Page<RefEtatDossier> result = refEtatDossierSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(refEtatDossierMapper::toDto);
    }
}
