package com.gaconnecte.auxilium.web.rest;

import com.gaconnecte.auxilium.AuxiliumApp;

import com.gaconnecte.auxilium.domain.RefEtatBs;
import com.gaconnecte.auxilium.repository.RefEtatBsRepository;
import com.gaconnecte.auxilium.service.RefEtatBsService;
import com.gaconnecte.auxilium.repository.search.RefEtatBsSearchRepository;
import com.gaconnecte.auxilium.service.dto.RefEtatBsDTO;
import com.gaconnecte.auxilium.service.mapper.RefEtatBsMapper;
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
 * Test class for the RefEtatBsResource REST controller.
 *
 * @see RefEtatBsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuxiliumApp.class)
public class RefEtatBsResourceIntTest {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    @Autowired
    private RefEtatBsRepository refEtatBsRepository;

    @Autowired
    private RefEtatBsMapper refEtatBsMapper;

    @Autowired
    private RefEtatBsService refEtatBsService;

    @Autowired
    private RefEtatBsSearchRepository refEtatBsSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRefEtatBsMockMvc;

    private RefEtatBs refEtatBs;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RefEtatBsResource refEtatBsResource = new RefEtatBsResource(refEtatBsService);
        this.restRefEtatBsMockMvc = MockMvcBuilders.standaloneSetup(refEtatBsResource)
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
    public static RefEtatBs createEntity(EntityManager em) {
        RefEtatBs refEtatBs = new RefEtatBs()
            .libelle(DEFAULT_LIBELLE);
        return refEtatBs;
    }

    @Before
    public void initTest() {
        refEtatBsSearchRepository.deleteAll();
        refEtatBs = createEntity(em);
    }

    @Test
    @Transactional
    public void createRefEtatBs() throws Exception {
        int databaseSizeBeforeCreate = refEtatBsRepository.findAll().size();

        // Create the RefEtatBs
        RefEtatBsDTO refEtatBsDTO = refEtatBsMapper.toDto(refEtatBs);
        restRefEtatBsMockMvc.perform(post("/api/ref-etat-bs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refEtatBsDTO)))
            .andExpect(status().isCreated());

        // Validate the RefEtatBs in the database
        List<RefEtatBs> refEtatBsList = refEtatBsRepository.findAll();
        assertThat(refEtatBsList).hasSize(databaseSizeBeforeCreate + 1);
        RefEtatBs testRefEtatBs = refEtatBsList.get(refEtatBsList.size() - 1);
        assertThat(testRefEtatBs.getLibelle()).isEqualTo(DEFAULT_LIBELLE);

        // Validate the RefEtatBs in Elasticsearch
        RefEtatBs refEtatBsEs = refEtatBsSearchRepository.findOne(testRefEtatBs.getId());
        assertThat(refEtatBsEs).isEqualToComparingFieldByField(testRefEtatBs);
    }

    @Test
    @Transactional
    public void createRefEtatBsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = refEtatBsRepository.findAll().size();

        // Create the RefEtatBs with an existing ID
        refEtatBs.setId(1L);
        RefEtatBsDTO refEtatBsDTO = refEtatBsMapper.toDto(refEtatBs);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRefEtatBsMockMvc.perform(post("/api/ref-etat-bs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refEtatBsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<RefEtatBs> refEtatBsList = refEtatBsRepository.findAll();
        assertThat(refEtatBsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = refEtatBsRepository.findAll().size();
        // set the field null
        refEtatBs.setLibelle(null);

        // Create the RefEtatBs, which fails.
        RefEtatBsDTO refEtatBsDTO = refEtatBsMapper.toDto(refEtatBs);

        restRefEtatBsMockMvc.perform(post("/api/ref-etat-bs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refEtatBsDTO)))
            .andExpect(status().isBadRequest());

        List<RefEtatBs> refEtatBsList = refEtatBsRepository.findAll();
        assertThat(refEtatBsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRefEtatBs() throws Exception {
        // Initialize the database
        refEtatBsRepository.saveAndFlush(refEtatBs);

        // Get all the refEtatBsList
        restRefEtatBsMockMvc.perform(get("/api/ref-etat-bs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(refEtatBs.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())));
    }

    @Test
    @Transactional
    public void getRefEtatBs() throws Exception {
        // Initialize the database
        refEtatBsRepository.saveAndFlush(refEtatBs);

        // Get the refEtatBs
        restRefEtatBsMockMvc.perform(get("/api/ref-etat-bs/{id}", refEtatBs.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(refEtatBs.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRefEtatBs() throws Exception {
        // Get the refEtatBs
        restRefEtatBsMockMvc.perform(get("/api/ref-etat-bs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRefEtatBs() throws Exception {
        // Initialize the database
        refEtatBsRepository.saveAndFlush(refEtatBs);
        refEtatBsSearchRepository.save(refEtatBs);
        int databaseSizeBeforeUpdate = refEtatBsRepository.findAll().size();

        // Update the refEtatBs
        RefEtatBs updatedRefEtatBs = refEtatBsRepository.findOne(refEtatBs.getId());
        updatedRefEtatBs
            .libelle(UPDATED_LIBELLE);
        RefEtatBsDTO refEtatBsDTO = refEtatBsMapper.toDto(updatedRefEtatBs);

        restRefEtatBsMockMvc.perform(put("/api/ref-etat-bs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refEtatBsDTO)))
            .andExpect(status().isOk());

        // Validate the RefEtatBs in the database
        List<RefEtatBs> refEtatBsList = refEtatBsRepository.findAll();
        assertThat(refEtatBsList).hasSize(databaseSizeBeforeUpdate);
        RefEtatBs testRefEtatBs = refEtatBsList.get(refEtatBsList.size() - 1);
        assertThat(testRefEtatBs.getLibelle()).isEqualTo(UPDATED_LIBELLE);

        // Validate the RefEtatBs in Elasticsearch
        RefEtatBs refEtatBsEs = refEtatBsSearchRepository.findOne(testRefEtatBs.getId());
        assertThat(refEtatBsEs).isEqualToComparingFieldByField(testRefEtatBs);
    }

    @Test
    @Transactional
    public void updateNonExistingRefEtatBs() throws Exception {
        int databaseSizeBeforeUpdate = refEtatBsRepository.findAll().size();

        // Create the RefEtatBs
        RefEtatBsDTO refEtatBsDTO = refEtatBsMapper.toDto(refEtatBs);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRefEtatBsMockMvc.perform(put("/api/ref-etat-bs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refEtatBsDTO)))
            .andExpect(status().isCreated());

        // Validate the RefEtatBs in the database
        List<RefEtatBs> refEtatBsList = refEtatBsRepository.findAll();
        assertThat(refEtatBsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRefEtatBs() throws Exception {
        // Initialize the database
        refEtatBsRepository.saveAndFlush(refEtatBs);
        refEtatBsSearchRepository.save(refEtatBs);
        int databaseSizeBeforeDelete = refEtatBsRepository.findAll().size();

        // Get the refEtatBs
        restRefEtatBsMockMvc.perform(delete("/api/ref-etat-bs/{id}", refEtatBs.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean refEtatBsExistsInEs = refEtatBsSearchRepository.exists(refEtatBs.getId());
        assertThat(refEtatBsExistsInEs).isFalse();

        // Validate the database is empty
        List<RefEtatBs> refEtatBsList = refEtatBsRepository.findAll();
        assertThat(refEtatBsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchRefEtatBs() throws Exception {
        // Initialize the database
        refEtatBsRepository.saveAndFlush(refEtatBs);
        refEtatBsSearchRepository.save(refEtatBs);

        // Search the refEtatBs
        restRefEtatBsMockMvc.perform(get("/api/_search/ref-etat-bs?query=id:" + refEtatBs.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(refEtatBs.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RefEtatBs.class);
        RefEtatBs refEtatBs1 = new RefEtatBs();
        refEtatBs1.setId(1L);
        RefEtatBs refEtatBs2 = new RefEtatBs();
        refEtatBs2.setId(refEtatBs1.getId());
        assertThat(refEtatBs1).isEqualTo(refEtatBs2);
        refEtatBs2.setId(2L);
        assertThat(refEtatBs1).isNotEqualTo(refEtatBs2);
        refEtatBs1.setId(null);
        assertThat(refEtatBs1).isNotEqualTo(refEtatBs2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RefEtatBsDTO.class);
        RefEtatBsDTO refEtatBsDTO1 = new RefEtatBsDTO();
        refEtatBsDTO1.setId(1L);
        RefEtatBsDTO refEtatBsDTO2 = new RefEtatBsDTO();
        assertThat(refEtatBsDTO1).isNotEqualTo(refEtatBsDTO2);
        refEtatBsDTO2.setId(refEtatBsDTO1.getId());
        assertThat(refEtatBsDTO1).isEqualTo(refEtatBsDTO2);
        refEtatBsDTO2.setId(2L);
        assertThat(refEtatBsDTO1).isNotEqualTo(refEtatBsDTO2);
        refEtatBsDTO1.setId(null);
        assertThat(refEtatBsDTO1).isNotEqualTo(refEtatBsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(refEtatBsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(refEtatBsMapper.fromId(null)).isNull();
    }
}
