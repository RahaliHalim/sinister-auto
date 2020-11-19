package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.Authority;
import com.gaconnecte.auxilium.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.time.Instant;

/**
 * Spring Data JPA repository for the User entity.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findOneByActivationKey(String activationKey);

    List<User> findAllByActivatedIsFalseAndCreatedDateBefore(Instant dateTime);

    Optional<User> findOneByResetKey(String resetKey);

    Optional<User> findOneByEmailIgnoreCase(String email);

    Optional<User> findOneByLoginIgnoreCase(String login);

    Optional<User> findOneById(Long id);

    @Query("update User user set user.idGroup = null  where user.idGroup =:id")
    @Modifying
    void updateGroup(@Param("id")Long id);

    @Query("select user from User user  where user.login =:login")
    User findOneUserByLogin(@Param("login") String login);

    @EntityGraph(attributePaths = "authorities")
    User findOneWithAuthoritiesById(Long id);

    @EntityGraph(attributePaths = "authorities")
    Optional<User> findOneWithAuthoritiesByLogin(String login);

    Page<User> findAllByLoginNot(Pageable pageable, String login);

   @Query("select user from User user  where user.activated = true")
    List<User> findUsersWithoutPagination();
   
   @Query("select user.authorities from User user  where user.id =:id")
   List<Authority> findAuthorityById(@Param("id")Long id);
} 
