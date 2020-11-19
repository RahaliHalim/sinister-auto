package com.gaconnecte.auxilium.web.rest;

import com.gaconnecte.auxilium.AuxiliumApp;

import com.gaconnecte.auxilium.domain.Functionality;
import com.gaconnecte.auxilium.repository.FunctionalityRepository;
import com.gaconnecte.auxilium.service.FunctionalityService;
import com.gaconnecte.auxilium.repository.search.FunctionalitySearchRepository;
import com.gaconnecte.auxilium.service.dto.FunctionalityDTO;
import com.gaconnecte.auxilium.service.mapper.FunctionalityMapper;
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
 * Test class for the FunctionalityResource REST controller.
 *
 * @see FunctionalityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuxiliumApp.class)
public class FunctionalityResourceIntTest {

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    @Autowired
    private FunctionalityRepository functionalityRepository;

    @Autowired
    private FunctionalityMapper functionalityMapper;

    @Autowired
    private FunctionalityService functionalityService;

    @Autowired
    private FunctionalitySearchRepository functionalitySearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFunctionalityMockMvc;

    private Functionality functionality;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FunctionalityResource functionalityResource = new FunctionalityResource(functionalityService);
        this.restFunctionalityMockMvc = MockMvcBuilders.standaloneSetup(functionalityResource)
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
    public static Functionality createEntity(EntityManager em) {
        Functionality functionality = new Functionality()
            .label(DEFAULT_LABEL)
            .code(DEFAULT_CODE);
        return functionality;
    }

    @Before
    public void initTest() {
        functionalitySearchRepository.deleteAll();
        functionality = createEntity(em);
    }

    @Test
    @Transactional
    public void createFunctionality() throws Exception {
        int databaseSizeBeforeCreate = functionalityRepository.findAll().size();

        // Create the Functionality
        FunctionalityDTO functionalityDTO = functionalityMapper.toDto(functionality);
        restFunctionalityMockMvc.perform(post("/api/functionalities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(functionalityDTO)))
            .andExpect(status().isCreated());

        // Validate the Functionality in the database
        List<Functionality> functionalityList = functionalityRepository.findAll();
        assertThat(functionalityList).hasSize(databaseSizeBeforeCreate + 1);
        Functionality testFunctionality = functionalityList.get(functionalityList.size() - 1);
        assertThat(testFunctionality.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testFunctionality.getCode()).isEqualTo(DEFAULT_CODE);

        // Validate the Functionality in Elasticsearch
        Functionality functionalityEs = functionalitySearchRepository.findOne(testFunctionality.getId());
        assertThat(functionalityEs).isEqualToComparingFieldByField(testFunctionality);
    }

    @Test
    @Transactional
    public void createFunctionalityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = functionalityRepository.findAll().size();

        // Create the Functionality with an existing ID
        functionality.setId(1L);
        FunctionalityDTO functionalityDTO = functionalityMapper.toDto(functionality);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFunctionalityMockMvc.perform(post("/api/functionalities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(functionalityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Functionality> functionalityList = functionalityRepository.findAll();
        assertThat(functionalityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllFunctionalities() throws Exception {
        // Initialize the database
        functionalityRepository.saveAndFlush(functionality);

        // Get all the functionalityList
        restFunctionalityMockMvc.perform(get("/api/functionalities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(functionality.getId().intValue())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())));
    }

    @Test
    @Transactional
    public void getFunctionality() throws Exception {
        // Initialize the database
        functionalityRepository.saveAndFlush(functionality);

        // Get the functionality
        restFunctionalityMockMvc.perform(get("/api/functionalities/{id}", functionality.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(functionality.getId().intValue()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFunctionality() throws Exception {
        // Get the functionality
        restFunctionalityMockMvc.perform(get("/api/functionalities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFunctionality() throws Exception {
        // Initialize the database
        functionalityRepository.saveAndFlush(functionality);
        functionalitySearchRepository.save(functionality);
        int databaseSizeBeforeUpdate = functionalityRepository.findAll().size();

        // Update the functionality
        Functionality updatedFunctionality = functionalityRepository.findOne(functionality.getId());
        updatedFunctionality
            .label(UPDATED_LABEL)
            .code(UPDATED_CODE);
        FunctionalityDTO functionalityDTO = functionalityMapper.toDto(updatedFunctionality);

        restFunctionalityMockMvc.perform(put("/api/functionalities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(functionalityDTO)))
            .andExpect(status().isOk());

        // Validate the Functionality in the database
        List<Functionality> functionalityList = functionalityRepository.findAll();
        assertThat(functionalityList).hasSize(databaseSizeBeforeUpdate);
        Functionality testFunctionality = functionalityList.get(functionalityList.size() - 1);
        assertThat(testFunctionality.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testFunctionality.getCode()).isEqualTo(UPDATED_CODE);

        // Validate the Functionality in Elasticsearch
        Functionality functionalityEs = functionalitySearchRepository.findOne(testFunctionality.getId());
        assertThat(functionalityEs).isEqualToComparingFieldByField(testFunctionality);
    }

    @Test
    @Transactional
    public void updateNonExistingFunctionality() throws Exception {
        int databaseSizeBeforeUpdate = functionalityRepository.findAll().size();

        // Create the Functionality
        FunctionalityDTO functionalityDTO = functionalityMapper.toDto(functionality);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFunctionalityMockMvc.perform(put("/api/functionalities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(functionalityDTO)))
            .andExpect(status().isCreated());

        // Validate the Functionality in the database
        List<Functionality> functionalityList = functionalityRepository.findAll();
        assertThat(functionalityList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFunctionality() throws Exception {
        // Initialize the database
        functionalityRepository.saveAndFlush(functionality);
        functionalitySearchRepository.save(functionality);
        int databaseSizeBeforeDelete = functionalityRepository.findAll().size();

        // Get the functionality
        restFunctionalityMockMvc.perform(delete("/api/functionalities/{id}", functionality.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean functionalityExistsInEs = functionalitySearchRepository.exists(functionality.getId());
        assertThat(functionalityExistsInEs).isFalse();

        // Validate the database is empty
        List<Functionality> functionalityList = functionalityRepository.findAll();
        assertThat(functionalityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchFunctionality() throws Exception {
        // Initialize the database
        functionalityRepository.saveAndFlush(functionality);
        functionalitySearchRepository.save(functionality);

        // Search the functionality
        restFunctionalityMockMvc.perform(get("/api/_search/functionalities?query=id:" + functionality.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(functionality.getId().intValue())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Functionality.class);
        Functionality functionality1 = new Functionality();
        functionality1.setId(1L);
        Functionality functionality2 = new Functionality();
        functionality2.setId(functionality1.getId());
        assertThat(functionality1).isEqualTo(functionality2);
        functionality2.setId(2L);
        assertThat(functionality1).isNotEqualTo(functionality2);
        functionality1.setId(null);
        assertThat(functionality1).isNotEqualTo(functionality2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FunctionalityDTO.class);
        FunctionalityDTO functionalityDTO1 = new FunctionalityDTO();
        functionalityDTO1.setId(1L);
        FunctionalityDTO functionalityDTO2 = new FunctionalityDTO();
        assertThat(functionalityDTO1).isNotEqualTo(functionalityDTO2);
        functionalityDTO2.setId(functionalityDTO1.getId());
        assertThat(functionalityDTO1).isEqualTo(functionalityDTO2);
        functionalityDTO2.setId(2L);
        assertThat(functionalityDTO1).isNotEqualTo(functionalityDTO2);
        functionalityDTO1.setId(null);
        assertThat(functionalityDTO1).isNotEqualTo(functionalityDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(functionalityMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(functionalityMapper.fromId(null)).isNull();
    }
}
