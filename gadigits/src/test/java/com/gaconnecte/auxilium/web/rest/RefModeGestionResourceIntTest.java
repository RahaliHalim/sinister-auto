package com.gaconnecte.auxilium.web.rest;

import com.gaconnecte.auxilium.AuxiliumApp;

import com.gaconnecte.auxilium.domain.RefModeGestion;
import com.gaconnecte.auxilium.repository.RefModeGestionRepository;
import com.gaconnecte.auxilium.service.RefModeGestionService;
import com.gaconnecte.auxilium.repository.search.RefModeGestionSearchRepository;
import com.gaconnecte.auxilium.service.dto.RefModeGestionDTO;
import com.gaconnecte.auxilium.service.mapper.RefModeGestionMapper;
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
 * Test class for the RefModeGestionResource REST controller.
 *
 * @see RefModeGestionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuxiliumApp.class)
public class RefModeGestionResourceIntTest {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private RefModeGestionRepository refModeGestionRepository;

    @Autowired
    private RefModeGestionMapper refModeGestionMapper;

    @Autowired
    private RefModeGestionService refModeGestionService;

    @Autowired
    private RefModeGestionSearchRepository refModeGestionSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRefModeGestionMockMvc;

    private RefModeGestion refModeGestion;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RefModeGestionResource refModeGestionResource = new RefModeGestionResource(refModeGestionService);
        this.restRefModeGestionMockMvc = MockMvcBuilders.standaloneSetup(refModeGestionResource)
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
    public static RefModeGestion createEntity(EntityManager em) {
        RefModeGestion refModeGestion = new RefModeGestion()
            .libelle(DEFAULT_LIBELLE)
            .description(DEFAULT_DESCRIPTION);
            em.flush();
        return refModeGestion;
    }

    @Before
    public void initTest() {
        refModeGestionSearchRepository.deleteAll();
        refModeGestion = createEntity(em);
    }

    @Test
    @Transactional
    public void createRefModeGestion() throws Exception {
        int databaseSizeBeforeCreate = refModeGestionRepository.findAll().size();

        // Create the RefModeGestion
        RefModeGestionDTO refModeGestionDTO = refModeGestionMapper.toDto(refModeGestion);
        restRefModeGestionMockMvc.perform(post("/api/ref-mode-gestions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refModeGestionDTO)))
            .andExpect(status().isCreated());

        // Validate the RefModeGestion in the database
        List<RefModeGestion> refModeGestionList = refModeGestionRepository.findAll();
        assertThat(refModeGestionList).hasSize(databaseSizeBeforeCreate + 1);
        RefModeGestion testRefModeGestion = refModeGestionList.get(refModeGestionList.size() - 1);
        assertThat(testRefModeGestion.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testRefModeGestion.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the RefModeGestion in Elasticsearch
        RefModeGestion refModeGestionEs = refModeGestionSearchRepository.findOne(testRefModeGestion.getId());
        assertThat(refModeGestionEs).isEqualToComparingFieldByField(testRefModeGestion);
    }

    @Test
    @Transactional
    public void createRefModeGestionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = refModeGestionRepository.findAll().size();

        // Create the RefModeGestion with an existing ID
        refModeGestion.setId(1L);
        RefModeGestionDTO refModeGestionDTO = refModeGestionMapper.toDto(refModeGestion);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRefModeGestionMockMvc.perform(post("/api/ref-mode-gestions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refModeGestionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<RefModeGestion> refModeGestionList = refModeGestionRepository.findAll();
        assertThat(refModeGestionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = refModeGestionRepository.findAll().size();
        // set the field null
        refModeGestion.setLibelle(null);

        // Create the RefModeGestion, which fails.
        RefModeGestionDTO refModeGestionDTO = refModeGestionMapper.toDto(refModeGestion);

        restRefModeGestionMockMvc.perform(post("/api/ref-mode-gestions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refModeGestionDTO)))
            .andExpect(status().isBadRequest());

        List<RefModeGestion> refModeGestionList = refModeGestionRepository.findAll();
        assertThat(refModeGestionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRefModeGestions() throws Exception {
        // Initialize the database
        refModeGestionRepository.saveAndFlush(refModeGestion);

        // Get all the refModeGestionList
        restRefModeGestionMockMvc.perform(get("/api/ref-mode-gestions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(refModeGestion.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getRefModeGestion() throws Exception {
        // Initialize the database
        refModeGestionRepository.saveAndFlush(refModeGestion);

        // Get the refModeGestion
        restRefModeGestionMockMvc.perform(get("/api/ref-mode-gestions/{id}", refModeGestion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(refModeGestion.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRefModeGestion() throws Exception {
        // Get the refModeGestion
        restRefModeGestionMockMvc.perform(get("/api/ref-mode-gestions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRefModeGestion() throws Exception {
        // Initialize the database
        refModeGestionRepository.saveAndFlush(refModeGestion);
        refModeGestionSearchRepository.save(refModeGestion);
        int databaseSizeBeforeUpdate = refModeGestionRepository.findAll().size();

        // Update the refModeGestion
        RefModeGestion updatedRefModeGestion = refModeGestionRepository.findOne(refModeGestion.getId());
        updatedRefModeGestion
            .libelle(UPDATED_LIBELLE)
            .description(UPDATED_DESCRIPTION);
        RefModeGestionDTO refModeGestionDTO = refModeGestionMapper.toDto(updatedRefModeGestion);

        restRefModeGestionMockMvc.perform(put("/api/ref-mode-gestions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refModeGestionDTO)))
            .andExpect(status().isOk());

        // Validate the RefModeGestion in the database
        List<RefModeGestion> refModeGestionList = refModeGestionRepository.findAll();
        assertThat(refModeGestionList).hasSize(databaseSizeBeforeUpdate);
        RefModeGestion testRefModeGestion = refModeGestionList.get(refModeGestionList.size() - 1);
        assertThat(testRefModeGestion.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testRefModeGestion.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the RefModeGestion in Elasticsearch
        RefModeGestion refModeGestionEs = refModeGestionSearchRepository.findOne(testRefModeGestion.getId());
        assertThat(refModeGestionEs).isEqualToComparingFieldByField(testRefModeGestion);
    }

    @Test
    @Transactional
    public void updateNonExistingRefModeGestion() throws Exception {
        int databaseSizeBeforeUpdate = refModeGestionRepository.findAll().size();

        // Create the RefModeGestion
        RefModeGestionDTO refModeGestionDTO = refModeGestionMapper.toDto(refModeGestion);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRefModeGestionMockMvc.perform(put("/api/ref-mode-gestions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refModeGestionDTO)))
            .andExpect(status().isCreated());

        // Validate the RefModeGestion in the database
        List<RefModeGestion> refModeGestionList = refModeGestionRepository.findAll();
        assertThat(refModeGestionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRefModeGestion() throws Exception {
        // Initialize the database
        refModeGestionRepository.saveAndFlush(refModeGestion);
        refModeGestionSearchRepository.save(refModeGestion);
        int databaseSizeBeforeDelete = refModeGestionRepository.findAll().size();

        // Get the refModeGestion
        restRefModeGestionMockMvc.perform(delete("/api/ref-mode-gestions/{id}", refModeGestion.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean refModeGestionExistsInEs = refModeGestionSearchRepository.exists(refModeGestion.getId());
        assertThat(refModeGestionExistsInEs).isFalse();

        // Validate the database is empty
        List<RefModeGestion> refModeGestionList = refModeGestionRepository.findAll();
        assertThat(refModeGestionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchRefModeGestion() throws Exception {
        // Initialize the database
        refModeGestionRepository.saveAndFlush(refModeGestion);
        refModeGestionSearchRepository.save(refModeGestion);

        // Search the refModeGestion
        restRefModeGestionMockMvc.perform(get("/api/_search/ref-mode-gestions?query=id:" + refModeGestion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(refModeGestion.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RefModeGestion.class);
        RefModeGestion refModeGestion1 = new RefModeGestion();
        refModeGestion1.setId(1L);
        RefModeGestion refModeGestion2 = new RefModeGestion();
        refModeGestion2.setId(refModeGestion1.getId());
        assertThat(refModeGestion1).isEqualTo(refModeGestion2);
        refModeGestion2.setId(2L);
        assertThat(refModeGestion1).isNotEqualTo(refModeGestion2);
        refModeGestion1.setId(null);
        assertThat(refModeGestion1).isNotEqualTo(refModeGestion2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RefModeGestionDTO.class);
        RefModeGestionDTO refModeGestionDTO1 = new RefModeGestionDTO();
        refModeGestionDTO1.setId(1L);
        RefModeGestionDTO refModeGestionDTO2 = new RefModeGestionDTO();
        assertThat(refModeGestionDTO1).isNotEqualTo(refModeGestionDTO2);
        refModeGestionDTO2.setId(refModeGestionDTO1.getId());
        assertThat(refModeGestionDTO1).isEqualTo(refModeGestionDTO2);
        refModeGestionDTO2.setId(2L);
        assertThat(refModeGestionDTO1).isNotEqualTo(refModeGestionDTO2);
        refModeGestionDTO1.setId(null);
        assertThat(refModeGestionDTO1).isNotEqualTo(refModeGestionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(refModeGestionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(refModeGestionMapper.fromId(null)).isNull();
    }
}
