package com.gaconnecte.auxilium.web.rest;

import com.gaconnecte.auxilium.AuxiliumApp;

import com.gaconnecte.auxilium.domain.RefPositionGa;
import com.gaconnecte.auxilium.repository.RefPositionGaRepository;
import com.gaconnecte.auxilium.service.RefPositionGaService;
import com.gaconnecte.auxilium.repository.search.RefPositionGaSearchRepository;
import com.gaconnecte.auxilium.service.dto.RefPositionGaDTO;
import com.gaconnecte.auxilium.service.mapper.RefPositionGaMapper;
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
 * Test class for the RefPositionGaResource REST controller.
 *
 * @see RefPositionGaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuxiliumApp.class)
public class RefPositionGaResourceIntTest {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    @Autowired
    private RefPositionGaRepository refPositionGaRepository;

    @Autowired
    private RefPositionGaMapper refPositionGaMapper;

    @Autowired
    private RefPositionGaService refPositionGaService;

    @Autowired
    private RefPositionGaSearchRepository refPositionGaSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRefPositionGaMockMvc;

    private RefPositionGa refPositionGa;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RefPositionGaResource refPositionGaResource = new RefPositionGaResource(refPositionGaService);
        this.restRefPositionGaMockMvc = MockMvcBuilders.standaloneSetup(refPositionGaResource)
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
    public static RefPositionGa createEntity(EntityManager em) {
        RefPositionGa refPositionGa = new RefPositionGa()
            .libelle(DEFAULT_LIBELLE);
        return refPositionGa;
    }

    @Before
    public void initTest() {
        refPositionGaSearchRepository.deleteAll();
        refPositionGa = createEntity(em);
    }

    @Test
    @Transactional
    public void createRefPositionGa() throws Exception {
        int databaseSizeBeforeCreate = refPositionGaRepository.findAll().size();

        // Create the RefPositionGa
        RefPositionGaDTO refPositionGaDTO = refPositionGaMapper.toDto(refPositionGa);
        restRefPositionGaMockMvc.perform(post("/api/ref-position-gas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refPositionGaDTO)))
            .andExpect(status().isCreated());

        // Validate the RefPositionGa in the database
        List<RefPositionGa> refPositionGaList = refPositionGaRepository.findAll();
        assertThat(refPositionGaList).hasSize(databaseSizeBeforeCreate + 1);
        RefPositionGa testRefPositionGa = refPositionGaList.get(refPositionGaList.size() - 1);
        assertThat(testRefPositionGa.getLibelle()).isEqualTo(DEFAULT_LIBELLE);

        // Validate the RefPositionGa in Elasticsearch
        RefPositionGa refPositionGaEs = refPositionGaSearchRepository.findOne(testRefPositionGa.getId());
        assertThat(refPositionGaEs).isEqualToComparingFieldByField(testRefPositionGa);
    }

    @Test
    @Transactional
    public void createRefPositionGaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = refPositionGaRepository.findAll().size();

        // Create the RefPositionGa with an existing ID
        refPositionGa.setId(1L);
        RefPositionGaDTO refPositionGaDTO = refPositionGaMapper.toDto(refPositionGa);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRefPositionGaMockMvc.perform(post("/api/ref-position-gas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refPositionGaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<RefPositionGa> refPositionGaList = refPositionGaRepository.findAll();
        assertThat(refPositionGaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = refPositionGaRepository.findAll().size();
        // set the field null
        refPositionGa.setLibelle(null);

        // Create the RefPositionGa, which fails.
        RefPositionGaDTO refPositionGaDTO = refPositionGaMapper.toDto(refPositionGa);

        restRefPositionGaMockMvc.perform(post("/api/ref-position-gas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refPositionGaDTO)))
            .andExpect(status().isBadRequest());

        List<RefPositionGa> refPositionGaList = refPositionGaRepository.findAll();
        assertThat(refPositionGaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRefPositionGas() throws Exception {
        // Initialize the database
        refPositionGaRepository.saveAndFlush(refPositionGa);

        // Get all the refPositionGaList
        restRefPositionGaMockMvc.perform(get("/api/ref-position-gas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(refPositionGa.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())));
    }

    @Test
    @Transactional
    public void getRefPositionGa() throws Exception {
        // Initialize the database
        refPositionGaRepository.saveAndFlush(refPositionGa);

        // Get the refPositionGa
        restRefPositionGaMockMvc.perform(get("/api/ref-position-gas/{id}", refPositionGa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(refPositionGa.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRefPositionGa() throws Exception {
        // Get the refPositionGa
        restRefPositionGaMockMvc.perform(get("/api/ref-position-gas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRefPositionGa() throws Exception {
        // Initialize the database
        refPositionGaRepository.saveAndFlush(refPositionGa);
        refPositionGaSearchRepository.save(refPositionGa);
        int databaseSizeBeforeUpdate = refPositionGaRepository.findAll().size();

        // Update the refPositionGa
        RefPositionGa updatedRefPositionGa = refPositionGaRepository.findOne(refPositionGa.getId());
        updatedRefPositionGa
            .libelle(UPDATED_LIBELLE);
        RefPositionGaDTO refPositionGaDTO = refPositionGaMapper.toDto(updatedRefPositionGa);

        restRefPositionGaMockMvc.perform(put("/api/ref-position-gas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refPositionGaDTO)))
            .andExpect(status().isOk());

        // Validate the RefPositionGa in the database
        List<RefPositionGa> refPositionGaList = refPositionGaRepository.findAll();
        assertThat(refPositionGaList).hasSize(databaseSizeBeforeUpdate);
        RefPositionGa testRefPositionGa = refPositionGaList.get(refPositionGaList.size() - 1);
        assertThat(testRefPositionGa.getLibelle()).isEqualTo(UPDATED_LIBELLE);

        // Validate the RefPositionGa in Elasticsearch
        RefPositionGa refPositionGaEs = refPositionGaSearchRepository.findOne(testRefPositionGa.getId());
        assertThat(refPositionGaEs).isEqualToComparingFieldByField(testRefPositionGa);
    }

    @Test
    @Transactional
    public void updateNonExistingRefPositionGa() throws Exception {
        int databaseSizeBeforeUpdate = refPositionGaRepository.findAll().size();

        // Create the RefPositionGa
        RefPositionGaDTO refPositionGaDTO = refPositionGaMapper.toDto(refPositionGa);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRefPositionGaMockMvc.perform(put("/api/ref-position-gas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refPositionGaDTO)))
            .andExpect(status().isCreated());

        // Validate the RefPositionGa in the database
        List<RefPositionGa> refPositionGaList = refPositionGaRepository.findAll();
        assertThat(refPositionGaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRefPositionGa() throws Exception {
        // Initialize the database
        refPositionGaRepository.saveAndFlush(refPositionGa);
        refPositionGaSearchRepository.save(refPositionGa);
        int databaseSizeBeforeDelete = refPositionGaRepository.findAll().size();

        // Get the refPositionGa
        restRefPositionGaMockMvc.perform(delete("/api/ref-position-gas/{id}", refPositionGa.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean refPositionGaExistsInEs = refPositionGaSearchRepository.exists(refPositionGa.getId());
        assertThat(refPositionGaExistsInEs).isFalse();

        // Validate the database is empty
        List<RefPositionGa> refPositionGaList = refPositionGaRepository.findAll();
        assertThat(refPositionGaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchRefPositionGa() throws Exception {
        // Initialize the database
        refPositionGaRepository.saveAndFlush(refPositionGa);
        refPositionGaSearchRepository.save(refPositionGa);

        // Search the refPositionGa
        restRefPositionGaMockMvc.perform(get("/api/_search/ref-position-gas?query=id:" + refPositionGa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(refPositionGa.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RefPositionGa.class);
        RefPositionGa refPositionGa1 = new RefPositionGa();
        refPositionGa1.setId(1L);
        RefPositionGa refPositionGa2 = new RefPositionGa();
        refPositionGa2.setId(refPositionGa1.getId());
        assertThat(refPositionGa1).isEqualTo(refPositionGa2);
        refPositionGa2.setId(2L);
        assertThat(refPositionGa1).isNotEqualTo(refPositionGa2);
        refPositionGa1.setId(null);
        assertThat(refPositionGa1).isNotEqualTo(refPositionGa2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RefPositionGaDTO.class);
        RefPositionGaDTO refPositionGaDTO1 = new RefPositionGaDTO();
        refPositionGaDTO1.setId(1L);
        RefPositionGaDTO refPositionGaDTO2 = new RefPositionGaDTO();
        assertThat(refPositionGaDTO1).isNotEqualTo(refPositionGaDTO2);
        refPositionGaDTO2.setId(refPositionGaDTO1.getId());
        assertThat(refPositionGaDTO1).isEqualTo(refPositionGaDTO2);
        refPositionGaDTO2.setId(2L);
        assertThat(refPositionGaDTO1).isNotEqualTo(refPositionGaDTO2);
        refPositionGaDTO1.setId(null);
        assertThat(refPositionGaDTO1).isNotEqualTo(refPositionGaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(refPositionGaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(refPositionGaMapper.fromId(null)).isNull();
    }
}
