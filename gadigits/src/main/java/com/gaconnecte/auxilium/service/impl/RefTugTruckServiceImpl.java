package com.gaconnecte.auxilium.service.impl;

import com.gaconnecte.auxilium.domain.RefTugTruck;
import com.gaconnecte.auxilium.domain.RefTypeService;
import com.gaconnecte.auxilium.domain.app.SinisterPrestation;
import com.gaconnecte.auxilium.repository.search.CamionSearchRepository;
import com.gaconnecte.auxilium.service.dto.RefTugTruckDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.gaconnecte.auxilium.repository.RefTugTruckRepository;
import com.gaconnecte.auxilium.repository.SinisterPrestationRepository;
import com.gaconnecte.auxilium.service.mapper.RefTugTruckMapper;
import com.gaconnecte.auxilium.service.RefTugTruckService;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.collections.CollectionUtils;

/**
 * Service Implementation for managing RefTugTruck.
 */
@Service
@Transactional
public class RefTugTruckServiceImpl implements RefTugTruckService {

    private final Logger log = LoggerFactory.getLogger(RefTugTruckServiceImpl.class);

    private final RefTugTruckRepository camionRepository;
    
    private final SinisterPrestationRepository prestationRepository;

    private final RefTugTruckMapper camionMapper;

    public RefTugTruckServiceImpl(RefTugTruckRepository camionRepository, RefTugTruckMapper camionMapper, CamionSearchRepository camionSearchRepository,
            SinisterPrestationRepository prestationRepository) {
        this.camionRepository = camionRepository;
        this.camionMapper = camionMapper;
        this.prestationRepository = prestationRepository;
    }

    /**
     * Save a camion.
     *
     * @param tarifDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RefTugTruckDTO save(RefTugTruckDTO camionDTO) {
        log.debug("Request to save Camion : {}", camionDTO);
        RefTugTruck camion = camionMapper.toEntity(camionDTO);
        camion = camionRepository.save(camion);
        return camionMapper.toDto(camion);
    }

    /**
     * Get all the camions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RefTugTruckDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Camions");
        return camionRepository.findAll(pageable)
                .map(camionMapper::toDto);
    }

    /**
     * Get one camion by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public RefTugTruckDTO findOne(Long id) {
        log.debug("Request to get Camion : {}", id);
        RefTugTruck camion = camionRepository.findOne(id);
        return camionMapper.toDto(camion);
    }

    /**
     * Get one camion by refRemorqueurId.
     *
     * @param refremorqueurId the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RefTugTruckDTO> findByRefRemorqueur(Pageable pageable, Long refRemorqueurId) {
        log.debug("Request to get Camion : {}", refRemorqueurId);
        Page<RefTugTruck> camions = camionRepository.findCamionsByRefRemorqueur(pageable, refRemorqueurId);
        return camions.map(camionMapper::toDto);

    }

    /**
     * Get all trucks by refServiceTypeId.
     *
     * @param refServiceTypeId the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Set<RefTugTruckDTO> findAllTrucksByServiceType(Long serviceTypeId) {
        log.debug("Request to get truck by service type : {}", serviceTypeId);
        RefTypeService serviceType = new RefTypeService();
        serviceType.setId(serviceTypeId);
        Set<RefTugTruck> trucks = camionRepository.findTrucksByServiceType(serviceType);
        if (CollectionUtils.isNotEmpty(trucks)) {
            Set<RefTugTruckDTO> ret = trucks.stream().map(camionMapper::toDto).collect(Collectors.toSet());
            for (RefTugTruckDTO refTugTruckDTO : ret) {
                Set<SinisterPrestation> prsts = prestationRepository.findSinisterPrestationActiveTruck(refTugTruckDTO.getId());
                if(CollectionUtils.isNotEmpty(prsts)) {
                    refTugTruckDTO.setStatusLabel("IN_PROGRESS");
                }
            }
            return ret;
        }
        return new HashSet<>();
    }
    
    @Override
    @Transactional(readOnly = true)
    public Set<RefTugTruckDTO> findAllTrucksByServiceTypeAndByGovernorate(Long serviceTypeId, Long governorateId) {
        log.debug("Request to get truck by service type : {}", serviceTypeId);
        RefTypeService serviceType = new RefTypeService();
        serviceType.setId(serviceTypeId);
        Set<RefTugTruck> trucks = camionRepository.findTrucksByServiceTypeAndByGovernorate(serviceType, governorateId);
        if (CollectionUtils.isNotEmpty(trucks)) {
            Set<RefTugTruckDTO> ret = trucks.stream().map(camionMapper::toDto).collect(Collectors.toSet());
            for (RefTugTruckDTO refTugTruckDTO : ret) {
                Set<SinisterPrestation> prsts = prestationRepository.findSinisterPrestationActiveTruck(refTugTruckDTO.getId());
                if(CollectionUtils.isNotEmpty(prsts)) {
                    refTugTruckDTO.setStatusLabel("IN_PROGRESS");
                }
            }
            return ret;
        }
        return new HashSet<>();
    }

    /**
     * Delete the camion by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Camion : {}", id);
        camionRepository.delete(id);
    }

    @Override
    public Page<RefTugTruckDTO> search(String query, Pageable pageable) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public RefTugTruckDTO findMainCamion(Long refemorqueurId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public RefTugTruckDTO findTrucksByImmatriculation(String immatriculation) {
        log.debug("Request to get truck by it registration number : {}", immatriculation);
        RefTugTruck truck = camionRepository.findTrucksByImmatriculation(immatriculation);
        return camionMapper.toDto(truck);
    }
    
}
