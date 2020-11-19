package com.gaconnecte.auxilium.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gaconnecte.auxilium.domain.referential.RefMapper;
@Repository
public interface RefMapperRepository extends JpaRepository<RefMapper, Long>{

	@Query("select refMapper.codeRef FROM RefMapper refMapper where refMapper.idCompagnie=:compagnie"
			+ " and refMapper.typeRef=:reftype"
			+ " and refMapper.codeCorrespondance=:codecompagnie")
	Long codeRefByCompagnie(@Param("compagnie") Long compagnie, @Param("reftype") Long reftype, @Param("codecompagnie") String codecompagnie);
}
