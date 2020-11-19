package com.gaconnecte.auxilium.web.rest;

import com.gaconnecte.auxilium.AuxiliumApp;

import com.gaconnecte.auxilium.domain.VehicleBrandModel;
import com.gaconnecte.auxilium.repository.VehicleBrandModelRepository;
import com.gaconnecte.auxilium.service.VehicleBrandModelService;
import com.gaconnecte.auxilium.repository.search.VehicleBrandModelSearchRepository;
import com.gaconnecte.auxilium.service.dto.VehicleBrandModelDTO;
import com.gaconnecte.auxilium.service.mapper.VehicleBrandModelMapper;
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
 * Test class for the VehicleBrandModelResource REST controller.
 *
 * @see VehicleBrandModelResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuxiliumApp.class)
public class VehicleBrandModelResourceIntTest {

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private VehicleBrandModelRepository vehicleBrandModelRepository;

    @Autowired
    private VehicleBrandModelMapper vehicleBrandModelMapper;

    @Autowired
    private VehicleBrandModelService vehicleBrandModelService;

    @Autowired
    private VehicleBrandModelSearchRepository vehicleBrandModelSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restVehicleBrandModelMockMvc;

    private VehicleBrandModel vehicleBrandModel;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        VehicleBrandModelResource vehicleBrandModelResource = new VehicleBrandModelResource(vehicleBrandModelService);
        this.restVehicleBrandModelMockMvc = MockMvcBuilders.standaloneSetup(vehicleBrandModelResource)
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
    public static VehicleBrandModel createEntity(EntityManager em) {
        VehicleBrandModel vehicleBrandModel = new VehicleBrandModel()
            .label(DEFAULT_LABEL)
            .active(DEFAULT_ACTIVE);
        return vehicleBrandModel;
    }

    @Before
    public void initTest() {
        vehicleBrandModelSearchRepository.deleteAll();
        vehicleBrandModel = createEntity(em);
    }

    @Test
    @Transactional
    public void createVehicleBrandModel() throws Exception {
        int databaseSizeBeforeCreate = vehicleBrandModelRepository.findAll().size();

        // Create the VehicleBrandModel
        VehicleBrandModelDTO vehicleBrandModelDTO = vehicleBrandModelMapper.toDto(vehicleBrandModel);
        restVehicleBrandModelMockMvc.perform(post("/api/vehicle-brand-models")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleBrandModelDTO)))
            .andExpect(status().isCreated());

        // Validate the VehicleBrandModel in the database
        List<VehicleBrandModel> vehicleBrandModelList = vehicleBrandModelRepository.findAll();
        assertThat(vehicleBrandModelList).hasSize(databaseSizeBeforeCreate + 1);
        VehicleBrandModel testVehicleBrandModel = vehicleBrandModelList.get(vehicleBrandModelList.size() - 1);
        assertThat(testVehicleBrandModel.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testVehicleBrandModel.isActive()).isEqualTo(DEFAULT_ACTIVE);

        // Validate the VehicleBrandModel in Elasticsearch
        VehicleBrandModel vehicleBrandModelEs = vehicleBrandModelSearchRepository.findOne(testVehicleBrandModel.getId());
        assertThat(vehicleBrandModelEs).isEqualToComparingFieldByField(testVehicleBrandModel);
    }

    @Test
    @Transactional
    public void createVehicleBrandModelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vehicleBrandModelRepository.findAll().size();

        // Create the VehicleBrandModel with an existing ID
        vehicleBrandModel.setId(1L);
        VehicleBrandModelDTO vehicleBrandModelDTO = vehicleBrandModelMapper.toDto(vehicleBrandModel);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVehicleBrandModelMockMvc.perform(post("/api/vehicle-brand-models")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleBrandModelDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<VehicleBrandModel> vehicleBrandModelList = vehicleBrandModelRepository.findAll();
        assertThat(vehicleBrandModelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllVehicleBrandModels() throws Exception {
        // Initialize the database
        vehicleBrandModelRepository.saveAndFlush(vehicleBrandModel);

        // Get all the vehicleBrandModelList
        restVehicleBrandModelMockMvc.perform(get("/api/vehicle-brand-models?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vehicleBrandModel.getId().intValue())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void getVehicleBrandModel() throws Exception {
        // Initialize the database
        vehicleBrandModelRepository.saveAndFlush(vehicleBrandModel);

        // Get the vehicleBrandModel
        restVehicleBrandModelMockMvc.perform(get("/api/vehicle-brand-models/{id}", vehicleBrandModel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(vehicleBrandModel.getId().intValue()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingVehicleBrandModel() throws Exception {
        // Get the vehicleBrandModel
        restVehicleBrandModelMockMvc.perform(get("/api/vehicle-brand-models/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVehicleBrandModel() throws Exception {
        // Initialize the database
        vehicleBrandModelRepository.saveAndFlush(vehicleBrandModel);
        vehicleBrandModelSearchRepository.save(vehicleBrandModel);
        int databaseSizeBeforeUpdate = vehicleBrandModelRepository.findAll().size();

        // Update the vehicleBrandModel
        VehicleBrandModel updatedVehicleBrandModel = vehicleBrandModelRepository.findOne(vehicleBrandModel.getId());
        updatedVehicleBrandModel
            .label(UPDATED_LABEL)
            .active(UPDATED_ACTIVE);
        VehicleBrandModelDTO vehicleBrandModelDTO = vehicleBrandModelMapper.toDto(updatedVehicleBrandModel);

        restVehicleBrandModelMockMvc.perform(put("/api/vehicle-brand-models")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleBrandModelDTO)))
            .andExpect(status().isOk());

        // Validate the VehicleBrandModel in the database
        List<VehicleBrandModel> vehicleBrandModelList = vehicleBrandModelRepository.findAll();
        assertThat(vehicleBrandModelList).hasSize(databaseSizeBeforeUpdate);
        VehicleBrandModel testVehicleBrandModel = vehicleBrandModelList.get(vehicleBrandModelList.size() - 1);
        assertThat(testVehicleBrandModel.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testVehicleBrandModel.isActive()).isEqualTo(UPDATED_ACTIVE);

        // Validate the VehicleBrandModel in Elasticsearch
        VehicleBrandModel vehicleBrandModelEs = vehicleBrandModelSearchRepository.findOne(testVehicleBrandModel.getId());
        assertThat(vehicleBrandModelEs).isEqualToComparingFieldByField(testVehicleBrandModel);
    }

    @Test
    @Transactional
    public void updateNonExistingVehicleBrandModel() throws Exception {
        int databaseSizeBeforeUpdate = vehicleBrandModelRepository.findAll().size();

        // Create the VehicleBrandModel
        VehicleBrandModelDTO vehicleBrandModelDTO = vehicleBrandModelMapper.toDto(vehicleBrandModel);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restVehicleBrandModelMockMvc.perform(put("/api/vehicle-brand-models")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleBrandModelDTO)))
            .andExpect(status().isCreated());

        // Validate the VehicleBrandModel in the database
        List<VehicleBrandModel> vehicleBrandModelList = vehicleBrandModelRepository.findAll();
        assertThat(vehicleBrandModelList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteVehicleBrandModel() throws Exception {
        // Initialize the database
        vehicleBrandModelRepository.saveAndFlush(vehicleBrandModel);
        vehicleBrandModelSearchRepository.save(vehicleBrandModel);
        int databaseSizeBeforeDelete = vehicleBrandModelRepository.findAll().size();

        // Get the vehicleBrandModel
        restVehicleBrandModelMockMvc.perform(delete("/api/vehicle-brand-models/{id}", vehicleBrandModel.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean vehicleBrandModelExistsInEs = vehicleBrandModelSearchRepository.exists(vehicleBrandModel.getId());
        assertThat(vehicleBrandModelExistsInEs).isFalse();

        // Validate the database is empty
        List<VehicleBrandModel> vehicleBrandModelList = vehicleBrandModelRepository.findAll();
        assertThat(vehicleBrandModelList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchVehicleBrandModel() throws Exception {
        // Initialize the database
        vehicleBrandModelRepository.saveAndFlush(vehicleBrandModel);
        vehicleBrandModelSearchRepository.save(vehicleBrandModel);

        // Search the vehicleBrandModel
        restVehicleBrandModelMockMvc.perform(get("/api/_search/vehicle-brand-models?query=id:" + vehicleBrandModel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vehicleBrandModel.getId().intValue())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VehicleBrandModel.class);
        VehicleBrandModel vehicleBrandModel1 = new VehicleBrandModel();
        vehicleBrandModel1.setId(1L);
        VehicleBrandModel vehicleBrandModel2 = new VehicleBrandModel();
        vehicleBrandModel2.setId(vehicleBrandModel1.getId());
        assertThat(vehicleBrandModel1).isEqualTo(vehicleBrandModel2);
        vehicleBrandModel2.setId(2L);
        assertThat(vehicleBrandModel1).isNotEqualTo(vehicleBrandModel2);
        vehicleBrandModel1.setId(null);
        assertThat(vehicleBrandModel1).isNotEqualTo(vehicleBrandModel2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VehicleBrandModelDTO.class);
        VehicleBrandModelDTO vehicleBrandModelDTO1 = new VehicleBrandModelDTO();
        vehicleBrandModelDTO1.setId(1L);
        VehicleBrandModelDTO vehicleBrandModelDTO2 = new VehicleBrandModelDTO();
        assertThat(vehicleBrandModelDTO1).isNotEqualTo(vehicleBrandModelDTO2);
        vehicleBrandModelDTO2.setId(vehicleBrandModelDTO1.getId());
        assertThat(vehicleBrandModelDTO1).isEqualTo(vehicleBrandModelDTO2);
        vehicleBrandModelDTO2.setId(2L);
        assertThat(vehicleBrandModelDTO1).isNotEqualTo(vehicleBrandModelDTO2);
        vehicleBrandModelDTO1.setId(null);
        assertThat(vehicleBrandModelDTO1).isNotEqualTo(vehicleBrandModelDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(vehicleBrandModelMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(vehicleBrandModelMapper.fromId(null)).isNull();
    }
}
