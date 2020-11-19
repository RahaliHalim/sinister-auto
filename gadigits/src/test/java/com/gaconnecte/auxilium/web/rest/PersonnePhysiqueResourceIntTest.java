package com.gaconnecte.auxilium.web.rest;

import com.gaconnecte.auxilium.AuxiliumApp;

import com.gaconnecte.auxilium.domain.PersonnePhysique;
import com.gaconnecte.auxilium.repository.PersonnePhysiqueRepository;
import com.gaconnecte.auxilium.service.PersonnePhysiqueService;
import com.gaconnecte.auxilium.repository.search.PersonnePhysiqueSearchRepository;
import com.gaconnecte.auxilium.service.dto.PersonnePhysiqueDTO;
import com.gaconnecte.auxilium.service.mapper.PersonnePhysiqueMapper;
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

import com.gaconnecte.auxilium.domain.enumeration.Civilite;
/**
 * Test class for the PersonnePhysiqueResource REST controller.
 *
 * @see PersonnePhysiqueResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuxiliumApp.class)
public class PersonnePhysiqueResourceIntTest {

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_PREM_TELEPHONE = "71459256";
    private static final String UPDATED_PREM_TELEPHONE = "59780088";

    private static final String DEFAULT_DEUX_TELEPHONE = "19585337";
    private static final String UPDATED_DEUX_TELEPHONE = "03424802";

    private static final String DEFAULT_PREM_MAIL = "*@a";
    private static final String UPDATED_PREM_MAIL = "3g@u";

    private static final String DEFAULT_DEUX_MAIL = "l@9";
    private static final String UPDATED_DEUX_MAIL = "7a@{`";

    private static final String DEFAULT_ADRESSE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE = "BBBBBBBBBB";


    private static final Boolean DEFAULT_IS_UTILISATEUR = false;
    private static final Boolean UPDATED_IS_UTILISATEUR = true;

    private static final Integer DEFAULT_CIN = 99999999;
    private static final Integer UPDATED_CIN = 99999998;

    private static final Civilite DEFAULT_CIVILITE = Civilite.MONSIEUR;
    private static final Civilite UPDATED_CIVILITE = Civilite.MADAME;

    @Autowired
    private PersonnePhysiqueRepository personnePhysiqueRepository;

    @Autowired
    private PersonnePhysiqueMapper personnePhysiqueMapper;

    @Autowired
    private PersonnePhysiqueService personnePhysiqueService;

    @Autowired
    private PersonnePhysiqueSearchRepository personnePhysiqueSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPersonnePhysiqueMockMvc;

    private PersonnePhysique personnePhysique;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PersonnePhysiqueResource personnePhysiqueResource = new PersonnePhysiqueResource(personnePhysiqueService);
        this.restPersonnePhysiqueMockMvc = MockMvcBuilders.standaloneSetup(personnePhysiqueResource)
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
    public static PersonnePhysique createEntity(EntityManager em) {
        PersonnePhysique personnePhysique = new PersonnePhysique()
            .prenom(DEFAULT_PRENOM)
            .nom(DEFAULT_NOM)
            .premTelephone(DEFAULT_PREM_TELEPHONE)
            .deuxTelephone(DEFAULT_DEUX_TELEPHONE)
            .premMail(DEFAULT_PREM_MAIL)
            .deuxMail(DEFAULT_DEUX_MAIL)
            .adresse(DEFAULT_ADRESSE)
            .isUtilisateur(DEFAULT_IS_UTILISATEUR)
            .cin(DEFAULT_CIN)
            .civilite(DEFAULT_CIVILITE);
        return personnePhysique;
    }

    @Before
    public void initTest() {
        personnePhysiqueSearchRepository.deleteAll();
        personnePhysique = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersonnePhysique() throws Exception {
        int databaseSizeBeforeCreate = personnePhysiqueRepository.findAll().size();

        // Create the PersonnePhysique
        PersonnePhysiqueDTO personnePhysiqueDTO = personnePhysiqueMapper.toDto(personnePhysique);
        restPersonnePhysiqueMockMvc.perform(post("/api/personne-physiques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personnePhysiqueDTO)))
            .andExpect(status().isCreated());

        // Validate the PersonnePhysique in the database
        List<PersonnePhysique> personnePhysiqueList = personnePhysiqueRepository.findAll();
        assertThat(personnePhysiqueList).hasSize(databaseSizeBeforeCreate + 1);
        PersonnePhysique testPersonnePhysique = personnePhysiqueList.get(personnePhysiqueList.size() - 1);
        assertThat(testPersonnePhysique.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testPersonnePhysique.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testPersonnePhysique.getPremTelephone()).isEqualTo(DEFAULT_PREM_TELEPHONE);
        assertThat(testPersonnePhysique.getDeuxTelephone()).isEqualTo(DEFAULT_DEUX_TELEPHONE);
        assertThat(testPersonnePhysique.getPremMail()).isEqualTo(DEFAULT_PREM_MAIL);
        assertThat(testPersonnePhysique.getDeuxMail()).isEqualTo(DEFAULT_DEUX_MAIL);
        assertThat(testPersonnePhysique.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
        assertThat(testPersonnePhysique.isIsUtilisateur()).isEqualTo(DEFAULT_IS_UTILISATEUR);
        assertThat(testPersonnePhysique.getCin()).isEqualTo(DEFAULT_CIN);
        assertThat(testPersonnePhysique.getCivilite()).isEqualTo(DEFAULT_CIVILITE);

        // Validate the PersonnePhysique in Elasticsearch
        PersonnePhysique personnePhysiqueEs = personnePhysiqueSearchRepository.findOne(testPersonnePhysique.getId());
        assertThat(personnePhysiqueEs).isEqualToComparingFieldByField(testPersonnePhysique);
    }

    @Test
    @Transactional
    public void createPersonnePhysiqueWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personnePhysiqueRepository.findAll().size();

        // Create the PersonnePhysique with an existing ID
        personnePhysique.setId(1L);
        PersonnePhysiqueDTO personnePhysiqueDTO = personnePhysiqueMapper.toDto(personnePhysique);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonnePhysiqueMockMvc.perform(post("/api/personne-physiques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personnePhysiqueDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PersonnePhysique> personnePhysiqueList = personnePhysiqueRepository.findAll();
        assertThat(personnePhysiqueList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkPrenomIsRequired() throws Exception {
        int databaseSizeBeforeTest = personnePhysiqueRepository.findAll().size();
        // set the field null
        personnePhysique.setPrenom(null);

        // Create the PersonnePhysique, which fails.
        PersonnePhysiqueDTO personnePhysiqueDTO = personnePhysiqueMapper.toDto(personnePhysique);

        restPersonnePhysiqueMockMvc.perform(post("/api/personne-physiques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personnePhysiqueDTO)))
            .andExpect(status().isBadRequest());

        List<PersonnePhysique> personnePhysiqueList = personnePhysiqueRepository.findAll();
        assertThat(personnePhysiqueList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = personnePhysiqueRepository.findAll().size();
        // set the field null
        personnePhysique.setNom(null);

        // Create the PersonnePhysique, which fails.
        PersonnePhysiqueDTO personnePhysiqueDTO = personnePhysiqueMapper.toDto(personnePhysique);

        restPersonnePhysiqueMockMvc.perform(post("/api/personne-physiques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personnePhysiqueDTO)))
            .andExpect(status().isBadRequest());

        List<PersonnePhysique> personnePhysiqueList = personnePhysiqueRepository.findAll();
        assertThat(personnePhysiqueList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPremTelephoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = personnePhysiqueRepository.findAll().size();
        // set the field null
        personnePhysique.setPremTelephone(null);

        // Create the PersonnePhysique, which fails.
        PersonnePhysiqueDTO personnePhysiqueDTO = personnePhysiqueMapper.toDto(personnePhysique);

        restPersonnePhysiqueMockMvc.perform(post("/api/personne-physiques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personnePhysiqueDTO)))
            .andExpect(status().isBadRequest());

        List<PersonnePhysique> personnePhysiqueList = personnePhysiqueRepository.findAll();
        assertThat(personnePhysiqueList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPersonnePhysiques() throws Exception {
        // Initialize the database
        personnePhysiqueRepository.saveAndFlush(personnePhysique);

        // Get all the personnePhysiqueList
        restPersonnePhysiqueMockMvc.perform(get("/api/personne-physiques?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personnePhysique.getId().intValue())))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM.toString())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].premTelephone").value(hasItem(DEFAULT_PREM_TELEPHONE.toString())))
            .andExpect(jsonPath("$.[*].deuxTelephone").value(hasItem(DEFAULT_DEUX_TELEPHONE.toString())))
            .andExpect(jsonPath("$.[*].premMail").value(hasItem(DEFAULT_PREM_MAIL.toString())))
            .andExpect(jsonPath("$.[*].deuxMail").value(hasItem(DEFAULT_DEUX_MAIL.toString())))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE.toString())))
            .andExpect(jsonPath("$.[*].isUtilisateur").value(hasItem(DEFAULT_IS_UTILISATEUR.booleanValue())))
            .andExpect(jsonPath("$.[*].cin").value(hasItem(DEFAULT_CIN)))
            .andExpect(jsonPath("$.[*].civilite").value(hasItem(DEFAULT_CIVILITE.toString())));
    }

    @Test
    @Transactional
    public void getPersonnePhysique() throws Exception {
        // Initialize the database
        personnePhysiqueRepository.saveAndFlush(personnePhysique);

        // Get the personnePhysique
        restPersonnePhysiqueMockMvc.perform(get("/api/personne-physiques/{id}", personnePhysique.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(personnePhysique.getId().intValue()))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM.toString()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.premTelephone").value(DEFAULT_PREM_TELEPHONE.toString()))
            .andExpect(jsonPath("$.deuxTelephone").value(DEFAULT_DEUX_TELEPHONE.toString()))
            .andExpect(jsonPath("$.premMail").value(DEFAULT_PREM_MAIL.toString()))
            .andExpect(jsonPath("$.deuxMail").value(DEFAULT_DEUX_MAIL.toString()))
            .andExpect(jsonPath("$.adresse").value(DEFAULT_ADRESSE.toString()))
            .andExpect(jsonPath("$.isUtilisateur").value(DEFAULT_IS_UTILISATEUR.booleanValue()))
            .andExpect(jsonPath("$.cin").value(DEFAULT_CIN))
            .andExpect(jsonPath("$.civilite").value(DEFAULT_CIVILITE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPersonnePhysique() throws Exception {
        // Get the personnePhysique
        restPersonnePhysiqueMockMvc.perform(get("/api/personne-physiques/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonnePhysique() throws Exception {
        // Initialize the database
        personnePhysiqueRepository.saveAndFlush(personnePhysique);
        personnePhysiqueSearchRepository.save(personnePhysique);
        int databaseSizeBeforeUpdate = personnePhysiqueRepository.findAll().size();

        // Update the personnePhysique
        PersonnePhysique updatedPersonnePhysique = personnePhysiqueRepository.findOne(personnePhysique.getId());
        updatedPersonnePhysique
            .prenom(UPDATED_PRENOM)
            .nom(UPDATED_NOM)
            .premTelephone(UPDATED_PREM_TELEPHONE)
            .deuxTelephone(UPDATED_DEUX_TELEPHONE)
            .premMail(UPDATED_PREM_MAIL)
            .deuxMail(UPDATED_DEUX_MAIL)
            .adresse(UPDATED_ADRESSE)
            .isUtilisateur(UPDATED_IS_UTILISATEUR)
            .cin(UPDATED_CIN)
            .civilite(UPDATED_CIVILITE);
        PersonnePhysiqueDTO personnePhysiqueDTO = personnePhysiqueMapper.toDto(updatedPersonnePhysique);

        restPersonnePhysiqueMockMvc.perform(put("/api/personne-physiques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personnePhysiqueDTO)))
            .andExpect(status().isOk());

        // Validate the PersonnePhysique in the database
        List<PersonnePhysique> personnePhysiqueList = personnePhysiqueRepository.findAll();
        assertThat(personnePhysiqueList).hasSize(databaseSizeBeforeUpdate);
        PersonnePhysique testPersonnePhysique = personnePhysiqueList.get(personnePhysiqueList.size() - 1);
        assertThat(testPersonnePhysique.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testPersonnePhysique.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testPersonnePhysique.getPremTelephone()).isEqualTo(UPDATED_PREM_TELEPHONE);
        assertThat(testPersonnePhysique.getDeuxTelephone()).isEqualTo(UPDATED_DEUX_TELEPHONE);
        assertThat(testPersonnePhysique.getPremMail()).isEqualTo(UPDATED_PREM_MAIL);
        assertThat(testPersonnePhysique.getDeuxMail()).isEqualTo(UPDATED_DEUX_MAIL);
        assertThat(testPersonnePhysique.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testPersonnePhysique.isIsUtilisateur()).isEqualTo(UPDATED_IS_UTILISATEUR);
        assertThat(testPersonnePhysique.getCin()).isEqualTo(UPDATED_CIN);
        assertThat(testPersonnePhysique.getCivilite()).isEqualTo(UPDATED_CIVILITE);

        // Validate the PersonnePhysique in Elasticsearch
        PersonnePhysique personnePhysiqueEs = personnePhysiqueSearchRepository.findOne(testPersonnePhysique.getId());
        assertThat(personnePhysiqueEs).isEqualToComparingFieldByField(testPersonnePhysique);
    }

    @Test
    @Transactional
    public void updateNonExistingPersonnePhysique() throws Exception {
        int databaseSizeBeforeUpdate = personnePhysiqueRepository.findAll().size();

        // Create the PersonnePhysique
        PersonnePhysiqueDTO personnePhysiqueDTO = personnePhysiqueMapper.toDto(personnePhysique);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPersonnePhysiqueMockMvc.perform(put("/api/personne-physiques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personnePhysiqueDTO)))
            .andExpect(status().isCreated());

        // Validate the PersonnePhysique in the database
        List<PersonnePhysique> personnePhysiqueList = personnePhysiqueRepository.findAll();
        assertThat(personnePhysiqueList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePersonnePhysique() throws Exception {
        // Initialize the database
        personnePhysiqueRepository.saveAndFlush(personnePhysique);
        personnePhysiqueSearchRepository.save(personnePhysique);
        int databaseSizeBeforeDelete = personnePhysiqueRepository.findAll().size();

        // Get the personnePhysique
        restPersonnePhysiqueMockMvc.perform(delete("/api/personne-physiques/{id}", personnePhysique.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean personnePhysiqueExistsInEs = personnePhysiqueSearchRepository.exists(personnePhysique.getId());
        assertThat(personnePhysiqueExistsInEs).isFalse();

        // Validate the database is empty
        List<PersonnePhysique> personnePhysiqueList = personnePhysiqueRepository.findAll();
        assertThat(personnePhysiqueList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchPersonnePhysique() throws Exception {
        // Initialize the database
        personnePhysiqueRepository.saveAndFlush(personnePhysique);
        personnePhysiqueSearchRepository.save(personnePhysique);

        // Search the personnePhysique
        restPersonnePhysiqueMockMvc.perform(get("/api/_search/personne-physiques?query=id:" + personnePhysique.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personnePhysique.getId().intValue())))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM.toString())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].premTelephone").value(hasItem(DEFAULT_PREM_TELEPHONE.toString())))
            .andExpect(jsonPath("$.[*].deuxTelephone").value(hasItem(DEFAULT_DEUX_TELEPHONE.toString())))
            .andExpect(jsonPath("$.[*].premMail").value(hasItem(DEFAULT_PREM_MAIL.toString())))
            .andExpect(jsonPath("$.[*].deuxMail").value(hasItem(DEFAULT_DEUX_MAIL.toString())))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE.toString())))
            .andExpect(jsonPath("$.[*].isUtilisateur").value(hasItem(DEFAULT_IS_UTILISATEUR.booleanValue())))
            .andExpect(jsonPath("$.[*].cin").value(hasItem(DEFAULT_CIN)))
            .andExpect(jsonPath("$.[*].civilite").value(hasItem(DEFAULT_CIVILITE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersonnePhysique.class);
        PersonnePhysique personnePhysique1 = new PersonnePhysique();
        personnePhysique1.setId(1L);
        PersonnePhysique personnePhysique2 = new PersonnePhysique();
        personnePhysique2.setId(personnePhysique1.getId());
        assertThat(personnePhysique1).isEqualTo(personnePhysique2);
        personnePhysique2.setId(2L);
        assertThat(personnePhysique1).isNotEqualTo(personnePhysique2);
        personnePhysique1.setId(null);
        assertThat(personnePhysique1).isNotEqualTo(personnePhysique2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersonnePhysiqueDTO.class);
        PersonnePhysiqueDTO personnePhysiqueDTO1 = new PersonnePhysiqueDTO();
        personnePhysiqueDTO1.setId(1L);
        PersonnePhysiqueDTO personnePhysiqueDTO2 = new PersonnePhysiqueDTO();
        assertThat(personnePhysiqueDTO1).isNotEqualTo(personnePhysiqueDTO2);
        personnePhysiqueDTO2.setId(personnePhysiqueDTO1.getId());
        assertThat(personnePhysiqueDTO1).isEqualTo(personnePhysiqueDTO2);
        personnePhysiqueDTO2.setId(2L);
        assertThat(personnePhysiqueDTO1).isNotEqualTo(personnePhysiqueDTO2);
        personnePhysiqueDTO1.setId(null);
        assertThat(personnePhysiqueDTO1).isNotEqualTo(personnePhysiqueDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(personnePhysiqueMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(personnePhysiqueMapper.fromId(null)).isNull();
    }
}
