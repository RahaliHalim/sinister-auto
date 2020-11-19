package com.gaconnecte.auxilium.web.rest;

import com.gaconnecte.auxilium.AuxiliumApp;

import com.gaconnecte.auxilium.domain.RaisonPec;
import com.gaconnecte.auxilium.repository.RaisonPecRepository;
import com.gaconnecte.auxilium.service.RaisonPecService;
import com.gaconnecte.auxilium.repository.search.RaisonPecSearchRepository;
import com.gaconnecte.auxilium.service.dto.RaisonPecDTO;
import com.gaconnecte.auxilium.service.mapper.RaisonPecMapper;
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

import com.gaconnecte.auxilium.domain.enumeration.ResponsibleEnum;
/**
 * Test class for the RaisonPecResource REST controller.
 *
 * @see RaisonPecResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuxiliumApp.class)
public class RaisonPecResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final LocalDate DEFAULT_CREATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_CREATION_USER_ID = 1L;
    private static final Long UPDATED_CREATION_USER_ID = 2L;

    private static final ResponsibleEnum DEFAULT_RESPONSIBLE = ResponsibleEnum.ga;
    private static final ResponsibleEnum UPDATED_RESPONSIBLE = ResponsibleEnum.agent;

    @Autowired
    private RaisonPecRepository raisonPecRepository;

    @Autowired
    private RaisonPecMapper raisonPecMapper;

    @Autowired
    private RaisonPecService raisonPecService;

    @Autowired
    private RaisonPecSearchRepository raisonPecSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRaisonPecMockMvc;

    private RaisonPec raisonPec;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RaisonPecResource raisonPecResource = new RaisonPecResource(raisonPecService);
        this.restRaisonPecMockMvc = MockMvcBuilders.standaloneSetup(raisonPecResource)
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
    public static RaisonPec createEntity(EntityManager em) {
        RaisonPec raisonPec = new RaisonPec()
            .code(DEFAULT_CODE)
            .label(DEFAULT_LABEL)
            .active(DEFAULT_ACTIVE)
            .creationDate(DEFAULT_CREATION_DATE)
            .creationUserId(DEFAULT_CREATION_USER_ID)
            .responsible(DEFAULT_RESPONSIBLE);
        return raisonPec;
    }

    @Before
    public void initTest() {
        raisonPecSearchRepository.deleteAll();
        raisonPec = createEntity(em);
    }

    @Test
    @Transactional
    public void createRaisonPec() throws Exception {
        int databaseSizeBeforeCreate = raisonPecRepository.findAll().size();

        // Create the RaisonPec
        RaisonPecDTO raisonPecDTO = raisonPecMapper.toDto(raisonPec);
        restRaisonPecMockMvc.perform(post("/api/raison-pecs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(raisonPecDTO)))
            .andExpect(status().isCreated());

        // Validate the RaisonPec in the database
        List<RaisonPec> raisonPecList = raisonPecRepository.findAll();
        assertThat(raisonPecList).hasSize(databaseSizeBeforeCreate + 1);
        RaisonPec testRaisonPec = raisonPecList.get(raisonPecList.size() - 1);
        assertThat(testRaisonPec.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testRaisonPec.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testRaisonPec.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testRaisonPec.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testRaisonPec.getCreationUserId()).isEqualTo(DEFAULT_CREATION_USER_ID);
        assertThat(testRaisonPec.getResponsible()).isEqualTo(DEFAULT_RESPONSIBLE);

        // Validate the RaisonPec in Elasticsearch
        RaisonPec raisonPecEs = raisonPecSearchRepository.findOne(testRaisonPec.getId());
        assertThat(raisonPecEs).isEqualToComparingFieldByField(testRaisonPec);
    }

    @Test
    @Transactional
    public void createRaisonPecWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = raisonPecRepository.findAll().size();

        // Create the RaisonPec with an existing ID
        raisonPec.setId(1L);
        RaisonPecDTO raisonPecDTO = raisonPecMapper.toDto(raisonPec);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRaisonPecMockMvc.perform(post("/api/raison-pecs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(raisonPecDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<RaisonPec> raisonPecList = raisonPecRepository.findAll();
        assertThat(raisonPecList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllRaisonPecs() throws Exception {
        // Initialize the database
        raisonPecRepository.saveAndFlush(raisonPec);

        // Get all the raisonPecList
        restRaisonPecMockMvc.perform(get("/api/raison-pecs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(raisonPec.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].creationUserId").value(hasItem(DEFAULT_CREATION_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].responsible").value(hasItem(DEFAULT_RESPONSIBLE.toString())));
    }

    @Test
    @Transactional
    public void getRaisonPec() throws Exception {
        // Initialize the database
        raisonPecRepository.saveAndFlush(raisonPec);

        // Get the raisonPec
        restRaisonPecMockMvc.perform(get("/api/raison-pecs/{id}", raisonPec.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(raisonPec.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.creationUserId").value(DEFAULT_CREATION_USER_ID.intValue()))
            .andExpect(jsonPath("$.responsible").value(DEFAULT_RESPONSIBLE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRaisonPec() throws Exception {
        // Get the raisonPec
        restRaisonPecMockMvc.perform(get("/api/raison-pecs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRaisonPec() throws Exception {
        // Initialize the database
        raisonPecRepository.saveAndFlush(raisonPec);
        raisonPecSearchRepository.save(raisonPec);
        int databaseSizeBeforeUpdate = raisonPecRepository.findAll().size();

        // Update the raisonPec
        RaisonPec updatedRaisonPec = raisonPecRepository.findOne(raisonPec.getId());
        updatedRaisonPec
            .code(UPDATED_CODE)
            .label(UPDATED_LABEL)
            .active(UPDATED_ACTIVE)
            .creationDate(UPDATED_CREATION_DATE)
            .creationUserId(UPDATED_CREATION_USER_ID)
            .responsible(UPDATED_RESPONSIBLE);
        RaisonPecDTO raisonPecDTO = raisonPecMapper.toDto(updatedRaisonPec);

        restRaisonPecMockMvc.perform(put("/api/raison-pecs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(raisonPecDTO)))
            .andExpect(status().isOk());

        // Validate the RaisonPec in the database
        List<RaisonPec> raisonPecList = raisonPecRepository.findAll();
        assertThat(raisonPecList).hasSize(databaseSizeBeforeUpdate);
        RaisonPec testRaisonPec = raisonPecList.get(raisonPecList.size() - 1);
        assertThat(testRaisonPec.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testRaisonPec.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testRaisonPec.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testRaisonPec.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testRaisonPec.getCreationUserId()).isEqualTo(UPDATED_CREATION_USER_ID);
        assertThat(testRaisonPec.getResponsible()).isEqualTo(UPDATED_RESPONSIBLE);

        // Validate the RaisonPec in Elasticsearch
        RaisonPec raisonPecEs = raisonPecSearchRepository.findOne(testRaisonPec.getId());
        assertThat(raisonPecEs).isEqualToComparingFieldByField(testRaisonPec);
    }

    @Test
    @Transactional
    public void updateNonExistingRaisonPec() throws Exception {
        int databaseSizeBeforeUpdate = raisonPecRepository.findAll().size();

        // Create the RaisonPec
        RaisonPecDTO raisonPecDTO = raisonPecMapper.toDto(raisonPec);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRaisonPecMockMvc.perform(put("/api/raison-pecs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(raisonPecDTO)))
            .andExpect(status().isCreated());

        // Validate the RaisonPec in the database
        List<RaisonPec> raisonPecList = raisonPecRepository.findAll();
        assertThat(raisonPecList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRaisonPec() throws Exception {
        // Initialize the database
        raisonPecRepository.saveAndFlush(raisonPec);
        raisonPecSearchRepository.save(raisonPec);
        int databaseSizeBeforeDelete = raisonPecRepository.findAll().size();

        // Get the raisonPec
        restRaisonPecMockMvc.perform(delete("/api/raison-pecs/{id}", raisonPec.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean raisonPecExistsInEs = raisonPecSearchRepository.exists(raisonPec.getId());
        assertThat(raisonPecExistsInEs).isFalse();

        // Validate the database is empty
        List<RaisonPec> raisonPecList = raisonPecRepository.findAll();
        assertThat(raisonPecList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchRaisonPec() throws Exception {
        // Initialize the database
        raisonPecRepository.saveAndFlush(raisonPec);
        raisonPecSearchRepository.save(raisonPec);

        // Search the raisonPec
        restRaisonPecMockMvc.perform(get("/api/_search/raison-pecs?query=id:" + raisonPec.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(raisonPec.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].creationUserId").value(hasItem(DEFAULT_CREATION_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].responsible").value(hasItem(DEFAULT_RESPONSIBLE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RaisonPec.class);
        RaisonPec raisonPec1 = new RaisonPec();
        raisonPec1.setId(1L);
        RaisonPec raisonPec2 = new RaisonPec();
        raisonPec2.setId(raisonPec1.getId());
        assertThat(raisonPec1).isEqualTo(raisonPec2);
        raisonPec2.setId(2L);
        assertThat(raisonPec1).isNotEqualTo(raisonPec2);
        raisonPec1.setId(null);
        assertThat(raisonPec1).isNotEqualTo(raisonPec2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RaisonPecDTO.class);
        RaisonPecDTO raisonPecDTO1 = new RaisonPecDTO();
        raisonPecDTO1.setId(1L);
        RaisonPecDTO raisonPecDTO2 = new RaisonPecDTO();
        assertThat(raisonPecDTO1).isNotEqualTo(raisonPecDTO2);
        raisonPecDTO2.setId(raisonPecDTO1.getId());
        assertThat(raisonPecDTO1).isEqualTo(raisonPecDTO2);
        raisonPecDTO2.setId(2L);
        assertThat(raisonPecDTO1).isNotEqualTo(raisonPecDTO2);
        raisonPecDTO1.setId(null);
        assertThat(raisonPecDTO1).isNotEqualTo(raisonPecDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(raisonPecMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(raisonPecMapper.fromId(null)).isNull();
    }
}
