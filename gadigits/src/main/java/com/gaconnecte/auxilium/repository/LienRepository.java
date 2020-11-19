package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.Lien;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Lien entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LienRepository extends JpaRepository<Lien,Long> {

    @Query("select distinct lien from Lien lien left join fetch lien.cellules")
    List<Lien> findAllWithEagerRelationships();

    @Query("select lien from Lien lien left join fetch lien.cellules where lien.id =:id")
    Lien findOneWithEagerRelationships(@Param("id") Long id);

       @Query("select distinct cellule.liens from  Cellule cellule, UserCellule user_cellule where user_cellule.cellule.id = cellule.id and user_cellule.user.id = :userId")
       List<Lien> findLienForUser(@Param("userId") Long userId);

       @Query("select distinct lien from Lien lien where lien.parent.id = null  ORDER BY lien.id")
       List<Lien> findLienWithoutParent();
}