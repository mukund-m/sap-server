package com.changeapp.web.rest;

import com.changeapp.ChangeAppServerApp;

import com.changeapp.domain.TaskQuestionInstance;
import com.changeapp.repository.TaskQuestionInstanceRepository;
import com.changeapp.service.TaskQuestionInstanceService;
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
 * Test class for the TaskQuestionInstanceResource REST controller.
 *
 * @see TaskQuestionInstanceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ChangeAppServerApp.class)
public class TaskQuestionInstanceResourceIntTest {

    private static final String DEFAULT_SORT_IN_PARENT_ID = "AAAAAAAAAA";
    private static final String UPDATED_SORT_IN_PARENT_ID = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DUE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DUE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_NOTIFIED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_NOTIFIED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_COMPLETED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_COMPLETED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_QUESTION_RESPONSE = "AAAAAAAAAA";
    private static final String UPDATED_QUESTION_RESPONSE = "BBBBBBBBBB";

    private static final Integer DEFAULT_PARENT_ID = 1;
    private static final Integer UPDATED_PARENT_ID = 2;

    private static final Integer DEFAULT_DEFINITION_ID = 1;
    private static final Integer UPDATED_DEFINITION_ID = 2;

    @Autowired
    private TaskQuestionInstanceRepository taskQuestionInstanceRepository;

    @Autowired
    private TaskQuestionInstanceService taskQuestionInstanceService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTaskQuestionInstanceMockMvc;

    private TaskQuestionInstance taskQuestionInstance;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TaskQuestionInstanceResource taskQuestionInstanceResource = new TaskQuestionInstanceResource(taskQuestionInstanceService);
        this.restTaskQuestionInstanceMockMvc = MockMvcBuilders.standaloneSetup(taskQuestionInstanceResource)
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
    public static TaskQuestionInstance createEntity(EntityManager em) {
        TaskQuestionInstance taskQuestionInstance = new TaskQuestionInstance()
            .sortInParentID(DEFAULT_SORT_IN_PARENT_ID)
            .dueDate(DEFAULT_DUE_DATE)
            .notifiedDate(DEFAULT_NOTIFIED_DATE)
            .completedDate(DEFAULT_COMPLETED_DATE)
            .status(DEFAULT_STATUS)
            .questionResponse(DEFAULT_QUESTION_RESPONSE)
            .parentID(DEFAULT_PARENT_ID)
            .definitionID(DEFAULT_DEFINITION_ID);
        return taskQuestionInstance;
    }

    @Before
    public void initTest() {
        taskQuestionInstance = createEntity(em);
    }

    @Test
    @Transactional
    public void createTaskQuestionInstance() throws Exception {
        int databaseSizeBeforeCreate = taskQuestionInstanceRepository.findAll().size();

        // Create the TaskQuestionInstance
        restTaskQuestionInstanceMockMvc.perform(post("/api/task-question-instances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taskQuestionInstance)))
            .andExpect(status().isCreated());

        // Validate the TaskQuestionInstance in the database
        List<TaskQuestionInstance> taskQuestionInstanceList = taskQuestionInstanceRepository.findAll();
        assertThat(taskQuestionInstanceList).hasSize(databaseSizeBeforeCreate + 1);
        TaskQuestionInstance testTaskQuestionInstance = taskQuestionInstanceList.get(taskQuestionInstanceList.size() - 1);
        assertThat(testTaskQuestionInstance.getSortInParentID()).isEqualTo(DEFAULT_SORT_IN_PARENT_ID);
        assertThat(testTaskQuestionInstance.getDueDate()).isEqualTo(DEFAULT_DUE_DATE);
        assertThat(testTaskQuestionInstance.getNotifiedDate()).isEqualTo(DEFAULT_NOTIFIED_DATE);
        assertThat(testTaskQuestionInstance.getCompletedDate()).isEqualTo(DEFAULT_COMPLETED_DATE);
        assertThat(testTaskQuestionInstance.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testTaskQuestionInstance.getQuestionResponse()).isEqualTo(DEFAULT_QUESTION_RESPONSE);
        assertThat(testTaskQuestionInstance.getParentID()).isEqualTo(DEFAULT_PARENT_ID);
        assertThat(testTaskQuestionInstance.getDefinitionID()).isEqualTo(DEFAULT_DEFINITION_ID);
    }

    @Test
    @Transactional
    public void createTaskQuestionInstanceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = taskQuestionInstanceRepository.findAll().size();

        // Create the TaskQuestionInstance with an existing ID
        taskQuestionInstance.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaskQuestionInstanceMockMvc.perform(post("/api/task-question-instances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taskQuestionInstance)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TaskQuestionInstance> taskQuestionInstanceList = taskQuestionInstanceRepository.findAll();
        assertThat(taskQuestionInstanceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTaskQuestionInstances() throws Exception {
        // Initialize the database
        taskQuestionInstanceRepository.saveAndFlush(taskQuestionInstance);

        // Get all the taskQuestionInstanceList
        restTaskQuestionInstanceMockMvc.perform(get("/api/task-question-instances?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taskQuestionInstance.getId().intValue())))
            .andExpect(jsonPath("$.[*].sortInParentID").value(hasItem(DEFAULT_SORT_IN_PARENT_ID.toString())))
            .andExpect(jsonPath("$.[*].dueDate").value(hasItem(DEFAULT_DUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].notifiedDate").value(hasItem(DEFAULT_NOTIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].completedDate").value(hasItem(DEFAULT_COMPLETED_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].questionResponse").value(hasItem(DEFAULT_QUESTION_RESPONSE.toString())))
            .andExpect(jsonPath("$.[*].parentID").value(hasItem(DEFAULT_PARENT_ID)))
            .andExpect(jsonPath("$.[*].definitionID").value(hasItem(DEFAULT_DEFINITION_ID)));
    }

    @Test
    @Transactional
    public void getTaskQuestionInstance() throws Exception {
        // Initialize the database
        taskQuestionInstanceRepository.saveAndFlush(taskQuestionInstance);

        // Get the taskQuestionInstance
        restTaskQuestionInstanceMockMvc.perform(get("/api/task-question-instances/{id}", taskQuestionInstance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(taskQuestionInstance.getId().intValue()))
            .andExpect(jsonPath("$.sortInParentID").value(DEFAULT_SORT_IN_PARENT_ID.toString()))
            .andExpect(jsonPath("$.dueDate").value(DEFAULT_DUE_DATE.toString()))
            .andExpect(jsonPath("$.notifiedDate").value(DEFAULT_NOTIFIED_DATE.toString()))
            .andExpect(jsonPath("$.completedDate").value(DEFAULT_COMPLETED_DATE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.questionResponse").value(DEFAULT_QUESTION_RESPONSE.toString()))
            .andExpect(jsonPath("$.parentID").value(DEFAULT_PARENT_ID))
            .andExpect(jsonPath("$.definitionID").value(DEFAULT_DEFINITION_ID));
    }

    @Test
    @Transactional
    public void getNonExistingTaskQuestionInstance() throws Exception {
        // Get the taskQuestionInstance
        restTaskQuestionInstanceMockMvc.perform(get("/api/task-question-instances/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTaskQuestionInstance() throws Exception {
        // Initialize the database
        taskQuestionInstanceService.save(taskQuestionInstance);

        int databaseSizeBeforeUpdate = taskQuestionInstanceRepository.findAll().size();

        // Update the taskQuestionInstance
        TaskQuestionInstance updatedTaskQuestionInstance = taskQuestionInstanceRepository.findOne(taskQuestionInstance.getId());
        updatedTaskQuestionInstance
            .sortInParentID(UPDATED_SORT_IN_PARENT_ID)
            .dueDate(UPDATED_DUE_DATE)
            .notifiedDate(UPDATED_NOTIFIED_DATE)
            .completedDate(UPDATED_COMPLETED_DATE)
            .status(UPDATED_STATUS)
            .questionResponse(UPDATED_QUESTION_RESPONSE)
            .parentID(UPDATED_PARENT_ID)
            .definitionID(UPDATED_DEFINITION_ID);

        restTaskQuestionInstanceMockMvc.perform(put("/api/task-question-instances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTaskQuestionInstance)))
            .andExpect(status().isOk());

        // Validate the TaskQuestionInstance in the database
        List<TaskQuestionInstance> taskQuestionInstanceList = taskQuestionInstanceRepository.findAll();
        assertThat(taskQuestionInstanceList).hasSize(databaseSizeBeforeUpdate);
        TaskQuestionInstance testTaskQuestionInstance = taskQuestionInstanceList.get(taskQuestionInstanceList.size() - 1);
        assertThat(testTaskQuestionInstance.getSortInParentID()).isEqualTo(UPDATED_SORT_IN_PARENT_ID);
        assertThat(testTaskQuestionInstance.getDueDate()).isEqualTo(UPDATED_DUE_DATE);
        assertThat(testTaskQuestionInstance.getNotifiedDate()).isEqualTo(UPDATED_NOTIFIED_DATE);
        assertThat(testTaskQuestionInstance.getCompletedDate()).isEqualTo(UPDATED_COMPLETED_DATE);
        assertThat(testTaskQuestionInstance.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTaskQuestionInstance.getQuestionResponse()).isEqualTo(UPDATED_QUESTION_RESPONSE);
        assertThat(testTaskQuestionInstance.getParentID()).isEqualTo(UPDATED_PARENT_ID);
        assertThat(testTaskQuestionInstance.getDefinitionID()).isEqualTo(UPDATED_DEFINITION_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingTaskQuestionInstance() throws Exception {
        int databaseSizeBeforeUpdate = taskQuestionInstanceRepository.findAll().size();

        // Create the TaskQuestionInstance

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTaskQuestionInstanceMockMvc.perform(put("/api/task-question-instances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taskQuestionInstance)))
            .andExpect(status().isCreated());

        // Validate the TaskQuestionInstance in the database
        List<TaskQuestionInstance> taskQuestionInstanceList = taskQuestionInstanceRepository.findAll();
        assertThat(taskQuestionInstanceList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTaskQuestionInstance() throws Exception {
        // Initialize the database
        taskQuestionInstanceService.save(taskQuestionInstance);

        int databaseSizeBeforeDelete = taskQuestionInstanceRepository.findAll().size();

        // Get the taskQuestionInstance
        restTaskQuestionInstanceMockMvc.perform(delete("/api/task-question-instances/{id}", taskQuestionInstance.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TaskQuestionInstance> taskQuestionInstanceList = taskQuestionInstanceRepository.findAll();
        assertThat(taskQuestionInstanceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaskQuestionInstance.class);
        TaskQuestionInstance taskQuestionInstance1 = new TaskQuestionInstance();
        taskQuestionInstance1.setId(1L);
        TaskQuestionInstance taskQuestionInstance2 = new TaskQuestionInstance();
        taskQuestionInstance2.setId(taskQuestionInstance1.getId());
        assertThat(taskQuestionInstance1).isEqualTo(taskQuestionInstance2);
        taskQuestionInstance2.setId(2L);
        assertThat(taskQuestionInstance1).isNotEqualTo(taskQuestionInstance2);
        taskQuestionInstance1.setId(null);
        assertThat(taskQuestionInstance1).isNotEqualTo(taskQuestionInstance2);
    }
}
