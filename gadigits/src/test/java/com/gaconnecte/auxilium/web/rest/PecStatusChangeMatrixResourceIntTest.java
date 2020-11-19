package com.gaconnecte.auxilium.web.rest;

import com.gaconnecte.auxilium.AuxiliumApp;

import com.gaconnecte.auxilium.domain.PecStatusChangeMatrix;
import com.gaconnecte.auxilium.repository.PecStatusChangeMatrixRepository;
import com.gaconnecte.auxilium.service.PecStatusChangeMatrixService;
import com.gaconnecte.auxilium.repository.search.PecStatusChangeMatrixSearchRepository;
import com.gaconnecte.auxilium.service.dto.PecStatusChangeMatrixDTO;
import com.gaconnecte.auxilium.service.mapper.PecStatusChangeMatrixMapper;
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
 * Test class for the PecStatusChangeMatrixResource REST controller.
 *
 * @see PecStatusChangeMatrixResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuxiliumApp.class)
public class PecStatusChangeMatrixResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private PecStatusChangeMatrixRepository pecStatusChangeMatrixRepository;

    @Autowired
    private PecStatusChangeMatrixMapper pecStatusChangeMatrixMapper;

    @Autowired
    private PecStatusChangeMatrixService pecStatusChangeMatrixService;

    @Autowired
    private PecStatusChangeMatrixSearchRepository pecStatusChangeMatrixSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPecStatusChangeMatrixMockMvc;

    private PecStatusChangeMatrix pecStatusChangeMatrix;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PecStatusChangeMatrixResource pecStatusChangeMatrixResource = new PecStatusChangeMatrixResource(pecStatusChangeMatrixService);
        this.restPecStatusChangeMatrixMockMvc = MockMvcBuilders.standaloneSetup(pecStatusChangeMatrixResource)
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
    public static PecStatusChangeMatrix createEntity(EntityManager em) {
        PecStatusChangeMatrix pecStatusChangeMatrix = new PecStatusChangeMatrix()
            .code(DEFAULT_CODE)
            .label(DEFAULT_LABEL)
            .active(DEFAULT_ACTIVE);
        return pecStatusChangeMatrix;
    }

    @Before
    public void initTest() {
        pecStatusChangeMatrixSearchRepository.deleteAll();
        pecStatusChangeMatrix = createEntity(em);
    }

    @Test
    @Transactional
    public void createPecStatusChangeMatrix() throws Exception {
        int databaseSizeBeforeCreate = pecStatusChangeMatrixRepository.findAll().size();

        // Create the PecStatusChangeMatrix
        PecStatusChangeMatrixDTO pecStatusChangeMatrixDTO = pecStatusChangeMatrixMapper.toDto(pecStatusChangeMatrix);
        restPecStatusChangeMatrixMockMvc.perform(post("/api/pec-status-change-matrices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pecStatusChangeMatrixDTO)))
            .andExpect(status().isCreated());

        // Validate the PecStatusChangeMatrix in the database
        List<PecStatusChangeMatrix> pecStatusChangeMatrixList = pecStatusChangeMatrixRepository.findAll();
        assertThat(pecStatusChangeMatrixList).hasSize(databaseSizeBeforeCreate + 1);
        PecStatusChangeMatrix testPecStatusChangeMatrix = pecStatusChangeMatrixList.get(pecStatusChangeMatrixList.size() - 1);
        assertThat(testPecStatusChangeMatrix.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testPecStatusChangeMatrix.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testPecStatusChangeMatrix.isActive()).isEqualTo(DEFAULT_ACTIVE);

        // Validate the PecStatusChangeMatrix in Elasticsearch
        PecStatusChangeMatrix pecStatusChangeMatrixEs = pecStatusChangeMatrixSearchRepository.findOne(testPecStatusChangeMatrix.getId());
        assertThat(pecStatusChangeMatrixEs).isEqualToComparingFieldByField(testPecStatusChangeMatrix);
    }

    @Test
    @Transactional
    public void createPecStatusChangeMatrixWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pecStatusChangeMatrixRepository.findAll().size();

        // Create the PecStatusChangeMatrix with an existing ID
        pecStatusChangeMatrix.setId(1L);
        PecStatusChangeMatrixDTO pecStatusChangeMatrixDTO = pecStatusChangeMatrixMapper.toDto(pecStatusChangeMatrix);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPecStatusChangeMatrixMockMvc.perform(post("/api/pec-status-change-matrices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pecStatusChangeMatrixDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PecStatusChangeMatrix> pecStatusChangeMatrixList = pecStatusChangeMatrixRepository.findAll();
        assertThat(pecStatusChangeMatrixList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPecStatusChangeMatrices() throws Exception {
        // Initialize the database
        pecStatusChangeMatrixRepository.saveAndFlush(pecStatusChangeMatrix);

        // Get all the pecStatusChangeMatrixList
        restPecStatusChangeMatrixMockMvc.perform(get("/api/pec-status-change-matrices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pecStatusChangeMatrix.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void getPecStatusChangeMatrix() throws Exception {
        // Initialize the database
        pecStatusChangeMatrixRepository.saveAndFlush(pecStatusChangeMatrix);

        // Get the pecStatusChangeMatrix
        restPecStatusChangeMatrixMockMvc.perform(get("/api/pec-status-change-matrices/{id}", pecStatusChangeMatrix.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pecStatusChangeMatrix.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPecStatusChangeMatrix() throws Exception {
        // Get the pecStatusChangeMatrix
        restPecStatusChangeMatrixMockMvc.perform(get("/api/pec-status-change-matrices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePecStatusChangeMatrix() throws Exception {
        // Initialize the database
        pecStatusChangeMatrixRepository.saveAndFlush(pecStatusChangeMatrix);
        pecStatusChangeMatrixSearchRepository.save(pecStatusChangeMatrix);
        int databaseSizeBeforeUpdate = pecStatusChangeMatrixRepository.findAll().size();

        // Update the pecStatusChangeMatrix
        PecStatusChangeMatrix updatedPecStatusChangeMatrix = pecStatusChangeMatrixRepository.findOne(pecStatusChangeMatrix.getId());
        updatedPecStatusChangeMatrix
            .code(UPDATED_CODE)
            .label(UPDATED_LABEL)
            .active(UPDATED_ACTIVE);
        PecStatusChangeMatrixDTO pecStatusChangeMatrixDTO = pecStatusChangeMatrixMapper.toDto(updatedPecStatusChangeMatrix);

        restPecStatusChangeMatrixMockMvc.perform(put("/api/pec-status-change-matrices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pecStatusChangeMatrixDTO)))
            .andExpect(status().isOk());

        // Validate the PecStatusChangeMatrix in the database
        List<PecStatusChangeMatrix> pecStatusChangeMatrixList = pecStatusChangeMatrixRepository.findAll();
        assertThat(pecStatusChangeMatrixList).hasSize(databaseSizeBeforeUpdate);
        PecStatusChangeMatrix testPecStatusChangeMatrix = pecStatusChangeMatrixList.get(pecStatusChangeMatrixList.size() - 1);
        assertThat(testPecStatusChangeMatrix.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testPecStatusChangeMatrix.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testPecStatusChangeMatrix.isActive()).isEqualTo(UPDATED_ACTIVE);

        // Validate the PecStatusChangeMatrix in Elasticsearch
        PecStatusChangeMatrix pecStatusChangeMatrixEs = pecStatusChangeMatrixSearchRepository.findOne(testPecStatusChangeMatrix.getId());
        assertThat(pecStatusChangeMatrixEs).isEqualToComparingFieldByField(testPecStatusChangeMatrix);
    }

    @Test
    @Transactional
    public void updateNonExistingPecStatusChangeMatrix() throws Exception {
        int databaseSizeBeforeUpdate = pecStatusChangeMatrixRepository.findAll().size();

        // Create the PecStatusChangeMatrix
        PecStatusChangeMatrixDTO pecStatusChangeMatrixDTO = pecStatusChangeMatrixMapper.toDto(pecStatusChangeMatrix);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPecStatusChangeMatrixMockMvc.perform(put("/api/pec-status-change-matrices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pecStatusChangeMatrixDTO)))
            .andExpect(status().isCreated());

        // Validate the PecStatusChangeMatrix in the database
        List<PecStatusChangeMatrix> pecStatusChangeMatrixList = pecStatusChangeMatrixRepository.findAll();
        assertThat(pecStatusChangeMatrixList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePecStatusChangeMatrix() throws Exception {
        // Initialize the database
        pecStatusChangeMatrixRepository.saveAndFlush(pecStatusChangeMatrix);
        pecStatusChangeMatrixSearchRepository.save(pecStatusChangeMatrix);
        int databaseSizeBeforeDelete = pecStatusChangeMatrixRepository.findAll().size();

        // Get the pecStatusChangeMatrix
        restPecStatusChangeMatrixMockMvc.perform(delete("/api/pec-status-change-matrices/{id}", pecStatusChangeMatrix.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean pecStatusChangeMatrixExistsInEs = pecStatusChangeMatrixSearchRepository.exists(pecStatusChangeMatrix.getId());
        assertThat(pecStatusChangeMatrixExistsInEs).isFalse();

        // Validate the database is empty
        List<PecStatusChangeMatrix> pecStatusChangeMatrixList = pecStatusChangeMatrixRepository.findAll();
        assertThat(pecStatusChangeMatrixList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchPecStatusChangeMatrix() throws Exception {
        // Initialize the database
        pecStatusChangeMatrixRepository.saveAndFlush(pecStatusChangeMatrix);
        pecStatusChangeMatrixSearchRepository.save(pecStatusChangeMatrix);

        // Search the pecStatusChangeMatrix
        restPecStatusChangeMatrixMockMvc.perform(get("/api/_search/pec-status-change-matrices?query=id:" + pecStatusChangeMatrix.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pecStatusChangeMatrix.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PecStatusChangeMatrix.class);
        PecStatusChangeMatrix pecStatusChangeMatrix1 = new PecStatusChangeMatrix();
        pecStatusChangeMatrix1.setId(1L);
        PecStatusChangeMatrix pecStatusChangeMatrix2 = new PecStatusChangeMatrix();
        pecStatusChangeMatrix2.setId(pecStatusChangeMatrix1.getId());
        assertThat(pecStatusChangeMatrix1).isEqualTo(pecStatusChangeMatrix2);
        pecStatusChangeMatrix2.setId(2L);
        assertThat(pecStatusChangeMatrix1).isNotEqualTo(pecStatusChangeMatrix2);
        pecStatusChangeMatrix1.setId(null);
        assertThat(pecStatusChangeMatrix1).isNotEqualTo(pecStatusChangeMatrix2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PecStatusChangeMatrixDTO.class);
        PecStatusChangeMatrixDTO pecStatusChangeMatrixDTO1 = new PecStatusChangeMatrixDTO();
        pecStatusChangeMatrixDTO1.setId(1L);
        PecStatusChangeMatrixDTO pecStatusChangeMatrixDTO2 = new PecStatusChangeMatrixDTO();
        assertThat(pecStatusChangeMatrixDTO1).isNotEqualTo(pecStatusChangeMatrixDTO2);
        pecStatusChangeMatrixDTO2.setId(pecStatusChangeMatrixDTO1.getId());
        assertThat(pecStatusChangeMatrixDTO1).isEqualTo(pecStatusChangeMatrixDTO2);
        pecStatusChangeMatrixDTO2.setId(2L);
        assertThat(pecStatusChangeMatrixDTO1).isNotEqualTo(pecStatusChangeMatrixDTO2);
        pecStatusChangeMatrixDTO1.setId(null);
        assertThat(pecStatusChangeMatrixDTO1).isNotEqualTo(pecStatusChangeMatrixDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(pecStatusChangeMatrixMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(pecStatusChangeMatrixMapper.fromId(null)).isNull();
    }
}
