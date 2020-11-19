package com.gaconnecte.auxilium.web.rest;

import com.gaconnecte.auxilium.AuxiliumApp;

import com.gaconnecte.auxilium.domain.RefRemorqueur;
import com.gaconnecte.auxilium.domain.PersonneMorale;
import com.gaconnecte.auxilium.repository.RefRemorqueurRepository;
import com.gaconnecte.auxilium.service.RefRemorqueurService;
import com.gaconnecte.auxilium.service.HistoryService;
import com.gaconnecte.auxilium.repository.search.RefRemorqueurSearchRepository;
import com.gaconnecte.auxilium.service.dto.RefRemorqueurDTO;
import com.gaconnecte.auxilium.service.mapper.RefRemorqueurMapper;
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
 * Test class for the RefRemorqueurResource REST controller.
 *
 * @see RefRemorqueurResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuxiliumApp.class)
public class RefRemorqueurResourceIntTest {

    private static final Boolean DEFAULT_HAS_HABILLAGE = false;
    private static final Boolean UPDATED_HAS_HABILLAGE = true;
    
    private static final Boolean DEFAULT_IS_BLOQUE = false;
    private static final Boolean UPDATED_IS_BLOQUE = true;


    private static final Boolean DEFAULT_IS_DELETE = false;
    private static final Boolean UPDATED_IS_DELETE = true;

    private static final Integer DEFAULT_NOMBRE_CAMION = 1;
    private static final Integer UPDATED_NOMBRE_CAMION = 2;

    @Autowired
    private RefRemorqueurRepository refRemorqueurRepository;

    @Autowired
    private RefRemorqueurMapper refRemorqueurMapper;

    @Autowired
    private RefRemorqueurService refRemorqueurService;
    @Autowired
    private HistoryService historyService;
   // @Autowired
  //  private RefRemorqueurSearchRepository refRemorqueurSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRefRemorqueurMockMvc;

    private RefRemorqueur refRemorqueur;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
     //   RefRemorqueurResource refRemorqueurResource = new RefRemorqueurResource(refRemorqueurService, historyService);
        this.restRefRemorqueurMockMvc = MockMvcBuilders.standaloneSetup(null)
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
    public static RefRemorqueur createEntity(EntityManager em) {
        RefRemorqueur refRemorqueur = new RefRemorqueur()
           
            .isDelete(DEFAULT_IS_DELETE)
            .nombreCamion(DEFAULT_NOMBRE_CAMION);
        // Add required entity
        PersonneMorale societe = null;
        em.persist(societe);
        em.flush();
        refRemorqueur.setSociete(societe);
        return refRemorqueur;
    }

    @Before
    public void initTest() {
       // refRemorqueurSearchRepository.deleteAll();
        refRemorqueur = createEntity(em);
    }

    @Test
    @Transactional
    public void createRefRemorqueur() throws Exception {
        int databaseSizeBeforeCreate = refRemorqueurRepository.findAll().size();

        // Create the RefRemorqueur
        RefRemorqueurDTO refRemorqueurDTO = refRemorqueurMapper.toDto(refRemorqueur);
        restRefRemorqueurMockMvc.perform(post("/api/ref-remorqueurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refRemorqueurDTO)))
            .andExpect(status().isCreated());

        // Validate the RefRemorqueur in the database
        List<RefRemorqueur> refRemorqueurList = refRemorqueurRepository.findAll();
        assertThat(refRemorqueurList).hasSize(databaseSizeBeforeCreate + 1);
        RefRemorqueur testRefRemorqueur = refRemorqueurList.get(refRemorqueurList.size() - 1);
  
        //assertThat(testRefRemorqueur.isIsDelete()).isEqualTo(DEFAULT_IS_DELETE);
        assertThat(testRefRemorqueur.getNombreCamion()).isEqualTo(DEFAULT_NOMBRE_CAMION);

        // Validate the RefRemorqueur in Elasticsearch
        //RefRemorqueur refRemorqueurEs = refRemorqueurSearchRepository.findOne(testRefRemorqueur.getId());
       // assertThat(refRemorqueurEs).isEqualToComparingFieldByField(testRefRemorqueur);
    }

    @Test
    @Transactional
    public void createRefRemorqueurWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = refRemorqueurRepository.findAll().size();

        // Create the RefRemorqueur with an existing ID
        refRemorqueur.setId(1L);
        RefRemorqueurDTO refRemorqueurDTO = refRemorqueurMapper.toDto(refRemorqueur);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRefRemorqueurMockMvc.perform(post("/api/ref-remorqueurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refRemorqueurDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<RefRemorqueur> refRemorqueurList = refRemorqueurRepository.findAll();
        assertThat(refRemorqueurList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkHasHabillageIsRequired() throws Exception {
        int databaseSizeBeforeTest = refRemorqueurRepository.findAll().size();
        // set the field null
    

        // Create the RefRemorqueur, which fails.
        RefRemorqueurDTO refRemorqueurDTO = refRemorqueurMapper.toDto(refRemorqueur);

        restRefRemorqueurMockMvc.perform(post("/api/ref-remorqueurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refRemorqueurDTO)))
            .andExpect(status().isBadRequest());

        List<RefRemorqueur> refRemorqueurList = refRemorqueurRepository.findAll();
        assertThat(refRemorqueurList).hasSize(databaseSizeBeforeTest);
    }


    @Test
    @Transactional
    public void checkNombreCamionIsRequired() throws Exception {
        int databaseSizeBeforeTest = refRemorqueurRepository.findAll().size();
        // set the field null
        refRemorqueur.setNombreCamion(null);

        // Create the RefRemorqueur, which fails.
        RefRemorqueurDTO refRemorqueurDTO = refRemorqueurMapper.toDto(refRemorqueur);

        restRefRemorqueurMockMvc.perform(post("/api/ref-remorqueurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refRemorqueurDTO)))
            .andExpect(status().isBadRequest());

        List<RefRemorqueur> refRemorqueurList = refRemorqueurRepository.findAll();
        assertThat(refRemorqueurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRefRemorqueurs() throws Exception {
        // Initialize the database
        refRemorqueurRepository.saveAndFlush(refRemorqueur);

        // Get all the refRemorqueurList
        restRefRemorqueurMockMvc.perform(get("/api/ref-remorqueurs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(refRemorqueur.getId().intValue())))
            .andExpect(jsonPath("$.[*].hasHabillage").value(hasItem(DEFAULT_HAS_HABILLAGE.booleanValue())))
            .andExpect(jsonPath("$.[*].isDelete").value(hasItem(DEFAULT_IS_DELETE.booleanValue())))
            .andExpect(jsonPath("$.[*].nombreCamion").value(hasItem(DEFAULT_NOMBRE_CAMION)));
    }

    @Test
    @Transactional
    public void getRefRemorqueur() throws Exception {
        // Initialize the database
        refRemorqueurRepository.saveAndFlush(refRemorqueur);

        // Get the refRemorqueur
        restRefRemorqueurMockMvc.perform(get("/api/ref-remorqueurs/{id}", refRemorqueur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(refRemorqueur.getId().intValue()))
            .andExpect(jsonPath("$.hasHabillage").value(DEFAULT_HAS_HABILLAGE.booleanValue()))
            .andExpect(jsonPath("$.isDelete").value(DEFAULT_IS_DELETE.booleanValue()))
            .andExpect(jsonPath("$.nombreCamion").value(DEFAULT_NOMBRE_CAMION))
            .andExpect(jsonPath("$.isBloque").value(DEFAULT_IS_BLOQUE.booleanValue()));

    }

    @Test
    @Transactional
    public void getNonExistingRefRemorqueur() throws Exception {
        // Get the refRemorqueur
        restRefRemorqueurMockMvc.perform(get("/api/ref-remorqueurs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRefRemorqueur() throws Exception {
        // Initialize the database
        refRemorqueurRepository.saveAndFlush(refRemorqueur);
       // refRemorqueurSearchRepository.save(refRemorqueur);
        int databaseSizeBeforeUpdate = refRemorqueurRepository.findAll().size();

        // Update the refRemorqueur
        RefRemorqueur updatedRefRemorqueur = refRemorqueurRepository.findOne(refRemorqueur.getId());
        updatedRefRemorqueur
           
            .isBloque(UPDATED_IS_BLOQUE)
            .isDelete(UPDATED_IS_DELETE)
            .nombreCamion(UPDATED_NOMBRE_CAMION);
        RefRemorqueurDTO refRemorqueurDTO = refRemorqueurMapper.toDto(updatedRefRemorqueur);

        restRefRemorqueurMockMvc.perform(put("/api/ref-remorqueurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refRemorqueurDTO)))
            .andExpect(status().isOk());

        // Validate the RefRemorqueur in the database
        List<RefRemorqueur> refRemorqueurList = refRemorqueurRepository.findAll();
        assertThat(refRemorqueurList).hasSize(databaseSizeBeforeUpdate);
        RefRemorqueur testRefRemorqueur = refRemorqueurList.get(refRemorqueurList.size() - 1);
      
        //assertThat(testRefRemorqueur.isIsDelete()).isEqualTo(UPDATED_IS_DELETE);
        assertThat(testRefRemorqueur.getNombreCamion()).isEqualTo(UPDATED_NOMBRE_CAMION);

        // Validate the RefRemorqueur in Elasticsearch
        //RefRemorqueur refRemorqueurEs = refRemorqueurSearchRepository.findOne(testRefRemorqueur.getId());
       // assertThat(refRemorqueurEs).isEqualToComparingFieldByField(testRefRemorqueur);
    }

    @Test
    @Transactional
    public void updateNonExistingRefRemorqueur() throws Exception {
        int databaseSizeBeforeUpdate = refRemorqueurRepository.findAll().size();

        // Create the RefRemorqueur
        RefRemorqueurDTO refRemorqueurDTO = refRemorqueurMapper.toDto(refRemorqueur);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRefRemorqueurMockMvc.perform(put("/api/ref-remorqueurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refRemorqueurDTO)))
            .andExpect(status().isCreated());

        // Validate the RefRemorqueur in the database
        List<RefRemorqueur> refRemorqueurList = refRemorqueurRepository.findAll();
        assertThat(refRemorqueurList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRefRemorqueur() throws Exception {
        // Initialize the database
        refRemorqueurRepository.saveAndFlush(refRemorqueur);
      //  refRemorqueurSearchRepository.save(refRemorqueur);
        int databaseSizeBeforeDelete = refRemorqueurRepository.findAll().size();

        // Get the refRemorqueur
        restRefRemorqueurMockMvc.perform(delete("/api/ref-remorqueurs/{id}", refRemorqueur.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
       // boolean refRemorqueurExistsInEs = refRemorqueurSearchRepository.exists(refRemorqueur.getId());
        //assertThat(refRemorqueurExistsInEs).isFalse();

        // Validate the database is empty
        List<RefRemorqueur> refRemorqueurList = refRemorqueurRepository.findAll();
        //assertThat(refRemorqueurList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchRefRemorqueur() throws Exception {
        // Initialize the database
        refRemorqueurRepository.saveAndFlush(refRemorqueur);
        //refRemorqueurSearchRepository.save(refRemorqueur);

        // Search the refRemorqueur
        restRefRemorqueurMockMvc.perform(get("/api/_search/ref-remorqueurs?query=id:" + refRemorqueur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(refRemorqueur.getId().intValue())))
            .andExpect(jsonPath("$.[*].hasHabillage").value(hasItem(DEFAULT_HAS_HABILLAGE.booleanValue())))
            .andExpect(jsonPath("$.[*].isBloque").value(hasItem(DEFAULT_IS_BLOQUE.booleanValue())))
            .andExpect(jsonPath("$.[*].isDelete").value(hasItem(DEFAULT_IS_DELETE.booleanValue())))
            .andExpect(jsonPath("$.[*].nombreCamion").value(hasItem(DEFAULT_NOMBRE_CAMION)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RefRemorqueur.class);
        RefRemorqueur refRemorqueur1 = new RefRemorqueur();
        refRemorqueur1.setId(1L);
        RefRemorqueur refRemorqueur2 = new RefRemorqueur();
        refRemorqueur2.setId(refRemorqueur1.getId());
        assertThat(refRemorqueur1).isEqualTo(refRemorqueur2);
        refRemorqueur2.setId(2L);
        assertThat(refRemorqueur1).isNotEqualTo(refRemorqueur2);
        refRemorqueur1.setId(null);
        assertThat(refRemorqueur1).isNotEqualTo(refRemorqueur2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RefRemorqueurDTO.class);
        RefRemorqueurDTO refRemorqueurDTO1 = new RefRemorqueurDTO();
        refRemorqueurDTO1.setId(1L);
        RefRemorqueurDTO refRemorqueurDTO2 = new RefRemorqueurDTO();
        assertThat(refRemorqueurDTO1).isNotEqualTo(refRemorqueurDTO2);
        refRemorqueurDTO2.setId(refRemorqueurDTO1.getId());
        assertThat(refRemorqueurDTO1).isEqualTo(refRemorqueurDTO2);
        refRemorqueurDTO2.setId(2L);
        assertThat(refRemorqueurDTO1).isNotEqualTo(refRemorqueurDTO2);
        refRemorqueurDTO1.setId(null);
        assertThat(refRemorqueurDTO1).isNotEqualTo(refRemorqueurDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(refRemorqueurMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(refRemorqueurMapper.fromId(null)).isNull();
    }
}
