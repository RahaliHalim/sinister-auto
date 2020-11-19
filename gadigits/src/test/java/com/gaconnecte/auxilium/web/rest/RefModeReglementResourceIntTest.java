package com.gaconnecte.auxilium.web.rest;

import com.gaconnecte.auxilium.AuxiliumApp;

import com.gaconnecte.auxilium.domain.RefModeReglement;
import com.gaconnecte.auxilium.repository.RefModeReglementRepository;
import com.gaconnecte.auxilium.service.RefModeReglementService;
import com.gaconnecte.auxilium.repository.search.RefModeReglementSearchRepository;
import com.gaconnecte.auxilium.service.dto.RefModeReglementDTO;
import com.gaconnecte.auxilium.service.mapper.RefModeReglementMapper;
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
 * Test class for the RefModeReglementResource REST controller.
 *
 * @see RefModeReglementResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuxiliumApp.class)
public class RefModeReglementResourceIntTest {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_BLOQUE = false;
    private static final Boolean UPDATED_IS_BLOQUE = true;

    @Autowired
    private RefModeReglementRepository refModeReglementRepository;

    @Autowired
    private RefModeReglementMapper refModeReglementMapper;

    @Autowired
    private RefModeReglementService refModeReglementService;

    @Autowired
    private RefModeReglementSearchRepository refModeReglementSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRefModeReglementMockMvc;

    private RefModeReglement refModeReglement;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RefModeReglementResource refModeReglementResource = new RefModeReglementResource(refModeReglementService);
        this.restRefModeReglementMockMvc = MockMvcBuilders.standaloneSetup(refModeReglementResource)
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
    public static RefModeReglement createEntity(EntityManager em) {
        RefModeReglement refModeReglement = new RefModeReglement()
            .nom(DEFAULT_NOM)
            .isBloque(DEFAULT_IS_BLOQUE);
        return refModeReglement;
    }

    @Before
    public void initTest() {
        refModeReglementSearchRepository.deleteAll();
        refModeReglement = createEntity(em);
    }

    @Test
    @Transactional
    public void createRefModeReglement() throws Exception {
        int databaseSizeBeforeCreate = refModeReglementRepository.findAll().size();

        // Create the RefModeReglement
        RefModeReglementDTO refModeReglementDTO = refModeReglementMapper.toDto(refModeReglement);
        restRefModeReglementMockMvc.perform(post("/api/ref-mode-reglements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refModeReglementDTO)))
            .andExpect(status().isCreated());

        // Validate the RefModeReglement in the database
        List<RefModeReglement> refModeReglementList = refModeReglementRepository.findAll();
        assertThat(refModeReglementList).hasSize(databaseSizeBeforeCreate + 1);
        RefModeReglement testRefModeReglement = refModeReglementList.get(refModeReglementList.size() - 1);
        assertThat(testRefModeReglement.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testRefModeReglement.isIsBloque()).isEqualTo(DEFAULT_IS_BLOQUE);

        // Validate the RefModeReglement in Elasticsearch
        RefModeReglement refModeReglementEs = refModeReglementSearchRepository.findOne(testRefModeReglement.getId());
        assertThat(refModeReglementEs).isEqualToComparingFieldByField(testRefModeReglement);
    }

    @Test
    @Transactional
    public void createRefModeReglementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = refModeReglementRepository.findAll().size();

        // Create the RefModeReglement with an existing ID
        refModeReglement.setId(1L);
        RefModeReglementDTO refModeReglementDTO = refModeReglementMapper.toDto(refModeReglement);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRefModeReglementMockMvc.perform(post("/api/ref-mode-reglements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refModeReglementDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<RefModeReglement> refModeReglementList = refModeReglementRepository.findAll();
        assertThat(refModeReglementList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = refModeReglementRepository.findAll().size();
        // set the field null
        refModeReglement.setNom(null);

        // Create the RefModeReglement, which fails.
        RefModeReglementDTO refModeReglementDTO = refModeReglementMapper.toDto(refModeReglement);

        restRefModeReglementMockMvc.perform(post("/api/ref-mode-reglements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refModeReglementDTO)))
            .andExpect(status().isBadRequest());

        List<RefModeReglement> refModeReglementList = refModeReglementRepository.findAll();
        assertThat(refModeReglementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRefModeReglements() throws Exception {
        // Initialize the database
        refModeReglementRepository.saveAndFlush(refModeReglement);

        // Get all the refModeReglementList
        restRefModeReglementMockMvc.perform(get("/api/ref-mode-reglements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(refModeReglement.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].isBloque").value(hasItem(DEFAULT_IS_BLOQUE.booleanValue())));
    }

    @Test
    @Transactional
    public void getRefModeReglement() throws Exception {
        // Initialize the database
        refModeReglementRepository.saveAndFlush(refModeReglement);

        // Get the refModeReglement
        restRefModeReglementMockMvc.perform(get("/api/ref-mode-reglements/{id}", refModeReglement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(refModeReglement.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.isBloque").value(DEFAULT_IS_BLOQUE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingRefModeReglement() throws Exception {
        // Get the refModeReglement
        restRefModeReglementMockMvc.perform(get("/api/ref-mode-reglements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRefModeReglement() throws Exception {
        // Initialize the database
        refModeReglementRepository.saveAndFlush(refModeReglement);
        refModeReglementSearchRepository.save(refModeReglement);
        int databaseSizeBeforeUpdate = refModeReglementRepository.findAll().size();

        // Update the refModeReglement
        RefModeReglement updatedRefModeReglement = refModeReglementRepository.findOne(refModeReglement.getId());
        updatedRefModeReglement
            .nom(UPDATED_NOM)
            .isBloque(UPDATED_IS_BLOQUE);
        RefModeReglementDTO refModeReglementDTO = refModeReglementMapper.toDto(updatedRefModeReglement);

        restRefModeReglementMockMvc.perform(put("/api/ref-mode-reglements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refModeReglementDTO)))
            .andExpect(status().isOk());

        // Validate the RefModeReglement in the database
        List<RefModeReglement> refModeReglementList = refModeReglementRepository.findAll();
        assertThat(refModeReglementList).hasSize(databaseSizeBeforeUpdate);
        RefModeReglement testRefModeReglement = refModeReglementList.get(refModeReglementList.size() - 1);
        assertThat(testRefModeReglement.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testRefModeReglement.isIsBloque()).isEqualTo(UPDATED_IS_BLOQUE);

        // Validate the RefModeReglement in Elasticsearch
        RefModeReglement refModeReglementEs = refModeReglementSearchRepository.findOne(testRefModeReglement.getId());
        assertThat(refModeReglementEs).isEqualToComparingFieldByField(testRefModeReglement);
    }

    @Test
    @Transactional
    public void updateNonExistingRefModeReglement() throws Exception {
        int databaseSizeBeforeUpdate = refModeReglementRepository.findAll().size();

        // Create the RefModeReglement
        RefModeReglementDTO refModeReglementDTO = refModeReglementMapper.toDto(refModeReglement);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRefModeReglementMockMvc.perform(put("/api/ref-mode-reglements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refModeReglementDTO)))
            .andExpect(status().isCreated());

        // Validate the RefModeReglement in the database
        List<RefModeReglement> refModeReglementList = refModeReglementRepository.findAll();
        assertThat(refModeReglementList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRefModeReglement() throws Exception {
        // Initialize the database
        refModeReglementRepository.saveAndFlush(refModeReglement);
        refModeReglementSearchRepository.save(refModeReglement);
        int databaseSizeBeforeDelete = refModeReglementRepository.findAll().size();

        // Get the refModeReglement
        restRefModeReglementMockMvc.perform(delete("/api/ref-mode-reglements/{id}", refModeReglement.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean refModeReglementExistsInEs = refModeReglementSearchRepository.exists(refModeReglement.getId());
        assertThat(refModeReglementExistsInEs).isFalse();

        // Validate the database is empty
        List<RefModeReglement> refModeReglementList = refModeReglementRepository.findAll();
        assertThat(refModeReglementList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchRefModeReglement() throws Exception {
        // Initialize the database
        refModeReglementRepository.saveAndFlush(refModeReglement);
        refModeReglementSearchRepository.save(refModeReglement);

        // Search the refModeReglement
        restRefModeReglementMockMvc.perform(get("/api/_search/ref-mode-reglements?query=id:" + refModeReglement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(refModeReglement.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].isBloque").value(hasItem(DEFAULT_IS_BLOQUE.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RefModeReglement.class);
        RefModeReglement refModeReglement1 = new RefModeReglement();
        refModeReglement1.setId(1L);
        RefModeReglement refModeReglement2 = new RefModeReglement();
        refModeReglement2.setId(refModeReglement1.getId());
        assertThat(refModeReglement1).isEqualTo(refModeReglement2);
        refModeReglement2.setId(2L);
        assertThat(refModeReglement1).isNotEqualTo(refModeReglement2);
        refModeReglement1.setId(null);
        assertThat(refModeReglement1).isNotEqualTo(refModeReglement2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RefModeReglementDTO.class);
        RefModeReglementDTO refModeReglementDTO1 = new RefModeReglementDTO();
        refModeReglementDTO1.setId(1L);
        RefModeReglementDTO refModeReglementDTO2 = new RefModeReglementDTO();
        assertThat(refModeReglementDTO1).isNotEqualTo(refModeReglementDTO2);
        refModeReglementDTO2.setId(refModeReglementDTO1.getId());
        assertThat(refModeReglementDTO1).isEqualTo(refModeReglementDTO2);
        refModeReglementDTO2.setId(2L);
        assertThat(refModeReglementDTO1).isNotEqualTo(refModeReglementDTO2);
        refModeReglementDTO1.setId(null);
        assertThat(refModeReglementDTO1).isNotEqualTo(refModeReglementDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(refModeReglementMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(refModeReglementMapper.fromId(null)).isNull();
    }
}
