package com.gaconnecte.auxilium.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gaconnecte.auxilium.domain.Upload;

	@SuppressWarnings("unused")
	@Repository
	public interface UploadRepository extends JpaRepository<Upload,Long> {
		 /* @Query(value="SELECT table_name FROM information_schema.tables WHERE table_type='BASE TABLE' AND table_schema='public' " ,nativeQuery = true)
		  List<String> findReference();*/
	}

