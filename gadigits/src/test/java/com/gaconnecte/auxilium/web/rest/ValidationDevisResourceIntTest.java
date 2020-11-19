package com.gaconnecte.auxilium.web.rest;

import com.gaconnecte.auxilium.AuxiliumApp;

import com.gaconnecte.auxilium.domain.ValidationDevis;
import com.gaconnecte.auxilium.repository.ValidationDevisRepository;
import com.gaconnecte.auxilium.service.ValidationDevisService;
import com.gaconnecte.auxilium.repository.search.ValidationDevisSearchRepository;
import com.gaconnecte.auxilium.service.dto.ValidationDevisDTO;
import com.gaconnecte.auxilium.service.mapper.ValidationDevisMapper;
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
 * Test class for the ValidationDevisResource REST controller.
 *
 * @see ValidationDevisResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuxiliumApp.class)
public class ValidationDevisResourceIntTest {

    private static final Boolean DEFAULT_IS_VALIDE = false;
    private static final Boolean UPDATED_IS_VALIDE = true;

    private static final Boolean DEFAULT_IS_ANNULE = false;
    private static final Boolean UPDATED_IS_ANNULE = true;

    private static final String DEFAULT_COMMENTAIRE = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTAIRE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_VALIDATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_VALIDATION = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private ValidationDevisRepository validationDevisRepository;

    @Autowired
    private ValidationDevisMapper validationDevisMapper;

    @Autowired
    private ValidationDevisService validationDevisService;

    @Autowired
    private ValidationDevisSearchRepository validationDevisSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restValidationDevisMockMvc;

    private ValidationDevis validationDevis;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ValidationDevisResource validationDevisResource = new ValidationDevisResource(validationDevisService);
        this.restValidationDevisMockMvc = MockMvcBuilders.standaloneSetup(validationDevisResource)
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
    public static ValidationDevis createEntity(EntityManager em) {
        ValidationDevis validationDevis = new ValidationDevis()
            .isValide(DEFAULT_IS_VALIDE)
            .isAnnule(DEFAULT_IS_ANNULE)
            .commentaire(DEFAULT_COMMENTAIRE)
            .dateValidation(DEFAULT_DATE_VALIDATION);
        return validationDevis;
    }

    @Before
    public void initTest() {
        validationDevisSearchRepository.deleteAll();
        validationDevis = createEntity(em);
    }

    @Test
    @Transactional
    public void createValidationDevis() throws Exception {
        int databaseSizeBeforeCreate = validationDevisRepository.findAll().size();

        // Create the ValidationDevis
        ValidationDevisDTO validationDevisDTO = validationDevisMapper.toDto(validationDevis);
        restValidationDevisMockMvc.perform(post("/api/validation-devis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(validationDevisDTO)))
            .andExpect(status().isCreated());

        // Validate the ValidationDevis in the database
        List<ValidationDevis> validationDevisList = validationDevisRepository.findAll();
        assertThat(validationDevisList).hasSize(databaseSizeBeforeCreate + 1);
        ValidationDevis testValidationDevis = validationDevisList.get(validationDevisList.size() - 1);
        assertThat(testValidationDevis.isIsValide()).isEqualTo(DEFAULT_IS_VALIDE);
        assertThat(testValidationDevis.isIsAnnule()).isEqualTo(DEFAULT_IS_ANNULE);
        assertThat(testValidationDevis.getCommentaire()).isEqualTo(DEFAULT_COMMENTAIRE);
        assertThat(testValidationDevis.getDateValidation()).isEqualTo(DEFAULT_DATE_VALIDATION);

        // Validate the ValidationDevis in Elasticsearch
        ValidationDevis validationDevisEs = validationDevisSearchRepository.findOne(testValidationDevis.getId());
        assertThat(validationDevisEs).isEqualToComparingFieldByField(testValidationDevis);
    }

    @Test
    @Transactional
    public void createValidationDevisWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = validationDevisRepository.findAll().size();

        // Create the ValidationDevis with an existing ID
        validationDevis.setId(1L);
        ValidationDevisDTO validationDevisDTO = validationDevisMapper.toDto(validationDevis);

        // An entity with an existing ID cannot be created, so this API call must fail
        restValidationDevisMockMvc.perform(post("/api/validation-devis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(validationDevisDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ValidationDevis> validationDevisList = validationDevisRepository.findAll();
        assertThat(validationDevisList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDateValidationIsRequired() throws Exception {
        int databaseSizeBeforeTest = validationDevisRepository.findAll().size();
        // set the field null
        validationDevis.setDateValidation(null);

        // Create the ValidationDevis, which fails.
        ValidationDevisDTO validationDevisDTO = validationDevisMapper.toDto(validationDevis);

        restValidationDevisMockMvc.perform(post("/api/validation-devis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(validationDevisDTO)))
            .andExpect(status().isBadRequest());

        List<ValidationDevis> validationDevisList = validationDevisRepository.findAll();
        assertThat(validationDevisList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllValidationDevis() throws Exception {
        // Initialize the database
        validationDevisRepository.saveAndFlush(validationDevis);

        // Get all the validationDevisList
        restValidationDevisMockMvc.perform(get("/api/validation-devis?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(validationDevis.getId().intValue())))
            .andExpect(jsonPath("$.[*].isValide").value(hasItem(DEFAULT_IS_VALIDE.booleanValue())))
            .andExpect(jsonPath("$.[*].isAnnule").value(hasItem(DEFAULT_IS_ANNULE.booleanValue())))
            .andExpect(jsonPath("$.[*].commentaire").value(hasItem(DEFAULT_COMMENTAIRE.toString())))
            .andExpect(jsonPath("$.[*].dateValidation").value(hasItem(DEFAULT_DATE_VALIDATION.toString())));
    }

    @Test
    @Transactional
    public void getValidationDevis() throws Exception {
        // Initialize the database
        validationDevisRepository.saveAndFlush(validationDevis);

        // Get the validationDevis
        restValidationDevisMockMvc.perform(get("/api/validation-devis/{id}", validationDevis.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(validationDevis.getId().intValue()))
            .andExpect(jsonPath("$.isValide").value(DEFAULT_IS_VALIDE.booleanValue()))
            .andExpect(jsonPath("$.isAnnule").value(DEFAULT_IS_ANNULE.booleanValue()))
            .andExpect(jsonPath("$.commentaire").value(DEFAULT_COMMENTAIRE.toString()))
            .andExpect(jsonPath("$.dateValidation").value(DEFAULT_DATE_VALIDATION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingValidationDevis() throws Exception {
        // Get the validationDevis
        restValidationDevisMockMvc.perform(get("/api/validation-devis/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateValidationDevis() throws Exception {
        // Initialize the database
        validationDevisRepository.saveAndFlush(validationDevis);
        validationDevisSearchRepository.save(validationDevis);
        int databaseSizeBeforeUpdate = validationDevisRepository.findAll().size();

        // Update the validationDevis
        ValidationDevis updatedValidationDevis = validationDevisRepository.findOne(validationDevis.getId());
        updatedValidationDevis
            .isValide(UPDATED_IS_VALIDE)
            .isAnnule(UPDATED_IS_ANNULE)
            .commentaire(UPDATED_COMMENTAIRE)
            .dateValidation(UPDATED_DATE_VALIDATION);
        ValidationDevisDTO validationDevisDTO = validationDevisMapper.toDto(updatedValidationDevis);

        restValidationDevisMockMvc.perform(put("/api/validation-devis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(validationDevisDTO)))
            .andExpect(status().isOk());

        // Validate the ValidationDevis in the database
        List<ValidationDevis> validationDevisList = validationDevisRepository.findAll();
        assertThat(validationDevisList).hasSize(databaseSizeBeforeUpdate);
        ValidationDevis testValidationDevis = validationDevisList.get(validationDevisList.size() - 1);
        assertThat(testValidationDevis.isIsValide()).isEqualTo(UPDATED_IS_VALIDE);
        assertThat(testValidationDevis.isIsAnnule()).isEqualTo(UPDATED_IS_ANNULE);
        assertThat(testValidationDevis.getCommentaire()).isEqualTo(UPDATED_COMMENTAIRE);
        assertThat(testValidationDevis.getDateValidation()).isEqualTo(UPDATED_DATE_VALIDATION);

        // Validate the ValidationDevis in Elasticsearch
        ValidationDevis validationDevisEs = validationDevisSearchRepository.findOne(testValidationDevis.getId());
        assertThat(validationDevisEs).isEqualToComparingFieldByField(testValidationDevis);
    }

    @Test
    @Transactional
    public void updateNonExistingValidationDevis() throws Exception {
        int databaseSizeBeforeUpdate = validationDevisRepository.findAll().size();

        // Create the ValidationDevis
        ValidationDevisDTO validationDevisDTO = validationDevisMapper.toDto(validationDevis);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restValidationDevisMockMvc.perform(put("/api/validation-devis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(validationDevisDTO)))
            .andExpect(status().isCreated());

        // Validate the ValidationDevis in the database
        List<ValidationDevis> validationDevisList = validationDevisRepository.findAll();
        assertThat(validationDevisList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteValidationDevis() throws Exception {
        // Initialize the database
        validationDevisRepository.saveAndFlush(validationDevis);
        validationDevisSearchRepository.save(validationDevis);
        int databaseSizeBeforeDelete = validationDevisRepository.findAll().size();

        // Get the validationDevis
        restValidationDevisMockMvc.perform(delete("/api/validation-devis/{id}", validationDevis.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean validationDevisExistsInEs = validationDevisSearchRepository.exists(validationDevis.getId());
        assertThat(validationDevisExistsInEs).isFalse();

        // Validate the database is empty
        List<ValidationDevis> validationDevisList = validationDevisRepository.findAll();
        assertThat(validationDevisList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchValidationDevis() throws Exception {
        // Initialize the database
        validationDevisRepository.saveAndFlush(validationDevis);
        validationDevisSearchRepository.save(validationDevis);

        // Search the validationDevis
        restValidationDevisMockMvc.perform(get("/api/_search/validation-devis?query=id:" + validationDevis.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(validationDevis.getId().intValue())))
            .andExpect(jsonPath("$.[*].isValide").value(hasItem(DEFAULT_IS_VALIDE.booleanValue())))
            .andExpect(jsonPath("$.[*].isAnnule").value(hasItem(DEFAULT_IS_ANNULE.booleanValue())))
            .andExpect(jsonPath("$.[*].commentaire").value(hasItem(DEFAULT_COMMENTAIRE.toString())))
            .andExpect(jsonPath("$.[*].dateValidation").value(hasItem(DEFAULT_DATE_VALIDATION.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ValidationDevis.class);
        ValidationDevis validationDevis1 = new ValidationDevis();
        validationDevis1.setId(1L);
        ValidationDevis validationDevis2 = new ValidationDevis();
        validationDevis2.setId(validationDevis1.getId());
        assertThat(validationDevis1).isEqualTo(validationDevis2);
        validationDevis2.setId(2L);
        assertThat(validationDevis1).isNotEqualTo(validationDevis2);
        validationDevis1.setId(null);
        assertThat(validationDevis1).isNotEqualTo(validationDevis2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ValidationDevisDTO.class);
        ValidationDevisDTO validationDevisDTO1 = new ValidationDevisDTO();
        validationDevisDTO1.setId(1L);
        ValidationDevisDTO validationDevisDTO2 = new ValidationDevisDTO();
        assertThat(validationDevisDTO1).isNotEqualTo(validationDevisDTO2);
        validationDevisDTO2.setId(validationDevisDTO1.getId());
        assertThat(validationDevisDTO1).isEqualTo(validationDevisDTO2);
        validationDevisDTO2.setId(2L);
        assertThat(validationDevisDTO1).isNotEqualTo(validationDevisDTO2);
        validationDevisDTO1.setId(null);
        assertThat(validationDevisDTO1).isNotEqualTo(validationDevisDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(validationDevisMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(validationDevisMapper.fromId(null)).isNull();
    }
}
