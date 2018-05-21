package com.changeapp.web.rest;

import com.changeapp.ChangeAppServerApp;

import com.changeapp.domain.RefCodeDetails;
import com.changeapp.repository.RefCodeDetailsRepository;
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
 * Test class for the RefCodeDetailsResource REST controller.
 *
 * @see RefCodeDetailsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ChangeAppServerApp.class)
public class RefCodeDetailsResourceIntTest {

    private static final String DEFAULT_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY = "BBBBBBBBBB";

    private static final String DEFAULT_REF_CODE = "AAAAAAAAAA";
    private static final String UPDATED_REF_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_REF_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_REF_VALUE = "BBBBBBBBBB";

    @Autowired
    private RefCodeDetailsRepository refCodeDetailsRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRefCodeDetailsMockMvc;

    private RefCodeDetails refCodeDetails;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RefCodeDetailsResource refCodeDetailsResource = new RefCodeDetailsResource(refCodeDetailsRepository);
        this.restRefCodeDetailsMockMvc = MockMvcBuilders.standaloneSetup(refCodeDetailsResource)
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
    public static RefCodeDetails createEntity(EntityManager em) {
        RefCodeDetails refCodeDetails = new RefCodeDetails()
            .category(DEFAULT_CATEGORY)
            .refCode(DEFAULT_REF_CODE)
            .refValue(DEFAULT_REF_VALUE);
        return refCodeDetails;
    }

    @Before
    public void initTest() {
        refCodeDetails = createEntity(em);
    }

    @Test
    @Transactional
    public void createRefCodeDetails() throws Exception {
        int databaseSizeBeforeCreate = refCodeDetailsRepository.findAll().size();

        // Create the RefCodeDetails
        restRefCodeDetailsMockMvc.perform(post("/api/ref-code-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refCodeDetails)))
            .andExpect(status().isCreated());

        // Validate the RefCodeDetails in the database
        List<RefCodeDetails> refCodeDetailsList = refCodeDetailsRepository.findAll();
        assertThat(refCodeDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        RefCodeDetails testRefCodeDetails = refCodeDetailsList.get(refCodeDetailsList.size() - 1);
        assertThat(testRefCodeDetails.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testRefCodeDetails.getRefCode()).isEqualTo(DEFAULT_REF_CODE);
        assertThat(testRefCodeDetails.getRefValue()).isEqualTo(DEFAULT_REF_VALUE);
    }

    @Test
    @Transactional
    public void createRefCodeDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = refCodeDetailsRepository.findAll().size();

        // Create the RefCodeDetails with an existing ID
        refCodeDetails.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRefCodeDetailsMockMvc.perform(post("/api/ref-code-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refCodeDetails)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<RefCodeDetails> refCodeDetailsList = refCodeDetailsRepository.findAll();
        assertThat(refCodeDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllRefCodeDetails() throws Exception {
        // Initialize the database
        refCodeDetailsRepository.saveAndFlush(refCodeDetails);

        // Get all the refCodeDetailsList
        restRefCodeDetailsMockMvc.perform(get("/api/ref-code-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(refCodeDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].refCode").value(hasItem(DEFAULT_REF_CODE.toString())))
            .andExpect(jsonPath("$.[*].refValue").value(hasItem(DEFAULT_REF_VALUE.toString())));
    }

    @Test
    @Transactional
    public void getRefCodeDetails() throws Exception {
        // Initialize the database
        refCodeDetailsRepository.saveAndFlush(refCodeDetails);

        // Get the refCodeDetails
        restRefCodeDetailsMockMvc.perform(get("/api/ref-code-details/{id}", refCodeDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(refCodeDetails.getId().intValue()))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY.toString()))
            .andExpect(jsonPath("$.refCode").value(DEFAULT_REF_CODE.toString()))
            .andExpect(jsonPath("$.refValue").value(DEFAULT_REF_VALUE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRefCodeDetails() throws Exception {
        // Get the refCodeDetails
        restRefCodeDetailsMockMvc.perform(get("/api/ref-code-details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRefCodeDetails() throws Exception {
        // Initialize the database
        refCodeDetailsRepository.saveAndFlush(refCodeDetails);
        int databaseSizeBeforeUpdate = refCodeDetailsRepository.findAll().size();

        // Update the refCodeDetails
        RefCodeDetails updatedRefCodeDetails = refCodeDetailsRepository.findOne(refCodeDetails.getId());
        updatedRefCodeDetails
            .category(UPDATED_CATEGORY)
            .refCode(UPDATED_REF_CODE)
            .refValue(UPDATED_REF_VALUE);

        restRefCodeDetailsMockMvc.perform(put("/api/ref-code-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRefCodeDetails)))
            .andExpect(status().isOk());

        // Validate the RefCodeDetails in the database
        List<RefCodeDetails> refCodeDetailsList = refCodeDetailsRepository.findAll();
        assertThat(refCodeDetailsList).hasSize(databaseSizeBeforeUpdate);
        RefCodeDetails testRefCodeDetails = refCodeDetailsList.get(refCodeDetailsList.size() - 1);
        assertThat(testRefCodeDetails.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testRefCodeDetails.getRefCode()).isEqualTo(UPDATED_REF_CODE);
        assertThat(testRefCodeDetails.getRefValue()).isEqualTo(UPDATED_REF_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingRefCodeDetails() throws Exception {
        int databaseSizeBeforeUpdate = refCodeDetailsRepository.findAll().size();

        // Create the RefCodeDetails

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRefCodeDetailsMockMvc.perform(put("/api/ref-code-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refCodeDetails)))
            .andExpect(status().isCreated());

        // Validate the RefCodeDetails in the database
        List<RefCodeDetails> refCodeDetailsList = refCodeDetailsRepository.findAll();
        assertThat(refCodeDetailsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRefCodeDetails() throws Exception {
        // Initialize the database
        refCodeDetailsRepository.saveAndFlush(refCodeDetails);
        int databaseSizeBeforeDelete = refCodeDetailsRepository.findAll().size();

        // Get the refCodeDetails
        restRefCodeDetailsMockMvc.perform(delete("/api/ref-code-details/{id}", refCodeDetails.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RefCodeDetails> refCodeDetailsList = refCodeDetailsRepository.findAll();
        assertThat(refCodeDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RefCodeDetails.class);
        RefCodeDetails refCodeDetails1 = new RefCodeDetails();
        refCodeDetails1.setId(1L);
        RefCodeDetails refCodeDetails2 = new RefCodeDetails();
        refCodeDetails2.setId(refCodeDetails1.getId());
        assertThat(refCodeDetails1).isEqualTo(refCodeDetails2);
        refCodeDetails2.setId(2L);
        assertThat(refCodeDetails1).isNotEqualTo(refCodeDetails2);
        refCodeDetails1.setId(null);
        assertThat(refCodeDetails1).isNotEqualTo(refCodeDetails2);
    }
}
