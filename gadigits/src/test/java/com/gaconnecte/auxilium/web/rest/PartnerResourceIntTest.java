package com.gaconnecte.auxilium.web.rest;

import com.gaconnecte.auxilium.AuxiliumApp;

import com.gaconnecte.auxilium.domain.Partner;
import com.gaconnecte.auxilium.repository.PartnerRepository;
import com.gaconnecte.auxilium.service.PartnerService;
import com.gaconnecte.auxilium.repository.search.PartnerSearchRepository;
import com.gaconnecte.auxilium.service.dto.PartnerDTO;
import com.gaconnecte.auxilium.service.mapper.PartnerMapper;
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
 * Test class for the PartnerResource REST controller.
 *
 * @see PartnerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuxiliumApp.class)
public class PartnerResourceIntTest {

    private static final String DEFAULT_COMPANY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_TRADE_REGISTER = "AAAAAAAAAA";
    private static final String UPDATED_TRADE_REGISTER = "BBBBBBBBBB";

    private static final String DEFAULT_TAX_IDENTIFICATION_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_TAX_IDENTIFICATION_NUMBER = "BBBBBBBBBB";

    private static final Boolean DEFAULT_FOREIGN_COMPANY = false;
    private static final Boolean UPDATED_FOREIGN_COMPANY = true;

    private static final Integer DEFAULT_GENRE = 1;
    private static final Integer UPDATED_GENRE = 2;

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private PartnerRepository partnerRepository;

    @Autowired
    private PartnerMapper partnerMapper;

    @Autowired
    private PartnerService partnerService;

    @Autowired
    private PartnerSearchRepository partnerSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPartnerMockMvc;

    private Partner partner;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PartnerResource partnerResource = new PartnerResource(partnerService, null, null);
        this.restPartnerMockMvc = MockMvcBuilders.standaloneSetup(partnerResource)
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
    public static Partner createEntity(EntityManager em) {
        Partner partner = new Partner()
            .companyName(DEFAULT_COMPANY_NAME)
            .phone(DEFAULT_PHONE)
            .address(DEFAULT_ADDRESS)
            .tradeRegister(DEFAULT_TRADE_REGISTER)
            .taxIdentificationNumber(DEFAULT_TAX_IDENTIFICATION_NUMBER)
            .foreignCompany(DEFAULT_FOREIGN_COMPANY)
            .genre(DEFAULT_GENRE)
            .active(DEFAULT_ACTIVE);
        return partner;
    }

    @Before
    public void initTest() {
        partnerSearchRepository.deleteAll();
        partner = createEntity(em);
    }

    @Test
    @Transactional
    public void createPartner() throws Exception {
        int databaseSizeBeforeCreate = partnerRepository.findAll().size();

        // Create the Partner
        PartnerDTO partnerDTO = partnerMapper.toDto(partner);
        restPartnerMockMvc.perform(post("/api/partners")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partnerDTO)))
            .andExpect(status().isCreated());

        // Validate the Partner in the database
        List<Partner> partnerList = partnerRepository.findAll();
        assertThat(partnerList).hasSize(databaseSizeBeforeCreate + 1);
        Partner testPartner = partnerList.get(partnerList.size() - 1);
        assertThat(testPartner.getCompanyName()).isEqualTo(DEFAULT_COMPANY_NAME);
        assertThat(testPartner.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testPartner.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testPartner.getTradeRegister()).isEqualTo(DEFAULT_TRADE_REGISTER);
        assertThat(testPartner.getTaxIdentificationNumber()).isEqualTo(DEFAULT_TAX_IDENTIFICATION_NUMBER);
        assertThat(testPartner.isForeignCompany()).isEqualTo(DEFAULT_FOREIGN_COMPANY);
        assertThat(testPartner.getGenre()).isEqualTo(DEFAULT_GENRE);
        assertThat(testPartner.isActive()).isEqualTo(DEFAULT_ACTIVE);

        // Validate the Partner in Elasticsearch
        Partner partnerEs = partnerSearchRepository.findOne(testPartner.getId());
        assertThat(partnerEs).isEqualToComparingFieldByField(testPartner);
    }

    @Test
    @Transactional
    public void createPartnerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = partnerRepository.findAll().size();

        // Create the Partner with an existing ID
        partner.setId(1L);
        PartnerDTO partnerDTO = partnerMapper.toDto(partner);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPartnerMockMvc.perform(post("/api/partners")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partnerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Partner> partnerList = partnerRepository.findAll();
        assertThat(partnerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPartners() throws Exception {
        // Initialize the database
        partnerRepository.saveAndFlush(partner);

        // Get all the partnerList
        restPartnerMockMvc.perform(get("/api/partners?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partner.getId().intValue())))
            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME.toString())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].tradeRegister").value(hasItem(DEFAULT_TRADE_REGISTER.toString())))
            .andExpect(jsonPath("$.[*].taxIdentificationNumber").value(hasItem(DEFAULT_TAX_IDENTIFICATION_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].foreignCompany").value(hasItem(DEFAULT_FOREIGN_COMPANY.booleanValue())))
            .andExpect(jsonPath("$.[*].genre").value(hasItem(DEFAULT_GENRE)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void getPartner() throws Exception {
        // Initialize the database
        partnerRepository.saveAndFlush(partner);

        // Get the partner
        restPartnerMockMvc.perform(get("/api/partners/{id}", partner.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(partner.getId().intValue()))
            .andExpect(jsonPath("$.companyName").value(DEFAULT_COMPANY_NAME.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.tradeRegister").value(DEFAULT_TRADE_REGISTER.toString()))
            .andExpect(jsonPath("$.taxIdentificationNumber").value(DEFAULT_TAX_IDENTIFICATION_NUMBER.toString()))
            .andExpect(jsonPath("$.foreignCompany").value(DEFAULT_FOREIGN_COMPANY.booleanValue()))
            .andExpect(jsonPath("$.genre").value(DEFAULT_GENRE))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPartner() throws Exception {
        // Get the partner
        restPartnerMockMvc.perform(get("/api/partners/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePartner() throws Exception {
        // Initialize the database
        partnerRepository.saveAndFlush(partner);
        partnerSearchRepository.save(partner);
        int databaseSizeBeforeUpdate = partnerRepository.findAll().size();

        // Update the partner
        Partner updatedPartner = partnerRepository.findOne(partner.getId());
        updatedPartner
            .companyName(UPDATED_COMPANY_NAME)
            .phone(UPDATED_PHONE)
            .address(UPDATED_ADDRESS)
            .tradeRegister(UPDATED_TRADE_REGISTER)
            .taxIdentificationNumber(UPDATED_TAX_IDENTIFICATION_NUMBER)
            .foreignCompany(UPDATED_FOREIGN_COMPANY)
            .genre(UPDATED_GENRE)
            .active(UPDATED_ACTIVE);
        PartnerDTO partnerDTO = partnerMapper.toDto(updatedPartner);

        restPartnerMockMvc.perform(put("/api/partners")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partnerDTO)))
            .andExpect(status().isOk());

        // Validate the Partner in the database
        List<Partner> partnerList = partnerRepository.findAll();
        assertThat(partnerList).hasSize(databaseSizeBeforeUpdate);
        Partner testPartner = partnerList.get(partnerList.size() - 1);
        assertThat(testPartner.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
        assertThat(testPartner.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testPartner.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testPartner.getTradeRegister()).isEqualTo(UPDATED_TRADE_REGISTER);
        assertThat(testPartner.getTaxIdentificationNumber()).isEqualTo(UPDATED_TAX_IDENTIFICATION_NUMBER);
        assertThat(testPartner.isForeignCompany()).isEqualTo(UPDATED_FOREIGN_COMPANY);
        assertThat(testPartner.getGenre()).isEqualTo(UPDATED_GENRE);
        assertThat(testPartner.isActive()).isEqualTo(UPDATED_ACTIVE);

        // Validate the Partner in Elasticsearch
        Partner partnerEs = partnerSearchRepository.findOne(testPartner.getId());
        assertThat(partnerEs).isEqualToComparingFieldByField(testPartner);
    }

    @Test
    @Transactional
    public void updateNonExistingPartner() throws Exception {
        int databaseSizeBeforeUpdate = partnerRepository.findAll().size();

        // Create the Partner
        PartnerDTO partnerDTO = partnerMapper.toDto(partner);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPartnerMockMvc.perform(put("/api/partners")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partnerDTO)))
            .andExpect(status().isCreated());

        // Validate the Partner in the database
        List<Partner> partnerList = partnerRepository.findAll();
        assertThat(partnerList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePartner() throws Exception {
        // Initialize the database
        partnerRepository.saveAndFlush(partner);
        partnerSearchRepository.save(partner);
        int databaseSizeBeforeDelete = partnerRepository.findAll().size();

        // Get the partner
        restPartnerMockMvc.perform(delete("/api/partners/{id}", partner.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean partnerExistsInEs = partnerSearchRepository.exists(partner.getId());
        assertThat(partnerExistsInEs).isFalse();

        // Validate the database is empty
        List<Partner> partnerList = partnerRepository.findAll();
        assertThat(partnerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchPartner() throws Exception {
        // Initialize the database
        partnerRepository.saveAndFlush(partner);
        partnerSearchRepository.save(partner);

        // Search the partner
        restPartnerMockMvc.perform(get("/api/_search/partners?query=id:" + partner.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partner.getId().intValue())))
            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME.toString())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].tradeRegister").value(hasItem(DEFAULT_TRADE_REGISTER.toString())))
            .andExpect(jsonPath("$.[*].taxIdentificationNumber").value(hasItem(DEFAULT_TAX_IDENTIFICATION_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].foreignCompany").value(hasItem(DEFAULT_FOREIGN_COMPANY.booleanValue())))
            .andExpect(jsonPath("$.[*].genre").value(hasItem(DEFAULT_GENRE)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Partner.class);
        Partner partner1 = new Partner();
        partner1.setId(1L);
        Partner partner2 = new Partner();
        partner2.setId(partner1.getId());
        assertThat(partner1).isEqualTo(partner2);
        partner2.setId(2L);
        assertThat(partner1).isNotEqualTo(partner2);
        partner1.setId(null);
        assertThat(partner1).isNotEqualTo(partner2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PartnerDTO.class);
        PartnerDTO partnerDTO1 = new PartnerDTO();
        partnerDTO1.setId(1L);
        PartnerDTO partnerDTO2 = new PartnerDTO();
        assertThat(partnerDTO1).isNotEqualTo(partnerDTO2);
        partnerDTO2.setId(partnerDTO1.getId());
        assertThat(partnerDTO1).isEqualTo(partnerDTO2);
        partnerDTO2.setId(2L);
        assertThat(partnerDTO1).isNotEqualTo(partnerDTO2);
        partnerDTO1.setId(null);
        assertThat(partnerDTO1).isNotEqualTo(partnerDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(partnerMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(partnerMapper.fromId(null)).isNull();
    }
}
