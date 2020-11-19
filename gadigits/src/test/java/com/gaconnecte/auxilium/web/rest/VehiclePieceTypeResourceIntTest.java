package com.gaconnecte.auxilium.web.rest;

import com.gaconnecte.auxilium.AuxiliumApp;

import com.gaconnecte.auxilium.domain.VehiclePieceType;
import com.gaconnecte.auxilium.repository.VehiclePieceTypeRepository;
import com.gaconnecte.auxilium.service.VehiclePieceTypeService;
import com.gaconnecte.auxilium.repository.search.VehiclePieceTypeSearchRepository;
import com.gaconnecte.auxilium.service.dto.VehiclePieceTypeDTO;
import com.gaconnecte.auxilium.service.mapper.VehiclePieceTypeMapper;
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
 * Test class for the VehiclePieceTypeResource REST controller.
 *
 * @see VehiclePieceTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuxiliumApp.class)
public class VehiclePieceTypeResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    @Autowired
    private VehiclePieceTypeRepository vehiclePieceTypeRepository;

    @Autowired
    private VehiclePieceTypeMapper vehiclePieceTypeMapper;

    @Autowired
    private VehiclePieceTypeService vehiclePieceTypeService;

    @Autowired
    private VehiclePieceTypeSearchRepository vehiclePieceTypeSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restVehiclePieceTypeMockMvc;

    private VehiclePieceType vehiclePieceType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        VehiclePieceTypeResource vehiclePieceTypeResource = new VehiclePieceTypeResource(vehiclePieceTypeService);
        this.restVehiclePieceTypeMockMvc = MockMvcBuilders.standaloneSetup(vehiclePieceTypeResource)
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
    public static VehiclePieceType createEntity(EntityManager em) {
        VehiclePieceType vehiclePieceType = new VehiclePieceType()
            .code(DEFAULT_CODE)
            .label(DEFAULT_LABEL);
        return vehiclePieceType;
    }

    @Before
    public void initTest() {
        vehiclePieceTypeSearchRepository.deleteAll();
        vehiclePieceType = createEntity(em);
    }

    @Test
    @Transactional
    public void createVehiclePieceType() throws Exception {
        int databaseSizeBeforeCreate = vehiclePieceTypeRepository.findAll().size();

        // Create the VehiclePieceType
        VehiclePieceTypeDTO vehiclePieceTypeDTO = vehiclePieceTypeMapper.toDto(vehiclePieceType);
        restVehiclePieceTypeMockMvc.perform(post("/api/vehicle-piece-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehiclePieceTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the VehiclePieceType in the database
        List<VehiclePieceType> vehiclePieceTypeList = vehiclePieceTypeRepository.findAll();
        assertThat(vehiclePieceTypeList).hasSize(databaseSizeBeforeCreate + 1);
        VehiclePieceType testVehiclePieceType = vehiclePieceTypeList.get(vehiclePieceTypeList.size() - 1);
        assertThat(testVehiclePieceType.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testVehiclePieceType.getLabel()).isEqualTo(DEFAULT_LABEL);

        // Validate the VehiclePieceType in Elasticsearch
        VehiclePieceType vehiclePieceTypeEs = vehiclePieceTypeSearchRepository.findOne(testVehiclePieceType.getId());
        assertThat(vehiclePieceTypeEs).isEqualToComparingFieldByField(testVehiclePieceType);
    }

    @Test
    @Transactional
    public void createVehiclePieceTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vehiclePieceTypeRepository.findAll().size();

        // Create the VehiclePieceType with an existing ID
        vehiclePieceType.setId(1L);
        VehiclePieceTypeDTO vehiclePieceTypeDTO = vehiclePieceTypeMapper.toDto(vehiclePieceType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVehiclePieceTypeMockMvc.perform(post("/api/vehicle-piece-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehiclePieceTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<VehiclePieceType> vehiclePieceTypeList = vehiclePieceTypeRepository.findAll();
        assertThat(vehiclePieceTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllVehiclePieceTypes() throws Exception {
        // Initialize the database
        vehiclePieceTypeRepository.saveAndFlush(vehiclePieceType);

        // Get all the vehiclePieceTypeList
        restVehiclePieceTypeMockMvc.perform(get("/api/vehicle-piece-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vehiclePieceType.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())));
    }

    @Test
    @Transactional
    public void getVehiclePieceType() throws Exception {
        // Initialize the database
        vehiclePieceTypeRepository.saveAndFlush(vehiclePieceType);

        // Get the vehiclePieceType
        restVehiclePieceTypeMockMvc.perform(get("/api/vehicle-piece-types/{id}", vehiclePieceType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(vehiclePieceType.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingVehiclePieceType() throws Exception {
        // Get the vehiclePieceType
        restVehiclePieceTypeMockMvc.perform(get("/api/vehicle-piece-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVehiclePieceType() throws Exception {
        // Initialize the database
        vehiclePieceTypeRepository.saveAndFlush(vehiclePieceType);
        vehiclePieceTypeSearchRepository.save(vehiclePieceType);
        int databaseSizeBeforeUpdate = vehiclePieceTypeRepository.findAll().size();

        // Update the vehiclePieceType
        VehiclePieceType updatedVehiclePieceType = vehiclePieceTypeRepository.findOne(vehiclePieceType.getId());
        updatedVehiclePieceType
            .code(UPDATED_CODE)
            .label(UPDATED_LABEL);
        VehiclePieceTypeDTO vehiclePieceTypeDTO = vehiclePieceTypeMapper.toDto(updatedVehiclePieceType);

        restVehiclePieceTypeMockMvc.perform(put("/api/vehicle-piece-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehiclePieceTypeDTO)))
            .andExpect(status().isOk());

        // Validate the VehiclePieceType in the database
        List<VehiclePieceType> vehiclePieceTypeList = vehiclePieceTypeRepository.findAll();
        assertThat(vehiclePieceTypeList).hasSize(databaseSizeBeforeUpdate);
        VehiclePieceType testVehiclePieceType = vehiclePieceTypeList.get(vehiclePieceTypeList.size() - 1);
        assertThat(testVehiclePieceType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testVehiclePieceType.getLabel()).isEqualTo(UPDATED_LABEL);

        // Validate the VehiclePieceType in Elasticsearch
        VehiclePieceType vehiclePieceTypeEs = vehiclePieceTypeSearchRepository.findOne(testVehiclePieceType.getId());
        assertThat(vehiclePieceTypeEs).isEqualToComparingFieldByField(testVehiclePieceType);
    }

    @Test
    @Transactional
    public void updateNonExistingVehiclePieceType() throws Exception {
        int databaseSizeBeforeUpdate = vehiclePieceTypeRepository.findAll().size();

        // Create the VehiclePieceType
        VehiclePieceTypeDTO vehiclePieceTypeDTO = vehiclePieceTypeMapper.toDto(vehiclePieceType);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restVehiclePieceTypeMockMvc.perform(put("/api/vehicle-piece-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehiclePieceTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the VehiclePieceType in the database
        List<VehiclePieceType> vehiclePieceTypeList = vehiclePieceTypeRepository.findAll();
        assertThat(vehiclePieceTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteVehiclePieceType() throws Exception {
        // Initialize the database
        vehiclePieceTypeRepository.saveAndFlush(vehiclePieceType);
        vehiclePieceTypeSearchRepository.save(vehiclePieceType);
        int databaseSizeBeforeDelete = vehiclePieceTypeRepository.findAll().size();

        // Get the vehiclePieceType
        restVehiclePieceTypeMockMvc.perform(delete("/api/vehicle-piece-types/{id}", vehiclePieceType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean vehiclePieceTypeExistsInEs = vehiclePieceTypeSearchRepository.exists(vehiclePieceType.getId());
        assertThat(vehiclePieceTypeExistsInEs).isFalse();

        // Validate the database is empty
        List<VehiclePieceType> vehiclePieceTypeList = vehiclePieceTypeRepository.findAll();
        assertThat(vehiclePieceTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchVehiclePieceType() throws Exception {
        // Initialize the database
        vehiclePieceTypeRepository.saveAndFlush(vehiclePieceType);
        vehiclePieceTypeSearchRepository.save(vehiclePieceType);

        // Search the vehiclePieceType
        restVehiclePieceTypeMockMvc.perform(get("/api/_search/vehicle-piece-types?query=id:" + vehiclePieceType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vehiclePieceType.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VehiclePieceType.class);
        VehiclePieceType vehiclePieceType1 = new VehiclePieceType();
        vehiclePieceType1.setId(1L);
        VehiclePieceType vehiclePieceType2 = new VehiclePieceType();
        vehiclePieceType2.setId(vehiclePieceType1.getId());
        assertThat(vehiclePieceType1).isEqualTo(vehiclePieceType2);
        vehiclePieceType2.setId(2L);
        assertThat(vehiclePieceType1).isNotEqualTo(vehiclePieceType2);
        vehiclePieceType1.setId(null);
        assertThat(vehiclePieceType1).isNotEqualTo(vehiclePieceType2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VehiclePieceTypeDTO.class);
        VehiclePieceTypeDTO vehiclePieceTypeDTO1 = new VehiclePieceTypeDTO();
        vehiclePieceTypeDTO1.setId(1L);
        VehiclePieceTypeDTO vehiclePieceTypeDTO2 = new VehiclePieceTypeDTO();
        assertThat(vehiclePieceTypeDTO1).isNotEqualTo(vehiclePieceTypeDTO2);
        vehiclePieceTypeDTO2.setId(vehiclePieceTypeDTO1.getId());
        assertThat(vehiclePieceTypeDTO1).isEqualTo(vehiclePieceTypeDTO2);
        vehiclePieceTypeDTO2.setId(2L);
        assertThat(vehiclePieceTypeDTO1).isNotEqualTo(vehiclePieceTypeDTO2);
        vehiclePieceTypeDTO1.setId(null);
        assertThat(vehiclePieceTypeDTO1).isNotEqualTo(vehiclePieceTypeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(vehiclePieceTypeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(vehiclePieceTypeMapper.fromId(null)).isNull();
    }
}
