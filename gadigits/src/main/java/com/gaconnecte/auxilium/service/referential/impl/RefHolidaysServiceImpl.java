package com.gaconnecte.auxilium.service.referential.impl;

import com.gaconnecte.auxilium.domain.referential.RefHolidays;
import com.gaconnecte.auxilium.repository.referential.RefHolidaysRepository;
import com.gaconnecte.auxilium.service.referential.dto.RefHolidaysDTO;
import com.gaconnecte.auxilium.service.referential.RefHolidaysService;
import com.gaconnecte.auxilium.service.referential.mapper.RefHolidaysMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.collections.CollectionUtils;


/**
 * Service Implementation for managing RefHolidays.
 */
@Service
@Transactional
public class RefHolidaysServiceImpl implements RefHolidaysService {

    private final Logger log = LoggerFactory.getLogger(RefHolidaysServiceImpl.class);

    private final RefHolidaysRepository refHolidaysRepository;

    private final RefHolidaysMapper refHolidaysMapper;

    public RefHolidaysServiceImpl(RefHolidaysRepository refHolidaysRepository, RefHolidaysMapper refHolidaysMapper) {
        this.refHolidaysRepository = refHolidaysRepository;
        this.refHolidaysMapper = refHolidaysMapper;
    }

    /**
     * Save a refHolidays.
     *
     * @param refHolidaysDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RefHolidaysDTO save(RefHolidaysDTO refHolidaysDTO) {
        log.debug("Request to save RefHolidays : {}", refHolidaysDTO);
        RefHolidays refHolidays = refHolidaysMapper.toEntity(refHolidaysDTO);
        refHolidays = refHolidaysRepository.save(refHolidays);
        return refHolidaysMapper.toDto(refHolidays);
    }

    /**
     *  Get all the refHolidayss.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<RefHolidaysDTO> findAll() {
        log.debug("Request to get all RefHolidayss");
        return refHolidaysRepository.findAll().stream().map(refHolidaysMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the refHolidays.
     *
     *  @param set the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Set<RefHolidaysDTO> findByLabelOrDate(RefHolidaysDTO refHolidaysDTO) {
        log.debug("Request to get all RefHolidays");
        Set<RefHolidays> holidays = refHolidaysRepository.findByLabelOrDate(refHolidaysDTO.getLabel(), refHolidaysDTO.getDate());
        if(CollectionUtils.isNotEmpty(holidays)) {
        	return holidays.stream().map(refHolidaysMapper::toDto).collect(Collectors.toSet());
        }
        return new HashSet<>();
    }
    
    /**
     *  Get one refHolidays by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public RefHolidaysDTO findOne(Long id) {
        log.debug("Request to get RefHolidays : {}", id);
        RefHolidays refHolidays = refHolidaysRepository.findOne(id);
        return refHolidaysMapper.toDto(refHolidays);
    }


/**
     *  Get one refHolidays by date.
     *
     *  @param date the date of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public RefHolidaysDTO findByDate(LocalDate date) {
        log.debug("Request to get RefHolidays : {}", date);
        RefHolidays refHolidays = refHolidaysRepository.findByDate(date);
        System.out.println(refHolidays);
        return refHolidaysMapper.toDto(refHolidays);
    }

    /**
     *  Delete the  refHolidays by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RefHolidays : {}", id);
        refHolidaysRepository.delete(id);
    }
}
