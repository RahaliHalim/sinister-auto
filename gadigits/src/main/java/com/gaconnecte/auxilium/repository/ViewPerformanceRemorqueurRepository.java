package com.gaconnecte.auxilium.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gaconnecte.auxilium.domain.view.ViewPerformanceRemorqueur;


	
	@Repository
	public interface ViewPerformanceRemorqueurRepository extends JpaRepository<ViewPerformanceRemorqueur, Long> {
	    @Query("SELECT sp from ViewPerformanceRemorqueur sp WHERE sp.creationDate <= :endDate AND sp.creationDate >= :startDate")
	    List<ViewPerformanceRemorqueur> findAllByDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

	
}
