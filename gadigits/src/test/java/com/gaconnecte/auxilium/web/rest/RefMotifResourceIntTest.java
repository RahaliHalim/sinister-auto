package com.gaconnecte.auxilium.web.rest;

import com.gaconnecte.auxilium.AuxiliumApp;

import com.gaconnecte.auxilium.domain.RefMotif;
import com.gaconnecte.auxilium.repository.RefMotifRepository;
import com.gaconnecte.auxilium.service.RefMotifService;
import com.gaconnecte.auxilium.repository.search.RefMotifSearchRepository;
import com.gaconnecte.auxilium.service.dto.RefMotifDTO;
import com.gaconnecte.auxilium.service.mapper.RefMotifMapper;
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
 * Test class for the RefMotifResource REST controller.
 *
 * @see RefMotifResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuxiliumApp.class)
public class RefMotifResourceIntTest {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    @Autowired
    private RefMotifRepository refMotifRepository;

    @Autowired
    private RefMotifMapper refMotifMapper;

    @Autowired
    private RefMotifService refMotifService;

    @Autowired
    private RefMotifSearchRepository refMotifSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRefMotifMockMvc;

    private RefMotif refMotif;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RefMotifResource refMotifResource = new RefMotifResource(refMotifService);
        this.restRefMotifMockMvc = MockMvcBuilders.standaloneSetup(refMotifResource)
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
    public static RefMotif createEntity(EntityManager em) {
        RefMotif refMotif = new RefMotif()
            .libelle(DEFAULT_LIBELLE);
        return refMotif;
    }

    @Before
    public void initTest() {
        refMotifSearchRepository.deleteAll();
        refMotif = createEntity(em);
    }

    @Test
    @Transactional
    public void createRefMotif() throws Exception {
        int databaseSizeBeforeCreate = refMotifRepository.findAll().size();

        // Create the RefMotif
        RefMotifDTO refMotifDTO = refMotifMapper.toDto(refMotif);
        restRefMotifMockMvc.perform(post("/api/ref-motifs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refMotifDTO)))
            .andExpect(status().isCreated());

        // Validate the RefMotif in the database
        List<RefMotif> refMotifList = refMotifRepository.findAll();
        assertThat(refMotifList).hasSize(databaseSizeBeforeCreate + 1);
        RefMotif testRefMotif = refMotifList.get(refMotifList.size() - 1);
        assertThat(testRefMotif.getLibelle()).isEqualTo(DEFAULT_LIBELLE);

        // Validate the RefMotif in Elasticsearch
        RefMotif refMotifEs = refMotifSearchRepository.findOne(testRefMotif.getId());
        assertThat(refMotifEs).isEqualToComparingFieldByField(testRefMotif);
    }

    @Test
    @Transactional
    public void createRefMotifWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = refMotifRepository.findAll().size();

        // Create the RefMotif with an existing ID
        refMotif.setId(1L);
        RefMotifDTO refMotifDTO = refMotifMapper.toDto(refMotif);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRefMotifMockMvc.perform(post("/api/ref-motifs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refMotifDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<RefMotif> refMotifList = refMotifRepository.findAll();
        assertThat(refMotifList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = refMotifRepository.findAll().size();
        // set the field null
        refMotif.setLibelle(null);

        // Create the RefMotif, which fails.
        RefMotifDTO refMotifDTO = refMotifMapper.toDto(refMotif);

        restRefMotifMockMvc.perform(post("/api/ref-motifs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refMotifDTO)))
            .andExpect(status().isBadRequest());

        List<RefMotif> refMotifList = refMotifRepository.findAll();
        assertThat(refMotifList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRefMotifs() throws Exception {
        // Initialize the database
        refMotifRepository.saveAndFlush(refMotif);

        // Get all the refMotifList
        restRefMotifMockMvc.perform(get("/api/ref-motifs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(refMotif.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())));
    }

    @Test
    @Transactional
    public void getRefMotif() throws Exception {
        // Initialize the database
        refMotifRepository.saveAndFlush(refMotif);

        // Get the refMotif
        restRefMotifMockMvc.perform(get("/api/ref-motifs/{id}", refMotif.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(refMotif.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRefMotif() throws Exception {
        // Get the refMotif
        restRefMotifMockMvc.perform(get("/api/ref-motifs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRefMotif() throws Exception {
        // Initialize the database
        refMotifRepository.saveAndFlush(refMotif);
        refMotifSearchRepository.save(refMotif);
        int databaseSizeBeforeUpdate = refMotifRepository.findAll().size();

        // Update the refMotif
        RefMotif updatedRefMotif = refMotifRepository.findOne(refMotif.getId());
        updatedRefMotif
            .libelle(UPDATED_LIBELLE);
        RefMotifDTO refMotifDTO = refMotifMapper.toDto(updatedRefMotif);

        restRefMotifMockMvc.perform(put("/api/ref-motifs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refMotifDTO)))
            .andExpect(status().isOk());

        // Validate the RefMotif in the database
        List<RefMotif> refMotifList = refMotifRepository.findAll();
        assertThat(refMotifList).hasSize(databaseSizeBeforeUpdate);
        RefMotif testRefMotif = refMotifList.get(refMotifList.size() - 1);
        assertThat(testRefMotif.getLibelle()).isEqualTo(UPDATED_LIBELLE);

        // Validate the RefMotif in Elasticsearch
        RefMotif refMotifEs = refMotifSearchRepository.findOne(testRefMotif.getId());
        assertThat(refMotifEs).isEqualToComparingFieldByField(testRefMotif);
    }

    @Test
    @Transactional
    public void updateNonExistingRefMotif() throws Exception {
        int databaseSizeBeforeUpdate = refMotifRepository.findAll().size();

        // Create the RefMotif
        RefMotifDTO refMotifDTO = refMotifMapper.toDto(refMotif);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRefMotifMockMvc.perform(put("/api/ref-motifs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refMotifDTO)))
            .andExpect(status().isCreated());

        // Validate the RefMotif in the database
        List<RefMotif> refMotifList = refMotifRepository.findAll();
        assertThat(refMotifList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRefMotif() throws Exception {
        // Initialize the database
        refMotifRepository.saveAndFlush(refMotif);
        refMotifSearchRepository.save(refMotif);
        int databaseSizeBeforeDelete = refMotifRepository.findAll().size();

        // Get the refMotif
        restRefMotifMockMvc.perform(delete("/api/ref-motifs/{id}", refMotif.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean refMotifExistsInEs = refMotifSearchRepository.exists(refMotif.getId());
        assertThat(refMotifExistsInEs).isFalse();

        // Validate the database is empty
        List<RefMotif> refMotifList = refMotifRepository.findAll();
        assertThat(refMotifList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchRefMotif() throws Exception {
        // Initialize the database
        refMotifRepository.saveAndFlush(refMotif);
        refMotifSearchRepository.save(refMotif);

        // Search the refMotif
        restRefMotifMockMvc.perform(get("/api/_search/ref-motifs?query=id:" + refMotif.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(refMotif.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RefMotif.class);
        RefMotif refMotif1 = new RefMotif();
        refMotif1.setId(1L);
        RefMotif refMotif2 = new RefMotif();
        refMotif2.setId(refMotif1.getId());
        assertThat(refMotif1).isEqualTo(refMotif2);
        refMotif2.setId(2L);
        assertThat(refMotif1).isNotEqualTo(refMotif2);
        refMotif1.setId(null);
        assertThat(refMotif1).isNotEqualTo(refMotif2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RefMotifDTO.class);
        RefMotifDTO refMotifDTO1 = new RefMotifDTO();
        refMotifDTO1.setId(1L);
        RefMotifDTO refMotifDTO2 = new RefMotifDTO();
        assertThat(refMotifDTO1).isNotEqualTo(refMotifDTO2);
        refMotifDTO2.setId(refMotifDTO1.getId());
        assertThat(refMotifDTO1).isEqualTo(refMotifDTO2);
        refMotifDTO2.setId(2L);
        assertThat(refMotifDTO1).isNotEqualTo(refMotifDTO2);
        refMotifDTO1.setId(null);
        assertThat(refMotifDTO1).isNotEqualTo(refMotifDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(refMotifMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(refMotifMapper.fromId(null)).isNull();
    }
}
