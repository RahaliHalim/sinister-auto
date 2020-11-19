package com.gaconnecte.auxilium.service.impl;

import com.gaconnecte.auxilium.service.PrestationAvtService;
import com.gaconnecte.auxilium.domain.PrestationAvt;
import com.gaconnecte.auxilium.repository.PrestationAvtRepository;
import com.gaconnecte.auxilium.repository.RefRemorqueurRepository;
import com.gaconnecte.auxilium.service.dto.PrestationAvtDTO;
import com.gaconnecte.auxilium.service.mapper.PrestationAvtMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.gaconnecte.auxilium.domain.enumeration.TypePrestation;
import java.time.ZonedDateTime;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import com.gaconnecte.auxilium.domain.enumeration.EtatDossierRmq;


/**
 * Service Implementation for managing PrestationAvt.
 */
@Service
@Transactional
public class PrestationAvtServiceImpl implements PrestationAvtService{

    private final Logger log = LoggerFactory.getLogger(PrestationAvtServiceImpl.class);

    private final PrestationAvtRepository prestationAvtRepository;

    private final RefRemorqueurRepository refRemorqueurRepository;

    private final PrestationAvtMapper prestationAvtMapper;

    public PrestationAvtServiceImpl(PrestationAvtRepository prestationAvtRepository, PrestationAvtMapper prestationAvtMapper, 
        RefRemorqueurRepository refRemorqueurRepository) {
        this.prestationAvtRepository = prestationAvtRepository;
        this.prestationAvtMapper = prestationAvtMapper;
        this.refRemorqueurRepository = refRemorqueurRepository;
    }
   
    /**
     * Save a prestationAvt.
     *
     * @param prestationAvtDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PrestationAvtDTO save(PrestationAvtDTO prestationAvtDTO) {
        log.debug("Request to save PrestationAvt : {}", prestationAvtDTO);
        ZonedDateTime date = ZonedDateTime.now();
        
        PrestationAvt prestationAvt = prestationAvtMapper.toEntity(prestationAvtDTO);
        if(prestationAvt.getTugArrivalDate() != null && prestationAvt.getInsuredArrivalDate() != null){
           prestationAvt.setEtat(EtatDossierRmq.Cloturer);
           prestationAvt.setDateCloture(date);
        }else{
            prestationAvt.setEtat(EtatDossierRmq.Encours);
        }
        prestationAvt = prestationAvtRepository.save(prestationAvt);
        PrestationAvtDTO result = prestationAvtMapper.toDto(prestationAvt);
        return result;
    }
 
    /**
     *  Get all the prestationAvts.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PrestationAvtDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PrestationAvts");
        return prestationAvtRepository.findAll(pageable)
            .map(prestationAvtMapper::toDto);
    }

    /**
     *  Get one prestationAvt by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public PrestationAvtDTO findOne(Long id) {
        log.debug("Request to get PrestationAvt : {}", id);
        PrestationAvt prestationAvt = prestationAvtRepository.getOne(id);
        return prestationAvtMapper.toDto(prestationAvt);
    }

    /**
     *  Delete the  prestationAvt by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PrestationAvt : {}", id);
        prestationAvtRepository.delete(id);
    }

    /**
     *  Get one prestationAvt by prestationId.
     *
     *  @param prestationId the prestationId of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public PrestationAvtDTO findByPrestation( Long prestationId) {
        log.debug("Request to get PrestationAvt : {}", prestationId);
        PrestationAvt prestationAvt = prestationAvtRepository.findByPrestation(prestationId);
        return prestationAvtMapper.toDto(prestationAvt);
    }

    

    
     @Override
    public List<PrestationAvtDTO> findAllPrestationByType(TypePrestation typePrestation) {
        log.debug("Request to get  PrestationByType");
        List<PrestationAvt> prestationAvt = prestationAvtRepository.findPrestationAvtByType(typePrestation);
        return prestationAvtMapper.toDto(prestationAvt);
    }

    /**
     * Count the number of avt prestation
     * @return the number of avt prestation
     */
    @Override
    public Long getCountPrestationAvt(String debut, String fin) {

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

        return prestationAvtRepository.countPrestationAvt(convertedD, convertedF);
    }
    /**
     */
    @Override
    public PrestationAvtDTO annulerPrestation(Long id){

        log.debug("Request to annuler Prestation AVT : {}", id);
        
        ZonedDateTime date = ZonedDateTime.now();
        PrestationAvt prestationAvt  = prestationAvtRepository.findOne(id);
        prestationAvt.setDateDerniereMaj(date);
        prestationAvt.setEtat(EtatDossierRmq.Annuler);
        prestationAvt = prestationAvtRepository.save(prestationAvt);
        PrestationAvtDTO result = prestationAvtMapper.toDto(prestationAvt);

        return result;
    }

    public List<PrestationAvtDTO> findByDossier(Long dossierId) {
       log.debug("Request to get PrestaionAvt : {}", dossierId);
       List<PrestationAvt> prestations = prestationAvtRepository.findPrestationAvtsByDossier(dossierId);
       return prestationAvtMapper.toDto(prestations);

   }

}