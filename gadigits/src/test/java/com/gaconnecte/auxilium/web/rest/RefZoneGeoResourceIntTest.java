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
import com.gaconnecte.auxilium.domain.RefZoneGeo;
import com.gaconnecte.auxilium.repository.RefZoneGeoRepository;
import com.gaconnecte.auxilium.repository.search.RefZoneGeoSearchRepository;
import com.gaconnecte.auxilium.service.dto.RefZoneGeoDTO;
import com.gaconnecte.auxilium.service.mapper.RefZoneGeoMapper;
import com.gaconnecte.auxilium.web.rest.errors.ExceptionTranslator;

/**
 * Test class for the RefZoneGeoResource REST controller.
 *
 * @see RefZoneGeoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuxiliumApp.class)
public class RefZoneGeoResourceIntTest {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    @Autowired
    private RefZoneGeoRepository refZoneGeoRepository;

    @Autowired
    private RefZoneGeoMapper refZoneGeoMapper;


  //  private RefZoneGeoService refZoneGeoService;

    @Autowired
    private RefZoneGeoSearchRepository refZoneGeoSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRefZoneGeoMockMvc;

    private RefZoneGeo refZoneGeo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
       // RefZoneGeoResource refZoneGeoResource = new RefZoneGeoResource(refZoneGeoService);
        this.restRefZoneGeoMockMvc = MockMvcBuilders.standaloneSetup(null)
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
    public static RefZoneGeo createEntity(EntityManager em) {
        RefZoneGeo refZoneGeo = new RefZoneGeo()
            .libelle(DEFAULT_LIBELLE);
        // Add required entity
     //   SysGouvernorat gouvernorat = SysGouvernoratResourceIntTest.createEntity(em);
        em.persist(null);
        em.flush();
        refZoneGeo.getGouvernorats().add(null);
        return refZoneGeo;
    }

    @Before
    public void initTest() {
        refZoneGeoSearchRepository.deleteAll();
        refZoneGeo = createEntity(em);
    }

    @Test
    @Transactional
    public void createRefZoneGeo() throws Exception {
        int databaseSizeBeforeCreate = refZoneGeoRepository.findAll().size();

        // Create the RefZoneGeo
        RefZoneGeoDTO refZoneGeoDTO = refZoneGeoMapper.toDto(refZoneGeo);
        restRefZoneGeoMockMvc.perform(post("/api/ref-zone-geos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refZoneGeoDTO)))
            .andExpect(status().isCreated());

        // Validate the RefZoneGeo in the database
        List<RefZoneGeo> refZoneGeoList = refZoneGeoRepository.findAll();
        assertThat(refZoneGeoList).hasSize(databaseSizeBeforeCreate + 1);
        RefZoneGeo testRefZoneGeo = refZoneGeoList.get(refZoneGeoList.size() - 1);
        assertThat(testRefZoneGeo.getLibelle()).isEqualTo(DEFAULT_LIBELLE);

        // Validate the RefZoneGeo in Elasticsearch
        RefZoneGeo refZoneGeoEs = refZoneGeoSearchRepository.findOne(testRefZoneGeo.getId());
        assertThat(refZoneGeoEs).isEqualToComparingFieldByField(testRefZoneGeo);
    }

    @Test
    @Transactional
    public void createRefZoneGeoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = refZoneGeoRepository.findAll().size();

        // Create the RefZoneGeo with an existing ID
        refZoneGeo.setId(1L);
        RefZoneGeoDTO refZoneGeoDTO = refZoneGeoMapper.toDto(refZoneGeo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRefZoneGeoMockMvc.perform(post("/api/ref-zone-geos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refZoneGeoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<RefZoneGeo> refZoneGeoList = refZoneGeoRepository.findAll();
        assertThat(refZoneGeoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = refZoneGeoRepository.findAll().size();
        // set the field null
        refZoneGeo.setLibelle(null);

        // Create the RefZoneGeo, which fails.
        RefZoneGeoDTO refZoneGeoDTO = refZoneGeoMapper.toDto(refZoneGeo);

        restRefZoneGeoMockMvc.perform(post("/api/ref-zone-geos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refZoneGeoDTO)))
            .andExpect(status().isBadRequest());

        List<RefZoneGeo> refZoneGeoList = refZoneGeoRepository.findAll();
        assertThat(refZoneGeoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRefZoneGeos() throws Exception {
        // Initialize the database
        refZoneGeoRepository.saveAndFlush(refZoneGeo);

        // Get all the refZoneGeoList
        restRefZoneGeoMockMvc.perform(get("/api/ref-zone-geos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(refZoneGeo.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())));
    }

    @Test
    @Transactional
    public void getRefZoneGeo() throws Exception {
        // Initialize the database
        refZoneGeoRepository.saveAndFlush(refZoneGeo);

        // Get the refZoneGeo
        restRefZoneGeoMockMvc.perform(get("/api/ref-zone-geos/{id}", refZoneGeo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(refZoneGeo.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRefZoneGeo() throws Exception {
        // Get the refZoneGeo
        restRefZoneGeoMockMvc.perform(get("/api/ref-zone-geos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRefZoneGeo() throws Exception {
        // Initialize the database
        refZoneGeoRepository.saveAndFlush(refZoneGeo);
        refZoneGeoSearchRepository.save(refZoneGeo);
        int databaseSizeBeforeUpdate = refZoneGeoRepository.findAll().size();

        // Update the refZoneGeo
        RefZoneGeo updatedRefZoneGeo = refZoneGeoRepository.findOne(refZoneGeo.getId());
        updatedRefZoneGeo
            .libelle(UPDATED_LIBELLE);
        RefZoneGeoDTO refZoneGeoDTO = refZoneGeoMapper.toDto(updatedRefZoneGeo);

        restRefZoneGeoMockMvc.perform(put("/api/ref-zone-geos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refZoneGeoDTO)))
            .andExpect(status().isOk());

        // Validate the RefZoneGeo in the database
        List<RefZoneGeo> refZoneGeoList = refZoneGeoRepository.findAll();
        assertThat(refZoneGeoList).hasSize(databaseSizeBeforeUpdate);
        RefZoneGeo testRefZoneGeo = refZoneGeoList.get(refZoneGeoList.size() - 1);
        assertThat(testRefZoneGeo.getLibelle()).isEqualTo(UPDATED_LIBELLE);

        // Validate the RefZoneGeo in Elasticsearch
        RefZoneGeo refZoneGeoEs = refZoneGeoSearchRepository.findOne(testRefZoneGeo.getId());
        assertThat(refZoneGeoEs).isEqualToComparingFieldByField(testRefZoneGeo);
    }

    @Test
    @Transactional
    public void updateNonExistingRefZoneGeo() throws Exception {
        int databaseSizeBeforeUpdate = refZoneGeoRepository.findAll().size();

        // Create the RefZoneGeo
        RefZoneGeoDTO refZoneGeoDTO = refZoneGeoMapper.toDto(refZoneGeo);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRefZoneGeoMockMvc.perform(put("/api/ref-zone-geos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refZoneGeoDTO)))
            .andExpect(status().isCreated());

        // Validate the RefZoneGeo in the database
        List<RefZoneGeo> refZoneGeoList = refZoneGeoRepository.findAll();
        assertThat(refZoneGeoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRefZoneGeo() throws Exception {
        // Initialize the database
        refZoneGeoRepository.saveAndFlush(refZoneGeo);
        refZoneGeoSearchRepository.save(refZoneGeo);
        int databaseSizeBeforeDelete = refZoneGeoRepository.findAll().size();

        // Get the refZoneGeo
        restRefZoneGeoMockMvc.perform(delete("/api/ref-zone-geos/{id}", refZoneGeo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean refZoneGeoExistsInEs = refZoneGeoSearchRepository.exists(refZoneGeo.getId());
        assertThat(refZoneGeoExistsInEs).isFalse();

        // Validate the database is empty
        List<RefZoneGeo> refZoneGeoList = refZoneGeoRepository.findAll();
        assertThat(refZoneGeoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchRefZoneGeo() throws Exception {
        // Initialize the database
        refZoneGeoRepository.saveAndFlush(refZoneGeo);
        refZoneGeoSearchRepository.save(refZoneGeo);

        // Search the refZoneGeo
        restRefZoneGeoMockMvc.perform(get("/api/_search/ref-zone-geos?query=id:" + refZoneGeo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(refZoneGeo.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RefZoneGeo.class);
        RefZoneGeo refZoneGeo1 = new RefZoneGeo();
        refZoneGeo1.setId(1L);
        RefZoneGeo refZoneGeo2 = new RefZoneGeo();
        refZoneGeo2.setId(refZoneGeo1.getId());
        assertThat(refZoneGeo1).isEqualTo(refZoneGeo2);
        refZoneGeo2.setId(2L);
        assertThat(refZoneGeo1).isNotEqualTo(refZoneGeo2);
        refZoneGeo1.setId(null);
        assertThat(refZoneGeo1).isNotEqualTo(refZoneGeo2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RefZoneGeoDTO.class);
        RefZoneGeoDTO refZoneGeoDTO1 = new RefZoneGeoDTO();
        refZoneGeoDTO1.setId(1L);
        RefZoneGeoDTO refZoneGeoDTO2 = new RefZoneGeoDTO();
        assertThat(refZoneGeoDTO1).isNotEqualTo(refZoneGeoDTO2);
        refZoneGeoDTO2.setId(refZoneGeoDTO1.getId());
        assertThat(refZoneGeoDTO1).isEqualTo(refZoneGeoDTO2);
        refZoneGeoDTO2.setId(2L);
        assertThat(refZoneGeoDTO1).isNotEqualTo(refZoneGeoDTO2);
        refZoneGeoDTO1.setId(null);
        assertThat(refZoneGeoDTO1).isNotEqualTo(refZoneGeoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(refZoneGeoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(refZoneGeoMapper.fromId(null)).isNull();
    }
}
