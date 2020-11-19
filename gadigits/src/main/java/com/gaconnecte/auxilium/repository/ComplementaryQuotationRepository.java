package com.gaconnecte.auxilium.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.gaconnecte.auxilium.domain.ComplementaryQuotation;
import com.gaconnecte.auxilium.domain.PrimaryQuotation;

@SuppressWarnings("unused")
@Repository
public interface ComplementaryQuotationRepository extends JpaRepository<ComplementaryQuotation,Long>{


	/*@Query(value = "select cq from ComplementaryQuotation cq WHERE cq.prestation.id =:idPec")
	List<ComplementaryQuotation> findComplementaryQuotationByIdPEC(@Param("id") Long idPec);*/
   
}
