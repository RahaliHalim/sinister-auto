package com.gaconnecte.auxilium.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gaconnecte.auxilium.domain.ViewPaimentReparation;


@Repository
public interface ViewPaimentReparationRepository extends JpaRepository<ViewPaimentReparation, Long> {
	@Query("SELECT sp from ViewPaimentReparation sp WHERE sp.dateDemande <= :endDate AND sp.dateDemande >= :startDate")
    List<ViewPaimentReparation> findAllByDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

}
