package com.changeapp.web.rest;

import com.changeapp.ChangeAppServerApp;

import com.changeapp.domain.RequestTypeDefConfig;
import com.changeapp.repository.RequestTypeDefConfigRepository;
import com.changeapp.service.RequestTypeDefConfigService;
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
 * Test class for the RequestTypeDefConfigResource REST controller.
 *
 * @see RequestTypeDefConfigResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ChangeAppServerApp.class)
public class RequestTypeDefConfigResourceIntTest {

    private static final String DEFAULT_REQUEST_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_REQUEST_TYPE = "BBBBBBBBBB";

    @Autowired
    private RequestTypeDefConfigRepository requestTypeDefConfigRepository;

    @Autowired
    private RequestTypeDefConfigService requestTypeDefConfigService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRequestTypeDefConfigMockMvc;

    private RequestTypeDefConfig requestTypeDefConfig;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RequestTypeDefConfigResource requestTypeDefConfigResource = new RequestTypeDefConfigResource(requestTypeDefConfigService);
        this.restRequestTypeDefConfigMockMvc = MockMvcBuilders.standaloneSetup(requestTypeDefConfigResource)
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
    public static RequestTypeDefConfig createEntity(EntityManager em) {
        RequestTypeDefConfig requestTypeDefConfig = new RequestTypeDefConfig()
            .requestType(DEFAULT_REQUEST_TYPE);
        return requestTypeDefConfig;
    }

    @Before
    public void initTest() {
        requestTypeDefConfig = createEntity(em);
    }

    @Test
    @Transactional
    public void createRequestTypeDefConfig() throws Exception {
        int databaseSizeBeforeCreate = requestTypeDefConfigRepository.findAll().size();

        // Create the RequestTypeDefConfig
        restRequestTypeDefConfigMockMvc.perform(post("/api/request-type-def-configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requestTypeDefConfig)))
            .andExpect(status().isCreated());

        // Validate the RequestTypeDefConfig in the database
        List<RequestTypeDefConfig> requestTypeDefConfigList = requestTypeDefConfigRepository.findAll();
        assertThat(requestTypeDefConfigList).hasSize(databaseSizeBeforeCreate + 1);
        RequestTypeDefConfig testRequestTypeDefConfig = requestTypeDefConfigList.get(requestTypeDefConfigList.size() - 1);
        assertThat(testRequestTypeDefConfig.getRequestType()).isEqualTo(DEFAULT_REQUEST_TYPE);
    }

    @Test
    @Transactional
    public void createRequestTypeDefConfigWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = requestTypeDefConfigRepository.findAll().size();

        // Create the RequestTypeDefConfig with an existing ID
        requestTypeDefConfig.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRequestTypeDefConfigMockMvc.perform(post("/api/request-type-def-configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requestTypeDefConfig)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<RequestTypeDefConfig> requestTypeDefConfigList = requestTypeDefConfigRepository.findAll();
        assertThat(requestTypeDefConfigList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllRequestTypeDefConfigs() throws Exception {
        // Initialize the database
        requestTypeDefConfigRepository.saveAndFlush(requestTypeDefConfig);

        // Get all the requestTypeDefConfigList
        restRequestTypeDefConfigMockMvc.perform(get("/api/request-type-def-configs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(requestTypeDefConfig.getId().intValue())))
            .andExpect(jsonPath("$.[*].requestType").value(hasItem(DEFAULT_REQUEST_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getRequestTypeDefConfig() throws Exception {
        // Initialize the database
        requestTypeDefConfigRepository.saveAndFlush(requestTypeDefConfig);

        // Get the requestTypeDefConfig
        restRequestTypeDefConfigMockMvc.perform(get("/api/request-type-def-configs/{id}", requestTypeDefConfig.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(requestTypeDefConfig.getId().intValue()))
            .andExpect(jsonPath("$.requestType").value(DEFAULT_REQUEST_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRequestTypeDefConfig() throws Exception {
        // Get the requestTypeDefConfig
        restRequestTypeDefConfigMockMvc.perform(get("/api/request-type-def-configs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRequestTypeDefConfig() throws Exception {
        // Initialize the database
        requestTypeDefConfigService.save(requestTypeDefConfig);

        int databaseSizeBeforeUpdate = requestTypeDefConfigRepository.findAll().size();

        // Update the requestTypeDefConfig
        RequestTypeDefConfig updatedRequestTypeDefConfig = requestTypeDefConfigRepository.findOne(requestTypeDefConfig.getId());
        updatedRequestTypeDefConfig
            .requestType(UPDATED_REQUEST_TYPE);

        restRequestTypeDefConfigMockMvc.perform(put("/api/request-type-def-configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRequestTypeDefConfig)))
            .andExpect(status().isOk());

        // Validate the RequestTypeDefConfig in the database
        List<RequestTypeDefConfig> requestTypeDefConfigList = requestTypeDefConfigRepository.findAll();
        assertThat(requestTypeDefConfigList).hasSize(databaseSizeBeforeUpdate);
        RequestTypeDefConfig testRequestTypeDefConfig = requestTypeDefConfigList.get(requestTypeDefConfigList.size() - 1);
        assertThat(testRequestTypeDefConfig.getRequestType()).isEqualTo(UPDATED_REQUEST_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingRequestTypeDefConfig() throws Exception {
        int databaseSizeBeforeUpdate = requestTypeDefConfigRepository.findAll().size();

        // Create the RequestTypeDefConfig

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRequestTypeDefConfigMockMvc.perform(put("/api/request-type-def-configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requestTypeDefConfig)))
            .andExpect(status().isCreated());

        // Validate the RequestTypeDefConfig in the database
        List<RequestTypeDefConfig> requestTypeDefConfigList = requestTypeDefConfigRepository.findAll();
        assertThat(requestTypeDefConfigList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRequestTypeDefConfig() throws Exception {
        // Initialize the database
        requestTypeDefConfigService.save(requestTypeDefConfig);

        int databaseSizeBeforeDelete = requestTypeDefConfigRepository.findAll().size();

        // Get the requestTypeDefConfig
        restRequestTypeDefConfigMockMvc.perform(delete("/api/request-type-def-configs/{id}", requestTypeDefConfig.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RequestTypeDefConfig> requestTypeDefConfigList = requestTypeDefConfigRepository.findAll();
        assertThat(requestTypeDefConfigList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RequestTypeDefConfig.class);
        RequestTypeDefConfig requestTypeDefConfig1 = new RequestTypeDefConfig();
        requestTypeDefConfig1.setId(1L);
        RequestTypeDefConfig requestTypeDefConfig2 = new RequestTypeDefConfig();
        requestTypeDefConfig2.setId(requestTypeDefConfig1.getId());
        assertThat(requestTypeDefConfig1).isEqualTo(requestTypeDefConfig2);
        requestTypeDefConfig2.setId(2L);
        assertThat(requestTypeDefConfig1).isNotEqualTo(requestTypeDefConfig2);
        requestTypeDefConfig1.setId(null);
        assertThat(requestTypeDefConfig1).isNotEqualTo(requestTypeDefConfig2);
    }
}
