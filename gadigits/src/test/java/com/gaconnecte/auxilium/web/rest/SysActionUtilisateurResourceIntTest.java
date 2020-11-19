package com.gaconnecte.auxilium.web.rest;

import com.gaconnecte.auxilium.AuxiliumApp;

import com.gaconnecte.auxilium.domain.SysActionUtilisateur;
import com.gaconnecte.auxilium.domain.Journal;
import com.gaconnecte.auxilium.repository.SysActionUtilisateurRepository;
import com.gaconnecte.auxilium.service.SysActionUtilisateurService;
import com.gaconnecte.auxilium.repository.search.SysActionUtilisateurSearchRepository;
import com.gaconnecte.auxilium.service.dto.SysActionUtilisateurDTO;
import com.gaconnecte.auxilium.service.mapper.SysActionUtilisateurMapper;
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
 * Test class for the SysActionUtilisateurResource REST controller.
 *
 * @see SysActionUtilisateurResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuxiliumApp.class)
public class SysActionUtilisateurResourceIntTest {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    @Autowired
    private SysActionUtilisateurRepository sysActionUtilisateurRepository;

    @Autowired
    private SysActionUtilisateurMapper sysActionUtilisateurMapper;

    @Autowired
    private SysActionUtilisateurService sysActionUtilisateurService;

    @Autowired
    private SysActionUtilisateurSearchRepository sysActionUtilisateurSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSysActionUtilisateurMockMvc;

    private SysActionUtilisateur sysActionUtilisateur;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SysActionUtilisateurResource sysActionUtilisateurResource = new SysActionUtilisateurResource(sysActionUtilisateurService);
        this.restSysActionUtilisateurMockMvc = MockMvcBuilders.standaloneSetup(sysActionUtilisateurResource)
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
    public static SysActionUtilisateur createEntity(EntityManager em) {
        SysActionUtilisateur sysActionUtilisateur = new SysActionUtilisateur()
            .nom(DEFAULT_NOM);
        // Add required entity
        Journal journal = JournalResourceIntTest.createEntity(em);
        em.persist(journal);
        em.flush();
        sysActionUtilisateur.getJournals().add(journal);
        return sysActionUtilisateur;
    }

    @Before
    public void initTest() {
        sysActionUtilisateurSearchRepository.deleteAll();
        sysActionUtilisateur = createEntity(em);
    }

    @Test
    @Transactional
    public void createSysActionUtilisateur() throws Exception {
        int databaseSizeBeforeCreate = sysActionUtilisateurRepository.findAll().size();

        // Create the SysActionUtilisateur
        SysActionUtilisateurDTO sysActionUtilisateurDTO = sysActionUtilisateurMapper.toDto(sysActionUtilisateur);
        restSysActionUtilisateurMockMvc.perform(post("/api/sys-action-utilisateurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sysActionUtilisateurDTO)))
            .andExpect(status().isCreated());

        // Validate the SysActionUtilisateur in the database
        List<SysActionUtilisateur> sysActionUtilisateurList = sysActionUtilisateurRepository.findAll();
        assertThat(sysActionUtilisateurList).hasSize(databaseSizeBeforeCreate + 1);
        SysActionUtilisateur testSysActionUtilisateur = sysActionUtilisateurList.get(sysActionUtilisateurList.size() - 1);
        assertThat(testSysActionUtilisateur.getNom()).isEqualTo(DEFAULT_NOM);

        // Validate the SysActionUtilisateur in Elasticsearch
        SysActionUtilisateur sysActionUtilisateurEs = sysActionUtilisateurSearchRepository.findOne(testSysActionUtilisateur.getId());
        assertThat(sysActionUtilisateurEs).isEqualToComparingFieldByField(testSysActionUtilisateur);
    }

    @Test
    @Transactional
    public void createSysActionUtilisateurWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sysActionUtilisateurRepository.findAll().size();

        // Create the SysActionUtilisateur with an existing ID
        sysActionUtilisateur.setId(1L);
        SysActionUtilisateurDTO sysActionUtilisateurDTO = sysActionUtilisateurMapper.toDto(sysActionUtilisateur);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSysActionUtilisateurMockMvc.perform(post("/api/sys-action-utilisateurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sysActionUtilisateurDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<SysActionUtilisateur> sysActionUtilisateurList = sysActionUtilisateurRepository.findAll();
        assertThat(sysActionUtilisateurList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = sysActionUtilisateurRepository.findAll().size();
        // set the field null
        sysActionUtilisateur.setNom(null);

        // Create the SysActionUtilisateur, which fails.
        SysActionUtilisateurDTO sysActionUtilisateurDTO = sysActionUtilisateurMapper.toDto(sysActionUtilisateur);

        restSysActionUtilisateurMockMvc.perform(post("/api/sys-action-utilisateurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sysActionUtilisateurDTO)))
            .andExpect(status().isBadRequest());

        List<SysActionUtilisateur> sysActionUtilisateurList = sysActionUtilisateurRepository.findAll();
        assertThat(sysActionUtilisateurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSysActionUtilisateurs() throws Exception {
        // Initialize the database
        sysActionUtilisateurRepository.saveAndFlush(sysActionUtilisateur);

        // Get all the sysActionUtilisateurList
        restSysActionUtilisateurMockMvc.perform(get("/api/sys-action-utilisateurs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sysActionUtilisateur.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())));
    }

    @Test
    @Transactional
    public void getSysActionUtilisateur() throws Exception {
        // Initialize the database
        sysActionUtilisateurRepository.saveAndFlush(sysActionUtilisateur);

        // Get the sysActionUtilisateur
        restSysActionUtilisateurMockMvc.perform(get("/api/sys-action-utilisateurs/{id}", sysActionUtilisateur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sysActionUtilisateur.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSysActionUtilisateur() throws Exception {
        // Get the sysActionUtilisateur
        restSysActionUtilisateurMockMvc.perform(get("/api/sys-action-utilisateurs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSysActionUtilisateur() throws Exception {
        // Initialize the database
        sysActionUtilisateurRepository.saveAndFlush(sysActionUtilisateur);
        sysActionUtilisateurSearchRepository.save(sysActionUtilisateur);
        int databaseSizeBeforeUpdate = sysActionUtilisateurRepository.findAll().size();

        // Update the sysActionUtilisateur
        SysActionUtilisateur updatedSysActionUtilisateur = sysActionUtilisateurRepository.findOne(sysActionUtilisateur.getId());
        updatedSysActionUtilisateur
            .nom(UPDATED_NOM);
        SysActionUtilisateurDTO sysActionUtilisateurDTO = sysActionUtilisateurMapper.toDto(updatedSysActionUtilisateur);

        restSysActionUtilisateurMockMvc.perform(put("/api/sys-action-utilisateurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sysActionUtilisateurDTO)))
            .andExpect(status().isOk());

        // Validate the SysActionUtilisateur in the database
        List<SysActionUtilisateur> sysActionUtilisateurList = sysActionUtilisateurRepository.findAll();
        assertThat(sysActionUtilisateurList).hasSize(databaseSizeBeforeUpdate);
        SysActionUtilisateur testSysActionUtilisateur = sysActionUtilisateurList.get(sysActionUtilisateurList.size() - 1);
        assertThat(testSysActionUtilisateur.getNom()).isEqualTo(UPDATED_NOM);

        // Validate the SysActionUtilisateur in Elasticsearch
        SysActionUtilisateur sysActionUtilisateurEs = sysActionUtilisateurSearchRepository.findOne(testSysActionUtilisateur.getId());
        assertThat(sysActionUtilisateurEs).isEqualToComparingFieldByField(testSysActionUtilisateur);
    }

    @Test
    @Transactional
    public void updateNonExistingSysActionUtilisateur() throws Exception {
        int databaseSizeBeforeUpdate = sysActionUtilisateurRepository.findAll().size();

        // Create the SysActionUtilisateur
        SysActionUtilisateurDTO sysActionUtilisateurDTO = sysActionUtilisateurMapper.toDto(sysActionUtilisateur);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSysActionUtilisateurMockMvc.perform(put("/api/sys-action-utilisateurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sysActionUtilisateurDTO)))
            .andExpect(status().isCreated());

        // Validate the SysActionUtilisateur in the database
        List<SysActionUtilisateur> sysActionUtilisateurList = sysActionUtilisateurRepository.findAll();
        assertThat(sysActionUtilisateurList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSysActionUtilisateur() throws Exception {
        // Initialize the database
        sysActionUtilisateurRepository.saveAndFlush(sysActionUtilisateur);
        sysActionUtilisateurSearchRepository.save(sysActionUtilisateur);
        int databaseSizeBeforeDelete = sysActionUtilisateurRepository.findAll().size();

        // Get the sysActionUtilisateur
        restSysActionUtilisateurMockMvc.perform(delete("/api/sys-action-utilisateurs/{id}", sysActionUtilisateur.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean sysActionUtilisateurExistsInEs = sysActionUtilisateurSearchRepository.exists(sysActionUtilisateur.getId());
        assertThat(sysActionUtilisateurExistsInEs).isFalse();

        // Validate the database is empty
        List<SysActionUtilisateur> sysActionUtilisateurList = sysActionUtilisateurRepository.findAll();
        assertThat(sysActionUtilisateurList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchSysActionUtilisateur() throws Exception {
        // Initialize the database
        sysActionUtilisateurRepository.saveAndFlush(sysActionUtilisateur);
        sysActionUtilisateurSearchRepository.save(sysActionUtilisateur);

        // Search the sysActionUtilisateur
        restSysActionUtilisateurMockMvc.perform(get("/api/_search/sys-action-utilisateurs?query=id:" + sysActionUtilisateur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sysActionUtilisateur.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SysActionUtilisateur.class);
        SysActionUtilisateur sysActionUtilisateur1 = new SysActionUtilisateur();
        sysActionUtilisateur1.setId(1L);
        SysActionUtilisateur sysActionUtilisateur2 = new SysActionUtilisateur();
        sysActionUtilisateur2.setId(sysActionUtilisateur1.getId());
        assertThat(sysActionUtilisateur1).isEqualTo(sysActionUtilisateur2);
        sysActionUtilisateur2.setId(2L);
        assertThat(sysActionUtilisateur1).isNotEqualTo(sysActionUtilisateur2);
        sysActionUtilisateur1.setId(null);
        assertThat(sysActionUtilisateur1).isNotEqualTo(sysActionUtilisateur2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SysActionUtilisateurDTO.class);
        SysActionUtilisateurDTO sysActionUtilisateurDTO1 = new SysActionUtilisateurDTO();
        sysActionUtilisateurDTO1.setId(1L);
        SysActionUtilisateurDTO sysActionUtilisateurDTO2 = new SysActionUtilisateurDTO();
        assertThat(sysActionUtilisateurDTO1).isNotEqualTo(sysActionUtilisateurDTO2);
        sysActionUtilisateurDTO2.setId(sysActionUtilisateurDTO1.getId());
        assertThat(sysActionUtilisateurDTO1).isEqualTo(sysActionUtilisateurDTO2);
        sysActionUtilisateurDTO2.setId(2L);
        assertThat(sysActionUtilisateurDTO1).isNotEqualTo(sysActionUtilisateurDTO2);
        sysActionUtilisateurDTO1.setId(null);
        assertThat(sysActionUtilisateurDTO1).isNotEqualTo(sysActionUtilisateurDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(sysActionUtilisateurMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(sysActionUtilisateurMapper.fromId(null)).isNull();
    }
}
