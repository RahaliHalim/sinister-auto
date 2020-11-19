package com.gaconnecte.auxilium.web.rest;

import com.gaconnecte.auxilium.AuxiliumApp;

import com.gaconnecte.auxilium.domain.PointChoc;
import com.gaconnecte.auxilium.repository.PointChocRepository;
import com.gaconnecte.auxilium.service.PointChocService;
import com.gaconnecte.auxilium.repository.search.PointChocSearchRepository;
import com.gaconnecte.auxilium.service.dto.PointChocDTO;
import com.gaconnecte.auxilium.service.mapper.PointChocMapper;
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
 * Test class for the PointChocResource REST controller.
 *
 * @see PointChocResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuxiliumApp.class)
public class PointChocResourceIntTest {

    private static final Boolean DEFAULT_FACE_AV = false;
    private static final Boolean UPDATED_FACE_AV = true;

    private static final Boolean DEFAULT_TIERS_AV_D = false;
    private static final Boolean UPDATED_TIERS_AV_D = true;

    private static final Boolean DEFAULT_TIERS_LAT_D = false;
    private static final Boolean UPDATED_TIERS_LAT_D = true;

    private static final Boolean DEFAULT_LAT_D = false;
    private static final Boolean UPDATED_LAT_D = true;

    private static final Boolean DEFAULT_TIERS_AR_D = false;
    private static final Boolean UPDATED_TIERS_AR_D = true;

    private static final Boolean DEFAULT_TIERS_LAT_G = false;
    private static final Boolean UPDATED_TIERS_LAT_G = true;

    private static final Boolean DEFAULT_TIERS_AR_G = false;
    private static final Boolean UPDATED_TIERS_AR_G = true;

    private static final Boolean DEFAULT_LAT_G = false;
    private static final Boolean UPDATED_LAT_G = true;

    private static final Boolean DEFAULT_FACE_AR = false;
    private static final Boolean UPDATED_FACE_AR = true;

    private static final Boolean DEFAULT_DIVERS_CHOC = false;
    private static final Boolean UPDATED_DIVERS_CHOC = true;

    private static final Boolean DEFAULT_PARE_BRISE = false;
    private static final Boolean UPDATED_PARE_BRISE = true;

    private static final Boolean DEFAULT_TRIANGLE_AV_D = false;
    private static final Boolean UPDATED_TRIANGLE_AV_D = true;

    private static final Boolean DEFAULT_TRIANGLE_AR_D = false;
    private static final Boolean UPDATED_TRIANGLE_AR_D = true;

    private static final Boolean DEFAULT_TRIANGLE_AV_G = false;
    private static final Boolean UPDATED_TRIANGLE_AV_G = true;

    private static final Boolean DEFAULT_TRIANGLE_AR_G = false;
    private static final Boolean UPDATED_TRIANGLE_AR_G = true;

    private static final Boolean DEFAULT_VITRE_AV_D = false;
    private static final Boolean UPDATED_VITRE_AV_D = true;

    private static final Boolean DEFAULT_VITRE_AR_D = false;
    private static final Boolean UPDATED_VITRE_AR_D = true;

    private static final Boolean DEFAULT_VITRE_AV_G = false;
    private static final Boolean UPDATED_VITRE_AV_G = true;

    private static final Boolean DEFAULT_VITRE_AR_G = false;
    private static final Boolean UPDATED_VITRE_AR_G = true;

    private static final Boolean DEFAULT_LUNETTE_AR = false;
    private static final Boolean UPDATED_LUNETTE_AR = true;

    private static final Boolean DEFAULT_TIERS_AV_G = false;
    private static final Boolean UPDATED_TIERS_AV_G = true;

    @Autowired
    private PointChocRepository pointChocRepository;

    @Autowired
    private PointChocMapper pointChocMapper;

    @Autowired
    private PointChocService pointChocService;

    @Autowired
    private PointChocSearchRepository pointChocSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPointChocMockMvc;

    private PointChoc pointChoc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PointChocResource pointChocResource = new PointChocResource(pointChocService);
        this.restPointChocMockMvc = MockMvcBuilders.standaloneSetup(pointChocResource)
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
    public static PointChoc createEntity(EntityManager em) {
        PointChoc pointChoc = null ;
       
        return pointChoc;
    }

    @Before
    public void initTest() {
        pointChocSearchRepository.deleteAll();
        pointChoc = createEntity(em);
    }

    @Test
    @Transactional
    public void createPointChoc() throws Exception {
        int databaseSizeBeforeCreate = pointChocRepository.findAll().size();

        // Create the PointChoc
        PointChocDTO pointChocDTO = pointChocMapper.toDto(pointChoc);
        restPointChocMockMvc.perform(post("/api/point-chocs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pointChocDTO)))
            .andExpect(status().isCreated());

        // Validate the PointChoc in the database
        List<PointChoc> pointChocList = pointChocRepository.findAll();
        assertThat(pointChocList).hasSize(databaseSizeBeforeCreate + 1);
        PointChoc testPointChoc = pointChocList.get(pointChocList.size() - 1);
      

        // Validate the PointChoc in Elasticsearch
        PointChoc pointChocEs = pointChocSearchRepository.findOne(testPointChoc.getId());
        assertThat(pointChocEs).isEqualToComparingFieldByField(testPointChoc);
    }

    @Test
    @Transactional
    public void createPointChocWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pointChocRepository.findAll().size();

        // Create the PointChoc with an existing ID
        pointChoc.setId(1L);
        PointChocDTO pointChocDTO = pointChocMapper.toDto(pointChoc);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPointChocMockMvc.perform(post("/api/point-chocs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pointChocDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PointChoc> pointChocList = pointChocRepository.findAll();
        assertThat(pointChocList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPointChocs() throws Exception {
        // Initialize the database
        pointChocRepository.saveAndFlush(pointChoc);

        // Get all the pointChocList
        restPointChocMockMvc.perform(get("/api/point-chocs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pointChoc.getId().intValue())))
            .andExpect(jsonPath("$.[*].faceAv").value(hasItem(DEFAULT_FACE_AV.booleanValue())))
            .andExpect(jsonPath("$.[*].tiersAvD").value(hasItem(DEFAULT_TIERS_AV_D.booleanValue())))
            .andExpect(jsonPath("$.[*].tiersLatD").value(hasItem(DEFAULT_TIERS_LAT_D.booleanValue())))
            .andExpect(jsonPath("$.[*].latD").value(hasItem(DEFAULT_LAT_D.booleanValue())))
            .andExpect(jsonPath("$.[*].tiersArD").value(hasItem(DEFAULT_TIERS_AR_D.booleanValue())))
            .andExpect(jsonPath("$.[*].tiersLatG").value(hasItem(DEFAULT_TIERS_LAT_G.booleanValue())))
            .andExpect(jsonPath("$.[*].tiersArG").value(hasItem(DEFAULT_TIERS_AR_G.booleanValue())))
            .andExpect(jsonPath("$.[*].latG").value(hasItem(DEFAULT_LAT_G.booleanValue())))
            .andExpect(jsonPath("$.[*].faceAr").value(hasItem(DEFAULT_FACE_AR.booleanValue())))
            .andExpect(jsonPath("$.[*].diversChoc").value(hasItem(DEFAULT_DIVERS_CHOC.booleanValue())))
            .andExpect(jsonPath("$.[*].pareBrise").value(hasItem(DEFAULT_PARE_BRISE.booleanValue())))
            .andExpect(jsonPath("$.[*].triangleAvD").value(hasItem(DEFAULT_TRIANGLE_AV_D.booleanValue())))
            .andExpect(jsonPath("$.[*].triangleArD").value(hasItem(DEFAULT_TRIANGLE_AR_D.booleanValue())))
            .andExpect(jsonPath("$.[*].triangleAvG").value(hasItem(DEFAULT_TRIANGLE_AV_G.booleanValue())))
            .andExpect(jsonPath("$.[*].triangleArG").value(hasItem(DEFAULT_TRIANGLE_AR_G.booleanValue())))
            .andExpect(jsonPath("$.[*].vitreAvD").value(hasItem(DEFAULT_VITRE_AV_D.booleanValue())))
            .andExpect(jsonPath("$.[*].vitreArD").value(hasItem(DEFAULT_VITRE_AR_D.booleanValue())))
            .andExpect(jsonPath("$.[*].vitreAvG").value(hasItem(DEFAULT_VITRE_AV_G.booleanValue())))
            .andExpect(jsonPath("$.[*].vitreArG").value(hasItem(DEFAULT_VITRE_AR_G.booleanValue())))
            .andExpect(jsonPath("$.[*].lunetteAr").value(hasItem(DEFAULT_LUNETTE_AR.booleanValue())))
            .andExpect(jsonPath("$.[*].tiersAvG").value(hasItem(DEFAULT_TIERS_AV_G.booleanValue())));
    }

    @Test
    @Transactional
    public void getPointChoc() throws Exception {
        // Initialize the database
        pointChocRepository.saveAndFlush(pointChoc);

        // Get the pointChoc
        restPointChocMockMvc.perform(get("/api/point-chocs/{id}", pointChoc.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pointChoc.getId().intValue()))
            .andExpect(jsonPath("$.faceAv").value(DEFAULT_FACE_AV.booleanValue()))
            .andExpect(jsonPath("$.tiersAvD").value(DEFAULT_TIERS_AV_D.booleanValue()))
            .andExpect(jsonPath("$.tiersLatD").value(DEFAULT_TIERS_LAT_D.booleanValue()))
            .andExpect(jsonPath("$.latD").value(DEFAULT_LAT_D.booleanValue()))
            .andExpect(jsonPath("$.tiersArD").value(DEFAULT_TIERS_AR_D.booleanValue()))
            .andExpect(jsonPath("$.tiersLatG").value(DEFAULT_TIERS_LAT_G.booleanValue()))
            .andExpect(jsonPath("$.tiersArG").value(DEFAULT_TIERS_AR_G.booleanValue()))
            .andExpect(jsonPath("$.latG").value(DEFAULT_LAT_G.booleanValue()))
            .andExpect(jsonPath("$.faceAr").value(DEFAULT_FACE_AR.booleanValue()))
            .andExpect(jsonPath("$.diversChoc").value(DEFAULT_DIVERS_CHOC.booleanValue()))
            .andExpect(jsonPath("$.pareBrise").value(DEFAULT_PARE_BRISE.booleanValue()))
            .andExpect(jsonPath("$.triangleAvD").value(DEFAULT_TRIANGLE_AV_D.booleanValue()))
            .andExpect(jsonPath("$.triangleArD").value(DEFAULT_TRIANGLE_AR_D.booleanValue()))
            .andExpect(jsonPath("$.triangleAvG").value(DEFAULT_TRIANGLE_AV_G.booleanValue()))
            .andExpect(jsonPath("$.triangleArG").value(DEFAULT_TRIANGLE_AR_G.booleanValue()))
            .andExpect(jsonPath("$.vitreAvD").value(DEFAULT_VITRE_AV_D.booleanValue()))
            .andExpect(jsonPath("$.vitreArD").value(DEFAULT_VITRE_AR_D.booleanValue()))
            .andExpect(jsonPath("$.vitreAvG").value(DEFAULT_VITRE_AV_G.booleanValue()))
            .andExpect(jsonPath("$.vitreArG").value(DEFAULT_VITRE_AR_G.booleanValue()))
            .andExpect(jsonPath("$.lunetteAr").value(DEFAULT_LUNETTE_AR.booleanValue()))
            .andExpect(jsonPath("$.tiersAvG").value(DEFAULT_TIERS_AV_G.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPointChoc() throws Exception {
        // Get the pointChoc
        restPointChocMockMvc.perform(get("/api/point-chocs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePointChoc() throws Exception {
        // Initialize the database
        pointChocRepository.saveAndFlush(pointChoc);
        pointChocSearchRepository.save(pointChoc);
        int databaseSizeBeforeUpdate = pointChocRepository.findAll().size();

        // Update the pointChoc
        PointChoc updatedPointChoc = pointChocRepository.findOne(pointChoc.getId());
        updatedPointChoc=  null ; 
       
        PointChocDTO pointChocDTO = pointChocMapper.toDto(updatedPointChoc);

        restPointChocMockMvc.perform(put("/api/point-chocs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pointChocDTO)))
            .andExpect(status().isOk());

        // Validate the PointChoc in the database
        List<PointChoc> pointChocList = pointChocRepository.findAll();
        assertThat(pointChocList).hasSize(databaseSizeBeforeUpdate);
        PointChoc testPointChoc = pointChocList.get(pointChocList.size() - 1);
    

        // Validate the PointChoc in Elasticsearch
        PointChoc pointChocEs = pointChocSearchRepository.findOne(testPointChoc.getId());
        assertThat(pointChocEs).isEqualToComparingFieldByField(testPointChoc);
    }

    @Test
    @Transactional
    public void updateNonExistingPointChoc() throws Exception {
        int databaseSizeBeforeUpdate = pointChocRepository.findAll().size();

        // Create the PointChoc
        PointChocDTO pointChocDTO = pointChocMapper.toDto(pointChoc);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPointChocMockMvc.perform(put("/api/point-chocs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pointChocDTO)))
            .andExpect(status().isCreated());

        // Validate the PointChoc in the database
        List<PointChoc> pointChocList = pointChocRepository.findAll();
        assertThat(pointChocList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePointChoc() throws Exception {
        // Initialize the database
        pointChocRepository.saveAndFlush(pointChoc);
        pointChocSearchRepository.save(pointChoc);
        int databaseSizeBeforeDelete = pointChocRepository.findAll().size();

        // Get the pointChoc
        restPointChocMockMvc.perform(delete("/api/point-chocs/{id}", pointChoc.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean pointChocExistsInEs = pointChocSearchRepository.exists(pointChoc.getId());
        assertThat(pointChocExistsInEs).isFalse();

        // Validate the database is empty
        List<PointChoc> pointChocList = pointChocRepository.findAll();
        assertThat(pointChocList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchPointChoc() throws Exception {
        // Initialize the database
        pointChocRepository.saveAndFlush(pointChoc);
        pointChocSearchRepository.save(pointChoc);

        // Search the pointChoc
        restPointChocMockMvc.perform(get("/api/_search/point-chocs?query=id:" + pointChoc.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pointChoc.getId().intValue())))
            .andExpect(jsonPath("$.[*].faceAv").value(hasItem(DEFAULT_FACE_AV.booleanValue())))
            .andExpect(jsonPath("$.[*].tiersAvD").value(hasItem(DEFAULT_TIERS_AV_D.booleanValue())))
            .andExpect(jsonPath("$.[*].tiersLatD").value(hasItem(DEFAULT_TIERS_LAT_D.booleanValue())))
            .andExpect(jsonPath("$.[*].latD").value(hasItem(DEFAULT_LAT_D.booleanValue())))
            .andExpect(jsonPath("$.[*].tiersArD").value(hasItem(DEFAULT_TIERS_AR_D.booleanValue())))
            .andExpect(jsonPath("$.[*].tiersLatG").value(hasItem(DEFAULT_TIERS_LAT_G.booleanValue())))
            .andExpect(jsonPath("$.[*].tiersArG").value(hasItem(DEFAULT_TIERS_AR_G.booleanValue())))
            .andExpect(jsonPath("$.[*].latG").value(hasItem(DEFAULT_LAT_G.booleanValue())))
            .andExpect(jsonPath("$.[*].faceAr").value(hasItem(DEFAULT_FACE_AR.booleanValue())))
            .andExpect(jsonPath("$.[*].diversChoc").value(hasItem(DEFAULT_DIVERS_CHOC.booleanValue())))
            .andExpect(jsonPath("$.[*].pareBrise").value(hasItem(DEFAULT_PARE_BRISE.booleanValue())))
            .andExpect(jsonPath("$.[*].triangleAvD").value(hasItem(DEFAULT_TRIANGLE_AV_D.booleanValue())))
            .andExpect(jsonPath("$.[*].triangleArD").value(hasItem(DEFAULT_TRIANGLE_AR_D.booleanValue())))
            .andExpect(jsonPath("$.[*].triangleAvG").value(hasItem(DEFAULT_TRIANGLE_AV_G.booleanValue())))
            .andExpect(jsonPath("$.[*].triangleArG").value(hasItem(DEFAULT_TRIANGLE_AR_G.booleanValue())))
            .andExpect(jsonPath("$.[*].vitreAvD").value(hasItem(DEFAULT_VITRE_AV_D.booleanValue())))
            .andExpect(jsonPath("$.[*].vitreArD").value(hasItem(DEFAULT_VITRE_AR_D.booleanValue())))
            .andExpect(jsonPath("$.[*].vitreAvG").value(hasItem(DEFAULT_VITRE_AV_G.booleanValue())))
            .andExpect(jsonPath("$.[*].vitreArG").value(hasItem(DEFAULT_VITRE_AR_G.booleanValue())))
            .andExpect(jsonPath("$.[*].lunetteAr").value(hasItem(DEFAULT_LUNETTE_AR.booleanValue())))
            .andExpect(jsonPath("$.[*].tiersAvG").value(hasItem(DEFAULT_TIERS_AV_G.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PointChoc.class);
        PointChoc pointChoc1 = new PointChoc();
        pointChoc1.setId(1L);
        PointChoc pointChoc2 = new PointChoc();
        pointChoc2.setId(pointChoc1.getId());
        assertThat(pointChoc1).isEqualTo(pointChoc2);
        pointChoc2.setId(2L);
        assertThat(pointChoc1).isNotEqualTo(pointChoc2);
        pointChoc1.setId(null);
        assertThat(pointChoc1).isNotEqualTo(pointChoc2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PointChocDTO.class);
        PointChocDTO pointChocDTO1 = new PointChocDTO();
        pointChocDTO1.setId(1L);
        PointChocDTO pointChocDTO2 = new PointChocDTO();
        assertThat(pointChocDTO1).isNotEqualTo(pointChocDTO2);
        pointChocDTO2.setId(pointChocDTO1.getId());
        assertThat(pointChocDTO1).isEqualTo(pointChocDTO2);
        pointChocDTO2.setId(2L);
        assertThat(pointChocDTO1).isNotEqualTo(pointChocDTO2);
        pointChocDTO1.setId(null);
        assertThat(pointChocDTO1).isNotEqualTo(pointChocDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(pointChocMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(pointChocMapper.fromId(null)).isNull();
    }
}
