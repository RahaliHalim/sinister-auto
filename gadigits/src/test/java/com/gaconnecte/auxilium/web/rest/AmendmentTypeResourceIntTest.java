package com.gaconnecte.auxilium.web.rest;

import com.gaconnecte.auxilium.AuxiliumApp;

import com.gaconnecte.auxilium.domain.AmendmentType;
import com.gaconnecte.auxilium.repository.AmendmentTypeRepository;
import com.gaconnecte.auxilium.service.AmendmentTypeService;
import com.gaconnecte.auxilium.repository.search.AmendmentTypeSearchRepository;
import com.gaconnecte.auxilium.service.dto.AmendmentTypeDTO;
import com.gaconnecte.auxilium.service.mapper.AmendmentTypeMapper;
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
 * Test class for the AmendmentTypeResource REST controller.
 *
 * @see AmendmentTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuxiliumApp.class)
public class AmendmentTypeResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private AmendmentTypeRepository amendmentTypeRepository;

    @Autowired
    private AmendmentTypeMapper amendmentTypeMapper;

    @Autowired
    private AmendmentTypeService amendmentTypeService;

    @Autowired
    private AmendmentTypeSearchRepository amendmentTypeSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAmendmentTypeMockMvc;

    private AmendmentType amendmentType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AmendmentTypeResource amendmentTypeResource = new AmendmentTypeResource(amendmentTypeService);
        this.restAmendmentTypeMockMvc = MockMvcBuilders.standaloneSetup(amendmentTypeResource)
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
    public static AmendmentType createEntity(EntityManager em) {
        AmendmentType amendmentType = new AmendmentType()
            .code(DEFAULT_CODE)
            .label(DEFAULT_LABEL)
            .active(DEFAULT_ACTIVE);
        return amendmentType;
    }

    @Before
    public void initTest() {
        amendmentTypeSearchRepository.deleteAll();
        amendmentType = createEntity(em);
    }

    @Test
    @Transactional
    public void createAmendmentType() throws Exception {
        int databaseSizeBeforeCreate = amendmentTypeRepository.findAll().size();

        // Create the AmendmentType
        AmendmentTypeDTO amendmentTypeDTO = amendmentTypeMapper.toDto(amendmentType);
        restAmendmentTypeMockMvc.perform(post("/api/amendment-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amendmentTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the AmendmentType in the database
        List<AmendmentType> amendmentTypeList = amendmentTypeRepository.findAll();
        assertThat(amendmentTypeList).hasSize(databaseSizeBeforeCreate + 1);
        AmendmentType testAmendmentType = amendmentTypeList.get(amendmentTypeList.size() - 1);
        assertThat(testAmendmentType.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testAmendmentType.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testAmendmentType.isActive()).isEqualTo(DEFAULT_ACTIVE);

        // Validate the AmendmentType in Elasticsearch
        AmendmentType amendmentTypeEs = amendmentTypeSearchRepository.findOne(testAmendmentType.getId());
        assertThat(amendmentTypeEs).isEqualToComparingFieldByField(testAmendmentType);
    }

    @Test
    @Transactional
    public void createAmendmentTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = amendmentTypeRepository.findAll().size();

        // Create the AmendmentType with an existing ID
        amendmentType.setId(1L);
        AmendmentTypeDTO amendmentTypeDTO = amendmentTypeMapper.toDto(amendmentType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAmendmentTypeMockMvc.perform(post("/api/amendment-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amendmentTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<AmendmentType> amendmentTypeList = amendmentTypeRepository.findAll();
        assertThat(amendmentTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAmendmentTypes() throws Exception {
        // Initialize the database
        amendmentTypeRepository.saveAndFlush(amendmentType);

        // Get all the amendmentTypeList
        restAmendmentTypeMockMvc.perform(get("/api/amendment-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(amendmentType.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void getAmendmentType() throws Exception {
        // Initialize the database
        amendmentTypeRepository.saveAndFlush(amendmentType);

        // Get the amendmentType
        restAmendmentTypeMockMvc.perform(get("/api/amendment-types/{id}", amendmentType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(amendmentType.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAmendmentType() throws Exception {
        // Get the amendmentType
        restAmendmentTypeMockMvc.perform(get("/api/amendment-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAmendmentType() throws Exception {
        // Initialize the database
        amendmentTypeRepository.saveAndFlush(amendmentType);
        amendmentTypeSearchRepository.save(amendmentType);
        int databaseSizeBeforeUpdate = amendmentTypeRepository.findAll().size();

        // Update the amendmentType
        AmendmentType updatedAmendmentType = amendmentTypeRepository.findOne(amendmentType.getId());
        updatedAmendmentType
            .code(UPDATED_CODE)
            .label(UPDATED_LABEL)
            .active(UPDATED_ACTIVE);
        AmendmentTypeDTO amendmentTypeDTO = amendmentTypeMapper.toDto(updatedAmendmentType);

        restAmendmentTypeMockMvc.perform(put("/api/amendment-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amendmentTypeDTO)))
            .andExpect(status().isOk());

        // Validate the AmendmentType in the database
        List<AmendmentType> amendmentTypeList = amendmentTypeRepository.findAll();
        assertThat(amendmentTypeList).hasSize(databaseSizeBeforeUpdate);
        AmendmentType testAmendmentType = amendmentTypeList.get(amendmentTypeList.size() - 1);
        assertThat(testAmendmentType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testAmendmentType.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testAmendmentType.isActive()).isEqualTo(UPDATED_ACTIVE);

        // Validate the AmendmentType in Elasticsearch
        AmendmentType amendmentTypeEs = amendmentTypeSearchRepository.findOne(testAmendmentType.getId());
        assertThat(amendmentTypeEs).isEqualToComparingFieldByField(testAmendmentType);
    }

    @Test
    @Transactional
    public void updateNonExistingAmendmentType() throws Exception {
        int databaseSizeBeforeUpdate = amendmentTypeRepository.findAll().size();

        // Create the AmendmentType
        AmendmentTypeDTO amendmentTypeDTO = amendmentTypeMapper.toDto(amendmentType);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAmendmentTypeMockMvc.perform(put("/api/amendment-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amendmentTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the AmendmentType in the database
        List<AmendmentType> amendmentTypeList = amendmentTypeRepository.findAll();
        assertThat(amendmentTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAmendmentType() throws Exception {
        // Initialize the database
        amendmentTypeRepository.saveAndFlush(amendmentType);
        amendmentTypeSearchRepository.save(amendmentType);
        int databaseSizeBeforeDelete = amendmentTypeRepository.findAll().size();

        // Get the amendmentType
        restAmendmentTypeMockMvc.perform(delete("/api/amendment-types/{id}", amendmentType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean amendmentTypeExistsInEs = amendmentTypeSearchRepository.exists(amendmentType.getId());
        assertThat(amendmentTypeExistsInEs).isFalse();

        // Validate the database is empty
        List<AmendmentType> amendmentTypeList = amendmentTypeRepository.findAll();
        assertThat(amendmentTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchAmendmentType() throws Exception {
        // Initialize the database
        amendmentTypeRepository.saveAndFlush(amendmentType);
        amendmentTypeSearchRepository.save(amendmentType);

        // Search the amendmentType
        restAmendmentTypeMockMvc.perform(get("/api/_search/amendment-types?query=id:" + amendmentType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(amendmentType.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AmendmentType.class);
        AmendmentType amendmentType1 = new AmendmentType();
        amendmentType1.setId(1L);
        AmendmentType amendmentType2 = new AmendmentType();
        amendmentType2.setId(amendmentType1.getId());
        assertThat(amendmentType1).isEqualTo(amendmentType2);
        amendmentType2.setId(2L);
        assertThat(amendmentType1).isNotEqualTo(amendmentType2);
        amendmentType1.setId(null);
        assertThat(amendmentType1).isNotEqualTo(amendmentType2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AmendmentTypeDTO.class);
        AmendmentTypeDTO amendmentTypeDTO1 = new AmendmentTypeDTO();
        amendmentTypeDTO1.setId(1L);
        AmendmentTypeDTO amendmentTypeDTO2 = new AmendmentTypeDTO();
        assertThat(amendmentTypeDTO1).isNotEqualTo(amendmentTypeDTO2);
        amendmentTypeDTO2.setId(amendmentTypeDTO1.getId());
        assertThat(amendmentTypeDTO1).isEqualTo(amendmentTypeDTO2);
        amendmentTypeDTO2.setId(2L);
        assertThat(amendmentTypeDTO1).isNotEqualTo(amendmentTypeDTO2);
        amendmentTypeDTO1.setId(null);
        assertThat(amendmentTypeDTO1).isNotEqualTo(amendmentTypeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(amendmentTypeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(amendmentTypeMapper.fromId(null)).isNull();
    }
}
