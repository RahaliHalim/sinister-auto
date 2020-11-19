package com.gaconnecte.auxilium.web.rest;

import com.gaconnecte.auxilium.AuxiliumApp;

import com.gaconnecte.auxilium.domain.view.ViewPolicy;
import com.gaconnecte.auxilium.repository.ViewPolicyRepository;
import com.gaconnecte.auxilium.service.ViewPolicyService;
import com.gaconnecte.auxilium.repository.search.ViewPolicySearchRepository;
import com.gaconnecte.auxilium.service.dto.ViewPolicyDTO;
import com.gaconnecte.auxilium.service.mapper.ViewPolicyMapper;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ViewPolicyResource REST controller.
 *
 * @see ViewPolicyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuxiliumApp.class)
public class ViewPolicyResourceIntTest {

    private static final String DEFAULT_POLICY_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_POLICY_NUMBER = "BBBBBBBBBB";

    private static final Long DEFAULT_COMPANY_ID = 1L;
    private static final Long UPDATED_COMPANY_ID = 2L;

    private static final String DEFAULT_COMPANY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_AGENCY_ID = 1L;
    private static final Long UPDATED_AGENCY_ID = 2L;

    private static final String DEFAULT_AGENCY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_AGENCY_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_VEHICLE_ID = 1L;
    private static final Long UPDATED_VEHICLE_ID = 2L;

    private static final String DEFAULT_REGISTRATION_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_REGISTRATION_NUMBER = "BBBBBBBBBB";

    private static final Long DEFAULT_POLICY_HOLDER_ID = 1L;
    private static final Long UPDATED_POLICY_HOLDER_ID = 2L;

    private static final String DEFAULT_POLICY_HOLDER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_POLICY_HOLDER_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private ViewPolicyRepository viewPolicyRepository;

    @Autowired
    private ViewPolicyMapper viewPolicyMapper;

    @Autowired
    private ViewPolicyService viewPolicyService;

    @Autowired
    private ViewPolicySearchRepository viewPolicySearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restViewPolicyMockMvc;

    private ViewPolicy viewPolicy;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ViewPolicyResource viewPolicyResource = new ViewPolicyResource(viewPolicyService);
        this.restViewPolicyMockMvc = MockMvcBuilders.standaloneSetup(viewPolicyResource)
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
    public static ViewPolicy createEntity(EntityManager em) {
        ViewPolicy viewPolicy = new ViewPolicy()
            .policyNumber(DEFAULT_POLICY_NUMBER)
            .companyId(DEFAULT_COMPANY_ID)
            .companyName(DEFAULT_COMPANY_NAME)
            .agencyId(DEFAULT_AGENCY_ID)
            .agencyName(DEFAULT_AGENCY_NAME)
            .vehicleId(DEFAULT_VEHICLE_ID)
            .registrationNumber(DEFAULT_REGISTRATION_NUMBER)
            .policyHolderId(DEFAULT_POLICY_HOLDER_ID)
            .policyHolderName(DEFAULT_POLICY_HOLDER_NAME)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE);
        return viewPolicy;
    }

    @Before
    public void initTest() {
        viewPolicySearchRepository.deleteAll();
        viewPolicy = createEntity(em);
    }

    @Test
    @Transactional
    public void createViewPolicy() throws Exception {
        int databaseSizeBeforeCreate = viewPolicyRepository.findAll().size();

        // Create the ViewPolicy
        ViewPolicyDTO viewPolicyDTO = viewPolicyMapper.toDto(viewPolicy);
        restViewPolicyMockMvc.perform(post("/api/view-policies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(viewPolicyDTO)))
            .andExpect(status().isCreated());

        // Validate the ViewPolicy in the database
        List<ViewPolicy> viewPolicyList = viewPolicyRepository.findAll();
        assertThat(viewPolicyList).hasSize(databaseSizeBeforeCreate + 1);
        ViewPolicy testViewPolicy = viewPolicyList.get(viewPolicyList.size() - 1);
        assertThat(testViewPolicy.getPolicyNumber()).isEqualTo(DEFAULT_POLICY_NUMBER);
        assertThat(testViewPolicy.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testViewPolicy.getCompanyName()).isEqualTo(DEFAULT_COMPANY_NAME);
        assertThat(testViewPolicy.getAgencyId()).isEqualTo(DEFAULT_AGENCY_ID);
        assertThat(testViewPolicy.getAgencyName()).isEqualTo(DEFAULT_AGENCY_NAME);
        assertThat(testViewPolicy.getVehicleId()).isEqualTo(DEFAULT_VEHICLE_ID);
        assertThat(testViewPolicy.getRegistrationNumber()).isEqualTo(DEFAULT_REGISTRATION_NUMBER);
        assertThat(testViewPolicy.getPolicyHolderId()).isEqualTo(DEFAULT_POLICY_HOLDER_ID);
        assertThat(testViewPolicy.getPolicyHolderName()).isEqualTo(DEFAULT_POLICY_HOLDER_NAME);
        assertThat(testViewPolicy.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testViewPolicy.getEndDate()).isEqualTo(DEFAULT_END_DATE);

        // Validate the ViewPolicy in Elasticsearch
        ViewPolicy viewPolicyEs = viewPolicySearchRepository.findOne(testViewPolicy.getId());
        assertThat(viewPolicyEs).isEqualToComparingFieldByField(testViewPolicy);
    }

    @Test
    @Transactional
    public void createViewPolicyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = viewPolicyRepository.findAll().size();

        // Create the ViewPolicy with an existing ID
        viewPolicy.setId(1L);
        ViewPolicyDTO viewPolicyDTO = viewPolicyMapper.toDto(viewPolicy);

        // An entity with an existing ID cannot be created, so this API call must fail
        restViewPolicyMockMvc.perform(post("/api/view-policies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(viewPolicyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ViewPolicy> viewPolicyList = viewPolicyRepository.findAll();
        assertThat(viewPolicyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllViewPolicies() throws Exception {
        // Initialize the database
        viewPolicyRepository.saveAndFlush(viewPolicy);

        // Get all the viewPolicyList
        restViewPolicyMockMvc.perform(get("/api/view-policies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(viewPolicy.getId().intValue())))
            .andExpect(jsonPath("$.[*].policyNumber").value(hasItem(DEFAULT_POLICY_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME.toString())))
            .andExpect(jsonPath("$.[*].agencyId").value(hasItem(DEFAULT_AGENCY_ID.intValue())))
            .andExpect(jsonPath("$.[*].agencyName").value(hasItem(DEFAULT_AGENCY_NAME.toString())))
            .andExpect(jsonPath("$.[*].vehicleId").value(hasItem(DEFAULT_VEHICLE_ID.intValue())))
            .andExpect(jsonPath("$.[*].registrationNumber").value(hasItem(DEFAULT_REGISTRATION_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].policyHolderId").value(hasItem(DEFAULT_POLICY_HOLDER_ID.intValue())))
            .andExpect(jsonPath("$.[*].policyHolderName").value(hasItem(DEFAULT_POLICY_HOLDER_NAME.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())));
    }

    @Test
    @Transactional
    public void getViewPolicy() throws Exception {
        // Initialize the database
        viewPolicyRepository.saveAndFlush(viewPolicy);

        // Get the viewPolicy
        restViewPolicyMockMvc.perform(get("/api/view-policies/{id}", viewPolicy.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(viewPolicy.getId().intValue()))
            .andExpect(jsonPath("$.policyNumber").value(DEFAULT_POLICY_NUMBER.toString()))
            .andExpect(jsonPath("$.companyId").value(DEFAULT_COMPANY_ID.intValue()))
            .andExpect(jsonPath("$.companyName").value(DEFAULT_COMPANY_NAME.toString()))
            .andExpect(jsonPath("$.agencyId").value(DEFAULT_AGENCY_ID.intValue()))
            .andExpect(jsonPath("$.agencyName").value(DEFAULT_AGENCY_NAME.toString()))
            .andExpect(jsonPath("$.vehicleId").value(DEFAULT_VEHICLE_ID.intValue()))
            .andExpect(jsonPath("$.registrationNumber").value(DEFAULT_REGISTRATION_NUMBER.toString()))
            .andExpect(jsonPath("$.policyHolderId").value(DEFAULT_POLICY_HOLDER_ID.intValue()))
            .andExpect(jsonPath("$.policyHolderName").value(DEFAULT_POLICY_HOLDER_NAME.toString()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingViewPolicy() throws Exception {
        // Get the viewPolicy
        restViewPolicyMockMvc.perform(get("/api/view-policies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateViewPolicy() throws Exception {
        // Initialize the database
        viewPolicyRepository.saveAndFlush(viewPolicy);
        viewPolicySearchRepository.save(viewPolicy);
        int databaseSizeBeforeUpdate = viewPolicyRepository.findAll().size();

        // Update the viewPolicy
        ViewPolicy updatedViewPolicy = viewPolicyRepository.findOne(viewPolicy.getId());
        updatedViewPolicy
            .policyNumber(UPDATED_POLICY_NUMBER)
            .companyId(UPDATED_COMPANY_ID)
            .companyName(UPDATED_COMPANY_NAME)
            .agencyId(UPDATED_AGENCY_ID)
            .agencyName(UPDATED_AGENCY_NAME)
            .vehicleId(UPDATED_VEHICLE_ID)
            .registrationNumber(UPDATED_REGISTRATION_NUMBER)
            .policyHolderId(UPDATED_POLICY_HOLDER_ID)
            .policyHolderName(UPDATED_POLICY_HOLDER_NAME)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE);
        ViewPolicyDTO viewPolicyDTO = viewPolicyMapper.toDto(updatedViewPolicy);

        restViewPolicyMockMvc.perform(put("/api/view-policies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(viewPolicyDTO)))
            .andExpect(status().isOk());

        // Validate the ViewPolicy in the database
        List<ViewPolicy> viewPolicyList = viewPolicyRepository.findAll();
        assertThat(viewPolicyList).hasSize(databaseSizeBeforeUpdate);
        ViewPolicy testViewPolicy = viewPolicyList.get(viewPolicyList.size() - 1);
        assertThat(testViewPolicy.getPolicyNumber()).isEqualTo(UPDATED_POLICY_NUMBER);
        assertThat(testViewPolicy.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testViewPolicy.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
        assertThat(testViewPolicy.getAgencyId()).isEqualTo(UPDATED_AGENCY_ID);
        assertThat(testViewPolicy.getAgencyName()).isEqualTo(UPDATED_AGENCY_NAME);
        assertThat(testViewPolicy.getVehicleId()).isEqualTo(UPDATED_VEHICLE_ID);
        assertThat(testViewPolicy.getRegistrationNumber()).isEqualTo(UPDATED_REGISTRATION_NUMBER);
        assertThat(testViewPolicy.getPolicyHolderId()).isEqualTo(UPDATED_POLICY_HOLDER_ID);
        assertThat(testViewPolicy.getPolicyHolderName()).isEqualTo(UPDATED_POLICY_HOLDER_NAME);
        assertThat(testViewPolicy.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testViewPolicy.getEndDate()).isEqualTo(UPDATED_END_DATE);

        // Validate the ViewPolicy in Elasticsearch
        ViewPolicy viewPolicyEs = viewPolicySearchRepository.findOne(testViewPolicy.getId());
        assertThat(viewPolicyEs).isEqualToComparingFieldByField(testViewPolicy);
    }

    @Test
    @Transactional
    public void updateNonExistingViewPolicy() throws Exception {
        int databaseSizeBeforeUpdate = viewPolicyRepository.findAll().size();

        // Create the ViewPolicy
        ViewPolicyDTO viewPolicyDTO = viewPolicyMapper.toDto(viewPolicy);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restViewPolicyMockMvc.perform(put("/api/view-policies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(viewPolicyDTO)))
            .andExpect(status().isCreated());

        // Validate the ViewPolicy in the database
        List<ViewPolicy> viewPolicyList = viewPolicyRepository.findAll();
        assertThat(viewPolicyList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteViewPolicy() throws Exception {
        // Initialize the database
        viewPolicyRepository.saveAndFlush(viewPolicy);
        viewPolicySearchRepository.save(viewPolicy);
        int databaseSizeBeforeDelete = viewPolicyRepository.findAll().size();

        // Get the viewPolicy
        restViewPolicyMockMvc.perform(delete("/api/view-policies/{id}", viewPolicy.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean viewPolicyExistsInEs = viewPolicySearchRepository.exists(viewPolicy.getId());
        assertThat(viewPolicyExistsInEs).isFalse();

        // Validate the database is empty
        List<ViewPolicy> viewPolicyList = viewPolicyRepository.findAll();
        assertThat(viewPolicyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchViewPolicy() throws Exception {
        // Initialize the database
        viewPolicyRepository.saveAndFlush(viewPolicy);
        viewPolicySearchRepository.save(viewPolicy);

        // Search the viewPolicy
        restViewPolicyMockMvc.perform(get("/api/_search/view-policies?query=id:" + viewPolicy.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(viewPolicy.getId().intValue())))
            .andExpect(jsonPath("$.[*].policyNumber").value(hasItem(DEFAULT_POLICY_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME.toString())))
            .andExpect(jsonPath("$.[*].agencyId").value(hasItem(DEFAULT_AGENCY_ID.intValue())))
            .andExpect(jsonPath("$.[*].agencyName").value(hasItem(DEFAULT_AGENCY_NAME.toString())))
            .andExpect(jsonPath("$.[*].vehicleId").value(hasItem(DEFAULT_VEHICLE_ID.intValue())))
            .andExpect(jsonPath("$.[*].registrationNumber").value(hasItem(DEFAULT_REGISTRATION_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].policyHolderId").value(hasItem(DEFAULT_POLICY_HOLDER_ID.intValue())))
            .andExpect(jsonPath("$.[*].policyHolderName").value(hasItem(DEFAULT_POLICY_HOLDER_NAME.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ViewPolicy.class);
        ViewPolicy viewPolicy1 = new ViewPolicy();
        viewPolicy1.setId(1L);
        ViewPolicy viewPolicy2 = new ViewPolicy();
        viewPolicy2.setId(viewPolicy1.getId());
        assertThat(viewPolicy1).isEqualTo(viewPolicy2);
        viewPolicy2.setId(2L);
        assertThat(viewPolicy1).isNotEqualTo(viewPolicy2);
        viewPolicy1.setId(null);
        assertThat(viewPolicy1).isNotEqualTo(viewPolicy2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ViewPolicyDTO.class);
        ViewPolicyDTO viewPolicyDTO1 = new ViewPolicyDTO();
        viewPolicyDTO1.setId(1L);
        ViewPolicyDTO viewPolicyDTO2 = new ViewPolicyDTO();
        assertThat(viewPolicyDTO1).isNotEqualTo(viewPolicyDTO2);
        viewPolicyDTO2.setId(viewPolicyDTO1.getId());
        assertThat(viewPolicyDTO1).isEqualTo(viewPolicyDTO2);
        viewPolicyDTO2.setId(2L);
        assertThat(viewPolicyDTO1).isNotEqualTo(viewPolicyDTO2);
        viewPolicyDTO1.setId(null);
        assertThat(viewPolicyDTO1).isNotEqualTo(viewPolicyDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(viewPolicyMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(viewPolicyMapper.fromId(null)).isNull();
    }
}
