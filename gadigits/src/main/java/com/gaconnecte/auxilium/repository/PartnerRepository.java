package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.Partner;
import java.util.Set;
import org.springframework.stereotype.Repository;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;



/**
 * Spring Data JPA repository for the Partner entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PartnerRepository extends JpaRepository<Partner, Long> {
    Set<Partner> findAllByGenre(Integer genre);
    
    @Query("SELECT partner from Partner partner where partner.companyName = :name and partner.tradeRegister = :tradeRegister and partner.genre = :genre ")
    Partner findCompanyByNameReg(@Param("name") String name, @Param("tradeRegister") String tradeRegister, @Param("genre") Integer genre);
    
    @Query("SELECT partner from Partner partner where partner.companyName = :name and partner.tradeRegister = :tradeRegister and partner.genre = :genre ")
    Partner findDealerByNameReg(@Param("name") String name, @Param("tradeRegister") String tradeRegister, @Param("genre") Integer genre);
    
    @Query("SELECT partner from Partner partner where partner.companyName = :name and partner.genre = :genre ")
    Partner findCompanyByName(@Param("name") String name, @Param("genre") Integer genre);
    
    @Query("SELECT partner from Partner partner where partner.companyName = :name")
    Partner findPartnerByName(@Param("name") String name);
    
}
