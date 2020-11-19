package com.gaconnecte.auxilium.web.rest;

import com.gaconnecte.auxilium.AuxiliumApp;

import com.gaconnecte.auxilium.domain.Statement;
import com.gaconnecte.auxilium.repository.StatementRepository;
import com.gaconnecte.auxilium.service.StatementService;
import com.gaconnecte.auxilium.repository.search.StatementSearchRepository;
import com.gaconnecte.auxilium.service.dto.StatementDTO;
import com.gaconnecte.auxilium.service.impl.FileStorageService;
import com.gaconnecte.auxilium.service.mapper.StatementMapper;
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
 * Test class for the StatementResource REST controller.
 *
 * @see StatementResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuxiliumApp.class)
public class StatementResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PATH = "AAAAAAAAAA";
    private static final String UPDATED_PATH = "BBBBBBBBBB";

    private static final Long DEFAULT_TUG_ID = 1L;
    private static final Long UPDATED_TUG_ID = 2L;

    private static final LocalDate DEFAULT_CREATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    @Autowired
    private StatementRepository statementRepository;

    @Autowired
    private StatementMapper statementMapper;

    @Autowired
    private StatementService statementService;
    
    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private StatementSearchRepository statementSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restStatementMockMvc;

    private Statement statement;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        StatementResource statementResource = new StatementResource(statementService, fileStorageService);
        this.restStatementMockMvc = MockMvcBuilders.standaloneSetup(statementResource)
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
    public static Statement createEntity(EntityManager em) {
        Statement statement = new Statement()
            .name(DEFAULT_NAME)
            .path(DEFAULT_PATH)
            .tugId(DEFAULT_TUG_ID)
            .creationDate(DEFAULT_CREATION_DATE)
            .notes(DEFAULT_NOTES);
        return statement;
    }

    @Before
    public void initTest() {
        statementSearchRepository.deleteAll();
        statement = createEntity(em);
    }

    @Test
    @Transactional
    public void createStatement() throws Exception {
        int databaseSizeBeforeCreate = statementRepository.findAll().size();

        // Create the Statement
        StatementDTO statementDTO = statementMapper.toDto(statement);
        restStatementMockMvc.perform(post("/api/statements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(statementDTO)))
            .andExpect(status().isCreated());

        // Validate the Statement in the database
        List<Statement> statementList = statementRepository.findAll();
        assertThat(statementList).hasSize(databaseSizeBeforeCreate + 1);
        Statement testStatement = statementList.get(statementList.size() - 1);
        assertThat(testStatement.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testStatement.getPath()).isEqualTo(DEFAULT_PATH);
        assertThat(testStatement.getTugId()).isEqualTo(DEFAULT_TUG_ID);
        assertThat(testStatement.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testStatement.getNotes()).isEqualTo(DEFAULT_NOTES);

        // Validate the Statement in Elasticsearch
        Statement statementEs = statementSearchRepository.findOne(testStatement.getId());
        assertThat(statementEs).isEqualToComparingFieldByField(testStatement);
    }

    @Test
    @Transactional
    public void createStatementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = statementRepository.findAll().size();

        // Create the Statement with an existing ID
        statement.setId(1L);
        StatementDTO statementDTO = statementMapper.toDto(statement);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStatementMockMvc.perform(post("/api/statements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(statementDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Statement> statementList = statementRepository.findAll();
        assertThat(statementList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllStatements() throws Exception {
        // Initialize the database
        statementRepository.saveAndFlush(statement);

        // Get all the statementList
        restStatementMockMvc.perform(get("/api/statements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(statement.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].path").value(hasItem(DEFAULT_PATH.toString())))
            .andExpect(jsonPath("$.[*].tugId").value(hasItem(DEFAULT_TUG_ID.intValue())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES.toString())));
    }

    @Test
    @Transactional
    public void getStatement() throws Exception {
        // Initialize the database
        statementRepository.saveAndFlush(statement);

        // Get the statement
        restStatementMockMvc.perform(get("/api/statements/{id}", statement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(statement.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.path").value(DEFAULT_PATH.toString()))
            .andExpect(jsonPath("$.tugId").value(DEFAULT_TUG_ID.intValue()))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingStatement() throws Exception {
        // Get the statement
        restStatementMockMvc.perform(get("/api/statements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStatement() throws Exception {
        // Initialize the database
        statementRepository.saveAndFlush(statement);
        statementSearchRepository.save(statement);
        int databaseSizeBeforeUpdate = statementRepository.findAll().size();

        // Update the statement
        Statement updatedStatement = statementRepository.findOne(statement.getId());
        updatedStatement
            .name(UPDATED_NAME)
            .path(UPDATED_PATH)
            .tugId(UPDATED_TUG_ID)
            .creationDate(UPDATED_CREATION_DATE)
            .notes(UPDATED_NOTES);
        StatementDTO statementDTO = statementMapper.toDto(updatedStatement);

        restStatementMockMvc.perform(put("/api/statements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(statementDTO)))
            .andExpect(status().isOk());

        // Validate the Statement in the database
        List<Statement> statementList = statementRepository.findAll();
        assertThat(statementList).hasSize(databaseSizeBeforeUpdate);
        Statement testStatement = statementList.get(statementList.size() - 1);
        assertThat(testStatement.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testStatement.getPath()).isEqualTo(UPDATED_PATH);
        assertThat(testStatement.getTugId()).isEqualTo(UPDATED_TUG_ID);
        assertThat(testStatement.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testStatement.getNotes()).isEqualTo(UPDATED_NOTES);

        // Validate the Statement in Elasticsearch
        Statement statementEs = statementSearchRepository.findOne(testStatement.getId());
        assertThat(statementEs).isEqualToComparingFieldByField(testStatement);
    }

    @Test
    @Transactional
    public void updateNonExistingStatement() throws Exception {
        int databaseSizeBeforeUpdate = statementRepository.findAll().size();

        // Create the Statement
        StatementDTO statementDTO = statementMapper.toDto(statement);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restStatementMockMvc.perform(put("/api/statements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(statementDTO)))
            .andExpect(status().isCreated());

        // Validate the Statement in the database
        List<Statement> statementList = statementRepository.findAll();
        assertThat(statementList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteStatement() throws Exception {
        // Initialize the database
        statementRepository.saveAndFlush(statement);
        statementSearchRepository.save(statement);
        int databaseSizeBeforeDelete = statementRepository.findAll().size();

        // Get the statement
        restStatementMockMvc.perform(delete("/api/statements/{id}", statement.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean statementExistsInEs = statementSearchRepository.exists(statement.getId());
        assertThat(statementExistsInEs).isFalse();

        // Validate the database is empty
        List<Statement> statementList = statementRepository.findAll();
        assertThat(statementList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchStatement() throws Exception {
        // Initialize the database
        statementRepository.saveAndFlush(statement);
        statementSearchRepository.save(statement);

        // Search the statement
        restStatementMockMvc.perform(get("/api/_search/statements?query=id:" + statement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(statement.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].path").value(hasItem(DEFAULT_PATH.toString())))
            .andExpect(jsonPath("$.[*].tugId").value(hasItem(DEFAULT_TUG_ID.intValue())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Statement.class);
        Statement statement1 = new Statement();
        statement1.setId(1L);
        Statement statement2 = new Statement();
        statement2.setId(statement1.getId());
        assertThat(statement1).isEqualTo(statement2);
        statement2.setId(2L);
        assertThat(statement1).isNotEqualTo(statement2);
        statement1.setId(null);
        assertThat(statement1).isNotEqualTo(statement2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StatementDTO.class);
        StatementDTO statementDTO1 = new StatementDTO();
        statementDTO1.setId(1L);
        StatementDTO statementDTO2 = new StatementDTO();
        assertThat(statementDTO1).isNotEqualTo(statementDTO2);
        statementDTO2.setId(statementDTO1.getId());
        assertThat(statementDTO1).isEqualTo(statementDTO2);
        statementDTO2.setId(2L);
        assertThat(statementDTO1).isNotEqualTo(statementDTO2);
        statementDTO1.setId(null);
        assertThat(statementDTO1).isNotEqualTo(statementDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(statementMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(statementMapper.fromId(null)).isNull();
    }
}
