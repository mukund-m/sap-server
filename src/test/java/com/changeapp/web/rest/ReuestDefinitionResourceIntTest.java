package com.changeapp.web.rest;

import com.changeapp.ChangeAppServerApp;

import com.changeapp.domain.ReuestDefinition;
import com.changeapp.repository.ReuestDefinitionRepository;
import com.changeapp.service.ReuestDefinitionService;
import com.changeapp.web.rest.errors.ExceptionTranslator;

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
 * Test class for the ReuestDefinitionResource REST controller.
 *
 * @see ReuestDefinitionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ChangeAppServerApp.class)
public class ReuestDefinitionResourceIntTest {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Autowired
    private ReuestDefinitionRepository reuestDefinitionRepository;

    @Autowired
    private ReuestDefinitionService reuestDefinitionService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restReuestDefinitionMockMvc;

    private ReuestDefinition reuestDefinition;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ReuestDefinitionResource reuestDefinitionResource = new ReuestDefinitionResource(reuestDefinitionService);
        this.restReuestDefinitionMockMvc = MockMvcBuilders.standaloneSetup(reuestDefinitionResource)
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
    public static ReuestDefinition createEntity(EntityManager em) {
        ReuestDefinition reuestDefinition = new ReuestDefinition()
            .value(DEFAULT_VALUE);
        return reuestDefinition;
    }

    @Before
    public void initTest() {
        reuestDefinition = createEntity(em);
    }

    @Test
    @Transactional
    public void createReuestDefinition() throws Exception {
        int databaseSizeBeforeCreate = reuestDefinitionRepository.findAll().size();

        // Create the ReuestDefinition
        restReuestDefinitionMockMvc.perform(post("/api/reuest-definitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reuestDefinition)))
            .andExpect(status().isCreated());

        // Validate the ReuestDefinition in the database
        List<ReuestDefinition> reuestDefinitionList = reuestDefinitionRepository.findAll();
        assertThat(reuestDefinitionList).hasSize(databaseSizeBeforeCreate + 1);
        ReuestDefinition testReuestDefinition = reuestDefinitionList.get(reuestDefinitionList.size() - 1);
        assertThat(testReuestDefinition.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createReuestDefinitionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = reuestDefinitionRepository.findAll().size();

        // Create the ReuestDefinition with an existing ID
        reuestDefinition.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReuestDefinitionMockMvc.perform(post("/api/reuest-definitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reuestDefinition)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ReuestDefinition> reuestDefinitionList = reuestDefinitionRepository.findAll();
        assertThat(reuestDefinitionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllReuestDefinitions() throws Exception {
        // Initialize the database
        reuestDefinitionRepository.saveAndFlush(reuestDefinition);

        // Get all the reuestDefinitionList
        restReuestDefinitionMockMvc.perform(get("/api/reuest-definitions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reuestDefinition.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));
    }

    @Test
    @Transactional
    public void getReuestDefinition() throws Exception {
        // Initialize the database
        reuestDefinitionRepository.saveAndFlush(reuestDefinition);

        // Get the reuestDefinition
        restReuestDefinitionMockMvc.perform(get("/api/reuest-definitions/{id}", reuestDefinition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(reuestDefinition.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingReuestDefinition() throws Exception {
        // Get the reuestDefinition
        restReuestDefinitionMockMvc.perform(get("/api/reuest-definitions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReuestDefinition() throws Exception {
        // Initialize the database
        reuestDefinitionService.save(reuestDefinition);

        int databaseSizeBeforeUpdate = reuestDefinitionRepository.findAll().size();

        // Update the reuestDefinition
        ReuestDefinition updatedReuestDefinition = reuestDefinitionRepository.findOne(reuestDefinition.getId());
        updatedReuestDefinition
            .value(UPDATED_VALUE);

        restReuestDefinitionMockMvc.perform(put("/api/reuest-definitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedReuestDefinition)))
            .andExpect(status().isOk());

        // Validate the ReuestDefinition in the database
        List<ReuestDefinition> reuestDefinitionList = reuestDefinitionRepository.findAll();
        assertThat(reuestDefinitionList).hasSize(databaseSizeBeforeUpdate);
        ReuestDefinition testReuestDefinition = reuestDefinitionList.get(reuestDefinitionList.size() - 1);
        assertThat(testReuestDefinition.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingReuestDefinition() throws Exception {
        int databaseSizeBeforeUpdate = reuestDefinitionRepository.findAll().size();

        // Create the ReuestDefinition

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restReuestDefinitionMockMvc.perform(put("/api/reuest-definitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reuestDefinition)))
            .andExpect(status().isCreated());

        // Validate the ReuestDefinition in the database
        List<ReuestDefinition> reuestDefinitionList = reuestDefinitionRepository.findAll();
        assertThat(reuestDefinitionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteReuestDefinition() throws Exception {
        // Initialize the database
        reuestDefinitionService.save(reuestDefinition);

        int databaseSizeBeforeDelete = reuestDefinitionRepository.findAll().size();

        // Get the reuestDefinition
        restReuestDefinitionMockMvc.perform(delete("/api/reuest-definitions/{id}", reuestDefinition.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ReuestDefinition> reuestDefinitionList = reuestDefinitionRepository.findAll();
        assertThat(reuestDefinitionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReuestDefinition.class);
        ReuestDefinition reuestDefinition1 = new ReuestDefinition();
        reuestDefinition1.setId(1L);
        ReuestDefinition reuestDefinition2 = new ReuestDefinition();
        reuestDefinition2.setId(reuestDefinition1.getId());
        assertThat(reuestDefinition1).isEqualTo(reuestDefinition2);
        reuestDefinition2.setId(2L);
        assertThat(reuestDefinition1).isNotEqualTo(reuestDefinition2);
        reuestDefinition1.setId(null);
        assertThat(reuestDefinition1).isNotEqualTo(reuestDefinition2);
    }
}
