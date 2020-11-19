package com.gaconnecte.auxilium.web.rest;

import com.gaconnecte.auxilium.AuxiliumApp;

import com.gaconnecte.auxilium.domain.VehicleEnergy;
import com.gaconnecte.auxilium.repository.VehicleEnergyRepository;
import com.gaconnecte.auxilium.service.VehicleEnergyService;
import com.gaconnecte.auxilium.repository.search.VehicleEnergySearchRepository;
import com.gaconnecte.auxilium.service.dto.VehicleEnergyDTO;
import com.gaconnecte.auxilium.service.mapper.VehicleEnergyMapper;
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
 * Test class for the VehicleEnergyResource REST controller.
 *
 * @see VehicleEnergyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuxiliumApp.class)
public class VehicleEnergyResourceIntTest {

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private VehicleEnergyRepository vehicleEnergyRepository;

    @Autowired
    private VehicleEnergyMapper vehicleEnergyMapper;

    @Autowired
    private VehicleEnergyService vehicleEnergyService;

    @Autowired
    private VehicleEnergySearchRepository vehicleEnergySearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restVehicleEnergyMockMvc;

    private VehicleEnergy vehicleEnergy;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        VehicleEnergyResource vehicleEnergyResource = new VehicleEnergyResource(vehicleEnergyService);
        this.restVehicleEnergyMockMvc = MockMvcBuilders.standaloneSetup(vehicleEnergyResource)
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
    public static VehicleEnergy createEntity(EntityManager em) {
        VehicleEnergy vehicleEnergy = new VehicleEnergy()
            .label(DEFAULT_LABEL)
            .active(DEFAULT_ACTIVE);
        return vehicleEnergy;
    }

    @Before
    public void initTest() {
        vehicleEnergySearchRepository.deleteAll();
        vehicleEnergy = createEntity(em);
    }

    @Test
    @Transactional
    public void createVehicleEnergy() throws Exception {
        int databaseSizeBeforeCreate = vehicleEnergyRepository.findAll().size();

        // Create the VehicleEnergy
        VehicleEnergyDTO vehicleEnergyDTO = vehicleEnergyMapper.toDto(vehicleEnergy);
        restVehicleEnergyMockMvc.perform(post("/api/vehicle-energies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleEnergyDTO)))
            .andExpect(status().isCreated());

        // Validate the VehicleEnergy in the database
        List<VehicleEnergy> vehicleEnergyList = vehicleEnergyRepository.findAll();
        assertThat(vehicleEnergyList).hasSize(databaseSizeBeforeCreate + 1);
        VehicleEnergy testVehicleEnergy = vehicleEnergyList.get(vehicleEnergyList.size() - 1);
        assertThat(testVehicleEnergy.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testVehicleEnergy.isActive()).isEqualTo(DEFAULT_ACTIVE);

        // Validate the VehicleEnergy in Elasticsearch
        VehicleEnergy vehicleEnergyEs = vehicleEnergySearchRepository.findOne(testVehicleEnergy.getId());
        assertThat(vehicleEnergyEs).isEqualToComparingFieldByField(testVehicleEnergy);
    }

    @Test
    @Transactional
    public void createVehicleEnergyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vehicleEnergyRepository.findAll().size();

        // Create the VehicleEnergy with an existing ID
        vehicleEnergy.setId(1L);
        VehicleEnergyDTO vehicleEnergyDTO = vehicleEnergyMapper.toDto(vehicleEnergy);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVehicleEnergyMockMvc.perform(post("/api/vehicle-energies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleEnergyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<VehicleEnergy> vehicleEnergyList = vehicleEnergyRepository.findAll();
        assertThat(vehicleEnergyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllVehicleEnergies() throws Exception {
        // Initialize the database
        vehicleEnergyRepository.saveAndFlush(vehicleEnergy);

        // Get all the vehicleEnergyList
        restVehicleEnergyMockMvc.perform(get("/api/vehicle-energies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vehicleEnergy.getId().intValue())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void getVehicleEnergy() throws Exception {
        // Initialize the database
        vehicleEnergyRepository.saveAndFlush(vehicleEnergy);

        // Get the vehicleEnergy
        restVehicleEnergyMockMvc.perform(get("/api/vehicle-energies/{id}", vehicleEnergy.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(vehicleEnergy.getId().intValue()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingVehicleEnergy() throws Exception {
        // Get the vehicleEnergy
        restVehicleEnergyMockMvc.perform(get("/api/vehicle-energies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVehicleEnergy() throws Exception {
        // Initialize the database
        vehicleEnergyRepository.saveAndFlush(vehicleEnergy);
        vehicleEnergySearchRepository.save(vehicleEnergy);
        int databaseSizeBeforeUpdate = vehicleEnergyRepository.findAll().size();

        // Update the vehicleEnergy
        VehicleEnergy updatedVehicleEnergy = vehicleEnergyRepository.findOne(vehicleEnergy.getId());
        updatedVehicleEnergy
            .label(UPDATED_LABEL)
            .active(UPDATED_ACTIVE);
        VehicleEnergyDTO vehicleEnergyDTO = vehicleEnergyMapper.toDto(updatedVehicleEnergy);

        restVehicleEnergyMockMvc.perform(put("/api/vehicle-energies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleEnergyDTO)))
            .andExpect(status().isOk());

        // Validate the VehicleEnergy in the database
        List<VehicleEnergy> vehicleEnergyList = vehicleEnergyRepository.findAll();
        assertThat(vehicleEnergyList).hasSize(databaseSizeBeforeUpdate);
        VehicleEnergy testVehicleEnergy = vehicleEnergyList.get(vehicleEnergyList.size() - 1);
        assertThat(testVehicleEnergy.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testVehicleEnergy.isActive()).isEqualTo(UPDATED_ACTIVE);

        // Validate the VehicleEnergy in Elasticsearch
        VehicleEnergy vehicleEnergyEs = vehicleEnergySearchRepository.findOne(testVehicleEnergy.getId());
        assertThat(vehicleEnergyEs).isEqualToComparingFieldByField(testVehicleEnergy);
    }

    @Test
    @Transactional
    public void updateNonExistingVehicleEnergy() throws Exception {
        int databaseSizeBeforeUpdate = vehicleEnergyRepository.findAll().size();

        // Create the VehicleEnergy
        VehicleEnergyDTO vehicleEnergyDTO = vehicleEnergyMapper.toDto(vehicleEnergy);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restVehicleEnergyMockMvc.perform(put("/api/vehicle-energies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleEnergyDTO)))
            .andExpect(status().isCreated());

        // Validate the VehicleEnergy in the database
        List<VehicleEnergy> vehicleEnergyList = vehicleEnergyRepository.findAll();
        assertThat(vehicleEnergyList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteVehicleEnergy() throws Exception {
        // Initialize the database
        vehicleEnergyRepository.saveAndFlush(vehicleEnergy);
        vehicleEnergySearchRepository.save(vehicleEnergy);
        int databaseSizeBeforeDelete = vehicleEnergyRepository.findAll().size();

        // Get the vehicleEnergy
        restVehicleEnergyMockMvc.perform(delete("/api/vehicle-energies/{id}", vehicleEnergy.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean vehicleEnergyExistsInEs = vehicleEnergySearchRepository.exists(vehicleEnergy.getId());
        assertThat(vehicleEnergyExistsInEs).isFalse();

        // Validate the database is empty
        List<VehicleEnergy> vehicleEnergyList = vehicleEnergyRepository.findAll();
        assertThat(vehicleEnergyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchVehicleEnergy() throws Exception {
        // Initialize the database
        vehicleEnergyRepository.saveAndFlush(vehicleEnergy);
        vehicleEnergySearchRepository.save(vehicleEnergy);

        // Search the vehicleEnergy
        restVehicleEnergyMockMvc.perform(get("/api/_search/vehicle-energies?query=id:" + vehicleEnergy.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vehicleEnergy.getId().intValue())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VehicleEnergy.class);
        VehicleEnergy vehicleEnergy1 = new VehicleEnergy();
        vehicleEnergy1.setId(1L);
        VehicleEnergy vehicleEnergy2 = new VehicleEnergy();
        vehicleEnergy2.setId(vehicleEnergy1.getId());
        assertThat(vehicleEnergy1).isEqualTo(vehicleEnergy2);
        vehicleEnergy2.setId(2L);
        assertThat(vehicleEnergy1).isNotEqualTo(vehicleEnergy2);
        vehicleEnergy1.setId(null);
        assertThat(vehicleEnergy1).isNotEqualTo(vehicleEnergy2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VehicleEnergyDTO.class);
        VehicleEnergyDTO vehicleEnergyDTO1 = new VehicleEnergyDTO();
        vehicleEnergyDTO1.setId(1L);
        VehicleEnergyDTO vehicleEnergyDTO2 = new VehicleEnergyDTO();
        assertThat(vehicleEnergyDTO1).isNotEqualTo(vehicleEnergyDTO2);
        vehicleEnergyDTO2.setId(vehicleEnergyDTO1.getId());
        assertThat(vehicleEnergyDTO1).isEqualTo(vehicleEnergyDTO2);
        vehicleEnergyDTO2.setId(2L);
        assertThat(vehicleEnergyDTO1).isNotEqualTo(vehicleEnergyDTO2);
        vehicleEnergyDTO1.setId(null);
        assertThat(vehicleEnergyDTO1).isNotEqualTo(vehicleEnergyDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(vehicleEnergyMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(vehicleEnergyMapper.fromId(null)).isNull();
    }
}
