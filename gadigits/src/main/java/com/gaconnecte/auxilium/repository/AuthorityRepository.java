package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.Authority;
import com.gaconnecte.auxilium.domain.UserCellule;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {


    @Query("select authority from Authority authority where authority.name =:name")
    Authority findByName(@Param("name") String name);

    @Query("select distinct authority from Authority authority where authority.isInterne = true")
    List<Authority> findAuthorityInterne();

    @Query("select distinct authority from Authority authority where authority.isInterne = false or authority.isInterne = null")
    List<Authority> findAuthorityExterne();

    @Query(value= "select * from jhi_user_authority ua, jhi_authority a where ua.user_id=?1 and (a.is_interne = false or a.is_interne = null) and a.name = ua.authority_name",nativeQuery = true)
    List<Authority> findAuthorityExterneForUser(Long id);

    @Query("select distinct user.authorities from User user, UserCellule user_cellule where user_cellule.user.id=user.id and user_cellule.user.id =:id")
    List<Authority> findAuthorityInterneForUser(@Param("id") Long id);

    @Query("select distinct authority from Authority authority where authority.isActive = true")
    List<Authority> findAuthorityActive();





}
