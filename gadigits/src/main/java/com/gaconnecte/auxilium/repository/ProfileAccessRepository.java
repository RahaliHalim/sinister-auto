package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.ProfileAccess;
import java.util.List;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ProfileAccess entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProfileAccessRepository extends JpaRepository<ProfileAccess, Long> {
    List<ProfileAccess> findByProfileId(Long profileId);
}
