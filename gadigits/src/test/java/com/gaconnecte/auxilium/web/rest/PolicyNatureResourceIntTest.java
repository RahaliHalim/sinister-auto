package com.gaconnecte.auxilium.web.rest;

import com.gaconnecte.auxilium.AuxiliumApp;

import com.gaconnecte.auxilium.domain.PolicyNature;
import com.gaconnecte.auxilium.repository.PolicyNatureRepository;
import com.gaconnecte.auxilium.service.PolicyNatureService;
import com.gaconnecte.auxilium.repository.search.PolicyNatureSearchRepository;
import com.gaconnecte.auxilium.service.dto.PolicyNatureDTO;
import com.gaconnecte.auxilium.service.mapper.PolicyNatureMapper;
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
 * Test class for the PolicyNatureResource REST controller.
 *
 * @see PolicyNatureResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuxiliumApp.class)
public class PolicyNatureResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private PolicyNatureRepository policyNatureRepository;

    @Autowired
    private PolicyNatureMapper policyNatureMapper;

    @Autowired
    private PolicyNatureService policyNatureService;

    @Autowired
    private PolicyNatureSearchRepository policyNatureSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPolicyNatureMockMvc;

    private PolicyNature policyNature;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PolicyNatureResource policyNatureResource = new PolicyNatureResource(policyNatureService);
        this.restPolicyNatureMockMvc = MockMvcBuilders.standaloneSetup(policyNatureResource)
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
    public static PolicyNature createEntity(EntityManager em) {
        PolicyNature policyNature = new PolicyNature()
            .code(DEFAULT_CODE)
            .label(DEFAULT_LABEL)
            .active(DEFAULT_ACTIVE);
        return policyNature;
    }

    @Before
    public void initTest() {
        policyNatureSearchRepository.deleteAll();
        policyNature = createEntity(em);
    }

    @Test
    @Transactional
    public void createPolicyNature() throws Exception {
        int databaseSizeBeforeCreate = policyNatureRepository.findAll().size();

        // Create the PolicyNature
        PolicyNatureDTO policyNatureDTO = policyNatureMapper.toDto(policyNature);
        restPolicyNatureMockMvc.perform(post("/api/policy-natures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(policyNatureDTO)))
            .andExpect(status().isCreated());

        // Validate the PolicyNature in the database
        List<PolicyNature> policyNatureList = policyNatureRepository.findAll();
        assertThat(policyNatureList).hasSize(databaseSizeBeforeCreate + 1);
        PolicyNature testPolicyNature = policyNatureList.get(policyNatureList.size() - 1);
        assertThat(testPolicyNature.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testPolicyNature.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testPolicyNature.isActive()).isEqualTo(DEFAULT_ACTIVE);

        // Validate the PolicyNature in Elasticsearch
        PolicyNature policyNatureEs = policyNatureSearchRepository.findOne(testPolicyNature.getId());
        assertThat(policyNatureEs).isEqualToComparingFieldByField(testPolicyNature);
    }

    @Test
    @Transactional
    public void createPolicyNatureWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = policyNatureRepository.findAll().size();

        // Create the PolicyNature with an existing ID
        policyNature.setId(1L);
        PolicyNatureDTO policyNatureDTO = policyNatureMapper.toDto(policyNature);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPolicyNatureMockMvc.perform(post("/api/policy-natures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(policyNatureDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PolicyNature> policyNatureList = policyNatureRepository.findAll();
        assertThat(policyNatureList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPolicyNatures() throws Exception {
        // Initialize the database
        policyNatureRepository.saveAndFlush(policyNature);

        // Get all the policyNatureList
        restPolicyNatureMockMvc.perform(get("/api/policy-natures?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(policyNature.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void getPolicyNature() throws Exception {
        // Initialize the database
        policyNatureRepository.saveAndFlush(policyNature);

        // Get the policyNature
        restPolicyNatureMockMvc.perform(get("/api/policy-natures/{id}", policyNature.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(policyNature.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPolicyNature() throws Exception {
        // Get the policyNature
        restPolicyNatureMockMvc.perform(get("/api/policy-natures/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePolicyNature() throws Exception {
        // Initialize the database
        policyNatureRepository.saveAndFlush(policyNature);
        policyNatureSearchRepository.save(policyNature);
        int databaseSizeBeforeUpdate = policyNatureRepository.findAll().size();

        // Update the policyNature
        PolicyNature updatedPolicyNature = policyNatureRepository.findOne(policyNature.getId());
        updatedPolicyNature
            .code(UPDATED_CODE)
            .label(UPDATED_LABEL)
            .active(UPDATED_ACTIVE);
        PolicyNatureDTO policyNatureDTO = policyNatureMapper.toDto(updatedPolicyNature);

        restPolicyNatureMockMvc.perform(put("/api/policy-natures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(policyNatureDTO)))
            .andExpect(status().isOk());

        // Validate the PolicyNature in the database
        List<PolicyNature> policyNatureList = policyNatureRepository.findAll();
        assertThat(policyNatureList).hasSize(databaseSizeBeforeUpdate);
        PolicyNature testPolicyNature = policyNatureList.get(policyNatureList.size() - 1);
        assertThat(testPolicyNature.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testPolicyNature.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testPolicyNature.isActive()).isEqualTo(UPDATED_ACTIVE);

        // Validate the PolicyNature in Elasticsearch
        PolicyNature policyNatureEs = policyNatureSearchRepository.findOne(testPolicyNature.getId());
        assertThat(policyNatureEs).isEqualToComparingFieldByField(testPolicyNature);
    }

    @Test
    @Transactional
    public void updateNonExistingPolicyNature() throws Exception {
        int databaseSizeBeforeUpdate = policyNatureRepository.findAll().size();

        // Create the PolicyNature
        PolicyNatureDTO policyNatureDTO = policyNatureMapper.toDto(policyNature);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPolicyNatureMockMvc.perform(put("/api/policy-natures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(policyNatureDTO)))
            .andExpect(status().isCreated());

        // Validate the PolicyNature in the database
        List<PolicyNature> policyNatureList = policyNatureRepository.findAll();
        assertThat(policyNatureList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePolicyNature() throws Exception {
        // Initialize the database
        policyNatureRepository.saveAndFlush(policyNature);
        policyNatureSearchRepository.save(policyNature);
        int databaseSizeBeforeDelete = policyNatureRepository.findAll().size();

        // Get the policyNature
        restPolicyNatureMockMvc.perform(delete("/api/policy-natures/{id}", policyNature.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean policyNatureExistsInEs = policyNatureSearchRepository.exists(policyNature.getId());
        assertThat(policyNatureExistsInEs).isFalse();

        // Validate the database is empty
        List<PolicyNature> policyNatureList = policyNatureRepository.findAll();
        assertThat(policyNatureList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchPolicyNature() throws Exception {
        // Initialize the database
        policyNatureRepository.saveAndFlush(policyNature);
        policyNatureSearchRepository.save(policyNature);

        // Search the policyNature
        restPolicyNatureMockMvc.perform(get("/api/_search/policy-natures?query=id:" + policyNature.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(policyNature.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PolicyNature.class);
        PolicyNature policyNature1 = new PolicyNature();
        policyNature1.setId(1L);
        PolicyNature policyNature2 = new PolicyNature();
        policyNature2.setId(policyNature1.getId());
        assertThat(policyNature1).isEqualTo(policyNature2);
        policyNature2.setId(2L);
        assertThat(policyNature1).isNotEqualTo(policyNature2);
        policyNature1.setId(null);
        assertThat(policyNature1).isNotEqualTo(policyNature2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PolicyNatureDTO.class);
        PolicyNatureDTO policyNatureDTO1 = new PolicyNatureDTO();
        policyNatureDTO1.setId(1L);
        PolicyNatureDTO policyNatureDTO2 = new PolicyNatureDTO();
        assertThat(policyNatureDTO1).isNotEqualTo(policyNatureDTO2);
        policyNatureDTO2.setId(policyNatureDTO1.getId());
        assertThat(policyNatureDTO1).isEqualTo(policyNatureDTO2);
        policyNatureDTO2.setId(2L);
        assertThat(policyNatureDTO1).isNotEqualTo(policyNatureDTO2);
        policyNatureDTO1.setId(null);
        assertThat(policyNatureDTO1).isNotEqualTo(policyNatureDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(policyNatureMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(policyNatureMapper.fromId(null)).isNull();
    }
}
