package com.changeapp.web.rest;

import com.changeapp.ChangeAppServerApp;

import com.changeapp.domain.FieldDefinition;
import com.changeapp.repository.FieldDefinitionRepository;
import com.changeapp.service.FieldDefinitionService;
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
 * Test class for the FieldDefinitionResource REST controller.
 *
 * @see FieldDefinitionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ChangeAppServerApp.class)
public class FieldDefinitionResourceIntTest {

    private static final String DEFAULT_FIELD_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_FIELD_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_KEY = "AAAAAAAAAA";
    private static final String UPDATED_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PLACE_HOLDER = "AAAAAAAAAA";
    private static final String UPDATED_PLACE_HOLDER = "BBBBBBBBBB";

    private static final Boolean DEFAULT_MANDATORY = false;
    private static final Boolean UPDATED_MANDATORY = true;

    @Autowired
    private FieldDefinitionRepository fieldDefinitionRepository;

    @Autowired
    private FieldDefinitionService fieldDefinitionService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFieldDefinitionMockMvc;

    private FieldDefinition fieldDefinition;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FieldDefinitionResource fieldDefinitionResource = new FieldDefinitionResource(fieldDefinitionService);
        this.restFieldDefinitionMockMvc = MockMvcBuilders.standaloneSetup(fieldDefinitionResource)
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
    public static FieldDefinition createEntity(EntityManager em) {
        FieldDefinition fieldDefinition = new FieldDefinition()
            .fieldType(DEFAULT_FIELD_TYPE)
            .key(DEFAULT_KEY)
            .name(DEFAULT_NAME)
            .placeHolder(DEFAULT_PLACE_HOLDER)
            .mandatory(DEFAULT_MANDATORY);
        return fieldDefinition;
    }

    @Before
    public void initTest() {
        fieldDefinition = createEntity(em);
    }

    @Test
    @Transactional
    public void createFieldDefinition() throws Exception {
        int databaseSizeBeforeCreate = fieldDefinitionRepository.findAll().size();

        // Create the FieldDefinition
        restFieldDefinitionMockMvc.perform(post("/api/field-definitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fieldDefinition)))
            .andExpect(status().isCreated());

        // Validate the FieldDefinition in the database
        List<FieldDefinition> fieldDefinitionList = fieldDefinitionRepository.findAll();
        assertThat(fieldDefinitionList).hasSize(databaseSizeBeforeCreate + 1);
        FieldDefinition testFieldDefinition = fieldDefinitionList.get(fieldDefinitionList.size() - 1);
        assertThat(testFieldDefinition.getFieldType()).isEqualTo(DEFAULT_FIELD_TYPE);
        assertThat(testFieldDefinition.getKey()).isEqualTo(DEFAULT_KEY);
        assertThat(testFieldDefinition.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFieldDefinition.getPlaceHolder()).isEqualTo(DEFAULT_PLACE_HOLDER);
        assertThat(testFieldDefinition.isMandatory()).isEqualTo(DEFAULT_MANDATORY);
    }

    @Test
    @Transactional
    public void createFieldDefinitionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fieldDefinitionRepository.findAll().size();

        // Create the FieldDefinition with an existing ID
        fieldDefinition.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFieldDefinitionMockMvc.perform(post("/api/field-definitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fieldDefinition)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<FieldDefinition> fieldDefinitionList = fieldDefinitionRepository.findAll();
        assertThat(fieldDefinitionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllFieldDefinitions() throws Exception {
        // Initialize the database
        fieldDefinitionRepository.saveAndFlush(fieldDefinition);

        // Get all the fieldDefinitionList
        restFieldDefinitionMockMvc.perform(get("/api/field-definitions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fieldDefinition.getId().intValue())))
            .andExpect(jsonPath("$.[*].fieldType").value(hasItem(DEFAULT_FIELD_TYPE.toString())))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].placeHolder").value(hasItem(DEFAULT_PLACE_HOLDER.toString())))
            .andExpect(jsonPath("$.[*].mandatory").value(hasItem(DEFAULT_MANDATORY.booleanValue())));
    }

    @Test
    @Transactional
    public void getFieldDefinition() throws Exception {
        // Initialize the database
        fieldDefinitionRepository.saveAndFlush(fieldDefinition);

        // Get the fieldDefinition
        restFieldDefinitionMockMvc.perform(get("/api/field-definitions/{id}", fieldDefinition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(fieldDefinition.getId().intValue()))
            .andExpect(jsonPath("$.fieldType").value(DEFAULT_FIELD_TYPE.toString()))
            .andExpect(jsonPath("$.key").value(DEFAULT_KEY.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.placeHolder").value(DEFAULT_PLACE_HOLDER.toString()))
            .andExpect(jsonPath("$.mandatory").value(DEFAULT_MANDATORY.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingFieldDefinition() throws Exception {
        // Get the fieldDefinition
        restFieldDefinitionMockMvc.perform(get("/api/field-definitions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFieldDefinition() throws Exception {
        // Initialize the database
        fieldDefinitionService.save(fieldDefinition);

        int databaseSizeBeforeUpdate = fieldDefinitionRepository.findAll().size();

        // Update the fieldDefinition
        FieldDefinition updatedFieldDefinition = fieldDefinitionRepository.findOne(fieldDefinition.getId());
        updatedFieldDefinition
            .fieldType(UPDATED_FIELD_TYPE)
            .key(UPDATED_KEY)
            .name(UPDATED_NAME)
            .placeHolder(UPDATED_PLACE_HOLDER)
            .mandatory(UPDATED_MANDATORY);

        restFieldDefinitionMockMvc.perform(put("/api/field-definitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFieldDefinition)))
            .andExpect(status().isOk());

        // Validate the FieldDefinition in the database
        List<FieldDefinition> fieldDefinitionList = fieldDefinitionRepository.findAll();
        assertThat(fieldDefinitionList).hasSize(databaseSizeBeforeUpdate);
        FieldDefinition testFieldDefinition = fieldDefinitionList.get(fieldDefinitionList.size() - 1);
        assertThat(testFieldDefinition.getFieldType()).isEqualTo(UPDATED_FIELD_TYPE);
        assertThat(testFieldDefinition.getKey()).isEqualTo(UPDATED_KEY);
        assertThat(testFieldDefinition.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFieldDefinition.getPlaceHolder()).isEqualTo(UPDATED_PLACE_HOLDER);
        assertThat(testFieldDefinition.isMandatory()).isEqualTo(UPDATED_MANDATORY);
    }

    @Test
    @Transactional
    public void updateNonExistingFieldDefinition() throws Exception {
        int databaseSizeBeforeUpdate = fieldDefinitionRepository.findAll().size();

        // Create the FieldDefinition

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFieldDefinitionMockMvc.perform(put("/api/field-definitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fieldDefinition)))
            .andExpect(status().isCreated());

        // Validate the FieldDefinition in the database
        List<FieldDefinition> fieldDefinitionList = fieldDefinitionRepository.findAll();
        assertThat(fieldDefinitionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFieldDefinition() throws Exception {
        // Initialize the database
        fieldDefinitionService.save(fieldDefinition);

        int databaseSizeBeforeDelete = fieldDefinitionRepository.findAll().size();

        // Get the fieldDefinition
        restFieldDefinitionMockMvc.perform(delete("/api/field-definitions/{id}", fieldDefinition.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FieldDefinition> fieldDefinitionList = fieldDefinitionRepository.findAll();
        assertThat(fieldDefinitionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FieldDefinition.class);
        FieldDefinition fieldDefinition1 = new FieldDefinition();
        fieldDefinition1.setId(1L);
        FieldDefinition fieldDefinition2 = new FieldDefinition();
        fieldDefinition2.setId(fieldDefinition1.getId());
        assertThat(fieldDefinition1).isEqualTo(fieldDefinition2);
        fieldDefinition2.setId(2L);
        assertThat(fieldDefinition1).isNotEqualTo(fieldDefinition2);
        fieldDefinition1.setId(null);
        assertThat(fieldDefinition1).isNotEqualTo(fieldDefinition2);
    }
}
