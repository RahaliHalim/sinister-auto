package com.gaconnecte.auxilium.web.rest;

import com.gaconnecte.auxilium.AuxiliumApp;

import com.gaconnecte.auxilium.domain.ProfileAccess;
import com.gaconnecte.auxilium.repository.ProfileAccessRepository;
import com.gaconnecte.auxilium.service.ProfileAccessService;
import com.gaconnecte.auxilium.repository.search.ProfileAccessSearchRepository;
import com.gaconnecte.auxilium.service.dto.ProfileAccessDTO;
import com.gaconnecte.auxilium.service.mapper.ProfileAccessMapper;
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
 * Test class for the ProfileAccessResource REST controller.
 *
 * @see ProfileAccessResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuxiliumApp.class)
public class ProfileAccessResourceIntTest {

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_LEAF = false;
    private static final Boolean UPDATED_LEAF = true;

    @Autowired
    private ProfileAccessRepository profileAccessRepository;

    @Autowired
    private ProfileAccessMapper profileAccessMapper;

    @Autowired
    private ProfileAccessService profileAccessService;

    @Autowired
    private ProfileAccessSearchRepository profileAccessSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProfileAccessMockMvc;

    private ProfileAccess profileAccess;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProfileAccessResource profileAccessResource = new ProfileAccessResource(profileAccessService);
        this.restProfileAccessMockMvc = MockMvcBuilders.standaloneSetup(profileAccessResource)
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
    public static ProfileAccess createEntity(EntityManager em) {
        ProfileAccess profileAccess = new ProfileAccess()
            .label(DEFAULT_LABEL)
            .code(DEFAULT_CODE)
            .url(DEFAULT_URL)
            .leaf(DEFAULT_LEAF);
        return profileAccess;
    }

    @Before
    public void initTest() {
        profileAccessSearchRepository.deleteAll();
        profileAccess = createEntity(em);
    }

    @Test
    @Transactional
    public void createProfileAccess() throws Exception {
        int databaseSizeBeforeCreate = profileAccessRepository.findAll().size();

        // Create the ProfileAccess
        ProfileAccessDTO profileAccessDTO = profileAccessMapper.toDto(profileAccess);
        restProfileAccessMockMvc.perform(post("/api/profile-accesses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profileAccessDTO)))
            .andExpect(status().isCreated());

        // Validate the ProfileAccess in the database
        List<ProfileAccess> profileAccessList = profileAccessRepository.findAll();
        assertThat(profileAccessList).hasSize(databaseSizeBeforeCreate + 1);
        ProfileAccess testProfileAccess = profileAccessList.get(profileAccessList.size() - 1);
        assertThat(testProfileAccess.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testProfileAccess.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testProfileAccess.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testProfileAccess.isLeaf()).isEqualTo(DEFAULT_LEAF);

        // Validate the ProfileAccess in Elasticsearch
        ProfileAccess profileAccessEs = profileAccessSearchRepository.findOne(testProfileAccess.getId());
        assertThat(profileAccessEs).isEqualToComparingFieldByField(testProfileAccess);
    }

    @Test
    @Transactional
    public void createProfileAccessWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = profileAccessRepository.findAll().size();

        // Create the ProfileAccess with an existing ID
        profileAccess.setId(1L);
        ProfileAccessDTO profileAccessDTO = profileAccessMapper.toDto(profileAccess);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProfileAccessMockMvc.perform(post("/api/profile-accesses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profileAccessDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ProfileAccess> profileAccessList = profileAccessRepository.findAll();
        assertThat(profileAccessList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllProfileAccesses() throws Exception {
        // Initialize the database
        profileAccessRepository.saveAndFlush(profileAccess);

        // Get all the profileAccessList
        restProfileAccessMockMvc.perform(get("/api/profile-accesses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(profileAccess.getId().intValue())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
            .andExpect(jsonPath("$.[*].leaf").value(hasItem(DEFAULT_LEAF.booleanValue())));
    }

    @Test
    @Transactional
    public void getProfileAccess() throws Exception {
        // Initialize the database
        profileAccessRepository.saveAndFlush(profileAccess);

        // Get the profileAccess
        restProfileAccessMockMvc.perform(get("/api/profile-accesses/{id}", profileAccess.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(profileAccess.getId().intValue()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()))
            .andExpect(jsonPath("$.leaf").value(DEFAULT_LEAF.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingProfileAccess() throws Exception {
        // Get the profileAccess
        restProfileAccessMockMvc.perform(get("/api/profile-accesses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProfileAccess() throws Exception {
        // Initialize the database
        profileAccessRepository.saveAndFlush(profileAccess);
        profileAccessSearchRepository.save(profileAccess);
        int databaseSizeBeforeUpdate = profileAccessRepository.findAll().size();

        // Update the profileAccess
        ProfileAccess updatedProfileAccess = profileAccessRepository.findOne(profileAccess.getId());
        updatedProfileAccess
            .label(UPDATED_LABEL)
            .code(UPDATED_CODE)
            .url(UPDATED_URL)
            .leaf(UPDATED_LEAF);
        ProfileAccessDTO profileAccessDTO = profileAccessMapper.toDto(updatedProfileAccess);

        restProfileAccessMockMvc.perform(put("/api/profile-accesses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profileAccessDTO)))
            .andExpect(status().isOk());

        // Validate the ProfileAccess in the database
        List<ProfileAccess> profileAccessList = profileAccessRepository.findAll();
        assertThat(profileAccessList).hasSize(databaseSizeBeforeUpdate);
        ProfileAccess testProfileAccess = profileAccessList.get(profileAccessList.size() - 1);
        assertThat(testProfileAccess.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testProfileAccess.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testProfileAccess.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testProfileAccess.isLeaf()).isEqualTo(UPDATED_LEAF);

        // Validate the ProfileAccess in Elasticsearch
        ProfileAccess profileAccessEs = profileAccessSearchRepository.findOne(testProfileAccess.getId());
        assertThat(profileAccessEs).isEqualToComparingFieldByField(testProfileAccess);
    }

    @Test
    @Transactional
    public void updateNonExistingProfileAccess() throws Exception {
        int databaseSizeBeforeUpdate = profileAccessRepository.findAll().size();

        // Create the ProfileAccess
        ProfileAccessDTO profileAccessDTO = profileAccessMapper.toDto(profileAccess);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProfileAccessMockMvc.perform(put("/api/profile-accesses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profileAccessDTO)))
            .andExpect(status().isCreated());

        // Validate the ProfileAccess in the database
        List<ProfileAccess> profileAccessList = profileAccessRepository.findAll();
        assertThat(profileAccessList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProfileAccess() throws Exception {
        // Initialize the database
        profileAccessRepository.saveAndFlush(profileAccess);
        profileAccessSearchRepository.save(profileAccess);
        int databaseSizeBeforeDelete = profileAccessRepository.findAll().size();

        // Get the profileAccess
        restProfileAccessMockMvc.perform(delete("/api/profile-accesses/{id}", profileAccess.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean profileAccessExistsInEs = profileAccessSearchRepository.exists(profileAccess.getId());
        assertThat(profileAccessExistsInEs).isFalse();

        // Validate the database is empty
        List<ProfileAccess> profileAccessList = profileAccessRepository.findAll();
        assertThat(profileAccessList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchProfileAccess() throws Exception {
        // Initialize the database
        profileAccessRepository.saveAndFlush(profileAccess);
        profileAccessSearchRepository.save(profileAccess);

        // Search the profileAccess
        restProfileAccessMockMvc.perform(get("/api/_search/profile-accesses?query=id:" + profileAccess.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(profileAccess.getId().intValue())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
            .andExpect(jsonPath("$.[*].leaf").value(hasItem(DEFAULT_LEAF.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProfileAccess.class);
        ProfileAccess profileAccess1 = new ProfileAccess();
        profileAccess1.setId(1L);
        ProfileAccess profileAccess2 = new ProfileAccess();
        profileAccess2.setId(profileAccess1.getId());
        assertThat(profileAccess1).isEqualTo(profileAccess2);
        profileAccess2.setId(2L);
        assertThat(profileAccess1).isNotEqualTo(profileAccess2);
        profileAccess1.setId(null);
        assertThat(profileAccess1).isNotEqualTo(profileAccess2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProfileAccessDTO.class);
        ProfileAccessDTO profileAccessDTO1 = new ProfileAccessDTO();
        profileAccessDTO1.setId(1L);
        ProfileAccessDTO profileAccessDTO2 = new ProfileAccessDTO();
        assertThat(profileAccessDTO1).isNotEqualTo(profileAccessDTO2);
        profileAccessDTO2.setId(profileAccessDTO1.getId());
        assertThat(profileAccessDTO1).isEqualTo(profileAccessDTO2);
        profileAccessDTO2.setId(2L);
        assertThat(profileAccessDTO1).isNotEqualTo(profileAccessDTO2);
        profileAccessDTO1.setId(null);
        assertThat(profileAccessDTO1).isNotEqualTo(profileAccessDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(profileAccessMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(profileAccessMapper.fromId(null)).isNull();
    }
}
