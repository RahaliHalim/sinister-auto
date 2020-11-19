package com.gaconnecte.auxilium.web.rest;

import com.gaconnecte.auxilium.AuxiliumApp;

import com.gaconnecte.auxilium.domain.ServiceAssurance;
import com.gaconnecte.auxilium.repository.ServiceAssuranceRepository;
import com.gaconnecte.auxilium.service.ServiceAssuranceService;
import com.gaconnecte.auxilium.repository.search.ServiceAssuranceSearchRepository;
import com.gaconnecte.auxilium.service.dto.ServiceAssuranceDTO;
import com.gaconnecte.auxilium.service.mapper.ServiceAssuranceMapper;
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
 * Test class for the ServiceAssuranceResource REST controller.
 *
 * @see ServiceAssuranceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuxiliumApp.class)
public class ServiceAssuranceResourceIntTest {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    @Autowired
    private ServiceAssuranceRepository serviceAssuranceRepository;

    @Autowired
    private ServiceAssuranceMapper serviceAssuranceMapper;

    @Autowired
    private ServiceAssuranceService serviceAssuranceService;

    @Autowired
    private ServiceAssuranceSearchRepository serviceAssuranceSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restServiceAssuranceMockMvc;

    private ServiceAssurance serviceAssurance;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ServiceAssuranceResource serviceAssuranceResource = new ServiceAssuranceResource(serviceAssuranceService);
        this.restServiceAssuranceMockMvc = MockMvcBuilders.standaloneSetup(serviceAssuranceResource)
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
    public static ServiceAssurance createEntity(EntityManager em) {
        ServiceAssurance serviceAssurance = new ServiceAssurance()
            .libelle(DEFAULT_LIBELLE);
        return serviceAssurance;
    }

    @Before
    public void initTest() {
        serviceAssuranceSearchRepository.deleteAll();
        serviceAssurance = createEntity(em);
    }

    @Test
    @Transactional
    public void createServiceAssurance() throws Exception {
        int databaseSizeBeforeCreate = serviceAssuranceRepository.findAll().size();

        // Create the ServiceAssurance
        ServiceAssuranceDTO serviceAssuranceDTO = serviceAssuranceMapper.toDto(serviceAssurance);
        restServiceAssuranceMockMvc.perform(post("/api/service-assurances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceAssuranceDTO)))
            .andExpect(status().isCreated());

        // Validate the ServiceAssurance in the database
        List<ServiceAssurance> serviceAssuranceList = serviceAssuranceRepository.findAll();
        assertThat(serviceAssuranceList).hasSize(databaseSizeBeforeCreate + 1);
        ServiceAssurance testServiceAssurance = serviceAssuranceList.get(serviceAssuranceList.size() - 1);
        assertThat(testServiceAssurance.getLibelle()).isEqualTo(DEFAULT_LIBELLE);

        // Validate the ServiceAssurance in Elasticsearch
        ServiceAssurance serviceAssuranceEs = serviceAssuranceSearchRepository.findOne(testServiceAssurance.getId());
        assertThat(serviceAssuranceEs).isEqualToComparingFieldByField(testServiceAssurance);
    }

    @Test
    @Transactional
    public void createServiceAssuranceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = serviceAssuranceRepository.findAll().size();

        // Create the ServiceAssurance with an existing ID
        serviceAssurance.setId(1L);
        ServiceAssuranceDTO serviceAssuranceDTO = serviceAssuranceMapper.toDto(serviceAssurance);

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiceAssuranceMockMvc.perform(post("/api/service-assurances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceAssuranceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ServiceAssurance> serviceAssuranceList = serviceAssuranceRepository.findAll();
        assertThat(serviceAssuranceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceAssuranceRepository.findAll().size();
        // set the field null
        serviceAssurance.setLibelle(null);

        // Create the ServiceAssurance, which fails.
        ServiceAssuranceDTO serviceAssuranceDTO = serviceAssuranceMapper.toDto(serviceAssurance);

        restServiceAssuranceMockMvc.perform(post("/api/service-assurances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceAssuranceDTO)))
            .andExpect(status().isBadRequest());

        List<ServiceAssurance> serviceAssuranceList = serviceAssuranceRepository.findAll();
        assertThat(serviceAssuranceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllServiceAssurances() throws Exception {
        // Initialize the database
        serviceAssuranceRepository.saveAndFlush(serviceAssurance);

        // Get all the serviceAssuranceList
        restServiceAssuranceMockMvc.perform(get("/api/service-assurances?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceAssurance.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())));
    }

    @Test
    @Transactional
    public void getServiceAssurance() throws Exception {
        // Initialize the database
        serviceAssuranceRepository.saveAndFlush(serviceAssurance);

        // Get the serviceAssurance
        restServiceAssuranceMockMvc.perform(get("/api/service-assurances/{id}", serviceAssurance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(serviceAssurance.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingServiceAssurance() throws Exception {
        // Get the serviceAssurance
        restServiceAssuranceMockMvc.perform(get("/api/service-assurances/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateServiceAssurance() throws Exception {
        // Initialize the database
        serviceAssuranceRepository.saveAndFlush(serviceAssurance);
        serviceAssuranceSearchRepository.save(serviceAssurance);
        int databaseSizeBeforeUpdate = serviceAssuranceRepository.findAll().size();

        // Update the serviceAssurance
        ServiceAssurance updatedServiceAssurance = serviceAssuranceRepository.findOne(serviceAssurance.getId());
        updatedServiceAssurance
            .libelle(UPDATED_LIBELLE);
        ServiceAssuranceDTO serviceAssuranceDTO = serviceAssuranceMapper.toDto(updatedServiceAssurance);

        restServiceAssuranceMockMvc.perform(put("/api/service-assurances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceAssuranceDTO)))
            .andExpect(status().isOk());

        // Validate the ServiceAssurance in the database
        List<ServiceAssurance> serviceAssuranceList = serviceAssuranceRepository.findAll();
        assertThat(serviceAssuranceList).hasSize(databaseSizeBeforeUpdate);
        ServiceAssurance testServiceAssurance = serviceAssuranceList.get(serviceAssuranceList.size() - 1);
        assertThat(testServiceAssurance.getLibelle()).isEqualTo(UPDATED_LIBELLE);

        // Validate the ServiceAssurance in Elasticsearch
        ServiceAssurance serviceAssuranceEs = serviceAssuranceSearchRepository.findOne(testServiceAssurance.getId());
        assertThat(serviceAssuranceEs).isEqualToComparingFieldByField(testServiceAssurance);
    }

    @Test
    @Transactional
    public void updateNonExistingServiceAssurance() throws Exception {
        int databaseSizeBeforeUpdate = serviceAssuranceRepository.findAll().size();

        // Create the ServiceAssurance
        ServiceAssuranceDTO serviceAssuranceDTO = serviceAssuranceMapper.toDto(serviceAssurance);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restServiceAssuranceMockMvc.perform(put("/api/service-assurances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceAssuranceDTO)))
            .andExpect(status().isCreated());

        // Validate the ServiceAssurance in the database
        List<ServiceAssurance> serviceAssuranceList = serviceAssuranceRepository.findAll();
        assertThat(serviceAssuranceList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteServiceAssurance() throws Exception {
        // Initialize the database
        serviceAssuranceRepository.saveAndFlush(serviceAssurance);
        serviceAssuranceSearchRepository.save(serviceAssurance);
        int databaseSizeBeforeDelete = serviceAssuranceRepository.findAll().size();

        // Get the serviceAssurance
        restServiceAssuranceMockMvc.perform(delete("/api/service-assurances/{id}", serviceAssurance.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean serviceAssuranceExistsInEs = serviceAssuranceSearchRepository.exists(serviceAssurance.getId());
        assertThat(serviceAssuranceExistsInEs).isFalse();

        // Validate the database is empty
        List<ServiceAssurance> serviceAssuranceList = serviceAssuranceRepository.findAll();
        assertThat(serviceAssuranceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchServiceAssurance() throws Exception {
        // Initialize the database
        serviceAssuranceRepository.saveAndFlush(serviceAssurance);
        serviceAssuranceSearchRepository.save(serviceAssurance);

        // Search the serviceAssurance
        restServiceAssuranceMockMvc.perform(get("/api/_search/service-assurances?query=id:" + serviceAssurance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceAssurance.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceAssurance.class);
        ServiceAssurance serviceAssurance1 = new ServiceAssurance();
        serviceAssurance1.setId(1L);
        ServiceAssurance serviceAssurance2 = new ServiceAssurance();
        serviceAssurance2.setId(serviceAssurance1.getId());
        assertThat(serviceAssurance1).isEqualTo(serviceAssurance2);
        serviceAssurance2.setId(2L);
        assertThat(serviceAssurance1).isNotEqualTo(serviceAssurance2);
        serviceAssurance1.setId(null);
        assertThat(serviceAssurance1).isNotEqualTo(serviceAssurance2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceAssuranceDTO.class);
        ServiceAssuranceDTO serviceAssuranceDTO1 = new ServiceAssuranceDTO();
        serviceAssuranceDTO1.setId(1L);
        ServiceAssuranceDTO serviceAssuranceDTO2 = new ServiceAssuranceDTO();
        assertThat(serviceAssuranceDTO1).isNotEqualTo(serviceAssuranceDTO2);
        serviceAssuranceDTO2.setId(serviceAssuranceDTO1.getId());
        assertThat(serviceAssuranceDTO1).isEqualTo(serviceAssuranceDTO2);
        serviceAssuranceDTO2.setId(2L);
        assertThat(serviceAssuranceDTO1).isNotEqualTo(serviceAssuranceDTO2);
        serviceAssuranceDTO1.setId(null);
        assertThat(serviceAssuranceDTO1).isNotEqualTo(serviceAssuranceDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(serviceAssuranceMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(serviceAssuranceMapper.fromId(null)).isNull();
    }
}
