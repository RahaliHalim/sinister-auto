package com.gaconnecte.auxilium.repository;
import com.gaconnecte.auxilium.domain.view.ViewBonification;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

public interface ViewBonificationRepository extends JpaRepository<ViewBonification, Long> {

	
	 @Query("SELECT sp from ViewBonification sp WHERE sp.dateOuverture <= :endDate AND sp.dateOuverture >= :startDate")
	    List<ViewBonification> findAllByDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

	
	
}
