package com.gaconnecte.auxilium.web.rest;

import com.gaconnecte.auxilium.AuxiliumApp;

import com.gaconnecte.auxilium.domain.Grille;
import com.gaconnecte.auxilium.repository.GrilleRepository;
import com.gaconnecte.auxilium.service.GrilleService;
import com.gaconnecte.auxilium.repository.search.GrilleSearchRepository;
import com.gaconnecte.auxilium.service.dto.GrilleDTO;
import com.gaconnecte.auxilium.service.mapper.GrilleMapper;
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
 * Test class for the GrilleResource REST controller.
 *
 * @see GrilleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuxiliumApp.class)
public class GrilleResourceIntTest {

    private static final Float DEFAULT_TH = 5F;
    private static final Float UPDATED_TH = 5F;

    private static final Float DEFAULT_REMISE = 1F;
    private static final Float UPDATED_REMISE = 1F;

    private static final Float DEFAULT_TVA = 1F;
    private static final Float UPDATED_TVA = 1F;

    @Autowired
    private GrilleRepository grilleRepository;

    @Autowired
    private GrilleMapper grilleMapper;

    @Autowired
    private GrilleService grilleService;

    @Autowired
    private GrilleSearchRepository grilleSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGrilleMockMvc;

    private Grille grille;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        GrilleResource grilleResource = new GrilleResource(grilleService);
        this.restGrilleMockMvc = MockMvcBuilders.standaloneSetup(grilleResource)
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
    public static Grille createEntity(EntityManager em) {
        Grille grille = new Grille()
            .th(DEFAULT_TH)
            .remise(DEFAULT_REMISE)
            .tva(DEFAULT_TVA);
        return grille;
    }

    @Before
    public void initTest() {
        grilleSearchRepository.deleteAll();
        grille = createEntity(em);
    }

    @Test
    @Transactional
    public void createGrille() throws Exception {
        int databaseSizeBeforeCreate = grilleRepository.findAll().size();

        // Create the Grille
        GrilleDTO grilleDTO = grilleMapper.toDto(grille);
        restGrilleMockMvc.perform(post("/api/grilles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(grilleDTO)))
            .andExpect(status().isCreated());

        // Validate the Grille in the database
        List<Grille> grilleList = grilleRepository.findAll();
        assertThat(grilleList).hasSize(databaseSizeBeforeCreate + 1);
        Grille testGrille = grilleList.get(grilleList.size() - 1);
        assertThat(testGrille.getTh()).isEqualTo(DEFAULT_TH);
        assertThat(testGrille.getRemise()).isEqualTo(DEFAULT_REMISE);
        assertThat(testGrille.getTva()).isEqualTo(DEFAULT_TVA);

        // Validate the Grille in Elasticsearch
        Grille grilleEs = grilleSearchRepository.findOne(testGrille.getId());
        assertThat(grilleEs).isEqualToComparingFieldByField(testGrille);
    }

    @Test
    @Transactional
    public void createGrilleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = grilleRepository.findAll().size();

        // Create the Grille with an existing ID
        grille.setId(1L);
        GrilleDTO grilleDTO = grilleMapper.toDto(grille);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGrilleMockMvc.perform(post("/api/grilles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(grilleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Grille> grilleList = grilleRepository.findAll();
        assertThat(grilleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkThIsRequired() throws Exception {
        int databaseSizeBeforeTest = grilleRepository.findAll().size();
        // set the field null
        grille.setTh(null);

        // Create the Grille, which fails.
        GrilleDTO grilleDTO = grilleMapper.toDto(grille);

        restGrilleMockMvc.perform(post("/api/grilles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(grilleDTO)))
            .andExpect(status().isBadRequest());

        List<Grille> grilleList = grilleRepository.findAll();
        assertThat(grilleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGrilles() throws Exception {
        // Initialize the database
        grilleRepository.saveAndFlush(grille);

        // Get all the grilleList
        restGrilleMockMvc.perform(get("/api/grilles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(grille.getId().intValue())))
            .andExpect(jsonPath("$.[*].th").value(hasItem(DEFAULT_TH.doubleValue())))
            .andExpect(jsonPath("$.[*].remise").value(hasItem(DEFAULT_REMISE.doubleValue())))
            .andExpect(jsonPath("$.[*].tva").value(hasItem(DEFAULT_TVA.doubleValue())));
    }

    @Test
    @Transactional
    public void getGrille() throws Exception {
        // Initialize the database
        grilleRepository.saveAndFlush(grille);

        // Get the grille
        restGrilleMockMvc.perform(get("/api/grilles/{id}", grille.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(grille.getId().intValue()))
            .andExpect(jsonPath("$.th").value(DEFAULT_TH.doubleValue()))
            .andExpect(jsonPath("$.remise").value(DEFAULT_REMISE.doubleValue()))
            .andExpect(jsonPath("$.tva").value(DEFAULT_TVA.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingGrille() throws Exception {
        // Get the grille
        restGrilleMockMvc.perform(get("/api/grilles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGrille() throws Exception {
        // Initialize the database
        grilleRepository.saveAndFlush(grille);
        grilleSearchRepository.save(grille);
        int databaseSizeBeforeUpdate = grilleRepository.findAll().size();

        // Update the grille
        Grille updatedGrille = grilleRepository.findOne(grille.getId());
        updatedGrille
            .th(UPDATED_TH)
            .remise(UPDATED_REMISE)
            .tva(UPDATED_TVA);
        GrilleDTO grilleDTO = grilleMapper.toDto(updatedGrille);

        restGrilleMockMvc.perform(put("/api/grilles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(grilleDTO)))
            .andExpect(status().isOk());

        // Validate the Grille in the database
        List<Grille> grilleList = grilleRepository.findAll();
        assertThat(grilleList).hasSize(databaseSizeBeforeUpdate);
        Grille testGrille = grilleList.get(grilleList.size() - 1);
        assertThat(testGrille.getTh()).isEqualTo(UPDATED_TH);
        assertThat(testGrille.getRemise()).isEqualTo(UPDATED_REMISE);
        assertThat(testGrille.getTva()).isEqualTo(UPDATED_TVA);

        // Validate the Grille in Elasticsearch
        Grille grilleEs = grilleSearchRepository.findOne(testGrille.getId());
        assertThat(grilleEs).isEqualToComparingFieldByField(testGrille);
    }

    @Test
    @Transactional
    public void updateNonExistingGrille() throws Exception {
        int databaseSizeBeforeUpdate = grilleRepository.findAll().size();

        // Create the Grille
        GrilleDTO grilleDTO = grilleMapper.toDto(grille);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restGrilleMockMvc.perform(put("/api/grilles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(grilleDTO)))
            .andExpect(status().isCreated());

        // Validate the Grille in the database
        List<Grille> grilleList = grilleRepository.findAll();
        assertThat(grilleList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteGrille() throws Exception {
        // Initialize the database
        grilleRepository.saveAndFlush(grille);
        grilleSearchRepository.save(grille);
        int databaseSizeBeforeDelete = grilleRepository.findAll().size();

        // Get the grille
        restGrilleMockMvc.perform(delete("/api/grilles/{id}", grille.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean grilleExistsInEs = grilleSearchRepository.exists(grille.getId());
        assertThat(grilleExistsInEs).isFalse();

        // Validate the database is empty
        List<Grille> grilleList = grilleRepository.findAll();
        assertThat(grilleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchGrille() throws Exception {
        // Initialize the database
        grilleRepository.saveAndFlush(grille);
        grilleSearchRepository.save(grille);

        // Search the grille
        restGrilleMockMvc.perform(get("/api/_search/grilles?query=id:" + grille.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(grille.getId().intValue())))
            .andExpect(jsonPath("$.[*].th").value(hasItem(DEFAULT_TH.doubleValue())))
            .andExpect(jsonPath("$.[*].remise").value(hasItem(DEFAULT_REMISE.doubleValue())))
            .andExpect(jsonPath("$.[*].tva").value(hasItem(DEFAULT_TVA.doubleValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Grille.class);
        Grille grille1 = new Grille();
        grille1.setId(1L);
        Grille grille2 = new Grille();
        grille2.setId(grille1.getId());
        assertThat(grille1).isEqualTo(grille2);
        grille2.setId(2L);
        assertThat(grille1).isNotEqualTo(grille2);
        grille1.setId(null);
        assertThat(grille1).isNotEqualTo(grille2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GrilleDTO.class);
        GrilleDTO grilleDTO1 = new GrilleDTO();
        grilleDTO1.setId(1L);
        GrilleDTO grilleDTO2 = new GrilleDTO();
        assertThat(grilleDTO1).isNotEqualTo(grilleDTO2);
        grilleDTO2.setId(grilleDTO1.getId());
        assertThat(grilleDTO1).isEqualTo(grilleDTO2);
        grilleDTO2.setId(2L);
        assertThat(grilleDTO1).isNotEqualTo(grilleDTO2);
        grilleDTO1.setId(null);
        assertThat(grilleDTO1).isNotEqualTo(grilleDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(grilleMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(grilleMapper.fromId(null)).isNull();
    }
}
