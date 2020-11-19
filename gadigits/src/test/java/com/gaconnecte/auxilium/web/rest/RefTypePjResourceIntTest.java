package com.gaconnecte.auxilium.web.rest;

import com.gaconnecte.auxilium.AuxiliumApp;

import com.gaconnecte.auxilium.domain.RefTypePj;
import com.gaconnecte.auxilium.repository.RefTypePjRepository;
import com.gaconnecte.auxilium.service.RefTypePjService;
import com.gaconnecte.auxilium.repository.search.RefTypePjSearchRepository;
import com.gaconnecte.auxilium.service.dto.RefTypePjDTO;
import com.gaconnecte.auxilium.service.mapper.RefTypePjMapper;
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
 * Test class for the RefTypePjResource REST controller.
 *
 * @see RefTypePjResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuxiliumApp.class)
public class RefTypePjResourceIntTest {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    @Autowired
    private RefTypePjRepository refTypePjRepository;

    @Autowired
    private RefTypePjMapper refTypePjMapper;

    @Autowired
    private RefTypePjService refTypePjService;

    @Autowired
    private RefTypePjSearchRepository refTypePjSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRefTypePjMockMvc;

    private RefTypePj refTypePj;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RefTypePjResource refTypePjResource = new RefTypePjResource(refTypePjService);
        this.restRefTypePjMockMvc = MockMvcBuilders.standaloneSetup(refTypePjResource)
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
    public static RefTypePj createEntity(EntityManager em) {
        RefTypePj refTypePj = new RefTypePj()
            .libelle(DEFAULT_LIBELLE);
        return refTypePj;
    }

    @Before
    public void initTest() {
        refTypePjSearchRepository.deleteAll();
        refTypePj = createEntity(em);
    }

    @Test
    @Transactional
    public void createRefTypePj() throws Exception {
        int databaseSizeBeforeCreate = refTypePjRepository.findAll().size();

        // Create the RefTypePj
        RefTypePjDTO refTypePjDTO = refTypePjMapper.toDto(refTypePj);
        restRefTypePjMockMvc.perform(post("/api/ref-type-pjs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refTypePjDTO)))
            .andExpect(status().isCreated());

        // Validate the RefTypePj in the database
        List<RefTypePj> refTypePjList = refTypePjRepository.findAll();
        assertThat(refTypePjList).hasSize(databaseSizeBeforeCreate + 1);
        RefTypePj testRefTypePj = refTypePjList.get(refTypePjList.size() - 1);
        assertThat(testRefTypePj.getLibelle()).isEqualTo(DEFAULT_LIBELLE);

        // Validate the RefTypePj in Elasticsearch
        RefTypePj refTypePjEs = refTypePjSearchRepository.findOne(testRefTypePj.getId());
        assertThat(refTypePjEs).isEqualToComparingFieldByField(testRefTypePj);
    }

    @Test
    @Transactional
    public void createRefTypePjWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = refTypePjRepository.findAll().size();

        // Create the RefTypePj with an existing ID
        refTypePj.setId(1L);
        RefTypePjDTO refTypePjDTO = refTypePjMapper.toDto(refTypePj);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRefTypePjMockMvc.perform(post("/api/ref-type-pjs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refTypePjDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<RefTypePj> refTypePjList = refTypePjRepository.findAll();
        assertThat(refTypePjList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = refTypePjRepository.findAll().size();
        // set the field null
        refTypePj.setLibelle(null);

        // Create the RefTypePj, which fails.
        RefTypePjDTO refTypePjDTO = refTypePjMapper.toDto(refTypePj);

        restRefTypePjMockMvc.perform(post("/api/ref-type-pjs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refTypePjDTO)))
            .andExpect(status().isBadRequest());

        List<RefTypePj> refTypePjList = refTypePjRepository.findAll();
        assertThat(refTypePjList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRefTypePjs() throws Exception {
        // Initialize the database
        refTypePjRepository.saveAndFlush(refTypePj);

        // Get all the refTypePjList
        restRefTypePjMockMvc.perform(get("/api/ref-type-pjs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(refTypePj.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())));
    }

    @Test
    @Transactional
    public void getRefTypePj() throws Exception {
        // Initialize the database
        refTypePjRepository.saveAndFlush(refTypePj);

        // Get the refTypePj
        restRefTypePjMockMvc.perform(get("/api/ref-type-pjs/{id}", refTypePj.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(refTypePj.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRefTypePj() throws Exception {
        // Get the refTypePj
        restRefTypePjMockMvc.perform(get("/api/ref-type-pjs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRefTypePj() throws Exception {
        // Initialize the database
        refTypePjRepository.saveAndFlush(refTypePj);
        refTypePjSearchRepository.save(refTypePj);
        int databaseSizeBeforeUpdate = refTypePjRepository.findAll().size();

        // Update the refTypePj
        RefTypePj updatedRefTypePj = refTypePjRepository.findOne(refTypePj.getId());
        updatedRefTypePj
            .libelle(UPDATED_LIBELLE);
        RefTypePjDTO refTypePjDTO = refTypePjMapper.toDto(updatedRefTypePj);

        restRefTypePjMockMvc.perform(put("/api/ref-type-pjs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refTypePjDTO)))
            .andExpect(status().isOk());

        // Validate the RefTypePj in the database
        List<RefTypePj> refTypePjList = refTypePjRepository.findAll();
        assertThat(refTypePjList).hasSize(databaseSizeBeforeUpdate);
        RefTypePj testRefTypePj = refTypePjList.get(refTypePjList.size() - 1);
        assertThat(testRefTypePj.getLibelle()).isEqualTo(UPDATED_LIBELLE);

        // Validate the RefTypePj in Elasticsearch
        RefTypePj refTypePjEs = refTypePjSearchRepository.findOne(testRefTypePj.getId());
        assertThat(refTypePjEs).isEqualToComparingFieldByField(testRefTypePj);
    }

    @Test
    @Transactional
    public void updateNonExistingRefTypePj() throws Exception {
        int databaseSizeBeforeUpdate = refTypePjRepository.findAll().size();

        // Create the RefTypePj
        RefTypePjDTO refTypePjDTO = refTypePjMapper.toDto(refTypePj);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRefTypePjMockMvc.perform(put("/api/ref-type-pjs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refTypePjDTO)))
            .andExpect(status().isCreated());

        // Validate the RefTypePj in the database
        List<RefTypePj> refTypePjList = refTypePjRepository.findAll();
        assertThat(refTypePjList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRefTypePj() throws Exception {
        // Initialize the database
        refTypePjRepository.saveAndFlush(refTypePj);
        refTypePjSearchRepository.save(refTypePj);
        int databaseSizeBeforeDelete = refTypePjRepository.findAll().size();

        // Get the refTypePj
        restRefTypePjMockMvc.perform(delete("/api/ref-type-pjs/{id}", refTypePj.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean refTypePjExistsInEs = refTypePjSearchRepository.exists(refTypePj.getId());
        assertThat(refTypePjExistsInEs).isFalse();

        // Validate the database is empty
        List<RefTypePj> refTypePjList = refTypePjRepository.findAll();
        assertThat(refTypePjList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchRefTypePj() throws Exception {
        // Initialize the database
        refTypePjRepository.saveAndFlush(refTypePj);
        refTypePjSearchRepository.save(refTypePj);

        // Search the refTypePj
        restRefTypePjMockMvc.perform(get("/api/_search/ref-type-pjs?query=id:" + refTypePj.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(refTypePj.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RefTypePj.class);
        RefTypePj refTypePj1 = new RefTypePj();
        refTypePj1.setId(1L);
        RefTypePj refTypePj2 = new RefTypePj();
        refTypePj2.setId(refTypePj1.getId());
        assertThat(refTypePj1).isEqualTo(refTypePj2);
        refTypePj2.setId(2L);
        assertThat(refTypePj1).isNotEqualTo(refTypePj2);
        refTypePj1.setId(null);
        assertThat(refTypePj1).isNotEqualTo(refTypePj2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RefTypePjDTO.class);
        RefTypePjDTO refTypePjDTO1 = new RefTypePjDTO();
        refTypePjDTO1.setId(1L);
        RefTypePjDTO refTypePjDTO2 = new RefTypePjDTO();
        assertThat(refTypePjDTO1).isNotEqualTo(refTypePjDTO2);
        refTypePjDTO2.setId(refTypePjDTO1.getId());
        assertThat(refTypePjDTO1).isEqualTo(refTypePjDTO2);
        refTypePjDTO2.setId(2L);
        assertThat(refTypePjDTO1).isNotEqualTo(refTypePjDTO2);
        refTypePjDTO1.setId(null);
        assertThat(refTypePjDTO1).isNotEqualTo(refTypePjDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(refTypePjMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(refTypePjMapper.fromId(null)).isNull();
    }
}
