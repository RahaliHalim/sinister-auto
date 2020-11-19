package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.RefTypeService;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.*;
import java.util.Set;

/**
 * Spring Data JPA repository for the RefTypeService entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RefTypeServiceRepository extends JpaRepository<RefTypeService, Long> {

    @Query("SELECT st from RefTypeService st order by st.id")
    Set<RefTypeService> findAllServiceTypes();
    
    Set<RefTypeService> findAllByActiveOrderByIdAsc(boolean active);
}
