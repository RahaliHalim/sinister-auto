package com.gaconnecte.auxilium.service.impl;

import com.gaconnecte.auxilium.service.AnneeService;
import com.gaconnecte.auxilium.domain.Prestation;
//import com.gaconnecte.auxilium.repository.AnneeRepository;
//import com.gaconnecte.auxilium.repository.search.AnneeSearchRepository;
//import com.gaconnecte.auxilium.service.dto.PrestationDTO;
//import com.gaconnecte.auxilium.service.mapper.AnneeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Date;
import java.util.*;
import java.util.ArrayList;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Assure.
 */
@Service
@Transactional
public class AnneeServiceImpl implements AnneeService{

    private final Logger log = LoggerFactory.getLogger(AnneeServiceImpl.class);

    //Get list of years from anneeReference (to do : configure this variable as constant)
    final int anneeReference = 2017;

    //private final AnneeRepository anneeRepository;

    //private final AnneeMapper anneeMapper;

    //private final AnneeSearchRepository anneeSearchRepository;

    

    

    /**
     * Get the list of years
     * @return the list of years
     */
    @Override
    public List<Integer> getAnnee() {
        //return anneeRepository.getListAnnee();
        List<Integer> list = new ArrayList<Integer>();
        Date d = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        int year = c.get(Calendar.YEAR);
        list.add(anneeReference);
        
        for (int i = 1; i <= year-anneeReference; i++){
				list.add(anneeReference+i);
		}

		return list;


    }

}
