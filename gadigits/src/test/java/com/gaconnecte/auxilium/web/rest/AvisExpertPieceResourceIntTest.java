package com.gaconnecte.auxilium.web.rest;

import com.gaconnecte.auxilium.AuxiliumApp;

import com.gaconnecte.auxilium.domain.AvisExpertPiece;
import com.gaconnecte.auxilium.repository.AvisExpertPieceRepository;
import com.gaconnecte.auxilium.service.AvisExpertPieceService;
import com.gaconnecte.auxilium.repository.search.AvisExpertPieceSearchRepository;
import com.gaconnecte.auxilium.service.dto.AvisExpertPieceDTO;
import com.gaconnecte.auxilium.service.mapper.AvisExpertPieceMapper;
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
 * Test class for the AvisExpertPieceResource REST controller.
 *
 * @see AvisExpertPieceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuxiliumApp.class)
public class AvisExpertPieceResourceIntTest {

    private static final String DEFAULT_MOTIF = "AAAAAAAAAA";
    private static final String UPDATED_MOTIF = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENTAIRE = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTAIRE = "BBBBBBBBBB";

    private static final Double DEFAULT_QUANTITE = 100000000000000000D;
    private static final Double UPDATED_QUANTITE = 100000000000000000D;

    @Autowired
    private AvisExpertPieceRepository avisExpertPieceRepository;

    @Autowired
    private AvisExpertPieceMapper avisExpertPieceMapper;

    @Autowired
    private AvisExpertPieceService avisExpertPieceService;

    @Autowired
    private AvisExpertPieceSearchRepository avisExpertPieceSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAvisExpertPieceMockMvc;

    private AvisExpertPiece avisExpertPiece;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AvisExpertPieceResource avisExpertPieceResource = new AvisExpertPieceResource(avisExpertPieceService);
        this.restAvisExpertPieceMockMvc = MockMvcBuilders.standaloneSetup(avisExpertPieceResource)
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
    public static AvisExpertPiece createEntity(EntityManager em) {
        AvisExpertPiece avisExpertPiece = new AvisExpertPiece()
            .motif(DEFAULT_MOTIF)
            .commentaire(DEFAULT_COMMENTAIRE)
            .quantite(DEFAULT_QUANTITE);
        return avisExpertPiece;
    }

    @Before
    public void initTest() {
        avisExpertPieceSearchRepository.deleteAll();
        avisExpertPiece = createEntity(em);
    }

    @Test
    @Transactional
    public void createAvisExpertPiece() throws Exception {
        int databaseSizeBeforeCreate = avisExpertPieceRepository.findAll().size();

        // Create the AvisExpertPiece
        AvisExpertPieceDTO avisExpertPieceDTO = avisExpertPieceMapper.toDto(avisExpertPiece);
        restAvisExpertPieceMockMvc.perform(post("/api/avis-expert-pieces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(avisExpertPieceDTO)))
            .andExpect(status().isCreated());

        // Validate the AvisExpertPiece in the database
        List<AvisExpertPiece> avisExpertPieceList = avisExpertPieceRepository.findAll();
        assertThat(avisExpertPieceList).hasSize(databaseSizeBeforeCreate + 1);
        AvisExpertPiece testAvisExpertPiece = avisExpertPieceList.get(avisExpertPieceList.size() - 1);
        assertThat(testAvisExpertPiece.getMotif()).isEqualTo(DEFAULT_MOTIF);
        assertThat(testAvisExpertPiece.getCommentaire()).isEqualTo(DEFAULT_COMMENTAIRE);
        assertThat(testAvisExpertPiece.getQuantite()).isEqualTo(DEFAULT_QUANTITE);

        // Validate the AvisExpertPiece in Elasticsearch
        AvisExpertPiece avisExpertPieceEs = avisExpertPieceSearchRepository.findOne(testAvisExpertPiece.getId());
        assertThat(avisExpertPieceEs).isEqualToComparingFieldByField(testAvisExpertPiece);
    }

    @Test
    @Transactional
    public void createAvisExpertPieceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = avisExpertPieceRepository.findAll().size();

        // Create the AvisExpertPiece with an existing ID
        avisExpertPiece.setId(1L);
        AvisExpertPieceDTO avisExpertPieceDTO = avisExpertPieceMapper.toDto(avisExpertPiece);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAvisExpertPieceMockMvc.perform(post("/api/avis-expert-pieces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(avisExpertPieceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<AvisExpertPiece> avisExpertPieceList = avisExpertPieceRepository.findAll();
        assertThat(avisExpertPieceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAvisExpertPieces() throws Exception {
        // Initialize the database
        avisExpertPieceRepository.saveAndFlush(avisExpertPiece);

        // Get all the avisExpertPieceList
        restAvisExpertPieceMockMvc.perform(get("/api/avis-expert-pieces?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(avisExpertPiece.getId().intValue())))
            .andExpect(jsonPath("$.[*].motif").value(hasItem(DEFAULT_MOTIF.toString())))
            .andExpect(jsonPath("$.[*].commentaire").value(hasItem(DEFAULT_COMMENTAIRE.toString())))
            .andExpect(jsonPath("$.[*].quantite").value(hasItem(DEFAULT_QUANTITE.doubleValue())));
    }

    @Test
    @Transactional
    public void getAvisExpertPiece() throws Exception {
        // Initialize the database
        avisExpertPieceRepository.saveAndFlush(avisExpertPiece);

        // Get the avisExpertPiece
        restAvisExpertPieceMockMvc.perform(get("/api/avis-expert-pieces/{id}", avisExpertPiece.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(avisExpertPiece.getId().intValue()))
            .andExpect(jsonPath("$.motif").value(DEFAULT_MOTIF.toString()))
            .andExpect(jsonPath("$.commentaire").value(DEFAULT_COMMENTAIRE.toString()))
            .andExpect(jsonPath("$.quantite").value(DEFAULT_QUANTITE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAvisExpertPiece() throws Exception {
        // Get the avisExpertPiece
        restAvisExpertPieceMockMvc.perform(get("/api/avis-expert-pieces/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAvisExpertPiece() throws Exception {
        // Initialize the database
        avisExpertPieceRepository.saveAndFlush(avisExpertPiece);
        avisExpertPieceSearchRepository.save(avisExpertPiece);
        int databaseSizeBeforeUpdate = avisExpertPieceRepository.findAll().size();

        // Update the avisExpertPiece
        AvisExpertPiece updatedAvisExpertPiece = avisExpertPieceRepository.findOne(avisExpertPiece.getId());
        updatedAvisExpertPiece
            .motif(UPDATED_MOTIF)
            .commentaire(UPDATED_COMMENTAIRE)
            .quantite(UPDATED_QUANTITE);
        AvisExpertPieceDTO avisExpertPieceDTO = avisExpertPieceMapper.toDto(updatedAvisExpertPiece);

        restAvisExpertPieceMockMvc.perform(put("/api/avis-expert-pieces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(avisExpertPieceDTO)))
            .andExpect(status().isOk());

        // Validate the AvisExpertPiece in the database
        List<AvisExpertPiece> avisExpertPieceList = avisExpertPieceRepository.findAll();
        assertThat(avisExpertPieceList).hasSize(databaseSizeBeforeUpdate);
        AvisExpertPiece testAvisExpertPiece = avisExpertPieceList.get(avisExpertPieceList.size() - 1);
        assertThat(testAvisExpertPiece.getMotif()).isEqualTo(UPDATED_MOTIF);
        assertThat(testAvisExpertPiece.getCommentaire()).isEqualTo(UPDATED_COMMENTAIRE);
        assertThat(testAvisExpertPiece.getQuantite()).isEqualTo(UPDATED_QUANTITE);

        // Validate the AvisExpertPiece in Elasticsearch
        AvisExpertPiece avisExpertPieceEs = avisExpertPieceSearchRepository.findOne(testAvisExpertPiece.getId());
        assertThat(avisExpertPieceEs).isEqualToComparingFieldByField(testAvisExpertPiece);
    }

    @Test
    @Transactional
    public void updateNonExistingAvisExpertPiece() throws Exception {
        int databaseSizeBeforeUpdate = avisExpertPieceRepository.findAll().size();

        // Create the AvisExpertPiece
        AvisExpertPieceDTO avisExpertPieceDTO = avisExpertPieceMapper.toDto(avisExpertPiece);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAvisExpertPieceMockMvc.perform(put("/api/avis-expert-pieces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(avisExpertPieceDTO)))
            .andExpect(status().isCreated());

        // Validate the AvisExpertPiece in the database
        List<AvisExpertPiece> avisExpertPieceList = avisExpertPieceRepository.findAll();
        assertThat(avisExpertPieceList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAvisExpertPiece() throws Exception {
        // Initialize the database
        avisExpertPieceRepository.saveAndFlush(avisExpertPiece);
        avisExpertPieceSearchRepository.save(avisExpertPiece);
        int databaseSizeBeforeDelete = avisExpertPieceRepository.findAll().size();

        // Get the avisExpertPiece
        restAvisExpertPieceMockMvc.perform(delete("/api/avis-expert-pieces/{id}", avisExpertPiece.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean avisExpertPieceExistsInEs = avisExpertPieceSearchRepository.exists(avisExpertPiece.getId());
        assertThat(avisExpertPieceExistsInEs).isFalse();

        // Validate the database is empty
        List<AvisExpertPiece> avisExpertPieceList = avisExpertPieceRepository.findAll();
        assertThat(avisExpertPieceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchAvisExpertPiece() throws Exception {
        // Initialize the database
        avisExpertPieceRepository.saveAndFlush(avisExpertPiece);
        avisExpertPieceSearchRepository.save(avisExpertPiece);

        // Search the avisExpertPiece
        restAvisExpertPieceMockMvc.perform(get("/api/_search/avis-expert-pieces?query=id:" + avisExpertPiece.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(avisExpertPiece.getId().intValue())))
            .andExpect(jsonPath("$.[*].motif").value(hasItem(DEFAULT_MOTIF.toString())))
            .andExpect(jsonPath("$.[*].commentaire").value(hasItem(DEFAULT_COMMENTAIRE.toString())))
            .andExpect(jsonPath("$.[*].quantite").value(hasItem(DEFAULT_QUANTITE.doubleValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AvisExpertPiece.class);
        AvisExpertPiece avisExpertPiece1 = new AvisExpertPiece();
        avisExpertPiece1.setId(1L);
        AvisExpertPiece avisExpertPiece2 = new AvisExpertPiece();
        avisExpertPiece2.setId(avisExpertPiece1.getId());
        assertThat(avisExpertPiece1).isEqualTo(avisExpertPiece2);
        avisExpertPiece2.setId(2L);
        assertThat(avisExpertPiece1).isNotEqualTo(avisExpertPiece2);
        avisExpertPiece1.setId(null);
        assertThat(avisExpertPiece1).isNotEqualTo(avisExpertPiece2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AvisExpertPieceDTO.class);
        AvisExpertPieceDTO avisExpertPieceDTO1 = new AvisExpertPieceDTO();
        avisExpertPieceDTO1.setId(1L);
        AvisExpertPieceDTO avisExpertPieceDTO2 = new AvisExpertPieceDTO();
        assertThat(avisExpertPieceDTO1).isNotEqualTo(avisExpertPieceDTO2);
        avisExpertPieceDTO2.setId(avisExpertPieceDTO1.getId());
        assertThat(avisExpertPieceDTO1).isEqualTo(avisExpertPieceDTO2);
        avisExpertPieceDTO2.setId(2L);
        assertThat(avisExpertPieceDTO1).isNotEqualTo(avisExpertPieceDTO2);
        avisExpertPieceDTO1.setId(null);
        assertThat(avisExpertPieceDTO1).isNotEqualTo(avisExpertPieceDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(avisExpertPieceMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(avisExpertPieceMapper.fromId(null)).isNull();
    }
}
