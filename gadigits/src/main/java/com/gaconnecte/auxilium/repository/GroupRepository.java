package com.gaconnecte.auxilium.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import com.gaconnecte.auxilium.domain.Group;

@SuppressWarnings("unused")
@Repository
public interface GroupRepository extends JpaRepository<Group,Long> {

    @Query("select group.product.id from Group group where group.id =:id")
    Long findProductIdByGroup(@Param("id") Long id);

    /*@Query("delete from Group group where group.id =:id")
	@Modifying
	void deleteGroup(@Param("id") Long id);*/
}
