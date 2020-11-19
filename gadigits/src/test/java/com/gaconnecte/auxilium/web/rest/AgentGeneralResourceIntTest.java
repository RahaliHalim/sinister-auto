package com.gaconnecte.auxilium.web.rest;

import com.gaconnecte.auxilium.AuxiliumApp;

import com.gaconnecte.auxilium.domain.AgentGeneral;
import com.gaconnecte.auxilium.domain.PersonnePhysique;
import com.gaconnecte.auxilium.repository.AgentGeneralRepository;
import com.gaconnecte.auxilium.service.AgentGeneralService;
import com.gaconnecte.auxilium.repository.search.AgentGeneralSearchRepository;
import com.gaconnecte.auxilium.service.dto.AgentGeneralDTO;
import com.gaconnecte.auxilium.service.mapper.AgentGeneralMapper;
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
 * Test class for the AgentGeneralResource REST controller.
 *
 * @see AgentGeneralResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuxiliumApp.class)
public class AgentGeneralResourceIntTest {

    private static final Double DEFAULT_CODE = 111D;
    private static final Double UPDATED_CODE = 111D;

    private static final Boolean DEFAULT_IS_BLOQUE = false;
    private static final Boolean UPDATED_IS_BLOQUE = true;

    @Autowired
    private AgentGeneralRepository agentGeneralRepository;

    @Autowired
    private AgentGeneralMapper agentGeneralMapper;

    @Autowired
    private AgentGeneralService agentGeneralService;

    @Autowired
    private AgentGeneralSearchRepository agentGeneralSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAgentGeneralMockMvc;

    private AgentGeneral agentGeneral;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
      
        this.restAgentGeneralMockMvc = MockMvcBuilders.standaloneSetup(null)
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
    public static AgentGeneral createEntity(EntityManager em) {
        AgentGeneral agentGeneral = new AgentGeneral()
            .isBloque(DEFAULT_IS_BLOQUE);
        // Add required entity
        PersonnePhysique personnePhysique = PersonnePhysiqueResourceIntTest.createEntity(em);
        em.persist(personnePhysique);
        em.flush();
        agentGeneral.setPersonnePhysique(personnePhysique);
        // Add required entity
       // RefAgence agence = RefAgenceResourceIntTest.createEntity(em);
        em.persist(null);
        em.flush();
        //agentGeneral.setAgence(agence);
        return agentGeneral;
    }

    @Before
    public void initTest() {
        agentGeneralSearchRepository.deleteAll();
        agentGeneral = createEntity(em);
    }

    @Test
    @Transactional
    public void createAgentGeneral() throws Exception {
        int databaseSizeBeforeCreate = agentGeneralRepository.findAll().size();

        // Create the AgentGeneral
//        AgentGeneralDTO agentGeneralDTO = agentGeneralMapper.toDto(agentGeneral);
//        restAgentGeneralMockMvc.perform(post("/api/agent-generals")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(agentGeneralDTO)))
//            .andExpect(status().isCreated());

        // Validate the AgentGeneral in the database
//        List<AgentGeneral> agentGeneralList = agentGeneralRepository.findAll();
//        assertThat(agentGeneralList).hasSize(databaseSizeBeforeCreate + 1);
//        AgentGeneral testAgentGeneral = agentGeneralList.get(agentGeneralList.size() - 1);
//        assertThat(testAgentGeneral.getCode()).isEqualTo(DEFAULT_CODE);
//        assertThat(testAgentGeneral.isIsBloque()).isEqualTo(DEFAULT_IS_BLOQUE);

        // Validate the AgentGeneral in Elasticsearch
//        AgentGeneral agentGeneralEs = agentGeneralSearchRepository.findOne(testAgentGeneral.getId());
//        assertThat(agentGeneralEs).isEqualToComparingFieldByField(testAgentGeneral);
    }

    @Test
    @Transactional
    public void createAgentGeneralWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = agentGeneralRepository.findAll().size();

        // Create the AgentGeneral with an existing ID
        agentGeneral.setId(1L);
        AgentGeneralDTO agentGeneralDTO = agentGeneralMapper.toDto(agentGeneral);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAgentGeneralMockMvc.perform(post("/api/agent-generals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agentGeneralDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<AgentGeneral> agentGeneralList = agentGeneralRepository.findAll();
        assertThat(agentGeneralList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = agentGeneralRepository.findAll().size();
       
        // Create the AgentGeneral, which fails.
        AgentGeneralDTO agentGeneralDTO = agentGeneralMapper.toDto(agentGeneral);

        restAgentGeneralMockMvc.perform(post("/api/agent-generals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agentGeneralDTO)))
            .andExpect(status().isBadRequest());

        List<AgentGeneral> agentGeneralList = agentGeneralRepository.findAll();
        assertThat(agentGeneralList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAgentGenerals() throws Exception {
        // Initialize the database
        agentGeneralRepository.saveAndFlush(agentGeneral);

        // Get all the agentGeneralList
        restAgentGeneralMockMvc.perform(get("/api/agent-generals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(agentGeneral.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.doubleValue())))
            .andExpect(jsonPath("$.[*].isBloque").value(hasItem(DEFAULT_IS_BLOQUE.booleanValue())));
    }

    @Test
    @Transactional
    public void getAgentGeneral() throws Exception {
        // Initialize the database
        agentGeneralRepository.saveAndFlush(agentGeneral);

        // Get the agentGeneral
        restAgentGeneralMockMvc.perform(get("/api/agent-generals/{id}", agentGeneral.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(agentGeneral.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.doubleValue()))
            .andExpect(jsonPath("$.isBloque").value(DEFAULT_IS_BLOQUE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAgentGeneral() throws Exception {
        // Get the agentGeneral
        restAgentGeneralMockMvc.perform(get("/api/agent-generals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAgentGeneral() throws Exception {
        // Initialize the database
        agentGeneralRepository.saveAndFlush(agentGeneral);
        agentGeneralSearchRepository.save(agentGeneral);
        int databaseSizeBeforeUpdate = agentGeneralRepository.findAll().size();

        // Update the agentGeneral
        AgentGeneral updatedAgentGeneral = agentGeneralRepository.findOne(agentGeneral.getId());
        updatedAgentGeneral
            .isBloque(UPDATED_IS_BLOQUE);
        AgentGeneralDTO agentGeneralDTO = agentGeneralMapper.toDto(updatedAgentGeneral);

        restAgentGeneralMockMvc.perform(put("/api/agent-generals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agentGeneralDTO)))
            .andExpect(status().isOk());

        // Validate the AgentGeneral in the database
        List<AgentGeneral> agentGeneralList = agentGeneralRepository.findAll();
        assertThat(agentGeneralList).hasSize(databaseSizeBeforeUpdate);
        AgentGeneral testAgentGeneral = agentGeneralList.get(agentGeneralList.size() - 1);
        assertThat(testAgentGeneral.isIsBloque()).isEqualTo(UPDATED_IS_BLOQUE);

        // Validate the AgentGeneral in Elasticsearch
        AgentGeneral agentGeneralEs = agentGeneralSearchRepository.findOne(testAgentGeneral.getId());
        assertThat(agentGeneralEs).isEqualToComparingFieldByField(testAgentGeneral);
    }

    @Test
    @Transactional
    public void updateNonExistingAgentGeneral() throws Exception {
        int databaseSizeBeforeUpdate = agentGeneralRepository.findAll().size();

        // Create the AgentGeneral
        AgentGeneralDTO agentGeneralDTO = agentGeneralMapper.toDto(agentGeneral);

        // If the entity doesn't have an ID, it will be created instead of just being updated
//        restAgentGeneralMockMvc.perform(put("/api/agent-generals")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(agentGeneralDTO)))
//            .andExpect(status().isCreated());

        // Validate the AgentGeneral in the database
        List<AgentGeneral> agentGeneralList = agentGeneralRepository.findAll();
        //assertThat(agentGeneralList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAgentGeneral() throws Exception {
        // Initialize the database
        agentGeneralRepository.saveAndFlush(agentGeneral);
        agentGeneralSearchRepository.save(agentGeneral);
        int databaseSizeBeforeDelete = agentGeneralRepository.findAll().size();

        // Get the agentGeneral
        restAgentGeneralMockMvc.perform(delete("/api/agent-generals/{id}", agentGeneral.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean agentGeneralExistsInEs = agentGeneralSearchRepository.exists(agentGeneral.getId());
        assertThat(agentGeneralExistsInEs).isFalse();

        // Validate the database is empty
        List<AgentGeneral> agentGeneralList = agentGeneralRepository.findAll();
        assertThat(agentGeneralList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchAgentGeneral() throws Exception {
        // Initialize the database
        agentGeneralRepository.saveAndFlush(agentGeneral);
        agentGeneralSearchRepository.save(agentGeneral);

        // Search the agentGeneral
        restAgentGeneralMockMvc.perform(get("/api/_search/agent-generals?query=id:" + agentGeneral.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(agentGeneral.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.doubleValue())))
            .andExpect(jsonPath("$.[*].isBloque").value(hasItem(DEFAULT_IS_BLOQUE.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AgentGeneral.class);
        AgentGeneral agentGeneral1 = new AgentGeneral();
        agentGeneral1.setId(1L);
        AgentGeneral agentGeneral2 = new AgentGeneral();
        agentGeneral2.setId(agentGeneral1.getId());
        assertThat(agentGeneral1).isEqualTo(agentGeneral2);
        agentGeneral2.setId(2L);
        assertThat(agentGeneral1).isNotEqualTo(agentGeneral2);
        agentGeneral1.setId(null);
        assertThat(agentGeneral1).isNotEqualTo(agentGeneral2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AgentGeneralDTO.class);
        AgentGeneralDTO agentGeneralDTO1 = new AgentGeneralDTO();
        agentGeneralDTO1.setId(1L);
        AgentGeneralDTO agentGeneralDTO2 = new AgentGeneralDTO();
        assertThat(agentGeneralDTO1).isNotEqualTo(agentGeneralDTO2);
        agentGeneralDTO2.setId(agentGeneralDTO1.getId());
        assertThat(agentGeneralDTO1).isEqualTo(agentGeneralDTO2);
        agentGeneralDTO2.setId(2L);
        assertThat(agentGeneralDTO1).isNotEqualTo(agentGeneralDTO2);
        agentGeneralDTO1.setId(null);
        assertThat(agentGeneralDTO1).isNotEqualTo(agentGeneralDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(agentGeneralMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(agentGeneralMapper.fromId(null)).isNull();
    }
}
