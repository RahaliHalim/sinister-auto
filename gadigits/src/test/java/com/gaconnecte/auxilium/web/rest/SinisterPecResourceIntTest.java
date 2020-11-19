package com.gaconnecte.auxilium.web.rest;

import com.gaconnecte.auxilium.AuxiliumApp;

import com.gaconnecte.auxilium.domain.SinisterPec;
import com.gaconnecte.auxilium.repository.SinisterPecRepository;
import com.gaconnecte.auxilium.service.SinisterPecService;
import com.gaconnecte.auxilium.service.dto.SinisterPecDTO;
import com.gaconnecte.auxilium.service.mapper.SinisterPecMapper;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SinisterPecResource REST controller.
 *
 * @see SinisterPecResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuxiliumApp.class)
public class SinisterPecResourceIntTest {

    private static final String DEFAULT_REFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY_REFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_REFERENCE = "BBBBBBBBBB";

    private static final LocalDateTime DEFAULT_DECLARATION_DATE = LocalDateTime.now();
    private static final LocalDate UPDATED_DECLARATION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_VEHICLE_NUMBER = 1;
    private static final Integer UPDATED_VEHICLE_NUMBER = 2;

    private static final Boolean DEFAULT_RESPONSABILITY_RATE = false;
    private static final Boolean UPDATED_RESPONSABILITY_RATE = true;

    private static final BigDecimal DEFAULT_INSURED_CAPITAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_INSURED_CAPITAL = new BigDecimal(2);

    private static final BigDecimal DEFAULT_REMAINING_CAPITAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_REMAINING_CAPITAL = new BigDecimal(2);

    private static final BigDecimal DEFAULT_FRANCHISE = new BigDecimal(1);
    private static final BigDecimal UPDATED_FRANCHISE = new BigDecimal(2);

    private static final Boolean DEFAULT_DRIVER_OR_INSURED = false;
    private static final Boolean UPDATED_DRIVER_OR_INSURED = true;

    private static final String DEFAULT_DRIVER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DRIVER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DRIVER_LICENSE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_DRIVER_LICENSE_NUMBER = "BBBBBBBBBB";

    @Autowired
    private SinisterPecRepository sinisterPecRepository;

    @Autowired
    private SinisterPecMapper sinisterPecMapper;

    @Autowired
    private SinisterPecService sinisterPecService;

    //@Autowired
   //private SinisterPecSearchRepository sinisterPecSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSinisterPecMockMvc;

    private SinisterPec sinisterPec;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SinisterPecResource sinisterPecResource = null;
        this.restSinisterPecMockMvc = MockMvcBuilders.standaloneSetup(sinisterPecResource)
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
    public static SinisterPec createEntity(EntityManager em) {
        SinisterPec sinisterPec = new SinisterPec()
            .reference(DEFAULT_REFERENCE)
            .companyReference(DEFAULT_COMPANY_REFERENCE)
            .declarationDate(DEFAULT_DECLARATION_DATE)
            .vehicleNumber(DEFAULT_VEHICLE_NUMBER)
            .responsabilityRate(DEFAULT_RESPONSABILITY_RATE)
            //.insuredCapital(DEFAULT_INSURED_CAPITAL)
            .remainingCapital(DEFAULT_REMAINING_CAPITAL)
            //.franchise(DEFAULT_FRANCHISE)
            .driverOrInsured(DEFAULT_DRIVER_OR_INSURED)
            .driverName(DEFAULT_DRIVER_NAME)
            .driverLicenseNumber(DEFAULT_DRIVER_LICENSE_NUMBER);
        return sinisterPec;
    }

    @Before
    public void initTest() {
      //  sinisterPecSearchRepository.deleteAll();
        sinisterPec = createEntity(em);
    }

    @Test
    @Transactional
    public void createSinisterPec() throws Exception {
        int databaseSizeBeforeCreate = sinisterPecRepository.findAll().size();

        // Create the SinisterPec
        SinisterPecDTO sinisterPecDTO = sinisterPecMapper.toDto(sinisterPec);
        restSinisterPecMockMvc.perform(post("/api/sinister-pecs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sinisterPecDTO)))
            .andExpect(status().isCreated());

        // Validate the SinisterPec in the database
        List<SinisterPec> sinisterPecList = sinisterPecRepository.findAll();
        assertThat(sinisterPecList).hasSize(databaseSizeBeforeCreate + 1);
        SinisterPec testSinisterPec = sinisterPecList.get(sinisterPecList.size() - 1);
        assertThat(testSinisterPec.getReference()).isEqualTo(DEFAULT_REFERENCE);
        assertThat(testSinisterPec.getCompanyReference()).isEqualTo(DEFAULT_COMPANY_REFERENCE);
        assertThat(testSinisterPec.getDeclarationDate()).isEqualTo(DEFAULT_DECLARATION_DATE);
        assertThat(testSinisterPec.getVehicleNumber()).isEqualTo(DEFAULT_VEHICLE_NUMBER);
        assertThat(testSinisterPec.isResponsabilityRate()).isEqualTo(DEFAULT_RESPONSABILITY_RATE);
        //assertThat(testSinisterPec.getInsuredCapital()).isEqualTo(DEFAULT_INSURED_CAPITAL);
        assertThat(testSinisterPec.getRemainingCapital()).isEqualTo(DEFAULT_REMAINING_CAPITAL);
        //assertThat(testSinisterPec.getFranchise()).isEqualTo(DEFAULT_FRANCHISE);
        assertThat(testSinisterPec.isDriverOrInsured()).isEqualTo(DEFAULT_DRIVER_OR_INSURED);
        assertThat(testSinisterPec.getDriverName()).isEqualTo(DEFAULT_DRIVER_NAME);
        assertThat(testSinisterPec.getDriverLicenseNumber()).isEqualTo(DEFAULT_DRIVER_LICENSE_NUMBER);

        // Validate the SinisterPec in Elasticsearch
       // SinisterPec sinisterPecEs = sinisterPecSearchRepository.findOne(testSinisterPec.getId());
       // assertThat(sinisterPecEs).isEqualToComparingFieldByField(testSinisterPec);
    }

    @Test
    @Transactional
    public void createSinisterPecWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sinisterPecRepository.findAll().size();

        // Create the SinisterPec with an existing ID
        sinisterPec.setId(1L);
        SinisterPecDTO sinisterPecDTO = sinisterPecMapper.toDto(sinisterPec);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSinisterPecMockMvc.perform(post("/api/sinister-pecs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sinisterPecDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<SinisterPec> sinisterPecList = sinisterPecRepository.findAll();
        assertThat(sinisterPecList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSinisterPecs() throws Exception {
        // Initialize the database
        sinisterPecRepository.saveAndFlush(sinisterPec);

        // Get all the sinisterPecList
        restSinisterPecMockMvc.perform(get("/api/sinister-pecs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sinisterPec.getId().intValue())))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE.toString())))
            .andExpect(jsonPath("$.[*].companyReference").value(hasItem(DEFAULT_COMPANY_REFERENCE.toString())))
           //.andExpect(jsonPath("$.[*].declarationDate").value(hasItem(DEFAULT_DECLARATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].vehicleNumber").value(hasItem(DEFAULT_VEHICLE_NUMBER)))
            .andExpect(jsonPath("$.[*].responsabilityRate").value(hasItem(DEFAULT_RESPONSABILITY_RATE.booleanValue())))
            //.andExpect(jsonPath("$.[*].insuredCapital").value(hasItem(DEFAULT_INSURED_CAPITAL.intValue())))
            .andExpect(jsonPath("$.[*].remainingCapital").value(hasItem(DEFAULT_REMAINING_CAPITAL.intValue())))
            //.andExpect(jsonPath("$.[*].franchise").value(hasItem(DEFAULT_FRANCHISE.intValue())))
            .andExpect(jsonPath("$.[*].driverOrInsured").value(hasItem(DEFAULT_DRIVER_OR_INSURED.booleanValue())))
            .andExpect(jsonPath("$.[*].driverName").value(hasItem(DEFAULT_DRIVER_NAME.toString())))
            .andExpect(jsonPath("$.[*].driverLicenseNumber").value(hasItem(DEFAULT_DRIVER_LICENSE_NUMBER.toString())));
    }

    @Test
    @Transactional
    public void getSinisterPec() throws Exception {
        // Initialize the database
        sinisterPecRepository.saveAndFlush(sinisterPec);

        // Get the sinisterPec
        restSinisterPecMockMvc.perform(get("/api/sinister-pecs/{id}", sinisterPec.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sinisterPec.getId().intValue()))
            .andExpect(jsonPath("$.reference").value(DEFAULT_REFERENCE.toString()))
            .andExpect(jsonPath("$.companyReference").value(DEFAULT_COMPANY_REFERENCE.toString()))
            .andExpect(jsonPath("$.declarationDate").value(DEFAULT_DECLARATION_DATE.toString()))
            .andExpect(jsonPath("$.vehicleNumber").value(DEFAULT_VEHICLE_NUMBER))
            .andExpect(jsonPath("$.responsabilityRate").value(DEFAULT_RESPONSABILITY_RATE.booleanValue()))
           // .andExpect(jsonPath("$.insuredCapital").value(DEFAULT_INSURED_CAPITAL.intValue()))
            .andExpect(jsonPath("$.remainingCapital").value(DEFAULT_REMAINING_CAPITAL.intValue()))
            //.andExpect(jsonPath("$.franchise").value(DEFAULT_FRANCHISE.intValue()))
            .andExpect(jsonPath("$.driverOrInsured").value(DEFAULT_DRIVER_OR_INSURED.booleanValue()))
            .andExpect(jsonPath("$.driverName").value(DEFAULT_DRIVER_NAME.toString()))
            .andExpect(jsonPath("$.driverLicenseNumber").value(DEFAULT_DRIVER_LICENSE_NUMBER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSinisterPec() throws Exception {
        // Get the sinisterPec
        restSinisterPecMockMvc.perform(get("/api/sinister-pecs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSinisterPec() throws Exception {
        // Initialize the database
        sinisterPecRepository.saveAndFlush(sinisterPec);
        //sinisterPecSearchRepository.save(sinisterPec);
        int databaseSizeBeforeUpdate = sinisterPecRepository.findAll().size();

        // Update the sinisterPec
        SinisterPec updatedSinisterPec = sinisterPecRepository.findOne(sinisterPec.getId());
        updatedSinisterPec
            .reference(UPDATED_REFERENCE)
            .companyReference(UPDATED_COMPANY_REFERENCE)
            //.declarationDate(UPDATED_DECLARATION_DATE)
            .vehicleNumber(UPDATED_VEHICLE_NUMBER)
            .responsabilityRate(UPDATED_RESPONSABILITY_RATE)
            //.insuredCapital(UPDATED_INSURED_CAPITAL)
            .remainingCapital(UPDATED_REMAINING_CAPITAL)
            //.franchise(UPDATED_FRANCHISE)
            .driverOrInsured(UPDATED_DRIVER_OR_INSURED)
            .driverName(UPDATED_DRIVER_NAME)
            .driverLicenseNumber(UPDATED_DRIVER_LICENSE_NUMBER);
        SinisterPecDTO sinisterPecDTO = sinisterPecMapper.toDto(updatedSinisterPec);

        restSinisterPecMockMvc.perform(put("/api/sinister-pecs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sinisterPecDTO)))
            .andExpect(status().isOk());

        // Validate the SinisterPec in the database
        List<SinisterPec> sinisterPecList = sinisterPecRepository.findAll();
        assertThat(sinisterPecList).hasSize(databaseSizeBeforeUpdate);
        SinisterPec testSinisterPec = sinisterPecList.get(sinisterPecList.size() - 1);
        assertThat(testSinisterPec.getReference()).isEqualTo(UPDATED_REFERENCE);
        assertThat(testSinisterPec.getCompanyReference()).isEqualTo(UPDATED_COMPANY_REFERENCE);
        assertThat(testSinisterPec.getDeclarationDate()).isEqualTo(UPDATED_DECLARATION_DATE);
        assertThat(testSinisterPec.getVehicleNumber()).isEqualTo(UPDATED_VEHICLE_NUMBER);
        assertThat(testSinisterPec.isResponsabilityRate()).isEqualTo(UPDATED_RESPONSABILITY_RATE);
        //assertThat(testSinisterPec.getInsuredCapital()).isEqualTo(UPDATED_INSURED_CAPITAL);
        assertThat(testSinisterPec.getRemainingCapital()).isEqualTo(UPDATED_REMAINING_CAPITAL);
        //assertThat(testSinisterPec.getFranchise()).isEqualTo(UPDATED_FRANCHISE);
        assertThat(testSinisterPec.isDriverOrInsured()).isEqualTo(UPDATED_DRIVER_OR_INSURED);
        assertThat(testSinisterPec.getDriverName()).isEqualTo(UPDATED_DRIVER_NAME);
        assertThat(testSinisterPec.getDriverLicenseNumber()).isEqualTo(UPDATED_DRIVER_LICENSE_NUMBER);

        // Validate the SinisterPec in Elasticsearch
       // SinisterPec sinisterPecEs = sinisterPecSearchRepository.findOne(testSinisterPec.getId());
       // assertThat(null).isEqualToComparingFieldByField(testSinisterPec);
    }

    @Test
    @Transactional
    public void updateNonExistingSinisterPec() throws Exception {
        int databaseSizeBeforeUpdate = sinisterPecRepository.findAll().size();

        // Create the SinisterPec
        SinisterPecDTO sinisterPecDTO = sinisterPecMapper.toDto(sinisterPec);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSinisterPecMockMvc.perform(put("/api/sinister-pecs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sinisterPecDTO)))
            .andExpect(status().isCreated());

        // Validate the SinisterPec in the database
        List<SinisterPec> sinisterPecList = sinisterPecRepository.findAll();
        assertThat(sinisterPecList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSinisterPec() throws Exception {
        // Initialize the database
        sinisterPecRepository.saveAndFlush(sinisterPec);
       // sinisterPecSearchRepository.save(sinisterPec);
        int databaseSizeBeforeDelete = sinisterPecRepository.findAll().size();

        // Get the sinisterPec
        restSinisterPecMockMvc.perform(delete("/api/sinister-pecs/{id}", sinisterPec.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
      //  boolean sinisterPecExistsInEs = sinisterPecSearchRepository.exists(sinisterPec.getId());
       // assertThat(sinisterPecExistsInEs).isFalse();

        // Validate the database is empty
        List<SinisterPec> sinisterPecList = sinisterPecRepository.findAll();
        assertThat(sinisterPecList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchSinisterPec() throws Exception {
        // Initialize the database
        sinisterPecRepository.saveAndFlush(sinisterPec);
       // sinisterPecSearchRepository.save(sinisterPec);

        // Search the sinisterPec
        restSinisterPecMockMvc.perform(get("/api/_search/sinister-pecs?query=id:" + sinisterPec.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sinisterPec.getId().intValue())))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE.toString())))
            .andExpect(jsonPath("$.[*].companyReference").value(hasItem(DEFAULT_COMPANY_REFERENCE.toString())))
            .andExpect(jsonPath("$.[*].declarationDate").value(hasItem(DEFAULT_DECLARATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].vehicleNumber").value(hasItem(DEFAULT_VEHICLE_NUMBER)))
            .andExpect(jsonPath("$.[*].responsabilityRate").value(hasItem(DEFAULT_RESPONSABILITY_RATE.booleanValue())))
           // .andExpect(jsonPath("$.[*].insuredCapital").value(hasItem(DEFAULT_INSURED_CAPITAL.intValue())))
            .andExpect(jsonPath("$.[*].remainingCapital").value(hasItem(DEFAULT_REMAINING_CAPITAL.intValue())))
            //.andExpect(jsonPath("$.[*].franchise").value(hasItem(DEFAULT_FRANCHISE.intValue())))
            .andExpect(jsonPath("$.[*].driverOrInsured").value(hasItem(DEFAULT_DRIVER_OR_INSURED.booleanValue())))
            .andExpect(jsonPath("$.[*].driverName").value(hasItem(DEFAULT_DRIVER_NAME.toString())))
            .andExpect(jsonPath("$.[*].driverLicenseNumber").value(hasItem(DEFAULT_DRIVER_LICENSE_NUMBER.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SinisterPec.class);
        SinisterPec sinisterPec1 = new SinisterPec();
        sinisterPec1.setId(1L);
        SinisterPec sinisterPec2 = new SinisterPec();
        sinisterPec2.setId(sinisterPec1.getId());
        assertThat(sinisterPec1).isEqualTo(sinisterPec2);
        sinisterPec2.setId(2L);
        assertThat(sinisterPec1).isNotEqualTo(sinisterPec2);
        sinisterPec1.setId(null);
        assertThat(sinisterPec1).isNotEqualTo(sinisterPec2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SinisterPecDTO.class);
        SinisterPecDTO sinisterPecDTO1 = new SinisterPecDTO();
        sinisterPecDTO1.setId(1L);
        SinisterPecDTO sinisterPecDTO2 = new SinisterPecDTO();
        assertThat(sinisterPecDTO1).isNotEqualTo(sinisterPecDTO2);
        sinisterPecDTO2.setId(sinisterPecDTO1.getId());
        assertThat(sinisterPecDTO1).isEqualTo(sinisterPecDTO2);
        sinisterPecDTO2.setId(2L);
        assertThat(sinisterPecDTO1).isNotEqualTo(sinisterPecDTO2);
        sinisterPecDTO1.setId(null);
        assertThat(sinisterPecDTO1).isNotEqualTo(sinisterPecDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(sinisterPecMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(sinisterPecMapper.fromId(null)).isNull();
    }
}
