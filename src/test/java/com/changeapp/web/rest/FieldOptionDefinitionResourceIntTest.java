package com.changeapp.web.rest;

import com.changeapp.ChangeAppServerApp;

import com.changeapp.domain.FieldOptionDefinition;
import com.changeapp.repository.FieldOptionDefinitionRepository;
import com.changeapp.service.FieldOptionDefinitionService;
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
 * Test class for the FieldOptionDefinitionResource REST controller.
 *
 * @see FieldOptionDefinitionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ChangeAppServerApp.class)
public class FieldOptionDefinitionResourceIntTest {

    private static final String DEFAULT_KEY = "AAAAAAAAAA";
    private static final String UPDATED_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Autowired
    private FieldOptionDefinitionRepository fieldOptionDefinitionRepository;

    @Autowired
    private FieldOptionDefinitionService fieldOptionDefinitionService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFieldOptionDefinitionMockMvc;

    private FieldOptionDefinition fieldOptionDefinition;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FieldOptionDefinitionResource fieldOptionDefinitionResource = new FieldOptionDefinitionResource(fieldOptionDefinitionService);
        this.restFieldOptionDefinitionMockMvc = MockMvcBuilders.standaloneSetup(fieldOptionDefinitionResource)
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
    public static FieldOptionDefinition createEntity(EntityManager em) {
        FieldOptionDefinition fieldOptionDefinition = new FieldOptionDefinition()
            .key(DEFAULT_KEY)
            .value(DEFAULT_VALUE);
        return fieldOptionDefinition;
    }

    @Before
    public void initTest() {
        fieldOptionDefinition = createEntity(em);
    }

    @Test
    @Transactional
    public void createFieldOptionDefinition() throws Exception {
        int databaseSizeBeforeCreate = fieldOptionDefinitionRepository.findAll().size();

        // Create the FieldOptionDefinition
        restFieldOptionDefinitionMockMvc.perform(post("/api/field-option-definitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fieldOptionDefinition)))
            .andExpect(status().isCreated());

        // Validate the FieldOptionDefinition in the database
        List<FieldOptionDefinition> fieldOptionDefinitionList = fieldOptionDefinitionRepository.findAll();
        assertThat(fieldOptionDefinitionList).hasSize(databaseSizeBeforeCreate + 1);
        FieldOptionDefinition testFieldOptionDefinition = fieldOptionDefinitionList.get(fieldOptionDefinitionList.size() - 1);
        assertThat(testFieldOptionDefinition.getKey()).isEqualTo(DEFAULT_KEY);
        assertThat(testFieldOptionDefinition.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createFieldOptionDefinitionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fieldOptionDefinitionRepository.findAll().size();

        // Create the FieldOptionDefinition with an existing ID
        fieldOptionDefinition.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFieldOptionDefinitionMockMvc.perform(post("/api/field-option-definitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fieldOptionDefinition)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<FieldOptionDefinition> fieldOptionDefinitionList = fieldOptionDefinitionRepository.findAll();
        assertThat(fieldOptionDefinitionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllFieldOptionDefinitions() throws Exception {
        // Initialize the database
        fieldOptionDefinitionRepository.saveAndFlush(fieldOptionDefinition);

        // Get all the fieldOptionDefinitionList
        restFieldOptionDefinitionMockMvc.perform(get("/api/field-option-definitions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fieldOptionDefinition.getId().intValue())))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));
    }

    @Test
    @Transactional
    public void getFieldOptionDefinition() throws Exception {
        // Initialize the database
        fieldOptionDefinitionRepository.saveAndFlush(fieldOptionDefinition);

        // Get the fieldOptionDefinition
        restFieldOptionDefinitionMockMvc.perform(get("/api/field-option-definitions/{id}", fieldOptionDefinition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(fieldOptionDefinition.getId().intValue()))
            .andExpect(jsonPath("$.key").value(DEFAULT_KEY.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFieldOptionDefinition() throws Exception {
        // Get the fieldOptionDefinition
        restFieldOptionDefinitionMockMvc.perform(get("/api/field-option-definitions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFieldOptionDefinition() throws Exception {
        // Initialize the database
        fieldOptionDefinitionService.save(fieldOptionDefinition);

        int databaseSizeBeforeUpdate = fieldOptionDefinitionRepository.findAll().size();

        // Update the fieldOptionDefinition
        FieldOptionDefinition updatedFieldOptionDefinition = fieldOptionDefinitionRepository.findOne(fieldOptionDefinition.getId());
        updatedFieldOptionDefinition
            .key(UPDATED_KEY)
            .value(UPDATED_VALUE);

        restFieldOptionDefinitionMockMvc.perform(put("/api/field-option-definitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFieldOptionDefinition)))
            .andExpect(status().isOk());

        // Validate the FieldOptionDefinition in the database
        List<FieldOptionDefinition> fieldOptionDefinitionList = fieldOptionDefinitionRepository.findAll();
        assertThat(fieldOptionDefinitionList).hasSize(databaseSizeBeforeUpdate);
        FieldOptionDefinition testFieldOptionDefinition = fieldOptionDefinitionList.get(fieldOptionDefinitionList.size() - 1);
        assertThat(testFieldOptionDefinition.getKey()).isEqualTo(UPDATED_KEY);
        assertThat(testFieldOptionDefinition.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingFieldOptionDefinition() throws Exception {
        int databaseSizeBeforeUpdate = fieldOptionDefinitionRepository.findAll().size();

        // Create the FieldOptionDefinition

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFieldOptionDefinitionMockMvc.perform(put("/api/field-option-definitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fieldOptionDefinition)))
            .andExpect(status().isCreated());

        // Validate the FieldOptionDefinition in the database
        List<FieldOptionDefinition> fieldOptionDefinitionList = fieldOptionDefinitionRepository.findAll();
        assertThat(fieldOptionDefinitionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFieldOptionDefinition() throws Exception {
        // Initialize the database
        fieldOptionDefinitionService.save(fieldOptionDefinition);

        int databaseSizeBeforeDelete = fieldOptionDefinitionRepository.findAll().size();

        // Get the fieldOptionDefinition
        restFieldOptionDefinitionMockMvc.perform(delete("/api/field-option-definitions/{id}", fieldOptionDefinition.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FieldOptionDefinition> fieldOptionDefinitionList = fieldOptionDefinitionRepository.findAll();
        assertThat(fieldOptionDefinitionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FieldOptionDefinition.class);
        FieldOptionDefinition fieldOptionDefinition1 = new FieldOptionDefinition();
        fieldOptionDefinition1.setId(1L);
        FieldOptionDefinition fieldOptionDefinition2 = new FieldOptionDefinition();
        fieldOptionDefinition2.setId(fieldOptionDefinition1.getId());
        assertThat(fieldOptionDefinition1).isEqualTo(fieldOptionDefinition2);
        fieldOptionDefinition2.setId(2L);
        assertThat(fieldOptionDefinition1).isNotEqualTo(fieldOptionDefinition2);
        fieldOptionDefinition1.setId(null);
        assertThat(fieldOptionDefinition1).isNotEqualTo(fieldOptionDefinition2);
    }
}
