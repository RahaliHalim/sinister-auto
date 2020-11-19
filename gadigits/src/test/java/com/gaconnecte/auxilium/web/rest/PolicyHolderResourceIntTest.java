package com.gaconnecte.auxilium.web.rest;

import com.gaconnecte.auxilium.AuxiliumApp;

import com.gaconnecte.auxilium.domain.PolicyHolder;
import com.gaconnecte.auxilium.domain.User;
import com.gaconnecte.auxilium.repository.PolicyHolderRepository;
import com.gaconnecte.auxilium.service.PolicyHolderService;
import com.gaconnecte.auxilium.repository.search.PolicyHolderSearchRepository;
import com.gaconnecte.auxilium.service.dto.PolicyHolderDTO;
import com.gaconnecte.auxilium.service.mapper.PolicyHolderMapper;
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
 * Test class for the PolicyHolderResource REST controller.
 *
 * @see PolicyHolderResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuxiliumApp.class)
public class PolicyHolderResourceIntTest {

    private static final Boolean DEFAULT_COMPANY = false;
    private static final Boolean UPDATED_COMPANY = true;

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_SECOND_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_SECOND_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_FAX = "AAAAAAAAAA";
    private static final String UPDATED_FAX = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_SECOND_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_SECOND_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final Integer DEFAULT_IDENTIFIER = 1;
    private static final Integer UPDATED_IDENTIFIER = 2;

    private static final Boolean DEFAULT_VAT_REGISTERED = false;
    private static final Boolean UPDATED_VAT_REGISTERED = true;

    private static final String DEFAULT_TRADE_REGISTER = "AAAAAAAAAA";
    private static final String UPDATED_TRADE_REGISTER = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UPDATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private PolicyHolderRepository policyHolderRepository;

    @Autowired
    private PolicyHolderMapper policyHolderMapper;

    @Autowired
    private PolicyHolderService policyHolderService;

    @Autowired
    private PolicyHolderSearchRepository policyHolderSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPolicyHolderMockMvc;

    private PolicyHolder policyHolder;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PolicyHolderResource policyHolderResource = new PolicyHolderResource(policyHolderService);
        this.restPolicyHolderMockMvc = MockMvcBuilders.standaloneSetup(policyHolderResource)
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
    public static PolicyHolder createEntity(EntityManager em) {
        PolicyHolder policyHolder = new PolicyHolder()
            .company(DEFAULT_COMPANY)
            .title(DEFAULT_TITLE)
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .companyName(DEFAULT_COMPANY_NAME)
            .firstPhone(DEFAULT_FIRST_PHONE)
            .secondPhone(DEFAULT_SECOND_PHONE)
            .fax(DEFAULT_FAX)
            .firstEmail(DEFAULT_FIRST_EMAIL)
            .secondEmail(DEFAULT_SECOND_EMAIL)
            .address(DEFAULT_ADDRESS)
            .identifier(DEFAULT_IDENTIFIER)
            .vatRegistered(DEFAULT_VAT_REGISTERED)
            .tradeRegister(DEFAULT_TRADE_REGISTER)
            .creationDate(DEFAULT_CREATION_DATE)
            .updateDate(DEFAULT_UPDATE_DATE);
        // Add required entity
        User creationUser = new User();
        creationUser.setId(1l);
        em.persist(creationUser);
        em.flush();
        policyHolder.setCreationUser(creationUser);
        return policyHolder;
    }

    @Before
    public void initTest() {
        policyHolderSearchRepository.deleteAll();
        policyHolder = createEntity(em);
    }

    @Test
    @Transactional
    public void createPolicyHolder() throws Exception {
        int databaseSizeBeforeCreate = policyHolderRepository.findAll().size();

        // Create the PolicyHolder
        PolicyHolderDTO policyHolderDTO = policyHolderMapper.toDto(policyHolder);
        restPolicyHolderMockMvc.perform(post("/api/policy-holders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(policyHolderDTO)))
            .andExpect(status().isCreated());

        // Validate the PolicyHolder in the database
        List<PolicyHolder> policyHolderList = policyHolderRepository.findAll();
        assertThat(policyHolderList).hasSize(databaseSizeBeforeCreate + 1);
        PolicyHolder testPolicyHolder = policyHolderList.get(policyHolderList.size() - 1);
        assertThat(testPolicyHolder.isCompany()).isEqualTo(DEFAULT_COMPANY);
        assertThat(testPolicyHolder.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testPolicyHolder.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testPolicyHolder.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testPolicyHolder.getCompanyName()).isEqualTo(DEFAULT_COMPANY_NAME);
        assertThat(testPolicyHolder.getFirstPhone()).isEqualTo(DEFAULT_FIRST_PHONE);
        assertThat(testPolicyHolder.getSecondPhone()).isEqualTo(DEFAULT_SECOND_PHONE);
        assertThat(testPolicyHolder.getFax()).isEqualTo(DEFAULT_FAX);
        assertThat(testPolicyHolder.getFirstEmail()).isEqualTo(DEFAULT_FIRST_EMAIL);
        assertThat(testPolicyHolder.getSecondEmail()).isEqualTo(DEFAULT_SECOND_EMAIL);
        assertThat(testPolicyHolder.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testPolicyHolder.getIdentifier()).isEqualTo(DEFAULT_IDENTIFIER);
        assertThat(testPolicyHolder.isVatRegistered()).isEqualTo(DEFAULT_VAT_REGISTERED);
        assertThat(testPolicyHolder.getTradeRegister()).isEqualTo(DEFAULT_TRADE_REGISTER);
        assertThat(testPolicyHolder.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testPolicyHolder.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);

        // Validate the PolicyHolder in Elasticsearch
        PolicyHolder policyHolderEs = policyHolderSearchRepository.findOne(testPolicyHolder.getId());
        assertThat(policyHolderEs).isEqualToComparingFieldByField(testPolicyHolder);
    }

    @Test
    @Transactional
    public void createPolicyHolderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = policyHolderRepository.findAll().size();

        // Create the PolicyHolder with an existing ID
        policyHolder.setId(1L);
        PolicyHolderDTO policyHolderDTO = policyHolderMapper.toDto(policyHolder);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPolicyHolderMockMvc.perform(post("/api/policy-holders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(policyHolderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PolicyHolder> policyHolderList = policyHolderRepository.findAll();
        assertThat(policyHolderList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPolicyHolders() throws Exception {
        // Initialize the database
        policyHolderRepository.saveAndFlush(policyHolder);

        // Get all the policyHolderList
        restPolicyHolderMockMvc.perform(get("/api/policy-holders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(policyHolder.getId().intValue())))
            .andExpect(jsonPath("$.[*].company").value(hasItem(DEFAULT_COMPANY.booleanValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME.toString())))
            .andExpect(jsonPath("$.[*].firstPhone").value(hasItem(DEFAULT_FIRST_PHONE.toString())))
            .andExpect(jsonPath("$.[*].secondPhone").value(hasItem(DEFAULT_SECOND_PHONE.toString())))
            .andExpect(jsonPath("$.[*].fax").value(hasItem(DEFAULT_FAX.toString())))
            .andExpect(jsonPath("$.[*].firstEmail").value(hasItem(DEFAULT_FIRST_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].secondEmail").value(hasItem(DEFAULT_SECOND_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].identifier").value(hasItem(DEFAULT_IDENTIFIER)))
            .andExpect(jsonPath("$.[*].vatRegistered").value(hasItem(DEFAULT_VAT_REGISTERED.booleanValue())))
            .andExpect(jsonPath("$.[*].tradeRegister").value(hasItem(DEFAULT_TRADE_REGISTER.toString())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())));
    }

    @Test
    @Transactional
    public void getPolicyHolder() throws Exception {
        // Initialize the database
        policyHolderRepository.saveAndFlush(policyHolder);

        // Get the policyHolder
        restPolicyHolderMockMvc.perform(get("/api/policy-holders/{id}", policyHolder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(policyHolder.getId().intValue()))
            .andExpect(jsonPath("$.company").value(DEFAULT_COMPANY.booleanValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.companyName").value(DEFAULT_COMPANY_NAME.toString()))
            .andExpect(jsonPath("$.firstPhone").value(DEFAULT_FIRST_PHONE.toString()))
            .andExpect(jsonPath("$.secondPhone").value(DEFAULT_SECOND_PHONE.toString()))
            .andExpect(jsonPath("$.fax").value(DEFAULT_FAX.toString()))
            .andExpect(jsonPath("$.firstEmail").value(DEFAULT_FIRST_EMAIL.toString()))
            .andExpect(jsonPath("$.secondEmail").value(DEFAULT_SECOND_EMAIL.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.identifier").value(DEFAULT_IDENTIFIER))
            .andExpect(jsonPath("$.vatRegistered").value(DEFAULT_VAT_REGISTERED.booleanValue()))
            .andExpect(jsonPath("$.tradeRegister").value(DEFAULT_TRADE_REGISTER.toString()))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPolicyHolder() throws Exception {
        // Get the policyHolder
        restPolicyHolderMockMvc.perform(get("/api/policy-holders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePolicyHolder() throws Exception {
        // Initialize the database
        policyHolderRepository.saveAndFlush(policyHolder);
        policyHolderSearchRepository.save(policyHolder);
        int databaseSizeBeforeUpdate = policyHolderRepository.findAll().size();

        // Update the policyHolder
        PolicyHolder updatedPolicyHolder = policyHolderRepository.findOne(policyHolder.getId());
        updatedPolicyHolder
            .company(UPDATED_COMPANY)
            .title(UPDATED_TITLE)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .companyName(UPDATED_COMPANY_NAME)
            .firstPhone(UPDATED_FIRST_PHONE)
            .secondPhone(UPDATED_SECOND_PHONE)
            .fax(UPDATED_FAX)
            .firstEmail(UPDATED_FIRST_EMAIL)
            .secondEmail(UPDATED_SECOND_EMAIL)
            .address(UPDATED_ADDRESS)
            .identifier(UPDATED_IDENTIFIER)
            .vatRegistered(UPDATED_VAT_REGISTERED)
            .tradeRegister(UPDATED_TRADE_REGISTER)
            .creationDate(UPDATED_CREATION_DATE)
            .updateDate(UPDATED_UPDATE_DATE);
        PolicyHolderDTO policyHolderDTO = policyHolderMapper.toDto(updatedPolicyHolder);

        restPolicyHolderMockMvc.perform(put("/api/policy-holders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(policyHolderDTO)))
            .andExpect(status().isOk());

        // Validate the PolicyHolder in the database
        List<PolicyHolder> policyHolderList = policyHolderRepository.findAll();
        assertThat(policyHolderList).hasSize(databaseSizeBeforeUpdate);
        PolicyHolder testPolicyHolder = policyHolderList.get(policyHolderList.size() - 1);
        assertThat(testPolicyHolder.isCompany()).isEqualTo(UPDATED_COMPANY);
        assertThat(testPolicyHolder.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testPolicyHolder.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testPolicyHolder.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testPolicyHolder.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
        assertThat(testPolicyHolder.getFirstPhone()).isEqualTo(UPDATED_FIRST_PHONE);
        assertThat(testPolicyHolder.getSecondPhone()).isEqualTo(UPDATED_SECOND_PHONE);
        assertThat(testPolicyHolder.getFax()).isEqualTo(UPDATED_FAX);
        assertThat(testPolicyHolder.getFirstEmail()).isEqualTo(UPDATED_FIRST_EMAIL);
        assertThat(testPolicyHolder.getSecondEmail()).isEqualTo(UPDATED_SECOND_EMAIL);
        assertThat(testPolicyHolder.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testPolicyHolder.getIdentifier()).isEqualTo(UPDATED_IDENTIFIER);
        assertThat(testPolicyHolder.isVatRegistered()).isEqualTo(UPDATED_VAT_REGISTERED);
        assertThat(testPolicyHolder.getTradeRegister()).isEqualTo(UPDATED_TRADE_REGISTER);
        assertThat(testPolicyHolder.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testPolicyHolder.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);

        // Validate the PolicyHolder in Elasticsearch
        PolicyHolder policyHolderEs = policyHolderSearchRepository.findOne(testPolicyHolder.getId());
        assertThat(policyHolderEs).isEqualToComparingFieldByField(testPolicyHolder);
    }

    @Test
    @Transactional
    public void updateNonExistingPolicyHolder() throws Exception {
        int databaseSizeBeforeUpdate = policyHolderRepository.findAll().size();

        // Create the PolicyHolder
        PolicyHolderDTO policyHolderDTO = policyHolderMapper.toDto(policyHolder);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPolicyHolderMockMvc.perform(put("/api/policy-holders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(policyHolderDTO)))
            .andExpect(status().isCreated());

        // Validate the PolicyHolder in the database
        List<PolicyHolder> policyHolderList = policyHolderRepository.findAll();
        assertThat(policyHolderList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePolicyHolder() throws Exception {
        // Initialize the database
        policyHolderRepository.saveAndFlush(policyHolder);
        policyHolderSearchRepository.save(policyHolder);
        int databaseSizeBeforeDelete = policyHolderRepository.findAll().size();

        // Get the policyHolder
        restPolicyHolderMockMvc.perform(delete("/api/policy-holders/{id}", policyHolder.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean policyHolderExistsInEs = policyHolderSearchRepository.exists(policyHolder.getId());
        assertThat(policyHolderExistsInEs).isFalse();

        // Validate the database is empty
        List<PolicyHolder> policyHolderList = policyHolderRepository.findAll();
        assertThat(policyHolderList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchPolicyHolder() throws Exception {
        // Initialize the database
        policyHolderRepository.saveAndFlush(policyHolder);
        policyHolderSearchRepository.save(policyHolder);

        // Search the policyHolder
        restPolicyHolderMockMvc.perform(get("/api/_search/policy-holders?query=id:" + policyHolder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(policyHolder.getId().intValue())))
            .andExpect(jsonPath("$.[*].company").value(hasItem(DEFAULT_COMPANY.booleanValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME.toString())))
            .andExpect(jsonPath("$.[*].firstPhone").value(hasItem(DEFAULT_FIRST_PHONE.toString())))
            .andExpect(jsonPath("$.[*].secondPhone").value(hasItem(DEFAULT_SECOND_PHONE.toString())))
            .andExpect(jsonPath("$.[*].fax").value(hasItem(DEFAULT_FAX.toString())))
            .andExpect(jsonPath("$.[*].firstEmail").value(hasItem(DEFAULT_FIRST_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].secondEmail").value(hasItem(DEFAULT_SECOND_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].identifier").value(hasItem(DEFAULT_IDENTIFIER)))
            .andExpect(jsonPath("$.[*].vatRegistered").value(hasItem(DEFAULT_VAT_REGISTERED.booleanValue())))
            .andExpect(jsonPath("$.[*].tradeRegister").value(hasItem(DEFAULT_TRADE_REGISTER.toString())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PolicyHolder.class);
        PolicyHolder policyHolder1 = new PolicyHolder();
        policyHolder1.setId(1L);
        PolicyHolder policyHolder2 = new PolicyHolder();
        policyHolder2.setId(policyHolder1.getId());
        assertThat(policyHolder1).isEqualTo(policyHolder2);
        policyHolder2.setId(2L);
        assertThat(policyHolder1).isNotEqualTo(policyHolder2);
        policyHolder1.setId(null);
        assertThat(policyHolder1).isNotEqualTo(policyHolder2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PolicyHolderDTO.class);
        PolicyHolderDTO policyHolderDTO1 = new PolicyHolderDTO();
        policyHolderDTO1.setId(1L);
        PolicyHolderDTO policyHolderDTO2 = new PolicyHolderDTO();
        assertThat(policyHolderDTO1).isNotEqualTo(policyHolderDTO2);
        policyHolderDTO2.setId(policyHolderDTO1.getId());
        assertThat(policyHolderDTO1).isEqualTo(policyHolderDTO2);
        policyHolderDTO2.setId(2L);
        assertThat(policyHolderDTO1).isNotEqualTo(policyHolderDTO2);
        policyHolderDTO1.setId(null);
        assertThat(policyHolderDTO1).isNotEqualTo(policyHolderDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(policyHolderMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(policyHolderMapper.fromId(null)).isNull();
    }
}
