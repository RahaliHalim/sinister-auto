package com.gaconnecte.auxilium.web.rest;

import com.gaconnecte.auxilium.AuxiliumApp;

import com.gaconnecte.auxilium.domain.StatusPec;
import com.gaconnecte.auxilium.repository.StatusPecRepository;
import com.gaconnecte.auxilium.service.StatusPecService;
import com.gaconnecte.auxilium.repository.search.StatusPecSearchRepository;
import com.gaconnecte.auxilium.service.dto.StatusPecDTO;
import com.gaconnecte.auxilium.service.mapper.StatusPecMapper;
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
 * Test class for the StatusPecResource REST controller.
 *
 * @see StatusPecResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuxiliumApp.class)
public class StatusPecResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_HAS_REASON = false;
    private static final Boolean UPDATED_HAS_REASON = true;

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private StatusPecRepository statusPecRepository;

    @Autowired
    private StatusPecMapper statusPecMapper;

    @Autowired
    private StatusPecService statusPecService;

    @Autowired
    private StatusPecSearchRepository statusPecSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restStatusPecMockMvc;

    private StatusPec statusPec;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        StatusPecResource statusPecResource = new StatusPecResource(statusPecService);
        this.restStatusPecMockMvc = MockMvcBuilders.standaloneSetup(statusPecResource)
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
    public static StatusPec createEntity(EntityManager em) {
        StatusPec statusPec = new StatusPec()
            .code(DEFAULT_CODE)
            .label(DEFAULT_LABEL)
            .hasReason(DEFAULT_HAS_REASON)
            .active(DEFAULT_ACTIVE);
        return statusPec;
    }

    @Before
    public void initTest() {
        statusPecSearchRepository.deleteAll();
        statusPec = createEntity(em);
    }

    @Test
    @Transactional
    public void createStatusPec() throws Exception {
        int databaseSizeBeforeCreate = statusPecRepository.findAll().size();

        // Create the StatusPec
        StatusPecDTO statusPecDTO = statusPecMapper.toDto(statusPec);
        restStatusPecMockMvc.perform(post("/api/status-pecs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(statusPecDTO)))
            .andExpect(status().isCreated());

        // Validate the StatusPec in the database
        List<StatusPec> statusPecList = statusPecRepository.findAll();
        assertThat(statusPecList).hasSize(databaseSizeBeforeCreate + 1);
        StatusPec testStatusPec = statusPecList.get(statusPecList.size() - 1);
        assertThat(testStatusPec.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testStatusPec.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testStatusPec.isHasReason()).isEqualTo(DEFAULT_HAS_REASON);
        assertThat(testStatusPec.isActive()).isEqualTo(DEFAULT_ACTIVE);

        // Validate the StatusPec in Elasticsearch
        StatusPec statusPecEs = statusPecSearchRepository.findOne(testStatusPec.getId());
        assertThat(statusPecEs).isEqualToComparingFieldByField(testStatusPec);
    }

    @Test
    @Transactional
    public void createStatusPecWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = statusPecRepository.findAll().size();

        // Create the StatusPec with an existing ID
        statusPec.setId(1L);
        StatusPecDTO statusPecDTO = statusPecMapper.toDto(statusPec);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStatusPecMockMvc.perform(post("/api/status-pecs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(statusPecDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<StatusPec> statusPecList = statusPecRepository.findAll();
        assertThat(statusPecList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllStatusPecs() throws Exception {
        // Initialize the database
        statusPecRepository.saveAndFlush(statusPec);

        // Get all the statusPecList
        restStatusPecMockMvc.perform(get("/api/status-pecs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(statusPec.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].hasReason").value(hasItem(DEFAULT_HAS_REASON.booleanValue())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void getStatusPec() throws Exception {
        // Initialize the database
        statusPecRepository.saveAndFlush(statusPec);

        // Get the statusPec
        restStatusPecMockMvc.perform(get("/api/status-pecs/{id}", statusPec.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(statusPec.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()))
            .andExpect(jsonPath("$.hasReason").value(DEFAULT_HAS_REASON.booleanValue()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingStatusPec() throws Exception {
        // Get the statusPec
        restStatusPecMockMvc.perform(get("/api/status-pecs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStatusPec() throws Exception {
        // Initialize the database
        statusPecRepository.saveAndFlush(statusPec);
        statusPecSearchRepository.save(statusPec);
        int databaseSizeBeforeUpdate = statusPecRepository.findAll().size();

        // Update the statusPec
        StatusPec updatedStatusPec = statusPecRepository.findOne(statusPec.getId());
        updatedStatusPec
            .code(UPDATED_CODE)
            .label(UPDATED_LABEL)
            .hasReason(UPDATED_HAS_REASON)
            .active(UPDATED_ACTIVE);
        StatusPecDTO statusPecDTO = statusPecMapper.toDto(updatedStatusPec);

        restStatusPecMockMvc.perform(put("/api/status-pecs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(statusPecDTO)))
            .andExpect(status().isOk());

        // Validate the StatusPec in the database
        List<StatusPec> statusPecList = statusPecRepository.findAll();
        assertThat(statusPecList).hasSize(databaseSizeBeforeUpdate);
        StatusPec testStatusPec = statusPecList.get(statusPecList.size() - 1);
        assertThat(testStatusPec.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testStatusPec.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testStatusPec.isHasReason()).isEqualTo(UPDATED_HAS_REASON);
        assertThat(testStatusPec.isActive()).isEqualTo(UPDATED_ACTIVE);

        // Validate the StatusPec in Elasticsearch
        StatusPec statusPecEs = statusPecSearchRepository.findOne(testStatusPec.getId());
        assertThat(statusPecEs).isEqualToComparingFieldByField(testStatusPec);
    }

    @Test
    @Transactional
    public void updateNonExistingStatusPec() throws Exception {
        int databaseSizeBeforeUpdate = statusPecRepository.findAll().size();

        // Create the StatusPec
        StatusPecDTO statusPecDTO = statusPecMapper.toDto(statusPec);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restStatusPecMockMvc.perform(put("/api/status-pecs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(statusPecDTO)))
            .andExpect(status().isCreated());

        // Validate the StatusPec in the database
        List<StatusPec> statusPecList = statusPecRepository.findAll();
        assertThat(statusPecList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteStatusPec() throws Exception {
        // Initialize the database
        statusPecRepository.saveAndFlush(statusPec);
        statusPecSearchRepository.save(statusPec);
        int databaseSizeBeforeDelete = statusPecRepository.findAll().size();

        // Get the statusPec
        restStatusPecMockMvc.perform(delete("/api/status-pecs/{id}", statusPec.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean statusPecExistsInEs = statusPecSearchRepository.exists(statusPec.getId());
        assertThat(statusPecExistsInEs).isFalse();

        // Validate the database is empty
        List<StatusPec> statusPecList = statusPecRepository.findAll();
        assertThat(statusPecList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchStatusPec() throws Exception {
        // Initialize the database
        statusPecRepository.saveAndFlush(statusPec);
        statusPecSearchRepository.save(statusPec);

        // Search the statusPec
        restStatusPecMockMvc.perform(get("/api/_search/status-pecs?query=id:" + statusPec.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(statusPec.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].hasReason").value(hasItem(DEFAULT_HAS_REASON.booleanValue())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StatusPec.class);
        StatusPec statusPec1 = new StatusPec();
        statusPec1.setId(1L);
        StatusPec statusPec2 = new StatusPec();
        statusPec2.setId(statusPec1.getId());
        assertThat(statusPec1).isEqualTo(statusPec2);
        statusPec2.setId(2L);
        assertThat(statusPec1).isNotEqualTo(statusPec2);
        statusPec1.setId(null);
        assertThat(statusPec1).isNotEqualTo(statusPec2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StatusPecDTO.class);
        StatusPecDTO statusPecDTO1 = new StatusPecDTO();
        statusPecDTO1.setId(1L);
        StatusPecDTO statusPecDTO2 = new StatusPecDTO();
        assertThat(statusPecDTO1).isNotEqualTo(statusPecDTO2);
        statusPecDTO2.setId(statusPecDTO1.getId());
        assertThat(statusPecDTO1).isEqualTo(statusPecDTO2);
        statusPecDTO2.setId(2L);
        assertThat(statusPecDTO1).isNotEqualTo(statusPecDTO2);
        statusPecDTO1.setId(null);
        assertThat(statusPecDTO1).isNotEqualTo(statusPecDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(statusPecMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(statusPecMapper.fromId(null)).isNull();
    }
}
