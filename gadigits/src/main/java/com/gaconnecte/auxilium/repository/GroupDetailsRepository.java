package com.gaconnecte.auxilium.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gaconnecte.auxilium.domain.GroupDetails;

@SuppressWarnings("unused")
@Repository
public interface GroupDetailsRepository extends JpaRepository<GroupDetails,Long> {

	@Query("select groupDetails from GroupDetails groupDetails where groupDetails.group.id =:id")
	List<GroupDetails> findByGroupId(@Param("id") Long id);
	@Query("delete from GroupDetails groupDetails where groupDetails.group.id =:id")
	@Modifying
	void deleteGroupDetails(@Param("id") Long id);

}
