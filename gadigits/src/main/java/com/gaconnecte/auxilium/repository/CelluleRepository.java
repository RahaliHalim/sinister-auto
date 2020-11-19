package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.Cellule;
import com.gaconnecte.auxilium.domain.UserCellule;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Cellule entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CelluleRepository extends JpaRepository<Cellule, Long> {

    @Query("select cellule from Cellule cellule where cellule.name =:name")
	Cellule findByName(@Param("name") String name);

     @Query("select distinct cellule from Cellule cellule")
	List<Cellule> findAllCellule();

    @Query("select uc from UserCellule  uc where uc.user.id =:id")
    List<UserCellule> findCellulesbyUser(@Param("id") Long id);


}
