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
import com.gaconnecte.auxilium.domain.Devis;
import com.gaconnecte.auxilium.domain.Reparateur;
import com.gaconnecte.auxilium.repository.DevisRepository;
import com.gaconnecte.auxilium.service.DevisService;
import com.gaconnecte.auxilium.service.dto.DevisDTO;
import com.gaconnecte.auxilium.service.mapper.DevisMapper;
import com.gaconnecte.auxilium.web.rest.errors.ExceptionTranslator;

/**
 * Test class for the DevisResource REST controller.
 *
 * @see DevisResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuxiliumApp.class)
public class DevisResourceIntTest {

    private static final Double DEFAULT_TOTAL_TTC = 1D;
    private static final Double UPDATED_TOTAL_TTC = 1D;

    private static final Double DEFAULT_TOT_HT = 1D;
    private static final Double UPDATED_TOT_HT = 1D;

    private static final Boolean DEFAULT_IS_COMPLEMENTAIRE = false;
    private static final Boolean UPDATED_IS_COMPLEMENTAIRE = true;

    private static final Boolean DEFAULT_IS_SUPPRIME = false;
    private static final Boolean UPDATED_IS_SUPPRIME = true;

    private static final Float DEFAULT_TIMBRE = 1F;
    private static final Float UPDATED_TIMBRE = 1F;

    private static final String DEFAULT_COMMENTAIRE = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTAIRE = "BBBBBBBBBB";

    private static final Float DEFAULT_DROIT_TIMBRE = 1F;
    private static final Float UPDATED_DROIT_TIMBRE = 1F;

    @Autowired
    private DevisRepository devisRepository;

    @Autowired
    private DevisMapper devisMapper;

    @Autowired
    private DevisService devisService;

  

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDevisMockMvc;

    private Devis devis;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DevisResource devisResource = new DevisResource(devisService, null);
        this.restDevisMockMvc = MockMvcBuilders.standaloneSetup(devisResource)
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
    public static Devis createEntity(EntityManager em) {
        Devis devis = new Devis()
            .totalTtc(DEFAULT_TOTAL_TTC)
            .totHt(DEFAULT_TOT_HT)
            .isComplementaire(DEFAULT_IS_COMPLEMENTAIRE)
            .isSupprime(DEFAULT_IS_SUPPRIME)
            .timbre(DEFAULT_TIMBRE)
            .commentaire(DEFAULT_COMMENTAIRE)
            .droitTimbre(DEFAULT_DROIT_TIMBRE);
        // Add required entity
  //PrestationPEC prestation = PrestationPECResourceIntTest.createEntity(em);
   //    em.persist(prestation);
      // em.flush();
      // devis.setPrestation(prestation);
        // Add required entity
        Reparateur reparateur = ReparateurResourceIntTest.createEntity(em);
        em.persist(reparateur);
        em.flush();
        devis.setReparateur(reparateur);
        return devis;
    }

  

    @Test
    @Transactional
    public void createDevis() throws Exception {
        int databaseSizeBeforeCreate = devisRepository.findAll().size();

        // Create the Devis
        DevisDTO devisDTO = devisMapper.toDto(devis);
        restDevisMockMvc.perform(post("/api/devis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(devisDTO)))
            .andExpect(status().isCreated());

        // Validate the Devis in the database
        List<Devis> devisList = devisRepository.findAll();
        assertThat(devisList).hasSize(databaseSizeBeforeCreate + 1);
        Devis testDevis = devisList.get(devisList.size() - 1);
        assertThat(testDevis.getTotalTtc()).isEqualTo(DEFAULT_TOTAL_TTC);
        assertThat(testDevis.getTotHt()).isEqualTo(DEFAULT_TOT_HT);
        assertThat(testDevis.isIsComplementaire()).isEqualTo(DEFAULT_IS_COMPLEMENTAIRE);
        assertThat(testDevis.isIsSupprime()).isEqualTo(DEFAULT_IS_SUPPRIME);
        assertThat(testDevis.getTimbre()).isEqualTo(DEFAULT_TIMBRE);
        assertThat(testDevis.getCommentaire()).isEqualTo(DEFAULT_COMMENTAIRE);
        assertThat(testDevis.getDroitTimbre()).isEqualTo(DEFAULT_DROIT_TIMBRE);

        // Validate the Devis in Elasticsearch
        Devis devisEs = null;
        assertThat(devisEs).isEqualToComparingFieldByField(testDevis);
    }

    @Test
    @Transactional
    public void createDevisWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = devisRepository.findAll().size();

        // Create the Devis with an existing ID
        devis.setId(1L);
        DevisDTO devisDTO = devisMapper.toDto(devis);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDevisMockMvc.perform(post("/api/devis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(devisDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Devis> devisList = devisRepository.findAll();
        assertThat(devisList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTotalTtcIsRequired() throws Exception {
        int databaseSizeBeforeTest = devisRepository.findAll().size();
        // set the field null
        devis.setTotalTtc(null);

        // Create the Devis, which fails.
        DevisDTO devisDTO = devisMapper.toDto(devis);

        restDevisMockMvc.perform(post("/api/devis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(devisDTO)))
            .andExpect(status().isBadRequest());

        List<Devis> devisList = devisRepository.findAll();
        assertThat(devisList).hasSize(databaseSizeBeforeTest);
    }

   

    @Test
    @Transactional
    public void checkDroitTimbreIsRequired() throws Exception {
        int databaseSizeBeforeTest = devisRepository.findAll().size();
        // set the field null
        devis.setDroitTimbre(null);

        // Create the Devis, which fails.
        DevisDTO devisDTO = devisMapper.toDto(devis);

        restDevisMockMvc.perform(post("/api/devis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(devisDTO)))
            .andExpect(status().isBadRequest());

        List<Devis> devisList = devisRepository.findAll();
        assertThat(devisList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDevis() throws Exception {
        // Initialize the database
        devisRepository.saveAndFlush(devis);

        // Get all the devisList
        restDevisMockMvc.perform(get("/api/devis?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(devis.getId().intValue())))
            .andExpect(jsonPath("$.[*].totalTtc").value(hasItem(DEFAULT_TOTAL_TTC.doubleValue())))
            .andExpect(jsonPath("$.[*].totHt").value(hasItem(DEFAULT_TOT_HT.doubleValue())))
            .andExpect(jsonPath("$.[*].isComplementaire").value(hasItem(DEFAULT_IS_COMPLEMENTAIRE.booleanValue())))
            .andExpect(jsonPath("$.[*].isSupprime").value(hasItem(DEFAULT_IS_SUPPRIME.booleanValue())))
            .andExpect(jsonPath("$.[*].timbre").value(hasItem(DEFAULT_TIMBRE.doubleValue())))
            .andExpect(jsonPath("$.[*].commentaire").value(hasItem(DEFAULT_COMMENTAIRE.toString())))
            .andExpect(jsonPath("$.[*].droitTimbre").value(hasItem(DEFAULT_DROIT_TIMBRE.doubleValue())));
    }

    @Test
    @Transactional
    public void getDevis() throws Exception {
        // Initialize the database
        devisRepository.saveAndFlush(devis);

        // Get the devis
        restDevisMockMvc.perform(get("/api/devis/{id}", devis.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(devis.getId().intValue()))
            .andExpect(jsonPath("$.totalTtc").value(DEFAULT_TOTAL_TTC.doubleValue()))
            .andExpect(jsonPath("$.totHt").value(DEFAULT_TOT_HT.doubleValue()))
            .andExpect(jsonPath("$.isComplementaire").value(DEFAULT_IS_COMPLEMENTAIRE.booleanValue()))
            .andExpect(jsonPath("$.isSupprime").value(DEFAULT_IS_SUPPRIME.booleanValue()))
            .andExpect(jsonPath("$.timbre").value(DEFAULT_TIMBRE.doubleValue()))
            .andExpect(jsonPath("$.commentaire").value(DEFAULT_COMMENTAIRE.toString()))
            .andExpect(jsonPath("$.droitTimbre").value(DEFAULT_DROIT_TIMBRE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingDevis() throws Exception {
        // Get the devis
        restDevisMockMvc.perform(get("/api/devis/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDevis() throws Exception {
        // Initialize the database
        devisRepository.saveAndFlush(devis);

        int databaseSizeBeforeUpdate = devisRepository.findAll().size();

        // Update the devis
        Devis updatedDevis = devisRepository.findOne(devis.getId());
        updatedDevis
            .totalTtc(UPDATED_TOTAL_TTC)
            .totHt(UPDATED_TOT_HT)
            .isComplementaire(UPDATED_IS_COMPLEMENTAIRE)
            .isSupprime(UPDATED_IS_SUPPRIME)
            .timbre(UPDATED_TIMBRE)
            .commentaire(UPDATED_COMMENTAIRE)
            .droitTimbre(UPDATED_DROIT_TIMBRE);
        DevisDTO devisDTO = devisMapper.toDto(updatedDevis);

        restDevisMockMvc.perform(put("/api/devis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(devisDTO)))
            .andExpect(status().isOk());

        // Validate the Devis in the database
        List<Devis> devisList = devisRepository.findAll();
        assertThat(devisList).hasSize(databaseSizeBeforeUpdate);
        Devis testDevis = devisList.get(devisList.size() - 1);
        assertThat(testDevis.getTotalTtc()).isEqualTo(UPDATED_TOTAL_TTC);
        assertThat(testDevis.getTotHt()).isEqualTo(UPDATED_TOT_HT);
        assertThat(testDevis.isIsComplementaire()).isEqualTo(UPDATED_IS_COMPLEMENTAIRE);
        assertThat(testDevis.isIsSupprime()).isEqualTo(UPDATED_IS_SUPPRIME);
        assertThat(testDevis.getTimbre()).isEqualTo(UPDATED_TIMBRE);
        assertThat(testDevis.getCommentaire()).isEqualTo(UPDATED_COMMENTAIRE);
        assertThat(testDevis.getDroitTimbre()).isEqualTo(UPDATED_DROIT_TIMBRE);

        // Validate the Devis in Elasticsearch
        Devis devisEs = null;
        assertThat(devisEs).isEqualToComparingFieldByField(testDevis);
    }

    @Test
    @Transactional
    public void updateNonExistingDevis() throws Exception {
        int databaseSizeBeforeUpdate = devisRepository.findAll().size();

        // Create the Devis
        DevisDTO devisDTO = devisMapper.toDto(devis);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDevisMockMvc.perform(put("/api/devis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(devisDTO)))
            .andExpect(status().isCreated());

        // Validate the Devis in the database
        List<Devis> devisList = devisRepository.findAll();
        assertThat(devisList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDevis() throws Exception {
        // Initialize the database
        devisRepository.saveAndFlush(devis);
        //devisSearchRepository.save(devis);
        int databaseSizeBeforeDelete = devisRepository.findAll().size();

        // Get the devis
        restDevisMockMvc.perform(delete("/api/devis/{id}", devis.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
       // boolean devisExistsInEs = devisSearchRepository.exists(devis.getId());
       //(devisExistsInEs).isFalse();

        // Validate the database is empty
        List<Devis> devisList = devisRepository.findAll();
        assertThat(devisList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchDevis() throws Exception {
        // Initialize the database
        devisRepository.saveAndFlush(devis);
      //  devisSearchRepository.save(devis);

        // Search the devis
        restDevisMockMvc.perform(get("/api/_search/devis?query=id:" + devis.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(devis.getId().intValue())))
            .andExpect(jsonPath("$.[*].totalTtc").value(hasItem(DEFAULT_TOTAL_TTC.doubleValue())))
            .andExpect(jsonPath("$.[*].totHt").value(hasItem(DEFAULT_TOT_HT.doubleValue())))
            .andExpect(jsonPath("$.[*].isComplementaire").value(hasItem(DEFAULT_IS_COMPLEMENTAIRE.booleanValue())))
            .andExpect(jsonPath("$.[*].isSupprime").value(hasItem(DEFAULT_IS_SUPPRIME.booleanValue())))
            .andExpect(jsonPath("$.[*].timbre").value(hasItem(DEFAULT_TIMBRE.doubleValue())))
            .andExpect(jsonPath("$.[*].commentaire").value(hasItem(DEFAULT_COMMENTAIRE.toString())))
            .andExpect(jsonPath("$.[*].droitTimbre").value(hasItem(DEFAULT_DROIT_TIMBRE.doubleValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Devis.class);
        Devis devis1 = new Devis();
        devis1.setId(1L);
        Devis devis2 = new Devis();
        devis2.setId(devis1.getId());
        assertThat(devis1).isEqualTo(devis2);
        devis2.setId(2L);
        assertThat(devis1).isNotEqualTo(devis2);
        devis1.setId(null);
        assertThat(devis1).isNotEqualTo(devis2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DevisDTO.class);
        DevisDTO devisDTO1 = new DevisDTO();
        devisDTO1.setId(1L);
        DevisDTO devisDTO2 = new DevisDTO();
        assertThat(devisDTO1).isNotEqualTo(devisDTO2);
        devisDTO2.setId(devisDTO1.getId());
        assertThat(devisDTO1).isEqualTo(devisDTO2);
        devisDTO2.setId(2L);
        assertThat(devisDTO1).isNotEqualTo(devisDTO2);
        devisDTO1.setId(null);
        assertThat(devisDTO1).isNotEqualTo(devisDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(devisMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(devisMapper.fromId(null)).isNull();
    }
}