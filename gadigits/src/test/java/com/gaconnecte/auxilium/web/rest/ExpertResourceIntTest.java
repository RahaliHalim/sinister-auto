package com.gaconnecte.auxilium.web.rest;

import com.gaconnecte.auxilium.AuxiliumApp;

import com.gaconnecte.auxilium.domain.Expert;
import com.gaconnecte.auxilium.domain.PersonneMorale;
import com.gaconnecte.auxilium.domain.Contact;
import com.gaconnecte.auxilium.repository.ExpertRepository;
import com.gaconnecte.auxilium.service.ExpertService;
import com.gaconnecte.auxilium.repository.search.ExpertSearchRepository;
import com.gaconnecte.auxilium.service.dto.ExpertDTO;
import com.gaconnecte.auxilium.service.mapper.ExpertMapper;
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
 * Test class for the ExpertResource REST controller.
 *
 * @see ExpertResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuxiliumApp.class)
public class ExpertResourceIntTest {

    private static final Integer DEFAULT_NMAX_AFFILIATION = 99999999;
    private static final Integer UPDATED_NMAX_AFFILIATION = 99999998;

    private static final String DEFAULT_NUM_ENREG_FTUSA = "AAAAAAAAAA";
    private static final String UPDATED_NUM_ENREG_FTUSA = "BBBBBBBBBB";

    private static final String DEFAULT_DIPLOME = "AAAAAAAAAA";
    private static final String UPDATED_DIPLOME = "BBBBBBBBBB";

    private static final String DEFAULT_SPECIALITE = "AAAAAAAAAA";
    private static final String UPDATED_SPECIALITE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_AGREMENT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_AGREMENT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_CREATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CREATION = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DEBUT_CONGE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DEBUT_CONGE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_FIN_CONGE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_FIN_CONGE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_IS_BLOQUE = false;
    private static final Boolean UPDATED_IS_BLOQUE = true;

    private static final Boolean DEFAULT_IS_ENG = false;
    private static final Boolean UPDATED_IS_ENG = true;

    @Autowired
    private ExpertRepository expertRepository;

    @Autowired
    private ExpertMapper expertMapper;

    @Autowired
    private ExpertService expertService;

    @Autowired
    private ExpertSearchRepository expertSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restExpertMockMvc;

    private Expert expert;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ExpertResource expertResource = new ExpertResource(expertService);
        this.restExpertMockMvc = MockMvcBuilders.standaloneSetup(expertResource)
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
   


    @Test
    @Transactional
    public void createExpertWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = expertRepository.findAll().size();

        // Create the Expert with an existing ID
        expert.setId(1L);
        ExpertDTO expertDTO = expertMapper.toDto(expert);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExpertMockMvc.perform(post("/api/experts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(expertDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Expert> expertList = expertRepository.findAll();
        assertThat(expertList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllExperts() throws Exception {
        // Initialize the database
        expertRepository.saveAndFlush(expert);

        // Get all the expertList
        restExpertMockMvc.perform(get("/api/experts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(expert.getId().intValue())))
            .andExpect(jsonPath("$.[*].nmaxAffiliation").value(hasItem(DEFAULT_NMAX_AFFILIATION)))
            .andExpect(jsonPath("$.[*].numEnregFtusa").value(hasItem(DEFAULT_NUM_ENREG_FTUSA.toString())))
            .andExpect(jsonPath("$.[*].diplome").value(hasItem(DEFAULT_DIPLOME.toString())))
            .andExpect(jsonPath("$.[*].specialite").value(hasItem(DEFAULT_SPECIALITE.toString())))
            .andExpect(jsonPath("$.[*].dateAgrement").value(hasItem(DEFAULT_DATE_AGREMENT.toString())))
            .andExpect(jsonPath("$.[*].dateCreation").value(hasItem(DEFAULT_DATE_CREATION.toString())))
            .andExpect(jsonPath("$.[*].debutConge").value(hasItem(DEFAULT_DEBUT_CONGE.toString())))
            .andExpect(jsonPath("$.[*].dateFinConge").value(hasItem(DEFAULT_DATE_FIN_CONGE.toString())))
            .andExpect(jsonPath("$.[*].isBloque").value(hasItem(DEFAULT_IS_BLOQUE.booleanValue())))
            .andExpect(jsonPath("$.[*].isEng").value(hasItem(DEFAULT_IS_ENG.booleanValue())));
    }

    @Test
    @Transactional
    public void getExpert() throws Exception {
        // Initialize the database
        expertRepository.saveAndFlush(expert);

        // Get the expert
        restExpertMockMvc.perform(get("/api/experts/{id}", expert.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(expert.getId().intValue()))
            .andExpect(jsonPath("$.nmaxAffiliation").value(DEFAULT_NMAX_AFFILIATION))
            .andExpect(jsonPath("$.numEnregFtusa").value(DEFAULT_NUM_ENREG_FTUSA.toString()))
            .andExpect(jsonPath("$.diplome").value(DEFAULT_DIPLOME.toString()))
            .andExpect(jsonPath("$.specialite").value(DEFAULT_SPECIALITE.toString()))
            .andExpect(jsonPath("$.dateAgrement").value(DEFAULT_DATE_AGREMENT.toString()))
            .andExpect(jsonPath("$.dateCreation").value(DEFAULT_DATE_CREATION.toString()))
            .andExpect(jsonPath("$.debutConge").value(DEFAULT_DEBUT_CONGE.toString()))
            .andExpect(jsonPath("$.dateFinConge").value(DEFAULT_DATE_FIN_CONGE.toString()))
            .andExpect(jsonPath("$.isBloque").value(DEFAULT_IS_BLOQUE.booleanValue()))
            .andExpect(jsonPath("$.isEng").value(DEFAULT_IS_ENG.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingExpert() throws Exception {
        // Get the expert
        restExpertMockMvc.perform(get("/api/experts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

   
    @Test
    @Transactional
    public void updateNonExistingExpert() throws Exception {
        int databaseSizeBeforeUpdate = expertRepository.findAll().size();

        // Create the Expert
        ExpertDTO expertDTO = expertMapper.toDto(expert);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restExpertMockMvc.perform(put("/api/experts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(expertDTO)))
            .andExpect(status().isCreated());

        // Validate the Expert in the database
        List<Expert> expertList = expertRepository.findAll();
        assertThat(expertList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteExpert() throws Exception {
        // Initialize the database
        expertRepository.saveAndFlush(expert);
        expertSearchRepository.save(expert);
        int databaseSizeBeforeDelete = expertRepository.findAll().size();

        // Get the expert
        restExpertMockMvc.perform(delete("/api/experts/{id}", expert.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean expertExistsInEs = expertSearchRepository.exists(expert.getId());
        assertThat(expertExistsInEs).isFalse();

        // Validate the database is empty
        List<Expert> expertList = expertRepository.findAll();
        assertThat(expertList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchExpert() throws Exception {
        // Initialize the database
        expertRepository.saveAndFlush(expert);
        expertSearchRepository.save(expert);

        // Search the expert
        restExpertMockMvc.perform(get("/api/_search/experts?query=id:" + expert.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(expert.getId().intValue())))
            .andExpect(jsonPath("$.[*].nmaxAffiliation").value(hasItem(DEFAULT_NMAX_AFFILIATION)))
            .andExpect(jsonPath("$.[*].numEnregFtusa").value(hasItem(DEFAULT_NUM_ENREG_FTUSA.toString())))
            .andExpect(jsonPath("$.[*].diplome").value(hasItem(DEFAULT_DIPLOME.toString())))
            .andExpect(jsonPath("$.[*].specialite").value(hasItem(DEFAULT_SPECIALITE.toString())))
            .andExpect(jsonPath("$.[*].dateAgrement").value(hasItem(DEFAULT_DATE_AGREMENT.toString())))
            .andExpect(jsonPath("$.[*].dateCreation").value(hasItem(DEFAULT_DATE_CREATION.toString())))
            .andExpect(jsonPath("$.[*].debutConge").value(hasItem(DEFAULT_DEBUT_CONGE.toString())))
            .andExpect(jsonPath("$.[*].dateFinConge").value(hasItem(DEFAULT_DATE_FIN_CONGE.toString())))
            .andExpect(jsonPath("$.[*].isBloque").value(hasItem(DEFAULT_IS_BLOQUE.booleanValue())))
            .andExpect(jsonPath("$.[*].isEng").value(hasItem(DEFAULT_IS_ENG.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Expert.class);
        Expert expert1 = new Expert();
        expert1.setId(1L);
        Expert expert2 = new Expert();
        expert2.setId(expert1.getId());
        assertThat(expert1).isEqualTo(expert2);
        expert2.setId(2L);
        assertThat(expert1).isNotEqualTo(expert2);
        expert1.setId(null);
        assertThat(expert1).isNotEqualTo(expert2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExpertDTO.class);
        ExpertDTO expertDTO1 = new ExpertDTO();
        expertDTO1.setId(1L);
        ExpertDTO expertDTO2 = new ExpertDTO();
        assertThat(expertDTO1).isNotEqualTo(expertDTO2);
        expertDTO2.setId(expertDTO1.getId());
        assertThat(expertDTO1).isEqualTo(expertDTO2);
        expertDTO2.setId(2L);
        assertThat(expertDTO1).isNotEqualTo(expertDTO2);
        expertDTO1.setId(null);
        assertThat(expertDTO1).isNotEqualTo(expertDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(expertMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(expertMapper.fromId(null)).isNull();
    }
}
