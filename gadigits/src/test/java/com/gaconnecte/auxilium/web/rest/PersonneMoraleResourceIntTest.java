package com.gaconnecte.auxilium.web.rest;

import com.gaconnecte.auxilium.AuxiliumApp;

import com.gaconnecte.auxilium.domain.PersonneMorale;
import com.gaconnecte.auxilium.repository.PersonneMoraleRepository;
import com.gaconnecte.auxilium.service.PersonneMoraleService;
import com.gaconnecte.auxilium.repository.search.PersonneMoraleSearchRepository;
import com.gaconnecte.auxilium.service.dto.PersonneMoraleDTO;
import com.gaconnecte.auxilium.service.mapper.PersonneMoraleMapper;
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
 * Test class for the PersonneMoraleResource REST controller.
 *
 * @see PersonneMoraleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuxiliumApp.class)
public class PersonneMoraleResourceIntTest {

    private static final String DEFAULT_RAISON_SOCIALE = "AAAAAAAAAA";
    private static final String UPDATED_RAISON_SOCIALE = "BBBBBBBBBB";

    private static final String DEFAULT_REGISTRE_COMMERCE = "AAAAAAAAAA";
    private static final String UPDATED_REGISTRE_COMMERCE = "BBBBBBBBBB";

    private static final String DEFAULT_NUM_ETABLISSEMENT = "AAA";
    private static final String UPDATED_NUM_ETABLISSEMENT = "BBB";

    private static final String DEFAULT_CODE_CATEGORIE = "A";
    private static final String UPDATED_CODE_CATEGORIE = "B";

    private static final String DEFAULT_CODE_TVA = "A";
    private static final String UPDATED_CODE_TVA = "B";

    private static final String DEFAULT_MATRICULE_FISCALE = "AAAAAAAAA";
    private static final String UPDATED_MATRICULE_FISCALE = "BBBBBBBBB";

    private static final String DEFAULT_ADRESSE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE = "BBBBBBBBBB";



    private static final String DEFAULT_BANQUE = "AAAAAAAAAA";
    private static final String UPDATED_BANQUE = "BBBBBBBBBB";

    private static final String DEFAULT_AGENCE = "AAAAAAAAAA";
    private static final String UPDATED_AGENCE = "BBBBBBBBBB";

    private static final Double DEFAULT_RIB = 100000000000000000D;
    private static final Double UPDATED_RIB = 100000000000000000D;

    private static final Boolean DEFAULT_IS_ASSUJETTIE_TVA = false;
    private static final Boolean UPDATED_IS_ASSUJETTIE_TVA = true;

    @Autowired
    private PersonneMoraleRepository personneMoraleRepository;

    @Autowired
    private PersonneMoraleMapper personneMoraleMapper;

    @Autowired
    private PersonneMoraleService personneMoraleService;

    @Autowired
    private PersonneMoraleSearchRepository personneMoraleSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPersonneMoraleMockMvc;

    private PersonneMorale personneMorale;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PersonneMoraleResource personneMoraleResource = new PersonneMoraleResource(personneMoraleService);
        this.restPersonneMoraleMockMvc = MockMvcBuilders.standaloneSetup(personneMoraleResource)
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
    public static PersonneMorale createEntity(EntityManager em) {
        PersonneMorale personneMorale = new PersonneMorale()
            .raisonSociale(DEFAULT_RAISON_SOCIALE)
            .registreCommerce(DEFAULT_REGISTRE_COMMERCE)
            .numEtablissement(DEFAULT_NUM_ETABLISSEMENT)
           
            .matriculeFiscale(DEFAULT_MATRICULE_FISCALE)
            .adresse(DEFAULT_ADRESSE)
            .banque(DEFAULT_BANQUE)
            .agence(DEFAULT_AGENCE)
            
            .isAssujettieTva(DEFAULT_IS_ASSUJETTIE_TVA);
        return personneMorale;
    }

    @Before
    public void initTest() {
        personneMoraleSearchRepository.deleteAll();
        personneMorale = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersonneMorale() throws Exception {
        int databaseSizeBeforeCreate = personneMoraleRepository.findAll().size();

        // Create the PersonneMorale
        PersonneMoraleDTO personneMoraleDTO = personneMoraleMapper.toDto(personneMorale);
        restPersonneMoraleMockMvc.perform(post("/api/personne-morales")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personneMoraleDTO)))
            .andExpect(status().isCreated());

        // Validate the PersonneMorale in the database
        List<PersonneMorale> personneMoraleList = personneMoraleRepository.findAll();
        assertThat(personneMoraleList).hasSize(databaseSizeBeforeCreate + 1);
        PersonneMorale testPersonneMorale = personneMoraleList.get(personneMoraleList.size() - 1);
        assertThat(testPersonneMorale.getRaisonSociale()).isEqualTo(DEFAULT_RAISON_SOCIALE);
        assertThat(testPersonneMorale.getRegistreCommerce()).isEqualTo(DEFAULT_REGISTRE_COMMERCE);
        assertThat(testPersonneMorale.getNumEtablissement()).isEqualTo(DEFAULT_NUM_ETABLISSEMENT);
        assertThat(testPersonneMorale.getCodeCategorie()).isEqualTo(DEFAULT_CODE_CATEGORIE);
        assertThat(testPersonneMorale.getMatriculeFiscale()).isEqualTo(DEFAULT_MATRICULE_FISCALE);
        assertThat(testPersonneMorale.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
        assertThat(testPersonneMorale.getBanque()).isEqualTo(DEFAULT_BANQUE);
        assertThat(testPersonneMorale.getAgence()).isEqualTo(DEFAULT_AGENCE);
        assertThat(testPersonneMorale.getRib()).isEqualTo(DEFAULT_RIB);
        assertThat(testPersonneMorale.isIsAssujettieTva()).isEqualTo(DEFAULT_IS_ASSUJETTIE_TVA);

        // Validate the PersonneMorale in Elasticsearch
        PersonneMorale personneMoraleEs = personneMoraleSearchRepository.findOne(testPersonneMorale.getId());
        assertThat(personneMoraleEs).isEqualToComparingFieldByField(testPersonneMorale);
    }

    @Test
    @Transactional
    public void createPersonneMoraleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personneMoraleRepository.findAll().size();

        // Create the PersonneMorale with an existing ID
        personneMorale.setId(1L);
        PersonneMoraleDTO personneMoraleDTO = personneMoraleMapper.toDto(personneMorale);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonneMoraleMockMvc.perform(post("/api/personne-morales")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personneMoraleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PersonneMorale> personneMoraleList = personneMoraleRepository.findAll();
        assertThat(personneMoraleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkRaisonSocialeIsRequired() throws Exception {
        int databaseSizeBeforeTest = personneMoraleRepository.findAll().size();
        // set the field null
        personneMorale.setRaisonSociale(null);

        // Create the PersonneMorale, which fails.
        PersonneMoraleDTO personneMoraleDTO = personneMoraleMapper.toDto(personneMorale);

        restPersonneMoraleMockMvc.perform(post("/api/personne-morales")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personneMoraleDTO)))
            .andExpect(status().isBadRequest());

        List<PersonneMorale> personneMoraleList = personneMoraleRepository.findAll();
        assertThat(personneMoraleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRegistreCommerceIsRequired() throws Exception {
        int databaseSizeBeforeTest = personneMoraleRepository.findAll().size();
        // set the field null
        personneMorale.setRegistreCommerce(null);

        // Create the PersonneMorale, which fails.
        PersonneMoraleDTO personneMoraleDTO = personneMoraleMapper.toDto(personneMorale);

        restPersonneMoraleMockMvc.perform(post("/api/personne-morales")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personneMoraleDTO)))
            .andExpect(status().isBadRequest());

        List<PersonneMorale> personneMoraleList = personneMoraleRepository.findAll();
        assertThat(personneMoraleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPersonneMorales() throws Exception {
        // Initialize the database
        personneMoraleRepository.saveAndFlush(personneMorale);

        // Get all the personneMoraleList
        restPersonneMoraleMockMvc.perform(get("/api/personne-morales?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personneMorale.getId().intValue())))
            .andExpect(jsonPath("$.[*].raisonSociale").value(hasItem(DEFAULT_RAISON_SOCIALE.toString())))
            .andExpect(jsonPath("$.[*].registreCommerce").value(hasItem(DEFAULT_REGISTRE_COMMERCE.toString())))
            .andExpect(jsonPath("$.[*].numEtablissement").value(hasItem(DEFAULT_NUM_ETABLISSEMENT.toString())))
            .andExpect(jsonPath("$.[*].codeCategorie").value(hasItem(DEFAULT_CODE_CATEGORIE.toString())))
            .andExpect(jsonPath("$.[*].matriculeFiscale").value(hasItem(DEFAULT_MATRICULE_FISCALE.toString())))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE.toString())))
            .andExpect(jsonPath("$.[*].banque").value(hasItem(DEFAULT_BANQUE.toString())))
            .andExpect(jsonPath("$.[*].agence").value(hasItem(DEFAULT_AGENCE.toString())))
            .andExpect(jsonPath("$.[*].rib").value(hasItem(DEFAULT_RIB.doubleValue())))
            .andExpect(jsonPath("$.[*].isAssujettieTva").value(hasItem(DEFAULT_IS_ASSUJETTIE_TVA.booleanValue())));
    }

    @Test
    @Transactional
    public void getPersonneMorale() throws Exception {
        // Initialize the database
        personneMoraleRepository.saveAndFlush(personneMorale);

        // Get the personneMorale
        restPersonneMoraleMockMvc.perform(get("/api/personne-morales/{id}", personneMorale.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(personneMorale.getId().intValue()))
            .andExpect(jsonPath("$.raisonSociale").value(DEFAULT_RAISON_SOCIALE.toString()))
            .andExpect(jsonPath("$.registreCommerce").value(DEFAULT_REGISTRE_COMMERCE.toString()))
            .andExpect(jsonPath("$.numEtablissement").value(DEFAULT_NUM_ETABLISSEMENT.toString()))
            .andExpect(jsonPath("$.codeCategorie").value(DEFAULT_CODE_CATEGORIE.toString()))
            .andExpect(jsonPath("$.matriculeFiscale").value(DEFAULT_MATRICULE_FISCALE.toString()))
            .andExpect(jsonPath("$.adresse").value(DEFAULT_ADRESSE.toString()))
            .andExpect(jsonPath("$.banque").value(DEFAULT_BANQUE.toString()))
            .andExpect(jsonPath("$.agence").value(DEFAULT_AGENCE.toString()))
            .andExpect(jsonPath("$.rib").value(DEFAULT_RIB.doubleValue()))
            .andExpect(jsonPath("$.isAssujettieTva").value(DEFAULT_IS_ASSUJETTIE_TVA.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPersonneMorale() throws Exception {
        // Get the personneMorale
        restPersonneMoraleMockMvc.perform(get("/api/personne-morales/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonneMorale() throws Exception {
        // Initialize the database
        personneMoraleRepository.saveAndFlush(personneMorale);
        personneMoraleSearchRepository.save(personneMorale);
        int databaseSizeBeforeUpdate = personneMoraleRepository.findAll().size();

        // Update the personneMorale
        PersonneMorale updatedPersonneMorale = personneMoraleRepository.findOne(personneMorale.getId());
        updatedPersonneMorale
            .raisonSociale(UPDATED_RAISON_SOCIALE)
            .registreCommerce(UPDATED_REGISTRE_COMMERCE)
            .numEtablissement(UPDATED_NUM_ETABLISSEMENT)
       
            .matriculeFiscale(UPDATED_MATRICULE_FISCALE)
            .adresse(UPDATED_ADRESSE)
            .banque(UPDATED_BANQUE)
            .agence(UPDATED_AGENCE)
            
            .isAssujettieTva(UPDATED_IS_ASSUJETTIE_TVA);
        PersonneMoraleDTO personneMoraleDTO = personneMoraleMapper.toDto(updatedPersonneMorale);

        restPersonneMoraleMockMvc.perform(put("/api/personne-morales")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personneMoraleDTO)))
            .andExpect(status().isOk());

        // Validate the PersonneMorale in the database
        List<PersonneMorale> personneMoraleList = personneMoraleRepository.findAll();
        assertThat(personneMoraleList).hasSize(databaseSizeBeforeUpdate);
        PersonneMorale testPersonneMorale = personneMoraleList.get(personneMoraleList.size() - 1);
        assertThat(testPersonneMorale.getRaisonSociale()).isEqualTo(UPDATED_RAISON_SOCIALE);
        assertThat(testPersonneMorale.getRegistreCommerce()).isEqualTo(UPDATED_REGISTRE_COMMERCE);
        assertThat(testPersonneMorale.getNumEtablissement()).isEqualTo(UPDATED_NUM_ETABLISSEMENT);
        assertThat(testPersonneMorale.getCodeCategorie()).isEqualTo(UPDATED_CODE_CATEGORIE);
        assertThat(testPersonneMorale.getMatriculeFiscale()).isEqualTo(UPDATED_MATRICULE_FISCALE);
        assertThat(testPersonneMorale.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testPersonneMorale.getBanque()).isEqualTo(UPDATED_BANQUE);
        assertThat(testPersonneMorale.getAgence()).isEqualTo(UPDATED_AGENCE);
        assertThat(testPersonneMorale.getRib()).isEqualTo(UPDATED_RIB);
        assertThat(testPersonneMorale.isIsAssujettieTva()).isEqualTo(UPDATED_IS_ASSUJETTIE_TVA);

        // Validate the PersonneMorale in Elasticsearch
        PersonneMorale personneMoraleEs = personneMoraleSearchRepository.findOne(testPersonneMorale.getId());
        assertThat(personneMoraleEs).isEqualToComparingFieldByField(testPersonneMorale);
    }

    @Test
    @Transactional
    public void updateNonExistingPersonneMorale() throws Exception {
        int databaseSizeBeforeUpdate = personneMoraleRepository.findAll().size();

        // Create the PersonneMorale
        PersonneMoraleDTO personneMoraleDTO = personneMoraleMapper.toDto(personneMorale);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPersonneMoraleMockMvc.perform(put("/api/personne-morales")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personneMoraleDTO)))
            .andExpect(status().isCreated());

        // Validate the PersonneMorale in the database
        List<PersonneMorale> personneMoraleList = personneMoraleRepository.findAll();
        assertThat(personneMoraleList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePersonneMorale() throws Exception {
        // Initialize the database
        personneMoraleRepository.saveAndFlush(personneMorale);
        personneMoraleSearchRepository.save(personneMorale);
        int databaseSizeBeforeDelete = personneMoraleRepository.findAll().size();

        // Get the personneMorale
        restPersonneMoraleMockMvc.perform(delete("/api/personne-morales/{id}", personneMorale.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean personneMoraleExistsInEs = personneMoraleSearchRepository.exists(personneMorale.getId());
        assertThat(personneMoraleExistsInEs).isFalse();

        // Validate the database is empty
        List<PersonneMorale> personneMoraleList = personneMoraleRepository.findAll();
        assertThat(personneMoraleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchPersonneMorale() throws Exception {
        // Initialize the database
        personneMoraleRepository.saveAndFlush(personneMorale);
        personneMoraleSearchRepository.save(personneMorale);

        // Search the personneMorale
        restPersonneMoraleMockMvc.perform(get("/api/_search/personne-morales?query=id:" + personneMorale.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personneMorale.getId().intValue())))
            .andExpect(jsonPath("$.[*].raisonSociale").value(hasItem(DEFAULT_RAISON_SOCIALE.toString())))
            .andExpect(jsonPath("$.[*].registreCommerce").value(hasItem(DEFAULT_REGISTRE_COMMERCE.toString())))
            .andExpect(jsonPath("$.[*].numEtablissement").value(hasItem(DEFAULT_NUM_ETABLISSEMENT.toString())))
            .andExpect(jsonPath("$.[*].codeCategorie").value(hasItem(DEFAULT_CODE_CATEGORIE.toString())))
            .andExpect(jsonPath("$.[*].matriculeFiscale").value(hasItem(DEFAULT_MATRICULE_FISCALE.toString())))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE.toString())))
            .andExpect(jsonPath("$.[*].banque").value(hasItem(DEFAULT_BANQUE.toString())))
            .andExpect(jsonPath("$.[*].agence").value(hasItem(DEFAULT_AGENCE.toString())))
            .andExpect(jsonPath("$.[*].rib").value(hasItem(DEFAULT_RIB.doubleValue())))
            .andExpect(jsonPath("$.[*].isAssujettieTva").value(hasItem(DEFAULT_IS_ASSUJETTIE_TVA.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersonneMorale.class);
        PersonneMorale personneMorale1 = new PersonneMorale();
        personneMorale1.setId(1L);
        PersonneMorale personneMorale2 = new PersonneMorale();
        personneMorale2.setId(personneMorale1.getId());
        assertThat(personneMorale1).isEqualTo(personneMorale2);
        personneMorale2.setId(2L);
        assertThat(personneMorale1).isNotEqualTo(personneMorale2);
        personneMorale1.setId(null);
        assertThat(personneMorale1).isNotEqualTo(personneMorale2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersonneMoraleDTO.class);
        PersonneMoraleDTO personneMoraleDTO1 = new PersonneMoraleDTO();
        personneMoraleDTO1.setId(1L);
        PersonneMoraleDTO personneMoraleDTO2 = new PersonneMoraleDTO();
        assertThat(personneMoraleDTO1).isNotEqualTo(personneMoraleDTO2);
        personneMoraleDTO2.setId(personneMoraleDTO1.getId());
        assertThat(personneMoraleDTO1).isEqualTo(personneMoraleDTO2);
        personneMoraleDTO2.setId(2L);
        assertThat(personneMoraleDTO1).isNotEqualTo(personneMoraleDTO2);
        personneMoraleDTO1.setId(null);
        assertThat(personneMoraleDTO1).isNotEqualTo(personneMoraleDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(personneMoraleMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(personneMoraleMapper.fromId(null)).isNull();
    }
}
