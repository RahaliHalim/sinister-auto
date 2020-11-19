package com.gaconnecte.auxilium.web.rest;

import com.gaconnecte.auxilium.AuxiliumApp;

import com.gaconnecte.auxilium.domain.BusinessEntity;
import com.gaconnecte.auxilium.repository.BusinessEntityRepository;
import com.gaconnecte.auxilium.service.BusinessEntityService;
import com.gaconnecte.auxilium.repository.search.BusinessEntitySearchRepository;
import com.gaconnecte.auxilium.service.dto.BusinessEntityDTO;
import com.gaconnecte.auxilium.service.mapper.BusinessEntityMapper;
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
 * Test class for the BusinessEntityResource REST controller.
 *
 * @see BusinessEntityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuxiliumApp.class)
public class BusinessEntityResourceIntTest {

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    @Autowired
    private BusinessEntityRepository businessEntityRepository;

    @Autowired
    private BusinessEntityMapper businessEntityMapper;

    @Autowired
    private BusinessEntityService businessEntityService;

    @Autowired
    private BusinessEntitySearchRepository businessEntitySearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBusinessEntityMockMvc;

    private BusinessEntity businessEntity;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BusinessEntityResource businessEntityResource = new BusinessEntityResource(businessEntityService);
        this.restBusinessEntityMockMvc = MockMvcBuilders.standaloneSetup(businessEntityResource)
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
    public static BusinessEntity createEntity(EntityManager em) {
        BusinessEntity businessEntity = new BusinessEntity()
            .label(DEFAULT_LABEL)
            .code(DEFAULT_CODE);
        return businessEntity;
    }

    @Before
    public void initTest() {
        businessEntitySearchRepository.deleteAll();
        businessEntity = createEntity(em);
    }

    @Test
    @Transactional
    public void createBusinessEntity() throws Exception {
        int databaseSizeBeforeCreate = businessEntityRepository.findAll().size();

        // Create the BusinessEntity
        BusinessEntityDTO businessEntityDTO = businessEntityMapper.toDto(businessEntity);
        restBusinessEntityMockMvc.perform(post("/api/business-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(businessEntityDTO)))
            .andExpect(status().isCreated());

        // Validate the BusinessEntity in the database
        List<BusinessEntity> businessEntityList = businessEntityRepository.findAll();
        assertThat(businessEntityList).hasSize(databaseSizeBeforeCreate + 1);
        BusinessEntity testBusinessEntity = businessEntityList.get(businessEntityList.size() - 1);
        assertThat(testBusinessEntity.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testBusinessEntity.getCode()).isEqualTo(DEFAULT_CODE);

        // Validate the BusinessEntity in Elasticsearch
        BusinessEntity businessEntityEs = businessEntitySearchRepository.findOne(testBusinessEntity.getId());
        assertThat(businessEntityEs).isEqualToComparingFieldByField(testBusinessEntity);
    }

    @Test
    @Transactional
    public void createBusinessEntityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = businessEntityRepository.findAll().size();

        // Create the BusinessEntity with an existing ID
        businessEntity.setId(1L);
        BusinessEntityDTO businessEntityDTO = businessEntityMapper.toDto(businessEntity);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBusinessEntityMockMvc.perform(post("/api/business-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(businessEntityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<BusinessEntity> businessEntityList = businessEntityRepository.findAll();
        assertThat(businessEntityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBusinessEntities() throws Exception {
        // Initialize the database
        businessEntityRepository.saveAndFlush(businessEntity);

        // Get all the businessEntityList
        restBusinessEntityMockMvc.perform(get("/api/business-entities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(businessEntity.getId().intValue())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())));
    }

    @Test
    @Transactional
    public void getBusinessEntity() throws Exception {
        // Initialize the database
        businessEntityRepository.saveAndFlush(businessEntity);

        // Get the businessEntity
        restBusinessEntityMockMvc.perform(get("/api/business-entities/{id}", businessEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(businessEntity.getId().intValue()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBusinessEntity() throws Exception {
        // Get the businessEntity
        restBusinessEntityMockMvc.perform(get("/api/business-entities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBusinessEntity() throws Exception {
        // Initialize the database
        businessEntityRepository.saveAndFlush(businessEntity);
        businessEntitySearchRepository.save(businessEntity);
        int databaseSizeBeforeUpdate = businessEntityRepository.findAll().size();

        // Update the businessEntity
        BusinessEntity updatedBusinessEntity = businessEntityRepository.findOne(businessEntity.getId());
        updatedBusinessEntity
            .label(UPDATED_LABEL)
            .code(UPDATED_CODE);
        BusinessEntityDTO businessEntityDTO = businessEntityMapper.toDto(updatedBusinessEntity);

        restBusinessEntityMockMvc.perform(put("/api/business-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(businessEntityDTO)))
            .andExpect(status().isOk());

        // Validate the BusinessEntity in the database
        List<BusinessEntity> businessEntityList = businessEntityRepository.findAll();
        assertThat(businessEntityList).hasSize(databaseSizeBeforeUpdate);
        BusinessEntity testBusinessEntity = businessEntityList.get(businessEntityList.size() - 1);
        assertThat(testBusinessEntity.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testBusinessEntity.getCode()).isEqualTo(UPDATED_CODE);

        // Validate the BusinessEntity in Elasticsearch
        BusinessEntity businessEntityEs = businessEntitySearchRepository.findOne(testBusinessEntity.getId());
        assertThat(businessEntityEs).isEqualToComparingFieldByField(testBusinessEntity);
    }

    @Test
    @Transactional
    public void updateNonExistingBusinessEntity() throws Exception {
        int databaseSizeBeforeUpdate = businessEntityRepository.findAll().size();

        // Create the BusinessEntity
        BusinessEntityDTO businessEntityDTO = businessEntityMapper.toDto(businessEntity);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBusinessEntityMockMvc.perform(put("/api/business-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(businessEntityDTO)))
            .andExpect(status().isCreated());

        // Validate the BusinessEntity in the database
        List<BusinessEntity> businessEntityList = businessEntityRepository.findAll();
        assertThat(businessEntityList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBusinessEntity() throws Exception {
        // Initialize the database
        businessEntityRepository.saveAndFlush(businessEntity);
        businessEntitySearchRepository.save(businessEntity);
        int databaseSizeBeforeDelete = businessEntityRepository.findAll().size();

        // Get the businessEntity
        restBusinessEntityMockMvc.perform(delete("/api/business-entities/{id}", businessEntity.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean businessEntityExistsInEs = businessEntitySearchRepository.exists(businessEntity.getId());
        assertThat(businessEntityExistsInEs).isFalse();

        // Validate the database is empty
        List<BusinessEntity> businessEntityList = businessEntityRepository.findAll();
        assertThat(businessEntityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchBusinessEntity() throws Exception {
        // Initialize the database
        businessEntityRepository.saveAndFlush(businessEntity);
        businessEntitySearchRepository.save(businessEntity);

        // Search the businessEntity
        restBusinessEntityMockMvc.perform(get("/api/_search/business-entities?query=id:" + businessEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(businessEntity.getId().intValue())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BusinessEntity.class);
        BusinessEntity businessEntity1 = new BusinessEntity();
        businessEntity1.setId(1L);
        BusinessEntity businessEntity2 = new BusinessEntity();
        businessEntity2.setId(businessEntity1.getId());
        assertThat(businessEntity1).isEqualTo(businessEntity2);
        businessEntity2.setId(2L);
        assertThat(businessEntity1).isNotEqualTo(businessEntity2);
        businessEntity1.setId(null);
        assertThat(businessEntity1).isNotEqualTo(businessEntity2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BusinessEntityDTO.class);
        BusinessEntityDTO businessEntityDTO1 = new BusinessEntityDTO();
        businessEntityDTO1.setId(1L);
        BusinessEntityDTO businessEntityDTO2 = new BusinessEntityDTO();
        assertThat(businessEntityDTO1).isNotEqualTo(businessEntityDTO2);
        businessEntityDTO2.setId(businessEntityDTO1.getId());
        assertThat(businessEntityDTO1).isEqualTo(businessEntityDTO2);
        businessEntityDTO2.setId(2L);
        assertThat(businessEntityDTO1).isNotEqualTo(businessEntityDTO2);
        businessEntityDTO1.setId(null);
        assertThat(businessEntityDTO1).isNotEqualTo(businessEntityDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(businessEntityMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(businessEntityMapper.fromId(null)).isNull();
    }
}
