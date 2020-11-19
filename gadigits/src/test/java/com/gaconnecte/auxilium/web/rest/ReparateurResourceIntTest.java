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
import com.gaconnecte.auxilium.domain.Reparateur;
import com.gaconnecte.auxilium.repository.ReparateurRepository;
import com.gaconnecte.auxilium.service.ReparateurService;
import com.gaconnecte.auxilium.service.dto.ReparateurDTO;
import com.gaconnecte.auxilium.service.mapper.ReparateurMapper;
import com.gaconnecte.auxilium.web.rest.errors.ExceptionTranslator;

/**
 * Test class for the ReparateurResource REST controller.
 *
 * @see ReparateurResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuxiliumApp.class)
public class ReparateurResourceIntTest {

    private static final Integer DEFAULT_NMAX_AFFILIATION = 99999999;
    private static final Integer UPDATED_NMAX_AFFILIATION = 99999998;

    private static final LocalDate DEFAULT_DEBUT_CONGE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DEBUT_CONGE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DAT_FIN_CONGE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DAT_FIN_CONGE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_CREATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CREATION = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_IS_BLOQUE = false;
    private static final Boolean UPDATED_IS_BLOQUE = true;

    private static final Boolean DEFAULT_IS_CNG = false;
    private static final Boolean UPDATED_IS_CNG = true;

    @Autowired
    private ReparateurRepository reparateurRepository;

    @Autowired
    private ReparateurMapper reparateurMapper;

    @Autowired
    private ReparateurService reparateurService;

   
   // private ReparateurSearchRepository reparateurSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restReparateurMockMvc;

    private Reparateur reparateur;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ReparateurResource reparateurResource = new ReparateurResource(reparateurService, null);
        this.restReparateurMockMvc = MockMvcBuilders.standaloneSetup(reparateurResource)
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
    public static Reparateur createEntity(EntityManager em) {
        Reparateur reparateur = new Reparateur();
            //.nmaxAffiliation(DEFAULT_NMAX_AFFILIATION)
            //.debutConge(DEFAULT_DEBUT_CONGE)
            //.datFinConge(DEFAULT_DAT_FIN_CONGE)
           // .dateCreation(DEFAULT_DATE_CREATION)
           // .isBloque(DEFAULT_IS_BLOQUE)
           // .isCng(DEFAULT_IS_CNG);
        // Add required entity
     
       // em.persist(personneMorale);
       // em.flush();
        //reparateur.setPersonneMorale(personneMorale);
        return reparateur;
    }

    @Before
    public void initTest() {
        //reparateurSearchRepository.deleteAll();
        reparateur = createEntity(em);
    }

    @Test
    @Transactional
    public void createReparateur() throws Exception {
        int databaseSizeBeforeCreate = reparateurRepository.findAll().size();

        // Create the Reparateur
        ReparateurDTO reparateurDTO = reparateurMapper.toDto(reparateur);
        restReparateurMockMvc.perform(post("/api/reparateurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reparateurDTO)))
            .andExpect(status().isCreated());

        // Validate the Reparateur in the database
        List<Reparateur> reparateurList = reparateurRepository.findAll();
        assertThat(reparateurList).hasSize(databaseSizeBeforeCreate + 1);
        Reparateur testReparateur = reparateurList.get(reparateurList.size() - 1);
        //assertThat(testReparateur.getNmaxAffiliation()).isEqualTo(DEFAULT_NMAX_AFFILIATION);
       // assertThat(testReparateur.getDebutConge()).isEqualTo(DEFAULT_DEBUT_CONGE);
        //assertThat(testReparateur.getDatFinConge()).isEqualTo(DEFAULT_DAT_FIN_CONGE);
       // assertThat(testReparateur.getDateCreation()).isEqualTo(DEFAULT_DATE_CREATION);
       // assertThat(testReparateur.isIsBloque()).isEqualTo(DEFAULT_IS_BLOQUE);
       // assertThat(testReparateur.isIsCng()).isEqualTo(DEFAULT_IS_CNG);

        // Validate the Reparateur in Elasticsearch
      //  Reparateur reparateurEs = reparateurSearchRepository.findOne(testReparateur.getId());
      //  assertThat(reparateurEs).isEqualToComparingFieldByField(testReparateur);
    }

    @Test
    @Transactional
    public void createReparateurWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = reparateurRepository.findAll().size();

        // Create the Reparateur with an existing ID
        reparateur.setId(1L);
        ReparateurDTO reparateurDTO = reparateurMapper.toDto(reparateur);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReparateurMockMvc.perform(post("/api/reparateurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reparateurDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Reparateur> reparateurList = reparateurRepository.findAll();
        assertThat(reparateurList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDateCreationIsRequired() throws Exception {
        int databaseSizeBeforeTest = reparateurRepository.findAll().size();
        // set the field null
       // reparateur.setDateCreation(null);

        // Create the Reparateur, which fails.
        ReparateurDTO reparateurDTO = reparateurMapper.toDto(reparateur);

        restReparateurMockMvc.perform(post("/api/reparateurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reparateurDTO)))
            .andExpect(status().isBadRequest());

        List<Reparateur> reparateurList = reparateurRepository.findAll();
        assertThat(reparateurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllReparateurs() throws Exception {
        // Initialize the database
        reparateurRepository.saveAndFlush(reparateur);

        // Get all the reparateurList
        restReparateurMockMvc.perform(get("/api/reparateurs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reparateur.getId().intValue())))
            .andExpect(jsonPath("$.[*].nmaxAffiliation").value(hasItem(DEFAULT_NMAX_AFFILIATION)))
            .andExpect(jsonPath("$.[*].debutConge").value(hasItem(DEFAULT_DEBUT_CONGE.toString())))
            .andExpect(jsonPath("$.[*].datFinConge").value(hasItem(DEFAULT_DAT_FIN_CONGE.toString())))
            .andExpect(jsonPath("$.[*].dateCreation").value(hasItem(DEFAULT_DATE_CREATION.toString())))
            .andExpect(jsonPath("$.[*].isBloque").value(hasItem(DEFAULT_IS_BLOQUE.booleanValue())))
            .andExpect(jsonPath("$.[*].isCng").value(hasItem(DEFAULT_IS_CNG.booleanValue())));
    }

    @Test
    @Transactional
    public void getReparateur() throws Exception {
        // Initialize the database
        reparateurRepository.saveAndFlush(reparateur);

        // Get the reparateur
        restReparateurMockMvc.perform(get("/api/reparateurs/{id}", reparateur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(reparateur.getId().intValue()))
            .andExpect(jsonPath("$.nmaxAffiliation").value(DEFAULT_NMAX_AFFILIATION))
            .andExpect(jsonPath("$.debutConge").value(DEFAULT_DEBUT_CONGE.toString()))
            .andExpect(jsonPath("$.datFinConge").value(DEFAULT_DAT_FIN_CONGE.toString()))
            .andExpect(jsonPath("$.dateCreation").value(DEFAULT_DATE_CREATION.toString()))
            .andExpect(jsonPath("$.isBloque").value(DEFAULT_IS_BLOQUE.booleanValue()))
            .andExpect(jsonPath("$.isCng").value(DEFAULT_IS_CNG.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingReparateur() throws Exception {
        // Get the reparateur
        restReparateurMockMvc.perform(get("/api/reparateurs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReparateur() throws Exception {
        // Initialize the database
        reparateurRepository.saveAndFlush(reparateur);
       // reparateurSearchRepository.save(reparateur);
        int databaseSizeBeforeUpdate = reparateurRepository.findAll().size();

        // Update the reparateur
        Reparateur updatedReparateur = reparateurRepository.findOne(reparateur.getId());
      //  updatedReparateur
          //  .nmaxAffiliation(UPDATED_NMAX_AFFILIATION)
          //  .debutConge(UPDATED_DEBUT_CONGE)
           // .datFinConge(UPDATED_DAT_FIN_CONGE)
           // .dateCreation(UPDATED_DATE_CREATION)
         //   .isBloque(UPDATED_IS_BLOQUE)
           // .isCng(UPDATED_IS_CNG);
        ReparateurDTO reparateurDTO = reparateurMapper.toDto(updatedReparateur);

        restReparateurMockMvc.perform(put("/api/reparateurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reparateurDTO)))
            .andExpect(status().isOk());

        // Validate the Reparateur in the database
        List<Reparateur> reparateurList = reparateurRepository.findAll();
        assertThat(reparateurList).hasSize(databaseSizeBeforeUpdate);
        Reparateur testReparateur = reparateurList.get(reparateurList.size() - 1);
        //assertThat(testReparateur.getNmaxAffiliation()).isEqualTo(UPDATED_NMAX_AFFILIATION);
       // assertThat(testReparateur.getDebutConge()).isEqualTo(UPDATED_DEBUT_CONGE);
        //assertThat(testReparateur.getDatFinConge()).isEqualTo(UPDATED_DAT_FIN_CONGE);
        //assertThat(testReparateur.getDateCreation()).isEqualTo(UPDATED_DATE_CREATION);
        //assertThat(testReparateur.isIsBloque()).isEqualTo(UPDATED_IS_BLOQUE);
      //  assertThat(testReparateur.isIsCng()).isEqualTo(UPDATED_IS_CNG);

        // Validate the Reparateur in Elasticsearch
       // Reparateur reparateurEs = reparateurSearchRepository.findOne(testReparateur.getId());
       // assertThat(reparateurEs).isEqualToComparingFieldByField(testReparateur);
    }

    @Test
    @Transactional
    public void updateNonExistingReparateur() throws Exception {
        int databaseSizeBeforeUpdate = reparateurRepository.findAll().size();

        // Create the Reparateur
        ReparateurDTO reparateurDTO = reparateurMapper.toDto(reparateur);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restReparateurMockMvc.perform(put("/api/reparateurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reparateurDTO)))
            .andExpect(status().isCreated());

        // Validate the Reparateur in the database
        List<Reparateur> reparateurList = reparateurRepository.findAll();
        assertThat(reparateurList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteReparateur() throws Exception {
        // Initialize the database
        reparateurRepository.saveAndFlush(reparateur);
       // reparateurSearchRepository.save(reparateur);
        int databaseSizeBeforeDelete = reparateurRepository.findAll().size();

        // Get the reparateur
        restReparateurMockMvc.perform(delete("/api/reparateurs/{id}", reparateur.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
       // boolean reparateurExistsInEs = reparateurSearchRepository.exists(reparateur.getId());
        //assertThat(reparateurExistsInEs).isFalse();

        // Validate the database is empty
        List<Reparateur> reparateurList = reparateurRepository.findAll();
        assertThat(reparateurList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchReparateur() throws Exception {
        // Initialize the database
        reparateurRepository.saveAndFlush(reparateur);
        //reparateurSearchRepository.save(reparateur);

        // Search the reparateur
        restReparateurMockMvc.perform(get("/api/_search/reparateurs?query=id:" + reparateur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reparateur.getId().intValue())))
            .andExpect(jsonPath("$.[*].nmaxAffiliation").value(hasItem(DEFAULT_NMAX_AFFILIATION)))
            .andExpect(jsonPath("$.[*].debutConge").value(hasItem(DEFAULT_DEBUT_CONGE.toString())))
            .andExpect(jsonPath("$.[*].datFinConge").value(hasItem(DEFAULT_DAT_FIN_CONGE.toString())))
            .andExpect(jsonPath("$.[*].dateCreation").value(hasItem(DEFAULT_DATE_CREATION.toString())))
            .andExpect(jsonPath("$.[*].isBloque").value(hasItem(DEFAULT_IS_BLOQUE.booleanValue())))
            .andExpect(jsonPath("$.[*].isCng").value(hasItem(DEFAULT_IS_CNG.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Reparateur.class);
        Reparateur reparateur1 = new Reparateur();
        reparateur1.setId(1L);
        Reparateur reparateur2 = new Reparateur();
        reparateur2.setId(reparateur1.getId());
        assertThat(reparateur1).isEqualTo(reparateur2);
        reparateur2.setId(2L);
        assertThat(reparateur1).isNotEqualTo(reparateur2);
        reparateur1.setId(null);
        assertThat(reparateur1).isNotEqualTo(reparateur2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReparateurDTO.class);
        ReparateurDTO reparateurDTO1 = new ReparateurDTO();
        reparateurDTO1.setId(1L);
        ReparateurDTO reparateurDTO2 = new ReparateurDTO();
        assertThat(reparateurDTO1).isNotEqualTo(reparateurDTO2);
        reparateurDTO2.setId(reparateurDTO1.getId());
        assertThat(reparateurDTO1).isEqualTo(reparateurDTO2);
        reparateurDTO2.setId(2L);
        assertThat(reparateurDTO1).isNotEqualTo(reparateurDTO2);
        reparateurDTO1.setId(null);
        assertThat(reparateurDTO1).isNotEqualTo(reparateurDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(reparateurMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(reparateurMapper.fromId(null)).isNull();
    }
}
