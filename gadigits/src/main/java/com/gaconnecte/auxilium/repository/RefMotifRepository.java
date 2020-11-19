package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.RefMotif;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.*;
import java.util.Set;


/**
 * Spring Data JPA repository for the RefMotif entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RefMotifRepository extends JpaRepository<RefMotif,Long> {

  @Query("SELECT refMotif from RefMotif refMotif")
  Set<RefMotif> findAllMotifsByTypeAndEtat();

}
