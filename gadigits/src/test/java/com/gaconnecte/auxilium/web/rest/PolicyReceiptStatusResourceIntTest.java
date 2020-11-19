package com.gaconnecte.auxilium.web.rest;

import com.gaconnecte.auxilium.AuxiliumApp;

import com.gaconnecte.auxilium.domain.PolicyReceiptStatus;
import com.gaconnecte.auxilium.repository.PolicyReceiptStatusRepository;
import com.gaconnecte.auxilium.service.PolicyReceiptStatusService;
import com.gaconnecte.auxilium.repository.search.PolicyReceiptStatusSearchRepository;
import com.gaconnecte.auxilium.service.dto.PolicyReceiptStatusDTO;
import com.gaconnecte.auxilium.service.mapper.PolicyReceiptStatusMapper;
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
 * Test class for the PolicyReceiptStatusResource REST controller.
 *
 * @see PolicyReceiptStatusResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuxiliumApp.class)
public class PolicyReceiptStatusResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private PolicyReceiptStatusRepository policyReceiptStatusRepository;

    @Autowired
    private PolicyReceiptStatusMapper policyReceiptStatusMapper;

    @Autowired
    private PolicyReceiptStatusService policyReceiptStatusService;

    @Autowired
    private PolicyReceiptStatusSearchRepository policyReceiptStatusSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPolicyReceiptStatusMockMvc;

    private PolicyReceiptStatus policyReceiptStatus;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PolicyReceiptStatusResource policyReceiptStatusResource = new PolicyReceiptStatusResource(policyReceiptStatusService);
        this.restPolicyReceiptStatusMockMvc = MockMvcBuilders.standaloneSetup(policyReceiptStatusResource)
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
    public static PolicyReceiptStatus createEntity(EntityManager em) {
        PolicyReceiptStatus policyReceiptStatus = new PolicyReceiptStatus()
            .code(DEFAULT_CODE)
            .label(DEFAULT_LABEL)
            .active(DEFAULT_ACTIVE);
        return policyReceiptStatus;
    }

    @Before
    public void initTest() {
        policyReceiptStatusSearchRepository.deleteAll();
        policyReceiptStatus = createEntity(em);
    }

    @Test
    @Transactional
    public void createPolicyReceiptStatus() throws Exception {
        int databaseSizeBeforeCreate = policyReceiptStatusRepository.findAll().size();

        // Create the PolicyReceiptStatus
        PolicyReceiptStatusDTO policyReceiptStatusDTO = policyReceiptStatusMapper.toDto(policyReceiptStatus);
        restPolicyReceiptStatusMockMvc.perform(post("/api/policy-receipt-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(policyReceiptStatusDTO)))
            .andExpect(status().isCreated());

        // Validate the PolicyReceiptStatus in the database
        List<PolicyReceiptStatus> policyReceiptStatusList = policyReceiptStatusRepository.findAll();
        assertThat(policyReceiptStatusList).hasSize(databaseSizeBeforeCreate + 1);
        PolicyReceiptStatus testPolicyReceiptStatus = policyReceiptStatusList.get(policyReceiptStatusList.size() - 1);
        assertThat(testPolicyReceiptStatus.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testPolicyReceiptStatus.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testPolicyReceiptStatus.isActive()).isEqualTo(DEFAULT_ACTIVE);

        // Validate the PolicyReceiptStatus in Elasticsearch
        PolicyReceiptStatus policyReceiptStatusEs = policyReceiptStatusSearchRepository.findOne(testPolicyReceiptStatus.getId());
        assertThat(policyReceiptStatusEs).isEqualToComparingFieldByField(testPolicyReceiptStatus);
    }

    @Test
    @Transactional
    public void createPolicyReceiptStatusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = policyReceiptStatusRepository.findAll().size();

        // Create the PolicyReceiptStatus with an existing ID
        policyReceiptStatus.setId(1L);
        PolicyReceiptStatusDTO policyReceiptStatusDTO = policyReceiptStatusMapper.toDto(policyReceiptStatus);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPolicyReceiptStatusMockMvc.perform(post("/api/policy-receipt-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(policyReceiptStatusDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PolicyReceiptStatus> policyReceiptStatusList = policyReceiptStatusRepository.findAll();
        assertThat(policyReceiptStatusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPolicyReceiptStatuses() throws Exception {
        // Initialize the database
        policyReceiptStatusRepository.saveAndFlush(policyReceiptStatus);

        // Get all the policyReceiptStatusList
        restPolicyReceiptStatusMockMvc.perform(get("/api/policy-receipt-statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(policyReceiptStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void getPolicyReceiptStatus() throws Exception {
        // Initialize the database
        policyReceiptStatusRepository.saveAndFlush(policyReceiptStatus);

        // Get the policyReceiptStatus
        restPolicyReceiptStatusMockMvc.perform(get("/api/policy-receipt-statuses/{id}", policyReceiptStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(policyReceiptStatus.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPolicyReceiptStatus() throws Exception {
        // Get the policyReceiptStatus
        restPolicyReceiptStatusMockMvc.perform(get("/api/policy-receipt-statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePolicyReceiptStatus() throws Exception {
        // Initialize the database
        policyReceiptStatusRepository.saveAndFlush(policyReceiptStatus);
        policyReceiptStatusSearchRepository.save(policyReceiptStatus);
        int databaseSizeBeforeUpdate = policyReceiptStatusRepository.findAll().size();

        // Update the policyReceiptStatus
        PolicyReceiptStatus updatedPolicyReceiptStatus = policyReceiptStatusRepository.findOne(policyReceiptStatus.getId());
        updatedPolicyReceiptStatus
            .code(UPDATED_CODE)
            .label(UPDATED_LABEL)
            .active(UPDATED_ACTIVE);
        PolicyReceiptStatusDTO policyReceiptStatusDTO = policyReceiptStatusMapper.toDto(updatedPolicyReceiptStatus);

        restPolicyReceiptStatusMockMvc.perform(put("/api/policy-receipt-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(policyReceiptStatusDTO)))
            .andExpect(status().isOk());

        // Validate the PolicyReceiptStatus in the database
        List<PolicyReceiptStatus> policyReceiptStatusList = policyReceiptStatusRepository.findAll();
        assertThat(policyReceiptStatusList).hasSize(databaseSizeBeforeUpdate);
        PolicyReceiptStatus testPolicyReceiptStatus = policyReceiptStatusList.get(policyReceiptStatusList.size() - 1);
        assertThat(testPolicyReceiptStatus.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testPolicyReceiptStatus.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testPolicyReceiptStatus.isActive()).isEqualTo(UPDATED_ACTIVE);

        // Validate the PolicyReceiptStatus in Elasticsearch
        PolicyReceiptStatus policyReceiptStatusEs = policyReceiptStatusSearchRepository.findOne(testPolicyReceiptStatus.getId());
        assertThat(policyReceiptStatusEs).isEqualToComparingFieldByField(testPolicyReceiptStatus);
    }

    @Test
    @Transactional
    public void updateNonExistingPolicyReceiptStatus() throws Exception {
        int databaseSizeBeforeUpdate = policyReceiptStatusRepository.findAll().size();

        // Create the PolicyReceiptStatus
        PolicyReceiptStatusDTO policyReceiptStatusDTO = policyReceiptStatusMapper.toDto(policyReceiptStatus);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPolicyReceiptStatusMockMvc.perform(put("/api/policy-receipt-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(policyReceiptStatusDTO)))
            .andExpect(status().isCreated());

        // Validate the PolicyReceiptStatus in the database
        List<PolicyReceiptStatus> policyReceiptStatusList = policyReceiptStatusRepository.findAll();
        assertThat(policyReceiptStatusList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePolicyReceiptStatus() throws Exception {
        // Initialize the database
        policyReceiptStatusRepository.saveAndFlush(policyReceiptStatus);
        policyReceiptStatusSearchRepository.save(policyReceiptStatus);
        int databaseSizeBeforeDelete = policyReceiptStatusRepository.findAll().size();

        // Get the policyReceiptStatus
        restPolicyReceiptStatusMockMvc.perform(delete("/api/policy-receipt-statuses/{id}", policyReceiptStatus.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean policyReceiptStatusExistsInEs = policyReceiptStatusSearchRepository.exists(policyReceiptStatus.getId());
        assertThat(policyReceiptStatusExistsInEs).isFalse();

        // Validate the database is empty
        List<PolicyReceiptStatus> policyReceiptStatusList = policyReceiptStatusRepository.findAll();
        assertThat(policyReceiptStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchPolicyReceiptStatus() throws Exception {
        // Initialize the database
        policyReceiptStatusRepository.saveAndFlush(policyReceiptStatus);
        policyReceiptStatusSearchRepository.save(policyReceiptStatus);

        // Search the policyReceiptStatus
        restPolicyReceiptStatusMockMvc.perform(get("/api/_search/policy-receipt-statuses?query=id:" + policyReceiptStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(policyReceiptStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PolicyReceiptStatus.class);
        PolicyReceiptStatus policyReceiptStatus1 = new PolicyReceiptStatus();
        policyReceiptStatus1.setId(1L);
        PolicyReceiptStatus policyReceiptStatus2 = new PolicyReceiptStatus();
        policyReceiptStatus2.setId(policyReceiptStatus1.getId());
        assertThat(policyReceiptStatus1).isEqualTo(policyReceiptStatus2);
        policyReceiptStatus2.setId(2L);
        assertThat(policyReceiptStatus1).isNotEqualTo(policyReceiptStatus2);
        policyReceiptStatus1.setId(null);
        assertThat(policyReceiptStatus1).isNotEqualTo(policyReceiptStatus2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PolicyReceiptStatusDTO.class);
        PolicyReceiptStatusDTO policyReceiptStatusDTO1 = new PolicyReceiptStatusDTO();
        policyReceiptStatusDTO1.setId(1L);
        PolicyReceiptStatusDTO policyReceiptStatusDTO2 = new PolicyReceiptStatusDTO();
        assertThat(policyReceiptStatusDTO1).isNotEqualTo(policyReceiptStatusDTO2);
        policyReceiptStatusDTO2.setId(policyReceiptStatusDTO1.getId());
        assertThat(policyReceiptStatusDTO1).isEqualTo(policyReceiptStatusDTO2);
        policyReceiptStatusDTO2.setId(2L);
        assertThat(policyReceiptStatusDTO1).isNotEqualTo(policyReceiptStatusDTO2);
        policyReceiptStatusDTO1.setId(null);
        assertThat(policyReceiptStatusDTO1).isNotEqualTo(policyReceiptStatusDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(policyReceiptStatusMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(policyReceiptStatusMapper.fromId(null)).isNull();
    }
}
