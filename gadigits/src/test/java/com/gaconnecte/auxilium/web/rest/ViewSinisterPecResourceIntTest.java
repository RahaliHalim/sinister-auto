package com.gaconnecte.auxilium.web.rest;

import com.gaconnecte.auxilium.AuxiliumApp;

import com.gaconnecte.auxilium.domain.ViewSinisterPec;
import com.gaconnecte.auxilium.repository.ViewSinisterPecRepository;
import com.gaconnecte.auxilium.service.UserExtraService;
import com.gaconnecte.auxilium.service.ViewSinisterPecService;
import com.gaconnecte.auxilium.service.dto.ViewSinisterPecDTO;
import com.gaconnecte.auxilium.service.mapper.ViewSinisterPecMapper;
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
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ViewSinisterPecResource REST controller.
 *
 * @see ViewSinisterPecResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuxiliumApp.class)
public class ViewSinisterPecResourceIntTest {

    private static final String DEFAULT_GA_REFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_GA_REFERENCE = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY_REFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_REFERENCE = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_AGENCY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_AGENCY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CONTRACT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_CONTRACT_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_IMMATRICULATION = "AAAAAAAAAA";
    private static final String UPDATED_IMMATRICULATION = "BBBBBBBBBB";

    private static final String DEFAULT_MODE_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_MODE_LABEL = "BBBBBBBBBB";

    private static final LocalDateTime DEFAULT_DECLARATION_DATE = LocalDateTime.now();
    private static final LocalDateTime UPDATED_DECLARATION_DATE = LocalDateTime.now();

    private static final LocalDate DEFAULT_INCIDENT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_INCIDENT_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DECISION = "AAAAAAAAAA";
    private static final String UPDATED_DECISION = "BBBBBBBBBB";

    private static final Integer DEFAULT_STEP = 1;
    private static final Integer UPDATED_STEP = 2;

    private static final String DEFAULT_APPROV_PEC = "AAAAAAAAAA";
    private static final String UPDATED_APPROV_PEC = "BBBBBBBBBB";

    private static final Long DEFAULT_EXPERT_ID = 1L;
    private static final Long UPDATED_EXPERT_ID = 2L;

    private static final Long DEFAULT_REPARATEUR_ID = 1L;
    private static final Long UPDATED_REPARATEUR_ID = 2L;

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;

    private static final Long DEFAULT_ASSIGNED_TO_ID = 1L;
    private static final Long UPDATED_ASSIGNED_TO_ID = 2L;

    @Autowired
    private ViewSinisterPecRepository viewSinisterPecRepository;

    @Autowired
    private ViewSinisterPecMapper viewSinisterPecMapper;

    @Autowired
    private ViewSinisterPecService viewSinisterPecService;
    
    @Autowired
    private UserExtraService userExtraService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restViewSinisterPecMockMvc;

    private ViewSinisterPec viewSinisterPec;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ViewSinisterPecResource viewSinisterPecResource = new ViewSinisterPecResource(viewSinisterPecService,userExtraService);
        this.restViewSinisterPecMockMvc = MockMvcBuilders.standaloneSetup(viewSinisterPecResource)
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
    public static ViewSinisterPec createEntity(EntityManager em) {
        ViewSinisterPec viewSinisterPec = new ViewSinisterPec()
            .gaReference(DEFAULT_GA_REFERENCE)
            .companyReference(DEFAULT_COMPANY_REFERENCE)
            .companyName(DEFAULT_COMPANY_NAME)
            .agencyName(DEFAULT_AGENCY_NAME)
            .contractNumber(DEFAULT_CONTRACT_NUMBER)
            .immatriculation(DEFAULT_IMMATRICULATION)
            .modeLabel(DEFAULT_MODE_LABEL)
            .declarationDate(DEFAULT_DECLARATION_DATE)
            .incidentDate(DEFAULT_INCIDENT_DATE)
            .decision(DEFAULT_DECISION)
            .step(DEFAULT_STEP)
            .approvPec(DEFAULT_APPROV_PEC)
            .expertId(DEFAULT_EXPERT_ID)
            .reparateurId(DEFAULT_REPARATEUR_ID)
            .userId(DEFAULT_USER_ID)
            .assignedToId(DEFAULT_ASSIGNED_TO_ID);
        return viewSinisterPec;
    }

    @Before
    public void initTest() {
        viewSinisterPec = createEntity(em);
    }

    @Test
    @Transactional
    public void createViewSinisterPec() throws Exception {
        int databaseSizeBeforeCreate = viewSinisterPecRepository.findAll().size();

        // Create the ViewSinisterPec
        ViewSinisterPecDTO viewSinisterPecDTO = viewSinisterPecMapper.toDto(viewSinisterPec);
        restViewSinisterPecMockMvc.perform(post("/api/view-sinister-pecs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(viewSinisterPecDTO)))
            .andExpect(status().isCreated());

        // Validate the ViewSinisterPec in the database
        List<ViewSinisterPec> viewSinisterPecList = viewSinisterPecRepository.findAll();
        assertThat(viewSinisterPecList).hasSize(databaseSizeBeforeCreate + 1);
        ViewSinisterPec testViewSinisterPec = viewSinisterPecList.get(viewSinisterPecList.size() - 1);
        assertThat(testViewSinisterPec.getGaReference()).isEqualTo(DEFAULT_GA_REFERENCE);
        assertThat(testViewSinisterPec.getCompanyReference()).isEqualTo(DEFAULT_COMPANY_REFERENCE);
        assertThat(testViewSinisterPec.getCompanyName()).isEqualTo(DEFAULT_COMPANY_NAME);
        assertThat(testViewSinisterPec.getAgencyName()).isEqualTo(DEFAULT_AGENCY_NAME);
        assertThat(testViewSinisterPec.getContractNumber()).isEqualTo(DEFAULT_CONTRACT_NUMBER);
        assertThat(testViewSinisterPec.getImmatriculation()).isEqualTo(DEFAULT_IMMATRICULATION);
        assertThat(testViewSinisterPec.getModeLabel()).isEqualTo(DEFAULT_MODE_LABEL);
        assertThat(testViewSinisterPec.getDeclarationDate()).isEqualTo(DEFAULT_DECLARATION_DATE);
        assertThat(testViewSinisterPec.getIncidentDate()).isEqualTo(DEFAULT_INCIDENT_DATE);
        assertThat(testViewSinisterPec.getDecision()).isEqualTo(DEFAULT_DECISION);
        assertThat(testViewSinisterPec.getStep()).isEqualTo(DEFAULT_STEP);
        assertThat(testViewSinisterPec.getApprovPec()).isEqualTo(DEFAULT_APPROV_PEC);
        assertThat(testViewSinisterPec.getExpertId()).isEqualTo(DEFAULT_EXPERT_ID);
        assertThat(testViewSinisterPec.getReparateurId()).isEqualTo(DEFAULT_REPARATEUR_ID);
        assertThat(testViewSinisterPec.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testViewSinisterPec.getAssignedToId()).isEqualTo(DEFAULT_ASSIGNED_TO_ID);

    }

    @Test
    @Transactional
    public void createViewSinisterPecWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = viewSinisterPecRepository.findAll().size();

        // Create the ViewSinisterPec with an existing ID
        viewSinisterPec.setId(1L);
        ViewSinisterPecDTO viewSinisterPecDTO = viewSinisterPecMapper.toDto(viewSinisterPec);

        // An entity with an existing ID cannot be created, so this API call must fail
        restViewSinisterPecMockMvc.perform(post("/api/view-sinister-pecs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(viewSinisterPecDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ViewSinisterPec> viewSinisterPecList = viewSinisterPecRepository.findAll();
        assertThat(viewSinisterPecList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllViewSinisterPecs() throws Exception {
        // Initialize the database
        viewSinisterPecRepository.saveAndFlush(viewSinisterPec);

        // Get all the viewSinisterPecList
        restViewSinisterPecMockMvc.perform(get("/api/view-sinister-pecs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(viewSinisterPec.getId().intValue())))
            .andExpect(jsonPath("$.[*].gaReference").value(hasItem(DEFAULT_GA_REFERENCE.toString())))
            .andExpect(jsonPath("$.[*].companyReference").value(hasItem(DEFAULT_COMPANY_REFERENCE.toString())))
            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME.toString())))
            .andExpect(jsonPath("$.[*].agencyName").value(hasItem(DEFAULT_AGENCY_NAME.toString())))
            .andExpect(jsonPath("$.[*].contractNumber").value(hasItem(DEFAULT_CONTRACT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].immatriculation").value(hasItem(DEFAULT_IMMATRICULATION.toString())))
            .andExpect(jsonPath("$.[*].modeLabel").value(hasItem(DEFAULT_MODE_LABEL.toString())))
            .andExpect(jsonPath("$.[*].declarationDate").value(hasItem(DEFAULT_DECLARATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].incidentDate").value(hasItem(DEFAULT_INCIDENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].decision").value(hasItem(DEFAULT_DECISION.toString())))
            .andExpect(jsonPath("$.[*].step").value(hasItem(DEFAULT_STEP)))
            .andExpect(jsonPath("$.[*].approvPec").value(hasItem(DEFAULT_APPROV_PEC.toString())))
            .andExpect(jsonPath("$.[*].expertId").value(hasItem(DEFAULT_EXPERT_ID.intValue())))
            .andExpect(jsonPath("$.[*].reparateurId").value(hasItem(DEFAULT_REPARATEUR_ID.intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].assignedToId").value(hasItem(DEFAULT_ASSIGNED_TO_ID.intValue())));
    }

    @Test
    @Transactional
    public void getViewSinisterPec() throws Exception {
        // Initialize the database
        viewSinisterPecRepository.saveAndFlush(viewSinisterPec);

        // Get the viewSinisterPec
        restViewSinisterPecMockMvc.perform(get("/api/view-sinister-pecs/{id}", viewSinisterPec.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(viewSinisterPec.getId().intValue()))
            .andExpect(jsonPath("$.gaReference").value(DEFAULT_GA_REFERENCE.toString()))
            .andExpect(jsonPath("$.companyReference").value(DEFAULT_COMPANY_REFERENCE.toString()))
            .andExpect(jsonPath("$.companyName").value(DEFAULT_COMPANY_NAME.toString()))
            .andExpect(jsonPath("$.agencyName").value(DEFAULT_AGENCY_NAME.toString()))
            .andExpect(jsonPath("$.contractNumber").value(DEFAULT_CONTRACT_NUMBER.toString()))
            .andExpect(jsonPath("$.immatriculation").value(DEFAULT_IMMATRICULATION.toString()))
            .andExpect(jsonPath("$.modeLabel").value(DEFAULT_MODE_LABEL.toString()))
            .andExpect(jsonPath("$.declarationDate").value(DEFAULT_DECLARATION_DATE.toString()))
            .andExpect(jsonPath("$.incidentDate").value(DEFAULT_INCIDENT_DATE.toString()))
            .andExpect(jsonPath("$.decision").value(DEFAULT_DECISION.toString()))
            .andExpect(jsonPath("$.step").value(DEFAULT_STEP))
            .andExpect(jsonPath("$.approvPec").value(DEFAULT_APPROV_PEC.toString()))
            .andExpect(jsonPath("$.expertId").value(DEFAULT_EXPERT_ID.intValue()))
            .andExpect(jsonPath("$.reparateurId").value(DEFAULT_REPARATEUR_ID.intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()))
            .andExpect(jsonPath("$.assignedToId").value(DEFAULT_ASSIGNED_TO_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingViewSinisterPec() throws Exception {
        // Get the viewSinisterPec
        restViewSinisterPecMockMvc.perform(get("/api/view-sinister-pecs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateViewSinisterPec() throws Exception {
        // Initialize the database
        viewSinisterPecRepository.saveAndFlush(viewSinisterPec);
        int databaseSizeBeforeUpdate = viewSinisterPecRepository.findAll().size();

        // Update the viewSinisterPec
        ViewSinisterPec updatedViewSinisterPec = viewSinisterPecRepository.findOne(viewSinisterPec.getId());
        updatedViewSinisterPec
            .gaReference(UPDATED_GA_REFERENCE)
            .companyReference(UPDATED_COMPANY_REFERENCE)
            .companyName(UPDATED_COMPANY_NAME)
            .agencyName(UPDATED_AGENCY_NAME)
            .contractNumber(UPDATED_CONTRACT_NUMBER)
            .immatriculation(UPDATED_IMMATRICULATION)
            .modeLabel(UPDATED_MODE_LABEL)
            .declarationDate(UPDATED_DECLARATION_DATE)
            .incidentDate(UPDATED_INCIDENT_DATE)
            .decision(UPDATED_DECISION)
            .step(UPDATED_STEP)
            .approvPec(UPDATED_APPROV_PEC)
            .expertId(UPDATED_EXPERT_ID)
            .reparateurId(UPDATED_REPARATEUR_ID)
            .userId(UPDATED_USER_ID)
            .assignedToId(UPDATED_ASSIGNED_TO_ID);
        ViewSinisterPecDTO viewSinisterPecDTO = viewSinisterPecMapper.toDto(updatedViewSinisterPec);

        restViewSinisterPecMockMvc.perform(put("/api/view-sinister-pecs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(viewSinisterPecDTO)))
            .andExpect(status().isOk());

        // Validate the ViewSinisterPec in the database
        List<ViewSinisterPec> viewSinisterPecList = viewSinisterPecRepository.findAll();
        assertThat(viewSinisterPecList).hasSize(databaseSizeBeforeUpdate);
        ViewSinisterPec testViewSinisterPec = viewSinisterPecList.get(viewSinisterPecList.size() - 1);
        assertThat(testViewSinisterPec.getGaReference()).isEqualTo(UPDATED_GA_REFERENCE);
        assertThat(testViewSinisterPec.getCompanyReference()).isEqualTo(UPDATED_COMPANY_REFERENCE);
        assertThat(testViewSinisterPec.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
        assertThat(testViewSinisterPec.getAgencyName()).isEqualTo(UPDATED_AGENCY_NAME);
        assertThat(testViewSinisterPec.getContractNumber()).isEqualTo(UPDATED_CONTRACT_NUMBER);
        assertThat(testViewSinisterPec.getImmatriculation()).isEqualTo(UPDATED_IMMATRICULATION);
        assertThat(testViewSinisterPec.getModeLabel()).isEqualTo(UPDATED_MODE_LABEL);
        assertThat(testViewSinisterPec.getDeclarationDate()).isEqualTo(UPDATED_DECLARATION_DATE);
        assertThat(testViewSinisterPec.getIncidentDate()).isEqualTo(UPDATED_INCIDENT_DATE);
        assertThat(testViewSinisterPec.getDecision()).isEqualTo(UPDATED_DECISION);
        assertThat(testViewSinisterPec.getStep()).isEqualTo(UPDATED_STEP);
        assertThat(testViewSinisterPec.getApprovPec()).isEqualTo(UPDATED_APPROV_PEC);
        assertThat(testViewSinisterPec.getExpertId()).isEqualTo(UPDATED_EXPERT_ID);
        assertThat(testViewSinisterPec.getReparateurId()).isEqualTo(UPDATED_REPARATEUR_ID);
        assertThat(testViewSinisterPec.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testViewSinisterPec.getAssignedToId()).isEqualTo(UPDATED_ASSIGNED_TO_ID);

        // Validate the ViewSinisterPec in Elasticsearch
    }

    @Test
    @Transactional
    public void updateNonExistingViewSinisterPec() throws Exception {
        int databaseSizeBeforeUpdate = viewSinisterPecRepository.findAll().size();

        // Create the ViewSinisterPec
        ViewSinisterPecDTO viewSinisterPecDTO = viewSinisterPecMapper.toDto(viewSinisterPec);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restViewSinisterPecMockMvc.perform(put("/api/view-sinister-pecs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(viewSinisterPecDTO)))
            .andExpect(status().isCreated());

        // Validate the ViewSinisterPec in the database
        List<ViewSinisterPec> viewSinisterPecList = viewSinisterPecRepository.findAll();
        assertThat(viewSinisterPecList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteViewSinisterPec() throws Exception {
        // Initialize the database
        viewSinisterPecRepository.saveAndFlush(viewSinisterPec);
        int databaseSizeBeforeDelete = viewSinisterPecRepository.findAll().size();

        // Get the viewSinisterPec
        restViewSinisterPecMockMvc.perform(delete("/api/view-sinister-pecs/{id}", viewSinisterPec.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ViewSinisterPec> viewSinisterPecList = viewSinisterPecRepository.findAll();
        assertThat(viewSinisterPecList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchViewSinisterPec() throws Exception {
        // Initialize the database
        viewSinisterPecRepository.saveAndFlush(viewSinisterPec);
        // Search the viewSinisterPec
        restViewSinisterPecMockMvc.perform(get("/api/_search/view-sinister-pecs?query=id:" + viewSinisterPec.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(viewSinisterPec.getId().intValue())))
            .andExpect(jsonPath("$.[*].gaReference").value(hasItem(DEFAULT_GA_REFERENCE.toString())))
            .andExpect(jsonPath("$.[*].companyReference").value(hasItem(DEFAULT_COMPANY_REFERENCE.toString())))
            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME.toString())))
            .andExpect(jsonPath("$.[*].agencyName").value(hasItem(DEFAULT_AGENCY_NAME.toString())))
            .andExpect(jsonPath("$.[*].contractNumber").value(hasItem(DEFAULT_CONTRACT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].immatriculation").value(hasItem(DEFAULT_IMMATRICULATION.toString())))
            .andExpect(jsonPath("$.[*].modeLabel").value(hasItem(DEFAULT_MODE_LABEL.toString())))
            .andExpect(jsonPath("$.[*].declarationDate").value(hasItem(DEFAULT_DECLARATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].incidentDate").value(hasItem(DEFAULT_INCIDENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].decision").value(hasItem(DEFAULT_DECISION.toString())))
            .andExpect(jsonPath("$.[*].step").value(hasItem(DEFAULT_STEP)))
            .andExpect(jsonPath("$.[*].approvPec").value(hasItem(DEFAULT_APPROV_PEC.toString())))
            .andExpect(jsonPath("$.[*].expertId").value(hasItem(DEFAULT_EXPERT_ID.intValue())))
            .andExpect(jsonPath("$.[*].reparateurId").value(hasItem(DEFAULT_REPARATEUR_ID.intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].assignedToId").value(hasItem(DEFAULT_ASSIGNED_TO_ID.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ViewSinisterPec.class);
        ViewSinisterPec viewSinisterPec1 = new ViewSinisterPec();
        viewSinisterPec1.setId(1L);
        ViewSinisterPec viewSinisterPec2 = new ViewSinisterPec();
        viewSinisterPec2.setId(viewSinisterPec1.getId());
        assertThat(viewSinisterPec1).isEqualTo(viewSinisterPec2);
        viewSinisterPec2.setId(2L);
        assertThat(viewSinisterPec1).isNotEqualTo(viewSinisterPec2);
        viewSinisterPec1.setId(null);
        assertThat(viewSinisterPec1).isNotEqualTo(viewSinisterPec2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ViewSinisterPecDTO.class);
        ViewSinisterPecDTO viewSinisterPecDTO1 = new ViewSinisterPecDTO();
        viewSinisterPecDTO1.setId(1L);
        ViewSinisterPecDTO viewSinisterPecDTO2 = new ViewSinisterPecDTO();
        assertThat(viewSinisterPecDTO1).isNotEqualTo(viewSinisterPecDTO2);
        viewSinisterPecDTO2.setId(viewSinisterPecDTO1.getId());
        assertThat(viewSinisterPecDTO1).isEqualTo(viewSinisterPecDTO2);
        viewSinisterPecDTO2.setId(2L);
        assertThat(viewSinisterPecDTO1).isNotEqualTo(viewSinisterPecDTO2);
        viewSinisterPecDTO1.setId(null);
        assertThat(viewSinisterPecDTO1).isNotEqualTo(viewSinisterPecDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(viewSinisterPecMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(viewSinisterPecMapper.fromId(null)).isNull();
    }
}
