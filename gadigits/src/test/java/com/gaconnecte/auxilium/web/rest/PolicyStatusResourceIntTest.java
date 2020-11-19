package com.gaconnecte.auxilium.web.rest;

import com.gaconnecte.auxilium.AuxiliumApp;

import com.gaconnecte.auxilium.domain.PolicyStatus;
import com.gaconnecte.auxilium.repository.PolicyStatusRepository;
import com.gaconnecte.auxilium.service.PolicyStatusService;
import com.gaconnecte.auxilium.repository.search.PolicyStatusSearchRepository;
import com.gaconnecte.auxilium.service.dto.PolicyStatusDTO;
import com.gaconnecte.auxilium.service.mapper.PolicyStatusMapper;
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
 * Test class for the PolicyStatusResource REST controller.
 *
 * @see PolicyStatusResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuxiliumApp.class)
public class PolicyStatusResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private PolicyStatusRepository policyStatusRepository;

    @Autowired
    private PolicyStatusMapper policyStatusMapper;

    @Autowired
    private PolicyStatusService policyStatusService;

    @Autowired
    private PolicyStatusSearchRepository policyStatusSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPolicyStatusMockMvc;

    private PolicyStatus policyStatus;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PolicyStatusResource policyStatusResource = new PolicyStatusResource(policyStatusService);
        this.restPolicyStatusMockMvc = MockMvcBuilders.standaloneSetup(policyStatusResource)
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
    public static PolicyStatus createEntity(EntityManager em) {
        PolicyStatus policyStatus = new PolicyStatus()
            .code(DEFAULT_CODE)
            .label(DEFAULT_LABEL)
            .active(DEFAULT_ACTIVE);
        return policyStatus;
    }

    @Before
    public void initTest() {
        policyStatusSearchRepository.deleteAll();
        policyStatus = createEntity(em);
    }

    @Test
    @Transactional
    public void createPolicyStatus() throws Exception {
        int databaseSizeBeforeCreate = policyStatusRepository.findAll().size();

        // Create the PolicyStatus
        PolicyStatusDTO policyStatusDTO = policyStatusMapper.toDto(policyStatus);
        restPolicyStatusMockMvc.perform(post("/api/policy-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(policyStatusDTO)))
            .andExpect(status().isCreated());

        // Validate the PolicyStatus in the database
        List<PolicyStatus> policyStatusList = policyStatusRepository.findAll();
        assertThat(policyStatusList).hasSize(databaseSizeBeforeCreate + 1);
        PolicyStatus testPolicyStatus = policyStatusList.get(policyStatusList.size() - 1);
        assertThat(testPolicyStatus.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testPolicyStatus.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testPolicyStatus.isActive()).isEqualTo(DEFAULT_ACTIVE);

        // Validate the PolicyStatus in Elasticsearch
        PolicyStatus policyStatusEs = policyStatusSearchRepository.findOne(testPolicyStatus.getId());
        assertThat(policyStatusEs).isEqualToComparingFieldByField(testPolicyStatus);
    }

    @Test
    @Transactional
    public void createPolicyStatusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = policyStatusRepository.findAll().size();

        // Create the PolicyStatus with an existing ID
        policyStatus.setId(1L);
        PolicyStatusDTO policyStatusDTO = policyStatusMapper.toDto(policyStatus);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPolicyStatusMockMvc.perform(post("/api/policy-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(policyStatusDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PolicyStatus> policyStatusList = policyStatusRepository.findAll();
        assertThat(policyStatusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPolicyStatuses() throws Exception {
        // Initialize the database
        policyStatusRepository.saveAndFlush(policyStatus);

        // Get all the policyStatusList
        restPolicyStatusMockMvc.perform(get("/api/policy-statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(policyStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void getPolicyStatus() throws Exception {
        // Initialize the database
        policyStatusRepository.saveAndFlush(policyStatus);

        // Get the policyStatus
        restPolicyStatusMockMvc.perform(get("/api/policy-statuses/{id}", policyStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(policyStatus.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPolicyStatus() throws Exception {
        // Get the policyStatus
        restPolicyStatusMockMvc.perform(get("/api/policy-statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePolicyStatus() throws Exception {
        // Initialize the database
        policyStatusRepository.saveAndFlush(policyStatus);
        policyStatusSearchRepository.save(policyStatus);
        int databaseSizeBeforeUpdate = policyStatusRepository.findAll().size();

        // Update the policyStatus
        PolicyStatus updatedPolicyStatus = policyStatusRepository.findOne(policyStatus.getId());
        updatedPolicyStatus
            .code(UPDATED_CODE)
            .label(UPDATED_LABEL)
            .active(UPDATED_ACTIVE);
        PolicyStatusDTO policyStatusDTO = policyStatusMapper.toDto(updatedPolicyStatus);

        restPolicyStatusMockMvc.perform(put("/api/policy-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(policyStatusDTO)))
            .andExpect(status().isOk());

        // Validate the PolicyStatus in the database
        List<PolicyStatus> policyStatusList = policyStatusRepository.findAll();
        assertThat(policyStatusList).hasSize(databaseSizeBeforeUpdate);
        PolicyStatus testPolicyStatus = policyStatusList.get(policyStatusList.size() - 1);
        assertThat(testPolicyStatus.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testPolicyStatus.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testPolicyStatus.isActive()).isEqualTo(UPDATED_ACTIVE);

        // Validate the PolicyStatus in Elasticsearch
        PolicyStatus policyStatusEs = policyStatusSearchRepository.findOne(testPolicyStatus.getId());
        assertThat(policyStatusEs).isEqualToComparingFieldByField(testPolicyStatus);
    }

    @Test
    @Transactional
    public void updateNonExistingPolicyStatus() throws Exception {
        int databaseSizeBeforeUpdate = policyStatusRepository.findAll().size();

        // Create the PolicyStatus
        PolicyStatusDTO policyStatusDTO = policyStatusMapper.toDto(policyStatus);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPolicyStatusMockMvc.perform(put("/api/policy-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(policyStatusDTO)))
            .andExpect(status().isCreated());

        // Validate the PolicyStatus in the database
        List<PolicyStatus> policyStatusList = policyStatusRepository.findAll();
        assertThat(policyStatusList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePolicyStatus() throws Exception {
        // Initialize the database
        policyStatusRepository.saveAndFlush(policyStatus);
        policyStatusSearchRepository.save(policyStatus);
        int databaseSizeBeforeDelete = policyStatusRepository.findAll().size();

        // Get the policyStatus
        restPolicyStatusMockMvc.perform(delete("/api/policy-statuses/{id}", policyStatus.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean policyStatusExistsInEs = policyStatusSearchRepository.exists(policyStatus.getId());
        assertThat(policyStatusExistsInEs).isFalse();

        // Validate the database is empty
        List<PolicyStatus> policyStatusList = policyStatusRepository.findAll();
        assertThat(policyStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchPolicyStatus() throws Exception {
        // Initialize the database
        policyStatusRepository.saveAndFlush(policyStatus);
        policyStatusSearchRepository.save(policyStatus);

        // Search the policyStatus
        restPolicyStatusMockMvc.perform(get("/api/_search/policy-statuses?query=id:" + policyStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(policyStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PolicyStatus.class);
        PolicyStatus policyStatus1 = new PolicyStatus();
        policyStatus1.setId(1L);
        PolicyStatus policyStatus2 = new PolicyStatus();
        policyStatus2.setId(policyStatus1.getId());
        assertThat(policyStatus1).isEqualTo(policyStatus2);
        policyStatus2.setId(2L);
        assertThat(policyStatus1).isNotEqualTo(policyStatus2);
        policyStatus1.setId(null);
        assertThat(policyStatus1).isNotEqualTo(policyStatus2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PolicyStatusDTO.class);
        PolicyStatusDTO policyStatusDTO1 = new PolicyStatusDTO();
        policyStatusDTO1.setId(1L);
        PolicyStatusDTO policyStatusDTO2 = new PolicyStatusDTO();
        assertThat(policyStatusDTO1).isNotEqualTo(policyStatusDTO2);
        policyStatusDTO2.setId(policyStatusDTO1.getId());
        assertThat(policyStatusDTO1).isEqualTo(policyStatusDTO2);
        policyStatusDTO2.setId(2L);
        assertThat(policyStatusDTO1).isNotEqualTo(policyStatusDTO2);
        policyStatusDTO1.setId(null);
        assertThat(policyStatusDTO1).isNotEqualTo(policyStatusDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(policyStatusMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(policyStatusMapper.fromId(null)).isNull();
    }
}
