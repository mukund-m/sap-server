package com.changeapp.web.rest;

import com.changeapp.ChangeAppServerApp;

import com.changeapp.domain.AttachmentType;
import com.changeapp.repository.AttachmentTypeRepository;
import com.changeapp.service.AttachmentTypeService;
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
 * Test class for the AttachmentTypeResource REST controller.
 *
 * @see AttachmentTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ChangeAppServerApp.class)
public class AttachmentTypeResourceIntTest {

    private static final String DEFAULT_TYPE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_TYPE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private AttachmentTypeRepository attachmentTypeRepository;

    @Autowired
    private AttachmentTypeService attachmentTypeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAttachmentTypeMockMvc;

    private AttachmentType attachmentType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AttachmentTypeResource attachmentTypeResource = new AttachmentTypeResource(attachmentTypeService);
        this.restAttachmentTypeMockMvc = MockMvcBuilders.standaloneSetup(attachmentTypeResource)
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
    public static AttachmentType createEntity(EntityManager em) {
        AttachmentType attachmentType = new AttachmentType()
            .typeName(DEFAULT_TYPE_NAME)
            .description(DEFAULT_DESCRIPTION);
        return attachmentType;
    }

    @Before
    public void initTest() {
        attachmentType = createEntity(em);
    }

    @Test
    @Transactional
    public void createAttachmentType() throws Exception {
        int databaseSizeBeforeCreate = attachmentTypeRepository.findAll().size();

        // Create the AttachmentType
        restAttachmentTypeMockMvc.perform(post("/api/attachment-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(attachmentType)))
            .andExpect(status().isCreated());

        // Validate the AttachmentType in the database
        List<AttachmentType> attachmentTypeList = attachmentTypeRepository.findAll();
        assertThat(attachmentTypeList).hasSize(databaseSizeBeforeCreate + 1);
        AttachmentType testAttachmentType = attachmentTypeList.get(attachmentTypeList.size() - 1);
        assertThat(testAttachmentType.getTypeName()).isEqualTo(DEFAULT_TYPE_NAME);
        assertThat(testAttachmentType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createAttachmentTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = attachmentTypeRepository.findAll().size();

        // Create the AttachmentType with an existing ID
        attachmentType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAttachmentTypeMockMvc.perform(post("/api/attachment-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(attachmentType)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<AttachmentType> attachmentTypeList = attachmentTypeRepository.findAll();
        assertThat(attachmentTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAttachmentTypes() throws Exception {
        // Initialize the database
        attachmentTypeRepository.saveAndFlush(attachmentType);

        // Get all the attachmentTypeList
        restAttachmentTypeMockMvc.perform(get("/api/attachment-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(attachmentType.getId().intValue())))
            .andExpect(jsonPath("$.[*].typeName").value(hasItem(DEFAULT_TYPE_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getAttachmentType() throws Exception {
        // Initialize the database
        attachmentTypeRepository.saveAndFlush(attachmentType);

        // Get the attachmentType
        restAttachmentTypeMockMvc.perform(get("/api/attachment-types/{id}", attachmentType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(attachmentType.getId().intValue()))
            .andExpect(jsonPath("$.typeName").value(DEFAULT_TYPE_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAttachmentType() throws Exception {
        // Get the attachmentType
        restAttachmentTypeMockMvc.perform(get("/api/attachment-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAttachmentType() throws Exception {
        // Initialize the database
        attachmentTypeService.save(attachmentType);

        int databaseSizeBeforeUpdate = attachmentTypeRepository.findAll().size();

        // Update the attachmentType
        AttachmentType updatedAttachmentType = attachmentTypeRepository.findOne(attachmentType.getId());
        updatedAttachmentType
            .typeName(UPDATED_TYPE_NAME)
            .description(UPDATED_DESCRIPTION);

        restAttachmentTypeMockMvc.perform(put("/api/attachment-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAttachmentType)))
            .andExpect(status().isOk());

        // Validate the AttachmentType in the database
        List<AttachmentType> attachmentTypeList = attachmentTypeRepository.findAll();
        assertThat(attachmentTypeList).hasSize(databaseSizeBeforeUpdate);
        AttachmentType testAttachmentType = attachmentTypeList.get(attachmentTypeList.size() - 1);
        assertThat(testAttachmentType.getTypeName()).isEqualTo(UPDATED_TYPE_NAME);
        assertThat(testAttachmentType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingAttachmentType() throws Exception {
        int databaseSizeBeforeUpdate = attachmentTypeRepository.findAll().size();

        // Create the AttachmentType

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAttachmentTypeMockMvc.perform(put("/api/attachment-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(attachmentType)))
            .andExpect(status().isCreated());

        // Validate the AttachmentType in the database
        List<AttachmentType> attachmentTypeList = attachmentTypeRepository.findAll();
        assertThat(attachmentTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAttachmentType() throws Exception {
        // Initialize the database
        attachmentTypeService.save(attachmentType);

        int databaseSizeBeforeDelete = attachmentTypeRepository.findAll().size();

        // Get the attachmentType
        restAttachmentTypeMockMvc.perform(delete("/api/attachment-types/{id}", attachmentType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AttachmentType> attachmentTypeList = attachmentTypeRepository.findAll();
        assertThat(attachmentTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AttachmentType.class);
        AttachmentType attachmentType1 = new AttachmentType();
        attachmentType1.setId(1L);
        AttachmentType attachmentType2 = new AttachmentType();
        attachmentType2.setId(attachmentType1.getId());
        assertThat(attachmentType1).isEqualTo(attachmentType2);
        attachmentType2.setId(2L);
        assertThat(attachmentType1).isNotEqualTo(attachmentType2);
        attachmentType1.setId(null);
        assertThat(attachmentType1).isNotEqualTo(attachmentType2);
    }
}
