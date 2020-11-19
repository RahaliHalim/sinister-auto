package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.domain.ElementMenu;
import com.gaconnecte.auxilium.repository.ElementMenuRepository;
import com.gaconnecte.auxilium.repository.search.ElementMenuSearchRepository;
import com.gaconnecte.auxilium.service.dto.ElementMenuDTO;
import com.gaconnecte.auxilium.service.mapper.ElementMenuMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing ElementMenu.
 */
@Service
@Transactional
public class ElementMenuService {

    private final Logger log = LoggerFactory.getLogger(ElementMenuService.class);

    private final ElementMenuRepository elementMenuRepository;

    private final ElementMenuMapper elementMenuMapper;

    private final ElementMenuSearchRepository elementMenuSearchRepository;

    public ElementMenuService(ElementMenuRepository elementMenuRepository, ElementMenuMapper elementMenuMapper, ElementMenuSearchRepository elementMenuSearchRepository) {
        this.elementMenuRepository = elementMenuRepository;
        this.elementMenuMapper = elementMenuMapper;
        this.elementMenuSearchRepository = elementMenuSearchRepository;
    }

    /**
     * Save a elementMenu.
     *
     * @param elementMenuDTO the entity to save
     * @return the persisted entity
     */
    public ElementMenuDTO save(ElementMenuDTO elementMenuDTO) {
        log.debug("Request to save ElementMenu : {}", elementMenuDTO);
        ElementMenu elementMenu = elementMenuMapper.toEntity(elementMenuDTO);
        elementMenu = elementMenuRepository.save(elementMenu);
        ElementMenuDTO result = elementMenuMapper.toDto(elementMenu);
        elementMenuSearchRepository.save(elementMenu);
        return result;
    }

    /**
     *  Get all the elementMenus.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ElementMenuDTO> findAll() {
        log.debug("Request to get all ElementMenus");
        return elementMenuRepository.findAll().stream()
            .map(elementMenuMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one elementMenu by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public ElementMenuDTO findOne(Long id) {
        log.debug("Request to get ElementMenu : {}", id);
        ElementMenu elementMenu = elementMenuRepository.findOne(id);
        return elementMenuMapper.toDto(elementMenu);
    }

    /**
     *  Delete the  elementMenu by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ElementMenu : {}", id);
        elementMenuRepository.delete(id);
        elementMenuSearchRepository.delete(id);
    }

    /**
     * Search for the elementMenu corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ElementMenuDTO> search(String query) {
        log.debug("Request to search ElementMenus for query {}", query);
        return StreamSupport
            .stream(elementMenuSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(elementMenuMapper::toDto)
            .collect(Collectors.toList());
    }
}
