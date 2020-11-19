package com.gaconnecte.auxilium.web.rest;

import com.gaconnecte.auxilium.AuxiliumApp;

import com.gaconnecte.auxilium.domain.Journal;
import com.gaconnecte.auxilium.repository.JournalRepository;
import com.gaconnecte.auxilium.service.JournalService;
import com.gaconnecte.auxilium.repository.search.JournalSearchRepository;
import com.gaconnecte.auxilium.service.dto.JournalDTO;
import com.gaconnecte.auxilium.service.mapper.JournalMapper;
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
 * Test class for the JournalResource REST controller.
 *
 * @see JournalResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuxiliumApp.class)
public class JournalResourceIntTest {

    private static final String DEFAULT_COMMENTAIRE = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTAIRE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_TIMESTAMP = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TIMESTAMP = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private JournalRepository journalRepository;

    @Autowired
    private JournalMapper journalMapper;

    @Autowired
    private JournalService journalService;

    @Autowired
    private JournalSearchRepository journalSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restJournalMockMvc;

    private Journal journal;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        JournalResource journalResource = new JournalResource(journalService);
        this.restJournalMockMvc = MockMvcBuilders.standaloneSetup(journalResource)
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
    public static Journal createEntity(EntityManager em) {
        Journal journal = new Journal()
            .commentaire(DEFAULT_COMMENTAIRE)
            .timestamp(DEFAULT_TIMESTAMP);
        return journal;
    }

    @Before
    public void initTest() {
        journalSearchRepository.deleteAll();
        journal = createEntity(em);
    }

    @Test
    @Transactional
    public void createJournal() throws Exception {
        int databaseSizeBeforeCreate = journalRepository.findAll().size();

        // Create the Journal
        JournalDTO journalDTO = journalMapper.toDto(journal);
        restJournalMockMvc.perform(post("/api/journals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(journalDTO)))
            .andExpect(status().isCreated());

        // Validate the Journal in the database
        List<Journal> journalList = journalRepository.findAll();
        assertThat(journalList).hasSize(databaseSizeBeforeCreate + 1);
        Journal testJournal = journalList.get(journalList.size() - 1);
        assertThat(testJournal.getCommentaire()).isEqualTo(DEFAULT_COMMENTAIRE);
        assertThat(testJournal.getTimestamp()).isEqualTo(DEFAULT_TIMESTAMP);

        // Validate the Journal in Elasticsearch
        Journal journalEs = journalSearchRepository.findOne(testJournal.getId());
        assertThat(journalEs).isEqualToComparingFieldByField(testJournal);
    }

    @Test
    @Transactional
    public void createJournalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = journalRepository.findAll().size();

        // Create the Journal with an existing ID
        journal.setId(1L);
        JournalDTO journalDTO = journalMapper.toDto(journal);

        // An entity with an existing ID cannot be created, so this API call must fail
        restJournalMockMvc.perform(post("/api/journals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(journalDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Journal> journalList = journalRepository.findAll();
        assertThat(journalList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTimestampIsRequired() throws Exception {
        int databaseSizeBeforeTest = journalRepository.findAll().size();
        // set the field null
        journal.setTimestamp(null);

        // Create the Journal, which fails.
        JournalDTO journalDTO = journalMapper.toDto(journal);

        restJournalMockMvc.perform(post("/api/journals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(journalDTO)))
            .andExpect(status().isBadRequest());

        List<Journal> journalList = journalRepository.findAll();
        assertThat(journalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllJournals() throws Exception {
        // Initialize the database
        journalRepository.saveAndFlush(journal);

        // Get all the journalList
        restJournalMockMvc.perform(get("/api/journals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(journal.getId().intValue())))
            .andExpect(jsonPath("$.[*].commentaire").value(hasItem(DEFAULT_COMMENTAIRE.toString())))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(sameInstant(DEFAULT_TIMESTAMP))));
    }

    @Test
    @Transactional
    public void getJournal() throws Exception {
        // Initialize the database
        journalRepository.saveAndFlush(journal);

        // Get the journal
        restJournalMockMvc.perform(get("/api/journals/{id}", journal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(journal.getId().intValue()))
            .andExpect(jsonPath("$.commentaire").value(DEFAULT_COMMENTAIRE.toString()))
            .andExpect(jsonPath("$.timestamp").value(sameInstant(DEFAULT_TIMESTAMP)));
    }

    @Test
    @Transactional
    public void getNonExistingJournal() throws Exception {
        // Get the journal
        restJournalMockMvc.perform(get("/api/journals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJournal() throws Exception {
        // Initialize the database
        journalRepository.saveAndFlush(journal);
        journalSearchRepository.save(journal);
        int databaseSizeBeforeUpdate = journalRepository.findAll().size();

        // Update the journal
        Journal updatedJournal = journalRepository.findOne(journal.getId());
        updatedJournal
            .commentaire(UPDATED_COMMENTAIRE)
            .timestamp(UPDATED_TIMESTAMP);
        JournalDTO journalDTO = journalMapper.toDto(updatedJournal);

        restJournalMockMvc.perform(put("/api/journals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(journalDTO)))
            .andExpect(status().isOk());

        // Validate the Journal in the database
        List<Journal> journalList = journalRepository.findAll();
        assertThat(journalList).hasSize(databaseSizeBeforeUpdate);
        Journal testJournal = journalList.get(journalList.size() - 1);
        assertThat(testJournal.getCommentaire()).isEqualTo(UPDATED_COMMENTAIRE);
        assertThat(testJournal.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);

        // Validate the Journal in Elasticsearch
        Journal journalEs = journalSearchRepository.findOne(testJournal.getId());
        assertThat(journalEs).isEqualToComparingFieldByField(testJournal);
    }

    @Test
    @Transactional
    public void updateNonExistingJournal() throws Exception {
        int databaseSizeBeforeUpdate = journalRepository.findAll().size();

        // Create the Journal
        JournalDTO journalDTO = journalMapper.toDto(journal);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restJournalMockMvc.perform(put("/api/journals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(journalDTO)))
            .andExpect(status().isCreated());

        // Validate the Journal in the database
        List<Journal> journalList = journalRepository.findAll();
        assertThat(journalList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteJournal() throws Exception {
        // Initialize the database
        journalRepository.saveAndFlush(journal);
        journalSearchRepository.save(journal);
        int databaseSizeBeforeDelete = journalRepository.findAll().size();

        // Get the journal
        restJournalMockMvc.perform(delete("/api/journals/{id}", journal.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean journalExistsInEs = journalSearchRepository.exists(journal.getId());
        assertThat(journalExistsInEs).isFalse();

        // Validate the database is empty
        List<Journal> journalList = journalRepository.findAll();
        assertThat(journalList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchJournal() throws Exception {
        // Initialize the database
        journalRepository.saveAndFlush(journal);
        journalSearchRepository.save(journal);

        // Search the journal
        restJournalMockMvc.perform(get("/api/_search/journals?query=id:" + journal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(journal.getId().intValue())))
            .andExpect(jsonPath("$.[*].commentaire").value(hasItem(DEFAULT_COMMENTAIRE.toString())))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(sameInstant(DEFAULT_TIMESTAMP))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Journal.class);
        Journal journal1 = new Journal();
        journal1.setId(1L);
        Journal journal2 = new Journal();
        journal2.setId(journal1.getId());
        assertThat(journal1).isEqualTo(journal2);
        journal2.setId(2L);
        assertThat(journal1).isNotEqualTo(journal2);
        journal1.setId(null);
        assertThat(journal1).isNotEqualTo(journal2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(JournalDTO.class);
        JournalDTO journalDTO1 = new JournalDTO();
        journalDTO1.setId(1L);
        JournalDTO journalDTO2 = new JournalDTO();
        assertThat(journalDTO1).isNotEqualTo(journalDTO2);
        journalDTO2.setId(journalDTO1.getId());
        assertThat(journalDTO1).isEqualTo(journalDTO2);
        journalDTO2.setId(2L);
        assertThat(journalDTO1).isNotEqualTo(journalDTO2);
        journalDTO1.setId(null);
        assertThat(journalDTO1).isNotEqualTo(journalDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(journalMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(journalMapper.fromId(null)).isNull();
    }
}
