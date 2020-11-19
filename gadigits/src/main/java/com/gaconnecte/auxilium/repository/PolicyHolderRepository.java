package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.PolicyHolder;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the PolicyHolder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PolicyHolderRepository extends JpaRepository<PolicyHolder,Long> {

    @Query("select policy_holder from PolicyHolder policy_holder where policy_holder.creationUser.login = ?#{principal.username}")
    List<PolicyHolder> findByCreationUserIsCurrentUser();

    @Query("select policy_holder from PolicyHolder policy_holder where policy_holder.updateUser.login = ?#{principal.username}")
    List<PolicyHolder> findByUpdateUserIsCurrentUser();
    
}
