package com.gaconnecte.auxilium.web.rest;

import com.gaconnecte.auxilium.AuxiliumApp;

import com.gaconnecte.auxilium.domain.Observation;
import com.gaconnecte.auxilium.repository.ObservationRepository;
import com.gaconnecte.auxilium.service.ObservationService;
import com.gaconnecte.auxilium.service.dto.ObservationDTO;
import com.gaconnecte.auxilium.service.mapper.ObservationMapper;
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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.gaconnecte.auxilium.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ObservationResource REST controller.
 *
 * @see ObservationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuxiliumApp.class)
public class ObservationResourceIntTest {

    private static final String DEFAULT_COMMENTAIRE = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTAIRE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Boolean DEFAULT_PRIVE = false;
    private static final Boolean UPDATED_PRIVE = true;

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    @Autowired
    private ObservationRepository observationRepository;

    @Autowired
    private ObservationMapper observationMapper;

    @Autowired
    private ObservationService observationService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restObservationMockMvc;

    private Observation observation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ObservationResource observationResource = new ObservationResource(observationService);
        this.restObservationMockMvc = MockMvcBuilders.standaloneSetup(observationResource)
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
    public static Observation createEntity(EntityManager em) {
        Observation observation = new Observation()
            .commentaire(DEFAULT_COMMENTAIRE)
            .date(DEFAULT_DATE);
        return observation;
    }

    @Before
    public void initTest() {
        observation = createEntity(em);
    }

    @Test
    @Transactional
    public void createObservation() throws Exception {
        int databaseSizeBeforeCreate = observationRepository.findAll().size();

        // Create the Observation
        ObservationDTO observationDTO = observationMapper.toDto(observation);
        restObservationMockMvc.perform(post("/api/observations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(observationDTO)))
            .andExpect(status().isCreated());

        // Validate the Observation in the database
        List<Observation> observationList = observationRepository.findAll();
        assertThat(observationList).hasSize(databaseSizeBeforeCreate + 1);
        Observation testObservation = observationList.get(observationList.size() - 1);
        assertThat(testObservation.getCommentaire()).isEqualTo(DEFAULT_COMMENTAIRE);
        assertThat(testObservation.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testObservation.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createObservationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = observationRepository.findAll().size();

        // Create the Observation with an existing ID
        observation.setId(1L);
        ObservationDTO observationDTO = observationMapper.toDto(observation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restObservationMockMvc.perform(post("/api/observations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(observationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Observation> observationList = observationRepository.findAll();
        assertThat(observationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCommentaireIsRequired() throws Exception {
        int databaseSizeBeforeTest = observationRepository.findAll().size();
        // set the field null
        observation.setCommentaire(null);

        // Create the Observation, which fails.
        ObservationDTO observationDTO = observationMapper.toDto(observation);

        restObservationMockMvc.perform(post("/api/observations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(observationDTO)))
            .andExpect(status().isBadRequest());

        List<Observation> observationList = observationRepository.findAll();
        assertThat(observationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = observationRepository.findAll().size();
        // set the field null
        observation.setDate(null);

        // Create the Observation, which fails.
        ObservationDTO observationDTO = observationMapper.toDto(observation);

        restObservationMockMvc.perform(post("/api/observations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(observationDTO)))
            .andExpect(status().isBadRequest());

        List<Observation> observationList = observationRepository.findAll();
        assertThat(observationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPriveIsRequired() throws Exception {
        int databaseSizeBeforeTest = observationRepository.findAll().size();

        // Create the Observation, which fails.
        ObservationDTO observationDTO = observationMapper.toDto(observation);

        restObservationMockMvc.perform(post("/api/observations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(observationDTO)))
            .andExpect(status().isBadRequest());

        List<Observation> observationList = observationRepository.findAll();
        assertThat(observationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllObservations() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList
        restObservationMockMvc.perform(get("/api/observations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(observation.getId().intValue())))
            .andExpect(jsonPath("$.[*].commentaire").value(hasItem(DEFAULT_COMMENTAIRE.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getObservation() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get the observation
        restObservationMockMvc.perform(get("/api/observations/{id}", observation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(observation.getId().intValue()))
            .andExpect(jsonPath("$.commentaire").value(DEFAULT_COMMENTAIRE.toString()))
            .andExpect(jsonPath("$.date").value(sameInstant(DEFAULT_DATE)))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingObservation() throws Exception {
        // Get the observation
        restObservationMockMvc.perform(get("/api/observations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateObservation() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);
        int databaseSizeBeforeUpdate = observationRepository.findAll().size();

        // Update the observation
        Observation updatedObservation = observationRepository.findOne(observation.getId());
        updatedObservation
            .commentaire(UPDATED_COMMENTAIRE)
            .date(UPDATED_DATE);
        ObservationDTO observationDTO = observationMapper.toDto(updatedObservation);

        restObservationMockMvc.perform(put("/api/observations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(observationDTO)))
            .andExpect(status().isOk());

        // Validate the Observation in the database
        List<Observation> observationList = observationRepository.findAll();
        assertThat(observationList).hasSize(databaseSizeBeforeUpdate);
        Observation testObservation = observationList.get(observationList.size() - 1);
        assertThat(testObservation.getCommentaire()).isEqualTo(UPDATED_COMMENTAIRE);
        assertThat(testObservation.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testObservation.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingObservation() throws Exception {
        int databaseSizeBeforeUpdate = observationRepository.findAll().size();

        // Create the Observation
        ObservationDTO observationDTO = observationMapper.toDto(observation);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restObservationMockMvc.perform(put("/api/observations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(observationDTO)))
            .andExpect(status().isCreated());

        // Validate the Observation in the database
        List<Observation> observationList = observationRepository.findAll();
        assertThat(observationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteObservation() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);
        int databaseSizeBeforeDelete = observationRepository.findAll().size();

        // Get the observation
        restObservationMockMvc.perform(delete("/api/observations/{id}", observation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Observation> observationList = observationRepository.findAll();
        assertThat(observationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Observation.class);
        Observation observation1 = new Observation();
        observation1.setId(1L);
        Observation observation2 = new Observation();
        observation2.setId(observation1.getId());
        assertThat(observation1).isEqualTo(observation2);
        observation2.setId(2L);
        assertThat(observation1).isNotEqualTo(observation2);
        observation1.setId(null);
        assertThat(observation1).isNotEqualTo(observation2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ObservationDTO.class);
        ObservationDTO observationDTO1 = new ObservationDTO();
        observationDTO1.setId(1L);
        ObservationDTO observationDTO2 = new ObservationDTO();
        assertThat(observationDTO1).isNotEqualTo(observationDTO2);
        observationDTO2.setId(observationDTO1.getId());
        assertThat(observationDTO1).isEqualTo(observationDTO2);
        observationDTO2.setId(2L);
        assertThat(observationDTO1).isNotEqualTo(observationDTO2);
        observationDTO1.setId(null);
        assertThat(observationDTO1).isNotEqualTo(observationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(observationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(observationMapper.fromId(null)).isNull();
    }
}
