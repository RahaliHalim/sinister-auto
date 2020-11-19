package com.gaconnecte.auxilium.web.rest;

import com.gaconnecte.auxilium.AuxiliumApp;

import com.gaconnecte.auxilium.domain.Tarif;
import com.gaconnecte.auxilium.repository.TarifRepository;
import com.gaconnecte.auxilium.service.HistoryService;
import com.gaconnecte.auxilium.service.TarifService;
import com.gaconnecte.auxilium.service.dto.TarifDTO;
import com.gaconnecte.auxilium.service.mapper.TarifMapper;
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
 * Test class for the TarifResource REST controller.
 *
 * @see TarifResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuxiliumApp.class)
public class TarifResourceIntTest {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final Integer DEFAULT_MAJORATION_FERIER = 1;
    private static final Integer UPDATED_MAJORATION_FERIER = 2;

    private static final Integer DEFAULT_MAJORATION_NUIT = 1;
    private static final Integer UPDATED_MAJORATION_NUIT = 2;

    private static final Double DEFAULT_TAUX_TARIF = 1D;
    private static final Double UPDATED_TAUX_TARIF = 2D;

    @Autowired
    private TarifRepository tarifRepository;

    @Autowired
    private TarifMapper tarifMapper;

    @Autowired
    private TarifService tarifService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTarifMockMvc;

    private Tarif tarif;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TarifResource tarifResource = new TarifResource(tarifService, historyService);
        this.restTarifMockMvc = MockMvcBuilders.standaloneSetup(tarifResource)
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
    public static Tarif createEntity(EntityManager em) {
        Tarif tarif = new Tarif()
            .libelle(DEFAULT_LIBELLE)
            .majorationFerier(DEFAULT_MAJORATION_FERIER)
            .majorationNuit(DEFAULT_MAJORATION_NUIT)
            .tauxTarif(DEFAULT_TAUX_TARIF);
        return tarif;
    }

    @Before
    public void initTest() {
        tarif = createEntity(em);
    }

    @Test
    @Transactional
    public void createTarif() throws Exception {
        int databaseSizeBeforeCreate = tarifRepository.findAll().size();

        // Create the Tarif
        TarifDTO tarifDTO = tarifMapper.toDto(tarif);
        restTarifMockMvc.perform(post("/api/tarifs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tarifDTO)))
            .andExpect(status().isCreated());

        // Validate the Tarif in the database
        List<Tarif> tarifList = tarifRepository.findAll();
        assertThat(tarifList).hasSize(databaseSizeBeforeCreate + 1);
        Tarif testTarif = tarifList.get(tarifList.size() - 1);
        assertThat(testTarif.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testTarif.getMajorationFerier()).isEqualTo(DEFAULT_MAJORATION_FERIER);
        assertThat(testTarif.getMajorationNuit()).isEqualTo(DEFAULT_MAJORATION_NUIT);
        assertThat(testTarif.getTauxTarif()).isEqualTo(DEFAULT_TAUX_TARIF);
    }

    @Test
    @Transactional
    public void createTarifWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tarifRepository.findAll().size();

        // Create the Tarif with an existing ID
        tarif.setId(1L);
        TarifDTO tarifDTO = tarifMapper.toDto(tarif);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTarifMockMvc.perform(post("/api/tarifs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tarifDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Tarif> tarifList = tarifRepository.findAll();
        assertThat(tarifList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = tarifRepository.findAll().size();
        // set the field null
        tarif.setLibelle(null);

        // Create the Tarif, which fails.
        TarifDTO tarifDTO = tarifMapper.toDto(tarif);

        restTarifMockMvc.perform(post("/api/tarifs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tarifDTO)))
            .andExpect(status().isBadRequest());

        List<Tarif> tarifList = tarifRepository.findAll();
        assertThat(tarifList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMajorationFerierIsRequired() throws Exception {
        int databaseSizeBeforeTest = tarifRepository.findAll().size();
        // set the field null
        tarif.setMajorationFerier(null);

        // Create the Tarif, which fails.
        TarifDTO tarifDTO = tarifMapper.toDto(tarif);

        restTarifMockMvc.perform(post("/api/tarifs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tarifDTO)))
            .andExpect(status().isBadRequest());

        List<Tarif> tarifList = tarifRepository.findAll();
        assertThat(tarifList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMajorationNuitIsRequired() throws Exception {
        int databaseSizeBeforeTest = tarifRepository.findAll().size();
        // set the field null
        tarif.setMajorationNuit(null);

        // Create the Tarif, which fails.
        TarifDTO tarifDTO = tarifMapper.toDto(tarif);

        restTarifMockMvc.perform(post("/api/tarifs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tarifDTO)))
            .andExpect(status().isBadRequest());

        List<Tarif> tarifList = tarifRepository.findAll();
        assertThat(tarifList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTauxTarifIsRequired() throws Exception {
        int databaseSizeBeforeTest = tarifRepository.findAll().size();
        // set the field null
        tarif.setTauxTarif(null);

        // Create the Tarif, which fails.
        TarifDTO tarifDTO = tarifMapper.toDto(tarif);

        restTarifMockMvc.perform(post("/api/tarifs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tarifDTO)))
            .andExpect(status().isBadRequest());

        List<Tarif> tarifList = tarifRepository.findAll();
        assertThat(tarifList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTarifs() throws Exception {
        // Initialize the database
        tarifRepository.saveAndFlush(tarif);

        // Get all the tarifList
        restTarifMockMvc.perform(get("/api/tarifs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tarif.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())))
            .andExpect(jsonPath("$.[*].majorationFerier").value(hasItem(DEFAULT_MAJORATION_FERIER)))
            .andExpect(jsonPath("$.[*].majorationNuit").value(hasItem(DEFAULT_MAJORATION_NUIT)))
            .andExpect(jsonPath("$.[*].tauxTarif").value(hasItem(DEFAULT_TAUX_TARIF.doubleValue())));
    }

    @Test
    @Transactional
    public void getTarif() throws Exception {
        // Initialize the database
        tarifRepository.saveAndFlush(tarif);

        // Get the tarif
        restTarifMockMvc.perform(get("/api/tarifs/{id}", tarif.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tarif.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()))
            .andExpect(jsonPath("$.majorationFerier").value(DEFAULT_MAJORATION_FERIER))
            .andExpect(jsonPath("$.majorationNuit").value(DEFAULT_MAJORATION_NUIT))
            .andExpect(jsonPath("$.tauxTarif").value(DEFAULT_TAUX_TARIF.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTarif() throws Exception {
        // Get the tarif
        restTarifMockMvc.perform(get("/api/tarifs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTarif() throws Exception {
        // Initialize the database
        tarifRepository.saveAndFlush(tarif);
        int databaseSizeBeforeUpdate = tarifRepository.findAll().size();

        // Update the tarif
        Tarif updatedTarif = tarifRepository.findOne(tarif.getId());
        updatedTarif
            .libelle(UPDATED_LIBELLE)
            .majorationFerier(UPDATED_MAJORATION_FERIER)
            .majorationNuit(UPDATED_MAJORATION_NUIT)
            .tauxTarif(UPDATED_TAUX_TARIF);
        TarifDTO tarifDTO = tarifMapper.toDto(updatedTarif);

        restTarifMockMvc.perform(put("/api/tarifs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tarifDTO)))
            .andExpect(status().isOk());

        // Validate the Tarif in the database
        List<Tarif> tarifList = tarifRepository.findAll();
        assertThat(tarifList).hasSize(databaseSizeBeforeUpdate);
        Tarif testTarif = tarifList.get(tarifList.size() - 1);
        assertThat(testTarif.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testTarif.getMajorationFerier()).isEqualTo(UPDATED_MAJORATION_FERIER);
        assertThat(testTarif.getMajorationNuit()).isEqualTo(UPDATED_MAJORATION_NUIT);
        assertThat(testTarif.getTauxTarif()).isEqualTo(UPDATED_TAUX_TARIF);
    }

    @Test
    @Transactional
    public void updateNonExistingTarif() throws Exception {
        int databaseSizeBeforeUpdate = tarifRepository.findAll().size();

        // Create the Tarif
        TarifDTO tarifDTO = tarifMapper.toDto(tarif);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTarifMockMvc.perform(put("/api/tarifs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tarifDTO)))
            .andExpect(status().isCreated());

        // Validate the Tarif in the database
        List<Tarif> tarifList = tarifRepository.findAll();
        assertThat(tarifList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTarif() throws Exception {
        // Initialize the database
        tarifRepository.saveAndFlush(tarif);
        int databaseSizeBeforeDelete = tarifRepository.findAll().size();

        // Get the tarif
        restTarifMockMvc.perform(delete("/api/tarifs/{id}", tarif.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Tarif> tarifList = tarifRepository.findAll();
        assertThat(tarifList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tarif.class);
        Tarif tarif1 = new Tarif();
        tarif1.setId(1L);
        Tarif tarif2 = new Tarif();
        tarif2.setId(tarif1.getId());
        assertThat(tarif1).isEqualTo(tarif2);
        tarif2.setId(2L);
        assertThat(tarif1).isNotEqualTo(tarif2);
        tarif1.setId(null);
        assertThat(tarif1).isNotEqualTo(tarif2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TarifDTO.class);
        TarifDTO tarifDTO1 = new TarifDTO();
        tarifDTO1.setId(1L);
        TarifDTO tarifDTO2 = new TarifDTO();
        assertThat(tarifDTO1).isNotEqualTo(tarifDTO2);
        tarifDTO2.setId(tarifDTO1.getId());
        assertThat(tarifDTO1).isEqualTo(tarifDTO2);
        tarifDTO2.setId(2L);
        assertThat(tarifDTO1).isNotEqualTo(tarifDTO2);
        tarifDTO1.setId(null);
        assertThat(tarifDTO1).isNotEqualTo(tarifDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(tarifMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(tarifMapper.fromId(null)).isNull();
    }
}
