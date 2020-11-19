package com.gaconnecte.auxilium.web.rest;

import com.gaconnecte.auxilium.AuxiliumApp;

import com.gaconnecte.auxilium.domain.RefEtatDossier;
import com.gaconnecte.auxilium.repository.RefEtatDossierRepository;
import com.gaconnecte.auxilium.service.RefEtatDossierService;
import com.gaconnecte.auxilium.repository.search.RefEtatDossierSearchRepository;
import com.gaconnecte.auxilium.service.dto.RefEtatDossierDTO;
import com.gaconnecte.auxilium.service.mapper.RefEtatDossierMapper;
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
 * Test class for the RefEtatDossierResource REST controller.
 *
 * @see RefEtatDossierResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuxiliumApp.class)
public class RefEtatDossierResourceIntTest {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    @Autowired
    private RefEtatDossierRepository refEtatDossierRepository;

    @Autowired
    private RefEtatDossierMapper refEtatDossierMapper;

    @Autowired
    private RefEtatDossierService refEtatDossierService;

    @Autowired
    private RefEtatDossierSearchRepository refEtatDossierSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRefEtatDossierMockMvc;

    private RefEtatDossier refEtatDossier;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RefEtatDossierResource refEtatDossierResource = new RefEtatDossierResource(refEtatDossierService);
        this.restRefEtatDossierMockMvc = MockMvcBuilders.standaloneSetup(refEtatDossierResource)
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
    public static RefEtatDossier createEntity(EntityManager em) {
        RefEtatDossier refEtatDossier = new RefEtatDossier()
            .libelle(DEFAULT_LIBELLE);
        return refEtatDossier;
    }

    @Before
    public void initTest() {
        refEtatDossierSearchRepository.deleteAll();
        refEtatDossier = createEntity(em);
    }

    @Test
    @Transactional
    public void createRefEtatDossier() throws Exception {
        int databaseSizeBeforeCreate = refEtatDossierRepository.findAll().size();

        // Create the RefEtatDossier
        RefEtatDossierDTO refEtatDossierDTO = refEtatDossierMapper.toDto(refEtatDossier);
        restRefEtatDossierMockMvc.perform(post("/api/ref-etat-dossiers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refEtatDossierDTO)))
            .andExpect(status().isCreated());

        // Validate the RefEtatDossier in the database
        List<RefEtatDossier> refEtatDossierList = refEtatDossierRepository.findAll();
        assertThat(refEtatDossierList).hasSize(databaseSizeBeforeCreate + 1);
        RefEtatDossier testRefEtatDossier = refEtatDossierList.get(refEtatDossierList.size() - 1);
        assertThat(testRefEtatDossier.getLibelle()).isEqualTo(DEFAULT_LIBELLE);

        // Validate the RefEtatDossier in Elasticsearch
        RefEtatDossier refEtatDossierEs = refEtatDossierSearchRepository.findOne(testRefEtatDossier.getId());
        assertThat(refEtatDossierEs).isEqualToComparingFieldByField(testRefEtatDossier);
    }

    @Test
    @Transactional
    public void createRefEtatDossierWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = refEtatDossierRepository.findAll().size();

        // Create the RefEtatDossier with an existing ID
        refEtatDossier.setId(1L);
        RefEtatDossierDTO refEtatDossierDTO = refEtatDossierMapper.toDto(refEtatDossier);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRefEtatDossierMockMvc.perform(post("/api/ref-etat-dossiers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refEtatDossierDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<RefEtatDossier> refEtatDossierList = refEtatDossierRepository.findAll();
        assertThat(refEtatDossierList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = refEtatDossierRepository.findAll().size();
        // set the field null
        refEtatDossier.setLibelle(null);

        // Create the RefEtatDossier, which fails.
        RefEtatDossierDTO refEtatDossierDTO = refEtatDossierMapper.toDto(refEtatDossier);

        restRefEtatDossierMockMvc.perform(post("/api/ref-etat-dossiers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refEtatDossierDTO)))
            .andExpect(status().isBadRequest());

        List<RefEtatDossier> refEtatDossierList = refEtatDossierRepository.findAll();
        assertThat(refEtatDossierList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRefEtatDossiers() throws Exception {
        // Initialize the database
        refEtatDossierRepository.saveAndFlush(refEtatDossier);

        // Get all the refEtatDossierList
        restRefEtatDossierMockMvc.perform(get("/api/ref-etat-dossiers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(refEtatDossier.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())));
    }

    @Test
    @Transactional
    public void getRefEtatDossier() throws Exception {
        // Initialize the database
        refEtatDossierRepository.saveAndFlush(refEtatDossier);

        // Get the refEtatDossier
        restRefEtatDossierMockMvc.perform(get("/api/ref-etat-dossiers/{id}", refEtatDossier.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(refEtatDossier.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRefEtatDossier() throws Exception {
        // Get the refEtatDossier
        restRefEtatDossierMockMvc.perform(get("/api/ref-etat-dossiers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRefEtatDossier() throws Exception {
        // Initialize the database
        refEtatDossierRepository.saveAndFlush(refEtatDossier);
        refEtatDossierSearchRepository.save(refEtatDossier);
        int databaseSizeBeforeUpdate = refEtatDossierRepository.findAll().size();

        // Update the refEtatDossier
        RefEtatDossier updatedRefEtatDossier = refEtatDossierRepository.findOne(refEtatDossier.getId());
        updatedRefEtatDossier
            .libelle(UPDATED_LIBELLE);
        RefEtatDossierDTO refEtatDossierDTO = refEtatDossierMapper.toDto(updatedRefEtatDossier);

        restRefEtatDossierMockMvc.perform(put("/api/ref-etat-dossiers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refEtatDossierDTO)))
            .andExpect(status().isOk());

        // Validate the RefEtatDossier in the database
        List<RefEtatDossier> refEtatDossierList = refEtatDossierRepository.findAll();
        assertThat(refEtatDossierList).hasSize(databaseSizeBeforeUpdate);
        RefEtatDossier testRefEtatDossier = refEtatDossierList.get(refEtatDossierList.size() - 1);
        assertThat(testRefEtatDossier.getLibelle()).isEqualTo(UPDATED_LIBELLE);

        // Validate the RefEtatDossier in Elasticsearch
        RefEtatDossier refEtatDossierEs = refEtatDossierSearchRepository.findOne(testRefEtatDossier.getId());
        assertThat(refEtatDossierEs).isEqualToComparingFieldByField(testRefEtatDossier);
    }

    @Test
    @Transactional
    public void updateNonExistingRefEtatDossier() throws Exception {
        int databaseSizeBeforeUpdate = refEtatDossierRepository.findAll().size();

        // Create the RefEtatDossier
        RefEtatDossierDTO refEtatDossierDTO = refEtatDossierMapper.toDto(refEtatDossier);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRefEtatDossierMockMvc.perform(put("/api/ref-etat-dossiers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refEtatDossierDTO)))
            .andExpect(status().isCreated());

        // Validate the RefEtatDossier in the database
        List<RefEtatDossier> refEtatDossierList = refEtatDossierRepository.findAll();
        assertThat(refEtatDossierList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRefEtatDossier() throws Exception {
        // Initialize the database
        refEtatDossierRepository.saveAndFlush(refEtatDossier);
        refEtatDossierSearchRepository.save(refEtatDossier);
        int databaseSizeBeforeDelete = refEtatDossierRepository.findAll().size();

        // Get the refEtatDossier
        restRefEtatDossierMockMvc.perform(delete("/api/ref-etat-dossiers/{id}", refEtatDossier.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean refEtatDossierExistsInEs = refEtatDossierSearchRepository.exists(refEtatDossier.getId());
        assertThat(refEtatDossierExistsInEs).isFalse();

        // Validate the database is empty
        List<RefEtatDossier> refEtatDossierList = refEtatDossierRepository.findAll();
        assertThat(refEtatDossierList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchRefEtatDossier() throws Exception {
        // Initialize the database
        refEtatDossierRepository.saveAndFlush(refEtatDossier);
        refEtatDossierSearchRepository.save(refEtatDossier);

        // Search the refEtatDossier
        restRefEtatDossierMockMvc.perform(get("/api/_search/ref-etat-dossiers?query=id:" + refEtatDossier.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(refEtatDossier.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RefEtatDossier.class);
        RefEtatDossier refEtatDossier1 = new RefEtatDossier();
        refEtatDossier1.setId(1L);
        RefEtatDossier refEtatDossier2 = new RefEtatDossier();
        refEtatDossier2.setId(refEtatDossier1.getId());
        assertThat(refEtatDossier1).isEqualTo(refEtatDossier2);
        refEtatDossier2.setId(2L);
        assertThat(refEtatDossier1).isNotEqualTo(refEtatDossier2);
        refEtatDossier1.setId(null);
        assertThat(refEtatDossier1).isNotEqualTo(refEtatDossier2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RefEtatDossierDTO.class);
        RefEtatDossierDTO refEtatDossierDTO1 = new RefEtatDossierDTO();
        refEtatDossierDTO1.setId(1L);
        RefEtatDossierDTO refEtatDossierDTO2 = new RefEtatDossierDTO();
        assertThat(refEtatDossierDTO1).isNotEqualTo(refEtatDossierDTO2);
        refEtatDossierDTO2.setId(refEtatDossierDTO1.getId());
        assertThat(refEtatDossierDTO1).isEqualTo(refEtatDossierDTO2);
        refEtatDossierDTO2.setId(2L);
        assertThat(refEtatDossierDTO1).isNotEqualTo(refEtatDossierDTO2);
        refEtatDossierDTO1.setId(null);
        assertThat(refEtatDossierDTO1).isNotEqualTo(refEtatDossierDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(refEtatDossierMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(refEtatDossierMapper.fromId(null)).isNull();
    }
}
