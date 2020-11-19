package com.gaconnecte.auxilium.web.rest;

import com.gaconnecte.auxilium.AuxiliumApp;

import com.gaconnecte.auxilium.domain.Concessionnaire;
import com.gaconnecte.auxilium.repository.ConcessionnaireRepository;
import com.gaconnecte.auxilium.service.ConcessionnaireService;
import com.gaconnecte.auxilium.repository.search.ConcessionnaireSearchRepository;
import com.gaconnecte.auxilium.service.dto.ConcessionnaireDTO;
import com.gaconnecte.auxilium.service.mapper.ConcessionnaireMapper;
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
 * Test class for the ConcessionnaireResource REST controller.
 *
 * @see ConcessionnaireResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuxiliumApp.class)
public class ConcessionnaireResourceIntTest {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    @Autowired
    private ConcessionnaireRepository concessionnaireRepository;

    @Autowired
    private ConcessionnaireMapper concessionnaireMapper;

    @Autowired
    private ConcessionnaireService concessionnaireService;

    @Autowired
    private ConcessionnaireSearchRepository concessionnaireSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restConcessionnaireMockMvc;

    private Concessionnaire concessionnaire;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ConcessionnaireResource concessionnaireResource = new ConcessionnaireResource(concessionnaireService);
        this.restConcessionnaireMockMvc = MockMvcBuilders.standaloneSetup(concessionnaireResource)
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
    public static Concessionnaire createEntity(EntityManager em) {
        Concessionnaire concessionnaire = new Concessionnaire()
            .libelle(DEFAULT_LIBELLE);
        // Add required entity
      //  RefMarque marque = RefMarqueResourceIntTest.createEntity(em);
        em.persist(null);
        em.flush();
        concessionnaire.getMarques().add(null);
        return concessionnaire;
    }

    @Before
    public void initTest() {
        concessionnaireSearchRepository.deleteAll();
        concessionnaire = createEntity(em);
    }

    @Test
    @Transactional
    public void createConcessionnaire() throws Exception {
        int databaseSizeBeforeCreate = concessionnaireRepository.findAll().size();

        // Create the Concessionnaire
        ConcessionnaireDTO concessionnaireDTO = concessionnaireMapper.toDto(concessionnaire);
        restConcessionnaireMockMvc.perform(post("/api/concessionnaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(concessionnaireDTO)))
            .andExpect(status().isCreated());

        // Validate the Concessionnaire in the database
        List<Concessionnaire> concessionnaireList = concessionnaireRepository.findAll();
        assertThat(concessionnaireList).hasSize(databaseSizeBeforeCreate + 1);
        Concessionnaire testConcessionnaire = concessionnaireList.get(concessionnaireList.size() - 1);
        assertThat(testConcessionnaire.getLibelle()).isEqualTo(DEFAULT_LIBELLE);

        // Validate the Concessionnaire in Elasticsearch
        Concessionnaire concessionnaireEs = concessionnaireSearchRepository.findOne(testConcessionnaire.getId());
        assertThat(concessionnaireEs).isEqualToComparingFieldByField(testConcessionnaire);
    }

    @Test
    @Transactional
    public void createConcessionnaireWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = concessionnaireRepository.findAll().size();

        // Create the Concessionnaire with an existing ID
        concessionnaire.setId(1L);
        ConcessionnaireDTO concessionnaireDTO = concessionnaireMapper.toDto(concessionnaire);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConcessionnaireMockMvc.perform(post("/api/concessionnaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(concessionnaireDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Concessionnaire> concessionnaireList = concessionnaireRepository.findAll();
        assertThat(concessionnaireList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = concessionnaireRepository.findAll().size();
        // set the field null
        concessionnaire.setLibelle(null);

        // Create the Concessionnaire, which fails.
        ConcessionnaireDTO concessionnaireDTO = concessionnaireMapper.toDto(concessionnaire);

        restConcessionnaireMockMvc.perform(post("/api/concessionnaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(concessionnaireDTO)))
            .andExpect(status().isBadRequest());

        List<Concessionnaire> concessionnaireList = concessionnaireRepository.findAll();
        assertThat(concessionnaireList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllConcessionnaires() throws Exception {
        // Initialize the database
        concessionnaireRepository.saveAndFlush(concessionnaire);

        // Get all the concessionnaireList
        restConcessionnaireMockMvc.perform(get("/api/concessionnaires?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(concessionnaire.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())));
    }

    @Test
    @Transactional
    public void getConcessionnaire() throws Exception {
        // Initialize the database
        concessionnaireRepository.saveAndFlush(concessionnaire);

        // Get the concessionnaire
        restConcessionnaireMockMvc.perform(get("/api/concessionnaires/{id}", concessionnaire.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(concessionnaire.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingConcessionnaire() throws Exception {
        // Get the concessionnaire
        restConcessionnaireMockMvc.perform(get("/api/concessionnaires/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConcessionnaire() throws Exception {
        // Initialize the database
        concessionnaireRepository.saveAndFlush(concessionnaire);
        concessionnaireSearchRepository.save(concessionnaire);
        int databaseSizeBeforeUpdate = concessionnaireRepository.findAll().size();

        // Update the concessionnaire
        Concessionnaire updatedConcessionnaire = concessionnaireRepository.findOne(concessionnaire.getId());
        updatedConcessionnaire
            .libelle(UPDATED_LIBELLE);
        ConcessionnaireDTO concessionnaireDTO = concessionnaireMapper.toDto(updatedConcessionnaire);

        restConcessionnaireMockMvc.perform(put("/api/concessionnaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(concessionnaireDTO)))
            .andExpect(status().isOk());

        // Validate the Concessionnaire in the database
        List<Concessionnaire> concessionnaireList = concessionnaireRepository.findAll();
        assertThat(concessionnaireList).hasSize(databaseSizeBeforeUpdate);
        Concessionnaire testConcessionnaire = concessionnaireList.get(concessionnaireList.size() - 1);
        assertThat(testConcessionnaire.getLibelle()).isEqualTo(UPDATED_LIBELLE);

        // Validate the Concessionnaire in Elasticsearch
        Concessionnaire concessionnaireEs = concessionnaireSearchRepository.findOne(testConcessionnaire.getId());
        assertThat(concessionnaireEs).isEqualToComparingFieldByField(testConcessionnaire);
    }

    @Test
    @Transactional
    public void updateNonExistingConcessionnaire() throws Exception {
        int databaseSizeBeforeUpdate = concessionnaireRepository.findAll().size();

        // Create the Concessionnaire
        ConcessionnaireDTO concessionnaireDTO = concessionnaireMapper.toDto(concessionnaire);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restConcessionnaireMockMvc.perform(put("/api/concessionnaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(concessionnaireDTO)))
            .andExpect(status().isCreated());

        // Validate the Concessionnaire in the database
        List<Concessionnaire> concessionnaireList = concessionnaireRepository.findAll();
        assertThat(concessionnaireList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteConcessionnaire() throws Exception {
        // Initialize the database
        concessionnaireRepository.saveAndFlush(concessionnaire);
        concessionnaireSearchRepository.save(concessionnaire);
        int databaseSizeBeforeDelete = concessionnaireRepository.findAll().size();

        // Get the concessionnaire
        restConcessionnaireMockMvc.perform(delete("/api/concessionnaires/{id}", concessionnaire.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean concessionnaireExistsInEs = concessionnaireSearchRepository.exists(concessionnaire.getId());
        assertThat(concessionnaireExistsInEs).isFalse();

        // Validate the database is empty
        List<Concessionnaire> concessionnaireList = concessionnaireRepository.findAll();
        assertThat(concessionnaireList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchConcessionnaire() throws Exception {
        // Initialize the database
        concessionnaireRepository.saveAndFlush(concessionnaire);
        concessionnaireSearchRepository.save(concessionnaire);

        // Search the concessionnaire
        restConcessionnaireMockMvc.perform(get("/api/_search/concessionnaires?query=id:" + concessionnaire.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(concessionnaire.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Concessionnaire.class);
        Concessionnaire concessionnaire1 = new Concessionnaire();
        concessionnaire1.setId(1L);
        Concessionnaire concessionnaire2 = new Concessionnaire();
        concessionnaire2.setId(concessionnaire1.getId());
        assertThat(concessionnaire1).isEqualTo(concessionnaire2);
        concessionnaire2.setId(2L);
        assertThat(concessionnaire1).isNotEqualTo(concessionnaire2);
        concessionnaire1.setId(null);
        assertThat(concessionnaire1).isNotEqualTo(concessionnaire2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConcessionnaireDTO.class);
        ConcessionnaireDTO concessionnaireDTO1 = new ConcessionnaireDTO();
        concessionnaireDTO1.setId(1L);
        ConcessionnaireDTO concessionnaireDTO2 = new ConcessionnaireDTO();
        assertThat(concessionnaireDTO1).isNotEqualTo(concessionnaireDTO2);
        concessionnaireDTO2.setId(concessionnaireDTO1.getId());
        assertThat(concessionnaireDTO1).isEqualTo(concessionnaireDTO2);
        concessionnaireDTO2.setId(2L);
        assertThat(concessionnaireDTO1).isNotEqualTo(concessionnaireDTO2);
        concessionnaireDTO1.setId(null);
        assertThat(concessionnaireDTO1).isNotEqualTo(concessionnaireDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(concessionnaireMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(concessionnaireMapper.fromId(null)).isNull();
    }
}
