package com.gaconnecte.auxilium.web.rest;

import com.gaconnecte.auxilium.AuxiliumApp;

import com.gaconnecte.auxilium.domain.RefTypePieces;
import com.gaconnecte.auxilium.repository.RefTypePiecesRepository;
import com.gaconnecte.auxilium.service.RefTypePiecesService;
import com.gaconnecte.auxilium.repository.search.RefTypePiecesSearchRepository;
import com.gaconnecte.auxilium.service.dto.RefTypePiecesDTO;
import com.gaconnecte.auxilium.service.mapper.RefTypePiecesMapper;
import com.gaconnecte.auxilium.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the RefTypePiecesResource REST controller.
 *
 * @see RefTypePiecesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuxiliumApp.class)
public class RefTypePiecesResourceIntTest {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    @Autowired
    private RefTypePiecesRepository refTypePiecesRepository;

    @Autowired
    private RefTypePiecesMapper refTypePiecesMapper;

    @Autowired
    private RefTypePiecesService refTypePiecesService;

    @Autowired
    private RefTypePiecesSearchRepository refTypePiecesSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRefTypePiecesMockMvc;

    private RefTypePieces refTypePieces;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RefTypePiecesResource refTypePiecesResource = new RefTypePiecesResource(refTypePiecesService);
        this.restRefTypePiecesMockMvc = MockMvcBuilders.standaloneSetup(refTypePiecesResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RefTypePieces createEntity(EntityManager em) {
        RefTypePieces refTypePieces = new RefTypePieces()
            .libelle(DEFAULT_LIBELLE);
        return refTypePieces;
    }

    @Before
    public void initTest() {
        refTypePiecesSearchRepository.deleteAll();
        refTypePieces = createEntity(em);
    }

    @Test
    @Transactional
    public void createRefTypePieces() throws Exception {
        int databaseSizeBeforeCreate = refTypePiecesRepository.findAll().size();

        // Create the RefTypePieces
        RefTypePiecesDTO refTypePiecesDTO = refTypePiecesMapper.toDto(refTypePieces);
        restRefTypePiecesMockMvc.perform(post("/api/ref-type-pieces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refTypePiecesDTO)))
            .andExpect(status().isCreated());

        // Validate the RefTypePieces in the database
        List<RefTypePieces> refTypePiecesList = refTypePiecesRepository.findAll();
        assertThat(refTypePiecesList).hasSize(databaseSizeBeforeCreate + 1);
        RefTypePieces testRefTypePieces = refTypePiecesList.get(refTypePiecesList.size() - 1);
        assertThat(testRefTypePieces.getLibelle()).isEqualTo(DEFAULT_LIBELLE);

        // Validate the RefTypePieces in Elasticsearch
        RefTypePieces refTypePiecesEs = refTypePiecesSearchRepository.findOne(testRefTypePieces.getId());
        assertThat(refTypePiecesEs).isEqualToComparingFieldByField(testRefTypePieces);
    }

    @Test
    @Transactional
    public void createRefTypePiecesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = refTypePiecesRepository.findAll().size();

        // Create the RefTypePieces with an existing ID
        refTypePieces.setId(1L);
        RefTypePiecesDTO refTypePiecesDTO = refTypePiecesMapper.toDto(refTypePieces);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRefTypePiecesMockMvc.perform(post("/api/ref-type-pieces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refTypePiecesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<RefTypePieces> refTypePiecesList = refTypePiecesRepository.findAll();
        assertThat(refTypePiecesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = refTypePiecesRepository.findAll().size();
        // set the field null
        refTypePieces.setLibelle(null);

        // Create the RefTypePieces, which fails.
        RefTypePiecesDTO refTypePiecesDTO = refTypePiecesMapper.toDto(refTypePieces);

        restRefTypePiecesMockMvc.perform(post("/api/ref-type-pieces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refTypePiecesDTO)))
            .andExpect(status().isBadRequest());

        List<RefTypePieces> refTypePiecesList = refTypePiecesRepository.findAll();
        assertThat(refTypePiecesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRefTypePieces() throws Exception {
        // Initialize the database
        refTypePiecesRepository.saveAndFlush(refTypePieces);

        // Get all the refTypePiecesList
        restRefTypePiecesMockMvc.perform(get("/api/ref-type-pieces?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(refTypePieces.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())));
    }

    @Test
    @Transactional
    public void getRefTypePieces() throws Exception {
        // Initialize the database
        refTypePiecesRepository.saveAndFlush(refTypePieces);

        // Get the refTypePieces
        restRefTypePiecesMockMvc.perform(get("/api/ref-type-pieces/{id}", refTypePieces.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(refTypePieces.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRefTypePieces() throws Exception {
        // Get the refTypePieces
        restRefTypePiecesMockMvc.perform(get("/api/ref-type-pieces/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRefTypePieces() throws Exception {
        // Initialize the database
        refTypePiecesRepository.saveAndFlush(refTypePieces);
        refTypePiecesSearchRepository.save(refTypePieces);
        int databaseSizeBeforeUpdate = refTypePiecesRepository.findAll().size();

        // Update the refTypePieces
        RefTypePieces updatedRefTypePieces = refTypePiecesRepository.findOne(refTypePieces.getId());
        updatedRefTypePieces
            .libelle(UPDATED_LIBELLE);
        RefTypePiecesDTO refTypePiecesDTO = refTypePiecesMapper.toDto(updatedRefTypePieces);

        restRefTypePiecesMockMvc.perform(put("/api/ref-type-pieces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refTypePiecesDTO)))
            .andExpect(status().isOk());

        // Validate the RefTypePieces in the database
        List<RefTypePieces> refTypePiecesList = refTypePiecesRepository.findAll();
        assertThat(refTypePiecesList).hasSize(databaseSizeBeforeUpdate);
        RefTypePieces testRefTypePieces = refTypePiecesList.get(refTypePiecesList.size() - 1);
        assertThat(testRefTypePieces.getLibelle()).isEqualTo(UPDATED_LIBELLE);

        // Validate the RefTypePieces in Elasticsearch
        RefTypePieces refTypePiecesEs = refTypePiecesSearchRepository.findOne(testRefTypePieces.getId());
        assertThat(refTypePiecesEs).isEqualToComparingFieldByField(testRefTypePieces);
    }

    @Test
    @Transactional
    public void updateNonExistingRefTypePieces() throws Exception {
        int databaseSizeBeforeUpdate = refTypePiecesRepository.findAll().size();

        // Create the RefTypePieces
        RefTypePiecesDTO refTypePiecesDTO = refTypePiecesMapper.toDto(refTypePieces);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRefTypePiecesMockMvc.perform(put("/api/ref-type-pieces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refTypePiecesDTO)))
            .andExpect(status().isCreated());

        // Validate the RefTypePieces in the database
        List<RefTypePieces> refTypePiecesList = refTypePiecesRepository.findAll();
        assertThat(refTypePiecesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRefTypePieces() throws Exception {
        // Initialize the database
        refTypePiecesRepository.saveAndFlush(refTypePieces);
        refTypePiecesSearchRepository.save(refTypePieces);
        int databaseSizeBeforeDelete = refTypePiecesRepository.findAll().size();

        // Get the refTypePieces
        restRefTypePiecesMockMvc.perform(delete("/api/ref-type-pieces/{id}", refTypePieces.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean refTypePiecesExistsInEs = refTypePiecesSearchRepository.exists(refTypePieces.getId());
        assertThat(refTypePiecesExistsInEs).isFalse();

        // Validate the database is empty
        List<RefTypePieces> refTypePiecesList = refTypePiecesRepository.findAll();
        assertThat(refTypePiecesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchRefTypePieces() throws Exception {
        // Initialize the database
        refTypePiecesRepository.saveAndFlush(refTypePieces);
        refTypePiecesSearchRepository.save(refTypePieces);

        // Search the refTypePieces
        restRefTypePiecesMockMvc.perform(get("/api/_search/ref-type-pieces?query=id:" + refTypePieces.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(refTypePieces.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RefTypePieces.class);
        RefTypePieces refTypePieces1 = new RefTypePieces();
        refTypePieces1.setId(1L);
        RefTypePieces refTypePieces2 = new RefTypePieces();
        refTypePieces2.setId(refTypePieces1.getId());
        assertThat(refTypePieces1).isEqualTo(refTypePieces2);
        refTypePieces2.setId(2L);
        assertThat(refTypePieces1).isNotEqualTo(refTypePieces2);
        refTypePieces1.setId(null);
        assertThat(refTypePieces1).isNotEqualTo(refTypePieces2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RefTypePiecesDTO.class);
        RefTypePiecesDTO refTypePiecesDTO1 = new RefTypePiecesDTO();
        refTypePiecesDTO1.setId(1L);
        RefTypePiecesDTO refTypePiecesDTO2 = new RefTypePiecesDTO();
        assertThat(refTypePiecesDTO1).isNotEqualTo(refTypePiecesDTO2);
        refTypePiecesDTO2.setId(refTypePiecesDTO1.getId());
        assertThat(refTypePiecesDTO1).isEqualTo(refTypePiecesDTO2);
        refTypePiecesDTO2.setId(2L);
        assertThat(refTypePiecesDTO1).isNotEqualTo(refTypePiecesDTO2);
        refTypePiecesDTO1.setId(null);
        assertThat(refTypePiecesDTO1).isNotEqualTo(refTypePiecesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(refTypePiecesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(refTypePiecesMapper.fromId(null)).isNull();
    }
}
