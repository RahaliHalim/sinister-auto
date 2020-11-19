package com.gaconnecte.auxilium.service.impl;

import com.gaconnecte.auxilium.domain.Devis;
import com.gaconnecte.auxilium.domain.Journal;
import com.gaconnecte.auxilium.repository.JournalRepository;
import com.gaconnecte.auxilium.repository.search.JournalSearchRepository;
import com.gaconnecte.auxilium.service.JournalService;
import com.gaconnecte.auxilium.service.RefMotifService;
import com.gaconnecte.auxilium.service.SysActionUtilisateurService;
import com.gaconnecte.auxilium.service.dto.*;
import com.gaconnecte.auxilium.service.mapper.JournalMapper;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing Journal.
 */
@Service
@Transactional
public class JournalServiceImpl implements JournalService {

    private final Logger log = LoggerFactory.getLogger(JournalServiceImpl.class);

    private final JournalRepository journalRepository;

    private final JournalMapper journalMapper;

    private final JournalSearchRepository journalSearchRepository;

    @Autowired
    private SysActionUtilisateurService sysActionUtilisateurService;
    @Autowired
    private RefMotifService refMotifService;
    
 	public JournalServiceImpl(JournalRepository journalRepository, JournalMapper journalMapper, JournalSearchRepository journalSearchRepository) {
        this.journalRepository = journalRepository;
        this.journalMapper = journalMapper;
        this.journalSearchRepository = journalSearchRepository;
    }

    /**
     * Save a journal.
     *
     * @param journalDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public JournalDTO save(JournalDTO journalDTO) {
        log.debug("Request to save Journal : {}", journalDTO);
        Journal journal = journalMapper.toEntity(journalDTO);
        journal.setUtilisateur(journalDTO.getUtilisateur());
        journal = journalRepository.save(journal);
        JournalDTO result = journalMapper.toDto(journal);
        journalSearchRepository.save(journal);
        return result;
    }
    @Override
    public JournalDTO findJournalByRemorqueur(Long remorqueurId) {
        log.debug("Request to get  all journal by remmorqueur");
        Journal journal = journalRepository.findLastJournalByRemorqueur(remorqueurId);
        return journalMapper.toDto(journal);
    }
    @Override
    public JournalDTO findJournalByReparateur(Long reparateurId) {
        log.debug("Request to get  all journal by reparateur");
        Journal journal = journalRepository.findLastJournalByReparateur(reparateurId);
        return journalMapper.toDto(journal);
    }
    /*@Override
    public JournalDTO findJournalByQuotation(Long id) {
        log.debug("Request to get  all journal by quotation");
        Journal journal = journalRepository.findLastJournalByQuotation(id);
        return journalMapper.toDto(journal);
    }*/
    @Override
    public JournalDTO findJournalByPrestationPec(Long prestationId) {
        log.debug("Request to get  all Devis by prestationPEC");
        Journal journal = journalRepository.findLastJournalByPrestationPec(prestationId);
        return journalMapper.toDto(journal);
    }
    /**
     * Get all the journals.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<JournalDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Journals");
        return journalRepository.findAll(pageable)
            .map(journalMapper::toDto);
    }

    /**
     * Get one journal by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public JournalDTO findOne(Long id) {
        log.debug("Request to get Journal : {}", id);
        Journal journal = journalRepository.findOneWithEagerRelationships(id);
        return journalMapper.toDto(journal);
    }

    /**
     * Delete the  journal by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Journal : {}", id);
        journalRepository.delete(id);
        journalSearchRepository.delete(id);
    }

    /**
     * Search for the journal corresponding to the query.
     *
     * @param query    the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<JournalDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Journals for query {}", query);
        Page<Journal> result = journalSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(journalMapper::toDto);
    }

    @Override
    public void journalisationDossier(String commentaire, String utilisateur, Long action, DossierDTO dossierDTO) {
        log.debug("Request to dossier jounalisation  {}");
        JournalDTO journalDTO = new JournalDTO();
        journalDTO.setTimestamp(ZonedDateTime.now());
        journalDTO.setCommentaire(commentaire);
        journalDTO.setUtilisateur(utilisateur);

        SysActionUtilisateurDTO sysActionUtilisateurDTO = sysActionUtilisateurService.findOne(action);
        if (sysActionUtilisateurDTO != null) {
            journalDTO.setSysActionUtilisateurId(sysActionUtilisateurDTO.getId());
        }
        Journal journal = journalMapper.toEntity(journalDTO);
        journal.setUtilisateur(journalDTO.getUtilisateur());
        journal.setReference(dossierDTO.getReference());
        journalRepository.save(journal);

    }

    @Override
    public void journalisationConnection(String commentaire, String utilisateur, Long action) {
        log.debug("Request to loggin jounalisation  {}");
        JournalDTO journalDTO = new JournalDTO();
        journalDTO.setTimestamp(ZonedDateTime.now());
        journalDTO.setCommentaire(commentaire);
        journalDTO.setUtilisateur(utilisateur);
        SysActionUtilisateurDTO sysActionUtilisateurDTO = sysActionUtilisateurService.findOne(action);
        if (sysActionUtilisateurDTO != null) {
            journalDTO.setSysActionUtilisateurId(sysActionUtilisateurDTO.getId());
        }
        Journal journal = journalMapper.toEntity(journalDTO);
        journal.setUtilisateur(journalDTO.getUtilisateur());
        journalRepository.save(journal);


    }

    @Override
    public void journalisationPrestation(String commentaire, String utilisateur, Long action, PrestationDTO prestationDTO) {
        log.debug("Request to prestation jounalisation  {}");
        JournalDTO journalDTO = new JournalDTO();
        journalDTO.setTimestamp(ZonedDateTime.now());
        journalDTO.setCommentaire(commentaire);
        journalDTO.setUtilisateur(utilisateur);
        SysActionUtilisateurDTO sysActionUtilisateurDTO = sysActionUtilisateurService.findOne(action);
        if (sysActionUtilisateurDTO != null) {
            journalDTO.setSysActionUtilisateurId(sysActionUtilisateurDTO.getId());
        }
        Journal journal = journalMapper.toEntity(journalDTO);
        journal.setUtilisateur(journalDTO.getUtilisateur());
        journal.setReference(prestationDTO.getReference());
        journalRepository.save(journal);
    }

   
    @Override
    public void journalisationRefRemorqueurMotif(String commentaire, String utilisateur, Long action, RefRemorqueurDTO refRemorqueurDTO, Long[] motifs) {
        
    	
    	log.debug("Request to RefRemorqueur jounalisation motif Blocage  {}");
        Set<RefMotifDTO> refMotifs = new HashSet<>();
        for (Long motif : motifs) {
        refMotifs.add(refMotifService.findOne(motif));
        }
        JournalDTO journalDTO = new JournalDTO();
        journalDTO.setTimestamp(ZonedDateTime.now());
        journalDTO.setCommentaire(commentaire);
        journalDTO.setUtilisateur(utilisateur);
        journalDTO.setRefRemorqueurId(refRemorqueurDTO.getId());
        journalDTO.setMotifs(refMotifs);
        SysActionUtilisateurDTO sysActionUtilisateurDTO = sysActionUtilisateurService.findOne(action);
        if (sysActionUtilisateurDTO != null) {
            journalDTO.setSysActionUtilisateurId(sysActionUtilisateurDTO.getId());
        }
        Journal journal = journalMapper.toEntity(journalDTO);
        journal.setUtilisateur(journalDTO.getUtilisateur());
        journalRepository.save(journal);
    }
    
    @Override
    public void journalisationReparateurMotif(String commentaire, String utilisateur, Long action, ReparateurDTO reparateurDTO, Long[] motifs) {
    	log.debug("Request to Reparateur jounalisation motif Blocage  {}");
        Set<RefMotifDTO> refMotifs = new HashSet<>();
        for (Long motif : motifs) {
        refMotifs.add(refMotifService.findOne(motif));
        }
        JournalDTO journalDTO = new JournalDTO();
        journalDTO.setTimestamp(ZonedDateTime.now());
        journalDTO.setCommentaire(commentaire);
        journalDTO.setUtilisateur(utilisateur);
        journalDTO.setReparateurId(reparateurDTO.getId());
        journalDTO.setMotifs(refMotifs);
        SysActionUtilisateurDTO sysActionUtilisateurDTO = sysActionUtilisateurService.findOne(action);
        if (sysActionUtilisateurDTO != null) {
            journalDTO.setSysActionUtilisateurId(sysActionUtilisateurDTO.getId());
        }
        Journal journal = journalMapper.toEntity(journalDTO);
        journal.setUtilisateur(journalDTO.getUtilisateur());
        journalRepository.save(journal);
    }
 
    
    @Override
    public void journalisationMotifNonConfirmeDevis(String commentaire, String utilisateur, Long action, QuotationDTO quotationDTO, Long[] motifs) {
    	log.debug("Request to RefRemorqueur jounalisation motif Blocage  {}");
        Set<RefMotifDTO> refMotifs = new HashSet<>();
        for (Long motif : motifs) {
        refMotifs.add(refMotifService.findOne(motif));
        }
        JournalDTO journalDTO = new JournalDTO();
        journalDTO.setTimestamp(ZonedDateTime.now());
        journalDTO.setCommentaire(commentaire);
        journalDTO.setUtilisateur(utilisateur);
        journalDTO.setQuotationId(quotationDTO.getId());
        journalDTO.setMotifs(refMotifs);
        SysActionUtilisateurDTO sysActionUtilisateurDTO = sysActionUtilisateurService.findOne(action);
        if (sysActionUtilisateurDTO != null) {
            journalDTO.setSysActionUtilisateurId(sysActionUtilisateurDTO.getId());
        }
        Journal journal = journalMapper.toEntity(journalDTO);
        journal.setUtilisateur(journalDTO.getUtilisateur());
        journalRepository.save(journal);
    }
    
    
    @Override
    public void journalisationPrestationAVTMotif(String commentaire, String utilisateur, Long action, PrestationAvtDTO prestationAvtDTO, Long[] motifs) {
        log.debug("Request to prestation Avt jounalisation  {}");
        Set<RefMotifDTO> refMotifs = new HashSet<>();
        for (Long motif : motifs) {
            refMotifs.add(refMotifService.findOne(motif));
        }
        JournalDTO journalDTO = new JournalDTO();
        journalDTO.setTimestamp(ZonedDateTime.now());
        journalDTO.setCommentaire(commentaire);
        journalDTO.setUtilisateur(utilisateur);
        journalDTO.setMotifs(refMotifs);
        SysActionUtilisateurDTO sysActionUtilisateurDTO = sysActionUtilisateurService.findOne(action);
        if (sysActionUtilisateurDTO != null) {
            journalDTO.setSysActionUtilisateurId(sysActionUtilisateurDTO.getId());
        }
        Journal journal = journalMapper.toEntity(journalDTO);
        journal.setUtilisateur(journalDTO.getUtilisateur());
        journalRepository.save(journal);
    }

	@Override
	public void journalisationPrestationPEC(String commentaire, String utilisateur, Long action, Long[]  motifsJournalisation,
			PrestationPECDTO prestationPECDTO) {

		log.debug("journalisationPrestationPEC-->Request to prestationPEC jounalisation  {}");
        JournalDTO journalDTO = new JournalDTO();
        journalDTO.setTimestamp(ZonedDateTime.now());
        journalDTO.setCommentaire(commentaire);
        journalDTO.setUtilisateur(utilisateur);
        journalDTO.setPrestationId(prestationPECDTO.getId());
        Set<RefMotifDTO> setMotifs = new HashSet<RefMotifDTO>();
        if(motifsJournalisation != null) {
        	for (int i = 0; i < motifsJournalisation.length; i++) {
        		setMotifs.add(refMotifService.findOne(motifsJournalisation[i]));
        	}
        }
        
        journalDTO.setMotifs(setMotifs);

        try {
        	String IP = InetAddress.getLocalHost().getHostAddress();
            journalDTO.setIpaddress(IP);
		} catch (UnknownHostException e) {
			log.error("Error when getting IP Address", e);
 		}

        SysActionUtilisateurDTO sysActionUtilisateurDTO = sysActionUtilisateurService.findOne(action);
        if (sysActionUtilisateurDTO != null) {
            journalDTO.setSysActionUtilisateurId(sysActionUtilisateurDTO.getId());
        }
        Journal journal = journalMapper.toEntity(journalDTO);
        journal.setUtilisateur(journalDTO.getUtilisateur());
        journal.setReference(prestationPECDTO.getDossierReference());
        journalRepository.save(journal);		
		
	}

}
