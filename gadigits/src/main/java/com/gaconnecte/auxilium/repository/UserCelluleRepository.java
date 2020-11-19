package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.UserCellule;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the UserCellule entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserCelluleRepository extends JpaRepository<UserCellule,Long> {

    @Query("select user_cellule from UserCellule user_cellule where user_cellule.user.id =:userId and user_cellule.cellule.id =:celluleId")
	UserCellule findByUserAndCellule(@Param("userId") Long userId, @Param("celluleId") Long celluleId);

    @Query("select user_cellule from UserCellule user_cellule where user_cellule.user.id =:userId")
	List <UserCellule> findByUser(@Param("userId") Long userId);

    @Query("select uc from UserCellule  uc where uc.user.id =:id")
    List<UserCellule> findCellulesbyUser(@Param("id") Long id);

}
