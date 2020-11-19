package com.gaconnecte.auxilium.service.impl;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.gaconnecte.auxilium.domain.Devis;
import com.gaconnecte.auxilium.domain.Estimation;
import com.gaconnecte.auxilium.domain.enumeration.EtatDevis;
import com.gaconnecte.auxilium.repository.DevisRepository;
import com.gaconnecte.auxilium.repository.search.DevisSearchRepository;
import com.gaconnecte.auxilium.service.DevisService;
import com.gaconnecte.auxilium.service.dto.DevisDTO;
import com.gaconnecte.auxilium.service.mapper.DevisMapper;
import org.apache.commons.collections4.CollectionUtils;

/**
 * Service Implementation for managing Devis.
 */

@Service
@Transactional
public class DevisServiceImpl implements DevisService{

    private final Logger log = LoggerFactory.getLogger(DevisServiceImpl.class);

    private final DevisRepository devisRepository;
  
   
    
    private final DevisMapper devisMapper;
   
    private final DevisSearchRepository devisSearchRepository;
    public DevisServiceImpl(DevisRepository devisRepository, DevisMapper devisMapper, DevisSearchRepository devisSearchRepository) {
        this.devisRepository = devisRepository;
        this.devisMapper = devisMapper;
        this.devisSearchRepository = devisSearchRepository;
     
    }

    /**
     * Save a devis.
     *
     * @param devisDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public DevisDTO save(DevisDTO devisDTO) {
        log.debug("Request to save Devis : {}", devisDTO);
        ZonedDateTime date = ZonedDateTime.now();
        Devis devis = devisMapper.toEntity(devisDTO);
        devis.setDateGeneration(date);
        //devis.setEtatDevis(EtatDevis.En_cours);
        devis = devisRepository.save(devis);
        DevisDTO result = devisMapper.toDto(devis);
        result.setDateGeneration(date);
        //result.setEtatDevis(EtatDevis.En_cours);
        devisSearchRepository.save(devis);
        return result;
    }

     /** Bug750
     * Save a devis.
     *
     * @param devisDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public DevisDTO update(DevisDTO devisDTO) {
        log.debug("Request to save Devis : {}", devisDTO);
        
        Devis devis = devisMapper.toEntity(devisDTO);
        devis = devisRepository.save(devis);
        
        DevisDTO result = devisMapper.toDto(devis);
        devisSearchRepository.save(devis);
        return result;
    }

    /**
     * soumettre a devis.
     *
     * @param devisDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public DevisDTO soumettre(Long id) {

         log.debug("Request to accepter Devis : {}", id);
        Devis devis = devisRepository.findOne(id);
        devis.setEtatDevis(EtatDevis.Soumis);
        devis = devisRepository.save(devis);
        DevisDTO result = devisMapper.toDto(devis);
        devisSearchRepository.save(devis);
        return result;


        /*log.debug("Request to soumettre Devis : {}", devisDTO);
        ZonedDateTime date = ZonedDateTime.now();
        Devis devis = devisMapper.toEntity(devisDTO);
        devis.setDateGeneration(date);
        devis.setEtatDevis(EtatDevis.Soumis);
        devis = devisRepository.save(devis);
        DevisDTO result = devisMapper.toDto(devis);
        result.setDateGeneration(date);
        result.setEtatDevis(EtatDevis.Soumis);
        devisSearchRepository.save(devis);
        return result;*/
    }

    /**
     * accepter a devis.
     *
     * @param devisDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public DevisDTO accepterDevis(Long id) {
        log.debug("Request to accepter Devis : {}", id);
        Devis devis = devisRepository.findOne(id);
        devis.setEtatDevis(EtatDevis.Accepte);
        devis = devisRepository.save(devis);
        DevisDTO result = devisMapper.toDto(devis);
        devisSearchRepository.save(devis);
        return result;
    }

    /**
     * valider a devis.
     *
     * @param devisDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public DevisDTO validerDevis(Long id) {
        log.debug("Request to valider Devis : {}", id);
        Devis devis = devisRepository.findOne(id);
        devis.setEtatDevis(EtatDevis.Valide);
        devis = devisRepository.save(devis);
        DevisDTO result = devisMapper.toDto(devis);
        devisSearchRepository.save(devis);
        return result;
    }

    /**
     *  Get all the devis.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DevisDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Devis");
        return devisRepository.findAll(pageable)
            .map(devisMapper::toDto);
    }

    /**
     *  Get one devis by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public DevisDTO findOne(Long id) {
        log.debug("Request to get Devis : {}", id);
        Devis devis = devisRepository.findOne(id);
        return devisMapper.toDto(devis);
    }

    /**
     *  Get one devis by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public DevisDTO findDevisSoumis(Long id) {
        log.debug("Request to get Devis : {}", id);  
        List<Devis> devis = devisRepository.findDevisSoumisByPrestation(id);
           if (CollectionUtils.isNotEmpty(devis)) {
                Devis result = devis.get(0);
                return devisMapper.toDto(result);
            } else
               return null;

            }

            /**
     *  Get one devis by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public DevisDTO findDevisValideElseSoumis(Long id) {
        log.debug("Request to get Devis : {}", id);  
        List<Devis> devisValide = devisRepository.findDevisValideByPrestation(id);
           if (CollectionUtils.isNotEmpty(devisValide)) {
                Devis result = devisValide.get(0);
                return devisMapper.toDto(result);
            } else {
               List<Devis> devisSoumis = devisRepository.findDevisSoumisByPrestation(id);
                if (CollectionUtils.isNotEmpty(devisSoumis)) {
                Devis result = devisSoumis.get(0);
                return devisMapper.toDto(result);
            } else
               return null;
            }}

     /**
     *  Get one devis by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public DevisDTO findDevisAccepteOuValideOuRefuse(Long id) {
        log.debug("Request to get Devis : {}", id);
        List<Devis> devis = devisRepository.findDevisAccepteOuValideOuRefuse(id);
           if (CollectionUtils.isNotEmpty(devis)) {
                Devis result = devis.get(0);
                return devisMapper.toDto(result);
            } else
               return null;

            }

    /**
     *  Get one devis by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public DevisDTO findDevisAccepte(Long id) {
        log.debug("Request to get Devis : {}", id);
        List<Devis> devis = devisRepository.findDevisAccepteByPrestation(id);
           if (CollectionUtils.isNotEmpty(devis)) {
                Devis result = devis.get(0);
                return devisMapper.toDto(result);
            } else
               return null;

            }

             /**
     *  Get one devis by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public DevisDTO findDevisFacture(Long id) {
        log.debug("Request to get Devis : {}", id);
        List<Devis> devis = devisRepository.findDevisFactureByPrestation(id);
           if (CollectionUtils.isNotEmpty(devis)) {
                Devis result = devis.get(0);
                return devisMapper.toDto(result);
            } else
               return null;

            }

                    /**
     *  Get one devis by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public DevisDTO getGenereBonSortieByPrestation(Long id) {
        log.debug("Request to get Devis : {}", id);
        List<Devis> devis = devisRepository.findDevisGenereBonSortieByPrestation(id);
           if (CollectionUtils.isNotEmpty(devis)) {
                Devis result = devis.get(0);
                return devisMapper.toDto(result);
            } else
               return null;

            }
                            /**
     *  Get one devis by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public DevisDTO getDevisRefusAferFactureByPrestation(Long id) {
        log.debug("Request to get Devis : {}", id);
        List<Devis> devis = devisRepository.findDevisRefusAferFactureByPrestation(id);
           if (CollectionUtils.isNotEmpty(devis)) {
                Devis result = devis.get(0);
                return devisMapper.toDto(result);
            } else
               return null;

            }

    /**
     *  Get one devis by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public DevisDTO findDevisValide(Long id) {
        log.debug("Request to get Devis : {}", id);
       List<Devis> devis = devisRepository.findDevisValideByPrestation(id);
           if (CollectionUtils.isNotEmpty(devis)) {
                Devis result = devis.get(0);
                return devisMapper.toDto(result);
            } else
               return null;

            }

    /**
     *  Get one devis by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public DevisDTO findLastDevisSauvegarde(Long id) {
        log.debug("Request to get Devis : {}", id);
          List<Devis> devis = devisRepository.findDevisSauvegardeByPrestation(id);
           if (CollectionUtils.isNotEmpty(devis)) {
                Devis result = devis.get(0);
                return devisMapper.toDto(result);
            } else
               return null;

            }


    /**
     *  Delete the  devis by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Devis : {}", id);
        devisRepository.delete(id);
        devisSearchRepository.delete(id);
    }

    /**
     * Search for the devis corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DevisDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Devis for query {}", query);
        Page<Devis> result = devisSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(devisMapper::toDto);
    }

     @Override
    public List<DevisDTO> findDevisByPrestation(Long prestationId) {
        log.debug("Request to get  all Devis by prestationPEC");
        List<Devis> devis = devisRepository.findDevisByPrestation(prestationId);
        return devisMapper.toDto(devis);
    }

    @Override
    public DevisDTO findQuotationByPrestation(Long prestationId) {
        log.debug("Request to get  Quotation by prestationPEC");
        Devis devis = devisRepository.findQuotationByPrestation(prestationId);
        return devisMapper.toDto(devis);
    }

    @Override
    public DevisDTO findLastDevisVersion(Long prestationId) {
        log.debug("Request to get  last devis version: {}", prestationId);
        Devis lastDevis = null;
        DevisDTO last = null;
        List<Devis> devis = new ArrayList();
        devis = devisRepository.findDevisByPrestation(prestationId);
        if (CollectionUtils.isNotEmpty(devis)) {
        lastDevis = devis.get(0);
        for(int i=1; i<devis.size(); i++) {
          if(devis.get(i).getDateGeneration().isAfter(lastDevis.getDateGeneration())) {
            lastDevis =  devis.get(i);
        }} 
        last = devisMapper.toDto(lastDevis);
        } 
         if (last != null) {return last;} else {return null;}
        
    
    }

     /**
     * refuser a devisDTO.
     *
     * @param devisDTO the entity to refuser
     * @return the persisted entity
     */

     @Override
    public DevisDTO refuserDevis(Long id) {
        log.debug("Request to save devis : {}", id);
        Devis devis = devisRepository.findOne(id);
        devis.setEtatDevis(EtatDevis.Refuse);
        devis = devisRepository.save(devis);
        DevisDTO result = devisMapper.toDto(devis);
        return result;
    }

    /**
     * Count the number of quotation
     * @return the number of quotation
     */
    @Override
    public Long getCountQuotation(String debut, String fin) {

        String dateF = fin + "/31/" + debut;
        String dateD = fin + "/01/" + debut;
        String dateDToConvert = debut + "-" + fin + "-01";

        
        /* get end day from a given month*/
        LocalDate convertedDateF = LocalDate.parse(dateD, DateTimeFormatter.ofPattern("M/d/yyyy"));
        log.debug("convertedDateF: ", convertedDateF);
        convertedDateF = convertedDateF.withDayOfMonth(
                                        convertedDateF.getMonth().length(convertedDateF.isLeapYear()));
        log.debug("convertedDateF after convert: ", convertedDateF);        
        

        /*
        LocalDate convertedDateD = LocalDate.parse(dateD, DateTimeFormatter.ofPattern("M/d/yyyy"));
        convertedDateD = convertedDateD.withDayOfMonth(
                                        convertedDateD.getMonth().length(convertedDateD.isLeapYear()));
        */
        //System.out.println(convertedDate);

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate convertedDateD = LocalDate.parse(dateDToConvert, fmt);

        ZonedDateTime convertedD = convertedDateD.atStartOfDay(ZoneId.systemDefault());
        ZonedDateTime convertedF = convertedDateF.atStartOfDay(ZoneId.systemDefault());

        return devisRepository.countDevis(convertedD, convertedF);
    }

	@Override
	public DevisDTO validerGesDevis(Long id) {
		log.debug("Request to valider Devis : {}", id);
        Devis devis = devisRepository.findOne(id);
        devis.setEtatDevis(EtatDevis.ValideGestionnaire);
        devis = devisRepository.save(devis);
        DevisDTO result = devisMapper.toDto(devis);
        devisSearchRepository.save(devis);
        return result;
	}

	@Override
	public DevisDTO findEtatLastDevisByPrestation(Long id) {
		
		List<Devis> devis =  devisRepository.findLastDevisBySinister(id);
		
		 if (CollectionUtils.isNotEmpty(devis)) {
             Devis result = devis.get(0);
             return devisMapper.toDto(result);
         } else
            return null;
		 
		/*log.debug("etat last devis back-----------------"+devis.getEtatDevis());
		System.out.println("etat last devis back-----------------"+devis.getEtatDevis());
		DevisDTO result = devisMapper.toDto(devis);
		
		return result;*/
	}

	@Override
	@Transactional(readOnly = true)
	public DevisDTO findLastDevisSauvegardeRefuseValidGes(Long id) {
		
          List<Devis> devis = devisRepository.findDevisByPrestation(id);
          Devis devisReparateur = null ;
          if (CollectionUtils.isNotEmpty(devis)) {
        	  for(Devis dev:devis){
               if(dev.getQuotationStatus() != null) {
 				if(dev.getQuotationStatus().getId().equals(1L)){
 					devisReparateur = dev;
 				}
        	  }
 	         }
              
         	
          }
 			
 				
		return devisMapper.toDto(devisReparateur);
 			
          
	}

	@Override
	@Transactional(readOnly = true)
	public DevisDTO findLastDevisValidGes(Long id) {
		
		 log.debug("Request to get Last Devis ValidGes: {}", id);
         List<Devis> devis = devisRepository.findLastDevisValidGes(id);
          if (CollectionUtils.isNotEmpty(devis)) {
               Devis result = devis.get(0);
               
               log.debug("Request to get Last Devis ValidGes etat: {}", result.getEtatDevis());
               return devisMapper.toDto(result);
           } else
              return null;
	}

	

  
}
