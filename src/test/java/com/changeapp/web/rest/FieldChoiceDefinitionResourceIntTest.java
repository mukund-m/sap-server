package com.changeapp.web.rest;

import com.changeapp.ChangeAppServerApp;

import com.changeapp.domain.FieldChoiceDefinition;
import com.changeapp.repository.FieldChoiceDefinitionRepository;
import com.changeapp.service.FieldChoiceDefinitionService;
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
 * Test class for the FieldChoiceDefinitionResource REST controller.
 *
 * @see FieldChoiceDefinitionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ChangeAppServerApp.class)
public class FieldChoiceDefinitionResourceIntTest {

    private static final String DEFAULT_KEY = "AAAAAAAAAA";
    private static final String UPDATED_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Autowired
    private FieldChoiceDefinitionRepository fieldChoiceDefinitionRepository;

    @Autowired
    private FieldChoiceDefinitionService fieldChoiceDefinitionService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFieldChoiceDefinitionMockMvc;

    private FieldChoiceDefinition fieldChoiceDefinition;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FieldChoiceDefinitionResource fieldChoiceDefinitionResource = new FieldChoiceDefinitionResource(fieldChoiceDefinitionService);
        this.restFieldChoiceDefinitionMockMvc = MockMvcBuilders.standaloneSetup(fieldChoiceDefinitionResource)
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
    public static FieldChoiceDefinition createEntity(EntityManager em) {
        FieldChoiceDefinition fieldChoiceDefinition = new FieldChoiceDefinition()
            .key(DEFAULT_KEY)
            .value(DEFAULT_VALUE);
        return fieldChoiceDefinition;
    }

    @Before
    public void initTest() {
        fieldChoiceDefinition = createEntity(em);
    }

    @Test
    @Transactional
    public void createFieldChoiceDefinition() throws Exception {
        int databaseSizeBeforeCreate = fieldChoiceDefinitionRepository.findAll().size();

        // Create the FieldChoiceDefinition
        restFieldChoiceDefinitionMockMvc.perform(post("/api/field-choice-definitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fieldChoiceDefinition)))
            .andExpect(status().isCreated());

        // Validate the FieldChoiceDefinition in the database
        List<FieldChoiceDefinition> fieldChoiceDefinitionList = fieldChoiceDefinitionRepository.findAll();
        assertThat(fieldChoiceDefinitionList).hasSize(databaseSizeBeforeCreate + 1);
        FieldChoiceDefinition testFieldChoiceDefinition = fieldChoiceDefinitionList.get(fieldChoiceDefinitionList.size() - 1);
        assertThat(testFieldChoiceDefinition.getKey()).isEqualTo(DEFAULT_KEY);
        assertThat(testFieldChoiceDefinition.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createFieldChoiceDefinitionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fieldChoiceDefinitionRepository.findAll().size();

        // Create the FieldChoiceDefinition with an existing ID
        fieldChoiceDefinition.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFieldChoiceDefinitionMockMvc.perform(post("/api/field-choice-definitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fieldChoiceDefinition)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<FieldChoiceDefinition> fieldChoiceDefinitionList = fieldChoiceDefinitionRepository.findAll();
        assertThat(fieldChoiceDefinitionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllFieldChoiceDefinitions() throws Exception {
        // Initialize the database
        fieldChoiceDefinitionRepository.saveAndFlush(fieldChoiceDefinition);

        // Get all the fieldChoiceDefinitionList
        restFieldChoiceDefinitionMockMvc.perform(get("/api/field-choice-definitions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fieldChoiceDefinition.getId().intValue())))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));
    }

    @Test
    @Transactional
    public void getFieldChoiceDefinition() throws Exception {
        // Initialize the database
        fieldChoiceDefinitionRepository.saveAndFlush(fieldChoiceDefinition);

        // Get the fieldChoiceDefinition
        restFieldChoiceDefinitionMockMvc.perform(get("/api/field-choice-definitions/{id}", fieldChoiceDefinition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(fieldChoiceDefinition.getId().intValue()))
            .andExpect(jsonPath("$.key").value(DEFAULT_KEY.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFieldChoiceDefinition() throws Exception {
        // Get the fieldChoiceDefinition
        restFieldChoiceDefinitionMockMvc.perform(get("/api/field-choice-definitions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFieldChoiceDefinition() throws Exception {
        // Initialize the database
        fieldChoiceDefinitionService.save(fieldChoiceDefinition);

        int databaseSizeBeforeUpdate = fieldChoiceDefinitionRepository.findAll().size();

        // Update the fieldChoiceDefinition
        FieldChoiceDefinition updatedFieldChoiceDefinition = fieldChoiceDefinitionRepository.findOne(fieldChoiceDefinition.getId());
        updatedFieldChoiceDefinition
            .key(UPDATED_KEY)
            .value(UPDATED_VALUE);

        restFieldChoiceDefinitionMockMvc.perform(put("/api/field-choice-definitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFieldChoiceDefinition)))
            .andExpect(status().isOk());

        // Validate the FieldChoiceDefinition in the database
        List<FieldChoiceDefinition> fieldChoiceDefinitionList = fieldChoiceDefinitionRepository.findAll();
        assertThat(fieldChoiceDefinitionList).hasSize(databaseSizeBeforeUpdate);
        FieldChoiceDefinition testFieldChoiceDefinition = fieldChoiceDefinitionList.get(fieldChoiceDefinitionList.size() - 1);
        assertThat(testFieldChoiceDefinition.getKey()).isEqualTo(UPDATED_KEY);
        assertThat(testFieldChoiceDefinition.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingFieldChoiceDefinition() throws Exception {
        int databaseSizeBeforeUpdate = fieldChoiceDefinitionRepository.findAll().size();

        // Create the FieldChoiceDefinition

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFieldChoiceDefinitionMockMvc.perform(put("/api/field-choice-definitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fieldChoiceDefinition)))
            .andExpect(status().isCreated());

        // Validate the FieldChoiceDefinition in the database
        List<FieldChoiceDefinition> fieldChoiceDefinitionList = fieldChoiceDefinitionRepository.findAll();
        assertThat(fieldChoiceDefinitionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFieldChoiceDefinition() throws Exception {
        // Initialize the database
        fieldChoiceDefinitionService.save(fieldChoiceDefinition);

        int databaseSizeBeforeDelete = fieldChoiceDefinitionRepository.findAll().size();

        // Get the fieldChoiceDefinition
        restFieldChoiceDefinitionMockMvc.perform(delete("/api/field-choice-definitions/{id}", fieldChoiceDefinition.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FieldChoiceDefinition> fieldChoiceDefinitionList = fieldChoiceDefinitionRepository.findAll();
        assertThat(fieldChoiceDefinitionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FieldChoiceDefinition.class);
        FieldChoiceDefinition fieldChoiceDefinition1 = new FieldChoiceDefinition();
        fieldChoiceDefinition1.setId(1L);
        FieldChoiceDefinition fieldChoiceDefinition2 = new FieldChoiceDefinition();
        fieldChoiceDefinition2.setId(fieldChoiceDefinition1.getId());
        assertThat(fieldChoiceDefinition1).isEqualTo(fieldChoiceDefinition2);
        fieldChoiceDefinition2.setId(2L);
        assertThat(fieldChoiceDefinition1).isNotEqualTo(fieldChoiceDefinition2);
        fieldChoiceDefinition1.setId(null);
        assertThat(fieldChoiceDefinition1).isNotEqualTo(fieldChoiceDefinition2);
    }
}
