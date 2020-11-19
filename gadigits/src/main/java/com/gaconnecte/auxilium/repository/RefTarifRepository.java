package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.RefTugTruck;
import com.gaconnecte.auxilium.domain.RefTarif;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;


/**
 * Spring Data JPA repository for the RefTva entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RefTarifRepository extends JpaRepository<RefTarif,Long> {
	 @Query("select distinct refTarif from RefTarif refTarif where refTarif.line.id =:lineId")
     Page<RefTarif> findRefTarifsByTarifLine(Pageable pageable,@Param("lineId") Long lineId);  
}
