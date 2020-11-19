package com.gaconnecte.auxilium.web.rest;

import com.gaconnecte.auxilium.AuxiliumApp;

import com.gaconnecte.auxilium.domain.RaisonAssistance;
import com.gaconnecte.auxilium.repository.RaisonAssistanceRepository;
import com.gaconnecte.auxilium.service.RaisonAssistanceService;
import com.gaconnecte.auxilium.repository.search.RaisonAssistanceSearchRepository;
import com.gaconnecte.auxilium.service.dto.RaisonAssistanceDTO;
import com.gaconnecte.auxilium.service.mapper.RaisonAssistanceMapper;
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
 * Test class for the RaisonAssistanceResource REST controller.
 *
 * @see RaisonAssistanceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuxiliumApp.class)
public class RaisonAssistanceResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final LocalDate DEFAULT_CREATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_CREATION_USER_ID = 1L;
    private static final Long UPDATED_CREATION_USER_ID = 2L;

    @Autowired
    private RaisonAssistanceRepository raisonAssistanceRepository;

    @Autowired
    private RaisonAssistanceMapper raisonAssistanceMapper;

    @Autowired
    private RaisonAssistanceService raisonAssistanceService;

    @Autowired
    private RaisonAssistanceSearchRepository raisonAssistanceSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRaisonAssistanceMockMvc;

    private RaisonAssistance raisonAssistance;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RaisonAssistanceResource raisonAssistanceResource = new RaisonAssistanceResource(raisonAssistanceService);
        this.restRaisonAssistanceMockMvc = MockMvcBuilders.standaloneSetup(raisonAssistanceResource)
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
    public static RaisonAssistance createEntity(EntityManager em) {
        RaisonAssistance raisonAssistance = new RaisonAssistance()
            .code(DEFAULT_CODE)
            .label(DEFAULT_LABEL)
            .active(DEFAULT_ACTIVE)
            .creationDate(DEFAULT_CREATION_DATE)
            .creationUserId(DEFAULT_CREATION_USER_ID);
        return raisonAssistance;
    }

    @Before
    public void initTest() {
        raisonAssistanceSearchRepository.deleteAll();
        raisonAssistance = createEntity(em);
    }

    @Test
    @Transactional
    public void createRaisonAssistance() throws Exception {
        int databaseSizeBeforeCreate = raisonAssistanceRepository.findAll().size();

        // Create the RaisonAssistance
        RaisonAssistanceDTO raisonAssistanceDTO = raisonAssistanceMapper.toDto(raisonAssistance);
        restRaisonAssistanceMockMvc.perform(post("/api/raison-assistances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(raisonAssistanceDTO)))
            .andExpect(status().isCreated());

        // Validate the RaisonAssistance in the database
        List<RaisonAssistance> raisonAssistanceList = raisonAssistanceRepository.findAll();
        assertThat(raisonAssistanceList).hasSize(databaseSizeBeforeCreate + 1);
        RaisonAssistance testRaisonAssistance = raisonAssistanceList.get(raisonAssistanceList.size() - 1);
        assertThat(testRaisonAssistance.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testRaisonAssistance.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testRaisonAssistance.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testRaisonAssistance.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testRaisonAssistance.getCreationUserId()).isEqualTo(DEFAULT_CREATION_USER_ID);

        // Validate the RaisonAssistance in Elasticsearch
        RaisonAssistance raisonAssistanceEs = raisonAssistanceSearchRepository.findOne(testRaisonAssistance.getId());
        assertThat(raisonAssistanceEs).isEqualToComparingFieldByField(testRaisonAssistance);
    }

    @Test
    @Transactional
    public void createRaisonAssistanceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = raisonAssistanceRepository.findAll().size();

        // Create the RaisonAssistance with an existing ID
        raisonAssistance.setId(1L);
        RaisonAssistanceDTO raisonAssistanceDTO = raisonAssistanceMapper.toDto(raisonAssistance);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRaisonAssistanceMockMvc.perform(post("/api/raison-assistances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(raisonAssistanceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<RaisonAssistance> raisonAssistanceList = raisonAssistanceRepository.findAll();
        assertThat(raisonAssistanceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllRaisonAssistances() throws Exception {
        // Initialize the database
        raisonAssistanceRepository.saveAndFlush(raisonAssistance);

        // Get all the raisonAssistanceList
        restRaisonAssistanceMockMvc.perform(get("/api/raison-assistances?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(raisonAssistance.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].creationUserId").value(hasItem(DEFAULT_CREATION_USER_ID.intValue())));
    }

    @Test
    @Transactional
    public void getRaisonAssistance() throws Exception {
        // Initialize the database
        raisonAssistanceRepository.saveAndFlush(raisonAssistance);

        // Get the raisonAssistance
        restRaisonAssistanceMockMvc.perform(get("/api/raison-assistances/{id}", raisonAssistance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(raisonAssistance.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.creationUserId").value(DEFAULT_CREATION_USER_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingRaisonAssistance() throws Exception {
        // Get the raisonAssistance
        restRaisonAssistanceMockMvc.perform(get("/api/raison-assistances/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRaisonAssistance() throws Exception {
        // Initialize the database
        raisonAssistanceRepository.saveAndFlush(raisonAssistance);
        raisonAssistanceSearchRepository.save(raisonAssistance);
        int databaseSizeBeforeUpdate = raisonAssistanceRepository.findAll().size();

        // Update the raisonAssistance
        RaisonAssistance updatedRaisonAssistance = raisonAssistanceRepository.findOne(raisonAssistance.getId());
        updatedRaisonAssistance
            .code(UPDATED_CODE)
            .label(UPDATED_LABEL)
            .active(UPDATED_ACTIVE)
            .creationDate(UPDATED_CREATION_DATE)
            .creationUserId(UPDATED_CREATION_USER_ID);
        RaisonAssistanceDTO raisonAssistanceDTO = raisonAssistanceMapper.toDto(updatedRaisonAssistance);

        restRaisonAssistanceMockMvc.perform(put("/api/raison-assistances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(raisonAssistanceDTO)))
            .andExpect(status().isOk());

        // Validate the RaisonAssistance in the database
        List<RaisonAssistance> raisonAssistanceList = raisonAssistanceRepository.findAll();
        assertThat(raisonAssistanceList).hasSize(databaseSizeBeforeUpdate);
        RaisonAssistance testRaisonAssistance = raisonAssistanceList.get(raisonAssistanceList.size() - 1);
        assertThat(testRaisonAssistance.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testRaisonAssistance.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testRaisonAssistance.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testRaisonAssistance.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testRaisonAssistance.getCreationUserId()).isEqualTo(UPDATED_CREATION_USER_ID);

        // Validate the RaisonAssistance in Elasticsearch
        RaisonAssistance raisonAssistanceEs = raisonAssistanceSearchRepository.findOne(testRaisonAssistance.getId());
        assertThat(raisonAssistanceEs).isEqualToComparingFieldByField(testRaisonAssistance);
    }

    @Test
    @Transactional
    public void updateNonExistingRaisonAssistance() throws Exception {
        int databaseSizeBeforeUpdate = raisonAssistanceRepository.findAll().size();

        // Create the RaisonAssistance
        RaisonAssistanceDTO raisonAssistanceDTO = raisonAssistanceMapper.toDto(raisonAssistance);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRaisonAssistanceMockMvc.perform(put("/api/raison-assistances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(raisonAssistanceDTO)))
            .andExpect(status().isCreated());

        // Validate the RaisonAssistance in the database
        List<RaisonAssistance> raisonAssistanceList = raisonAssistanceRepository.findAll();
        assertThat(raisonAssistanceList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRaisonAssistance() throws Exception {
        // Initialize the database
        raisonAssistanceRepository.saveAndFlush(raisonAssistance);
        raisonAssistanceSearchRepository.save(raisonAssistance);
        int databaseSizeBeforeDelete = raisonAssistanceRepository.findAll().size();

        // Get the raisonAssistance
        restRaisonAssistanceMockMvc.perform(delete("/api/raison-assistances/{id}", raisonAssistance.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean raisonAssistanceExistsInEs = raisonAssistanceSearchRepository.exists(raisonAssistance.getId());
        assertThat(raisonAssistanceExistsInEs).isFalse();

        // Validate the database is empty
        List<RaisonAssistance> raisonAssistanceList = raisonAssistanceRepository.findAll();
        assertThat(raisonAssistanceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchRaisonAssistance() throws Exception {
        // Initialize the database
        raisonAssistanceRepository.saveAndFlush(raisonAssistance);
        raisonAssistanceSearchRepository.save(raisonAssistance);

        // Search the raisonAssistance
        restRaisonAssistanceMockMvc.perform(get("/api/_search/raison-assistances?query=id:" + raisonAssistance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(raisonAssistance.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].creationUserId").value(hasItem(DEFAULT_CREATION_USER_ID.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RaisonAssistance.class);
        RaisonAssistance raisonAssistance1 = new RaisonAssistance();
        raisonAssistance1.setId(1L);
        RaisonAssistance raisonAssistance2 = new RaisonAssistance();
        raisonAssistance2.setId(raisonAssistance1.getId());
        assertThat(raisonAssistance1).isEqualTo(raisonAssistance2);
        raisonAssistance2.setId(2L);
        assertThat(raisonAssistance1).isNotEqualTo(raisonAssistance2);
        raisonAssistance1.setId(null);
        assertThat(raisonAssistance1).isNotEqualTo(raisonAssistance2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RaisonAssistanceDTO.class);
        RaisonAssistanceDTO raisonAssistanceDTO1 = new RaisonAssistanceDTO();
        raisonAssistanceDTO1.setId(1L);
        RaisonAssistanceDTO raisonAssistanceDTO2 = new RaisonAssistanceDTO();
        assertThat(raisonAssistanceDTO1).isNotEqualTo(raisonAssistanceDTO2);
        raisonAssistanceDTO2.setId(raisonAssistanceDTO1.getId());
        assertThat(raisonAssistanceDTO1).isEqualTo(raisonAssistanceDTO2);
        raisonAssistanceDTO2.setId(2L);
        assertThat(raisonAssistanceDTO1).isNotEqualTo(raisonAssistanceDTO2);
        raisonAssistanceDTO1.setId(null);
        assertThat(raisonAssistanceDTO1).isNotEqualTo(raisonAssistanceDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(raisonAssistanceMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(raisonAssistanceMapper.fromId(null)).isNull();
    }
}
