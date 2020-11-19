package com.gaconnecte.auxilium.web.rest;

import com.gaconnecte.auxilium.AuxiliumApp;

import com.gaconnecte.auxilium.domain.DesignationNatureContrat;
import com.gaconnecte.auxilium.repository.DesignationNatureContratRepository;
import com.gaconnecte.auxilium.service.DesignationNatureContratService;
import com.gaconnecte.auxilium.repository.search.DesignationNatureContratSearchRepository;
import com.gaconnecte.auxilium.service.dto.DesignationNatureContratDTO;
import com.gaconnecte.auxilium.service.mapper.DesignationNatureContratMapper;
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
 * Test class for the DesignationNatureContratResource REST controller.
 *
 * @see DesignationNatureContratResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuxiliumApp.class)
public class DesignationNatureContratResourceIntTest {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    @Autowired
    private DesignationNatureContratRepository designationNatureContratRepository;

    @Autowired
    private DesignationNatureContratMapper designationNatureContratMapper;

    @Autowired
    private DesignationNatureContratService designationNatureContratService;

    @Autowired
    private DesignationNatureContratSearchRepository designationNatureContratSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDesignationNatureContratMockMvc;

    private DesignationNatureContrat designationNatureContrat;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
       // DesignationNatureContratResource designationNatureContratResource = new DesignationNatureContratResource(designationNatureContratService);
        this.restDesignationNatureContratMockMvc = MockMvcBuilders.standaloneSetup(null)
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
    public static DesignationNatureContrat createEntity(EntityManager em) {
        DesignationNatureContrat designationNatureContrat = new DesignationNatureContrat()
            .libelle(DEFAULT_LIBELLE);
        return designationNatureContrat;
    }

    @Before
    public void initTest() {
        designationNatureContratSearchRepository.deleteAll();
        designationNatureContrat = createEntity(em);
    }

    @Test
    @Transactional
    public void createDesignationNatureContrat() throws Exception {
        int databaseSizeBeforeCreate = designationNatureContratRepository.findAll().size();

        // Create the DesignationNatureContrat
        DesignationNatureContratDTO designationNatureContratDTO = designationNatureContratMapper.toDto(designationNatureContrat);
        restDesignationNatureContratMockMvc.perform(post("/api/designation-nature-contrats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(designationNatureContratDTO)))
            .andExpect(status().isCreated());

        // Validate the DesignationNatureContrat in the database
        List<DesignationNatureContrat> designationNatureContratList = designationNatureContratRepository.findAll();
        assertThat(designationNatureContratList).hasSize(databaseSizeBeforeCreate + 1);
        DesignationNatureContrat testDesignationNatureContrat = designationNatureContratList.get(designationNatureContratList.size() - 1);
        assertThat(testDesignationNatureContrat.getLibelle()).isEqualTo(DEFAULT_LIBELLE);

        // Validate the DesignationNatureContrat in Elasticsearch
        DesignationNatureContrat designationNatureContratEs = designationNatureContratSearchRepository.findOne(testDesignationNatureContrat.getId());
        assertThat(designationNatureContratEs).isEqualToComparingFieldByField(testDesignationNatureContrat);
    }

    @Test
    @Transactional
    public void createDesignationNatureContratWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = designationNatureContratRepository.findAll().size();

        // Create the DesignationNatureContrat with an existing ID
        designationNatureContrat.setId(1L);
        DesignationNatureContratDTO designationNatureContratDTO = designationNatureContratMapper.toDto(designationNatureContrat);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDesignationNatureContratMockMvc.perform(post("/api/designation-nature-contrats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(designationNatureContratDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<DesignationNatureContrat> designationNatureContratList = designationNatureContratRepository.findAll();
        assertThat(designationNatureContratList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = designationNatureContratRepository.findAll().size();
        // set the field null
        designationNatureContrat.setLibelle(null);

        // Create the DesignationNatureContrat, which fails.
        DesignationNatureContratDTO designationNatureContratDTO = designationNatureContratMapper.toDto(designationNatureContrat);

        restDesignationNatureContratMockMvc.perform(post("/api/designation-nature-contrats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(designationNatureContratDTO)))
            .andExpect(status().isBadRequest());

        List<DesignationNatureContrat> designationNatureContratList = designationNatureContratRepository.findAll();
        assertThat(designationNatureContratList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDesignationNatureContrats() throws Exception {
        // Initialize the database
        designationNatureContratRepository.saveAndFlush(designationNatureContrat);

        // Get all the designationNatureContratList
        restDesignationNatureContratMockMvc.perform(get("/api/designation-nature-contrats?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(designationNatureContrat.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())));
    }

    @Test
    @Transactional
    public void getDesignationNatureContrat() throws Exception {
        // Initialize the database
        designationNatureContratRepository.saveAndFlush(designationNatureContrat);

        // Get the designationNatureContrat
        restDesignationNatureContratMockMvc.perform(get("/api/designation-nature-contrats/{id}", designationNatureContrat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(designationNatureContrat.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDesignationNatureContrat() throws Exception {
        // Get the designationNatureContrat
        restDesignationNatureContratMockMvc.perform(get("/api/designation-nature-contrats/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDesignationNatureContrat() throws Exception {
        // Initialize the database
        designationNatureContratRepository.saveAndFlush(designationNatureContrat);
        designationNatureContratSearchRepository.save(designationNatureContrat);
        int databaseSizeBeforeUpdate = designationNatureContratRepository.findAll().size();

        // Update the designationNatureContrat
        DesignationNatureContrat updatedDesignationNatureContrat = designationNatureContratRepository.findOne(designationNatureContrat.getId());
        updatedDesignationNatureContrat
            .libelle(UPDATED_LIBELLE);
        DesignationNatureContratDTO designationNatureContratDTO = designationNatureContratMapper.toDto(updatedDesignationNatureContrat);

        restDesignationNatureContratMockMvc.perform(put("/api/designation-nature-contrats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(designationNatureContratDTO)))
            .andExpect(status().isOk());

        // Validate the DesignationNatureContrat in the database
        List<DesignationNatureContrat> designationNatureContratList = designationNatureContratRepository.findAll();
        assertThat(designationNatureContratList).hasSize(databaseSizeBeforeUpdate);
        DesignationNatureContrat testDesignationNatureContrat = designationNatureContratList.get(designationNatureContratList.size() - 1);
        assertThat(testDesignationNatureContrat.getLibelle()).isEqualTo(UPDATED_LIBELLE);

        // Validate the DesignationNatureContrat in Elasticsearch
        DesignationNatureContrat designationNatureContratEs = designationNatureContratSearchRepository.findOne(testDesignationNatureContrat.getId());
        assertThat(designationNatureContratEs).isEqualToComparingFieldByField(testDesignationNatureContrat);
    }

    @Test
    @Transactional
    public void updateNonExistingDesignationNatureContrat() throws Exception {
        int databaseSizeBeforeUpdate = designationNatureContratRepository.findAll().size();

        // Create the DesignationNatureContrat
        DesignationNatureContratDTO designationNatureContratDTO = designationNatureContratMapper.toDto(designationNatureContrat);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDesignationNatureContratMockMvc.perform(put("/api/designation-nature-contrats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(designationNatureContratDTO)))
            .andExpect(status().isCreated());

        // Validate the DesignationNatureContrat in the database
        List<DesignationNatureContrat> designationNatureContratList = designationNatureContratRepository.findAll();
        assertThat(designationNatureContratList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDesignationNatureContrat() throws Exception {
        // Initialize the database
        designationNatureContratRepository.saveAndFlush(designationNatureContrat);
        designationNatureContratSearchRepository.save(designationNatureContrat);
        int databaseSizeBeforeDelete = designationNatureContratRepository.findAll().size();

        // Get the designationNatureContrat
        restDesignationNatureContratMockMvc.perform(delete("/api/designation-nature-contrats/{id}", designationNatureContrat.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean designationNatureContratExistsInEs = designationNatureContratSearchRepository.exists(designationNatureContrat.getId());
        assertThat(designationNatureContratExistsInEs).isFalse();

        // Validate the database is empty
        List<DesignationNatureContrat> designationNatureContratList = designationNatureContratRepository.findAll();
        assertThat(designationNatureContratList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchDesignationNatureContrat() throws Exception {
        // Initialize the database
        designationNatureContratRepository.saveAndFlush(designationNatureContrat);
        designationNatureContratSearchRepository.save(designationNatureContrat);

        // Search the designationNatureContrat
        restDesignationNatureContratMockMvc.perform(get("/api/_search/designation-nature-contrats?query=id:" + designationNatureContrat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(designationNatureContrat.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DesignationNatureContrat.class);
        DesignationNatureContrat designationNatureContrat1 = new DesignationNatureContrat();
        designationNatureContrat1.setId(1L);
        DesignationNatureContrat designationNatureContrat2 = new DesignationNatureContrat();
        designationNatureContrat2.setId(designationNatureContrat1.getId());
        assertThat(designationNatureContrat1).isEqualTo(designationNatureContrat2);
        designationNatureContrat2.setId(2L);
        assertThat(designationNatureContrat1).isNotEqualTo(designationNatureContrat2);
        designationNatureContrat1.setId(null);
        assertThat(designationNatureContrat1).isNotEqualTo(designationNatureContrat2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DesignationNatureContratDTO.class);
        DesignationNatureContratDTO designationNatureContratDTO1 = new DesignationNatureContratDTO();
        designationNatureContratDTO1.setId(1L);
        DesignationNatureContratDTO designationNatureContratDTO2 = new DesignationNatureContratDTO();
        assertThat(designationNatureContratDTO1).isNotEqualTo(designationNatureContratDTO2);
        designationNatureContratDTO2.setId(designationNatureContratDTO1.getId());
        assertThat(designationNatureContratDTO1).isEqualTo(designationNatureContratDTO2);
        designationNatureContratDTO2.setId(2L);
        assertThat(designationNatureContratDTO1).isNotEqualTo(designationNatureContratDTO2);
        designationNatureContratDTO1.setId(null);
        assertThat(designationNatureContratDTO1).isNotEqualTo(designationNatureContratDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(designationNatureContratMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(designationNatureContratMapper.fromId(null)).isNull();
    }
}
