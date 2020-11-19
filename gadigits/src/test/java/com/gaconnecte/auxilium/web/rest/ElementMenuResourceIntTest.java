package com.gaconnecte.auxilium.web.rest;

import com.gaconnecte.auxilium.AuxiliumApp;

import com.gaconnecte.auxilium.domain.ElementMenu;
import com.gaconnecte.auxilium.repository.ElementMenuRepository;
import com.gaconnecte.auxilium.service.ElementMenuService;
import com.gaconnecte.auxilium.repository.search.ElementMenuSearchRepository;
import com.gaconnecte.auxilium.service.dto.ElementMenuDTO;
import com.gaconnecte.auxilium.service.mapper.ElementMenuMapper;
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
 * Test class for the ElementMenuResource REST controller.
 *
 * @see ElementMenuResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuxiliumApp.class)
public class ElementMenuResourceIntTest {

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private ElementMenuRepository elementMenuRepository;

    @Autowired
    private ElementMenuMapper elementMenuMapper;

    @Autowired
    private ElementMenuService elementMenuService;

    @Autowired
    private ElementMenuSearchRepository elementMenuSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restElementMenuMockMvc;

    private ElementMenu elementMenu;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ElementMenuResource elementMenuResource = new ElementMenuResource(elementMenuService);
        this.restElementMenuMockMvc = MockMvcBuilders.standaloneSetup(elementMenuResource)
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
    public static ElementMenu createEntity(EntityManager em) {
        ElementMenu elementMenu = new ElementMenu()
            .label(DEFAULT_LABEL)
            .code(DEFAULT_CODE)
            .active(DEFAULT_ACTIVE);
        return elementMenu;
    }

    @Before
    public void initTest() {
        elementMenuSearchRepository.deleteAll();
        elementMenu = createEntity(em);
    }

    @Test
    @Transactional
    public void createElementMenu() throws Exception {
        int databaseSizeBeforeCreate = elementMenuRepository.findAll().size();

        // Create the ElementMenu
        ElementMenuDTO elementMenuDTO = elementMenuMapper.toDto(elementMenu);
        restElementMenuMockMvc.perform(post("/api/element-menus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(elementMenuDTO)))
            .andExpect(status().isCreated());

        // Validate the ElementMenu in the database
        List<ElementMenu> elementMenuList = elementMenuRepository.findAll();
        assertThat(elementMenuList).hasSize(databaseSizeBeforeCreate + 1);
        ElementMenu testElementMenu = elementMenuList.get(elementMenuList.size() - 1);
        assertThat(testElementMenu.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testElementMenu.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testElementMenu.isActive()).isEqualTo(DEFAULT_ACTIVE);

        // Validate the ElementMenu in Elasticsearch
        ElementMenu elementMenuEs = elementMenuSearchRepository.findOne(testElementMenu.getId());
        assertThat(elementMenuEs).isEqualToComparingFieldByField(testElementMenu);
    }

    @Test
    @Transactional
    public void createElementMenuWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = elementMenuRepository.findAll().size();

        // Create the ElementMenu with an existing ID
        elementMenu.setId(1L);
        ElementMenuDTO elementMenuDTO = elementMenuMapper.toDto(elementMenu);

        // An entity with an existing ID cannot be created, so this API call must fail
        restElementMenuMockMvc.perform(post("/api/element-menus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(elementMenuDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ElementMenu> elementMenuList = elementMenuRepository.findAll();
        assertThat(elementMenuList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllElementMenus() throws Exception {
        // Initialize the database
        elementMenuRepository.saveAndFlush(elementMenu);

        // Get all the elementMenuList
        restElementMenuMockMvc.perform(get("/api/element-menus?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(elementMenu.getId().intValue())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void getElementMenu() throws Exception {
        // Initialize the database
        elementMenuRepository.saveAndFlush(elementMenu);

        // Get the elementMenu
        restElementMenuMockMvc.perform(get("/api/element-menus/{id}", elementMenu.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(elementMenu.getId().intValue()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingElementMenu() throws Exception {
        // Get the elementMenu
        restElementMenuMockMvc.perform(get("/api/element-menus/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateElementMenu() throws Exception {
        // Initialize the database
        elementMenuRepository.saveAndFlush(elementMenu);
        elementMenuSearchRepository.save(elementMenu);
        int databaseSizeBeforeUpdate = elementMenuRepository.findAll().size();

        // Update the elementMenu
        ElementMenu updatedElementMenu = elementMenuRepository.findOne(elementMenu.getId());
        updatedElementMenu
            .label(UPDATED_LABEL)
            .code(UPDATED_CODE)
            .active(UPDATED_ACTIVE);
        ElementMenuDTO elementMenuDTO = elementMenuMapper.toDto(updatedElementMenu);

        restElementMenuMockMvc.perform(put("/api/element-menus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(elementMenuDTO)))
            .andExpect(status().isOk());

        // Validate the ElementMenu in the database
        List<ElementMenu> elementMenuList = elementMenuRepository.findAll();
        assertThat(elementMenuList).hasSize(databaseSizeBeforeUpdate);
        ElementMenu testElementMenu = elementMenuList.get(elementMenuList.size() - 1);
        assertThat(testElementMenu.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testElementMenu.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testElementMenu.isActive()).isEqualTo(UPDATED_ACTIVE);

        // Validate the ElementMenu in Elasticsearch
        ElementMenu elementMenuEs = elementMenuSearchRepository.findOne(testElementMenu.getId());
        assertThat(elementMenuEs).isEqualToComparingFieldByField(testElementMenu);
    }

    @Test
    @Transactional
    public void updateNonExistingElementMenu() throws Exception {
        int databaseSizeBeforeUpdate = elementMenuRepository.findAll().size();

        // Create the ElementMenu
        ElementMenuDTO elementMenuDTO = elementMenuMapper.toDto(elementMenu);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restElementMenuMockMvc.perform(put("/api/element-menus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(elementMenuDTO)))
            .andExpect(status().isCreated());

        // Validate the ElementMenu in the database
        List<ElementMenu> elementMenuList = elementMenuRepository.findAll();
        assertThat(elementMenuList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteElementMenu() throws Exception {
        // Initialize the database
        elementMenuRepository.saveAndFlush(elementMenu);
        elementMenuSearchRepository.save(elementMenu);
        int databaseSizeBeforeDelete = elementMenuRepository.findAll().size();

        // Get the elementMenu
        restElementMenuMockMvc.perform(delete("/api/element-menus/{id}", elementMenu.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean elementMenuExistsInEs = elementMenuSearchRepository.exists(elementMenu.getId());
        assertThat(elementMenuExistsInEs).isFalse();

        // Validate the database is empty
        List<ElementMenu> elementMenuList = elementMenuRepository.findAll();
        assertThat(elementMenuList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchElementMenu() throws Exception {
        // Initialize the database
        elementMenuRepository.saveAndFlush(elementMenu);
        elementMenuSearchRepository.save(elementMenu);

        // Search the elementMenu
        restElementMenuMockMvc.perform(get("/api/_search/element-menus?query=id:" + elementMenu.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(elementMenu.getId().intValue())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ElementMenu.class);
        ElementMenu elementMenu1 = new ElementMenu();
        elementMenu1.setId(1L);
        ElementMenu elementMenu2 = new ElementMenu();
        elementMenu2.setId(elementMenu1.getId());
        assertThat(elementMenu1).isEqualTo(elementMenu2);
        elementMenu2.setId(2L);
        assertThat(elementMenu1).isNotEqualTo(elementMenu2);
        elementMenu1.setId(null);
        assertThat(elementMenu1).isNotEqualTo(elementMenu2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ElementMenuDTO.class);
        ElementMenuDTO elementMenuDTO1 = new ElementMenuDTO();
        elementMenuDTO1.setId(1L);
        ElementMenuDTO elementMenuDTO2 = new ElementMenuDTO();
        assertThat(elementMenuDTO1).isNotEqualTo(elementMenuDTO2);
        elementMenuDTO2.setId(elementMenuDTO1.getId());
        assertThat(elementMenuDTO1).isEqualTo(elementMenuDTO2);
        elementMenuDTO2.setId(2L);
        assertThat(elementMenuDTO1).isNotEqualTo(elementMenuDTO2);
        elementMenuDTO1.setId(null);
        assertThat(elementMenuDTO1).isNotEqualTo(elementMenuDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(elementMenuMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(elementMenuMapper.fromId(null)).isNull();
    }
}
