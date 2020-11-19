package com.gaconnecte.auxilium.web.rest;

import com.gaconnecte.auxilium.AuxiliumApp;

import com.gaconnecte.auxilium.domain.DesignationFractionnement;
import com.gaconnecte.auxilium.repository.DesignationFractionnementRepository;
import com.gaconnecte.auxilium.service.DesignationFractionnementService;
import com.gaconnecte.auxilium.repository.search.DesignationFractionnementSearchRepository;
import com.gaconnecte.auxilium.service.dto.DesignationFractionnementDTO;
import com.gaconnecte.auxilium.service.mapper.DesignationFractionnementMapper;
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
 * Test class for the DesignationFractionnementResource REST controller.
 *
 * @see DesignationFractionnementResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuxiliumApp.class)
public class DesignationFractionnementResourceIntTest {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    @Autowired
    private DesignationFractionnementRepository designationFractionnementRepository;

    @Autowired
    private DesignationFractionnementMapper designationFractionnementMapper;

    @Autowired
    private DesignationFractionnementService designationFractionnementService;

    @Autowired
    private DesignationFractionnementSearchRepository designationFractionnementSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDesignationFractionnementMockMvc;

    private DesignationFractionnement designationFractionnement;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DesignationFractionnementResource designationFractionnementResource = new DesignationFractionnementResource(designationFractionnementService);
        this.restDesignationFractionnementMockMvc = MockMvcBuilders.standaloneSetup(designationFractionnementResource)
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
    public static DesignationFractionnement createEntity(EntityManager em) {
        DesignationFractionnement designationFractionnement = new DesignationFractionnement()
            .libelle(DEFAULT_LIBELLE);
        return designationFractionnement;
    }

    @Before
    public void initTest() {
        designationFractionnementSearchRepository.deleteAll();
        designationFractionnement = createEntity(em);
    }

    @Test
    @Transactional
    public void createDesignationFractionnement() throws Exception {
        int databaseSizeBeforeCreate = designationFractionnementRepository.findAll().size();

        // Create the DesignationFractionnement
        DesignationFractionnementDTO designationFractionnementDTO = designationFractionnementMapper.toDto(designationFractionnement);
        restDesignationFractionnementMockMvc.perform(post("/api/designation-fractionnements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(designationFractionnementDTO)))
            .andExpect(status().isCreated());

        // Validate the DesignationFractionnement in the database
        List<DesignationFractionnement> designationFractionnementList = designationFractionnementRepository.findAll();
        assertThat(designationFractionnementList).hasSize(databaseSizeBeforeCreate + 1);
        DesignationFractionnement testDesignationFractionnement = designationFractionnementList.get(designationFractionnementList.size() - 1);
        assertThat(testDesignationFractionnement.getLibelle()).isEqualTo(DEFAULT_LIBELLE);

        // Validate the DesignationFractionnement in Elasticsearch
        DesignationFractionnement designationFractionnementEs = designationFractionnementSearchRepository.findOne(testDesignationFractionnement.getId());
        assertThat(designationFractionnementEs).isEqualToComparingFieldByField(testDesignationFractionnement);
    }

    @Test
    @Transactional
    public void createDesignationFractionnementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = designationFractionnementRepository.findAll().size();

        // Create the DesignationFractionnement with an existing ID
        designationFractionnement.setId(1L);
        DesignationFractionnementDTO designationFractionnementDTO = designationFractionnementMapper.toDto(designationFractionnement);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDesignationFractionnementMockMvc.perform(post("/api/designation-fractionnements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(designationFractionnementDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<DesignationFractionnement> designationFractionnementList = designationFractionnementRepository.findAll();
        assertThat(designationFractionnementList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = designationFractionnementRepository.findAll().size();
        // set the field null
        designationFractionnement.setLibelle(null);

        // Create the DesignationFractionnement, which fails.
        DesignationFractionnementDTO designationFractionnementDTO = designationFractionnementMapper.toDto(designationFractionnement);

        restDesignationFractionnementMockMvc.perform(post("/api/designation-fractionnements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(designationFractionnementDTO)))
            .andExpect(status().isBadRequest());

        List<DesignationFractionnement> designationFractionnementList = designationFractionnementRepository.findAll();
        assertThat(designationFractionnementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDesignationFractionnements() throws Exception {
        // Initialize the database
        designationFractionnementRepository.saveAndFlush(designationFractionnement);

        // Get all the designationFractionnementList
        restDesignationFractionnementMockMvc.perform(get("/api/designation-fractionnements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(designationFractionnement.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())));
    }

    @Test
    @Transactional
    public void getDesignationFractionnement() throws Exception {
        // Initialize the database
        designationFractionnementRepository.saveAndFlush(designationFractionnement);

        // Get the designationFractionnement
        restDesignationFractionnementMockMvc.perform(get("/api/designation-fractionnements/{id}", designationFractionnement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(designationFractionnement.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDesignationFractionnement() throws Exception {
        // Get the designationFractionnement
        restDesignationFractionnementMockMvc.perform(get("/api/designation-fractionnements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDesignationFractionnement() throws Exception {
        // Initialize the database
        designationFractionnementRepository.saveAndFlush(designationFractionnement);
        designationFractionnementSearchRepository.save(designationFractionnement);
        int databaseSizeBeforeUpdate = designationFractionnementRepository.findAll().size();

        // Update the designationFractionnement
        DesignationFractionnement updatedDesignationFractionnement = designationFractionnementRepository.findOne(designationFractionnement.getId());
        updatedDesignationFractionnement
            .libelle(UPDATED_LIBELLE);
        DesignationFractionnementDTO designationFractionnementDTO = designationFractionnementMapper.toDto(updatedDesignationFractionnement);

        restDesignationFractionnementMockMvc.perform(put("/api/designation-fractionnements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(designationFractionnementDTO)))
            .andExpect(status().isOk());

        // Validate the DesignationFractionnement in the database
        List<DesignationFractionnement> designationFractionnementList = designationFractionnementRepository.findAll();
        assertThat(designationFractionnementList).hasSize(databaseSizeBeforeUpdate);
        DesignationFractionnement testDesignationFractionnement = designationFractionnementList.get(designationFractionnementList.size() - 1);
        assertThat(testDesignationFractionnement.getLibelle()).isEqualTo(UPDATED_LIBELLE);

        // Validate the DesignationFractionnement in Elasticsearch
        DesignationFractionnement designationFractionnementEs = designationFractionnementSearchRepository.findOne(testDesignationFractionnement.getId());
        assertThat(designationFractionnementEs).isEqualToComparingFieldByField(testDesignationFractionnement);
    }

    @Test
    @Transactional
    public void updateNonExistingDesignationFractionnement() throws Exception {
        int databaseSizeBeforeUpdate = designationFractionnementRepository.findAll().size();

        // Create the DesignationFractionnement
        DesignationFractionnementDTO designationFractionnementDTO = designationFractionnementMapper.toDto(designationFractionnement);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDesignationFractionnementMockMvc.perform(put("/api/designation-fractionnements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(designationFractionnementDTO)))
            .andExpect(status().isCreated());

        // Validate the DesignationFractionnement in the database
        List<DesignationFractionnement> designationFractionnementList = designationFractionnementRepository.findAll();
        assertThat(designationFractionnementList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDesignationFractionnement() throws Exception {
        // Initialize the database
        designationFractionnementRepository.saveAndFlush(designationFractionnement);
        designationFractionnementSearchRepository.save(designationFractionnement);
        int databaseSizeBeforeDelete = designationFractionnementRepository.findAll().size();

        // Get the designationFractionnement
        restDesignationFractionnementMockMvc.perform(delete("/api/designation-fractionnements/{id}", designationFractionnement.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean designationFractionnementExistsInEs = designationFractionnementSearchRepository.exists(designationFractionnement.getId());
        assertThat(designationFractionnementExistsInEs).isFalse();

        // Validate the database is empty
        List<DesignationFractionnement> designationFractionnementList = designationFractionnementRepository.findAll();
        assertThat(designationFractionnementList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchDesignationFractionnement() throws Exception {
        // Initialize the database
        designationFractionnementRepository.saveAndFlush(designationFractionnement);
        designationFractionnementSearchRepository.save(designationFractionnement);

        // Search the designationFractionnement
        restDesignationFractionnementMockMvc.perform(get("/api/_search/designation-fractionnements?query=id:" + designationFractionnement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(designationFractionnement.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DesignationFractionnement.class);
        DesignationFractionnement designationFractionnement1 = new DesignationFractionnement();
        designationFractionnement1.setId(1L);
        DesignationFractionnement designationFractionnement2 = new DesignationFractionnement();
        designationFractionnement2.setId(designationFractionnement1.getId());
        assertThat(designationFractionnement1).isEqualTo(designationFractionnement2);
        designationFractionnement2.setId(2L);
        assertThat(designationFractionnement1).isNotEqualTo(designationFractionnement2);
        designationFractionnement1.setId(null);
        assertThat(designationFractionnement1).isNotEqualTo(designationFractionnement2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DesignationFractionnementDTO.class);
        DesignationFractionnementDTO designationFractionnementDTO1 = new DesignationFractionnementDTO();
        designationFractionnementDTO1.setId(1L);
        DesignationFractionnementDTO designationFractionnementDTO2 = new DesignationFractionnementDTO();
        assertThat(designationFractionnementDTO1).isNotEqualTo(designationFractionnementDTO2);
        designationFractionnementDTO2.setId(designationFractionnementDTO1.getId());
        assertThat(designationFractionnementDTO1).isEqualTo(designationFractionnementDTO2);
        designationFractionnementDTO2.setId(2L);
        assertThat(designationFractionnementDTO1).isNotEqualTo(designationFractionnementDTO2);
        designationFractionnementDTO1.setId(null);
        assertThat(designationFractionnementDTO1).isNotEqualTo(designationFractionnementDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(designationFractionnementMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(designationFractionnementMapper.fromId(null)).isNull();
    }
}
