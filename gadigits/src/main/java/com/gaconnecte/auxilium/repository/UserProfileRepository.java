package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.UserProfile;
import java.util.List;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the UserProfile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    List<UserProfile> findAllByOrderByLabelAsc();
}
