package com.gaconnecte.auxilium.web.rest;

import com.gaconnecte.auxilium.AuxiliumApp;

import com.gaconnecte.auxilium.domain.PieceJointe;
import com.gaconnecte.auxilium.domain.RefTypePj;
import com.gaconnecte.auxilium.domain.RefEtatDossier;
import com.gaconnecte.auxilium.repository.PieceJointeRepository;
import com.gaconnecte.auxilium.service.PieceJointeService;
import com.gaconnecte.auxilium.repository.search.PieceJointeSearchRepository;
import com.gaconnecte.auxilium.service.dto.PieceJointeDTO;
import com.gaconnecte.auxilium.service.mapper.PieceJointeMapper;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PieceJointeResource REST controller.
 *
 * @see PieceJointeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuxiliumApp.class)
public class PieceJointeResourceIntTest {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_CHEMIN = "AAAAAAAAAA";
    private static final String UPDATED_CHEMIN = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ORIGINALE = false;
    private static final Boolean UPDATED_IS_ORIGINALE = true;

    private static final LocalDate DEFAULT_DATE_IMPORT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_IMPORT = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private PieceJointeRepository pieceJointeRepository;

    @Autowired
    private PieceJointeMapper pieceJointeMapper;

    @Autowired
    private PieceJointeService pieceJointeService;

    @Autowired
    private PieceJointeSearchRepository pieceJointeSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPieceJointeMockMvc;

    private PieceJointe pieceJointe;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PieceJointeResource pieceJointeResource = new PieceJointeResource(pieceJointeService);
        this.restPieceJointeMockMvc = MockMvcBuilders.standaloneSetup(pieceJointeResource)
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
    public static PieceJointe createEntity(EntityManager em) {
        PieceJointe pieceJointe = new PieceJointe()
            .libelle(DEFAULT_LIBELLE)
            .chemin(DEFAULT_CHEMIN)
            .isOriginale(DEFAULT_IS_ORIGINALE)
            .dateImport(DEFAULT_DATE_IMPORT);
        // Add required entity
        RefTypePj type = RefTypePjResourceIntTest.createEntity(em);
        em.persist(type);
        em.flush();
        pieceJointe.setType(type);
        return pieceJointe;
    }

    @Before
    public void initTest() {
        pieceJointeSearchRepository.deleteAll();
        pieceJointe = createEntity(em);
    }

    @Test
    @Transactional
    public void createPieceJointe() throws Exception {
        int databaseSizeBeforeCreate = pieceJointeRepository.findAll().size();

        // Create the PieceJointe
        PieceJointeDTO pieceJointeDTO = pieceJointeMapper.toDto(pieceJointe);
        restPieceJointeMockMvc.perform(post("/api/piece-jointes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pieceJointeDTO)))
            .andExpect(status().isCreated());

        // Validate the PieceJointe in the database
        List<PieceJointe> pieceJointeList = pieceJointeRepository.findAll();
        assertThat(pieceJointeList).hasSize(databaseSizeBeforeCreate + 1);
        PieceJointe testPieceJointe = pieceJointeList.get(pieceJointeList.size() - 1);
        assertThat(testPieceJointe.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testPieceJointe.getChemin()).isEqualTo(DEFAULT_CHEMIN);
        assertThat(testPieceJointe.isIsOriginale()).isEqualTo(DEFAULT_IS_ORIGINALE);
        assertThat(testPieceJointe.getDateImport()).isEqualTo(DEFAULT_DATE_IMPORT);

        // Validate the PieceJointe in Elasticsearch
        PieceJointe pieceJointeEs = pieceJointeSearchRepository.findOne(testPieceJointe.getId());
        assertThat(pieceJointeEs).isEqualToComparingFieldByField(testPieceJointe);
    }

    @Test
    @Transactional
    public void createPieceJointeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pieceJointeRepository.findAll().size();

        // Create the PieceJointe with an existing ID
        pieceJointe.setId(1L);
        PieceJointeDTO pieceJointeDTO = pieceJointeMapper.toDto(pieceJointe);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPieceJointeMockMvc.perform(post("/api/piece-jointes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pieceJointeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PieceJointe> pieceJointeList = pieceJointeRepository.findAll();
        assertThat(pieceJointeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = pieceJointeRepository.findAll().size();
        // set the field null
        pieceJointe.setLibelle(null);

        // Create the PieceJointe, which fails.
        PieceJointeDTO pieceJointeDTO = pieceJointeMapper.toDto(pieceJointe);

        restPieceJointeMockMvc.perform(post("/api/piece-jointes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pieceJointeDTO)))
            .andExpect(status().isBadRequest());

        List<PieceJointe> pieceJointeList = pieceJointeRepository.findAll();
        assertThat(pieceJointeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCheminIsRequired() throws Exception {
        int databaseSizeBeforeTest = pieceJointeRepository.findAll().size();
        // set the field null
        pieceJointe.setChemin(null);

        // Create the PieceJointe, which fails.
        PieceJointeDTO pieceJointeDTO = pieceJointeMapper.toDto(pieceJointe);

        restPieceJointeMockMvc.perform(post("/api/piece-jointes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pieceJointeDTO)))
            .andExpect(status().isBadRequest());

        List<PieceJointe> pieceJointeList = pieceJointeRepository.findAll();
        assertThat(pieceJointeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateImportIsRequired() throws Exception {
        int databaseSizeBeforeTest = pieceJointeRepository.findAll().size();
        // set the field null
        pieceJointe.setDateImport(null);

        // Create the PieceJointe, which fails.
        PieceJointeDTO pieceJointeDTO = pieceJointeMapper.toDto(pieceJointe);

        restPieceJointeMockMvc.perform(post("/api/piece-jointes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pieceJointeDTO)))
            .andExpect(status().isBadRequest());

        List<PieceJointe> pieceJointeList = pieceJointeRepository.findAll();
        assertThat(pieceJointeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPieceJointes() throws Exception {
        // Initialize the database
        pieceJointeRepository.saveAndFlush(pieceJointe);

        // Get all the pieceJointeList
        restPieceJointeMockMvc.perform(get("/api/piece-jointes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pieceJointe.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())))
            .andExpect(jsonPath("$.[*].chemin").value(hasItem(DEFAULT_CHEMIN.toString())))
            .andExpect(jsonPath("$.[*].isOriginale").value(hasItem(DEFAULT_IS_ORIGINALE.booleanValue())))
            .andExpect(jsonPath("$.[*].dateImport").value(hasItem(DEFAULT_DATE_IMPORT.toString())));
    }

    @Test
    @Transactional
    public void getPieceJointe() throws Exception {
        // Initialize the database
        pieceJointeRepository.saveAndFlush(pieceJointe);

        // Get the pieceJointe
        restPieceJointeMockMvc.perform(get("/api/piece-jointes/{id}", pieceJointe.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pieceJointe.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()))
            .andExpect(jsonPath("$.chemin").value(DEFAULT_CHEMIN.toString()))
            .andExpect(jsonPath("$.isOriginale").value(DEFAULT_IS_ORIGINALE.booleanValue()))
            .andExpect(jsonPath("$.dateImport").value(DEFAULT_DATE_IMPORT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPieceJointe() throws Exception {
        // Get the pieceJointe
        restPieceJointeMockMvc.perform(get("/api/piece-jointes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePieceJointe() throws Exception {
        // Initialize the database
        pieceJointeRepository.saveAndFlush(pieceJointe);
        pieceJointeSearchRepository.save(pieceJointe);
        int databaseSizeBeforeUpdate = pieceJointeRepository.findAll().size();

        // Update the pieceJointe
        PieceJointe updatedPieceJointe = pieceJointeRepository.findOne(pieceJointe.getId());
        updatedPieceJointe
            .libelle(UPDATED_LIBELLE)
            .chemin(UPDATED_CHEMIN)
            .isOriginale(UPDATED_IS_ORIGINALE)
            .dateImport(UPDATED_DATE_IMPORT);
        PieceJointeDTO pieceJointeDTO = pieceJointeMapper.toDto(updatedPieceJointe);

        restPieceJointeMockMvc.perform(put("/api/piece-jointes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pieceJointeDTO)))
            .andExpect(status().isOk());

        // Validate the PieceJointe in the database
        List<PieceJointe> pieceJointeList = pieceJointeRepository.findAll();
        assertThat(pieceJointeList).hasSize(databaseSizeBeforeUpdate);
        PieceJointe testPieceJointe = pieceJointeList.get(pieceJointeList.size() - 1);
        assertThat(testPieceJointe.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testPieceJointe.getChemin()).isEqualTo(UPDATED_CHEMIN);
        assertThat(testPieceJointe.isIsOriginale()).isEqualTo(UPDATED_IS_ORIGINALE);
        assertThat(testPieceJointe.getDateImport()).isEqualTo(UPDATED_DATE_IMPORT);

        // Validate the PieceJointe in Elasticsearch
        PieceJointe pieceJointeEs = pieceJointeSearchRepository.findOne(testPieceJointe.getId());
        assertThat(pieceJointeEs).isEqualToComparingFieldByField(testPieceJointe);
    }

    @Test
    @Transactional
    public void updateNonExistingPieceJointe() throws Exception {
        int databaseSizeBeforeUpdate = pieceJointeRepository.findAll().size();

        // Create the PieceJointe
        PieceJointeDTO pieceJointeDTO = pieceJointeMapper.toDto(pieceJointe);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPieceJointeMockMvc.perform(put("/api/piece-jointes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pieceJointeDTO)))
            .andExpect(status().isCreated());

        // Validate the PieceJointe in the database
        List<PieceJointe> pieceJointeList = pieceJointeRepository.findAll();
        assertThat(pieceJointeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePieceJointe() throws Exception {
        // Initialize the database
        pieceJointeRepository.saveAndFlush(pieceJointe);
        pieceJointeSearchRepository.save(pieceJointe);
        int databaseSizeBeforeDelete = pieceJointeRepository.findAll().size();

        // Get the pieceJointe
        restPieceJointeMockMvc.perform(delete("/api/piece-jointes/{id}", pieceJointe.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean pieceJointeExistsInEs = pieceJointeSearchRepository.exists(pieceJointe.getId());
        assertThat(pieceJointeExistsInEs).isFalse();

        // Validate the database is empty
        List<PieceJointe> pieceJointeList = pieceJointeRepository.findAll();
        assertThat(pieceJointeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchPieceJointe() throws Exception {
        // Initialize the database
        pieceJointeRepository.saveAndFlush(pieceJointe);
        pieceJointeSearchRepository.save(pieceJointe);

        // Search the pieceJointe
        restPieceJointeMockMvc.perform(get("/api/_search/piece-jointes?query=id:" + pieceJointe.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pieceJointe.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())))
            .andExpect(jsonPath("$.[*].chemin").value(hasItem(DEFAULT_CHEMIN.toString())))
            .andExpect(jsonPath("$.[*].isOriginale").value(hasItem(DEFAULT_IS_ORIGINALE.booleanValue())))
            .andExpect(jsonPath("$.[*].dateImport").value(hasItem(DEFAULT_DATE_IMPORT.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PieceJointe.class);
        PieceJointe pieceJointe1 = new PieceJointe();
        pieceJointe1.setId(1L);
        PieceJointe pieceJointe2 = new PieceJointe();
        pieceJointe2.setId(pieceJointe1.getId());
        assertThat(pieceJointe1).isEqualTo(pieceJointe2);
        pieceJointe2.setId(2L);
        assertThat(pieceJointe1).isNotEqualTo(pieceJointe2);
        pieceJointe1.setId(null);
        assertThat(pieceJointe1).isNotEqualTo(pieceJointe2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PieceJointeDTO.class);
        PieceJointeDTO pieceJointeDTO1 = new PieceJointeDTO();
        pieceJointeDTO1.setId(1L);
        PieceJointeDTO pieceJointeDTO2 = new PieceJointeDTO();
        assertThat(pieceJointeDTO1).isNotEqualTo(pieceJointeDTO2);
        pieceJointeDTO2.setId(pieceJointeDTO1.getId());
        assertThat(pieceJointeDTO1).isEqualTo(pieceJointeDTO2);
        pieceJointeDTO2.setId(2L);
        assertThat(pieceJointeDTO1).isNotEqualTo(pieceJointeDTO2);
        pieceJointeDTO1.setId(null);
        assertThat(pieceJointeDTO1).isNotEqualTo(pieceJointeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(pieceJointeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(pieceJointeMapper.fromId(null)).isNull();
    }
}
