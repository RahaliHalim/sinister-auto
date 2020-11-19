package com.gaconnecte.auxilium.web.rest;

import com.gaconnecte.auxilium.AuxiliumApp;

import com.gaconnecte.auxilium.domain.RefTypeIntervention;
import com.gaconnecte.auxilium.repository.RefTypeInterventionRepository;
import com.gaconnecte.auxilium.service.RefTypeInterventionService;
import com.gaconnecte.auxilium.repository.search.RefTypeInterventionSearchRepository;
import com.gaconnecte.auxilium.service.dto.RefTypeInterventionDTO;
import com.gaconnecte.auxilium.service.mapper.RefTypeInterventionMapper;
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
 * Test class for the RefTypeInterventionResource REST controller.
 *
 * @see RefTypeInterventionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuxiliumApp.class)
public class RefTypeInterventionResourceIntTest {

    private static final String DEFAULT_LIBELLE = "AB";
    private static final String UPDATED_LIBELLE = "AC";

    @Autowired
    private RefTypeInterventionRepository refTypeInterventionRepository;

    @Autowired
    private RefTypeInterventionMapper refTypeInterventionMapper;

    @Autowired
    private RefTypeInterventionService refTypeInterventionService;

    @Autowired
    private RefTypeInterventionSearchRepository refTypeInterventionSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRefTypeInterventionMockMvc;

    private RefTypeIntervention refTypeIntervention;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RefTypeInterventionResource refTypeInterventionResource = new RefTypeInterventionResource(refTypeInterventionService);
        this.restRefTypeInterventionMockMvc = MockMvcBuilders.standaloneSetup(refTypeInterventionResource)
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
    public static RefTypeIntervention createEntity(EntityManager em) {
        RefTypeIntervention refTypeIntervention = new RefTypeIntervention()
            .libelle(DEFAULT_LIBELLE);
        return refTypeIntervention;
    }

    @Before
    public void initTest() {
        refTypeInterventionSearchRepository.deleteAll();
        refTypeIntervention = createEntity(em);
    }

    @Test
    @Transactional
    public void createRefTypeIntervention() throws Exception {
        int databaseSizeBeforeCreate = refTypeInterventionRepository.findAll().size();

        // Create the RefTypeIntervention
        RefTypeInterventionDTO refTypeInterventionDTO = refTypeInterventionMapper.toDto(refTypeIntervention);
        restRefTypeInterventionMockMvc.perform(post("/api/ref-type-interventions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refTypeInterventionDTO)))
            .andExpect(status().isCreated());

        // Validate the RefTypeIntervention in the database
        List<RefTypeIntervention> refTypeInterventionList = refTypeInterventionRepository.findAll();
        assertThat(refTypeInterventionList).hasSize(databaseSizeBeforeCreate + 1);
        RefTypeIntervention testRefTypeIntervention = refTypeInterventionList.get(refTypeInterventionList.size() - 1);
        assertThat(testRefTypeIntervention.getLibelle()).isEqualTo(DEFAULT_LIBELLE);

        // Validate the RefTypeIntervention in Elasticsearch
        RefTypeIntervention refTypeInterventionEs = refTypeInterventionSearchRepository.findOne(testRefTypeIntervention.getId());
        assertThat(refTypeInterventionEs).isEqualToComparingFieldByField(testRefTypeIntervention);
    }

    @Test
    @Transactional
    public void createRefTypeInterventionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = refTypeInterventionRepository.findAll().size();

        // Create the RefTypeIntervention with an existing ID
        refTypeIntervention.setId(1L);
        RefTypeInterventionDTO refTypeInterventionDTO = refTypeInterventionMapper.toDto(refTypeIntervention);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRefTypeInterventionMockMvc.perform(post("/api/ref-type-interventions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refTypeInterventionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<RefTypeIntervention> refTypeInterventionList = refTypeInterventionRepository.findAll();
        assertThat(refTypeInterventionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = refTypeInterventionRepository.findAll().size();
        // set the field null
        refTypeIntervention.setLibelle(null);

        // Create the RefTypeIntervention, which fails.
        RefTypeInterventionDTO refTypeInterventionDTO = refTypeInterventionMapper.toDto(refTypeIntervention);

        restRefTypeInterventionMockMvc.perform(post("/api/ref-type-interventions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refTypeInterventionDTO)))
            .andExpect(status().isBadRequest());

        List<RefTypeIntervention> refTypeInterventionList = refTypeInterventionRepository.findAll();
        assertThat(refTypeInterventionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRefTypeInterventions() throws Exception {
        // Initialize the database
        refTypeInterventionRepository.saveAndFlush(refTypeIntervention);

        // Get all the refTypeInterventionList
        restRefTypeInterventionMockMvc.perform(get("/api/ref-type-interventions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(refTypeIntervention.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())));
    }

    @Test
    @Transactional
    public void getRefTypeIntervention() throws Exception {
        // Initialize the database
        refTypeInterventionRepository.saveAndFlush(refTypeIntervention);

        // Get the refTypeIntervention
        restRefTypeInterventionMockMvc.perform(get("/api/ref-type-interventions/{id}", refTypeIntervention.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(refTypeIntervention.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRefTypeIntervention() throws Exception {
        // Get the refTypeIntervention
        restRefTypeInterventionMockMvc.perform(get("/api/ref-type-interventions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRefTypeIntervention() throws Exception {
        // Initialize the database
        refTypeInterventionRepository.saveAndFlush(refTypeIntervention);
        refTypeInterventionSearchRepository.save(refTypeIntervention);
        int databaseSizeBeforeUpdate = refTypeInterventionRepository.findAll().size();

        // Update the refTypeIntervention
        RefTypeIntervention updatedRefTypeIntervention = refTypeInterventionRepository.findOne(refTypeIntervention.getId());
        updatedRefTypeIntervention
            .libelle(UPDATED_LIBELLE);
        RefTypeInterventionDTO refTypeInterventionDTO = refTypeInterventionMapper.toDto(updatedRefTypeIntervention);

        restRefTypeInterventionMockMvc.perform(put("/api/ref-type-interventions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refTypeInterventionDTO)))
            .andExpect(status().isOk());

        // Validate the RefTypeIntervention in the database
        List<RefTypeIntervention> refTypeInterventionList = refTypeInterventionRepository.findAll();
        assertThat(refTypeInterventionList).hasSize(databaseSizeBeforeUpdate);
        RefTypeIntervention testRefTypeIntervention = refTypeInterventionList.get(refTypeInterventionList.size() - 1);
        assertThat(testRefTypeIntervention.getLibelle()).isEqualTo(UPDATED_LIBELLE);

        // Validate the RefTypeIntervention in Elasticsearch
        RefTypeIntervention refTypeInterventionEs = refTypeInterventionSearchRepository.findOne(testRefTypeIntervention.getId());
        assertThat(refTypeInterventionEs).isEqualToComparingFieldByField(testRefTypeIntervention);
    }

    @Test
    @Transactional
    public void updateNonExistingRefTypeIntervention() throws Exception {
        int databaseSizeBeforeUpdate = refTypeInterventionRepository.findAll().size();

        // Create the RefTypeIntervention
        RefTypeInterventionDTO refTypeInterventionDTO = refTypeInterventionMapper.toDto(refTypeIntervention);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRefTypeInterventionMockMvc.perform(put("/api/ref-type-interventions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refTypeInterventionDTO)))
            .andExpect(status().isCreated());

        // Validate the RefTypeIntervention in the database
        List<RefTypeIntervention> refTypeInterventionList = refTypeInterventionRepository.findAll();
        assertThat(refTypeInterventionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRefTypeIntervention() throws Exception {
        // Initialize the database
        refTypeInterventionRepository.saveAndFlush(refTypeIntervention);
        refTypeInterventionSearchRepository.save(refTypeIntervention);
        int databaseSizeBeforeDelete = refTypeInterventionRepository.findAll().size();

        // Get the refTypeIntervention
        restRefTypeInterventionMockMvc.perform(delete("/api/ref-type-interventions/{id}", refTypeIntervention.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean refTypeInterventionExistsInEs = refTypeInterventionSearchRepository.exists(refTypeIntervention.getId());
        assertThat(refTypeInterventionExistsInEs).isFalse();

        // Validate the database is empty
        List<RefTypeIntervention> refTypeInterventionList = refTypeInterventionRepository.findAll();
        assertThat(refTypeInterventionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchRefTypeIntervention() throws Exception {
        // Initialize the database
        refTypeInterventionRepository.saveAndFlush(refTypeIntervention);
        refTypeInterventionSearchRepository.save(refTypeIntervention);

        // Search the refTypeIntervention
        restRefTypeInterventionMockMvc.perform(get("/api/_search/ref-type-interventions?query=id:" + refTypeIntervention.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(refTypeIntervention.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RefTypeIntervention.class);
        RefTypeIntervention refTypeIntervention1 = new RefTypeIntervention();
        refTypeIntervention1.setId(1L);
        RefTypeIntervention refTypeIntervention2 = new RefTypeIntervention();
        refTypeIntervention2.setId(refTypeIntervention1.getId());
        assertThat(refTypeIntervention1).isEqualTo(refTypeIntervention2);
        refTypeIntervention2.setId(2L);
        assertThat(refTypeIntervention1).isNotEqualTo(refTypeIntervention2);
        refTypeIntervention1.setId(null);
        assertThat(refTypeIntervention1).isNotEqualTo(refTypeIntervention2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RefTypeInterventionDTO.class);
        RefTypeInterventionDTO refTypeInterventionDTO1 = new RefTypeInterventionDTO();
        refTypeInterventionDTO1.setId(1L);
        RefTypeInterventionDTO refTypeInterventionDTO2 = new RefTypeInterventionDTO();
        assertThat(refTypeInterventionDTO1).isNotEqualTo(refTypeInterventionDTO2);
        refTypeInterventionDTO2.setId(refTypeInterventionDTO1.getId());
        assertThat(refTypeInterventionDTO1).isEqualTo(refTypeInterventionDTO2);
        refTypeInterventionDTO2.setId(2L);
        assertThat(refTypeInterventionDTO1).isNotEqualTo(refTypeInterventionDTO2);
        refTypeInterventionDTO1.setId(null);
        assertThat(refTypeInterventionDTO1).isNotEqualTo(refTypeInterventionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(refTypeInterventionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(refTypeInterventionMapper.fromId(null)).isNull();
    }
}
