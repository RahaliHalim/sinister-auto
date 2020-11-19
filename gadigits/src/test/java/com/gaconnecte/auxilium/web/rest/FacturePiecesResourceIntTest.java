package com.gaconnecte.auxilium.web.rest;

import com.gaconnecte.auxilium.AuxiliumApp;

import com.gaconnecte.auxilium.domain.FacturePieces;
import com.gaconnecte.auxilium.repository.FacturePiecesRepository;
import com.gaconnecte.auxilium.service.FacturePiecesService;
import com.gaconnecte.auxilium.repository.search.FacturePiecesSearchRepository;
import com.gaconnecte.auxilium.service.dto.FacturePiecesDTO;
import com.gaconnecte.auxilium.service.mapper.FacturePiecesMapper;
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
 * Test class for the FacturePiecesResource REST controller.
 *
 * @see FacturePiecesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuxiliumApp.class)
public class FacturePiecesResourceIntTest {

    private static final LocalDate DEFAULT_DATE_GENERATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_GENERATION = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private FacturePiecesRepository facturePiecesRepository;

    @Autowired
    private FacturePiecesMapper facturePiecesMapper;

    @Autowired
    private FacturePiecesService facturePiecesService;

    @Autowired
    private FacturePiecesSearchRepository facturePiecesSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFacturePiecesMockMvc;

    private FacturePieces facturePieces;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FacturePiecesResource facturePiecesResource = new FacturePiecesResource(facturePiecesService);
        this.restFacturePiecesMockMvc = MockMvcBuilders.standaloneSetup(facturePiecesResource)
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
    public static FacturePieces createEntity(EntityManager em) {
        FacturePieces facturePieces = new FacturePieces()
            .dateGeneration(DEFAULT_DATE_GENERATION);
        return facturePieces;
    }

    @Before
    public void initTest() {
        facturePiecesSearchRepository.deleteAll();
        facturePieces = createEntity(em);
    }

    @Test
    @Transactional
    public void createFacturePieces() throws Exception {
        int databaseSizeBeforeCreate = facturePiecesRepository.findAll().size();

        // Create the FacturePieces
        FacturePiecesDTO facturePiecesDTO = facturePiecesMapper.toDto(facturePieces);
        restFacturePiecesMockMvc.perform(post("/api/facture-pieces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(facturePiecesDTO)))
            .andExpect(status().isCreated());

        // Validate the FacturePieces in the database
        List<FacturePieces> facturePiecesList = facturePiecesRepository.findAll();
        assertThat(facturePiecesList).hasSize(databaseSizeBeforeCreate + 1);
        FacturePieces testFacturePieces = facturePiecesList.get(facturePiecesList.size() - 1);
        assertThat(testFacturePieces.getDateGeneration()).isEqualTo(DEFAULT_DATE_GENERATION);

        // Validate the FacturePieces in Elasticsearch
        FacturePieces facturePiecesEs = facturePiecesSearchRepository.findOne(testFacturePieces.getId());
        assertThat(facturePiecesEs).isEqualToComparingFieldByField(testFacturePieces);
    }

    @Test
    @Transactional
    public void createFacturePiecesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = facturePiecesRepository.findAll().size();

        // Create the FacturePieces with an existing ID
        facturePieces.setId(1L);
        FacturePiecesDTO facturePiecesDTO = facturePiecesMapper.toDto(facturePieces);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFacturePiecesMockMvc.perform(post("/api/facture-pieces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(facturePiecesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<FacturePieces> facturePiecesList = facturePiecesRepository.findAll();
        assertThat(facturePiecesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDateGenerationIsRequired() throws Exception {
        int databaseSizeBeforeTest = facturePiecesRepository.findAll().size();
        // set the field null
        facturePieces.setDateGeneration(null);

        // Create the FacturePieces, which fails.
        FacturePiecesDTO facturePiecesDTO = facturePiecesMapper.toDto(facturePieces);

        restFacturePiecesMockMvc.perform(post("/api/facture-pieces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(facturePiecesDTO)))
            .andExpect(status().isBadRequest());

        List<FacturePieces> facturePiecesList = facturePiecesRepository.findAll();
        assertThat(facturePiecesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFacturePieces() throws Exception {
        // Initialize the database
        facturePiecesRepository.saveAndFlush(facturePieces);

        // Get all the facturePiecesList
        restFacturePiecesMockMvc.perform(get("/api/facture-pieces?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(facturePieces.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateGeneration").value(hasItem(DEFAULT_DATE_GENERATION.toString())));
    }

    @Test
    @Transactional
    public void getFacturePieces() throws Exception {
        // Initialize the database
        facturePiecesRepository.saveAndFlush(facturePieces);

        // Get the facturePieces
        restFacturePiecesMockMvc.perform(get("/api/facture-pieces/{id}", facturePieces.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(facturePieces.getId().intValue()))
            .andExpect(jsonPath("$.dateGeneration").value(DEFAULT_DATE_GENERATION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFacturePieces() throws Exception {
        // Get the facturePieces
        restFacturePiecesMockMvc.perform(get("/api/facture-pieces/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFacturePieces() throws Exception {
        // Initialize the database
        facturePiecesRepository.saveAndFlush(facturePieces);
        facturePiecesSearchRepository.save(facturePieces);
        int databaseSizeBeforeUpdate = facturePiecesRepository.findAll().size();

        // Update the facturePieces
        FacturePieces updatedFacturePieces = facturePiecesRepository.findOne(facturePieces.getId());
        updatedFacturePieces
            .dateGeneration(UPDATED_DATE_GENERATION);
        FacturePiecesDTO facturePiecesDTO = facturePiecesMapper.toDto(updatedFacturePieces);

        restFacturePiecesMockMvc.perform(put("/api/facture-pieces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(facturePiecesDTO)))
            .andExpect(status().isOk());

        // Validate the FacturePieces in the database
        List<FacturePieces> facturePiecesList = facturePiecesRepository.findAll();
        assertThat(facturePiecesList).hasSize(databaseSizeBeforeUpdate);
        FacturePieces testFacturePieces = facturePiecesList.get(facturePiecesList.size() - 1);
        assertThat(testFacturePieces.getDateGeneration()).isEqualTo(UPDATED_DATE_GENERATION);

        // Validate the FacturePieces in Elasticsearch
        FacturePieces facturePiecesEs = facturePiecesSearchRepository.findOne(testFacturePieces.getId());
        assertThat(facturePiecesEs).isEqualToComparingFieldByField(testFacturePieces);
    }

    @Test
    @Transactional
    public void updateNonExistingFacturePieces() throws Exception {
        int databaseSizeBeforeUpdate = facturePiecesRepository.findAll().size();

        // Create the FacturePieces
        FacturePiecesDTO facturePiecesDTO = facturePiecesMapper.toDto(facturePieces);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFacturePiecesMockMvc.perform(put("/api/facture-pieces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(facturePiecesDTO)))
            .andExpect(status().isCreated());

        // Validate the FacturePieces in the database
        List<FacturePieces> facturePiecesList = facturePiecesRepository.findAll();
        assertThat(facturePiecesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFacturePieces() throws Exception {
        // Initialize the database
        facturePiecesRepository.saveAndFlush(facturePieces);
        facturePiecesSearchRepository.save(facturePieces);
        int databaseSizeBeforeDelete = facturePiecesRepository.findAll().size();

        // Get the facturePieces
        restFacturePiecesMockMvc.perform(delete("/api/facture-pieces/{id}", facturePieces.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean facturePiecesExistsInEs = facturePiecesSearchRepository.exists(facturePieces.getId());
        assertThat(facturePiecesExistsInEs).isFalse();

        // Validate the database is empty
        List<FacturePieces> facturePiecesList = facturePiecesRepository.findAll();
        assertThat(facturePiecesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchFacturePieces() throws Exception {
        // Initialize the database
        facturePiecesRepository.saveAndFlush(facturePieces);
        facturePiecesSearchRepository.save(facturePieces);

        // Search the facturePieces
        restFacturePiecesMockMvc.perform(get("/api/_search/facture-pieces?query=id:" + facturePieces.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(facturePieces.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateGeneration").value(hasItem(DEFAULT_DATE_GENERATION.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FacturePieces.class);
        FacturePieces facturePieces1 = new FacturePieces();
        facturePieces1.setId(1L);
        FacturePieces facturePieces2 = new FacturePieces();
        facturePieces2.setId(facturePieces1.getId());
        assertThat(facturePieces1).isEqualTo(facturePieces2);
        facturePieces2.setId(2L);
        assertThat(facturePieces1).isNotEqualTo(facturePieces2);
        facturePieces1.setId(null);
        assertThat(facturePieces1).isNotEqualTo(facturePieces2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FacturePiecesDTO.class);
        FacturePiecesDTO facturePiecesDTO1 = new FacturePiecesDTO();
        facturePiecesDTO1.setId(1L);
        FacturePiecesDTO facturePiecesDTO2 = new FacturePiecesDTO();
        assertThat(facturePiecesDTO1).isNotEqualTo(facturePiecesDTO2);
        facturePiecesDTO2.setId(facturePiecesDTO1.getId());
        assertThat(facturePiecesDTO1).isEqualTo(facturePiecesDTO2);
        facturePiecesDTO2.setId(2L);
        assertThat(facturePiecesDTO1).isNotEqualTo(facturePiecesDTO2);
        facturePiecesDTO1.setId(null);
        assertThat(facturePiecesDTO1).isNotEqualTo(facturePiecesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(facturePiecesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(facturePiecesMapper.fromId(null)).isNull();
    }
}
