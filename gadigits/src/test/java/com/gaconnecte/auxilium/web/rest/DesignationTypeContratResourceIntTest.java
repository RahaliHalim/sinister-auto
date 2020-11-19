package com.gaconnecte.auxilium.web.rest;

import com.gaconnecte.auxilium.AuxiliumApp;

import com.gaconnecte.auxilium.domain.DesignationTypeContrat;
import com.gaconnecte.auxilium.repository.DesignationTypeContratRepository;
import com.gaconnecte.auxilium.service.DesignationTypeContratService;
import com.gaconnecte.auxilium.repository.search.DesignationTypeContratSearchRepository;
import com.gaconnecte.auxilium.service.dto.DesignationTypeContratDTO;
import com.gaconnecte.auxilium.service.mapper.DesignationTypeContratMapper;
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
 * Test class for the DesignationTypeContratResource REST controller.
 *
 * @see DesignationTypeContratResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuxiliumApp.class)
public class DesignationTypeContratResourceIntTest {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    @Autowired
    private DesignationTypeContratRepository designationTypeContratRepository;

    @Autowired
    private DesignationTypeContratMapper designationTypeContratMapper;

    @Autowired
    private DesignationTypeContratService designationTypeContratService;

    @Autowired
    private DesignationTypeContratSearchRepository designationTypeContratSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDesignationTypeContratMockMvc;

    private DesignationTypeContrat designationTypeContrat;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DesignationTypeContratResource designationTypeContratResource = new DesignationTypeContratResource(designationTypeContratService);
        this.restDesignationTypeContratMockMvc = MockMvcBuilders.standaloneSetup(designationTypeContratResource)
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
    public static DesignationTypeContrat createEntity(EntityManager em) {
        DesignationTypeContrat designationTypeContrat = new DesignationTypeContrat()
            .libelle(DEFAULT_LIBELLE);
        return designationTypeContrat;
    }

    @Before
    public void initTest() {
        designationTypeContratSearchRepository.deleteAll();
        designationTypeContrat = createEntity(em);
    }

    @Test
    @Transactional
    public void createDesignationTypeContrat() throws Exception {
        int databaseSizeBeforeCreate = designationTypeContratRepository.findAll().size();

        // Create the DesignationTypeContrat
        DesignationTypeContratDTO designationTypeContratDTO = designationTypeContratMapper.toDto(designationTypeContrat);
        restDesignationTypeContratMockMvc.perform(post("/api/designation-type-contrats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(designationTypeContratDTO)))
            .andExpect(status().isCreated());

        // Validate the DesignationTypeContrat in the database
        List<DesignationTypeContrat> designationTypeContratList = designationTypeContratRepository.findAll();
        assertThat(designationTypeContratList).hasSize(databaseSizeBeforeCreate + 1);
        DesignationTypeContrat testDesignationTypeContrat = designationTypeContratList.get(designationTypeContratList.size() - 1);
        assertThat(testDesignationTypeContrat.getLibelle()).isEqualTo(DEFAULT_LIBELLE);

        // Validate the DesignationTypeContrat in Elasticsearch
        DesignationTypeContrat designationTypeContratEs = designationTypeContratSearchRepository.findOne(testDesignationTypeContrat.getId());
        assertThat(designationTypeContratEs).isEqualToComparingFieldByField(testDesignationTypeContrat);
    }

    @Test
    @Transactional
    public void createDesignationTypeContratWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = designationTypeContratRepository.findAll().size();

        // Create the DesignationTypeContrat with an existing ID
        designationTypeContrat.setId(1L);
        DesignationTypeContratDTO designationTypeContratDTO = designationTypeContratMapper.toDto(designationTypeContrat);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDesignationTypeContratMockMvc.perform(post("/api/designation-type-contrats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(designationTypeContratDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<DesignationTypeContrat> designationTypeContratList = designationTypeContratRepository.findAll();
        assertThat(designationTypeContratList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = designationTypeContratRepository.findAll().size();
        // set the field null
        designationTypeContrat.setLibelle(null);

        // Create the DesignationTypeContrat, which fails.
        DesignationTypeContratDTO designationTypeContratDTO = designationTypeContratMapper.toDto(designationTypeContrat);

        restDesignationTypeContratMockMvc.perform(post("/api/designation-type-contrats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(designationTypeContratDTO)))
            .andExpect(status().isBadRequest());

        List<DesignationTypeContrat> designationTypeContratList = designationTypeContratRepository.findAll();
        assertThat(designationTypeContratList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDesignationTypeContrats() throws Exception {
        // Initialize the database
        designationTypeContratRepository.saveAndFlush(designationTypeContrat);

        // Get all the designationTypeContratList
        restDesignationTypeContratMockMvc.perform(get("/api/designation-type-contrats?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(designationTypeContrat.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())));
    }

    @Test
    @Transactional
    public void getDesignationTypeContrat() throws Exception {
        // Initialize the database
        designationTypeContratRepository.saveAndFlush(designationTypeContrat);

        // Get the designationTypeContrat
        restDesignationTypeContratMockMvc.perform(get("/api/designation-type-contrats/{id}", designationTypeContrat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(designationTypeContrat.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDesignationTypeContrat() throws Exception {
        // Get the designationTypeContrat
        restDesignationTypeContratMockMvc.perform(get("/api/designation-type-contrats/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDesignationTypeContrat() throws Exception {
        // Initialize the database
        designationTypeContratRepository.saveAndFlush(designationTypeContrat);
        designationTypeContratSearchRepository.save(designationTypeContrat);
        int databaseSizeBeforeUpdate = designationTypeContratRepository.findAll().size();

        // Update the designationTypeContrat
        DesignationTypeContrat updatedDesignationTypeContrat = designationTypeContratRepository.findOne(designationTypeContrat.getId());
        updatedDesignationTypeContrat
            .libelle(UPDATED_LIBELLE);
        DesignationTypeContratDTO designationTypeContratDTO = designationTypeContratMapper.toDto(updatedDesignationTypeContrat);

        restDesignationTypeContratMockMvc.perform(put("/api/designation-type-contrats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(designationTypeContratDTO)))
            .andExpect(status().isOk());

        // Validate the DesignationTypeContrat in the database
        List<DesignationTypeContrat> designationTypeContratList = designationTypeContratRepository.findAll();
        assertThat(designationTypeContratList).hasSize(databaseSizeBeforeUpdate);
        DesignationTypeContrat testDesignationTypeContrat = designationTypeContratList.get(designationTypeContratList.size() - 1);
        assertThat(testDesignationTypeContrat.getLibelle()).isEqualTo(UPDATED_LIBELLE);

        // Validate the DesignationTypeContrat in Elasticsearch
        DesignationTypeContrat designationTypeContratEs = designationTypeContratSearchRepository.findOne(testDesignationTypeContrat.getId());
        assertThat(designationTypeContratEs).isEqualToComparingFieldByField(testDesignationTypeContrat);
    }

    @Test
    @Transactional
    public void updateNonExistingDesignationTypeContrat() throws Exception {
        int databaseSizeBeforeUpdate = designationTypeContratRepository.findAll().size();

        // Create the DesignationTypeContrat
        DesignationTypeContratDTO designationTypeContratDTO = designationTypeContratMapper.toDto(designationTypeContrat);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDesignationTypeContratMockMvc.perform(put("/api/designation-type-contrats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(designationTypeContratDTO)))
            .andExpect(status().isCreated());

        // Validate the DesignationTypeContrat in the database
        List<DesignationTypeContrat> designationTypeContratList = designationTypeContratRepository.findAll();
        assertThat(designationTypeContratList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDesignationTypeContrat() throws Exception {
        // Initialize the database
        designationTypeContratRepository.saveAndFlush(designationTypeContrat);
        designationTypeContratSearchRepository.save(designationTypeContrat);
        int databaseSizeBeforeDelete = designationTypeContratRepository.findAll().size();

        // Get the designationTypeContrat
        restDesignationTypeContratMockMvc.perform(delete("/api/designation-type-contrats/{id}", designationTypeContrat.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean designationTypeContratExistsInEs = designationTypeContratSearchRepository.exists(designationTypeContrat.getId());
        assertThat(designationTypeContratExistsInEs).isFalse();

        // Validate the database is empty
        List<DesignationTypeContrat> designationTypeContratList = designationTypeContratRepository.findAll();
        assertThat(designationTypeContratList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchDesignationTypeContrat() throws Exception {
        // Initialize the database
        designationTypeContratRepository.saveAndFlush(designationTypeContrat);
        designationTypeContratSearchRepository.save(designationTypeContrat);

        // Search the designationTypeContrat
        restDesignationTypeContratMockMvc.perform(get("/api/_search/designation-type-contrats?query=id:" + designationTypeContrat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(designationTypeContrat.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DesignationTypeContrat.class);
        DesignationTypeContrat designationTypeContrat1 = new DesignationTypeContrat();
        designationTypeContrat1.setId(1L);
        DesignationTypeContrat designationTypeContrat2 = new DesignationTypeContrat();
        designationTypeContrat2.setId(designationTypeContrat1.getId());
        assertThat(designationTypeContrat1).isEqualTo(designationTypeContrat2);
        designationTypeContrat2.setId(2L);
        assertThat(designationTypeContrat1).isNotEqualTo(designationTypeContrat2);
        designationTypeContrat1.setId(null);
        assertThat(designationTypeContrat1).isNotEqualTo(designationTypeContrat2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DesignationTypeContratDTO.class);
        DesignationTypeContratDTO designationTypeContratDTO1 = new DesignationTypeContratDTO();
        designationTypeContratDTO1.setId(1L);
        DesignationTypeContratDTO designationTypeContratDTO2 = new DesignationTypeContratDTO();
        assertThat(designationTypeContratDTO1).isNotEqualTo(designationTypeContratDTO2);
        designationTypeContratDTO2.setId(designationTypeContratDTO1.getId());
        assertThat(designationTypeContratDTO1).isEqualTo(designationTypeContratDTO2);
        designationTypeContratDTO2.setId(2L);
        assertThat(designationTypeContratDTO1).isNotEqualTo(designationTypeContratDTO2);
        designationTypeContratDTO1.setId(null);
        assertThat(designationTypeContratDTO1).isNotEqualTo(designationTypeContratDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(designationTypeContratMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(designationTypeContratMapper.fromId(null)).isNull();
    }
}
