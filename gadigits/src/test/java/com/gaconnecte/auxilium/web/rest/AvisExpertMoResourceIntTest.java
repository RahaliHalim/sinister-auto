package com.gaconnecte.auxilium.web.rest;

import com.gaconnecte.auxilium.AuxiliumApp;

import com.gaconnecte.auxilium.domain.AvisExpertMo;
import com.gaconnecte.auxilium.repository.AvisExpertMoRepository;
import com.gaconnecte.auxilium.service.AvisExpertMoService;
import com.gaconnecte.auxilium.repository.search.AvisExpertMoSearchRepository;
import com.gaconnecte.auxilium.service.dto.AvisExpertMoDTO;
import com.gaconnecte.auxilium.service.mapper.AvisExpertMoMapper;
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
 * Test class for the AvisExpertMoResource REST controller.
 *
 * @see AvisExpertMoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuxiliumApp.class)
public class AvisExpertMoResourceIntTest {

    private static final String DEFAULT_MOTIF = "AAAAAAAAAA";
    private static final String UPDATED_MOTIF = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENTAIRE = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTAIRE = "BBBBBBBBBB";

    private static final Double DEFAULT_QUANTITE = 100000000000000000D;
    private static final Double UPDATED_QUANTITE = 100000000000000000D;

    @Autowired
    private AvisExpertMoRepository avisExpertMoRepository;

    @Autowired
    private AvisExpertMoMapper avisExpertMoMapper;

    @Autowired
    private AvisExpertMoService avisExpertMoService;

    @Autowired
    private AvisExpertMoSearchRepository avisExpertMoSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAvisExpertMoMockMvc;

    private AvisExpertMo avisExpertMo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AvisExpertMoResource avisExpertMoResource = new AvisExpertMoResource(avisExpertMoService);
        this.restAvisExpertMoMockMvc = MockMvcBuilders.standaloneSetup(avisExpertMoResource)
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
    public static AvisExpertMo createEntity(EntityManager em) {
        AvisExpertMo avisExpertMo = new AvisExpertMo()
            .motif(DEFAULT_MOTIF)
            .commentaire(DEFAULT_COMMENTAIRE)
            .quantite(DEFAULT_QUANTITE);
        return avisExpertMo;
    }

    @Before
    public void initTest() {
        avisExpertMoSearchRepository.deleteAll();
        avisExpertMo = createEntity(em);
    }

    @Test
    @Transactional
    public void createAvisExpertMo() throws Exception {
        int databaseSizeBeforeCreate = avisExpertMoRepository.findAll().size();

        // Create the AvisExpertMo
        AvisExpertMoDTO avisExpertMoDTO = avisExpertMoMapper.toDto(avisExpertMo);
        restAvisExpertMoMockMvc.perform(post("/api/avis-expert-mos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(avisExpertMoDTO)))
            .andExpect(status().isCreated());

        // Validate the AvisExpertMo in the database
        List<AvisExpertMo> avisExpertMoList = avisExpertMoRepository.findAll();
        assertThat(avisExpertMoList).hasSize(databaseSizeBeforeCreate + 1);
        AvisExpertMo testAvisExpertMo = avisExpertMoList.get(avisExpertMoList.size() - 1);
        assertThat(testAvisExpertMo.getMotif()).isEqualTo(DEFAULT_MOTIF);
        assertThat(testAvisExpertMo.getCommentaire()).isEqualTo(DEFAULT_COMMENTAIRE);
        assertThat(testAvisExpertMo.getQuantite()).isEqualTo(DEFAULT_QUANTITE);

        // Validate the AvisExpertMo in Elasticsearch
        AvisExpertMo avisExpertMoEs = avisExpertMoSearchRepository.findOne(testAvisExpertMo.getId());
        assertThat(avisExpertMoEs).isEqualToComparingFieldByField(testAvisExpertMo);
    }

    @Test
    @Transactional
    public void createAvisExpertMoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = avisExpertMoRepository.findAll().size();

        // Create the AvisExpertMo with an existing ID
        avisExpertMo.setId(1L);
        AvisExpertMoDTO avisExpertMoDTO = avisExpertMoMapper.toDto(avisExpertMo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAvisExpertMoMockMvc.perform(post("/api/avis-expert-mos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(avisExpertMoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<AvisExpertMo> avisExpertMoList = avisExpertMoRepository.findAll();
        assertThat(avisExpertMoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAvisExpertMos() throws Exception {
        // Initialize the database
        avisExpertMoRepository.saveAndFlush(avisExpertMo);

        // Get all the avisExpertMoList
        restAvisExpertMoMockMvc.perform(get("/api/avis-expert-mos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(avisExpertMo.getId().intValue())))
            .andExpect(jsonPath("$.[*].motif").value(hasItem(DEFAULT_MOTIF.toString())))
            .andExpect(jsonPath("$.[*].commentaire").value(hasItem(DEFAULT_COMMENTAIRE.toString())))
            .andExpect(jsonPath("$.[*].quantite").value(hasItem(DEFAULT_QUANTITE.doubleValue())));
    }

    @Test
    @Transactional
    public void getAvisExpertMo() throws Exception {
        // Initialize the database
        avisExpertMoRepository.saveAndFlush(avisExpertMo);

        // Get the avisExpertMo
        restAvisExpertMoMockMvc.perform(get("/api/avis-expert-mos/{id}", avisExpertMo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(avisExpertMo.getId().intValue()))
            .andExpect(jsonPath("$.motif").value(DEFAULT_MOTIF.toString()))
            .andExpect(jsonPath("$.commentaire").value(DEFAULT_COMMENTAIRE.toString()))
            .andExpect(jsonPath("$.quantite").value(DEFAULT_QUANTITE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAvisExpertMo() throws Exception {
        // Get the avisExpertMo
        restAvisExpertMoMockMvc.perform(get("/api/avis-expert-mos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAvisExpertMo() throws Exception {
        // Initialize the database
        avisExpertMoRepository.saveAndFlush(avisExpertMo);
        avisExpertMoSearchRepository.save(avisExpertMo);
        int databaseSizeBeforeUpdate = avisExpertMoRepository.findAll().size();

        // Update the avisExpertMo
        AvisExpertMo updatedAvisExpertMo = avisExpertMoRepository.findOne(avisExpertMo.getId());
        updatedAvisExpertMo
            .motif(UPDATED_MOTIF)
            .commentaire(UPDATED_COMMENTAIRE)
            .quantite(UPDATED_QUANTITE);
        AvisExpertMoDTO avisExpertMoDTO = avisExpertMoMapper.toDto(updatedAvisExpertMo);

        restAvisExpertMoMockMvc.perform(put("/api/avis-expert-mos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(avisExpertMoDTO)))
            .andExpect(status().isOk());

        // Validate the AvisExpertMo in the database
        List<AvisExpertMo> avisExpertMoList = avisExpertMoRepository.findAll();
        assertThat(avisExpertMoList).hasSize(databaseSizeBeforeUpdate);
        AvisExpertMo testAvisExpertMo = avisExpertMoList.get(avisExpertMoList.size() - 1);
        assertThat(testAvisExpertMo.getMotif()).isEqualTo(UPDATED_MOTIF);
        assertThat(testAvisExpertMo.getCommentaire()).isEqualTo(UPDATED_COMMENTAIRE);
        assertThat(testAvisExpertMo.getQuantite()).isEqualTo(UPDATED_QUANTITE);

        // Validate the AvisExpertMo in Elasticsearch
        AvisExpertMo avisExpertMoEs = avisExpertMoSearchRepository.findOne(testAvisExpertMo.getId());
        assertThat(avisExpertMoEs).isEqualToComparingFieldByField(testAvisExpertMo);
    }

    @Test
    @Transactional
    public void updateNonExistingAvisExpertMo() throws Exception {
        int databaseSizeBeforeUpdate = avisExpertMoRepository.findAll().size();

        // Create the AvisExpertMo
        AvisExpertMoDTO avisExpertMoDTO = avisExpertMoMapper.toDto(avisExpertMo);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAvisExpertMoMockMvc.perform(put("/api/avis-expert-mos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(avisExpertMoDTO)))
            .andExpect(status().isCreated());

        // Validate the AvisExpertMo in the database
        List<AvisExpertMo> avisExpertMoList = avisExpertMoRepository.findAll();
        assertThat(avisExpertMoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAvisExpertMo() throws Exception {
        // Initialize the database
        avisExpertMoRepository.saveAndFlush(avisExpertMo);
        avisExpertMoSearchRepository.save(avisExpertMo);
        int databaseSizeBeforeDelete = avisExpertMoRepository.findAll().size();

        // Get the avisExpertMo
        restAvisExpertMoMockMvc.perform(delete("/api/avis-expert-mos/{id}", avisExpertMo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean avisExpertMoExistsInEs = avisExpertMoSearchRepository.exists(avisExpertMo.getId());
        assertThat(avisExpertMoExistsInEs).isFalse();

        // Validate the database is empty
        List<AvisExpertMo> avisExpertMoList = avisExpertMoRepository.findAll();
        assertThat(avisExpertMoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchAvisExpertMo() throws Exception {
        // Initialize the database
        avisExpertMoRepository.saveAndFlush(avisExpertMo);
        avisExpertMoSearchRepository.save(avisExpertMo);

        // Search the avisExpertMo
        restAvisExpertMoMockMvc.perform(get("/api/_search/avis-expert-mos?query=id:" + avisExpertMo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(avisExpertMo.getId().intValue())))
            .andExpect(jsonPath("$.[*].motif").value(hasItem(DEFAULT_MOTIF.toString())))
            .andExpect(jsonPath("$.[*].commentaire").value(hasItem(DEFAULT_COMMENTAIRE.toString())))
            .andExpect(jsonPath("$.[*].quantite").value(hasItem(DEFAULT_QUANTITE.doubleValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AvisExpertMo.class);
        AvisExpertMo avisExpertMo1 = new AvisExpertMo();
        avisExpertMo1.setId(1L);
        AvisExpertMo avisExpertMo2 = new AvisExpertMo();
        avisExpertMo2.setId(avisExpertMo1.getId());
        assertThat(avisExpertMo1).isEqualTo(avisExpertMo2);
        avisExpertMo2.setId(2L);
        assertThat(avisExpertMo1).isNotEqualTo(avisExpertMo2);
        avisExpertMo1.setId(null);
        assertThat(avisExpertMo1).isNotEqualTo(avisExpertMo2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AvisExpertMoDTO.class);
        AvisExpertMoDTO avisExpertMoDTO1 = new AvisExpertMoDTO();
        avisExpertMoDTO1.setId(1L);
        AvisExpertMoDTO avisExpertMoDTO2 = new AvisExpertMoDTO();
        assertThat(avisExpertMoDTO1).isNotEqualTo(avisExpertMoDTO2);
        avisExpertMoDTO2.setId(avisExpertMoDTO1.getId());
        assertThat(avisExpertMoDTO1).isEqualTo(avisExpertMoDTO2);
        avisExpertMoDTO2.setId(2L);
        assertThat(avisExpertMoDTO1).isNotEqualTo(avisExpertMoDTO2);
        avisExpertMoDTO1.setId(null);
        assertThat(avisExpertMoDTO1).isNotEqualTo(avisExpertMoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(avisExpertMoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(avisExpertMoMapper.fromId(null)).isNull();
    }
}
