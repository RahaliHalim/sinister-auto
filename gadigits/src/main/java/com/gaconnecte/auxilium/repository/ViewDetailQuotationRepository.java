
package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.view.ViewDetailQuotation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface ViewDetailQuotationRepository extends JpaRepository<ViewDetailQuotation, Long> {
    
    @Query("select distinct detailDevis from ViewDetailQuotation detailDevis where detailDevis.id = :idPec")
	ViewDetailQuotation findViewDetailQuotationByPec(@Param("idPec") Long idPec);
}