package com.gaconnecte.auxilium.service.impl;

import com.gaconnecte.auxilium.service.AssureService;
import com.gaconnecte.auxilium.domain.Assure;
import com.gaconnecte.auxilium.repository.AssureRepository;
import com.gaconnecte.auxilium.repository.search.AssureSearchRepository;
import com.gaconnecte.auxilium.service.dto.AssureDTO;
import com.gaconnecte.auxilium.service.mapper.AssureMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Service Implementation for managing Assure.
 */
@Service
@Transactional
public class AssureServiceImpl implements AssureService{

    private final Logger log = LoggerFactory.getLogger(AssureServiceImpl.class);

    private final AssureRepository assureRepository;

    private final AssureMapper assureMapper;

    private final AssureSearchRepository assureSearchRepository;

    public AssureServiceImpl(AssureRepository assureRepository, AssureMapper assureMapper, AssureSearchRepository assureSearchRepository) {
        this.assureRepository = assureRepository;
        this.assureMapper = assureMapper;
        this.assureSearchRepository = assureSearchRepository;
    }

    /**
     * Save a assure.
     *
     * @param assureDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AssureDTO save(AssureDTO assureDTO) {
        log.debug("Request to save Assure : {}", assureDTO);
        Assure assure = assureMapper.toEntity(assureDTO);
        if (assure.getCompany().booleanValue()) {
            assure.setFullName(assure.getRaisonSociale());
        } else if (assure.getPrenom() != null && assure.getNom() != null) {
            assure.setFullName(assure.getPrenom() + ' ' + assure.getNom());
        } else if (assure.getPrenom() != null) {
            assure.setFullName(assure.getPrenom());
        } else {
            assure.setFullName(assure.getNom());
        }

        assure = assureRepository.save(assure);
        AssureDTO result = assureMapper.toDto(assure);
        assureSearchRepository.save(assure);
        return result;
    }

    /**
     *  Get all the assures.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AssureDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Assures");
        return assureRepository.findAll(pageable)
            .map(assureMapper::toDto);
    }

    /**
     *  Get one assure by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public AssureDTO findOne(Long id) {
        log.debug("Request to get Assure : {}", id);
        Assure assure = assureRepository.findOne(id);
        return assureMapper.toDto(assure);
    }

    /**
     *  Delete the  assure by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Assure : {}", id);
        assureRepository.delete(id);
        assureSearchRepository.delete(id);
    }

    /**
     * Search for the assure corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AssureDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Assures for query {}", query);
        Page<Assure> result = assureSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(assureMapper::toDto);
    }

    /**
     * Count the number of assure
     * @return the number of assure
     */
    @Override
    public Long getCountAssure(String debut, String fin) {
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
        return assureRepository.countAssure(convertedD, convertedF);
    }


}

