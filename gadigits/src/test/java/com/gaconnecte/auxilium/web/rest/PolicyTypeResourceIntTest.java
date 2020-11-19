package com.gaconnecte.auxilium.web.rest;

import com.gaconnecte.auxilium.AuxiliumApp;

import com.gaconnecte.auxilium.domain.PolicyType;
import com.gaconnecte.auxilium.repository.PolicyTypeRepository;
import com.gaconnecte.auxilium.service.PolicyTypeService;
import com.gaconnecte.auxilium.repository.search.PolicyTypeSearchRepository;
import com.gaconnecte.auxilium.service.dto.PolicyTypeDTO;
import com.gaconnecte.auxilium.service.mapper.PolicyTypeMapper;
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
 * Test class for the PolicyTypeResource REST controller.
 *
 * @see PolicyTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuxiliumApp.class)
public class PolicyTypeResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private PolicyTypeRepository policyTypeRepository;

    @Autowired
    private PolicyTypeMapper policyTypeMapper;

    @Autowired
    private PolicyTypeService policyTypeService;

    @Autowired
    private PolicyTypeSearchRepository policyTypeSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPolicyTypeMockMvc;

    private PolicyType policyType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PolicyTypeResource policyTypeResource = new PolicyTypeResource(policyTypeService);
        this.restPolicyTypeMockMvc = MockMvcBuilders.standaloneSetup(policyTypeResource)
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
    public static PolicyType createEntity(EntityManager em) {
        PolicyType policyType = new PolicyType()
            .code(DEFAULT_CODE)
            .label(DEFAULT_LABEL)
            .active(DEFAULT_ACTIVE);
        return policyType;
    }

    @Before
    public void initTest() {
        policyTypeSearchRepository.deleteAll();
        policyType = createEntity(em);
    }

    @Test
    @Transactional
    public void createPolicyType() throws Exception {
        int databaseSizeBeforeCreate = policyTypeRepository.findAll().size();

        // Create the PolicyType
        PolicyTypeDTO policyTypeDTO = policyTypeMapper.toDto(policyType);
        restPolicyTypeMockMvc.perform(post("/api/policy-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(policyTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the PolicyType in the database
        List<PolicyType> policyTypeList = policyTypeRepository.findAll();
        assertThat(policyTypeList).hasSize(databaseSizeBeforeCreate + 1);
        PolicyType testPolicyType = policyTypeList.get(policyTypeList.size() - 1);
        assertThat(testPolicyType.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testPolicyType.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testPolicyType.isActive()).isEqualTo(DEFAULT_ACTIVE);

        // Validate the PolicyType in Elasticsearch
        PolicyType policyTypeEs = policyTypeSearchRepository.findOne(testPolicyType.getId());
        assertThat(policyTypeEs).isEqualToComparingFieldByField(testPolicyType);
    }

    @Test
    @Transactional
    public void createPolicyTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = policyTypeRepository.findAll().size();

        // Create the PolicyType with an existing ID
        policyType.setId(1L);
        PolicyTypeDTO policyTypeDTO = policyTypeMapper.toDto(policyType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPolicyTypeMockMvc.perform(post("/api/policy-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(policyTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PolicyType> policyTypeList = policyTypeRepository.findAll();
        assertThat(policyTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPolicyTypes() throws Exception {
        // Initialize the database
        policyTypeRepository.saveAndFlush(policyType);

        // Get all the policyTypeList
        restPolicyTypeMockMvc.perform(get("/api/policy-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(policyType.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void getPolicyType() throws Exception {
        // Initialize the database
        policyTypeRepository.saveAndFlush(policyType);

        // Get the policyType
        restPolicyTypeMockMvc.perform(get("/api/policy-types/{id}", policyType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(policyType.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPolicyType() throws Exception {
        // Get the policyType
        restPolicyTypeMockMvc.perform(get("/api/policy-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePolicyType() throws Exception {
        // Initialize the database
        policyTypeRepository.saveAndFlush(policyType);
        policyTypeSearchRepository.save(policyType);
        int databaseSizeBeforeUpdate = policyTypeRepository.findAll().size();

        // Update the policyType
        PolicyType updatedPolicyType = policyTypeRepository.findOne(policyType.getId());
        updatedPolicyType
            .code(UPDATED_CODE)
            .label(UPDATED_LABEL)
            .active(UPDATED_ACTIVE);
        PolicyTypeDTO policyTypeDTO = policyTypeMapper.toDto(updatedPolicyType);

        restPolicyTypeMockMvc.perform(put("/api/policy-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(policyTypeDTO)))
            .andExpect(status().isOk());

        // Validate the PolicyType in the database
        List<PolicyType> policyTypeList = policyTypeRepository.findAll();
        assertThat(policyTypeList).hasSize(databaseSizeBeforeUpdate);
        PolicyType testPolicyType = policyTypeList.get(policyTypeList.size() - 1);
        assertThat(testPolicyType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testPolicyType.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testPolicyType.isActive()).isEqualTo(UPDATED_ACTIVE);

        // Validate the PolicyType in Elasticsearch
        PolicyType policyTypeEs = policyTypeSearchRepository.findOne(testPolicyType.getId());
        assertThat(policyTypeEs).isEqualToComparingFieldByField(testPolicyType);
    }

    @Test
    @Transactional
    public void updateNonExistingPolicyType() throws Exception {
        int databaseSizeBeforeUpdate = policyTypeRepository.findAll().size();

        // Create the PolicyType
        PolicyTypeDTO policyTypeDTO = policyTypeMapper.toDto(policyType);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPolicyTypeMockMvc.perform(put("/api/policy-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(policyTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the PolicyType in the database
        List<PolicyType> policyTypeList = policyTypeRepository.findAll();
        assertThat(policyTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePolicyType() throws Exception {
        // Initialize the database
        policyTypeRepository.saveAndFlush(policyType);
        policyTypeSearchRepository.save(policyType);
        int databaseSizeBeforeDelete = policyTypeRepository.findAll().size();

        // Get the policyType
        restPolicyTypeMockMvc.perform(delete("/api/policy-types/{id}", policyType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean policyTypeExistsInEs = policyTypeSearchRepository.exists(policyType.getId());
        assertThat(policyTypeExistsInEs).isFalse();

        // Validate the database is empty
        List<PolicyType> policyTypeList = policyTypeRepository.findAll();
        assertThat(policyTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchPolicyType() throws Exception {
        // Initialize the database
        policyTypeRepository.saveAndFlush(policyType);
        policyTypeSearchRepository.save(policyType);

        // Search the policyType
        restPolicyTypeMockMvc.perform(get("/api/_search/policy-types?query=id:" + policyType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(policyType.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PolicyType.class);
        PolicyType policyType1 = new PolicyType();
        policyType1.setId(1L);
        PolicyType policyType2 = new PolicyType();
        policyType2.setId(policyType1.getId());
        assertThat(policyType1).isEqualTo(policyType2);
        policyType2.setId(2L);
        assertThat(policyType1).isNotEqualTo(policyType2);
        policyType1.setId(null);
        assertThat(policyType1).isNotEqualTo(policyType2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PolicyTypeDTO.class);
        PolicyTypeDTO policyTypeDTO1 = new PolicyTypeDTO();
        policyTypeDTO1.setId(1L);
        PolicyTypeDTO policyTypeDTO2 = new PolicyTypeDTO();
        assertThat(policyTypeDTO1).isNotEqualTo(policyTypeDTO2);
        policyTypeDTO2.setId(policyTypeDTO1.getId());
        assertThat(policyTypeDTO1).isEqualTo(policyTypeDTO2);
        policyTypeDTO2.setId(2L);
        assertThat(policyTypeDTO1).isNotEqualTo(policyTypeDTO2);
        policyTypeDTO1.setId(null);
        assertThat(policyTypeDTO1).isNotEqualTo(policyTypeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(policyTypeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(policyTypeMapper.fromId(null)).isNull();
    }
}
