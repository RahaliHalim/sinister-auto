package com.gaconnecte.auxilium.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import javax.persistence.EntityManager;

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

import com.gaconnecte.auxilium.AuxiliumApp;
import com.gaconnecte.auxilium.domain.RefNatureContrat;
import com.gaconnecte.auxilium.repository.RefNatureContratRepository;
import com.gaconnecte.auxilium.repository.search.RefNatureContratSearchRepository;
import com.gaconnecte.auxilium.service.dto.RefNatureContratDTO;
import com.gaconnecte.auxilium.service.mapper.RefNatureContratMapper;
import com.gaconnecte.auxilium.web.rest.errors.ExceptionTranslator;

/**
 * Test class for the RefNatureContratResource REST controller.
 *
 * @see RefNatureContratResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuxiliumApp.class)
public class RefNatureContratResourceIntTest {

    private static final Integer DEFAULT_CODE = 99999999;
    private static final Integer UPDATED_CODE = 99999998;

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ACTIF = false;
    private static final Boolean UPDATED_IS_ACTIF = true;

    @Autowired
    private RefNatureContratRepository refNatureContratRepository;

    @Autowired
    private RefNatureContratMapper refNatureContratMapper;

   // @Autowired
   // private RefNatureContratService refNatureContratService;

    @Autowired
    private RefNatureContratSearchRepository refNatureContratSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRefNatureContratMockMvc;

    private RefNatureContrat refNatureContrat;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
      //  RefNatureContratResource refNatureContratResource = new RefNatureContratResource(refNatureContratService);
        this.restRefNatureContratMockMvc = MockMvcBuilders.standaloneSetup(null)
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
    public static RefNatureContrat createEntity(EntityManager em) {
        RefNatureContrat refNatureContrat = new RefNatureContrat()
            .code(DEFAULT_CODE)
            .libelle(DEFAULT_LIBELLE)
            .isActif(DEFAULT_IS_ACTIF);
        return refNatureContrat;
    }

    @Before
    public void initTest() {
        refNatureContratSearchRepository.deleteAll();
        refNatureContrat = createEntity(em);
    }

    @Test
    @Transactional
    public void createRefNatureContrat() throws Exception {
        int databaseSizeBeforeCreate = refNatureContratRepository.findAll().size();

        // Create the RefNatureContrat
        RefNatureContratDTO refNatureContratDTO = refNatureContratMapper.toDto(refNatureContrat);
        restRefNatureContratMockMvc.perform(post("/api/ref-nature-contrats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refNatureContratDTO)))
            .andExpect(status().isCreated());

        // Validate the RefNatureContrat in the database
        List<RefNatureContrat> refNatureContratList = refNatureContratRepository.findAll();
        assertThat(refNatureContratList).hasSize(databaseSizeBeforeCreate + 1);
        RefNatureContrat testRefNatureContrat = refNatureContratList.get(refNatureContratList.size() - 1);
        assertThat(testRefNatureContrat.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testRefNatureContrat.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testRefNatureContrat.isIsActif()).isEqualTo(DEFAULT_IS_ACTIF);

        // Validate the RefNatureContrat in Elasticsearch
        RefNatureContrat refNatureContratEs = refNatureContratSearchRepository.findOne(testRefNatureContrat.getId());
        assertThat(refNatureContratEs).isEqualToComparingFieldByField(testRefNatureContrat);
    }

    @Test
    @Transactional
    public void createRefNatureContratWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = refNatureContratRepository.findAll().size();

        // Create the RefNatureContrat with an existing ID
        refNatureContrat.setId(1L);
        RefNatureContratDTO refNatureContratDTO = refNatureContratMapper.toDto(refNatureContrat);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRefNatureContratMockMvc.perform(post("/api/ref-nature-contrats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refNatureContratDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<RefNatureContrat> refNatureContratList = refNatureContratRepository.findAll();
        assertThat(refNatureContratList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = refNatureContratRepository.findAll().size();
        // set the field null
        refNatureContrat.setCode(null);

        // Create the RefNatureContrat, which fails.
        RefNatureContratDTO refNatureContratDTO = refNatureContratMapper.toDto(refNatureContrat);

        restRefNatureContratMockMvc.perform(post("/api/ref-nature-contrats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refNatureContratDTO)))
            .andExpect(status().isBadRequest());

        List<RefNatureContrat> refNatureContratList = refNatureContratRepository.findAll();
        assertThat(refNatureContratList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = refNatureContratRepository.findAll().size();
        // set the field null
        refNatureContrat.setLibelle(null);

        // Create the RefNatureContrat, which fails.
        RefNatureContratDTO refNatureContratDTO = refNatureContratMapper.toDto(refNatureContrat);

        restRefNatureContratMockMvc.perform(post("/api/ref-nature-contrats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refNatureContratDTO)))
            .andExpect(status().isBadRequest());

        List<RefNatureContrat> refNatureContratList = refNatureContratRepository.findAll();
        assertThat(refNatureContratList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRefNatureContrats() throws Exception {
        // Initialize the database
        refNatureContratRepository.saveAndFlush(refNatureContrat);

        // Get all the refNatureContratList
        restRefNatureContratMockMvc.perform(get("/api/ref-nature-contrats?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(refNatureContrat.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())))
            .andExpect(jsonPath("$.[*].isActif").value(hasItem(DEFAULT_IS_ACTIF.booleanValue())));
    }

    @Test
    @Transactional
    public void getRefNatureContrat() throws Exception {
        // Initialize the database
        refNatureContratRepository.saveAndFlush(refNatureContrat);

        // Get the refNatureContrat
        restRefNatureContratMockMvc.perform(get("/api/ref-nature-contrats/{id}", refNatureContrat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(refNatureContrat.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()))
            .andExpect(jsonPath("$.isActif").value(DEFAULT_IS_ACTIF.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingRefNatureContrat() throws Exception {
        // Get the refNatureContrat
        restRefNatureContratMockMvc.perform(get("/api/ref-nature-contrats/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRefNatureContrat() throws Exception {
        // Initialize the database
        refNatureContratRepository.saveAndFlush(refNatureContrat);
        refNatureContratSearchRepository.save(refNatureContrat);
        int databaseSizeBeforeUpdate = refNatureContratRepository.findAll().size();

        // Update the refNatureContrat
        RefNatureContrat updatedRefNatureContrat = refNatureContratRepository.findOne(refNatureContrat.getId());
        updatedRefNatureContrat
            .code(UPDATED_CODE)
            .libelle(UPDATED_LIBELLE)
            .isActif(UPDATED_IS_ACTIF);
        RefNatureContratDTO refNatureContratDTO = refNatureContratMapper.toDto(updatedRefNatureContrat);

        restRefNatureContratMockMvc.perform(put("/api/ref-nature-contrats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refNatureContratDTO)))
            .andExpect(status().isOk());

        // Validate the RefNatureContrat in the database
        List<RefNatureContrat> refNatureContratList = refNatureContratRepository.findAll();
        assertThat(refNatureContratList).hasSize(databaseSizeBeforeUpdate);
        RefNatureContrat testRefNatureContrat = refNatureContratList.get(refNatureContratList.size() - 1);
        assertThat(testRefNatureContrat.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testRefNatureContrat.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testRefNatureContrat.isIsActif()).isEqualTo(UPDATED_IS_ACTIF);

        // Validate the RefNatureContrat in Elasticsearch
        RefNatureContrat refNatureContratEs = refNatureContratSearchRepository.findOne(testRefNatureContrat.getId());
        assertThat(refNatureContratEs).isEqualToComparingFieldByField(testRefNatureContrat);
    }

    @Test
    @Transactional
    public void updateNonExistingRefNatureContrat() throws Exception {
        int databaseSizeBeforeUpdate = refNatureContratRepository.findAll().size();

        // Create the RefNatureContrat
        RefNatureContratDTO refNatureContratDTO = refNatureContratMapper.toDto(refNatureContrat);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRefNatureContratMockMvc.perform(put("/api/ref-nature-contrats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refNatureContratDTO)))
            .andExpect(status().isCreated());

        // Validate the RefNatureContrat in the database
        List<RefNatureContrat> refNatureContratList = refNatureContratRepository.findAll();
        assertThat(refNatureContratList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRefNatureContrat() throws Exception {
        // Initialize the database
        refNatureContratRepository.saveAndFlush(refNatureContrat);
        refNatureContratSearchRepository.save(refNatureContrat);
        int databaseSizeBeforeDelete = refNatureContratRepository.findAll().size();

        // Get the refNatureContrat
        restRefNatureContratMockMvc.perform(delete("/api/ref-nature-contrats/{id}", refNatureContrat.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean refNatureContratExistsInEs = refNatureContratSearchRepository.exists(refNatureContrat.getId());
        assertThat(refNatureContratExistsInEs).isFalse();

        // Validate the database is empty
        List<RefNatureContrat> refNatureContratList = refNatureContratRepository.findAll();
        assertThat(refNatureContratList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchRefNatureContrat() throws Exception {
        // Initialize the database
        refNatureContratRepository.saveAndFlush(refNatureContrat);
        refNatureContratSearchRepository.save(refNatureContrat);

        // Search the refNatureContrat
        restRefNatureContratMockMvc.perform(get("/api/_search/ref-nature-contrats?query=id:" + refNatureContrat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(refNatureContrat.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())))
            .andExpect(jsonPath("$.[*].isActif").value(hasItem(DEFAULT_IS_ACTIF.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RefNatureContrat.class);
        RefNatureContrat refNatureContrat1 = new RefNatureContrat();
        refNatureContrat1.setId(1L);
        RefNatureContrat refNatureContrat2 = new RefNatureContrat();
        refNatureContrat2.setId(refNatureContrat1.getId());
        assertThat(refNatureContrat1).isEqualTo(refNatureContrat2);
        refNatureContrat2.setId(2L);
        assertThat(refNatureContrat1).isNotEqualTo(refNatureContrat2);
        refNatureContrat1.setId(null);
        assertThat(refNatureContrat1).isNotEqualTo(refNatureContrat2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RefNatureContratDTO.class);
        RefNatureContratDTO refNatureContratDTO1 = new RefNatureContratDTO();
        refNatureContratDTO1.setId(1L);
        RefNatureContratDTO refNatureContratDTO2 = new RefNatureContratDTO();
        assertThat(refNatureContratDTO1).isNotEqualTo(refNatureContratDTO2);
        refNatureContratDTO2.setId(refNatureContratDTO1.getId());
        assertThat(refNatureContratDTO1).isEqualTo(refNatureContratDTO2);
        refNatureContratDTO2.setId(2L);
        assertThat(refNatureContratDTO1).isNotEqualTo(refNatureContratDTO2);
        refNatureContratDTO1.setId(null);
        assertThat(refNatureContratDTO1).isNotEqualTo(refNatureContratDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(refNatureContratMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(refNatureContratMapper.fromId(null)).isNull();
    }
}
