package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.BusinessEntity;
import com.gaconnecte.auxilium.domain.Functionality;
import com.gaconnecte.auxilium.domain.UserAccess;
import com.gaconnecte.auxilium.domain.UserExtra;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the UserAccess entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserAccessRepository extends JpaRepository<UserAccess,Long> {

    @Query("select user_access from UserAccess user_access where user_access.userExtra.user.login = ?#{principal.username}")
    List<UserAccess> findByUserIsCurrentUser();
    
    List<UserAccess> findAllByUserExtraAndBusinessEntityAndFunctionality(UserExtra userExtra, BusinessEntity businessEntity, Functionality functionality);
    
}
