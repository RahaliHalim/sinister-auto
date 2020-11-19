package com.gaconnecte.auxilium.service;

//import com.gaconnecte.auxilium.service.dto.AnneeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Assure.
 */
public interface AnneeService {

    /**
     * Get list of years
     * @return list of years
     */
    List<Integer> getAnnee();

    
}
