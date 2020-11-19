package com.gaconnecte.auxilium.web.rest;

import com.gaconnecte.auxilium.AuxiliumApp;

import com.gaconnecte.auxilium.domain.RefFractionnement;
import com.gaconnecte.auxilium.repository.RefFractionnementRepository;
import com.gaconnecte.auxilium.service.RefFractionnementService;
import com.gaconnecte.auxilium.repository.search.RefFractionnementSearchRepository;
import com.gaconnecte.auxilium.service.dto.RefFractionnementDTO;
import com.gaconnecte.auxilium.service.mapper.RefFractionnementMapper;
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
 * Test class for the RefFractionnementResource REST controller.
 *
 * @see RefFractionnementResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuxiliumApp.class)
public class RefFractionnementResourceIntTest {

    private static final Integer DEFAULT_CODE = 99999999;
    private static final Integer UPDATED_CODE = 99999998;

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    @Autowired
    private RefFractionnementRepository refFractionnementRepository;

    @Autowired
    private RefFractionnementMapper refFractionnementMapper;

    @Autowired
    private RefFractionnementService refFractionnementService;

    @Autowired
    private RefFractionnementSearchRepository refFractionnementSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRefFractionnementMockMvc;

    private RefFractionnement refFractionnement;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RefFractionnementResource refFractionnementResource = new RefFractionnementResource(refFractionnementService);
        this.restRefFractionnementMockMvc = MockMvcBuilders.standaloneSetup(refFractionnementResource)
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
    public static RefFractionnement createEntity(EntityManager em) {
        RefFractionnement refFractionnement = new RefFractionnement()
            .code(DEFAULT_CODE)
            .libelle(DEFAULT_LIBELLE);
        return refFractionnement;
    }

    @Before
    public void initTest() {
        refFractionnementSearchRepository.deleteAll();
        refFractionnement = createEntity(em);
    }

    @Test
    @Transactional
    public void createRefFractionnement() throws Exception {
        int databaseSizeBeforeCreate = refFractionnementRepository.findAll().size();

        // Create the RefFractionnement
        RefFractionnementDTO refFractionnementDTO = refFractionnementMapper.toDto(refFractionnement);
        restRefFractionnementMockMvc.perform(post("/api/ref-fractionnements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refFractionnementDTO)))
            .andExpect(status().isCreated());

        // Validate the RefFractionnement in the database
        List<RefFractionnement> refFractionnementList = refFractionnementRepository.findAll();
        assertThat(refFractionnementList).hasSize(databaseSizeBeforeCreate + 1);
        RefFractionnement testRefFractionnement = refFractionnementList.get(refFractionnementList.size() - 1);
        assertThat(testRefFractionnement.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testRefFractionnement.getLibelle()).isEqualTo(DEFAULT_LIBELLE);

        // Validate the RefFractionnement in Elasticsearch
        RefFractionnement refFractionnementEs = refFractionnementSearchRepository.findOne(testRefFractionnement.getId());
        assertThat(refFractionnementEs).isEqualToComparingFieldByField(testRefFractionnement);
    }

    @Test
    @Transactional
    public void createRefFractionnementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = refFractionnementRepository.findAll().size();

        // Create the RefFractionnement with an existing ID
        refFractionnement.setId(1L);
        RefFractionnementDTO refFractionnementDTO = refFractionnementMapper.toDto(refFractionnement);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRefFractionnementMockMvc.perform(post("/api/ref-fractionnements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refFractionnementDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<RefFractionnement> refFractionnementList = refFractionnementRepository.findAll();
        assertThat(refFractionnementList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = refFractionnementRepository.findAll().size();
        // set the field null
        refFractionnement.setCode(null);

        // Create the RefFractionnement, which fails.
        RefFractionnementDTO refFractionnementDTO = refFractionnementMapper.toDto(refFractionnement);

        restRefFractionnementMockMvc.perform(post("/api/ref-fractionnements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refFractionnementDTO)))
            .andExpect(status().isBadRequest());

        List<RefFractionnement> refFractionnementList = refFractionnementRepository.findAll();
        assertThat(refFractionnementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = refFractionnementRepository.findAll().size();
        // set the field null
        refFractionnement.setLibelle(null);

        // Create the RefFractionnement, which fails.
        RefFractionnementDTO refFractionnementDTO = refFractionnementMapper.toDto(refFractionnement);

        restRefFractionnementMockMvc.perform(post("/api/ref-fractionnements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refFractionnementDTO)))
            .andExpect(status().isBadRequest());

        List<RefFractionnement> refFractionnementList = refFractionnementRepository.findAll();
        assertThat(refFractionnementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRefFractionnements() throws Exception {
        // Initialize the database
        refFractionnementRepository.saveAndFlush(refFractionnement);

        // Get all the refFractionnementList
        restRefFractionnementMockMvc.perform(get("/api/ref-fractionnements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(refFractionnement.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())));
    }

    @Test
    @Transactional
    public void getRefFractionnement() throws Exception {
        // Initialize the database
        refFractionnementRepository.saveAndFlush(refFractionnement);

        // Get the refFractionnement
        restRefFractionnementMockMvc.perform(get("/api/ref-fractionnements/{id}", refFractionnement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(refFractionnement.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRefFractionnement() throws Exception {
        // Get the refFractionnement
        restRefFractionnementMockMvc.perform(get("/api/ref-fractionnements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRefFractionnement() throws Exception {
        // Initialize the database
        refFractionnementRepository.saveAndFlush(refFractionnement);
        refFractionnementSearchRepository.save(refFractionnement);
        int databaseSizeBeforeUpdate = refFractionnementRepository.findAll().size();

        // Update the refFractionnement
        RefFractionnement updatedRefFractionnement = refFractionnementRepository.findOne(refFractionnement.getId());
        updatedRefFractionnement
            .code(UPDATED_CODE)
            .libelle(UPDATED_LIBELLE);
        RefFractionnementDTO refFractionnementDTO = refFractionnementMapper.toDto(updatedRefFractionnement);

        restRefFractionnementMockMvc.perform(put("/api/ref-fractionnements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refFractionnementDTO)))
            .andExpect(status().isOk());

        // Validate the RefFractionnement in the database
        List<RefFractionnement> refFractionnementList = refFractionnementRepository.findAll();
        assertThat(refFractionnementList).hasSize(databaseSizeBeforeUpdate);
        RefFractionnement testRefFractionnement = refFractionnementList.get(refFractionnementList.size() - 1);
        assertThat(testRefFractionnement.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testRefFractionnement.getLibelle()).isEqualTo(UPDATED_LIBELLE);

        // Validate the RefFractionnement in Elasticsearch
        RefFractionnement refFractionnementEs = refFractionnementSearchRepository.findOne(testRefFractionnement.getId());
        assertThat(refFractionnementEs).isEqualToComparingFieldByField(testRefFractionnement);
    }

    @Test
    @Transactional
    public void updateNonExistingRefFractionnement() throws Exception {
        int databaseSizeBeforeUpdate = refFractionnementRepository.findAll().size();

        // Create the RefFractionnement
        RefFractionnementDTO refFractionnementDTO = refFractionnementMapper.toDto(refFractionnement);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRefFractionnementMockMvc.perform(put("/api/ref-fractionnements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refFractionnementDTO)))
            .andExpect(status().isCreated());

        // Validate the RefFractionnement in the database
        List<RefFractionnement> refFractionnementList = refFractionnementRepository.findAll();
        assertThat(refFractionnementList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRefFractionnement() throws Exception {
        // Initialize the database
        refFractionnementRepository.saveAndFlush(refFractionnement);
        refFractionnementSearchRepository.save(refFractionnement);
        int databaseSizeBeforeDelete = refFractionnementRepository.findAll().size();

        // Get the refFractionnement
        restRefFractionnementMockMvc.perform(delete("/api/ref-fractionnements/{id}", refFractionnement.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean refFractionnementExistsInEs = refFractionnementSearchRepository.exists(refFractionnement.getId());
        assertThat(refFractionnementExistsInEs).isFalse();

        // Validate the database is empty
        List<RefFractionnement> refFractionnementList = refFractionnementRepository.findAll();
        assertThat(refFractionnementList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchRefFractionnement() throws Exception {
        // Initialize the database
        refFractionnementRepository.saveAndFlush(refFractionnement);
        refFractionnementSearchRepository.save(refFractionnement);

        // Search the refFractionnement
        restRefFractionnementMockMvc.perform(get("/api/_search/ref-fractionnements?query=id:" + refFractionnement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(refFractionnement.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RefFractionnement.class);
        RefFractionnement refFractionnement1 = new RefFractionnement();
        refFractionnement1.setId(1L);
        RefFractionnement refFractionnement2 = new RefFractionnement();
        refFractionnement2.setId(refFractionnement1.getId());
        assertThat(refFractionnement1).isEqualTo(refFractionnement2);
        refFractionnement2.setId(2L);
        assertThat(refFractionnement1).isNotEqualTo(refFractionnement2);
        refFractionnement1.setId(null);
        assertThat(refFractionnement1).isNotEqualTo(refFractionnement2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RefFractionnementDTO.class);
        RefFractionnementDTO refFractionnementDTO1 = new RefFractionnementDTO();
        refFractionnementDTO1.setId(1L);
        RefFractionnementDTO refFractionnementDTO2 = new RefFractionnementDTO();
        assertThat(refFractionnementDTO1).isNotEqualTo(refFractionnementDTO2);
        refFractionnementDTO2.setId(refFractionnementDTO1.getId());
        assertThat(refFractionnementDTO1).isEqualTo(refFractionnementDTO2);
        refFractionnementDTO2.setId(2L);
        assertThat(refFractionnementDTO1).isNotEqualTo(refFractionnementDTO2);
        refFractionnementDTO1.setId(null);
        assertThat(refFractionnementDTO1).isNotEqualTo(refFractionnementDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(refFractionnementMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(refFractionnementMapper.fromId(null)).isNull();
    }
}
