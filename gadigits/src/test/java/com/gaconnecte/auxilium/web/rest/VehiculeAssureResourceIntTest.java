package com.gaconnecte.auxilium.web.rest;

import com.gaconnecte.auxilium.AuxiliumApp;

import com.gaconnecte.auxilium.domain.VehiculeAssure;
import com.gaconnecte.auxilium.repository.VehiculeAssureRepository;
import com.gaconnecte.auxilium.service.VehiculeAssureService;
import com.gaconnecte.auxilium.repository.search.VehiculeAssureSearchRepository;
import com.gaconnecte.auxilium.service.dto.VehiculeAssureDTO;
import com.gaconnecte.auxilium.service.mapper.VehiculeAssureMapper;
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
 * Test class for the VehiculeAssureResource REST controller.
 *
 * @see VehiculeAssureResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuxiliumApp.class)
public class VehiculeAssureResourceIntTest {

    private static final String DEFAULT_IMMATRICULATION_VEHICULE = "2630TU268";
    private static final String UPDATED_IMMATRICULATION_VEHICULE = "5440TU608";

    private static final Integer DEFAULT_PUISSANCE = 99999999;
    private static final Integer UPDATED_PUISSANCE = 99999998;

    private static final String DEFAULT_NUMERO_CHASSIS = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_CHASSIS = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_P_CIRCULATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_P_CIRCULATION = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_PUISSANCE_FISCALE = 1L;
    private static final Long UPDATED_PUISSANCE_FISCALE = 2L;

    @Autowired
    private VehiculeAssureRepository vehiculeAssureRepository;

    @Autowired
    private VehiculeAssureMapper vehiculeAssureMapper;

    @Autowired
    private VehiculeAssureService vehiculeAssureService;

    @Autowired
    private VehiculeAssureSearchRepository vehiculeAssureSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restVehiculeAssureMockMvc;

    private VehiculeAssure vehiculeAssure;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        VehiculeAssureResource vehiculeAssureResource = new VehiculeAssureResource(vehiculeAssureService);
        this.restVehiculeAssureMockMvc = MockMvcBuilders.standaloneSetup(vehiculeAssureResource)
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
    public static VehiculeAssure createEntity(EntityManager em) {
        VehiculeAssure vehiculeAssure = new VehiculeAssure()
            .immatriculationVehicule(DEFAULT_IMMATRICULATION_VEHICULE)
            .puissance(DEFAULT_PUISSANCE)
            .numeroChassis(DEFAULT_NUMERO_CHASSIS)
            .datePCirculation(DEFAULT_DATE_P_CIRCULATION);
            /*
             * .puissanceFiscale(DEFAULT_PUISSANCE_FISCALE)
             */
        return vehiculeAssure;
    }

    @Before
    public void initTest() {
        vehiculeAssureSearchRepository.deleteAll();
        vehiculeAssure = createEntity(em);
    }

    @Test
    @Transactional
    public void createVehiculeAssure() throws Exception {
        int databaseSizeBeforeCreate = vehiculeAssureRepository.findAll().size();

        // Create the VehiculeAssure
        VehiculeAssureDTO vehiculeAssureDTO = vehiculeAssureMapper.toDto(vehiculeAssure);
        restVehiculeAssureMockMvc.perform(post("/api/vehicule-assures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehiculeAssureDTO)))
            .andExpect(status().isCreated());

        // Validate the VehiculeAssure in the database
        List<VehiculeAssure> vehiculeAssureList = vehiculeAssureRepository.findAll();
        assertThat(vehiculeAssureList).hasSize(databaseSizeBeforeCreate + 1);
        VehiculeAssure testVehiculeAssure = vehiculeAssureList.get(vehiculeAssureList.size() - 1);
        assertThat(testVehiculeAssure.getImmatriculationVehicule()).isEqualTo(DEFAULT_IMMATRICULATION_VEHICULE);
        assertThat(testVehiculeAssure.getPuissance()).isEqualTo(DEFAULT_PUISSANCE);
        assertThat(testVehiculeAssure.getNumeroChassis()).isEqualTo(DEFAULT_NUMERO_CHASSIS);
        assertThat(testVehiculeAssure.getDatePCirculation()).isEqualTo(DEFAULT_DATE_P_CIRCULATION);
       /* assertThat(testVehiculeAssure.getPuissanceFiscale()).isEqualTo(DEFAULT_PUISSANCE_FISCALE);*/

        // Validate the VehiculeAssure in Elasticsearch
        VehiculeAssure vehiculeAssureEs = vehiculeAssureSearchRepository.findOne(testVehiculeAssure.getId());
        assertThat(vehiculeAssureEs).isEqualToComparingFieldByField(testVehiculeAssure);
    }

    @Test
    @Transactional
    public void createVehiculeAssureWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vehiculeAssureRepository.findAll().size();

        // Create the VehiculeAssure with an existing ID
        vehiculeAssure.setId(1L);
        VehiculeAssureDTO vehiculeAssureDTO = vehiculeAssureMapper.toDto(vehiculeAssure);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVehiculeAssureMockMvc.perform(post("/api/vehicule-assures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehiculeAssureDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<VehiculeAssure> vehiculeAssureList = vehiculeAssureRepository.findAll();
        assertThat(vehiculeAssureList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkImmatriculationVehiculeIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehiculeAssureRepository.findAll().size();
        // set the field null
        vehiculeAssure.setImmatriculationVehicule(null);

        // Create the VehiculeAssure, which fails.
        VehiculeAssureDTO vehiculeAssureDTO = vehiculeAssureMapper.toDto(vehiculeAssure);

        restVehiculeAssureMockMvc.perform(post("/api/vehicule-assures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehiculeAssureDTO)))
            .andExpect(status().isBadRequest());

        List<VehiculeAssure> vehiculeAssureList = vehiculeAssureRepository.findAll();
        assertThat(vehiculeAssureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumeroChassisIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehiculeAssureRepository.findAll().size();
        // set the field null
        vehiculeAssure.setNumeroChassis(null);

        // Create the VehiculeAssure, which fails.
        VehiculeAssureDTO vehiculeAssureDTO = vehiculeAssureMapper.toDto(vehiculeAssure);

        restVehiculeAssureMockMvc.perform(post("/api/vehicule-assures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehiculeAssureDTO)))
            .andExpect(status().isBadRequest());

        List<VehiculeAssure> vehiculeAssureList = vehiculeAssureRepository.findAll();
        assertThat(vehiculeAssureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVehiculeAssures() throws Exception {
        // Initialize the database
        vehiculeAssureRepository.saveAndFlush(vehiculeAssure);

        // Get all the vehiculeAssureList
        restVehiculeAssureMockMvc.perform(get("/api/vehicule-assures?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vehiculeAssure.getId().intValue())))
            .andExpect(jsonPath("$.[*].immatriculationVehicule").value(hasItem(DEFAULT_IMMATRICULATION_VEHICULE.toString())))
            .andExpect(jsonPath("$.[*].puissance").value(hasItem(DEFAULT_PUISSANCE)))
            .andExpect(jsonPath("$.[*].numeroChassis").value(hasItem(DEFAULT_NUMERO_CHASSIS.toString())))
            .andExpect(jsonPath("$.[*].datePCirculation").value(hasItem(DEFAULT_DATE_P_CIRCULATION.toString())));
            /*.andExpect(jsonPath("$.[*].puissanceFiscale").value(hasItem(DEFAULT_PUISSANCE_FISCALE.intValue())))*/
    }

    @Test
    @Transactional
    public void getVehiculeAssure() throws Exception {
        // Initialize the database
        vehiculeAssureRepository.saveAndFlush(vehiculeAssure);

        // Get the vehiculeAssure
        restVehiculeAssureMockMvc.perform(get("/api/vehicule-assures/{id}", vehiculeAssure.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(vehiculeAssure.getId().intValue()))
            .andExpect(jsonPath("$.immatriculationVehicule").value(DEFAULT_IMMATRICULATION_VEHICULE.toString()))
            .andExpect(jsonPath("$.puissance").value(DEFAULT_PUISSANCE))
            .andExpect(jsonPath("$.numeroChassis").value(DEFAULT_NUMERO_CHASSIS.toString()))
            .andExpect(jsonPath("$.datePCirculation").value(DEFAULT_DATE_P_CIRCULATION.toString()));
            /*.andExpect(jsonPath("$.puissanceFiscale").value(DEFAULT_PUISSANCE_FISCALE.intValue())) */
    }

    @Test
    @Transactional
    public void getNonExistingVehiculeAssure() throws Exception {
        // Get the vehiculeAssure
        restVehiculeAssureMockMvc.perform(get("/api/vehicule-assures/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVehiculeAssure() throws Exception {
        // Initialize the database
        vehiculeAssureRepository.saveAndFlush(vehiculeAssure);
        vehiculeAssureSearchRepository.save(vehiculeAssure);
        int databaseSizeBeforeUpdate = vehiculeAssureRepository.findAll().size();

        // Update the vehiculeAssure
        VehiculeAssure updatedVehiculeAssure = vehiculeAssureRepository.findOne(vehiculeAssure.getId());
        updatedVehiculeAssure
            .immatriculationVehicule(UPDATED_IMMATRICULATION_VEHICULE)
            .puissance(UPDATED_PUISSANCE)
            .numeroChassis(UPDATED_NUMERO_CHASSIS)
            .datePCirculation(UPDATED_DATE_P_CIRCULATION);
            /*.puissanceFiscale(UPDATED_PUISSANCE_FISCALE)*/
        VehiculeAssureDTO vehiculeAssureDTO = vehiculeAssureMapper.toDto(updatedVehiculeAssure);

        restVehiculeAssureMockMvc.perform(put("/api/vehicule-assures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehiculeAssureDTO)))
            .andExpect(status().isOk());

        // Validate the VehiculeAssure in the database
        List<VehiculeAssure> vehiculeAssureList = vehiculeAssureRepository.findAll();
        assertThat(vehiculeAssureList).hasSize(databaseSizeBeforeUpdate);
        VehiculeAssure testVehiculeAssure = vehiculeAssureList.get(vehiculeAssureList.size() - 1);
        assertThat(testVehiculeAssure.getImmatriculationVehicule()).isEqualTo(UPDATED_IMMATRICULATION_VEHICULE);
        assertThat(testVehiculeAssure.getPuissance()).isEqualTo(UPDATED_PUISSANCE);
        assertThat(testVehiculeAssure.getNumeroChassis()).isEqualTo(UPDATED_NUMERO_CHASSIS);
        assertThat(testVehiculeAssure.getDatePCirculation()).isEqualTo(UPDATED_DATE_P_CIRCULATION);
        /*assertThat(testVehiculeAssure.getPuissanceFiscale()).isEqualTo(UPDATED_PUISSANCE_FISCALE);*/

        // Validate the VehiculeAssure in Elasticsearch
        VehiculeAssure vehiculeAssureEs = vehiculeAssureSearchRepository.findOne(testVehiculeAssure.getId());
        assertThat(vehiculeAssureEs).isEqualToComparingFieldByField(testVehiculeAssure);
    }

    @Test
    @Transactional
    public void updateNonExistingVehiculeAssure() throws Exception {
        int databaseSizeBeforeUpdate = vehiculeAssureRepository.findAll().size();

        // Create the VehiculeAssure
        VehiculeAssureDTO vehiculeAssureDTO = vehiculeAssureMapper.toDto(vehiculeAssure);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restVehiculeAssureMockMvc.perform(put("/api/vehicule-assures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehiculeAssureDTO)))
            .andExpect(status().isCreated());

        // Validate the VehiculeAssure in the database
        List<VehiculeAssure> vehiculeAssureList = vehiculeAssureRepository.findAll();
        assertThat(vehiculeAssureList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteVehiculeAssure() throws Exception {
        // Initialize the database
        vehiculeAssureRepository.saveAndFlush(vehiculeAssure);
        vehiculeAssureSearchRepository.save(vehiculeAssure);
        int databaseSizeBeforeDelete = vehiculeAssureRepository.findAll().size();

        // Get the vehiculeAssure
        restVehiculeAssureMockMvc.perform(delete("/api/vehicule-assures/{id}", vehiculeAssure.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean vehiculeAssureExistsInEs = vehiculeAssureSearchRepository.exists(vehiculeAssure.getId());
        assertThat(vehiculeAssureExistsInEs).isFalse();

        // Validate the database is empty
        List<VehiculeAssure> vehiculeAssureList = vehiculeAssureRepository.findAll();
        assertThat(vehiculeAssureList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchVehiculeAssure() throws Exception {
        // Initialize the database
        vehiculeAssureRepository.saveAndFlush(vehiculeAssure);
        vehiculeAssureSearchRepository.save(vehiculeAssure);

        // Search the vehiculeAssure
        restVehiculeAssureMockMvc.perform(get("/api/_search/vehicule-assures?query=id:" + vehiculeAssure.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vehiculeAssure.getId().intValue())))
            .andExpect(jsonPath("$.[*].immatriculationVehicule").value(hasItem(DEFAULT_IMMATRICULATION_VEHICULE.toString())))
            .andExpect(jsonPath("$.[*].puissance").value(hasItem(DEFAULT_PUISSANCE)))
            .andExpect(jsonPath("$.[*].numeroChassis").value(hasItem(DEFAULT_NUMERO_CHASSIS.toString())))
            .andExpect(jsonPath("$.[*].datePCirculation").value(hasItem(DEFAULT_DATE_P_CIRCULATION.toString())));
            /*.andExpect(jsonPath("$.[*].puissanceFiscale").value(hasItem(DEFAULT_PUISSANCE_FISCALE.intValue())))*/
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VehiculeAssure.class);
        VehiculeAssure vehiculeAssure1 = new VehiculeAssure();
        vehiculeAssure1.setId(1L);
        VehiculeAssure vehiculeAssure2 = new VehiculeAssure();
        vehiculeAssure2.setId(vehiculeAssure1.getId());
        assertThat(vehiculeAssure1).isEqualTo(vehiculeAssure2);
        vehiculeAssure2.setId(2L);
        assertThat(vehiculeAssure1).isNotEqualTo(vehiculeAssure2);
        vehiculeAssure1.setId(null);
        assertThat(vehiculeAssure1).isNotEqualTo(vehiculeAssure2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VehiculeAssureDTO.class);
        VehiculeAssureDTO vehiculeAssureDTO1 = new VehiculeAssureDTO();
        vehiculeAssureDTO1.setId(1L);
        VehiculeAssureDTO vehiculeAssureDTO2 = new VehiculeAssureDTO();
        assertThat(vehiculeAssureDTO1).isNotEqualTo(vehiculeAssureDTO2);
        vehiculeAssureDTO2.setId(vehiculeAssureDTO1.getId());
        assertThat(vehiculeAssureDTO1).isEqualTo(vehiculeAssureDTO2);
        vehiculeAssureDTO2.setId(2L);
        assertThat(vehiculeAssureDTO1).isNotEqualTo(vehiculeAssureDTO2);
        vehiculeAssureDTO1.setId(null);
        assertThat(vehiculeAssureDTO1).isNotEqualTo(vehiculeAssureDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(vehiculeAssureMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(vehiculeAssureMapper.fromId(null)).isNull();
    }
}
