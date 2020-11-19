package com.gaconnecte.auxilium.web.rest;

import com.gaconnecte.auxilium.AuxiliumApp;

import com.gaconnecte.auxilium.domain.RefMateriel;
import com.gaconnecte.auxilium.repository.RefMaterielRepository;
import com.gaconnecte.auxilium.service.RefMaterielService;
import com.gaconnecte.auxilium.repository.search.RefMaterielSearchRepository;
import com.gaconnecte.auxilium.service.dto.RefMaterielDTO;
import com.gaconnecte.auxilium.service.mapper.RefMaterielMapper;
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
 * Test class for the RefMaterielResource REST controller.
 *
 * @see RefMaterielResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuxiliumApp.class)
public class RefMaterielResourceIntTest {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_OBLIGATOIRE_CNG = false;
    private static final Boolean UPDATED_OBLIGATOIRE_CNG = true;

    @Autowired
    private RefMaterielRepository refMaterielRepository;

    @Autowired
    private RefMaterielMapper refMaterielMapper;

    @Autowired
    private RefMaterielService refMaterielService;

    @Autowired
    private RefMaterielSearchRepository refMaterielSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRefMaterielMockMvc;

    private RefMateriel refMateriel;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RefMaterielResource refMaterielResource = new RefMaterielResource(refMaterielService);
        this.restRefMaterielMockMvc = MockMvcBuilders.standaloneSetup(refMaterielResource)
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
    public static RefMateriel createEntity(EntityManager em) {
        RefMateriel refMateriel = new RefMateriel()
            .libelle(DEFAULT_LIBELLE)
            .description(DEFAULT_DESCRIPTION)
            .obligatoireCng(DEFAULT_OBLIGATOIRE_CNG);
        return refMateriel;
    }

    @Before
    public void initTest() {
        refMaterielSearchRepository.deleteAll();
        refMateriel = createEntity(em);
    }

    @Test
    @Transactional
    public void createRefMateriel() throws Exception {
        int databaseSizeBeforeCreate = refMaterielRepository.findAll().size();

        // Create the RefMateriel
        RefMaterielDTO refMaterielDTO = refMaterielMapper.toDto(refMateriel);
        restRefMaterielMockMvc.perform(post("/api/ref-materiels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refMaterielDTO)))
            .andExpect(status().isCreated());

        // Validate the RefMateriel in the database
        List<RefMateriel> refMaterielList = refMaterielRepository.findAll();
        assertThat(refMaterielList).hasSize(databaseSizeBeforeCreate + 1);
        RefMateriel testRefMateriel = refMaterielList.get(refMaterielList.size() - 1);
        assertThat(testRefMateriel.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testRefMateriel.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testRefMateriel.isObligatoireCng()).isEqualTo(DEFAULT_OBLIGATOIRE_CNG);

        // Validate the RefMateriel in Elasticsearch
        RefMateriel refMaterielEs = refMaterielSearchRepository.findOne(testRefMateriel.getId());
        assertThat(refMaterielEs).isEqualToComparingFieldByField(testRefMateriel);
    }

    @Test
    @Transactional
    public void createRefMaterielWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = refMaterielRepository.findAll().size();

        // Create the RefMateriel with an existing ID
        refMateriel.setId(1L);
        RefMaterielDTO refMaterielDTO = refMaterielMapper.toDto(refMateriel);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRefMaterielMockMvc.perform(post("/api/ref-materiels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refMaterielDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<RefMateriel> refMaterielList = refMaterielRepository.findAll();
        assertThat(refMaterielList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = refMaterielRepository.findAll().size();
        // set the field null
        refMateriel.setLibelle(null);

        // Create the RefMateriel, which fails.
        RefMaterielDTO refMaterielDTO = refMaterielMapper.toDto(refMateriel);

        restRefMaterielMockMvc.perform(post("/api/ref-materiels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refMaterielDTO)))
            .andExpect(status().isBadRequest());

        List<RefMateriel> refMaterielList = refMaterielRepository.findAll();
        assertThat(refMaterielList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRefMateriels() throws Exception {
        // Initialize the database
        refMaterielRepository.saveAndFlush(refMateriel);

        // Get all the refMaterielList
        restRefMaterielMockMvc.perform(get("/api/ref-materiels?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(refMateriel.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].obligatoireCng").value(hasItem(DEFAULT_OBLIGATOIRE_CNG.booleanValue())));
    }

    @Test
    @Transactional
    public void getRefMateriel() throws Exception {
        // Initialize the database
        refMaterielRepository.saveAndFlush(refMateriel);

        // Get the refMateriel
        restRefMaterielMockMvc.perform(get("/api/ref-materiels/{id}", refMateriel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(refMateriel.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.obligatoireCng").value(DEFAULT_OBLIGATOIRE_CNG.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingRefMateriel() throws Exception {
        // Get the refMateriel
        restRefMaterielMockMvc.perform(get("/api/ref-materiels/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRefMateriel() throws Exception {
        // Initialize the database
        refMaterielRepository.saveAndFlush(refMateriel);
        refMaterielSearchRepository.save(refMateriel);
        int databaseSizeBeforeUpdate = refMaterielRepository.findAll().size();

        // Update the refMateriel
        RefMateriel updatedRefMateriel = refMaterielRepository.findOne(refMateriel.getId());
        updatedRefMateriel
            .libelle(UPDATED_LIBELLE)
            .description(UPDATED_DESCRIPTION)
            .obligatoireCng(UPDATED_OBLIGATOIRE_CNG);
        RefMaterielDTO refMaterielDTO = refMaterielMapper.toDto(updatedRefMateriel);

        restRefMaterielMockMvc.perform(put("/api/ref-materiels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refMaterielDTO)))
            .andExpect(status().isOk());

        // Validate the RefMateriel in the database
        List<RefMateriel> refMaterielList = refMaterielRepository.findAll();
        assertThat(refMaterielList).hasSize(databaseSizeBeforeUpdate);
        RefMateriel testRefMateriel = refMaterielList.get(refMaterielList.size() - 1);
        assertThat(testRefMateriel.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testRefMateriel.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRefMateriel.isObligatoireCng()).isEqualTo(UPDATED_OBLIGATOIRE_CNG);

        // Validate the RefMateriel in Elasticsearch
        RefMateriel refMaterielEs = refMaterielSearchRepository.findOne(testRefMateriel.getId());
        assertThat(refMaterielEs).isEqualToComparingFieldByField(testRefMateriel);
    }

    @Test
    @Transactional
    public void updateNonExistingRefMateriel() throws Exception {
        int databaseSizeBeforeUpdate = refMaterielRepository.findAll().size();

        // Create the RefMateriel
        RefMaterielDTO refMaterielDTO = refMaterielMapper.toDto(refMateriel);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRefMaterielMockMvc.perform(put("/api/ref-materiels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refMaterielDTO)))
            .andExpect(status().isCreated());

        // Validate the RefMateriel in the database
        List<RefMateriel> refMaterielList = refMaterielRepository.findAll();
        assertThat(refMaterielList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRefMateriel() throws Exception {
        // Initialize the database
        refMaterielRepository.saveAndFlush(refMateriel);
        refMaterielSearchRepository.save(refMateriel);
        int databaseSizeBeforeDelete = refMaterielRepository.findAll().size();

        // Get the refMateriel
        restRefMaterielMockMvc.perform(delete("/api/ref-materiels/{id}", refMateriel.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean refMaterielExistsInEs = refMaterielSearchRepository.exists(refMateriel.getId());
        assertThat(refMaterielExistsInEs).isFalse();

        // Validate the database is empty
        List<RefMateriel> refMaterielList = refMaterielRepository.findAll();
        assertThat(refMaterielList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchRefMateriel() throws Exception {
        // Initialize the database
        refMaterielRepository.saveAndFlush(refMateriel);
        refMaterielSearchRepository.save(refMateriel);

        // Search the refMateriel
        restRefMaterielMockMvc.perform(get("/api/_search/ref-materiels?query=id:" + refMateriel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(refMateriel.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].obligatoireCng").value(hasItem(DEFAULT_OBLIGATOIRE_CNG.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RefMateriel.class);
        RefMateriel refMateriel1 = new RefMateriel();
        refMateriel1.setId(1L);
        RefMateriel refMateriel2 = new RefMateriel();
        refMateriel2.setId(refMateriel1.getId());
        assertThat(refMateriel1).isEqualTo(refMateriel2);
        refMateriel2.setId(2L);
        assertThat(refMateriel1).isNotEqualTo(refMateriel2);
        refMateriel1.setId(null);
        assertThat(refMateriel1).isNotEqualTo(refMateriel2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RefMaterielDTO.class);
        RefMaterielDTO refMaterielDTO1 = new RefMaterielDTO();
        refMaterielDTO1.setId(1L);
        RefMaterielDTO refMaterielDTO2 = new RefMaterielDTO();
        assertThat(refMaterielDTO1).isNotEqualTo(refMaterielDTO2);
        refMaterielDTO2.setId(refMaterielDTO1.getId());
        assertThat(refMaterielDTO1).isEqualTo(refMaterielDTO2);
        refMaterielDTO2.setId(2L);
        assertThat(refMaterielDTO1).isNotEqualTo(refMaterielDTO2);
        refMaterielDTO1.setId(null);
        assertThat(refMaterielDTO1).isNotEqualTo(refMaterielDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(refMaterielMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(refMaterielMapper.fromId(null)).isNull();
    }
}
