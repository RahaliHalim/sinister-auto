package com.gaconnecte.auxilium.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gaconnecte.auxilium.domain.ObservationApec;

@SuppressWarnings("unused")
@Repository
public interface ObservationApecRepository extends JpaRepository<ObservationApec, Long> {
	
	@Query("SELECT observationApec from ObservationApec observationApec where observationApec.apecId =:id")
	ObservationApec findOneByApecId(@Param("id") Long id);

}
