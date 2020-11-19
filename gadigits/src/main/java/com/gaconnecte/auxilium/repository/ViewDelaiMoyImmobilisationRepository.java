package com.gaconnecte.auxilium.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gaconnecte.auxilium.domain.view.ViewDelaiMoyImmobilisation;

@Repository
public interface ViewDelaiMoyImmobilisationRepository extends JpaRepository<ViewDelaiMoyImmobilisation, Long>{

	@Query("SELECT sp from ViewDelaiMoyImmobilisation sp WHERE sp.dateOuverture <= :endDate AND sp.dateOuverture >= :startDate")
    List<ViewDelaiMoyImmobilisation> findAllByDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

}
