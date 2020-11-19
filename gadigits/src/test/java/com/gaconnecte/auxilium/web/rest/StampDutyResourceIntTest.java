package com.gaconnecte.auxilium.web.rest;

import com.gaconnecte.auxilium.AuxiliumApp;

import com.gaconnecte.auxilium.domain.StampDuty;
import com.gaconnecte.auxilium.repository.StampDutyRepository;
import com.gaconnecte.auxilium.service.StampDutyService;
import com.gaconnecte.auxilium.repository.search.StampDutySearchRepository;
import com.gaconnecte.auxilium.service.dto.StampDutyDTO;
import com.gaconnecte.auxilium.service.mapper.StampDutyMapper;
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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the StampDutyResource REST controller.
 *
 * @see StampDutyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuxiliumApp.class)
public class StampDutyResourceIntTest {

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private StampDutyRepository stampDutyRepository;

    @Autowired
    private StampDutyMapper stampDutyMapper;

    @Autowired
    private StampDutyService stampDutyService;

    @Autowired
    private StampDutySearchRepository stampDutySearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restStampDutyMockMvc;

    private StampDuty stampDuty;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        StampDutyResource stampDutyResource = new StampDutyResource(stampDutyService);
        this.restStampDutyMockMvc = MockMvcBuilders.standaloneSetup(stampDutyResource)
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
    public static StampDuty createEntity(EntityManager em) {
        StampDuty stampDuty = new StampDuty()
            .amount(DEFAULT_AMOUNT)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .active(DEFAULT_ACTIVE);
        return stampDuty;
    }

    @Before
    public void initTest() {
        stampDutySearchRepository.deleteAll();
        stampDuty = createEntity(em);
    }

    @Test
    @Transactional
    public void createStampDuty() throws Exception {
        int databaseSizeBeforeCreate = stampDutyRepository.findAll().size();

        // Create the StampDuty
        StampDutyDTO stampDutyDTO = stampDutyMapper.toDto(stampDuty);
        restStampDutyMockMvc.perform(post("/api/stamp-duties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stampDutyDTO)))
            .andExpect(status().isCreated());

        // Validate the StampDuty in the database
        List<StampDuty> stampDutyList = stampDutyRepository.findAll();
        assertThat(stampDutyList).hasSize(databaseSizeBeforeCreate + 1);
        StampDuty testStampDuty = stampDutyList.get(stampDutyList.size() - 1);
        assertThat(testStampDuty.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testStampDuty.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testStampDuty.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testStampDuty.isActive()).isEqualTo(DEFAULT_ACTIVE);

        // Validate the StampDuty in Elasticsearch
        StampDuty stampDutyEs = stampDutySearchRepository.findOne(testStampDuty.getId());
        assertThat(stampDutyEs).isEqualToComparingFieldByField(testStampDuty);
    }

    @Test
    @Transactional
    public void createStampDutyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = stampDutyRepository.findAll().size();

        // Create the StampDuty with an existing ID
        stampDuty.setId(1L);
        StampDutyDTO stampDutyDTO = stampDutyMapper.toDto(stampDuty);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStampDutyMockMvc.perform(post("/api/stamp-duties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stampDutyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<StampDuty> stampDutyList = stampDutyRepository.findAll();
        assertThat(stampDutyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllStampDuties() throws Exception {
        // Initialize the database
        stampDutyRepository.saveAndFlush(stampDuty);

        // Get all the stampDutyList
        restStampDutyMockMvc.perform(get("/api/stamp-duties?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stampDuty.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void getStampDuty() throws Exception {
        // Initialize the database
        stampDutyRepository.saveAndFlush(stampDuty);

        // Get the stampDuty
        restStampDutyMockMvc.perform(get("/api/stamp-duties/{id}", stampDuty.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(stampDuty.getId().intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingStampDuty() throws Exception {
        // Get the stampDuty
        restStampDutyMockMvc.perform(get("/api/stamp-duties/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStampDuty() throws Exception {
        // Initialize the database
        stampDutyRepository.saveAndFlush(stampDuty);
        stampDutySearchRepository.save(stampDuty);
        int databaseSizeBeforeUpdate = stampDutyRepository.findAll().size();

        // Update the stampDuty
        StampDuty updatedStampDuty = stampDutyRepository.findOne(stampDuty.getId());
        updatedStampDuty
            .amount(UPDATED_AMOUNT)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .active(UPDATED_ACTIVE);
        StampDutyDTO stampDutyDTO = stampDutyMapper.toDto(updatedStampDuty);

        restStampDutyMockMvc.perform(put("/api/stamp-duties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stampDutyDTO)))
            .andExpect(status().isOk());

        // Validate the StampDuty in the database
        List<StampDuty> stampDutyList = stampDutyRepository.findAll();
        assertThat(stampDutyList).hasSize(databaseSizeBeforeUpdate);
        StampDuty testStampDuty = stampDutyList.get(stampDutyList.size() - 1);
        assertThat(testStampDuty.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testStampDuty.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testStampDuty.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testStampDuty.isActive()).isEqualTo(UPDATED_ACTIVE);

        // Validate the StampDuty in Elasticsearch
        StampDuty stampDutyEs = stampDutySearchRepository.findOne(testStampDuty.getId());
        assertThat(stampDutyEs).isEqualToComparingFieldByField(testStampDuty);
    }

    @Test
    @Transactional
    public void updateNonExistingStampDuty() throws Exception {
        int databaseSizeBeforeUpdate = stampDutyRepository.findAll().size();

        // Create the StampDuty
        StampDutyDTO stampDutyDTO = stampDutyMapper.toDto(stampDuty);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restStampDutyMockMvc.perform(put("/api/stamp-duties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stampDutyDTO)))
            .andExpect(status().isCreated());

        // Validate the StampDuty in the database
        List<StampDuty> stampDutyList = stampDutyRepository.findAll();
        assertThat(stampDutyList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteStampDuty() throws Exception {
        // Initialize the database
        stampDutyRepository.saveAndFlush(stampDuty);
        stampDutySearchRepository.save(stampDuty);
        int databaseSizeBeforeDelete = stampDutyRepository.findAll().size();

        // Get the stampDuty
        restStampDutyMockMvc.perform(delete("/api/stamp-duties/{id}", stampDuty.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean stampDutyExistsInEs = stampDutySearchRepository.exists(stampDuty.getId());
        assertThat(stampDutyExistsInEs).isFalse();

        // Validate the database is empty
        List<StampDuty> stampDutyList = stampDutyRepository.findAll();
        assertThat(stampDutyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchStampDuty() throws Exception {
        // Initialize the database
        stampDutyRepository.saveAndFlush(stampDuty);
        stampDutySearchRepository.save(stampDuty);

        // Search the stampDuty
        restStampDutyMockMvc.perform(get("/api/_search/stamp-duties?query=id:" + stampDuty.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stampDuty.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StampDuty.class);
        StampDuty stampDuty1 = new StampDuty();
        stampDuty1.setId(1L);
        StampDuty stampDuty2 = new StampDuty();
        stampDuty2.setId(stampDuty1.getId());
        assertThat(stampDuty1).isEqualTo(stampDuty2);
        stampDuty2.setId(2L);
        assertThat(stampDuty1).isNotEqualTo(stampDuty2);
        stampDuty1.setId(null);
        assertThat(stampDuty1).isNotEqualTo(stampDuty2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StampDutyDTO.class);
        StampDutyDTO stampDutyDTO1 = new StampDutyDTO();
        stampDutyDTO1.setId(1L);
        StampDutyDTO stampDutyDTO2 = new StampDutyDTO();
        assertThat(stampDutyDTO1).isNotEqualTo(stampDutyDTO2);
        stampDutyDTO2.setId(stampDutyDTO1.getId());
        assertThat(stampDutyDTO1).isEqualTo(stampDutyDTO2);
        stampDutyDTO2.setId(2L);
        assertThat(stampDutyDTO1).isNotEqualTo(stampDutyDTO2);
        stampDutyDTO1.setId(null);
        assertThat(stampDutyDTO1).isNotEqualTo(stampDutyDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(stampDutyMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(stampDutyMapper.fromId(null)).isNull();
    }
}
