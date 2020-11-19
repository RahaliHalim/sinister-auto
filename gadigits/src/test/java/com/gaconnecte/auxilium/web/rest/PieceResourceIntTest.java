package com.gaconnecte.auxilium.web.rest;

import com.gaconnecte.auxilium.AuxiliumApp;

import com.gaconnecte.auxilium.domain.Piece;
import com.gaconnecte.auxilium.domain.RefTypePieces;
import com.gaconnecte.auxilium.repository.PieceRepository;
import com.gaconnecte.auxilium.service.PieceService;
import com.gaconnecte.auxilium.repository.search.PieceSearchRepository;
import com.gaconnecte.auxilium.service.dto.PieceDTO;
import com.gaconnecte.auxilium.service.mapper.PieceMapper;
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
 * Test class for the PieceResource REST controller.
 *
 * @see PieceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuxiliumApp.class)
public class PieceResourceIntTest {

    private static final String DEFAULT_REFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_VETUSTE = false;
    private static final Boolean UPDATED_IS_VETUSTE = true;

    @Autowired
    private PieceRepository pieceRepository;

    @Autowired
    private PieceMapper pieceMapper;

    @Autowired
    private PieceService pieceService;

    @Autowired
    private PieceSearchRepository pieceSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPieceMockMvc;

    private Piece piece;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PieceResource pieceResource = new PieceResource(pieceService);
        this.restPieceMockMvc = MockMvcBuilders.standaloneSetup(pieceResource)
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
    public static Piece createEntity(EntityManager em) {
        Piece piece = new Piece()
            .reference(DEFAULT_REFERENCE)
            .isVetuste(DEFAULT_IS_VETUSTE);
        // Add required entity
        RefTypePieces typePiece = RefTypePiecesResourceIntTest.createEntity(em);
        em.persist(typePiece);
        em.flush();
        piece.setTypePiece(typePiece);
        return piece;
    }

    @Before
    public void initTest() {
        pieceSearchRepository.deleteAll();
        piece = createEntity(em);
    }

    @Test
    @Transactional
    public void createPiece() throws Exception {
        int databaseSizeBeforeCreate = pieceRepository.findAll().size();

        // Create the Piece
        PieceDTO pieceDTO = pieceMapper.toDto(piece);
        restPieceMockMvc.perform(post("/api/pieces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pieceDTO)))
            .andExpect(status().isCreated());

        // Validate the Piece in the database
        List<Piece> pieceList = pieceRepository.findAll();
        assertThat(pieceList).hasSize(databaseSizeBeforeCreate + 1);
        Piece testPiece = pieceList.get(pieceList.size() - 1);
        assertThat(testPiece.getReference()).isEqualTo(DEFAULT_REFERENCE);
        assertThat(testPiece.isIsVetuste()).isEqualTo(DEFAULT_IS_VETUSTE);

        // Validate the Piece in Elasticsearch
        Piece pieceEs = pieceSearchRepository.findOne(testPiece.getId());
        assertThat(pieceEs).isEqualToComparingFieldByField(testPiece);
    }

    @Test
    @Transactional
    public void createPieceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pieceRepository.findAll().size();

        // Create the Piece with an existing ID
        piece.setId(1L);
        PieceDTO pieceDTO = pieceMapper.toDto(piece);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPieceMockMvc.perform(post("/api/pieces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pieceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Piece> pieceList = pieceRepository.findAll();
        assertThat(pieceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkReferenceIsRequired() throws Exception {
        int databaseSizeBeforeTest = pieceRepository.findAll().size();
        // set the field null
        piece.setReference(null);

        // Create the Piece, which fails.
        PieceDTO pieceDTO = pieceMapper.toDto(piece);

        restPieceMockMvc.perform(post("/api/pieces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pieceDTO)))
            .andExpect(status().isBadRequest());

        List<Piece> pieceList = pieceRepository.findAll();
        assertThat(pieceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPieces() throws Exception {
        // Initialize the database
        pieceRepository.saveAndFlush(piece);

        // Get all the pieceList
        restPieceMockMvc.perform(get("/api/pieces?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(piece.getId().intValue())))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE.toString())))
            .andExpect(jsonPath("$.[*].isVetuste").value(hasItem(DEFAULT_IS_VETUSTE.booleanValue())));
    }

    @Test
    @Transactional
    public void getPiece() throws Exception {
        // Initialize the database
        pieceRepository.saveAndFlush(piece);

        // Get the piece
        restPieceMockMvc.perform(get("/api/pieces/{id}", piece.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(piece.getId().intValue()))
            .andExpect(jsonPath("$.reference").value(DEFAULT_REFERENCE.toString()))
            .andExpect(jsonPath("$.isVetuste").value(DEFAULT_IS_VETUSTE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPiece() throws Exception {
        // Get the piece
        restPieceMockMvc.perform(get("/api/pieces/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePiece() throws Exception {
        // Initialize the database
        pieceRepository.saveAndFlush(piece);
        pieceSearchRepository.save(piece);
        int databaseSizeBeforeUpdate = pieceRepository.findAll().size();

        // Update the piece
        Piece updatedPiece = pieceRepository.findOne(piece.getId());
        updatedPiece
            .reference(UPDATED_REFERENCE)
            .isVetuste(UPDATED_IS_VETUSTE);
        PieceDTO pieceDTO = pieceMapper.toDto(updatedPiece);

        restPieceMockMvc.perform(put("/api/pieces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pieceDTO)))
            .andExpect(status().isOk());

        // Validate the Piece in the database
        List<Piece> pieceList = pieceRepository.findAll();
        assertThat(pieceList).hasSize(databaseSizeBeforeUpdate);
        Piece testPiece = pieceList.get(pieceList.size() - 1);
        assertThat(testPiece.getReference()).isEqualTo(UPDATED_REFERENCE);
        assertThat(testPiece.isIsVetuste()).isEqualTo(UPDATED_IS_VETUSTE);

        // Validate the Piece in Elasticsearch
        Piece pieceEs = pieceSearchRepository.findOne(testPiece.getId());
        assertThat(pieceEs).isEqualToComparingFieldByField(testPiece);
    }

    @Test
    @Transactional
    public void updateNonExistingPiece() throws Exception {
        int databaseSizeBeforeUpdate = pieceRepository.findAll().size();

        // Create the Piece
        PieceDTO pieceDTO = pieceMapper.toDto(piece);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPieceMockMvc.perform(put("/api/pieces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pieceDTO)))
            .andExpect(status().isCreated());

        // Validate the Piece in the database
        List<Piece> pieceList = pieceRepository.findAll();
        assertThat(pieceList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePiece() throws Exception {
        // Initialize the database
        pieceRepository.saveAndFlush(piece);
        pieceSearchRepository.save(piece);
        int databaseSizeBeforeDelete = pieceRepository.findAll().size();

        // Get the piece
        restPieceMockMvc.perform(delete("/api/pieces/{id}", piece.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean pieceExistsInEs = pieceSearchRepository.exists(piece.getId());
        assertThat(pieceExistsInEs).isFalse();

        // Validate the database is empty
        List<Piece> pieceList = pieceRepository.findAll();
        assertThat(pieceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchPiece() throws Exception {
        // Initialize the database
        pieceRepository.saveAndFlush(piece);
        pieceSearchRepository.save(piece);

        // Search the piece
        restPieceMockMvc.perform(get("/api/_search/pieces?query=id:" + piece.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(piece.getId().intValue())))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE.toString())))
            .andExpect(jsonPath("$.[*].isVetuste").value(hasItem(DEFAULT_IS_VETUSTE.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Piece.class);
        Piece piece1 = new Piece();
        piece1.setId(1L);
        Piece piece2 = new Piece();
        piece2.setId(piece1.getId());
        assertThat(piece1).isEqualTo(piece2);
        piece2.setId(2L);
        assertThat(piece1).isNotEqualTo(piece2);
        piece1.setId(null);
        assertThat(piece1).isNotEqualTo(piece2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PieceDTO.class);
        PieceDTO pieceDTO1 = new PieceDTO();
        pieceDTO1.setId(1L);
        PieceDTO pieceDTO2 = new PieceDTO();
        assertThat(pieceDTO1).isNotEqualTo(pieceDTO2);
        pieceDTO2.setId(pieceDTO1.getId());
        assertThat(pieceDTO1).isEqualTo(pieceDTO2);
        pieceDTO2.setId(2L);
        assertThat(pieceDTO1).isNotEqualTo(pieceDTO2);
        pieceDTO1.setId(null);
        assertThat(pieceDTO1).isNotEqualTo(pieceDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(pieceMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(pieceMapper.fromId(null)).isNull();
    }
}
