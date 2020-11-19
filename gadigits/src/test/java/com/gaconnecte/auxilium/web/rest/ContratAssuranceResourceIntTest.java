/*package com.gaconnecte.auxilium.web.rest;

import com.gaconnecte.auxilium.AuxiliumApp;

import com.gaconnecte.auxilium.domain.ContratAssurance;
import com.gaconnecte.auxilium.domain.RefTypeContrat;
import com.gaconnecte.auxilium.domain.RefNatureContrat;
import com.gaconnecte.auxilium.domain.RefAgence;
import com.gaconnecte.auxilium.domain.RefUsageContrat;
import com.gaconnecte.auxilium.domain.VehiculeAssure;
import com.gaconnecte.auxilium.domain.Assure;
import com.gaconnecte.auxilium.domain.RefFractionnement;
import com.gaconnecte.auxilium.repository.ContratAssuranceRepository;
import com.gaconnecte.auxilium.service.ContratAssuranceService;
import com.gaconnecte.auxilium.repository.search.ContratAssuranceSearchRepository;
import com.gaconnecte.auxilium.service.dto.ContratAssuranceDTO;
import com.gaconnecte.auxilium.service.mapper.ContratAssuranceMapper;
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

/*
 * Test class for the ContratAssuranceResource REST controller.
 *
 * @see ContratAssuranceResource
 */
/*@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuxiliumApp.class)
public class ContratAssuranceResourceIntTest {

    private static final Double DEFAULT_NUMERO_CONTRAT = 100000000000000000D;
    private static final Double UPDATED_NUMERO_CONTRAT = 100000000000000000D;

    private static final LocalDate DEFAULT_DEBUT_VALIDITE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DEBUT_VALIDITE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_FIN_VALIDITE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FIN_VALIDITE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_IS_SUSPENDU = false;
    private static final Boolean UPDATED_IS_SUSPENDU = true;

    private static final Boolean DEFAULT_IS_DELETE = false;
    private static final Boolean UPDATED_IS_DELETE = true;

    private static final Double DEFAULT_VALEUR_NEUF = 100000000000000000D;
    private static final Double UPDATED_VALEUR_NEUF = 100000000000000000D;

    private static final Double DEFAULT_FRANCHISE = 100000000000000000D;
    private static final Double UPDATED_FRANCHISE = 100000000000000000D;

    private static final Double DEFAULT_CAPITAL_DC = 100000000000000000D;
    private static final Double UPDATED_CAPITAL_DC = 100000000000000000D;

    private static final Double DEFAULT_VALEUR_VENALE = 100000000000000000D;
    private static final Double UPDATED_VALEUR_VENALE = 100000000000000000D;

    private static final String DEFAULT_SOUSCRIPTEUR = "AAAAAAAAAA";
    private static final String UPDATED_SOUSCRIPTEUR = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENTAIRE = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTAIRE = "BBBBBBBBBB";

    @Autowired
    private ContratAssuranceRepository contratAssuranceRepository;

    @Autowired
    private ContratAssuranceMapper contratAssuranceMapper;

    @Autowired
    private ContratAssuranceService contratAssuranceService;

    @Autowired
    private ContratAssuranceSearchRepository contratAssuranceSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restContratAssuranceMockMvc;

    private ContratAssurance contratAssurance;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ContratAssuranceResource contratAssuranceResource = new ContratAssuranceResource(contratAssuranceService);
        this.restContratAssuranceMockMvc = MockMvcBuilders.standaloneSetup(contratAssuranceResource)
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
  /*  public static ContratAssurance createEntity(EntityManager em) {
        ContratAssurance contratAssurance = new ContratAssurance()
            .numeroContrat(DEFAULT_NUMERO_CONTRAT)
            .debutValidite(DEFAULT_DEBUT_VALIDITE)
            .finValidite(DEFAULT_FIN_VALIDITE)
            .isSuspendu(DEFAULT_IS_SUSPENDU)
            .isDelete(DEFAULT_IS_DELETE)
            .valeurNeuf(DEFAULT_VALEUR_NEUF)
            .franchise(DEFAULT_FRANCHISE)
            .capitalDc(DEFAULT_CAPITAL_DC)
            .valeurVenale(DEFAULT_VALEUR_VENALE)
            .souscripteur(DEFAULT_SOUSCRIPTEUR)
            .commentaire(DEFAULT_COMMENTAIRE);
        // Add required entity
        RefTypeContrat type = RefTypeContratResourceIntTest.createEntity(em);
        em.persist(type);
        em.flush();
        contratAssurance.setType(type);
        // Add required entity
        RefNatureContrat nature = RefNatureContratResourceIntTest.createEntity(em);
        em.persist(nature);
        em.flush();
        contratAssurance.setNature(nature);
        // Add required entity
        RefAgence agence = RefAgenceResourceIntTest.createEntity(em);
        em.persist(agence);
        em.flush();
        contratAssurance.setAgence(agence);
        // Add required entity
        RefUsageContrat usage = RefUsageContratResourceIntTest.createEntity(em);
        em.persist(usage);
        em.flush();
        contratAssurance.setUsage(usage);
        // Add required entity
        VehiculeAssure vehicule = VehiculeAssureResourceIntTest.createEntity(em);
        em.persist(vehicule);
        em.flush();
        contratAssurance.setVehicule(vehicule);
        // Add required entity
        Assure assure = AssureResourceIntTest.createEntity(em);
        em.persist(assure);
        em.flush();
        contratAssurance.setAssure(assure);
        // Add required entity
        RefFractionnement fractionnement = RefFractionnementResourceIntTest.createEntity(em);
        em.persist(fractionnement);
        em.flush();
        contratAssurance.setFractionnement(fractionnement);
        return contratAssurance;
    }

    @Before
    public void initTest() {
        contratAssuranceSearchRepository.deleteAll();
        contratAssurance = createEntity(em);
    }

    @Test
    @Transactional
    public void createContratAssurance() throws Exception {
        int databaseSizeBeforeCreate = contratAssuranceRepository.findAll().size();

        // Create the ContratAssurance
        ContratAssuranceDTO contratAssuranceDTO = contratAssuranceMapper.toDto(contratAssurance);
        restContratAssuranceMockMvc.perform(post("/api/contrat-assurances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contratAssuranceDTO)))
            .andExpect(status().isCreated());

        // Validate the ContratAssurance in the database
        List<ContratAssurance> contratAssuranceList = contratAssuranceRepository.findAll();
        assertThat(contratAssuranceList).hasSize(databaseSizeBeforeCreate + 1);
        ContratAssurance testContratAssurance = contratAssuranceList.get(contratAssuranceList.size() - 1);
        assertThat(testContratAssurance.getNumeroContrat()).isEqualTo(DEFAULT_NUMERO_CONTRAT);
        assertThat(testContratAssurance.getDebutValidite()).isEqualTo(DEFAULT_DEBUT_VALIDITE);
        assertThat(testContratAssurance.getFinValidite()).isEqualTo(DEFAULT_FIN_VALIDITE);
        assertThat(testContratAssurance.isIsSuspendu()).isEqualTo(DEFAULT_IS_SUSPENDU);
        assertThat(testContratAssurance.isIsDelete()).isEqualTo(DEFAULT_IS_DELETE);
        assertThat(testContratAssurance.getValeurNeuf()).isEqualTo(DEFAULT_VALEUR_NEUF);
        assertThat(testContratAssurance.getFranchise()).isEqualTo(DEFAULT_FRANCHISE);
        assertThat(testContratAssurance.getCapitalDc()).isEqualTo(DEFAULT_CAPITAL_DC);
        assertThat(testContratAssurance.getValeurVenale()).isEqualTo(DEFAULT_VALEUR_VENALE);
        assertThat(testContratAssurance.getSouscripteur()).isEqualTo(DEFAULT_SOUSCRIPTEUR);
        assertThat(testContratAssurance.getCommentaire()).isEqualTo(DEFAULT_COMMENTAIRE);

        // Validate the ContratAssurance in Elasticsearch
        ContratAssurance contratAssuranceEs = contratAssuranceSearchRepository.findOne(testContratAssurance.getId());
        assertThat(contratAssuranceEs).isEqualToComparingFieldByField(testContratAssurance);
    }

    @Test
    @Transactional
    public void createContratAssuranceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contratAssuranceRepository.findAll().size();

        // Create the ContratAssurance with an existing ID
        contratAssurance.setId(1L);
        ContratAssuranceDTO contratAssuranceDTO = contratAssuranceMapper.toDto(contratAssurance);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContratAssuranceMockMvc.perform(post("/api/contrat-assurances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contratAssuranceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ContratAssurance> contratAssuranceList = contratAssuranceRepository.findAll();
        assertThat(contratAssuranceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNumeroContratIsRequired() throws Exception {
        int databaseSizeBeforeTest = contratAssuranceRepository.findAll().size();
        // set the field null
        contratAssurance.setNumeroContrat(null);

        // Create the ContratAssurance, which fails.
        ContratAssuranceDTO contratAssuranceDTO = contratAssuranceMapper.toDto(contratAssurance);

        restContratAssuranceMockMvc.perform(post("/api/contrat-assurances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contratAssuranceDTO)))
            .andExpect(status().isBadRequest());

        List<ContratAssurance> contratAssuranceList = contratAssuranceRepository.findAll();
        assertThat(contratAssuranceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDebutValiditeIsRequired() throws Exception {
        int databaseSizeBeforeTest = contratAssuranceRepository.findAll().size();
        // set the field null
        contratAssurance.setDebutValidite(null);

        // Create the ContratAssurance, which fails.
        ContratAssuranceDTO contratAssuranceDTO = contratAssuranceMapper.toDto(contratAssurance);

        restContratAssuranceMockMvc.perform(post("/api/contrat-assurances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contratAssuranceDTO)))
            .andExpect(status().isBadRequest());

        List<ContratAssurance> contratAssuranceList = contratAssuranceRepository.findAll();
        assertThat(contratAssuranceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFinValiditeIsRequired() throws Exception {
        int databaseSizeBeforeTest = contratAssuranceRepository.findAll().size();
        // set the field null
        contratAssurance.setFinValidite(null);

        // Create the ContratAssurance, which fails.
        ContratAssuranceDTO contratAssuranceDTO = contratAssuranceMapper.toDto(contratAssurance);

        restContratAssuranceMockMvc.perform(post("/api/contrat-assurances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contratAssuranceDTO)))
            .andExpect(status().isBadRequest());

        List<ContratAssurance> contratAssuranceList = contratAssuranceRepository.findAll();
        assertThat(contratAssuranceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllContratAssurances() throws Exception {
        // Initialize the database
        contratAssuranceRepository.saveA  ndFlush(contratAssurance);

        // Get all the contratAssuranceList
        restContratAssuranceMockMvc.perform(get("/api/contrat-assurances?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contratAssurance.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroContrat").value(hasItem(DEFAULT_NUMERO_CONTRAT.doubleValue())))
            .andExpect(jsonPath("$.[*].debutValidite").value(hasItem(DEFAULT_DEBUT_VALIDITE.toString())))
            .andExpect(jsonPath("$.[*].finValidite").value(hasItem(DEFAULT_FIN_VALIDITE.toString())))
            .andExpect(jsonPath("$.[*].isSuspendu").value(hasItem(DEFAULT_IS_SUSPENDU.booleanValue())))
            .andExpect(jsonPath("$.[*].isDelete").value(hasItem(DEFAULT_IS_DELETE.booleanValue())))
            .andExpect(jsonPath("$.[*].valeurNeuf").value(hasItem(DEFAULT_VALEUR_NEUF.doubleValue())))
            .andExpect(jsonPath("$.[*].franchise").value(hasItem(DEFAULT_FRANCHISE.doubleValue())))
            .andExpect(jsonPath("$.[*].capitalDc").value(hasItem(DEFAULT_CAPITAL_DC.doubleValue())))
            .andExpect(jsonPath("$.[*].valeurVenale").value(hasItem(DEFAULT_VALEUR_VENALE.doubleValue())))
            .andExpect(jsonPath("$.[*].souscripteur").value(hasItem(DEFAULT_SOUSCRIPTEUR.toString())))
            .andExpect(jsonPath("$.[*].commentaire").value(hasItem(DEFAULT_COMMENTAIRE.toString())));
    }

    @Test
    @Transactional
    public void getContratAssurance() throws Exception {
        // Initialize the database
        contratAssuranceRepository.saveAndFlush(contratAssurance);

        // Get the contratAssurance
        restContratAssuranceMockMvc.perform(get("/api/contrat-assurances/{id}", contratAssurance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(contratAssurance.getId().intValue()))
            .andExpect(jsonPath("$.numeroContrat").value(DEFAULT_NUMERO_CONTRAT.doubleValue()))
            .andExpect(jsonPath("$.debutValidite").value(DEFAULT_DEBUT_VALIDITE.toString()))
            .andExpect(jsonPath("$.finValidite").value(DEFAULT_FIN_VALIDITE.toString()))
            .andExpect(jsonPath("$.isSuspendu").value(DEFAULT_IS_SUSPENDU.booleanValue()))
            .andExpect(jsonPath("$.isDelete").value(DEFAULT_IS_DELETE.booleanValue()))
            .andExpect(jsonPath("$.valeurNeuf").value(DEFAULT_VALEUR_NEUF.doubleValue()))
            .andExpect(jsonPath("$.franchise").value(DEFAULT_FRANCHISE.doubleValue()))
            .andExpect(jsonPath("$.capitalDc").value(DEFAULT_CAPITAL_DC.doubleValue()))
            .andExpect(jsonPath("$.valeurVenale").value(DEFAULT_VALEUR_VENALE.doubleValue()))
            .andExpect(jsonPath("$.souscripteur").value(DEFAULT_SOUSCRIPTEUR.toString()))
            .andExpect(jsonPath("$.commentaire").value(DEFAULT_COMMENTAIRE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingContratAssurance() throws Exception {
        // Get the contratAssurance
        restContratAssuranceMockMvc.perform(get("/api/contrat-assurances/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContratAssurance() throws Exception {
        // Initialize the database
        contratAssuranceRepository.saveAndFlush(contratAssurance);
        contratAssuranceSearchRepository.save(contratAssurance);
        int databaseSizeBeforeUpdate = contratAssuranceRepository.findAll().size();

        // Update the contratAssurance
        ContratAssurance updatedContratAssurance = contratAssuranceRepository.findOne(contratAssurance.getId());
        updatedContratAssurance
            .numeroContrat(UPDATED_NUMERO_CONTRAT)
            .debutValidite(UPDATED_DEBUT_VALIDITE)
            .finValidite(UPDATED_FIN_VALIDITE)
            .isSuspendu(UPDATED_IS_SUSPENDU)
            .isDelete(UPDATED_IS_DELETE)
            .valeurNeuf(UPDATED_VALEUR_NEUF)
            .franchise(UPDATED_FRANCHISE)
            .capitalDc(UPDATED_CAPITAL_DC)
            .valeurV
	@ManyToOneenale(UPDATED_VALEUR_VENALE)
            .souscripteur(UPDATED_SOUSCRIPTEUR)
            .commentaire(UPDATED_COMMENTAIRE);
        ContratAssuranceDTO contratAssuranceDTO = contratAssuranceMapper.toDto(updatedContratAssurance);

        restContratAssuranceMockMvc.perform(put("/api/contrat-assurances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contratAssuranceDTO)))
            .andExpect(status().isOk());

        // Validate the ContratAssurance in the database
        List<ContratAssurance> contratAssuranceList = contratAssuranceRepository.findAll();
        assertThat(contratAssuranceList).hasSize(databaseSizeBeforeUpdate);
        ContratAssurance testContratAssurance = contratAssuranceList.get(contratAssuranceList.size() - 1);
        assertThat(testContratAssurance.getNumeroContrat()).isEqualTo(UPDATED_NUMERO_CONTRAT);
        assertThat(testContratAssurance.getDebutValidite()).isEqualTo(UPDATED_DEBUT_VALIDITE);
        assertThat(testContratAssurance.getFinValidite()).isEqualTo(UPDATED_FIN_VALIDITE);
        assertThat(testContratAssurance.isIsSuspendu()).isEqualTo(UPDATED_IS_SUSPENDU);
        assertThat(testContratAssurance.isIsDelete()).isEqualTo(UPDATED_IS_DELETE);
        assertThat(testContratAssurance.getValeurNeuf()).isEqualTo(UPDATED_VALEUR_NEUF);
        assertThat(testContratAssurance.getFranchise()).isEqualTo(UPDATED_FRANCHISE);
        assertThat(testContratAssurance.getCapitalDc()).isEqualTo(UPDATED_CAPITAL_DC);
        assertThat(testContratAssurance.getValeurVenale()).isEqualTo(UPDATED_VALEUR_VENALE);
        assertThat(testContratAssurance.getSouscripteur()).isEqualTo(UPDATED_SOUSCRIPTEUR);
        assertThat(testContratAssurance.getCommentaire()).isEqualTo(UPDATED_COMMENTAIRE);

        // Validate the ContratAssurance in Elasticsearch
        ContratAssurance contratAssuranceEs = contratAssuranceSearchRepository.findOne(testContratAssurance.getId());
        assertThat(contratAssuranceEs).isEqualToComparingFieldByField(testContratAssurance);
    }

    @Test
    @Transactional
    public void updateNonExistingContratAssurance() throws Exception {
        int databaseSizeBeforeUpdate = contratAssuranceRepository.findAll().size();

        // Create the ContratAssurance
        ContratAssuranceDTO contratAssuranceDTO = contratAssuranceMapper.toDto(contratAssurance);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restContratAssuranceMockMvc.perform(put("/api/contrat-assurances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contratAssuranceDTO)))
            .andExpect(status().isCreated());

        // Validate the ContratAssurance in the database
        List<ContratAssurance> contratAssuranceList = contratAssuranceRepository.findAll();
        assertThat(contratAssuranceList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteContratAssurance() throws Exception {
        // Initialize the database
        contratAssuranceRepository.saveAndFlush(contratAssurance);
        contratAssuranceSearchRepository.save(contratAssurance);
        int databaseSizeBeforeDelete = contratAssuranceRepository.findAll().size();

        // Get the contratAssurance
        restContratAssuranceMockMvc.perform(delete("/api/contrat-assurances/{id}", contratAssurance.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean contratAssuranceExistsInEs = contratAssuranceSearchRepository.exists(contratAssurance.getId());
        assertThat(contratAssuranceExistsInEs).isFalse();

        // Validate the database is empty
        List<ContratAssurance> contratAssuranceList = contratAssuranceRepository.findAll();
        assertThat(contratAssuranceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchContratAssurance() throws Exception {
        // Initialize the database
        contratAssuranceRepository.saveAndFlush(contratAssurance);
        contratAssuranceSearchRepository.save(contratAssurance);

        // Search the contratAssurance
        restContratAssuranceMockMvc.perform(get("/api/_search/contrat-assurances?query=id:" + contratAssurance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contratAssurance.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroContrat").value(hasItem(DEFAULT_NUMERO_CONTRAT.doubleValue())))
            .andExpect(jsonPath("$.[*].debutValidite").value(hasItem(DEFAULT_DEBUT_VALIDITE.toString())))
            .andExpect(jsonPath("$.[*].finValidite").value(hasItem(DEFAULT_FIN_VALIDITE.toString())))
            .andExpect(jsonPath("$.[*].isSuspendu").value(hasItem(DEFAULT_IS_SUSPENDU.booleanValue())))
            .andExpect(jsonPath("$.[*].valeurNeuf").value(hasItem(DEFAULT_VALEUR_NEUF.doubleValue())))
            .andExpect(jsonPath("$.[*].franchise").value(hasItem(DEFAULT_FRANCHISE.doubleValue())))
            .andExpect(jsonPath("$.[*].capitalDc").value(hasItem(DEFAULT_CAPITAL_DC.doubleValue())))
            .andExpect(jsonPath("$.[*].valeurVenale").value(hasItem(DEFAULT_VALEUR_VENALE.doubleValue())))
            .andExpect(jsonPath("$.[*].souscripteur").value(hasItem(DEFAULT_SOUSCRIPTEUR.toString())))
            .andExpect(jsonPath("$.[*].commentaire").value(hasItem(DEFAULT_COMMENTAIRE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContratAssurance.class);
        ContratAssurance contratAssurance1 = new ContratAssurance();
        contratAssurance1.setId(1L);
        ContratAssurance contratAssurance2 = new ContratAssurance();
        contratAssurance2.setId(contratAssurance1.getId());
        assertThat(contratAssurance1).isEqualTo(contratAssurance2);
        contratAssurance2.setId(2L);
        assertThat(contratAssurance1).isNotEqualTo(contratAssurance2);
        contratAssurance1.setId(null);
        assertThat(contratAssurance1).isNotEqualTo(contratAssurance2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContratAssuranceDTO.class);
        ContratAssuranceDTO contratAssuranceDTO1 = new ContratAssuranceDTO();
        contratAssuranceDTO1.setId(1L);
        ContratAssuranceDTO contratAssuranceDTO2 = new ContratAssuranceDTO();
        assertThat(contratAssuranceDTO1).isNotEqualTo(contratAssuranceDTO2);
        contratAssuranceDTO2.setId(contratAssuranceDTO1.getId());
        assertThat(contratAssuranceDTO1).isEqualTo(contratAssuranceDTO2);
        contratAssuranceDTO2.setId(2L);
        assertThat(contratAssuranceDTO1).isNotEqualTo(contratAssuranceDTO2);
        contratAssuranceDTO1.setId(null);
        assertThat(contratAssuranceDTO1).isNotEqualTo(contratAssuranceDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(contratAssuranceMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(contratAssuranceMapper.fromId(null)).isNull();
    }
}
*/
