package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.service.dto.DevisDTO;
import com.gaconnecte.auxilium.service.dto.EstimationDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Devis.
 */
public interface DevisService {

    /**
     * Save a devis.
     *
     * @param devisDTO the entity to save
     * @return the persisted entity
     */
    DevisDTO save(DevisDTO devisDTO);

    DevisDTO update(DevisDTO devisDTO);

    DevisDTO soumettre(Long id);

    /**
     *  Get all the devis.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<DevisDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" devis.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    DevisDTO findOne(Long id);

    /**
     *  Delete the "id" devis.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the devis corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<DevisDTO> search(String query, Pageable pageable);

    List<DevisDTO> findDevisByPrestation(Long prestationId);

    DevisDTO findQuotationByPrestation(Long prestationId);

    DevisDTO findLastDevisVersion(Long prestationId);

    DevisDTO findDevisSoumis(Long id);

    DevisDTO findDevisAccepteOuValideOuRefuse(Long id);

    DevisDTO findDevisAccepte(Long id);

    DevisDTO findDevisFacture(Long id);

    DevisDTO findDevisValide(Long id);

    DevisDTO getGenereBonSortieByPrestation(Long id);

    DevisDTO getDevisRefusAferFactureByPrestation(Long id);

    DevisDTO findLastDevisSauvegarde(Long id);

    DevisDTO refuserDevis(Long id);

    DevisDTO accepterDevis(Long id);
    
    DevisDTO validerDevis(Long id);
    DevisDTO validerGesDevis(Long id);

    DevisDTO findDevisValideElseSoumis(Long id);
    
    DevisDTO findEtatLastDevisByPrestation(Long id);
    
    DevisDTO findLastDevisSauvegardeRefuseValidGes(Long id);
    
    DevisDTO findLastDevisValidGes(Long id);

    /**
     * Get count of quotation
     * @return the number of quotation
     */
    Long getCountQuotation(String debut, String fin);

  
}
