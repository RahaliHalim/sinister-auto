package com.gaconnecte.auxilium.web.rest;

import com.gaconnecte.auxilium.AuxiliumApp;

import com.gaconnecte.auxilium.domain.VehicleUsage;
import com.gaconnecte.auxilium.repository.VehicleUsageRepository;
import com.gaconnecte.auxilium.service.VehicleUsageService;
import com.gaconnecte.auxilium.repository.search.VehicleUsageSearchRepository;
import com.gaconnecte.auxilium.service.dto.VehicleUsageDTO;
import com.gaconnecte.auxilium.service.mapper.VehicleUsageMapper;
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
 * Test class for the VehicleUsageResource REST controller.
 *
 * @see VehicleUsageResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuxiliumApp.class)
public class VehicleUsageResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private VehicleUsageRepository vehicleUsageRepository;

    @Autowired
    private VehicleUsageMapper vehicleUsageMapper;

    @Autowired
    private VehicleUsageService vehicleUsageService;

    @Autowired
    private VehicleUsageSearchRepository vehicleUsageSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restVehicleUsageMockMvc;

    private VehicleUsage vehicleUsage;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        VehicleUsageResource vehicleUsageResource = new VehicleUsageResource(vehicleUsageService);
        this.restVehicleUsageMockMvc = MockMvcBuilders.standaloneSetup(vehicleUsageResource)
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
    public static VehicleUsage createEntity(EntityManager em) {
        VehicleUsage vehicleUsage = new VehicleUsage()
            .code(DEFAULT_CODE)
            .label(DEFAULT_LABEL)
            .active(DEFAULT_ACTIVE);
        return vehicleUsage;
    }

    @Before
    public void initTest() {
        vehicleUsageSearchRepository.deleteAll();
        vehicleUsage = createEntity(em);
    }

    @Test
    @Transactional
    public void createVehicleUsage() throws Exception {
        int databaseSizeBeforeCreate = vehicleUsageRepository.findAll().size();

        // Create the VehicleUsage
        VehicleUsageDTO vehicleUsageDTO = vehicleUsageMapper.toDto(vehicleUsage);
        restVehicleUsageMockMvc.perform(post("/api/vehicle-usages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleUsageDTO)))
            .andExpect(status().isCreated());

        // Validate the VehicleUsage in the database
        List<VehicleUsage> vehicleUsageList = vehicleUsageRepository.findAll();
        assertThat(vehicleUsageList).hasSize(databaseSizeBeforeCreate + 1);
        VehicleUsage testVehicleUsage = vehicleUsageList.get(vehicleUsageList.size() - 1);
        assertThat(testVehicleUsage.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testVehicleUsage.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testVehicleUsage.isActive()).isEqualTo(DEFAULT_ACTIVE);

        // Validate the VehicleUsage in Elasticsearch
        VehicleUsage vehicleUsageEs = vehicleUsageSearchRepository.findOne(testVehicleUsage.getId());
        assertThat(vehicleUsageEs).isEqualToComparingFieldByField(testVehicleUsage);
    }

    @Test
    @Transactional
    public void createVehicleUsageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vehicleUsageRepository.findAll().size();

        // Create the VehicleUsage with an existing ID
        vehicleUsage.setId(1L);
        VehicleUsageDTO vehicleUsageDTO = vehicleUsageMapper.toDto(vehicleUsage);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVehicleUsageMockMvc.perform(post("/api/vehicle-usages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleUsageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<VehicleUsage> vehicleUsageList = vehicleUsageRepository.findAll();
        assertThat(vehicleUsageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllVehicleUsages() throws Exception {
        // Initialize the database
        vehicleUsageRepository.saveAndFlush(vehicleUsage);

        // Get all the vehicleUsageList
        restVehicleUsageMockMvc.perform(get("/api/vehicle-usages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vehicleUsage.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void getVehicleUsage() throws Exception {
        // Initialize the database
        vehicleUsageRepository.saveAndFlush(vehicleUsage);

        // Get the vehicleUsage
        restVehicleUsageMockMvc.perform(get("/api/vehicle-usages/{id}", vehicleUsage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(vehicleUsage.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingVehicleUsage() throws Exception {
        // Get the vehicleUsage
        restVehicleUsageMockMvc.perform(get("/api/vehicle-usages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVehicleUsage() throws Exception {
        // Initialize the database
        vehicleUsageRepository.saveAndFlush(vehicleUsage);
        vehicleUsageSearchRepository.save(vehicleUsage);
        int databaseSizeBeforeUpdate = vehicleUsageRepository.findAll().size();

        // Update the vehicleUsage
        VehicleUsage updatedVehicleUsage = vehicleUsageRepository.findOne(vehicleUsage.getId());
        updatedVehicleUsage
            .code(UPDATED_CODE)
            .label(UPDATED_LABEL)
            .active(UPDATED_ACTIVE);
        VehicleUsageDTO vehicleUsageDTO = vehicleUsageMapper.toDto(updatedVehicleUsage);

        restVehicleUsageMockMvc.perform(put("/api/vehicle-usages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleUsageDTO)))
            .andExpect(status().isOk());

        // Validate the VehicleUsage in the database
        List<VehicleUsage> vehicleUsageList = vehicleUsageRepository.findAll();
        assertThat(vehicleUsageList).hasSize(databaseSizeBeforeUpdate);
        VehicleUsage testVehicleUsage = vehicleUsageList.get(vehicleUsageList.size() - 1);
        assertThat(testVehicleUsage.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testVehicleUsage.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testVehicleUsage.isActive()).isEqualTo(UPDATED_ACTIVE);

        // Validate the VehicleUsage in Elasticsearch
        VehicleUsage vehicleUsageEs = vehicleUsageSearchRepository.findOne(testVehicleUsage.getId());
        assertThat(vehicleUsageEs).isEqualToComparingFieldByField(testVehicleUsage);
    }

    @Test
    @Transactional
    public void updateNonExistingVehicleUsage() throws Exception {
        int databaseSizeBeforeUpdate = vehicleUsageRepository.findAll().size();

        // Create the VehicleUsage
        VehicleUsageDTO vehicleUsageDTO = vehicleUsageMapper.toDto(vehicleUsage);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restVehicleUsageMockMvc.perform(put("/api/vehicle-usages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleUsageDTO)))
            .andExpect(status().isCreated());

        // Validate the VehicleUsage in the database
        List<VehicleUsage> vehicleUsageList = vehicleUsageRepository.findAll();
        assertThat(vehicleUsageList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteVehicleUsage() throws Exception {
        // Initialize the database
        vehicleUsageRepository.saveAndFlush(vehicleUsage);
        vehicleUsageSearchRepository.save(vehicleUsage);
        int databaseSizeBeforeDelete = vehicleUsageRepository.findAll().size();

        // Get the vehicleUsage
        restVehicleUsageMockMvc.perform(delete("/api/vehicle-usages/{id}", vehicleUsage.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean vehicleUsageExistsInEs = vehicleUsageSearchRepository.exists(vehicleUsage.getId());
        assertThat(vehicleUsageExistsInEs).isFalse();

        // Validate the database is empty
        List<VehicleUsage> vehicleUsageList = vehicleUsageRepository.findAll();
        assertThat(vehicleUsageList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchVehicleUsage() throws Exception {
        // Initialize the database
        vehicleUsageRepository.saveAndFlush(vehicleUsage);
        vehicleUsageSearchRepository.save(vehicleUsage);

        // Search the vehicleUsage
        restVehicleUsageMockMvc.perform(get("/api/_search/vehicle-usages?query=id:" + vehicleUsage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vehicleUsage.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VehicleUsage.class);
        VehicleUsage vehicleUsage1 = new VehicleUsage();
        vehicleUsage1.setId(1L);
        VehicleUsage vehicleUsage2 = new VehicleUsage();
        vehicleUsage2.setId(vehicleUsage1.getId());
        assertThat(vehicleUsage1).isEqualTo(vehicleUsage2);
        vehicleUsage2.setId(2L);
        assertThat(vehicleUsage1).isNotEqualTo(vehicleUsage2);
        vehicleUsage1.setId(null);
        assertThat(vehicleUsage1).isNotEqualTo(vehicleUsage2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VehicleUsageDTO.class);
        VehicleUsageDTO vehicleUsageDTO1 = new VehicleUsageDTO();
        vehicleUsageDTO1.setId(1L);
        VehicleUsageDTO vehicleUsageDTO2 = new VehicleUsageDTO();
        assertThat(vehicleUsageDTO1).isNotEqualTo(vehicleUsageDTO2);
        vehicleUsageDTO2.setId(vehicleUsageDTO1.getId());
        assertThat(vehicleUsageDTO1).isEqualTo(vehicleUsageDTO2);
        vehicleUsageDTO2.setId(2L);
        assertThat(vehicleUsageDTO1).isNotEqualTo(vehicleUsageDTO2);
        vehicleUsageDTO1.setId(null);
        assertThat(vehicleUsageDTO1).isNotEqualTo(vehicleUsageDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(vehicleUsageMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(vehicleUsageMapper.fromId(null)).isNull();
    }
}
