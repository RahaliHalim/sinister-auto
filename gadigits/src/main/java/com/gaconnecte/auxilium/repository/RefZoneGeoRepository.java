package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.RefZoneGeo;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the RefZoneGeo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RefZoneGeoRepository extends JpaRepository<RefZoneGeo,Long> {
    
    @Query("select distinct ref_zone_geo from RefZoneGeo ref_zone_geo left join fetch ref_zone_geo.gouvernorats")
    List<RefZoneGeo> findAllWithEagerRelationships();

    @Query("select ref_zone_geo from RefZoneGeo ref_zone_geo left join fetch ref_zone_geo.gouvernorats where ref_zone_geo.id =:id")
    RefZoneGeo findOneWithEagerRelationships(@Param("id") Long id);
    
}
