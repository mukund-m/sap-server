package com.changeapp.web.rest;

import com.changeapp.ChangeAppServerApp;

import com.changeapp.domain.DefinitionConfig;
import com.changeapp.repository.DefinitionConfigRepository;
import com.changeapp.service.DefinitionConfigService;
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
 * Test class for the DefinitionConfigResource REST controller.
 *
 * @see DefinitionConfigResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ChangeAppServerApp.class)
public class DefinitionConfigResourceIntTest {

    private static final String DEFAULT_DEF_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DEF_NAME = "BBBBBBBBBB";

    @Autowired
    private DefinitionConfigRepository definitionConfigRepository;

    @Autowired
    private DefinitionConfigService definitionConfigService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDefinitionConfigMockMvc;

    private DefinitionConfig definitionConfig;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DefinitionConfigResource definitionConfigResource = new DefinitionConfigResource(definitionConfigService);
        this.restDefinitionConfigMockMvc = MockMvcBuilders.standaloneSetup(definitionConfigResource)
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
    public static DefinitionConfig createEntity(EntityManager em) {
        DefinitionConfig definitionConfig = new DefinitionConfig()
            .defName(DEFAULT_DEF_NAME);
        return definitionConfig;
    }

    @Before
    public void initTest() {
        definitionConfig = createEntity(em);
    }

    @Test
    @Transactional
    public void createDefinitionConfig() throws Exception {
        int databaseSizeBeforeCreate = definitionConfigRepository.findAll().size();

        // Create the DefinitionConfig
        restDefinitionConfigMockMvc.perform(post("/api/definition-configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(definitionConfig)))
            .andExpect(status().isCreated());

        // Validate the DefinitionConfig in the database
        List<DefinitionConfig> definitionConfigList = definitionConfigRepository.findAll();
        assertThat(definitionConfigList).hasSize(databaseSizeBeforeCreate + 1);
        DefinitionConfig testDefinitionConfig = definitionConfigList.get(definitionConfigList.size() - 1);
        assertThat(testDefinitionConfig.getDefName()).isEqualTo(DEFAULT_DEF_NAME);
    }

    @Test
    @Transactional
    public void createDefinitionConfigWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = definitionConfigRepository.findAll().size();

        // Create the DefinitionConfig with an existing ID
        definitionConfig.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDefinitionConfigMockMvc.perform(post("/api/definition-configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(definitionConfig)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<DefinitionConfig> definitionConfigList = definitionConfigRepository.findAll();
        assertThat(definitionConfigList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDefinitionConfigs() throws Exception {
        // Initialize the database
        definitionConfigRepository.saveAndFlush(definitionConfig);

        // Get all the definitionConfigList
        restDefinitionConfigMockMvc.perform(get("/api/definition-configs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(definitionConfig.getId().intValue())))
            .andExpect(jsonPath("$.[*].defName").value(hasItem(DEFAULT_DEF_NAME.toString())));
    }

    @Test
    @Transactional
    public void getDefinitionConfig() throws Exception {
        // Initialize the database
        definitionConfigRepository.saveAndFlush(definitionConfig);

        // Get the definitionConfig
        restDefinitionConfigMockMvc.perform(get("/api/definition-configs/{id}", definitionConfig.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(definitionConfig.getId().intValue()))
            .andExpect(jsonPath("$.defName").value(DEFAULT_DEF_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDefinitionConfig() throws Exception {
        // Get the definitionConfig
        restDefinitionConfigMockMvc.perform(get("/api/definition-configs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDefinitionConfig() throws Exception {
        // Initialize the database
        definitionConfigService.save(definitionConfig);

        int databaseSizeBeforeUpdate = definitionConfigRepository.findAll().size();

        // Update the definitionConfig
        DefinitionConfig updatedDefinitionConfig = definitionConfigRepository.findOne(definitionConfig.getId());
        updatedDefinitionConfig
            .defName(UPDATED_DEF_NAME);

        restDefinitionConfigMockMvc.perform(put("/api/definition-configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDefinitionConfig)))
            .andExpect(status().isOk());

        // Validate the DefinitionConfig in the database
        List<DefinitionConfig> definitionConfigList = definitionConfigRepository.findAll();
        assertThat(definitionConfigList).hasSize(databaseSizeBeforeUpdate);
        DefinitionConfig testDefinitionConfig = definitionConfigList.get(definitionConfigList.size() - 1);
        assertThat(testDefinitionConfig.getDefName()).isEqualTo(UPDATED_DEF_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingDefinitionConfig() throws Exception {
        int databaseSizeBeforeUpdate = definitionConfigRepository.findAll().size();

        // Create the DefinitionConfig

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDefinitionConfigMockMvc.perform(put("/api/definition-configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(definitionConfig)))
            .andExpect(status().isCreated());

        // Validate the DefinitionConfig in the database
        List<DefinitionConfig> definitionConfigList = definitionConfigRepository.findAll();
        assertThat(definitionConfigList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDefinitionConfig() throws Exception {
        // Initialize the database
        definitionConfigService.save(definitionConfig);

        int databaseSizeBeforeDelete = definitionConfigRepository.findAll().size();

        // Get the definitionConfig
        restDefinitionConfigMockMvc.perform(delete("/api/definition-configs/{id}", definitionConfig.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DefinitionConfig> definitionConfigList = definitionConfigRepository.findAll();
        assertThat(definitionConfigList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DefinitionConfig.class);
        DefinitionConfig definitionConfig1 = new DefinitionConfig();
        definitionConfig1.setId(1L);
        DefinitionConfig definitionConfig2 = new DefinitionConfig();
        definitionConfig2.setId(definitionConfig1.getId());
        assertThat(definitionConfig1).isEqualTo(definitionConfig2);
        definitionConfig2.setId(2L);
        assertThat(definitionConfig1).isNotEqualTo(definitionConfig2);
        definitionConfig1.setId(null);
        assertThat(definitionConfig1).isNotEqualTo(definitionConfig2);
    }
}
