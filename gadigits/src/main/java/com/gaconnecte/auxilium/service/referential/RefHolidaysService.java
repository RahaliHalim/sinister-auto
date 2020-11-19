package com.gaconnecte.auxilium.service.referential;

import com.gaconnecte.auxilium.service.referential.dto.RefHolidaysDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/**
 * Service Interface for managing RefHolidays.
 */
public interface RefHolidaysService {

    /**
     * Save a refHolidays.
     *
     * @param refHolidaysDTO the entity to save
     * @return the persisted entity
     */
    RefHolidaysDTO save(RefHolidaysDTO refHolidaysDTO);

    /**
     *  Get all the refHolidays.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    List<RefHolidaysDTO> findAll();

    /**
     * Get all the refHolidays.
     *
     *  @param refHolidaysDTO 
     *  @return the list of entities
     */
    Set<RefHolidaysDTO> findByLabelOrDate(RefHolidaysDTO refHolidaysDTO);

    /**
     *  Get the "id" refHolidays.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    RefHolidaysDTO findOne(Long id);

    /**
     *  Delete the "id" refHolidays.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     *  Get one refHolidays by date.
     *
     *  @param date the id of the entity
     *  @return the entity
     */
    
     RefHolidaysDTO findByDate(LocalDate date); 
         
    
}
