package com.gaconnecte.auxilium.web.rest;

import com.gaconnecte.auxilium.AuxiliumApp;

import com.gaconnecte.auxilium.domain.VehicleBrand;
import com.gaconnecte.auxilium.repository.VehicleBrandRepository;
import com.gaconnecte.auxilium.service.VehicleBrandService;
import com.gaconnecte.auxilium.repository.search.VehicleBrandSearchRepository;
import com.gaconnecte.auxilium.service.dto.VehicleBrandDTO;
import com.gaconnecte.auxilium.service.mapper.VehicleBrandMapper;
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
 * Test class for the VehicleBrandResource REST controller.
 *
 * @see VehicleBrandResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuxiliumApp.class)
public class VehicleBrandResourceIntTest {

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private VehicleBrandRepository vehicleBrandRepository;

    @Autowired
    private VehicleBrandMapper vehicleBrandMapper;

    @Autowired
    private VehicleBrandService vehicleBrandService;

    @Autowired
    private VehicleBrandSearchRepository vehicleBrandSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restVehicleBrandMockMvc;

    private VehicleBrand vehicleBrand;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        VehicleBrandResource vehicleBrandResource = new VehicleBrandResource(vehicleBrandService);
        this.restVehicleBrandMockMvc = MockMvcBuilders.standaloneSetup(vehicleBrandResource)
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
    public static VehicleBrand createEntity(EntityManager em) {
        VehicleBrand vehicleBrand = new VehicleBrand()
            .label(DEFAULT_LABEL)
            .active(DEFAULT_ACTIVE);
        return vehicleBrand;
    }

    @Before
    public void initTest() {
        vehicleBrandSearchRepository.deleteAll();
        vehicleBrand = createEntity(em);
    }

    @Test
    @Transactional
    public void createVehicleBrand() throws Exception {
        int databaseSizeBeforeCreate = vehicleBrandRepository.findAll().size();

        // Create the VehicleBrand
        VehicleBrandDTO vehicleBrandDTO = vehicleBrandMapper.toDto(vehicleBrand);
        restVehicleBrandMockMvc.perform(post("/api/vehicle-brands")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleBrandDTO)))
            .andExpect(status().isCreated());

        // Validate the VehicleBrand in the database
        List<VehicleBrand> vehicleBrandList = vehicleBrandRepository.findAll();
        assertThat(vehicleBrandList).hasSize(databaseSizeBeforeCreate + 1);
        VehicleBrand testVehicleBrand = vehicleBrandList.get(vehicleBrandList.size() - 1);
        assertThat(testVehicleBrand.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testVehicleBrand.isActive()).isEqualTo(DEFAULT_ACTIVE);

        // Validate the VehicleBrand in Elasticsearch
        VehicleBrand vehicleBrandEs = vehicleBrandSearchRepository.findOne(testVehicleBrand.getId());
        assertThat(vehicleBrandEs).isEqualToComparingFieldByField(testVehicleBrand);
    }

    @Test
    @Transactional
    public void createVehicleBrandWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vehicleBrandRepository.findAll().size();

        // Create the VehicleBrand with an existing ID
        vehicleBrand.setId(1L);
        VehicleBrandDTO vehicleBrandDTO = vehicleBrandMapper.toDto(vehicleBrand);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVehicleBrandMockMvc.perform(post("/api/vehicle-brands")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleBrandDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<VehicleBrand> vehicleBrandList = vehicleBrandRepository.findAll();
        assertThat(vehicleBrandList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllVehicleBrands() throws Exception {
        // Initialize the database
        vehicleBrandRepository.saveAndFlush(vehicleBrand);

        // Get all the vehicleBrandList
        restVehicleBrandMockMvc.perform(get("/api/vehicle-brands?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vehicleBrand.getId().intValue())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void getVehicleBrand() throws Exception {
        // Initialize the database
        vehicleBrandRepository.saveAndFlush(vehicleBrand);

        // Get the vehicleBrand
        restVehicleBrandMockMvc.perform(get("/api/vehicle-brands/{id}", vehicleBrand.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(vehicleBrand.getId().intValue()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingVehicleBrand() throws Exception {
        // Get the vehicleBrand
        restVehicleBrandMockMvc.perform(get("/api/vehicle-brands/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVehicleBrand() throws Exception {
        // Initialize the database
        vehicleBrandRepository.saveAndFlush(vehicleBrand);
        vehicleBrandSearchRepository.save(vehicleBrand);
        int databaseSizeBeforeUpdate = vehicleBrandRepository.findAll().size();

        // Update the vehicleBrand
        VehicleBrand updatedVehicleBrand = vehicleBrandRepository.findOne(vehicleBrand.getId());
        updatedVehicleBrand
            .label(UPDATED_LABEL)
            .active(UPDATED_ACTIVE);
        VehicleBrandDTO vehicleBrandDTO = vehicleBrandMapper.toDto(updatedVehicleBrand);

        restVehicleBrandMockMvc.perform(put("/api/vehicle-brands")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleBrandDTO)))
            .andExpect(status().isOk());

        // Validate the VehicleBrand in the database
        List<VehicleBrand> vehicleBrandList = vehicleBrandRepository.findAll();
        assertThat(vehicleBrandList).hasSize(databaseSizeBeforeUpdate);
        VehicleBrand testVehicleBrand = vehicleBrandList.get(vehicleBrandList.size() - 1);
        assertThat(testVehicleBrand.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testVehicleBrand.isActive()).isEqualTo(UPDATED_ACTIVE);

        // Validate the VehicleBrand in Elasticsearch
        VehicleBrand vehicleBrandEs = vehicleBrandSearchRepository.findOne(testVehicleBrand.getId());
        assertThat(vehicleBrandEs).isEqualToComparingFieldByField(testVehicleBrand);
    }

    @Test
    @Transactional
    public void updateNonExistingVehicleBrand() throws Exception {
        int databaseSizeBeforeUpdate = vehicleBrandRepository.findAll().size();

        // Create the VehicleBrand
        VehicleBrandDTO vehicleBrandDTO = vehicleBrandMapper.toDto(vehicleBrand);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restVehicleBrandMockMvc.perform(put("/api/vehicle-brands")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleBrandDTO)))
            .andExpect(status().isCreated());

        // Validate the VehicleBrand in the database
        List<VehicleBrand> vehicleBrandList = vehicleBrandRepository.findAll();
        assertThat(vehicleBrandList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteVehicleBrand() throws Exception {
        // Initialize the database
        vehicleBrandRepository.saveAndFlush(vehicleBrand);
        vehicleBrandSearchRepository.save(vehicleBrand);
        int databaseSizeBeforeDelete = vehicleBrandRepository.findAll().size();

        // Get the vehicleBrand
        restVehicleBrandMockMvc.perform(delete("/api/vehicle-brands/{id}", vehicleBrand.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean vehicleBrandExistsInEs = vehicleBrandSearchRepository.exists(vehicleBrand.getId());
        assertThat(vehicleBrandExistsInEs).isFalse();

        // Validate the database is empty
        List<VehicleBrand> vehicleBrandList = vehicleBrandRepository.findAll();
        assertThat(vehicleBrandList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchVehicleBrand() throws Exception {
        // Initialize the database
        vehicleBrandRepository.saveAndFlush(vehicleBrand);
        vehicleBrandSearchRepository.save(vehicleBrand);

        // Search the vehicleBrand
        restVehicleBrandMockMvc.perform(get("/api/_search/vehicle-brands?query=id:" + vehicleBrand.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vehicleBrand.getId().intValue())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VehicleBrand.class);
        VehicleBrand vehicleBrand1 = new VehicleBrand();
        vehicleBrand1.setId(1L);
        VehicleBrand vehicleBrand2 = new VehicleBrand();
        vehicleBrand2.setId(vehicleBrand1.getId());
        assertThat(vehicleBrand1).isEqualTo(vehicleBrand2);
        vehicleBrand2.setId(2L);
        assertThat(vehicleBrand1).isNotEqualTo(vehicleBrand2);
        vehicleBrand1.setId(null);
        assertThat(vehicleBrand1).isNotEqualTo(vehicleBrand2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VehicleBrandDTO.class);
        VehicleBrandDTO vehicleBrandDTO1 = new VehicleBrandDTO();
        vehicleBrandDTO1.setId(1L);
        VehicleBrandDTO vehicleBrandDTO2 = new VehicleBrandDTO();
        assertThat(vehicleBrandDTO1).isNotEqualTo(vehicleBrandDTO2);
        vehicleBrandDTO2.setId(vehicleBrandDTO1.getId());
        assertThat(vehicleBrandDTO1).isEqualTo(vehicleBrandDTO2);
        vehicleBrandDTO2.setId(2L);
        assertThat(vehicleBrandDTO1).isNotEqualTo(vehicleBrandDTO2);
        vehicleBrandDTO1.setId(null);
        assertThat(vehicleBrandDTO1).isNotEqualTo(vehicleBrandDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(vehicleBrandMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(vehicleBrandMapper.fromId(null)).isNull();
    }
}
