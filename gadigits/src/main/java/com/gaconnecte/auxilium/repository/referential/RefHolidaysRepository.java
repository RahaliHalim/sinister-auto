package com.gaconnecte.auxilium.repository.referential;

import com.gaconnecte.auxilium.domain.referential.RefHolidays;
import org.springframework.stereotype.Repository;

import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Spring Data JPA repository for the Holidays entity.
 */
@Repository
public interface RefHolidaysRepository extends JpaRepository<RefHolidays, Long> {

    @Query("select distinct holidays from RefHolidays holidays where holidays.date =:date")
    RefHolidays findByDate(@Param("date") LocalDate date);

    @Query("select distinct holidays from RefHolidays holidays where holidays.label =:label or holidays.date =:date")
    Set<RefHolidays> findByLabelOrDate(@Param("label") String label, @Param("date") LocalDate date);
    
}
