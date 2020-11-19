package com.gaconnecte.auxilium.repository;
import com.gaconnecte.auxilium.domain.view.ViewPolicy;
import com.gaconnecte.auxilium.domain.view.ViewSouscriptionGa;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;


@Repository
public interface ViewSouscriptionGaRepository extends JpaRepository<ViewSouscriptionGa, Long>{
	
	@Query("SELECT sp from ViewSouscriptionGa sp WHERE sp.dateCreation <= :endDate AND sp.dateCreation >= :startDate")
    Page<ViewSouscriptionGa> findAllByDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, Pageable pageable);
	
	@Query("SELECT sp FROM ViewSouscriptionGa sp WHERE lower(sp.compagnie) like %:filter% OR  lower(sp.numeroContrat)  like %:filter%"
            + " OR  lower(sp.zone)  like %:filter% OR  lower(sp.pack)  like %:filter% ")
    Page<ViewPolicy> findAllWithFilter(@Param("filter") String filter, Pageable pageable);

    @Query("SELECT count(sp) FROM ViewSouscriptionGa sp WHERE lower(sp.compagnie) like %:filter% OR  lower(sp.numeroContrat)  like %:filter%"
            + " OR  lower(sp.zone)  like %:filter% OR  lower(sp.pack)  like %:filter% ")
    Long countAllWithFilter(@Param("filter") String filter);
    

	@Query("SELECT sp from ViewSouscriptionGa sp WHERE sp.dateCreation <= :endDate AND sp.dateCreation >= :startDate")
    List<ViewSouscriptionGa> findAllByDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
	

}
