package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.service.dto.DossierDTO;
import com.gaconnecte.auxilium.service.dto.JournalDTO;
import com.gaconnecte.auxilium.service.dto.LoueurDTO;
import com.gaconnecte.auxilium.service.dto.PrestationDTO;
import com.gaconnecte.auxilium.service.dto.PrestationPECDTO;
import com.gaconnecte.auxilium.service.dto.PrestationAvtDTO;
import com.gaconnecte.auxilium.service.dto.RefRemorqueurDTO;
import com.gaconnecte.auxilium.service.dto.QuotationDTO;
import com.gaconnecte.auxilium.service.dto.ReparateurDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Journal.
 */
public interface JournalService {

    /**
     * Save a journal.
     *
     * @param journalDTO the entity to save
     * @return the persisted entity
     */
    JournalDTO save(JournalDTO journalDTO);

    /**
     *  Get all the journals.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<JournalDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" journal.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    JournalDTO findOne(Long id);

    /**
     *  Delete the "id" journal.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the journal corresponding to the query.
     *
     *  @param query the query of the search
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    JournalDTO findJournalByPrestationPec(Long prestationPecId);
    //JournalDTO findJournalByQuotation(Long id);
    JournalDTO findJournalByRemorqueur(Long remorqueurId);
    JournalDTO findJournalByReparateur(Long reparateurId);
    Page<JournalDTO> search(String query, Pageable pageable);
    void journalisationDossier(String commentaire, String utilisateur, Long action, DossierDTO dossierDTO);
    void journalisationConnection(String commentaire, String utilisateur, Long action);
    void journalisationPrestation(String commentaire, String utilisateur, Long action, PrestationDTO prestationDTO);
    void journalisationPrestationPEC(String commentaire, String utilisateur, Long action,Long[]  motifsJournalisation , PrestationPECDTO prestationPECDTO);
    void journalisationPrestationAVTMotif(String commentaire, String utilisateur, Long action, PrestationAvtDTO prestationAvtDTO,Long[] motifs);
    void journalisationRefRemorqueurMotif(String commentaire, String utilisateur, Long action,   RefRemorqueurDTO refRemorqueurDTO ,Long[] motifs);
    void journalisationReparateurMotif(String commentaire, String utilisateur, Long action,   ReparateurDTO reparateurDTO ,Long[] motifs);
    void journalisationMotifNonConfirmeDevis(String commentaire, String utilisateur, Long action,   QuotationDTO QuotationDTO ,Long[] motifs);



}
