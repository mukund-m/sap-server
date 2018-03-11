package com.changeapp.web.rest;

import com.changeapp.ChangeAppServerApp;

import com.changeapp.domain.RequestAttachment;
import com.changeapp.repository.RequestAttachmentRepository;
import com.changeapp.service.RequestAttachmentService;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the RequestAttachmentResource REST controller.
 *
 * @see RequestAttachmentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ChangeAppServerApp.class)
public class RequestAttachmentResourceIntTest {

    private static final String DEFAULT_FILE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FILE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_UPLOADED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPLOADED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_UPLOADED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPLOADED_ON = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_ATTACHMENT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ATTACHMENT_TYPE = "BBBBBBBBBB";

    @Autowired
    private RequestAttachmentRepository requestAttachmentRepository;

    @Autowired
    private RequestAttachmentService requestAttachmentService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRequestAttachmentMockMvc;

    private RequestAttachment requestAttachment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RequestAttachmentResource requestAttachmentResource = new RequestAttachmentResource(requestAttachmentService, null);
        this.restRequestAttachmentMockMvc = MockMvcBuilders.standaloneSetup(requestAttachmentResource)
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
    public static RequestAttachment createEntity(EntityManager em) {
        RequestAttachment requestAttachment = new RequestAttachment()
            .fileName(DEFAULT_FILE_NAME)
            .uploadedBy(DEFAULT_UPLOADED_BY)
            .uploadedOn(DEFAULT_UPLOADED_ON)
            .attachmentType(DEFAULT_ATTACHMENT_TYPE);
        return requestAttachment;
    }

    @Before
    public void initTest() {
        requestAttachment = createEntity(em);
    }

    @Test
    @Transactional
    public void createRequestAttachment() throws Exception {
        int databaseSizeBeforeCreate = requestAttachmentRepository.findAll().size();

        // Create the RequestAttachment
        restRequestAttachmentMockMvc.perform(post("/api/request-attachments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requestAttachment)))
            .andExpect(status().isCreated());

        // Validate the RequestAttachment in the database
        List<RequestAttachment> requestAttachmentList = requestAttachmentRepository.findAll();
        assertThat(requestAttachmentList).hasSize(databaseSizeBeforeCreate + 1);
        RequestAttachment testRequestAttachment = requestAttachmentList.get(requestAttachmentList.size() - 1);
        assertThat(testRequestAttachment.getFileName()).isEqualTo(DEFAULT_FILE_NAME);
        assertThat(testRequestAttachment.getUploadedBy()).isEqualTo(DEFAULT_UPLOADED_BY);
        assertThat(testRequestAttachment.getUploadedOn()).isEqualTo(DEFAULT_UPLOADED_ON);
        assertThat(testRequestAttachment.getAttachmentType()).isEqualTo(DEFAULT_ATTACHMENT_TYPE);
    }

    @Test
    @Transactional
    public void createRequestAttachmentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = requestAttachmentRepository.findAll().size();

        // Create the RequestAttachment with an existing ID
        requestAttachment.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRequestAttachmentMockMvc.perform(post("/api/request-attachments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requestAttachment)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<RequestAttachment> requestAttachmentList = requestAttachmentRepository.findAll();
        assertThat(requestAttachmentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllRequestAttachments() throws Exception {
        // Initialize the database
        requestAttachmentRepository.saveAndFlush(requestAttachment);

        // Get all the requestAttachmentList
        restRequestAttachmentMockMvc.perform(get("/api/request-attachments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(requestAttachment.getId().intValue())))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME.toString())))
            .andExpect(jsonPath("$.[*].uploadedBy").value(hasItem(DEFAULT_UPLOADED_BY.toString())))
            .andExpect(jsonPath("$.[*].uploadedOn").value(hasItem(DEFAULT_UPLOADED_ON.toString())))
            .andExpect(jsonPath("$.[*].attachmentType").value(hasItem(DEFAULT_ATTACHMENT_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getRequestAttachment() throws Exception {
        // Initialize the database
        requestAttachmentRepository.saveAndFlush(requestAttachment);

        // Get the requestAttachment
        restRequestAttachmentMockMvc.perform(get("/api/request-attachments/{id}", requestAttachment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(requestAttachment.getId().intValue()))
            .andExpect(jsonPath("$.fileName").value(DEFAULT_FILE_NAME.toString()))
            .andExpect(jsonPath("$.uploadedBy").value(DEFAULT_UPLOADED_BY.toString()))
            .andExpect(jsonPath("$.uploadedOn").value(DEFAULT_UPLOADED_ON.toString()))
            .andExpect(jsonPath("$.attachmentType").value(DEFAULT_ATTACHMENT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRequestAttachment() throws Exception {
        // Get the requestAttachment
        restRequestAttachmentMockMvc.perform(get("/api/request-attachments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRequestAttachment() throws Exception {
        // Initialize the database
        requestAttachmentService.save(requestAttachment);

        int databaseSizeBeforeUpdate = requestAttachmentRepository.findAll().size();

        // Update the requestAttachment
        RequestAttachment updatedRequestAttachment = requestAttachmentRepository.findOne(requestAttachment.getId());
        updatedRequestAttachment
            .fileName(UPDATED_FILE_NAME)
            .uploadedBy(UPDATED_UPLOADED_BY)
            .uploadedOn(UPDATED_UPLOADED_ON)
            .attachmentType(UPDATED_ATTACHMENT_TYPE);

        restRequestAttachmentMockMvc.perform(put("/api/request-attachments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRequestAttachment)))
            .andExpect(status().isOk());

        // Validate the RequestAttachment in the database
        List<RequestAttachment> requestAttachmentList = requestAttachmentRepository.findAll();
        assertThat(requestAttachmentList).hasSize(databaseSizeBeforeUpdate);
        RequestAttachment testRequestAttachment = requestAttachmentList.get(requestAttachmentList.size() - 1);
        assertThat(testRequestAttachment.getFileName()).isEqualTo(UPDATED_FILE_NAME);
        assertThat(testRequestAttachment.getUploadedBy()).isEqualTo(UPDATED_UPLOADED_BY);
        assertThat(testRequestAttachment.getUploadedOn()).isEqualTo(UPDATED_UPLOADED_ON);
        assertThat(testRequestAttachment.getAttachmentType()).isEqualTo(UPDATED_ATTACHMENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingRequestAttachment() throws Exception {
        int databaseSizeBeforeUpdate = requestAttachmentRepository.findAll().size();

        // Create the RequestAttachment

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRequestAttachmentMockMvc.perform(put("/api/request-attachments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requestAttachment)))
            .andExpect(status().isCreated());

        // Validate the RequestAttachment in the database
        List<RequestAttachment> requestAttachmentList = requestAttachmentRepository.findAll();
        assertThat(requestAttachmentList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRequestAttachment() throws Exception {
        // Initialize the database
        requestAttachmentService.save(requestAttachment);

        int databaseSizeBeforeDelete = requestAttachmentRepository.findAll().size();

        // Get the requestAttachment
        restRequestAttachmentMockMvc.perform(delete("/api/request-attachments/{id}", requestAttachment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RequestAttachment> requestAttachmentList = requestAttachmentRepository.findAll();
        assertThat(requestAttachmentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RequestAttachment.class);
        RequestAttachment requestAttachment1 = new RequestAttachment();
        requestAttachment1.setId(1L);
        RequestAttachment requestAttachment2 = new RequestAttachment();
        requestAttachment2.setId(requestAttachment1.getId());
        assertThat(requestAttachment1).isEqualTo(requestAttachment2);
        requestAttachment2.setId(2L);
        assertThat(requestAttachment1).isNotEqualTo(requestAttachment2);
        requestAttachment1.setId(null);
        assertThat(requestAttachment1).isNotEqualTo(requestAttachment2);
    }
}
