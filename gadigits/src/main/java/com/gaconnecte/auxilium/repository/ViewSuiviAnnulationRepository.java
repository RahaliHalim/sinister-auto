package com.gaconnecte.auxilium.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gaconnecte.auxilium.domain.view.ViewSuiviAnnulation;

public interface ViewSuiviAnnulationRepository extends JpaRepository<ViewSuiviAnnulation, Long>{

	@Query("SELECT sp from ViewSuiviAnnulation sp WHERE sp.compagnieId = :companyId")
	 List<ViewSuiviAnnulation> findAllByCompagny(@Param("companyId") Long companyId);

	 @Query("SELECT sp from ViewSuiviAnnulation sp WHERE sp.ouvertureDate <= :endDate AND sp.ouvertureDate >= :startDate")
	 List<ViewSuiviAnnulation> findAllByDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

	 @Query("SELECT sp from ViewSuiviAnnulation sp WHERE sp.compagnieId = :companyId AND sp.ouvertureDate <= :endDate AND sp.ouvertureDate >= :startDate")
	 List<ViewSuiviAnnulation> findAllByDatesAndCompagny(@Param("companyId") Long companyId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

	
}
