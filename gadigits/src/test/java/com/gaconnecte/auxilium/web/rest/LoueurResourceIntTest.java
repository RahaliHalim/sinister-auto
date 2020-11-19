package com.gaconnecte.auxilium.web.rest;

import com.gaconnecte.auxilium.AuxiliumApp;

import com.gaconnecte.auxilium.domain.Loueur;
import com.gaconnecte.auxilium.repository.LoueurRepository;
import com.gaconnecte.auxilium.service.LoueurService;
import com.gaconnecte.auxilium.repository.search.LoueurSearchRepository;
import com.gaconnecte.auxilium.service.dto.LoueurDTO;
import com.gaconnecte.auxilium.service.mapper.LoueurMapper;
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

/**
 * Test class for the LoueurResource REST controller.
 *
 * @see LoueurResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuxiliumApp.class)
public class LoueurResourceIntTest {

    private static final Long DEFAULT_CODE = 1L;
    private static final Long UPDATED_CODE = 2L;

    private static final String DEFAULT_RAISON_SOCIALE = "AAAAAAAAAA";
    private static final String UPDATED_RAISON_SOCIALE = "BBBBBBBBBB";

    private static final String DEFAULT_MATRICULE_FISCALE = "AAAAAAAAAA";
    private static final String UPDATED_MATRICULE_FISCALE = "BBBBBBBBBB";

    private static final String DEFAULT_REGISTRE_COMMERCE = "AAAAAAAAAA";
    private static final String UPDATED_REGISTRE_COMMERCE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_CONVENTIONNE = false;
    private static final Boolean UPDATED_CONVENTIONNE = true;

    private static final LocalDate DEFAULT_DATE_EFFET_CONVENTION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_EFFET_CONVENTION = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_FIN_CONVENTION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_FIN_CONVENTION = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_NBR_VEHICULES = 1;
    private static final Integer UPDATED_NBR_VEHICULES = 2;

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_BLOCAGE = false;
    private static final Boolean UPDATED_BLOCAGE = true;

    private static final String DEFAULT_RIB = "AAAAAAAAAA";
    private static final String UPDATED_RIB = "BBBBBBBBBB";

    @Autowired
    private LoueurRepository loueurRepository;

    @Autowired
    private LoueurMapper loueurMapper;

    @Autowired
    private LoueurService loueurService;

    @Autowired
    private LoueurSearchRepository loueurSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLoueurMockMvc;

    private Loueur loueur;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LoueurResource loueurResource = new LoueurResource(loueurService);
        this.restLoueurMockMvc = MockMvcBuilders.standaloneSetup(loueurResource)
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
    public static Loueur createEntity(EntityManager em) {
        Loueur loueur = new Loueur()
            .code(DEFAULT_CODE)
            //.raisonSociale(DEFAULT_RAISON_SOCIALE)
            .matriculeFiscale(DEFAULT_MATRICULE_FISCALE)
            .registreCommerce(DEFAULT_REGISTRE_COMMERCE)
            .conventionne(DEFAULT_CONVENTIONNE)
            .dateEffetConvention(DEFAULT_DATE_EFFET_CONVENTION)
            .dateFinConvention(DEFAULT_DATE_FIN_CONVENTION)
            .nbrVehicules(DEFAULT_NBR_VEHICULES)
            .address(DEFAULT_ADDRESS)
            .blocage(DEFAULT_BLOCAGE)
            .rib(DEFAULT_RIB);
        return loueur;
    }

    @Before
    public void initTest() {
        loueurSearchRepository.deleteAll();
        loueur = createEntity(em);
    }

    @Test
    @Transactional
    public void createLoueur() throws Exception {
        int databaseSizeBeforeCreate = loueurRepository.findAll().size();

        // Create the Loueur
        LoueurDTO loueurDTO = loueurMapper.toDto(loueur);
        restLoueurMockMvc.perform(post("/api/loueurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loueurDTO)))
            .andExpect(status().isCreated());

        // Validate the Loueur in the database
        List<Loueur> loueurList = loueurRepository.findAll();
        assertThat(loueurList).hasSize(databaseSizeBeforeCreate + 1);
        Loueur testLoueur = loueurList.get(loueurList.size() - 1);
        assertThat(testLoueur.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testLoueur.getRaisonSociale()).isEqualTo(DEFAULT_RAISON_SOCIALE);
        assertThat(testLoueur.getMatriculeFiscale()).isEqualTo(DEFAULT_MATRICULE_FISCALE);
        assertThat(testLoueur.getRegistreCommerce()).isEqualTo(DEFAULT_REGISTRE_COMMERCE);
        assertThat(testLoueur.isConventionne()).isEqualTo(DEFAULT_CONVENTIONNE);
        assertThat(testLoueur.getDateEffetConvention()).isEqualTo(DEFAULT_DATE_EFFET_CONVENTION);
        assertThat(testLoueur.getDateFinConvention()).isEqualTo(DEFAULT_DATE_FIN_CONVENTION);
        assertThat(testLoueur.getNbrVehicules()).isEqualTo(DEFAULT_NBR_VEHICULES);
        assertThat(testLoueur.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testLoueur.isBlocage()).isEqualTo(DEFAULT_BLOCAGE);
        assertThat(testLoueur.getRib()).isEqualTo(DEFAULT_RIB);

        // Validate the Loueur in Elasticsearch
        Loueur loueurEs = loueurSearchRepository.findOne(testLoueur.getId());
        assertThat(loueurEs).isEqualToComparingFieldByField(testLoueur);
    }

    @Test
    @Transactional
    public void createLoueurWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = loueurRepository.findAll().size();

        // Create the Loueur with an existing ID
        loueur.setId(1L);
        LoueurDTO loueurDTO = loueurMapper.toDto(loueur);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLoueurMockMvc.perform(post("/api/loueurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loueurDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Loueur> loueurList = loueurRepository.findAll();
        assertThat(loueurList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllLoueurs() throws Exception {
        // Initialize the database
        loueurRepository.saveAndFlush(loueur);

        // Get all the loueurList
        restLoueurMockMvc.perform(get("/api/loueurs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(loueur.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.intValue())))
            .andExpect(jsonPath("$.[*].raisonSociale").value(hasItem(DEFAULT_RAISON_SOCIALE.toString())))
            .andExpect(jsonPath("$.[*].matriculeFiscale").value(hasItem(DEFAULT_MATRICULE_FISCALE.toString())))
            .andExpect(jsonPath("$.[*].registreCommerce").value(hasItem(DEFAULT_REGISTRE_COMMERCE.toString())))
            .andExpect(jsonPath("$.[*].conventionne").value(hasItem(DEFAULT_CONVENTIONNE.booleanValue())))
            .andExpect(jsonPath("$.[*].dateEffetConvention").value(hasItem(DEFAULT_DATE_EFFET_CONVENTION.toString())))
            .andExpect(jsonPath("$.[*].dateFinConvention").value(hasItem(DEFAULT_DATE_FIN_CONVENTION.toString())))
            .andExpect(jsonPath("$.[*].nbrVehicules").value(hasItem(DEFAULT_NBR_VEHICULES)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].blocage").value(hasItem(DEFAULT_BLOCAGE.booleanValue())))
            .andExpect(jsonPath("$.[*].rib").value(hasItem(DEFAULT_RIB.toString())));
    }

    @Test
    @Transactional
    public void getLoueur() throws Exception {
        // Initialize the database
        loueurRepository.saveAndFlush(loueur);

        // Get the loueur
        restLoueurMockMvc.perform(get("/api/loueurs/{id}", loueur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(loueur.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.intValue()))
            .andExpect(jsonPath("$.raisonSociale").value(DEFAULT_RAISON_SOCIALE.toString()))
            .andExpect(jsonPath("$.matriculeFiscale").value(DEFAULT_MATRICULE_FISCALE.toString()))
            .andExpect(jsonPath("$.registreCommerce").value(DEFAULT_REGISTRE_COMMERCE.toString()))
            .andExpect(jsonPath("$.conventionne").value(DEFAULT_CONVENTIONNE.booleanValue()))
            .andExpect(jsonPath("$.dateEffetConvention").value(DEFAULT_DATE_EFFET_CONVENTION.toString()))
            .andExpect(jsonPath("$.dateFinConvention").value(DEFAULT_DATE_FIN_CONVENTION.toString()))
            .andExpect(jsonPath("$.nbrVehicules").value(DEFAULT_NBR_VEHICULES))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.blocage").value(DEFAULT_BLOCAGE.booleanValue()))
            .andExpect(jsonPath("$.rib").value(DEFAULT_RIB.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLoueur() throws Exception {
        // Get the loueur
        restLoueurMockMvc.perform(get("/api/loueurs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLoueur() throws Exception {
        // Initialize the database
        loueurRepository.saveAndFlush(loueur);
        loueurSearchRepository.save(loueur);
        int databaseSizeBeforeUpdate = loueurRepository.findAll().size();

        // Update the loueur
        Loueur updatedLoueur = loueurRepository.findOne(loueur.getId());
        updatedLoueur
            .code(UPDATED_CODE)
            //.raisonSociale(UPDATED_RAISON_SOCIALE)
            .matriculeFiscale(UPDATED_MATRICULE_FISCALE)
            .registreCommerce(UPDATED_REGISTRE_COMMERCE)
            .conventionne(UPDATED_CONVENTIONNE)
            .dateEffetConvention(UPDATED_DATE_EFFET_CONVENTION)
            .dateFinConvention(UPDATED_DATE_FIN_CONVENTION)
            .nbrVehicules(UPDATED_NBR_VEHICULES)
            .address(UPDATED_ADDRESS)
            .blocage(UPDATED_BLOCAGE)
            .rib(UPDATED_RIB);
        LoueurDTO loueurDTO = loueurMapper.toDto(updatedLoueur);

        restLoueurMockMvc.perform(put("/api/loueurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loueurDTO)))
            .andExpect(status().isOk());

        // Validate the Loueur in the database
        List<Loueur> loueurList = loueurRepository.findAll();
        assertThat(loueurList).hasSize(databaseSizeBeforeUpdate);
        Loueur testLoueur = loueurList.get(loueurList.size() - 1);
        assertThat(testLoueur.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testLoueur.getRaisonSociale()).isEqualTo(UPDATED_RAISON_SOCIALE);
        assertThat(testLoueur.getMatriculeFiscale()).isEqualTo(UPDATED_MATRICULE_FISCALE);
        assertThat(testLoueur.getRegistreCommerce()).isEqualTo(UPDATED_REGISTRE_COMMERCE);
        assertThat(testLoueur.isConventionne()).isEqualTo(UPDATED_CONVENTIONNE);
        assertThat(testLoueur.getDateEffetConvention()).isEqualTo(UPDATED_DATE_EFFET_CONVENTION);
        assertThat(testLoueur.getDateFinConvention()).isEqualTo(UPDATED_DATE_FIN_CONVENTION);
        assertThat(testLoueur.getNbrVehicules()).isEqualTo(UPDATED_NBR_VEHICULES);
        assertThat(testLoueur.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testLoueur.isBlocage()).isEqualTo(UPDATED_BLOCAGE);
        assertThat(testLoueur.getRib()).isEqualTo(UPDATED_RIB);

        // Validate the Loueur in Elasticsearch
        Loueur loueurEs = loueurSearchRepository.findOne(testLoueur.getId());
        assertThat(loueurEs).isEqualToComparingFieldByField(testLoueur);
    }

    @Test
    @Transactional
    public void updateNonExistingLoueur() throws Exception {
        int databaseSizeBeforeUpdate = loueurRepository.findAll().size();

        // Create the Loueur
        LoueurDTO loueurDTO = loueurMapper.toDto(loueur);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLoueurMockMvc.perform(put("/api/loueurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loueurDTO)))
            .andExpect(status().isCreated());

        // Validate the Loueur in the database
        List<Loueur> loueurList = loueurRepository.findAll();
        assertThat(loueurList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLoueur() throws Exception {
        // Initialize the database
        loueurRepository.saveAndFlush(loueur);
        loueurSearchRepository.save(loueur);
        int databaseSizeBeforeDelete = loueurRepository.findAll().size();

        // Get the loueur
        restLoueurMockMvc.perform(delete("/api/loueurs/{id}", loueur.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean loueurExistsInEs = loueurSearchRepository.exists(loueur.getId());
        assertThat(loueurExistsInEs).isFalse();

        // Validate the database is empty
        List<Loueur> loueurList = loueurRepository.findAll();
        assertThat(loueurList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchLoueur() throws Exception {
        // Initialize the database
        loueurRepository.saveAndFlush(loueur);
        loueurSearchRepository.save(loueur);

        // Search the loueur
        restLoueurMockMvc.perform(get("/api/_search/loueurs?query=id:" + loueur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(loueur.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.intValue())))
            .andExpect(jsonPath("$.[*].raisonSociale").value(hasItem(DEFAULT_RAISON_SOCIALE.toString())))
            .andExpect(jsonPath("$.[*].matriculeFiscale").value(hasItem(DEFAULT_MATRICULE_FISCALE.toString())))
            .andExpect(jsonPath("$.[*].registreCommerce").value(hasItem(DEFAULT_REGISTRE_COMMERCE.toString())))
            .andExpect(jsonPath("$.[*].conventionne").value(hasItem(DEFAULT_CONVENTIONNE.booleanValue())))
            .andExpect(jsonPath("$.[*].dateEffetConvention").value(hasItem(DEFAULT_DATE_EFFET_CONVENTION.toString())))
            .andExpect(jsonPath("$.[*].dateFinConvention").value(hasItem(DEFAULT_DATE_FIN_CONVENTION.toString())))
            .andExpect(jsonPath("$.[*].nbrVehicules").value(hasItem(DEFAULT_NBR_VEHICULES)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].blocage").value(hasItem(DEFAULT_BLOCAGE.booleanValue())))
            .andExpect(jsonPath("$.[*].rib").value(hasItem(DEFAULT_RIB.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Loueur.class);
        Loueur loueur1 = new Loueur();
        loueur1.setId(1L);
        Loueur loueur2 = new Loueur();
        loueur2.setId(loueur1.getId());
        assertThat(loueur1).isEqualTo(loueur2);
        loueur2.setId(2L);
        assertThat(loueur1).isNotEqualTo(loueur2);
        loueur1.setId(null);
        assertThat(loueur1).isNotEqualTo(loueur2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LoueurDTO.class);
        LoueurDTO loueurDTO1 = new LoueurDTO();
        loueurDTO1.setId(1L);
        LoueurDTO loueurDTO2 = new LoueurDTO();
        assertThat(loueurDTO1).isNotEqualTo(loueurDTO2);
        loueurDTO2.setId(loueurDTO1.getId());
        assertThat(loueurDTO1).isEqualTo(loueurDTO2);
        loueurDTO2.setId(2L);
        assertThat(loueurDTO1).isNotEqualTo(loueurDTO2);
        loueurDTO1.setId(null);
        assertThat(loueurDTO1).isNotEqualTo(loueurDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(loueurMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(loueurMapper.fromId(null)).isNull();
    }
}
