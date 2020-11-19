package com.gaconnecte.auxilium.web.rest;

import com.gaconnecte.auxilium.AuxiliumApp;

import com.gaconnecte.auxilium.domain.DetailsPieces;
import com.gaconnecte.auxilium.domain.Fournisseur;
import com.gaconnecte.auxilium.domain.Devis;
import com.gaconnecte.auxilium.domain.Piece;
import com.gaconnecte.auxilium.repository.DetailsPiecesRepository;
import com.gaconnecte.auxilium.service.DetailsPiecesService;
import com.gaconnecte.auxilium.repository.search.DetailsPiecesSearchRepository;
import com.gaconnecte.auxilium.service.dto.DetailsPiecesDTO;
import com.gaconnecte.auxilium.service.mapper.DetailsPiecesMapper;
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
 * Test class for the DetailsPiecesResource REST controller.
 *
 * @see DetailsPiecesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuxiliumApp.class)
public class DetailsPiecesResourceIntTest {

    private static final Float DEFAULT_QUANTITE = 9F;
    private static final Float UPDATED_QUANTITE = 9F;

    private static final String DEFAULT_DESIGNATION = "AB";
    private static final String UPDATED_DESIGNATION = "AC";

    private static final Double DEFAULT_PRIX_UNIT = 20D;
    private static final Double UPDATED_PRIX_UNIT = 20D;

    private static final Float DEFAULT_TVA = 1F;
    private static final Float UPDATED_TVA = 1F;

    @Autowired
    private DetailsPiecesRepository detailsPiecesRepository;

    @Autowired
    private DetailsPiecesMapper detailsPiecesMapper;

    @Autowired
    private DetailsPiecesService detailsPiecesService;

    @Autowired
    private DetailsPiecesSearchRepository detailsPiecesSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDetailsPiecesMockMvc;

    private DetailsPieces detailsPieces;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
       // DetailsPiecesResource detailsPiecesResource = new DetailsPiecesResource(detailsPiecesService);
        this.restDetailsPiecesMockMvc = MockMvcBuilders.standaloneSetup(null)
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
    public static DetailsPieces createEntity(EntityManager em) {
        DetailsPieces detailsPieces = new DetailsPieces()
            .quantite(DEFAULT_QUANTITE)
            .prixUnit(DEFAULT_PRIX_UNIT)
            .tva(DEFAULT_TVA);
        // Add required entity
        Fournisseur fournisseur = FournisseurResourceIntTest.createEntity(em);
        em.persist(fournisseur);
        em.flush();
        detailsPieces.setFournisseur(fournisseur);
        // Add required entity
        Devis devis = DevisResourceIntTest.createEntity(em);
        em.persist(devis);
        em.flush();
        //detailsPieces.setDevis(devis);
        // Add required entity
        Piece piece = PieceResourceIntTest.createEntity(em);
        em.persist(piece);
        em.flush();
        return detailsPieces;
    }

    @Before
    public void initTest() {
        detailsPiecesSearchRepository.deleteAll();
        detailsPieces = createEntity(em);
    }

    @Test
    @Transactional
    public void createDetailsPieces() throws Exception {
        int databaseSizeBeforeCreate = detailsPiecesRepository.findAll().size();

        // Create the DetailsPieces
        DetailsPiecesDTO detailsPiecesDTO = detailsPiecesMapper.toDto(detailsPieces);
        restDetailsPiecesMockMvc.perform(post("/api/details-pieces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detailsPiecesDTO)))
            .andExpect(status().isCreated());

        // Validate the DetailsPieces in the database
        List<DetailsPieces> detailsPiecesList = detailsPiecesRepository.findAll();
        assertThat(detailsPiecesList).hasSize(databaseSizeBeforeCreate + 1);
        DetailsPieces testDetailsPieces = detailsPiecesList.get(detailsPiecesList.size() - 1);
        assertThat(testDetailsPieces.getQuantite()).isEqualTo(DEFAULT_QUANTITE);
        assertThat(testDetailsPieces.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
        assertThat(testDetailsPieces.getPrixUnit()).isEqualTo(DEFAULT_PRIX_UNIT);
        assertThat(testDetailsPieces.getTva()).isEqualTo(DEFAULT_TVA);

        // Validate the DetailsPieces in Elasticsearch
        DetailsPieces detailsPiecesEs = detailsPiecesSearchRepository.findOne(testDetailsPieces.getId());
        assertThat(detailsPiecesEs).isEqualToComparingFieldByField(testDetailsPieces);
    }

    @Test
    @Transactional
    public void createDetailsPiecesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = detailsPiecesRepository.findAll().size();

        // Create the DetailsPieces with an existing ID
        detailsPieces.setId(1L);
        DetailsPiecesDTO detailsPiecesDTO = detailsPiecesMapper.toDto(detailsPieces);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDetailsPiecesMockMvc.perform(post("/api/details-pieces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detailsPiecesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<DetailsPieces> detailsPiecesList = detailsPiecesRepository.findAll();
        assertThat(detailsPiecesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkQuantiteIsRequired() throws Exception {
        int databaseSizeBeforeTest = detailsPiecesRepository.findAll().size();
        // set the field null
        detailsPieces.setQuantite(null);

        // Create the DetailsPieces, which fails.
        DetailsPiecesDTO detailsPiecesDTO = detailsPiecesMapper.toDto(detailsPieces);

        restDetailsPiecesMockMvc.perform(post("/api/details-pieces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detailsPiecesDTO)))
            .andExpect(status().isBadRequest());

        List<DetailsPieces> detailsPiecesList = detailsPiecesRepository.findAll();
        assertThat(detailsPiecesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrixUnitIsRequired() throws Exception {
        int databaseSizeBeforeTest = detailsPiecesRepository.findAll().size();
        // set the field null
        detailsPieces.setPrixUnit(null);

        // Create the DetailsPieces, which fails.
        DetailsPiecesDTO detailsPiecesDTO = detailsPiecesMapper.toDto(detailsPieces);

        restDetailsPiecesMockMvc.perform(post("/api/details-pieces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detailsPiecesDTO)))
            .andExpect(status().isBadRequest());

        List<DetailsPieces> detailsPiecesList = detailsPiecesRepository.findAll();
        assertThat(detailsPiecesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDetailsPieces() throws Exception {
        // Initialize the database
        detailsPiecesRepository.saveAndFlush(detailsPieces);

        // Get all the detailsPiecesList
        restDetailsPiecesMockMvc.perform(get("/api/details-pieces?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(detailsPieces.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantite").value(hasItem(DEFAULT_QUANTITE)))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION.toString())))
            .andExpect(jsonPath("$.[*].prixUnit").value(hasItem(DEFAULT_PRIX_UNIT.doubleValue())))
            .andExpect(jsonPath("$.[*].tva").value(hasItem(DEFAULT_TVA.doubleValue())));
    }

    @Test
    @Transactional
    public void getDetailsPieces() throws Exception {
        // Initialize the database
        detailsPiecesRepository.saveAndFlush(detailsPieces);

        // Get the detailsPieces
        restDetailsPiecesMockMvc.perform(get("/api/details-pieces/{id}", detailsPieces.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(detailsPieces.getId().intValue()))
            .andExpect(jsonPath("$.quantite").value(DEFAULT_QUANTITE))
            .andExpect(jsonPath("$.designation").value(DEFAULT_DESIGNATION.toString()))
            .andExpect(jsonPath("$.prixUnit").value(DEFAULT_PRIX_UNIT.doubleValue()))
            .andExpect(jsonPath("$.tva").value(DEFAULT_TVA.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingDetailsPieces() throws Exception {
        // Get the detailsPieces
        restDetailsPiecesMockMvc.perform(get("/api/details-pieces/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDetailsPieces() throws Exception {
        // Initialize the database
        detailsPiecesRepository.saveAndFlush(detailsPieces);
        detailsPiecesSearchRepository.save(detailsPieces);
        int databaseSizeBeforeUpdate = detailsPiecesRepository.findAll().size();

        // Update the detailsPieces
        DetailsPieces updatedDetailsPieces = detailsPiecesRepository.findOne(detailsPieces.getId());
        updatedDetailsPieces
            .quantite(UPDATED_QUANTITE)
            .prixUnit(UPDATED_PRIX_UNIT)
            .tva(UPDATED_TVA);
        DetailsPiecesDTO detailsPiecesDTO = detailsPiecesMapper.toDto(updatedDetailsPieces);

        restDetailsPiecesMockMvc.perform(put("/api/details-pieces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detailsPiecesDTO)))
            .andExpect(status().isOk());

        // Validate the DetailsPieces in the database
        List<DetailsPieces> detailsPiecesList = detailsPiecesRepository.findAll();
        assertThat(detailsPiecesList).hasSize(databaseSizeBeforeUpdate);
        DetailsPieces testDetailsPieces = detailsPiecesList.get(detailsPiecesList.size() - 1);
        assertThat(testDetailsPieces.getQuantite()).isEqualTo(UPDATED_QUANTITE);
        assertThat(testDetailsPieces.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testDetailsPieces.getPrixUnit()).isEqualTo(UPDATED_PRIX_UNIT);
        assertThat(testDetailsPieces.getTva()).isEqualTo(UPDATED_TVA);

        // Validate the DetailsPieces in Elasticsearch
        DetailsPieces detailsPiecesEs = detailsPiecesSearchRepository.findOne(testDetailsPieces.getId());
        assertThat(detailsPiecesEs).isEqualToComparingFieldByField(testDetailsPieces);
    }

    @Test
    @Transactional
    public void updateNonExistingDetailsPieces() throws Exception {
        int databaseSizeBeforeUpdate = detailsPiecesRepository.findAll().size();

        // Create the DetailsPieces
        DetailsPiecesDTO detailsPiecesDTO = detailsPiecesMapper.toDto(detailsPieces);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDetailsPiecesMockMvc.perform(put("/api/details-pieces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detailsPiecesDTO)))
            .andExpect(status().isCreated());

        // Validate the DetailsPieces in the database
        List<DetailsPieces> detailsPiecesList = detailsPiecesRepository.findAll();
        assertThat(detailsPiecesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDetailsPieces() throws Exception {
        // Initialize the database
        detailsPiecesRepository.saveAndFlush(detailsPieces);
        detailsPiecesSearchRepository.save(detailsPieces);
        int databaseSizeBeforeDelete = detailsPiecesRepository.findAll().size();

        // Get the detailsPieces
        restDetailsPiecesMockMvc.perform(delete("/api/details-pieces/{id}", detailsPieces.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean detailsPiecesExistsInEs = detailsPiecesSearchRepository.exists(detailsPieces.getId());
        assertThat(detailsPiecesExistsInEs).isFalse();

        // Validate the database is empty
        List<DetailsPieces> detailsPiecesList = detailsPiecesRepository.findAll();
        assertThat(detailsPiecesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchDetailsPieces() throws Exception {
        // Initialize the database
        detailsPiecesRepository.saveAndFlush(detailsPieces);
        detailsPiecesSearchRepository.save(detailsPieces);

        // Search the detailsPieces
        restDetailsPiecesMockMvc.perform(get("/api/_search/details-pieces?query=id:" + detailsPieces.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(detailsPieces.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantite").value(hasItem(DEFAULT_QUANTITE)))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION.toString())))
            .andExpect(jsonPath("$.[*].prixUnit").value(hasItem(DEFAULT_PRIX_UNIT.doubleValue())))
            .andExpect(jsonPath("$.[*].tva").value(hasItem(DEFAULT_TVA.doubleValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DetailsPieces.class);
        DetailsPieces detailsPieces1 = new DetailsPieces();
        detailsPieces1.setId(1L);
        DetailsPieces detailsPieces2 = new DetailsPieces();
        detailsPieces2.setId(detailsPieces1.getId());
        assertThat(detailsPieces1).isEqualTo(detailsPieces2);
        detailsPieces2.setId(2L);
        assertThat(detailsPieces1).isNotEqualTo(detailsPieces2);
        detailsPieces1.setId(null);
        assertThat(detailsPieces1).isNotEqualTo(detailsPieces2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DetailsPiecesDTO.class);
        DetailsPiecesDTO detailsPiecesDTO1 = new DetailsPiecesDTO();
        detailsPiecesDTO1.setId(1L);
        DetailsPiecesDTO detailsPiecesDTO2 = new DetailsPiecesDTO();
        assertThat(detailsPiecesDTO1).isNotEqualTo(detailsPiecesDTO2);
        detailsPiecesDTO2.setId(detailsPiecesDTO1.getId());
        assertThat(detailsPiecesDTO1).isEqualTo(detailsPiecesDTO2);
        detailsPiecesDTO2.setId(2L);
        assertThat(detailsPiecesDTO1).isNotEqualTo(detailsPiecesDTO2);
        detailsPiecesDTO1.setId(null);
        assertThat(detailsPiecesDTO1).isNotEqualTo(detailsPiecesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(detailsPiecesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(detailsPiecesMapper.fromId(null)).isNull();
    }
}
