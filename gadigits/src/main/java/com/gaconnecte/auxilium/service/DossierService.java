package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.service.dto.DemandPecDTO;
import com.gaconnecte.auxilium.service.dto.DossierDTO;
import com.gaconnecte.auxilium.service.dto.PrestationAvtDTO;
import com.gaconnecte.auxilium.service.dto.PrestationPECDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Set;
/**
 * Service Interface for managing Dossier.
 */
public interface DossierService {

    /**
     * Save a dossier.
     *
     * @param dossierDTO the entity to save
     * @return the persisted entity
     */
    DossierDTO save(DossierDTO dossierDTO);

    /**
     *  Get all the dossiers.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Set<DossierDTO> findAll();

    /**
     *  Get the "id" dossier.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    DossierDTO findOne(Long id);

    /**
     *  Delete the "id" dossier.
     *
     *  @param id the id of the entity
     */
    Boolean delete(Long id);
    Long countDossier();

    Long countKpiDossier(String debut, String fin);

    DossierDTO update(DossierDTO dossierDTO);

    Page<PrestationPECDTO> findAllPEC(Pageable pageable, Long id);

    Page<PrestationAvtDTO> findAllAssistance(Pageable pageable, Long id);

    List<DemandPecDTO> getOutstandingDemands();

    List<DemandPecDTO> getSentDemands();

    List<DemandPecDTO> getAccordDemands();

    List<DemandPecDTO> getAllNewExternalDemands();
}
