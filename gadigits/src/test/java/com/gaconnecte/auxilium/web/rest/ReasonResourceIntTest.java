package com.gaconnecte.auxilium.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import javax.persistence.EntityManager;

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

import com.gaconnecte.auxilium.AuxiliumApp;
import com.gaconnecte.auxilium.domain.Reason;
import com.gaconnecte.auxilium.domain.enumeration.ResponsibleEnum;
import com.gaconnecte.auxilium.repository.ReasonRepository;
import com.gaconnecte.auxilium.service.ReasonService;
import com.gaconnecte.auxilium.service.dto.ReasonDTO;
import com.gaconnecte.auxilium.service.mapper.ReasonMapper;
import com.gaconnecte.auxilium.web.rest.errors.ExceptionTranslator;
/**
 * Test class for the ReasonResource REST controller.
 *
 * @see ReasonResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuxiliumApp.class)
public class ReasonResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final ResponsibleEnum DEFAULT_RESPONSIBLE = ResponsibleEnum.ga;
    private static final ResponsibleEnum UPDATED_RESPONSIBLE = ResponsibleEnum.agent;

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private ReasonRepository reasonRepository;

    @Autowired
    private ReasonMapper reasonMapper;

    @Autowired
    private ReasonService reasonService;

   // @Autowired
  //  private ReasonSearchRepository reasonSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restReasonMockMvc;

    private Reason reason;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ReasonResource reasonResource = new ReasonResource(reasonService);
        this.restReasonMockMvc = MockMvcBuilders.standaloneSetup(reasonResource)
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
    public static Reason createEntity(EntityManager em) {
        Reason reason = new Reason()
            .code(DEFAULT_CODE)
            .label(DEFAULT_LABEL)
            .description(DEFAULT_DESCRIPTION)
            .responsible(DEFAULT_RESPONSIBLE)
            .active(DEFAULT_ACTIVE);
        return reason;
    }

    @Before
    public void initTest() {
       // reasonSearchRepository.deleteAll();
        reason = createEntity(em);
    }

    @Test
    @Transactional
    public void createReason() throws Exception {
        int databaseSizeBeforeCreate = reasonRepository.findAll().size();

        // Create the Reason
        ReasonDTO reasonDTO = reasonMapper.toDto(reason);
        restReasonMockMvc.perform(post("/api/reasons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reasonDTO)))
            .andExpect(status().isCreated());

        // Validate the Reason in the database
        List<Reason> reasonList = reasonRepository.findAll();
        assertThat(reasonList).hasSize(databaseSizeBeforeCreate + 1);
        Reason testReason = reasonList.get(reasonList.size() - 1);
        assertThat(testReason.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testReason.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testReason.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testReason.getResponsible()).isEqualTo(DEFAULT_RESPONSIBLE);
        assertThat(testReason.isActive()).isEqualTo(DEFAULT_ACTIVE);

        // Validate the Reason in Elasticsearch
       // Reason reasonEs = reasonSearchRepository.findOne(testReason.getId());
      //  assertThat(reasonEs).isEqualToComparingFieldByField(testReason);
    }

    @Test
    @Transactional
    public void createReasonWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = reasonRepository.findAll().size();

        // Create the Reason with an existing ID
        reason.setId(1L);
        ReasonDTO reasonDTO = reasonMapper.toDto(reason);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReasonMockMvc.perform(post("/api/reasons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reasonDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Reason> reasonList = reasonRepository.findAll();
        assertThat(reasonList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllReasons() throws Exception {
        // Initialize the database
        reasonRepository.saveAndFlush(reason);

        // Get all the reasonList
        restReasonMockMvc.perform(get("/api/reasons?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reason.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].responsible").value(hasItem(DEFAULT_RESPONSIBLE.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void getReason() throws Exception {
        // Initialize the database
        reasonRepository.saveAndFlush(reason);

        // Get the reason
        restReasonMockMvc.perform(get("/api/reasons/{id}", reason.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(reason.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.responsible").value(DEFAULT_RESPONSIBLE.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingReason() throws Exception {
        // Get the reason
        restReasonMockMvc.perform(get("/api/reasons/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReason() throws Exception {
        // Initialize the database
        reasonRepository.saveAndFlush(reason);
     //   reasonSearchRepository.save(reason);
        int databaseSizeBeforeUpdate = reasonRepository.findAll().size();

        // Update the reason
        Reason updatedReason = reasonRepository.findOne(reason.getId());
        updatedReason
            .code(UPDATED_CODE)
            .label(UPDATED_LABEL)
            .description(UPDATED_DESCRIPTION)
            .responsible(UPDATED_RESPONSIBLE)
            .active(UPDATED_ACTIVE);
        ReasonDTO reasonDTO = reasonMapper.toDto(updatedReason);

        restReasonMockMvc.perform(put("/api/reasons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reasonDTO)))
            .andExpect(status().isOk());

        // Validate the Reason in the database
        List<Reason> reasonList = reasonRepository.findAll();
        assertThat(reasonList).hasSize(databaseSizeBeforeUpdate);
        Reason testReason = reasonList.get(reasonList.size() - 1);
        assertThat(testReason.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testReason.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testReason.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testReason.getResponsible()).isEqualTo(UPDATED_RESPONSIBLE);
        assertThat(testReason.isActive()).isEqualTo(UPDATED_ACTIVE);

        // Validate the Reason in Elasticsearch
        //Reason reasonEs = reasonSearchRepository.findOne(testReason.getId());
        //assertThat(reasonEs).isEqualToComparingFieldByField(testReason);
    }

    @Test
    @Transactional
    public void updateNonExistingReason() throws Exception {
        int databaseSizeBeforeUpdate = reasonRepository.findAll().size();

        // Create the Reason
        ReasonDTO reasonDTO = reasonMapper.toDto(reason);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restReasonMockMvc.perform(put("/api/reasons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reasonDTO)))
            .andExpect(status().isCreated());

        // Validate the Reason in the database
        List<Reason> reasonList = reasonRepository.findAll();
        assertThat(reasonList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteReason() throws Exception {
        // Initialize the database
        reasonRepository.saveAndFlush(reason);
        //reasonSearchRepository.save(reason);
        int databaseSizeBeforeDelete = reasonRepository.findAll().size();

        // Get the reason
        restReasonMockMvc.perform(delete("/api/reasons/{id}", reason.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
       // boolean reasonExistsInEs = reasonSearchRepository.exists(reason.getId());
       // assertThat(reasonExistsInEs).isFalse();

        // Validate the database is empty
        List<Reason> reasonList = reasonRepository.findAll();
        assertThat(reasonList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchReason() throws Exception {
        // Initialize the database
        reasonRepository.saveAndFlush(reason);
     //   reasonSearchRepository.save(reason);

        // Search the reason
        restReasonMockMvc.perform(get("/api/_search/reasons?query=id:" + reason.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reason.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].responsible").value(hasItem(DEFAULT_RESPONSIBLE.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Reason.class);
        Reason reason1 = new Reason();
        reason1.setId(1L);
        Reason reason2 = new Reason();
        reason2.setId(reason1.getId());
        assertThat(reason1).isEqualTo(reason2);
        reason2.setId(2L);
        assertThat(reason1).isNotEqualTo(reason2);
        reason1.setId(null);
        assertThat(reason1).isNotEqualTo(reason2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReasonDTO.class);
        ReasonDTO reasonDTO1 = new ReasonDTO();
        reasonDTO1.setId(1L);
        ReasonDTO reasonDTO2 = new ReasonDTO();
        assertThat(reasonDTO1).isNotEqualTo(reasonDTO2);
        reasonDTO2.setId(reasonDTO1.getId());
        assertThat(reasonDTO1).isEqualTo(reasonDTO2);
        reasonDTO2.setId(2L);
        assertThat(reasonDTO1).isNotEqualTo(reasonDTO2);
        reasonDTO1.setId(null);
        assertThat(reasonDTO1).isNotEqualTo(reasonDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(reasonMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(reasonMapper.fromId(null)).isNull();
    }
}
