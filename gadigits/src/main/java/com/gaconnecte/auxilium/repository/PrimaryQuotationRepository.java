package com.gaconnecte.auxilium.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.gaconnecte.auxilium.domain.PrimaryQuotation;

@SuppressWarnings("unused")
@Repository
public interface PrimaryQuotationRepository extends JpaRepository<PrimaryQuotation,Long>{

    @Query(value = "select pq from PrimaryQuotation pq where pq.id =:id")
   PrimaryQuotation findPrimaryQuotationById(@Param("id") Long id);
}
