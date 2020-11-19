package com.gaconnecte.auxilium.web.rest;

import com.gaconnecte.auxilium.AuxiliumApp;

import com.gaconnecte.auxilium.domain.VehiculeLoueur;
import com.gaconnecte.auxilium.repository.VehiculeLoueurRepository;
import com.gaconnecte.auxilium.service.VehiculeLoueurService;
import com.gaconnecte.auxilium.repository.search.VehiculeLoueurSearchRepository;
import com.gaconnecte.auxilium.service.dto.VehiculeLoueurDTO;
import com.gaconnecte.auxilium.service.mapper.VehiculeLoueurMapper;
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
 * Test class for the VehiculeLoueurResource REST controller.
 *
 * @see VehiculeLoueurResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuxiliumApp.class)
public class VehiculeLoueurResourceIntTest {

    private static final String DEFAULT_IMMATRICULATION = "AAAAAAAAAA";
    private static final String UPDATED_IMMATRICULATION = "BBBBBBBBBB";

    private static final Double DEFAULT_TOTAL_PRIX = 1D;
    private static final Double UPDATED_TOTAL_PRIX = 2D;

    @Autowired
    private VehiculeLoueurRepository vehiculeLoueurRepository;

    @Autowired
    private VehiculeLoueurMapper vehiculeLoueurMapper;

    @Autowired
    private VehiculeLoueurService vehiculeLoueurService;

    @Autowired
    private VehiculeLoueurSearchRepository vehiculeLoueurSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restVehiculeLoueurMockMvc;

    private VehiculeLoueur vehiculeLoueur;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        VehiculeLoueurResource vehiculeLoueurResource = new VehiculeLoueurResource(vehiculeLoueurService);
        this.restVehiculeLoueurMockMvc = MockMvcBuilders.standaloneSetup(vehiculeLoueurResource)
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
    public static VehiculeLoueur createEntity(EntityManager em) {
        VehiculeLoueur vehiculeLoueur = new VehiculeLoueur()
            .immatriculation(DEFAULT_IMMATRICULATION)
            .totalPrix(DEFAULT_TOTAL_PRIX);
        return vehiculeLoueur;
    }

    @Before
    public void initTest() {
        vehiculeLoueurSearchRepository.deleteAll();
        vehiculeLoueur = createEntity(em);
    }

    @Test
    @Transactional
    public void createVehiculeLoueur() throws Exception {
        int databaseSizeBeforeCreate = vehiculeLoueurRepository.findAll().size();

        // Create the VehiculeLoueur
        VehiculeLoueurDTO vehiculeLoueurDTO = vehiculeLoueurMapper.toDto(vehiculeLoueur);
        restVehiculeLoueurMockMvc.perform(post("/api/vehicule-loueurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehiculeLoueurDTO)))
            .andExpect(status().isCreated());

        // Validate the VehiculeLoueur in the database
        List<VehiculeLoueur> vehiculeLoueurList = vehiculeLoueurRepository.findAll();
        assertThat(vehiculeLoueurList).hasSize(databaseSizeBeforeCreate + 1);
        VehiculeLoueur testVehiculeLoueur = vehiculeLoueurList.get(vehiculeLoueurList.size() - 1);
        assertThat(testVehiculeLoueur.getImmatriculation()).isEqualTo(DEFAULT_IMMATRICULATION);
        assertThat(testVehiculeLoueur.getTotalPrix()).isEqualTo(DEFAULT_TOTAL_PRIX);

        // Validate the VehiculeLoueur in Elasticsearch
        VehiculeLoueur vehiculeLoueurEs = vehiculeLoueurSearchRepository.findOne(testVehiculeLoueur.getId());
        assertThat(vehiculeLoueurEs).isEqualToComparingFieldByField(testVehiculeLoueur);
    }

    @Test
    @Transactional
    public void createVehiculeLoueurWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vehiculeLoueurRepository.findAll().size();

        // Create the VehiculeLoueur with an existing ID
        vehiculeLoueur.setId(1L);
        VehiculeLoueurDTO vehiculeLoueurDTO = vehiculeLoueurMapper.toDto(vehiculeLoueur);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVehiculeLoueurMockMvc.perform(post("/api/vehicule-loueurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehiculeLoueurDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<VehiculeLoueur> vehiculeLoueurList = vehiculeLoueurRepository.findAll();
        assertThat(vehiculeLoueurList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllVehiculeLoueurs() throws Exception {
        // Initialize the database
        vehiculeLoueurRepository.saveAndFlush(vehiculeLoueur);

        // Get all the vehiculeLoueurList
        restVehiculeLoueurMockMvc.perform(get("/api/vehicule-loueurs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vehiculeLoueur.getId().intValue())))
            .andExpect(jsonPath("$.[*].immatriculation").value(hasItem(DEFAULT_IMMATRICULATION.toString())))
            .andExpect(jsonPath("$.[*].totalPrix").value(hasItem(DEFAULT_TOTAL_PRIX.doubleValue())));
    }

    @Test
    @Transactional
    public void getVehiculeLoueur() throws Exception {
        // Initialize the database
        vehiculeLoueurRepository.saveAndFlush(vehiculeLoueur);

        // Get the vehiculeLoueur
        restVehiculeLoueurMockMvc.perform(get("/api/vehicule-loueurs/{id}", vehiculeLoueur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(vehiculeLoueur.getId().intValue()))
            .andExpect(jsonPath("$.immatriculation").value(DEFAULT_IMMATRICULATION.toString()))
            .andExpect(jsonPath("$.totalPrix").value(DEFAULT_TOTAL_PRIX.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingVehiculeLoueur() throws Exception {
        // Get the vehiculeLoueur
        restVehiculeLoueurMockMvc.perform(get("/api/vehicule-loueurs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVehiculeLoueur() throws Exception {
        // Initialize the database
        vehiculeLoueurRepository.saveAndFlush(vehiculeLoueur);
        vehiculeLoueurSearchRepository.save(vehiculeLoueur);
        int databaseSizeBeforeUpdate = vehiculeLoueurRepository.findAll().size();

        // Update the vehiculeLoueur
        VehiculeLoueur updatedVehiculeLoueur = vehiculeLoueurRepository.findOne(vehiculeLoueur.getId());
        updatedVehiculeLoueur
            .immatriculation(UPDATED_IMMATRICULATION)
            .totalPrix(UPDATED_TOTAL_PRIX);
        VehiculeLoueurDTO vehiculeLoueurDTO = vehiculeLoueurMapper.toDto(updatedVehiculeLoueur);

        restVehiculeLoueurMockMvc.perform(put("/api/vehicule-loueurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehiculeLoueurDTO)))
            .andExpect(status().isOk());

        // Validate the VehiculeLoueur in the database
        List<VehiculeLoueur> vehiculeLoueurList = vehiculeLoueurRepository.findAll();
        assertThat(vehiculeLoueurList).hasSize(databaseSizeBeforeUpdate);
        VehiculeLoueur testVehiculeLoueur = vehiculeLoueurList.get(vehiculeLoueurList.size() - 1);
        assertThat(testVehiculeLoueur.getImmatriculation()).isEqualTo(UPDATED_IMMATRICULATION);
        assertThat(testVehiculeLoueur.getTotalPrix()).isEqualTo(UPDATED_TOTAL_PRIX);

        // Validate the VehiculeLoueur in Elasticsearch
        VehiculeLoueur vehiculeLoueurEs = vehiculeLoueurSearchRepository.findOne(testVehiculeLoueur.getId());
        assertThat(vehiculeLoueurEs).isEqualToComparingFieldByField(testVehiculeLoueur);
    }

    @Test
    @Transactional
    public void updateNonExistingVehiculeLoueur() throws Exception {
        int databaseSizeBeforeUpdate = vehiculeLoueurRepository.findAll().size();

        // Create the VehiculeLoueur
        VehiculeLoueurDTO vehiculeLoueurDTO = vehiculeLoueurMapper.toDto(vehiculeLoueur);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restVehiculeLoueurMockMvc.perform(put("/api/vehicule-loueurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehiculeLoueurDTO)))
            .andExpect(status().isCreated());

        // Validate the VehiculeLoueur in the database
        List<VehiculeLoueur> vehiculeLoueurList = vehiculeLoueurRepository.findAll();
        assertThat(vehiculeLoueurList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteVehiculeLoueur() throws Exception {
        // Initialize the database
        vehiculeLoueurRepository.saveAndFlush(vehiculeLoueur);
        vehiculeLoueurSearchRepository.save(vehiculeLoueur);
        int databaseSizeBeforeDelete = vehiculeLoueurRepository.findAll().size();

        // Get the vehiculeLoueur
        restVehiculeLoueurMockMvc.perform(delete("/api/vehicule-loueurs/{id}", vehiculeLoueur.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean vehiculeLoueurExistsInEs = vehiculeLoueurSearchRepository.exists(vehiculeLoueur.getId());
        assertThat(vehiculeLoueurExistsInEs).isFalse();

        // Validate the database is empty
        List<VehiculeLoueur> vehiculeLoueurList = vehiculeLoueurRepository.findAll();
        assertThat(vehiculeLoueurList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchVehiculeLoueur() throws Exception {
        // Initialize the database
        vehiculeLoueurRepository.saveAndFlush(vehiculeLoueur);
        vehiculeLoueurSearchRepository.save(vehiculeLoueur);

        // Search the vehiculeLoueur
        restVehiculeLoueurMockMvc.perform(get("/api/_search/vehicule-loueurs?query=id:" + vehiculeLoueur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vehiculeLoueur.getId().intValue())))
            .andExpect(jsonPath("$.[*].immatriculation").value(hasItem(DEFAULT_IMMATRICULATION.toString())))
            .andExpect(jsonPath("$.[*].totalPrix").value(hasItem(DEFAULT_TOTAL_PRIX.doubleValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VehiculeLoueur.class);
        VehiculeLoueur vehiculeLoueur1 = new VehiculeLoueur();
        vehiculeLoueur1.setId(1L);
        VehiculeLoueur vehiculeLoueur2 = new VehiculeLoueur();
        vehiculeLoueur2.setId(vehiculeLoueur1.getId());
        assertThat(vehiculeLoueur1).isEqualTo(vehiculeLoueur2);
        vehiculeLoueur2.setId(2L);
        assertThat(vehiculeLoueur1).isNotEqualTo(vehiculeLoueur2);
        vehiculeLoueur1.setId(null);
        assertThat(vehiculeLoueur1).isNotEqualTo(vehiculeLoueur2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VehiculeLoueurDTO.class);
        VehiculeLoueurDTO vehiculeLoueurDTO1 = new VehiculeLoueurDTO();
        vehiculeLoueurDTO1.setId(1L);
        VehiculeLoueurDTO vehiculeLoueurDTO2 = new VehiculeLoueurDTO();
        assertThat(vehiculeLoueurDTO1).isNotEqualTo(vehiculeLoueurDTO2);
        vehiculeLoueurDTO2.setId(vehiculeLoueurDTO1.getId());
        assertThat(vehiculeLoueurDTO1).isEqualTo(vehiculeLoueurDTO2);
        vehiculeLoueurDTO2.setId(2L);
        assertThat(vehiculeLoueurDTO1).isNotEqualTo(vehiculeLoueurDTO2);
        vehiculeLoueurDTO1.setId(null);
        assertThat(vehiculeLoueurDTO1).isNotEqualTo(vehiculeLoueurDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(vehiculeLoueurMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(vehiculeLoueurMapper.fromId(null)).isNull();
    }
}
