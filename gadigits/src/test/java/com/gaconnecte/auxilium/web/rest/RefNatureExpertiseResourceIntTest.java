package com.gaconnecte.auxilium.web.rest;

import com.gaconnecte.auxilium.AuxiliumApp;

import com.gaconnecte.auxilium.domain.RefNatureExpertise;
import com.gaconnecte.auxilium.repository.RefNatureExpertiseRepository;
import com.gaconnecte.auxilium.service.RefNatureExpertiseService;
import com.gaconnecte.auxilium.repository.search.RefNatureExpertiseSearchRepository;
import com.gaconnecte.auxilium.service.dto.RefNatureExpertiseDTO;
import com.gaconnecte.auxilium.service.mapper.RefNatureExpertiseMapper;
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
 * Test class for the RefNatureExpertiseResource REST controller.
 *
 * @see RefNatureExpertiseResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuxiliumApp.class)
public class RefNatureExpertiseResourceIntTest {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    @Autowired
    private RefNatureExpertiseRepository refNatureExpertiseRepository;

    @Autowired
    private RefNatureExpertiseMapper refNatureExpertiseMapper;

    @Autowired
    private RefNatureExpertiseService refNatureExpertiseService;

    @Autowired
    private RefNatureExpertiseSearchRepository refNatureExpertiseSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRefNatureExpertiseMockMvc;

    private RefNatureExpertise refNatureExpertise;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RefNatureExpertiseResource refNatureExpertiseResource = new RefNatureExpertiseResource(refNatureExpertiseService);
        this.restRefNatureExpertiseMockMvc = MockMvcBuilders.standaloneSetup(refNatureExpertiseResource)
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
    public static RefNatureExpertise createEntity(EntityManager em) {
        RefNatureExpertise refNatureExpertise = new RefNatureExpertise()
            .libelle(DEFAULT_LIBELLE)
            .isActive(DEFAULT_IS_ACTIVE);
        return refNatureExpertise;
    }

    @Before
    public void initTest() {
        refNatureExpertiseSearchRepository.deleteAll();
        refNatureExpertise = createEntity(em);
    }

    @Test
    @Transactional
    public void createRefNatureExpertise() throws Exception {
        int databaseSizeBeforeCreate = refNatureExpertiseRepository.findAll().size();

        // Create the RefNatureExpertise
        RefNatureExpertiseDTO refNatureExpertiseDTO = refNatureExpertiseMapper.toDto(refNatureExpertise);
        restRefNatureExpertiseMockMvc.perform(post("/api/ref-nature-expertises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refNatureExpertiseDTO)))
            .andExpect(status().isCreated());

        // Validate the RefNatureExpertise in the database
        List<RefNatureExpertise> refNatureExpertiseList = refNatureExpertiseRepository.findAll();
        assertThat(refNatureExpertiseList).hasSize(databaseSizeBeforeCreate + 1);
        RefNatureExpertise testRefNatureExpertise = refNatureExpertiseList.get(refNatureExpertiseList.size() - 1);
        assertThat(testRefNatureExpertise.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testRefNatureExpertise.isIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);

        // Validate the RefNatureExpertise in Elasticsearch
        RefNatureExpertise refNatureExpertiseEs = refNatureExpertiseSearchRepository.findOne(testRefNatureExpertise.getId());
        assertThat(refNatureExpertiseEs).isEqualToComparingFieldByField(testRefNatureExpertise);
    }

    @Test
    @Transactional
    public void createRefNatureExpertiseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = refNatureExpertiseRepository.findAll().size();

        // Create the RefNatureExpertise with an existing ID
        refNatureExpertise.setId(1L);
        RefNatureExpertiseDTO refNatureExpertiseDTO = refNatureExpertiseMapper.toDto(refNatureExpertise);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRefNatureExpertiseMockMvc.perform(post("/api/ref-nature-expertises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refNatureExpertiseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<RefNatureExpertise> refNatureExpertiseList = refNatureExpertiseRepository.findAll();
        assertThat(refNatureExpertiseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = refNatureExpertiseRepository.findAll().size();
        // set the field null
        refNatureExpertise.setLibelle(null);

        // Create the RefNatureExpertise, which fails.
        RefNatureExpertiseDTO refNatureExpertiseDTO = refNatureExpertiseMapper.toDto(refNatureExpertise);

        restRefNatureExpertiseMockMvc.perform(post("/api/ref-nature-expertises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refNatureExpertiseDTO)))
            .andExpect(status().isBadRequest());

        List<RefNatureExpertise> refNatureExpertiseList = refNatureExpertiseRepository.findAll();
        assertThat(refNatureExpertiseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRefNatureExpertises() throws Exception {
        // Initialize the database
        refNatureExpertiseRepository.saveAndFlush(refNatureExpertise);

        // Get all the refNatureExpertiseList
        restRefNatureExpertiseMockMvc.perform(get("/api/ref-nature-expertises?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(refNatureExpertise.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void getRefNatureExpertise() throws Exception {
        // Initialize the database
        refNatureExpertiseRepository.saveAndFlush(refNatureExpertise);

        // Get the refNatureExpertise
        restRefNatureExpertiseMockMvc.perform(get("/api/ref-nature-expertises/{id}", refNatureExpertise.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(refNatureExpertise.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingRefNatureExpertise() throws Exception {
        // Get the refNatureExpertise
        restRefNatureExpertiseMockMvc.perform(get("/api/ref-nature-expertises/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRefNatureExpertise() throws Exception {
        // Initialize the database
        refNatureExpertiseRepository.saveAndFlush(refNatureExpertise);
        refNatureExpertiseSearchRepository.save(refNatureExpertise);
        int databaseSizeBeforeUpdate = refNatureExpertiseRepository.findAll().size();

        // Update the refNatureExpertise
        RefNatureExpertise updatedRefNatureExpertise = refNatureExpertiseRepository.findOne(refNatureExpertise.getId());
        updatedRefNatureExpertise
            .libelle(UPDATED_LIBELLE)
            .isActive(UPDATED_IS_ACTIVE);
        RefNatureExpertiseDTO refNatureExpertiseDTO = refNatureExpertiseMapper.toDto(updatedRefNatureExpertise);

        restRefNatureExpertiseMockMvc.perform(put("/api/ref-nature-expertises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refNatureExpertiseDTO)))
            .andExpect(status().isOk());

        // Validate the RefNatureExpertise in the database
        List<RefNatureExpertise> refNatureExpertiseList = refNatureExpertiseRepository.findAll();
        assertThat(refNatureExpertiseList).hasSize(databaseSizeBeforeUpdate);
        RefNatureExpertise testRefNatureExpertise = refNatureExpertiseList.get(refNatureExpertiseList.size() - 1);
        assertThat(testRefNatureExpertise.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testRefNatureExpertise.isIsActive()).isEqualTo(UPDATED_IS_ACTIVE);

        // Validate the RefNatureExpertise in Elasticsearch
        RefNatureExpertise refNatureExpertiseEs = refNatureExpertiseSearchRepository.findOne(testRefNatureExpertise.getId());
        assertThat(refNatureExpertiseEs).isEqualToComparingFieldByField(testRefNatureExpertise);
    }

    @Test
    @Transactional
    public void updateNonExistingRefNatureExpertise() throws Exception {
        int databaseSizeBeforeUpdate = refNatureExpertiseRepository.findAll().size();

        // Create the RefNatureExpertise
        RefNatureExpertiseDTO refNatureExpertiseDTO = refNatureExpertiseMapper.toDto(refNatureExpertise);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRefNatureExpertiseMockMvc.perform(put("/api/ref-nature-expertises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refNatureExpertiseDTO)))
            .andExpect(status().isCreated());

        // Validate the RefNatureExpertise in the database
        List<RefNatureExpertise> refNatureExpertiseList = refNatureExpertiseRepository.findAll();
        assertThat(refNatureExpertiseList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRefNatureExpertise() throws Exception {
        // Initialize the database
        refNatureExpertiseRepository.saveAndFlush(refNatureExpertise);
        refNatureExpertiseSearchRepository.save(refNatureExpertise);
        int databaseSizeBeforeDelete = refNatureExpertiseRepository.findAll().size();

        // Get the refNatureExpertise
        restRefNatureExpertiseMockMvc.perform(delete("/api/ref-nature-expertises/{id}", refNatureExpertise.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean refNatureExpertiseExistsInEs = refNatureExpertiseSearchRepository.exists(refNatureExpertise.getId());
        assertThat(refNatureExpertiseExistsInEs).isFalse();

        // Validate the database is empty
        List<RefNatureExpertise> refNatureExpertiseList = refNatureExpertiseRepository.findAll();
        assertThat(refNatureExpertiseList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchRefNatureExpertise() throws Exception {
        // Initialize the database
        refNatureExpertiseRepository.saveAndFlush(refNatureExpertise);
        refNatureExpertiseSearchRepository.save(refNatureExpertise);

        // Search the refNatureExpertise
        restRefNatureExpertiseMockMvc.perform(get("/api/_search/ref-nature-expertises?query=id:" + refNatureExpertise.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(refNatureExpertise.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RefNatureExpertise.class);
        RefNatureExpertise refNatureExpertise1 = new RefNatureExpertise();
        refNatureExpertise1.setId(1L);
        RefNatureExpertise refNatureExpertise2 = new RefNatureExpertise();
        refNatureExpertise2.setId(refNatureExpertise1.getId());
        assertThat(refNatureExpertise1).isEqualTo(refNatureExpertise2);
        refNatureExpertise2.setId(2L);
        assertThat(refNatureExpertise1).isNotEqualTo(refNatureExpertise2);
        refNatureExpertise1.setId(null);
        assertThat(refNatureExpertise1).isNotEqualTo(refNatureExpertise2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RefNatureExpertiseDTO.class);
        RefNatureExpertiseDTO refNatureExpertiseDTO1 = new RefNatureExpertiseDTO();
        refNatureExpertiseDTO1.setId(1L);
        RefNatureExpertiseDTO refNatureExpertiseDTO2 = new RefNatureExpertiseDTO();
        assertThat(refNatureExpertiseDTO1).isNotEqualTo(refNatureExpertiseDTO2);
        refNatureExpertiseDTO2.setId(refNatureExpertiseDTO1.getId());
        assertThat(refNatureExpertiseDTO1).isEqualTo(refNatureExpertiseDTO2);
        refNatureExpertiseDTO2.setId(2L);
        assertThat(refNatureExpertiseDTO1).isNotEqualTo(refNatureExpertiseDTO2);
        refNatureExpertiseDTO1.setId(null);
        assertThat(refNatureExpertiseDTO1).isNotEqualTo(refNatureExpertiseDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(refNatureExpertiseMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(refNatureExpertiseMapper.fromId(null)).isNull();
    }
}
