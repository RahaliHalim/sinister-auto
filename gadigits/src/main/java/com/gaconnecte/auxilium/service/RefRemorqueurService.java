package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.service.dto.RefRemorqueurDTO;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing RefRemorqueur.
 */
public interface RefRemorqueurService {
    /**
     * Save a refRemorqueur.
     *
     * @param refRemorqueurDTO the entity to save
     * @return the persisted entity
     */
    RefRemorqueurDTO save(RefRemorqueurDTO refRemorqueurDTO);
    /**
     *  Get all the refRemorqueurs.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    List<RefRemorqueurDTO> findAll();
    /**
     *  Get the "id" refRemorqueur.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
   Page<RefRemorqueurDTO>findAllRemorqueurCloture(Pageable pageable);
    /*
     *  Get the "id" refRemorqueur.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    
    
    RefRemorqueurDTO findOne(Long id);

    /**
     *  Delete the "id" refRemorqueur.
     *
     *  @param id the id of the entity
     */
    Boolean delete(Long id);
    RefRemorqueurDTO findRefRemorqueurByMail(String mail);

    /**
     * Search for the refRemorqueur corresponding to the query.
     *
     *  @param query the query of the search
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<RefRemorqueurDTO> search(String query, Pageable pageable);

    Double convertRad(Double input);
    
    Page<RefRemorqueurDTO> findAllRemorqueur(Pageable pageable);
    
    List<RefRemorqueurDTO> findTousRemorqueurNonBloque();
    
    Double distanceAssureRemorqueur(Double lat_a_degre, Double lon_a_degre, Double lat_b_degre, Double lon_b_degre);
    RefRemorqueurDTO findRefRemorqueurByMailAndMdp(String mail, String mdp);
    
    
    Page<RefRemorqueurDTO> findAllRemorqueurWithOrder(Long idVille ,Pageable pageable);

    void  bloque(Long id);

    public Long countDossierByRemorqueur(Long id) ;
    
    List<RefRemorqueurDTO> RechercheRemorqueurs (Double latitudeAssure , Double longitudeAssure);

   RefRemorqueurDTO getTugCompanyByName(String pname,String matriculeFiscale,String registreCommerce);
   
   public RefRemorqueurDTO findRefRemorqueurByDeviceId(String deviceId);

}
