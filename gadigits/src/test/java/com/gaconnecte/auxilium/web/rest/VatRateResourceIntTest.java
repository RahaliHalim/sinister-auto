package com.gaconnecte.auxilium.web.rest;

import com.gaconnecte.auxilium.AuxiliumApp;

import com.gaconnecte.auxilium.domain.VatRate;
import com.gaconnecte.auxilium.repository.VatRateRepository;
import com.gaconnecte.auxilium.service.VatRateService;
import com.gaconnecte.auxilium.repository.search.VatRateSearchRepository;
import com.gaconnecte.auxilium.service.dto.VatRateDTO;
import com.gaconnecte.auxilium.service.mapper.VatRateMapper;
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
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the VatRateResource REST controller.
 *
 * @see VatRateResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuxiliumApp.class)
public class VatRateResourceIntTest {

    private static final BigDecimal DEFAULT_VAT_RATE = new BigDecimal(1);
    private static final BigDecimal UPDATED_VAT_RATE = new BigDecimal(2);

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private VatRateRepository vatRateRepository;

    @Autowired
    private VatRateMapper vatRateMapper;

    @Autowired
    private VatRateService vatRateService;

    @Autowired
    private VatRateSearchRepository vatRateSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restVatRateMockMvc;

    private VatRate vatRate;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        VatRateResource vatRateResource = new VatRateResource(vatRateService);
        this.restVatRateMockMvc = MockMvcBuilders.standaloneSetup(vatRateResource)
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
    public static VatRate createEntity(EntityManager em) {
        VatRate vatRate = new VatRate()
            .vatRate(DEFAULT_VAT_RATE)
            .active(DEFAULT_ACTIVE);
        return vatRate;
    }

    @Before
    public void initTest() {
        vatRateSearchRepository.deleteAll();
        vatRate = createEntity(em);
    }

    @Test
    @Transactional
    public void createVatRate() throws Exception {
        int databaseSizeBeforeCreate = vatRateRepository.findAll().size();

        // Create the VatRate
        VatRateDTO vatRateDTO = vatRateMapper.toDto(vatRate);
        restVatRateMockMvc.perform(post("/api/vat-rates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vatRateDTO)))
            .andExpect(status().isCreated());

        // Validate the VatRate in the database
        List<VatRate> vatRateList = vatRateRepository.findAll();
        assertThat(vatRateList).hasSize(databaseSizeBeforeCreate + 1);
        VatRate testVatRate = vatRateList.get(vatRateList.size() - 1);
        assertThat(testVatRate.getVatRate()).isEqualTo(DEFAULT_VAT_RATE);
        assertThat(testVatRate.isActive()).isEqualTo(DEFAULT_ACTIVE);

        // Validate the VatRate in Elasticsearch
        VatRate vatRateEs = vatRateSearchRepository.findOne(testVatRate.getId());
        assertThat(vatRateEs).isEqualToComparingFieldByField(testVatRate);
    }

    @Test
    @Transactional
    public void createVatRateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vatRateRepository.findAll().size();

        // Create the VatRate with an existing ID
        vatRate.setId(1L);
        VatRateDTO vatRateDTO = vatRateMapper.toDto(vatRate);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVatRateMockMvc.perform(post("/api/vat-rates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vatRateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<VatRate> vatRateList = vatRateRepository.findAll();
        assertThat(vatRateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllVatRates() throws Exception {
        // Initialize the database
        vatRateRepository.saveAndFlush(vatRate);

        // Get all the vatRateList
        restVatRateMockMvc.perform(get("/api/vat-rates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vatRate.getId().intValue())))
            .andExpect(jsonPath("$.[*].vatRate").value(hasItem(DEFAULT_VAT_RATE.intValue())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void getVatRate() throws Exception {
        // Initialize the database
        vatRateRepository.saveAndFlush(vatRate);

        // Get the vatRate
        restVatRateMockMvc.perform(get("/api/vat-rates/{id}", vatRate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(vatRate.getId().intValue()))
            .andExpect(jsonPath("$.vatRate").value(DEFAULT_VAT_RATE.intValue()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingVatRate() throws Exception {
        // Get the vatRate
        restVatRateMockMvc.perform(get("/api/vat-rates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVatRate() throws Exception {
        // Initialize the database
        vatRateRepository.saveAndFlush(vatRate);
        vatRateSearchRepository.save(vatRate);
        int databaseSizeBeforeUpdate = vatRateRepository.findAll().size();

        // Update the vatRate
        VatRate updatedVatRate = vatRateRepository.findOne(vatRate.getId());
        updatedVatRate
            .vatRate(UPDATED_VAT_RATE)
            .active(UPDATED_ACTIVE);
        VatRateDTO vatRateDTO = vatRateMapper.toDto(updatedVatRate);

        restVatRateMockMvc.perform(put("/api/vat-rates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vatRateDTO)))
            .andExpect(status().isOk());

        // Validate the VatRate in the database
        List<VatRate> vatRateList = vatRateRepository.findAll();
        assertThat(vatRateList).hasSize(databaseSizeBeforeUpdate);
        VatRate testVatRate = vatRateList.get(vatRateList.size() - 1);
        assertThat(testVatRate.getVatRate()).isEqualTo(UPDATED_VAT_RATE);
        assertThat(testVatRate.isActive()).isEqualTo(UPDATED_ACTIVE);

        // Validate the VatRate in Elasticsearch
        VatRate vatRateEs = vatRateSearchRepository.findOne(testVatRate.getId());
        assertThat(vatRateEs).isEqualToComparingFieldByField(testVatRate);
    }

    @Test
    @Transactional
    public void updateNonExistingVatRate() throws Exception {
        int databaseSizeBeforeUpdate = vatRateRepository.findAll().size();

        // Create the VatRate
        VatRateDTO vatRateDTO = vatRateMapper.toDto(vatRate);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restVatRateMockMvc.perform(put("/api/vat-rates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vatRateDTO)))
            .andExpect(status().isCreated());

        // Validate the VatRate in the database
        List<VatRate> vatRateList = vatRateRepository.findAll();
        assertThat(vatRateList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteVatRate() throws Exception {
        // Initialize the database
        vatRateRepository.saveAndFlush(vatRate);
        vatRateSearchRepository.save(vatRate);
        int databaseSizeBeforeDelete = vatRateRepository.findAll().size();

        // Get the vatRate
        restVatRateMockMvc.perform(delete("/api/vat-rates/{id}", vatRate.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean vatRateExistsInEs = vatRateSearchRepository.exists(vatRate.getId());
        assertThat(vatRateExistsInEs).isFalse();

        // Validate the database is empty
        List<VatRate> vatRateList = vatRateRepository.findAll();
        assertThat(vatRateList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchVatRate() throws Exception {
        // Initialize the database
        vatRateRepository.saveAndFlush(vatRate);
        vatRateSearchRepository.save(vatRate);

        // Search the vatRate
        restVatRateMockMvc.perform(get("/api/_search/vat-rates?query=id:" + vatRate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vatRate.getId().intValue())))
            .andExpect(jsonPath("$.[*].vatRate").value(hasItem(DEFAULT_VAT_RATE.intValue())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VatRate.class);
        VatRate vatRate1 = new VatRate();
        vatRate1.setId(1L);
        VatRate vatRate2 = new VatRate();
        vatRate2.setId(vatRate1.getId());
        assertThat(vatRate1).isEqualTo(vatRate2);
        vatRate2.setId(2L);
        assertThat(vatRate1).isNotEqualTo(vatRate2);
        vatRate1.setId(null);
        assertThat(vatRate1).isNotEqualTo(vatRate2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VatRateDTO.class);
        VatRateDTO vatRateDTO1 = new VatRateDTO();
        vatRateDTO1.setId(1L);
        VatRateDTO vatRateDTO2 = new VatRateDTO();
        assertThat(vatRateDTO1).isNotEqualTo(vatRateDTO2);
        vatRateDTO2.setId(vatRateDTO1.getId());
        assertThat(vatRateDTO1).isEqualTo(vatRateDTO2);
        vatRateDTO2.setId(2L);
        assertThat(vatRateDTO1).isNotEqualTo(vatRateDTO2);
        vatRateDTO1.setId(null);
        assertThat(vatRateDTO1).isNotEqualTo(vatRateDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(vatRateMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(vatRateMapper.fromId(null)).isNull();
    }
}
