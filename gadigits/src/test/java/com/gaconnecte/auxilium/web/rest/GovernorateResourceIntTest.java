package com.gaconnecte.auxilium.web.rest;

import com.gaconnecte.auxilium.AuxiliumApp;

import com.gaconnecte.auxilium.domain.Governorate;
import com.gaconnecte.auxilium.repository.GovernorateRepository;
import com.gaconnecte.auxilium.service.GovernorateService;
import com.gaconnecte.auxilium.repository.search.GovernorateSearchRepository;
import com.gaconnecte.auxilium.service.dto.GovernorateDTO;
import com.gaconnecte.auxilium.service.mapper.GovernorateMapper;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the GovernorateResource REST controller.
 *
 * @see GovernorateResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuxiliumApp.class)
public class GovernorateResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_LATITUDE = new BigDecimal(1);
    private static final BigDecimal UPDATED_LATITUDE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_LONGITUDE = new BigDecimal(1);
    private static final BigDecimal UPDATED_LONGITUDE = new BigDecimal(2);

    @Autowired
    private GovernorateRepository governorateRepository;

    @Autowired
    private GovernorateMapper governorateMapper;

    @Autowired
    private GovernorateService governorateService;

    @Autowired
    private GovernorateSearchRepository governorateSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGovernorateMockMvc;

    private Governorate governorate;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        GovernorateResource governorateResource = new GovernorateResource(governorateService);
        this.restGovernorateMockMvc = MockMvcBuilders.standaloneSetup(governorateResource)
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
    public static Governorate createEntity(EntityManager em) {
        Governorate governorate = new Governorate()
            .code(DEFAULT_CODE)
            .label(DEFAULT_LABEL)
            .latitude(DEFAULT_LATITUDE)
            .longitude(DEFAULT_LONGITUDE);
        return governorate;
    }

    @Before
    public void initTest() {
        governorateSearchRepository.deleteAll();
        governorate = createEntity(em);
    }

    @Test
    @Transactional
    public void createGovernorate() throws Exception {
        int databaseSizeBeforeCreate = governorateRepository.findAll().size();

        // Create the Governorate
        GovernorateDTO governorateDTO = governorateMapper.toDto(governorate);
        restGovernorateMockMvc.perform(post("/api/governorates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(governorateDTO)))
            .andExpect(status().isCreated());

        // Validate the Governorate in the database
        List<Governorate> governorateList = governorateRepository.findAll();
        assertThat(governorateList).hasSize(databaseSizeBeforeCreate + 1);
        Governorate testGovernorate = governorateList.get(governorateList.size() - 1);
        assertThat(testGovernorate.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testGovernorate.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testGovernorate.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testGovernorate.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);

        // Validate the Governorate in Elasticsearch
        Governorate governorateEs = governorateSearchRepository.findOne(testGovernorate.getId());
        assertThat(governorateEs).isEqualToComparingFieldByField(testGovernorate);
    }

    @Test
    @Transactional
    public void createGovernorateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = governorateRepository.findAll().size();

        // Create the Governorate with an existing ID
        governorate.setId(1L);
        GovernorateDTO governorateDTO = governorateMapper.toDto(governorate);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGovernorateMockMvc.perform(post("/api/governorates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(governorateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Governorate> governorateList = governorateRepository.findAll();
        assertThat(governorateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllGovernorates() throws Exception {
        // Initialize the database
        governorateRepository.saveAndFlush(governorate);

        // Get all the governorateList
        restGovernorateMockMvc.perform(get("/api/governorates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(governorate.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.intValue())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.intValue())));
    }

    @Test
    @Transactional
    public void getGovernorate() throws Exception {
        // Initialize the database
        governorateRepository.saveAndFlush(governorate);

        // Get the governorate
        restGovernorateMockMvc.perform(get("/api/governorates/{id}", governorate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(governorate.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.intValue()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingGovernorate() throws Exception {
        // Get the governorate
        restGovernorateMockMvc.perform(get("/api/governorates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGovernorate() throws Exception {
        // Initialize the database
        governorateRepository.saveAndFlush(governorate);
        governorateSearchRepository.save(governorate);
        int databaseSizeBeforeUpdate = governorateRepository.findAll().size();

        // Update the governorate
        Governorate updatedGovernorate = governorateRepository.findOne(governorate.getId());
        updatedGovernorate
            .code(UPDATED_CODE)
            .label(UPDATED_LABEL)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE);
        GovernorateDTO governorateDTO = governorateMapper.toDto(updatedGovernorate);

        restGovernorateMockMvc.perform(put("/api/governorates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(governorateDTO)))
            .andExpect(status().isOk());

        // Validate the Governorate in the database
        List<Governorate> governorateList = governorateRepository.findAll();
        assertThat(governorateList).hasSize(databaseSizeBeforeUpdate);
        Governorate testGovernorate = governorateList.get(governorateList.size() - 1);
        assertThat(testGovernorate.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testGovernorate.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testGovernorate.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testGovernorate.getLongitude()).isEqualTo(UPDATED_LONGITUDE);

        // Validate the Governorate in Elasticsearch
        Governorate governorateEs = governorateSearchRepository.findOne(testGovernorate.getId());
        assertThat(governorateEs).isEqualToComparingFieldByField(testGovernorate);
    }

    @Test
    @Transactional
    public void updateNonExistingGovernorate() throws Exception {
        int databaseSizeBeforeUpdate = governorateRepository.findAll().size();

        // Create the Governorate
        GovernorateDTO governorateDTO = governorateMapper.toDto(governorate);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restGovernorateMockMvc.perform(put("/api/governorates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(governorateDTO)))
            .andExpect(status().isCreated());

        // Validate the Governorate in the database
        List<Governorate> governorateList = governorateRepository.findAll();
        assertThat(governorateList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteGovernorate() throws Exception {
        // Initialize the database
        governorateRepository.saveAndFlush(governorate);
        governorateSearchRepository.save(governorate);
        int databaseSizeBeforeDelete = governorateRepository.findAll().size();

        // Get the governorate
        restGovernorateMockMvc.perform(delete("/api/governorates/{id}", governorate.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean governorateExistsInEs = governorateSearchRepository.exists(governorate.getId());
        assertThat(governorateExistsInEs).isFalse();

        // Validate the database is empty
        List<Governorate> governorateList = governorateRepository.findAll();
        assertThat(governorateList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchGovernorate() throws Exception {
        // Initialize the database
        governorateRepository.saveAndFlush(governorate);
        governorateSearchRepository.save(governorate);

        // Search the governorate
        restGovernorateMockMvc.perform(get("/api/_search/governorates?query=id:" + governorate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(governorate.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.intValue())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Governorate.class);
        Governorate governorate1 = new Governorate();
        governorate1.setId(1L);
        Governorate governorate2 = new Governorate();
        governorate2.setId(governorate1.getId());
        assertThat(governorate1).isEqualTo(governorate2);
        governorate2.setId(2L);
        assertThat(governorate1).isNotEqualTo(governorate2);
        governorate1.setId(null);
        assertThat(governorate1).isNotEqualTo(governorate2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GovernorateDTO.class);
        GovernorateDTO governorateDTO1 = new GovernorateDTO();
        governorateDTO1.setId(1L);
        GovernorateDTO governorateDTO2 = new GovernorateDTO();
        assertThat(governorateDTO1).isNotEqualTo(governorateDTO2);
        governorateDTO2.setId(governorateDTO1.getId());
        assertThat(governorateDTO1).isEqualTo(governorateDTO2);
        governorateDTO2.setId(2L);
        assertThat(governorateDTO1).isNotEqualTo(governorateDTO2);
        governorateDTO1.setId(null);
        assertThat(governorateDTO1).isNotEqualTo(governorateDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(governorateMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(governorateMapper.fromId(null)).isNull();
    }
}
