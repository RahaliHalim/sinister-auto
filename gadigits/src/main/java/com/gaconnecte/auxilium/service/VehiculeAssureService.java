package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.domain.ContratAssurance;
import com.gaconnecte.auxilium.service.dto.PartnerDTO;
import com.gaconnecte.auxilium.service.dto.RefTugTruckDTO;
import com.gaconnecte.auxilium.service.dto.VehiculeAssureDTO;
import com.gaconnecte.auxilium.service.dto.VehiculeContratDTO;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing VehiculeAssure.
 */
public interface VehiculeAssureService {

    /**
     * Save a vehiculeAssure.
     *
     * @param vehiculeAssureDTO the entity to save
     * @return the persisted entity
     */
    VehiculeAssureDTO save(VehiculeAssureDTO vehiculeAssureDTO);

    /**
     *  Get all the vehiculeAssures.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<VehiculeAssureDTO> findAll(Pageable pageable);
    
    List<VehiculeAssureDTO> findAllWithoutPagination();
    List<VehiculeContratDTO> findContratsForEachVehicle();

    VehiculeContratDTO findContratByImmatriculation(String immatriculation);

    /**
     *  Get the "id" vehiculeAssure.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    VehiculeAssureDTO findOne(Long id);

     VehiculeAssureDTO findOneByImmatriculation(String immatriculationVehicule);
     
     Set<PartnerDTO> findListByImmatriculation(String immatriculationVehicule);
     
     VehiculeAssureDTO findOneByCompagnyAndImmatriculation(String company, String immatriculation);
     
     VehiculeAssureDTO findOneByClientIdAndImmatriculation(Long clientId, String immatriculation);
    
     Page<VehiculeAssureDTO>  findOneByContrat(Pageable pageable, Long contratId);
    /**
     *  Delete the "id" vehiculeAssure.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
    
    void deleteByContrat(Long id);

    /**
     * Search for the vehiculeAssure corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<VehiculeAssureDTO> search(String query, Pageable pageable);
    
    String findMarqueByVehicule(Long vehiculeId);

}
