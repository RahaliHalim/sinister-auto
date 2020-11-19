package com.gaconnecte.auxilium.web.rest;

import com.gaconnecte.auxilium.AuxiliumApp;

import com.gaconnecte.auxilium.domain.Policy;
import com.gaconnecte.auxilium.repository.PolicyRepository;
import com.gaconnecte.auxilium.service.PolicyService;
import com.gaconnecte.auxilium.repository.search.PolicySearchRepository;
import com.gaconnecte.auxilium.service.dto.PolicyDTO;
import com.gaconnecte.auxilium.service.mapper.PolicyMapper;
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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PolicyResource REST controller.
 *
 * @see PolicyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuxiliumApp.class)
public class PolicyResourceIntTest {

    private static final String DEFAULT_REFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_NEW_VEHICLE_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_NEW_VEHICLE_PRICE = new BigDecimal(2);

    private static final Boolean DEFAULT_NEW_VEHICLE_PRICE_IS_AMOUNT = false;
    private static final Boolean UPDATED_NEW_VEHICLE_PRICE_IS_AMOUNT = true;

    private static final BigDecimal DEFAULT_DC_CAPITAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_DC_CAPITAL = new BigDecimal(2);

    private static final Boolean DEFAULT_DC_CAPITAL_IS_AMOUNT = false;
    private static final Boolean UPDATED_DC_CAPITAL_IS_AMOUNT = true;

    private static final BigDecimal DEFAULT_BG_CAPITAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_BG_CAPITAL = new BigDecimal(2);

    private static final Boolean DEFAULT_BG_CAPITAL_IS_AMOUNT = false;
    private static final Boolean UPDATED_BG_CAPITAL_IS_AMOUNT = true;

    private static final BigDecimal DEFAULT_MARKET_VALUE = new BigDecimal(1);
    private static final BigDecimal UPDATED_MARKET_VALUE = new BigDecimal(2);

    private static final Boolean DEFAULT_MARKET_VALUE_IS_AMOUNT = false;
    private static final Boolean UPDATED_MARKET_VALUE_IS_AMOUNT = true;

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    @Autowired
    private PolicyRepository policyRepository;

    @Autowired
    private PolicyMapper policyMapper;

    @Autowired
    private PolicyService policyService;

    @Autowired
    private PolicySearchRepository policySearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPolicyMockMvc;

    private Policy policy;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PolicyResource policyResource = new PolicyResource(policyService);
        this.restPolicyMockMvc = MockMvcBuilders.standaloneSetup(policyResource)
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
    public static Policy createEntity(EntityManager em) {
        Policy policy = new Policy()
            .reference(DEFAULT_REFERENCE)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .newVehiclePrice(DEFAULT_NEW_VEHICLE_PRICE)
            .newVehiclePriceIsAmount(DEFAULT_NEW_VEHICLE_PRICE_IS_AMOUNT)
            .dcCapital(DEFAULT_DC_CAPITAL)
            .dcCapitalIsAmount(DEFAULT_DC_CAPITAL_IS_AMOUNT)
            .bgCapital(DEFAULT_BG_CAPITAL)
            .bgCapitalIsAmount(DEFAULT_BG_CAPITAL_IS_AMOUNT)
            .marketValue(DEFAULT_MARKET_VALUE)
            .marketValueIsAmount(DEFAULT_MARKET_VALUE_IS_AMOUNT)
            .active(DEFAULT_ACTIVE)
            .deleted(DEFAULT_DELETED);
        return policy;
    }

    @Before
    public void initTest() {
        policySearchRepository.deleteAll();
        policy = createEntity(em);
    }

    @Test
    @Transactional
    public void createPolicy() throws Exception {
        int databaseSizeBeforeCreate = policyRepository.findAll().size();

        // Create the Policy
        PolicyDTO policyDTO = policyMapper.toDto(policy);
        restPolicyMockMvc.perform(post("/api/policies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(policyDTO)))
            .andExpect(status().isCreated());

        // Validate the Policy in the database
        List<Policy> policyList = policyRepository.findAll();
        assertThat(policyList).hasSize(databaseSizeBeforeCreate + 1);
        Policy testPolicy = policyList.get(policyList.size() - 1);
        assertThat(testPolicy.getReference()).isEqualTo(DEFAULT_REFERENCE);
        assertThat(testPolicy.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testPolicy.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testPolicy.getNewVehiclePrice()).isEqualTo(DEFAULT_NEW_VEHICLE_PRICE);
        assertThat(testPolicy.isNewVehiclePriceIsAmount()).isEqualTo(DEFAULT_NEW_VEHICLE_PRICE_IS_AMOUNT);
        assertThat(testPolicy.getDcCapital()).isEqualTo(DEFAULT_DC_CAPITAL);
        assertThat(testPolicy.isDcCapitalIsAmount()).isEqualTo(DEFAULT_DC_CAPITAL_IS_AMOUNT);
        assertThat(testPolicy.getBgCapital()).isEqualTo(DEFAULT_BG_CAPITAL);
        assertThat(testPolicy.isBgCapitalIsAmount()).isEqualTo(DEFAULT_BG_CAPITAL_IS_AMOUNT);
        assertThat(testPolicy.getMarketValue()).isEqualTo(DEFAULT_MARKET_VALUE);
        assertThat(testPolicy.isMarketValueIsAmount()).isEqualTo(DEFAULT_MARKET_VALUE_IS_AMOUNT);
        assertThat(testPolicy.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testPolicy.isDeleted()).isEqualTo(DEFAULT_DELETED);

        // Validate the Policy in Elasticsearch
        Policy policyEs = policySearchRepository.findOne(testPolicy.getId());
        assertThat(policyEs).isEqualToComparingFieldByField(testPolicy);
    }

    @Test
    @Transactional
    public void createPolicyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = policyRepository.findAll().size();

        // Create the Policy with an existing ID
        policy.setId(1L);
        PolicyDTO policyDTO = policyMapper.toDto(policy);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPolicyMockMvc.perform(post("/api/policies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(policyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Policy> policyList = policyRepository.findAll();
        assertThat(policyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPolicies() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList
        restPolicyMockMvc.perform(get("/api/policies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(policy.getId().intValue())))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].newVehiclePrice").value(hasItem(DEFAULT_NEW_VEHICLE_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].newVehiclePriceIsAmount").value(hasItem(DEFAULT_NEW_VEHICLE_PRICE_IS_AMOUNT.booleanValue())))
            .andExpect(jsonPath("$.[*].dcCapital").value(hasItem(DEFAULT_DC_CAPITAL.intValue())))
            .andExpect(jsonPath("$.[*].dcCapitalIsAmount").value(hasItem(DEFAULT_DC_CAPITAL_IS_AMOUNT.booleanValue())))
            .andExpect(jsonPath("$.[*].bgCapital").value(hasItem(DEFAULT_BG_CAPITAL.intValue())))
            .andExpect(jsonPath("$.[*].bgCapitalIsAmount").value(hasItem(DEFAULT_BG_CAPITAL_IS_AMOUNT.booleanValue())))
            .andExpect(jsonPath("$.[*].marketValue").value(hasItem(DEFAULT_MARKET_VALUE.intValue())))
            .andExpect(jsonPath("$.[*].marketValueIsAmount").value(hasItem(DEFAULT_MARKET_VALUE_IS_AMOUNT.booleanValue())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())));
    }

    @Test
    @Transactional
    public void getPolicy() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get the policy
        restPolicyMockMvc.perform(get("/api/policies/{id}", policy.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(policy.getId().intValue()))
            .andExpect(jsonPath("$.reference").value(DEFAULT_REFERENCE.toString()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.newVehiclePrice").value(DEFAULT_NEW_VEHICLE_PRICE.intValue()))
            .andExpect(jsonPath("$.newVehiclePriceIsAmount").value(DEFAULT_NEW_VEHICLE_PRICE_IS_AMOUNT.booleanValue()))
            .andExpect(jsonPath("$.dcCapital").value(DEFAULT_DC_CAPITAL.intValue()))
            .andExpect(jsonPath("$.dcCapitalIsAmount").value(DEFAULT_DC_CAPITAL_IS_AMOUNT.booleanValue()))
            .andExpect(jsonPath("$.bgCapital").value(DEFAULT_BG_CAPITAL.intValue()))
            .andExpect(jsonPath("$.bgCapitalIsAmount").value(DEFAULT_BG_CAPITAL_IS_AMOUNT.booleanValue()))
            .andExpect(jsonPath("$.marketValue").value(DEFAULT_MARKET_VALUE.intValue()))
            .andExpect(jsonPath("$.marketValueIsAmount").value(DEFAULT_MARKET_VALUE_IS_AMOUNT.booleanValue()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPolicy() throws Exception {
        // Get the policy
        restPolicyMockMvc.perform(get("/api/policies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePolicy() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);
        policySearchRepository.save(policy);
        int databaseSizeBeforeUpdate = policyRepository.findAll().size();

        // Update the policy
        Policy updatedPolicy = policyRepository.findOne(policy.getId());
        updatedPolicy
            .reference(UPDATED_REFERENCE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .newVehiclePrice(UPDATED_NEW_VEHICLE_PRICE)
            .newVehiclePriceIsAmount(UPDATED_NEW_VEHICLE_PRICE_IS_AMOUNT)
            .dcCapital(UPDATED_DC_CAPITAL)
            .dcCapitalIsAmount(UPDATED_DC_CAPITAL_IS_AMOUNT)
            .bgCapital(UPDATED_BG_CAPITAL)
            .bgCapitalIsAmount(UPDATED_BG_CAPITAL_IS_AMOUNT)
            .marketValue(UPDATED_MARKET_VALUE)
            .marketValueIsAmount(UPDATED_MARKET_VALUE_IS_AMOUNT)
            .active(UPDATED_ACTIVE)
            .deleted(UPDATED_DELETED);
        PolicyDTO policyDTO = policyMapper.toDto(updatedPolicy);

        restPolicyMockMvc.perform(put("/api/policies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(policyDTO)))
            .andExpect(status().isOk());

        // Validate the Policy in the database
        List<Policy> policyList = policyRepository.findAll();
        assertThat(policyList).hasSize(databaseSizeBeforeUpdate);
        Policy testPolicy = policyList.get(policyList.size() - 1);
        assertThat(testPolicy.getReference()).isEqualTo(UPDATED_REFERENCE);
        assertThat(testPolicy.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testPolicy.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testPolicy.getNewVehiclePrice()).isEqualTo(UPDATED_NEW_VEHICLE_PRICE);
        assertThat(testPolicy.isNewVehiclePriceIsAmount()).isEqualTo(UPDATED_NEW_VEHICLE_PRICE_IS_AMOUNT);
        assertThat(testPolicy.getDcCapital()).isEqualTo(UPDATED_DC_CAPITAL);
        assertThat(testPolicy.isDcCapitalIsAmount()).isEqualTo(UPDATED_DC_CAPITAL_IS_AMOUNT);
        assertThat(testPolicy.getBgCapital()).isEqualTo(UPDATED_BG_CAPITAL);
        assertThat(testPolicy.isBgCapitalIsAmount()).isEqualTo(UPDATED_BG_CAPITAL_IS_AMOUNT);
        assertThat(testPolicy.getMarketValue()).isEqualTo(UPDATED_MARKET_VALUE);
        assertThat(testPolicy.isMarketValueIsAmount()).isEqualTo(UPDATED_MARKET_VALUE_IS_AMOUNT);
        assertThat(testPolicy.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testPolicy.isDeleted()).isEqualTo(UPDATED_DELETED);

        // Validate the Policy in Elasticsearch
        Policy policyEs = policySearchRepository.findOne(testPolicy.getId());
        assertThat(policyEs).isEqualToComparingFieldByField(testPolicy);
    }

    @Test
    @Transactional
    public void updateNonExistingPolicy() throws Exception {
        int databaseSizeBeforeUpdate = policyRepository.findAll().size();

        // Create the Policy
        PolicyDTO policyDTO = policyMapper.toDto(policy);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPolicyMockMvc.perform(put("/api/policies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(policyDTO)))
            .andExpect(status().isCreated());

        // Validate the Policy in the database
        List<Policy> policyList = policyRepository.findAll();
        assertThat(policyList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePolicy() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);
        policySearchRepository.save(policy);
        int databaseSizeBeforeDelete = policyRepository.findAll().size();

        // Get the policy
        restPolicyMockMvc.perform(delete("/api/policies/{id}", policy.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean policyExistsInEs = policySearchRepository.exists(policy.getId());
        assertThat(policyExistsInEs).isFalse();

        // Validate the database is empty
        List<Policy> policyList = policyRepository.findAll();
        assertThat(policyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchPolicy() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);
        policySearchRepository.save(policy);

        // Search the policy
        restPolicyMockMvc.perform(get("/api/_search/policies?query=id:" + policy.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(policy.getId().intValue())))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].newVehiclePrice").value(hasItem(DEFAULT_NEW_VEHICLE_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].newVehiclePriceIsAmount").value(hasItem(DEFAULT_NEW_VEHICLE_PRICE_IS_AMOUNT.booleanValue())))
            .andExpect(jsonPath("$.[*].dcCapital").value(hasItem(DEFAULT_DC_CAPITAL.intValue())))
            .andExpect(jsonPath("$.[*].dcCapitalIsAmount").value(hasItem(DEFAULT_DC_CAPITAL_IS_AMOUNT.booleanValue())))
            .andExpect(jsonPath("$.[*].bgCapital").value(hasItem(DEFAULT_BG_CAPITAL.intValue())))
            .andExpect(jsonPath("$.[*].bgCapitalIsAmount").value(hasItem(DEFAULT_BG_CAPITAL_IS_AMOUNT.booleanValue())))
            .andExpect(jsonPath("$.[*].marketValue").value(hasItem(DEFAULT_MARKET_VALUE.intValue())))
            .andExpect(jsonPath("$.[*].marketValueIsAmount").value(hasItem(DEFAULT_MARKET_VALUE_IS_AMOUNT.booleanValue())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Policy.class);
        Policy policy1 = new Policy();
        policy1.setId(1L);
        Policy policy2 = new Policy();
        policy2.setId(policy1.getId());
        assertThat(policy1).isEqualTo(policy2);
        policy2.setId(2L);
        assertThat(policy1).isNotEqualTo(policy2);
        policy1.setId(null);
        assertThat(policy1).isNotEqualTo(policy2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PolicyDTO.class);
        PolicyDTO policyDTO1 = new PolicyDTO();
        policyDTO1.setId(1L);
        PolicyDTO policyDTO2 = new PolicyDTO();
        assertThat(policyDTO1).isNotEqualTo(policyDTO2);
        policyDTO2.setId(policyDTO1.getId());
        assertThat(policyDTO1).isEqualTo(policyDTO2);
        policyDTO2.setId(2L);
        assertThat(policyDTO1).isNotEqualTo(policyDTO2);
        policyDTO1.setId(null);
        assertThat(policyDTO1).isNotEqualTo(policyDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(policyMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(policyMapper.fromId(null)).isNull();
    }
}
