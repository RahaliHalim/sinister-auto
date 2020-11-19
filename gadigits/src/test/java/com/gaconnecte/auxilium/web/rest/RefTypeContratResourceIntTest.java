package com.gaconnecte.auxilium.web.rest;

import com.gaconnecte.auxilium.AuxiliumApp;

import com.gaconnecte.auxilium.domain.RefTypeContrat;
import com.gaconnecte.auxilium.repository.RefTypeContratRepository;
import com.gaconnecte.auxilium.service.RefTypeContratService;
import com.gaconnecte.auxilium.repository.search.RefTypeContratSearchRepository;
import com.gaconnecte.auxilium.service.dto.RefTypeContratDTO;
import com.gaconnecte.auxilium.service.mapper.RefTypeContratMapper;
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
 * Test class for the RefTypeContratResource REST controller.
 *
 * @see RefTypeContratResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuxiliumApp.class)
public class RefTypeContratResourceIntTest {

    private static final Integer DEFAULT_CODE = 99999999;
    private static final Integer UPDATED_CODE = 99999998;

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ACTIF = false;
    private static final Boolean UPDATED_IS_ACTIF = true;

    @Autowired
    private RefTypeContratRepository refTypeContratRepository;

    @Autowired
    private RefTypeContratMapper refTypeContratMapper;

    @Autowired
    private RefTypeContratService refTypeContratService;

    @Autowired
    private RefTypeContratSearchRepository refTypeContratSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRefTypeContratMockMvc;

    private RefTypeContrat refTypeContrat;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RefTypeContratResource refTypeContratResource = new RefTypeContratResource(refTypeContratService);
        this.restRefTypeContratMockMvc = MockMvcBuilders.standaloneSetup(refTypeContratResource)
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
    public static RefTypeContrat createEntity(EntityManager em) {
        RefTypeContrat refTypeContrat = new RefTypeContrat()
            .code(DEFAULT_CODE)
            .libelle(DEFAULT_LIBELLE)
            .isActif(DEFAULT_IS_ACTIF);
        return refTypeContrat;
    }

    @Before
    public void initTest() {
        refTypeContratSearchRepository.deleteAll();
        refTypeContrat = createEntity(em);
    }

    @Test
    @Transactional
    public void createRefTypeContrat() throws Exception {
        int databaseSizeBeforeCreate = refTypeContratRepository.findAll().size();

        // Create the RefTypeContrat
        RefTypeContratDTO refTypeContratDTO = refTypeContratMapper.toDto(refTypeContrat);
        restRefTypeContratMockMvc.perform(post("/api/ref-type-contrats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refTypeContratDTO)))
            .andExpect(status().isCreated());

        // Validate the RefTypeContrat in the database
        List<RefTypeContrat> refTypeContratList = refTypeContratRepository.findAll();
        assertThat(refTypeContratList).hasSize(databaseSizeBeforeCreate + 1);
        RefTypeContrat testRefTypeContrat = refTypeContratList.get(refTypeContratList.size() - 1);
        assertThat(testRefTypeContrat.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testRefTypeContrat.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testRefTypeContrat.isIsActif()).isEqualTo(DEFAULT_IS_ACTIF);

        // Validate the RefTypeContrat in Elasticsearch
        RefTypeContrat refTypeContratEs = refTypeContratSearchRepository.findOne(testRefTypeContrat.getId());
        assertThat(refTypeContratEs).isEqualToComparingFieldByField(testRefTypeContrat);
    }

    @Test
    @Transactional
    public void createRefTypeContratWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = refTypeContratRepository.findAll().size();

        // Create the RefTypeContrat with an existing ID
        refTypeContrat.setId(1L);
        RefTypeContratDTO refTypeContratDTO = refTypeContratMapper.toDto(refTypeContrat);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRefTypeContratMockMvc.perform(post("/api/ref-type-contrats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refTypeContratDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<RefTypeContrat> refTypeContratList = refTypeContratRepository.findAll();
        assertThat(refTypeContratList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = refTypeContratRepository.findAll().size();
        // set the field null
        refTypeContrat.setCode(null);

        // Create the RefTypeContrat, which fails.
        RefTypeContratDTO refTypeContratDTO = refTypeContratMapper.toDto(refTypeContrat);

        restRefTypeContratMockMvc.perform(post("/api/ref-type-contrats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refTypeContratDTO)))
            .andExpect(status().isBadRequest());

        List<RefTypeContrat> refTypeContratList = refTypeContratRepository.findAll();
        assertThat(refTypeContratList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = refTypeContratRepository.findAll().size();
        // set the field null
        refTypeContrat.setLibelle(null);

        // Create the RefTypeContrat, which fails.
        RefTypeContratDTO refTypeContratDTO = refTypeContratMapper.toDto(refTypeContrat);

        restRefTypeContratMockMvc.perform(post("/api/ref-type-contrats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refTypeContratDTO)))
            .andExpect(status().isBadRequest());

        List<RefTypeContrat> refTypeContratList = refTypeContratRepository.findAll();
        assertThat(refTypeContratList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRefTypeContrats() throws Exception {
        // Initialize the database
        refTypeContratRepository.saveAndFlush(refTypeContrat);

        // Get all the refTypeContratList
        restRefTypeContratMockMvc.perform(get("/api/ref-type-contrats?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(refTypeContrat.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())))
            .andExpect(jsonPath("$.[*].isActif").value(hasItem(DEFAULT_IS_ACTIF.booleanValue())));
    }

    @Test
    @Transactional
    public void getRefTypeContrat() throws Exception {
        // Initialize the database
        refTypeContratRepository.saveAndFlush(refTypeContrat);

        // Get the refTypeContrat
        restRefTypeContratMockMvc.perform(get("/api/ref-type-contrats/{id}", refTypeContrat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(refTypeContrat.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()))
            .andExpect(jsonPath("$.isActif").value(DEFAULT_IS_ACTIF.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingRefTypeContrat() throws Exception {
        // Get the refTypeContrat
        restRefTypeContratMockMvc.perform(get("/api/ref-type-contrats/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRefTypeContrat() throws Exception {
        // Initialize the database
        refTypeContratRepository.saveAndFlush(refTypeContrat);
        refTypeContratSearchRepository.save(refTypeContrat);
        int databaseSizeBeforeUpdate = refTypeContratRepository.findAll().size();

        // Update the refTypeContrat
        RefTypeContrat updatedRefTypeContrat = refTypeContratRepository.findOne(refTypeContrat.getId());
        updatedRefTypeContrat
            .code(UPDATED_CODE)
            .libelle(UPDATED_LIBELLE)
            .isActif(UPDATED_IS_ACTIF);
        RefTypeContratDTO refTypeContratDTO = refTypeContratMapper.toDto(updatedRefTypeContrat);

        restRefTypeContratMockMvc.perform(put("/api/ref-type-contrats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refTypeContratDTO)))
            .andExpect(status().isOk());

        // Validate the RefTypeContrat in the database
        List<RefTypeContrat> refTypeContratList = refTypeContratRepository.findAll();
        assertThat(refTypeContratList).hasSize(databaseSizeBeforeUpdate);
        RefTypeContrat testRefTypeContrat = refTypeContratList.get(refTypeContratList.size() - 1);
        assertThat(testRefTypeContrat.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testRefTypeContrat.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testRefTypeContrat.isIsActif()).isEqualTo(UPDATED_IS_ACTIF);

        // Validate the RefTypeContrat in Elasticsearch
        RefTypeContrat refTypeContratEs = refTypeContratSearchRepository.findOne(testRefTypeContrat.getId());
        assertThat(refTypeContratEs).isEqualToComparingFieldByField(testRefTypeContrat);
    }

    @Test
    @Transactional
    public void updateNonExistingRefTypeContrat() throws Exception {
        int databaseSizeBeforeUpdate = refTypeContratRepository.findAll().size();

        // Create the RefTypeContrat
        RefTypeContratDTO refTypeContratDTO = refTypeContratMapper.toDto(refTypeContrat);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRefTypeContratMockMvc.perform(put("/api/ref-type-contrats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refTypeContratDTO)))
            .andExpect(status().isCreated());

        // Validate the RefTypeContrat in the database
        List<RefTypeContrat> refTypeContratList = refTypeContratRepository.findAll();
        assertThat(refTypeContratList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRefTypeContrat() throws Exception {
        // Initialize the database
        refTypeContratRepository.saveAndFlush(refTypeContrat);
        refTypeContratSearchRepository.save(refTypeContrat);
        int databaseSizeBeforeDelete = refTypeContratRepository.findAll().size();

        // Get the refTypeContrat
        restRefTypeContratMockMvc.perform(delete("/api/ref-type-contrats/{id}", refTypeContrat.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean refTypeContratExistsInEs = refTypeContratSearchRepository.exists(refTypeContrat.getId());
        assertThat(refTypeContratExistsInEs).isFalse();

        // Validate the database is empty
        List<RefTypeContrat> refTypeContratList = refTypeContratRepository.findAll();
        assertThat(refTypeContratList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchRefTypeContrat() throws Exception {
        // Initialize the database
        refTypeContratRepository.saveAndFlush(refTypeContrat);
        refTypeContratSearchRepository.save(refTypeContrat);

        // Search the refTypeContrat
        restRefTypeContratMockMvc.perform(get("/api/_search/ref-type-contrats?query=id:" + refTypeContrat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(refTypeContrat.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())))
            .andExpect(jsonPath("$.[*].isActif").value(hasItem(DEFAULT_IS_ACTIF.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RefTypeContrat.class);
        RefTypeContrat refTypeContrat1 = new RefTypeContrat();
        refTypeContrat1.setId(1L);
        RefTypeContrat refTypeContrat2 = new RefTypeContrat();
        refTypeContrat2.setId(refTypeContrat1.getId());
        assertThat(refTypeContrat1).isEqualTo(refTypeContrat2);
        refTypeContrat2.setId(2L);
        assertThat(refTypeContrat1).isNotEqualTo(refTypeContrat2);
        refTypeContrat1.setId(null);
        assertThat(refTypeContrat1).isNotEqualTo(refTypeContrat2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RefTypeContratDTO.class);
        RefTypeContratDTO refTypeContratDTO1 = new RefTypeContratDTO();
        refTypeContratDTO1.setId(1L);
        RefTypeContratDTO refTypeContratDTO2 = new RefTypeContratDTO();
        assertThat(refTypeContratDTO1).isNotEqualTo(refTypeContratDTO2);
        refTypeContratDTO2.setId(refTypeContratDTO1.getId());
        assertThat(refTypeContratDTO1).isEqualTo(refTypeContratDTO2);
        refTypeContratDTO2.setId(2L);
        assertThat(refTypeContratDTO1).isNotEqualTo(refTypeContratDTO2);
        refTypeContratDTO1.setId(null);
        assertThat(refTypeContratDTO1).isNotEqualTo(refTypeContratDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(refTypeContratMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(refTypeContratMapper.fromId(null)).isNull();
    }
}
