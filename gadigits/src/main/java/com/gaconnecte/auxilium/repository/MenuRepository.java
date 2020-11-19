package com.gaconnecte.auxilium.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gaconnecte.auxilium.domain.Menu;

@SuppressWarnings("unused")
@Repository
public interface MenuRepository extends JpaRepository<Menu, Long>{

}
