package com.gaconnecte.auxilium.web.rest;

import com.gaconnecte.auxilium.AuxiliumApp;

import com.gaconnecte.auxilium.domain.Assure;
import com.gaconnecte.auxilium.repository.AssureRepository;
import com.gaconnecte.auxilium.service.AssureService;
import com.gaconnecte.auxilium.repository.search.AssureSearchRepository;
import com.gaconnecte.auxilium.service.dto.AssureDTO;
import com.gaconnecte.auxilium.service.mapper.AssureMapper;
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
 * Test class for the AssureResource REST controller.
 *
 * @see AssureResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuxiliumApp.class)
public class AssureResourceIntTest {

    @Autowired
    private AssureRepository assureRepository;

    @Autowired
    private AssureMapper assureMapper;

    @Autowired
    private AssureService assureService;

    @Autowired
    private AssureSearchRepository assureSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAssureMockMvc;

    private Assure assure;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AssureResource assureResource = new AssureResource(assureService);
        this.restAssureMockMvc = MockMvcBuilders.standaloneSetup(assureResource)
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
    public static Assure createEntity(EntityManager em) {
        Assure assure = new Assure();
        return assure;
    }

    @Before
    public void initTest() {
        assureSearchRepository.deleteAll();
        assure = createEntity(em);
    }

    @Test
    @Transactional
    public void createAssure() throws Exception {
        int databaseSizeBeforeCreate = assureRepository.findAll().size();

        // Create the Assure
        AssureDTO assureDTO = assureMapper.toDto(assure);
        restAssureMockMvc.perform(post("/api/assures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assureDTO)))
            .andExpect(status().isCreated());

        // Validate the Assure in the database
        List<Assure> assureList = assureRepository.findAll();
        assertThat(assureList).hasSize(databaseSizeBeforeCreate + 1);
        Assure testAssure = assureList.get(assureList.size() - 1);

        // Validate the Assure in Elasticsearch
        Assure assureEs = assureSearchRepository.findOne(testAssure.getId());
        assertThat(assureEs).isEqualToComparingFieldByField(testAssure);
    }

    @Test
    @Transactional
    public void createAssureWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = assureRepository.findAll().size();

        // Create the Assure with an existing ID
        assure.setId(1L);
        AssureDTO assureDTO = assureMapper.toDto(assure);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAssureMockMvc.perform(post("/api/assures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assureDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Assure> assureList = assureRepository.findAll();
        assertThat(assureList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAssures() throws Exception {
        // Initialize the database
        assureRepository.saveAndFlush(assure);

        // Get all the assureList
        restAssureMockMvc.perform(get("/api/assures?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(assure.getId().intValue())));
    }

    @Test
    @Transactional
    public void getAssure() throws Exception {
        // Initialize the database
        assureRepository.saveAndFlush(assure);

        // Get the assure
        restAssureMockMvc.perform(get("/api/assures/{id}", assure.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(assure.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAssure() throws Exception {
        // Get the assure
        restAssureMockMvc.perform(get("/api/assures/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAssure() throws Exception {
        // Initialize the database
        assureRepository.saveAndFlush(assure);
        assureSearchRepository.save(assure);
        int databaseSizeBeforeUpdate = assureRepository.findAll().size();

        // Update the assure
        Assure updatedAssure = assureRepository.findOne(assure.getId());
        AssureDTO assureDTO = assureMapper.toDto(updatedAssure);

        restAssureMockMvc.perform(put("/api/assures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assureDTO)))
            .andExpect(status().isOk());

        // Validate the Assure in the database
        List<Assure> assureList = assureRepository.findAll();
        assertThat(assureList).hasSize(databaseSizeBeforeUpdate);
        Assure testAssure = assureList.get(assureList.size() - 1);

        // Validate the Assure in Elasticsearch
        Assure assureEs = assureSearchRepository.findOne(testAssure.getId());
        assertThat(assureEs).isEqualToComparingFieldByField(testAssure);
    }

    @Test
    @Transactional
    public void updateNonExistingAssure() throws Exception {
        int databaseSizeBeforeUpdate = assureRepository.findAll().size();

        // Create the Assure
        AssureDTO assureDTO = assureMapper.toDto(assure);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAssureMockMvc.perform(put("/api/assures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assureDTO)))
            .andExpect(status().isCreated());

        // Validate the Assure in the database
        List<Assure> assureList = assureRepository.findAll();
        assertThat(assureList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAssure() throws Exception {
        // Initialize the database
        assureRepository.saveAndFlush(assure);
        assureSearchRepository.save(assure);
        int databaseSizeBeforeDelete = assureRepository.findAll().size();

        // Get the assure
        restAssureMockMvc.perform(delete("/api/assures/{id}", assure.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean assureExistsInEs = assureSearchRepository.exists(assure.getId());
        assertThat(assureExistsInEs).isFalse();

        // Validate the database is empty
        List<Assure> assureList = assureRepository.findAll();
        assertThat(assureList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchAssure() throws Exception {
        // Initialize the database
        assureRepository.saveAndFlush(assure);
        assureSearchRepository.save(assure);

        // Search the assure
        restAssureMockMvc.perform(get("/api/_search/assures?query=id:" + assure.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(assure.getId().intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Assure.class);
        Assure assure1 = new Assure();
        assure1.setId(1L);
        Assure assure2 = new Assure();
        assure2.setId(assure1.getId());
        assertThat(assure1).isEqualTo(assure2);
        assure2.setId(2L);
        assertThat(assure1).isNotEqualTo(assure2);
        assure1.setId(null);
        assertThat(assure1).isNotEqualTo(assure2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AssureDTO.class);
        AssureDTO assureDTO1 = new AssureDTO();
        assureDTO1.setId(1L);
        AssureDTO assureDTO2 = new AssureDTO();
        assertThat(assureDTO1).isNotEqualTo(assureDTO2);
        assureDTO2.setId(assureDTO1.getId());
        assertThat(assureDTO1).isEqualTo(assureDTO2);
        assureDTO2.setId(2L);
        assertThat(assureDTO1).isNotEqualTo(assureDTO2);
        assureDTO1.setId(null);
        assertThat(assureDTO1).isNotEqualTo(assureDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(assureMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(assureMapper.fromId(null)).isNull();
    }
}
