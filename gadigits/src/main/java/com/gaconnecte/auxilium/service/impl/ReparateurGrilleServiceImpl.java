package com.gaconnecte.auxilium.service.impl;

import com.gaconnecte.auxilium.service.ReparateurGrilleService;
import com.gaconnecte.auxilium.domain.ReparateurGrille;
import com.gaconnecte.auxilium.repository.ReparateurGrilleRepository;
import com.gaconnecte.auxilium.service.dto.ReparateurGrilleDTO;
import com.gaconnecte.auxilium.service.mapper.ReparateurGrilleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Service Implementation for managing ReparateurGrille.
 */
@Service
@Transactional
public class ReparateurGrilleServiceImpl implements ReparateurGrilleService{

    private final Logger log = LoggerFactory.getLogger(ReparateurGrilleServiceImpl.class);

    private final ReparateurGrilleRepository reparateurGrilleRepository;

    private final ReparateurGrilleMapper reparateurGrilleMapper;

    public ReparateurGrilleServiceImpl(ReparateurGrilleRepository reparateurGrilleRepository, ReparateurGrilleMapper reparateurGrilleMapper) {
        this.reparateurGrilleRepository = reparateurGrilleRepository;
        this.reparateurGrilleMapper = reparateurGrilleMapper;
    }

    /**
     * Save a reparateurGrille.
     *
     * @param reparateurGrilleMapperDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ReparateurGrilleDTO save(ReparateurGrilleDTO reparateurGrilleDTO) {
        log.debug("Request to save ReparateurGrille : {}", reparateurGrilleDTO);
        ReparateurGrille reparateurGrille = reparateurGrilleMapper.toEntity(reparateurGrilleDTO);
        reparateurGrille = reparateurGrilleRepository.save(reparateurGrille);
        return reparateurGrilleMapper.toDto(reparateurGrille);
    }

    /**
     *  Get all the reparateurGrille.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ReparateurGrilleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all reparateurGrille");
        return reparateurGrilleRepository.findAll(pageable)
            .map(reparateurGrilleMapper::toDto);
    }

    /**
     *  Get one reparateurGrille by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ReparateurGrilleDTO findOne(Long id) {
        log.debug("Request to get reparateurGrille : {}", id);
        ReparateurGrille reparateurGrille = reparateurGrilleRepository.findOne(id);
        return reparateurGrilleMapper.toDto(reparateurGrille);
    }

    /**
     *  Delete the  reparateurGrille by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete reparateurGrille : {}", id);
        reparateurGrilleRepository.delete(id);
    }

    /**
     *  Get one reparateurGrille by reparateur and cellule.
     *
     *  @param userId, celluleId the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ReparateurGrilleDTO findByReparateurAndGrille(Long grilleId, Long reparateurId) {
        log.debug("Request to get reparateurGrille : {}", grilleId,reparateurId);
        ReparateurGrille reparateurGrille = reparateurGrilleRepository.findByGrilleAndReparateur(grilleId,reparateurId);
        return reparateurGrilleMapper.toDto(reparateurGrille);
    }

    @Override
    public List<ReparateurGrilleDTO> findByReparateur(Long reparateurId) {
        log.debug("Request to get  authority: {}", reparateurId);
        List<ReparateurGrille> reparateurGrille = reparateurGrilleRepository.findByReparateur(reparateurId);
        return reparateurGrilleMapper.toDto(reparateurGrille);
    }

    @Override
    public ReparateurGrilleDTO getByTypeInterventionAndReparateur(Long refTypeInterventionId, Long reparateurId) {
        
        log.debug("Request to get  authority: {}", refTypeInterventionId, reparateurId);
        
        List<ReparateurGrille> repGrille = new ArrayList();
        List<ReparateurGrille> reparateurGrille = reparateurGrilleRepository.findByTypeInterventionAndReparateur(refTypeInterventionId, reparateurId);   
        LocalDate date = LocalDate.now();
        
        for(int i=0; i<reparateurGrille.size(); i++) {
            if(reparateurGrille.get(i).getDate().isBefore(date)) {
                repGrille.add(reparateurGrille.get(i));
            }
        }

        if(repGrille != null && !repGrille.isEmpty()) {
            ReparateurGrille selectedReparateurGrille = repGrille.get(0);
            for (int i = 1; i < repGrille.size(); i++) {
                if (repGrille.get(i).getDate().isAfter(selectedReparateurGrille.getDate())) {
                    selectedReparateurGrille =  repGrille.get(i);
                }
            }
            return reparateurGrilleMapper.toDto(selectedReparateurGrille);
        } else {
            return null;
        }

        
    }
    
}