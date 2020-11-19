package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.RefTypeServicePack;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

/**
 * Spring Data JPA repository for the RefTypeService entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RefTypeServicePackRepository extends JpaRepository<RefTypeServicePack,Long> {
    @Query("select  refTypeServicePack from RefTypeServicePack refTypeServicePack where refTypeServicePack.id =:serviceId")
    RefTypeServicePack findPacksByTypeService(@Param("serviceId") Long serviceId);
    
    
    
    @Query("select distinct  refTypeServicePack from RefTypeServicePack refTypeServicePack where refTypeServicePack.service.id =:serviceId")
    Page<RefTypeServicePack> findPacksByRefTypeService(Pageable pageable,@Param("serviceId") Long serviceId);
    
   
    
    
    
}



