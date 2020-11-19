package com.gaconnecte.auxilium.web.rest;

import com.gaconnecte.auxilium.AuxiliumApp;

import com.gaconnecte.auxilium.domain.RefBareme;
import com.gaconnecte.auxilium.repository.RefBaremeRepository;
import com.gaconnecte.auxilium.service.RefBaremeService;
import com.gaconnecte.auxilium.repository.search.RefBaremeSearchRepository;
import com.gaconnecte.auxilium.service.dto.RefBaremeDTO;
import com.gaconnecte.auxilium.service.impl.FileStorageService;
import com.gaconnecte.auxilium.service.mapper.RefBaremeMapper;
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
 * Test class for the RefBaremeResource REST controller.
 *
 * @see RefBaremeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuxiliumApp.class)
public class RefBaremeResourceIntTest {

    private static final Integer DEFAULT_CODE = 99999999;
    private static final Integer UPDATED_CODE = 99999998;

    private static final Integer DEFAULT_RESPONSABILITE_X = 99999999;
    private static final Integer UPDATED_RESPONSABILITE_X = 99999998;

    private static final Integer DEFAULT_RESPONSABILITE_Y = 99999999;
    private static final Integer UPDATED_RESPONSABILITE_Y = 99999998;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private RefBaremeRepository refBaremeRepository;

    @Autowired
    private RefBaremeMapper refBaremeMapper;

    @Autowired
    private RefBaremeService refBaremeService;

    @Autowired
    private RefBaremeSearchRepository refBaremeSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private FileStorageService fileStorageService;
    
    @Autowired
    private EntityManager em;

    private MockMvc restRefBaremeMockMvc;

    private RefBareme refBareme;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RefBaremeResource refBaremeResource = new RefBaremeResource(refBaremeService, null, fileStorageService);
        this.restRefBaremeMockMvc = MockMvcBuilders.standaloneSetup(refBaremeResource)
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
    public static RefBareme createEntity(EntityManager em) {
        RefBareme refBareme = new RefBareme()
            .code(DEFAULT_CODE)
            .responsabiliteX(DEFAULT_RESPONSABILITE_X)
            .responsabiliteY(DEFAULT_RESPONSABILITE_Y)
            .description(DEFAULT_DESCRIPTION);
        return refBareme;
    }

    @Before
    public void initTest() {
        refBaremeSearchRepository.deleteAll();
        refBareme = createEntity(em);
    }

    @Test
    @Transactional
    public void createRefBareme() throws Exception {
        int databaseSizeBeforeCreate = refBaremeRepository.findAll().size();

        // Create the RefBareme
        RefBaremeDTO refBaremeDTO = refBaremeMapper.toDto(refBareme);
        restRefBaremeMockMvc.perform(post("/api/ref-baremes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refBaremeDTO)))
            .andExpect(status().isCreated());

        // Validate the RefBareme in the database
        List<RefBareme> refBaremeList = refBaremeRepository.findAll();
        assertThat(refBaremeList).hasSize(databaseSizeBeforeCreate + 1);
        RefBareme testRefBareme = refBaremeList.get(refBaremeList.size() - 1);
        assertThat(testRefBareme.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testRefBareme.getResponsabiliteX()).isEqualTo(DEFAULT_RESPONSABILITE_X);
        assertThat(testRefBareme.getResponsabiliteY()).isEqualTo(DEFAULT_RESPONSABILITE_Y);
        assertThat(testRefBareme.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the RefBareme in Elasticsearch
        RefBareme refBaremeEs = refBaremeSearchRepository.findOne(testRefBareme.getId());
        assertThat(refBaremeEs).isEqualToComparingFieldByField(testRefBareme);
    }

    @Test
    @Transactional
    public void createRefBaremeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = refBaremeRepository.findAll().size();

        // Create the RefBareme with an existing ID
        refBareme.setId(1L);
        RefBaremeDTO refBaremeDTO = refBaremeMapper.toDto(refBareme);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRefBaremeMockMvc.perform(post("/api/ref-baremes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refBaremeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<RefBareme> refBaremeList = refBaremeRepository.findAll();
        assertThat(refBaremeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = refBaremeRepository.findAll().size();
        // set the field null
        refBareme.setCode(null);

        // Create the RefBareme, which fails.
        RefBaremeDTO refBaremeDTO = refBaremeMapper.toDto(refBareme);

        restRefBaremeMockMvc.perform(post("/api/ref-baremes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refBaremeDTO)))
            .andExpect(status().isBadRequest());

        List<RefBareme> refBaremeList = refBaremeRepository.findAll();
        assertThat(refBaremeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkResponsabiliteXIsRequired() throws Exception {
        int databaseSizeBeforeTest = refBaremeRepository.findAll().size();
        // set the field null
        refBareme.setResponsabiliteX(null);

        // Create the RefBareme, which fails.
        RefBaremeDTO refBaremeDTO = refBaremeMapper.toDto(refBareme);

        restRefBaremeMockMvc.perform(post("/api/ref-baremes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refBaremeDTO)))
            .andExpect(status().isBadRequest());

        List<RefBareme> refBaremeList = refBaremeRepository.findAll();
        assertThat(refBaremeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkResponsabiliteYIsRequired() throws Exception {
        int databaseSizeBeforeTest = refBaremeRepository.findAll().size();
        // set the field null
        refBareme.setResponsabiliteY(null);

        // Create the RefBareme, which fails.
        RefBaremeDTO refBaremeDTO = refBaremeMapper.toDto(refBareme);

        restRefBaremeMockMvc.perform(post("/api/ref-baremes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refBaremeDTO)))
            .andExpect(status().isBadRequest());

        List<RefBareme> refBaremeList = refBaremeRepository.findAll();
        assertThat(refBaremeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = refBaremeRepository.findAll().size();
        // set the field null
        refBareme.setDescription(null);

        // Create the RefBareme, which fails.
        RefBaremeDTO refBaremeDTO = refBaremeMapper.toDto(refBareme);

        restRefBaremeMockMvc.perform(post("/api/ref-baremes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refBaremeDTO)))
            .andExpect(status().isBadRequest());

        List<RefBareme> refBaremeList = refBaremeRepository.findAll();
        assertThat(refBaremeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRefBaremes() throws Exception {
        // Initialize the database
        refBaremeRepository.saveAndFlush(refBareme);

        // Get all the refBaremeList
        restRefBaremeMockMvc.perform(get("/api/ref-baremes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(refBareme.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].responsabiliteX").value(hasItem(DEFAULT_RESPONSABILITE_X)))
            .andExpect(jsonPath("$.[*].responsabiliteY").value(hasItem(DEFAULT_RESPONSABILITE_Y)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getRefBareme() throws Exception {
        // Initialize the database
        refBaremeRepository.saveAndFlush(refBareme);

        // Get the refBareme
        restRefBaremeMockMvc.perform(get("/api/ref-baremes/{id}", refBareme.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(refBareme.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.responsabiliteX").value(DEFAULT_RESPONSABILITE_X))
            .andExpect(jsonPath("$.responsabiliteY").value(DEFAULT_RESPONSABILITE_Y))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRefBareme() throws Exception {
        // Get the refBareme
        restRefBaremeMockMvc.perform(get("/api/ref-baremes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRefBareme() throws Exception {
        // Initialize the database
        refBaremeRepository.saveAndFlush(refBareme);
        refBaremeSearchRepository.save(refBareme);
        int databaseSizeBeforeUpdate = refBaremeRepository.findAll().size();

        // Update the refBareme
        RefBareme updatedRefBareme = refBaremeRepository.findOne(refBareme.getId());
        updatedRefBareme
            .code(UPDATED_CODE)
            .responsabiliteX(UPDATED_RESPONSABILITE_X)
            .responsabiliteY(UPDATED_RESPONSABILITE_Y)
            .description(UPDATED_DESCRIPTION);
        RefBaremeDTO refBaremeDTO = refBaremeMapper.toDto(updatedRefBareme);

        restRefBaremeMockMvc.perform(put("/api/ref-baremes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refBaremeDTO)))
            .andExpect(status().isOk());

        // Validate the RefBareme in the database
        List<RefBareme> refBaremeList = refBaremeRepository.findAll();
        assertThat(refBaremeList).hasSize(databaseSizeBeforeUpdate);
        RefBareme testRefBareme = refBaremeList.get(refBaremeList.size() - 1);
        assertThat(testRefBareme.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testRefBareme.getResponsabiliteX()).isEqualTo(UPDATED_RESPONSABILITE_X);
        assertThat(testRefBareme.getResponsabiliteY()).isEqualTo(UPDATED_RESPONSABILITE_Y);
        assertThat(testRefBareme.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the RefBareme in Elasticsearch
        RefBareme refBaremeEs = refBaremeSearchRepository.findOne(testRefBareme.getId());
        assertThat(refBaremeEs).isEqualToComparingFieldByField(testRefBareme);
    }

    @Test
    @Transactional
    public void updateNonExistingRefBareme() throws Exception {
        int databaseSizeBeforeUpdate = refBaremeRepository.findAll().size();

        // Create the RefBareme
        RefBaremeDTO refBaremeDTO = refBaremeMapper.toDto(refBareme);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRefBaremeMockMvc.perform(put("/api/ref-baremes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refBaremeDTO)))
            .andExpect(status().isCreated());

        // Validate the RefBareme in the database
        List<RefBareme> refBaremeList = refBaremeRepository.findAll();
        assertThat(refBaremeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRefBareme() throws Exception {
        // Initialize the database
        refBaremeRepository.saveAndFlush(refBareme);
        refBaremeSearchRepository.save(refBareme);
        int databaseSizeBeforeDelete = refBaremeRepository.findAll().size();

        // Get the refBareme
        restRefBaremeMockMvc.perform(delete("/api/ref-baremes/{id}", refBareme.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean refBaremeExistsInEs = refBaremeSearchRepository.exists(refBareme.getId());
        assertThat(refBaremeExistsInEs).isFalse();

        // Validate the database is empty
        List<RefBareme> refBaremeList = refBaremeRepository.findAll();
        assertThat(refBaremeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchRefBareme() throws Exception {
        // Initialize the database
        refBaremeRepository.saveAndFlush(refBareme);
        refBaremeSearchRepository.save(refBareme);

        // Search the refBareme
        restRefBaremeMockMvc.perform(get("/api/_search/ref-baremes?query=id:" + refBareme.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(refBareme.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].responsabiliteX").value(hasItem(DEFAULT_RESPONSABILITE_X)))
            .andExpect(jsonPath("$.[*].responsabiliteY").value(hasItem(DEFAULT_RESPONSABILITE_Y)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RefBareme.class);
        RefBareme refBareme1 = new RefBareme();
        refBareme1.setId(1L);
        RefBareme refBareme2 = new RefBareme();
        refBareme2.setId(refBareme1.getId());
        assertThat(refBareme1).isEqualTo(refBareme2);
        refBareme2.setId(2L);
        assertThat(refBareme1).isNotEqualTo(refBareme2);
        refBareme1.setId(null);
        assertThat(refBareme1).isNotEqualTo(refBareme2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RefBaremeDTO.class);
        RefBaremeDTO refBaremeDTO1 = new RefBaremeDTO();
        refBaremeDTO1.setId(1L);
        RefBaremeDTO refBaremeDTO2 = new RefBaremeDTO();
        assertThat(refBaremeDTO1).isNotEqualTo(refBaremeDTO2);
        refBaremeDTO2.setId(refBaremeDTO1.getId());
        assertThat(refBaremeDTO1).isEqualTo(refBaremeDTO2);
        refBaremeDTO2.setId(2L);
        assertThat(refBaremeDTO1).isNotEqualTo(refBaremeDTO2);
        refBaremeDTO1.setId(null);
        assertThat(refBaremeDTO1).isNotEqualTo(refBaremeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(refBaremeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(refBaremeMapper.fromId(null)).isNull();
    }
}
