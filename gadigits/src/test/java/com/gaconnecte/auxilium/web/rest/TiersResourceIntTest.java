package com.gaconnecte.auxilium.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import javax.persistence.EntityManager;

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

import com.gaconnecte.auxilium.AuxiliumApp;
import com.gaconnecte.auxilium.domain.Tiers;
import com.gaconnecte.auxilium.repository.TiersRepository;
import com.gaconnecte.auxilium.repository.search.TiersSearchRepository;
import com.gaconnecte.auxilium.service.TiersService;
import com.gaconnecte.auxilium.service.dto.TiersDTO;
import com.gaconnecte.auxilium.service.mapper.TiersMapper;
import com.gaconnecte.auxilium.web.rest.errors.ExceptionTranslator;

/**
 * Test class for the TiersResource REST controller.
 *
 * @see TiersResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuxiliumApp.class)
public class TiersResourceIntTest {

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DEBUT_VALIDITE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DEBUT_VALIDITE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_FIN_VALIDITE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FIN_VALIDITE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_IMMATRICULATION = "9670TU536";
    private static final String UPDATED_IMMATRICULATION = "8596TU357";

    @Autowired
    private TiersRepository tiersRepository;

    @Autowired
    private TiersMapper tiersMapper;

    @Autowired
    private TiersService tiersService;

    @Autowired
    private TiersSearchRepository tiersSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTiersMockMvc;

    private Tiers tiers;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TiersResource tiersResource = new TiersResource(tiersService);
        this.restTiersMockMvc = MockMvcBuilders.standaloneSetup(tiersResource)
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
    public static Tiers createEntity(EntityManager em) {
        Tiers tiers = new Tiers()
            .prenom(DEFAULT_PRENOM)
            .nom(DEFAULT_NOM)
            .debutValidite(DEFAULT_DEBUT_VALIDITE)
            .finValidite(DEFAULT_FIN_VALIDITE)
            .immatriculation(DEFAULT_IMMATRICULATION);
       // RefCompagnie compagnie = RefCompagnieResourceIntTest.createEntity(em);
        em.persist(null);
        em.flush();
        //tiers.setCompagnie(compagnie);
        return tiers;
    }

    @Before
    public void initTest() {
        tiersSearchRepository.deleteAll();
        tiers = createEntity(em);
    }

    @Test
    @Transactional
    public void createTiers() throws Exception {
        int databaseSizeBeforeCreate = tiersRepository.findAll().size();

        // Create the Tiers
        TiersDTO tiersDTO = tiersMapper.toDto(tiers);
        restTiersMockMvc.perform(post("/api/tiers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tiersDTO)))
            .andExpect(status().isCreated());

        // Validate the Tiers in the database
        List<Tiers> tiersList = tiersRepository.findAll();
        assertThat(tiersList).hasSize(databaseSizeBeforeCreate + 1);
        Tiers testTiers = tiersList.get(tiersList.size() - 1);
        assertThat(testTiers.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testTiers.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testTiers.getDebutValidite()).isEqualTo(DEFAULT_DEBUT_VALIDITE);
        assertThat(testTiers.getFinValidite()).isEqualTo(DEFAULT_FIN_VALIDITE);
        assertThat(testTiers.getImmatriculation()).isEqualTo(DEFAULT_IMMATRICULATION);

        // Validate the Tiers in Elasticsearch
        Tiers tiersEs = tiersSearchRepository.findOne(testTiers.getId());
        assertThat(tiersEs).isEqualToComparingFieldByField(testTiers);
    }

    @Test
    @Transactional
    public void createTiersWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tiersRepository.findAll().size();

        // Create the Tiers with an existing ID
        tiers.setId(1L);
        TiersDTO tiersDTO = tiersMapper.toDto(tiers);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTiersMockMvc.perform(post("/api/tiers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tiersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Tiers> tiersList = tiersRepository.findAll();
        assertThat(tiersList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkPrenomIsRequired() throws Exception {
        int databaseSizeBeforeTest = tiersRepository.findAll().size();
        // set the field null
        tiers.setPrenom(null);

        // Create the Tiers, which fails.
        TiersDTO tiersDTO = tiersMapper.toDto(tiers);

        restTiersMockMvc.perform(post("/api/tiers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tiersDTO)))
            .andExpect(status().isBadRequest());

        List<Tiers> tiersList = tiersRepository.findAll();
        assertThat(tiersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = tiersRepository.findAll().size();
        // set the field null
        tiers.setNom(null);

        // Create the Tiers, which fails.
        TiersDTO tiersDTO = tiersMapper.toDto(tiers);

        restTiersMockMvc.perform(post("/api/tiers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tiersDTO)))
            .andExpect(status().isBadRequest());

        List<Tiers> tiersList = tiersRepository.findAll();
        assertThat(tiersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDebutValiditeIsRequired() throws Exception {
        int databaseSizeBeforeTest = tiersRepository.findAll().size();
        // set the field null
        tiers.setDebutValidite(null);

        // Create the Tiers, which fails.
        TiersDTO tiersDTO = tiersMapper.toDto(tiers);

        restTiersMockMvc.perform(post("/api/tiers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tiersDTO)))
            .andExpect(status().isBadRequest());

        List<Tiers> tiersList = tiersRepository.findAll();
        assertThat(tiersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFinValiditeIsRequired() throws Exception {
        int databaseSizeBeforeTest = tiersRepository.findAll().size();
        // set the field null
        tiers.setFinValidite(null);

        // Create the Tiers, which fails.
        TiersDTO tiersDTO = tiersMapper.toDto(tiers);

        restTiersMockMvc.perform(post("/api/tiers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tiersDTO)))
            .andExpect(status().isBadRequest());

        List<Tiers> tiersList = tiersRepository.findAll();
        assertThat(tiersList).hasSize(databaseSizeBeforeTest);
    }



    @Test
    @Transactional
    public void checkImmatriculationIsRequired() throws Exception {
        int databaseSizeBeforeTest = tiersRepository.findAll().size();
        // set the field null
        tiers.setImmatriculation(null);

        // Create the Tiers, which fails.
        TiersDTO tiersDTO = tiersMapper.toDto(tiers);

        restTiersMockMvc.perform(post("/api/tiers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tiersDTO)))
            .andExpect(status().isBadRequest());

        List<Tiers> tiersList = tiersRepository.findAll();
        assertThat(tiersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTiers() throws Exception {
        // Initialize the database
        tiersRepository.saveAndFlush(tiers);

        // Get all the tiersList
        restTiersMockMvc.perform(get("/api/tiers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tiers.getId().intValue())))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM.toString())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].debutValidite").value(hasItem(DEFAULT_DEBUT_VALIDITE.toString())))
            .andExpect(jsonPath("$.[*].finValidite").value(hasItem(DEFAULT_FIN_VALIDITE.toString())))
            .andExpect(jsonPath("$.[*].immatriculation").value(hasItem(DEFAULT_IMMATRICULATION.toString())));
    }

    @Test
    @Transactional
    public void getTiers() throws Exception {
        // Initialize the database
        tiersRepository.saveAndFlush(tiers);

        // Get the tiers
        restTiersMockMvc.perform(get("/api/tiers/{id}", tiers.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tiers.getId().intValue()))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM.toString()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.debutValidite").value(DEFAULT_DEBUT_VALIDITE.toString()))
            .andExpect(jsonPath("$.finValidite").value(DEFAULT_FIN_VALIDITE.toString()))
            .andExpect(jsonPath("$.immatriculation").value(DEFAULT_IMMATRICULATION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTiers() throws Exception {
        // Get the tiers
        restTiersMockMvc.perform(get("/api/tiers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTiers() throws Exception {
        // Initialize the database
        tiersRepository.saveAndFlush(tiers);
        tiersSearchRepository.save(tiers);
        int databaseSizeBeforeUpdate = tiersRepository.findAll().size();

        // Update the tiers
        Tiers updatedTiers = tiersRepository.findOne(tiers.getId());
        updatedTiers
            .prenom(UPDATED_PRENOM)
            .nom(UPDATED_NOM)
            .debutValidite(UPDATED_DEBUT_VALIDITE)
            .finValidite(UPDATED_FIN_VALIDITE)
            .immatriculation(UPDATED_IMMATRICULATION);
        TiersDTO tiersDTO = tiersMapper.toDto(updatedTiers);

        restTiersMockMvc.perform(put("/api/tiers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tiersDTO)))
            .andExpect(status().isOk());

        // Validate the Tiers in the database
        List<Tiers> tiersList = tiersRepository.findAll();
        assertThat(tiersList).hasSize(databaseSizeBeforeUpdate);
        Tiers testTiers = tiersList.get(tiersList.size() - 1);
        assertThat(testTiers.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testTiers.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testTiers.getDebutValidite()).isEqualTo(UPDATED_DEBUT_VALIDITE);
        assertThat(testTiers.getFinValidite()).isEqualTo(UPDATED_FIN_VALIDITE);
        assertThat(testTiers.getImmatriculation()).isEqualTo(UPDATED_IMMATRICULATION);

        // Validate the Tiers in Elasticsearch
        Tiers tiersEs = tiersSearchRepository.findOne(testTiers.getId());
        assertThat(tiersEs).isEqualToComparingFieldByField(testTiers);
    }

    @Test
    @Transactional
    public void updateNonExistingTiers() throws Exception {
        int databaseSizeBeforeUpdate = tiersRepository.findAll().size();

        // Create the Tiers
        TiersDTO tiersDTO = tiersMapper.toDto(tiers);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTiersMockMvc.perform(put("/api/tiers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tiersDTO)))
            .andExpect(status().isCreated());

        // Validate the Tiers in the database
        List<Tiers> tiersList = tiersRepository.findAll();
        assertThat(tiersList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTiers() throws Exception {
        // Initialize the database
        tiersRepository.saveAndFlush(tiers);
        tiersSearchRepository.save(tiers);
        int databaseSizeBeforeDelete = tiersRepository.findAll().size();

        // Get the tiers
        restTiersMockMvc.perform(delete("/api/tiers/{id}", tiers.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean tiersExistsInEs = tiersSearchRepository.exists(tiers.getId());
        assertThat(tiersExistsInEs).isFalse();

        // Validate the database is empty
        List<Tiers> tiersList = tiersRepository.findAll();
        assertThat(tiersList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchTiers() throws Exception {
        // Initialize the database
        tiersRepository.saveAndFlush(tiers);
        tiersSearchRepository.save(tiers);

        // Search the tiers
        restTiersMockMvc.perform(get("/api/_search/tiers?query=id:" + tiers.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tiers.getId().intValue())))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM.toString())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].debutValidite").value(hasItem(DEFAULT_DEBUT_VALIDITE.toString())))
            .andExpect(jsonPath("$.[*].finValidite").value(hasItem(DEFAULT_FIN_VALIDITE.toString())))
            .andExpect(jsonPath("$.[*].immatriculation").value(hasItem(DEFAULT_IMMATRICULATION.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tiers.class);
        Tiers tiers1 = new Tiers();
        tiers1.setId(1L);
        Tiers tiers2 = new Tiers();
        tiers2.setId(tiers1.getId());
        assertThat(tiers1).isEqualTo(tiers2);
        tiers2.setId(2L);
        assertThat(tiers1).isNotEqualTo(tiers2);
        tiers1.setId(null);
        assertThat(tiers1).isNotEqualTo(tiers2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TiersDTO.class);
        TiersDTO tiersDTO1 = new TiersDTO();
        tiersDTO1.setId(1L);
        TiersDTO tiersDTO2 = new TiersDTO();
        assertThat(tiersDTO1).isNotEqualTo(tiersDTO2);
        tiersDTO2.setId(tiersDTO1.getId());
        assertThat(tiersDTO1).isEqualTo(tiersDTO2);
        tiersDTO2.setId(2L);
        assertThat(tiersDTO1).isNotEqualTo(tiersDTO2);
        tiersDTO1.setId(null);
        assertThat(tiersDTO1).isNotEqualTo(tiersDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(tiersMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(tiersMapper.fromId(null)).isNull();
    }
}
