package com.gaconnecte.auxilium.web.rest;

import com.gaconnecte.auxilium.AuxiliumApp;

import com.gaconnecte.auxilium.domain.BonSortie;
import com.gaconnecte.auxilium.repository.BonSortieRepository;
import com.gaconnecte.auxilium.service.BonSortieService;
import com.gaconnecte.auxilium.repository.search.BonSortieSearchRepository;
import com.gaconnecte.auxilium.service.dto.BonSortieDTO;
import com.gaconnecte.auxilium.service.mapper.BonSortieMapper;
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
 * Test class for the BonSortieResource REST controller.
 *
 * @see BonSortieResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuxiliumApp.class)
public class BonSortieResourceIntTest {

    private static final Double DEFAULT_NUMERO = 100000000000000000D;
    private static final Double UPDATED_NUMERO = 100000000000000000D;

    private static final Boolean DEFAULT_IS_SIGNE = false;
    private static final Boolean UPDATED_IS_SIGNE = true;

    private static final String DEFAULT_OBSERVATION = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVATION = "BBBBBBBBBB";

    @Autowired
    private BonSortieRepository bonSortieRepository;

    @Autowired
    private BonSortieMapper bonSortieMapper;

    @Autowired
    private BonSortieService bonSortieService;

    @Autowired
    private BonSortieSearchRepository bonSortieSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBonSortieMockMvc;

    private BonSortie bonSortie;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BonSortieResource bonSortieResource = new BonSortieResource(bonSortieService);
        this.restBonSortieMockMvc = MockMvcBuilders.standaloneSetup(bonSortieResource)
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
    public static BonSortie createEntity(EntityManager em) {
        BonSortie bonSortie = new BonSortie()
            .numero(DEFAULT_NUMERO)
            .isSigne(DEFAULT_IS_SIGNE)
            .observation(DEFAULT_OBSERVATION);
        return bonSortie;
    }

    @Before
    public void initTest() {
        bonSortieSearchRepository.deleteAll();
        bonSortie = createEntity(em);
    }

    @Test
    @Transactional
    public void createBonSortie() throws Exception {
        int databaseSizeBeforeCreate = bonSortieRepository.findAll().size();

        // Create the BonSortie
        BonSortieDTO bonSortieDTO = bonSortieMapper.toDto(bonSortie);
        restBonSortieMockMvc.perform(post("/api/bon-sorties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bonSortieDTO)))
            .andExpect(status().isCreated());

        // Validate the BonSortie in the database
        List<BonSortie> bonSortieList = bonSortieRepository.findAll();
        assertThat(bonSortieList).hasSize(databaseSizeBeforeCreate + 1);
        BonSortie testBonSortie = bonSortieList.get(bonSortieList.size() - 1);
        assertThat(testBonSortie.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testBonSortie.isIsSigne()).isEqualTo(DEFAULT_IS_SIGNE);
        assertThat(testBonSortie.getObservation()).isEqualTo(DEFAULT_OBSERVATION);

        // Validate the BonSortie in Elasticsearch
        BonSortie bonSortieEs = bonSortieSearchRepository.findOne(testBonSortie.getId());
        assertThat(bonSortieEs).isEqualToComparingFieldByField(testBonSortie);
    }

    @Test
    @Transactional
    public void createBonSortieWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bonSortieRepository.findAll().size();

        // Create the BonSortie with an existing ID
        bonSortie.setId(1L);
        BonSortieDTO bonSortieDTO = bonSortieMapper.toDto(bonSortie);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBonSortieMockMvc.perform(post("/api/bon-sorties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bonSortieDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<BonSortie> bonSortieList = bonSortieRepository.findAll();
        assertThat(bonSortieList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNumeroIsRequired() throws Exception {
        int databaseSizeBeforeTest = bonSortieRepository.findAll().size();
        // set the field null
        bonSortie.setNumero(null);

        // Create the BonSortie, which fails.
        BonSortieDTO bonSortieDTO = bonSortieMapper.toDto(bonSortie);

        restBonSortieMockMvc.perform(post("/api/bon-sorties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bonSortieDTO)))
            .andExpect(status().isBadRequest());

        List<BonSortie> bonSortieList = bonSortieRepository.findAll();
        assertThat(bonSortieList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBonSorties() throws Exception {
        // Initialize the database
        bonSortieRepository.saveAndFlush(bonSortie);

        // Get all the bonSortieList
        restBonSortieMockMvc.perform(get("/api/bon-sorties?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bonSortie.getId().intValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO.doubleValue())))
            .andExpect(jsonPath("$.[*].isSigne").value(hasItem(DEFAULT_IS_SIGNE.booleanValue())))
            .andExpect(jsonPath("$.[*].observation").value(hasItem(DEFAULT_OBSERVATION.toString())));
    }

    @Test
    @Transactional
    public void getBonSortie() throws Exception {
        // Initialize the database
        bonSortieRepository.saveAndFlush(bonSortie);

        // Get the bonSortie
        restBonSortieMockMvc.perform(get("/api/bon-sorties/{id}", bonSortie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bonSortie.getId().intValue()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO.doubleValue()))
            .andExpect(jsonPath("$.isSigne").value(DEFAULT_IS_SIGNE.booleanValue()))
            .andExpect(jsonPath("$.observation").value(DEFAULT_OBSERVATION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBonSortie() throws Exception {
        // Get the bonSortie
        restBonSortieMockMvc.perform(get("/api/bon-sorties/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBonSortie() throws Exception {
        // Initialize the database
        bonSortieRepository.saveAndFlush(bonSortie);
        bonSortieSearchRepository.save(bonSortie);
        int databaseSizeBeforeUpdate = bonSortieRepository.findAll().size();

        // Update the bonSortie
        BonSortie updatedBonSortie = bonSortieRepository.findOne(bonSortie.getId());
        updatedBonSortie
            .numero(UPDATED_NUMERO)
            .isSigne(UPDATED_IS_SIGNE)
            .observation(UPDATED_OBSERVATION);
        BonSortieDTO bonSortieDTO = bonSortieMapper.toDto(updatedBonSortie);

        restBonSortieMockMvc.perform(put("/api/bon-sorties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bonSortieDTO)))
            .andExpect(status().isOk());

        // Validate the BonSortie in the database
        List<BonSortie> bonSortieList = bonSortieRepository.findAll();
        assertThat(bonSortieList).hasSize(databaseSizeBeforeUpdate);
        BonSortie testBonSortie = bonSortieList.get(bonSortieList.size() - 1);
        assertThat(testBonSortie.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testBonSortie.isIsSigne()).isEqualTo(UPDATED_IS_SIGNE);
        assertThat(testBonSortie.getObservation()).isEqualTo(UPDATED_OBSERVATION);

        // Validate the BonSortie in Elasticsearch
        BonSortie bonSortieEs = bonSortieSearchRepository.findOne(testBonSortie.getId());
        assertThat(bonSortieEs).isEqualToComparingFieldByField(testBonSortie);
    }

    @Test
    @Transactional
    public void updateNonExistingBonSortie() throws Exception {
        int databaseSizeBeforeUpdate = bonSortieRepository.findAll().size();

        // Create the BonSortie
        BonSortieDTO bonSortieDTO = bonSortieMapper.toDto(bonSortie);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBonSortieMockMvc.perform(put("/api/bon-sorties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bonSortieDTO)))
            .andExpect(status().isCreated());

        // Validate the BonSortie in the database
        List<BonSortie> bonSortieList = bonSortieRepository.findAll();
        assertThat(bonSortieList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBonSortie() throws Exception {
        // Initialize the database
        bonSortieRepository.saveAndFlush(bonSortie);
        bonSortieSearchRepository.save(bonSortie);
        int databaseSizeBeforeDelete = bonSortieRepository.findAll().size();

        // Get the bonSortie
        restBonSortieMockMvc.perform(delete("/api/bon-sorties/{id}", bonSortie.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean bonSortieExistsInEs = bonSortieSearchRepository.exists(bonSortie.getId());
        assertThat(bonSortieExistsInEs).isFalse();

        // Validate the database is empty
        List<BonSortie> bonSortieList = bonSortieRepository.findAll();
        assertThat(bonSortieList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchBonSortie() throws Exception {
        // Initialize the database
        bonSortieRepository.saveAndFlush(bonSortie);
        bonSortieSearchRepository.save(bonSortie);

        // Search the bonSortie
        restBonSortieMockMvc.perform(get("/api/_search/bon-sorties?query=id:" + bonSortie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bonSortie.getId().intValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO.doubleValue())))
            .andExpect(jsonPath("$.[*].isSigne").value(hasItem(DEFAULT_IS_SIGNE.booleanValue())))
            .andExpect(jsonPath("$.[*].observation").value(hasItem(DEFAULT_OBSERVATION.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BonSortie.class);
        BonSortie bonSortie1 = new BonSortie();
        bonSortie1.setId(1L);
        BonSortie bonSortie2 = new BonSortie();
        bonSortie2.setId(bonSortie1.getId());
        assertThat(bonSortie1).isEqualTo(bonSortie2);
        bonSortie2.setId(2L);
        assertThat(bonSortie1).isNotEqualTo(bonSortie2);
        bonSortie1.setId(null);
        assertThat(bonSortie1).isNotEqualTo(bonSortie2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BonSortieDTO.class);
        BonSortieDTO bonSortieDTO1 = new BonSortieDTO();
        bonSortieDTO1.setId(1L);
        BonSortieDTO bonSortieDTO2 = new BonSortieDTO();
        assertThat(bonSortieDTO1).isNotEqualTo(bonSortieDTO2);
        bonSortieDTO2.setId(bonSortieDTO1.getId());
        assertThat(bonSortieDTO1).isEqualTo(bonSortieDTO2);
        bonSortieDTO2.setId(2L);
        assertThat(bonSortieDTO1).isNotEqualTo(bonSortieDTO2);
        bonSortieDTO1.setId(null);
        assertThat(bonSortieDTO1).isNotEqualTo(bonSortieDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(bonSortieMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(bonSortieMapper.fromId(null)).isNull();
    }
}
