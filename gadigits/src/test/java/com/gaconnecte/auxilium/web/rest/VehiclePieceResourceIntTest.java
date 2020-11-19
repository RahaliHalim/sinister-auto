package com.gaconnecte.auxilium.web.rest;

import com.gaconnecte.auxilium.AuxiliumApp;

import com.gaconnecte.auxilium.domain.VehiclePiece;
import com.gaconnecte.auxilium.repository.VehiclePieceRepository;
import com.gaconnecte.auxilium.service.VehiclePieceService;
import com.gaconnecte.auxilium.repository.search.VehiclePieceSearchRepository;
import com.gaconnecte.auxilium.service.dto.VehiclePieceDTO;
import com.gaconnecte.auxilium.service.mapper.VehiclePieceMapper;
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
 * Test class for the VehiclePieceResource REST controller.
 *
 * @see VehiclePieceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuxiliumApp.class)
public class VehiclePieceResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final String DEFAULT_SOURCE = "AAAAAAAAAA";
    private static final String UPDATED_SOURCE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final Boolean DEFAULT_VETUSTE = false;
    private static final Boolean UPDATED_VETUSTE = true;

    private static final Integer DEFAULT_GRAPHIC_AREA_ID = 1;
    private static final Integer UPDATED_GRAPHIC_AREA_ID = 2;

    private static final Integer DEFAULT_FUNCTIONAL_GROUP_ID = 1;
    private static final Integer UPDATED_FUNCTIONAL_GROUP_ID = 2;

    private static final Integer DEFAULT_NATURE_ID = 1;
    private static final Integer UPDATED_NATURE_ID = 2;

    @Autowired
    private VehiclePieceRepository vehiclePieceRepository;

    @Autowired
    private VehiclePieceMapper vehiclePieceMapper;

    @Autowired
    private VehiclePieceService vehiclePieceService;

    @Autowired
    private VehiclePieceSearchRepository vehiclePieceSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restVehiclePieceMockMvc;

    private VehiclePiece vehiclePiece;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        VehiclePieceResource vehiclePieceResource = new VehiclePieceResource(vehiclePieceService);
        this.restVehiclePieceMockMvc = MockMvcBuilders.standaloneSetup(vehiclePieceResource)
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
    public static VehiclePiece createEntity(EntityManager em) {
        VehiclePiece vehiclePiece = new VehiclePiece()
            .code(DEFAULT_CODE)
            .label(DEFAULT_LABEL)
            .source(DEFAULT_SOURCE)
            .active(DEFAULT_ACTIVE)
            .vetuste(DEFAULT_VETUSTE)
            .graphicAreaId(DEFAULT_GRAPHIC_AREA_ID)
            .functionalGroupId(DEFAULT_FUNCTIONAL_GROUP_ID)
            .natureId(DEFAULT_NATURE_ID);
        return vehiclePiece;
    }

    @Before
    public void initTest() {
        vehiclePieceSearchRepository.deleteAll();
        vehiclePiece = createEntity(em);
    }

    @Test
    @Transactional
    public void createVehiclePiece() throws Exception {
        int databaseSizeBeforeCreate = vehiclePieceRepository.findAll().size();

        // Create the VehiclePiece
        VehiclePieceDTO vehiclePieceDTO = vehiclePieceMapper.toDto(vehiclePiece);
        restVehiclePieceMockMvc.perform(post("/api/vehicle-pieces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehiclePieceDTO)))
            .andExpect(status().isCreated());

        // Validate the VehiclePiece in the database
        List<VehiclePiece> vehiclePieceList = vehiclePieceRepository.findAll();
        assertThat(vehiclePieceList).hasSize(databaseSizeBeforeCreate + 1);
        VehiclePiece testVehiclePiece = vehiclePieceList.get(vehiclePieceList.size() - 1);
        assertThat(testVehiclePiece.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testVehiclePiece.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testVehiclePiece.getSource()).isEqualTo(DEFAULT_SOURCE);
        assertThat(testVehiclePiece.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testVehiclePiece.isVetuste()).isEqualTo(DEFAULT_VETUSTE);
        assertThat(testVehiclePiece.getGraphicAreaId()).isEqualTo(DEFAULT_GRAPHIC_AREA_ID);
        assertThat(testVehiclePiece.getFunctionalGroupId()).isEqualTo(DEFAULT_FUNCTIONAL_GROUP_ID);
        assertThat(testVehiclePiece.getNatureId()).isEqualTo(DEFAULT_NATURE_ID);

        // Validate the VehiclePiece in Elasticsearch
        VehiclePiece vehiclePieceEs = vehiclePieceSearchRepository.findOne(testVehiclePiece.getId());
        assertThat(vehiclePieceEs).isEqualToComparingFieldByField(testVehiclePiece);
    }

    @Test
    @Transactional
    public void createVehiclePieceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vehiclePieceRepository.findAll().size();

        // Create the VehiclePiece with an existing ID
        vehiclePiece.setId(1L);
        VehiclePieceDTO vehiclePieceDTO = vehiclePieceMapper.toDto(vehiclePiece);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVehiclePieceMockMvc.perform(post("/api/vehicle-pieces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehiclePieceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<VehiclePiece> vehiclePieceList = vehiclePieceRepository.findAll();
        assertThat(vehiclePieceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllVehiclePieces() throws Exception {
        // Initialize the database
        vehiclePieceRepository.saveAndFlush(vehiclePiece);

        // Get all the vehiclePieceList
        restVehiclePieceMockMvc.perform(get("/api/vehicle-pieces?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vehiclePiece.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].vetuste").value(hasItem(DEFAULT_VETUSTE.booleanValue())))
            .andExpect(jsonPath("$.[*].graphicAreaId").value(hasItem(DEFAULT_GRAPHIC_AREA_ID)))
            .andExpect(jsonPath("$.[*].functionalGroupId").value(hasItem(DEFAULT_FUNCTIONAL_GROUP_ID)))
            .andExpect(jsonPath("$.[*].natureId").value(hasItem(DEFAULT_NATURE_ID)));
    }

    @Test
    @Transactional
    public void getVehiclePiece() throws Exception {
        // Initialize the database
        vehiclePieceRepository.saveAndFlush(vehiclePiece);

        // Get the vehiclePiece
        restVehiclePieceMockMvc.perform(get("/api/vehicle-pieces/{id}", vehiclePiece.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(vehiclePiece.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()))
            .andExpect(jsonPath("$.source").value(DEFAULT_SOURCE.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.vetuste").value(DEFAULT_VETUSTE.booleanValue()))
            .andExpect(jsonPath("$.graphicAreaId").value(DEFAULT_GRAPHIC_AREA_ID))
            .andExpect(jsonPath("$.functionalGroupId").value(DEFAULT_FUNCTIONAL_GROUP_ID))
            .andExpect(jsonPath("$.natureId").value(DEFAULT_NATURE_ID));
    }

    @Test
    @Transactional
    public void getNonExistingVehiclePiece() throws Exception {
        // Get the vehiclePiece
        restVehiclePieceMockMvc.perform(get("/api/vehicle-pieces/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVehiclePiece() throws Exception {
        // Initialize the database
        vehiclePieceRepository.saveAndFlush(vehiclePiece);
        vehiclePieceSearchRepository.save(vehiclePiece);
        int databaseSizeBeforeUpdate = vehiclePieceRepository.findAll().size();

        // Update the vehiclePiece
        VehiclePiece updatedVehiclePiece = vehiclePieceRepository.findOne(vehiclePiece.getId());
        updatedVehiclePiece
            .code(UPDATED_CODE)
            .label(UPDATED_LABEL)
            .source(UPDATED_SOURCE)
            .active(UPDATED_ACTIVE)
            .vetuste(UPDATED_VETUSTE)
            .graphicAreaId(UPDATED_GRAPHIC_AREA_ID)
            .functionalGroupId(UPDATED_FUNCTIONAL_GROUP_ID)
            .natureId(UPDATED_NATURE_ID);
        VehiclePieceDTO vehiclePieceDTO = vehiclePieceMapper.toDto(updatedVehiclePiece);

        restVehiclePieceMockMvc.perform(put("/api/vehicle-pieces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehiclePieceDTO)))
            .andExpect(status().isOk());

        // Validate the VehiclePiece in the database
        List<VehiclePiece> vehiclePieceList = vehiclePieceRepository.findAll();
        assertThat(vehiclePieceList).hasSize(databaseSizeBeforeUpdate);
        VehiclePiece testVehiclePiece = vehiclePieceList.get(vehiclePieceList.size() - 1);
        assertThat(testVehiclePiece.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testVehiclePiece.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testVehiclePiece.getSource()).isEqualTo(UPDATED_SOURCE);
        assertThat(testVehiclePiece.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testVehiclePiece.isVetuste()).isEqualTo(UPDATED_VETUSTE);
        assertThat(testVehiclePiece.getGraphicAreaId()).isEqualTo(UPDATED_GRAPHIC_AREA_ID);
        assertThat(testVehiclePiece.getFunctionalGroupId()).isEqualTo(UPDATED_FUNCTIONAL_GROUP_ID);
        assertThat(testVehiclePiece.getNatureId()).isEqualTo(UPDATED_NATURE_ID);

        // Validate the VehiclePiece in Elasticsearch
        VehiclePiece vehiclePieceEs = vehiclePieceSearchRepository.findOne(testVehiclePiece.getId());
        assertThat(vehiclePieceEs).isEqualToComparingFieldByField(testVehiclePiece);
    }

    @Test
    @Transactional
    public void updateNonExistingVehiclePiece() throws Exception {
        int databaseSizeBeforeUpdate = vehiclePieceRepository.findAll().size();

        // Create the VehiclePiece
        VehiclePieceDTO vehiclePieceDTO = vehiclePieceMapper.toDto(vehiclePiece);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restVehiclePieceMockMvc.perform(put("/api/vehicle-pieces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehiclePieceDTO)))
            .andExpect(status().isCreated());

        // Validate the VehiclePiece in the database
        List<VehiclePiece> vehiclePieceList = vehiclePieceRepository.findAll();
        assertThat(vehiclePieceList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteVehiclePiece() throws Exception {
        // Initialize the database
        vehiclePieceRepository.saveAndFlush(vehiclePiece);
        vehiclePieceSearchRepository.save(vehiclePiece);
        int databaseSizeBeforeDelete = vehiclePieceRepository.findAll().size();

        // Get the vehiclePiece
        restVehiclePieceMockMvc.perform(delete("/api/vehicle-pieces/{id}", vehiclePiece.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean vehiclePieceExistsInEs = vehiclePieceSearchRepository.exists(vehiclePiece.getId());
        assertThat(vehiclePieceExistsInEs).isFalse();

        // Validate the database is empty
        List<VehiclePiece> vehiclePieceList = vehiclePieceRepository.findAll();
        assertThat(vehiclePieceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchVehiclePiece() throws Exception {
        // Initialize the database
        vehiclePieceRepository.saveAndFlush(vehiclePiece);
        vehiclePieceSearchRepository.save(vehiclePiece);

        // Search the vehiclePiece
        restVehiclePieceMockMvc.perform(get("/api/_search/vehicle-pieces?query=id:" + vehiclePiece.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vehiclePiece.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].vetuste").value(hasItem(DEFAULT_VETUSTE.booleanValue())))
            .andExpect(jsonPath("$.[*].graphicAreaId").value(hasItem(DEFAULT_GRAPHIC_AREA_ID)))
            .andExpect(jsonPath("$.[*].functionalGroupId").value(hasItem(DEFAULT_FUNCTIONAL_GROUP_ID)))
            .andExpect(jsonPath("$.[*].natureId").value(hasItem(DEFAULT_NATURE_ID)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VehiclePiece.class);
        VehiclePiece vehiclePiece1 = new VehiclePiece();
        vehiclePiece1.setId(1L);
        VehiclePiece vehiclePiece2 = new VehiclePiece();
        vehiclePiece2.setId(vehiclePiece1.getId());
        assertThat(vehiclePiece1).isEqualTo(vehiclePiece2);
        vehiclePiece2.setId(2L);
        assertThat(vehiclePiece1).isNotEqualTo(vehiclePiece2);
        vehiclePiece1.setId(null);
        assertThat(vehiclePiece1).isNotEqualTo(vehiclePiece2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VehiclePieceDTO.class);
        VehiclePieceDTO vehiclePieceDTO1 = new VehiclePieceDTO();
        vehiclePieceDTO1.setId(1L);
        VehiclePieceDTO vehiclePieceDTO2 = new VehiclePieceDTO();
        assertThat(vehiclePieceDTO1).isNotEqualTo(vehiclePieceDTO2);
        vehiclePieceDTO2.setId(vehiclePieceDTO1.getId());
        assertThat(vehiclePieceDTO1).isEqualTo(vehiclePieceDTO2);
        vehiclePieceDTO2.setId(2L);
        assertThat(vehiclePieceDTO1).isNotEqualTo(vehiclePieceDTO2);
        vehiclePieceDTO1.setId(null);
        assertThat(vehiclePieceDTO1).isNotEqualTo(vehiclePieceDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(vehiclePieceMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(vehiclePieceMapper.fromId(null)).isNull();
    }
}
