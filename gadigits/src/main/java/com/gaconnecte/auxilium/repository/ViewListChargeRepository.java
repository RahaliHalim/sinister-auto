package com.gaconnecte.auxilium.repository;
import com.gaconnecte.auxilium.domain.view.ViewListCharge;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ViewListChargeRepository extends JpaRepository<ViewListCharge, Long>{

}
