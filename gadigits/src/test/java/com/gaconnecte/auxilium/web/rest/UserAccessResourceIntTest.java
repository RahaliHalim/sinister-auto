package com.gaconnecte.auxilium.web.rest;

import com.gaconnecte.auxilium.AuxiliumApp;

import com.gaconnecte.auxilium.domain.UserAccess;
import com.gaconnecte.auxilium.repository.UserAccessRepository;
import com.gaconnecte.auxilium.service.UserAccessService;
import com.gaconnecte.auxilium.repository.search.UserAccessSearchRepository;
import com.gaconnecte.auxilium.service.dto.UserAccessDTO;
import com.gaconnecte.auxilium.service.mapper.UserAccessMapper;
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
 * Test class for the UserAccessResource REST controller.
 *
 * @see UserAccessResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuxiliumApp.class)
public class UserAccessResourceIntTest {

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_LEAF = false;
    private static final Boolean UPDATED_LEAF = true;

    @Autowired
    private UserAccessRepository userAccessRepository;

    @Autowired
    private UserAccessMapper userAccessMapper;

    @Autowired
    private UserAccessService userAccessService;

    @Autowired
    private UserAccessSearchRepository userAccessSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUserAccessMockMvc;

    private UserAccess userAccess;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UserAccessResource userAccessResource = new UserAccessResource(userAccessService);
        this.restUserAccessMockMvc = MockMvcBuilders.standaloneSetup(userAccessResource)
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
    public static UserAccess createEntity(EntityManager em) {
        UserAccess userAccess = new UserAccess()
            .label(DEFAULT_LABEL)
            .code(DEFAULT_CODE)
            .url(DEFAULT_URL)
            .leaf(DEFAULT_LEAF);
        return userAccess;
    }

    @Before
    public void initTest() {
        userAccessSearchRepository.deleteAll();
        userAccess = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserAccess() throws Exception {
        int databaseSizeBeforeCreate = userAccessRepository.findAll().size();

        // Create the UserAccess
        UserAccessDTO userAccessDTO = userAccessMapper.toDto(userAccess);
        restUserAccessMockMvc.perform(post("/api/user-accesses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userAccessDTO)))
            .andExpect(status().isCreated());

        // Validate the UserAccess in the database
        List<UserAccess> userAccessList = userAccessRepository.findAll();
        assertThat(userAccessList).hasSize(databaseSizeBeforeCreate + 1);
        UserAccess testUserAccess = userAccessList.get(userAccessList.size() - 1);
        assertThat(testUserAccess.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testUserAccess.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testUserAccess.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testUserAccess.isLeaf()).isEqualTo(DEFAULT_LEAF);

        // Validate the UserAccess in Elasticsearch
        UserAccess userAccessEs = userAccessSearchRepository.findOne(testUserAccess.getId());
        assertThat(userAccessEs).isEqualToComparingFieldByField(testUserAccess);
    }

    @Test
    @Transactional
    public void createUserAccessWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userAccessRepository.findAll().size();

        // Create the UserAccess with an existing ID
        userAccess.setId(1L);
        UserAccessDTO userAccessDTO = userAccessMapper.toDto(userAccess);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserAccessMockMvc.perform(post("/api/user-accesses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userAccessDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<UserAccess> userAccessList = userAccessRepository.findAll();
        assertThat(userAccessList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllUserAccesses() throws Exception {
        // Initialize the database
        userAccessRepository.saveAndFlush(userAccess);

        // Get all the userAccessList
        restUserAccessMockMvc.perform(get("/api/user-accesses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userAccess.getId().intValue())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
            .andExpect(jsonPath("$.[*].leaf").value(hasItem(DEFAULT_LEAF.booleanValue())));
    }

    @Test
    @Transactional
    public void getUserAccess() throws Exception {
        // Initialize the database
        userAccessRepository.saveAndFlush(userAccess);

        // Get the userAccess
        restUserAccessMockMvc.perform(get("/api/user-accesses/{id}", userAccess.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userAccess.getId().intValue()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()))
            .andExpect(jsonPath("$.leaf").value(DEFAULT_LEAF.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingUserAccess() throws Exception {
        // Get the userAccess
        restUserAccessMockMvc.perform(get("/api/user-accesses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserAccess() throws Exception {
        // Initialize the database
        userAccessRepository.saveAndFlush(userAccess);
        userAccessSearchRepository.save(userAccess);
        int databaseSizeBeforeUpdate = userAccessRepository.findAll().size();

        // Update the userAccess
        UserAccess updatedUserAccess = userAccessRepository.findOne(userAccess.getId());
        updatedUserAccess
            .label(UPDATED_LABEL)
            .code(UPDATED_CODE)
            .url(UPDATED_URL)
            .leaf(UPDATED_LEAF);
        UserAccessDTO userAccessDTO = userAccessMapper.toDto(updatedUserAccess);

        restUserAccessMockMvc.perform(put("/api/user-accesses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userAccessDTO)))
            .andExpect(status().isOk());

        // Validate the UserAccess in the database
        List<UserAccess> userAccessList = userAccessRepository.findAll();
        assertThat(userAccessList).hasSize(databaseSizeBeforeUpdate);
        UserAccess testUserAccess = userAccessList.get(userAccessList.size() - 1);
        assertThat(testUserAccess.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testUserAccess.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testUserAccess.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testUserAccess.isLeaf()).isEqualTo(UPDATED_LEAF);

        // Validate the UserAccess in Elasticsearch
        UserAccess userAccessEs = userAccessSearchRepository.findOne(testUserAccess.getId());
        assertThat(userAccessEs).isEqualToComparingFieldByField(testUserAccess);
    }

    @Test
    @Transactional
    public void updateNonExistingUserAccess() throws Exception {
        int databaseSizeBeforeUpdate = userAccessRepository.findAll().size();

        // Create the UserAccess
        UserAccessDTO userAccessDTO = userAccessMapper.toDto(userAccess);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUserAccessMockMvc.perform(put("/api/user-accesses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userAccessDTO)))
            .andExpect(status().isCreated());

        // Validate the UserAccess in the database
        List<UserAccess> userAccessList = userAccessRepository.findAll();
        assertThat(userAccessList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUserAccess() throws Exception {
        // Initialize the database
        userAccessRepository.saveAndFlush(userAccess);
        userAccessSearchRepository.save(userAccess);
        int databaseSizeBeforeDelete = userAccessRepository.findAll().size();

        // Get the userAccess
        restUserAccessMockMvc.perform(delete("/api/user-accesses/{id}", userAccess.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean userAccessExistsInEs = userAccessSearchRepository.exists(userAccess.getId());
        assertThat(userAccessExistsInEs).isFalse();

        // Validate the database is empty
        List<UserAccess> userAccessList = userAccessRepository.findAll();
        assertThat(userAccessList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchUserAccess() throws Exception {
        // Initialize the database
        userAccessRepository.saveAndFlush(userAccess);
        userAccessSearchRepository.save(userAccess);

        // Search the userAccess
        restUserAccessMockMvc.perform(get("/api/_search/user-accesses?query=id:" + userAccess.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userAccess.getId().intValue())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
            .andExpect(jsonPath("$.[*].leaf").value(hasItem(DEFAULT_LEAF.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserAccess.class);
        UserAccess userAccess1 = new UserAccess();
        userAccess1.setId(1L);
        UserAccess userAccess2 = new UserAccess();
        userAccess2.setId(userAccess1.getId());
        assertThat(userAccess1).isEqualTo(userAccess2);
        userAccess2.setId(2L);
        assertThat(userAccess1).isNotEqualTo(userAccess2);
        userAccess1.setId(null);
        assertThat(userAccess1).isNotEqualTo(userAccess2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserAccessDTO.class);
        UserAccessDTO userAccessDTO1 = new UserAccessDTO();
        userAccessDTO1.setId(1L);
        UserAccessDTO userAccessDTO2 = new UserAccessDTO();
        assertThat(userAccessDTO1).isNotEqualTo(userAccessDTO2);
        userAccessDTO2.setId(userAccessDTO1.getId());
        assertThat(userAccessDTO1).isEqualTo(userAccessDTO2);
        userAccessDTO2.setId(2L);
        assertThat(userAccessDTO1).isNotEqualTo(userAccessDTO2);
        userAccessDTO1.setId(null);
        assertThat(userAccessDTO1).isNotEqualTo(userAccessDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(userAccessMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(userAccessMapper.fromId(null)).isNull();
    }
}
