package com.gaconnecte.auxilium.web.rest;

import com.gaconnecte.auxilium.AuxiliumApp;

import com.gaconnecte.auxilium.domain.DetailsMo;
import com.gaconnecte.auxilium.domain.Devis;
import com.gaconnecte.auxilium.domain.RefTypeIntervention;
import com.gaconnecte.auxilium.repository.DetailsMoRepository;
import com.gaconnecte.auxilium.service.DetailsMoService;
import com.gaconnecte.auxilium.repository.search.DetailsMoSearchRepository;
import com.gaconnecte.auxilium.service.dto.DetailsMoDTO;
import com.gaconnecte.auxilium.service.mapper.DetailsMoMapper;
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
 * Test class for the DetailsMoResource REST controller.
 *
 * @see DetailsMoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuxiliumApp.class)
public class DetailsMoResourceIntTest {

    private static final Float DEFAULT_NOMBRE_HEURES = 1f;
    private static final Float UPDATED_NOMBRE_HEURES = 1f;

    private static final String DEFAULT_DESIGNATION = "AB";
    private static final String UPDATED_DESIGNATION = "AC";

    @Autowired
    private DetailsMoRepository detailsMoRepository;

    @Autowired
    private DetailsMoMapper detailsMoMapper;

    @Autowired
    private DetailsMoService detailsMoService;

    @Autowired
    private DetailsMoSearchRepository detailsMoSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDetailsMoMockMvc;

    private DetailsMo detailsMo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
       // DetailsMoResource detailsMoResource = new DetailsMoResource(detailsMoService);
        this.restDetailsMoMockMvc = MockMvcBuilders.standaloneSetup(null)
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
    public static DetailsMo createEntity(EntityManager em) {
        DetailsMo detailsMo = new DetailsMo()
            .nombreHeures(DEFAULT_NOMBRE_HEURES);
        // Add required entity
        Devis devis = DevisResourceIntTest.createEntity(em);
        em.persist(devis);
        em.flush();
        detailsMo.setDevis(devis);
        // Add required entity
        RefTypeIntervention typeIntervention = RefTypeInterventionResourceIntTest.createEntity(em);
        em.persist(typeIntervention);
        em.flush();
        detailsMo.setTypeIntervention(typeIntervention);
        return detailsMo;
    }

    @Before
    public void initTest() {
        detailsMoSearchRepository.deleteAll();
        detailsMo = createEntity(em);
    }

    @Test
    @Transactional
    public void createDetailsMo() throws Exception {
        int databaseSizeBeforeCreate = detailsMoRepository.findAll().size();

        // Create the DetailsMo
        DetailsMoDTO detailsMoDTO = detailsMoMapper.toDto(detailsMo);
        restDetailsMoMockMvc.perform(post("/api/details-mos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detailsMoDTO)))
            .andExpect(status().isCreated());

        // Validate the DetailsMo in the database
        List<DetailsMo> detailsMoList = detailsMoRepository.findAll();
        assertThat(detailsMoList).hasSize(databaseSizeBeforeCreate + 1);
        DetailsMo testDetailsMo = detailsMoList.get(detailsMoList.size() - 1);
        assertThat(testDetailsMo.getNombreHeures()).isEqualTo(DEFAULT_NOMBRE_HEURES);

        // Validate the DetailsMo in Elasticsearch
        DetailsMo detailsMoEs = detailsMoSearchRepository.findOne(testDetailsMo.getId());
        assertThat(detailsMoEs).isEqualToComparingFieldByField(testDetailsMo);
    }

    @Test
    @Transactional
    public void createDetailsMoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = detailsMoRepository.findAll().size();

        // Create the DetailsMo with an existing ID
        detailsMo.setId(1L);
        DetailsMoDTO detailsMoDTO = detailsMoMapper.toDto(detailsMo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDetailsMoMockMvc.perform(post("/api/details-mos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detailsMoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<DetailsMo> detailsMoList = detailsMoRepository.findAll();
        assertThat(detailsMoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDetailsMos() throws Exception {
        // Initialize the database
        detailsMoRepository.saveAndFlush(detailsMo);

        // Get all the detailsMoList
        restDetailsMoMockMvc.perform(get("/api/details-mos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(detailsMo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombreHeures").value(hasItem(DEFAULT_NOMBRE_HEURES)))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION.toString())));
    }

    @Test
    @Transactional
    public void getDetailsMo() throws Exception {
        // Initialize the database
        detailsMoRepository.saveAndFlush(detailsMo);

        // Get the detailsMo
        restDetailsMoMockMvc.perform(get("/api/details-mos/{id}", detailsMo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(detailsMo.getId().intValue()))
            .andExpect(jsonPath("$.nombreHeures").value(DEFAULT_NOMBRE_HEURES))
            .andExpect(jsonPath("$.designation").value(DEFAULT_DESIGNATION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDetailsMo() throws Exception {
        // Get the detailsMo
        restDetailsMoMockMvc.perform(get("/api/details-mos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDetailsMo() throws Exception {
        // Initialize the database
        detailsMoRepository.saveAndFlush(detailsMo);
        detailsMoSearchRepository.save(detailsMo);
        int databaseSizeBeforeUpdate = detailsMoRepository.findAll().size();

        // Update the detailsMo
        DetailsMo updatedDetailsMo = detailsMoRepository.findOne(detailsMo.getId());
        updatedDetailsMo
            .nombreHeures(UPDATED_NOMBRE_HEURES);
        DetailsMoDTO detailsMoDTO = detailsMoMapper.toDto(updatedDetailsMo);

        restDetailsMoMockMvc.perform(put("/api/details-mos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detailsMoDTO)))
            .andExpect(status().isOk());

        // Validate the DetailsMo in the database
        List<DetailsMo> detailsMoList = detailsMoRepository.findAll();
        assertThat(detailsMoList).hasSize(databaseSizeBeforeUpdate);
        DetailsMo testDetailsMo = detailsMoList.get(detailsMoList.size() - 1);
        assertThat(testDetailsMo.getNombreHeures()).isEqualTo(UPDATED_NOMBRE_HEURES);

        // Validate the DetailsMo in Elasticsearch
        DetailsMo detailsMoEs = detailsMoSearchRepository.findOne(testDetailsMo.getId());
        assertThat(detailsMoEs).isEqualToComparingFieldByField(testDetailsMo);
    }

    @Test
    @Transactional
    public void updateNonExistingDetailsMo() throws Exception {
        int databaseSizeBeforeUpdate = detailsMoRepository.findAll().size();

        // Create the DetailsMo
        DetailsMoDTO detailsMoDTO = detailsMoMapper.toDto(detailsMo);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDetailsMoMockMvc.perform(put("/api/details-mos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detailsMoDTO)))
            .andExpect(status().isCreated());

        // Validate the DetailsMo in the database
        List<DetailsMo> detailsMoList = detailsMoRepository.findAll();
        assertThat(detailsMoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDetailsMo() throws Exception {
        // Initialize the database
        detailsMoRepository.saveAndFlush(detailsMo);
        detailsMoSearchRepository.save(detailsMo);
        int databaseSizeBeforeDelete = detailsMoRepository.findAll().size();

        // Get the detailsMo
        restDetailsMoMockMvc.perform(delete("/api/details-mos/{id}", detailsMo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean detailsMoExistsInEs = detailsMoSearchRepository.exists(detailsMo.getId());
        assertThat(detailsMoExistsInEs).isFalse();

        // Validate the database is empty
        List<DetailsMo> detailsMoList = detailsMoRepository.findAll();
        assertThat(detailsMoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchDetailsMo() throws Exception {
        // Initialize the database
        detailsMoRepository.saveAndFlush(detailsMo);
        detailsMoSearchRepository.save(detailsMo);

        // Search the detailsMo
        restDetailsMoMockMvc.perform(get("/api/_search/details-mos?query=id:" + detailsMo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(detailsMo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombreHeures").value(hasItem(DEFAULT_NOMBRE_HEURES)))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DetailsMo.class);
        DetailsMo detailsMo1 = new DetailsMo();
        detailsMo1.setId(1L);
        DetailsMo detailsMo2 = new DetailsMo();
        detailsMo2.setId(detailsMo1.getId());
        assertThat(detailsMo1).isEqualTo(detailsMo2);
        detailsMo2.setId(2L);
        assertThat(detailsMo1).isNotEqualTo(detailsMo2);
        detailsMo1.setId(null);
        assertThat(detailsMo1).isNotEqualTo(detailsMo2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DetailsMoDTO.class);
        DetailsMoDTO detailsMoDTO1 = new DetailsMoDTO();
        detailsMoDTO1.setId(1L);
        DetailsMoDTO detailsMoDTO2 = new DetailsMoDTO();
        assertThat(detailsMoDTO1).isNotEqualTo(detailsMoDTO2);
        detailsMoDTO2.setId(detailsMoDTO1.getId());
        assertThat(detailsMoDTO1).isEqualTo(detailsMoDTO2);
        detailsMoDTO2.setId(2L);
        assertThat(detailsMoDTO1).isNotEqualTo(detailsMoDTO2);
        detailsMoDTO1.setId(null);
        assertThat(detailsMoDTO1).isNotEqualTo(detailsMoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(detailsMoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(detailsMoMapper.fromId(null)).isNull();
    }
}
