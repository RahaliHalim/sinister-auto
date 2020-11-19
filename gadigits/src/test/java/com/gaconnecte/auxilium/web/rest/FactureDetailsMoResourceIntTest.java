package com.gaconnecte.auxilium.web.rest;

import com.gaconnecte.auxilium.AuxiliumApp;

import com.gaconnecte.auxilium.domain.FactureDetailsMo;
import com.gaconnecte.auxilium.repository.FactureDetailsMoRepository;
import com.gaconnecte.auxilium.service.FactureDetailsMoService;
import com.gaconnecte.auxilium.repository.search.FactureDetailsMoSearchRepository;
import com.gaconnecte.auxilium.service.dto.FactureDetailsMoDTO;
import com.gaconnecte.auxilium.service.mapper.FactureDetailsMoMapper;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the FactureDetailsMoResource REST controller.
 *
 * @see FactureDetailsMoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuxiliumApp.class)
public class FactureDetailsMoResourceIntTest {

    private static final LocalDate DEFAULT_DATE_GENERATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_GENERATION = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private FactureDetailsMoRepository factureDetailsMoRepository;

    @Autowired
    private FactureDetailsMoMapper factureDetailsMoMapper;

    @Autowired
    private FactureDetailsMoService factureDetailsMoService;

    @Autowired
    private FactureDetailsMoSearchRepository factureDetailsMoSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFactureDetailsMoMockMvc;

    private FactureDetailsMo factureDetailsMo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FactureDetailsMoResource factureDetailsMoResource = new FactureDetailsMoResource(factureDetailsMoService);
        this.restFactureDetailsMoMockMvc = MockMvcBuilders.standaloneSetup(factureDetailsMoResource)
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
    public static FactureDetailsMo createEntity(EntityManager em) {
        FactureDetailsMo factureDetailsMo = new FactureDetailsMo()
            .dateGeneration(DEFAULT_DATE_GENERATION);
        return factureDetailsMo;
    }

    @Before
    public void initTest() {
        factureDetailsMoSearchRepository.deleteAll();
        factureDetailsMo = createEntity(em);
    }

    @Test
    @Transactional
    public void createFactureDetailsMo() throws Exception {
        int databaseSizeBeforeCreate = factureDetailsMoRepository.findAll().size();

        // Create the FactureDetailsMo
        FactureDetailsMoDTO factureDetailsMoDTO = factureDetailsMoMapper.toDto(factureDetailsMo);
        restFactureDetailsMoMockMvc.perform(post("/api/facture-details-mos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(factureDetailsMoDTO)))
            .andExpect(status().isCreated());

        // Validate the FactureDetailsMo in the database
        List<FactureDetailsMo> factureDetailsMoList = factureDetailsMoRepository.findAll();
        assertThat(factureDetailsMoList).hasSize(databaseSizeBeforeCreate + 1);
        FactureDetailsMo testFactureDetailsMo = factureDetailsMoList.get(factureDetailsMoList.size() - 1);
        assertThat(testFactureDetailsMo.getDateGeneration()).isEqualTo(DEFAULT_DATE_GENERATION);

        // Validate the FactureDetailsMo in Elasticsearch
        FactureDetailsMo factureDetailsMoEs = factureDetailsMoSearchRepository.findOne(testFactureDetailsMo.getId());
        assertThat(factureDetailsMoEs).isEqualToComparingFieldByField(testFactureDetailsMo);
    }

    @Test
    @Transactional
    public void createFactureDetailsMoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = factureDetailsMoRepository.findAll().size();

        // Create the FactureDetailsMo with an existing ID
        factureDetailsMo.setId(1L);
        FactureDetailsMoDTO factureDetailsMoDTO = factureDetailsMoMapper.toDto(factureDetailsMo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFactureDetailsMoMockMvc.perform(post("/api/facture-details-mos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(factureDetailsMoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<FactureDetailsMo> factureDetailsMoList = factureDetailsMoRepository.findAll();
        assertThat(factureDetailsMoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDateGenerationIsRequired() throws Exception {
        int databaseSizeBeforeTest = factureDetailsMoRepository.findAll().size();
        // set the field null
        factureDetailsMo.setDateGeneration(null);

        // Create the FactureDetailsMo, which fails.
        FactureDetailsMoDTO factureDetailsMoDTO = factureDetailsMoMapper.toDto(factureDetailsMo);

        restFactureDetailsMoMockMvc.perform(post("/api/facture-details-mos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(factureDetailsMoDTO)))
            .andExpect(status().isBadRequest());

        List<FactureDetailsMo> factureDetailsMoList = factureDetailsMoRepository.findAll();
        assertThat(factureDetailsMoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFactureDetailsMos() throws Exception {
        // Initialize the database
        factureDetailsMoRepository.saveAndFlush(factureDetailsMo);

        // Get all the factureDetailsMoList
        restFactureDetailsMoMockMvc.perform(get("/api/facture-details-mos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(factureDetailsMo.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateGeneration").value(hasItem(DEFAULT_DATE_GENERATION.toString())));
    }

    @Test
    @Transactional
    public void getFactureDetailsMo() throws Exception {
        // Initialize the database
        factureDetailsMoRepository.saveAndFlush(factureDetailsMo);

        // Get the factureDetailsMo
        restFactureDetailsMoMockMvc.perform(get("/api/facture-details-mos/{id}", factureDetailsMo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(factureDetailsMo.getId().intValue()))
            .andExpect(jsonPath("$.dateGeneration").value(DEFAULT_DATE_GENERATION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFactureDetailsMo() throws Exception {
        // Get the factureDetailsMo
        restFactureDetailsMoMockMvc.perform(get("/api/facture-details-mos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFactureDetailsMo() throws Exception {
        // Initialize the database
        factureDetailsMoRepository.saveAndFlush(factureDetailsMo);
        factureDetailsMoSearchRepository.save(factureDetailsMo);
        int databaseSizeBeforeUpdate = factureDetailsMoRepository.findAll().size();

        // Update the factureDetailsMo
        FactureDetailsMo updatedFactureDetailsMo = factureDetailsMoRepository.findOne(factureDetailsMo.getId());
        updatedFactureDetailsMo
            .dateGeneration(UPDATED_DATE_GENERATION);
        FactureDetailsMoDTO factureDetailsMoDTO = factureDetailsMoMapper.toDto(updatedFactureDetailsMo);

        restFactureDetailsMoMockMvc.perform(put("/api/facture-details-mos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(factureDetailsMoDTO)))
            .andExpect(status().isOk());

        // Validate the FactureDetailsMo in the database
        List<FactureDetailsMo> factureDetailsMoList = factureDetailsMoRepository.findAll();
        assertThat(factureDetailsMoList).hasSize(databaseSizeBeforeUpdate);
        FactureDetailsMo testFactureDetailsMo = factureDetailsMoList.get(factureDetailsMoList.size() - 1);
        assertThat(testFactureDetailsMo.getDateGeneration()).isEqualTo(UPDATED_DATE_GENERATION);

        // Validate the FactureDetailsMo in Elasticsearch
        FactureDetailsMo factureDetailsMoEs = factureDetailsMoSearchRepository.findOne(testFactureDetailsMo.getId());
        assertThat(factureDetailsMoEs).isEqualToComparingFieldByField(testFactureDetailsMo);
    }

    @Test
    @Transactional
    public void updateNonExistingFactureDetailsMo() throws Exception {
        int databaseSizeBeforeUpdate = factureDetailsMoRepository.findAll().size();

        // Create the FactureDetailsMo
        FactureDetailsMoDTO factureDetailsMoDTO = factureDetailsMoMapper.toDto(factureDetailsMo);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFactureDetailsMoMockMvc.perform(put("/api/facture-details-mos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(factureDetailsMoDTO)))
            .andExpect(status().isCreated());

        // Validate the FactureDetailsMo in the database
        List<FactureDetailsMo> factureDetailsMoList = factureDetailsMoRepository.findAll();
        assertThat(factureDetailsMoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFactureDetailsMo() throws Exception {
        // Initialize the database
        factureDetailsMoRepository.saveAndFlush(factureDetailsMo);
        factureDetailsMoSearchRepository.save(factureDetailsMo);
        int databaseSizeBeforeDelete = factureDetailsMoRepository.findAll().size();

        // Get the factureDetailsMo
        restFactureDetailsMoMockMvc.perform(delete("/api/facture-details-mos/{id}", factureDetailsMo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean factureDetailsMoExistsInEs = factureDetailsMoSearchRepository.exists(factureDetailsMo.getId());
        assertThat(factureDetailsMoExistsInEs).isFalse();

        // Validate the database is empty
        List<FactureDetailsMo> factureDetailsMoList = factureDetailsMoRepository.findAll();
        assertThat(factureDetailsMoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchFactureDetailsMo() throws Exception {
        // Initialize the database
        factureDetailsMoRepository.saveAndFlush(factureDetailsMo);
        factureDetailsMoSearchRepository.save(factureDetailsMo);

        // Search the factureDetailsMo
        restFactureDetailsMoMockMvc.perform(get("/api/_search/facture-details-mos?query=id:" + factureDetailsMo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(factureDetailsMo.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateGeneration").value(hasItem(DEFAULT_DATE_GENERATION.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FactureDetailsMo.class);
        FactureDetailsMo factureDetailsMo1 = new FactureDetailsMo();
        factureDetailsMo1.setId(1L);
        FactureDetailsMo factureDetailsMo2 = new FactureDetailsMo();
        factureDetailsMo2.setId(factureDetailsMo1.getId());
        assertThat(factureDetailsMo1).isEqualTo(factureDetailsMo2);
        factureDetailsMo2.setId(2L);
        assertThat(factureDetailsMo1).isNotEqualTo(factureDetailsMo2);
        factureDetailsMo1.setId(null);
        assertThat(factureDetailsMo1).isNotEqualTo(factureDetailsMo2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FactureDetailsMoDTO.class);
        FactureDetailsMoDTO factureDetailsMoDTO1 = new FactureDetailsMoDTO();
        factureDetailsMoDTO1.setId(1L);
        FactureDetailsMoDTO factureDetailsMoDTO2 = new FactureDetailsMoDTO();
        assertThat(factureDetailsMoDTO1).isNotEqualTo(factureDetailsMoDTO2);
        factureDetailsMoDTO2.setId(factureDetailsMoDTO1.getId());
        assertThat(factureDetailsMoDTO1).isEqualTo(factureDetailsMoDTO2);
        factureDetailsMoDTO2.setId(2L);
        assertThat(factureDetailsMoDTO1).isNotEqualTo(factureDetailsMoDTO2);
        factureDetailsMoDTO1.setId(null);
        assertThat(factureDetailsMoDTO1).isNotEqualTo(factureDetailsMoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(factureDetailsMoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(factureDetailsMoMapper.fromId(null)).isNull();
    }
}
