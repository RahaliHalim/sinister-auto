package com.gaconnecte.auxilium.service.impl;

import com.gaconnecte.auxilium.service.PieceJointeService;
import com.gaconnecte.auxilium.domain.PieceJointe;
import com.gaconnecte.auxilium.repository.PieceJointeRepository;
import com.gaconnecte.auxilium.repository.search.PieceJointeSearchRepository;
import com.gaconnecte.auxilium.service.dto.PieceJointeDTO;
import com.gaconnecte.auxilium.service.mapper.PieceJointeMapper;
import com.gaconnecte.auxilium.domain.ResourceNotFoundException;
import com.gaconnecte.auxilium.domain.User;
import com.gaconnecte.auxilium.security.SecurityUtils;
import com.gaconnecte.auxilium.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.time.LocalDate;
import com.gaconnecte.auxilium.config.ApplicationProperties;

import com.gaconnecte.auxilium.domain.Result;
import com.gaconnecte.auxilium.service.mapper.DossierMapper;
import com.gaconnecte.auxilium.service.ContratAssuranceService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;



import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing PieceJointe.
 */
@Service
@Transactional
public class PieceJointeServiceImpl implements PieceJointeService{

    private final ApplicationProperties properties;
    //private static final String UPLOADED_FOLDER = "C://Users//salwa.chaabi//Documents//apache-tomcat-7.0.90//webapps//examples//";
    //private final String UPLOADED_FOLDER = properties.getCheminPdfReport();

    private final Logger log = LoggerFactory.getLogger(PieceJointeServiceImpl.class);

    private final PieceJointeRepository pieceJointeRepository;

    private final PieceJointeMapper pieceJointeMapper;

    private final PieceJointeSearchRepository pieceJointeSearchRepository;

    
    Result result = new Result();
  


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContratAssuranceService contratAssuranceService;

    @Autowired
    private DossierMapper dossierMapper;

 	
   @Autowired
    public PieceJointeServiceImpl(ApplicationProperties properties, PieceJointeRepository pieceJointeRepository, PieceJointeMapper pieceJointeMapper, PieceJointeSearchRepository pieceJointeSearchRepository) {
        this.pieceJointeRepository = pieceJointeRepository;
        this.pieceJointeMapper = pieceJointeMapper;
        this.pieceJointeSearchRepository = pieceJointeSearchRepository;
        this.properties = properties;
     
    }

    /**
     * Save a pieceJointe.
     *
     * @param pieceJointeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PieceJointeDTO save(PieceJointeDTO pieceJointeDTO) {
        log.debug("Request to save PieceJointe : {}", pieceJointeDTO);
        PieceJointe pieceJointe = pieceJointeMapper.toEntity(pieceJointeDTO);
        String login = SecurityUtils.getCurrentUserLogin();
    	Optional<User> user = userRepository.findOneByLoginIgnoreCase(login);
        if (user.isPresent()) {
            User utilisateur = user.get();
            pieceJointe.setUser(utilisateur);
            //pieceJointe.setChemin(affectChemin(pieceJointe));
            LocalDate today = LocalDate.now();
            pieceJointe.setDateImport(today);
            pieceJointe = pieceJointeRepository.save(pieceJointe);
            PieceJointeDTO result = pieceJointeMapper.toDto(pieceJointe);
           // result.setChemin(affectChemin(pieceJointe));
            log.debug("-------------------------" + pieceJointe.getChemin());
            log.debug("-------------------------" + pieceJointe.getLibelle());

            pieceJointeSearchRepository.save(pieceJointe);
            return result;
         } else {
            throw new ResourceNotFoundException("user not found");
        }
    }

    
    

    /**
     * Save a pieceJointe.
     *
     * @param pieceJointeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PieceJointeDTO savePieceJointeSignature(PieceJointeDTO pieceJointeDTO) {
        log.debug("Request to save PieceJointe : {}", pieceJointeDTO);
        PieceJointe pieceJointe = pieceJointeMapper.toEntity(pieceJointeDTO);
        String login = SecurityUtils.getCurrentUserLogin();
    	Optional<User> user = userRepository.findOneByLoginIgnoreCase(login);
        if (user.isPresent()) {
            User utilisateur = user.get();
            pieceJointe.setUser(utilisateur);
            pieceJointe = pieceJointeRepository.save(pieceJointe);
            PieceJointeDTO result = pieceJointeMapper.toDto(pieceJointe);
            log.debug("-------------------------" + pieceJointe.getChemin());
            log.debug("-------------------------" + pieceJointe.getLibelle());
            log.debug("-------------------------" + pieceJointe.getDateImport());

            pieceJointeSearchRepository.save(pieceJointe);
            return result;
         } else {
            throw new ResourceNotFoundException("user not found");
        }
    }

     /**
     * affect a chemin.
     *
     * @param  
     * @return Result
     */

     @Override
    public Result affectChemin() { 
           result.setcontent(properties.getCheminFichier());
           //result.setcontent(UPLOADED_FOLDER);           
           return result;
    }

     @Override
     public Result affectCheminPdfReport() {
            result.setcontent(properties.getCheminPdfReport());       
            return result;
     }

    /**
     *  Get all the pieceJointes.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PieceJointeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PieceJointes");
        return pieceJointeRepository.findAll(pageable)
            .map(pieceJointeMapper::toDto);
    }

    /**
     *  Get one pieceJointe by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public PieceJointeDTO findOne(Long id) {
        log.debug("Request to get PieceJointe : {}", id);
        PieceJointe pieceJointe = pieceJointeRepository.findOne(id);
        return pieceJointeMapper.toDto(pieceJointe);
    }

    /**
     *  Delete the  pieceJointe by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PieceJointe : {}", id);
        pieceJointeRepository.delete(id);
        pieceJointeSearchRepository.delete(id);
    }

    /**
     * Search for the pieceJointe corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PieceJointeDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of PieceJointes for query {}", query);
        Page<PieceJointe> result = pieceJointeSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(pieceJointeMapper::toDto);
    }

      /**
     *  Get pieceJointe by prestationId.
     *
     *  @param prestationId the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PieceJointeDTO> findByPrestation(Pageable pageable, Long prestationId) {
        log.debug("Request to get PieceJointes : {}", prestationId);
        Page<PieceJointe> pieceJointes = pieceJointeRepository.findPieceJointesByPrestation(pageable, prestationId);
        return pieceJointes.map(pieceJointeMapper::toDto);
         
    }

      /**
     *  Get pieceJointe by devisId.
     *
     *  @param devisId the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PieceJointeDTO> findByDevis(Pageable pageable, Long devisId) {
        log.debug("Request to get PieceJointes : {}", devisId);
        Page<PieceJointe> pieceJointes = pieceJointeRepository.findPieceJointesByDevis(pageable, devisId);
        return pieceJointes.map(pieceJointeMapper::toDto);
         
    }

    public PieceJointeDTO uploadChemin(Long id, MultipartFile file) {

        log.debug("Uploading chemin");
        String UPLOADED_FOLDER = properties.getCheminFichier();
        log.debug("---------------------------------"+UPLOADED_FOLDER);
        PieceJointe pieceJointes = pieceJointeRepository.findOne(id);
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + "/"+ file.getOriginalFilename());
            Files.write(path, bytes);
            pieceJointes.setChemin(UPLOADED_FOLDER + "/"+ file.getOriginalFilename());
            pieceJointes.setLibelle(file.getOriginalFilename());
            pieceJointeRepository.save(pieceJointes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pieceJointeMapper.toDto(pieceJointes);
    }

     public void createChemin(MultipartFile file) {

        log.debug("Uploading chemin");
        String UPLOADED_FOLDER = properties.getCheminFichier();
        log.debug("---------------------------------"+UPLOADED_FOLDER);
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER +"/"+ file.getOriginalFilename());
            Files.write(path, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
}
