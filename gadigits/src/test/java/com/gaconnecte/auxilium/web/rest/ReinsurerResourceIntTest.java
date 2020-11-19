package com.gaconnecte.auxilium.web.rest;

import com.gaconnecte.auxilium.AuxiliumApp;

import com.gaconnecte.auxilium.domain.Reinsurer;
import com.gaconnecte.auxilium.repository.ReinsurerRepository;
import com.gaconnecte.auxilium.service.ReinsurerService;
import com.gaconnecte.auxilium.repository.search.ReinsurerSearchRepository;
import com.gaconnecte.auxilium.service.dto.ReinsurerDTO;
import com.gaconnecte.auxilium.service.mapper.ReinsurerMapper;
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
 * Test class for the ReinsurerResource REST controller.
 *
 * @see ReinsurerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuxiliumApp.class)
public class ReinsurerResourceIntTest {

    private static final String DEFAULT_COMPANY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TAX_IDENTIFICATION_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_TAX_IDENTIFICATION_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_TRADE_REGISTER = "AAAAAAAAAA";
    private static final String UPDATED_TRADE_REGISTER = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    @Autowired
    private ReinsurerRepository reinsurerRepository;

    @Autowired
    private ReinsurerMapper reinsurerMapper;

    @Autowired
    private ReinsurerService reinsurerService;

    @Autowired
    private ReinsurerSearchRepository reinsurerSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restReinsurerMockMvc;

    private Reinsurer reinsurer;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ReinsurerResource reinsurerResource = new ReinsurerResource(reinsurerService);
        this.restReinsurerMockMvc = MockMvcBuilders.standaloneSetup(reinsurerResource)
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
    public static Reinsurer createEntity(EntityManager em) {
        Reinsurer reinsurer = new Reinsurer()
            .companyName(DEFAULT_COMPANY_NAME)
            .taxIdentificationNumber(DEFAULT_TAX_IDENTIFICATION_NUMBER)
            .tradeRegister(DEFAULT_TRADE_REGISTER)
            .address(DEFAULT_ADDRESS)
            .phone(DEFAULT_PHONE);
        return reinsurer;
    }

    @Before
    public void initTest() {
        reinsurerSearchRepository.deleteAll();
        reinsurer = createEntity(em);
    }

    @Test
    @Transactional
    public void createReinsurer() throws Exception {
        int databaseSizeBeforeCreate = reinsurerRepository.findAll().size();

        // Create the Reinsurer
        ReinsurerDTO reinsurerDTO = reinsurerMapper.toDto(reinsurer);
        restReinsurerMockMvc.perform(post("/api/reinsurers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reinsurerDTO)))
            .andExpect(status().isCreated());

        // Validate the Reinsurer in the database
        List<Reinsurer> reinsurerList = reinsurerRepository.findAll();
        assertThat(reinsurerList).hasSize(databaseSizeBeforeCreate + 1);
        Reinsurer testReinsurer = reinsurerList.get(reinsurerList.size() - 1);
        assertThat(testReinsurer.getCompanyName()).isEqualTo(DEFAULT_COMPANY_NAME);
        assertThat(testReinsurer.getTaxIdentificationNumber()).isEqualTo(DEFAULT_TAX_IDENTIFICATION_NUMBER);
        assertThat(testReinsurer.getTradeRegister()).isEqualTo(DEFAULT_TRADE_REGISTER);
        assertThat(testReinsurer.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testReinsurer.getPhone()).isEqualTo(DEFAULT_PHONE);

        // Validate the Reinsurer in Elasticsearch
        Reinsurer reinsurerEs = reinsurerSearchRepository.findOne(testReinsurer.getId());
        assertThat(reinsurerEs).isEqualToComparingFieldByField(testReinsurer);
    }

    @Test
    @Transactional
    public void createReinsurerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = reinsurerRepository.findAll().size();

        // Create the Reinsurer with an existing ID
        reinsurer.setId(1L);
        ReinsurerDTO reinsurerDTO = reinsurerMapper.toDto(reinsurer);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReinsurerMockMvc.perform(post("/api/reinsurers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reinsurerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Reinsurer> reinsurerList = reinsurerRepository.findAll();
        assertThat(reinsurerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllReinsurers() throws Exception {
        // Initialize the database
        reinsurerRepository.saveAndFlush(reinsurer);

        // Get all the reinsurerList
        restReinsurerMockMvc.perform(get("/api/reinsurers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reinsurer.getId().intValue())))
            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME.toString())))
            .andExpect(jsonPath("$.[*].taxIdentificationNumber").value(hasItem(DEFAULT_TAX_IDENTIFICATION_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].tradeRegister").value(hasItem(DEFAULT_TRADE_REGISTER.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())));
    }

    @Test
    @Transactional
    public void getReinsurer() throws Exception {
        // Initialize the database
        reinsurerRepository.saveAndFlush(reinsurer);

        // Get the reinsurer
        restReinsurerMockMvc.perform(get("/api/reinsurers/{id}", reinsurer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(reinsurer.getId().intValue()))
            .andExpect(jsonPath("$.companyName").value(DEFAULT_COMPANY_NAME.toString()))
            .andExpect(jsonPath("$.taxIdentificationNumber").value(DEFAULT_TAX_IDENTIFICATION_NUMBER.toString()))
            .andExpect(jsonPath("$.tradeRegister").value(DEFAULT_TRADE_REGISTER.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingReinsurer() throws Exception {
        // Get the reinsurer
        restReinsurerMockMvc.perform(get("/api/reinsurers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReinsurer() throws Exception {
        // Initialize the database
        reinsurerRepository.saveAndFlush(reinsurer);
        reinsurerSearchRepository.save(reinsurer);
        int databaseSizeBeforeUpdate = reinsurerRepository.findAll().size();

        // Update the reinsurer
        Reinsurer updatedReinsurer = reinsurerRepository.findOne(reinsurer.getId());
        updatedReinsurer
            .companyName(UPDATED_COMPANY_NAME)
            .taxIdentificationNumber(UPDATED_TAX_IDENTIFICATION_NUMBER)
            .tradeRegister(UPDATED_TRADE_REGISTER)
            .address(UPDATED_ADDRESS)
            .phone(UPDATED_PHONE);
        ReinsurerDTO reinsurerDTO = reinsurerMapper.toDto(updatedReinsurer);

        restReinsurerMockMvc.perform(put("/api/reinsurers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reinsurerDTO)))
            .andExpect(status().isOk());

        // Validate the Reinsurer in the database
        List<Reinsurer> reinsurerList = reinsurerRepository.findAll();
        assertThat(reinsurerList).hasSize(databaseSizeBeforeUpdate);
        Reinsurer testReinsurer = reinsurerList.get(reinsurerList.size() - 1);
        assertThat(testReinsurer.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
        assertThat(testReinsurer.getTaxIdentificationNumber()).isEqualTo(UPDATED_TAX_IDENTIFICATION_NUMBER);
        assertThat(testReinsurer.getTradeRegister()).isEqualTo(UPDATED_TRADE_REGISTER);
        assertThat(testReinsurer.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testReinsurer.getPhone()).isEqualTo(UPDATED_PHONE);

        // Validate the Reinsurer in Elasticsearch
        Reinsurer reinsurerEs = reinsurerSearchRepository.findOne(testReinsurer.getId());
        assertThat(reinsurerEs).isEqualToComparingFieldByField(testReinsurer);
    }

    @Test
    @Transactional
    public void updateNonExistingReinsurer() throws Exception {
        int databaseSizeBeforeUpdate = reinsurerRepository.findAll().size();

        // Create the Reinsurer
        ReinsurerDTO reinsurerDTO = reinsurerMapper.toDto(reinsurer);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restReinsurerMockMvc.perform(put("/api/reinsurers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reinsurerDTO)))
            .andExpect(status().isCreated());

        // Validate the Reinsurer in the database
        List<Reinsurer> reinsurerList = reinsurerRepository.findAll();
        assertThat(reinsurerList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteReinsurer() throws Exception {
        // Initialize the database
        reinsurerRepository.saveAndFlush(reinsurer);
        reinsurerSearchRepository.save(reinsurer);
        int databaseSizeBeforeDelete = reinsurerRepository.findAll().size();

        // Get the reinsurer
        restReinsurerMockMvc.perform(delete("/api/reinsurers/{id}", reinsurer.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean reinsurerExistsInEs = reinsurerSearchRepository.exists(reinsurer.getId());
        assertThat(reinsurerExistsInEs).isFalse();

        // Validate the database is empty
        List<Reinsurer> reinsurerList = reinsurerRepository.findAll();
        assertThat(reinsurerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchReinsurer() throws Exception {
        // Initialize the database
        reinsurerRepository.saveAndFlush(reinsurer);
        reinsurerSearchRepository.save(reinsurer);

        // Search the reinsurer
        restReinsurerMockMvc.perform(get("/api/_search/reinsurers?query=id:" + reinsurer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reinsurer.getId().intValue())))
            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME.toString())))
            .andExpect(jsonPath("$.[*].taxIdentificationNumber").value(hasItem(DEFAULT_TAX_IDENTIFICATION_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].tradeRegister").value(hasItem(DEFAULT_TRADE_REGISTER.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Reinsurer.class);
        Reinsurer reinsurer1 = new Reinsurer();
        reinsurer1.setId(1L);
        Reinsurer reinsurer2 = new Reinsurer();
        reinsurer2.setId(reinsurer1.getId());
        assertThat(reinsurer1).isEqualTo(reinsurer2);
        reinsurer2.setId(2L);
        assertThat(reinsurer1).isNotEqualTo(reinsurer2);
        reinsurer1.setId(null);
        assertThat(reinsurer1).isNotEqualTo(reinsurer2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReinsurerDTO.class);
        ReinsurerDTO reinsurerDTO1 = new ReinsurerDTO();
        reinsurerDTO1.setId(1L);
        ReinsurerDTO reinsurerDTO2 = new ReinsurerDTO();
        assertThat(reinsurerDTO1).isNotEqualTo(reinsurerDTO2);
        reinsurerDTO2.setId(reinsurerDTO1.getId());
        assertThat(reinsurerDTO1).isEqualTo(reinsurerDTO2);
        reinsurerDTO2.setId(2L);
        assertThat(reinsurerDTO1).isNotEqualTo(reinsurerDTO2);
        reinsurerDTO1.setId(null);
        assertThat(reinsurerDTO1).isNotEqualTo(reinsurerDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(reinsurerMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(reinsurerMapper.fromId(null)).isNull();
    }
}
