package com.gaconnecte.auxilium.web.rest;

import com.gaconnecte.auxilium.AuxiliumApp;

import com.gaconnecte.auxilium.domain.DesignationUsageContrat;
import com.gaconnecte.auxilium.repository.DesignationUsageContratRepository;
import com.gaconnecte.auxilium.service.DesignationUsageContratService;
import com.gaconnecte.auxilium.repository.search.DesignationUsageContratSearchRepository;
import com.gaconnecte.auxilium.service.dto.DesignationUsageContratDTO;
import com.gaconnecte.auxilium.service.mapper.DesignationUsageContratMapper;
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
 * Test class for the DesignationUsageContratResource REST controller.
 *
 * @see DesignationUsageContratResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuxiliumApp.class)
public class DesignationUsageContratResourceIntTest {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    @Autowired
    private DesignationUsageContratRepository designationUsageContratRepository;

    @Autowired
    private DesignationUsageContratMapper designationUsageContratMapper;

    @Autowired
    private DesignationUsageContratService designationUsageContratService;

    @Autowired
    private DesignationUsageContratSearchRepository designationUsageContratSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDesignationUsageContratMockMvc;

    private DesignationUsageContrat designationUsageContrat;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        //DesignationUsageContratResource designationUsageContratResource = new DesignationUsageContratResource(designationUsageContratService);
        this.restDesignationUsageContratMockMvc = MockMvcBuilders.standaloneSetup(null)
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
    public static DesignationUsageContrat createEntity(EntityManager em) {
        DesignationUsageContrat designationUsageContrat = new DesignationUsageContrat()
            .libelle(DEFAULT_LIBELLE);
        return designationUsageContrat;
    }

    @Before
    public void initTest() {
        designationUsageContratSearchRepository.deleteAll();
        designationUsageContrat = createEntity(em);
    }

    @Test
    @Transactional
    public void createDesignationUsageContrat() throws Exception {
        int databaseSizeBeforeCreate = designationUsageContratRepository.findAll().size();

        // Create the DesignationUsageContrat
        DesignationUsageContratDTO designationUsageContratDTO = designationUsageContratMapper.toDto(designationUsageContrat);
        restDesignationUsageContratMockMvc.perform(post("/api/designation-usage-contrats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(designationUsageContratDTO)))
            .andExpect(status().isCreated());

        // Validate the DesignationUsageContrat in the database
        List<DesignationUsageContrat> designationUsageContratList = designationUsageContratRepository.findAll();
        assertThat(designationUsageContratList).hasSize(databaseSizeBeforeCreate + 1);
        DesignationUsageContrat testDesignationUsageContrat = designationUsageContratList.get(designationUsageContratList.size() - 1);
        assertThat(testDesignationUsageContrat.getLibelle()).isEqualTo(DEFAULT_LIBELLE);

        // Validate the DesignationUsageContrat in Elasticsearch
        DesignationUsageContrat designationUsageContratEs = designationUsageContratSearchRepository.findOne(testDesignationUsageContrat.getId());
        assertThat(designationUsageContratEs).isEqualToComparingFieldByField(testDesignationUsageContrat);
    }

    @Test
    @Transactional
    public void createDesignationUsageContratWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = designationUsageContratRepository.findAll().size();

        // Create the DesignationUsageContrat with an existing ID
        designationUsageContrat.setId(1L);
        DesignationUsageContratDTO designationUsageContratDTO = designationUsageContratMapper.toDto(designationUsageContrat);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDesignationUsageContratMockMvc.perform(post("/api/designation-usage-contrats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(designationUsageContratDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<DesignationUsageContrat> designationUsageContratList = designationUsageContratRepository.findAll();
        assertThat(designationUsageContratList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = designationUsageContratRepository.findAll().size();
        // set the field null
        designationUsageContrat.setLibelle(null);

        // Create the DesignationUsageContrat, which fails.
        DesignationUsageContratDTO designationUsageContratDTO = designationUsageContratMapper.toDto(designationUsageContrat);

        restDesignationUsageContratMockMvc.perform(post("/api/designation-usage-contrats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(designationUsageContratDTO)))
            .andExpect(status().isBadRequest());

        List<DesignationUsageContrat> designationUsageContratList = designationUsageContratRepository.findAll();
        assertThat(designationUsageContratList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDesignationUsageContrats() throws Exception {
        // Initialize the database
        designationUsageContratRepository.saveAndFlush(designationUsageContrat);

        // Get all the designationUsageContratList
        restDesignationUsageContratMockMvc.perform(get("/api/designation-usage-contrats?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(designationUsageContrat.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())));
    }

    @Test
    @Transactional
    public void getDesignationUsageContrat() throws Exception {
        // Initialize the database
        designationUsageContratRepository.saveAndFlush(designationUsageContrat);

        // Get the designationUsageContrat
        restDesignationUsageContratMockMvc.perform(get("/api/designation-usage-contrats/{id}", designationUsageContrat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(designationUsageContrat.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDesignationUsageContrat() throws Exception {
        // Get the designationUsageContrat
        restDesignationUsageContratMockMvc.perform(get("/api/designation-usage-contrats/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDesignationUsageContrat() throws Exception {
        // Initialize the database
        designationUsageContratRepository.saveAndFlush(designationUsageContrat);
        designationUsageContratSearchRepository.save(designationUsageContrat);
        int databaseSizeBeforeUpdate = designationUsageContratRepository.findAll().size();

        // Update the designationUsageContrat
        DesignationUsageContrat updatedDesignationUsageContrat = designationUsageContratRepository.findOne(designationUsageContrat.getId());
        updatedDesignationUsageContrat
            .libelle(UPDATED_LIBELLE);
        DesignationUsageContratDTO designationUsageContratDTO = designationUsageContratMapper.toDto(updatedDesignationUsageContrat);

        restDesignationUsageContratMockMvc.perform(put("/api/designation-usage-contrats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(designationUsageContratDTO)))
            .andExpect(status().isOk());

        // Validate the DesignationUsageContrat in the database
        List<DesignationUsageContrat> designationUsageContratList = designationUsageContratRepository.findAll();
        assertThat(designationUsageContratList).hasSize(databaseSizeBeforeUpdate);
        DesignationUsageContrat testDesignationUsageContrat = designationUsageContratList.get(designationUsageContratList.size() - 1);
        assertThat(testDesignationUsageContrat.getLibelle()).isEqualTo(UPDATED_LIBELLE);

        // Validate the DesignationUsageContrat in Elasticsearch
        DesignationUsageContrat designationUsageContratEs = designationUsageContratSearchRepository.findOne(testDesignationUsageContrat.getId());
        assertThat(designationUsageContratEs).isEqualToComparingFieldByField(testDesignationUsageContrat);
    }

    @Test
    @Transactional
    public void updateNonExistingDesignationUsageContrat() throws Exception {
        int databaseSizeBeforeUpdate = designationUsageContratRepository.findAll().size();

        // Create the DesignationUsageContrat
        DesignationUsageContratDTO designationUsageContratDTO = designationUsageContratMapper.toDto(designationUsageContrat);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDesignationUsageContratMockMvc.perform(put("/api/designation-usage-contrats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(designationUsageContratDTO)))
            .andExpect(status().isCreated());

        // Validate the DesignationUsageContrat in the database
        List<DesignationUsageContrat> designationUsageContratList = designationUsageContratRepository.findAll();
        assertThat(designationUsageContratList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDesignationUsageContrat() throws Exception {
        // Initialize the database
        designationUsageContratRepository.saveAndFlush(designationUsageContrat);
        designationUsageContratSearchRepository.save(designationUsageContrat);
        int databaseSizeBeforeDelete = designationUsageContratRepository.findAll().size();

        // Get the designationUsageContrat
        restDesignationUsageContratMockMvc.perform(delete("/api/designation-usage-contrats/{id}", designationUsageContrat.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean designationUsageContratExistsInEs = designationUsageContratSearchRepository.exists(designationUsageContrat.getId());
        assertThat(designationUsageContratExistsInEs).isFalse();

        // Validate the database is empty
        List<DesignationUsageContrat> designationUsageContratList = designationUsageContratRepository.findAll();
        assertThat(designationUsageContratList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchDesignationUsageContrat() throws Exception {
        // Initialize the database
        designationUsageContratRepository.saveAndFlush(designationUsageContrat);
        designationUsageContratSearchRepository.save(designationUsageContrat);

        // Search the designationUsageContrat
        restDesignationUsageContratMockMvc.perform(get("/api/_search/designation-usage-contrats?query=id:" + designationUsageContrat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(designationUsageContrat.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DesignationUsageContrat.class);
        DesignationUsageContrat designationUsageContrat1 = new DesignationUsageContrat();
        designationUsageContrat1.setId(1L);
        DesignationUsageContrat designationUsageContrat2 = new DesignationUsageContrat();
        designationUsageContrat2.setId(designationUsageContrat1.getId());
        assertThat(designationUsageContrat1).isEqualTo(designationUsageContrat2);
        designationUsageContrat2.setId(2L);
        assertThat(designationUsageContrat1).isNotEqualTo(designationUsageContrat2);
        designationUsageContrat1.setId(null);
        assertThat(designationUsageContrat1).isNotEqualTo(designationUsageContrat2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DesignationUsageContratDTO.class);
        DesignationUsageContratDTO designationUsageContratDTO1 = new DesignationUsageContratDTO();
        designationUsageContratDTO1.setId(1L);
        DesignationUsageContratDTO designationUsageContratDTO2 = new DesignationUsageContratDTO();
        assertThat(designationUsageContratDTO1).isNotEqualTo(designationUsageContratDTO2);
        designationUsageContratDTO2.setId(designationUsageContratDTO1.getId());
        assertThat(designationUsageContratDTO1).isEqualTo(designationUsageContratDTO2);
        designationUsageContratDTO2.setId(2L);
        assertThat(designationUsageContratDTO1).isNotEqualTo(designationUsageContratDTO2);
        designationUsageContratDTO1.setId(null);
        assertThat(designationUsageContratDTO1).isNotEqualTo(designationUsageContratDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(designationUsageContratMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(designationUsageContratMapper.fromId(null)).isNull();
    }
}
