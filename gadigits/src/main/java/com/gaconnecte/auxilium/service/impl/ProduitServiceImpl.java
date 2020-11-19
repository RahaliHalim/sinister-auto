package com.gaconnecte.auxilium.service.impl;

import com.gaconnecte.auxilium.service.ProduitService;
import com.gaconnecte.auxilium.domain.Produit;
import com.gaconnecte.auxilium.repository.ProduitRepository;
import com.gaconnecte.auxilium.repository.search.ProduitSearchRepository;
import com.gaconnecte.auxilium.service.dto.ProduitDTO;
import com.gaconnecte.auxilium.service.mapper.ProduitMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Produit.
 */
@Service
@Transactional
public class ProduitServiceImpl implements ProduitService{

    private final Logger log = LoggerFactory.getLogger(ProduitServiceImpl.class);

    private final ProduitRepository produitRepository;

    private final ProduitMapper produitMapper;

    private final ProduitSearchRepository produitSearchRepository;

    public ProduitServiceImpl(ProduitRepository produitRepository, ProduitMapper produitMapper, ProduitSearchRepository produitSearchRepository) {
        this.produitRepository = produitRepository;
        this.produitMapper = produitMapper;
        this.produitSearchRepository = produitSearchRepository;
    }

    /**
     * Save a produit.
     *
     * @param produitDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ProduitDTO save(ProduitDTO produitDTO) {
        log.debug("Request to save Produit : {}", produitDTO);
        Produit produit = produitMapper.toEntity(produitDTO);
        produit = produitRepository.save(produit);
        ProduitDTO result = produitMapper.toDto(produit);
        produitSearchRepository.save(produit);
        return result;
    }

    /**
     *  Get all the produits.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProduitDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Produits");
        return produitRepository.findAll(pageable)
            .map(produitMapper::toDto);
    }

    /**
     *  Get one produit by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ProduitDTO findOne(Long id) {
        log.debug("Request to get Produit : {}", id);
        Produit produit = produitRepository.findOne(id);
        return produitMapper.toDto(produit);
    }

    /**
     *  Delete the  produit by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Produit : {}", id);
        produitRepository.delete(id);
        produitSearchRepository.delete(id);
    }

    /**
     * Search for the produit corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProduitDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Produits for query {}", query);
        Page<Produit> result = produitSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(produitMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public ProduitDTO findProductClientCode(Integer code, Long clientId) {
        Produit produit = produitRepository.findProductClientCode(code, clientId);
        return produitMapper.toDto(produit);
    }


}
