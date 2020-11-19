package com.gaconnecte.auxilium.web.rest;

import com.gaconnecte.auxilium.AuxiliumApp;

import com.gaconnecte.auxilium.domain.Apec;
import com.gaconnecte.auxilium.repository.ApecRepository;
import com.gaconnecte.auxilium.service.ApecService;
import com.gaconnecte.auxilium.repository.search.ApecSearchRepository;
import com.gaconnecte.auxilium.service.dto.ApecDTO;
import com.gaconnecte.auxilium.service.mapper.ApecMapper;
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
 * Test class for the ApecResource REST controller.
 *
 * @see ApecResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuxiliumApp.class)
public class ApecResourceIntTest {

    private static final LocalDate DEFAULT_DATE_GENERATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_GENERATION = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_IS_COMPLEMENTAIRE = false;
    private static final Boolean UPDATED_IS_COMPLEMENTAIRE = true;

    private static final Double DEFAULT_PARTICIPATION_GA = 1D;
    private static final Double UPDATED_PARTICIPATION_GA = 2D;

    private static final Double DEFAULT_PARTICIPATION_ASSURE = 1D;
    private static final Double UPDATED_PARTICIPATION_ASSURE = 2D;

    private static final Double DEFAULT_PARTICIPATION_VETUSTE = 1D;
    private static final Double UPDATED_PARTICIPATION_VETUSTE = 2D;

    private static final Double DEFAULT_PARTICIPATION_RPC = 1D;
    private static final Double UPDATED_PARTICIPATION_RPC = 2D;

    private static final Double DEFAULT_DEPACEMENT_PLAFOND = 1D;
    private static final Double UPDATED_DEPACEMENT_PLAFOND = 2D;

    private static final Double DEFAULT_ESTIMATION_FRANCHISE = 1D;
    private static final Double UPDATED_ESTIMATION_FRANCHISE = 2D;

    private static final String DEFAULT_COMMENTAIRE = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTAIRE = "BBBBBBBBBB";

    @Autowired
    private ApecRepository apecRepository;

    @Autowired
    private ApecMapper apecMapper;

    @Autowired
    private ApecService apecService;

    @Autowired
    private ApecSearchRepository apecSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restApecMockMvc;

    private Apec apec;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ApecResource apecResource = null;
        this.restApecMockMvc = MockMvcBuilders.standaloneSetup(apecResource)
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
    public static Apec createEntity(EntityManager em) {
        Apec apec = new Apec()
            .dateGeneration(DEFAULT_DATE_GENERATION)
            .isComplementaire(DEFAULT_IS_COMPLEMENTAIRE)
            .participationGa(DEFAULT_PARTICIPATION_GA)
            .participationAssure(DEFAULT_PARTICIPATION_ASSURE)
            .participationVetuste(DEFAULT_PARTICIPATION_VETUSTE)
            .participationRpc(DEFAULT_PARTICIPATION_RPC)
            .depacementPlafond(DEFAULT_DEPACEMENT_PLAFOND)
            .estimationFranchise(DEFAULT_ESTIMATION_FRANCHISE)
            .commentaire(DEFAULT_COMMENTAIRE);
        return apec;
    }

    @Before
    public void initTest() {
        apecSearchRepository.deleteAll();
        apec = createEntity(em);
    }

    @Test
    @Transactional
    public void createApec() throws Exception {
        int databaseSizeBeforeCreate = apecRepository.findAll().size();

        // Create the Apec
        ApecDTO apecDTO = apecMapper.toDto(apec);
        restApecMockMvc.perform(post("/api/apecs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apecDTO)))
            .andExpect(status().isCreated());

        // Validate the Apec in the database
        List<Apec> apecList = apecRepository.findAll();
        assertThat(apecList).hasSize(databaseSizeBeforeCreate + 1);
        Apec testApec = apecList.get(apecList.size() - 1);
        assertThat(testApec.getDateGeneration()).isEqualTo(DEFAULT_DATE_GENERATION);
        assertThat(testApec.isIsComplementaire()).isEqualTo(DEFAULT_IS_COMPLEMENTAIRE);
        assertThat(testApec.getParticipationGa()).isEqualTo(DEFAULT_PARTICIPATION_GA);
        assertThat(testApec.getParticipationAssure()).isEqualTo(DEFAULT_PARTICIPATION_ASSURE);
        assertThat(testApec.getParticipationVetuste()).isEqualTo(DEFAULT_PARTICIPATION_VETUSTE);
        assertThat(testApec.getParticipationRpc()).isEqualTo(DEFAULT_PARTICIPATION_RPC);
        assertThat(testApec.getDepacementPlafond()).isEqualTo(DEFAULT_DEPACEMENT_PLAFOND);
        assertThat(testApec.getEstimationFranchise()).isEqualTo(DEFAULT_ESTIMATION_FRANCHISE);
        assertThat(testApec.getCommentaire()).isEqualTo(DEFAULT_COMMENTAIRE);

        // Validate the Apec in Elasticsearch
        Apec apecEs = apecSearchRepository.findOne(testApec.getId());
        assertThat(apecEs).isEqualToComparingFieldByField(testApec);
    }

    @Test
    @Transactional
    public void createApecWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = apecRepository.findAll().size();

        // Create the Apec with an existing ID
        apec.setId(1L);
        ApecDTO apecDTO = apecMapper.toDto(apec);

        // An entity with an existing ID cannot be created, so this API call must fail
        restApecMockMvc.perform(post("/api/apecs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apecDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Apec> apecList = apecRepository.findAll();
        assertThat(apecList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDateGenerationIsRequired() throws Exception {
        int databaseSizeBeforeTest = apecRepository.findAll().size();
        // set the field null
        apec.setDateGeneration(null);

        // Create the Apec, which fails.
        ApecDTO apecDTO = apecMapper.toDto(apec);

        restApecMockMvc.perform(post("/api/apecs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apecDTO)))
            .andExpect(status().isBadRequest());

        List<Apec> apecList = apecRepository.findAll();
        assertThat(apecList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkParticipationGaIsRequired() throws Exception {
        int databaseSizeBeforeTest = apecRepository.findAll().size();
        // set the field null
        apec.setParticipationGa(null);

        // Create the Apec, which fails.
        ApecDTO apecDTO = apecMapper.toDto(apec);

        restApecMockMvc.perform(post("/api/apecs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apecDTO)))
            .andExpect(status().isBadRequest());

        List<Apec> apecList = apecRepository.findAll();
        assertThat(apecList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkParticipationAssureIsRequired() throws Exception {
        int databaseSizeBeforeTest = apecRepository.findAll().size();
        // set the field null
        apec.setParticipationAssure(null);

        // Create the Apec, which fails.
        ApecDTO apecDTO = apecMapper.toDto(apec);

        restApecMockMvc.perform(post("/api/apecs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apecDTO)))
            .andExpect(status().isBadRequest());

        List<Apec> apecList = apecRepository.findAll();
        assertThat(apecList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllApecs() throws Exception {
        // Initialize the database
        apecRepository.saveAndFlush(apec);

        // Get all the apecList
        restApecMockMvc.perform(get("/api/apecs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(apec.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateGeneration").value(hasItem(DEFAULT_DATE_GENERATION.toString())))
            .andExpect(jsonPath("$.[*].isComplementaire").value(hasItem(DEFAULT_IS_COMPLEMENTAIRE.booleanValue())))
            .andExpect(jsonPath("$.[*].participationGa").value(hasItem(DEFAULT_PARTICIPATION_GA.doubleValue())))
            .andExpect(jsonPath("$.[*].participationAssure").value(hasItem(DEFAULT_PARTICIPATION_ASSURE.doubleValue())))
            .andExpect(jsonPath("$.[*].participationVetuste").value(hasItem(DEFAULT_PARTICIPATION_VETUSTE.doubleValue())))
            .andExpect(jsonPath("$.[*].participationRpc").value(hasItem(DEFAULT_PARTICIPATION_RPC.doubleValue())))
            .andExpect(jsonPath("$.[*].depacementPlafond").value(hasItem(DEFAULT_DEPACEMENT_PLAFOND.doubleValue())))
            .andExpect(jsonPath("$.[*].estimationFranchise").value(hasItem(DEFAULT_ESTIMATION_FRANCHISE.doubleValue())))
            .andExpect(jsonPath("$.[*].commentaire").value(hasItem(DEFAULT_COMMENTAIRE.toString())));
    }

    @Test
    @Transactional
    public void getApec() throws Exception {
        // Initialize the database
        apecRepository.saveAndFlush(apec);

        // Get the apec
        restApecMockMvc.perform(get("/api/apecs/{id}", apec.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(apec.getId().intValue()))
            .andExpect(jsonPath("$.dateGeneration").value(DEFAULT_DATE_GENERATION.toString()))
            .andExpect(jsonPath("$.isComplementaire").value(DEFAULT_IS_COMPLEMENTAIRE.booleanValue()))
            .andExpect(jsonPath("$.participationGa").value(DEFAULT_PARTICIPATION_GA.doubleValue()))
            .andExpect(jsonPath("$.participationAssure").value(DEFAULT_PARTICIPATION_ASSURE.doubleValue()))
            .andExpect(jsonPath("$.participationVetuste").value(DEFAULT_PARTICIPATION_VETUSTE.doubleValue()))
            .andExpect(jsonPath("$.participationRpc").value(DEFAULT_PARTICIPATION_RPC.doubleValue()))
            .andExpect(jsonPath("$.depacementPlafond").value(DEFAULT_DEPACEMENT_PLAFOND.doubleValue()))
            .andExpect(jsonPath("$.estimationFranchise").value(DEFAULT_ESTIMATION_FRANCHISE.doubleValue()))
            .andExpect(jsonPath("$.commentaire").value(DEFAULT_COMMENTAIRE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingApec() throws Exception {
        // Get the apec
        restApecMockMvc.perform(get("/api/apecs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApec() throws Exception {
        // Initialize the database
        apecRepository.saveAndFlush(apec);
        apecSearchRepository.save(apec);
        int databaseSizeBeforeUpdate = apecRepository.findAll().size();

        // Update the apec
        Apec updatedApec = apecRepository.findOne(apec.getId());
        updatedApec
            .dateGeneration(UPDATED_DATE_GENERATION)
            .isComplementaire(UPDATED_IS_COMPLEMENTAIRE)
            .participationGa(UPDATED_PARTICIPATION_GA)
            .participationAssure(UPDATED_PARTICIPATION_ASSURE)
            .participationVetuste(UPDATED_PARTICIPATION_VETUSTE)
            .participationRpc(UPDATED_PARTICIPATION_RPC)
            .depacementPlafond(UPDATED_DEPACEMENT_PLAFOND)
            .estimationFranchise(UPDATED_ESTIMATION_FRANCHISE)
            .commentaire(UPDATED_COMMENTAIRE);
        ApecDTO apecDTO = apecMapper.toDto(updatedApec);

        restApecMockMvc.perform(put("/api/apecs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apecDTO)))
            .andExpect(status().isOk());

        // Validate the Apec in the database
        List<Apec> apecList = apecRepository.findAll();
        assertThat(apecList).hasSize(databaseSizeBeforeUpdate);
        Apec testApec = apecList.get(apecList.size() - 1);
        assertThat(testApec.getDateGeneration()).isEqualTo(UPDATED_DATE_GENERATION);
        assertThat(testApec.isIsComplementaire()).isEqualTo(UPDATED_IS_COMPLEMENTAIRE);
        assertThat(testApec.getParticipationGa()).isEqualTo(UPDATED_PARTICIPATION_GA);
        assertThat(testApec.getParticipationAssure()).isEqualTo(UPDATED_PARTICIPATION_ASSURE);
        assertThat(testApec.getParticipationVetuste()).isEqualTo(UPDATED_PARTICIPATION_VETUSTE);
        assertThat(testApec.getParticipationRpc()).isEqualTo(UPDATED_PARTICIPATION_RPC);
        assertThat(testApec.getDepacementPlafond()).isEqualTo(UPDATED_DEPACEMENT_PLAFOND);
        assertThat(testApec.getEstimationFranchise()).isEqualTo(UPDATED_ESTIMATION_FRANCHISE);
        assertThat(testApec.getCommentaire()).isEqualTo(UPDATED_COMMENTAIRE);

        // Validate the Apec in Elasticsearch
        Apec apecEs = apecSearchRepository.findOne(testApec.getId());
        assertThat(apecEs).isEqualToComparingFieldByField(testApec);
    }

    @Test
    @Transactional
    public void updateNonExistingApec() throws Exception {
        int databaseSizeBeforeUpdate = apecRepository.findAll().size();

        // Create the Apec
        ApecDTO apecDTO = apecMapper.toDto(apec);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restApecMockMvc.perform(put("/api/apecs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apecDTO)))
            .andExpect(status().isCreated());

        // Validate the Apec in the database
        List<Apec> apecList = apecRepository.findAll();
        assertThat(apecList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteApec() throws Exception {
        // Initialize the database
        apecRepository.saveAndFlush(apec);
        apecSearchRepository.save(apec);
        int databaseSizeBeforeDelete = apecRepository.findAll().size();

        // Get the apec
        restApecMockMvc.perform(delete("/api/apecs/{id}", apec.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean apecExistsInEs = apecSearchRepository.exists(apec.getId());
        assertThat(apecExistsInEs).isFalse();

        // Validate the database is empty
        List<Apec> apecList = apecRepository.findAll();
        assertThat(apecList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchApec() throws Exception {
        // Initialize the database
        apecRepository.saveAndFlush(apec);
        apecSearchRepository.save(apec);

        // Search the apec
        restApecMockMvc.perform(get("/api/_search/apecs?query=id:" + apec.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(apec.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateGeneration").value(hasItem(DEFAULT_DATE_GENERATION.toString())))
            .andExpect(jsonPath("$.[*].isComplementaire").value(hasItem(DEFAULT_IS_COMPLEMENTAIRE.booleanValue())))
            .andExpect(jsonPath("$.[*].participationGa").value(hasItem(DEFAULT_PARTICIPATION_GA.doubleValue())))
            .andExpect(jsonPath("$.[*].participationAssure").value(hasItem(DEFAULT_PARTICIPATION_ASSURE.doubleValue())))
            .andExpect(jsonPath("$.[*].participationVetuste").value(hasItem(DEFAULT_PARTICIPATION_VETUSTE.doubleValue())))
            .andExpect(jsonPath("$.[*].participationRpc").value(hasItem(DEFAULT_PARTICIPATION_RPC.doubleValue())))
            .andExpect(jsonPath("$.[*].depacementPlafond").value(hasItem(DEFAULT_DEPACEMENT_PLAFOND.doubleValue())))
            .andExpect(jsonPath("$.[*].estimationFranchise").value(hasItem(DEFAULT_ESTIMATION_FRANCHISE.doubleValue())))
            .andExpect(jsonPath("$.[*].commentaire").value(hasItem(DEFAULT_COMMENTAIRE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Apec.class);
        Apec apec1 = new Apec();
        apec1.setId(1L);
        Apec apec2 = new Apec();
        apec2.setId(apec1.getId());
        assertThat(apec1).isEqualTo(apec2);
        apec2.setId(2L);
        assertThat(apec1).isNotEqualTo(apec2);
        apec1.setId(null);
        assertThat(apec1).isNotEqualTo(apec2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApecDTO.class);
        ApecDTO apecDTO1 = new ApecDTO();
        apecDTO1.setId(1L);
        ApecDTO apecDTO2 = new ApecDTO();
        assertThat(apecDTO1).isNotEqualTo(apecDTO2);
        apecDTO2.setId(apecDTO1.getId());
        assertThat(apecDTO1).isEqualTo(apecDTO2);
        apecDTO2.setId(2L);
        assertThat(apecDTO1).isNotEqualTo(apecDTO2);
        apecDTO1.setId(null);
        assertThat(apecDTO1).isNotEqualTo(apecDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(apecMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(apecMapper.fromId(null)).isNull();
    }
}
