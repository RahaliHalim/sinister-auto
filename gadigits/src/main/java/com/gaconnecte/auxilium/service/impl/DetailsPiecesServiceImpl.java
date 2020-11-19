package com.gaconnecte.auxilium.service.impl;

import com.gaconnecte.auxilium.service.DetailsPiecesService;
import com.gaconnecte.auxilium.domain.ComplementaryQuotation;
import com.gaconnecte.auxilium.domain.DetailsPieces;
import com.gaconnecte.auxilium.domain.PrimaryQuotation;
import com.gaconnecte.auxilium.domain.Quotation;
import com.gaconnecte.auxilium.domain.SinisterPec;
import com.gaconnecte.auxilium.repository.DetailsPiecesRepository;
import com.gaconnecte.auxilium.repository.PrimaryQuotationRepository;
import com.gaconnecte.auxilium.repository.QuotationRepository;
import com.gaconnecte.auxilium.repository.SinisterPecRepository;
import com.gaconnecte.auxilium.repository.search.DetailsPiecesSearchRepository;
import com.gaconnecte.auxilium.service.dto.ComplementaryQuotationDTO;
import com.gaconnecte.auxilium.service.dto.DetailsPiecesDTO;
import com.gaconnecte.auxilium.service.dto.SinisterPecDTO;
import com.gaconnecte.auxilium.service.mapper.DetailsPiecesMapper;

import io.swagger.annotations.ApiParam;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing DetailsPieces.
 */
@Service
@Transactional
public class DetailsPiecesServiceImpl implements DetailsPiecesService{

    private final Logger log = LoggerFactory.getLogger(DetailsPiecesServiceImpl.class);

    private final DetailsPiecesRepository detailsPiecesRepository;

    private final DetailsPiecesMapper detailsPiecesMapper;

    private final DetailsPiecesSearchRepository detailsPiecesSearchRepository;
    
    private final SinisterPecRepository sinisterPecRepository;
    
    @Autowired
    PrimaryQuotationRepository primaryQuotationRepository;

    public DetailsPiecesServiceImpl(DetailsPiecesRepository detailsPiecesRepository, DetailsPiecesMapper detailsPiecesMapper, DetailsPiecesSearchRepository detailsPiecesSearchRepository, SinisterPecRepository sinisterPecRepository) {
        this.detailsPiecesRepository = detailsPiecesRepository;
        this.detailsPiecesMapper = detailsPiecesMapper;
        this.detailsPiecesSearchRepository = detailsPiecesSearchRepository;
        this.sinisterPecRepository = sinisterPecRepository;
    }

    /**
     * Save a detailsPieces.
     *
     * @param detailsPiecesDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public DetailsPiecesDTO save(DetailsPiecesDTO detailsPiecesDTO) {
        log.debug("Request to save DetailsPieces : {}", detailsPiecesDTO);
        DetailsPieces detailsPieces = detailsPiecesMapper.toEntity(detailsPiecesDTO);
        detailsPieces = detailsPiecesRepository.save(detailsPieces);
        DetailsPiecesDTO result = detailsPiecesMapper.toDto(detailsPieces);
        detailsPiecesSearchRepository.save(detailsPieces);
        return result;
    }

    /**
     *  Get all the detailsPieces.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DetailsPiecesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DetailsPieces");
        return detailsPiecesRepository.findAll(pageable)
            .map(detailsPiecesMapper::toDto);
    }
    
    /**
     *  Get all the detailsPieces.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<DetailsPiecesDTO> findAllByDevisAndType(Long devisId, Long typeId, Boolean isMo) {
        log.debug("Request to get all DetailsPieces");
        List<DetailsPieces> detailsPieces = detailsPiecesRepository.findDetailsMoByDevisAndType(devisId, typeId, isMo);
    	if (CollectionUtils.isNotEmpty(detailsPieces)) {
			return detailsPieces.stream().map(detailsPiecesMapper::toDto).collect(Collectors.toCollection(ArrayList::new));
		}
		return new ArrayList<>();
        
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<DetailsPiecesDTO> findDetailsPiecesBySinisterPecId(Long sinisterPecId) {
        log.debug("Request to get all DetailsPieces");
        Set<DetailsPieces> detailsPieces = detailsPiecesRepository.findDetailsPiecesBySinisterPecId(sinisterPecId);
    	if (CollectionUtils.isNotEmpty(detailsPieces)) {
			return detailsPieces.stream().map(detailsPiecesMapper::toDto).collect(Collectors.toCollection(ArrayList::new));
		}
		return new ArrayList<>();
        
    }
 
    
    @Override
    @Transactional(readOnly = true)
    public List<DetailsPiecesDTO> findAllByDevisAndTypeOther(Long devisId, Long typeId, Boolean isMo) {
        log.debug("Request to get all DetailsPieces");
        List<DetailsPieces> detailsPieces = detailsPiecesRepository.findDetailsMoByDevisAndTypeOther(devisId, typeId, isMo);
    	if (CollectionUtils.isNotEmpty(detailsPieces)) {
			return detailsPieces.stream().map(detailsPiecesMapper::toDto).collect(Collectors.toCollection(ArrayList::new));
		}
		return new ArrayList<>();
        
    }
    
    
    @Override
    @Transactional(readOnly = true)
    public Boolean findAllByDevis(Long devisId) {
        log.debug("Request to get all DetailsPieces");
        List<DetailsPieces> detailsPieces = detailsPiecesRepository.findDetailsMoByDevis(devisId);
        Boolean isDeleted = false;
        for(DetailsPieces detPiece : detailsPieces) {
        	detPiece.setQuotation(null);
        	detailsPiecesRepository.save(detPiece);
        }
        if(detailsPieces.size() == 0) {
        	isDeleted = true;
        }
        return isDeleted;
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<DetailsPiecesDTO> findAllByQuotationMPAndType(Long devisId, Long typeId, Boolean isMo) {
        log.debug("Request to get all DetailsPieces");
        List<DetailsPieces> detailsPieces =  detailsPiecesRepository.findDetailsMoByQuotationMPAndType(devisId, typeId, isMo);
        if (CollectionUtils.isNotEmpty(detailsPieces)) {
			return detailsPieces.stream().map(detailsPiecesMapper::toDto).collect(Collectors.toCollection(ArrayList::new));
		}
		return new ArrayList<>();
    }
    /**
     *  Get one detailsPieces by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public DetailsPiecesDTO findOne(Long id) {
        log.debug("Request to get DetailsPieces : {}", id);
        DetailsPieces detailsPieces = detailsPiecesRepository.findOne(id);
        return detailsPiecesMapper.toDto(detailsPieces);
    }
    
    /**
     *  Get one detailsPiecesModified .
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public DetailsPiecesDTO findLineModified(Long quotationId,Long lineModified) {
        log.debug("Request to get DetailsPieces Modified : {}", quotationId , lineModified);
        DetailsPieces detailsPieces = detailsPiecesRepository.findLineModified(quotationId,lineModified);
        return detailsPiecesMapper.toDto(detailsPieces);
    }

    /**
     *  Delete the  detailsPieces by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DetailsPieces : {}", id);
        detailsPiecesRepository.delete(id);
        detailsPiecesSearchRepository.delete(id);
    }
    
    @Override
    public void deleteByDevis(Long id) {
        List<DetailsPieces> detailsPieces = detailsPiecesRepository.findDetailsMoByDevis(id);
        for(DetailsPieces detPiece : detailsPieces) {
        	detailsPiecesRepository.delete(detPiece.getId());
        }
    }
    
    @Override
    public void deleteByDevisComp(Long id) {
        log.debug("Request to delete DetailsPieces : {}", id);
        SinisterPec sinPec = sinisterPecRepository.findOne(id);
        for(ComplementaryQuotation comp : sinPec.getListComplementaryQuotation()) {
        	List<DetailsPieces> detailsPieces = detailsPiecesRepository.findDetailsMoByDevis(comp.getId());
        	for(DetailsPieces detPiece : detailsPieces) {
            	detailsPiecesRepository.delete(detPiece.getId());
            }
        }
    }

    /**
     * Search for the detailsPieces corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DetailsPiecesDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of DetailsPieces for query {}", query);
        Page<DetailsPieces> result = detailsPiecesSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(detailsPiecesMapper::toDto);
    }

    @Override
    public void deleteDetailsPiecesDevisAnnule(Long sinisterPecId) {
        Set<DetailsPieces> detailsPieces = detailsPiecesRepository.findDetailsPiecesAnnuleeBySinisterPecId(sinisterPecId);
        for(DetailsPieces detPiece : detailsPieces) {
        	detailsPiecesRepository.delete(detPiece.getId());
        }
    }
    
    @Override
    public DetailsPiecesDTO fusion(Long primaryQuotationId, Long sinisterPecId) {
        log.debug("Request to save quotation : {}", primaryQuotationId);
        Set<DetailsPieces> detailsPiecesAF = detailsPiecesRepository.findDetailsPiecesBySinisterPecId(sinisterPecId);
        PrimaryQuotation quotation = primaryQuotationRepository.findOne(primaryQuotationId);
        List<DetailsPieces> listDetailsPieces  = quotation.getListPieces();
        for(DetailsPieces detailsPieces : detailsPiecesAF) {
            DetailsPieces d = new DetailsPieces();
            
            d.setQuantite(detailsPieces.getQuantite());
            d.setNombreHeures(detailsPieces.getNombreHeures());
            d.setReference(detailsPieces.getReference());
            d.setVetuste(detailsPieces.getVetuste());
            d.setInsuredParticipation(detailsPieces.getInsuredParticipation());
            d.setHTVetuste(detailsPieces.getHTVetuste());
            d.setTTCVetuste(detailsPieces.getTTCVetuste());
            d.setObservationExpert(detailsPieces.getObservationExpert());
            d.setTypePA(detailsPieces.getTypePA());
            d.setNombreMOEstime(detailsPieces.getNombreMOEstime());
            d.setDesignation(detailsPieces.getDesignation());
            d.setObservation(detailsPieces.getObservation());
            d.setPrixUnit(detailsPieces.getPrixUnit());
            d.setTva(detailsPieces.getTva());
            d.setVatRate(detailsPieces.getVatRate());
            d.setAmountVat(detailsPieces.getAmountVat());
            d.setTotalHt(detailsPieces.getTotalHt());
            d.setTotalTtc(detailsPieces.getTotalTtc());
            d.setFournisseur(detailsPieces.getFournisseur());
            d.setTypeIntervention(detailsPieces.getTypeIntervention());
            d.setNaturePiece(detailsPieces.getNaturePiece());
            d.setObservationRepairer(detailsPieces.getObservationRepairer());
            d.setIsMo(detailsPieces.getIsMo());
            d.setModifiedLine(detailsPieces.getModifiedLine());
            d.setIsModified(detailsPieces.getIsModified());
            d.setIsComplementary(detailsPieces.getIsComplementary());
            d.setQuotation(quotation);
       
            listDetailsPieces.add(d);
        }
        quotation.setListPieces(listDetailsPieces);
        primaryQuotationRepository.save(quotation);
        return new DetailsPiecesDTO();
    }

    

}
